package com.gestionHopital.serv_utilisateur.consultation.repository;

import com.gestionHopital.serv_utilisateur.consultation.model.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation,Long> {
}
