package com.mind.xzs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.mind.xzs.domain.UserEventLogEntity;
import com.mind.xzs.domain.viewmodel.admin.user.UserEventPageRequestVM;

import java.util.List;

public interface UserEventLogService extends IService<UserEventLogEntity> {

    List<UserEventLogEntity> getUserEventLogByUserId(Long id);

    PageInfo<UserEventLogEntity> page(UserEventPageRequestVM requestVM);

    List<Long> selectMothCount();
}
