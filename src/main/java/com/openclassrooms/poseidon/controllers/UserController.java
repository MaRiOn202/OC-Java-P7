package com.openclassrooms.poseidon.controllers;


import com.openclassrooms.poseidon.entity.User;
import com.openclassrooms.poseidon.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@Controller
public class UserController {


    @Autowired
    private final UserService userService;

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }




    @GetMapping("/user/list")
    public String home(Model model, Principal principal)
    {
        model.addAttribute("user", principal.getName());
        model.addAttribute("users", userService.getAllUsers());
        return "user/list";
    }

    @GetMapping("/user/add")
    public String addUser(User user, Principal principal, Model model) {

        model.addAttribute("user", principal.getName()); // pour la navbar
        model.addAttribute("userForm", new User());  // le formulaire
        log.info("Accès à la page d'ajout de user");
        return "user/add";
    }


    @PostMapping("/user/validate")
    public String validate(@Valid @ModelAttribute("userForm") User userForm, BindingResult result, Model model, Principal principal) {

        model.addAttribute("user", principal.getName());
        if (result.hasErrors()) {
            log.error("Validation erreur : {}", result.getAllErrors());
            return "user/add";
        }
        userService.createUser(userForm);
        model.addAttribute("users", userService.getAllUsers());
        return "redirect:/user/list";
    }

    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, Principal principal) {

        model.addAttribute("user", principal.getName());
        User user = userService.getUserById(id);
        user.setPassword("");            // champ à vider pour ne pas réencoder le hash
        model.addAttribute("userForm", user);
        return "user/update";
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid @ModelAttribute("userForm") User userForm,
                             BindingResult result, Model model, Principal principal ) {

        model.addAttribute("user", principal.getName());
        if (result.hasErrors()) {
            return "user/update";
        }
        userService.updateUser(id, userForm);
        model.addAttribute("users", userService.getAllUsers());
        return "redirect:/user/list";
    }

    @PostMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model, Principal principal) {

        model.addAttribute("user", principal.getName());
        userService.deleteUser(id);
        return "redirect:/user/list";
    }
}
