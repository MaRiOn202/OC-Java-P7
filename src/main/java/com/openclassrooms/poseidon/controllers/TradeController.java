package com.openclassrooms.poseidon.controllers;


import com.openclassrooms.poseidon.entity.Trade;
import com.openclassrooms.poseidon.service.TradeService;
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
public class TradeController {

    @Autowired
    private TradeService tradeService;

    private static final Logger log = LoggerFactory.getLogger(TradeController.class);


    @GetMapping("/trade/list")
    public String home(Model model, Principal principal)
    {
        if (principal == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", principal.getName());
        model.addAttribute("trades", tradeService.getAllTrades());
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addTrade(Trade trade, Model model, Principal principal) {

        model.addAttribute("user", principal.getName());
        log.info("Accès à la page d'ajout de trade");
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model, Principal principal) {

        model.addAttribute("user", principal.getName());
        if (result.hasErrors()) {
            log.error("Validation erreur : {}", result.getAllErrors());
            return "trade/add";
        }
        tradeService.createTrade(trade);
        model.addAttribute("trades", tradeService.getAllTrades());
        return "redirect:/trade/list";

    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, Principal principal) {

        model.addAttribute("user", principal.getName());
        Trade trade = tradeService.getTradeById(id);
        model.addAttribute("trade", trade);
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                             BindingResult result, Model model, Principal principal) {

        model.addAttribute("user", principal.getName());
        if (result.hasErrors()) {
           return "trade/update";
        }
        tradeService.updateTrade(id, trade);
        model.addAttribute("trades", tradeService.getAllTrades());
        return "redirect:/trade/list";
    }

    @PostMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model, Principal principal) {

        model.addAttribute("user", principal.getName());
        tradeService.deleteTrade(id);
        //model.addAttribute("trades", tradeService.getAllTrades()); // à suppr car redirect ne conserve pas le model
        return "redirect:/trade/list";
    }
}
