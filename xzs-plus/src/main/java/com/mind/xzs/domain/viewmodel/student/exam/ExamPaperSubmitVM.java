package com.mind.xzs.domain.viewmodel.student.exam;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;


@Data
public class ExamPaperSubmitVM {

//    @NotNull
@JsonSerialize(using = ToStringSerializer.class)
    private Long id;

//    @NotNull
    private Integer doTime;

    private String score;

//    @NotNull
//    @Valid
    private List<ExamPaperSubmitItemVM> answerItems;


}
