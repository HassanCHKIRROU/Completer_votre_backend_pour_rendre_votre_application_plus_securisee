package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


/** Implémentation des CRUD pour l'entité BidList coté service . */
@Service
@Transactional
public class BidListServiceImpl implements BidListService {

	
	
    private final BidListRepository repo;

    //Constructeur pour injecter le repository
    public BidListServiceImpl(BidListRepository repo) {
        this.repo = repo;
    }

    
    //Récupère la liste de tous les BidList existants.
    @Override
    @Transactional(readOnly = true)
    public List<BidList> findAll() {
        return repo.findAll();
    }

    
    //Recherche un BidList par son identifiant Id
    @Override
    @Transactional(readOnly = true)
    public Optional<BidList> findById(Integer id) {
        return repo.findById(id);
    }

    
    //Sauvegarde un nouveau BidList ou met à jour un existant
    @Override
    public BidList save(BidList bid) {
        return repo.save(bid);
    }

    
    //Met à jour un BidList existant identifié par son id.
    @Override
    public BidList update(Integer id, BidList bid) {
        BidList existing = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("BidList introuvable id=" + id));
        // On met à jour uniquement les champs éditables depuis le formulaire
        existing.setAccount(bid.getAccount());
        existing.setType(bid.getType());
        existing.setBidQuantity(bid.getBidQuantity());
        // On garantit l'id
        existing.setId(id);
        return repo.save(existing);
    }

    
    //Supprime un BidList par son identifiant Id
    @Override
    public void deleteById(Integer id) {
        if (!repo.existsById(id)) {
            throw new IllegalArgumentException("BidList introuvable id=" + id);
        }
        repo.deleteById(id);
    }
}
