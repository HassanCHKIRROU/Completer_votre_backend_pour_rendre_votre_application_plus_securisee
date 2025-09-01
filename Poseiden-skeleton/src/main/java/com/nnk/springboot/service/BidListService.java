package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import java.util.List;
import java.util.Optional;

/** Service métier simple pour BidList. */
public interface BidListService {
	
	
    List<BidList> findAll();
    
    Optional<BidList> findById(Integer id);
    
    BidList save(BidList bid);
    
    BidList update(Integer id, BidList bid);
    
    void deleteById(Integer id);
    
}
