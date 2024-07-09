package com.mind.xzs.domain.viewmodel.admin.question;


import lombok.Data;



@Data
public class QuestionEditItemVM {
//    @NotBlank
    private String prefix;
//    @NotBlank
    private String content;

    private String score;

    private String itemUuid;



}
