package com.mind.xzs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mind.xzs.domain.ExamPaperAnswerEntity;
import com.mind.xzs.domain.ExamPaperEntity;
import com.mind.xzs.domain.TaskExamCustomerAnswerEntity;
import com.mind.xzs.domain.TextContentEntity;
import com.mind.xzs.domain.task.TaskItemAnswerObject;
import com.mind.xzs.mapper.TaskExamCustomerAnswerMapper;
import com.mind.xzs.service.TaskExamCustomerAnswerService;
import com.mind.xzs.service.TextContentService;
import com.mind.xzs.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class TaskExamCustomerAnswerImpl extends ServiceImpl<TaskExamCustomerAnswerMapper,TaskExamCustomerAnswerEntity> implements TaskExamCustomerAnswerService {
//
//    private final TaskExamCustomerAnswerMapper taskExamCustomerAnswerMapper;
    @Autowired
    private TextContentService textContentService;
//
//    @Autowired
//    public TaskExamCustomerAnswerImpl(TaskExamCustomerAnswerMapper taskExamCustomerAnswerMapper, TextContentService textContentService) {
//        super(taskExamCustomerAnswerMapper);
//        this.taskExamCustomerAnswerMapper = taskExamCustomerAnswerMapper;
//        this.textContentService = textContentService;
//    }

    @Override
    public void insertOrUpdate(ExamPaperEntity examPaper, ExamPaperAnswerEntity examPaperAnswer, Date now) {
        Long taskId = examPaper.getTaskExamId();
        Long userId = examPaperAnswer.getCreateUser();
        TaskExamCustomerAnswerEntity taskExamCustomerAnswer = baseMapper.getByTUid(taskId, userId);
        if (null == taskExamCustomerAnswer) {
            taskExamCustomerAnswer = new TaskExamCustomerAnswerEntity();
            taskExamCustomerAnswer.setCreateTime(now);
            taskExamCustomerAnswer.setCreateUser(userId);
            taskExamCustomerAnswer.setTaskExamId(taskId);
            List<TaskItemAnswerObject> taskItemAnswerObjects = Arrays.asList(new TaskItemAnswerObject(examPaperAnswer.getExamPaperId(), examPaperAnswer.getId(), examPaperAnswer.getStatus()));
            TextContentEntity textContent = textContentService.jsonConvertInsert(taskItemAnswerObjects, now, null);
            textContentService.save(textContent);
            taskExamCustomerAnswer.setTextContentId(textContent.getId());
            baseMapper.insert(taskExamCustomerAnswer);
        } else {
            TextContentEntity textContent = textContentService.getById(taskExamCustomerAnswer.getTextContentId());
            List<TaskItemAnswerObject> taskItemAnswerObjects = JsonUtil.toJsonListObject(textContent.getContent(), TaskItemAnswerObject.class);
            taskItemAnswerObjects.add(new TaskItemAnswerObject(examPaperAnswer.getExamPaperId(), examPaperAnswer.getId(), examPaperAnswer.getStatus()));
            textContentService.jsonConvertUpdate(textContent, taskItemAnswerObjects, null);
            textContentService.updateById(textContent);
        }
    }
//
//    @Override
//    public TaskExamCustomerAnswer selectByTUid(Integer tid, Integer uid) {
//        return taskExamCustomerAnswerMapper.getByTUid(tid, uid);
//    }

    @Override
    public List<TaskExamCustomerAnswerEntity> selectByTUid(List<Long> taskIds, Long uid) {
        return baseMapper.selectByTUid(taskIds, uid);
    }
}
