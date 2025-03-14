package com.gestionHopital.serv_utilisateur.gestionBatiment.lit.controller;

import com.gestionHopital.serv_utilisateur.Authentification.modele.Utilisateur;
import com.gestionHopital.serv_utilisateur.Authentification.service.UtilisateurService;
import com.gestionHopital.serv_utilisateur.gestionBatiment.lit.model.EtatLit;
import com.gestionHopital.serv_utilisateur.gestionBatiment.lit.model.Lit;
import com.gestionHopital.serv_utilisateur.gestionBatiment.lit.service.LitService;
import com.gestionHopital.serv_utilisateur.gestionBatiment.salle.model.Salle;
import com.gestionHopital.serv_utilisateur.gestionBatiment.salle.service.SalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/Administrateur/lits")
public class LitController {

    @Autowired
    private LitService litService;

    @Autowired
    private SalleService salleService;

    @Autowired
    private UtilisateurService utilisateurService;

    @GetMapping("")
    public String getLits(Model model, Principal principal) {
        Utilisateur user = utilisateurService.rechercher_Utilisateur(principal.getName());
        model.addAttribute("prenom", user.getPrenom().charAt(0));
        model.addAttribute("nom", user.getNom());
        model.addAttribute("salles",salleService.findAll());
        model.addAttribute("lits", litService.findAll());
        return "admin_Lit";
    }

    @PostMapping("/ajouter")
    public String ajouter(@RequestParam(name = "idSalle", required = false) Long idSalle,
                          @ModelAttribute Lit lit,
                          RedirectAttributes redirectAttributes) {
        if (idSalle != null) {
            Salle salle = salleService.findById(idSalle);
            if (salle != null) {
                lit.setSalle(salle);
                litService.addLit(salle);
            }
        }
        lit.setEtat(EtatLit.DISPONIBLE);
        Lit created = litService.create(lit);
        if (created != null) {
            redirectAttributes.addFlashAttribute("success", "Lit ajouté avec succès");
        } else {
            redirectAttributes.addFlashAttribute("error", "Le lit existe déjà");
        }

        return "redirect:/Administrateur/lits";
    }


    @PostMapping("/modifier")
    public String modifier(
            @RequestParam(name = "id") Long id,
            @ModelAttribute Lit update,
            @RequestParam(name = "salle", required = false) Long salle,
            RedirectAttributes redirectAttributes
    ) {
        Lit existing = litService.findById(id);
        if (existing != null) {
            if(salle != null){
                Salle salle1 = salleService.findById(salle);
                existing.setSalle(salle1);
            }
            existing.setType(update.getType());
            existing.setNumero(update.getNumero());
            existing.setEtat(update.getEtat());
            Lit updated = litService.update(existing);
            if (updated != null) {
                redirectAttributes.addFlashAttribute("success", "Lit mis à jour avec succès");
            } else {
                redirectAttributes.addFlashAttribute("error", "La mise à jour a échoué");
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "Lit inexistant");
        }
        return "redirect:/Administrateur/lits";
    }

    @PostMapping("/supprimer")
    public String supprimer(@RequestParam(name = "id",required = true) Long id, RedirectAttributes redirectAttributes) {
        Lit lit = litService.findById(id);
        if (lit != null) {
            litService.delete(lit);
            redirectAttributes.addFlashAttribute("success", "Lit supprimé avec succès");
        } else {
            redirectAttributes.addFlashAttribute("error", "Lit inexistant");
        }
        return "redirect:/Administrateur/lits";
    }
}
