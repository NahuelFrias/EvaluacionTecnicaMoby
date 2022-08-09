package com.test.backend.developer.test_backend_nahuel.exceptions;

public class CandidateNotExistsException extends RuntimeException {

    public CandidateNotExistsException(String msg) {
        super(msg);
    }
}