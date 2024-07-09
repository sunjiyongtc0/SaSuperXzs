package com.mind.xzs.controller.student;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageInfo;

import com.mind.xzs.controller.BaseApiController;
import com.mind.xzs.core.response.RestResponse;
import com.mind.xzs.domain.ExamPaperEntity;
import com.mind.xzs.domain.viewmodel.admin.exam.ExamPaperEditRequestVM;
import com.mind.xzs.domain.viewmodel.student.exam.ExamPaperPageResponseVM;
import com.mind.xzs.domain.viewmodel.student.exam.ExamPaperPageVM;
import com.mind.xzs.service.ExamPaperAnswerService;
import com.mind.xzs.service.ExamPaperService;
import com.mind.xzs.utils.DateTimeUtil;
import com.mind.xzs.utils.PageInfoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;


@RestController("StudentExamPaperController")
@RequestMapping(value = "/api/student/exam/paper")
public class StudentExamPaperController extends BaseApiController {

    @Autowired
    private  ExamPaperService examPaperService;
    @Autowired
    private  ExamPaperAnswerService examPaperAnswerService;
//    private final ApplicationEventPublisher eventPublisher;



    @RequestMapping(value = "/select/{id}", method = RequestMethod.POST)
    public RestResponse<ExamPaperEditRequestVM> select(@PathVariable Long id) {
        ExamPaperEditRequestVM vm = examPaperService.examPaperToVM(id);
        return RestResponse.ok(vm);
    }


    @RequestMapping(value = "/pageList", method = RequestMethod.POST)
    public RestResponse<PageInfo<ExamPaperPageResponseVM>> pageList(@RequestBody  ExamPaperPageVM model) {
        PageInfo<ExamPaperEntity> pageInfo = examPaperService.studentPage(model);
        PageInfo<ExamPaperPageResponseVM> page = PageInfoHelper.copyMap(pageInfo, e -> {
            ExamPaperPageResponseVM vm = BeanUtil.copyProperties(e, ExamPaperPageResponseVM.class);
            vm.setCreateTime(DateTimeUtil.dateFormat(e.getCreateTime()));
            return vm;
        });
        return RestResponse.ok(page);
    }
}
