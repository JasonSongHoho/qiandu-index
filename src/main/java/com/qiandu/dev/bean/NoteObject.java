package com.qiandu.dev.bean;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LTN on 2016/9/6.
 * 数据模型中的对象说明信结构
 */
public class NoteObject {
    private String noteTitle = "说明的默认标题";
    private String noteData = "说明的默认描述信息";
    private List<JSONObject> dataSourceRecordSet;

    public NoteObject() {
        List<JSONObject> dataSrcRcdSet = new ArrayList<>();
        JSONObject dataSrcRcdJson = new JSONObject();
        dataSrcRcdJson.put("dataSource", "default_ds1");
        dataSrcRcdJson.put("importKey", "default_note_id");
        dataSrcRcdSet.add(dataSrcRcdJson);
        this.dataSourceRecordSet = dataSrcRcdSet;
    }

    public NoteObject(String noteTitle, String noteData, List<JSONObject> dataSourceRecordSet) {
        this.noteTitle = noteTitle;
        this.noteData = noteData;
        this.dataSourceRecordSet = dataSourceRecordSet;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteData() {
        return noteData;
    }

    public void setNoteData(String noteData) {
        this.noteData = noteData;
    }

    public List<JSONObject> getDataSourceRecordSet() {
        return dataSourceRecordSet;
    }

    public void setDataSourceRecordSet(List<JSONObject> dataSourceRecordSet) {
        this.dataSourceRecordSet = dataSourceRecordSet;
    }

    @Override
    public String toString() {
        return "{" +
                "\"noteTitle\"" + "\"" + noteTitle + "\"" +
                ",\"noteData\"" + "\"" + noteData + "\"" +
                ",\"dataSourceRecordSet\"" + "\"" + dataSourceRecordSet + "\"" +
                "}";
    }
}
