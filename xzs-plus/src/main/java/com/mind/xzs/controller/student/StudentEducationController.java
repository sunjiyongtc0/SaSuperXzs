package com.mind.xzs.controller.student;



import cn.hutool.core.bean.BeanUtil;
import com.mind.xzs.controller.BaseApiController;
import com.mind.xzs.core.response.RestResponse;
import com.mind.xzs.domain.SubjectEntity;
import com.mind.xzs.domain.UserEntity;
import com.mind.xzs.domain.viewmodel.admin.education.SubjectEditRequestVM;
import com.mind.xzs.domain.viewmodel.student.education.SubjectVM;
import com.mind.xzs.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController("StudentEducationController")
@RequestMapping(value = "/api/student/education")
public class StudentEducationController extends BaseApiController {

    @Autowired
    private SubjectService subjectService;


    @RequestMapping(value = "/subject/list", method = RequestMethod.POST)
    public RestResponse<List<SubjectVM>> list() {
        UserEntity user = getCurrentUser();
        List<SubjectEntity> subjects = subjectService.getSubjectByLevel(user.getUserLevel());
        List<SubjectVM> subjectVMS = subjects.stream().map(d -> {
            SubjectVM subjectVM = BeanUtil.copyProperties(d, SubjectVM.class);
            subjectVM.setId(String.valueOf(d.getId()));
            return subjectVM;
        }).collect(Collectors.toList());
        return RestResponse.ok(subjectVMS);
    }

    @RequestMapping(value = "/subject/select/{id}", method = RequestMethod.POST)
    public RestResponse<SubjectEditRequestVM> select(@PathVariable Integer id) {
        SubjectEntity subject = subjectService.getById(id);
        SubjectEditRequestVM vm = BeanUtil.copyProperties(subject, SubjectEditRequestVM.class);
        return RestResponse.ok(vm);
    }

}
