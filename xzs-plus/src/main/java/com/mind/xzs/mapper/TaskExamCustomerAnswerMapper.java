package com.mind.xzs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mind.xzs.domain.TaskExamCustomerAnswerEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TaskExamCustomerAnswerMapper extends BaseMapper<TaskExamCustomerAnswerEntity> {

    TaskExamCustomerAnswerEntity getByTUid(@Param("tid") Long tid, @Param("uid") Long uid);

    List<TaskExamCustomerAnswerEntity> selectByTUid(@Param("taskIds") List<Long> taskIds, @Param("uid") Long uid);
}
