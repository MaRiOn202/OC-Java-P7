package com.openclassrooms.poseidon.exception;

public class AccessDeniedException extends RuntimeException{

    public AccessDeniedException(String message) {

        super(message);
    }

    public AccessDeniedException(String message, Throwable cause) {

        super(message, cause);

    }


}
