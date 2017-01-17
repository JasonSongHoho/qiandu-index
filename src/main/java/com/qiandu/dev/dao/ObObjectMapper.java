package com.qiandu.dev.dao;

import com.qiandu.dev.bean.ObObject;

import java.util.List;

/**
 * Created by Diablo on 16/6/29.
 * Describe:
 */
public interface ObObjectMapper {
    /**
     * 获取全部对象
     * @return
     */
    List<ObObject> getAll();

}
