package com.gestionHopital.serv_utilisateur.dossiermedical.service;

import com.gestionHopital.serv_utilisateur.dossiermedical.model.DossierMedical;
import com.gestionHopital.serv_utilisateur.dossiermedical.repository.DossierMedicalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DossierMedicalService {
    @Autowired
    private DossierMedicalRepository dossierMedicalRepository;

    public DossierMedical create(DossierMedical dossierMedical){
        DossierMedical existing = dossierMedicalRepository.findByNumeroDossier(dossierMedical.getNumeroDossier());
        if(existing != null){
            return null;
        }
        return dossierMedicalRepository.save(dossierMedical);
    }

    public DossierMedical update(DossierMedical update){
        DossierMedical existing = dossierMedicalRepository.findById(update.getId()).get();
        if(existing != null){
            return dossierMedicalRepository.save(update);
        }else {
            return null;
        }
    }

    public void delete(DossierMedical dossierMedical){
        dossierMedicalRepository.delete(dossierMedical);
    }

    public List<DossierMedical> findALl(){
        return dossierMedicalRepository.findAll();
    }

    public DossierMedical findById(Long numeroDossier){
        return dossierMedicalRepository.findByNumeroDossier(numeroDossier);
    }
}
