package com.qiandu.dev.bean;

/**
 * Created by LTN on 2016/9/19.
 * 对应数据库中的OB_MEDIA表结构
 */
public class ObMediaBean {
    String mediaId;
    String objectId;
    String mediaTitle;
    String mediaFormat;
    String note;
    byte[] profile;

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getMediaTitle() {
        return mediaTitle;
    }

    public void setMediaTitle(String mediaTitle) {
        this.mediaTitle = mediaTitle;
    }

    public String getMediaFormat() {
        return mediaFormat;
    }

    public void setMediaFormat(String mediaFormat) {
        this.mediaFormat = mediaFormat;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public byte[] getProfile() {
        return profile;
    }

    public void setProfile(byte[] profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "\"ObMediaBean\":{" +
                "\"mediaId\":" + "\"" + mediaId + "\"" +
                ", \"objectId\":" + "\"" + objectId + "\"" +
                ", \"mediaTitle\":" + "\"" + mediaTitle + "\"" +
                ", \"mediaFormat\":'" + "\"" + mediaFormat + "\"" +
                ", \"note\":" + "\"" + note + "\"" +
                ", \"profile\":" + "\"" + profile + "\"" +
                "}}";
    }
}
