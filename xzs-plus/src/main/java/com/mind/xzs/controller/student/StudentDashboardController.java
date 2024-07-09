package com.mind.xzs.controller.student;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mind.xzs.controller.BaseApiController;
import com.mind.xzs.core.response.RestResponse;
import com.mind.xzs.domain.TaskExamCustomerAnswerEntity;
import com.mind.xzs.domain.TaskExamEntity;
import com.mind.xzs.domain.TextContentEntity;
import com.mind.xzs.domain.UserEntity;
import com.mind.xzs.domain.enums.ExamPaperTypeEnum;
import com.mind.xzs.domain.task.TaskItemAnswerObject;
import com.mind.xzs.domain.task.TaskItemObject;
import com.mind.xzs.domain.viewmodel.student.dashboard.*;
import com.mind.xzs.service.*;
import com.mind.xzs.utils.DateTimeUtil;
import com.mind.xzs.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController("StudentDashboardController")
@RequestMapping(value = "/api/student/dashboard")
public class StudentDashboardController extends BaseApiController {

    @Autowired
    private UserService userService;
    @Autowired
    private ExamPaperService examPaperService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private TaskExamService taskExamService;
    @Autowired
    private  TaskExamCustomerAnswerService taskExamCustomerAnswerService;
    @Autowired
    private  TextContentService textContentService;



    @RequestMapping(value = "/index", method = RequestMethod.POST)
    public RestResponse<IndexVM> index() {
        IndexVM indexVM = new IndexVM();
        UserEntity user = getCurrentUser();

        PaperFilter fixedPaperFilter = new PaperFilter();
        fixedPaperFilter.setGradeLevel(user.getUserLevel());
        fixedPaperFilter.setExamPaperType(ExamPaperTypeEnum.Fixed.getCode());
        indexVM.setFixedPaper(examPaperService.indexPaper(fixedPaperFilter));

        PaperFilter timeLimitPaperFilter = new PaperFilter();
        timeLimitPaperFilter.setDateTime(new Date());
        timeLimitPaperFilter.setGradeLevel(user.getUserLevel());
        timeLimitPaperFilter.setExamPaperType(ExamPaperTypeEnum.TimeLimit.getCode());

        List<PaperInfo> limitPaper = examPaperService.indexPaper(timeLimitPaperFilter);
        List<PaperInfoVM> paperInfoVMS = limitPaper.stream().map(d -> {
            PaperInfoVM vm = BeanUtil.copyProperties(d, PaperInfoVM.class);
            vm.setStartTime(DateTimeUtil.dateFormat(d.getLimitStartTime()));
            vm.setEndTime(DateTimeUtil.dateFormat(d.getLimitEndTime()));
            return vm;
        }).collect(Collectors.toList());
        indexVM.setTimeLimitPaper(paperInfoVMS);
        return RestResponse.ok(indexVM);
    }


    @RequestMapping(value = "/task", method = RequestMethod.POST)
    public RestResponse<List<TaskItemVm>> task() {
        UserEntity user = getCurrentUser();
        QueryWrapper<TaskExamEntity> qwte=new QueryWrapper<>();
        qwte.eq("deleted",0);
        qwte.eq("grade_level",user.getUserLevel());
        List<TaskExamEntity> taskExams = taskExamService.list(qwte);
//        List<TaskExam> taskExams = taskExamService.getByGradeLevel(user.getUserLevel());
        if (taskExams.size() == 0) {
            return RestResponse.ok(new ArrayList<>());
        }
        List<Long> tIds = taskExams.stream().map(taskExam -> taskExam.getId()).collect(Collectors.toList());
        QueryWrapper<TaskExamCustomerAnswerEntity> qwteca=new QueryWrapper<>();
        qwteca.eq("create_user",user.getId());
        qwteca.in("task_exam_id",tIds);
//        List<TaskExamCustomerAnswerEntity> taskExamCustomerAnswers = taskExamCustomerAnswerService.selectByTUid(tIds, user.getId());
        List<TaskExamCustomerAnswerEntity> taskExamCustomerAnswers = taskExamCustomerAnswerService.list(qwteca);
        List<TaskItemVm> vm = taskExams.stream().map(t -> {
            TaskItemVm itemVm = new TaskItemVm();
            itemVm.setId(t.getId());
            itemVm.setTitle(t.getTitle());
            TaskExamCustomerAnswerEntity taskExamCustomerAnswer = taskExamCustomerAnswers.stream()
                    .filter(tc -> tc.getTaskExamId().equals(t.getId())).findFirst().orElse(null);
            List<TaskItemPaperVm> paperItemVMS = getTaskItemPaperVm(t.getFrameTextContentId(), taskExamCustomerAnswer);
            itemVm.setPaperItems(paperItemVMS);
            return itemVm;
        }).collect(Collectors.toList());
        return RestResponse.ok(vm);
    }


    private List<TaskItemPaperVm> getTaskItemPaperVm(Long tFrameId, TaskExamCustomerAnswerEntity taskExamCustomerAnswers) {
        TextContentEntity textContent = textContentService.getById(tFrameId);
        List<TaskItemObject> paperItems = JsonUtil.toJsonListObject(textContent.getContent(), TaskItemObject.class);

        List<TaskItemAnswerObject> answerPaperItems = null;
        if (null != taskExamCustomerAnswers) {
            TextContentEntity answerTextContent = textContentService.getById(taskExamCustomerAnswers.getTextContentId());
            answerPaperItems = JsonUtil.toJsonListObject(answerTextContent.getContent(), TaskItemAnswerObject.class);
        }


        List<TaskItemAnswerObject> finalAnswerPaperItems = answerPaperItems;
        return paperItems.stream().map(p -> {
                    TaskItemPaperVm ivm = new TaskItemPaperVm();
                    ivm.setExamPaperId(p.getExamPaperId());
                    ivm.setExamPaperName(p.getExamPaperName());
                    if (null != finalAnswerPaperItems) {
                        finalAnswerPaperItems.stream()
                                .filter(a -> a.getExamPaperId().equals(p.getExamPaperId()))
                                .findFirst()
                                .ifPresent(a -> {
                                    ivm.setExamPaperAnswerId(a.getExamPaperAnswerId());
                                    ivm.setStatus(a.getStatus());
                                });
                    }
                    return ivm;
                }
        ).collect(Collectors.toList());
    }
}
