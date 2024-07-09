package com.mind.xzs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mind.xzs.domain.*;
import com.mind.xzs.domain.enums.ExamPaperAnswerStatusEnum;
import com.mind.xzs.domain.enums.ExamPaperTypeEnum;
import com.mind.xzs.domain.enums.QuestionTypeEnum;
import com.mind.xzs.domain.exam.ExamPaperAnswerInfo;
import com.mind.xzs.domain.exam.ExamPaperTitleItemObject;
import com.mind.xzs.domain.viewmodel.student.exam.ExamPaperSubmitItemVM;
import com.mind.xzs.domain.viewmodel.student.exam.ExamPaperSubmitVM;
import com.mind.xzs.domain.viewmodel.student.exampaper.ExamPaperAnswerPageVM;
import com.mind.xzs.mapper.ExamPaperAnswerMapper;
import com.mind.xzs.service.*;

import com.mind.xzs.utils.ExamUtil;
import com.mind.xzs.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExamPaperAnswerServiceImpl extends ServiceImpl<ExamPaperAnswerMapper,ExamPaperAnswerEntity> implements ExamPaperAnswerService {


    @Autowired
    private ExamPaperService examPaperService;
    @Autowired
    private TextContentService textContentService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private ExamPaperQuestionCustomerAnswerService examPaperQuestionCustomerAnswerService;
//    private final ExamPaperMapper examPaperMapper;
//    private final TextContentService textContentService;
//    private final QuestionMapper questionMapper;
//    private final ExamPaperQuestionCustomerAnswerService examPaperQuestionCustomerAnswerService;
//    private final TaskExamCustomerAnswerMapper taskExamCustomerAnswerMapper;
//    @Autowired
//    public ExamPaperAnswerServiceImpl(ExamPaperAnswerMapper examPaperAnswerMapper, ExamPaperMapper examPaperMapper, TextContentService textContentService, QuestionMapper questionMapper, ExamPaperQuestionCustomerAnswerService examPaperQuestionCustomerAnswerService, TaskExamCustomerAnswerMapper taskExamCustomerAnswerMapper) {
//        super(examPaperAnswerMapper);
//        this.examPaperAnswerMapper = examPaperAnswerMapper;
//        this.examPaperMapper = examPaperMapper;
//        this.textContentService = textContentService;
//        this.questionMapper = questionMapper;
//        this.examPaperQuestionCustomerAnswerService = examPaperQuestionCustomerAnswerService;
//        this.taskExamCustomerAnswerMapper = taskExamCustomerAnswerMapper;
//    }

    @Override
    public PageInfo<ExamPaperAnswerEntity> studentPage(ExamPaperAnswerPageVM requestVM) {
        return PageHelper.startPage(requestVM.getPageIndex(), requestVM.getPageSize(), "id desc").doSelectPageInfo(() ->
                baseMapper.studentPage(requestVM));
    }


    @Override
    public ExamPaperAnswerInfo calculateExamPaperAnswer(ExamPaperSubmitVM examPaperSubmitVM, UserEntity user) {
        ExamPaperAnswerInfo examPaperAnswerInfo = new ExamPaperAnswerInfo();
        Date now = new Date();
//        ExamPaperEntity examPaper = examPaperMapper.selectByPrimaryKey(examPaperSubmitVM.getId());
        ExamPaperEntity examPaper = examPaperService.getById(examPaperSubmitVM.getId());
        ExamPaperTypeEnum paperTypeEnum = ExamPaperTypeEnum.fromCode(examPaper.getPaperType());
        //任务试卷只能做一次
        if (paperTypeEnum == ExamPaperTypeEnum.Task) {
            ExamPaperAnswerEntity examPaperAnswer = baseMapper.getByPidUid(examPaperSubmitVM.getId(), user.getId());
            if (null != examPaperAnswer)
                return null;
        }
        String frameTextContent = textContentService.getById(examPaper.getFrameTextContentId()).getContent();
        List<ExamPaperTitleItemObject> examPaperTitleItemObjects = JsonUtil.toJsonListObject(frameTextContent, ExamPaperTitleItemObject.class);
        List<Long> questionIds = examPaperTitleItemObjects.stream().flatMap(t -> t.getQuestionItems().stream().map(q -> q.getId())).collect(Collectors.toList());
        List<QuestionEntity> questions = questionService.listByIds(questionIds);
        //将题目结构的转化为题目答案
        List<ExamPaperQuestionCustomerAnswerEntity> examPaperQuestionCustomerAnswers = examPaperTitleItemObjects.stream()
                .flatMap(t -> t.getQuestionItems().stream()
                        .map(q -> {
                            QuestionEntity question = questions.stream().filter(tq -> tq.getId().equals(q.getId())).findFirst().get();
                            ExamPaperSubmitItemVM customerQuestionAnswer = examPaperSubmitVM.getAnswerItems().stream()
                                    .filter(tq -> tq.getQuestionId().equals(q.getId()))
                                    .findFirst()
                                    .orElse(null);
                            return ExamPaperQuestionCustomerAnswerFromVM(question, customerQuestionAnswer, examPaper, q.getItemOrder(), user, now);
                        })
                ).collect(Collectors.toList());

        ExamPaperAnswerEntity examPaperAnswer = ExamPaperAnswerFromVM(examPaperSubmitVM, examPaper, examPaperQuestionCustomerAnswers, user, now);
        examPaperAnswerInfo.setExamPaper(examPaper);
        examPaperAnswerInfo.setExamPaperAnswer(examPaperAnswer);
        examPaperAnswerInfo.setExamPaperQuestionCustomerAnswers(examPaperQuestionCustomerAnswers);
        return examPaperAnswerInfo;
    }
//
//    @Override
//    @Transactional
//    public String judge(ExamPaperSubmitVM examPaperSubmitVM) {
//        ExamPaperAnswer examPaperAnswer = examPaperAnswerMapper.selectByPrimaryKey(examPaperSubmitVM.getId());
//        List<ExamPaperSubmitItemVM> judgeItems = examPaperSubmitVM.getAnswerItems().stream().filter(d -> d.getDoRight() == null).collect(Collectors.toList());
//        List<ExamPaperAnswerUpdate> examPaperAnswerUpdates = new ArrayList<>(judgeItems.size());
//        Integer customerScore = examPaperAnswer.getUserScore();
//        Integer questionCorrect = examPaperAnswer.getQuestionCorrect();
//        for (ExamPaperSubmitItemVM d : judgeItems) {
//            ExamPaperAnswerUpdate examPaperAnswerUpdate = new ExamPaperAnswerUpdate();
//            examPaperAnswerUpdate.setId(d.getId());
//            examPaperAnswerUpdate.setCustomerScore(ExamUtil.scoreFromVM(d.getScore()));
//            boolean doRight = examPaperAnswerUpdate.getCustomerScore().equals(ExamUtil.scoreFromVM(d.getQuestionScore()));
//            examPaperAnswerUpdate.setDoRight(doRight);
//            examPaperAnswerUpdates.add(examPaperAnswerUpdate);
//            customerScore += examPaperAnswerUpdate.getCustomerScore();
//            if (examPaperAnswerUpdate.getDoRight()) {
//                ++questionCorrect;
//            }
//        }
//        examPaperAnswer.setUserScore(customerScore);
//        examPaperAnswer.setQuestionCorrect(questionCorrect);
//        examPaperAnswer.setStatus(ExamPaperAnswerStatusEnum.Complete.getCode());
//        examPaperAnswerMapper.updateByPrimaryKeySelective(examPaperAnswer);
//        examPaperQuestionCustomerAnswerService.updateScore(examPaperAnswerUpdates);
//
//        ExamPaperTypeEnum examPaperTypeEnum = ExamPaperTypeEnum.fromCode(examPaperAnswer.getPaperType());
//        switch (examPaperTypeEnum) {
//            case Task:
//                //任务试卷批改完成后，需要更新任务的状态
//                ExamPaper examPaper = examPaperMapper.selectByPrimaryKey(examPaperAnswer.getExamPaperId());
//                Integer taskId = examPaper.getTaskExamId();
//                Integer userId = examPaperAnswer.getCreateUser();
//                TaskExamCustomerAnswer taskExamCustomerAnswer = taskExamCustomerAnswerMapper.getByTUid(taskId, userId);
//                TextContent textContent = textContentService.selectById(taskExamCustomerAnswer.getTextContentId());
//                List<TaskItemAnswerObject> taskItemAnswerObjects = JsonUtil.toJsonListObject(textContent.getContent(), TaskItemAnswerObject.class);
//                taskItemAnswerObjects.stream()
//                        .filter(d -> d.getExamPaperAnswerId().equals(examPaperAnswer.getId()))
//                        .findFirst().ifPresent(taskItemAnswerObject -> taskItemAnswerObject.setStatus(examPaperAnswer.getStatus()));
//                textContentService.jsonConvertUpdate(textContent, taskItemAnswerObjects, null);
//                textContentService.updateByIdFilter(textContent);
//                break;
//            default:
//                break;
//        }
//        return ExamUtil.scoreToVM(customerScore);
//    }

    @Override
    public ExamPaperSubmitVM examPaperAnswerToVM(Long id) {
        ExamPaperSubmitVM examPaperSubmitVM = new ExamPaperSubmitVM();
        ExamPaperAnswerEntity examPaperAnswer = baseMapper.selectById(id);
        examPaperSubmitVM.setId(examPaperAnswer.getId());
        examPaperSubmitVM.setDoTime(examPaperAnswer.getDoTime());
        examPaperSubmitVM.setScore(ExamUtil.scoreToVM(examPaperAnswer.getUserScore()));
        List<ExamPaperQuestionCustomerAnswerEntity> examPaperQuestionCustomerAnswers = examPaperQuestionCustomerAnswerService.selectListByPaperAnswerId(examPaperAnswer.getId());
        List<ExamPaperSubmitItemVM> examPaperSubmitItemVMS = examPaperQuestionCustomerAnswers.stream()
                .map(a -> examPaperQuestionCustomerAnswerService.examPaperQuestionCustomerAnswerToVM(a))
                .collect(Collectors.toList());
        examPaperSubmitVM.setAnswerItems(examPaperSubmitItemVMS);
        return examPaperSubmitVM;
    }
//
//    @Override
//    public Integer selectAllCount() {
//        return examPaperAnswerMapper.selectAllCount();
//    }
//
//    @Override
//    public List<Integer> selectMothCount() {
//        Date startTime = DateTimeUtil.getMonthStartDay();
//        Date endTime = DateTimeUtil.getMonthEndDay();
//        List<KeyValue> mouthCount = examPaperAnswerMapper.selectCountByDate(startTime, endTime);
//        List<String> mothStartToNowFormat = DateTimeUtil.MothStartToNowFormat();
//        return mothStartToNowFormat.stream().map(md -> {
//            KeyValue keyValue = mouthCount.stream().filter(kv -> kv.getName().equals(md)).findAny().orElse(null);
//            return null == keyValue ? 0 : keyValue.getValue();
//        }).collect(Collectors.toList());
//    }


    /**
     * 用户提交答案的转化存储对象
     * @param question question
     * @param customerQuestionAnswer customerQuestionAnswer
     * @param examPaper examPaper
     * @param itemOrder itemOrder
     * @param user user
     * @param now now
     * @return ExamPaperQuestionCustomerAnswer
     */
    private ExamPaperQuestionCustomerAnswerEntity ExamPaperQuestionCustomerAnswerFromVM(QuestionEntity question, ExamPaperSubmitItemVM customerQuestionAnswer, ExamPaperEntity examPaper, Integer itemOrder, UserEntity user, Date now) {
        ExamPaperQuestionCustomerAnswerEntity examPaperQuestionCustomerAnswer = new ExamPaperQuestionCustomerAnswerEntity();
        examPaperQuestionCustomerAnswer.setQuestionId(question.getId());
        examPaperQuestionCustomerAnswer.setExamPaperId(examPaper.getId());
        examPaperQuestionCustomerAnswer.setQuestionScore(question.getScore());
        examPaperQuestionCustomerAnswer.setSubjectId(examPaper.getSubjectId());
        examPaperQuestionCustomerAnswer.setItemOrder(itemOrder);
        examPaperQuestionCustomerAnswer.setCreateTime(now);
        examPaperQuestionCustomerAnswer.setCreateUser(user.getId());
        examPaperQuestionCustomerAnswer.setQuestionType(question.getQuestionType());
        examPaperQuestionCustomerAnswer.setQuestionTextContentId(question.getInfoTextContentId());
        if (null == customerQuestionAnswer) {
            examPaperQuestionCustomerAnswer.setCustomerScore(0);
        } else {
            setSpecialFromVM(examPaperQuestionCustomerAnswer, question, customerQuestionAnswer);
        }
        return examPaperQuestionCustomerAnswer;
    }

    /**
     * 判断提交答案是否正确，保留用户提交的答案
     * @param examPaperQuestionCustomerAnswer examPaperQuestionCustomerAnswer
     * @param question  question
     * @param customerQuestionAnswer customerQuestionAnswer
     */
    private void setSpecialFromVM(ExamPaperQuestionCustomerAnswerEntity examPaperQuestionCustomerAnswer, QuestionEntity question, ExamPaperSubmitItemVM customerQuestionAnswer) {
        QuestionTypeEnum questionTypeEnum = QuestionTypeEnum.fromCode(examPaperQuestionCustomerAnswer.getQuestionType());
        switch (questionTypeEnum) {
            case SingleChoice:
            case TrueFalse:
                examPaperQuestionCustomerAnswer.setAnswer(customerQuestionAnswer.getContent());
                examPaperQuestionCustomerAnswer.setDoRight(question.getCorrect().equals(customerQuestionAnswer.getContent()));
                examPaperQuestionCustomerAnswer.setCustomerScore(examPaperQuestionCustomerAnswer.getDoRight() ? question.getScore() : 0);
                break;
            case MultipleChoice:
                String customerAnswer = ExamUtil.contentToString(customerQuestionAnswer.getContentArray());
                examPaperQuestionCustomerAnswer.setAnswer(customerAnswer);
                examPaperQuestionCustomerAnswer.setDoRight(customerAnswer.equals(question.getCorrect()));
                examPaperQuestionCustomerAnswer.setCustomerScore(examPaperQuestionCustomerAnswer.getDoRight() ? question.getScore() : 0);
                break;
            case GapFilling:
                String correctAnswer = JsonUtil.toJsonStr(customerQuestionAnswer.getContentArray());
                examPaperQuestionCustomerAnswer.setAnswer(correctAnswer);
                examPaperQuestionCustomerAnswer.setCustomerScore(0);
                break;
            default:
                examPaperQuestionCustomerAnswer.setAnswer(customerQuestionAnswer.getContent());
                examPaperQuestionCustomerAnswer.setCustomerScore(0);
                break;
        }
    }

    private ExamPaperAnswerEntity ExamPaperAnswerFromVM(ExamPaperSubmitVM examPaperSubmitVM, ExamPaperEntity examPaper, List<ExamPaperQuestionCustomerAnswerEntity> examPaperQuestionCustomerAnswers, UserEntity user, Date now) {
        Integer systemScore = examPaperQuestionCustomerAnswers.stream().mapToInt(a -> a.getCustomerScore()).sum();
        long questionCorrect = examPaperQuestionCustomerAnswers.stream().filter(a -> a.getCustomerScore().equals(a.getQuestionScore())).count();
        ExamPaperAnswerEntity examPaperAnswer = new ExamPaperAnswerEntity();
        examPaperAnswer.setPaperName(examPaper.getName());
        examPaperAnswer.setDoTime(examPaperSubmitVM.getDoTime());
        examPaperAnswer.setExamPaperId(examPaper.getId());
        examPaperAnswer.setCreateUser(user.getId());
        examPaperAnswer.setCreateTime(now);
        examPaperAnswer.setSubjectId(examPaper.getSubjectId());
        examPaperAnswer.setQuestionCount(examPaper.getQuestionCount());
        examPaperAnswer.setPaperScore(examPaper.getScore());
        examPaperAnswer.setPaperType(examPaper.getPaperType());
        examPaperAnswer.setSystemScore(systemScore);
        examPaperAnswer.setUserScore(systemScore);
        examPaperAnswer.setTaskExamId(examPaper.getTaskExamId());
        examPaperAnswer.setQuestionCorrect((int) questionCorrect);
        boolean needJudge = examPaperQuestionCustomerAnswers.stream().anyMatch(d -> QuestionTypeEnum.needSaveTextContent(d.getQuestionType()));
        if (needJudge) {
            examPaperAnswer.setStatus(ExamPaperAnswerStatusEnum.WaitJudge.getCode());
        } else {
            examPaperAnswer.setStatus(ExamPaperAnswerStatusEnum.Complete.getCode());
        }
        return examPaperAnswer;
    }

    @Override
    public PageInfo<ExamPaperAnswerEntity> adminPage(com.mind.xzs.domain.viewmodel.admin.paper.ExamPaperAnswerPageRequestVM requestVM) {
        return PageHelper.startPage(requestVM.getPageIndex(), requestVM.getPageSize(), "id desc").doSelectPageInfo(() ->
                baseMapper.adminPage(requestVM));
    }
}
