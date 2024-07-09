package com.mind.xzs.event;

import com.mind.xzs.domain.UserEntity;
import org.springframework.context.ApplicationEvent;

/**
 * @version 3.5.0
 * @description:  The type On registration complete event.
 * Copyright (C), 2020-2024, 武汉思维跳跃科技有限公司
 * @date 2021/12/25 9:45
 */
public class OnRegistrationCompleteEvent extends ApplicationEvent {


    private final UserEntity user;


    /**
     * Instantiates a new On registration complete event.
     *
     * @param user the user
     */
    public OnRegistrationCompleteEvent(final UserEntity user) {
        super(user);
        this.user = user;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public UserEntity getUser() {
        return user;
    }

}
