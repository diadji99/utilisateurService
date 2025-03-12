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
import com.gestionHopital.serv_utilisateur.gestionBatiment.batiment.model.Batiment;
import com.gestionHopital.serv_utilisateur.gestionBatiment.batiment.service.BatimentService;
import com.gestionHopital.serv_utilisateur.gestionBatiment.lit.model.Lit;
import com.gestionHopital.serv_utilisateur.gestionBatiment.lit.service.LitService;
import com.gestionHopital.serv_utilisateur.gestionBatiment.salle.model.Salle;
import com.gestionHopital.serv_utilisateur.gestionBatiment.salle.service.SalleService;
import com.gestionHopital.serv_utilisateur.gestionBatiment.service.model.ServiceF;
import com.gestionHopital.serv_utilisateur.gestionBatiment.service.service.ServiceService;
import com.gestionHopital.serv_utilisateur.patient.model.Patient;
import com.gestionHopital.serv_utilisateur.patient.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
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
    @Autowired
    private BatimentService batimentService;
    @Autowired
    private ServiceService serviceService;
    @Autowired
    private LitService litService;
    @Autowired
    private SalleService salleService;
    @Autowired
    private PatientService patientService;


    @GetMapping("/Accueil")
    public String accueilAdmin(Model model, Principal principal) {
        // Récupération des données
        Utilisateur admin = utilisateurService.rechercher_Utilisateur(principal.getName());
        List<Medecin> medecins = medecinService.findAll();
        List<Infirmier> infirmiers = infirmierService.listerInfirmiers();
        List<Batiment> batiments = batimentService.findAll();
        List<ServiceF> services = serviceService.findAll();
        List<Lit> lits = litService.findAll();
        List<Salle> salles = salleService.findAll();
        List<Patient> patients = patientService.findAll();
        int deces = 0;
        int operations = 0;
        int testlabo = 0;
        int gain = 0;
        model.addAttribute("deces",deces);
        model.addAttribute("operations",operations);
        model.addAttribute("testLabo",testlabo);
        model.addAttribute("gain",gain);
        List<Infirmier> infirmier=new ArrayList<>();
        model.addAttribute("infirmiers",infirmiers);
        model.addAttribute("patients",patients);
        // Ajout des attributs au modèle
        model.addAttribute("nom", admin.getNom());
        model.addAttribute("prenom", admin.getPrenom().charAt(0));
        model.addAttribute("medecins", medecins);
        model.addAttribute("infirmiers", infirmiers);
        model.addAttribute("batiments",batiments);
        model.addAttribute("services",services);
        model.addAttribute("lits",lits);
        model.addAttribute("salles",salles);
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
        utilisateurService.ajouter_UtilisateurRoles(administrateur);

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

    @GetMapping("/profile")
    public String adminProfil(Model model){
        return "admin_profile";
    }
}
