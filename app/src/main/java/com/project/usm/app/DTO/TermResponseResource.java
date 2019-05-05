package com.project.usm.app.DTO;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TermResponseResource {

    private String termNumber;
    private List<MarkResponseResource> marks;

    public TermResponseResource(String termNumber, List<MarkResponseResource> marks) {
        this.termNumber = termNumber;
        this.marks = marks;
    }
}
