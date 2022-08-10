package com.test.backend.developer.test_backend_nahuel.services;

import com.test.backend.developer.test_backend_nahuel.exceptions.CandidateExistsException;
import com.test.backend.developer.test_backend_nahuel.exceptions.CandidateNotExistsException;
import com.test.backend.developer.test_backend_nahuel.repositories.CandidateRepository;
import com.test.backend.developer.test_backend_nahuel.services.impl.CandidateServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static com.test.backend.developer.test_backend_nahuel.utils.TestEntityFactory.getCandidate;
import static com.test.backend.developer.test_backend_nahuel.utils.TestEntityFactory.getCandidateDTO;
import static com.test.backend.developer.test_backend_nahuel.utils.TestEntityFactory.getCandidateDTOWithoutDni;
import static com.test.backend.developer.test_backend_nahuel.utils.TestEntityFactory.getCandidateList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CandidateServicesImplTest extends AbstractMvcTestServices {
    @InjectMocks
    CandidateServiceImpl candidateService;

    @Mock
    CandidateRepository candidateRepository;

    @Test
    void createTestOk() {
        var candidates = getCandidateList();
        var candidateDTO = getCandidateDTOWithoutDni();
        var candidate = getCandidate();
        when(candidateRepository.findAll()).thenReturn(candidates);
        candidateService.create(candidateDTO);
        candidateRepository.save(candidate);
        verify(candidateRepository, times(1)).save(candidate);
    }

    @Test
    void createWhenCandidateAlreadyExists() {
        candidateRepository.save(getCandidate());
        candidateService.create(getCandidateDTO());
        assertThrows(CandidateExistsException.class, () -> candidateService.create(getCandidateDTO()));
    }

    @Nested
    class UpdateTest {
        @Test
        void updateTestOk() {
            var candidateDTO = getCandidateDTO();
            var candidate = getCandidate();
            when(candidateRepository.findById(candidateDTO.getId())).thenReturn(Optional.of(getCandidate()));
            candidateRepository.save(candidate);
            candidateService.update(candidateDTO);
            verify(candidateRepository).save(candidate);
        }

        @Test
        void updateWhenCandidateNotExists() {
            assertThrows(CandidateNotExistsException.class, () -> candidateService.update(getCandidateDTO()));

        }
    }

    @Test
    void findByDocumentTest() {
        var candidate = getCandidate();
        when(candidateRepository.findByDocument(candidate.getNumDocument())).thenReturn(candidate);
        assertEquals(candidateService.findByDocument(candidate.getNumDocument()), candidate);
    }

    @Test
    void documentNotExists() {
        assertThrows(CandidateNotExistsException.class, () -> candidateService.findByDocument(getCandidateDTO().getNumDocument()));
    }

    @Test
    void findAll() {
        var candidates = getCandidateList();
        when(candidateRepository.findAll()).thenReturn(candidates);

        var candidateList = candidateService.findAll();
        verify(candidateRepository, times(1)).findAll();
        assertEquals(candidateRepository.findAll(), candidateList);
    }

    @Nested
    class DeleteCandidate {
        @Test
        void deleteById() {
            when(candidateRepository.findById(1L)).thenReturn(Optional.of(getCandidate()));
            candidateService.delete(1L);
            verify(candidateRepository, times(1)).deleteById(1L);
        }

        @Test
        void deleteWhenCandidateNotExists() {
            assertThrows(CandidateNotExistsException.class, () -> candidateService.delete(getCandidateDTO().getId()));

        }
    }
}
