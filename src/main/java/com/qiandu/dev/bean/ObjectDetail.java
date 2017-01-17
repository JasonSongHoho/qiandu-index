package com.qiandu.dev.bean;

import java.util.List;

/**
 * Created by LTN on 2016/9/6.
 * 数据模型中的对象详细信息结构
 */
public class ObjectDetail {
    private String primaryObject;
    private String title;
    private String type;
    private String typeLabel;
    private String latitude;
    private String longitude;
    private String beginTime;
    private String endTime;
    private List<PropertyObject> propertySet;
    private List<MediaObject> mediaSet;
    private List<NoteObject> noteSet;

    public String getPrimaryObject() {
        return primaryObject;
    }

    public void setPrimaryObject(String primaryObject) {
        this.primaryObject = primaryObject;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeLabel() {
        return typeLabel;
    }

    public void setTypeLabel(String typeLabel) {
        this.typeLabel = typeLabel;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public List<PropertyObject> getPropertySet() {
        return propertySet;
    }

    public void setPropertySet(List<PropertyObject> propertySet) {
        this.propertySet = propertySet;
    }

    public List<MediaObject> getMediaSet() {
        return mediaSet;
    }

    public void setMediaSet(List<MediaObject> mediaSet) {
        this.mediaSet = mediaSet;
    }

    public List<NoteObject> getNoteSet() {
        return noteSet;
    }

    public void setNoteSet(List<NoteObject> noteSet) {
        this.noteSet = noteSet;
    }

    @Override
    public String toString() {
        return "{" +
                "\"primaryObject\": " + "\"" + primaryObject + "\"" +
                ",\"title\": " + "\"" + title + '\"' +
                ",\"type\": " + "\"" + type + '\"' +
                ",\"typeLabel\": " + "\"" + typeLabel + '\"' +
                ",\"latitude\": " + "\"" + latitude + '\"' +
                ",\"longitude\": " + "\"" + longitude + '\"' +
                ",\"beginTime\": " + "\"" + beginTime + '\"' +
                ",\"endTime\": " + "\"" + endTime + '\"' +
                ",\"propertySet\": " + "\"" + propertySet + "\"" +
                ",\"mediaSet\": " + "\"" + mediaSet + "\"" +
                ",\"noteSet\": " + "\"" + noteSet + "\"" +
                "}";
    }
}
