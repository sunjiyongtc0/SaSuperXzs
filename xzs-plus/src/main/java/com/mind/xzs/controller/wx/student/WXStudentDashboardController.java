package com.mind.xzs.controller.wx.student;
//
//import com.mindskip.xzs.base.RestResponse;
//import com.mindskip.xzs.controller.wx.BaseWXApiController;
//import com.mindskip.xzs.domain.TaskExam;
//import com.mindskip.xzs.domain.TaskExamCustomerAnswer;
//import com.mindskip.xzs.domain.TextContent;
//import com.mindskip.xzs.domain.User;
//import com.mindskip.xzs.domain.enums.ExamPaperTypeEnum;
//import com.mindskip.xzs.domain.task.TaskItemAnswerObject;
//import com.mindskip.xzs.domain.task.TaskItemObject;
//import com.mindskip.xzs.service.ExamPaperService;
//import com.mindskip.xzs.service.TaskExamCustomerAnswerService;
//import com.mindskip.xzs.service.TaskExamService;
//import com.mindskip.xzs.service.TextContentService;
//import com.mindskip.xzs.utility.DateTimeUtil;
//import com.mindskip.xzs.utility.JsonUtil;
//import com.mindskip.xzs.viewmodel.student.dashboard.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.stream.Collectors;


import cn.hutool.core.bean.BeanUtil;
import com.mind.xzs.controller.wx.BaseWXApiController;
import com.mind.xzs.core.response.RestResponse;
import com.mind.xzs.domain.TaskExamCustomerAnswerEntity;
import com.mind.xzs.domain.TaskExamEntity;
import com.mind.xzs.domain.TextContentEntity;
import com.mind.xzs.domain.UserEntity;
import com.mind.xzs.domain.enums.ExamPaperTypeEnum;
import com.mind.xzs.domain.task.TaskItemAnswerObject;
import com.mind.xzs.domain.task.TaskItemObject;
import com.mind.xzs.domain.viewmodel.student.dashboard.*;
import com.mind.xzs.service.ExamPaperService;
import com.mind.xzs.service.TaskExamCustomerAnswerService;
import com.mind.xzs.service.TaskExamService;
import com.mind.xzs.service.TextContentService;
import com.mind.xzs.utils.DateTimeUtil;
import com.mind.xzs.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller("WXStudentDashboardController")
@RequestMapping(value = "/api/wx/student/dashboard")
@ResponseBody
public class WXStudentDashboardController extends BaseWXApiController {
    @Autowired
    private ExamPaperService examPaperService;
    @Autowired
    private TextContentService textContentService;
    @Autowired
    private TaskExamService taskExamService;
    @Autowired
    private TaskExamCustomerAnswerService taskExamCustomerAnswerService;


    @RequestMapping(value = "/index", method = RequestMethod.POST)
    public RestResponse<IndexVM> index() {
        IndexVM indexVM = new IndexVM();
        UserEntity user = getCurrentUser();

        PaperFilter fixedPaperFilter = new PaperFilter();
        fixedPaperFilter.setGradeLevel(user.getUserLevel());
        fixedPaperFilter.setExamPaperType(ExamPaperTypeEnum.Fixed.getCode());
        indexVM.setFixedPaper(examPaperService.indexPaper(fixedPaperFilter));

        PaperFilter timeLimitPaperFilter = new PaperFilter();
        timeLimitPaperFilter.setDateTime(new Date());
        timeLimitPaperFilter.setGradeLevel(user.getUserLevel());
        timeLimitPaperFilter.setExamPaperType(ExamPaperTypeEnum.TimeLimit.getCode());

        List<PaperInfo> limitPaper = examPaperService.indexPaper(timeLimitPaperFilter);
        List<PaperInfoVM> paperInfoVMS = limitPaper.stream().map(d -> {
            PaperInfoVM vm = BeanUtil.copyProperties(d, PaperInfoVM.class);
            vm.setStartTime(DateTimeUtil.dateFormat(d.getLimitStartTime()));
            vm.setEndTime(DateTimeUtil.dateFormat(d.getLimitEndTime()));
            return vm;
        }).collect(Collectors.toList());
        indexVM.setTimeLimitPaper(paperInfoVMS);
        return RestResponse.ok(indexVM);
    }

    @RequestMapping(value = "/task", method = RequestMethod.POST)
    public RestResponse<List<TaskItemVm>> task() {
        UserEntity user = getCurrentUser();
        List<TaskExamEntity> taskExams = taskExamService.getByGradeLevel(user.getUserLevel());
        if (taskExams.size() == 0) {
            return RestResponse.ok(new ArrayList<>());
        }
        List<Long> tIds = taskExams.stream().map(taskExam -> taskExam.getId()).collect(Collectors.toList());
        List<TaskExamCustomerAnswerEntity> taskExamCustomerAnswers = taskExamCustomerAnswerService.selectByTUid(tIds, user.getId());
        List<TaskItemVm> vm = taskExams.stream().map(t -> {
            TaskItemVm itemVm = new TaskItemVm();
            itemVm.setId(t.getId());
            itemVm.setTitle(t.getTitle());
            TaskExamCustomerAnswerEntity taskExamCustomerAnswer = taskExamCustomerAnswers.stream()
                    .filter(tc -> tc.getTaskExamId().equals(t.getId())).findFirst().orElse(null);
            List<TaskItemPaperVm> paperItemVMS = getTaskItemPaperVm(t.getFrameTextContentId(), taskExamCustomerAnswer);
            itemVm.setPaperItems(paperItemVMS);
            return itemVm;
        }).collect(Collectors.toList());
        return RestResponse.ok(vm);
    }


    private List<TaskItemPaperVm> getTaskItemPaperVm(Long tFrameId, TaskExamCustomerAnswerEntity taskExamCustomerAnswers) {
        TextContentEntity textContent = textContentService.getById(tFrameId);
        List<TaskItemObject> paperItems = JsonUtil.toJsonListObject(textContent.getContent(), TaskItemObject.class);

        List<TaskItemAnswerObject> answerPaperItems = null;
        if (null != taskExamCustomerAnswers) {
            TextContentEntity answerTextContent = textContentService.getById(taskExamCustomerAnswers.getTextContentId());
            answerPaperItems = JsonUtil.toJsonListObject(answerTextContent.getContent(), TaskItemAnswerObject.class);
        }


        List<TaskItemAnswerObject> finalAnswerPaperItems = answerPaperItems;
        return paperItems.stream().map(p -> {
                    TaskItemPaperVm ivm = new TaskItemPaperVm();
                    ivm.setExamPaperId(p.getExamPaperId());
                    ivm.setExamPaperName(p.getExamPaperName());
                    if (null != finalAnswerPaperItems) {
                        finalAnswerPaperItems.stream()
                                .filter(a -> a.getExamPaperId().equals(p.getExamPaperId()))
                                .findFirst()
                                .ifPresent(a -> {
                                    ivm.setExamPaperAnswerId(a.getExamPaperAnswerId());
                                    ivm.setStatus(a.getStatus());
                                });
                    }
                    return ivm;
                }
        ).collect(Collectors.toList());
    }


}
