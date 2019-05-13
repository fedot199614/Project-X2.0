package com.project.usm.app.DTO;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorResponseResource {
    private String authorId;
    private String firstName;
    private String lastName;
    private String email;
    private List<String> newsIds;

    public AuthorResponseResource(String authorId, String firstName, String lastName, String email, List<String> newsIds) {
        this.authorId = authorId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.newsIds = newsIds;
    }
}
