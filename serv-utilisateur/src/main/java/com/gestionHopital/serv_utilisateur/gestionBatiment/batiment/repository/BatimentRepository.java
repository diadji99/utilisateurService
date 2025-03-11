package com.gestionHopital.serv_utilisateur.gestionBatiment.batiment.repository;

import com.gestionHopital.serv_utilisateur.gestionBatiment.batiment.model.Batiment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface BatimentRepository extends JpaRepository<Batiment,Long> {
    public Batiment findByNom(String nom);
}
