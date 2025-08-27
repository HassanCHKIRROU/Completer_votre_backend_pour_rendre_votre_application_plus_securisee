package com.nnk.springboot.controllers;

import com.nnk.springboot.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Contrôleur d'authentification (pages login, 403, et un exemple de page sécurisée).
 * Les mappings sont sous /app pour correspondre aux templates existants.
 */
@Controller
@RequestMapping("/app")
public class LoginController {
	

    private final UserRepository userRepository;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    
    
    /** Page de login (utilisée par la config Spring Security). */
    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    
    
    /** Exemple de page sécurisée listant les users (à protéger via Spring Security). */
    @GetMapping("/secure/article-details")
    public ModelAndView getAllUserArticles() {
        ModelAndView mav = new ModelAndView("user/list");
        mav.addObject("users", userRepository.findAll());
        return mav;
    }

    
    
    /** Page d'erreur 403 (access denied). */
    @GetMapping("/error")
    public ModelAndView error() {
        ModelAndView mav = new ModelAndView("403");
        mav.addObject("errorMsg", "You are not authorized for the requested data.");
        return mav;
    }
}
