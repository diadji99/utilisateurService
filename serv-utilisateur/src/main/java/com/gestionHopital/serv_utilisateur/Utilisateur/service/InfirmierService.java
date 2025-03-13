package com.gestionHopital.serv_utilisateur.Utilisateur.service;

import com.gestionHopital.serv_utilisateur.Utilisateur.modele.Infirmier;
import com.gestionHopital.serv_utilisateur.Utilisateur.repository.InfirmierRepository;
import com.gestionHopital.serv_utilisateur.exception.ResourceAlreadyExistException;
import com.gestionHopital.serv_utilisateur.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class InfirmierService {

    @Autowired
    private InfirmierRepository infirmierRepository;

    public void ajouterInfirmier(Infirmier infirmier) {
        Optional<Infirmier> optionalInfirmier = infirmierRepository.findByNumeroProfessionnel(infirmier.getNumeroProfessionnel());
        if (optionalInfirmier.isPresent())
            throw new ResourceAlreadyExistException("Le numéro professionnel existe déjà" + infirmier.getNumeroProfessionnel());
        try {
            infirmierRepository.save(infirmier);
        } catch (Exception exception) {
            throw new ResourceAlreadyExistException("Error lors de l'Ajout" + infirmier.getNumeroProfessionnel());
        }

    }

    public List<Infirmier> listerInfirmier() { return infirmierRepository.findAll(); }

    public Infirmier rechercher(Long id) {
        try {return infirmierRepository.findById(id).get();
        }catch (Exception exception) {
            throw new ResourceNotFoundException("Infirmier avec pour ID" + id + "n'existe pas");
        }
    }

    public Infirmier modifierInfirmier(Infirmier infirmier) {
        return infirmierRepository.save(infirmier);
    }

    // InfirmierService.java
    public List<Infirmier> listerInfirmiers() {
        return infirmierRepository.findAll();
    }

    public void activerInfirmier(Long id) {
        Infirmier infirmier = infirmierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Infirmier non trouvé"));
        infirmier.setActive(!infirmier.isActive());
        infirmierRepository.save(infirmier);
    }

    public void delete(Infirmier infirmier){
        infirmierRepository.delete(infirmier);
    }
}
