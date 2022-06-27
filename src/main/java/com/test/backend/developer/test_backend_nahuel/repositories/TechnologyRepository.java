package com.test.backend.developer.test_backend_nahuel.repositories;

import com.test.backend.developer.test_backend_nahuel.models.entities.Technology;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TechnologyRepository extends JpaRepository<Technology, Long> {
}
