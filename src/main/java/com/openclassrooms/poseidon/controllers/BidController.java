package com.openclassrooms.poseidon.controllers;


import com.openclassrooms.poseidon.entity.Bid;
import com.openclassrooms.poseidon.service.BidService;
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

// Offre

@Controller
public class BidController {

    @Autowired
    private BidService bidService;


    private static final Logger log = LoggerFactory.getLogger(BidController.class);


    @GetMapping("/bidList/list")
    public String home(Model model, Principal principal)
    {
        if (principal == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", principal.getName());
        model.addAttribute("bids", bidService.getAllBids());
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(Bid bid, Model model, Principal principal) {

        model.addAttribute("user", principal.getName());
        log.info("Accès à la page d'ajout des offres");
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid Bid bid, BindingResult result, Model model, Principal principal) {

        model.addAttribute("user", principal.getName());
        if (result.hasErrors()) {
            log.error("Validation erreur : {}", result.getAllErrors());
            return "bidList/add";
        }
        bidService.createBid(bid);
        model.addAttribute("bids", bidService.getAllBids());
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, Principal principal) {

        model.addAttribute("user", principal.getName());
        Bid bid = bidService.getBidById(id);
        model.addAttribute("bid", bid);
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid Bid bid,
                             BindingResult result, Model model, Principal principal) {

        model.addAttribute("user", principal.getName());
        if (result.hasErrors()) {
            return "bidList/update";
        }
        bidService.updateBid(id, bid);
        model.addAttribute("bids", bidService.getAllBids());
        return "redirect:/bidList/list";
    }

    @PostMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model, Principal principal) {

        model.addAttribute("user", principal.getName());
        bidService.deleteBid(id);
        return "redirect:/bidList/list";
    }
}
