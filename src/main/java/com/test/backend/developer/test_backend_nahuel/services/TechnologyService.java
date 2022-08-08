package com.test.backend.developer.test_backend_nahuel.services;

import com.test.backend.developer.test_backend_nahuel.exceptions.TechnologyExistsException;
import com.test.backend.developer.test_backend_nahuel.exceptions.TechnologyNotExistsException;
import com.test.backend.developer.test_backend_nahuel.models.entities.Technology;
import com.test.backend.developer.test_backend_nahuel.models.views.TechnologyDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TechnologyService {
    void create(TechnologyDTO technologyDTO);

    Boolean update(TechnologyDTO technologyDTO);

    Technology findById(Long techId);

    List<Technology> findAll();

    void delete(Long id);

    Boolean findByName(String technology);

}
