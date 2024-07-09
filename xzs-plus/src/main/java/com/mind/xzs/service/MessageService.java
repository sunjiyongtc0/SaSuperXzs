package com.mind.xzs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.mind.xzs.domain.MessageEntity;
import com.mind.xzs.domain.MessageUserEntity;
import com.mind.xzs.domain.viewmodel.admin.message.MessagePageRequestVM;

import java.util.List;

public interface MessageService extends IService<MessageEntity> {

//    List<Message> selectMessageByIds(List<Integer> ids);


    PageInfo<MessageEntity> page(MessagePageRequestVM requestVM);
//
//    List<MessageUser> selectByMessageIds(List<Integer> ids);
//
    void sendMessage(MessageEntity message, List<MessageUserEntity> messageUsers);

    void read(Long id);


    MessageEntity messageDetail(Long id);
}
