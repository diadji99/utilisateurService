package com.gestionHopital.serv_utilisateur.Utilisateur.modele;

import com.gestionHopital.serv_utilisateur.Authentification.modele.Utilisateur;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Medecin extends Utilisateur {
    @Column(unique = true)
    private String numeroProfessionnel;
    private String specialite;
}
