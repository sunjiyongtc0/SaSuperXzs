package com.mind.xzs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mind.xzs.domain.UserEntity;
import com.mind.xzs.domain.UserTokenEntity;

public interface UserTokenService extends IService<UserTokenEntity> {

    /**
     * 微信token绑定
     *
     * @param user user
     * @return UserToken
     */
    UserTokenEntity bind(UserEntity user);

    /**
     * 检查微信openId是否绑定过
     *
     * @param openId openId
     * @return UserToken
     */
    UserTokenEntity checkBind(String openId);

    /**
     * 根据token获取UserToken，带缓存的
     *
     * @param token token
     * @return UserToken
     */
    UserTokenEntity getToken(String token);

    /**
     * 插入用户Token
     *
     * @param user user
     * @return UserToken
     */
    UserTokenEntity insertUserToken(UserEntity user);

    /**
     * 微信小程序退出，清除缓存
     *
     * @param userToken userToken
     */
    void unBind(UserTokenEntity userToken);
}
