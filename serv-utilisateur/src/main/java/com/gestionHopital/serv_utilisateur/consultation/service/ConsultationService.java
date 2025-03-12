package com.gestionHopital.serv_utilisateur.consultation.service;

import com.gestionHopital.serv_utilisateur.consultation.model.Consultation;
import com.gestionHopital.serv_utilisateur.consultation.repository.ConsultationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ConsultationService {
    @Autowired
    private ConsultationRepository consultationRepository;

    public Consultation create(Consultation consultation){
        return consultationRepository.save(consultation);
    }

    public Consultation update(Consultation update){
        Consultation existing = consultationRepository.findById(update.getId()).get();
        if(existing != null){
            return consultationRepository.save(update);
        }else {
            return null;
        }
    }

    public Consultation suivi(Long id){
        Consultation consultation = consultationRepository.findById(id).get();
        if(consultation != null){
            if(consultation.isSuivi()){
                consultation.setSuivi(false);
            }else{
                consultation.setSuivi(true);
            }
            consultation.setDateConsultation(LocalDate.now());
            return consultationRepository.save(consultation);
        }else {
            return null;
        }


    }

    public  void delete(Consultation consultation){
        consultationRepository.delete(consultation);
    }

    public List<Consultation> findAll(){
        return consultationRepository.findAll();
    }

    public Consultation findById(Long id){
        return consultationRepository.findById(id).get();
    }
}
