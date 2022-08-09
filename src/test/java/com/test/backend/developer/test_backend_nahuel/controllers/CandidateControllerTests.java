package com.test.backend.developer.test_backend_nahuel.controllers;

import com.test.backend.developer.test_backend_nahuel.exceptions.CandidateExistsException;
import com.test.backend.developer.test_backend_nahuel.exceptions.CandidateNotExistsException;
import com.test.backend.developer.test_backend_nahuel.models.views.CandidateDTO;
import com.test.backend.developer.test_backend_nahuel.repositories.CandidateRepository;
import com.test.backend.developer.test_backend_nahuel.services.impl.CandidateServiceImpl;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.google.gson.Gson;

import java.util.Optional;

import static com.test.backend.developer.test_backend_nahuel.utils.TestEntityFactory.getCandidateList;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static com.test.backend.developer.test_backend_nahuel.utils.TestEntityFactory.getCandidate;
import static com.test.backend.developer.test_backend_nahuel.utils.TestEntityFactory.getCandidateDTO;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class CandidateControllerTests extends AbstractMVCTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CandidateServiceImpl candidateService;

    @MockBean
    CandidateRepository candidateRepository;


    @Nested
    class createTest {
        @Test
        void createTestOk() throws Exception {
            String candidateDto = new Gson().toJson(getCandidateDTO());
            mockMvc.perform(post("/ev-tec/candidate/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(candidateDto))
                    .andExpect(status().isCreated());
            verify(candidateService, atLeastOnce()).create(any(CandidateDTO.class));
        }

        @Test
        void createCandidateAlreadyExistTest() throws Exception {
            doThrow(CandidateExistsException.class).when(candidateService).create(getCandidateDTO());
            String candidateDto = new Gson().toJson(getCandidateDTO());
            mockMvc.perform(post("/ev-tec/candidate/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(candidateDto))
                    .andExpect(status().isAccepted())
                    .andExpect(result -> assertThrows(CandidateExistsException.class, () -> candidateService.create(getCandidateDTO())));
            verify(candidateService, atLeastOnce()).create(any(CandidateDTO.class));
        }
    }

    @Nested
    class FindByDocumentTest {
        @Test
        void findByDocumentOkTest() throws Exception {
            var candidate = getCandidate();
            String document = new Gson().toJson(12345678);
            when(candidateService.findByDocument(getCandidate().getNumDocument())).thenReturn(candidate);
            mockMvc.perform(get("/ev-tec/candidate/findByDocument/{document}", document)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(document))
                    .andExpect(status().isOk());
            verify(candidateService, times(1)).findByDocument(document);
        }

        @Test
        void findByDocumentCandidateNotFoundTest() throws Exception {
            String document = "99999999";
            var candidate = getCandidate();
            doThrow(CandidateNotExistsException.class).when(candidateService).findByDocument(document);
            String documentJson = new Gson().toJson(99999999);
            when(candidateService.findByDocument(getCandidate().getNumDocument())).thenReturn(candidate);
            mockMvc.perform(get("/ev-tec/candidate/findByDocument/{document}", document)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(document))
                    .andExpect(status().isAccepted())
                    .andExpect(result -> assertThrows(CandidateNotExistsException.class, () -> candidateService.findByDocument(documentJson)));
            verify(candidateService, atLeastOnce()).findByDocument(documentJson);
        }
    }

    @Test
    void listTest() throws Exception {
        var candidateList = getCandidateList();
        when(candidateService.findAll()).thenReturn(candidateList);
        mockMvc.perform(get("/ev-tec/candidate/"))
                .andExpect(status().isOk());
    }

    @Nested
    class DeleteTest{
        @Test
        void deleteOkTest() throws Exception {
            var candidateId = getCandidate().getId();
            var candidate = getCandidate();
            var candidateIdJson = new Gson().toJson(getCandidate().getId());
            when(candidateRepository.findById(candidateId)).thenReturn(Optional.ofNullable(candidate));
            mockMvc.perform(delete("/ev-tec/candidate/delete/{candidateId}", candidateId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(candidateIdJson))
                    .andExpect(status().isOk());
            verify(candidateService,atLeastOnce()).delete(candidateId);
        }
        @Test
        void deleteCandidateNotExistsTest() throws Exception {
            var candidateId = getCandidate().getId();
            var candidate = getCandidate();
            var candidateIdJson = new Gson().toJson(getCandidate().getId());
            doThrow(CandidateNotExistsException.class).when(candidateService).delete(candidateId);
            when(candidateRepository.findById(candidateId)).thenReturn(Optional.ofNullable(candidate));
            mockMvc.perform(delete("/ev-tec/candidate/delete/{candidateId}", candidateId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(candidateIdJson))
                    .andExpect(status().isAccepted())
                    .andExpect(result -> assertThrows(CandidateNotExistsException.class, () -> candidateService.delete(candidateId)));
            verify(candidateService,atLeastOnce()).delete(candidateId);
        }
    }

}
