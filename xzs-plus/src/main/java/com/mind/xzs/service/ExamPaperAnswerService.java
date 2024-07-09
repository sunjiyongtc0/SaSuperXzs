package com.mind.xzs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.mind.xzs.domain.ExamPaperAnswerEntity;
import com.mind.xzs.domain.UserEntity;
import com.mind.xzs.domain.exam.ExamPaperAnswerInfo;
import com.mind.xzs.domain.viewmodel.student.exam.ExamPaperSubmitVM;
import com.mind.xzs.domain.viewmodel.student.exampaper.ExamPaperAnswerPageVM;

import java.util.List;

public interface ExamPaperAnswerService extends IService<ExamPaperAnswerEntity> {

    /**
     * 学生考试记录分页
     *
     * @param requestVM 过滤条件
     * @return PageInfo<ExamPaperAnswer>
     */
    PageInfo<ExamPaperAnswerEntity> studentPage(ExamPaperAnswerPageVM requestVM);

    /**
     * 计算试卷提交结果(不入库)
     *
     * @param examPaperSubmitVM
     * @param user
     * @return
     */
    ExamPaperAnswerInfo calculateExamPaperAnswer(ExamPaperSubmitVM examPaperSubmitVM, UserEntity user);
//
//
//    /**
//     * 试卷批改
//     * @param examPaperSubmitVM  examPaperSubmitVM
//     * @return String
//     */
//    String judge(ExamPaperSubmitVM examPaperSubmitVM);

    /**
     * 试卷答题信息转成ViewModel 传给前台
     *
     * @param id 试卷id
     * @return ExamPaperSubmitVM
     */
    ExamPaperSubmitVM examPaperAnswerToVM(Long id);
//
//
//    Integer selectAllCount();
//
//    List<Integer> selectMothCount();
//
    PageInfo<ExamPaperAnswerEntity> adminPage(com.mind.xzs.domain.viewmodel.admin.paper.ExamPaperAnswerPageRequestVM requestVM);
}
