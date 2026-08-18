package com.duoc.banco.exception;

public class MovimientoCuentaNoValidoException extends RuntimeException {

    public MovimientoCuentaNoValidoException(String message) {
        super(message);
    }

    public MovimientoCuentaNoValidoException(String message, Throwable cause) {
        super(message, cause);
    }

}
