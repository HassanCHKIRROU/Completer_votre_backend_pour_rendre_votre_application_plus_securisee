package com.nnk.springboot.service;


import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


/** Implémentation du service pour la gestion des CRUD de  l'entité CurvePoint. */
@Service
@Transactional
public class CurvePointServiceImpl implements CurvePointService {
	

	
    private final CurvePointRepository repo;

    //Constructeur pour injecter le repository
    public CurvePointServiceImpl(CurvePointRepository repo) {
        this.repo = repo;
    }

    
    //Récupère la liste de tous les CurvePoint existants.
    @Override
    @Transactional(readOnly = true)
    public List<CurvePoint> findAll() {
        return repo.findAll();
    }

    
    //Recherche un CurvePoint par son identifiant.
    @Override
    @Transactional(readOnly = true)
    public Optional<CurvePoint> findById(Integer id) {
        return repo.findById(id);
    }

    
   
    //Sauvegarde un nouveau CurvePoint ou met à jour un existant
    @Override
    public CurvePoint save(CurvePoint curvePoint) {
        return repo.save(curvePoint);
    }

    
    
    //Met à jour un CurvePoint existant identifié par son id.
    @Override
    public CurvePoint update(Integer id, CurvePoint cp) {
        CurvePoint existing = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("CurvePoint introuvable id=" + id));
        
        // Les formulaires ne modifient que term & value ; on met à jour ces champs
        existing.setTerm(cp.getTerm());
        existing.setValue(cp.getValue());
        // Si tu veux gérer d'autres champs (curveId, asOfDate), ajoute-les ici :
         existing.setCurveId(cp.getCurveId());
        // existing.setAsOfDate(cp.getAsOfDate());
        return repo.save(existing);
    }

    
    
    //Supprime un CurvePoint par son identifiant.
    @Override
    public void deleteById(Integer id) {
        if (!repo.existsById(id)) {
            throw new IllegalArgumentException("CurvePoint introuvable id=" + id);
        }
        repo.deleteById(id);
    }
}
