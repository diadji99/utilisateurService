package com.gestionHopital.serv_utilisateur.Utilisateur.controller;

import com.gestionHopital.serv_utilisateur.Authentification.modele.Role;
import com.gestionHopital.serv_utilisateur.Authentification.modele.Utilisateur;
import com.gestionHopital.serv_utilisateur.Authentification.service.UtilisateurService;
import com.gestionHopital.serv_utilisateur.Utilisateur.modele.Administrateur;
import com.gestionHopital.serv_utilisateur.Utilisateur.modele.Medecin;
import com.gestionHopital.serv_utilisateur.Utilisateur.modele.Infirmier;
import com.gestionHopital.serv_utilisateur.Utilisateur.service.AdministrateurService;
import com.gestionHopital.serv_utilisateur.Utilisateur.service.InfirmierService;
import com.gestionHopital.serv_utilisateur.Utilisateur.service.MedecinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/Administrateur")
public class AdministrateurController {

    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MedecinService medecinService;
    @Autowired
    private AdministrateurService administrateurService;
    @Autowired
    private InfirmierService infirmierService;

    @PostMapping("/activerMedecin")
    public String activerMedecin(@RequestParam("id") Long id) {
        medecinService.activerMedecin(id);
        return "redirect:/Administrateur/Accueil";
    }

    @PostMapping("/activerInfirmier")
    public String activerInfirmier(@RequestParam("id") Long id) {
        infirmierService.activerInfirmier(id);
        return "redirect:/Administrateur/Accueil";
    }

    @GetMapping("/Accueil")
    public String accueilAdmin(Model model, Principal principal) {
        // Récupération des données
        Utilisateur admin = utilisateurService.rechercher_Utilisateur(principal.getName());
        List<Medecin> medecins = medecinService.listerMedecins();
        List<Infirmier> infirmiers = infirmierService.listerInfirmiers();

        // Ajout des attributs au modèle
        model.addAttribute("nom", admin.getNom());
        model.addAttribute("prenom", admin.getPrenom().charAt(0));
        model.addAttribute("medecins", medecins);
        model.addAttribute("infirmiers", infirmiers);

        return "admin"; // Nom du template Thymeleaf
    }

    // Ajout d'un nouvel administrateur
    @PostMapping("/ajouterAdministrateur")
    public String ajouterAdministrateur(Administrateur administrateur) {
        // Encodage du mot de passe et initialisation des autres attributs
        String encodedPassword = passwordEncoder.encode("Passer123");
        administrateur.setPassword(encodedPassword);
        administrateur.setDateCreation(new Date());
        administrateur.setActive(true);

        // Attribution du rôle ADMINISTRATEUR
        Role role = utilisateurService.ajouter_Role(new Role("ADMINISTRATEUR"));

        // Sauvegarde de l'administrateur et attribution du rôle
        administrateurService.ajouterAdministrateur(administrateur);
        utilisateurService.ajouter_UtilisateurRoles(administrateur, role);

        return "redirect:/Administrateur/Accueil";
    }

    // Modification d'un administrateur existant
    @PostMapping("/modifierAdministrateur")
    public String modifierAdministrateur(Administrateur administrateur) {
        // Récupération de l'administrateur existant
        Administrateur adminModif = administrateurService.rechercherAdministrateur(administrateur.getId());
        // Mise à jour des informations
        adminModif.setNom(administrateur.getNom());
        adminModif.setPrenom(administrateur.getPrenom());
        adminModif.setNumeroProfessionnel(administrateur.getNumeroProfessionnel());
        // Autres mises à jour éventuelles...
        administrateurService.modifierAdministrateur(adminModif);

        return "redirect:/Administrateur/Accueil"; // redirige vers la page souhaitée
    }

    @PostMapping("/ajouterMedecin")
    public String ajouterMedecin(Medecin medecin) {
        String pasword = passwordEncoder.encode("Passer123");
        medecin.setPassword(pasword); medecin.setDateCreation((new Date())); medecin.setActive(true);
        Role role = utilisateurService.ajouter_Role(new Role("MEDECIN"));
        utilisateurService.ajouter_Role(role);
        medecinService.ajouterMedecin(medecin);
        return "redirect:/Administrateur/Accueil";
    }

    @PostMapping("/modifierMedecin")
    public String modifierMedecin(Medecin medecin) {
        Medecin med_modif=medecinService.rechercher(medecin.getId());
        med_modif.setNumeroProfessionnel(medecin.getNumeroProfessionnel());
        med_modif.setNom(medecin.getNom()); med_modif.setPrenom(medecin.getPrenom());
        med_modif.setSpecialite(medecin.getSpecialite());
        medecinService.modifierMedecin(med_modif);
        return "redirect:/Administrateur/Accueil";
    }

    @PostMapping("/ajouterInfirmier")
    public String ajouterInfirmier(Infirmier infirmier) {
        String password = passwordEncoder.encode("Passer123");
        infirmier.setPassword(password);
        infirmier.setDateCreation(new Date());
        infirmier.setActive(true);
        Role role = utilisateurService.ajouter_Role(new Role("INFIRMIER"));
        utilisateurService.ajouter_Role(role);
        infirmierService.ajouterInfirmier(infirmier);
        return "redirect:/Administrateur/Accueil";
    }

    @PostMapping("/modifierInfirmier")
    public String modifierInfirmier(Infirmier infirmier) {
        Infirmier infirmierModif = infirmierService.rechercher(infirmier.getId());
        infirmierModif.setNumeroProfessionnel(infirmier.getNumeroProfessionnel());
        infirmierModif.setNom(infirmier.getNom());
        infirmierModif.setPrenom(infirmier.getPrenom());
        infirmierModif.setSpecialite(infirmier.getSpecialite());
        infirmierService.modifierInfirmier(infirmierModif);
        return "redirect:/Administrateur/Accueil";
    }

}
