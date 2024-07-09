package com.mind.xzs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.mind.xzs.domain.ExamPaperQuestionCustomerAnswerEntity;
import com.mind.xzs.domain.viewmodel.student.exam.ExamPaperSubmitItemVM;
import com.mind.xzs.domain.viewmodel.student.question.answer.QuestionPageStudentRequestVM;


import java.util.List;

public interface ExamPaperQuestionCustomerAnswerService extends IService<ExamPaperQuestionCustomerAnswerEntity> {

    PageInfo<ExamPaperQuestionCustomerAnswerEntity> studentPage(QuestionPageStudentRequestVM requestVM);

    List<ExamPaperQuestionCustomerAnswerEntity> selectListByPaperAnswerId(Long id);
//
//    /**
//     * 试卷提交答案入库
//     *
//     * @param examPaperQuestionCustomerAnswers List<ExamPaperQuestionCustomerAnswer>
//     */
//    void insertList(List<ExamPaperQuestionCustomerAnswer> examPaperQuestionCustomerAnswers);

    /**
     * 试卷问题答题信息转成ViewModel 传给前台
     *
     * @param qa ExamPaperQuestionCustomerAnswer
     * @return ExamPaperSubmitItemVM
     */
    ExamPaperSubmitItemVM examPaperQuestionCustomerAnswerToVM(ExamPaperQuestionCustomerAnswerEntity qa);
//
//
//    Integer selectAllCount();
//
    List<Long> selectMothCount();
//
//    int updateScore(List<ExamPaperAnswerUpdate> examPaperAnswerUpdates);
}
