package com.mind.xzs.domain.viewmodel.student.education;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class SubjectEditRequestVM  {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

//    @NotBlank
    private String name;

//    @NotNull
    private Integer level;

//    @NotBlank
    private String levelName;


}
