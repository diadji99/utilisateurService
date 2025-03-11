package com.gestionHopital.serv_utilisateur.hospitalisation.controller;

import com.gestionHopital.serv_utilisateur.Authentification.modele.Utilisateur;
import com.gestionHopital.serv_utilisateur.Authentification.service.UtilisateurService;
import com.gestionHopital.serv_utilisateur.gestionBatiment.lit.model.Lit;
import com.gestionHopital.serv_utilisateur.gestionBatiment.lit.service.LitService;
import com.gestionHopital.serv_utilisateur.hospitalisation.model.Hospitalisation;
import com.gestionHopital.serv_utilisateur.hospitalisation.service.HospitalisationService;
import com.gestionHopital.serv_utilisateur.patient.model.Patient;
import com.gestionHopital.serv_utilisateur.patient.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/hospitalisations")
public class HospitalisationController {

    @Autowired
    private HospitalisationService hospitalisationService;
    @Autowired
    private LitService litService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private UtilisateurService utilisateurService;
    @GetMapping("")
    public String getAllHospitalisations(Model model, Principal principal) {
        model.addAttribute("hospitalisations", hospitalisationService.findAll());
        Utilisateur user= utilisateurService.rechercher_Utilisateur(principal.getName());
        model.addAttribute("prenom",user.getPrenom().charAt(0));
        model.addAttribute("nom",user.getNom());
        return "hospitalisation_list";
    }


    @PostMapping("/ajouter")
    public String ajouter(@RequestParam Long idPatient, @RequestParam Long idLit, @ModelAttribute Hospitalisation hospitalisation, RedirectAttributes redirectAttributes) {
        Patient patient = patientService.findById(idPatient);
        Lit lit = litService.findById(idLit);
        if (patient != null && lit != null) {
            hospitalisation.setPatient(patient);
            hospitalisation.setLit(lit);
            Hospitalisation create = hospitalisationService.create(hospitalisation);
            if (create != null) {
                redirectAttributes.addFlashAttribute("success", "Hospitalisation ajoutée avec succès");
            } else {
                redirectAttributes.addFlashAttribute("error", "Erreur lors de la création de l'hospitalisation");
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "Patient ou lit introuvable");
        }
        return "redirect:/hospitalisations";
    }

    @PostMapping("/{id}/modifier")
    public String modifier(@PathVariable Long id, @RequestParam Long idPatient, @RequestParam Long idLit, @ModelAttribute Hospitalisation hospitalisation, RedirectAttributes redirectAttributes) {
        Hospitalisation existing = hospitalisationService.findById(id);
        if (existing != null) {
            Patient patient = patientService.findById(idPatient);
            Lit lit = litService.findById(idLit);
            if (patient != null && lit != null) {
                existing.setLit(lit);
                existing.setPatient(patient);
                existing.setDateEntree(hospitalisation.getDateEntree());
                existing.setDateSortie(hospitalisation.getDateSortie());
                Hospitalisation update = hospitalisationService.update(existing);
                if (update != null) {
                    redirectAttributes.addFlashAttribute("success", "Hospitalisation mise à jour avec succès");
                } else {
                    redirectAttributes.addFlashAttribute("error", "Erreur lors de la mise à jour de l'hospitalisation");
                }
            } else {
                redirectAttributes.addFlashAttribute("error", "Patient ou lit introuvable");
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "Hospitalisation inexistante");
        }
        return "redirect:/hospitalisations";
    }

    @PostMapping("/{id}/supprimer")
    public String supprimer(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Hospitalisation hospitalisation = hospitalisationService.findById(id);
        if (hospitalisation != null) {
            hospitalisationService.delete(hospitalisation);
            redirectAttributes.addFlashAttribute("success", "Hospitalisation supprimée avec succès");
        } else {
            redirectAttributes.addFlashAttribute("error", "Hospitalisation introuvable");
        }
        return "redirect:/hospitalisations";
    }
}
