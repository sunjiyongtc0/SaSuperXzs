package com.mind.xzs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.mind.xzs.domain.SubjectEntity;
import com.mind.xzs.domain.viewmodel.admin.education.SubjectPageRequestVM;

import java.util.List;

public interface SubjectService extends IService<SubjectEntity> {

    List<SubjectEntity> getSubjectByLevel(Integer level);

    List<SubjectEntity> allSubject();

    Integer levelBySubjectId(Long id);

    PageInfo<SubjectEntity> page(SubjectPageRequestVM requestVM);
}
