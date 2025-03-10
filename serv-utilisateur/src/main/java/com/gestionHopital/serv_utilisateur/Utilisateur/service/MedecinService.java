package com.gestionHopital.serv_utilisateur.Utilisateur.service;

import com.gestionHopital.serv_utilisateur.Utilisateur.modele.Medecin;
import com.gestionHopital.serv_utilisateur.Utilisateur.repository.MedecinRepository;
import com.gestionHopital.serv_utilisateur.exception.ResourceAlreadyExistException;
import com.gestionHopital.serv_utilisateur.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MedecinService {

    @Autowired
    private MedecinRepository medecinRepository;

    public void ajouterMedecin(Medecin medecin) {
        Optional<Medecin> optionalMedecin = medecinRepository.findByNumeroProfessionnel(medecin.getNumeroProfessionnel());
        if (optionalMedecin.isPresent())
            throw new ResourceAlreadyExistException("Le numéro professionnel existe déjà" + medecin.getNumeroProfessionnel());
        try {
            medecinRepository.save(medecin);
        } catch (Exception exception) {
            throw new ResourceAlreadyExistException("Error lors de l'Ajout" + medecin.getNumeroProfessionnel());
        }

    }

    public List<Medecin> listerMedecin() { return medecinRepository.findAll(); }

    public Medecin rechercher(Long id) {
        try {return medecinRepository.findById(id).get();
        }catch (Exception exception) {
            throw new ResourceNotFoundException("Medecin avec pour ID" + id + "n'existe pas");
        }
    }

    public void modifierMedecin(Medecin medecin) {
        try {
            medecinRepository.save(medecin);
        } catch (Exception exception) {
            throw new ResourceNotFoundException("Erreur lors de la modificatiocation " + medecin.getId());
        }
    }

    public List<Medecin> listerMedecins() {
        return medecinRepository.findAll();
    }

    public void activerMedecin(Long id) {
        Medecin medecin = medecinRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Médecin non trouvé"));
        medecin.setActive(!medecin.isActive());
        medecinRepository.save(medecin);
    }

}
