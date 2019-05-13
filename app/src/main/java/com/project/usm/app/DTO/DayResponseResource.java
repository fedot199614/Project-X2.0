package com.project.usm.app.DTO;

import java.time.DayOfWeek;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DayResponseResource {

    private String day;
    private List<SubjectResponseResource> subjects;

    @Override
    public String toString() {
        return "DayResponseResource{" +
                "day='" + day + '\'' +
                ", subjects=" + subjects +
                '}';
    }

    public DayResponseResource(String day, List<SubjectResponseResource> subjects) {
        this.day = day;
        this.subjects = subjects;
    }
}
