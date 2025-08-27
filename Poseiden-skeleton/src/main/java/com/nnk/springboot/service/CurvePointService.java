package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;

import java.util.List;
import java.util.Optional;

/** Service métier  pour CurvePoint. */
public interface CurvePointService {
	
    List<CurvePoint> findAll();
    Optional<CurvePoint> findById(Integer id);
    CurvePoint save(CurvePoint curvePoint);
    CurvePoint update(Integer id, CurvePoint curvePoint);
    void deleteById(Integer id);
}
