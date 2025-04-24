package com.openclassrooms.poseidon.exception;

public class BidNotFoundException extends RuntimeException {

    public BidNotFoundException(String message) {

        super(message);
    }

    public BidNotFoundException(String message, Throwable cause) {

        super(message, cause);

    }


}
