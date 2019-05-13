package com.project.usm.app.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
public class ScheduleModel extends ISchedule {
    private String day;
    private String timeStart;
    private String timeEnd;
    private String block;
    private String subject;
    private String cabinetNumber;
    private String professor;

    public ScheduleModel(String timeStart, String timeEnd, String block, String subjectName, String cabinetNumber, String professorName) {
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.block = block;
        this.subject = subjectName;
        this.cabinetNumber = cabinetNumber;
        this.professor = professorName;
    }

    @Override
    public String toString() {
        return "ScheduleModel{" +
                "day='" + day + '\'' +
                ", timeStart='" + timeStart + '\'' +
                ", timeEnd='" + timeEnd + '\'' +
                ", block='" + block + '\'' +
                ", subject='" + subject + '\'' +
                ", cabinetNumber='" + cabinetNumber + '\'' +
                ", professor='" + professor + '\'' +
                '}';
    }
}
