package com.mind.xzs.domain.viewmodel.student.exam;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;

@Data
public class ExamPaperSubmitItemVM {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
//    @NotNull
    private Long questionId;

    private Boolean doRight;

    private String content;

    private Integer itemOrder;

    private List<String> contentArray;

    private String score;

    private String questionScore;


}
