package com.mind.xzs.listener;

import com.mind.xzs.event.UserEvent;
import com.mind.xzs.service.UserEventLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @version 3.5.0
 * @description:  The type User log listener.
 * Copyright (C), 2020-2024, 武汉思维跳跃科技有限公司
 * @date 2021/12/25 9:45
 */
@Component
public class UserLogListener implements ApplicationListener<UserEvent> {

    @Autowired
    private UserEventLogService userEventLogService;


    @Autowired
    public UserLogListener(UserEventLogService userEventLogService) {
        this.userEventLogService = userEventLogService;
    }

    @Override
    public void onApplicationEvent(UserEvent userEvent) {
        userEventLogService.save(userEvent.getUserEventLog());
    }

}
