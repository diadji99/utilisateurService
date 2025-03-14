package com.gestionHopital.serv_utilisateur.Utilisateur.modele;

import com.gestionHopital.serv_utilisateur.Authentification.modele.Utilisateur;
import com.gestionHopital.serv_utilisateur.consultation.model.Consultation;
import com.gestionHopital.serv_utilisateur.gestionBatiment.bureau.model.Bureau;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Medecin extends Utilisateur {

    private String specialite;
    private String grade;
    @OneToOne
    private Bureau bureau;
    @OneToMany(mappedBy = "medecin",orphanRemoval = false)
    private List<Consultation> consultations;
}
