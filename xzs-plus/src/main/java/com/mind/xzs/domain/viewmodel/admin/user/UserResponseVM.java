package com.mind.xzs.domain.viewmodel.admin.user;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class UserResponseVM  {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String userUuid;

    private String userName;

    private String realName;

    private Integer age;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long role;

    private Integer sex;

    private String birthDay;

    private String phone;

    private String lastActiveTime;

    private String createTime;

    private String modifyTime;

    private Integer status;

    private Integer userLevel;

    private String imagePath;


}
