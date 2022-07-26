package com.test.backend.developer.test_backend_nahuel.utils;

import com.test.backend.developer.test_backend_nahuel.models.entities.Candidate;
import com.test.backend.developer.test_backend_nahuel.models.entities.CandidateTechnologies;
import com.test.backend.developer.test_backend_nahuel.models.entities.Technology;
import com.test.backend.developer.test_backend_nahuel.models.enums.DocumentType;
import com.test.backend.developer.test_backend_nahuel.models.views.CandidateDTO;
import com.test.backend.developer.test_backend_nahuel.models.views.CandidateTechnologiesDTO;
import com.test.backend.developer.test_backend_nahuel.models.views.TechnologyDTO;

import java.util.ArrayList;
import java.util.List;

public class TestEntityFactory {

    public static Candidate getCandidate() {
        return Candidate.builder()
                .id(1L)
                .name("Lionel")
                .lastName("Messi")
                .documentType(DocumentType.DNI)
                .numDocument("12345678")
                .birthDate(null)
                .build();
    }

    public static CandidateDTO getCandidateDTO() {
        return CandidateDTO.builder()
                .id(2L)
                .name("Jose Luis")
                .lastName("Chilavert")
                .documentType(DocumentType.DNI)
                .numDocument("11111111")
                .birthDate(null)
                .build();
    }
    public static CandidateDTO getCandidateDTODuplicated() {
        return CandidateDTO.builder()
                .id(1L)
                .name("Lionel")
                .lastName("Messi")
                .documentType(DocumentType.DNI)
                .numDocument("12345678")
                .birthDate(null)
                .build();
    }
    public static List<Candidate> getCandidateList() {
        List<Candidate> candidates = new ArrayList<>();
        candidates.add(getCandidate());
        return candidates;
    }

    public static Technology getTechnology() {
        return Technology.builder()
                .id(1L)
                .name("Java")
                .version("8.0")
                .build();
    }

    public static TechnologyDTO getTechnologyDTO() {
        return TechnologyDTO.builder()
                .id(1L)
                .name("Java")
                .version("8.0")
                .build();
    }

    public static List<Technology> getTechnologyList() {
        List<Technology> technologies = new ArrayList<>();
        technologies.add(getTechnology());
        return technologies;
    }

    public static CandidateTechnologies getCandidateTechnologies() {
        Candidate candidate = getCandidate();
        Technology technology = getTechnology();
        return CandidateTechnologies.builder()
                .id(1L)
                .candidate(candidate)
                .technology(technology)
                .experience(2)
                .build();
    }

    public static CandidateTechnologiesDTO getCandidateTechnologiesDTO() {
        Candidate candidate = getCandidate();
        Technology technology = getTechnology();
        return CandidateTechnologiesDTO.builder()
                .candidate(candidate)
                .technology(technology)
                .experience(2)
                .build();
    }

    public static CandidateTechnologies getCandidateByTechnology() {
        Candidate candidate = getCandidate();
        Technology technology = getTechnology();
        return CandidateTechnologies.builder()
                .id(1L)
                .candidate(candidate)
                .technology(technology)
                .experience(2)
                .build();
    }

    public static CandidateTechnologiesDTO getCandidateByTechnologyDto() {
        Candidate candidate = getCandidate();
        Technology technology = getTechnology();
        return CandidateTechnologiesDTO.builder()
                .candidate(candidate)
                .technology(technology)
                .experience(2)
                .build();
    }

    public static List<CandidateTechnologies> getCandidateByTechnologyList() {
        List<CandidateTechnologies> candidateByTechnologies = new ArrayList<>();
        candidateByTechnologies.add(getCandidateByTechnology());
        return candidateByTechnologies;
    }
}
