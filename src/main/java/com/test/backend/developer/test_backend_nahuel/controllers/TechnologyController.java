package com.test.backend.developer.test_backend_nahuel.controllers;

import com.test.backend.developer.test_backend_nahuel.exceptions.TechnologyExistsException;
import com.test.backend.developer.test_backend_nahuel.exceptions.TechnologyNotExistsException;
import com.test.backend.developer.test_backend_nahuel.models.entities.Technology;
import com.test.backend.developer.test_backend_nahuel.models.views.TechnologyDTO;
import com.test.backend.developer.test_backend_nahuel.services.TechnologyService;
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
@Api(tags = "Technology", value = "Create, update and deletion of technologies")
@RequestMapping(value = "ev-tec/technology")
public class TechnologyController {

    @Autowired
    TechnologyService technologyService;

    @Operation(summary = "Create a new technology", description = "technology creation", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation")})
    @PostMapping(value = "/create")
    public ResponseEntity<HttpStatus> create(@RequestBody TechnologyDTO technologyDTO) {
        try {
            technologyService.create(technologyDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (TechnologyExistsException e) {
            log.error("Technology could not be created correctly.", e.getMessage());
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
    }

    @Operation(summary = "Technology update", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Could not update correctly")})
    @PutMapping("/update")
    public ResponseEntity<Boolean> update(@RequestBody TechnologyDTO technologyDTO) {
        try {
            return new ResponseEntity<>(technologyService.update(technologyDTO), HttpStatus.OK);
        } catch (TechnologyNotExistsException e) {
            log.error("Could not update correctly.", e.getMessage());
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
    }

    @Operation(summary = "Get a technology by id", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = Technology.class))),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
            @ApiResponse(responseCode = "404", description = "Technology not found")})
    @GetMapping("/{technologyId}")
    public ResponseEntity<Technology> findById(@PathVariable Long technologyId) {
        try {
            return ResponseEntity.ok(technologyService.findById(technologyId));
        } catch (TechnologyNotExistsException e) {
            log.error("Technology not found.", e.getMessage());
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
    }

    @Operation(summary = "Get all technologies", description = "Returns all technologies", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = Technology.class)))})
    @GetMapping("/")
    public ResponseEntity<List<Technology>> list() {
        return new ResponseEntity<>(technologyService.findAll(), HttpStatus.OK);
    }

    @Operation(summary = "Deletes a technology by id", description = "Delete a technology", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
            @ApiResponse(responseCode = "404", description = "Technology not found")}
    )
    @DeleteMapping("/delete/{technologyId}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Long technologyId) {
        try {
            technologyService.delete(technologyId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (TechnologyNotExistsException e) {
            log.error("Failed to delete correctly.", e.getMessage());
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
    }
}
