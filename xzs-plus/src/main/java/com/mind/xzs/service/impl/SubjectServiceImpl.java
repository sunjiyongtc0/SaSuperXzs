package com.mind.xzs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mind.xzs.domain.SubjectEntity;
import com.mind.xzs.domain.viewmodel.admin.education.SubjectPageRequestVM;
import com.mind.xzs.mapper.ExamPaperMapper;
import com.mind.xzs.mapper.SubjectMapper;
import com.mind.xzs.service.SubjectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectServiceImpl  extends ServiceImpl<SubjectMapper, SubjectEntity> implements SubjectService {


//
//    @Override
//    public SubjectEntity selectById(Long id) {
//        return baseMapper.selectById(id);
//    }
//
//    @Override
//    public int updateById(SubjectEntity record) {
//        return baseMapper.updateById(record);
//    }

    @Override
    public List<SubjectEntity> getSubjectByLevel(Integer level) {
        return baseMapper.getSubjectByLevel(level);
    }

    @Override
    public List<SubjectEntity> allSubject() {
        return baseMapper.allSubject();
    }

    @Override
    public Integer levelBySubjectId(Long id) {
        return baseMapper.selectById(id).getLevel();
    }

    @Override
    public PageInfo<SubjectEntity> page(SubjectPageRequestVM requestVM) {
        return PageHelper.startPage(requestVM.getPageIndex(), requestVM.getPageSize(), "id desc").doSelectPageInfo(() ->
                baseMapper.page(requestVM)
        );
    }

}
