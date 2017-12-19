package com.moler.technicaltask.util;

import lombok.Getter;

@Getter
public class CustomErrorType {

    private String errorMessage;

    public CustomErrorType(String errorMessage){
        this.errorMessage = errorMessage;
    }


}
