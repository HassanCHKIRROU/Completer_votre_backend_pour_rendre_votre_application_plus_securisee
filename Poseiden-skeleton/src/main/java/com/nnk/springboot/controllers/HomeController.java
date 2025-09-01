package com.nnk.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@Controller
public class HomeController {

    /** Renseigne le nom de l'utilisateur connecté pour les vues renvoyées par ce contrôleur */
    @ModelAttribute("principalName")
    public String principalName(Principal principal) {
        return principal != null ? principal.getName() : "anonymous";
    }

    @GetMapping("/")
    public String home() {
        return "home"; // templates/home.html
    }

    @GetMapping("/admin/home")
    public String adminHome() {
        return "redirect:/bidList/list";
    }

    // Optionnel : accès direct via /home
    @GetMapping("/home")
    public String homeAlias() {
        return "home";
    }
}
