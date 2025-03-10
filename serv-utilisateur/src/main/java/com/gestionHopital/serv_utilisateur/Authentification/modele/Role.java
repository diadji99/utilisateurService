package com.gestionHopital.serv_utilisateur.Authentification.modele;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "role")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    private String role;  // Cet attribut doit correspondre exactement à celui utilisé dans la requête du repository
}
