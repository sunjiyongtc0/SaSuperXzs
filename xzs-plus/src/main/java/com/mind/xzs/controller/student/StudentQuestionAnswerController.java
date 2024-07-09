package com.mind.xzs.controller.student;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageInfo;
import com.mind.xzs.controller.BaseApiController;
import com.mind.xzs.core.response.RestResponse;
import com.mind.xzs.domain.ExamPaperQuestionCustomerAnswerEntity;
import com.mind.xzs.domain.SubjectEntity;
import com.mind.xzs.domain.TextContentEntity;
import com.mind.xzs.domain.question.QuestionObject;
import com.mind.xzs.domain.viewmodel.admin.question.QuestionEditRequestVM;
import com.mind.xzs.domain.viewmodel.student.exam.ExamPaperSubmitItemVM;
import com.mind.xzs.domain.viewmodel.student.question.answer.QuestionAnswerVM;
import com.mind.xzs.domain.viewmodel.student.question.answer.QuestionPageStudentRequestVM;
import com.mind.xzs.domain.viewmodel.student.question.answer.QuestionPageStudentResponseVM;
import com.mind.xzs.service.ExamPaperQuestionCustomerAnswerService;
import com.mind.xzs.service.QuestionService;
import com.mind.xzs.service.SubjectService;
import com.mind.xzs.service.TextContentService;
import com.mind.xzs.utils.DateTimeUtil;
import com.mind.xzs.utils.HtmlUtil;
import com.mind.xzs.utils.JsonUtil;
import com.mind.xzs.utils.PageInfoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("StudentQuestionAnswerController")
@RequestMapping(value = "/api/student/question/answer")
public class StudentQuestionAnswerController extends BaseApiController {

    @Autowired
    private ExamPaperQuestionCustomerAnswerService examPaperQuestionCustomerAnswerService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private TextContentService textContentService;
    @Autowired
    private SubjectService subjectService;


    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public RestResponse<PageInfo<QuestionPageStudentResponseVM>> pageList(@RequestBody QuestionPageStudentRequestVM model) {
        model.setCreateUser(getCurrentUser().getId());
        PageInfo<ExamPaperQuestionCustomerAnswerEntity> pageInfo = examPaperQuestionCustomerAnswerService.studentPage(model);
        PageInfo<QuestionPageStudentResponseVM> page = PageInfoHelper.copyMap(pageInfo, q -> {
            SubjectEntity subject = subjectService.getById(q.getSubjectId());
            QuestionPageStudentResponseVM vm = BeanUtil.copyProperties(q, QuestionPageStudentResponseVM.class);
            vm.setCreateTime(DateTimeUtil.dateFormat(q.getCreateTime()));
            TextContentEntity textContent = textContentService.getById(q.getQuestionTextContentId());
            QuestionObject questionObject = JsonUtil.toJsonObject(textContent.getContent(), QuestionObject.class);
            String clearHtml = HtmlUtil.clear(questionObject.getTitleContent());
            vm.setShortTitle(clearHtml);
            vm.setSubjectName(subject.getName());
            return vm;
        });
        return RestResponse.ok(page);
    }


    @RequestMapping(value = "/select/{id}", method = RequestMethod.POST)
    public RestResponse<QuestionAnswerVM> select(@PathVariable Long id) {
        QuestionAnswerVM vm = new QuestionAnswerVM();
        ExamPaperQuestionCustomerAnswerEntity examPaperQuestionCustomerAnswer = examPaperQuestionCustomerAnswerService.getById(id);
        ExamPaperSubmitItemVM questionAnswerVM = examPaperQuestionCustomerAnswerService.examPaperQuestionCustomerAnswerToVM(examPaperQuestionCustomerAnswer);
        QuestionEditRequestVM questionVM = questionService.getQuestionEditRequestVM(examPaperQuestionCustomerAnswer.getQuestionId());
        vm.setQuestionVM(questionVM);
        vm.setQuestionAnswerVM(questionAnswerVM);
        return RestResponse.ok(vm);
    }

}
