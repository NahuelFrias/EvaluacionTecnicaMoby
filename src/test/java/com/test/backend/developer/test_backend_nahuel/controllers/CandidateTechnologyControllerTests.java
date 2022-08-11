package com.test.backend.developer.test_backend_nahuel.controllers;

import com.google.gson.Gson;
import com.test.backend.developer.test_backend_nahuel.exceptions.CandidateExistsException;
import com.test.backend.developer.test_backend_nahuel.exceptions.CandidateNotExistsException;
import com.test.backend.developer.test_backend_nahuel.exceptions.CandidateTechnologiesExistsException;
import com.test.backend.developer.test_backend_nahuel.exceptions.TechnologyNotExistsException;
import com.test.backend.developer.test_backend_nahuel.models.views.CandidateDTO;
import com.test.backend.developer.test_backend_nahuel.models.views.CandidateTechnologiesDTO;
import com.test.backend.developer.test_backend_nahuel.repositories.CandidateTechnologiesRepository;
import com.test.backend.developer.test_backend_nahuel.services.impl.CandidateTechnologiesServicesImpl;
import com.test.backend.developer.test_backend_nahuel.services.impl.TechnologyServiceImpl;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static com.test.backend.developer.test_backend_nahuel.utils.TestEntityFactory.getCandidate;
import static com.test.backend.developer.test_backend_nahuel.utils.TestEntityFactory.getCandidateDTO;
import static com.test.backend.developer.test_backend_nahuel.utils.TestEntityFactory.getCandidateList;
import static com.test.backend.developer.test_backend_nahuel.utils.TestEntityFactory.getCandidateTechnologies;
import static com.test.backend.developer.test_backend_nahuel.utils.TestEntityFactory.getCandidateTechnologiesDTO;
import static com.test.backend.developer.test_backend_nahuel.utils.TestEntityFactory.getCandidateByTechnologyList;
import static com.test.backend.developer.test_backend_nahuel.utils.TestEntityFactory.getTechnology;
import static com.test.backend.developer.test_backend_nahuel.utils.TestEntityFactory.getTechnologyDTO;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

class CandidateTechnologyControllerTests extends AbstractMVCTest {

    @MockBean
    CandidateTechnologiesServicesImpl candidateTechnologiesService;

    @MockBean
    CandidateTechnologiesRepository candidateTechnologiesRepository;

    @MockBean
    TechnologyServiceImpl technologyService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void createTest() throws Exception {
        var candidateTechnologiesDto = getCandidateTechnologiesDTO();
        String candidateTechnologiesDtoJson = new Gson().toJson(getCandidateTechnologiesDTO());
        mockMvc.perform(post("/ev-tec/candidate-technologies/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(candidateTechnologiesDtoJson))
                .andExpect(status().isCreated());
        verify(candidateTechnologiesService, atLeastOnce()).create(any(CandidateTechnologiesDTO.class));
    }

    @Test
    void createCandidateTechnologiesAlreadyExistTest() throws Exception {
        doThrow(CandidateTechnologiesExistsException.class).when(candidateTechnologiesService).create(getCandidateTechnologiesDTO());
        String candidateTechnologiesJson = new Gson().toJson(getCandidateTechnologiesDTO());
        var candidateTechnology = getCandidateTechnologiesDTO();
        mockMvc.perform(post("/ev-tec/candidate-technologies/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(candidateTechnologiesJson))
                .andExpect(status().isAccepted())
                .andExpect(result -> assertThrows(CandidateTechnologiesExistsException.class, () -> candidateTechnologiesService.create(candidateTechnology)));
        verify(candidateTechnologiesService, atLeastOnce()).create(any(CandidateTechnologiesDTO.class));
    }

    @Test
    void findAllTest() throws Exception {
        var candidateByTechnologyList = getCandidateByTechnologyList();
        when(candidateTechnologiesService.findAll()).thenReturn(candidateByTechnologyList);
        mockMvc.perform(get("/ev-tec/candidate-technologies/"))
                .andExpect(status().isOk());
    }

    @Test
    void findByIdOkTest() throws Exception {
        var candidateTechnologies = getCandidateTechnologies();
        String idJson = new Gson().toJson(getCandidateTechnologies().getId());
        when(candidateTechnologiesService.findById(getCandidateTechnologies().getId())).thenReturn(candidateTechnologies);
        mockMvc.perform(get("/ev-tec/candidate-technologies/findById/{candidateTechnologiesId}", idJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(idJson))
                .andExpect(status().isOk());
        verify(candidateTechnologiesService, times(1)).findById(candidateTechnologies.getId());
    }

    @Nested
    class DeleteTest {
        @Test
        void deleteOkTest() throws Exception {
            var candidateTechnologiesId = getCandidateTechnologies().getId();
            var candidateTechnologies = getCandidateTechnologies();
            var candidateTechnologiesIdJson = new Gson().toJson(getCandidateTechnologies().getId());
            when(candidateTechnologiesRepository.findById(candidateTechnologiesId)).thenReturn(Optional.ofNullable(candidateTechnologies));
            mockMvc.perform(delete("/ev-tec/candidate-technologies/delete/{candidateTechnologiesId}", candidateTechnologiesId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(candidateTechnologiesIdJson))
                    .andExpect(status().isOk());
            verify(candidateTechnologiesService, atLeastOnce()).delete(candidateTechnologiesId);
        }

        @Test
        void deleteCandidateTechnologiesNotExistsTest() throws Exception {
            var candidateTechnologiesId = getCandidateTechnologies().getId();
            var candidateTechnologies = getCandidateTechnologies();
            var candidateTechnologiesIdJson = new Gson().toJson(getCandidateTechnologies().getId());
            doThrow(TechnologyNotExistsException.class).when(candidateTechnologiesService).delete(candidateTechnologiesId);
            when(candidateTechnologiesRepository.findById(candidateTechnologiesId)).thenReturn(Optional.ofNullable(candidateTechnologies));
            mockMvc.perform(delete("/ev-tec/candidate-technologies/delete/{candidateTechnologiesId}", candidateTechnologiesId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(candidateTechnologiesIdJson))
                    .andExpect(status().isAccepted())
                    .andExpect(result -> assertThrows(TechnologyNotExistsException.class, () -> candidateTechnologiesService.delete(candidateTechnologiesId)));
            verify(candidateTechnologiesService, atLeastOnce()).delete(candidateTechnologiesId);
        }
    }

    @Test
    void findCandidateByTechnologyTest() throws Exception {
        var technologyName = getTechnology().getName();
        String technologyNameJson = new Gson().toJson(getTechnology().getName());
        when(technologyService.findByName(technologyName)).thenReturn(true);
        mockMvc.perform(get("/ev-tec/candidate-technologies/listCandidateByTechnology/{technologyName}", technologyName)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(technologyNameJson))
                .andExpect(status().isOk());
        verify(candidateTechnologiesService, atLeastOnce()).listCandidateByTechnology(technologyName);
    }

    @Test
    void findCandidateByTechnologyNotFoundTest() throws Exception {
        var technologyName = getTechnology().getName();
        String technologyNameJson = new Gson().toJson(getTechnology().getName());
        doThrow(TechnologyNotExistsException.class).when(candidateTechnologiesService).listCandidateByTechnology(technologyName);
        when(technologyService.findByName(technologyName)).thenReturn(true);
        mockMvc.perform(get("/ev-tec/candidate-technologies/listCandidateByTechnology/{technologyName}", technologyName)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(technologyNameJson))
                .andExpect(status().isAccepted())
                .andExpect(result -> assertThrows(TechnologyNotExistsException.class, () -> candidateTechnologiesService.listCandidateByTechnology(technologyName)));
        verify(candidateTechnologiesService, atLeastOnce()).listCandidateByTechnology(technologyName);
    }
}
