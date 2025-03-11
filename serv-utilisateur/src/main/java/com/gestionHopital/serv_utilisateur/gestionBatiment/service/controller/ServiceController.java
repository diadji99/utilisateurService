package com.gestionHopital.serv_utilisateur.gestionBatiment.service.controller;

import com.gestionHopital.serv_utilisateur.Authentification.modele.Utilisateur;
import com.gestionHopital.serv_utilisateur.Authentification.service.UtilisateurService;
import com.gestionHopital.serv_utilisateur.gestionBatiment.batiment.model.Batiment;
import com.gestionHopital.serv_utilisateur.gestionBatiment.batiment.service.BatimentService;
import com.gestionHopital.serv_utilisateur.gestionBatiment.service.model.ServiceF;
import com.gestionHopital.serv_utilisateur.gestionBatiment.service.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/Administrateur/services")
public class ServiceController {

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private BatimentService batimentService;
    @Autowired
    private UtilisateurService utilisateurService;
    @GetMapping("")
    public String getServices(Model model, Principal principal) {
        Utilisateur user= utilisateurService.rechercher_Utilisateur(principal.getName());
        model.addAttribute("prenom",user.getPrenom().charAt(0));
        model.addAttribute("nom",user.getNom());

        List<ServiceF> serviceList = serviceService.findAll();
        model.addAttribute("services", serviceList);
        return "admin_Service"; // Vue Thymeleaf
    }

    @PostMapping("/ajouter")
    public String ajouterService(@RequestParam Long id, @ModelAttribute ServiceF service, RedirectAttributes redirectAttributes) {
        Batiment batiment = batimentService.findByid(id);
        if (batiment != null) {
            service.setBatiment(batiment);
            ServiceF created = serviceService.create(service);
            if (created != null) {
                redirectAttributes.addFlashAttribute("success", "Service ajouté avec succès");
            } else {
                redirectAttributes.addFlashAttribute("error", "Le service existe déjà");
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "Le bâtiment n'existe pas");
        }
        return "redirect:/Administrateur/services";
    }

    @PostMapping("/{id}/modifier")
    public String modifierService(@PathVariable Long id, @ModelAttribute ServiceF update, @RequestParam Long batiment, RedirectAttributes redirectAttributes) {
        ServiceF existing = serviceService.findById(id);
        if (existing != null) {
            Batiment bat = batimentService.findByid(batiment);
            if (bat != null) {
                update.setBatiment(bat);
            }
            ServiceF updated = serviceService.update(existing, update);
            if (updated != null) {
                redirectAttributes.addFlashAttribute("success", "Service mis à jour avec succès");
            } else {
                redirectAttributes.addFlashAttribute("error", "Échec lors de la mise à jour");
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "Le service n'existe pas");
        }
        return "redirect:/Administrateur/services";
    }

    @PostMapping("/{id}/supprimer")
    public String supprimerService(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        ServiceF service = serviceService.findById(id);
        if (service != null) {
            serviceService.delete(service);
            redirectAttributes.addFlashAttribute("success", "Service supprimé avec succès");
        } else {
            redirectAttributes.addFlashAttribute("error", "Ce service n'existe pas");
        }
        return "redirect:/Administrateur/services";
    }
}
