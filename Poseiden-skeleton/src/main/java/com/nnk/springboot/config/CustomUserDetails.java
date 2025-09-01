package com.nnk.springboot.config;

import com.nnk.springboot.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Adaptateur entre l'entité {@link com.nnk.springboot.domain.User} et
 * l'interface {@link org.springframework.security.core.userdetails.UserDetails}
 * attendue par Spring Security.
 * 
 * Les rôles applicatifs "ADMIN"/"USER" sont normalisés en autorités "ROLE_ADMIN"/"ROLE_USER".
 */
public class CustomUserDetails implements UserDetails {

	
	
    private final User user;
    
    //Constructeur pour injecter le repo
    public CustomUserDetails(User u) {
        this.user = u;
    }

    

    /**
     * Retourne la collection d'autorisations de l'utilisateur.
     * Si l'attribut {@code role} ne commence pas par "ROLE_", le préfixe est ajouté.
     *
     * @return une liste contenant l'autorité principale de l'utilisateur
     */
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // attend des rôles "ROLE_USER", "ROLE_ADMIN"
        String role = user.getRole();
        String normalized = role.startsWith("ROLE_") ? role : "ROLE_" + role;
        return List.of(new SimpleGrantedAuthority(normalized));
    }

    
    
    /**
     * Implémentation de l'interface UserDetails pour fournir les informations d'authentification
     * @return le mot de passe de l'utilisateur
     */
    @Override public String getPassword() {
    	return user.getPassword();
    }
    
    
    /**
     * Retourne le nom d'utilisateur utilisé pour l'authentification
     * 
     * @return le nom d'utilisateur de l'utilisateur
     */
    @Override public String getUsername() {
    	return user.getUsername();
    }
    
    
    
    /**
     * Indique si le compte de l'utilisateur n'a pas expiré
     * 
     * @return true si le compte n'est pas expiré, false sinon
     */
    @Override public boolean isAccountNonExpired() {
    	return true;
    }
    
    
    
    /**
     * Indique si le compte de l'utilisateur n'est pas verrouillé
     * 
     * @return true si le compte n'est pas verrouillé, false sinon
     */
    @Override public boolean isAccountNonLocked() {
    	return true; 
    }
    
    
    
    /**
     * Indique si les credentials de l'utilisateur n'ont pas expiré
     * 
     * @return true si les credentials ne sont pas expirés, false sinon
     */
    @Override public boolean isCredentialsNonExpired() {
    	return true; 
    }
   
    
    
    /**
     * Indique si le compte de l'utilisateur est activé
     * 
     * @return true si le compte est activé, false sinon
     */
    @Override public boolean isEnabled() { 
    	return true;
    }

    
    
    /**
     * Retourne l'objet User du domaine métier
     * 
     * @return l'instance User représentant l'utilisateur métier
     */
    public User getDomainUser() {
    	return user;
    }
}
