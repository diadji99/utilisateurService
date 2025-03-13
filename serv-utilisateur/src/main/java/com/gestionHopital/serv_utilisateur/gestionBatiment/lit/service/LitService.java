package com.gestionHopital.serv_utilisateur.gestionBatiment.lit.service;

import com.gestionHopital.serv_utilisateur.gestionBatiment.lit.model.Lit;
import com.gestionHopital.serv_utilisateur.gestionBatiment.lit.repository.LitRepository;
import com.gestionHopital.serv_utilisateur.gestionBatiment.salle.model.Salle;
import com.gestionHopital.serv_utilisateur.gestionBatiment.salle.service.SalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class LitService {
    @Autowired
    private LitRepository litRepository;
    @Autowired
    private SalleService salleService;
    public Lit create(Lit lit){
        return litRepository.save(lit);
    }

    public void addLit(Salle salle){
        int nbLit = salle.getNombreLits() +1;
        salle.setNombreLits(nbLit);
        salleService.update(salle);
    }


    public Lit update(Lit lit){
        Lit existing = litRepository.findById(lit.getId()).get();
        if(existing == null){
            return null;
        }
        return litRepository.save(lit);
    }

    public void delete(Lit lit){
        litRepository.delete(lit);
    }

    public List<Lit> findAll(){
        return litRepository.findAll();
    }

    public Lit findById(Long id){
        return litRepository.findById(id).get();
    }

}
