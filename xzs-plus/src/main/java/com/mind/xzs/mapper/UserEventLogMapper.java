package com.mind.xzs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mind.xzs.domain.UserEventLogEntity;
import com.mind.xzs.domain.other.KeyValue;
import com.mind.xzs.domain.viewmodel.admin.user.UserEventPageRequestVM;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface UserEventLogMapper extends BaseMapper<UserEventLogEntity> {

    List<UserEventLogEntity> getUserEventLogByUserId(Long id);

    List<UserEventLogEntity> page(UserEventPageRequestVM requestVM);

    List<KeyValue> selectCountByDate(@Param("startTime") Date startTime, @Param("endTime") Date endTime);
}
