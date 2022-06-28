package com.test.backend.developer.test_backend_nahuel.controllers;

import com.test.backend.developer.test_backend_nahuel.exceptions.CandidateExistsException;
import com.test.backend.developer.test_backend_nahuel.exceptions.CandidateNotExistsException;
import com.test.backend.developer.test_backend_nahuel.exceptions.CandidateTechnologiesExistsException;
import com.test.backend.developer.test_backend_nahuel.exceptions.TechnologyNotExistsException;
import com.test.backend.developer.test_backend_nahuel.models.entities.CandidateTechnologies;
import com.test.backend.developer.test_backend_nahuel.models.views.CandidateTechnologiesDTO;
import com.test.backend.developer.test_backend_nahuel.services.CandidateTechnologiesService;
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


@RestController
@RequestMapping(value = "ev-tec/candidate-technologies")
public class CandidateTechnologiesController {

    @Autowired
    CandidateTechnologiesService candidateTechnologiesService;
    @PostMapping("/create")
    public ResponseEntity<Boolean> create (@RequestBody CandidateTechnologiesDTO candidateTechnologiesDTO) throws CandidateTechnologiesExistsException, CandidateExistsException {
        return new ResponseEntity<>(candidateTechnologiesService.create(candidateTechnologiesDTO), HttpStatus.CREATED);
    }

    @GetMapping("/listCandidateTechnologies")
    public ResponseEntity<List<CandidateTechnologies>> findAll() {
        return ResponseEntity.ok(candidateTechnologiesService.findAll());
    }

    @GetMapping("/findById/{candidateTecnologiesId}")
    public ResponseEntity<CandidateTechnologies> findById(@PathVariable Long candidateTechnologiesId) throws TechnologyNotExistsException {
        return new ResponseEntity<>(candidateTechnologiesService.findById(candidateTechnologiesId), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{candidateTechnologiesId}")
    public ResponseEntity<HttpStatus> deleteById (@PathVariable Long id) throws TechnologyNotExistsException, CandidateNotExistsException {
        candidateTechnologiesService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
