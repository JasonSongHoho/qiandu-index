package com.qiandu.dev.service;

import com.qiandu.dev.bean.ObjectDetail;

/**
 * Created by Diablo on 16/6/29.
 * Describe:
 */

public interface ObjectBaseInfoService {

    /**
     * 获取对象的基本信息
     *
     * @param objectId 对象ID
     * @return
     */
    ObjectDetail getObjectById(int objectId);
}
