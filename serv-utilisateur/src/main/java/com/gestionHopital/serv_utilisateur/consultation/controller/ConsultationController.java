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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String ajouter(@RequestParam(required = true) Long idMedecin, @RequestParam(required = true) Long idPatient, double prix, RedirectAttributes redirectAttributes) {
        Patient existingPatient = patientService.findById(idPatient);
        Medecin existingMedecin = medecinService.findById(idMedecin);

        if (existingMedecin != null && existingPatient != null) {
            ServiceF service = existingMedecin.getBureau().getServiceF();
            if (service != null) {
                serviceService.addPatient(service);
                Consultation consultation = new Consultation();
                consultation.setDateConsultation(LocalDate.now());
                consultation.setMedecin(existingMedecin);
                consultation.setPatient(existingPatient);
                consultation.setPrix(prix);
                Consultation created = consultationService.create(consultation);

                if (created != null) {
                    redirectAttributes.addFlashAttribute("success", "Consultation créée avec succès");
                } else {
                    redirectAttributes.addFlashAttribute("error", "Échec lors de la création de la consultation");
                }
            } else {
                redirectAttributes.addFlashAttribute("error", "Service non trouvé pour ce médecin");
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "Médecin ou patient inexistant");
        }

        return "redirect:/Medecin/consultations";
    }


    @PostMapping("/modifier")
    public String modifier(@RequestParam Long id, @RequestParam(required = true) Long idMedecin, @RequestParam(required = true) Long idPatient, @RequestBody Consultation update, RedirectAttributes redirectAttributes) {
        Consultation existing = consultationService.findById(id);
        if (existing != null) {
            Medecin existingMedecin = medecinService.findById(idMedecin);
            Patient existingPatient = patientService.findById(idPatient);

            if (existingMedecin != null && existingPatient != null) {
                // Si le médecin a changé
                if (!existing.getMedecin().getId().equals(update.getMedecin().getId())) {
                    serviceService.moinPatient(existing.getMedecin().getBureau().getServiceF());
                    serviceService.addPatient(update.getMedecin().getBureau().getServiceF());
                }
                existing.setPatient(existingPatient);
                existing.setMedecin(existingMedecin);
                existing.setDateConsultation(LocalDate.now());
                Consultation updated = consultationService.update(existing);

                if (updated != null) {
                    redirectAttributes.addFlashAttribute("success", "Consultation mise à jour avec succès");
                } else {
                    redirectAttributes.addFlashAttribute("error", "Erreur lors de la mise à jour de la consultation");
                }
            } else {
                redirectAttributes.addFlashAttribute("error", "Médecin ou patient non trouvé");
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "Consultation inexistante");
        }

        return "redirect:/Medecin/consultations";
    }
    /*@PutMapping("/{id}/suivi")
    public ResponseEntity<?> suivi(@PathVariable Long id){
        Consultation consultation = consultationService.suivi(id);

        if(consultation != null){
            return ResponseEntity.ok(consultation);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("consultation inexistante");
        }
    }
*/
    @PostMapping("/supprimer")
    public String delete(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        String reponse = "";
        Consultation consultation = consultationService.findById(id);

        if (consultation != null) {
            consultationService.delete(consultation);
            // Message de succès
            redirectAttributes.addFlashAttribute("success", "Consultation supprimée avec succès");
        } else {
            // Message d'erreur
            redirectAttributes.addFlashAttribute("error", "Consultation inexistante");
        }

        return "redirect:/Medecin/consultations";
    }

}
