package com.mind.xzs.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mind.xzs.domain.ExamPaperEntity;
import com.mind.xzs.domain.QuestionEntity;
import com.mind.xzs.domain.TextContentEntity;
import com.mind.xzs.domain.UserEntity;
import com.mind.xzs.domain.enums.ExamPaperTypeEnum;
import com.mind.xzs.domain.exam.ExamPaperQuestionItemObject;
import com.mind.xzs.domain.exam.ExamPaperTitleItemObject;
import com.mind.xzs.domain.viewmodel.admin.exam.ExamPaperEditRequestVM;
import com.mind.xzs.domain.viewmodel.admin.exam.ExamPaperPageRequestVM;
import com.mind.xzs.domain.viewmodel.admin.exam.ExamPaperTitleItemVM;
import com.mind.xzs.domain.viewmodel.admin.question.QuestionEditRequestVM;
import com.mind.xzs.domain.viewmodel.student.dashboard.PaperFilter;
import com.mind.xzs.domain.viewmodel.student.dashboard.PaperInfo;
import com.mind.xzs.domain.viewmodel.student.exam.ExamPaperPageVM;
import com.mind.xzs.mapper.ExamPaperMapper;
import com.mind.xzs.service.ExamPaperService;
import com.mind.xzs.service.QuestionService;
import com.mind.xzs.service.SubjectService;
import com.mind.xzs.service.TextContentService;
import com.mind.xzs.utils.DateTimeUtil;
import com.mind.xzs.utils.ExamUtil;
import com.mind.xzs.utils.JsonUtil;
import com.mind.xzs.utils.SnowFlake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class ExamPaperServiceImpl extends ServiceImpl<ExamPaperMapper, ExamPaperEntity> implements ExamPaperService {


//    private final QuestionMapper questionMapper;
//    private final TextContentService textContentService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private TextContentService textContentService;
//
//    @Autowired
//    public ExamPaperServiceImpl(ExamPaperMapper examPaperMapper, QuestionMapper questionMapper, TextContentService textContentService, QuestionService questionService, SubjectService subjectService) {
//        super(examPaperMapper);
//        this.examPaperMapper = examPaperMapper;
//        this.questionMapper = questionMapper;
//        this.textContentService = textContentService;
//        this.questionService = questionService;
//        this.subjectService = subjectService;
//    }
//
//
    @Override
    public PageInfo<ExamPaperEntity> page(ExamPaperPageRequestVM requestVM) {
        return PageHelper.startPage(requestVM.getPageIndex(), requestVM.getPageSize(), "id desc").doSelectPageInfo(() ->
                baseMapper.page(requestVM));
    }

    @Override
    public PageInfo<ExamPaperEntity> taskExamPage(ExamPaperPageRequestVM requestVM) {
        return PageHelper.startPage(requestVM.getPageIndex(), requestVM.getPageSize(), "id desc").doSelectPageInfo(() ->
                baseMapper.taskExamPage(requestVM));
    }

    @Override
    public PageInfo<ExamPaperEntity> studentPage(ExamPaperPageVM requestVM) {
        return PageHelper.startPage(requestVM.getPageIndex(), requestVM.getPageSize(), "id desc").doSelectPageInfo(() ->
                baseMapper.studentPage(requestVM));
    }


    @Override
    @Transactional
    public ExamPaperEntity savePaperFromVM(ExamPaperEditRequestVM examPaperEditRequestVM, UserEntity user) {
        Date now = new Date();
        List<ExamPaperTitleItemVM> titleItemsVM = examPaperEditRequestVM.getTitleItems();
        List<ExamPaperTitleItemObject> frameTextContentList = frameTextContentFromVM(titleItemsVM);
        String frameTextContentStr = JsonUtil.toJsonStr(frameTextContentList);

        ExamPaperEntity examPaper;
        if (examPaperEditRequestVM.getId() == null) {
            examPaper = BeanUtil.copyProperties(examPaperEditRequestVM, ExamPaperEntity.class);
            TextContentEntity frameTextContent = new TextContentEntity(frameTextContentStr, now);
            frameTextContent.setId(SnowFlake.generateId());
            textContentService.save(frameTextContent);
            examPaper.setFrameTextContentId(frameTextContent.getId());
            examPaper.setCreateTime(now);
            examPaper.setCreateUser(user.getId());
            examPaper.setDeleted(false);
            examPaper.setId(SnowFlake.generateId());
            examPaperFromVM(examPaperEditRequestVM, examPaper, titleItemsVM);
            baseMapper.insert(examPaper);
        } else {
            examPaper = baseMapper.selectById(examPaperEditRequestVM.getId());
            TextContentEntity frameTextContent = textContentService.getById(examPaper.getFrameTextContentId());
            frameTextContent.setContent(frameTextContentStr);
            textContentService.updateById(frameTextContent);
            BeanUtil.copyProperties(examPaperEditRequestVM, examPaper);
            examPaperFromVM(examPaperEditRequestVM, examPaper, titleItemsVM);
            baseMapper.updateById(examPaper);
        }
        return examPaper;
    }

    @Override
    public ExamPaperEditRequestVM examPaperToVM(Long id) {
        ExamPaperEntity examPaper = baseMapper.selectById(id);
        ExamPaperEditRequestVM vm = BeanUtil.copyProperties(examPaper, ExamPaperEditRequestVM.class);
        vm.setLevel(examPaper.getGradeLevel());
        TextContentEntity frameTextContent = textContentService.getById(examPaper.getFrameTextContentId());
        List<ExamPaperTitleItemObject> examPaperTitleItemObjects = JsonUtil.toJsonListObject(frameTextContent.getContent(), ExamPaperTitleItemObject.class);
        List<Long> questionIds = examPaperTitleItemObjects.stream()
                .flatMap(t -> t.getQuestionItems().stream()
                        .map(q -> q.getId()))
                .collect(Collectors.toList());
        List<QuestionEntity> questions =questionService.listByIds(questionIds);
        List<ExamPaperTitleItemVM> examPaperTitleItemVMS = examPaperTitleItemObjects.stream().map(t -> {
            ExamPaperTitleItemVM tTitleVM = BeanUtil.copyProperties(t, ExamPaperTitleItemVM.class);
            List<QuestionEditRequestVM> questionItemsVM = t.getQuestionItems().stream().map(i -> {
                QuestionEntity question = questions.stream().filter(q -> q.getId().equals(i.getId())).findFirst().get();
                QuestionEditRequestVM questionEditRequestVM = questionService.getQuestionEditRequestVM(question);
                questionEditRequestVM.setItemOrder(i.getItemOrder());
                return questionEditRequestVM;
            }).collect(Collectors.toList());
            tTitleVM.setQuestionItems(questionItemsVM);
            return tTitleVM;
        }).collect(Collectors.toList());
        vm.setTitleItems(examPaperTitleItemVMS);
        vm.setScore(ExamUtil.scoreToVM(examPaper.getScore()));
        if (ExamPaperTypeEnum.TimeLimit == ExamPaperTypeEnum.fromCode(examPaper.getPaperType())) {
            List<String> limitDateTime = Arrays.asList(DateTimeUtil.dateFormat(examPaper.getLimitStartTime()), DateTimeUtil.dateFormat(examPaper.getLimitEndTime()));
            vm.setLimitDateTime(limitDateTime);
        }
        return vm;
    }

    @Override
    public List<PaperInfo> indexPaper(PaperFilter paperFilter) {
        return baseMapper.indexPaper(paperFilter);
    }
//
//
//    @Override
//    public Integer selectAllCount() {
//        return examPaperMapper.selectAllCount();
//    }
//
//    @Override
//    public List<Integer> selectMothCount() {
//        Date startTime = DateTimeUtil.getMonthStartDay();
//        Date endTime = DateTimeUtil.getMonthEndDay();
//        List<KeyValue> mouthCount = examPaperMapper.selectCountByDate(startTime, endTime);
//        List<String> mothStartToNowFormat = DateTimeUtil.MothStartToNowFormat();
//        return mothStartToNowFormat.stream().map(md -> {
//            KeyValue keyValue = mouthCount.stream().filter(kv -> kv.getName().equals(md)).findAny().orElse(null);
//            return null == keyValue ? 0 : keyValue.getValue();
//        }).collect(Collectors.toList());
//    }
//
    private void examPaperFromVM(ExamPaperEditRequestVM examPaperEditRequestVM, ExamPaperEntity examPaper, List<ExamPaperTitleItemVM> titleItemsVM) {
        Integer gradeLevel = subjectService.levelBySubjectId(examPaperEditRequestVM.getSubjectId());
        Integer questionCount = titleItemsVM.stream()
                .mapToInt(t -> t.getQuestionItems().size()).sum();
        Integer score = titleItemsVM.stream().
                flatMapToInt(t -> t.getQuestionItems().stream()
                        .mapToInt(q -> ExamUtil.scoreFromVM(q.getScore()))
                ).sum();
        examPaper.setQuestionCount(questionCount);
        examPaper.setScore(score);
        examPaper.setGradeLevel(gradeLevel);
        List<String> dateTimes = examPaperEditRequestVM.getLimitDateTime();
        if (ExamPaperTypeEnum.TimeLimit == ExamPaperTypeEnum.fromCode(examPaper.getPaperType())) {
            examPaper.setLimitStartTime(DateTimeUtil.parse(dateTimes.get(0), DateTimeUtil.STANDER_FORMAT));
            examPaper.setLimitEndTime(DateTimeUtil.parse(dateTimes.get(1), DateTimeUtil.STANDER_FORMAT));
        }
    }

    private List<ExamPaperTitleItemObject> frameTextContentFromVM(List<ExamPaperTitleItemVM> titleItems) {
        AtomicInteger index = new AtomicInteger(1);
        return titleItems.stream().map(t -> {
            ExamPaperTitleItemObject titleItem = BeanUtil.copyProperties(t, ExamPaperTitleItemObject.class);
            List<ExamPaperQuestionItemObject> questionItems = t.getQuestionItems().stream()
                    .map(q -> {
                        ExamPaperQuestionItemObject examPaperQuestionItemObject = BeanUtil.copyProperties(q, ExamPaperQuestionItemObject.class);
                        examPaperQuestionItemObject.setItemOrder(index.getAndIncrement());
                        return examPaperQuestionItemObject;
                    })
                    .collect(Collectors.toList());
            titleItem.setQuestionItems(questionItems);
            return titleItem;
        }).collect(Collectors.toList());
    }
}
