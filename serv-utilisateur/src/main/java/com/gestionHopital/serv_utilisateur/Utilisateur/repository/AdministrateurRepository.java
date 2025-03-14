package com.gestionHopital.serv_utilisateur.Utilisateur.repository;

import com.gestionHopital.serv_utilisateur.Utilisateur.modele.Administrateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdministrateurRepository extends JpaRepository<Administrateur, Long> {
    Optional<Administrateur> findByNumeroProfessionnel(String numeroProfessionnel);
    Administrateur findByUsername(String username);
}
