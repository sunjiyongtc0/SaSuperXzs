package com.mind.xzs.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_user_token")
public class UserTokenEntity implements Serializable {

    private static final long serialVersionUID = -2414443061696200360L;
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 用户token
     */
    private String token;

    /**
     * 用户Id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    /**
     * 微信小程序openId
     */
    private String wxOpenId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 用户名
     */
    private String userName;

    }
