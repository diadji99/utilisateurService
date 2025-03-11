package com.gestionHopital.serv_utilisateur.patient.repository;

import com.gestionHopital.serv_utilisateur.patient.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient,Long> {
    public Patient findByMatricule(Long matricule);
}
