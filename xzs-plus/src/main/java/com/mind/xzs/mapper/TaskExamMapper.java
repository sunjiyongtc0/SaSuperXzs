package com.mind.xzs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mind.xzs.domain.TaskExamEntity;
import com.mind.xzs.domain.viewmodel.admin.task.TaskPageRequestVM;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TaskExamMapper extends BaseMapper<TaskExamEntity> {

    List<TaskExamEntity> page(TaskPageRequestVM requestVM);

    List<TaskExamEntity> getByGradeLevel(Integer gradeLevel);
}
