package com.test.backend.developer.test_backend_nahuel.services.impl;

import com.test.backend.developer.test_backend_nahuel.models.entities.CandidateTechnologies;
import com.test.backend.developer.test_backend_nahuel.models.views.CandidateTechnologiesDTO;
import com.test.backend.developer.test_backend_nahuel.services.CandidateTechnologiesService;

import java.util.List;

public class CandidateTechnologiesServicesImpl implements CandidateTechnologiesService {
    @Override
    public Boolean create(CandidateTechnologiesDTO candidateTechnologiesDTO) {
        return null;
    }

    @Override
    public Boolean upload(CandidateTechnologiesDTO candidateTechnologiesDTO) {
        return null;
    }

    @Override
    public CandidateTechnologiesDTO findById(Long id) {
        return null;
    }

    @Override
    public List<CandidateTechnologies> findAll() {
        return null;
    }

    @Override
    public List<CandidateTechnologies> findByName(String techName) {
        return null;
    }

    @Override
    public List<CandidateTechnologies> findByTechVersion(String techName, String techVersion) {
        return null;
    }
}
