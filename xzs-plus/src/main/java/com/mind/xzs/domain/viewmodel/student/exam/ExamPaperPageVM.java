package com.mind.xzs.domain.viewmodel.student.exam;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.mind.xzs.domain.BasePage;
import lombok.Data;


@Data
public class ExamPaperPageVM extends BasePage {
//    @NotNull
    private Integer paperType;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long subjectId;

    private Integer levelId;


}
