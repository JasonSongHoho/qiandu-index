package com.qiandu.dev.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qiandu.dev.bean.*;
import com.qiandu.dev.dao.ObjectBaseInfoMapper;
import com.qiandu.dev.service.ObjectBaseInfoService;
import com.qiandu.dev.util.ImageUtil;
import com.qiandu.dev.util.PropertiesUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by Diablo on 16/6/29. Describe:
 */
@Service("objectBaseInfoService")
public class ObjectBaseInfoImpl implements ObjectBaseInfoService {
    private final Logger log = Logger.getLogger(this.getClass());
    @Autowired
    ObjectBaseInfoMapper objectBaseInfoMapper;

    @Override
    public ObjectDetail getObjectById(int objectId) {
        ObjectDetail objectDetail = new ObjectDetail();
        List<ObjectBaseInfo> objectBaseInfos = objectBaseInfoMapper.getById(objectId);
        //set objectid和objectName。对该两者，在每一条属性值中都一样，择一即可。
        if (objectBaseInfos.size() == 0) {
            return null;
        }
        objectDetail.setPrimaryObject(objectBaseInfos.get(0).getObjectId() + "");
        objectDetail.setTitle(objectBaseInfos.get(0).getObjectName());
        objectDetail.setType(objectBaseInfos.get(0).getObjectUri());
        objectDetail.setTypeLabel(objectBaseInfos.get(0).getLabel());
        objectDetail.setLatitude(objectBaseInfos.get(0).getLatitude());
        objectDetail.setLongitude(objectBaseInfos.get(0).getLongitude());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String beginTime = objectBaseInfos.get(0).getBeginTime();
        String endTime = objectBaseInfos.get(0).getEndTime();
        if(null!=beginTime) {
            try {
                beginTime = formatter.format(formatter.parse(beginTime));
            } catch (ParseException e) {
                e.printStackTrace();
//                log.debug("ObjectDetail beginTime: {} format error: {}",beginTime,e.getMessage());
            }
        }
        if(null!=endTime) {
            try {
                endTime = formatter.format(formatter.parse(endTime));
            } catch (ParseException e) {
                e.printStackTrace();
//                log.debug("ObjectDetail endTime: {} format error: {}",endTime,e.getMessage());
            }
        }
        objectDetail.setBeginTime(beginTime);
        objectDetail.setEndTime(endTime);

        //component property 判断
        Map<String, Integer> propTypeMap = new HashMap<>();
        for (ObjectBaseInfo objectBaseInfo : objectBaseInfos) {
            if (propTypeMap.containsKey(objectBaseInfo.getObjectPropertyId())) {
                propTypeMap.put(objectBaseInfo.getObjectPropertyId(), propTypeMap.get(objectBaseInfo.getObjectPropertyId()) + 1);
            } else {
                propTypeMap.put(objectBaseInfo.getObjectPropertyId(), 1);
            }
        }

        //添加PropertySet
        JSONArray componentPropArray = new JSONArray();//复合属性的定义

        //dataSourceRecordSet
        List<JSONObject> dataSrcRcdSet = new LinkedList<>();

        int componentPropCount = 0;//记录复合属性子属性的个数
        List<PropertyObject> propertyObjectList = new LinkedList<>();
        for (ObjectBaseInfo objectBaseInfo : objectBaseInfos) {

            PropertyObject outPropObj = null;//
            String dataFormat = objectBaseInfoMapper.getPropDataFormat(objectBaseInfo.getPropertyUri());
//            if (propTypeMap.get(objectBaseInfo.getObjectPropertyId()) == 1) {
            if (null!=dataFormat&&dataFormat.equalsIgnoreCase("S")) {
                //simple property
                //简单属性类型为simple，属性值使用JSONObject表示。
                PropertyObject propertyObject = new PropertyObject();
//                dataFormat = objectBaseInfoMapper.getPropDataFormat(objectBaseInfo.getPropertyUri());
                propertyObject.setDataFormat(dataFormat);

                propertyObject.setType(objectBaseInfo.getObjectPropertyId());
                JSONObject propertyJson = new JSONObject();
                propertyJson.put("propertyNote", objectBaseInfo.getNote());
                propertyJson.put("propertyURI", objectBaseInfo.getPropertyUri());
                propertyJson.put("valueType", objectBaseInfoMapper.getPropValueType(objectBaseInfo.getPropertyUri()));

                if (objectBaseInfo.getValueType().equals("S")) {
                    propertyJson.put("propertyData", objectBaseInfo.getStringValue());
                } else if (objectBaseInfo.getValueType().equals("T")) {
                    propertyJson.put("propertyData", objectBaseInfo.getDatetimeValue());
                }

                propertyObject.setPropertyValue(propertyJson);

                JSONObject dsr = new JSONObject();
                Properties dbProperty = PropertiesUtil.getProperties("datasource.properties");
                String dbSource = dbProperty.get("ontologyDataSourceId").toString();
                dsr.put("dataSource", dbSource);//
//                dsr.put("dataTable","OB_PROPERTY");//写死
                dsr.put("importKey", objectBaseInfo.getPropertyId());
                dataSrcRcdSet.add(dsr);
                propertyObject.setDataSourceRecordSet(dataSrcRcdSet);

                dataSrcRcdSet = new LinkedList<>();//reset
                outPropObj = propertyObject;
//            } else if (propTypeMap.get(objectBaseInfo.getObjectPropertyId()) > 1) {
            } else if (null!=dataFormat&&dataFormat.equalsIgnoreCase("C")) {
                //component property
                //复合属性类型为component，属性值使用JSONArray表示；

                String subPropertyName=objectBaseInfo.getPropertyName();
                if(null==subPropertyName){
                    continue;//子属性提取失败，跳过该异常复合类型属性
                }

                PropertyObject propertyObject = null;
                boolean ifExitProp = false;
                //判断是否出现该属性
                for (PropertyObject existProp : propertyObjectList) {
                    if (!existProp.getType().equals(objectBaseInfo.getObjectPropertyId())) {//不存在属性，新建
                        continue;
                    } else {//已存在该属性，则添加子属性
                        ifExitProp = true;
                        propertyObject = existProp;
                        propertyObjectList.remove(existProp);
                        break;
                    }
                }

                //添加新属性
                if (!ifExitProp) {
                    propertyObject = new PropertyObject();
                    propertyObject.setType(objectBaseInfo.getObjectPropertyId());
                }

                componentPropCount++;
                JSONObject jsonObject = new JSONObject();
//                dataFormat = objectBaseInfoMapper.getPropDataFormat(objectBaseInfo.getPropertyUri());
                propertyObject.setDataFormat(dataFormat);

                String detailPropertyUri = objectBaseInfo.getPropertyUri() + "." + subPropertyName;
                jsonObject.put("propertyNote", objectBaseInfoMapper.getNoteOfPropertyUri(detailPropertyUri));
                jsonObject.put("valueType", objectBaseInfoMapper.getPropValueType(detailPropertyUri));
                jsonObject.put("propertyURI", detailPropertyUri);
                jsonObject.put("propertyData", objectBaseInfo.getStringValue());

                JSONObject dsr = new JSONObject();
                Properties dbProperty = PropertiesUtil.getProperties("datasource.properties");
                String dbSource = dbProperty.get("ontologyDataSourceId").toString();
                dsr.put("dataSource", dbSource);
                dsr.put("importKey", objectBaseInfo.getPropertyId());
                dataSrcRcdSet.add(dsr);

                componentPropArray.add(jsonObject);
                if (componentPropCount == propTypeMap.get(objectBaseInfo.getObjectPropertyId())) {//复合属性的最后一个子属性
                    JSONObject componentObj = new JSONObject();
                    componentObj.put("propertyComponent", componentPropArray);
                    propertyObject.setPropertyValue(componentObj);

                    propertyObject.setDataSourceRecordSet(dataSrcRcdSet);

                    dataSrcRcdSet = new LinkedList<>();
                    componentPropArray = new JSONArray();//一个复合属性完成后，重新建立JSONArray包括可能有的新的复合属性
                    componentPropCount = 0;//记录新的复合属性的子属性个数
                }
                outPropObj = propertyObject;
            }
            if(null!=outPropObj) {
                propertyObjectList.add(outPropObj);
            }
        }
        for (PropertyObject propertyObject : propertyObjectList) {
            String typeId=propertyObject.getType();
            propertyObject.setType(objectBaseInfoMapper.getPropUrlByPropId(typeId+""));
        }
        objectDetail.setPropertySet(propertyObjectList);

        //添加MediaSet
        List<MediaObject> mediaObjectList = new ArrayList<>();
        MediaObject mediaObject = new MediaObject();

        ObMediaBean obMediaBean = objectBaseInfoMapper.getMedia(objectId);
        if (null != obMediaBean && null != obMediaBean.getProfile()) {
            StringBuilder sb = new StringBuilder();
            byte[] imgByte = ImageUtil.resizeImage(obMediaBean.getProfile(), 80, 80);
            sb.append(StringUtils.newStringUtf8(Base64.encodeBase64(imgByte, false)));
            String base64Profile = sb.toString();

            List<JSONObject> thumbnail = new LinkedList<>();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("thumbnailData", base64Profile);
            thumbnail.add(jsonObject);

            List<JSONObject> mediaDataSrcRcdSet = new LinkedList<>();
            JSONObject dsr = new JSONObject();
            Properties dbProperty = PropertiesUtil.getProperties("datasource.properties");
            String dbSource = dbProperty.get("ontologyDataSourceId").toString();
            dsr.put("dataSource", dbSource);
            dsr.put("importKey", obMediaBean.getMediaId());
            mediaDataSrcRcdSet.add(dsr);
            mediaObject.setDataSourceRecordSet(mediaDataSrcRcdSet);

            mediaObject.setMediaTitle(obMediaBean.getMediaTitle());
            mediaObject.setMediaDescription(obMediaBean.getNote());
            mediaObject.setThumbnail(thumbnail);
            mediaObjectList.add(mediaObject);
        }else {
            mediaObjectList.add(new MediaObject());//填充一个默认值
        }
        objectDetail.setMediaSet(mediaObjectList);

        //添加NoteSet
        List<NoteObject> noteObjectList = new ArrayList<>();
        NoteObject noteObject = new NoteObject();
        ObNoteBean obNoteBean = objectBaseInfoMapper.getNote(objectId);
        if (null != obNoteBean && null != obNoteBean.getNote()) {
            noteObject.setNoteTitle(obNoteBean.getNoteTitle());
            noteObject.setNoteData(obNoteBean.getNote());

            List<JSONObject> noteDataSrcRcdSet = new LinkedList<>();
            JSONObject noteJson = new JSONObject();
            Properties dbProperty = PropertiesUtil.getProperties("datasource.properties");
            String dbSource = dbProperty.get("ontologyDataSourceId").toString();
            noteJson.put("dataSource", dbSource);
            noteJson.put("importKey", obNoteBean.getNoteId());
            noteDataSrcRcdSet.add(noteJson);
            noteObject.setDataSourceRecordSet(noteDataSrcRcdSet);
            noteObjectList.add(noteObject);

            objectDetail.setNoteSet(noteObjectList);
        }else {
            noteObjectList.add(new NoteObject());//填充一个默认值
            objectDetail.setNoteSet(noteObjectList);
        }

//        log.debug("ObjectDetail of objectId {} is {}.",objectId, objectDetail.toString());
        return objectDetail;
    }

}
