package com.gestionHopital.serv_utilisateur.dossiermedical.controller;

import com.gestionHopital.serv_utilisateur.dossiermedical.model.DossierMedical;
import com.gestionHopital.serv_utilisateur.dossiermedical.service.DossierMedicalService;
import com.gestionHopital.serv_utilisateur.patient.model.Patient;
import com.gestionHopital.serv_utilisateur.patient.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dossierMedical")
public class DossierMedicalController {
    @Autowired
    private DossierMedicalService dossierMedicalService;
    @Autowired
    private PatientService patientService;

    @GetMapping("")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(dossierMedicalService.findALl());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBureau(@PathVariable Long id){
        DossierMedical existing = dossierMedicalService.findById(id);
        if(existing != null){
            return ResponseEntity.ok(existing);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dossier medical inexistant");
        }
    }

    @PostMapping("/{matricule}/ajouter")
    public ResponseEntity<?> ajouter(@PathVariable Long matricule,@RequestBody DossierMedical dossierMedical){
        Patient existing = patientService.findById(matricule);
        if(existing != null){
            dossierMedical.setPatient(existing);
            DossierMedical create = dossierMedicalService.create(dossierMedical);
            if(create != null){
                return ResponseEntity.ok(create);
            }else{
                return ResponseEntity.status(HttpStatus.CONFLICT).body("le dossier existe déja");
            }
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("patient inexistant veuillez d'abord enregistrer le patient");
        }
    }

    @PutMapping("/{id}/modifier")
    public ResponseEntity<?> modifier(@PathVariable Long id,@RequestParam Long idPatient,@RequestBody DossierMedical update){
        DossierMedical existing = dossierMedicalService.findById(id);
        if(existing != null){
            Patient patient = patientService.findById(idPatient);
            if(patient != null){
                update.setPatient(patient);
                DossierMedical updated = dossierMedicalService.update(update);
                if(updated != null){
                    return ResponseEntity.ok(updated);
                }else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("erreur lors de la mise à jour");
                }
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient inexistant");
            }
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("doosier medical inexistant");
        }
    }

    @DeleteMapping("/{id}/supprimer")
    public ResponseEntity<?> supprimer(@PathVariable Long id){
        DossierMedical dossierMedical = dossierMedicalService.findById(id);
        if(dossierMedical != null){
            dossierMedical.setPatient(null);
            DossierMedical update = dossierMedicalService.update(dossierMedical);
            dossierMedicalService.delete(update);
            return ResponseEntity.ok("dossier medical supprimé avec succés");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("dossier introuvable");
        }
    }

}
