package com.gestionHopital.serv_utilisateur.patient.service;

import com.gestionHopital.serv_utilisateur.patient.model.Patient;
import com.gestionHopital.serv_utilisateur.patient.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;

    public Patient create(Patient patient){
        Patient existing = patientRepository.findByMatricule(patient.getMatricule());
        if(existing != null){
            return null;
        }
        return patientRepository.save(patient);
    }

    public Patient update(Patient update){
        Patient existing = patientRepository.findById(update.getId()).get();
        if(existing != null){
            return patientRepository.save(update);
        }else {
            return null;
        }
    }

    public void delete(Patient patient){
        patientRepository.delete(patient);
    }

    public List<Patient> findAll(){
        return patientRepository.findAll();
    }

    public Patient findByMatricule(Long matricule){
        return patientRepository.findByMatricule(matricule);
    }

    public Patient findById(Long id){
        return patientRepository.findById(id).get();
    }
}
