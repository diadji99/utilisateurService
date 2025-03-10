package com.gestionHopital.serv_utilisateur.Utilisateur.repository;

import com.gestionHopital.serv_utilisateur.Utilisateur.modele.Infirmier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InfirmierRepository extends JpaRepository<Infirmier,Long> {
    Optional<Infirmier> findByNumeroProfessionnel(String numeroProfessionnel);
}
