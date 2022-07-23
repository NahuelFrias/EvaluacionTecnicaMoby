package com.test.backend.developer.test_backend_nahuel.exceptions;

public class CandidateTechnologiesExistsException extends RuntimeException{
    public CandidateTechnologiesExistsException(String msg){
        super(msg);
    }
}
