package com.gestionHopital.serv_utilisateur.gestionBatiment.bureau.repository;

import com.gestionHopital.serv_utilisateur.gestionBatiment.bureau.model.Bureau;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
public interface BureauRepository extends JpaRepository<Bureau,Long> {
}
