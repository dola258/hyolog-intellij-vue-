package com.hyolog.exception;

/*
*   status - 400
* */

import lombok.Data;
import lombok.Getter;

@Getter
public class InvalidRequest extends HyologException{

    private static final String MESSAGE = "잘못된 요청입니다.";

    public InvalidRequest() {
        super(MESSAGE);
    }

    public InvalidRequest(String filedName, String message) {
        super(MESSAGE);
        addvalidation(filedName, message);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
