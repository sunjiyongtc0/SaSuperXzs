package com.mind.xzs.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_exam_paper_question_customer_answer")
public class ExamPaperQuestionCustomerAnswerEntity implements Serializable {

    private static final long serialVersionUID = 3389482731220342366L;

    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 题目Id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long questionId;

    /**
     * 试卷Id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long examPaperId;

    /**
     * 答案Id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long examPaperAnswerId;

    /**
     * 题型
     */
    private Integer questionType;

    /**
     * 学科
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long subjectId;

    /**
     * 得分
     */
    private Integer customerScore;

    /**
     * 题目原始分数
     */
    private Integer questionScore;

    /**
     * 问题内容
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long questionTextContentId;

    /**
     * 做题答案
     */
    private String answer;

    /**
     * 做题内容
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long textContentId;

    /**
     * 是否正确
     */
    private Boolean doRight;

    /**
     * 做题人
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long createUser;

    private Date createTime;

    private Integer itemOrder;


}
