package com.test.backend.developer.test_backend_nahuel.exceptions.handler;

import com.test.backend.developer.test_backend_nahuel.exceptions.CandidateExistsException;
import com.test.backend.developer.test_backend_nahuel.exceptions.CandidateNotExistsException;
import com.test.backend.developer.test_backend_nahuel.exceptions.CandidateTechnologiesExistsException;
import com.test.backend.developer.test_backend_nahuel.exceptions.TechnologyExistsException;
import com.test.backend.developer.test_backend_nahuel.exceptions.TechnologyNotExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CandidateExistsException.class)
    public ResponseEntity<String> candidateExistsHandler() {
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @ExceptionHandler(CandidateNotExistsException.class)
    public ResponseEntity<String> candidateNotExistsHandler() {
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @ExceptionHandler(CandidateTechnologiesExistsException.class)
    public ResponseEntity<String> candidateTechnologiesExistsHandler() {
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @ExceptionHandler(TechnologyExistsException.class)
    public ResponseEntity<String> technologyExistsHandler() {
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @ExceptionHandler(TechnologyNotExistsException.class)
    public ResponseEntity<String> technologyNotExistsHandler() {
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
