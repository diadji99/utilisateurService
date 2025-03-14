package com.gestionHopital.serv_utilisateur.hospitalisation.model;

import com.gestionHopital.serv_utilisateur.gestionBatiment.lit.model.Lit;
import com.gestionHopital.serv_utilisateur.patient.model.Patient;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hospitalisation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateEntree;
    private LocalDate dateSortie;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = true)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Patient patient;
    private double prix=0;
    @ManyToOne
    @JoinColumn(name = "lit_id", nullable = false)
    private Lit lit;
}
