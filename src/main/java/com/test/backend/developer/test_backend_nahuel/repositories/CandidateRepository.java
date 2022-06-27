package com.test.backend.developer.test_backend_nahuel.repositories;

import com.test.backend.developer.test_backend_nahuel.models.entities.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import static com.test.backend.developer.test_backend_nahuel.utils.Querys.FIND_CANDIDATE_BY_DOCUMENT;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    @Query(value = FIND_CANDIDATE_BY_DOCUMENT, nativeQuery = true)
    Candidate findByDocument(String document);
}
