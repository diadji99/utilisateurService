package com.gestionHopital.serv_utilisateur.hospitalisation.repository;

import com.gestionHopital.serv_utilisateur.hospitalisation.model.Hospitalisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalisationRepository extends JpaRepository<Hospitalisation,Long> {
}
