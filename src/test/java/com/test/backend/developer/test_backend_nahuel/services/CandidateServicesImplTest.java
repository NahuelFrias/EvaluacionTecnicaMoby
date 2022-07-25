package com.test.backend.developer.test_backend_nahuel.services;

import com.test.backend.developer.test_backend_nahuel.repositories.CandidateRepository;
import com.test.backend.developer.test_backend_nahuel.services.impl.CandidateServiceImpl;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static com.test.backend.developer.test_backend_nahuel.utils.TestEntityFactory.getCandidate;
import static com.test.backend.developer.test_backend_nahuel.utils.TestEntityFactory.getCandidateDTO;
import static com.test.backend.developer.test_backend_nahuel.utils.TestEntityFactory.getCandidateList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CandidateServicesImplTest extends AbstractMvcTestServices {
    @InjectMocks
    CandidateServiceImpl candidateService;

    @Mock
    CandidateRepository candidateRepository;

    @Test
    void createTest(){
        var candidates = getCandidateList();
        var candidateDTO = getCandidateDTO();
        when(candidateRepository.findAll()).thenReturn(candidates);
        assertTrue(candidateService.create(candidateDTO));
    }

    @Disabled
    @Test
    void updateTest(){
        var candidateDTO = getCandidateDTO();
        var candidate = getCandidate();
        when(candidateRepository.findById(candidateDTO.getId())).thenReturn(Optional.of(getCandidate()));
        verify(candidateRepository).save(candidate);
    }

    @Test
    void findByDocumentTest() {
        var candidate = getCandidate();
        when(candidateRepository.findByDocument(candidate.getNumDocument())).thenReturn(candidate);
        assertEquals(candidateService.findByDocument(candidate.getNumDocument()),candidate);
    }

    @Test
    void findAll() {
        var candidates = getCandidateList();
        when(candidateRepository.findAll()).thenReturn(candidates);

        var candidateList = candidateService.findAll();
        verify(candidateRepository, times(1)).findAll();
        assertEquals(candidateRepository.findAll(), candidateList);
    }

    @Test
    void deleteById() {
        when(candidateRepository.findById(1L)).thenReturn(Optional.of(getCandidate()));
        candidateService.delete(1L);
        verify(candidateRepository, times(1)).deleteById(1L);
    }
}