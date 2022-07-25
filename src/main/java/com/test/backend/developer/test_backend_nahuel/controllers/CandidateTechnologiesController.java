package com.test.backend.developer.test_backend_nahuel.controllers;

import com.test.backend.developer.test_backend_nahuel.exceptions.CandidateExistsException;
import com.test.backend.developer.test_backend_nahuel.exceptions.CandidateNotExistsException;
import com.test.backend.developer.test_backend_nahuel.exceptions.CandidateTechnologiesExistsException;
import com.test.backend.developer.test_backend_nahuel.exceptions.TechnologyNotExistsException;
import com.test.backend.developer.test_backend_nahuel.models.entities.Candidate;
import com.test.backend.developer.test_backend_nahuel.models.entities.CandidateTechnologies;
import com.test.backend.developer.test_backend_nahuel.models.views.CandidateTechnologiesDTO;
import com.test.backend.developer.test_backend_nahuel.projections.CandidateTechnologiesProjection;
import com.test.backend.developer.test_backend_nahuel.services.CandidateTechnologiesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "CandidateTechnologies", description = "Create, update and deletion of technologies by candidates")
@RequestMapping(value = "ev-tec/candidate-technologies")
public class CandidateTechnologiesController {

    @Autowired
    CandidateTechnologiesService candidateTechnologiesService;

    @Operation(summary = "Create a new technology by candidate", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation")})
    @PostMapping("/create")
    public ResponseEntity<HttpStatus> create(@RequestBody CandidateTechnologiesDTO candidateTechnologiesDTO) {
        try {
            candidateTechnologiesService.create(candidateTechnologiesDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (CandidateExistsException | CandidateTechnologiesExistsException e) {
            log.error("The candidate technology could not be created correctly.");
            throw new RuntimeException(e.getMessage());
        }
    }

    @Operation(summary = "Get all technologies by candidates", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = CandidateTechnologies.class)))})
    @GetMapping("/")
    public ResponseEntity<List<CandidateTechnologies>> findAll() {
        return ResponseEntity.ok(candidateTechnologiesService.findAll());
    }

    @Operation(summary = "Get a technology by candidate by document", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = CandidateTechnologies.class))),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
            @ApiResponse(responseCode = "404", description = "Technology by candidate not found")})
    @GetMapping("/findById/{candidateTecnologiesId}")
    public ResponseEntity<CandidateTechnologies> findById(@PathVariable Long candidateTechnologiesId) {
        try {
            return new ResponseEntity<>(candidateTechnologiesService.findById(candidateTechnologiesId), HttpStatus.OK);
        } catch (TechnologyNotExistsException e) {
            log.error("Technology by candidate in the found.");
            throw new RuntimeException(e.getMessage());
        }
    }

    @Operation(summary = "Deletes a technology by candidate by id", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
            @ApiResponse(responseCode = "404", description = "Technology by candidate not found")})
    @DeleteMapping("/delete/{candidateTechnologiesId}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Long id) {
        try {
            candidateTechnologiesService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CandidateNotExistsException | TechnologyNotExistsException e) {
            log.error("Failed to delete correctly.");
            throw new RuntimeException(e.getMessage());
        }
    }

    @Operation(summary = "Get a list of candidates", description = "Gets a list of candidates from the name of a technology",
            responses = { @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = CandidateTechnologies.class)))})
    @GetMapping("/listCandidateByTechnology/{technologyName}")
    public ResponseEntity<List<CandidateTechnologiesProjection>> listCandidateByTechnology(@PathVariable String technology) {
        try {
            return new ResponseEntity<>(candidateTechnologiesService.listCandidateByTechnology(technology), HttpStatus.OK);
        } catch (TechnologyNotExistsException e) {
            log.error("Technology not found");
            throw new RuntimeException(e.getMessage());
        }
    }
}
