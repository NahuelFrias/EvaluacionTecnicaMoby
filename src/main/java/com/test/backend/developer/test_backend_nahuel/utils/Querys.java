package com.test.backend.developer.test_backend_nahuel.utils;

public class Querys {
    public Querys() {
    }
    public static final String FIND_CANDIDATE_BY_DOCUMENT = "SELECT * FROM CANDIDATES c WHERE c.document = ?1";
    public static final String FIND_TECHNOLOGY_NAME_AND_VERSION = "SELECT * FROM TECHNOLOGIES t WHERE t.technology_name = ?1 AND t.version = ?2";
}
