package com.qiandu.dev.dao;

import com.qiandu.dev.bean.ObMediaBean;
import com.qiandu.dev.bean.ObNoteBean;
import com.qiandu.dev.bean.ObjectBaseInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by Diablo on 16/6/29.
 * Describe:
 */
public interface ObjectBaseInfoMapper {

    List<ObjectBaseInfo> getById(int objectId);

    String getNoteOfPropertyUri(String propertyUri);

    String getPropValueType(String propertyUri);

    String getPropDataFormat(String propertyUri);

    String getPropUrlByPropId(String objectPropertyId);

    ObMediaBean getMedia(int objectId);

    ObNoteBean getNote(int objectId);
}
