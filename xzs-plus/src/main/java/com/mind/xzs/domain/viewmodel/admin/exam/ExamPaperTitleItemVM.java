package com.mind.xzs.domain.viewmodel.admin.exam;

import com.mind.xzs.domain.viewmodel.admin.question.QuestionEditRequestVM;
import lombok.Data;

import java.util.List;

@Data
public class ExamPaperTitleItemVM {

//    @NotBlank(message = "标题内容不能为空")
    private String name;

//    @Size(min = 1,message = "请添加题目")
//    @Valid
    private List<QuestionEditRequestVM> questionItems;


}
