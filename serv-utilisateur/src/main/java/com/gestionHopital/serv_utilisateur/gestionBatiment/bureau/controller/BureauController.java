package com.gestionHopital.serv_utilisateur.gestionBatiment.bureau.controller;

import com.gestionHopital.serv_utilisateur.Authentification.modele.Utilisateur;
import com.gestionHopital.serv_utilisateur.Authentification.service.UtilisateurService;
import com.gestionHopital.serv_utilisateur.gestionBatiment.bureau.model.Bureau;
import com.gestionHopital.serv_utilisateur.gestionBatiment.bureau.service.BureauService;
import com.gestionHopital.serv_utilisateur.gestionBatiment.service.model.ServiceF;
import com.gestionHopital.serv_utilisateur.gestionBatiment.service.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/Administrateur/bureaux")
public class BureauController {

    @Autowired
    private BureauService bureauService;

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private UtilisateurService utilisateurService;

    @GetMapping("")
    public String getAll(Principal principal, Model model) {
        Utilisateur user = utilisateurService.rechercher_Utilisateur(principal.getName());
        model.addAttribute("prenom", user.getPrenom().charAt(0));
        model.addAttribute("nom", user.getNom());
        model.addAttribute("bureaux", bureauService.findAll());
        return "admin_Bureau";
    }

    @PostMapping("/{id}/ajouter")
    public String ajouter(@PathVariable Long id, @ModelAttribute Bureau bureau, RedirectAttributes redirectAttributes) {
        ServiceF service = serviceService.findById(id);
        if (service != null) {
            bureau.setServiceF(service);
            Bureau saved = bureauService.create(bureau);
            if (saved != null) {
                redirectAttributes.addFlashAttribute("success", "Bureau enregistré avec succès");
            } else {
                redirectAttributes.addFlashAttribute("error", "Le bureau existe déjà");
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "Le service n'existe pas");
        }
        return "redirect:/Administrateur/bureaux";
    }

    @PostMapping("/{id}/modifier")
    public String modifier(@PathVariable Long id, @ModelAttribute Bureau update, @RequestParam Long service, RedirectAttributes redirectAttributes) {
        Bureau existing = bureauService.findById(id);
        if (existing != null) {
            ServiceF serviceF = serviceService.findById(service);
            update.setServiceF(serviceF);
            Bureau updated = bureauService.update(existing, update);
            if (updated != null) {
                redirectAttributes.addFlashAttribute("success", "Mise à jour réussie");
            } else {
                redirectAttributes.addFlashAttribute("error", "Les modifications n'ont pas été enregistrées");
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "Le bureau n'existe pas");
        }
        return "redirect:/Administrateur/bureaux";
    }

    @PostMapping("/{id}/supprimer")
    public String supprimer(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Bureau bureau = bureauService.findById(id);
        if (bureau != null) {
            bureauService.delete(bureau);
            redirectAttributes.addFlashAttribute("success", "Le bureau a été supprimé avec succès");
        } else {
            redirectAttributes.addFlashAttribute("error", "Bureau inexistant");
        }
        return "redirect:/Administrateur/bureaux";
    }
}
