package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;

import java.util.List;
import java.util.Optional;

/** Service métier  pour User. */
public interface UserService {
	
	
    List<User> findAll();
    
    Optional<User> findById(Integer id);
    
    Optional<User> findByUsername(String username);
    
    User save(User user);                 // encode le mot de passe
    
    User update(Integer id, User user);   // encode le mot de passe
    
    void deleteById(Integer id);
}
