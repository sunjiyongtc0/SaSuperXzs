package com.mind.xzs.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_message")
public class MessageEntity implements Serializable {

    private static final long serialVersionUID = -3510265139403747341L;

    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    private Date createTime;

    /**
     * 发送者用户ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long sendUserId;

    /**
     * 发送者用户名
     */
    private String sendUserName;

    /**
     * 发送者真实姓名
     */
    private String sendRealName;

    /**
     * 接收人数
     */
    private Integer receiveUserCount;

    /**
     * 已读人数
     */
    private Integer readCount;


}
