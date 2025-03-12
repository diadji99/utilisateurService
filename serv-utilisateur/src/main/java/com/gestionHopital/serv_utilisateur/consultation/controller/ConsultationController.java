package com.gestionHopital.serv_utilisateur.consultation.controller;
import com.gestionHopital.serv_utilisateur.Authentification.modele.Utilisateur;
import com.gestionHopital.serv_utilisateur.Authentification.service.UtilisateurService;
import com.gestionHopital.serv_utilisateur.Utilisateur.modele.Medecin;
import com.gestionHopital.serv_utilisateur.Utilisateur.service.MedecinService;
import com.gestionHopital.serv_utilisateur.consultation.model.Consultation;
import com.gestionHopital.serv_utilisateur.consultation.service.ConsultationService;
import com.gestionHopital.serv_utilisateur.gestionBatiment.service.model.ServiceF;
import com.gestionHopital.serv_utilisateur.gestionBatiment.service.service.ServiceService;
import com.gestionHopital.serv_utilisateur.patient.model.Patient;
import com.gestionHopital.serv_utilisateur.patient.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;

@Controller
@RequestMapping("/Medecin/consultations")
public class ConsultationController {
    @Autowired
    private ConsultationService consultationService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private MedecinService medecinService;
    @Autowired
    private ServiceService serviceService;
    @Autowired
    private UtilisateurService utilisateurService;
    @GetMapping("")
    public String getAll(Principal principal, Model model){
        Utilisateur user = utilisateurService.rechercher_Utilisateur(principal.getName());
        model.addAttribute("prenom",user.getPrenom());
        model.addAttribute("nom",user.getNom());
        return "consultation";
    }



    @PostMapping("/ajouter")
    public String ajouter(@RequestParam(required = true) Long idMedecin,@RequestParam(required = true) Long idPatient,double prix){
        Patient existingPatient = patientService.findById(idPatient);
        Medecin existingMedecin = medecinService.findById(idMedecin);
        String reponse = "";
        if(existingMedecin != null && existingMedecin != null){
            ServiceF service = existingMedecin.getBureau().getServiceF();
            serviceService.addPatient(service);
            Consultation consultation = new Consultation();
            consultation.setDateConsultation(LocalDate.now());
            consultation.setMedecin(existingMedecin);
            consultation.setPatient(existingPatient);
            consultation.setPrix(prix);
            Consultation create = consultationService.create(consultation);
            if(create != null){
                reponse = "consultation créée";
            }else{
                reponse = "echec lors de la creation de la consultation";
            }
        }else {
            reponse = "medecin ou patient null";
        }
        return "redirect:/Medecin/consultations";
    }


    @PutMapping("/modifier")
    public ResponseEntity<?> modifier(@RequestParam Long id,@RequestParam(required = true) Long idMedecin,@RequestParam(required = true) Long idPatient,@RequestBody Consultation update){
        Consultation existing = consultationService.findById(id);
        if(existing != null){
            Patient existingPatient = patientService.findById(idPatient);
            Medecin existingMedecin = medecinService.findById(idMedecin);
            if(existingMedecin != null && existingPatient != null){
                if(existing.getMedecin() != update.getMedecin()){
                    serviceService.moinPatient(existing.getMedecin().getBureau().getServiceF());
                    serviceService.addPatient(update.getMedecin().getBureau().getServiceF());
                }
                existing.setPatient(existingPatient);existing.setMedecin(existingMedecin);
                existing.setDateConsultation(LocalDate.now());
                Consultation updated = consultationService.update(existing);
                if(updated != null){
                    return ResponseEntity.ok(updated);
                }else{
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("erreur lors de la mise à jour de la consultation");
                }
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Medecin ou patient inexistant");
            }
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("consultation inexistante");
        }
    }

    @PutMapping("/{id}/suivi")
    public ResponseEntity<?> suivi(@PathVariable Long id){
        Consultation consultation = consultationService.suivi(id);

        if(consultation != null){
            return ResponseEntity.ok(consultation);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("consultation inexistante");
        }
    }

    @DeleteMapping("/{id}/supprimer")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Consultation consultation = consultationService.findById(id);
        if(consultation != null){
            consultationService.delete(consultation);
            return ResponseEntity.ok("consultation supprimée");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("consultation inexistante");
        }
    }
}
