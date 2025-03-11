package com.gestionHopital.serv_utilisateur.gestionBatiment.salle.controller;

import com.gestionHopital.serv_utilisateur.Authentification.modele.Utilisateur;
import com.gestionHopital.serv_utilisateur.Authentification.service.UtilisateurService;
import com.gestionHopital.serv_utilisateur.gestionBatiment.salle.model.Salle;
import com.gestionHopital.serv_utilisateur.gestionBatiment.salle.service.SalleService;
import com.gestionHopital.serv_utilisateur.gestionBatiment.service.model.ServiceF;
import com.gestionHopital.serv_utilisateur.gestionBatiment.service.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/Administrateur/salles")
public class SalleController {

    @Autowired
    private SalleService salleService;

    @Autowired
    private ServiceService serviceService;
    @Autowired
    private UtilisateurService utilisateurService;

    @GetMapping("")
    public String getAll(Model model, Principal principal) {
        Utilisateur user= utilisateurService.rechercher_Utilisateur(principal.getName());
        model.addAttribute("prenom",user.getPrenom().charAt(0));
        model.addAttribute("nom",user.getNom());
        model.addAttribute("salles", salleService.findAll());
        return "admin_Salle";
    }

    @PostMapping("/ajouter")
    public String ajouter(@RequestParam Long id, @ModelAttribute Salle salle, RedirectAttributes redirectAttributes) {
        ServiceF service = serviceService.findById(id);
        if (service != null) {
            salle.setServiceF(service);
            Salle created = salleService.create(salle);
            if (created != null) {
                redirectAttributes.addFlashAttribute("success", "Salle ajoutée avec succès");
            } else {
                redirectAttributes.addFlashAttribute("error", "La salle existe déjà");
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "Service inexistant");
        }
        return "redirect:/Administrateur/salles";
    }

    @PostMapping("/{id}/modifier")
    public String modifier(@PathVariable Long id, @ModelAttribute Salle update, @RequestParam Long service, RedirectAttributes redirectAttributes) {
        Salle existing = salleService.findById(id);
        if (existing != null) {
            ServiceF serviceF = serviceService.findById(service);
            if (serviceF != null) {
                update.setServiceF(serviceF);
            }
            Salle updated = salleService.update(existing, update);
            if (updated != null) {
                redirectAttributes.addFlashAttribute("success", "Salle mise à jour avec succès");
            } else {
                redirectAttributes.addFlashAttribute("error", "Échec lors de la mise à jour");
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "Salle introuvable");
        }
        return "redirect:/Administrateur/salles";
    }

    @PostMapping("/{id}/supprimer")
    public String supprimer(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Salle salle = salleService.findById(id);
        if (salle != null) {
            salleService.delete(salle);
            redirectAttributes.addFlashAttribute("success", "Salle supprimée avec succès");
        } else {
            redirectAttributes.addFlashAttribute("error", "Salle inexistante");
        }
        return "redirect:/Administrateur/salles";
    }
}
