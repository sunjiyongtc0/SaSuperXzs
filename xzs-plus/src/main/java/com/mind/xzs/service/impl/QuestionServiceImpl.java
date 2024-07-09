package com.mind.xzs.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mind.xzs.domain.QuestionEntity;
import com.mind.xzs.domain.TextContentEntity;
import com.mind.xzs.domain.enums.QuestionStatusEnum;
import com.mind.xzs.domain.enums.QuestionTypeEnum;
import com.mind.xzs.domain.question.QuestionItemObject;
import com.mind.xzs.domain.question.QuestionObject;
import com.mind.xzs.domain.question.QuestionPageRequestVM;
import com.mind.xzs.domain.viewmodel.admin.question.QuestionEditItemVM;
import com.mind.xzs.domain.viewmodel.admin.question.QuestionEditRequestVM;
import com.mind.xzs.mapper.QuestionMapper;
import com.mind.xzs.service.QuestionService;
import com.mind.xzs.service.SubjectService;
import com.mind.xzs.service.TextContentService;
import com.mind.xzs.utils.ExamUtil;
import com.mind.xzs.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, QuestionEntity> implements QuestionService {


    @Autowired
    private TextContentService textContentService;

    @Autowired
    private SubjectService subjectService;
    @Override
    public PageInfo<QuestionEntity> page(QuestionPageRequestVM requestVM) {
        return PageHelper.startPage(requestVM.getPageIndex(), requestVM.getPageSize(), "id desc").doSelectPageInfo(() ->
                baseMapper.page(requestVM)
        );
    }


    @Override
    @Transactional
    public QuestionEntity insertFullQuestion(QuestionEditRequestVM model, Long userId) {
        Date now = new Date();
        Integer gradeLevel = subjectService.levelBySubjectId(model.getSubjectId());

        //题干、解析、选项等 插入
        TextContentEntity infoTextContent = new TextContentEntity();
        infoTextContent.setCreateTime(now);
        setQuestionInfoFromVM(infoTextContent, model);
        textContentService.save(infoTextContent);

        QuestionEntity question = new QuestionEntity();
        question.setSubjectId(model.getSubjectId());
        question.setGradeLevel(gradeLevel);
        question.setCreateTime(now);
        question.setQuestionType(model.getQuestionType());
        question.setStatus(QuestionStatusEnum.OK.getCode());
        question.setCorrectFromVM(model.getCorrect(), model.getCorrectArray());
        question.setScore(ExamUtil.scoreFromVM(model.getScore()));
        question.setDifficult(model.getDifficult());
        question.setInfoTextContentId(infoTextContent.getId());
        question.setCreateUser(userId);
        question.setDeleted(false);
        baseMapper.insert(question);
        return question;
    }

    @Override
    @Transactional
    public QuestionEntity updateFullQuestion(QuestionEditRequestVM model) {
        Integer gradeLevel = subjectService.levelBySubjectId(model.getSubjectId());
        QuestionEntity question = baseMapper.selectById(model.getId());
        question.setSubjectId(model.getSubjectId());
        question.setGradeLevel(gradeLevel);
        question.setScore(ExamUtil.scoreFromVM(model.getScore()));
        question.setDifficult(model.getDifficult());
        question.setCorrectFromVM(model.getCorrect(), model.getCorrectArray());
        baseMapper.updateById(question);

        //题干、解析、选项等 更新
        TextContentEntity infoTextContent = textContentService.getById(question.getInfoTextContentId());
        setQuestionInfoFromVM(infoTextContent, model);
        textContentService.updateById(infoTextContent);

        return question;
    }

    @Override
    public QuestionEditRequestVM getQuestionEditRequestVM(Long questionId) {
        //题目映射
        QuestionEntity question = baseMapper.selectById(questionId);
        return getQuestionEditRequestVM(question);
    }

    @Override
    public QuestionEditRequestVM getQuestionEditRequestVM(QuestionEntity question) {
        //题目映射
        TextContentEntity questionInfoTextContent = textContentService.getById(question.getInfoTextContentId());
        QuestionObject questionObject = JsonUtil.toJsonObject(questionInfoTextContent.getContent(), QuestionObject.class);
        QuestionEditRequestVM questionEditRequestVM = BeanUtil.copyProperties(question, QuestionEditRequestVM.class);
        questionEditRequestVM.setTitle(questionObject.getTitleContent());

        //答案
        QuestionTypeEnum questionTypeEnum = QuestionTypeEnum.fromCode(question.getQuestionType());
        switch (questionTypeEnum) {
            case SingleChoice:
            case TrueFalse:
                questionEditRequestVM.setCorrect(question.getCorrect());
                break;
            case MultipleChoice:
                questionEditRequestVM.setCorrectArray(ExamUtil.contentToArray(question.getCorrect()));
                break;
            case GapFilling:
                List<String> correctContent = questionObject.getQuestionItemObjects().stream().map(d -> d.getContent()).collect(Collectors.toList());
                questionEditRequestVM.setCorrectArray(correctContent);
                break;
            case ShortAnswer:
                questionEditRequestVM.setCorrect(questionObject.getCorrect());
                break;
            default:
                break;
        }
        questionEditRequestVM.setScore(ExamUtil.scoreToVM(question.getScore()));
        questionEditRequestVM.setAnalyze(questionObject.getAnalyze());


        //题目项映射
        List<QuestionEditItemVM> editItems = questionObject.getQuestionItemObjects().stream().map(o -> {
            QuestionEditItemVM questionEditItemVM = BeanUtil.copyProperties(o, QuestionEditItemVM.class);
            if (o.getScore() != null) {
                questionEditItemVM.setScore(ExamUtil.scoreToVM(o.getScore()));
            }
            return questionEditItemVM;
        }).collect(Collectors.toList());
        questionEditRequestVM.setItems(editItems);
        return questionEditRequestVM;
    }

    public void setQuestionInfoFromVM(TextContentEntity infoTextContent, QuestionEditRequestVM model) {
        List<QuestionItemObject> itemObjects = model.getItems().stream().map(i ->
                {
                    QuestionItemObject item = new QuestionItemObject();
                    item.setPrefix(i.getPrefix());
                    item.setContent(i.getContent());
                    item.setItemUuid(i.getItemUuid());
                    item.setScore(ExamUtil.scoreFromVM(i.getScore()));
                    return item;
                }
        ).collect(Collectors.toList());
        QuestionObject questionObject = new QuestionObject();
        questionObject.setQuestionItemObjects(itemObjects);
        questionObject.setAnalyze(model.getAnalyze());
        questionObject.setTitleContent(model.getTitle());
        questionObject.setCorrect(model.getCorrect());
        infoTextContent.setContent(JsonUtil.toJsonStr(questionObject));
    }
//
//    @Override
//    public Integer selectAllCount() {
//        return questionMapper.selectAllCount();
//    }
//
//    @Override
//    public List<Integer> selectMothCount() {
//        Date startTime = DateTimeUtil.getMonthStartDay();
//        Date endTime = DateTimeUtil.getMonthEndDay();
//        List<String> mothStartToNowFormat = DateTimeUtil.MothStartToNowFormat();
//        List<KeyValue> mouthCount = questionMapper.selectCountByDate(startTime, endTime);
//        return mothStartToNowFormat.stream().map(md -> {
//            KeyValue keyValue = mouthCount.stream().filter(kv -> kv.getName().equals(md)).findAny().orElse(null);
//            return null == keyValue ? 0 : keyValue.getValue();
//        }).collect(Collectors.toList());
//    }


}
