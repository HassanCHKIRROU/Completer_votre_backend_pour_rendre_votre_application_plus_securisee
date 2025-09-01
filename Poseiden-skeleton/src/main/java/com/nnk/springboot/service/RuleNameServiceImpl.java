package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


/** Implémentation simple du service RuleName. */
@Service
@Transactional
public class RuleNameServiceImpl implements RuleNameService {
	

    private final RuleNameRepository repo;

    public RuleNameServiceImpl(RuleNameRepository repo) {
        this.repo = repo;
    }

    
    
    @Override
    @Transactional(readOnly = true)
    public List<RuleName> findAll() {
        return repo.findAll();
    }

    
    
    @Override
    @Transactional(readOnly = true)
    public Optional<RuleName> findById(Integer id) {
        return repo.findById(id);
    }

    
    
    @Override
    public RuleName save(RuleName ruleName) {
        return repo.save(ruleName);
    }

    
    
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

    
    
    @Override
    public void deleteById(Integer id) {
        if (!repo.existsById(id)) {
            throw new IllegalArgumentException("RuleName introuvable id=" + id);
        }
        repo.deleteById(id);
    }
}
