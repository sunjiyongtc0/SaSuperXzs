package com.mind.xzs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mind.xzs.domain.QuestionEntity;
import com.mind.xzs.domain.question.QuestionPageRequestVM;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface QuestionMapper extends BaseMapper<QuestionEntity> {

    List<QuestionEntity> page(QuestionPageRequestVM requestVM);
//
//    List<Question> selectByIds(@Param("ids") List<Integer> ids);
//
//    Integer selectAllCount();
//
//    List<KeyValue> selectCountByDate(@Param("startTime") Date startTime, @Param("endTime") Date endTime);
}
