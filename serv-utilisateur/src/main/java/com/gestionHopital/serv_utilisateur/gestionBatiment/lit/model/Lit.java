package com.gestionHopital.serv_utilisateur.gestionBatiment.lit.model;

import com.gestionHopital.serv_utilisateur.gestionBatiment.salle.model.Salle;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Lit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,unique = true)
    private int numero;
    private String type; // Exemple: "Médicalisé", "Simple", "Double", "Enfant"

    @Enumerated(EnumType.STRING)
    private EtatLit etat; // DISPONIBLE, OCCUPE, EN_REPARATION

    @ManyToOne
    @JoinColumn(name = "salle_id", nullable = true)
    private Salle salle;
}
