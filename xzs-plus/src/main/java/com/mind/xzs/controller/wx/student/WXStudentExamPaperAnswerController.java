package com.mind.xzs.controller.wx.student;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageInfo;
import com.mind.xzs.controller.wx.BaseWXApiController;
import com.mind.xzs.core.response.RestResponse;
import com.mind.xzs.domain.ExamPaperAnswerEntity;
import com.mind.xzs.domain.SubjectEntity;
import com.mind.xzs.domain.UserEntity;
import com.mind.xzs.domain.UserEventLogEntity;
import com.mind.xzs.domain.enums.QuestionTypeEnum;
import com.mind.xzs.domain.exam.ExamPaperAnswerInfo;
import com.mind.xzs.domain.viewmodel.admin.exam.ExamPaperEditRequestVM;
import com.mind.xzs.domain.viewmodel.student.exam.ExamPaperReadVM;
import com.mind.xzs.domain.viewmodel.student.exam.ExamPaperSubmitItemVM;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Controller("WXStudentExamPaperAnswerController")
@RequestMapping(value = "/api/wx/student/exampaper/answer")
@ResponseBody
public class WXStudentExamPaperAnswerController extends BaseWXApiController {

    @Autowired
    private ExamPaperAnswerService examPaperAnswerService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private ExamPaperService examPaperService;


    @RequestMapping(value = "/pageList", method = RequestMethod.POST)
    public RestResponse<PageInfo<ExamPaperAnswerPageResponseVM>> pageList(ExamPaperAnswerPageVM model) {
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
    public RestResponse answerSubmit(HttpServletRequest request) {
        ExamPaperSubmitVM examPaperSubmitVM = requestToExamPaperSubmitVM(request);
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

    private ExamPaperSubmitVM requestToExamPaperSubmitVM(HttpServletRequest request) {
        ExamPaperSubmitVM examPaperSubmitVM = new ExamPaperSubmitVM();
        examPaperSubmitVM.setId(Long.parseLong(request.getParameter("id")));
        examPaperSubmitVM.setDoTime(Integer.parseInt(request.getParameter("doTime")));
        List<String> parameterNames = Collections.list(request.getParameterNames()).stream()
                .filter(n -> n.contains("_"))
                .collect(Collectors.toList());
        //题目答案按序号分组
        Map<String, List<String>> questionGroup = parameterNames.stream().collect(Collectors.groupingBy(p -> p.substring(0, p.indexOf("_"))));
        List<ExamPaperSubmitItemVM> answerItems = new ArrayList<>();
        questionGroup.forEach((k, v) -> {
            ExamPaperSubmitItemVM examPaperSubmitItemVM = new ExamPaperSubmitItemVM();
            String p = v.get(0);
            String[] keys = p.split("_");
            examPaperSubmitItemVM.setQuestionId(Long.parseLong(keys[1]));
            examPaperSubmitItemVM.setItemOrder(Integer.parseInt(keys[0]));
            QuestionTypeEnum typeEnum = QuestionTypeEnum.fromCode(Integer.parseInt(keys[2]));
            if (v.size() == 1) {
                String content = request.getParameter(p);
                examPaperSubmitItemVM.setContent(content);
                if (typeEnum == QuestionTypeEnum.MultipleChoice) {
                    examPaperSubmitItemVM.setContentArray(Arrays.asList(content.split(",")));
                }
            } else {  //多个空 填空题
                List<String> answers = v.stream().sorted(Comparator.comparingInt(ExamUtil::lastNum)).map(inputKey -> request.getParameter(inputKey)).collect(Collectors.toList());
                examPaperSubmitItemVM.setContentArray(answers);
            }
            answerItems.add(examPaperSubmitItemVM);
        });
        examPaperSubmitVM.setAnswerItems(answerItems);
        return examPaperSubmitVM;
    }


    @PostMapping(value = "/read/{id}")
    public RestResponse<ExamPaperReadVM> read(@PathVariable Long id) {
        ExamPaperReadVM vm = new ExamPaperReadVM();
        ExamPaperAnswerEntity examPaperAnswer = examPaperAnswerService.getById(id);
        ExamPaperEditRequestVM paper = examPaperService.examPaperToVM(examPaperAnswer.getExamPaperId());
        ExamPaperSubmitVM answer = examPaperAnswerService.examPaperAnswerToVM(examPaperAnswer.getId());
        vm.setPaper(paper);
        vm.setAnswer(answer);
        return RestResponse.ok(vm);
    }
}
