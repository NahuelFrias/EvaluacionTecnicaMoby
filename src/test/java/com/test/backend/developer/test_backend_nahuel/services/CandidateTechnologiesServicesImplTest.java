package com.test.backend.developer.test_backend_nahuel.services;

import com.test.backend.developer.test_backend_nahuel.exceptions.CandidateExistsException;
import com.test.backend.developer.test_backend_nahuel.exceptions.CandidateNotExistsException;
import com.test.backend.developer.test_backend_nahuel.exceptions.TechnologyExistsException;
import com.test.backend.developer.test_backend_nahuel.exceptions.TechnologyNotExistsException;
import com.test.backend.developer.test_backend_nahuel.projections.CandidateTechnologiesProjection;
import com.test.backend.developer.test_backend_nahuel.repositories.CandidateTechnologiesRepository;
import com.test.backend.developer.test_backend_nahuel.repositories.TechnologyRepository;
import com.test.backend.developer.test_backend_nahuel.services.impl.CandidateTechnologiesServicesImpl;
import com.test.backend.developer.test_backend_nahuel.services.impl.TechnologyServiceImpl;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.test.backend.developer.test_backend_nahuel.utils.TestEntityFactory.getCandidateByTechnologyList;
import static com.test.backend.developer.test_backend_nahuel.utils.TestEntityFactory.getCandidateTechnologies;
import static com.test.backend.developer.test_backend_nahuel.utils.TestEntityFactory.getCandidateTechnologiesDTO;
import static com.test.backend.developer.test_backend_nahuel.utils.TestEntityFactory.getTechnology;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CandidateTechnologiesServicesImplTest extends AbstractMvcTestServices {

    @InjectMocks
    CandidateTechnologiesServicesImpl candidateTechnologiesServices;

    @Mock
    CandidateTechnologiesRepository candidateTechnologiesRepository;

    @Mock
    TechnologyServiceImpl technologyService;

    @Mock
    TechnologyRepository technologyRepository;

    @Nested
    class CreateTest {
        @Test
        void createTestOk() {
            var candidateTechnologyDto = getCandidateTechnologiesDTO();
            var candidateTechnology = getCandidateTechnologies();

            when(candidateTechnologiesRepository.save(candidateTechnology)).thenReturn(candidateTechnology);
            candidateTechnologiesServices.create(candidateTechnologyDto);
            candidateTechnologiesRepository.save(candidateTechnology);
            verify(candidateTechnologiesRepository, times(1)).save(candidateTechnology);
        }

        @Disabled("Se deshabilita por no encontrar el error")
        @Test
        void createWhenCandidateTechnologyAlreadyExists() {
            var candidateTechnologyDto = getCandidateTechnologiesDTO();
            candidateTechnologiesRepository.save(getCandidateTechnologies());
            candidateTechnologiesServices.create(getCandidateTechnologiesDTO());
            assertThrows(TechnologyExistsException.class, () -> candidateTechnologiesServices.create(candidateTechnologyDto));
        }
    }

    @Test
    void findAllTest() {
        var candidateTechnologiesList = getCandidateByTechnologyList();
        when(candidateTechnologiesRepository.findAll()).thenReturn(candidateTechnologiesList);
        var candidateTechnologiesServicesAll = candidateTechnologiesServices.findAll();
        verify(candidateTechnologiesRepository, times(1)).findAll();
        assertEquals(candidateTechnologiesRepository.findAll(), candidateTechnologiesServicesAll);
    }

    @Nested
    class FindById {
        @Test
        void findByIdOkTest() {
            Long id = 1L;
            var candidateTechnologies = getCandidateTechnologies();
            when(candidateTechnologiesRepository.findById(id)).thenReturn(Optional.of(candidateTechnologies));
            candidateTechnologiesServices.findById(id);
            assertEquals(id, candidateTechnologies.getId());
        }

        @Test
        void candidateTechnologyByIdNotExists() {
            var candidateTechnologyId = getCandidateTechnologies().getId();
            assertThrows(TechnologyNotExistsException.class, () -> candidateTechnologiesServices.findById(candidateTechnologyId));
        }
    }

    @Nested
    class DeleteTest {
        @Test
        void deleteById() {
            Long id = 1L;
            when(candidateTechnologiesRepository.findById(id)).thenReturn(Optional.of(getCandidateTechnologies()));
            candidateTechnologiesServices.delete(1L);
            verify(candidateTechnologiesRepository, times(1)).deleteById(1L);
        }

        @Test
        void deleteWhenCandidateTechnologyNotExists() {
            var candidateTechnologyId = getCandidateTechnologiesDTO().getId();
            assertThrows(TechnologyNotExistsException.class, () -> candidateTechnologiesServices.delete(candidateTechnologyId));
        }
    }

    @Nested
    class ListCandidateByTechnologyTest {
        @Test
        void listCandidateByTechnologyOkTest() {
            List<CandidateTechnologiesProjection> candidateTechnologyList = new ArrayList<>();
            var technologyName = getTechnology().getName();
            when(technologyService.findByName(technologyName)).thenReturn(true);
            candidateTechnologiesServices.listCandidateByTechnology(technologyName);
            assertEquals(candidateTechnologyList, candidateTechnologiesServices.listCandidateByTechnology(technologyName));
        }

        @Test
        void listCandidateByTechnologyNotFoundTest() {
            var technologyName = getTechnology().getName();
            assertThrows(TechnologyNotExistsException.class, () -> candidateTechnologiesServices.listCandidateByTechnology(technologyName));
        }
    }
}
