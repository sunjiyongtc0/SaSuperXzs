package com.mind.xzs.domain.viewmodel.admin.user;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.mind.xzs.domain.BasePage;
import lombok.Data;

@Data
public class UserPageRequestVM extends BasePage {

    private String userName;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long role;


}
