package com.mind.xzs.domain.viewmodel.student.dashboard;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;

@Data
public class TaskItemVm {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String title;
    private List<TaskItemPaperVm> paperItems;


}
