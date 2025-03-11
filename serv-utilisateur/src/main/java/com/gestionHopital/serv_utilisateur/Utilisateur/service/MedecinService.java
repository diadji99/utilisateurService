package com.gestionHopital.serv_utilisateur.Utilisateur.service;

import com.gestionHopital.serv_utilisateur.Utilisateur.modele.Medecin;
import com.gestionHopital.serv_utilisateur.Utilisateur.repository.MedecinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedecinService {
    @Autowired
    private MedecinRepository medecinRepository;

    public Medecin create(Medecin medecin){

        return medecinRepository.save(medecin);
    }

    public Medecin update(Medecin update){
        Medecin existing = medecinRepository.findById(update.getId()).get();
        if(existing != null){
            return medecinRepository.save(update);
        }
        else {
            return null;
        }
    }

    public Medecin activer(Medecin medecin){
        if(medecin.isActive()){
            medecin.setActive(false);
        }else{
            medecin.setActive(true);
        }
        return medecinRepository.save(medecin);
    }

    public void delete(Medecin medecin){
        medecinRepository.delete(medecin);
    }

    public List<Medecin> findAll(){
        return medecinRepository.findAll();
    }

    public Medecin findById(Long id){
        return medecinRepository.findById(id).get();
    }
}
