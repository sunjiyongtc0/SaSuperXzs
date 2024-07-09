package com.mind.xzs.domain.viewmodel.student.dashboard;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.Date;

@Data
public class PaperInfo {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String name;
    private Date limitStartTime;
    private Date limitEndTime;


}
