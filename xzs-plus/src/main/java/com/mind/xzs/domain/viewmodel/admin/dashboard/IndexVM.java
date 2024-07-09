package com.mind.xzs.domain.viewmodel.admin.dashboard;



import lombok.Data;

import java.util.List;


@Data
public class IndexVM {
    private Integer examPaperCount;
    private Integer questionCount;
    private Integer doExamPaperCount;
    private Integer doQuestionCount;
    private List<Long> mothDayUserActionValue;
    private List<Long> mothDayDoExamQuestionValue;
    private List<String> mothDayText;


}
