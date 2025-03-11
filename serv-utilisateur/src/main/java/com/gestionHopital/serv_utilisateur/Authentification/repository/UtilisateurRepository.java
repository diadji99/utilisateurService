package com.gestionHopital.serv_utilisateur.Authentification.repository;

import com.gestionHopital.serv_utilisateur.Authentification.modele.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    @Query("SELECT u FROM Utilisateur u WHERE u.username = :username")
    Utilisateur findUtilisateurByUsername(@Param("username") String username);

}
