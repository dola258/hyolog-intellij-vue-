package com.hyolog.exception;

public class InvalidRequest extends RuntimeException{

    private static final String MESSAGE = "잘못된 요청입니다.";

    public InvalidRequest() {
        super(MESSAGE);
    }

}
