package com.mind.xzs.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_task_exam")
public class TaskExamEntity implements Serializable {

    private static final long serialVersionUID = -7014704644631536195L;

    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 任务标题
     */
    private String title;

    /**
     * 年级
     */
    private Integer gradeLevel;

    /**
     * 任务框架 内容为JSON
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long frameTextContentId;

    /**
     * 创建者
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    private Boolean deleted;

    /**
     * 创建人用户名
     */
    private String createUserName;


}
