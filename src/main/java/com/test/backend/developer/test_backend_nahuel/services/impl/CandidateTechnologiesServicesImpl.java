package com.test.backend.developer.test_backend_nahuel.services.impl;

import com.test.backend.developer.test_backend_nahuel.exceptions.CandidateTechnologiesExistsException;
import com.test.backend.developer.test_backend_nahuel.exceptions.TechnologyNotExistsException;
import com.test.backend.developer.test_backend_nahuel.models.entities.Candidate;
import com.test.backend.developer.test_backend_nahuel.models.entities.CandidateTechnologies;
import com.test.backend.developer.test_backend_nahuel.models.entities.Technology;
import com.test.backend.developer.test_backend_nahuel.models.views.CandidateTechnologiesDTO;
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

    @Override
    @Transactional
    public Boolean create(CandidateTechnologiesDTO candidateTechnologiesDTO) throws CandidateTechnologiesExistsException {
        return upload(candidateTechnologiesDTO);
    }

    @Override
    @Transactional
    public Boolean upload(CandidateTechnologiesDTO candidateTechnologiesDTO) throws CandidateTechnologiesExistsException {
        if (candidateTechnologiesRepository.findByCandidateIdAndTechnologyId(candidateTechnologiesDTO.getCandidate().getId(), candidateTechnologiesDTO.getTechnology().getId()) != null) {
            throw new CandidateTechnologiesExistsException("La tecnologia relacionada a este candidato ya existe");
        } else {
            Candidate candidate = Candidate.builder()
                    .id(candidateTechnologiesDTO.getCandidate().getId())
                    .name(candidateTechnologiesDTO.getCandidate().getName())
                    .lastname(candidateTechnologiesDTO.getCandidate().getLastname())
                    .documentType(candidateTechnologiesDTO.getCandidate().getDocumentType())
                    .numDocument(candidateTechnologiesDTO.getCandidate().getNumDocument())
                    .birthdate(candidateTechnologiesDTO.getCandidate().getBirthdate())
                    .build();
            Technology technology = Technology.builder()
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
            log.info("Tecnologia asociada al candidato creada.");
        }
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public CandidateTechnologies findById(Long id) throws TechnologyNotExistsException {
        CandidateTechnologies candidateTechnologies;
        Optional<CandidateTechnologies> candidateTechnologiesOptional = candidateTechnologiesRepository.findById(id);
        if (candidateTechnologiesOptional.isPresent()) {
            candidateTechnologies = candidateTechnologiesOptional.get();
        } else {
            throw new TechnologyNotExistsException("Esta tecnologia relacionada al candidato no existe.");
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
    public void delete(Long id) throws TechnologyNotExistsException {
        Optional<CandidateTechnologies> candidateTechnologies = candidateTechnologiesRepository.findById(id);
        if (candidateTechnologies.isPresent()) {
            candidateTechnologiesRepository.deleteById(id);
            log.info("Tecnologia asociada al candidato eliminada.");
        } else {
            throw new TechnologyNotExistsException("Esta tecnologia relacionada al candidato no existe.");
        }
    }
}
