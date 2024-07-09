package com.mind.xzs.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mind.xzs.domain.TextContentEntity;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

public interface TextContentService extends IService<TextContentEntity> {

    /**
     * 创建一个TextContent，将内容转化为json，回写到content中，不入库
     *
     * @param list
     * @param now
     * @param mapper
     * @param <T>
     * @param <R>
     * @return
     */
    <T, R> TextContentEntity jsonConvertInsert(List<T> list, Date now, Function<? super T, ? extends R> mapper);

    /**
     * 修改一个TextContent，将内容转化为json，回写到content中，不入库
     *
     * @param textContent
     * @param list
     * @param mapper
     * @param <T>
     * @param <R>
     * @return
     */
    <T, R> TextContentEntity jsonConvertUpdate(TextContentEntity textContent, List<T> list, Function<? super T, ? extends R> mapper);

}
