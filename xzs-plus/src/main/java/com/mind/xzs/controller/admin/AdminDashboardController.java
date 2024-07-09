package com.mind.xzs.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mind.xzs.controller.BaseApiController;
import com.mind.xzs.core.response.RestResponse;
import com.mind.xzs.domain.ExamPaperAnswerEntity;
import com.mind.xzs.domain.ExamPaperEntity;
import com.mind.xzs.domain.QuestionEntity;
import com.mind.xzs.domain.viewmodel.admin.dashboard.IndexVM;
import com.mind.xzs.service.*;
import com.mind.xzs.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("AdminDashboardController")
@RequestMapping(value = "/api/admin/dashboard")
public class AdminDashboardController extends BaseApiController {


    @Autowired
    private ExamPaperService examPaperService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private ExamPaperAnswerService examPaperAnswerService;
    @Autowired
    private ExamPaperQuestionCustomerAnswerService examPaperQuestionCustomerAnswerService;
    @Autowired
    private  UserEventLogService userEventLogService;


    @RequestMapping(value = "/index", method = RequestMethod.POST)
    public RestResponse<IndexVM> Index() {
        IndexVM vm = new IndexVM();

        QueryWrapper<ExamPaperEntity> qwep=new QueryWrapper<>();
        qwep.eq("deleted",0);
        Integer examPaperCount = examPaperService.count(qwep);

        QueryWrapper<QuestionEntity> qwq=new QueryWrapper<>();
        qwep.eq("deleted",0);
        Integer questionCount = questionService.count(qwq);

        Integer doExamPaperCount = examPaperAnswerService.count();

        Integer doQuestionCount = examPaperQuestionCustomerAnswerService.count();

        vm.setExamPaperCount(examPaperCount);
        vm.setQuestionCount(questionCount);
        vm.setDoExamPaperCount(doExamPaperCount);
        vm.setDoQuestionCount(doQuestionCount);

        List<Long> mothDayUserActionValue = userEventLogService.selectMothCount();
        List<Long> mothDayDoExamQuestionValue = examPaperQuestionCustomerAnswerService.selectMothCount();
        vm.setMothDayUserActionValue(mothDayUserActionValue);
        vm.setMothDayDoExamQuestionValue(mothDayDoExamQuestionValue);

        vm.setMothDayText(DateTimeUtil.MothDay());
        return RestResponse.ok(vm);
    }
}
