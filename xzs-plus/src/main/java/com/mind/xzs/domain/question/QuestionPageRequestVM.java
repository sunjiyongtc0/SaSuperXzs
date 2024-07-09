package com.mind.xzs.domain.question;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.mind.xzs.domain.BasePage;
import lombok.Data;


@Data
public class QuestionPageRequestVM extends BasePage {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private Integer level;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long subjectId;
    private Integer questionType;


}
