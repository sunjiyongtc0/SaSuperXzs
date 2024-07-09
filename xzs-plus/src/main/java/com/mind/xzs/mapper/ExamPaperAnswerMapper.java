package com.mind.xzs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mind.xzs.domain.ExamPaperAnswerEntity;
import com.mind.xzs.domain.other.KeyValue;
import com.mind.xzs.domain.viewmodel.student.exampaper.ExamPaperAnswerPageVM;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface ExamPaperAnswerMapper extends BaseMapper<ExamPaperAnswerEntity> {

    List<ExamPaperAnswerEntity> studentPage(ExamPaperAnswerPageVM requestVM);

    Integer selectAllCount();

    List<KeyValue> selectCountByDate(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    ExamPaperAnswerEntity getByPidUid(@Param("pid") Long paperId, @Param("uid") Long uid);

    List<ExamPaperAnswerEntity> adminPage(com.mind.xzs.domain.viewmodel.admin.paper.ExamPaperAnswerPageRequestVM requestVM);
}
