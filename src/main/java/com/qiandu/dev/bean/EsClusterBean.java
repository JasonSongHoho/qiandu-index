package com.qiandu.dev.bean;

/**
 * Created by LTN on 2016/11/14.
 */
public class EsClusterBean {
    private String clusterName;
    private String[] clusterIds;
    private String indexName;
    private String typeName="baseinfo";
    private String indexIds;

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String[] getClusterIds() {
        return clusterIds;
    }

    public void setClusterIds(String[] clusterIds) {
        this.clusterIds = clusterIds;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getIndexIds() {
        return indexIds;
    }

    public void setIndexIds(String indexIds) {
        this.indexIds = indexIds;
    }
}
