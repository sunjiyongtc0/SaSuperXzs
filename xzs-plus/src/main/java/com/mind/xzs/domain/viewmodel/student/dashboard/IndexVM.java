package com.mind.xzs.domain.viewmodel.student.dashboard;


import lombok.Data;

import java.util.List;

@Data
public class IndexVM {
    private List<PaperInfo> fixedPaper;
    private List<PaperInfoVM> timeLimitPaper;
    private List<PaperInfo> pushPaper;

    public List<PaperInfo> getFixedPaper() {
        return fixedPaper;
    }


}
