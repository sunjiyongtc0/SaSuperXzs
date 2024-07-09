package com.mind.xzs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mind.xzs.domain.MessageEntity;
import com.mind.xzs.domain.viewmodel.admin.message.MessagePageRequestVM;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageMapper extends BaseMapper<MessageEntity> {

    List<MessageEntity> page(MessagePageRequestVM requestVM);
//
//    List<MessageEntity> selectByIds(List<Integer> ids);

    int readAdd(Long id);
}
