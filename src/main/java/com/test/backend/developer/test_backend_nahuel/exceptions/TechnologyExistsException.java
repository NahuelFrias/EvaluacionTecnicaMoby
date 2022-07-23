package com.test.backend.developer.test_backend_nahuel.exceptions;

public class TechnologyExistsException extends RuntimeException{
    public TechnologyExistsException (String msg){
        super(msg);
    }
}
