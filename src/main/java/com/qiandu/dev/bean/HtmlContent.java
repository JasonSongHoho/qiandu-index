package com.qiandu.dev.bean;

/**
 * Created by Diablo on 2016/10/19.
 */
public class HtmlContent {
    private String title;
    private String content;

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

    @Override
    public String toString() {
        return "" +
                "title=" + title  +
                ", content=" + content;
    }
}
