package com.gestionHopital.serv_utilisateur.Utilisateur.repository;

import com.gestionHopital.serv_utilisateur.Utilisateur.modele.Medecin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedecinRepository extends JpaRepository<Medecin, Long> {
    Optional<Medecin> findByNumeroProfessionnel(String numeroProfessionnel);
}
