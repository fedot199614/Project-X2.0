package com.project.usm.app.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
public class ScheduleModel extends ISchedule {
    private String timeStart;
    private String timeEnd;
    private String block;
    private String subjectName;
    private String cabinetNumber;
    private String professorName;

    public ScheduleModel(String timeStart, String timeEnd, String block, String subjectName, String cabinetNumber, String professorName) {
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.block = block;
        this.subjectName = subjectName;
        this.cabinetNumber = cabinetNumber;
        this.professorName = professorName;
    }
}
