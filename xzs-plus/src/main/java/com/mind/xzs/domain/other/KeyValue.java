package com.mind.xzs.domain.other;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class KeyValue {

    private String name;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long value;


}
