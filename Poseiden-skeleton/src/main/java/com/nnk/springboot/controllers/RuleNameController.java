package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.service.RuleNameService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Contrôleur MVC pour RuleName : liste, ajout, mise à jour, suppression.
 */
@Controller
public class RuleNameController {

	
	
    private final RuleNameService service;

    public RuleNameController(RuleNameService service) {
        this.service = service;
    }

    
    
    /** Met à disposition le nom de l'utilisateur connecté pour toutes les vues de ce contrôleur */
    
    @ModelAttribute("principalName")
    public String principalName(Principal principal) {
        return principal != null ? principal.getName() : "anonymous";
    }

    
    
    /** GET /ruleName/list : affiche la liste des règles */
    
    @GetMapping("/ruleName/list")
    public String home(Model model) {
        model.addAttribute("ruleNames", service.findAll());
        return "ruleName/list";
    }

    
    
    /** GET /ruleName/add : formulaire d'ajout */
    
    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName ruleName) {
        return "ruleName/add";
    }

    
    
    /** POST /ruleName/validate : valider et enregistrer */
    
    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result) {
        if (result.hasErrors()) {
            return "ruleName/add";
        }
        service.save(ruleName);
        return "redirect:/ruleName/list";
    }

    
    
    /** GET /ruleName/update/{id} : pré-remplir le formulaire */
    
    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        RuleName rn = service.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid RuleName Id: " + id));
        model.addAttribute("ruleName", rn);
        return "ruleName/update";
    }

    
    
    /** POST /ruleName/update/{id} : valider et mettre à jour */
    
    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id,
                                 @Valid RuleName ruleName,
                                 BindingResult result) {
        if (result.hasErrors()) {
            ruleName.setId(id); // garder l'ID dans le formulaire
            return "ruleName/update";
        }
        service.update(id, ruleName);
        return "redirect:/ruleName/list";
    }

    
    
    /** GET /ruleName/delete/{id} : supprimer puis revenir à la liste */
    
    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id) {
        service.deleteById(id);
        return "redirect:/ruleName/list";
    }
}
