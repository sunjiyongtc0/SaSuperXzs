package com.mind.xzs.controller.wx.student;
//
//import com.github.pagehelper.PageInfo;
//import com.mindskip.xzs.base.RestResponse;
//import com.mindskip.xzs.controller.wx.BaseWXApiController;
//import com.mindskip.xzs.domain.ExamPaper;
//import com.mindskip.xzs.domain.Subject;
//import com.mindskip.xzs.service.ExamPaperService;
//import com.mindskip.xzs.service.SubjectService;
//import com.mindskip.xzs.utility.DateTimeUtil;
//import com.mindskip.xzs.utility.PageInfoHelper;
//import com.mindskip.xzs.viewmodel.admin.exam.ExamPaperEditRequestVM;
//import com.mindskip.xzs.viewmodel.student.exam.ExamPaperPageResponseVM;
//import com.mindskip.xzs.viewmodel.student.exam.ExamPaperPageVM;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.validation.Valid;


import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageInfo;
import com.mind.xzs.controller.wx.BaseWXApiController;
import com.mind.xzs.core.response.RestResponse;
import com.mind.xzs.domain.ExamPaperEntity;
import com.mind.xzs.domain.SubjectEntity;
import com.mind.xzs.domain.viewmodel.admin.exam.ExamPaperEditRequestVM;
import com.mind.xzs.domain.viewmodel.student.exam.ExamPaperPageResponseVM;
import com.mind.xzs.domain.viewmodel.student.exam.ExamPaperPageVM;
import com.mind.xzs.service.ExamPaperService;
import com.mind.xzs.service.SubjectService;
import com.mind.xzs.utils.DateTimeUtil;
import com.mind.xzs.utils.PageInfoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("WXStudentExamController")
@RequestMapping(value = "/api/wx/student/exampaper")
@ResponseBody
public class WXStudentExamController extends BaseWXApiController {

    @Autowired
    private ExamPaperService examPaperService;
    @Autowired
    private SubjectService subjectService;



    @RequestMapping(value = "/select/{id}", method = RequestMethod.POST)
    public RestResponse<ExamPaperEditRequestVM> select(@PathVariable Long id) {
        ExamPaperEditRequestVM vm = examPaperService.examPaperToVM(id);
        return RestResponse.ok(vm);
    }


    @RequestMapping(value = "/pageList", method = RequestMethod.POST)
    public RestResponse<PageInfo<ExamPaperPageResponseVM>> pageList(ExamPaperPageVM model) {
        model.setLevelId(getCurrentUser().getUserLevel());
        PageInfo<ExamPaperEntity> pageInfo = examPaperService.studentPage(model);
        PageInfo<ExamPaperPageResponseVM> page = PageInfoHelper.copyMap(pageInfo, e -> {
            ExamPaperPageResponseVM vm = BeanUtil.copyProperties(e, ExamPaperPageResponseVM.class);
            SubjectEntity subject = subjectService.getById(vm.getSubjectId());
            vm.setSubjectName(subject.getName());
            vm.setCreateTime(DateTimeUtil.dateFormat(e.getCreateTime()));
            return vm;
        });
        return RestResponse.ok(page);
    }
}
