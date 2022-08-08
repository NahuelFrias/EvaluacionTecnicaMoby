package com.test.backend.developer.test_backend_nahuel.controllers;

import com.google.gson.Gson;
import com.test.backend.developer.test_backend_nahuel.services.impl.CandidateTechnologiesServicesImpl;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.test.backend.developer.test_backend_nahuel.utils.TestEntityFactory.getCandidateTechnologies;
import static com.test.backend.developer.test_backend_nahuel.utils.TestEntityFactory.getCandidateTechnologiesDTO;
import static com.test.backend.developer.test_backend_nahuel.utils.TestEntityFactory.getCandidateByTechnologyList;
import static com.test.backend.developer.test_backend_nahuel.utils.TestEntityFactory.getTechnology;
import static com.test.backend.developer.test_backend_nahuel.utils.TestEntityFactory.getTechnologyDTO;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class CandidateTechnologyControllerTests extends AbstractMVCTest{

    @Mock
    CandidateTechnologiesServicesImpl candidateTechnologiesService;

    @Disabled
    @Test
    void createTest() throws Exception {
        var candidateTechnologiesDTO = getCandidateTechnologiesDTO();
        when(candidateTechnologiesService.create(candidateTechnologiesDTO)).thenReturn(true);
        String candidateByTechnologyJson = new Gson().toJson(candidateTechnologiesDTO);
        mockMvc.perform(post("/ev-tec/candidate-technologies/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(candidateByTechnologyJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void finAllTest() throws Exception {
        var candidateByTechnologies = getCandidateByTechnologyList();
        when(candidateTechnologiesService.findAll()).thenReturn(candidateByTechnologies);
        mockMvc.perform(get("/ev-tec/candidate-technologies/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void findByIdTest() throws Exception {
        mockMvc.perform(get("/ev-tec/candidate-technologies/findById/{candidateTecnologiesId}", getCandidateTechnologies().getId()))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError());
    }

    @Test
    void listCandidateByTecnologyTest() throws Exception {
        mockMvc.perform(get("/ev-tec/candidate-technologies/candidateByTechnologyName/{technologyName}", getTechnology().getName()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(delete("/ev-tec/candidate-technologies/delete/{candidateTechnologiesId}", getTechnologyDTO().getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError());
    }
}
