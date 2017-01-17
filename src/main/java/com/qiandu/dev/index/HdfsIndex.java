package com.qiandu.dev.index;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.inspur.qiandu.es.index.IndexAPI;
import com.inspur.qiandu.es.util.ClientManager;
import com.qiandu.dev.bean.BinaryFileBean;
import com.qiandu.dev.bean.EsClusterBean;
import com.qiandu.dev.parser.BinaryFileParser;
import com.qiandu.dev.util.HadoopFSOperations;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.elasticsearch.client.Client;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by LTN on 2016/11/15.
 */
public class HdfsIndex {
    public static void main(String[] args) throws Exception{
        //第一个参数指定配置文件路径
        ApplicationContext context = new FileSystemXmlApplicationContext(args[0]);

        //读取es配置信息，建立es连接
        EsClusterBean esClusterBean=(EsClusterBean)context.getBean("lawCaseClusterBean");
        String clusterName = esClusterBean.getClusterName();
        String[] ips=esClusterBean.getClusterIds();
        String indexName=esClusterBean.getIndexName();
        String typeName=esClusterBean.getTypeName();

        ClientManager clientManager = new ClientManager(clusterName, ips);
        Client client = clientManager.getInstance().getClient();
        IndexAPI indexAPI = new IndexAPI(client);

        HadoopFSOperations hadoopFSOperations=new HadoopFSOperations("10.110.13.57","9000");
        FileStatus[] fileStatuses= hadoopFSOperations.listAll("/qiandu/channel/case/pdf");
        for (FileStatus fileStatus : fileStatuses) {
            FSDataInputStream fsDataInputStream=hadoopFSOperations.readHDFSFile2Stream(fileStatus.getPath().toString());
            BinaryFileBean fileBean= BinaryFileParser.parseBinary(fsDataInputStream);
            fileBean.setUri(fileStatus.getPath().toString());
            fileBean.setTitle(fileStatus.getPath().getName());
            String rawContent=fileBean.getContent();
            fileBean.setContent(rawContent.replace("\n",""));
            fileBean.setFileUrl("http://10.110.13.137/doc/pdf/"+fileStatus.getPath().getName());
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(fileBean));

            Date nowTime= new Date();
            SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String indexTime =time.format(nowTime);
            jsonObject.put("indexTime", indexTime);
            String uuid=UUID.randomUUID().toString().replaceAll("-","").substring(20);

            indexAPI.indexByString(indexName,typeName, "case"+uuid, jsonObject + "");
            System.out.println(jsonObject);
        }
    }
}
