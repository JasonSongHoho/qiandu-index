package com.qiandu.dev.bean;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LTN on 2016/9/6.
 * 数据模型中的对象媒体结构
 */
public class MediaObject {
    private String mediaTitle = "媒体的默认标题";
    private String mediaShortDescription = "媒体的默认简短描述信息.";
    private String mediaDescription = "媒体的默认详细描述信息.";
    private String mediaData = "媒体的数据";
    private List<JSONObject> thumbnail;
    private List<JSONObject> dataSourceRecordSet;

    //构造函数设置默认值
    public MediaObject() {
        List thumbnail = new ArrayList();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("thumbnailData", "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");
        thumbnail.add(jsonObject);

        this.thumbnail = thumbnail;
        List<JSONObject> dataSrcRcdSet = new ArrayList<>();
        JSONObject dataSrcRcdJson = new JSONObject();
        dataSrcRcdJson.put("dataSource", "default_ds1");
        dataSrcRcdJson.put("importKey", "default_media_id");
        dataSrcRcdSet.add(dataSrcRcdJson);
        this.dataSourceRecordSet = dataSrcRcdSet;
    }

    public MediaObject(String mediaTitle, String mediaShortDescription, String mediaDescription,
                       String mediaData, List<JSONObject> thumbnail, List<JSONObject> dataSourceRecordSet) {
        this.mediaTitle = mediaTitle;
        this.mediaShortDescription = mediaShortDescription;
        this.mediaDescription = mediaDescription;
        this.mediaData = mediaData;
        this.thumbnail = thumbnail;
        this.dataSourceRecordSet = dataSourceRecordSet;
    }

    public String getMediaTitle() {
        return mediaTitle;
    }

    public void setMediaTitle(String mediaTitle) {
        this.mediaTitle = mediaTitle;
    }

    public String getMediaShortDescription() {
        return mediaShortDescription;
    }

    public void setMediaShortDescription(String mediaShortDescription) {
        this.mediaShortDescription = mediaShortDescription;
    }

    public String getMediaDescription() {
        return mediaDescription;
    }

    public void setMediaDescription(String mediaDescription) {
        this.mediaDescription = mediaDescription;
    }

    public String getMediaData() {
        return mediaData;
    }

    public void setMediaData(String mediaData) {
        this.mediaData = mediaData;
    }

    public List<JSONObject> getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(List<JSONObject> thumbnail) {
        this.thumbnail = thumbnail;
    }

    public List<JSONObject> getDataSourceRecordSet() {
        return dataSourceRecordSet;
    }

    public void setDataSourceRecordSet(List<JSONObject> dataSourceRecordSet) {
        this.dataSourceRecordSet = dataSourceRecordSet;
    }

    @Override
    public String toString() {
        return "{\"MediaObject\": {" +
                "\"mediaTitle\":" + "\"" + mediaTitle + "\"" +
                ",\"mediaShortDescription\":" + "\"" + mediaShortDescription + "\"" +
                ",\"mediaDescription\":" + "\"" + mediaDescription + "\"" +
                ",\"mediaData\":" + "\"" + mediaData + "\"" +
                ",\"thumbnail\":" + "\"" + thumbnail + "\"" +
                ",\"dataSourceRecordSet\":" + "\"" + dataSourceRecordSet + "\"" +
                "}}";
    }
}
