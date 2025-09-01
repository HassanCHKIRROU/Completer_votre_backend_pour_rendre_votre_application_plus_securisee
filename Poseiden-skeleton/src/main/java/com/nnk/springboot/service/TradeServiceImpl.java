package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


/** Implémentation des CRUD de l'entité  Trade coté service. */
@Service
@Transactional
public class TradeServiceImpl implements TradeService {
	
	

    private final TradeRepository repo;

    //Constructeur pour injecter le repo.
    public TradeServiceImpl(TradeRepository repo) {
        this.repo = repo;
    }

    
    //Récupère tous les Trades existants
    @Override
    @Transactional(readOnly = true)
    public List<Trade> findAll() {
        return repo.findAll();
    }

    
    //Recherche un Trade par son id.
    @Override
    @Transactional(readOnly = true)
    public Optional<Trade> findById(Integer id) {
        return repo.findById(id);
    }

    
    //Sauvegarde un nouveau Trade ou met à jour un existant
    @Override
    public Trade save(Trade trade) {
        return repo.save(trade);
    }

    
    //Mettre à jour un trade identifié par son id.
    @Override
    public Trade update(Integer id, Trade t) {
        Trade existing = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Trade introuvable id=" + id));

        // Champs manipulés par les formulaires
        existing.setAccount(t.getAccount());
        existing.setType(t.getType());
        existing.setBuyQuantity(t.getBuyQuantity());

        // Si besoin d'autres champs :
        // existing.setSellQuantity(t.getSellQuantity());
        // existing.setBuyPrice(t.getBuyPrice());
        // existing.setSellPrice(t.getSellPrice());
        // existing.setBenchmark(t.getBenchmark());
        // existing.setTradeDate(t.getTradeDate());
        // existing.setSecurity(t.getSecurity());
        // existing.setStatus(t.getStatus());
        // existing.setTrader(t.getTrader());
        // existing.setBook(t.getBook());
        // existing.setCreationName(t.getCreationName());
        // existing.setCreationDate(t.getCreationDate());
        // existing.setRevisionName(t.getRevisionName());
        // existing.setRevisionDate(t.getRevisionDate());
        // existing.setDealName(t.getDealName());
        // existing.setDealType(t.getDealType());
        // existing.setSourceListId(t.getSourceListId());
        // existing.setSide(t.getSide());

        return repo.save(existing);
    }

    
    //Supprimer un Trade identifié par son id 
    @Override
    public void deleteById(Integer id) {
        if (!repo.existsById(id)) {
            throw new IllegalArgumentException("Trade introuvable id=" + id);
        }
        repo.deleteById(id);
    }
}
