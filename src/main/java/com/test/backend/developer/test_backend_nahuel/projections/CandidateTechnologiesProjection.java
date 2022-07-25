package com.test.backend.developer.test_backend_nahuel.projections;

import org.springframework.beans.factory.annotation.Value;

public interface CandidateTechnologiesProjection {

    @Value("#{target.name + ' ' + target.lastname}")
    String getFullName();

    @Value("#{target.document_type}")
    String getDocType();

    @Value("#{target.num_document}")
    String getDocument();

    @Value("#{target.birthdate}")
    String getBirthdate();

    @Value("#{target.technology_name}")
    String getTechnologyName();

    @Value("#{target.version}")
    String getVersion();

    @Value("#{target.experience}")
    String getExperience();
}
