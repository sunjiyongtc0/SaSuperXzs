package com.mind.xzs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mind.xzs.domain.UserEntity;
import com.mind.xzs.domain.other.KeyValue;
import com.mind.xzs.mapper.UserMapper;
import com.mind.xzs.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Xxx
 * @since 2023-11-22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

    @Override
    public PageInfo<UserEntity> userPage(UserEntity userEntity) {
        return PageHelper.startPage(userEntity.getPageIndex(), userEntity.getPageSize(), "id desc").doSelectPageInfo(() ->
                baseMapper.userPage(userEntity)
        );
    }

    @Override
    public UserEntity getUserByUserName(String username) {
        return baseMapper.getUserByUserName(username);
    }

    @Override
    public List<KeyValue> selectByUserName(String userName) {
        return baseMapper.selectByUserName(userName);
    }

    @Override
    public UserEntity selectByWxOpenId(String wxOpenId) {
        return baseMapper.selectByWxOpenId(wxOpenId);
    }

}
