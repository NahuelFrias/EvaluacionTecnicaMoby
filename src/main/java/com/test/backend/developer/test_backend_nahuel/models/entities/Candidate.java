package com.test.backend.developer.test_backend_nahuel.models.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.test.backend.developer.test_backend_nahuel.models.enums.DocumentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Candidate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastname;
    private DocumentType documentType;
    private String numDocument;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdate;

}
