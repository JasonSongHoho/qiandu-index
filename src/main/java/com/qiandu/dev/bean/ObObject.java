package com.qiandu.dev.bean;

/**
 * 对应数据库中的OB_OBJECT表结构
 */
public class ObObject {
    private Long object_id;
    private String object_name;
    private String object_uri;
    private Long object_uri_hash;
    private Long latitude;
    private Long longitude;
    private java.sql.Timestamp begin_time;
    private java.sql.Timestamp end_time;
    private String client_id;
    private String creator_id;
    private java.sql.Timestamp create_time;
    private String editor_id;
    private java.sql.Timestamp edit_time;
    private Long scn;

    public Long getObject_id() {
        return object_id;
    }

    public void setObject_id(Long object_id) {
        this.object_id = object_id;
    }

    public String getObject_name() {
        return object_name;
    }

    public void setObject_name(String object_name) {
        this.object_name = object_name;
    }

    public String getObject_uri() {
        return object_uri;
    }

    public void setObject_uri(String object_uri) {
        this.object_uri = object_uri;
    }

    public Long getObject_uri_hash() {
        return object_uri_hash;
    }

    public void setObject_uri_hash(Long object_uri_hash) {
        this.object_uri_hash = object_uri_hash;
    }

    public Long getLatitude() {
        return latitude;
    }

    public void setLatitude(Long latitude) {
        this.latitude = latitude;
    }

    public Long getLongitude() {
        return longitude;
    }

    public void setLongitude(Long longitude) {
        this.longitude = longitude;
    }

    public java.sql.Timestamp getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(java.sql.Timestamp begin_time) {
        this.begin_time = begin_time;
    }

    public java.sql.Timestamp getEnd_time() {
        return end_time;
    }

    public void setEnd_time(java.sql.Timestamp end_time) {
        this.end_time = end_time;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getCreator_id() {
        return creator_id;
    }

    public void setCreator_id(String creator_id) {
        this.creator_id = creator_id;
    }

    public java.sql.Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(java.sql.Timestamp create_time) {
        this.create_time = create_time;
    }

    public String getEditor_id() {
        return editor_id;
    }

    public void setEditor_id(String editor_id) {
        this.editor_id = editor_id;
    }

    public java.sql.Timestamp getEdit_time() {
        return edit_time;
    }

    public void setEdit_time(java.sql.Timestamp edit_time) {
        this.edit_time = edit_time;
    }

    public Long getScn() {
        return scn;
    }

    public void setScn(Long scn) {
        this.scn = scn;
    }

    @Override
    public String toString() {
        return "{\"ObObject\":{" +
                "\"object_id\":" + "\"" + object_id + "\"" +
                ", \"object_name\":" + "\"" + object_name + "\"" +
                ", \"object_uri\":" + "\"" + object_uri + "\"" +
                ", \"object_uri_hash\":" + "\"" + object_uri_hash + "\"" +
                ", \"latitude\":" + "\"" + latitude + "\"" +
                ", \"longitude\":" + "\"" + longitude + "\"" +
                ", \"begin_time\":" + "\"" + begin_time + "\"" +
                ", \"end_time\":" + "\"" + end_time + "\"" +
                ", \"client_id\":" + "\"" + client_id + "\"" +
                ", \"creator_id\":" + "\"" + creator_id + "\"" +
                ", \"create_time\":" + "\"" + create_time + "\"" +
                ", \"editor_id\":" + "\"" + editor_id + "\"" +
                ", \"edit_time\":" + "\"" + edit_time + "\"" +
                ", \"scn\":" + "\"" + scn + "\"" +
                "}}";
    }
}
