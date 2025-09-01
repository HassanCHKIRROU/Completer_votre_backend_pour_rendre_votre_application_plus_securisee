package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.service.TradeService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Contrôleur MVC pour Trade : liste, ajout, mise à jour, suppression.
 */
@Controller
public class TradeController {

	
    private final TradeService service;

    public TradeController(TradeService service) {
        this.service = service;
    }

    
    
    /** Met à disposition le nom de l'utilisateur connecté pour toutes les vues de ce contrôleur */
    
    @ModelAttribute("principalName")
    public String principalName(Principal principal) {
        return principal != null ? principal.getName() : "anonymous";
    }

    
    
    /** GET /trade/list : affiche la liste des trades */
    
    @GetMapping("/trade/list")
    public String home(Model model) {
        model.addAttribute("trades", service.findAll());
        return "trade/list";
    }

    
    
    /** GET /trade/add : formulaire d'ajout */
    
    @GetMapping("/trade/add")
    public String addUser(Trade trade) {
        return "trade/add";
    }

    
    
    /** POST /trade/validate : valider et enregistrer */
    
    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result) {
        if (result.hasErrors()) {
            return "trade/add";
        }
        service.save(trade);
        return "redirect:/trade/list";
    }

    
    
    /** GET /trade/update/{id} : pré-remplir le formulaire */
    
    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Trade t = service.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Trade Id: " + id));
        model.addAttribute("trade", t);
        return "trade/update";
    }

    
    
    /** POST /trade/update/{id} : valider et mettre à jour */
    
    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id,
                              @Valid Trade trade,
                              BindingResult result) {
        if (result.hasErrors()) {
            trade.setId(id); // s'assurer que l'ID est conservé dans le form
            return "trade/update";
        }
        service.update(id, trade);
        return "redirect:/trade/list";
    }

    
    
    /** GET /trade/delete/{id} : supprimer puis revenir à la liste */
    
    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id) {
        service.deleteById(id);
        return "redirect:/trade/list";
    }
}
