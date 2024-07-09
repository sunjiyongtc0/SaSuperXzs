package com.mind.xzs.controller.student;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageInfo;
import com.mind.xzs.controller.BaseApiController;
import com.mind.xzs.core.response.RestResponse;
import com.mind.xzs.domain.ExamPaperAnswerEntity;
import com.mind.xzs.domain.SubjectEntity;
import com.mind.xzs.domain.UserEntity;
import com.mind.xzs.domain.UserEventLogEntity;
import com.mind.xzs.domain.exam.ExamPaperAnswerInfo;
import com.mind.xzs.domain.viewmodel.admin.exam.ExamPaperEditRequestVM;
import com.mind.xzs.domain.viewmodel.student.exam.ExamPaperReadVM;
import com.mind.xzs.domain.viewmodel.student.exam.ExamPaperSubmitVM;
import com.mind.xzs.domain.viewmodel.student.exampaper.ExamPaperAnswerPageResponseVM;
import com.mind.xzs.domain.viewmodel.student.exampaper.ExamPaperAnswerPageVM;
import com.mind.xzs.event.CalculateExamPaperAnswerCompleteEvent;
import com.mind.xzs.event.UserEvent;
import com.mind.xzs.service.ExamPaperAnswerService;
import com.mind.xzs.service.ExamPaperService;
import com.mind.xzs.service.SubjectService;
import com.mind.xzs.utils.DateTimeUtil;
import com.mind.xzs.utils.ExamUtil;
import com.mind.xzs.utils.PageInfoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController("StudentExamPaperAnswerController")
@RequestMapping(value = "/api/student/exampaper/answer")
public class StudentExamPaperAnswerController extends BaseApiController {

    @Autowired
    private ExamPaperAnswerService examPaperAnswerService;
    @Autowired
    private ExamPaperService examPaperService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private  ApplicationEventPublisher eventPublisher;


    @RequestMapping(value = "/pageList", method = RequestMethod.POST)
    public RestResponse<PageInfo<ExamPaperAnswerPageResponseVM>> pageList(@RequestBody ExamPaperAnswerPageVM model) {
        model.setCreateUser(getCurrentUser().getId());
        PageInfo<ExamPaperAnswerEntity> pageInfo = examPaperAnswerService.studentPage(model);
        PageInfo<ExamPaperAnswerPageResponseVM> page = PageInfoHelper.copyMap(pageInfo, e -> {
            ExamPaperAnswerPageResponseVM vm = BeanUtil.copyProperties(e, ExamPaperAnswerPageResponseVM.class);
            SubjectEntity subject = subjectService.getById(vm.getSubjectId());
            vm.setDoTime(ExamUtil.secondToVM(e.getDoTime()));
            vm.setSystemScore(ExamUtil.scoreToVM(e.getSystemScore()));
            vm.setUserScore(ExamUtil.scoreToVM(e.getUserScore()));
            vm.setPaperScore(ExamUtil.scoreToVM(e.getPaperScore()));
            vm.setSubjectName(subject.getName());
            vm.setCreateTime(DateTimeUtil.dateFormat(e.getCreateTime()));
            return vm;
        });
        return RestResponse.ok(page);
    }


    @RequestMapping(value = "/answerSubmit", method = RequestMethod.POST)
    public RestResponse answerSubmit(@RequestBody ExamPaperSubmitVM examPaperSubmitVM) {
        UserEntity user = getCurrentUser();
        ExamPaperAnswerInfo examPaperAnswerInfo = examPaperAnswerService.calculateExamPaperAnswer(examPaperSubmitVM, user);
        if (null == examPaperAnswerInfo) {
            return RestResponse.fail(2, "试卷不能重复做");
        }
        ExamPaperAnswerEntity examPaperAnswer = examPaperAnswerInfo.getExamPaperAnswer();
        Integer userScore = examPaperAnswer.getUserScore();
        String scoreVm = ExamUtil.scoreToVM(userScore);
        UserEventLogEntity userEventLog = new UserEventLogEntity(user.getId(), user.getUserName(), user.getRealName(), new Date());
        String content = user.getUserName() + " 提交试卷：" + examPaperAnswerInfo.getExamPaper().getName()
                + " 得分：" + scoreVm
                + " 耗时：" + ExamUtil.secondToVM(examPaperAnswer.getDoTime());
        userEventLog.setContent(content);
        eventPublisher.publishEvent(new CalculateExamPaperAnswerCompleteEvent(examPaperAnswerInfo));
        eventPublisher.publishEvent(new UserEvent(userEventLog));
        return RestResponse.ok(scoreVm);
    }


//    @RequestMapping(value = "/edit", method = RequestMethod.POST)
//    public RestResponse edit(@RequestBody @Valid ExamPaperSubmitVM examPaperSubmitVM) {
//        boolean notJudge = examPaperSubmitVM.getAnswerItems().stream().anyMatch(i -> i.getDoRight() == null && i.getScore() == null);
//        if (notJudge) {
//            return RestResponse.fail(2, "有未批改题目");
//        }
//
//        ExamPaperAnswer examPaperAnswer = examPaperAnswerService.selectById(examPaperSubmitVM.getId());
//        ExamPaperAnswerStatusEnum examPaperAnswerStatusEnum = ExamPaperAnswerStatusEnum.fromCode(examPaperAnswer.getStatus());
//        if (examPaperAnswerStatusEnum == ExamPaperAnswerStatusEnum.Complete) {
//            return RestResponse.fail(3, "试卷已完成");
//        }
//        String score = examPaperAnswerService.judge(examPaperSubmitVM);
//        User user = getCurrentUser();
//        UserEventLog userEventLog = new UserEventLog(user.getId(), user.getUserName(), user.getRealName(), new Date());
//        String content = user.getUserName() + " 批改试卷：" + examPaperAnswer.getPaperName() + " 得分：" + score;
//        userEventLog.setContent(content);
//        eventPublisher.publishEvent(new UserEvent(userEventLog));
//        return RestResponse.ok(score);
//    }

    @RequestMapping(value = "/read/{id}", method = RequestMethod.POST)
    public RestResponse<ExamPaperReadVM> read(@PathVariable Long id) {
        ExamPaperAnswerEntity examPaperAnswer = examPaperAnswerService.getById(id);
        ExamPaperReadVM vm = new ExamPaperReadVM();
        ExamPaperEditRequestVM paper = examPaperService.examPaperToVM(examPaperAnswer.getExamPaperId());
        ExamPaperSubmitVM answer = examPaperAnswerService.examPaperAnswerToVM(examPaperAnswer.getId());
        vm.setPaper(paper);
        vm.setAnswer(answer);
        return RestResponse.ok(vm);
    }


}
