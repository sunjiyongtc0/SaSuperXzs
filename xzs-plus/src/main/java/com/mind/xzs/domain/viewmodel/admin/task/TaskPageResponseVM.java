package com.mind.xzs.domain.viewmodel.admin.task;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class TaskPageResponseVM {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String title;

    private Integer gradeLevel;

    private String createUserName;

    private String createTime;

    private Boolean deleted;


}
