package com.hyolog.response;


/*
*       {
*           "code": "400",
*           "message": "잘못된 요청입니다.",
*       }
* */

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ErrorResponse {

    private final String code;
    private final String message;
}
