package com.test.backend.developer.test_backend_nahuel.services;

import static com.test.backend.developer.test_backend_nahuel.utils.TestEntityFactory.getTechnology;
import static com.test.backend.developer.test_backend_nahuel.utils.TestEntityFactory.getTechnologyDTO;
import static com.test.backend.developer.test_backend_nahuel.utils.TestEntityFactory.getTechnologyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.test.backend.developer.test_backend_nahuel.exceptions.TechnologyNotExistsException;
import com.test.backend.developer.test_backend_nahuel.repositories.TechnologyRepository;
import com.test.backend.developer.test_backend_nahuel.services.impl.TechnologyServiceImpl;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

class TechnologyServicesImplTest extends AbstractMvcTestServices {

    @InjectMocks
    TechnologyServiceImpl technologyService;

    @Mock
    TechnologyRepository technologyRepository;

    @Test
    void create() {
        var technologyDto = getTechnologyDTO();
        var technology = getTechnology();
        when(technologyRepository.save(technology)).thenReturn(technology);
        technologyRepository.save(technology);
        technologyService.create(technologyDto);
        verify(technologyRepository, times(1)).save(technology);
    }

    @Nested
    class UpdateTest {
        @Test
        void updateTestOk() {
            var technologyDTO = getTechnologyDTO();
            var technology = getTechnology();
            when(technologyRepository.findById(1L)).thenReturn(Optional.ofNullable(technology));
            technologyService.update(technologyDTO);
            assert technology != null;
            verify(technologyRepository).save(technology);
        }

        @Test
        void updateWhenTechnologyNotExists() {
            assertThrows(TechnologyNotExistsException.class, () -> technologyService.update(getTechnologyDTO()));

        }
    }

    @Nested
    class FindByIdTest {
        @Test
        void findByIdOkTest() {
            var technology = getTechnology();
            when(technologyRepository.findById(1L)).thenReturn(Optional.ofNullable(technology));
            assertEquals(technologyService.findById(getTechnology().getId()), technology);
        }

        @Test
        void technologyByIdNotExists() {
            assertThrows(TechnologyNotExistsException.class, () -> technologyService.findById(getTechnologyDTO().getId()));
        }
    }

    @Nested
    class FindByNameTest {
        @Test
        void findByNameOkTest() {
            String techName = "Java";
            var technology = getTechnology();
            when(technologyRepository.findByName(techName)).thenReturn(technology);
            assertTrue(technologyService.findByName(getTechnology().getName()));
        }

        @Test
        void technologyByNameNotExistsTest() {
            assertThrows(TechnologyNotExistsException.class, () -> technologyService.findByName(getTechnologyDTO().getName()));
        }
    }

    @Test
    void findAll() {
        var technologiesList = getTechnologyList();
        when(technologyRepository.findAll()).thenReturn(technologiesList);

        var technologies = technologyService.findAll();
        verify(technologyRepository, times(1)).findAll();
        assertEquals(technologyService.findAll(), technologies);
    }

    @Nested
    class DeleteTest {
        @Test
        void deleteByIdOkTest() {
            when(technologyRepository.findById(1L)).thenReturn(Optional.of(getTechnology()));
            technologyService.delete(1L);
            verify(technologyRepository, times(1)).deleteById(1L);
        }

        @Test
        void deleteWhenCTechnologyNotExistsTest() {
            assertThrows(TechnologyNotExistsException.class, () -> technologyService.delete(getTechnologyDTO().getId()));
        }
    }
}
