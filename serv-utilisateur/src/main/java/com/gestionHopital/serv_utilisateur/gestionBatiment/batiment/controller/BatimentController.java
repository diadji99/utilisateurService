package com.gestionHopital.serv_utilisateur.gestionBatiment.batiment.controller;

import com.gestionHopital.serv_utilisateur.Authentification.modele.Utilisateur;
import com.gestionHopital.serv_utilisateur.Authentification.service.UtilisateurService;
import com.gestionHopital.serv_utilisateur.gestionBatiment.batiment.model.Batiment;
import com.gestionHopital.serv_utilisateur.gestionBatiment.batiment.service.BatimentService;
import com.gestionHopital.serv_utilisateur.gestionBatiment.lit.model.Lit;
import com.gestionHopital.serv_utilisateur.gestionBatiment.lit.service.LitService;
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
@RequestMapping("/Administrateur/batiments")
public class BatimentController {

    @Autowired
    private BatimentService batimentService;
    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private ServiceService serviceService;
    @Autowired
    private LitService litService;
    @Autowired
    private SalleService salleService;

    @GetMapping("/Accueil")
    public String accueilBatiment(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login"; // Rediriger vers login si non connecté
        }

        Utilisateur admin = utilisateurService.rechercher_Utilisateur(principal.getName());
        List<Batiment> batiments = batimentService.findAll();
        List<ServiceF> services = serviceService.findAll();
        List<Lit> lits = litService.findAll();
        List<Salle> salles = salleService.findAll();

        // Ajouter les données au modèle
        model.addAttribute("nom", admin.getNom());
        model.addAttribute("prenom", admin.getPrenom().charAt(0));
        model.addAttribute("batiments", batiments);
        model.addAttribute("services", services);
        model.addAttribute("lits", lits);
        model.addAttribute("salles", salles);

        return "admin_Batiment";
    }

    @PostMapping("/ajouter")
    public String ajouterBatiment(@ModelAttribute Batiment batiment, RedirectAttributes redirectAttributes) {
        Batiment create = batimentService.create(batiment);
        if (create != null) {
            redirectAttributes.addFlashAttribute("succes", "Bâtiment créé avec succès !");
        } else {
            redirectAttributes.addFlashAttribute("error", "Erreur : Ce bâtiment existe déjà !");
        }
        return "redirect:/Administrateur/batiments/Accueil";
    }

    @PostMapping("/modifier")
    public String modifierBatiment( @ModelAttribute Batiment update, RedirectAttributes redirectAttributes) {
        Batiment existing = batimentService.findByid(update.getId());
        if (existing != null) {
            existing.setNom(update.getNom());
            Batiment updated = batimentService.update(existing);
            if (updated != null) {
                redirectAttributes.addFlashAttribute("succes", "Mise à jour réussie !");
            } else {
                redirectAttributes.addFlashAttribute("error", "Erreur lors de la mise à jour.");
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "Bâtiment introuvable.");
        }
        return "redirect:/Administrateur/batiments/Accueil";
    }

    @RequestMapping("/supprimer")
    public String supprimerBatiment(@RequestParam(name = "id", required = true) Long id, RedirectAttributes redirectAttributes) {
        Batiment existing = batimentService.findByid(id);
        if (existing != null) {
            batimentService.delete(existing);
            redirectAttributes.addFlashAttribute("succes", "Bâtiment supprimé avec succès !");
        } else {
            redirectAttributes.addFlashAttribute("error", "Bâtiment introuvable.");
        }
        return "redirect:/Administrateur/batiments/Accueil";
    }

}
