package com.test.backend.developer.test_backend_nahuel.services.impl;

import com.test.backend.developer.test_backend_nahuel.exceptions.CandidateDocException;
import com.test.backend.developer.test_backend_nahuel.exceptions.CandidateExistsException;
import com.test.backend.developer.test_backend_nahuel.exceptions.CandidateNotExistsException;
import com.test.backend.developer.test_backend_nahuel.models.entities.Candidate;
import com.test.backend.developer.test_backend_nahuel.models.views.CandidateDTO;
import com.test.backend.developer.test_backend_nahuel.repositories.CandidateRepository;
import com.test.backend.developer.test_backend_nahuel.services.CandidateService;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
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
    public Boolean create(CandidateDTO candidateDTO) throws CandidateExistsException {
        return upload(candidateDTO);
    }

    @Override
    @Transactional
    public Boolean upload(CandidateDTO candidateDTO) throws CandidateExistsException {
        List<Candidate> listCandidate = candidateRepository.findAll();
        if(listCandidate.stream().anyMatch(
                candidate -> candidate.getNumDocument().equals(candidateDTO.getNumDocument())
        )){ throw new CandidateExistsException("El candidato con documento " + candidateDTO.getNumDocument() + " ya existe!");
        } else {
            Candidate candidate = Candidate.builder()
                    .name(candidateDTO.getName())
                    .lastname(candidateDTO.getLastname())
                    .documentType(candidateDTO.getDocumentType())
                    .numDocument(candidateDTO.getNumDocument())
                    .birthdate(candidateDTO.getBirthdate())
                    .build();
            candidateRepository.save(candidate);
            log.info("Candidato creado satisfactoriamente.");
        }
        return true;
    }

    @Override
    @Transactional
    public Boolean update(CandidateDTO candidateDTO) throws CandidateNotExistsException {
        Optional<Candidate> resp = candidateRepository.findById(candidateDTO.getId());

        if (resp.isPresent()) {
            Candidate candidate = resp.get();
            candidate.setName(candidateDTO.getName());
            candidate.setLastname(candidateDTO.getLastname());
            candidate.setDocumentType(candidateDTO.getDocumentType());
            candidate.setNumDocument(candidateDTO.getNumDocument());
            candidate.setBirthdate(candidateDTO.getBirthdate());

            candidateRepository.save(candidate);
            log.info("Candidato actualizado.");
            return true;
        } else {
            throw new CandidateNotExistsException("No se encontro el candidato.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Candidate findByDocument(String document) throws CandidateNotExistsException, CandidateDocException {
        validateDoc(document);
        Candidate candidate;
        if (candidateRepository.findByDocument(document) != null) {
            candidate = candidateRepository.findByDocument(document);
        } else {
            throw new CandidateNotExistsException("El candidato con documento " + document + " no existe!");
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
    public void delete(Long id) throws CandidateNotExistsException {
        if (candidateRepository.findById(id).isPresent()) {
            candidateRepository.deleteById(id);
            log.info("Candidato eliminado.");
        }else{
            throw new CandidateNotExistsException("El candidato con id " + id + "no existe en la base de datos.");
        }
    }
    public void validateDoc(String doc) throws CandidateDocException {
        if (doc == null || doc.length()<8) {
            throw new CandidateDocException("Documento mal ingresado.");
        }
    }
}
