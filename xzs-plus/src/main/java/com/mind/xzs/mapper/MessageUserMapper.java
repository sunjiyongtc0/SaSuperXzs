package com.mind.xzs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mind.xzs.domain.MessageUserEntity;
import com.mind.xzs.domain.viewmodel.user.MessageRequestVM;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageUserMapper extends BaseMapper<MessageUserEntity> {
//
//    List<MessageUser> selectByMessageIds(List<Integer> ids);
//
//    int inserts(List<MessageUser> list);

    List<MessageUserEntity> studentPage(MessageRequestVM requestVM);

    Integer unReadCount(Long userId);
}
