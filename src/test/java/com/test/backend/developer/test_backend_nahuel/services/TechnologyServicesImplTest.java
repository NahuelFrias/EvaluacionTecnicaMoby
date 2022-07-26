package com.test.backend.developer.test_backend_nahuel.services;

import static com.test.backend.developer.test_backend_nahuel.utils.TestEntityFactory.getTechnology;
import static com.test.backend.developer.test_backend_nahuel.utils.TestEntityFactory.getTechnologyDTO;
import static com.test.backend.developer.test_backend_nahuel.utils.TestEntityFactory.getTechnologyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.test.backend.developer.test_backend_nahuel.repositories.TechnologyRepository;
import com.test.backend.developer.test_backend_nahuel.services.impl.TechnologyServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

public class TechnologyServicesImplTest extends AbstractMvcTestServices {

    @InjectMocks
    TechnologyServiceImpl technologyService;

    @Mock
    TechnologyRepository technologyRepository;

    @Test
    void createTest() {
        var technologies = getTechnologyList();
        var technologyDTO = getTechnologyDTO();
        when(technologyRepository.findAll()).thenReturn(technologies);
        assertTrue(technologyService.create(technologyDTO));
    }

    @Test
    void updateTest(){
        var technologyDTO = getTechnologyDTO();
        var technology = getTechnology();
        when(technologyRepository.findById(1L)).thenReturn(Optional.ofNullable(technology));
        technologyService.update(technologyDTO);
        verify(technologyRepository).save(technology);
    }

    @Test
    void findById() {
        var technology = getTechnology();
        when(technologyRepository.findById(1L)).thenReturn(Optional.ofNullable(technology));
        assertEquals(technologyService.findById(getTechnology().getId()), technology);
    }

    @Test
    void findByName() {
        String techName = "Java";
        var technology = getTechnology();
        when(technologyRepository.findByName(techName)).thenReturn(technology);
        assertTrue(technologyService.findByname(getTechnology().getName()));
    }

    @Test
    void findAll() {
        var technologiesList = getTechnologyList();
        when(technologyRepository.findAll()).thenReturn(technologiesList);

        var technologies = technologyService.findAll();
        verify(technologyRepository, times(1)).findAll();
        assertEquals(technologyService.findAll(), technologies);
    }

}
