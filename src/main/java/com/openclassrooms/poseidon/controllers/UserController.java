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

    // repository à enlever
    // mettre la logique métier dans les services pas les controller
    // ne garder que les models 

    @Autowired
    private final UserService userService;

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }




    @GetMapping("/user/list")
    public String home(Model model)
    {

        model.addAttribute("users", userService.getAllUsers());
        return "user/list";
    }

    @GetMapping("/user/add")
    public String addUser(User user) {
        log.info("Accès à la page d'ajout de user");
        return "user/add";
    }


    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            userService.createUser(user);
            model.addAttribute("users", userService.getAllUsers());
            return "redirect:/user/list";
        }
        return "user/add";
    }

    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        User user = userService.getUserById(id);
        user.setPassword("");            // champ à vider pour ne pas réencoder le hash
        model.addAttribute("user", user);
        return "user/update";
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model, Principal principal ) {

        if (result.hasErrors()) {
            return "user/update";
        }
        userService.updateUser(id, user);
        model.addAttribute("users", userService.getAllUsers());
        return "redirect:/user/list";
    }

    @PostMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model, Principal principal) {

        model.addAttribute("user", principal.getName());
        userService.deleteUser(id);
        //model.addAttribute("users", userService.getAllUsers());
        return "redirect:/user/list";
    }
}
