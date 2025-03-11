package com.gestionHopital.serv_utilisateur.gestionBatiment.service.controller;

import com.gestionHopital.serv_utilisateur.gestionBatiment.batiment.model.Batiment;
import com.gestionHopital.serv_utilisateur.gestionBatiment.batiment.service.BatimentService;
import com.gestionHopital.serv_utilisateur.gestionBatiment.service.model.ServiceF;
import com.gestionHopital.serv_utilisateur.gestionBatiment.service.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/Administrateur/services")
public class ServiceController {

    @Autowired
    private ServiceService serviceService;
    @Autowired
    private BatimentService batimentService;
    @RequestMapping("")
    public ResponseEntity<?> getServices(){
        List<ServiceF> serviceList = serviceService.findAll();
        return ResponseEntity.ok(serviceList);
    }

    @RequestMapping("/{id}")
    public ResponseEntity<?> getService(@PathVariable Long id){
        ServiceF service = serviceService.findById(id);
        if(service == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("le service n'existe pas");
        }else{
            return ResponseEntity.ok(service);
        }
    }

    @RequestMapping("/{id}/ajouter")
    public ResponseEntity<?> ajouterService(@PathVariable Long id, @RequestBody ServiceF service){
        Batiment batiment = batimentService.findByid(id);
        if(batiment != null){
            service.setBatiment(batiment);
            ServiceF created = serviceService.create(service);
            if(created == null){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Le service existe déja");
            }else{
                return ResponseEntity.ok(created);
            }
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Le batiment n'existe pas");
        }
    }

    @RequestMapping("/{id}/modifier")
    public ResponseEntity<?> modifierService(@PathVariable Long id,@RequestBody ServiceF update,@RequestParam Long batiment){
        ServiceF existing = serviceService.findById(id);
        if(existing != null){
            if(batiment != null){
                Batiment bat = batimentService.findByid(batiment);
                update.setBatiment(bat);
            }
            ServiceF updated = serviceService.update(existing,update);
            if (updated != null){
                return ResponseEntity.ok(updated);
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("le service n'existe pas");
            }
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Le service n'existe pas");
        }
    }

    @RequestMapping("/{id}/supprimer")
    public ResponseEntity<?> supprimerService(@PathVariable Long id){
        ServiceF service = serviceService.findById(id);
        if(service != null){
            serviceService.delete(service);
            return ResponseEntity.ok("Service supprimé");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ce service n'existe pas");
        }
    }
}
