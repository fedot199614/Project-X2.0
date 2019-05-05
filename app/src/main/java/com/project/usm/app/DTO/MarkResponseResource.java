package com.project.usm.app.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MarkResponseResource {
    private String disciplineName;
    private String mark;
    private String ects;
    private String credits;

    public MarkResponseResource(String disciplineName, String mark, String ects, String credits) {
        this.disciplineName = disciplineName;
        this.mark = mark;
        this.ects = ects;
        this.credits = credits;
    }
}
