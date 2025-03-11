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
        Optional<Administrateur> optionalEtudiant = administrateurRepository.findByNumeroProfessionnel(administrateur.getNumeroProfessionnel());
        if (optionalEtudiant.isPresent())
            throw new ResourceAlreadyExistException("Le numéro professionnel existe déjà" + administrateur.getNumeroProfessionnel());
        try {
            administrateurRepository.save(administrateur);
        } catch (Exception exception) {
            throw new ResourceAlreadyExistException("Error lors de l'Ajout" + administrateur.getNumeroProfessionnel());
        }

    }

    public List<Administrateur> listerAdministrateur() { return administrateurRepository.findAll(); }

    public Administrateur rechercherAdministrateur(Long id) {
        try {return administrateurRepository.findById(id).get();
        }catch (Exception exception) {
            throw new ResourceNotFoundException("Administrateur avec pour ID" + id + "n'existe pas");
        }
    }

    public void modifierAdministrateur(Administrateur administrateur) {
        try {
            administrateurRepository.save(administrateur);
        } catch (Exception exception) {
            throw new ResourceNotFoundException("Erreur lors de la modificatiocation " + administrateur.getId());
        }
    }

    public void activer(Long id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        utilisateur.setActive(!utilisateur.isActive());
        utilisateurRepository.save(utilisateur);
    }

}
