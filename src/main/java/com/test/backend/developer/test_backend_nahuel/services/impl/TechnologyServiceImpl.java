package com.test.backend.developer.test_backend_nahuel.services.impl;

import com.test.backend.developer.test_backend_nahuel.exceptions.TechnologyExistsException;
import com.test.backend.developer.test_backend_nahuel.exceptions.TechnologyNotExistsException;
import com.test.backend.developer.test_backend_nahuel.models.entities.Technology;
import com.test.backend.developer.test_backend_nahuel.models.views.TechnologyDTO;
import com.test.backend.developer.test_backend_nahuel.repositories.TechnologyRepository;
import com.test.backend.developer.test_backend_nahuel.services.TechnologyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class TechnologyServiceImpl implements TechnologyService {

    @Autowired
    TechnologyRepository technologyRepository;

    @Override
    @Transactional
    public Boolean create(TechnologyDTO technologyDTO) throws TechnologyExistsException {
        return upload(technologyDTO);
    }

    @Override
    @Transactional
    public Boolean upload(TechnologyDTO technologyDTO) throws TechnologyExistsException {
        if (technologyRepository.findNameAndVersion(technologyDTO.getName(), technologyDTO.getVersion()) != null) {
            throw new TechnologyExistsException("La tecnologia " + technologyDTO.getName()
                    + ", version " + technologyDTO.getVersion() + " ya existe.");
        } else {
            Technology technology = Technology.builder()
                    .name(technologyDTO.getName())
                    .version(technologyDTO.getVersion())
                    .build();
            technologyRepository.save(technology);
        }
        return true;
    }

    @Override
    @Transactional
    public Boolean update(TechnologyDTO technologyDTO) throws TechnologyNotExistsException {
        Optional<Technology> resp = technologyRepository.findById(technologyDTO.getId());

        if (resp.isPresent()) {
            Technology technology= resp.get();
            technology.setName(technologyDTO.getName());
            technology.setVersion(technologyDTO.getVersion());

            technologyRepository.save(technology);
            return true;
        } else {
            throw new TechnologyNotExistsException("No se encontro la tecnologia.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Technology findById(Long techId) throws TechnologyNotExistsException {
        Optional<Technology> resp = technologyRepository.findById(techId);

        if (resp.isPresent()) {
            return resp.get();
        } else {
            throw new TechnologyNotExistsException("No se encontro la tecnologia.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Technology> findAll() {
        return technologyRepository.findAll();
    }

    @Override
    @Transactional
    public void delete(Long id) throws TechnologyNotExistsException {

        Optional<Technology> resp = technologyRepository.findById(id);
        if (resp.isPresent()) {
            technologyRepository.deleteById(id);
        } else {
            throw new TechnologyNotExistsException("No se encontro la tecnologia.");
        }

    }
}
