package com.openclassrooms.poseidon.exception;

public class CurvePointNotFoundException extends RuntimeException{


    public CurvePointNotFoundException(String message) {

        super(message);
    }

    public CurvePointNotFoundException(String message, Throwable cause) {

        super(message, cause);

    }

}
