package com.mind.xzs.domain.viewmodel.admin.paper;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.mind.xzs.domain.BasePage;
import lombok.Data;

@Data
public class ExamPaperAnswerPageRequestVM extends BasePage {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long subjectId;


}
