package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import java.util.List;
import java.util.Optional;

/** Service métier pour Rating. */
public interface RatingService {
	
	
    List<Rating> findAll();
    
    Optional<Rating> findById(Integer id);
    
    Rating save(Rating rating);
    
    Rating update(Integer id, Rating rating);
    
    void deleteById(Integer id);
}
