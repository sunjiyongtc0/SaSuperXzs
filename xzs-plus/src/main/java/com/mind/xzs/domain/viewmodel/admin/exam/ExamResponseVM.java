package com.mind.xzs.domain.viewmodel.admin.exam;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class ExamResponseVM {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String name;

    private Integer questionCount;

    private Integer score;

    private String createTime;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long createUser;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long subjectId;

    private Integer paperType;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long frameTextContentId;

}
