package com.mind.xzs.controller.admin;


import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageInfo;
import com.mind.xzs.controller.BaseApiController;
import com.mind.xzs.core.response.RestResponse;
import com.mind.xzs.domain.TaskExamEntity;
import com.mind.xzs.domain.viewmodel.admin.task.TaskPageRequestVM;
import com.mind.xzs.domain.viewmodel.admin.task.TaskPageResponseVM;
import com.mind.xzs.domain.viewmodel.admin.task.TaskRequestVM;
import com.mind.xzs.service.TaskExamService;
import com.mind.xzs.utils.DateTimeUtil;
import com.mind.xzs.utils.PageInfoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController("AdminTaskController")
@RequestMapping(value = "/api/admin/task")
public class AdminTaskController extends BaseApiController {

    @Autowired
    private TaskExamService taskExamService;


    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public RestResponse<PageInfo<TaskPageResponseVM>> pageList(@RequestBody TaskPageRequestVM model) {
        PageInfo<TaskExamEntity> pageInfo = taskExamService.page(model);
        PageInfo<TaskPageResponseVM> page = PageInfoHelper.copyMap(pageInfo, m -> {
            TaskPageResponseVM vm = BeanUtil.copyProperties(m, TaskPageResponseVM.class);
            vm.setCreateTime(DateTimeUtil.dateFormat(m.getCreateTime()));
            return vm;
        });
        return RestResponse.ok(page);
    }


    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public RestResponse edit(@RequestBody  TaskRequestVM model) {
        taskExamService.edit(model, getCurrentUser());
        TaskRequestVM vm = taskExamService.taskExamToVM(model.getId());
        return RestResponse.ok(vm);
    }


    @RequestMapping(value = "/select/{id}", method = RequestMethod.POST)
    public RestResponse<TaskRequestVM> select(@PathVariable Long id) {
        TaskRequestVM vm = taskExamService.taskExamToVM(id);
        return RestResponse.ok(vm);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public RestResponse delete(@PathVariable Long id) {
        TaskExamEntity taskExam = taskExamService.getById(id);
        taskExam.setDeleted(true);
        taskExamService.updateById(taskExam);
        return RestResponse.ok();
    }
}
