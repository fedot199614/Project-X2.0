package com.project.usm.app.DTO;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

enum Category {
    ANNOUNCEMENT,
    ADVERTISEMENT,
    EDUCATION,
    NEWS,
    SCHEDULE
}
@Getter
@Setter
public class NewsResponseResource {
    private Integer newsId;
    private String title;
    private Long publishDate;
    private String description;
    private Content content;
    private Long updated;
    private Category category;
    private AuthorResponseResource author;

    public NewsResponseResource(Integer newsId, String title, Long publishDate, String description, Content content, Long updated, Category category, AuthorResponseResource author) {
        this.newsId = newsId;
        this.title = title;
        this.publishDate = publishDate;
        this.description = description;
        this.content = content;
        this.updated = updated;
        this.category = category;
        this.author = author;
    }

    @Override
    public String toString() {
        return "NewsResponseResource{" +
                "newsId='" + newsId + '\'' +
                ", title='" + title + '\'' +
                ", publishDate=" + publishDate +
                ", description='" + description + '\'' +
                ", content=" + content +
                ", updated=" + updated +
                ", category=" + category +
                ", author=" + author +
                '}';
    }
}
