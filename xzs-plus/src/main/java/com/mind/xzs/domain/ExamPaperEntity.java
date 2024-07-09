package com.mind.xzs.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_exam_paper")
public class ExamPaperEntity implements Serializable {

    private static final long serialVersionUID = 8509645224550501395L;

    @JsonSerialize(using = ToStringSerializer.class)
    @TableId
    private Long id;

    /**
     * 试卷名称
     */
    private String name;

    /**
     * 学科
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long subjectId;

    /**
     * 试卷类型( 1固定试卷 4.时段试卷 6.任务试卷)
     */
    private Integer paperType;

    /**
     * 年级
     */
    private Integer gradeLevel;

    /**
     * 试卷总分(千分制)
     */
    private Integer score;

    /**
     * 题目数量
     */
    private Integer questionCount;

    /**
     * 建议时长(分钟)
     */
    private Integer suggestTime;

    /**
     * 时段试卷 开始时间
     */
    private Date limitStartTime;

    /**
     * 时段试卷 结束时间
     */
    private Date limitEndTime;

    /**
     * 试卷框架 内容为JSON
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long frameTextContentId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long createUser;

    private Date createTime;

    private Boolean deleted;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long taskExamId;


}
