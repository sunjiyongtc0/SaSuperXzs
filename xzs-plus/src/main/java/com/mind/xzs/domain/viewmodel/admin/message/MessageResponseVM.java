package com.mind.xzs.domain.viewmodel.admin.message;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class MessageResponseVM {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String title;

    private String content;

    private String sendUserName;

    private String receives;

    private Integer receiveUserCount;

    private Integer readCount;

    private String createTime;


}
