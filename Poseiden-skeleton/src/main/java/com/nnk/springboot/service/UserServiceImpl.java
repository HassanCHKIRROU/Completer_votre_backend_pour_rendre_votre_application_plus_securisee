package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Implemente les CRUD de USER coté service
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

	
    private final UserRepository repo;
    private final PasswordEncoder encoder;

    //Constructeur pour injecter lee dependences de repository user et l'encodeur de mot de passe
    public UserServiceImpl(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    
    //Récupère tous les utilisateurs
    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return repo.findAll();
    }

    
    //Récupère un utilisateur identifié par son id.
    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Integer id) {
        return repo.findById(id);
    }

    
  //Récupère un utilisateur identifié par son username.
    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        return repo.findByUsername(username);
    }

    
    // Sauvegarde un nouveau utilisateur ou un utilisateur met à jour
    @Override
    public User save(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return repo.save(user);
    }

    
    //Mettre à jour un utilisateur identifié par son id
    @Override
    public User update(Integer id, User changes) {
        User existing = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User introuvable id=" + id));

        existing.setUsername(changes.getUsername());
        existing.setFullname(changes.getFullname());
        existing.setRole(changes.getRole());

        String raw = changes.getPassword();
        if (raw != null && !raw.isBlank()) {
            existing.setPassword(encoder.encode(raw));
        }
        return repo.save(existing);
    }

    
    //Supprimer un utilisateur identifié par son id.
    @Override
    public void deleteById(Integer id) {
        if (!repo.existsById(id)) {
            throw new IllegalArgumentException("User introuvable id=" + id);
        }
        repo.deleteById(id);
    }
}
