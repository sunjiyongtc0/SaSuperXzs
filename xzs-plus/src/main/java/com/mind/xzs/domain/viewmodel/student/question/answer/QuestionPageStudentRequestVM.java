package com.mind.xzs.domain.viewmodel.student.question.answer;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.mind.xzs.domain.BasePage;
import lombok.Data;

@Data
public class QuestionPageStudentRequestVM extends BasePage {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long createUser;


}
