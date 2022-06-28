package com.test.backend.developer.test_backend_nahuel.models.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @Column(name = "candidate_id")
    private Long id;
    @Column(name = "candidate_name")
    private String name;
    @Column(name = "candidate_lastname")
    private String lastname;
    @Column(name = "document_type")
    private DocumentType documentType;
    @Column(name = "num_document")
    private String numDocument;
    @Column(name = "birthdate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdate;

}
