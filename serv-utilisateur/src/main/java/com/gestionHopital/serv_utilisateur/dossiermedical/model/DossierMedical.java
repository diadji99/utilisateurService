package com.gestionHopital.serv_utilisateur.dossiermedical.model;

import com.gestionHopital.serv_utilisateur.patient.model.Patient;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity @Data @AllArgsConstructor @NoArgsConstructor
public class DossierMedical {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long numeroDossier;
    private String antecedents;
    private List<String> allergies;
    private List<String> medicationsAnterieures;
    private List<String> pathologiesChroniques;
    private GroupeSanguin groupeSanguin;
    @OneToOne
    private Patient patient;
}
