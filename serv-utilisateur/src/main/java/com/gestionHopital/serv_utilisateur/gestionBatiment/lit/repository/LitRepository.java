package com.gestionHopital.serv_utilisateur.gestionBatiment.lit.repository;

import com.gestionHopital.serv_utilisateur.gestionBatiment.lit.model.Lit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
public interface LitRepository extends JpaRepository<Lit,Long> {
}
