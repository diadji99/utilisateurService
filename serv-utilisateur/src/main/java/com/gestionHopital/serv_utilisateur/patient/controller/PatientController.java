package com.gestionHopital.serv_utilisateur.patient.controller;


import com.gestionHopital.serv_utilisateur.patient.model.Patient;
import com.gestionHopital.serv_utilisateur.patient.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patients")
public class PatientController {
    @Autowired
    private PatientService patientService;

    @GetMapping("")
    public ResponseEntity<?> getAllPatients(){
        return ResponseEntity.ok(patientService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPatient(@PathVariable Long id){
        Patient existing = patientService.findById(id);
        if(existing != null){
            return ResponseEntity.ok(existing);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("patient inexistant");
        }
    }
    @PostMapping("/ajouter")
    public ResponseEntity<?> ajouter(@RequestBody Patient patient){
        Patient create = patientService.create(patient);
        if(create != null){
            return ResponseEntity.ok(patient);
        }else{
            return ResponseEntity.status(HttpStatus.CONFLICT).body("le patient existe");
        }
    }


    @PutMapping("/{id}/modifier")
    public ResponseEntity<?> modfier(@PathVariable Long id,@RequestBody Patient patient){
        Patient existing = patientService.findById(id);
        if(existing != null){
            if(patient.getSexe() != null){
                existing.setSexe(patient.getSexe());
            }
            if(patient.getAdresse() != null){
                existing.setAdresse(patient.getAdresse());
            }
            if(patient.getTelephone() != null){
                existing.setTelephone(patient.getTelephone());
            }
            if(patient.getDateNaissance() != null){
                existing.setDateNaissance(patient.getDateNaissance());
            }
            Patient update = patientService.update(existing);
            if(update != null){
                return ResponseEntity.ok(update);
            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("erreur lors de la mise en jour");
            }
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("patient inexistant");
        }
    }
    @DeleteMapping("/{id}/supprimer")
    public ResponseEntity<?> supprimer(@PathVariable Long id) {
        Patient existing = patientService.findById(id);
        if (existing != null) {
            patientService.delete(existing);
            return ResponseEntity.ok("patient supprimé");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("patient inexistant");
        }
    }
}
