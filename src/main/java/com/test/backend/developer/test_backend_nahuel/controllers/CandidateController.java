package com.test.backend.developer.test_backend_nahuel.controllers;

import com.test.backend.developer.test_backend_nahuel.exceptions.CandidateDocException;
import com.test.backend.developer.test_backend_nahuel.exceptions.CandidateExistsException;
import com.test.backend.developer.test_backend_nahuel.exceptions.CandidateNotExistsException;
import com.test.backend.developer.test_backend_nahuel.models.entities.Candidate;
import com.test.backend.developer.test_backend_nahuel.models.views.CandidateDTO;
import com.test.backend.developer.test_backend_nahuel.services.CandidateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "ev-tec/candidate")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @PostMapping("/create")
    public ResponseEntity<HttpStatus> create (@RequestBody CandidateDTO candidateDTO) {
        try {
            candidateService.create(candidateDTO);
            return new ResponseEntity<>( HttpStatus.CREATED);
        } catch (CandidateExistsException e) {
            log.error("The candidate could not be created.");
            throw new RuntimeException(e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Boolean> update (@RequestBody CandidateDTO candidateDTO){
        try {
            return new ResponseEntity<>(candidateService.update(candidateDTO), HttpStatus.OK);
        } catch (CandidateNotExistsException e) {
            log.error("Could not update correctly.");
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/findByDocument/{candidateDoc}")
    public ResponseEntity<Candidate> findByDocument (@PathVariable String document) {
        try {
            return new ResponseEntity<>(candidateService.findByDocument(document), HttpStatus.OK);
        } catch (CandidateNotExistsException | CandidateDocException e) {
            log.error("Document not found.");
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<Candidate>> list(){
        return ResponseEntity.ok(candidateService.findAll());
    }

    @DeleteMapping("/delete/{candidateId}")
    public ResponseEntity<HttpStatus> deleteById (@PathVariable Long id){
        try {
            candidateService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CandidateNotExistsException e) {
            log.error("Failed to delete correctly.");
            throw new RuntimeException(e.getMessage());
        }
    }
}
