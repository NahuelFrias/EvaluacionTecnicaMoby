package com.test.backend.developer.test_backend_nahuel.services;

import com.test.backend.developer.test_backend_nahuel.exceptions.TechnologyExistsException;
import com.test.backend.developer.test_backend_nahuel.exceptions.TechnologyNotExistsException;
import com.test.backend.developer.test_backend_nahuel.models.entities.Technology;
import com.test.backend.developer.test_backend_nahuel.models.views.TechnologyDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TechnologyService {
    Boolean create(TechnologyDTO technologyDTO) throws TechnologyExistsException;
    Boolean upload(TechnologyDTO technologyDTO) throws TechnologyExistsException;
    public Boolean update(TechnologyDTO technologyDTO) throws TechnologyNotExistsException;
    Technology findById(Long techId) throws TechnologyNotExistsException;
    List<Technology> findAll();
    void delete(Long id) throws TechnologyNotExistsException;

}
