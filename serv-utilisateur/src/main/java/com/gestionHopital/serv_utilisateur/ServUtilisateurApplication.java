package com.gestionHopital.serv_utilisateur;

import com.gestionHopital.serv_utilisateur.Authentification.modele.Role;
import com.gestionHopital.serv_utilisateur.Authentification.repository.RoleRepository;
import com.gestionHopital.serv_utilisateur.Authentification.service.UtilisateurService;
import com.gestionHopital.serv_utilisateur.Utilisateur.modele.Administrateur;
import com.gestionHopital.serv_utilisateur.Utilisateur.service.AdministrateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class ServUtilisateurApplication {
	@Autowired
	private RoleRepository roleRepository;
	public static void main(String[] args) {
		SpringApplication.run(ServUtilisateurApplication.class, args);
	}

/*	@Bean
	public CommandLineRunner initData(AdministrateurService administrateurService,
									  UtilisateurService utilisateurService,
									  PasswordEncoder passwordEncoder) {
		return args -> {
			Role administrateur = utilisateurService.ajouter_Role(new Role("ADMINISTRATEUR"));
			Role vacataire = utilisateurService.ajouter_Role(new Role("Vacataire"));
			Role chefDepartement = utilisateurService.ajouter_Role(new Role("ChefDepartement"));
			String password = passwordEncoder.encode("Passer123");
			administrateur = roleRepository.save(administrateur);
			Administrateur user_1 = new Administrateur();
			user_1.setNom("WADE");
			user_1.setPrenom("Mame Diadji");
			user_1.setUsername("mdwade@uasz.sn");
			user_1.setPassword(password);
			user_1.setDateCreation(new Date());
			user_1.setActive(true);
			user_1.setNumeroProfessionnel("M20250001");
			user_1.getRoles().add(administrateur);
			utilisateurService.ajouter_UtilisateurRoles(user_1);
		};
	}*/
}
