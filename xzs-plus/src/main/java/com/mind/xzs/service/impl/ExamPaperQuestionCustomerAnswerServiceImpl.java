package com.mind.xzs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.mind.xzs.domain.ExamPaperQuestionCustomerAnswerEntity;
import com.mind.xzs.domain.TextContentEntity;
import com.mind.xzs.domain.enums.QuestionTypeEnum;
import com.mind.xzs.domain.other.KeyValue;
import com.mind.xzs.domain.viewmodel.student.exam.ExamPaperSubmitItemVM;
import com.mind.xzs.domain.viewmodel.student.question.answer.QuestionPageStudentRequestVM;
import com.mind.xzs.mapper.ExamPaperQuestionCustomerAnswerMapper;
import com.mind.xzs.service.ExamPaperQuestionCustomerAnswerService;
import com.mind.xzs.service.TextContentService;
import com.mind.xzs.utils.DateTimeUtil;
import com.mind.xzs.utils.ExamUtil;
import com.mind.xzs.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExamPaperQuestionCustomerAnswerServiceImpl extends ServiceImpl<ExamPaperQuestionCustomerAnswerMapper,ExamPaperQuestionCustomerAnswerEntity> implements ExamPaperQuestionCustomerAnswerService {

    @Autowired
    private TextContentService textContentService;



    @Override
    public PageInfo<ExamPaperQuestionCustomerAnswerEntity> studentPage(QuestionPageStudentRequestVM requestVM) {
        return PageHelper.startPage(requestVM.getPageIndex(), requestVM.getPageSize(), "id desc").doSelectPageInfo(() ->
                baseMapper.studentPage(requestVM)
        );
    }

    @Override
    public List<ExamPaperQuestionCustomerAnswerEntity> selectListByPaperAnswerId(Long id) {
        return baseMapper.selectListByPaperAnswerId(id);
    }

//
//    @Override
//    public void insertList(List<ExamPaperQuestionCustomerAnswer> examPaperQuestionCustomerAnswers) {
//        examPaperQuestionCustomerAnswerMapper.insertList(examPaperQuestionCustomerAnswers);
//    }

    @Override
    public ExamPaperSubmitItemVM examPaperQuestionCustomerAnswerToVM(ExamPaperQuestionCustomerAnswerEntity qa) {
        ExamPaperSubmitItemVM examPaperSubmitItemVM = new ExamPaperSubmitItemVM();
        examPaperSubmitItemVM.setId(qa.getId());
        examPaperSubmitItemVM.setQuestionId(qa.getQuestionId());
        examPaperSubmitItemVM.setDoRight(qa.getDoRight());
        examPaperSubmitItemVM.setItemOrder(qa.getItemOrder());
        examPaperSubmitItemVM.setQuestionScore(ExamUtil.scoreToVM(qa.getQuestionScore()));
        examPaperSubmitItemVM.setScore(ExamUtil.scoreToVM(qa.getCustomerScore()));
        setSpecialToVM(examPaperSubmitItemVM, qa);
        return examPaperSubmitItemVM;
    }
//
//    @Override
//    public Integer selectAllCount() {
//        return examPaperQuestionCustomerAnswerMapper.selectAllCount();
//    }

    @Override
    public List<Long> selectMothCount() {
        Date startTime = DateTimeUtil.getMonthStartDay();
        Date endTime = DateTimeUtil.getMonthEndDay();
        List<KeyValue> mouthCount = baseMapper.selectCountByDate(startTime, endTime);
        List<String> mothStartToNowFormat = DateTimeUtil.MothStartToNowFormat();
        return mothStartToNowFormat.stream().map(md -> {
            KeyValue keyValue = mouthCount.stream().filter(kv -> kv.getName().equals(md)).findAny().orElse(null);
            return null == keyValue ? 0 : keyValue.getValue();
        }).collect(Collectors.toList());
    }
//
//    @Override
//    public int updateScore(List<ExamPaperAnswerUpdate> examPaperAnswerUpdates) {
//        return examPaperQuestionCustomerAnswerMapper.updateScore(examPaperAnswerUpdates);
//    }

    private void setSpecialToVM(ExamPaperSubmitItemVM examPaperSubmitItemVM, ExamPaperQuestionCustomerAnswerEntity examPaperQuestionCustomerAnswer) {
        QuestionTypeEnum questionTypeEnum = QuestionTypeEnum.fromCode(examPaperQuestionCustomerAnswer.getQuestionType());
        switch (questionTypeEnum) {
            case MultipleChoice:
                examPaperSubmitItemVM.setContent(examPaperQuestionCustomerAnswer.getAnswer());
                examPaperSubmitItemVM.setContentArray(ExamUtil.contentToArray(examPaperQuestionCustomerAnswer.getAnswer()));
                break;
            case GapFilling:
                TextContentEntity textContent = textContentService.getById(examPaperQuestionCustomerAnswer.getTextContentId());
                List<String> correctAnswer = JsonUtil.toJsonListObject(textContent.getContent(), String.class);
                examPaperSubmitItemVM.setContentArray(correctAnswer);
                break;
            default:
                if (QuestionTypeEnum.needSaveTextContent(examPaperQuestionCustomerAnswer.getQuestionType())) {
                    TextContentEntity content = textContentService.getById(examPaperQuestionCustomerAnswer.getTextContentId());
                    examPaperSubmitItemVM.setContent(content.getContent());
                } else {
                    examPaperSubmitItemVM.setContent(examPaperQuestionCustomerAnswer.getAnswer());
                }
                break;
        }
    }
}
