package com.mind.xzs.controller.admin;


import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageInfo;
import com.mind.xzs.controller.BaseApiController;
import com.mind.xzs.core.response.RestResponse;
import com.mind.xzs.domain.SubjectEntity;
import com.mind.xzs.domain.viewmodel.admin.education.SubjectEditRequestVM;
import com.mind.xzs.domain.viewmodel.admin.education.SubjectPageRequestVM;
import com.mind.xzs.domain.viewmodel.admin.education.SubjectResponseVM;
import com.mind.xzs.service.SubjectService;
import com.mind.xzs.utils.PageInfoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController("AdminEducationController")
@RequestMapping(value = "/api/admin/education")
public class AdminEducationController extends BaseApiController {

    @Autowired
    private SubjectService subjectService;

    @RequestMapping(value = "/subject/list", method = RequestMethod.POST)
    public RestResponse<List<SubjectEntity>> list() {
        List<SubjectEntity> subjects = subjectService.allSubject();
        return RestResponse.ok(subjects);
    }

    @RequestMapping(value = "/subject/page", method = RequestMethod.POST)
    public RestResponse<PageInfo<SubjectResponseVM>> pageList(@RequestBody SubjectPageRequestVM model) {
        PageInfo<SubjectEntity> pageInfo = subjectService.page(model);
        PageInfo<SubjectResponseVM> page = PageInfoHelper.copyMap(pageInfo,
                e -> BeanUtil.copyProperties(e, SubjectResponseVM.class));
        return RestResponse.ok(page);
    }

    @RequestMapping(value = "/subject/edit", method = RequestMethod.POST)
    public RestResponse edit(@RequestBody SubjectEditRequestVM model) {
        SubjectEntity subject = BeanUtil.copyProperties(model, SubjectEntity.class);
        if (model.getId() == null) {
            subject.setDeleted(false);
            subject.setId(generateId());
            subjectService.save(subject);
        } else {
            subjectService.updateById(subject);
        }
        return RestResponse.ok();
    }

    @RequestMapping(value = "/subject/select/{id}", method = RequestMethod.POST)
    public RestResponse<SubjectEditRequestVM> select(@PathVariable Long id) {
        SubjectEntity subject = subjectService.getById(id);
        SubjectEditRequestVM vm = BeanUtil.copyProperties(subject,  SubjectEditRequestVM.class);
        return RestResponse.ok(vm);
    }

    @RequestMapping(value = "/subject/delete/{id}", method = RequestMethod.POST)
    public RestResponse delete(@PathVariable Long id) {
        SubjectEntity subject = subjectService.getById(id);
        subject.setDeleted(true);
        subjectService.updateById(subject);
        return RestResponse.ok();
    }
}
