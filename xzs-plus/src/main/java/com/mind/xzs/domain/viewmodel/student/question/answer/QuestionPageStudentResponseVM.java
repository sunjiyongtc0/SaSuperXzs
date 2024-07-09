package com.mind.xzs.domain.viewmodel.student.question.answer;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class QuestionPageStudentResponseVM {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private Integer questionType;

    private String createTime;

    private String subjectName;

    private String shortTitle;


}
