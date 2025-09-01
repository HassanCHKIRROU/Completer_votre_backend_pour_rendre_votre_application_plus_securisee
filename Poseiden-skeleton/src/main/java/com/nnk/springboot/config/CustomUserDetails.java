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
 * <p>
 * Les rôles applicatifs "ADMIN"/"USER" sont normalisés en autorités "ROLE_ADMIN"/"ROLE_USER".
 */
public class CustomUserDetails implements UserDetails {

	
	//Injection de repo
    private final User user;

    public CustomUserDetails(User u) {
        this.user = u;
    }

    

    /**
     * Retourne la collection d'autorisations de l'utilisateur.
     * <p>
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

    
    
    @Override public String getPassword() {
    	return user.getPassword();
    }
    
    @Override public String getUsername() {
    	return user.getUsername();
    }
    
    @Override public boolean isAccountNonExpired() {
    	return true;
    }
    
    @Override public boolean isAccountNonLocked() {
    	return true; 
    }
    
    @Override public boolean isCredentialsNonExpired() {
    	return true; 
    }
    
    @Override public boolean isEnabled() { 
    	return true;
    }

    
    
    public User getDomainUser() {
    	return user;
    }
}
