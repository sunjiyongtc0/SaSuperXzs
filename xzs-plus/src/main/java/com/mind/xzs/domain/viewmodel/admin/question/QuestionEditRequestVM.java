package com.mind.xzs.domain.viewmodel.admin.question;



import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;

@Data
public class QuestionEditRequestVM {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
//    @NotNull
    private Integer questionType;
//    @NotNull
@JsonSerialize(using = ToStringSerializer.class)
    private Long subjectId;
//    @NotBlank
    private String title;

    private Integer gradeLevel;

//    @Valid
    private List<QuestionEditItemVM> items;
//    @NotBlank
    private String analyze;

    private List<String> correctArray;

    private String correct;
//    @NotBlank
    private String score;

//    @Range(min = 1, max = 5, message = "请选择题目难度")
    private Integer difficult;

    private Integer itemOrder;


}
