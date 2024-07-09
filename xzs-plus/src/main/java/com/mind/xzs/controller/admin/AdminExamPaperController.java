package com.mind.xzs.controller.admin;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageInfo;

import com.mind.xzs.controller.BaseApiController;
import com.mind.xzs.core.response.RestResponse;
import com.mind.xzs.domain.ExamPaperEntity;
import com.mind.xzs.domain.viewmodel.admin.exam.ExamPaperEditRequestVM;
import com.mind.xzs.domain.viewmodel.admin.exam.ExamPaperPageRequestVM;
import com.mind.xzs.domain.viewmodel.admin.exam.ExamResponseVM;
import com.mind.xzs.service.ExamPaperService;
import com.mind.xzs.utils.DateTimeUtil;
import com.mind.xzs.utils.PageInfoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController("AdminExamPaperController")
@RequestMapping(value = "/api/admin/exam/paper")
public class AdminExamPaperController extends BaseApiController {

    @Autowired
    private ExamPaperService examPaperService;


    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public RestResponse<PageInfo<ExamResponseVM>> pageList(@RequestBody ExamPaperPageRequestVM model) {
        PageInfo<ExamPaperEntity> pageInfo = examPaperService.page(model);
//        PageInfo<ExamResponseVM> page = PageInfoHelper.copyMap(pageInfo, e -> {
//            ExamResponseVM vm = modelMapper.map(e, ExamResponseVM.class);
//            e.setCreateTime(DateTimeUtil.dateFormat(e.getCreateTime()));
//            return vm;
//        });

        PageInfo<ExamResponseVM> page = PageInfoHelper.copyMap(pageInfo, e -> {
            ExamResponseVM vm = BeanUtil.copyProperties(e, ExamResponseVM.class);
            vm.setCreateTime(DateTimeUtil.dateFormat(e.getCreateTime()));
            return vm;
        });
        return RestResponse.ok(page);
    }



    @RequestMapping(value = "/taskExamPage", method = RequestMethod.POST)
    public RestResponse<PageInfo<ExamResponseVM>> taskExamPageList(@RequestBody ExamPaperPageRequestVM model) {
        PageInfo<ExamPaperEntity> pageInfo = examPaperService.taskExamPage(model);
        PageInfo<ExamResponseVM> page = PageInfoHelper.copyMap(pageInfo, e -> {
            ExamResponseVM vm = BeanUtil.copyProperties(e, ExamResponseVM.class);
            vm.setCreateTime(DateTimeUtil.dateFormat(e.getCreateTime()));
            return vm;
        });
        return RestResponse.ok(page);
    }


    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public RestResponse<ExamPaperEditRequestVM> edit(@RequestBody ExamPaperEditRequestVM model) {
        ExamPaperEntity examPaper = examPaperService.savePaperFromVM(model, getCurrentUser());
        ExamPaperEditRequestVM newVM = examPaperService.examPaperToVM(examPaper.getId());
        return RestResponse.ok(newVM);
    }

    @RequestMapping(value = "/select/{id}", method = RequestMethod.POST)
    public RestResponse<ExamPaperEditRequestVM> select(@PathVariable Long id) {
        ExamPaperEditRequestVM vm = examPaperService.examPaperToVM(id);
        return RestResponse.ok(vm);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public RestResponse delete(@PathVariable Long id) {
        ExamPaperEntity examPaper = examPaperService.getById(id);
        examPaper.setDeleted(true);
        examPaperService.updateById(examPaper);
        return RestResponse.ok();
    }
}
