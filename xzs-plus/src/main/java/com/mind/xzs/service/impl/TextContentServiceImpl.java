package com.mind.xzs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mind.xzs.domain.TextContentEntity;
import com.mind.xzs.mapper.TextContentMapper;
import com.mind.xzs.service.TextContentService;
import com.mind.xzs.utils.JsonUtil;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TextContentServiceImpl extends ServiceImpl<TextContentMapper, TextContentEntity> implements TextContentService {

    @Override
    public <T, R> TextContentEntity jsonConvertInsert(List<T> list, Date now, Function<? super T, ? extends R> mapper) {
        String frameTextContent = null;
        if (null == mapper) {
            frameTextContent = JsonUtil.toJsonStr(list);
        } else {
            List<R> mapList = list.stream().map(mapper).collect(Collectors.toList());
            frameTextContent = JsonUtil.toJsonStr(mapList);
        }
        TextContentEntity textContent = new TextContentEntity(frameTextContent, now);
        return textContent;
    }

    @Override
    public <T, R> TextContentEntity jsonConvertUpdate(TextContentEntity textContent, List<T> list, Function<? super T, ? extends R> mapper) {
        String frameTextContent = null;
        if (null == mapper) {
            frameTextContent = JsonUtil.toJsonStr(list);
        } else {
            List<R> mapList = list.stream().map(mapper).collect(Collectors.toList());
            frameTextContent = JsonUtil.toJsonStr(mapList);
        }
        textContent.setContent(frameTextContent);
        return textContent;
    }
}
