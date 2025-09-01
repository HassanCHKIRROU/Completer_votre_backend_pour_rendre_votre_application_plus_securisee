package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.service.RatingService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class RatingController {

	
	
    private final RatingService service;

    public RatingController(RatingService service) {
        this.service = service;
    }

    
    
    /** Renseigne le nom de l'utilisateur connecté pour les vues de ce contrôleur */
    
    @ModelAttribute("principalName")
    public String principalName(Principal principal) {
        return principal != null ? principal.getName() : "anonymous";
    }

    
    
    /** GET /rating/list : affiche la liste des ratings */
    
    @GetMapping("/rating/list")
    public String home(Model model) {
        model.addAttribute("ratings", service.findAll());
        return "rating/list";
    }

    
    
    /** GET /rating/add : formulaire d'ajout */
    
    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }

    
    
    /** POST /rating/validate : valider et enregistrer */
    
    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result) {
        if (result.hasErrors()) {
            return "rating/add";
        }
        service.save(rating);
        return "redirect:/rating/list";
    }

    
    
    /** GET /rating/update/{id} : pré-remplir le formulaire */
    
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Rating r = service.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Rating Id: " + id));
        model.addAttribute("rating", r);
        return "rating/update";
    }

    
    
    /** POST /rating/update/{id} : valider et mettre à jour */
    
    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id,
                               @Valid Rating rating,
                               BindingResult result) {
        if (result.hasErrors()) {
            rating.setId(id); // conserver l'id si erreurs
            return "rating/update";
        }
        service.update(id, rating);
        return "redirect:/rating/list";
    }

    
    
    /** GET /rating/delete/{id} : supprimer puis revenir à la liste */
    
    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id) {
        service.deleteById(id);
        return "redirect:/rating/list";
    }
}
