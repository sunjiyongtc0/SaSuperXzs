package com.mind.xzs.domain.viewmodel.user;


import lombok.Data;

@Data
public class UserUpdateVM {

//    @NotBlank
    private String realName;

    private String age;

    private Integer sex;

    private String birthDay;

    private String phone;

//    @NotNull
    private Integer userLevel;


}
