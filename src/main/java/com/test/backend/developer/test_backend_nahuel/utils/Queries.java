package com.test.backend.developer.test_backend_nahuel.utils;

public interface Queries {
    String FIND_CANDIDATE_BY_DOCUMENT = "SELECT * FROM CANDIDATE c WHERE c.document = ?1";
    String FIND_TECHNOLOGY_NAME_AND_VERSION = "SELECT * FROM TECHNOLOGY t WHERE t.name = ?1 AND t.version = ?2";
    String FIND_BY_CANDIDATE_ID_AND_TECHNOLOGY_ID = "SELECT * FROM CANDIDATE_TECHNOLOGIES ct WHERE ct.candidate_id = ?1 AND ct.technology_id = ?2";

}
