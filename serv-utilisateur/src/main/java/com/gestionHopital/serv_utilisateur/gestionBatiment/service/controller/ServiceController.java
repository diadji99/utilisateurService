package com.gestionHopital.serv_utilisateur.gestionBatiment.service.controller;

import com.gestionHopital.serv_utilisateur.Authentification.modele.Utilisateur;
import com.gestionHopital.serv_utilisateur.Authentification.service.UtilisateurService;
import com.gestionHopital.serv_utilisateur.Utilisateur.modele.Medecin;
import com.gestionHopital.serv_utilisateur.Utilisateur.service.MedecinService;
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
    @Autowired
    private MedecinService medecinService;
    @GetMapping("")
    public String getServices(Model model, Principal principal) {
        Utilisateur user= utilisateurService.rechercher_Utilisateur(principal.getName());
        model.addAttribute("prenom",user.getPrenom().charAt(0));
        model.addAttribute("nom",user.getNom());
        List<Batiment> batiments = batimentService.findAll();
        List<Medecin> medecins = medecinService.findAll();
        List<ServiceF> serviceList = serviceService.findAll();

        model.addAttribute("medecins", medecins);
        model.addAttribute("services", serviceList);
        model.addAttribute("batiments", batiments);
        return "admin_Service"; // Vue Thymeleaf
    }

    @PostMapping("/ajouter")
    public String ajouterService(@RequestParam(name = "idBatiment", required = false) Long idBatiment,
                                 @ModelAttribute ServiceF service,
                                 RedirectAttributes redirectAttributes) {
        System.out.println("ID du bâtiment reçu : " + idBatiment);
        Batiment batiment = batimentService.findByid(idBatiment);
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

    @PostMapping("/modifier")
    public String modifierService(@ModelAttribute ServiceF update, @RequestParam("idBatiment") Long idBatiment, RedirectAttributes redirectAttributes) {
        ServiceF existing = serviceService.findById(update.getId());
        if (existing != null) {
            Batiment bat = batimentService.findByid(idBatiment);
            if (bat != null) {
                existing.setBatiment(bat);
            }
            if (!existing.getNom().equals(update.getNom()) && !update.getNom().isEmpty()) {
                existing.setNom(update.getNom());
            }
            ServiceF updated = serviceService.update(existing);
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


    @PostMapping("/supprimer")
    public String supprimerService(@RequestParam(name = "id", required = true) Long id, RedirectAttributes redirectAttributes) {
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
