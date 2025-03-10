package com.gestionHopital.serv_utilisateur.Authentification.controller;

import com.gestionHopital.serv_utilisateur.Authentification.modele.Utilisateur;
import com.gestionHopital.serv_utilisateur.Authentification.service.UtilisateurService;
import com.gestionHopital.serv_utilisateur.Utilisateur.modele.Administrateur;
import com.gestionHopital.serv_utilisateur.Utilisateur.modele.Infirmier;
import com.gestionHopital.serv_utilisateur.Utilisateur.modele.Medecin;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value="/login")
    public String index() {return "connexion";}

    @RequestMapping("/")
    public String login(Principal principal) {
        // Le problème vient de cette méthode
        String url = "login";

        if(principal != null) { // Ajouter une vérification de nullité
            Utilisateur utilisateur = utilisateurService.rechercher_Utilisateur(principal.getName());

            if(utilisateur != null && !utilisateur.getRoles().isEmpty()) {
                String role = utilisateur.getRoles().get(0).getRole().toUpperCase();

                url = switch (role) {
                    case "ADMINISTRATEUR" -> "redirect:/Administrateur/Accueil";
                    case "MEDECIN" -> "redirect:/Medecin/Accueil";
                    case "INFIRMIER" -> "redirect:/Infirmier/Accueil";
                    default -> "redirect:/login?error=role_invalide";
                };
            }
        }
        return url;
    }

    @RequestMapping(value = "/logout")
    public String logOutAndRedirectToLoginPage(Authentication authentication,
                                               HttpServletRequest request,
                                               HttpServletResponse response) {
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/login?logout=true";
    }

    @GetMapping("/profil")
    public String profilUtilisateur(Model model, Principal principal) {
        Utilisateur utilisateur = utilisateurService.rechercher_Utilisateur(principal.getName());
        model.addAttribute("utilisateur", utilisateur);
        return "profil";
    }

    @PostMapping("/profil")
    public String updateProfil(
            @RequestParam Long id,
            @RequestParam String nom,
            @RequestParam String prenom,
            @RequestParam String username,
            @RequestParam(required = false) String numeroProfessionnel, // Champ commun aux 3 rôles
            @RequestParam(required = false) String specialite, // Champ pour Medecin/Infirmier
            @RequestParam(required = false) String currentPassword,
            @RequestParam(required = false) String newPassword,
            @RequestParam(required = false) String confirmPassword,
            Principal principal,
            RedirectAttributes redirectAttributes) {

        Utilisateur existingUser = utilisateurService.rechercher_Utilisateur(principal.getName());

        // Mise à jour des champs communs
        existingUser.setNom(nom);
        existingUser.setPrenom(prenom);
        existingUser.setUsername(username);

        // Mise à jour conditionnelle des sous-classes
        if(existingUser instanceof Administrateur admin) {
            admin.setNumeroProfessionnel(numeroProfessionnel);
        }
        else if(existingUser instanceof Medecin medecin) {
            medecin.setNumeroProfessionnel(numeroProfessionnel);
            medecin.setSpecialite(specialite);
        }
        else if(existingUser instanceof Infirmier infirmier) {
            infirmier.setNumeroProfessionnel(numeroProfessionnel);
            infirmier.setSpecialite(specialite);
        }

        // Gestion du mot de passe
        if (StringUtils.hasText(currentPassword)) {
            if (!passwordEncoder.matches(currentPassword, existingUser.getPassword())) {
                redirectAttributes.addFlashAttribute("error", "Mot de passe actuel incorrect");
                return "redirect:/profil";
            }

            if (!newPassword.equals(confirmPassword)) {
                redirectAttributes.addFlashAttribute("error", "Les nouveaux mots de passe ne correspondent pas");
                return "redirect:/profil";
            }

            existingUser.setPassword(passwordEncoder.encode(newPassword));
        }

        utilisateurService.updateUtilisateur(existingUser);
        redirectAttributes.addFlashAttribute("success", "Profil mis à jour avec succès");
        return "redirect:/profil";
    }
}
