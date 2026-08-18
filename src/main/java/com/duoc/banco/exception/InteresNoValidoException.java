package com.duoc.banco.exception;

public class InteresNoValidoException extends RuntimeException {

    public InteresNoValidoException(String message) {
        super(message);
    }

    public InteresNoValidoException(String message, Throwable cause) {
        super(message, cause);
    }

}
