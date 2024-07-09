package com.mind.xzs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.mind.xzs.domain.MessageUserEntity;
import com.mind.xzs.domain.viewmodel.user.MessageRequestVM;

public interface MessageUserService extends IService<MessageUserEntity> {

    PageInfo<MessageUserEntity> studentPage(MessageRequestVM requestVM);

    Integer unReadCount(Long userId);
}
