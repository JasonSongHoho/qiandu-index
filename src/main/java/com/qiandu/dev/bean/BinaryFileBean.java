package com.qiandu.dev.bean;

/**
 * Created by LTN on 2016/5/7.
 */
public class BinaryFileBean {
    //location of file, use decide
    private String uri;
    //文件在文件服务器上的访问
    private String fileUrl;
    //file type, "Content-Type" or "dc:format"
    private String type;
    //file author,"Author" or "creator"
    private String author;
    //file title,"title"
    private String title;
    //file content
    private String content;
    //crete time,"Creation-Date" or "meta:creation-date"
    private String createTime;
    //last modify time, "modified"
    private String lastModifyTime;


    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(String lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }
}
