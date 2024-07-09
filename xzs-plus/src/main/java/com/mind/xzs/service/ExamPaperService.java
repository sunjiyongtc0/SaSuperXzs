package com.mind.xzs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.mind.xzs.domain.ExamPaperEntity;
import com.mind.xzs.domain.UserEntity;
import com.mind.xzs.domain.viewmodel.admin.exam.ExamPaperEditRequestVM;
import com.mind.xzs.domain.viewmodel.admin.exam.ExamPaperPageRequestVM;
import com.mind.xzs.domain.viewmodel.student.dashboard.PaperFilter;
import com.mind.xzs.domain.viewmodel.student.dashboard.PaperInfo;
import com.mind.xzs.domain.viewmodel.student.exam.ExamPaperPageVM;

import java.util.List;

public interface ExamPaperService extends IService<ExamPaperEntity> {

    PageInfo<ExamPaperEntity> page(ExamPaperPageRequestVM requestVM);

    PageInfo<ExamPaperEntity> taskExamPage(ExamPaperPageRequestVM requestVM);

    PageInfo<ExamPaperEntity> studentPage(ExamPaperPageVM requestVM);

    ExamPaperEntity savePaperFromVM(ExamPaperEditRequestVM examPaperEditRequestVM, UserEntity user);

    ExamPaperEditRequestVM examPaperToVM(Long id);

    List<PaperInfo> indexPaper(PaperFilter paperFilter);
//
//    Integer selectAllCount();
//
//    List<Integer> selectMothCount();
}
