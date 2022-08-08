package com.test.backend.developer.test_backend_nahuel.services;

import com.test.backend.developer.test_backend_nahuel.exceptions.CandidateExistsException;
import com.test.backend.developer.test_backend_nahuel.exceptions.CandidateNotExistsException;
import com.test.backend.developer.test_backend_nahuel.exceptions.CandidateTechnologiesExistsException;
import com.test.backend.developer.test_backend_nahuel.exceptions.TechnologyNotExistsException;
import com.test.backend.developer.test_backend_nahuel.models.entities.Candidate;
import com.test.backend.developer.test_backend_nahuel.models.entities.CandidateTechnologies;
import com.test.backend.developer.test_backend_nahuel.models.views.CandidateTechnologiesDTO;
import com.test.backend.developer.test_backend_nahuel.projections.CandidateTechnologiesProjection;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CandidateTechnologiesService {

    void create(CandidateTechnologiesDTO candidateTechnologiesDTO) throws CandidateExistsException, CandidateTechnologiesExistsException;
    CandidateTechnologies findById (Long id) throws TechnologyNotExistsException;
    List<CandidateTechnologies> findAll ();
    void delete(Long id) throws CandidateNotExistsException, TechnologyNotExistsException;
    List<CandidateTechnologiesProjection> listCandidateByTechnology (String technology);
}
