package com.nnk.springboot.config;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * {@code UserDetailsService} chargé de récupérer un utilisateur 
 * depuis la base de données à partir de son {@code username}.
 * <p>
 * Utilisé par Spring Security pendant l'authentification .
 */

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
    

    private final UserRepository repo;
  
    //Constructeur pour injecter le repository
    public CustomUserDetailsService(UserRepository repo) {
        this.repo = repo;
    }

    
    /**
     * Charge un utilisateur par son nom d'utilisateur (username).
     *
     * @param username identifiant saisi dans le formulaire de login
     * @return un {@link CustomUserDetails} adapté à Spring Security
     * @throws UsernameNotFoundException si aucun utilisateur ne correspond
     */
    
    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	
        User user = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable: " + username));
        
        return new CustomUserDetails(user);
    }
}
