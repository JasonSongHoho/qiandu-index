package com.qiandu.dev.index;

import com.inspur.qiandu.es.index.DeleteAPI;
import com.inspur.qiandu.es.util.ClientManager;
import org.elasticsearch.client.Client;

/**
 * Created by LTN on 2016/6/30.
 */
public class BaseInfoDel {
    public static void main(String[] args) {
        String clusterName = "qiandu-dev";
        String[] ips={"10.110.13.136","10.110.13.137","10.110.13.138"};
        ClientManager clientManager = new ClientManager(clusterName, ips);
        Client client=clientManager.getInstance().getClient();
        DeleteAPI deleteAPI = new DeleteAPI(client);
        for(int i=0;i<45;i++) {
            System.out.println(i);
//            deleteAPI.delete("doc", "onto_baseinfo", "idx0630"+i);
        }
    }
}
