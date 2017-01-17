package com.qiandu.dev.bean;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * Created by LTN on 2016/9/6.
 * 数据模型中的属性对象结构
 */
public class PropertyObject {
    private String type;
    private String dataFormat;
    private JSONObject propertyValue;
    private List<JSONObject> dataSourceRecordSet;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDataFormat() {
        return dataFormat;
    }

    public void setDataFormat(String dataFormat) {
        this.dataFormat = dataFormat;
    }

    public JSONObject getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(JSONObject propertyValue) {
        this.propertyValue = propertyValue;
    }

    public List<JSONObject> getDataSourceRecordSet() {
        return dataSourceRecordSet;
    }

    public void setDataSourceRecordSet(List<JSONObject> dataSourceRecordSet) {
        this.dataSourceRecordSet = dataSourceRecordSet;
    }

    public String toString() {
        return "{" +
                "\"type\": " + "\"" + type + "\"" +
                ",\"dataFormat\": " + "\"" + dataFormat + "\"" +
                ",\"propertyValue\": " + "\"" + propertyValue + "\"" +
                ",\"dataSourceRecordSet\": " + "\"" + dataSourceRecordSet + "\"" +
                "}";
    }
}
