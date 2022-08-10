package com.test.backend.developer.test_backend_nahuel.controllers;

import com.test.backend.developer.test_backend_nahuel.models.entities.CandidateTechnologies;
import com.test.backend.developer.test_backend_nahuel.models.views.CandidateTechnologiesDTO;
import com.test.backend.developer.test_backend_nahuel.projections.CandidateTechnologiesProjection;
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

@Api(tags = "CandidateTechnologies", value = "Create, update and deletion of technologies by candidates")
public interface CandidateTechnologyController {

    @Operation(summary = "Create a new technology by candidate", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation")})

    ResponseEntity<HttpStatus> create(@RequestBody CandidateTechnologiesDTO candidateTechnologiesDTO);

    @Operation(summary = "Get all technologies by candidates", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = CandidateTechnologies.class)))})
    ResponseEntity<List<CandidateTechnologies>> findAll();

    @Operation(summary = "Get a technology by candidate by document", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = CandidateTechnologies.class))),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
            @ApiResponse(responseCode = "404", description = "Technology by candidate not found")})
    ResponseEntity<CandidateTechnologies> findById(@PathVariable Long candidateTechnologiesId);

    @Operation(summary = "Deletes a technology by candidate by id", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
            @ApiResponse(responseCode = "404", description = "Technology by candidate not found")})
    ResponseEntity<HttpStatus> deleteById(@PathVariable Long candidateTechnologiesId);

    @Operation(summary = "Get a list of candidates", description = "Gets a list of candidates from the name of a technology",
            responses = {@ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = CandidateTechnologies.class)))})
    ResponseEntity<List<CandidateTechnologiesProjection>> listCandidateByTechnology(@PathVariable String technologyName);
}
