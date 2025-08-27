package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/** Implémentation simple du service User (avec encodage du mot de passe). */
@Service
@Transactional
public class UserServiceImpl implements UserService {
	

    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;

    
    public UserServiceImpl(UserRepository repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    
    
    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return repo.findAll();
    }

    
    
    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Integer id) {
        return repo.findById(id);
    }

  
    

    @Override
    public User save(User user) {
        // On encode toujours le mot de passe à la création
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repo.save(user);
    }

    
    
    @Override
    public User update(Integer id, User user) {
        User existing = repo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User introuvable id=" + id));

        existing.setUsername(user.getUsername());
        existing.setFullname(user.getFullname());
        existing.setRole(user.getRole());

        // Si un mot de passe est fourni, on le ré-encode
        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            existing.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        return repo.save(existing);
    }

  /*  
    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(repo.findByUsername(username));
    }
   */ 
    
    
    @Override
    public void deleteById(Integer id) {
        if (!repo.existsById(id)) {
            throw new NoSuchElementException("User introuvable id=" + id);
        }
        repo.deleteById(id);
    }
}
