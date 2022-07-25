package com.test.backend.developer.test_backend_nahuel.services;

import com.test.backend.developer.test_backend_nahuel.repositories.CandidateTechnologiesRepository;
import com.test.backend.developer.test_backend_nahuel.services.impl.CandidateTechnologiesServicesImpl;
import com.test.backend.developer.test_backend_nahuel.services.impl.TechnologyServiceImpl;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static com.test.backend.developer.test_backend_nahuel.utils.TestEntityFactory.getCandidateByTechnologyList;
import static com.test.backend.developer.test_backend_nahuel.utils.TestEntityFactory.getCandidateTechnologies;
import static com.test.backend.developer.test_backend_nahuel.utils.TestEntityFactory.getCandidateTechnologiesDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CandidateTechnologiesServicesImplTest extends AbstractMvcTestServices {

    @InjectMocks
    CandidateTechnologiesServicesImpl candidateTechnologiesServices;

    @Mock
    CandidateTechnologiesRepository candidateTechnologiesRepository;

    @Mock
    TechnologyServiceImpl technologyService;

    @Test
    void createTest() {
        var candidateTechnologiesList = getCandidateByTechnologyList();
        var candidateByTechnologiesDTO = getCandidateTechnologiesDTO();
        when(candidateTechnologiesRepository.findAll()).thenReturn(candidateTechnologiesList);
        assertTrue(candidateTechnologiesServices.create(candidateByTechnologiesDTO));
    }

    @Test
    void findAll() {
        var candidateTechnologiesList = getCandidateByTechnologyList();
        when(candidateTechnologiesRepository.findAll()).thenReturn(candidateTechnologiesList);
        var candidateTechnologiesServicesAll = candidateTechnologiesServices.findAll();
        verify(candidateTechnologiesRepository, times(1)).findAll();
        assertEquals(candidateTechnologiesRepository.findAll(), candidateTechnologiesServicesAll);
    }

    @Test
    void deleteById() {
        Long id = 1L;
        when(candidateTechnologiesRepository.findById(id)).thenReturn(Optional.of(getCandidateTechnologies()));
        candidateTechnologiesServices.delete(1L);
        verify(candidateTechnologiesRepository, times(1)).deleteById(1L);
    }

    @Disabled
    @Test
    void listCandidateByTechnologyTest (){
        String techName = "Java";
        when(technologyService.findByname(techName)).thenReturn(true);
    }
}