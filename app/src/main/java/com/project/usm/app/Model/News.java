package com.project.usm.app.Model;

public class News {
    private String title;
    private String news;
    private String full_news;



    public News(String title, String model, String full_news) {
        this.title = title;
        this.news = model;
        this.full_news = full_news;
    }

    public String getFull_news() {
        return full_news;
    }

    public void setFull_news(String full_news) {
        this.full_news = full_news;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getModel() {
        return news;
    }

    public void setModel(String model) {
        this.news = model;
    }




}
