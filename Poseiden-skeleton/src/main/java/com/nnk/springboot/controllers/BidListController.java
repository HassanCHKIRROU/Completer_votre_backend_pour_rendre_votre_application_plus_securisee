package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.service.BidListService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Contrôleur MVC pour BidList : liste, ajout, maj, suppression.
 * Liens avec les vues Thymeleaf.
 */
@Controller
public class BidListController {

    private final BidListService service;

    public BidListController(BidListService service) {
        this.service = service;
    }

    /** Utilitaire : pousse le nom de l’utilisateur connecté dans le modèle (si présent). */
    private static void putPrincipal(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("principalName", principal.getName());
        }
    }

    /** GET /bidList/list : affiche la liste */
    @GetMapping("/bidList/list")
    public String home(Model model, Principal principal) {
        model.addAttribute("bidLists", service.findAll());
        putPrincipal(model, principal);
        return "bidList/list";
    }

    /** GET /bidList/add : affiche le formulaire d'ajout */
    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid, Model model, Principal principal) {
        putPrincipal(model, principal);
        return "bidList/add";
    }

    /** POST /bidList/validate : valider et enregistrer */
    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model, Principal principal) {
        if (result.hasErrors()) {
            putPrincipal(model, principal);
            return "bidList/add";
        }
        service.save(bid);
        return "redirect:/bidList/list";
    }

    /** GET /bidList/update/{id} : pré-remplir le formulaire */
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, Principal principal) {
        BidList bid = service.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid bidList Id: " + id));
        model.addAttribute("bidList", bid);
        putPrincipal(model, principal);
        return "bidList/update";
    }

    /** POST /bidList/update/{id} : valider et mettre à jour */
    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id,
                            @Valid BidList bidList,
                            BindingResult result,
                            Model model,
                            Principal principal) {
        if (result.hasErrors()) {
            // réinjecter l'id si le form le perd
            bidList.setId(id);
            model.addAttribute("bidList", bidList);
            putPrincipal(model, principal);
            return "bidList/update";
        }
        service.update(id, bidList);
        return "redirect:/bidList/list";
    }

    /** GET /bidList/delete/{id} : supprimer puis revenir à la liste */
    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id) {
        service.deleteById(id);
        return "redirect:/bidList/list";
    }
}
