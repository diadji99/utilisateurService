package com.gestionHopital.serv_utilisateur.Authentification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gestionHopital.serv_utilisateur.Authentification.modele.Role;
import com.gestionHopital.serv_utilisateur.Authentification.modele.Utilisateur;
import com.gestionHopital.serv_utilisateur.Authentification.repository.RoleRepository;
import com.gestionHopital.serv_utilisateur.Authentification.repository.UtilisateurRepository;

@Service
@Transactional
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private RoleRepository roleRepository;

    public Utilisateur ajouter_Utilisateur(Utilisateur utilisateur) {
        utilisateurRepository.save(utilisateur);
        return utilisateur;
    }

    public Role ajouter_Role(Role role) {
        roleRepository.save(role);
        return role;
    }

    public void ajouter_UtilisateurRoles(Utilisateur utilisateur, Role role) {
        Utilisateur user = utilisateurRepository.findUtilisateurByUsername(utilisateur.getUsername());
        Role profil = roleRepository.findRoleByRole(role.getRole());
        user.getRoles().add(profil);
    }

    public Utilisateur rechercher_Utilisateur(String username){
        return utilisateurRepository.findUtilisateurByUsername(username);
    }

    public void updateUtilisateur(Utilisateur utilisateur) {
        utilisateurRepository.save(utilisateur);
    }
}
