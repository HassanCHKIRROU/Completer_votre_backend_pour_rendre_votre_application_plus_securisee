package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


/** Implémentation des CRUD pour l'entité  Rating coté service. */
@Service
@Transactional
public class RatingServiceImpl implements RatingService {

	
    private final RatingRepository repo;

    //Constructeur pour injecter le service
    public RatingServiceImpl(RatingRepository repo) {
        this.repo = repo;
    }

    
    //Récupère la liste de tous les Rating existants
    @Override
    @Transactional(readOnly = true)
    public List<Rating> findAll() {
        return repo.findAll();
    }

    
    //Recherche un Rating par son identifiant
    @Override
    @Transactional(readOnly = true)
    public Optional<Rating> findById(Integer id) {
        return repo.findById(id);
    }

    
    //Sauvegarde un nouveau Rating ou met à jour un existant
    @Override
    public Rating save(Rating rating) {
        return repo.save(rating);
    }

    
    //Met à jour un Rating existant identifié par son id
    @Override
    public Rating update(Integer id, Rating rating) {
        Rating existing = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rating introuvable id=" + id));
        // champs utilisés par les formulaires
        existing.setMoodysRating(rating.getMoodysRating());
        existing.setSandPRating(rating.getSandPRating());
        existing.setFitchRating(rating.getFitchRating());
        existing.setOrder(rating.getOrder());
        
        return repo.save(existing);
    }

    
    //Supprimer un Rating identifié par son id
    @Override
    public void deleteById(Integer id) {
        if (!repo.existsById(id)) {
            throw new IllegalArgumentException("Rating introuvable id=" + id);
        }
        repo.deleteById(id);
    }
}
