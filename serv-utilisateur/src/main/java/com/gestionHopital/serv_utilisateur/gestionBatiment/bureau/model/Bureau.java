package com.gestionHopital.serv_utilisateur.gestionBatiment.bureau.model;

import com.gestionHopital.serv_utilisateur.gestionBatiment.service.model.ServiceF;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Bureau {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int numero;
    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private ServiceF serviceF;

    public void setService(String nom) {
        this.serviceF.setNom(nom);
    }
}
