package com.mind.xzs.domain.viewmodel.student.dashboard;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class TaskItemPaperVm {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long examPaperId;
    private String examPaperName;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long examPaperAnswerId;
    private Integer status;


}
