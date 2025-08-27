package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;
import java.util.List;
import java.util.Optional;

/** Service métier pour RuleName. */
public interface RuleNameService {
	
	
    List<RuleName> findAll();
    
    Optional<RuleName> findById(Integer id);
    
    RuleName save(RuleName ruleName);
    
    RuleName update(Integer id, RuleName ruleName);
    
    void deleteById(Integer id);
}
