package com.test.backend.developer.test_backend_nahuel.controllers;

import com.test.backend.developer.test_backend_nahuel.exceptions.CandidateDocException;
import com.test.backend.developer.test_backend_nahuel.exceptions.CandidateExistsException;
import com.test.backend.developer.test_backend_nahuel.exceptions.CandidateNotExistsException;
import com.test.backend.developer.test_backend_nahuel.models.entities.Candidate;
import com.test.backend.developer.test_backend_nahuel.models.views.CandidateDTO;
import com.test.backend.developer.test_backend_nahuel.services.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "ev-tec/candidate")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @PostMapping("/create")
    public ResponseEntity<Boolean> create (@RequestBody CandidateDTO candidateDTO) throws CandidateExistsException {
        return new ResponseEntity<>(candidateService.create(candidateDTO), HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity<Boolean> update (@RequestBody CandidateDTO candidateDTO) throws CandidateNotExistsException {
        return new ResponseEntity<>(candidateService.update(candidateDTO), HttpStatus.OK);
    }

    @GetMapping("/findByDocument/{candidateDoc}")
    public ResponseEntity<Candidate> findByDocument (@PathVariable String document) throws CandidateDocException, CandidateNotExistsException {
        return new ResponseEntity<>(candidateService.findByDocument(document), HttpStatus.OK);
    }

    @GetMapping("/listCandidate")
    public ResponseEntity<List<Candidate>> list(){
        return ResponseEntity.ok(candidateService.findAll());
    }

    @DeleteMapping("/delete/{candidateId}")
    public ResponseEntity<HttpStatus> deleteById (@PathVariable Long id) throws CandidateNotExistsException {
        candidateService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
