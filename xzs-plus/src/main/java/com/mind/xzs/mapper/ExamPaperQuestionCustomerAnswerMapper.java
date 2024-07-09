package com.mind.xzs.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mind.xzs.domain.ExamPaperQuestionCustomerAnswerEntity;
import com.mind.xzs.domain.other.KeyValue;
import com.mind.xzs.domain.viewmodel.student.question.answer.QuestionPageStudentRequestVM;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface ExamPaperQuestionCustomerAnswerMapper extends BaseMapper<ExamPaperQuestionCustomerAnswerEntity> {

    List<ExamPaperQuestionCustomerAnswerEntity> selectListByPaperAnswerId(Long id);

    List<ExamPaperQuestionCustomerAnswerEntity> studentPage(QuestionPageStudentRequestVM requestVM);
//
//    int insertList(List<ExamPaperQuestionCustomerAnswer> list);
//
//    Integer selectAllCount();
//
    List<KeyValue> selectCountByDate(@Param("startTime") Date startTime, @Param("endTime") Date endTime);
//
//    int updateScore(List<ExamPaperAnswerUpdate> examPaperAnswerUpdates);
}
