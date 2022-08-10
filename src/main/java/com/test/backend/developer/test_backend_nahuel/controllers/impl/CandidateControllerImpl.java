package com.test.backend.developer.test_backend_nahuel.controllers.impl;

import com.test.backend.developer.test_backend_nahuel.controllers.CandidateController;
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
public class CandidateControllerImpl implements CandidateController {
    @Autowired
    private CandidateService candidateService;

    @Override
    @PostMapping("/create")
    public ResponseEntity<HttpStatus> create(@RequestBody CandidateDTO candidateDTO) {
        try {
            candidateService.create(candidateDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (CandidateExistsException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
    }

    @Override
    @PutMapping("/update")
    public ResponseEntity<Boolean> update(@RequestBody CandidateDTO candidateDTO) {
        try {
            return new ResponseEntity<>(candidateService.update(candidateDTO), HttpStatus.OK);
        } catch (CandidateNotExistsException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
    }

    @Override
    @GetMapping("/findByDocument/{candidateDoc}")
    public ResponseEntity<Candidate> findByDocument(@PathVariable String candidateDoc) {
        try {
            return new ResponseEntity<>(candidateService.findByDocument(candidateDoc), HttpStatus.OK);
        } catch (CandidateNotExistsException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
    }

    @Override
    @GetMapping("/")
    public ResponseEntity<List<Candidate>> list() {
        return ResponseEntity.ok(candidateService.findAll());
    }

    @Override
    @DeleteMapping("/delete/{candidateId}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Long candidateId) {
        try {
            candidateService.delete(candidateId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CandidateNotExistsException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
    }
}
