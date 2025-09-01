package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


/** Implémentation des CRUD pour l'entité RuleName coté service. */
@Service
@Transactional
public class RuleNameServiceImpl implements RuleNameService {
	
	

    private final RuleNameRepository repo;

    //Constructeur pour injecter le service
    public RuleNameServiceImpl(RuleNameRepository repo) {
        this.repo = repo;
    }

    
    //Recupère la liste de tous les RuleName existants
    @Override
    @Transactional(readOnly = true)
    public List<RuleName> findAll() {
        return repo.findAll();
    }

    
    //Recherche un RulName par son identifiant
    @Override
    @Transactional(readOnly = true)
    public Optional<RuleName> findById(Integer id) {
        return repo.findById(id);
    }

    
    //Enregistrer un nouveau RuleName ou met à joir un existant
    @Override
    public RuleName save(RuleName ruleName) {
        return repo.save(ruleName);
    }

    
    //Mettre à jour un RuleName identifié par son id
    @Override
    public RuleName update(Integer id, RuleName rn) {
        RuleName existing = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("RuleName introuvable id=" + id));
        existing.setName(rn.getName());
        existing.setDescription(rn.getDescription());
        existing.setJson(rn.getJson());
        existing.setTemplate(rn.getTemplate());
        existing.setSql(rn.getSql());
        existing.setSqlPart(rn.getSqlPart());
        return repo.save(existing);
    }

    
    //Supprimer un ruleName identifié par son id.
    @Override
    public void deleteById(Integer id) {
        if (!repo.existsById(id)) {
            throw new IllegalArgumentException("RuleName introuvable id=" + id);
        }
        repo.deleteById(id);
    }
}
