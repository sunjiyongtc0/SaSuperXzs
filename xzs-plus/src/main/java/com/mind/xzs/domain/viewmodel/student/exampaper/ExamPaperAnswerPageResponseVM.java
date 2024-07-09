package com.mind.xzs.domain.viewmodel.student.exampaper;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class ExamPaperAnswerPageResponseVM {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String createTime;

    private String userScore;

    private String subjectName;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long subjectId;

    private Integer questionCount;

    private Integer questionCorrect;

    private String paperScore;

    private String doTime;

    private Integer paperType;

    private String systemScore;

    private Integer status;

    private String paperName;

    private String userName;


}
