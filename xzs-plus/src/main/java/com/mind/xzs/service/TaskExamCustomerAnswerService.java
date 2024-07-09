package com.mind.xzs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mind.xzs.domain.ExamPaperAnswerEntity;
import com.mind.xzs.domain.ExamPaperEntity;
import com.mind.xzs.domain.TaskExamCustomerAnswerEntity;

import java.util.Date;
import java.util.List;

public interface TaskExamCustomerAnswerService extends IService<TaskExamCustomerAnswerEntity> {

    void insertOrUpdate(ExamPaperEntity examPaper, ExamPaperAnswerEntity examPaperAnswer, Date now);
//
//    TaskExamCustomerAnswer selectByTUid(Integer tid, Integer uid);
//
    List<TaskExamCustomerAnswerEntity> selectByTUid(List<Long> taskIds, Long uid);
}
