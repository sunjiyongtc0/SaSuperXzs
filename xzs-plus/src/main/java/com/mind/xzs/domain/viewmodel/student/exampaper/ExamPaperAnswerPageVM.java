package com.mind.xzs.domain.viewmodel.student.exampaper;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.mind.xzs.domain.BasePage;
import lombok.Data;

@Data
public class ExamPaperAnswerPageVM extends BasePage {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long subjectId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long createUser;


}
