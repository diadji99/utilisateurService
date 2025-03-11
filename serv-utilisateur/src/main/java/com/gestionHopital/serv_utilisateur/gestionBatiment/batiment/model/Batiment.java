package com.gestionHopital.serv_utilisateur.gestionBatiment.batiment.model;

import com.gestionHopital.serv_utilisateur.gestionBatiment.service.model.ServiceF;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Batiment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true,nullable = false)
    private String nom;

    @OneToMany(mappedBy = "batiment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServiceF> serviceFS;
}
