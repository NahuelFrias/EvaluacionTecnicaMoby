package com.test.backend.developer.test_backend_nahuel.services;

import com.test.backend.developer.test_backend_nahuel.models.entities.CandidateTechnologies;
import com.test.backend.developer.test_backend_nahuel.models.views.CandidateTechnologiesDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CandidateTechnologiesService {

    Boolean create(CandidateTechnologiesDTO candidateTechnologiesDTO);
    Boolean upload (CandidateTechnologiesDTO candidateTechnologiesDTO);
    CandidateTechnologiesDTO findById (Long id);
    List<CandidateTechnologies> findAll ();
    List<CandidateTechnologies> findByName (String techName);
    List<CandidateTechnologies> findByTechVersion (String techName, String techVersion);
}
