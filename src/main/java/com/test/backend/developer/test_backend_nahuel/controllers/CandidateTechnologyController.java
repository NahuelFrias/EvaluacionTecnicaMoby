package com.test.backend.developer.test_backend_nahuel.controllers;

import com.test.backend.developer.test_backend_nahuel.exceptions.CandidateTechnologiesExistsException;
import com.test.backend.developer.test_backend_nahuel.exceptions.TechnologyNotExistsException;
import com.test.backend.developer.test_backend_nahuel.models.entities.CandidateTechnologies;
import com.test.backend.developer.test_backend_nahuel.models.views.CandidateTechnologiesDTO;
import com.test.backend.developer.test_backend_nahuel.projections.CandidateTechnologiesProjection;
import com.test.backend.developer.test_backend_nahuel.services.CandidateTechnologiesService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@Api(tags = "CandidateTechnologies", value = "Create, update and deletion of technologies by candidates")
@RequestMapping(value = "ev-tec/candidate-technologies")
public class CandidateTechnologyController {

    @Autowired
    CandidateTechnologiesService candidateTechnologiesService;

    @Operation(summary = "Create a new technology by candidate", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation")})
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
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
    }

    @Operation(summary = "Deletes a technology by candidate by id", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
            @ApiResponse(responseCode = "404", description = "Technology by candidate not found")})
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

    @Operation(summary = "Get a list of candidates", description = "Gets a list of candidates from the name of a technology",
            responses = {@ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = CandidateTechnologies.class)))})
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
