package com.test.backend.developer.test_backend_nahuel.controllers;

import com.test.backend.developer.test_backend_nahuel.services.TechnologyService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.google.gson.Gson;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.test.backend.developer.test_backend_nahuel.utils.TestEntityFactory.getTechnology;
import static com.test.backend.developer.test_backend_nahuel.utils.TestEntityFactory.getTechnologyDTO;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static com.test.backend.developer.test_backend_nahuel.utils.TestEntityFactory.getTechnologyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TechnologyControllerTests extends AbstractMVCTest {

    @Mock
    TechnologyService technologyService;

    @Autowired
    MockMvc mockMvc;

    @Disabled
    @Test
    void createTest() throws Exception {
        var technologyDto = getTechnologyDTO();
        when(technologyService.create(technologyDto)).thenReturn(true);
        String technologyDtoJson = new Gson().toJson(technologyDto);
        mockMvc.perform(post("/ev-tec/technology/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(technologyDtoJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void updateTest() throws Exception {
        var technologyDTO = getTechnologyDTO();
        when(technologyService.update(technologyDTO)).thenReturn(true);
        String candidateDtoJson = new Gson().toJson(technologyDTO);
        mockMvc.perform(post("/ev-tec/technology/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(candidateDtoJson))
                .andExpect(status().isCreated());
    }

    @Test
    void findAllTest() throws Exception {
        var technologies = getTechnologyList();
        when(technologyService.findAll()).thenReturn(technologies);
        mockMvc.perform(get("/ev-tec/technology/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void findById() throws Exception {
        mockMvc.perform(get("/ev-tec/technology/{technologyId}", getTechnology().getId()))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError());
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(delete("/ev-tec/technology/delete/{technologyId}", getTechnologyDTO().getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError());
    }
}
