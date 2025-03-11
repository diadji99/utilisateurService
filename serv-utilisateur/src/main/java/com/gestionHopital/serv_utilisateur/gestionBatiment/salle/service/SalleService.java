package com.gestionHopital.serv_utilisateur.gestionBatiment.salle.service;

import com.gestionHopital.serv_utilisateur.gestionBatiment.salle.model.Salle;
import com.gestionHopital.serv_utilisateur.gestionBatiment.salle.repository.SalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SalleService {
    @Autowired
    private SalleRepository salleRepository;

    public Salle create(Salle salle){
        return salleRepository.save(salle);
    }

    public Salle update(Salle existing,Salle update){
        if(existing.getNumero() != update.getNumero()){
            existing.setNumero(update.getNumero());
        }
        if(existing.getServiceF() != update.getServiceF() && update.getServiceF() != null){
            existing.setServiceF(update.getServiceF());
        }
        return salleRepository.save(existing);
    }

    public void delete(Salle salle){
        salleRepository.delete(salle);
    }

    public Salle findById(Long id){
        return salleRepository.findById(id).get();
    }

    public List<Salle> findAll(){
        return salleRepository.findAll();
    }

}
