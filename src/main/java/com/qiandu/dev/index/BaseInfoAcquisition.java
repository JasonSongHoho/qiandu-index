package com.qiandu.dev.index;

import com.qiandu.dev.bean.ObObject;
import com.qiandu.dev.bean.ObjectDetail;
import com.qiandu.dev.dao.ObObjectMapper;
import com.qiandu.dev.service.ObjectBaseInfoService;
import com.qiandu.dev.util.StringTransUtil;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LTN on 2016/6/30.
 */
public class BaseInfoAcquisition {
    /**
     * 获取对象基本信息，并返回基于DataModel数据模型描述的ObjectDetail集合
     *
     * @param args 仅仅是个标识重载的参数
     * @return
     */
    public List<ObjectDetail> getAllBaseInfo(String args) {

        ApplicationContext context = new FileSystemXmlApplicationContext(new String[]{
                "classpath:spring-context-test.xml"});
        BeanFactory factory = (BeanFactory) context;
        ObjectBaseInfoService objectBaseInfoService = (ObjectBaseInfoService) factory.getBean("objectBaseInfoService");

        ObObjectMapper obObjectMapper = (ObObjectMapper) context.getBean("obObjectMapper");

        List<ObjectDetail> objectDetailList = new ArrayList<>();
        List<Long> objectIdList = new ArrayList<>();

        //特定ID的测试
    /*    int[] addIds = new int[]{4347,13093,13090,13089,13087,13078,13077,9885,8468,7223,7222,7221,7220,5893,13076};
        for(int i=0;i<addIds.length;i++) {
            objectIdList.add(new Long(addIds[i]));
        }*/

        //获取 OB_OBJECT表全部OBJECT_ID
        List<ObObject> obObjectList = obObjectMapper.getAll();
        if (null == obObjectList) {
            return null;
        }

        //默认参数则表示为数据库所有数据建立索引
        if (null == args || args.equals("")) {
            for (int i=0;i<obObjectList.size();i++) {
                objectIdList.add(obObjectList.get(i).getObject_id());
            }
        }else {
            ArrayList<Integer> forIndexIdList= StringTransUtil.transStr2Int(args);
            for(int i=0;i<forIndexIdList.size();i++) {
                objectIdList.add(new Long(forIndexIdList.get(i)));
            }
        }


        //获取BaseInfo所有列表
        for (int i = 0; i < objectIdList.size(); i++) {
            long objectId = objectIdList.get(i);
            ObjectDetail objectDetail = objectBaseInfoService.getObjectById((int) objectId);
            objectDetailList.add(objectDetail);
        }

        return objectDetailList;
    }
}
