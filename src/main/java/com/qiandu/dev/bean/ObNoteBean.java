package com.qiandu.dev.bean;

/**
 * Created by LTN on 2016/9/19.
 * 对象数据库中的OB_NOTE表结构
 */
public class ObNoteBean {
    private String noteId;
    private String objectId;
    private String noteTitle;
    private String note;

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "\"ObNoteBean\":{" +
                "\"noteId\":" + "\"" + noteId + "\"" +
                ", \"objectId\":" + "\"" + objectId + "\"" +
                ", \"noteTitle\":" + "\"" + noteTitle + "\"" +
                ", \"note\":'" + "\"" + note + "\"" +
                "}}";
    }
}
