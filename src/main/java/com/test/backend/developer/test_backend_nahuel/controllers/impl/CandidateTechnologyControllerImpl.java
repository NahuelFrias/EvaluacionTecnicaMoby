package com.test.backend.developer.test_backend_nahuel.controllers.impl;

import com.test.backend.developer.test_backend_nahuel.controllers.CandidateTechnologyController;
import com.test.backend.developer.test_backend_nahuel.exceptions.CandidateTechnologiesExistsException;
import com.test.backend.developer.test_backend_nahuel.exceptions.TechnologyNotExistsException;
import com.test.backend.developer.test_backend_nahuel.models.entities.CandidateTechnologies;
import com.test.backend.developer.test_backend_nahuel.models.views.CandidateTechnologiesDTO;
import com.test.backend.developer.test_backend_nahuel.projections.CandidateTechnologiesProjection;
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


public class CandidateTechnologyControllerImpl implements CandidateTechnologyController {

    @Autowired
    CandidateTechnologiesService candidateTechnologiesService;

    @Override
    @PostMapping("/create")
    public ResponseEntity<HttpStatus> create(@RequestBody CandidateTechnologiesDTO candidateTechnologiesDTO) {
        try {
            candidateTechnologiesService.create(candidateTechnologiesDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (CandidateTechnologiesExistsException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
    }

    @Override
    @GetMapping("/")
    public ResponseEntity<List<CandidateTechnologies>> findAll() {
        return ResponseEntity.ok(candidateTechnologiesService.findAll());
    }

    @Override
    @GetMapping("/findById/{candidateTechnologiesId}")
    public ResponseEntity<CandidateTechnologies> findById(@PathVariable Long candidateTechnologiesId) {
        try {
            return new ResponseEntity<>(candidateTechnologiesService.findById(candidateTechnologiesId), HttpStatus.OK);
        } catch (TechnologyNotExistsException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
    }

    @Override
    @DeleteMapping("/delete/{candidateTechnologiesId}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Long candidateTechnologiesId) {
        try {
            candidateTechnologiesService.delete(candidateTechnologiesId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (TechnologyNotExistsException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
    }

    @Override
    @GetMapping("/listCandidateByTechnology/{technologyName}")
    public ResponseEntity<List<CandidateTechnologiesProjection>> listCandidateByTechnology(@PathVariable String technologyName) {
        try {
            return new ResponseEntity<>(candidateTechnologiesService.listCandidateByTechnology(technologyName), HttpStatus.OK);
        } catch (TechnologyNotExistsException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
    }
}
