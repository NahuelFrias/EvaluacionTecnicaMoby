package com.test.backend.developer.test_backend_nahuel.repositories;

import com.test.backend.developer.test_backend_nahuel.models.entities.Technology;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import static com.test.backend.developer.test_backend_nahuel.utils.Queries.FIND_TECHNOLOGY_NAME_AND_VERSION;

@Repository
public interface TechnologyRepository extends JpaRepository<Technology, Long> {
    @Query(value = FIND_TECHNOLOGY_NAME_AND_VERSION,nativeQuery = true)
    Technology findNameAndVersion(String name, String version);
}
