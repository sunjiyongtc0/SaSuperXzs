package com.mind.xzs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mind.xzs.domain.UserEntity;
import com.mind.xzs.domain.other.KeyValue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {


    List<UserEntity> userPage(UserEntity userEntity);

    UserEntity getUserByUserName(String username);

    List<KeyValue> selectByUserName(String userName);

    UserEntity selectByWxOpenId(@Param("wxOpenId") String wxOpenId);
}
