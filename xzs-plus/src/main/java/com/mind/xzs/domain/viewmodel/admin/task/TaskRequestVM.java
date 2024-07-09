package com.mind.xzs.domain.viewmodel.admin.task;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.mind.xzs.domain.viewmodel.admin.exam.ExamResponseVM;
import lombok.Data;


import java.util.List;

@Data
public class TaskRequestVM {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

//    @NotNull
    private Integer gradeLevel;

//    @NotNull
    private String title;

//    @Size(min = 1, message = "请添加试卷")
//    @Valid
    private List<ExamResponseVM> paperItems;

}
