package com.test.backend.developer.test_backend_nahuel.services.impl;

import com.test.backend.developer.test_backend_nahuel.exceptions.TechnologyExistsException;
import com.test.backend.developer.test_backend_nahuel.exceptions.TechnologyNotExistsException;
import com.test.backend.developer.test_backend_nahuel.models.entities.Technology;
import com.test.backend.developer.test_backend_nahuel.models.views.TechnologyDTO;
import com.test.backend.developer.test_backend_nahuel.repositories.TechnologyRepository;
import com.test.backend.developer.test_backend_nahuel.services.TechnologyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TechnologyServiceImpl implements TechnologyService {

    @Autowired
    TechnologyRepository technologyRepository;

    private static final String TECHNOLOGY_NOT_FOUND = "Technology not found.";

    @Override
    @Transactional
    public void create(TechnologyDTO technologyDTO) {
        log.info("technologyDTO: " + technologyDTO);
        if (technologyRepository.findNameAndVersion(technologyDTO.getName(), technologyDTO.getVersion()) != null) {
            throw new TechnologyExistsException("The technology " + technologyDTO.getName()
                    + ", version " + technologyDTO.getVersion() + " already exists.");
        } else {
            var technology = Technology.builder()
                    .name(technologyDTO.getName())
                    .version(technologyDTO.getVersion())
                    .build();
            technologyRepository.save(technology);
            log.info("Successfully created technology.");
        }
    }

    @Override
    @Transactional
    public Boolean update(TechnologyDTO technologyDTO) {
        Optional<Technology> technologyOptional = technologyRepository.findById(technologyDTO.getId());

        if (technologyOptional.isPresent()) {
            log.debug("technologyOptional: " + technologyOptional);
            var technology = technologyOptional.get();
            technology.setName(technologyDTO.getName());
            technology.setVersion(technologyDTO.getVersion());
            log.info("technology: " + technology);
            technologyRepository.save(technology);
            log.info("Updated technology.");
            return true;
        } else {
            throw new TechnologyNotExistsException(TECHNOLOGY_NOT_FOUND);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Technology findById(Long techId) {
        log.info("techId: " + techId);
        Optional<Technology> technologyRepositoryById = technologyRepository.findById(techId);
        log.debug("technologyRepositoryById: " + technologyRepositoryById);
        if (technologyRepositoryById.isPresent()) {
            return technologyRepositoryById.get();
        } else {
            throw new TechnologyNotExistsException(TECHNOLOGY_NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public Boolean findByName(String technology) {
        log.info("technology: " + technology);
        var technologyRepositoryByName = technologyRepository.findByName(technology);
        log.debug("technologyRepositoryByName: " + technologyRepositoryByName);
        if (technologyRepositoryByName != null) {
            return true;
        } else {
            throw new TechnologyNotExistsException(TECHNOLOGY_NOT_FOUND);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Technology> findAll() {
        return technologyRepository.findAll();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("id: " + id);
        Optional<Technology> technologyRepositoryById = technologyRepository.findById(id);
        if (technologyRepositoryById.isPresent()) {
            log.debug("technologyRepository: " + technologyRepositoryById);
            technologyRepository.deleteById(id);
            log.info("Technology deleted.");
        } else {
            throw new TechnologyNotExistsException(TECHNOLOGY_NOT_FOUND);
        }
    }
}
