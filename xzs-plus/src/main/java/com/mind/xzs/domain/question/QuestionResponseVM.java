package com.mind.xzs.domain.question;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class QuestionResponseVM {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private Integer questionType;

    private Integer textContentId;

    private String createTime;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long subjectId;

    private Integer createUser;

    private String score;

    private Integer status;

    private String correct;

    private Integer analyzeTextContentId;

    private Integer difficult;

    private String shortTitle;

   }
