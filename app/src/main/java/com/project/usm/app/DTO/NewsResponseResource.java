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
    private String id;
    private String title;
    private Long publishDate;
    private String description;
    private Content content;
    private Long updated;
    private Category category;
    private AuthorResponseResource author;

    public NewsResponseResource(String id, String title, Long publishDate, String description, Content content, Long updated, Category category, AuthorResponseResource author) {
        this.id = id;
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
                "id='" + id + '\'' +
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
