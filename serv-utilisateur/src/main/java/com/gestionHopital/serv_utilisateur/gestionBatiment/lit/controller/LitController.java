package com.gestionHopital.serv_utilisateur.gestionBatiment.lit.controller;

import com.gestionHopital.serv_utilisateur.gestionBatiment.lit.model.Lit;
import com.gestionHopital.serv_utilisateur.gestionBatiment.lit.service.LitService;
import com.gestionHopital.serv_utilisateur.gestionBatiment.salle.model.Salle;
import com.gestionHopital.serv_utilisateur.gestionBatiment.salle.service.SalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/Administrateur/lits")
public class LitController {
    @Autowired
    private LitService litService;
    @Autowired
    private SalleService salleService;

    @RequestMapping("")
    public ResponseEntity<?> getLits(){
        return ResponseEntity.ok(litService.findAll());
    }

    @RequestMapping("/{id}")
    public ResponseEntity<?> getLit(@PathVariable Long id){
        Lit lit = litService.findById(id);
        if(lit != null){
            return ResponseEntity.ok(lit);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lit introuvable");
        }
    }

    @RequestMapping("/esclaves")
    public ResponseEntity<?> getLitDTOs(){
        return ResponseEntity.ok(litService.getDTOList());
    }

    @RequestMapping("/{id}/esclave")
    public ResponseEntity<?> getLitDTO(@PathVariable Long id){
        return ResponseEntity.ok(litService.getDTO(id));
    }



    @RequestMapping("/ajouter")
    public ResponseEntity<?> ajouter(@RequestParam Long id,@RequestBody Lit lit){
        if(id != null){
            Salle salle = salleService.findById(id);
            if(salle != null){
                lit.setSalle(salle);
            }
        }
        Lit created = litService.create(lit);
        if(created != null){
            return ResponseEntity.ok(created);
        }else{
            return ResponseEntity.status(HttpStatus.CONFLICT).body("lit existant");
        }

    }




    @PutMapping("/{id}/modifier")
    public ResponseEntity<?> modifier(@PathVariable Long id,@RequestBody Lit update,@RequestParam(required = false) Long salle){
        Lit existing = litService.findById(id);
        if(existing != null){
            Salle salle1 = salleService.findById(salle);
            update.setSalle(salle1);
            existing.setType(update.getType());
            existing.setNumero(update.getNumero());
            existing.setSalle(update.getSalle());
            Lit updated = litService.update(existing);
            if(updated != null){
                return ResponseEntity.ok(updated);
            }else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("erreur lors de la mise à jour");
            }
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("lit inexistant");
        }
    }

    @DeleteMapping("/{id}/supprimer")
    public ResponseEntity<?> supprimer(@PathVariable Long id){
        Lit lit = litService.findById(id);
        if(lit != null){
            litService.delete(lit);
            return ResponseEntity.ok("lit supprimé");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("lit introuvable");
        }
    }
}
