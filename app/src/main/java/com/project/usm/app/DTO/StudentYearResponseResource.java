package com.project.usm.app.DTO;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentYearResponseResource {
    private String studentYear;
    private List<TermResponseResource> terms;

    public StudentYearResponseResource(String studentYear, List<TermResponseResource> terms) {
        this.studentYear = studentYear;
        this.terms = terms;
    }
}
