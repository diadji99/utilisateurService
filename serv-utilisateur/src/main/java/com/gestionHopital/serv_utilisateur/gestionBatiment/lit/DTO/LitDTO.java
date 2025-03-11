package com.gestionHopital.serv_utilisateur.gestionBatiment.lit.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class LitDTO {
    private Long id;
    private Long numero;
    private String salle;
}
