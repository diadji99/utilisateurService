package com.gestionHopital.serv_utilisateur.Utilisateur.service;

import com.gestionHopital.serv_utilisateur.Authentification.modele.Utilisateur;
import com.gestionHopital.serv_utilisateur.Authentification.repository.UtilisateurRepository;
import com.gestionHopital.serv_utilisateur.Utilisateur.modele.Administrateur;
import com.gestionHopital.serv_utilisateur.Utilisateur.repository.AdministrateurRepository;
import com.gestionHopital.serv_utilisateur.exception.ResourceAlreadyExistException;
import com.gestionHopital.serv_utilisateur.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AdministrateurService {
    @Autowired
    private AdministrateurRepository administrateurRepository;
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public void ajouterAdministrateur(Administrateur administrateur) {
            administrateurRepository.save(administrateur);
    }

    public List<Administrateur> listerAdministrateur() { return administrateurRepository.findAll(); }

    public Administrateur rechercherAdministrateur(Long id) {
        try {return administrateurRepository.findById(id).get();
        }catch (Exception exception) {
            throw new ResourceNotFoundException("Administrateur avec pour ID" + id + "n'existe pas");
        }
    }

    public Administrateur modifierAdministrateur(Administrateur administrateur) {
        return administrateurRepository.save(administrateur);
    }

    public void activer(Long id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        utilisateur.setActive(!utilisateur.isActive());
        utilisateurRepository.save(utilisateur);
    }

    public Administrateur findByUsername(String username){
        return administrateurRepository.findByUsername(username);
    }

}
