package com.test.backend.developer.test_backend_nahuel.models.views;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.test.backend.developer.test_backend_nahuel.models.enums.DocumentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateDTO {
    private Long id;
    private String name;
    private String lastName;
    private DocumentType documentType;
    private String numDocument;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
}
