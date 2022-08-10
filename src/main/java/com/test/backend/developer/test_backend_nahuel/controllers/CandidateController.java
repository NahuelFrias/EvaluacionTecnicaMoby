package com.test.backend.developer.test_backend_nahuel.controllers;

import com.test.backend.developer.test_backend_nahuel.models.entities.Candidate;
import com.test.backend.developer.test_backend_nahuel.models.views.CandidateDTO;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Api(tags = "Candidate", value = "Create, update and deletion of candidates")
public interface CandidateController {

    @Operation(summary = "Create a new candidate", description = "candidate creation", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation")})
    ResponseEntity<HttpStatus> create(@RequestBody CandidateDTO candidateDTO);

    @Operation(summary = "Candidate update", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Could not update correctly")})
    ResponseEntity<Boolean> update(@RequestBody CandidateDTO candidateDTO);

    @Operation(summary = "Get a candidate by document", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = Candidate.class))),
            @ApiResponse(responseCode = "400", description = "Invalid document"),
            @ApiResponse(responseCode = "404", description = "Candidate not found")})
    ResponseEntity<Candidate> findByDocument(@PathVariable String candidateDoc);

    @Operation(summary = "Get all candidates", description = "Returns all candidates", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = Candidate.class)))})
    ResponseEntity<List<Candidate>> list();

    @Operation(summary = "Deletes a candidate by id", description = "Delete a candidate", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
            @ApiResponse(responseCode = "404", description = "Candidate not found")}
    )
    ResponseEntity<HttpStatus> deleteById(@PathVariable Long candidateId);
}
