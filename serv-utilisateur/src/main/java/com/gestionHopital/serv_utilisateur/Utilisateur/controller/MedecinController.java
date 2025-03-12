package com.gestionHopital.serv_utilisateur.Utilisateur.controller;
import com.gestionHopital.serv_utilisateur.Authentification.modele.Utilisateur;
import com.gestionHopital.serv_utilisateur.Authentification.service.UtilisateurService;
import com.gestionHopital.serv_utilisateur.Utilisateur.modele.Medecin;
import com.gestionHopital.serv_utilisateur.Utilisateur.service.MedecinService;
import com.gestionHopital.serv_utilisateur.gestionBatiment.bureau.model.Bureau;
import com.gestionHopital.serv_utilisateur.gestionBatiment.bureau.service.BureauService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/Administrateur/medecins")
public class MedecinController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MedecinService medecinService;
    @Autowired
    private BureauService bureauService;
    @Autowired
    private UtilisateurService utilisateurService;
    @RequestMapping("")
    public String getAllMedecin(Model model, Principal principal){
        Utilisateur utilisateur = utilisateurService.rechercher_Utilisateur(principal.getName());
        model.addAttribute("prenom", utilisateur.getPrenom().charAt(0));
        model.addAttribute("nom", utilisateur.getNom());
        model.addAttribute("bureaux",bureauService.findAll());
        model.addAttribute("medecins", medecinService.findAll());
        return "admin_Medecin";
    }

    @PostMapping("/ajouter")
    public String ajouter(@ModelAttribute Medecin medecin, @RequestParam(required = false) Long idBureau, RedirectAttributes redirectAttributes) {
        if (idBureau == null) {
            redirectAttributes.addFlashAttribute("error", "Veuillez sélectionner un bureau.");
            return "redirect:/Administrateur/medecins";
        }

        Bureau bureau = bureauService.findById(idBureau);
        if (bureau == null) {
            redirectAttributes.addFlashAttribute("error", "Bureau inexistant.");
            return "redirect:/Administrateur/medecins";
        }

        if (bureau.getMedecin() != null) {
            redirectAttributes.addFlashAttribute("error", "Ce bureau est déjà attribué à un autre médecin.");
            return "redirect:/Administrateur/medecins";
        }
        medecin.setPassword(passwordEncoder.encode("Passer123"));
        medecin.setBureau(bureau);
        Medecin created = medecinService.create(medecin);

        if (created != null) {
            redirectAttributes.addFlashAttribute("success", "Médecin ajouté avec succès.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Ce médecin existe déjà.");
        }

        return "redirect:/Administrateur/medecins";
    }

    @PostMapping("/{id}/activer")
    public String activer(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Medecin existing = medecinService.findById(id);
        if (existing == null) {
            redirectAttributes.addFlashAttribute("error", "Médecin inexistant.");
        } else {
            Medecin active = medecinService.activer(existing);
            if (active != null) {
                redirectAttributes.addFlashAttribute("success", "Médecin activé avec succès.");
            } else {
                redirectAttributes.addFlashAttribute("error", "Échec de l'activation du médecin.");
            }
        }
        return "redirect:/Administrateur/medecins";
    }

    @PostMapping("/modifier")
    public String modifier(@RequestParam(required = false) Long idBureau, @ModelAttribute Medecin update, RedirectAttributes redirectAttributes) {
        Medecin existing = medecinService.findById(update.getId());
        if (existing == null) {
            redirectAttributes.addFlashAttribute("error", "Médecin inexistant.");
            return "redirect:/Administrateur/medecins";
        }

        if (idBureau == null) {
            redirectAttributes.addFlashAttribute("error", "Veuillez sélectionner un bureau.");
            return "redirect:/Administrateur/medecins";
        }

        Bureau bureau = bureauService.findById(idBureau);
        if (bureau == null) {
            redirectAttributes.addFlashAttribute("error", "Bureau inexistant.");
            return "redirect:/Administrateur/medecins";
        }

        if (bureau.getMedecin() != null && !bureau.getMedecin().equals(existing)) {
            redirectAttributes.addFlashAttribute("error", "Ce bureau est déjà attribué à un autre médecin.");
            return "redirect:/Administrateur/medecins";
        }

        // Mise à jour des informations du médecin
        existing.setBureau(bureau);
        existing.setNom(update.getNom());
        existing.setPrenom(update.getPrenom());
        Medecin updated = medecinService.update(existing);
        if (updated != null) {
            redirectAttributes.addFlashAttribute("success", "Médecin mis à jour avec succès.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Échec de la mise à jour.");
        }

        return "redirect:/Administrateur/medecins";
    }

    @PostMapping("/supprimer")
    public String supprimer(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        if (medecinService.findById(id) == null) {
            redirectAttributes.addFlashAttribute("error", "Médecin inexistant.");
        } else {
            medecinService.delete(medecinService.findById(id));
            redirectAttributes.addFlashAttribute("success", "Médecin supprimé avec succès.");
        }
        return "redirect:/Administrateur/medecins";
    }
}

