package com.mind.xzs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.mind.xzs.domain.QuestionEntity;
import com.mind.xzs.domain.question.QuestionPageRequestVM;
import com.mind.xzs.domain.viewmodel.admin.question.QuestionEditRequestVM;

import java.util.List;

public interface QuestionService extends IService<QuestionEntity> {

    PageInfo<QuestionEntity> page(QuestionPageRequestVM requestVM);

    QuestionEntity insertFullQuestion(QuestionEditRequestVM model, Long userId);

    QuestionEntity updateFullQuestion(QuestionEditRequestVM model);

    QuestionEditRequestVM getQuestionEditRequestVM(Long questionId);

    QuestionEditRequestVM getQuestionEditRequestVM(QuestionEntity question);
//
//    Integer selectAllCount();
//
//    List<Integer> selectMothCount();
}
