package com.mind.xzs.domain.viewmodel.admin.education;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.mind.xzs.domain.BasePage;
import lombok.Data;


@Data
public class SubjectPageRequestVM extends BasePage {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private Integer level;

}
