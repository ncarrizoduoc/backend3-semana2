package com.duoc.banco.exception;

public class TransaccionNoValidaException extends RuntimeException {

    public TransaccionNoValidaException(String message) {
        super(message);
    }

    public TransaccionNoValidaException(String message, Throwable cause) {
        super(message, cause);
    }

}
