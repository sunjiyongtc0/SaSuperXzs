package com.mind.xzs.domain.task;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class TaskItemObject {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long examPaperId;
    private String examPaperName;
    private Integer itemOrder;

}
