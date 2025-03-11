package com.gestionHopital.serv_utilisateur.Authentification.modele;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    @NotNull
    private String password;
    private String nom;
    private String prenom;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;
    private boolean active;
<<<<<<< HEAD
    @ManyToMany(fetch = FetchType.EAGER)
=======
    @OneToMany(fetch = FetchType.EAGER)
>>>>>>> 973a9bd (j'ai modifié le fichier de configuration pour pouvoir creer une base de donnée local et j'ai aussi changé le @ManyToMany en @OneToMany dans utilisateur pour les roles)
    private List<Role> roles;
}
