package com.project.usm.app.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileOneElement {
    String name;
    Boolean editable;
    String queryName;
    int inputType;
    String responseResult;

    public UserProfileOneElement(String name, String queryName, Boolean editable,int inputType,String responseResult) {
        this.name = name;
        this.editable = editable;
        this.queryName = queryName;
        this.inputType = inputType;
        this.responseResult = responseResult;
    }
}
