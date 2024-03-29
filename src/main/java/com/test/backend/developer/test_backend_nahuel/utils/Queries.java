package com.test.backend.developer.test_backend_nahuel.utils;

public final class Queries {
    private Queries() {}
    public static final String FIND_CANDIDATE_BY_DOCUMENT = "SELECT * FROM CANDIDATE c WHERE c.num_document = ?1";
    public static final String FIND_TECHNOLOGY_NAME_AND_VERSION = "SELECT * FROM TECHNOLOGY t WHERE t.technology_name = ?1 AND t.version = ?2";
    public static final String FIND_BY_CANDIDATE_ID_AND_TECHNOLOGY_ID = "SELECT * FROM CANDIDATE_TECHNOLOGIES ct WHERE ct.candidate_id = ?1 AND ct.technology_id = ?2";
    public static final String FIND_CANDIDATE_BY_TECHNOLOGY = "SELECT c.name, c.lastname, c.document_type ,c.num_document, c.birthdate, t.technology_name, t.version, ct.experience " +
            "FROM CANDIDATE c INNER JOIN CANDIDATE_TECHNOLOGIES ct ON c.id=ct.candidate_id INNER JOIN TECHNOLOGY t ON t.id=ct.technology_id WHERE t.technology_name LIKE ?1";
    public static final String FIND_TECHNOLOGY_NAME = "SELECT * FROM TECHNOLOGY t WHERE t.technology_name = ?1";
}
