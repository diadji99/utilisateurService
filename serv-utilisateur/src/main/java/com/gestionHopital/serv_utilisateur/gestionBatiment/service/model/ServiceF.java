package com.gestionHopital.serv_utilisateur.gestionBatiment.service.model;

import com.gestionHopital.serv_utilisateur.gestionBatiment.batiment.model.Batiment;
import com.gestionHopital.serv_utilisateur.gestionBatiment.bureau.model.Bureau;
import com.gestionHopital.serv_utilisateur.gestionBatiment.salle.model.Salle;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class ServiceF {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true,nullable = false)
    private String nom;

    @ManyToOne
    @JoinColumn(name = "batiment_id", nullable = false)
    private Batiment batiment;

    @OneToMany(mappedBy = "serviceF", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Salle> salles;

    @OneToMany(mappedBy = "serviceF", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bureau> bureauList;
}
