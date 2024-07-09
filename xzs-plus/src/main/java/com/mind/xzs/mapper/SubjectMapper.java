package com.mind.xzs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mind.xzs.domain.SubjectEntity;
import com.mind.xzs.domain.viewmodel.admin.education.SubjectPageRequestVM;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SubjectMapper extends BaseMapper<SubjectEntity> {

    List<SubjectEntity> getSubjectByLevel(Integer level);

    List<SubjectEntity> allSubject();

    List<SubjectEntity> page(SubjectPageRequestVM requestVM);
}
