package com.nnk.springboot.controllers;

import com.nnk.springboot.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@Controller
@RequestMapping("/app")
public class LoginController {

    private final UserRepository userRepository;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /** Expose le nom d'utilisateur connecté aux vues renvoyées par ce contrôleur */
    @ModelAttribute("principalName")
    public String principalName(Principal principal) {
        return principal != null ? principal.getName() : "anonymous";
    }

    /** Page de login : redirige vers la page par défaut générée par Spring Security (pas de template nécessaire). */
    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("redirect:/login");
    }

    /** Exemple de page sécurisée listant les users. */
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
