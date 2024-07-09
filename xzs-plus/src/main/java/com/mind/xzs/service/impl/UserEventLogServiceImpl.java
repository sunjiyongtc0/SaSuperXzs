package com.mind.xzs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mind.xzs.domain.UserEventLogEntity;
import com.mind.xzs.domain.other.KeyValue;
import com.mind.xzs.domain.viewmodel.admin.user.UserEventPageRequestVM;
import com.mind.xzs.mapper.UserEventLogMapper;
import com.mind.xzs.service.UserEventLogService;
import com.mind.xzs.utils.DateTimeUtil;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserEventLogServiceImpl extends ServiceImpl<UserEventLogMapper, UserEventLogEntity> implements UserEventLogService {



    @Override
    public List<UserEventLogEntity> getUserEventLogByUserId(Long id) {
        return baseMapper.getUserEventLogByUserId(id);
    }

    @Override
    public PageInfo<UserEventLogEntity> page(UserEventPageRequestVM requestVM) {
        return PageHelper.startPage(requestVM.getPageIndex(), requestVM.getPageSize(), "id desc").doSelectPageInfo(() ->
                baseMapper.page(requestVM)
        );
    }


    @Override
    public List<Long> selectMothCount() {
        Date startTime = DateTimeUtil.getMonthStartDay();
        Date endTime = DateTimeUtil.getMonthEndDay();
        List<KeyValue> mouthCount = baseMapper.selectCountByDate(startTime, endTime);
        List<String> mothStartToNowFormat = DateTimeUtil.MothStartToNowFormat();
        return mothStartToNowFormat.stream().map(md -> {
            KeyValue keyValue = mouthCount.stream().filter(kv -> kv.getName().equals(md)).findAny().orElse(null);
            return null == keyValue ? 0 : keyValue.getValue();
        }).collect(Collectors.toList());
    }

}
