package com.mind.xzs.domain.viewmodel.student.question.answer;


import com.mind.xzs.domain.viewmodel.admin.question.QuestionEditRequestVM;
import com.mind.xzs.domain.viewmodel.student.exam.ExamPaperSubmitItemVM;
import lombok.Data;

@Data
public class QuestionAnswerVM {
    private QuestionEditRequestVM questionVM;
    private ExamPaperSubmitItemVM questionAnswerVM;


}
