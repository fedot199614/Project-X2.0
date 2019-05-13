package com.project.usm.app.DTO;

public enum Days {

    MONDAY("ПН"),
    TUESDAY("ВТ"),
    WEDNESDAY("СР"),
    THURSDAY("ЧТ"),
    FRIDAY("ПТ"),
    SATURDAY("CБ"),
    SUNDAY("ВС");

    private String title;

    Days(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "DayOfWeek{" +
                "title='" + title + '\'' +
                '}';
    }
}
