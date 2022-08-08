package com.test.backend.developer.test_backend_nahuel.services.impl;

import com.test.backend.developer.test_backend_nahuel.exceptions.CandidateTechnologiesExistsException;
import com.test.backend.developer.test_backend_nahuel.exceptions.TechnologyNotExistsException;
import com.test.backend.developer.test_backend_nahuel.models.entities.Candidate;
import com.test.backend.developer.test_backend_nahuel.models.entities.CandidateTechnologies;
import com.test.backend.developer.test_backend_nahuel.models.entities.Technology;
import com.test.backend.developer.test_backend_nahuel.models.views.CandidateTechnologiesDTO;
import com.test.backend.developer.test_backend_nahuel.projections.CandidateTechnologiesProjection;
import com.test.backend.developer.test_backend_nahuel.repositories.CandidateTechnologiesRepository;
import com.test.backend.developer.test_backend_nahuel.services.CandidateTechnologiesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CandidateTechnologiesServicesImpl implements CandidateTechnologiesService {

    @Autowired
    CandidateTechnologiesRepository candidateTechnologiesRepository;
    @Autowired
    TechnologyServiceImpl technologyService;

    @Override
    @Transactional
    public void create(CandidateTechnologiesDTO candidateTechnologiesDTO) {
        if (candidateTechnologiesRepository.findByCandidateIdAndTechnologyId(candidateTechnologiesDTO.getCandidate().getId(), candidateTechnologiesDTO.getTechnology().getId()) != null) {
            throw new CandidateTechnologiesExistsException("The technology related to this candidate already exists");
        } else {
            var candidate = Candidate.builder()
                    .id(candidateTechnologiesDTO.getCandidate().getId())
                    .name(candidateTechnologiesDTO.getCandidate().getName())
                    .lastName(candidateTechnologiesDTO.getCandidate().getLastName())
                    .documentType(candidateTechnologiesDTO.getCandidate().getDocumentType())
                    .numDocument(candidateTechnologiesDTO.getCandidate().getNumDocument())
                    .birthDate(candidateTechnologiesDTO.getCandidate().getBirthDate())
                    .build();
            var technology = Technology.builder()
                    .id(candidateTechnologiesDTO.getTechnology().getId())
                    .name(candidateTechnologiesDTO.getTechnology().getName())
                    .version(candidateTechnologiesDTO.getTechnology().getVersion())
                    .build();
            candidateTechnologiesRepository.save(
                    CandidateTechnologies.builder()
                            .candidate(candidate)
                            .technology(technology)
                            .experience(candidateTechnologiesDTO.getExperience())
                            .build()
            );
            log.info("Technology associated with the candidate created.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public CandidateTechnologies findById(Long id) {
        CandidateTechnologies candidateTechnologies;
        Optional<CandidateTechnologies> candidateTechnologiesOptional = candidateTechnologiesRepository.findById(id);
        if (candidateTechnologiesOptional.isPresent()) {
            candidateTechnologies = candidateTechnologiesOptional.get();
        } else {
            throw new TechnologyNotExistsException("This candidate-related technology does not exist.");
        }
        return candidateTechnologies;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CandidateTechnologies> findAll() {
        return candidateTechnologiesRepository.findAll();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Optional<CandidateTechnologies> candidateTechnologies = candidateTechnologiesRepository.findById(id);
        if (candidateTechnologies.isPresent()) {
            candidateTechnologiesRepository.deleteById(id);
            log.info("Candidate's associated technology eliminated.");
        } else {
            throw new TechnologyNotExistsException("This candidate-related technology does not exist.");
        }
    }

    @Override
    public List<CandidateTechnologiesProjection> listCandidateByTechnology(String technology) {
        if (technologyService.findByName(technology)) {
            return candidateTechnologiesRepository.listCandidateByTechnology(technology);
        } else {
            throw new TechnologyNotExistsException("Technology not found");
        }
    }
}
