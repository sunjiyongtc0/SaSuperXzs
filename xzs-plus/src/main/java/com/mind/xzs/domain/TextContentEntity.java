package com.mind.xzs.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_text_content")
public class TextContentEntity implements Serializable {

    private static final long serialVersionUID = -1279530310964668131L;

    public TextContentEntity(){

    }

    public TextContentEntity(String content, Date createTime) {
        this.content = content;
        this.createTime = createTime;
    }
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 内容(Json)
     */
    private String content;

    /**
     * 创建时间
     */
    private Date createTime;


}
