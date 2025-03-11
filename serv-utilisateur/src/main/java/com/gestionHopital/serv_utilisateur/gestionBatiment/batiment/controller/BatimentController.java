package com.gestionHopital.serv_utilisateur.gestionBatiment.batiment.controller;

import com.gestionHopital.serv_utilisateur.gestionBatiment.batiment.model.Batiment;
import com.gestionHopital.serv_utilisateur.gestionBatiment.batiment.service.BatimentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/Administrateur/batiments")
public class BatimentController {
    @Autowired
    private BatimentService batimentService;

    @RequestMapping("")
    public ResponseEntity<?> getAll(){
        List<Batiment> batiments = batimentService.findAll();
        return ResponseEntity.ok(batiments);
    }

    @RequestMapping("/{id}")
    public ResponseEntity<?> getBatiment(@PathVariable Long id){
        Batiment batiment = batimentService.findByid(id);
        if(batiment == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Le batiment n'existe pas ou est introuvable");
        }
        return ResponseEntity.ok(batiment);
    }


    @RequestMapping("/ajouter")
    public ResponseEntity<?> ajouterBatiment(@RequestBody Batiment batiment){
        Batiment created = batimentService.create(batiment);
        if(created == null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Le Batiment existe déja");
        }
        return ResponseEntity.ok(created);
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
