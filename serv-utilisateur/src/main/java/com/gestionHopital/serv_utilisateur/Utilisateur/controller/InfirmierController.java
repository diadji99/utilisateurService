package com.gestionHopital.serv_utilisateur.Utilisateur.controller;

import com.gestionHopital.serv_utilisateur.Authentification.modele.Utilisateur;
import com.gestionHopital.serv_utilisateur.Authentification.service.UtilisateurService;
import com.gestionHopital.serv_utilisateur.Utilisateur.modele.Infirmier;
import com.gestionHopital.serv_utilisateur.Utilisateur.service.InfirmierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/Administrateur/infirmiers")
public class InfirmierController {
    @Autowired
    private InfirmierService infirmierService;
    @Autowired
    private UtilisateurService utilisateurService;
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
        infirmierService.ajouterInfirmier(infirmier);
        return "redirect:/Administrateur/infirmiers";
    }

    @PostMapping("/modifier")
    public String modifier(Infirmier update){
        Infirmier existing = infirmierService.rechercher(update.getId());
       String reponse="";
        if(existing != null){
            existing.setPrenom(update.getPrenom());
            existing.setNom(update.getNom());
            existing.setNumeroProfessionnel(update.getNumeroProfessionnel());
            existing.setUsername(update.getUsername());
            existing.setPassword(update.getPassword());
            Infirmier updated = infirmierService.modifierInfirmier(existing);
            if(updated != null){
                reponse = "infirmier mise à jour";
            }else{
                reponse ="echec lors de la mise à jour";
            }
        }else{
            reponse="infirmier inexistant";
        }
        return "redirect:/Administrateur/infirmiers?message="+reponse;
    }
}
