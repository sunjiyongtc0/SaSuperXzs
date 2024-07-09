package com.mind.xzs.controller.admin;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.mind.xzs.controller.BaseApiController;
import com.mind.xzs.core.response.RestResponse;
import com.mind.xzs.domain.MessageEntity;
import com.mind.xzs.domain.MessageUserEntity;
import com.mind.xzs.domain.UserEntity;
import com.mind.xzs.domain.viewmodel.admin.message.MessagePageRequestVM;
import com.mind.xzs.domain.viewmodel.admin.message.MessageResponseVM;
import com.mind.xzs.domain.viewmodel.admin.message.MessageSendVM;
import com.mind.xzs.service.MessageService;
import com.mind.xzs.service.MessageUserService;
import com.mind.xzs.service.UserService;
import com.mind.xzs.utils.DateTimeUtil;
import com.mind.xzs.utils.PageInfoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController("AdminMessageController")
@RequestMapping(value = "/api/admin/message")
public class AdminMessageController extends BaseApiController {

    @Autowired
    private  MessageService messageService;

    @Autowired
    private MessageUserService messageUserService;

    @Autowired
    private UserService userService;



    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public RestResponse<PageInfo<MessageResponseVM>> pageList(@RequestBody MessagePageRequestVM model) {
        PageInfo<MessageEntity> pageInfo = messageService.page(model);
        List<Long> ids = pageInfo.getList().stream().map(d -> d.getId()).collect(Collectors.toList());
        QueryWrapper<MessageUserEntity> qw=new QueryWrapper<>();
        qw.in("message_id",ids);
        List<MessageUserEntity> messageUsers = ids.size() == 0 ? null :messageUserService.list(qw);
        PageInfo<MessageResponseVM> page = PageInfoHelper.copyMap(pageInfo, m -> {
            MessageResponseVM vm = BeanUtil.copyProperties(m, MessageResponseVM.class);
            String receives = messageUsers.stream().filter(d -> d.getMessageId().equals(m.getId())).map(d -> d.getReceiveUserName())
                    .collect(Collectors.joining(","));
            vm.setReceives(receives);
            vm.setCreateTime(DateTimeUtil.dateFormat(m.getCreateTime()));
            return vm;
        });
        return RestResponse.ok(page);
    }


    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public RestResponse send(@RequestBody  MessageSendVM model) {
        UserEntity user = getCurrentUser();
        List<UserEntity> receiveUser = userService.listByIds(model.getReceiveUserIds());
        Date now = new Date();
        MessageEntity message = new MessageEntity();
        message.setTitle(model.getTitle());
        message.setContent(model.getContent());
        message.setCreateTime(now);
        message.setReadCount(0);
        message.setReceiveUserCount(receiveUser.size());
        message.setSendUserId(user.getId());
        message.setSendUserName(user.getUserName());
        message.setSendRealName(user.getRealName());
        List<MessageUserEntity> messageUsers = receiveUser.stream().map(d -> {
            MessageUserEntity messageUser = new MessageUserEntity();
            messageUser.setCreateTime(now);
            messageUser.setReaded(false);
            messageUser.setReceiveRealName(d.getRealName());
            messageUser.setReceiveUserId(d.getId());
            messageUser.setReceiveUserName(d.getUserName());
            return messageUser;
        }).collect(Collectors.toList());
        messageService.sendMessage(message, messageUsers);
        return RestResponse.ok();
    }

}
