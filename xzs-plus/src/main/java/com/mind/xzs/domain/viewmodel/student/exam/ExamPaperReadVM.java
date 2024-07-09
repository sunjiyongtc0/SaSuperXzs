package com.mind.xzs.domain.viewmodel.student.exam;

import com.mind.xzs.domain.viewmodel.admin.exam.ExamPaperEditRequestVM;
import lombok.Data;

@Data
public class ExamPaperReadVM {
    private ExamPaperEditRequestVM paper;
    private ExamPaperSubmitVM answer;


}
