package com.gestionHopital.serv_utilisateur.gestionBatiment.salle.repository;

import com.gestionHopital.serv_utilisateur.gestionBatiment.salle.model.Salle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalleRepository extends JpaRepository<Salle,Long> {
}
