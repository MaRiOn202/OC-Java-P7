package com.openclassrooms.poseidon.controllers;


import com.openclassrooms.poseidon.entity.Rating;
import com.openclassrooms.poseidon.exception.RatingNotFoundException;
import com.openclassrooms.poseidon.service.RatingService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;


@Controller
public class RatingController {

    @Autowired
    private RatingService ratingService;

    private static final Logger log = LoggerFactory.getLogger(RatingController.class);




    @GetMapping("/rating/list")
    public String home(Model model, Principal principal)
    {
        if (principal == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", principal.getName());
        model.addAttribute("ratings", ratingService.getAllRatings());
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating, Model model, Principal principal) {

        model.addAttribute("user", principal.getName());
        log.info("Accès à la page d'ajout de notation");
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model, Principal principal) {

        model.addAttribute("user", principal.getName());
        if (result.hasErrors()) {
            log.error("Validation erreur : {}", result.getAllErrors());
            return "rating/add";
        }
        ratingService.createRating(rating);
        model.addAttribute("ratings", ratingService.getAllRatings());
        return "redirect:/rating/list";

    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, Principal principal) {

        model.addAttribute("user", principal.getName());
        Rating rating = ratingService.getRatingById(id);
        model.addAttribute("rating", rating);
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model, Principal principal) {

        model.addAttribute("user", principal.getName());
        if (result.hasErrors()) {
            return "rating/update";
        }
        ratingService.updateRating(id, rating);
        model.addAttribute("ratings", ratingService.getAllRatings());
        return "redirect:/rating/list";
    }

    @PostMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model, Principal principal) {

        model.addAttribute("user", principal.getName());
        ratingService.deleteRating(id);
        return "redirect:/rating/list";
    }
}
