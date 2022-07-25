package com.test.backend.developer.test_backend_nahuel.models.entities;

import com.fasterxml.jackson.annotation.JsonFormat;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import com.test.backend.developer.test_backend_nahuel.models.enums.DocumentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "candidate")
public class Candidate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "lastname")
    @NotNull
    private String lastName;

    @Column(name = "document_type")
    @NotNull
    private DocumentType documentType;

    @Column(name = "num_document")
    @NotNull
    @Min(value = 7, message = "The document cannot be less than 7 digits long")
    @Max(value = 8, message = "The document cannot have more than 8 digits")
    private String numDocument;

    @Column(name = "birthdate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

}
