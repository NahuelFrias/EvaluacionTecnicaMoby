package com.test.backend.developer.test_backend_nahuel.controllers;

import com.test.backend.developer.test_backend_nahuel.models.entities.Technology;
import com.test.backend.developer.test_backend_nahuel.models.views.TechnologyDTO;
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

@Api(tags = "Technology", value = "Create, update and deletion of technologies")

public interface TechnologyController {

    @Operation(summary = "Create a new technology", description = "technology creation", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation")})
    ResponseEntity<HttpStatus> create(@RequestBody TechnologyDTO technologyDTO);

    @Operation(summary = "Technology update", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Could not update correctly")})
    ResponseEntity<Boolean> update(@RequestBody TechnologyDTO technologyDTO);

    @Operation(summary = "Get a technology by id", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = Technology.class))),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
            @ApiResponse(responseCode = "404", description = "Technology not found")})
    ResponseEntity<Technology> findById(@PathVariable Long technologyId);

    @Operation(summary = "Get all technologies", description = "Returns all technologies", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = Technology.class)))})
    ResponseEntity<List<Technology>> list();

    @Operation(summary = "Deletes a technology by id", description = "Delete a technology", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
            @ApiResponse(responseCode = "404", description = "Technology not found")}
    )
    ResponseEntity<HttpStatus> deleteById(@PathVariable Long technologyId);
}
