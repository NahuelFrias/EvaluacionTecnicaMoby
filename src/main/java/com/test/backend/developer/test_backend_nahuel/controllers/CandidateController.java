package com.test.backend.developer.test_backend_nahuel.controllers;

import com.test.backend.developer.test_backend_nahuel.exceptions.CandidateDocException;
import com.test.backend.developer.test_backend_nahuel.exceptions.CandidateExistsException;
import com.test.backend.developer.test_backend_nahuel.exceptions.CandidateNotExistsException;
import com.test.backend.developer.test_backend_nahuel.models.entities.Candidate;
import com.test.backend.developer.test_backend_nahuel.models.views.CandidateDTO;
import com.test.backend.developer.test_backend_nahuel.services.CandidateService;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@Api(tags = "Candidate", value = "Create, update and deletion of candidates")
@RequestMapping(value = "ev-tec/candidate")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @Operation(summary = "Create a new candidate", description = "candidate creation" ,responses = {
    @ApiResponse(responseCode = "200", description = "Successful operation")})
    @PostMapping("/create")
    public ResponseEntity<HttpStatus> create (@RequestBody CandidateDTO candidateDTO) {
        try {
            candidateService.create(candidateDTO);
            return new ResponseEntity<>( HttpStatus.CREATED);
        } catch (CandidateExistsException e) {
            log.error("The candidate could not be created.");
            throw new RuntimeException(e.getMessage());
        }
    }

    @Operation(summary = "Candidate update",responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Could not update correctly")})
    @PutMapping("/update")
    public ResponseEntity<Boolean> update (@RequestBody CandidateDTO candidateDTO){
        try {
            return new ResponseEntity<>(candidateService.update(candidateDTO), HttpStatus.OK);
        } catch (CandidateNotExistsException e) {
            log.error("Could not update correctly.");
            throw new RuntimeException(e.getMessage());
        }
    }

    @Operation(summary = "Get a candidate by document", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = Candidate.class))),
            @ApiResponse(responseCode = "400", description = "Invalid document"),
            @ApiResponse(responseCode = "404", description = "Candidate not found")})
    @GetMapping("/findByDocument/{candidateDoc}")
    public ResponseEntity<Candidate> findByDocument (@PathVariable String candidateDoc) {
        try {
            return new ResponseEntity<>(candidateService.findByDocument(candidateDoc), HttpStatus.OK);
        } catch (CandidateNotExistsException | CandidateDocException e) {
            log.error("Document not found.");
            throw new RuntimeException(e.getMessage());
        }
    }

    @Operation(summary = "Get all candidates", description = "Returns all candidates", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = Candidate.class)))})
    @GetMapping("/")
    public ResponseEntity<List<Candidate>> list(){
        return ResponseEntity.ok(candidateService.findAll());
    }

    @Operation(summary = "Deletes a candidate by id", description = "Delete a candidate", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
            @ApiResponse(responseCode = "404", description = "Candidate not found") }
    )
    @DeleteMapping("/delete/{candidateId}")
    public ResponseEntity<HttpStatus> deleteById (@PathVariable Long candidateId){
        try {
            candidateService.delete(candidateId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CandidateNotExistsException e) {
            log.error("Failed to delete correctly.");
            throw new RuntimeException(e.getMessage());
        }
    }
}
