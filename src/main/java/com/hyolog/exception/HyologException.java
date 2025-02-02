package com.hyolog.exception;

import java.util.HashMap;
import java.util.Map;

public abstract class HyologException extends RuntimeException {

    public final Map<String, String> validation = new HashMap<>();

    public HyologException(String message) {
        super(message);
    }

    public HyologException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int getStatusCode();

    public void addvalidation(String fieldName, String message) {
        validation.put(fieldName, message);
    }
}
