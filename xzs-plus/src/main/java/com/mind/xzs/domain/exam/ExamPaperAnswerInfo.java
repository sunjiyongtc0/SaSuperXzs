package com.mind.xzs.domain.exam;


import com.mind.xzs.domain.ExamPaperAnswerEntity;
import com.mind.xzs.domain.ExamPaperEntity;
import com.mind.xzs.domain.ExamPaperQuestionCustomerAnswerEntity;
import lombok.Data;

import java.util.List;

@Data
public class ExamPaperAnswerInfo {
    public ExamPaperEntity examPaper;
    public ExamPaperAnswerEntity examPaperAnswer;
    public List<ExamPaperQuestionCustomerAnswerEntity> examPaperQuestionCustomerAnswers;


}
