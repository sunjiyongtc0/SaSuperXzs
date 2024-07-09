package com.mind.xzs.domain.viewmodel.admin.exam;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.mind.xzs.domain.BasePage;
import lombok.Data;


@Data
public class ExamPaperPageRequestVM extends BasePage {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long subjectId;
    private Integer level;
    private Integer paperType;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long taskExamId;

}
