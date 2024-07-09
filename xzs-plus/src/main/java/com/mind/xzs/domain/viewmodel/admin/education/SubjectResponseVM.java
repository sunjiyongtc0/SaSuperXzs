package com.mind.xzs.domain.viewmodel.admin.education;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;


@Data
public class SubjectResponseVM  {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String name;

    private Integer level;

    private String levelName;


}
