package com.gestionHopital.serv_utilisateur.dossiermedical.repository;

import com.gestionHopital.serv_utilisateur.dossiermedical.model.DossierMedical;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
public interface DossierMedicalRepository extends JpaRepository<DossierMedical,Long> {
    public DossierMedical findByNumeroDossier(Long numeroDossier);
}
