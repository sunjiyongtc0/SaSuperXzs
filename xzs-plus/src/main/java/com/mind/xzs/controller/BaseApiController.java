package com.mind.xzs.controller;

import com.mind.xzs.domain.UserEntity;
import com.mind.xzs.secure.util.UserInfoUtil;
import com.mind.xzs.utils.SnowFlake;

import java.util.Map;

/**
 * <pre>
 *
 * </pre>
 *
 * @author sunjiyong
 * @since 2024/7/8 11:11
 */
public class BaseApiController {

    public long generateId(){
        SnowFlake snowFlake=new SnowFlake(1,1);

        return snowFlake.nextId();
    }


    public UserEntity getCurrentUser(){
        UserEntity userinfo = UserInfoUtil.userinfoEntity();
        return userinfo;
    }
}
