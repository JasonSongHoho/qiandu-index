package com.qiandu.dev.bean;

/**
 * Created by Diablo on 16/6/29.
 * Describe:
 * 主要的表：OB_PROPERTY
 * 所谓主要的表，即importKey所在的表
 */
public class ObjectBaseInfo {
    private int objectId;
    private int propertyId;
    private String objectPropertyId;
    private String objectName;
    private String latitude;
    private String longitude;
    private String beginTime;
    private String endTime;
    private String objectUriHash;
    private String propertyName;
    private String propertyUriHash;
    private String valueType;
    private String stringValue;
    private String datetimeValue;
    private String objectUri;
    private String note;
    private String propertyUri;
    private String propertyTitle;
    private int isRequired;
    private int isSys;
    private String label;

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getObjectPropertyId() {
        return objectPropertyId;
    }

    public void setObjectPropertyId(String objectPropertyId) {
        this.objectPropertyId = objectPropertyId;
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

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public String getObjectUriHash() {
        return objectUriHash;
    }

    public void setObjectUriHash(String objectUriHash) {
        this.objectUriHash = objectUriHash;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyUriHash() {
        return propertyUriHash;
    }

    public void setPropertyUriHash(String propertyUriHash) {
        this.propertyUriHash = propertyUriHash;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public String getDatetimeValue() {
        return datetimeValue;
    }

    public void setDatetimeValue(String datetimeValue) {
        this.datetimeValue = datetimeValue;
    }

    public String getObjectUri() {
        return objectUri;
    }

    public void setObjectUri(String objectUri) {
        this.objectUri = objectUri;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPropertyUri() {
        return propertyUri;
    }

    public void setPropertyUri(String propertyUri) {
        this.propertyUri = propertyUri;
    }

    public String getPropertyTitle() {
        return propertyTitle;
    }

    public void setPropertyTitle(String propertyTitle) {
        this.propertyTitle = propertyTitle;
    }

    public int getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(int isRequired) {
        this.isRequired = isRequired;
    }

    public int getIsSys() {
        return isSys;
    }

    public void setIsSys(int isSys) {
        this.isSys = isSys;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "{\"ObjectBaseInfo\":{" +
                "\"objectId\":" + "\"" + objectId + "\"" +
                ", \"objectName\":" + "\"" + objectName + "\"" +
                ", \"objectName\":" + "\"" + latitude + "\"" +
                ",\" objectName\":" + "\"" + longitude + "\"" +
                ",\" objectName\":" + "\"" + beginTime + "\"" +
                ",\" objectName\":" + "\"" + endTime + "\"" +
                ",\" propertyId\":" + "\"" + propertyId + "\"" +
                ",\" objectUriHash\":" + "\"" + objectUriHash + "\"" +
                ",\" propertyName\":" + "\"" + propertyName + "\"" +
                ",\" propertyUriHash\":" + "\"" + propertyUriHash + "\"" +
                ",\" valueType\":" + "\"" + valueType + "\"" +
                ",\" stringValue\":" + "\"" + stringValue + "\"" +
                ",\" datetimeValue\":" + "\"" + datetimeValue + "\"" +
                ",\" objectUri\":" + "\"" + objectUri + "\"" +
                ",\" note\":" + "\"" + note + "\"" +
                ",\" propertyUri\":" + "\"" + propertyUri + "\"" +
                ",\" propertyTitle\":" + "\"" + propertyTitle + "\"" +
                ",\" isRequired\":" + "\"" + isRequired + "\"" +
                ",\" isSys\":" + "\"" + isSys + "\"" +
                ",\" label\":" + "\"" + label + "\"" +
                "}}";
    }
}
