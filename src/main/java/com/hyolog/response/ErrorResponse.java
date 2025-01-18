package com.hyolog.response;


/*
*       {
*           "code": "400",
*           "message": "잘못된 요청입니다.",
*       }
* */

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class ErrorResponse {

    private final String code;
    private final String message;
    private final Map<String, String> validation = new HashMap<>();

    public void addValidation(String fieldName, String errorMessage) {
        this.validation.put(fieldName, errorMessage);
    }
}
