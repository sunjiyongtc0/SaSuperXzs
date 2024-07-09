package com.mind.xzs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.mind.xzs.domain.UserEntity;
import com.mind.xzs.domain.other.KeyValue;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Xxx
 * @since 2023-11-22
 */
public interface UserService extends IService<UserEntity> {

    PageInfo<UserEntity> userPage(UserEntity userEntity);


    UserEntity getUserByUserName(String username) ;

    List<KeyValue> selectByUserName(String userName);

    UserEntity selectByWxOpenId(String wxOpenId);
}
