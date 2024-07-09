package com.mind.xzs.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_message_user")
public class MessageUserEntity implements Serializable {

    private static final long serialVersionUID = -4042932811802896498L;

    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 消息内容ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long messageId;

    /**
     * 接收人ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long receiveUserId;

    /**
     * 接收人用户名
     */
    private String receiveUserName;

    /**
     * 接收人真实姓名
     */
    private String receiveRealName;

    /**
     * 是否已读
     */
    private Boolean readed;

    private Date createTime;

    /**
     * 阅读时间
     */
    private Date readTime;


}
