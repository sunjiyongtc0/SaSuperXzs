package com.mind.xzs.domain.viewmodel.admin.exam;



import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;


@Data
public class ExamPaperEditRequestVM {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
//    @NotNull
    private Integer level;
//    @NotNull
@JsonSerialize(using = ToStringSerializer.class)
    private Long subjectId;
//    @NotNull
    private Integer paperType;
//    @NotBlank
    private String name;
//    @NotNull
    private Integer suggestTime;

    private List<String> limitDateTime;

//    @Size(min = 1,message = "请添加试卷标题")
//    @Valid
    private List<ExamPaperTitleItemVM> titleItems;

    private String score;


}
