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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping("")
    public ResponseEntity<?> getAll(Principal principal){
        List<Batiment> batiments = batimentService.findAll();
        return ResponseEntity.ok(batiments);
    }

    @RequestMapping("/Accueil")
    public String accueiBatiment(Model model, Principal principal){
        Utilisateur admin = utilisateurService.rechercher_Utilisateur(principal.getName());
        List<Batiment> batiments = batimentService.findAll();
        List<ServiceF> services = serviceService.findAll();
        List<Lit> lits = litService.findAll();
        List<Salle> salles = salleService.findAll();
        // Ajout des attributs au modèle
        model.addAttribute("nom", admin.getNom());
        model.addAttribute("prenom", admin.getPrenom().charAt(0));

        model.addAttribute("batiments",batiments);
        model.addAttribute("services",services);
        model.addAttribute("lits",lits);
        model.addAttribute("salles",salles);

        return "admin_Batiment";
    }

    @RequestMapping("/{id}")
    public ResponseEntity<?> getBatiment(@PathVariable Long id){
        Batiment batiment = batimentService.findByid(id);
        if(batiment == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Le batiment n'existe pas ou est introuvable");
        }
        return ResponseEntity.ok(batiment);
    }


    @RequestMapping(value = "/ajouter", method = RequestMethod.POST)
    public String ajouterBatiment(@ModelAttribute Batiment batiment) {
        batimentService.create(batiment);
        return "redirect:/Administrateur/batiments/Accueil";
    }

    @RequestMapping("/{id}/modifier")
    public ResponseEntity<?> modifierBatiment(@PathVariable Long id,@RequestBody Batiment update){
        Batiment existing = batimentService.findByid(id);
        if(existing != null){
            return ResponseEntity.ok(batimentService.update(existing,update));
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Le batiment n'existe pas");
        }
    }

    @RequestMapping("/{id}/supprimer")
    public ResponseEntity<?> supprimerBatiment(@PathVariable Long id){
        Batiment existing = batimentService.findByid(id);
        if(existing != null){
            batimentService.delete(existing);
            return ResponseEntity.ok("batiment supprimer");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("batiment n'existe pas");
        }
    }

    @RequestMapping("/{id}/services")
    public ResponseEntity<?> getServices(@PathVariable Long id){
        Batiment batiment = batimentService.findByid(id);
        if(batiment != null){
            return ResponseEntity.ok(batiment.getServiceFS());
        }return ResponseEntity.status(HttpStatus.NOT_FOUND).body("batiment inexistant");
    }

}
