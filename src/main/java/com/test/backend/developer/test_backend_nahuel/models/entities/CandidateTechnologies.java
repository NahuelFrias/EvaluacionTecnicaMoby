package com.test.backend.developer.test_backend_nahuel.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "candidate_technologies")
public class CandidateTechnologies implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "candidate_id", referencedColumnName = "id")
    @NotNull
    private Candidate candidate;

    @ManyToOne
    @JoinColumn(name = "technology_id", referencedColumnName = "id")
    @NotNull
    private Technology technology;

    @Column(name = "experience")
    private Integer experience;
}
