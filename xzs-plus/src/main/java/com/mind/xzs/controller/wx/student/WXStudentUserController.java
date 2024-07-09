package com.mind.xzs.controller.wx.student;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.SecureUtil;
import com.github.pagehelper.PageInfo;
import com.mind.xzs.controller.wx.BaseWXApiController;
import com.mind.xzs.core.response.RestResponse;
import com.mind.xzs.domain.MessageEntity;
import com.mind.xzs.domain.MessageUserEntity;
import com.mind.xzs.domain.UserEntity;
import com.mind.xzs.domain.UserEventLogEntity;
import com.mind.xzs.domain.enums.RoleEnum;
import com.mind.xzs.domain.enums.UserStatusEnum;
import com.mind.xzs.domain.viewmodel.admin.user.UserResponseVM;
import com.mind.xzs.domain.viewmodel.user.*;
import com.mind.xzs.event.UserEvent;
import com.mind.xzs.service.MessageService;
import com.mind.xzs.service.MessageUserService;
import com.mind.xzs.service.UserEventLogService;
import com.mind.xzs.service.UserService;
import com.mind.xzs.utils.DateTimeUtil;
import com.mind.xzs.utils.PageInfoHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Controller("WXStudentUserController")
@RequestMapping(value = "/api/wx/student/user")
@ResponseBody
public class WXStudentUserController extends BaseWXApiController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserEventLogService userEventLogService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private MessageUserService messageUserService;
    @Autowired
    private  ApplicationEventPublisher eventPublisher;



    @RequestMapping(value = "/current", method = RequestMethod.POST)
    public RestResponse<UserResponseVM> current() {
        UserEntity user = getCurrentUser();
        UserResponseVM userVm = BeanUtil.copyProperties(user,UserResponseVM.class);
        userVm.setBirthDay(DateTimeUtil.dateShortFormat(user.getBirthDay()));
        return RestResponse.ok(userVm);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public RestResponse register( UserRegisterVM model) {
        UserEntity existUser = userService.getUserByUserName(model.getUserName());
        if (null != existUser) {
            return new RestResponse<>(2, "用户已存在");
        }
        UserEntity user = BeanUtil.copyProperties(model, UserEntity.class);
        String securePwd= SecureUtil.sha256(model.getPassword());
        user.setUserUuid(UUID.randomUUID().toString());
        user.setPassword(securePwd);
        user.setRole(1L);
        user.setStatus(UserStatusEnum.Enable.getCode());
        user.setLastActiveTime(new Date());
        user.setCreateTime(new Date());
        user.setDeleted(false);
        userService.save(user);
        UserEventLogEntity userEventLog = new UserEventLogEntity(user.getId(), user.getUserName(), user.getRealName(), new Date());
        userEventLog.setContent("欢迎 " + user.getUserName() + " 注册来到学之思开源考试系统");
        eventPublisher.publishEvent(new UserEvent(userEventLog));
        return RestResponse.ok();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public RestResponse<UserResponseVM> update( UserUpdateVM model) {
        if (StringUtils.isBlank(model.getBirthDay())) {
            model.setBirthDay(null);
        }
        UserEntity user = userService.getById(getCurrentUser().getId());
        BeanUtil.copyProperties(model, user);
        user.setModifyTime(new Date());
        userService.updateById(user);
        UserEventLogEntity userEventLog = new UserEventLogEntity(user.getId(), user.getUserName(), user.getRealName(), new Date());
        userEventLog.setContent(user.getUserName() + " 更新了个人资料");
        eventPublisher.publishEvent(new UserEvent(userEventLog));
        UserResponseVM userVm = BeanUtil.copyProperties(user,UserResponseVM.class);
        return RestResponse.ok(userVm);
    }

    @RequestMapping(value = "/log", method = RequestMethod.POST)
    public RestResponse<List<UserEventLogVM>> log() {
        UserEntity user = getCurrentUser();
        List<UserEventLogEntity> userEventLogs = userEventLogService.getUserEventLogByUserId(user.getId());
        List<UserEventLogVM> userEventLogVMS = userEventLogs.stream().map(d -> {
            UserEventLogVM vm = BeanUtil.copyProperties(d, UserEventLogVM.class);
            vm.setCreateTime(DateTimeUtil.dateFormat(d.getCreateTime()));
            return vm;
        }).collect(Collectors.toList());
        return RestResponse.ok(userEventLogVMS);
    }

    @RequestMapping(value = "/message/page", method = RequestMethod.POST)
    public RestResponse<PageInfo<MessageResponseVM>> messagePageList(MessageRequestVM messageRequestVM) {
        messageRequestVM.setReceiveUserId(getCurrentUser().getId());
        PageInfo<MessageUserEntity> messageUserPageInfo = messageUserService.studentPage(messageRequestVM);
        List<Long> ids = messageUserPageInfo.getList().stream().map(d -> d.getMessageId()).collect(Collectors.toList());
        List<MessageEntity> messages = ids.size() != 0 ? messageService.listByIds(ids) : null;
        PageInfo<MessageResponseVM> page = PageInfoHelper.copyMap(messageUserPageInfo, e -> {
            MessageResponseVM vm = BeanUtil.copyProperties(e, MessageResponseVM.class);
            messages.stream().filter(d -> e.getMessageId().equals(d.getId())).findFirst().ifPresent(message -> {
                vm.setTitle(message.getTitle());
                vm.setContent(message.getContent());
                vm.setSendUserName(message.getSendUserName());
            });
            vm.setCreateTime(DateTimeUtil.dateFormat(e.getCreateTime()));
            return vm;
        });
        return RestResponse.ok(page);
    }

    @RequestMapping(value = "/message/detail/{id}", method = RequestMethod.POST)
    public RestResponse messageDetail(@PathVariable Long id) {
        MessageEntity message = messageService.messageDetail(id);
        return RestResponse.ok(message);
    }


    @RequestMapping(value = "/message/unreadCount", method = RequestMethod.POST)
    public RestResponse unReadCount() {
        Integer count = messageUserService.unReadCount(getCurrentUser().getId());
        return RestResponse.ok(count);
    }

    @RequestMapping(value = "/message/read/{id}", method = RequestMethod.POST)
    public RestResponse read(@PathVariable Long id) {
        messageService.read(id);
        return RestResponse.ok();
    }

}
