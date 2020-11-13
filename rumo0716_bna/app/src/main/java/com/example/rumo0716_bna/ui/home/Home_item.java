package com.example.rumo0716_bna.ui.home;

import java.io.Serializable;

public class Home_item implements Serializable {
    public String source ;
    public String article ;
    public String context ;
    public String love ;
    public String search ;
    public String text_all;
    public int home_text_id;
    public boolean correct_or_fake;//alter=correct fake=fake
    public String key1;
    public String key2;
    public String key3;
    public String emo1;
    public String emo2;
    public String emo3;


    public Home_item() {
        this.search="0";
        this.love="0";
    }

    public Home_item(String source, String article, String context, String love, String search) {
        this.source = source;
        this.article = article;
        this.context = context;
        this.love = love;
        this.search = search;
    }

    public String getSource() {
        return source;
    }

    public String getArticle() {
        return article;
    }

    public String getContext() {
        return context;
    }

    public String getLove() {
        return love;
    }

    public String getSearch() {
        return search;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public void setLove(String love) {
        this.love = love;
    }

    public void setSearch(String search) {
        this.search = search;
    }


}
