package com.qiandu.dev.index;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qiandu.dev.bean.ObjectDetail;
import com.qiandu.dev.util.GetARandomPosition;
import com.qiandu.dev.util.GetARandomTime;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by LTN on 2016/10/28.
 */
public class RestIndex {
    public static void main(String[] args){

        List<JSONObject> jsonObjectList = new ArrayList<>();

        BaseInfoAcquisition baseInfoAcquisition = new BaseInfoAcquisition();
        List<ObjectDetail> objectDetailList = baseInfoAcquisition.getAllBaseInfo("");
        List<String> jinanPois=null;
        try {
            jinanPois = GetARandomPosition.getPositionFromFile("pois_jinan_gaode.txt");
        } catch (Exception e) {
            System.out.println("read pois error");
            e.printStackTrace();
        }
        int global=0;

        for (ObjectDetail objectDetail : objectDetailList) {
            try {
                String jsonStr = JSONObject.toJSONString(objectDetail);
                JSONObject jsonObject = JSON.parseObject(jsonStr);
                JSONArray propertySet = (JSONArray) jsonObject.get("propertySet");
                JSONArray newPropertySet = new JSONArray();
                JSONObject suggestObj = new JSONObject();
                List<String> suggestArray = new ArrayList<>();

                for (int i = 0; i < propertySet.size(); i++) {
                    JSONObject property = (JSONObject) propertySet.get(i);
                    JSONObject newProperty = property;

                    JSONObject newPropertyValue = new JSONObject();
                    Object dataFormat = property.get("dataFormat");
                    if (null != dataFormat && (dataFormat.toString()).equals("C")) {
                        JSONObject propertyValue = (JSONObject) property.get("propertyValue");
                        JSONArray propertyComponent = (JSONArray) propertyValue.get("propertyComponent");
                        String propertyUriValue = property.get("type").toString();
                        String valueType = "";
                        String propertyNoteValue = "";
                        String propertyDataValue = "";
                        for (int j = 0; j < propertyComponent.size(); j++) {
                            JSONObject subProperty = (JSONObject) propertyComponent.get(j);
                            propertyNoteValue += subProperty.get("propertyNote");
                            propertyDataValue += subProperty.get("propertyData");
                            valueType = subProperty.get("valueType").toString();
                        }
                        newProperty.remove("propertyValue");

                        newPropertyValue.put("propertyURI", propertyUriValue);
                        newPropertyValue.put("propertyNote", propertyNoteValue);
                        newPropertyValue.put("propertyData", propertyDataValue);
                        newPropertyValue.put("valueType", valueType);

                        if (IndexUtil.isSuggestField(propertyUriValue)) {
                            suggestArray.add(propertyDataValue);
                        }

                        newProperty.put("dataFormat", "R");
                        newProperty.put("propertyValue", newPropertyValue);

                    } else {
                        newProperty.remove("dataFormat");
                        newProperty.put("dataFormat", "R");
                    }
                    newPropertySet.add(newProperty);

                    jsonObject.remove("propertySet");
                    jsonObject.put("propertySet", newPropertySet);
                }

                suggestArray.add(jsonObject.get("title").toString());
                if (null != suggestArray) {
                    suggestObj.put("input", suggestArray);
                }
                jsonObject.put("suggestField", suggestObj);

                Date nowTime = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String indexTime = formatter.format(nowTime);
                jsonObject.put("indexTime", indexTime);

                //对地理坐标的处理
                if (null != jsonObject.get("latitude") && (!jsonObject.get("latitude").equals("0"))&& null != jsonObject.get("longitude")&&(!jsonObject.get("longitude").equals("0"))) {
                    String latitude = jsonObject.get("latitude") + "";
                    String longitude = jsonObject.get("longitude") + "";
                    //remove latigude and longitude field
                    jsonObject.remove("latitude");
                    jsonObject.remove("longitude");

                    //add location field
                    JSONObject locationObject = new JSONObject();
                    locationObject.put("lat", latitude);
                    locationObject.put("lon", longitude);
                    jsonObject.put("location", locationObject);
                } else {
                    //若没有地理坐标，则随机生成一个济南地区的坐标供地图展示使用
                    try {
                        jsonObject.remove("latitude");
                        jsonObject.remove("longitude");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//                    String[] randLocation=GetARandomPosition.getARandomPosition().split(",");
                    if(global>9720){
                        global=0;
                    }
                    System.out.println("global: "+global);
                    String[] randLocation=jinanPois.get(global++).split(",");
                    JSONObject locationObject = new JSONObject();
                    locationObject.put("lat", randLocation[1]);
                    locationObject.put("lon", randLocation[0]);
                    jsonObject.put("location", locationObject);
                }

                //对起止时间的处理
                if (null != jsonObject.get("beginTime")) {
                    try {
                        String beginTime = jsonObject.get("beginTime").toString();
                        Date beginDate = formatter.parse(beginTime);
                        beginTime = formatter.format(beginDate);
                        jsonObject.remove("beginTime");
                        jsonObject.put("beginTime", beginTime);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    //没有开始时间，则随机生成一个时间供地图展示使用
                    String beginTime = GetARandomTime.getTheRandomTime();
                    jsonObject.put("beginTime", beginTime);
                }

                if (null != jsonObject.get("endTime")) {
                    try {
                        String endTime = jsonObject.get("endTime").toString();
                        Date endDate = formatter.parse(endTime);
                        endTime = formatter.format(endDate);
                        jsonObject.remove("endTime");
                        jsonObject.put("endTime", endTime);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    //没有结束时间，则随机生成一个时间供地图展示使用
                    String endTime = GetARandomTime.getTheRandomTime();
                    jsonObject.put("endTime", endTime);
                }
                jsonObjectList.add(jsonObject);
                String _id = jsonObject.get("primaryObject") + "";
//                indexAPI.indexByString("ontology_datamodel_v_3_5", "baseinfo", _id, jsonObject + "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        List<JSONObject> testList = new ArrayList<>();
        for(int i=0;i<10;i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", "name");
            jsonObject.put("value", i + "");
            testList.add(jsonObject);
        }

        JestClient client=null;
        Bulk.Builder bulkIndexBuilder = new Bulk.Builder();
        int tailNum=0;
        for (int i=0;i<jsonObjectList.size();i++) {
            JSONObject jsonObject = jsonObjectList.get(i);
            String _id = jsonObject.get("primaryObject") + "";
            bulkIndexBuilder.addAction(new Index.Builder(jsonObject).index("ontology_datamodel_v_3_5").type("baseinfo").id(_id).build());
            if ((i!=0)&&(i % 20== 0)) {
                client=testBulkIndexBuilder();
                try {
                    client.execute(bulkIndexBuilder.build());
                } catch (Exception e) {
                    System.out.println("index error from i="+i);
                    e.printStackTrace();
                }
                bulkIndexBuilder = new Bulk.Builder();
            }
            tailNum = i;
        }
        //收尾
        bulkIndexBuilder = new Bulk.Builder();
        client=testBulkIndexBuilder();
        for (int i=tailNum;i<jsonObjectList.size();i++) {
            JSONObject jsonObject = jsonObjectList.get(i);
            String _id = jsonObject.get("primaryObject") + "";
            bulkIndexBuilder.addAction(new Index.Builder(jsonObject).index("ontology_datamodel_v_3_5").type("baseinfo").id(_id).build());
            }
        try {
            client.execute(bulkIndexBuilder.build());
        } catch (Exception e) {
            System.out.println("index error from i="+tailNum);
            e.printStackTrace();
        }
    }

    public static JestClient testBulkIndexBuilder() {
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(new HttpClientConfig.Builder("http://10.110.13.136:9200")
                .addServer("http://10.110.13.137:9200")
                .addServer("http://10.110.13.138:9200")
                .multiThreaded(true)
                .build());
        JestClient client = factory.getObject();
        return client;
    }
}
