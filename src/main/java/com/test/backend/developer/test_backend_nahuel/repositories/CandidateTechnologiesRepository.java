package com.test.backend.developer.test_backend_nahuel.repositories;

import com.test.backend.developer.test_backend_nahuel.models.entities.CandidateTechnologies;
import com.test.backend.developer.test_backend_nahuel.projections.CandidateTechnologiesProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.test.backend.developer.test_backend_nahuel.utils.Queries.FIND_BY_CANDIDATE_ID_AND_TECHNOLOGY_ID;
import static com.test.backend.developer.test_backend_nahuel.utils.Queries.FIND_CANDIDATE_BY_TECHNOLOGY;

@Repository
public interface CandidateTechnologiesRepository extends JpaRepository<CandidateTechnologies, Long> {
    @Query(value = FIND_BY_CANDIDATE_ID_AND_TECHNOLOGY_ID, nativeQuery = true)
    CandidateTechnologies findByCandidateIdAndTechnologyId(Long candidateId, Long technologyId);
    @Query(value = FIND_CANDIDATE_BY_TECHNOLOGY, nativeQuery = true)
    List<CandidateTechnologiesProjection> listCandidateByTechnology(String technology);
}
