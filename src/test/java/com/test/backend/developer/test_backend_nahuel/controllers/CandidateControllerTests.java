package com.test.backend.developer.test_backend_nahuel.controllers;

import com.test.backend.developer.test_backend_nahuel.repositories.CandidateRepository;
import com.test.backend.developer.test_backend_nahuel.services.impl.CandidateServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.google.gson.Gson;

import java.util.Optional;

import static com.test.backend.developer.test_backend_nahuel.utils.TestEntityFactory.getCandidateList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static com.test.backend.developer.test_backend_nahuel.utils.TestEntityFactory.getCandidate;
import static com.test.backend.developer.test_backend_nahuel.utils.TestEntityFactory.getCandidateDTO;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class CandidateControllerTest extends AbstractMVCTest {

    @Autowired
    MockMvc mockMvc;

    @Mock
    CandidateServiceImpl candidateService;

    @Mock
    CandidateRepository candidateRepository;

    @Test
    void createTest() throws Exception {
        var candidateDto = getCandidateDTO();
        when(candidateService.create(candidateDto)).thenReturn(true);
        String candidateDtoJson = new Gson().toJson(candidateDto);
        mockMvc.perform(post("/ev-tec/candidate/create").contentType(MediaType.APPLICATION_JSON).content(candidateDtoJson)).andExpect(status().isCreated());

    }

    @Test
    void updateTest() throws Exception {
        var candidateDto = getCandidateDTO();
        when(candidateService.update(candidateDto)).thenReturn(true);
        String candidateDtoJson = new Gson().toJson(candidateDto);
        mockMvc.perform(post("/ev-tec/candidate/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(candidateDtoJson))
                .andExpect(status().isCreated());
    }

    @Test
    void listTest() throws Exception {
        var candidateList = getCandidateList();
        when(candidateService.findAll()).thenReturn(candidateList);
        mockMvc.perform(get("/ev-tec/candidate/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void findByDocument() throws Exception {
        var candidate = getCandidate();
        when(candidateService.findByDocument(getCandidate().getNumDocument())).thenReturn(candidate);
        mockMvc.perform(get("/ev-tec/candidate/findByDocument/12345678"))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void deleteByIdTest() throws Exception {
        var candidate = getCandidate();
        when(candidateRepository.findById(getCandidate().getId())).thenReturn(Optional.ofNullable(candidate));
        mockMvc.perform(delete("/ev-tec/candidate/delete/1"))
                .andExpect(status().is5xxServerError());
    }

}
