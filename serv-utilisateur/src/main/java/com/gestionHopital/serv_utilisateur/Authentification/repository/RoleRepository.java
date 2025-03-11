package com.gestionHopital.serv_utilisateur.Authentification.repository;

import com.gestionHopital.serv_utilisateur.Authentification.modele.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Role findByRole(String role);
}
