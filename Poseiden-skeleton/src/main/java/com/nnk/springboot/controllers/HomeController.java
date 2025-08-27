package com.nnk.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	

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
