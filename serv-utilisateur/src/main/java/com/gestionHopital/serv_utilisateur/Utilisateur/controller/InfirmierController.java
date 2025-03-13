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
    public String ajouter(Infirmier infirmier){
        infirmier.setPassword(passwordEncoder.encode(infirmier.getPassword()));
        infirmierService.ajouterInfirmier(infirmier);
        return "redirect:/Administrateur/infirmiers";
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
        existing.setPassword(passwordEncoder.encode(update.getPassword()));

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
