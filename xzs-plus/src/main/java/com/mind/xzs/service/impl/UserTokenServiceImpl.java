package com.mind.xzs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mind.xzs.core.config.SystemConfig;
import com.mind.xzs.domain.UserEntity;
import com.mind.xzs.domain.UserTokenEntity;
import com.mind.xzs.mapper.UserTokenMapper;
import com.mind.xzs.service.UserService;
import com.mind.xzs.service.UserTokenService;
import com.mind.xzs.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
public class UserTokenServiceImpl extends ServiceImpl<UserTokenMapper,UserTokenEntity> implements UserTokenService {

    @Autowired
    private UserService userService;

    @Autowired
    private SystemConfig systemConfig;




    @Override
    @Transactional
    public UserTokenEntity bind(UserEntity user) {
        user.setModifyTime(new Date());
        userService.updateById(user);
        return insertUserToken(user);
    }


    @Override
    public UserTokenEntity checkBind(String openId) {
        UserEntity user = userService.selectByWxOpenId(openId);
        if (null != user) {
            return insertUserToken(user);
        }
        return null;
    }

    @Override
    public UserTokenEntity getToken(String token) {
        return baseMapper.getToken(token);
    }

    @Override
    public UserTokenEntity insertUserToken(UserEntity user) {
        Date startTime = new Date();
        Date endTime = DateTimeUtil.addDuration(startTime, systemConfig.getWx().getTokenToLive());
        UserTokenEntity userToken = new UserTokenEntity();
        userToken.setToken(UUID.randomUUID().toString());
        userToken.setUserId(user.getId());
        userToken.setWxOpenId(user.getWxOpenId());
        userToken.setCreateTime(startTime);
        userToken.setEndTime(endTime);
        userToken.setUserName(user.getUserName());
        userService.updateById(user);
        baseMapper.insert(userToken);
        return userToken;
    }

    @Override
    public void unBind(UserTokenEntity userToken) {
        UserEntity user = userService.getById(userToken.getUserId());
        user.setModifyTime(new Date());
        user.setWxOpenId(null);
        userService.updateById(user);
        baseMapper.deleteById(userToken.getId());
    }

}
