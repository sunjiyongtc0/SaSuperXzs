package com.mind.xzs.domain.viewmodel.admin.user;



import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;


@Data
public class UserCreateVM {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

//    @NotBlank
    private String userName;

    private String password;

//    @NotBlank
    private String realName;

    private String age;

    private Integer status;

    private Integer sex;

    private String birthDay;

    private String phone;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long role;

    private Integer userLevel;


}
