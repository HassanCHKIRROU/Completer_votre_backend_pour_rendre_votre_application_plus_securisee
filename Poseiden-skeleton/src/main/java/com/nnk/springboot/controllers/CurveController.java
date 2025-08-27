package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.service.CurvePointService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur MVC pour CurvePoint : liste, ajout, mise à jour, suppression.
 */
@Controller
public class CurveController {
	

    private final CurvePointService service;

    public CurveController(CurvePointService service) {
        this.service = service;
    }

    
    
    
    /** GET /curvePoint/list : affiche la liste */
    @GetMapping("/curvePoint/list")
    public String home(Model model) {
        model.addAttribute("curvePoints", service.findAll());
        return "curvePoint/list";
    }

    
    
    /** GET /curvePoint/add : formulaire d'ajout */
    @GetMapping("/curvePoint/add")
    public String addForm(CurvePoint curvePoint) {
        return "curvePoint/add";
    }

    
    
    /** POST /curvePoint/validate : valider et enregistrer */
    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "curvePoint/add";
        }
        service.save(curvePoint);
        return "redirect:/curvePoint/list";
    }

    
    
    /** GET /curvePoint/update/{id} : pré-remplir le formulaire */
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        CurvePoint cp = service.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid CurvePoint Id: " + id));
        model.addAttribute("curvePoint", cp);
        return "curvePoint/update";
    }

    
    
    /** POST /curvePoint/update/{id} : valider et mettre à jour */
    @PostMapping("/curvePoint/update/{id}")
    public String update(@PathVariable("id") Integer id,
                         @Valid CurvePoint curvePoint,
                         BindingResult result,
                         Model model) {
        if (result.hasErrors()) {
            curvePoint.setId(id);
            return "curvePoint/update";
        }
        service.update(id, curvePoint);
        return "redirect:/curvePoint/list";
    }

    
    
    /** GET /curvePoint/delete/{id} : supprimer puis revenir à la liste */
    @GetMapping("/curvePoint/delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model) {
        service.deleteById(id);
        return "redirect:/curvePoint/list";
    }
}
