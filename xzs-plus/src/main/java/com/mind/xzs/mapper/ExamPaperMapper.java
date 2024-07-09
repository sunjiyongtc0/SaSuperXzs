package com.mind.xzs.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mind.xzs.domain.ExamPaperEntity;
import com.mind.xzs.domain.other.KeyValue;
import com.mind.xzs.domain.viewmodel.admin.exam.ExamPaperPageRequestVM;
import com.mind.xzs.domain.viewmodel.student.dashboard.PaperFilter;
import com.mind.xzs.domain.viewmodel.student.dashboard.PaperInfo;
import com.mind.xzs.domain.viewmodel.student.exam.ExamPaperPageVM;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface ExamPaperMapper extends BaseMapper<ExamPaperEntity> {

    List<ExamPaperEntity> page(ExamPaperPageRequestVM requestVM);

    List<ExamPaperEntity> taskExamPage(ExamPaperPageRequestVM requestVM);

    List<ExamPaperEntity> studentPage(ExamPaperPageVM requestVM);

    List<PaperInfo> indexPaper(PaperFilter paperFilter);

    Integer selectAllCount();

    List<KeyValue> selectCountByDate(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    int updateTaskPaper(@Param("taskId") Integer taskId, @Param("paperIds") List<Integer> paperIds);

    int clearTaskPaper(@Param("paperIds") List<Integer> paperIds);
}
