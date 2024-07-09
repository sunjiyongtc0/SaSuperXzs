package com.mind.xzs.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
@TableName("t_task_exam_customer_answer")
public class TaskExamCustomerAnswerEntity implements Serializable {

    private static final long serialVersionUID = -556842372977600137L;

    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 任务Id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long taskExamId;

    /**
     * 创建者
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 任务完成情况(Json)
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long textContentId;


}
