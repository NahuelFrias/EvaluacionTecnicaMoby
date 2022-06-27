package com.test.backend.developer.test_backend_nahuel.controllers;

import com.test.backend.developer.test_backend_nahuel.exceptions.TechnologyExistsException;
import com.test.backend.developer.test_backend_nahuel.exceptions.TechnologyNotExistsException;
import com.test.backend.developer.test_backend_nahuel.models.entities.Technology;
import com.test.backend.developer.test_backend_nahuel.models.views.TechnologyDTO;
import com.test.backend.developer.test_backend_nahuel.services.TechnologyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RestController(value = "ev-tec/technology")
public class TechnologyController {

    @Autowired
    TechnologyService technologyService;
    @PostMapping(value = "/create")
    public ResponseEntity<Boolean> create (@RequestBody TechnologyDTO technologyDTO) throws TechnologyExistsException {
        return new ResponseEntity<>(technologyService.create(technologyDTO), HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity<Boolean> update (@RequestBody TechnologyDTO technologyDTO) throws TechnologyNotExistsException {
        return new ResponseEntity<>(technologyService.update(technologyDTO), HttpStatus.OK);
    }

    @GetMapping("/{technologyId}")
    public ResponseEntity<Technology> findById (@PathVariable Long techId) throws TechnologyNotExistsException {
        return ResponseEntity.ok(technologyService.findById(techId));
    }

    @GetMapping("/listTechnology")
    public ResponseEntity<List<Technology>> list() {
        return new ResponseEntity<>(technologyService.findAll(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{technologyId}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Long techId) throws TechnologyNotExistsException {
        technologyService.delete(techId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
