package com.project.usm.app.DTO;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentMarkDataResponseResource {

    private List<StudentYearResponseResource> years;

    public StudentMarkDataResponseResource(List<StudentYearResponseResource> years) {
        this.years = years;
    }
}
