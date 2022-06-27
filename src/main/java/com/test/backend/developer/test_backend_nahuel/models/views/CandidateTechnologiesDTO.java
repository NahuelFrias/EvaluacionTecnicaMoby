package com.test.backend.developer.test_backend_nahuel.models.views;

import com.test.backend.developer.test_backend_nahuel.models.entities.Candidate;
import com.test.backend.developer.test_backend_nahuel.models.entities.Technology;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CandidateTechnologiesDTO {

    private Long id;
    private Candidate candidate;
    private Technology technology;
    private Integer experience;
}
