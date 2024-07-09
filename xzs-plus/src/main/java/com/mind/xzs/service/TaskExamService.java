package com.mind.xzs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.mind.xzs.domain.TaskExamEntity;
import com.mind.xzs.domain.UserEntity;
import com.mind.xzs.domain.viewmodel.admin.task.TaskPageRequestVM;
import com.mind.xzs.domain.viewmodel.admin.task.TaskRequestVM;

import java.util.List;

public interface TaskExamService extends IService<TaskExamEntity> {

    PageInfo<TaskExamEntity> page(TaskPageRequestVM requestVM);

    void edit(TaskRequestVM model, UserEntity user);

    TaskRequestVM taskExamToVM(Long id);

    List<TaskExamEntity> getByGradeLevel(Integer gradeLevel);
}
