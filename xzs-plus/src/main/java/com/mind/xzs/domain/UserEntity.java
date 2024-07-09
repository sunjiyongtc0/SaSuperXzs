package com.mind.xzs.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_user")
public class UserEntity implements Serializable {

    private static final long serialVersionUID=1L;

    // 使用ToStringSerializer将Long类型的id字段转换为String类型
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId
    private Long id;

    @TableField("user_uuid")
    private String userUuid;

    /**
     * 用户名
     */
    @TableField("user_name")
    private String userName;

    @TableField("password")
    private String password;

    /**
     * 真实姓名
     */
    @TableField("real_name")
    private String realName;

    @TableField("age")
    private Integer age;

    /**
     * 1.男 2女
     */
    @TableField("sex")
    private Integer sex;

    @TableField("birth_day")
    private Date birthDay;

    /**
     * 学生年级(1-12)
     */
    @TableField("user_level")
    private Integer userLevel;

    @TableField("phone")
    private String phone;

    /**
     * 1.学生  3.管理员
     */
    @TableField("role")
    private Long role;

    /**
     * 0.启用 1禁用
     */
    @TableField("status")
    private Integer status;

    /**
     * 头像地址
     */
    @TableField("image_path")
    private String imagePath;

    @TableField("create_time")
    private Date createTime;

    @TableField("modify_time")
    private Date modifyTime;

    @TableField("last_active_time")
    private Date lastActiveTime;

    /**
     * 是否删除
     */
    @TableField("deleted")
    private Boolean deleted;

    /**
     * 微信openId
     */
    @TableField("wx_open_id")
    private String wxOpenId;

    @TableField(exist = false)
    private Integer PageIndex;

    @TableField(exist = false)
    private Integer pageSize;



//    public static UserEntity from(UserEntity user) {
//        UserEntity vm = modelMapper.map(user, UserEntity.class);
//        vm.setBirthDay(DateTimeUtil.dateFormat(user.getBirthDay()));
//        vm.setLastActiveTime(DateTimeUtil.dateFormat(user.getLastActiveTime()));
//        vm.setCreateTime(DateTimeUtil.dateFormat(user.getCreateTime()));
//        vm.setModifyTime(DateTimeUtil.dateFormat(user.getModifyTime()));
//        return vm;
//    }
   }
