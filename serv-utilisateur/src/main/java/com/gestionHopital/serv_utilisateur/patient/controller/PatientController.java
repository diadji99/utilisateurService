package com.gestionHopital.serv_utilisateur.patient.controller;

import com.gestionHopital.serv_utilisateur.Authentification.modele.Utilisateur;
import com.gestionHopital.serv_utilisateur.Authentification.service.UtilisateurService;
import com.gestionHopital.serv_utilisateur.patient.model.Patient;
import com.gestionHopital.serv_utilisateur.patient.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/Infirmier/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;
    @Autowired
    private UtilisateurService utilisateurService;

    @GetMapping("")
    public String getAllPatients(Model model, Principal principal) {
        Utilisateur user= utilisateurService.rechercher_Utilisateur(principal.getName());
        model.addAttribute("prenom",user.getPrenom().charAt(0));
        model.addAttribute("nom",user.getNom());

        model.addAttribute("patients", patientService.findAll());
        return "patient_list";  // Vue Thymeleaf pour afficher la liste des patients
    }

    @GetMapping("/{id}")
    public String getPatient(@PathVariable Long id, Model model) {
        Patient existing = patientService.findById(id);
        if (existing != null) {
            model.addAttribute("patient", existing);
            return "patient_details";  // Vue Thymeleaf pour afficher les détails du patient
        } else {
            model.addAttribute("error", "Patient inexistant");
            return "error";  // Vue d'erreur Thymeleaf
        }
    }

    @PostMapping("/ajouter")
    public String ajouter(@ModelAttribute Patient patient, RedirectAttributes redirectAttributes) {
        Patient create = patientService.create(patient);
        if (create != null) {
            redirectAttributes.addFlashAttribute("success", "Patient ajouté avec succès");
        } else {
            redirectAttributes.addFlashAttribute("error", "Le patient existe déjà");
        }
        return "redirect:/patients";
    }

    @PostMapping("/{id}/modifier")
    public String modifier(@PathVariable Long id, @ModelAttribute Patient patient, RedirectAttributes redirectAttributes) {
        Patient existing = patientService.findById(id);
        if (existing != null) {
            if (patient.getSexe() != null) {
                existing.setSexe(patient.getSexe());
            }
            if (patient.getAdresse() != null) {
                existing.setAdresse(patient.getAdresse());
            }
            if (patient.getTelephone() != null) {
                existing.setTelephone(patient.getTelephone());
            }
            if (patient.getDateNaissance() != null) {
                existing.setDateNaissance(patient.getDateNaissance());
            }
            Patient update = patientService.update(existing);
            if (update != null) {
                redirectAttributes.addFlashAttribute("success", "Mise à jour du patient réussie");
            } else {
                redirectAttributes.addFlashAttribute("error", "Erreur lors de la mise à jour");
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "Patient inexistant");
        }
        return "redirect:/patients";
    }

    @PostMapping("/{id}/supprimer")
    public String supprimer(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Patient existing = patientService.findById(id);
        if (existing != null) {
            patientService.delete(existing);
            redirectAttributes.addFlashAttribute("success", "Patient supprimé avec succès");
        } else {
            redirectAttributes.addFlashAttribute("error", "Patient inexistant");
        }
        return "redirect:/patients";
    }
}
