package com.test.backend.developer.test_backend_nahuel.controllers;

import com.test.backend.developer.test_backend_nahuel.exceptions.CandidateNotExistsException;
import com.test.backend.developer.test_backend_nahuel.exceptions.TechnologyExistsException;
import com.test.backend.developer.test_backend_nahuel.exceptions.TechnologyNotExistsException;
import com.test.backend.developer.test_backend_nahuel.models.views.CandidateDTO;
import com.test.backend.developer.test_backend_nahuel.models.views.TechnologyDTO;
import com.test.backend.developer.test_backend_nahuel.repositories.TechnologyRepository;
import com.test.backend.developer.test_backend_nahuel.services.impl.TechnologyServiceImpl;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.google.gson.Gson;
import java.util.Optional;

import static com.test.backend.developer.test_backend_nahuel.utils.TestEntityFactory.getCandidateDTO;
import static com.test.backend.developer.test_backend_nahuel.utils.TestEntityFactory.getTechnology;
import static com.test.backend.developer.test_backend_nahuel.utils.TestEntityFactory.getTechnologyDTO;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static com.test.backend.developer.test_backend_nahuel.utils.TestEntityFactory.getTechnologyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TechnologyControllerTests extends AbstractMVCTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TechnologyServiceImpl technologyService;
    @MockBean
    TechnologyRepository technologyRepository;

    @Nested
    class createTest {
        @Test
        void createTestOk() throws Exception {
            String technologyDto = new Gson().toJson(getTechnologyDTO());
            mockMvc.perform(post("/ev-tec/technology/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(technologyDto))
                    .andExpect(status().isCreated());
            verify(technologyService, atLeastOnce()).create(any(TechnologyDTO.class));
        }

        @Test
        void createTechnologyAlreadyExistTest() throws Exception {
            doThrow(TechnologyExistsException.class).when(technologyService).create(getTechnologyDTO());
            String technologyDtoJson = new Gson().toJson(getTechnologyDTO());
            var technologyDto = getTechnologyDTO();
            mockMvc.perform(post("/ev-tec/technology/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(technologyDtoJson))
                    .andExpect(status().isAccepted())
                    .andExpect(result -> assertThrows(TechnologyExistsException.class, () -> technologyService.create(technologyDto)));
            verify(technologyService, atLeastOnce()).create(any(TechnologyDTO.class));
        }
    }

    @Nested
    class UpdateTest {
        @Test
        void updateOkTest() throws Exception {
            var technologyDto = getTechnologyDTO();
            String technologyDtoJson = new Gson().toJson(getTechnologyDTO());
            when(technologyService.update(technologyDto)).thenReturn(true);
            mockMvc.perform(put("/ev-tec/technology/update")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(technologyDtoJson))
                    .andExpect(status().isOk());
            verify(technologyService, atLeastOnce()).update(any(TechnologyDTO.class));
        }
        @Test
        void updateWhenTechnologyNotExistsTest() throws Exception {
            doThrow(TechnologyNotExistsException.class).when(technologyService).update(getTechnologyDTO());
            var technologyDto = getTechnologyDTO();
            String technologyDtoJson = new Gson().toJson(getTechnologyDTO());
            mockMvc.perform(put("/ev-tec/technology/update")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(technologyDtoJson))
                    .andExpect(status().isAccepted())
                    .andExpect(result -> assertThrows(TechnologyNotExistsException.class, () -> technologyService.update(technologyDto)));
            verify(technologyService, atLeastOnce()).update(any(TechnologyDTO.class));
        }
    }

    @Test
    void listTest() throws Exception {
        var technologies = getTechnologyList();
        when(technologyService.findAll()).thenReturn(technologies);
        mockMvc.perform(get("/ev-tec/technology/"))
                .andExpect(status().isOk());
    }
    @Nested
    class FindByIdTest {
        @Test
        void findByIdOkTest() throws Exception {
            var technology = getTechnology();
            String technologyId = new Gson().toJson(1L);
            when(technologyService.findById(getTechnology().getId())).thenReturn(technology);
            mockMvc.perform(get("/ev-tec/technology/{technologyId}", technologyId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(technologyId))
                    .andExpect(status().isOk());
            verify(technologyService, times(1)).findById(technology.getId());
        }

        @Test
        void findByIdTechnologyNotFoundTest() throws Exception {
            Long technologyId = getTechnology().getId();
            var technology = getTechnology();
            doThrow(TechnologyNotExistsException.class).when(technologyService).findById(technologyId);
            String technologyIdJson = new Gson().toJson(technologyId);
            mockMvc.perform(get("/ev-tec/technology/{technologyId}", technologyId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(technologyIdJson))
                    .andExpect(status().isAccepted())
                    .andExpect(result -> assertThrows(TechnologyNotExistsException.class, () -> technologyService.findById(technologyId)));
            verify(technologyService, atLeastOnce()).findById(technologyId);
        }
    }
    @Nested
    class DeleteTest {
        @Test
        void deleteOkTest() throws Exception {
            var technologyId = getTechnology().getId();
            var technology = getTechnology();
            var technologyIdJson = new Gson().toJson(getTechnology().getId());
            when(technologyRepository.findById(technologyId)).thenReturn(Optional.ofNullable(technology));
            mockMvc.perform(delete("/ev-tec/technology/delete/{technologyId}", technologyId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(technologyIdJson))
                    .andExpect(status().isOk());
            verify(technologyService, atLeastOnce()).delete(technologyId);
        }

        @Test
        void deleteTechnologyNotExistsTest() throws Exception {
            var technologyId = getTechnology().getId();
            var technology = getTechnology();
            var technologyIdJson = new Gson().toJson(getTechnology().getId());
            doThrow(TechnologyNotExistsException.class).when(technologyService).delete(technologyId);
            when(technologyRepository.findById(technologyId)).thenReturn(Optional.ofNullable(technology));
            mockMvc.perform(delete("/ev-tec/technology/delete/{technologyId}", technologyId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(technologyIdJson))
                    .andExpect(status().isAccepted())
                    .andExpect(result -> assertThrows(TechnologyNotExistsException.class, () -> technologyService.delete(technologyId)));
            verify(technologyService, atLeastOnce()).delete(technologyId);
        }
    }

}
