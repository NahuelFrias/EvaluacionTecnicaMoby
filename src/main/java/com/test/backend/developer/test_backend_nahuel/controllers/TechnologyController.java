package com.test.backend.developer.test_backend_nahuel.controllers;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "ev-tec/technology")
public class TechnologyController {

    @Autowired
    TechnologyService technologyService;
    @PostMapping(value = "/create")
    public ResponseEntity<HttpStatus> create (@RequestBody TechnologyDTO technologyDTO){
        try {
            technologyService.create(technologyDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (TechnologyExistsException e) {
            log.error("Technology could not be created correctly.");
            throw new RuntimeException(e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Boolean> update (@RequestBody TechnologyDTO technologyDTO){
        try {
            return new ResponseEntity<>(technologyService.update(technologyDTO), HttpStatus.OK);
        } catch (TechnologyNotExistsException e) {
            log.error("Could not update correctly.");
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/{technologyId}")
    public ResponseEntity<Technology> findById (@PathVariable Long techId){
        try {
            return ResponseEntity.ok(technologyService.findById(techId));
        } catch (TechnologyNotExistsException e) {
            log.error("Technology not found.");
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<Technology>> list() {
        return new ResponseEntity<>(technologyService.findAll(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{technologyId}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Long techId) {
        try {
            technologyService.delete(techId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (TechnologyNotExistsException e) {
            log.error("Failed to delete correctly.");
            throw new RuntimeException(e.getMessage());
        }
    }
}
