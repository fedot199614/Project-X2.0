package com.project.usm.app.DTO;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleResponseResource {
    private String groupId;
    private List<DayResponseResource> days;

    @Override
    public String toString() {
        return "ScheduleResponseResource{" +
                "groupId='" + groupId + '\'' +
                ", days=" + days +
                '}';
    }

    public ScheduleResponseResource(String groupId, List<DayResponseResource> days) {
        this.groupId = groupId;
        this.days = days;
    }
}
