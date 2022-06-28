package com.test.backend.developer.test_backend_nahuel.utils;

public class Querys {
    public Querys() {
    }
    public static final String FIND_CANDIDATE_BY_DOCUMENT = "SELECT * FROM CANDIDATE c WHERE c.document = ?1";
    public static final String FIND_TECHNOLOGY_NAME_AND_VERSION = "SELECT * FROM TECHNOLOGY t WHERE t.name = ?1 AND t.version = ?2";
    public static final String FIND_BY_CANDIDATE_ID_AND_TECHNOLOGY_ID = "SELECT * FROM CANDIDATE_TECHNOLOGIES ct WHERE ct.candidate_id = ?1 AND ct.technology_id = ?2";

    public static final String FIND_BY_TECHNOLOGY_NAME = "SELECT c.candidate_name, c.candidate_lastname, c.document_type ,c.num_document, c.birthdate, c.technology_name, t.technology_version, ct.experience " +
            "FROM CANDIDATES c INNER JOIN CANDIDATE_TECHNOLOGIES ct ON c.candidate_id = ct.candidate_id " +
            "INNER JOIN TECHNOLOGIES t ON ct.technology_id = t.technology_id WHERE t.technology_name LIKE ?1";

    public static final String FIND_BY_TECHNOLOGY_NAME_AND_VERSION = "SELECT c.candidate_name, c.candidate_lastname, c.document_type ,c.num_document, c.birthdate, t.technology_name, t.version, ct.experience " +
            "FROM CANDIDATES c INNER JOIN CANDIDATE_TECHNOLOGY ct  ON c.candidate_id = ct.candidate_id " +
            "INNER JOIN TECHNOLOGIES t ON ct.technology_id = t.technology_id WHERE t.technology_name LIKE ?1 AND t.version LIKE ?2";
}
