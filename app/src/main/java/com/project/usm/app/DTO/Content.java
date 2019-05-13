package com.project.usm.app.DTO;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Content {
    private String message;
    private List<String> imageLinks;

    public Content(String message, List<String> imageLinks) {
        this.message = message;
        this.imageLinks = imageLinks;
    }

}
