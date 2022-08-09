package com.test.backend.developer.test_backend_nahuel.services.impl;

import com.test.backend.developer.test_backend_nahuel.exceptions.CandidateExistsException;
import com.test.backend.developer.test_backend_nahuel.exceptions.CandidateNotExistsException;
import com.test.backend.developer.test_backend_nahuel.models.entities.Candidate;
import com.test.backend.developer.test_backend_nahuel.models.views.CandidateDTO;
import com.test.backend.developer.test_backend_nahuel.repositories.CandidateRepository;
import com.test.backend.developer.test_backend_nahuel.services.CandidateService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CandidateServiceImpl implements CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    @Override
    @Transactional
    public void create(CandidateDTO candidateDTO) {
        log.info("candidateDto: " + candidateDTO);
        List<Candidate> listCandidate = candidateRepository.findAll();
        if (listCandidate.stream().anyMatch(
                candidate -> candidate.getNumDocument().equals(candidateDTO.getNumDocument()))) {
            throw new CandidateExistsException("The candidate with document " + candidateDTO.getNumDocument() + " already exists!");
        } else {
            Candidate candidate = Candidate.builder()
                    .name(candidateDTO.getName())
                    .lastName(candidateDTO.getLastName())
                    .documentType(candidateDTO.getDocumentType())
                    .numDocument(candidateDTO.getNumDocument())
                    .birthDate(candidateDTO.getBirthDate())
                    .build();
            candidateRepository.save(candidate);
            log.info("Candidate successfully created.");
        }
    }

    @Override
    @Transactional
    public Boolean update(CandidateDTO candidateDTO) {
        Optional<Candidate> candidateOptional = candidateRepository.findById(candidateDTO.getId());

        if (candidateOptional.isPresent()) {
            log.debug("The candidateOptional is: " + candidateOptional.get());
            Candidate candidate = candidateOptional.get();
            candidate.setName(candidateDTO.getName());
            candidate.setLastName(candidateDTO.getLastName());
            candidate.setDocumentType(candidateDTO.getDocumentType());
            candidate.setNumDocument(candidateDTO.getNumDocument());
            candidate.setBirthDate(candidateDTO.getBirthDate());
            log.debug("The candidate is: " + candidate);
            candidateRepository.save(candidate);
            log.info("Updated candidate.");
            return true;
        } else {
            throw new CandidateNotExistsException("Candidate not found.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Candidate findByDocument(String document) {
        log.info("document: " + document);
        Candidate candidate;
        if (candidateRepository.findByDocument(document) != null) {
            candidate = candidateRepository.findByDocument(document);
            log.debug("candidate: " + candidate);
        } else {
            throw new CandidateNotExistsException("The candidate with document " + document + " does not exist!");
        }
        return candidate;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Candidate> findAll() {
        return candidateRepository.findAll();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("id: " + id);
        if (candidateRepository.findById(id).isPresent()) {
            candidateRepository.deleteById(id);
            log.info("Eliminated candidate.");
        } else {
            throw new CandidateNotExistsException("Candidate with id " + id + "does not exist.");
        }
    }
}
