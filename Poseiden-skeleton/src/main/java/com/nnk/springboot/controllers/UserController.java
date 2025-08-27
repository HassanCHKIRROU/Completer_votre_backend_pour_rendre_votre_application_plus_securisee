package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * CRUD MVC pour User 
 */
@Controller
public class UserController {
	

    private final UserService userService;

    
    public UserController(UserService userService) {
        this.userService = userService;
    }

    
    
    /** GET /user/list : affiche la liste des utilisateurs */
    @GetMapping("/user/list")
    public String home(Model model) {
        model.addAttribute("users", userService.findAll());
        return "user/list";
    }

    
    
    /** GET /user/add : formulaire d'ajout */
    @GetMapping("/user/add")
    public String addUser(User user) {
        return "user/add";
    }

    
    
    /** POST /user/validate : valider et enregistrer un nouvel utilisateur */
    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return "user/add";
        }
        userService.save(user); // encode le password en service
        return "redirect:/user/list";
    }

    
    
    /** GET /user/update/{id} : pré-remplir le formulaire d'édition */
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        User user = userService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id: " + id));
        // On vide le champ password pour forcer une nouvelle saisie
        user.setPassword("");
        model.addAttribute("user", user);
        return "user/update";
    }

    
    
    /** POST /user/update/{id} : valider et mettre à jour */
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id,
                             @Valid User user,
                             BindingResult result) {
        if (result.hasErrors()) {
            return "user/update";
        }
        userService.update(id, user); // encode si mot de passe fourni
        return "redirect:/user/list";
    }

    
    
    /** GET /user/delete/{id} : supprimer puis revenir à la liste */
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
        userService.deleteById(id);
        return "redirect:/user/list";
    }
}
