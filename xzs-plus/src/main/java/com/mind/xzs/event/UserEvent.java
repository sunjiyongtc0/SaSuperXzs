package com.mind.xzs.event;

import com.mind.xzs.domain.UserEventLogEntity;
import org.springframework.context.ApplicationEvent;

/**
 * @version 3.5.0
 * @description:  The type User event.
 * Copyright (C), 2020-2024, 武汉思维跳跃科技有限公司
 * @date 2021/12/25 9:45
 */
public class UserEvent extends ApplicationEvent {

    private final UserEventLogEntity userEventLog;

    /**
     * Instantiates a new User event.
     *
     * @param userEventLog the user event log
     */
    public UserEvent(final UserEventLogEntity userEventLog) {
        super(userEventLog);
        this.userEventLog = userEventLog;
    }

    /**
     * Gets user event log.
     *
     * @return the user event log
     */
    public UserEventLogEntity getUserEventLog() {
        return userEventLog;
    }
}
