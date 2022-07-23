package com.test.backend.developer.test_backend_nahuel.controllers;

import com.test.backend.developer.test_backend_nahuel.exceptions.CandidateExistsException;
import com.test.backend.developer.test_backend_nahuel.exceptions.CandidateNotExistsException;
import com.test.backend.developer.test_backend_nahuel.exceptions.CandidateTechnologiesExistsException;
import com.test.backend.developer.test_backend_nahuel.exceptions.TechnologyNotExistsException;
import com.test.backend.developer.test_backend_nahuel.models.entities.CandidateTechnologies;
import com.test.backend.developer.test_backend_nahuel.models.views.CandidateTechnologiesDTO;
import com.test.backend.developer.test_backend_nahuel.services.CandidateTechnologiesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "ev-tec/candidate-technologies")
public class CandidateTechnologiesController {

    @Autowired
    CandidateTechnologiesService candidateTechnologiesService;
    @PostMapping("/create")
    public ResponseEntity<HttpStatus> create (@RequestBody CandidateTechnologiesDTO candidateTechnologiesDTO) {
        try {
            candidateTechnologiesService.create(candidateTechnologiesDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (CandidateExistsException | CandidateTechnologiesExistsException e) {
            log.error("The candidate technology could not be created correctly.");
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<CandidateTechnologies>> findAll() {
        return ResponseEntity.ok(candidateTechnologiesService.findAll());
    }

    @GetMapping("/findById/{candidateTecnologiesId}")
    public ResponseEntity<CandidateTechnologies> findById(@PathVariable Long candidateTechnologiesId) {
        try {
            return new ResponseEntity<>(candidateTechnologiesService.findById(candidateTechnologiesId), HttpStatus.OK);
        } catch (TechnologyNotExistsException e) {
            log.error("Technology by candidate in the found.");
            throw new RuntimeException(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{candidateTechnologiesId}")
    public ResponseEntity<HttpStatus> deleteById (@PathVariable Long id) {
        try {
            candidateTechnologiesService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CandidateNotExistsException | TechnologyNotExistsException e) {
            log.error("Failed to delete correctly.");
            throw new RuntimeException(e.getMessage());
        }
    }
}
