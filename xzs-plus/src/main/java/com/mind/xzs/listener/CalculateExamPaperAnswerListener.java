package com.mind.xzs.listener;


import com.mind.xzs.domain.ExamPaperAnswerEntity;
import com.mind.xzs.domain.ExamPaperEntity;
import com.mind.xzs.domain.ExamPaperQuestionCustomerAnswerEntity;
import com.mind.xzs.domain.TextContentEntity;
import com.mind.xzs.domain.enums.ExamPaperTypeEnum;
import com.mind.xzs.domain.enums.QuestionTypeEnum;
import com.mind.xzs.domain.exam.ExamPaperAnswerInfo;
import com.mind.xzs.event.CalculateExamPaperAnswerCompleteEvent;
import com.mind.xzs.service.ExamPaperAnswerService;
import com.mind.xzs.service.ExamPaperQuestionCustomerAnswerService;
import com.mind.xzs.service.TaskExamCustomerAnswerService;
import com.mind.xzs.service.TextContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


/**
 * @version 3.5.0
 * @description: The type Calculate exam paper answer listener.
 * Copyright (C), 2020-2024, 武汉思维跳跃科技有限公司
 * @date 2021/12/25 9:45
 */
@Component
public class CalculateExamPaperAnswerListener implements ApplicationListener<CalculateExamPaperAnswerCompleteEvent> {
    @Autowired
    private ExamPaperAnswerService examPaperAnswerService;
    @Autowired
    private ExamPaperQuestionCustomerAnswerService examPaperQuestionCustomerAnswerService;
    @Autowired
    private TextContentService textContentService;
    @Autowired
    private TaskExamCustomerAnswerService examCustomerAnswerService;


    @Override
    @Transactional
    public void onApplicationEvent(CalculateExamPaperAnswerCompleteEvent calculateExamPaperAnswerCompleteEvent) {
        Date now = new Date();

        ExamPaperAnswerInfo examPaperAnswerInfo = (ExamPaperAnswerInfo) calculateExamPaperAnswerCompleteEvent.getSource();
        ExamPaperEntity examPaper = examPaperAnswerInfo.getExamPaper();
        ExamPaperAnswerEntity examPaperAnswer = examPaperAnswerInfo.getExamPaperAnswer();
        List<ExamPaperQuestionCustomerAnswerEntity> examPaperQuestionCustomerAnswers = examPaperAnswerInfo.getExamPaperQuestionCustomerAnswers();

        examPaperAnswerService.save(examPaperAnswer);
        examPaperQuestionCustomerAnswers.stream().filter(a -> QuestionTypeEnum.needSaveTextContent(a.getQuestionType())).forEach(d -> {
            TextContentEntity textContent = new TextContentEntity(d.getAnswer(), now);
            textContentService.save(textContent);
            d.setTextContentId(textContent.getId());
            d.setAnswer(null);
        });
        examPaperQuestionCustomerAnswers.forEach(d -> {
            d.setExamPaperAnswerId(examPaperAnswer.getId());
        });
        examPaperQuestionCustomerAnswerService.saveBatch(examPaperQuestionCustomerAnswers);

        switch (ExamPaperTypeEnum.fromCode(examPaper.getPaperType())) {
            case Task: {
                examCustomerAnswerService.insertOrUpdate(examPaper, examPaperAnswer, now);
                break;
            }
            default:
                break;
        }
    }
}
