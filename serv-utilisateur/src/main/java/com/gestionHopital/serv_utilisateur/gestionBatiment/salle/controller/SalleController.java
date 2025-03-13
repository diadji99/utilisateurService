package com.gestionHopital.serv_utilisateur.gestionBatiment.salle.controller;

import com.gestionHopital.serv_utilisateur.Authentification.modele.Utilisateur;
import com.gestionHopital.serv_utilisateur.Authentification.service.UtilisateurService;
import com.gestionHopital.serv_utilisateur.gestionBatiment.batiment.model.Batiment;
import com.gestionHopital.serv_utilisateur.gestionBatiment.batiment.service.BatimentService;
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
import java.util.List;

@Controller
@RequestMapping("/Administrateur/salles")
public class SalleController {

    @Autowired
    private SalleService salleService;

    @Autowired
    private ServiceService serviceService;
    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private BatimentService batimentService;

    @GetMapping("")
    public String getAll(Model model, Principal principal) {
        Utilisateur user= utilisateurService.rechercher_Utilisateur(principal.getName());
        List<ServiceF> serviceFS = serviceService.findAll();

        model.addAttribute("serviceFS", serviceFS);
        model.addAttribute("prenom",user.getPrenom().charAt(0));
        model.addAttribute("nom",user.getNom());
        model.addAttribute("salles", salleService.findAll());
        return "admin_Salle";
    }

    @PostMapping("/ajouter")
    public String ajouter(@RequestParam("service_id") Long serviceId, @ModelAttribute Salle salle, RedirectAttributes redirectAttributes) {
        ServiceF service = serviceService.findById(serviceId);
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


    @PostMapping("/modifier")
    public String modifier(@ModelAttribute Salle update,
                           @RequestParam(name = "service_id", required = true) Long serviceId,
                           RedirectAttributes redirectAttributes) {
        Salle existing = salleService.findById(update.getId());
        if (existing != null) {
            ServiceF serviceF = serviceService.findById(serviceId);
            existing.setNumero(update.getNumero());
            if (serviceF != null) {
                existing.setServiceF(serviceF);
            }
            Salle updated = salleService.update(existing);
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

    @PostMapping("/supprimer")
    public String supprimer(@RequestParam(name = "id", required = true) Long id, RedirectAttributes redirectAttributes) {
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
