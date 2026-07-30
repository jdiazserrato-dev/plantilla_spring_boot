package com.jorel.template_api.exceptions;

public class DataBaseNotAvailableException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DataBaseNotAvailableException(String message) {
        super(message);
    }
}
