package com.project.usm.app.Model;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class News {
    private String title;
    private String news;
    private String full_news;
    private String publishDate;
    private List<String> imgURL = new ArrayList<>();
    private String authorName;
    private SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");


    public News(String title,long publishDate, String model, String full_news,List<String> imgURL,String authorName) {
        this.title = title;
        this.news = model;
        this.full_news = full_news;
        this.publishDate = format.format(new Date(publishDate));
        this.imgURL.addAll(imgURL);
        this.authorName = authorName;
    }

    public String getFull_news() {

        return full_news;
    }
    public boolean urlListIsEmpty(){
        if(imgURL.isEmpty()){
            return true;
        }else{
            return false;
        }
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


    @Override
    public String toString() {

        return "News{" +
                "title='" + title + '\'' +
                ", news='" + news + '\'' +
                ", full_news='" + full_news + '\'' +
                ", publishDate=" + publishDate +
                ", imgURL=" + imgURL +
                ", authorName='" + authorName + '\'' +
                '}';
    }
}
