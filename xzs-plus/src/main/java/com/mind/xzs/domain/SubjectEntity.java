package com.mind.xzs.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("t_subject")
public class SubjectEntity implements Serializable {


    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 语文 数学 英语 等
     */
    private String name;

    /**
     * 年级 (1-12) 小学 初中
     */
    private Integer level;

    /**
     * 一年级、二年级等
     */
    private String levelName;

    /**
     * 排序
     */
    private Integer itemOrder;

    private Boolean deleted;


}
