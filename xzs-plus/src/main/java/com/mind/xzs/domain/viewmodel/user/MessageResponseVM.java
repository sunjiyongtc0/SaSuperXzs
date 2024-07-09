package com.mind.xzs.domain.viewmodel.user;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class MessageResponseVM {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String title;

    private Long messageId;

    private String content;

    private Boolean readed;

    private String  createTime;

    private String sendUserName;

    }
