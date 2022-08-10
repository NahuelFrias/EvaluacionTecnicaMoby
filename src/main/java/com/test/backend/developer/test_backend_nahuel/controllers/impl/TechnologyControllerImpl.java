package com.test.backend.developer.test_backend_nahuel.controllers.impl;

import com.test.backend.developer.test_backend_nahuel.controllers.TechnologyController;
import com.test.backend.developer.test_backend_nahuel.exceptions.TechnologyExistsException;
import com.test.backend.developer.test_backend_nahuel.exceptions.TechnologyNotExistsException;
import com.test.backend.developer.test_backend_nahuel.models.entities.Technology;
import com.test.backend.developer.test_backend_nahuel.models.views.TechnologyDTO;
import com.test.backend.developer.test_backend_nahuel.services.TechnologyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "ev-tec/technology")
public class TechnologyControllerImpl implements TechnologyController {

    @Autowired
    TechnologyService technologyService;

    @Override
    @PostMapping(value = "/create")
    public ResponseEntity<HttpStatus> create(TechnologyDTO technologyDTO) {
        try {
            technologyService.create(technologyDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (TechnologyExistsException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
    }

    @Override
    @PutMapping("/update")
    public ResponseEntity<Boolean> update(TechnologyDTO technologyDTO) {
        try {
            return new ResponseEntity<>(technologyService.update(technologyDTO), HttpStatus.OK);
        } catch (TechnologyNotExistsException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
    }

    @Override
    @GetMapping("/{technologyId}")
    public ResponseEntity<Technology> findById(Long technologyId) {
        try {
            return ResponseEntity.ok(technologyService.findById(technologyId));
        } catch (TechnologyNotExistsException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
    }

    @Override
    @GetMapping("/")
    public ResponseEntity<List<Technology>> list() {
        return new ResponseEntity<>(technologyService.findAll(), HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/delete/{technologyId}")
    public ResponseEntity<HttpStatus> deleteById(Long technologyId) {
        try {
            technologyService.delete(technologyId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (TechnologyNotExistsException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
    }
}
