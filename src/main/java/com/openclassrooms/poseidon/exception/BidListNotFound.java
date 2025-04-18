package com.openclassrooms.poseidon.exception;

public class BidListNotFound extends RuntimeException {

    public BidListNotFound(String message) {

        super(message);
    }

    public BidListNotFound(String message, Throwable cause) {

        super(message, cause);

    }


}
