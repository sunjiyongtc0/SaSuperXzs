package com.mind.xzs.controller.admin;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageInfo;

import com.mind.xzs.controller.BaseApiController;
import com.mind.xzs.core.response.RestResponse;
import com.mind.xzs.domain.ExamPaperAnswerEntity;
import com.mind.xzs.domain.SubjectEntity;
import com.mind.xzs.domain.UserEntity;
import com.mind.xzs.domain.viewmodel.admin.paper.ExamPaperAnswerPageRequestVM;
import com.mind.xzs.domain.viewmodel.student.exampaper.ExamPaperAnswerPageResponseVM;
import com.mind.xzs.service.ExamPaperAnswerService;
import com.mind.xzs.service.SubjectService;
import com.mind.xzs.service.UserService;
import com.mind.xzs.utils.DateTimeUtil;
import com.mind.xzs.utils.ExamUtil;
import com.mind.xzs.utils.PageInfoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController("AdminExamPaperAnswerController")
@RequestMapping(value = "/api/admin/examPaperAnswer")
public class AdminExamPaperAnswerController extends BaseApiController {
    @Autowired
    private ExamPaperAnswerService examPaperAnswerService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public RestResponse<PageInfo<ExamPaperAnswerPageResponseVM>> pageJudgeList(@RequestBody ExamPaperAnswerPageRequestVM model) {
        PageInfo<ExamPaperAnswerEntity> pageInfo = examPaperAnswerService.adminPage(model);
        PageInfo<ExamPaperAnswerPageResponseVM> page = PageInfoHelper.copyMap(pageInfo, e -> {
            ExamPaperAnswerPageResponseVM vm =  BeanUtil.copyProperties(e, ExamPaperAnswerPageResponseVM.class);
            SubjectEntity subject = subjectService.getById(vm.getSubjectId());
            vm.setDoTime(ExamUtil.secondToVM(e.getDoTime()));
            vm.setSystemScore(ExamUtil.scoreToVM(e.getSystemScore()));
            vm.setUserScore(ExamUtil.scoreToVM(e.getUserScore()));
            vm.setPaperScore(ExamUtil.scoreToVM(e.getPaperScore()));
            vm.setSubjectName(subject.getName());
            vm.setCreateTime(DateTimeUtil.dateFormat(e.getCreateTime()));
            UserEntity user = userService.getById(e.getCreateUser());
            vm.setUserName(user.getUserName());
            return vm;
        });
        return RestResponse.ok(page);
    }


}
