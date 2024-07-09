package com.mind.xzs.domain.task;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class TaskItemAnswerObject {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long examPaperId;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long examPaperAnswerId;

    private Integer status;


    public TaskItemAnswerObject(){

    }

    public TaskItemAnswerObject(Long examPaperId, Long examPaperAnswerId, Integer status) {
        this.examPaperId = examPaperId;
        this.examPaperAnswerId = examPaperAnswerId;
        this.status = status;
    }
}
