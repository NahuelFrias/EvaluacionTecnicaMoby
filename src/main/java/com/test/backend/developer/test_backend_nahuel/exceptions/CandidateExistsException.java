package com.test.backend.developer.test_backend_nahuel.exceptions;

public class CandidateExistsException extends RuntimeException {

    public CandidateExistsException(String msg) {
        super(msg);
    }
}