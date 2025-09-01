package com.nnk.springboot.config;

import com.nnk.springboot.config.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration Spring Security (authentification par session-based).
 * <p>
 * - Utilise un PasswordEncoder BCrypt pour vérifier les mots de passe en base.
 * - Protège les routes : /user/** pour ADMIN, le reste authentifié, statiques/publics autorisés.
 * - Expose la page de login par défaut de Spring sur /login (GET) + /login (POST).
 * - Déconnecte via /app-logout (POST) avec invalidation de la session.
 *
 * Configuration Spring Security (session-based).
 */
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

	
	
	
	 /**
     * Crée le bean {@link PasswordEncoder} basé sur BCrypt.
     *
     * @return un encodeur BCrypt utilisé par Spring Security pour comparer les mots de passe
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    
     
    
    /**
     * Construit la chaîne de filtres de sécurité.
     * <ul>
     *  
     *   <li>Autorisations HTTP configurées</li>
     *   <li>Form login par défaut (/login), redirection succès vers /bidList/list</li>
     *   <li>Logout sur /app-logout (POST), redirection vers /login?logout</li>
     * </ul>
     *
     * @param http configuration:  HttpSecurity injectée par Spring
     * @return la {@link SecurityFilterChain} appliquée au contexte web
     * @throws Exception si la construction de la chaîne échoue
     */
        
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            
            .authorizeHttpRequests(registry -> registry
                .requestMatchers("/", "/css/**", "/images/**").permitAll()
                // on laisse /login géré par Spring Security (page par défaut)
                .requestMatchers("/app/error").permitAll()
                .requestMatchers("/user/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )

            // Page de login par défaut de Spring (GET /login + POST /login)
            .formLogin(form -> form
                .defaultSuccessUrl("/bidList/list", true)
                .failureUrl("/login?error")
                .permitAll()
            )

            // On garde l'URL de logout personnalisée (utilisée par les templates)
            .logout(logout -> logout
                .logoutUrl("/app-logout")
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
            );

        return http.build();
    }

    
}
