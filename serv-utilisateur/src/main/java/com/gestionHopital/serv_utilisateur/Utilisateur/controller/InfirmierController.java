package com.gestionHopital.serv_utilisateur.Utilisateur.controller;

import com.gestionHopital.serv_utilisateur.Authentification.modele.Utilisateur;
import com.gestionHopital.serv_utilisateur.Authentification.service.UtilisateurService;
import com.gestionHopital.serv_utilisateur.Utilisateur.modele.Infirmier;
import com.gestionHopital.serv_utilisateur.Utilisateur.service.InfirmierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/Administrateur/infirmiers")
public class InfirmierController {
    @Autowired
    private InfirmierService infirmierService;
    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @GetMapping("")
    public String getAllInfirmier(Principal principal, Model model){
        Utilisateur user = utilisateurService.rechercher_Utilisateur(principal.getName());
        model.addAttribute("prenom",user.getPrenom());
        model.addAttribute("nom",user.getNom());
        model.addAttribute("infirmiers",infirmierService.listerInfirmiers());
        return "admin_Infirmier";
    }

    @PostMapping("/ajouter")
    public String ajouter(Infirmier infirmier, RedirectAttributes redirectAttributes) {
        Infirmier existing = infirmierService.findByNumeroProfessionel(infirmier.getNumeroProfessionnel());

        if (existing != null) {
            // L'infirmier avec ce numéro professionnel existe déjà
            redirectAttributes.addFlashAttribute("error", "❌ Un infirmier avec ce même identifiant existe déjà.");
            return "redirect:/Administrateur/infirmiers";
        } else {
            // L'infirmier n'existe pas, on peut le créer
            infirmier.setPassword(passwordEncoder.encode("Passer123"));
            Infirmier created = infirmierService.ajouterInfirmier(infirmier);

            if (created != null) {
                // Succès de l'ajout
                redirectAttributes.addFlashAttribute("succes", "✅ Infirmier créé avec succès !");
            } else {
                // Erreur lors de la création
                redirectAttributes.addFlashAttribute("error", "⚠️ Échec lors de la création de l'infirmier.");
            }

            return "redirect:/Administrateur/infirmiers";
        }
    }

    @PostMapping("/modifier")
    public String modifier(@ModelAttribute Infirmier update, RedirectAttributes redirectAttributes) {
        Infirmier existing = infirmierService.rechercher(update.getId());

        if (existing == null) {
            redirectAttributes.addFlashAttribute("error", "❌ Infirmier inexistant !");
            return "redirect:/Administrateur/infirmiers";
        }

        existing.setPrenom(update.getPrenom());
        existing.setNom(update.getNom());
        existing.setNumeroProfessionnel(update.getNumeroProfessionnel());
        existing.setUsername(update.getUsername());
        Infirmier updated = infirmierService.modifierInfirmier(existing);
        if (updated != null) {
            redirectAttributes.addFlashAttribute("succes", "✅ Infirmier mis à jour avec succès !");
        } else {
            redirectAttributes.addFlashAttribute("error", "⚠️ Échec lors de la mise à jour !");
        }

        return "redirect:/Administrateur/infirmiers";
    }
    @PostMapping("/supprimer")
    public String supprimer(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        Infirmier infirmier = infirmierService.rechercher(id);

        if (infirmier != null) {
            infirmierService.delete(infirmier);
            redirectAttributes.addFlashAttribute("succes", "✅ Infirmier supprimé avec succès !");
        } else {
            redirectAttributes.addFlashAttribute("error", "❌ Infirmier inexistant !");
        }

        return "redirect:/Administrateur/infirmiers";
    }

}
