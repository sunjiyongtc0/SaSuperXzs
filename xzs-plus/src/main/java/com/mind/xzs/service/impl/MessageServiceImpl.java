package com.mind.xzs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mind.xzs.domain.MessageEntity;
import com.mind.xzs.domain.MessageUserEntity;
import com.mind.xzs.domain.viewmodel.admin.message.MessagePageRequestVM;
import com.mind.xzs.mapper.MessageMapper;
import com.mind.xzs.service.MessageService;
import com.mind.xzs.service.MessageUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, MessageEntity> implements MessageService {


    @Autowired
    private MessageUserService messageUserService;

//    private final MessageUserMapper messageUserMapper;

//    @Autowired
//    public MessageServiceImpl(MessageMapper messageMapper, MessageUserMapper messageUserMapper) {
//        this.messageMapper = messageMapper;
//        this.messageUserMapper = messageUserMapper;
//    }

//    @Override
//    public List<Message> selectMessageByIds(List<Integer> ids) {
//        return messageMapper.selectByIds(ids);
//    }


    @Override
    public PageInfo<MessageEntity> page(MessagePageRequestVM requestVM) {
        return PageHelper.startPage(requestVM.getPageIndex(), requestVM.getPageSize(), "id desc").doSelectPageInfo(() ->
                baseMapper.page(requestVM)
        );
    }
//
//    @Override
//    public List<MessageUser> selectByMessageIds(List<Integer> ids) {
//        return messageUserMapper.selectByMessageIds(ids);
//    }

    @Override
    @Transactional
    public void sendMessage(MessageEntity message, List<MessageUserEntity> messageUsers) {
        baseMapper.insert(message);
        messageUsers.forEach(d -> d.setMessageId(message.getId()));
        messageUserService.saveBatch(messageUsers);
    }

    @Override
    @Transactional
    public void read(Long id) {
        MessageUserEntity messageUser = messageUserService.getById(id);
        if (messageUser.getReaded())
            return;
        messageUser.setReaded(true);
        messageUser.setReadTime(new Date());
        messageUserService.updateById(messageUser);
        baseMapper.readAdd(messageUser.getMessageId());
    }


    @Override
    public MessageEntity messageDetail(Long id) {
        MessageUserEntity messageUser = messageUserService.getById(id);
        return baseMapper.selectById(messageUser.getMessageId());
    }

}
