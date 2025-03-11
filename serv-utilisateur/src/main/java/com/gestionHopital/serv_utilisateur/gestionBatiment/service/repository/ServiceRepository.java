package com.gestionHopital.serv_utilisateur.gestionBatiment.service.repository;

import com.gestionHopital.serv_utilisateur.gestionBatiment.batiment.model.Batiment;
import com.gestionHopital.serv_utilisateur.gestionBatiment.service.model.ServiceF;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository  extends JpaRepository<ServiceF,Long> {
    List<ServiceF> findByBatiment(Batiment batiment);
    ServiceF findByNom(String nom);
}
