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

    public Infirmier ajouterInfirmier(Infirmier infirmier) {
       return infirmierRepository.save(infirmier);
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

    public Infirmier findByNumeroProfessionel(String numeroProfessionel){
        return infirmierRepository.findByNumeroProfessionnel(numeroProfessionel);
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
