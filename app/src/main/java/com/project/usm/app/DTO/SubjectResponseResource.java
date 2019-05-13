package com.project.usm.app.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubjectResponseResource {
    private String time;
    private String professor;
    private String subject;
    private String lectureRoom;

    public SubjectResponseResource(String time, String professor, String subject, String lectureRoom) {
        this.time = time;
        this.professor = professor;
        this.subject = subject;
        this.lectureRoom = lectureRoom;
    }


}
