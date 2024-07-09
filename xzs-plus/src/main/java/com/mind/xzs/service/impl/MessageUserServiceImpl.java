package com.mind.xzs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mind.xzs.domain.MessageUserEntity;
import com.mind.xzs.domain.viewmodel.user.MessageRequestVM;
import com.mind.xzs.mapper.MessageUserMapper;
import com.mind.xzs.service.MessageUserService;
import org.springframework.stereotype.Service;

@Service
public class MessageUserServiceImpl extends ServiceImpl<MessageUserMapper, MessageUserEntity> implements MessageUserService {


    @Override
    public PageInfo<MessageUserEntity> studentPage(MessageRequestVM requestVM) {
        return PageHelper.startPage(requestVM.getPageIndex(), requestVM.getPageSize(), "id desc").doSelectPageInfo(() ->
                baseMapper.studentPage(requestVM)
        );
    }

    @Override
    public Integer unReadCount(Long userId) {
        return baseMapper.unReadCount(userId);
    }
}
