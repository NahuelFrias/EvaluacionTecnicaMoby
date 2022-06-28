package com.test.backend.developer.test_backend_nahuel.repositories;

import com.test.backend.developer.test_backend_nahuel.models.entities.CandidateTechnologies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.test.backend.developer.test_backend_nahuel.utils.Querys.FIND_BY_CANDIDATE_ID_AND_TECHNOLOGY_ID;
import static com.test.backend.developer.test_backend_nahuel.utils.Querys.FIND_BY_TECHNOLOGY_NAME;
import static com.test.backend.developer.test_backend_nahuel.utils.Querys.FIND_BY_TECHNOLOGY_NAME_AND_VERSION;

@Repository
public interface CandidateTechnologiesRepository extends JpaRepository<CandidateTechnologies, Long> {

    @Query(value = FIND_BY_CANDIDATE_ID_AND_TECHNOLOGY_ID, nativeQuery = true)
    CandidateTechnologies findByCandidateIdAndTechnologyId(Long candidateId, Long technologyId);

    @Query(value = FIND_BY_TECHNOLOGY_NAME, nativeQuery = true)
    List<CandidateTechnologies> findByTechnologyName(String technologyName);

    @Query(value = FIND_BY_TECHNOLOGY_NAME_AND_VERSION, nativeQuery = true)
    List<CandidateTechnologies> findByTechnologyNameAndVersion(String technologyName, String technologyVersion);
}
