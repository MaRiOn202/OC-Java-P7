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
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class RatingController {

    @Autowired
    private RatingService ratingService;

    private static final Logger log = LoggerFactory.getLogger(RatingController.class);




    @RequestMapping("/rating/list")              // ou GetMapping ?
    public String home(Model model)
    {
        // TODO: find all Rating, add to model
        model.addAttribute("ratings", ratingService.getAllRatings());
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        log.info("Accès à la page d'ajout de notation");
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return Rating list
        if (!result.hasErrors()) {
            ratingService.createRating(rating);
            model.addAttribute("ratings", ratingService.getAllRatings());
            return "redirect:/rating/list";
        }
        return "rating/add";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        // TODO: get Rating by Id and to model then show to the form
        Rating rating = ratingService.getRatingById(id);
        model.addAttribute("rating", rating);
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model) {

        // TODO: check required fields, if valid call service to update Rating and return Rating list
        if (result.hasErrors()) {
            return "rating/update";
        }
        ratingService.updateRating(id, rating);
        model.addAttribute("ratings", ratingService.getAllRatings());
        return "redirect:/rating/list";
    }

    @PostMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {

        // TODO: Find Rating by Id and delete the Rating, return to Rating list
        try {
            ratingService.deleteRating(id);
        } catch (RatingNotFoundException e) {
            log.info("Erreur lors de la suppression de la notation : {}", e.getMessage());
            model.addAttribute("error", "Notation introuvable");
            return "error";
        }
        //model.addAttribute("ratings", ratingService.getAllRatings());
        return "redirect:/rating/list";
    }
}
