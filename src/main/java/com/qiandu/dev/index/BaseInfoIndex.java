package com.qiandu.dev.index;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.inspur.qiandu.es.index.IndexAPI;
import com.inspur.qiandu.es.util.ClientManager;
import com.qiandu.dev.bean.EsClusterBean;
import com.qiandu.dev.bean.ObjectDetail;
import com.qiandu.dev.util.GetARandomPosition;
import com.qiandu.dev.util.GetARandomTime;
import org.elasticsearch.client.Client;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.Resource;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by LTN on 2016/6/30.
 */
public class BaseInfoIndex {
    static private org.slf4j.Logger log = LoggerFactory.getLogger(BaseInfoIndex.class);
    public static void main(String[] args) throws Exception {

        //加载Spring上下文配置文件
//        本地测试使用文件
        ApplicationContext context = new FileSystemXmlApplicationContext("src/main/resources/spring-context-test.xml");
//        应用环境使用用户配置的目录
//        ApplicationContext context = new FileSystemXmlApplicationContext(args[0]);

        EsClusterBean esClusterBean=(EsClusterBean)context.getBean("esClusterBean");
        String clusterName = esClusterBean.getClusterName();
        String[] ips=esClusterBean.getClusterIds();
        String indexName=esClusterBean.getIndexName();
        String typeName=esClusterBean.getTypeName();

        String indexIds=esClusterBean.getIndexIds();

        Resource resource = context.getResource("classpath:pois_jinan_gaode.txt");
        BaseInfoAcquisition baseInfoAcquisition = new BaseInfoAcquisition();
        List<ObjectDetail> objectDetailList = baseInfoAcquisition.getAllBaseInfo(indexIds);

        ClientManager clientManager = new ClientManager(clusterName, ips);
        Client client = clientManager.getInstance().getClient();
        IndexAPI indexAPI = new IndexAPI(client);

        InputStream inputStream=resource.getInputStream();
        List<String> jinanPois = GetARandomPosition.getPositionFromFile(inputStream);
        int global=6000;

        int totalItems=objectDetailList.size();
        int tailItems=totalItems;
        List<JSONObject> bulkIndexList = new ArrayList<>();
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
                    //分配完文件中所有记录后，从头开始分配
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

                /*String _id = jsonObject.get("primaryObject") + "";
                indexAPI.indexByString(indexName,typeName, _id, jsonObject + "");
                log.debug(_id+": indexed");
                System.out.println(_id+": indexed");*/

                //使用批量所有建立方式，并制定特定的_id。每1000条批量建立一次，批量中每次个bulk大小为10条。
                String _id = jsonObject.get("primaryObject") + "";
                jsonObject.put("_id", _id);
                bulkIndexList.add(jsonObject);
                if (tailItems > 1000 && bulkIndexList.size() == 1000) {
                    JSONObject indexStatObj = indexAPI.bulkIndex(indexName, typeName, bulkIndexList, 10);
                    bulkIndexList.clear();
                    tailItems = tailItems - 1000;
                    System.out.println(indexStatObj);
                } else {
                    JSONObject indexStatObj = indexAPI.bulkIndex(indexName, typeName, bulkIndexList, 10);
                    bulkIndexList.clear();
                    System.out.println(indexStatObj);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
