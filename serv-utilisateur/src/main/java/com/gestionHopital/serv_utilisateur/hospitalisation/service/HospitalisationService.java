package com.gestionHopital.serv_utilisateur.hospitalisation.service;

import com.gestionHopital.serv_utilisateur.hospitalisation.model.Hospitalisation;
import com.gestionHopital.serv_utilisateur.hospitalisation.repository.HospitalisationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HospitalisationService {
    @Autowired
    private HospitalisationRepository hospitalisationRepository;

    public Hospitalisation create(Hospitalisation hospitalisation){
        return hospitalisationRepository.save(hospitalisation);
    }

    public Hospitalisation update(Hospitalisation hospitalisation){
        Hospitalisation existing = hospitalisationRepository.findById(hospitalisation.getId()).get();
        if(existing == null){
            return null;
        }
        return hospitalisationRepository.save(hospitalisation);
    }

    public  void delete(Hospitalisation hospitalisation){
        hospitalisationRepository.delete(hospitalisation);
    }

    public Hospitalisation findById(Long id){
        return hospitalisationRepository.findById(id).get();
    }

    public List<Hospitalisation> findAll(){
        return hospitalisationRepository.findAll();
    }
}
