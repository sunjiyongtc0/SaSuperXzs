package com.mind.xzs.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_exam_paper_answer")
public class ExamPaperAnswerEntity implements Serializable {

    private static final long serialVersionUID = -2143539181805283910L;

    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long examPaperId;

    /**
     * 试卷名称
     */
    private String paperName;

    /**
     * 试卷类型( 1固定试卷 4.时段试卷 6.任务试卷)
     */
    private Integer paperType;

    /**
     * 学科
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long subjectId;

    /**
     * 系统判定得分
     */
    private Integer systemScore;

    /**
     * 最终得分(千分制)
     */
    private Integer userScore;

    /**
     * 试卷总分
     */
    private Integer paperScore;

    /**
     * 做对题目数量
     */
    private Integer questionCorrect;

    /**
     * 题目总数量
     */
    private Integer questionCount;

    /**
     * 做题时间(秒)
     */
    private Integer doTime;

    /**
     * 试卷状态(1待判分 2完成)
     */
    private Integer status;

    /**
     * 学生
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long createUser;

    /**
     * 提交时间
     */
    private Date createTime;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long taskExamId;


}
