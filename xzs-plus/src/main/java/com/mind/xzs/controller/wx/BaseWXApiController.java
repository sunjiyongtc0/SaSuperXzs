package com.mind.xzs.controller.wx;


import com.mind.xzs.core.context.WxContext;
import com.mind.xzs.domain.UserEntity;
import com.mind.xzs.domain.UserTokenEntity;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseWXApiController {

    @Autowired
    private WxContext wxContext;

    protected UserEntity getCurrentUser() {
        return wxContext.getCurrentUser();
    }

    protected UserTokenEntity getUserToken() {
        return wxContext.getCurrentUserToken();
    }
}
