package com.mind.xzs.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mind.xzs.domain.ExamPaperEntity;
import com.mind.xzs.domain.TaskExamEntity;
import com.mind.xzs.domain.TextContentEntity;
import com.mind.xzs.domain.UserEntity;
import com.mind.xzs.domain.task.TaskItemObject;
import com.mind.xzs.domain.viewmodel.admin.exam.ExamResponseVM;
import com.mind.xzs.domain.viewmodel.admin.task.TaskPageRequestVM;
import com.mind.xzs.domain.viewmodel.admin.task.TaskRequestVM;
import com.mind.xzs.mapper.TaskExamMapper;
import com.mind.xzs.service.ExamPaperService;
import com.mind.xzs.service.TaskExamService;
import com.mind.xzs.service.TextContentService;
import com.mind.xzs.utils.DateTimeUtil;
import com.mind.xzs.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskExamServiceImpl extends ServiceImpl<TaskExamMapper,TaskExamEntity> implements TaskExamService {
//
//    protected final static ModelMapper modelMapper = ModelMapperSingle.Instance();
//    private final TaskExamMapper taskExamMapper;
    @Autowired
    private TextContentService textContentService;

    @Autowired
    private  ExamPaperService examPaperService;
//
//    @Autowired
//    public TaskExamServiceImpl(TaskExamMapper taskExamMapper, TextContentService textContentService, ExamPaperMapper examPaperMapper) {
//        super(taskExamMapper);
//        this.taskExamMapper = taskExamMapper;
//        this.textContentService = textContentService;
//        this.examPaperMapper = examPaperMapper;
//    }

    @Override
    public PageInfo<TaskExamEntity> page(TaskPageRequestVM requestVM) {
        return PageHelper.startPage(requestVM.getPageIndex(), requestVM.getPageSize(), "id desc").doSelectPageInfo(() ->
                baseMapper.page(requestVM)
        );
    }

    @Override
    @Transactional
    public void edit(TaskRequestVM model, UserEntity user) {
//        ActionEnum actionEnum = (model.getId() == null) ? ActionEnum.ADD : ActionEnum.UPDATE;
        TaskExamEntity taskExam = null;
        if (model.getId() == null) {
            Date now = new Date();
            taskExam = BeanUtil.copyProperties(model, TaskExamEntity.class);
            taskExam.setCreateUser(user.getId());
            taskExam.setCreateUserName(user.getUserName());
            taskExam.setCreateTime(now);
            taskExam.setDeleted(false);

            //保存任务结构
            TextContentEntity textContent = textContentService.jsonConvertInsert(model.getPaperItems(), now, p -> {
                TaskItemObject taskItemObject = new TaskItemObject();
                taskItemObject.setExamPaperId(p.getId());
                taskItemObject.setExamPaperName(p.getName());
                return taskItemObject;
            });
            textContentService.save(textContent);
            taskExam.setFrameTextContentId(textContent.getId());
            baseMapper.insert(taskExam);

        } else {
            taskExam = baseMapper.selectById(model.getId());
            BeanUtil.copyProperties(model, taskExam);

            TextContentEntity textContent = textContentService.getById(taskExam.getFrameTextContentId());
            //清空试卷任务的试卷Id，后面会统一设置
            List<Long> paperIds = JsonUtil.toJsonListObject(textContent.getContent(), TaskItemObject.class)
                    .stream()
                    .map(d -> d.getExamPaperId())
                    .collect(Collectors.toList());
            UpdateWrapper<ExamPaperEntity> uw=new UpdateWrapper<>();
            uw.set("task_exam_id",null);
            uw.in("id",paperIds);
            examPaperService.update(uw);

            //更新任务结构
            textContentService.jsonConvertUpdate(textContent, model.getPaperItems(), p -> {
                TaskItemObject taskItemObject = new TaskItemObject();
                taskItemObject.setExamPaperId(p.getId());
                taskItemObject.setExamPaperName(p.getName());
                return taskItemObject;
            });
            textContentService.updateById(textContent);
            baseMapper.updateById(taskExam);
        }

        //更新试卷的taskId
        List<Long> paperIds = model.getPaperItems().stream().map(d -> d.getId()).collect(Collectors.toList());
        UpdateWrapper<ExamPaperEntity> uw=new UpdateWrapper<>();
        uw.set("task_exam_id",taskExam.getId());
        uw.in("id",paperIds);
        examPaperService.update(uw);
        model.setId(taskExam.getId());
    }

    @Override
    public TaskRequestVM taskExamToVM(Long id) {
        TaskExamEntity taskExam = baseMapper.selectById(id);
        TaskRequestVM vm = BeanUtil.copyProperties(taskExam, TaskRequestVM.class);
        TextContentEntity textContent = textContentService.getById(taskExam.getFrameTextContentId());

        List<Long> examPaperIds = JsonUtil.toJsonListObject(textContent.getContent(), TaskItemObject.class).stream().map(tk -> tk.getExamPaperId()).collect(Collectors.toList());
        List<ExamPaperEntity> examPaperEntities = examPaperService.listByIds(examPaperIds);

        List<ExamResponseVM> examResponseVMS = JsonUtil.toJsonListObject(textContent.getContent(), TaskItemObject.class).stream().map(tk -> {
//            ExamPaperEntity examPaper = examPaperService.getById(tk.getExamPaperId());
            List<ExamPaperEntity> collect = examPaperEntities.stream().filter(e -> StrUtil.equals(e.getId().toString(), tk.getExamPaperId().toString())).collect(Collectors.toList());
            ExamPaperEntity examPaper =collect.get(0);
            ExamResponseVM examResponseVM = BeanUtil.copyProperties(examPaper, ExamResponseVM.class);
            examResponseVM.setCreateTime(DateTimeUtil.dateFormat(examPaper.getCreateTime()));
            return examResponseVM;
        }).collect(Collectors.toList());
        vm.setPaperItems(examResponseVMS);
        return vm;
    }

    @Override
    public List<TaskExamEntity> getByGradeLevel(Integer gradeLevel) {
        return baseMapper.getByGradeLevel(gradeLevel);
    }
}
