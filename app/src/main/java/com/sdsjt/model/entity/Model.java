package com.sdsjt.model.entity;

/**
 * Created by Mark on 2021/2/3.
 * <p>Copyright 2021 ZTZQ.</p>
 */
public class Model {
    private String title;

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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    private String content;
    private String imgUrl;
}
