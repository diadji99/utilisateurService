package com.gestionHopital.serv_utilisateur.Authentification.security;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableAutoConfiguration
public class SecurityConfig {

    private static final String[] FOR_ADMINISTRATEUR = {"/Administrateur/**"};

    private static final String[] FOR_MEDECIN = {"/Medecin/**"};

    private static final String[] FOR_INFIRMIER = {"/Infirmier/**"};

    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorizeHttpRequests) ->
                        authorizeHttpRequests
                                .requestMatchers("/js/**", "/css/**").permitAll()
                                .requestMatchers("/login**", "/logout**").permitAll()
                                .requestMatchers("/h2/**").permitAll()
                                .requestMatchers("/profil").authenticated()
                                .requestMatchers(FOR_ADMINISTRATEUR).hasAuthority("ADMINISTRATEUR")
                                .requestMatchers(FOR_MEDECIN).hasAuthority("MEDECIN")
                                .requestMatchers(FOR_INFIRMIER).hasAuthority("INFIRMIER")
                                .requestMatchers("/api/**").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin((formlogin) ->
                        formlogin
                                .usernameParameter("username")
                                .passwordParameter("password")
                                .loginPage("/login")
                                .defaultSuccessUrl("/")
                                .successForwardUrl("/")
                                .permitAll()
                )
                .csrf(csrf -> csrf.ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/api/**")));
        return http.build();

    }
}
