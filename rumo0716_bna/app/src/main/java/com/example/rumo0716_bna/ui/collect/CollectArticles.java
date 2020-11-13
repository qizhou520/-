package com.example.rumo0716_bna.ui.collect;

import java.io.Serializable;

public class CollectArticles implements Serializable {
    public String imgPath;//圖片地址
    public String source;//文章來源
    public String content;//文章內容
    public int id;

    public CollectArticles() {
    }

    public CollectArticles(String imgPath, String source, String content) {
        this.imgPath = imgPath;
        this.source = source;
        this.content = content;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String articlesSource) {
        this.source = articlesSource;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String articlesContent) {
        this.content = articlesContent;
    }

}
