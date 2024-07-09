package com.mind.xzs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mind.xzs.domain.UserTokenEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserTokenMapper extends BaseMapper<UserTokenEntity> {

    UserTokenEntity getToken(String token);

}
