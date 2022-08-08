package com.test.backend.developer.test_backend_nahuel.services;

import com.test.backend.developer.test_backend_nahuel.exceptions.CandidateDocException;
import com.test.backend.developer.test_backend_nahuel.exceptions.CandidateExistsException;
import com.test.backend.developer.test_backend_nahuel.exceptions.CandidateNotExistsException;
import com.test.backend.developer.test_backend_nahuel.models.entities.Candidate;
import com.test.backend.developer.test_backend_nahuel.models.views.CandidateDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CandidateService {

    void create(CandidateDTO candidateDTO);

    Boolean update(CandidateDTO candidateDTO);

    Candidate findByDocument(String document);

    List<Candidate> findAll();

    void delete(Long id);

}
