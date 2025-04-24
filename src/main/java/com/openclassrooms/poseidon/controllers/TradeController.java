package com.openclassrooms.poseidon.controllers;


import com.openclassrooms.poseidon.entity.Trade;
import com.openclassrooms.poseidon.exception.TradeNotFoundException;
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
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class TradeController {

    @Autowired
    private TradeService tradeService;

    private static final Logger log = LoggerFactory.getLogger(TradeController.class);

// ????
    //@RequestMapping("/trade/list")
    @GetMapping("/trade/list")
    public String home(Model model)
    {
        // TODO: find all Trade, add to model
        model.addAttribute("trades", tradeService.getAllTrades());
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addTrade(Trade bid) {
        log.info("Accès à la page d'ajout de trade");
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {

        // TODO: check data valid and save to db, after saving return Trade list
        if (!result.hasErrors()) {
           tradeService.createTrade(trade);
           model.addAttribute("trades", tradeService.getAllTrades());
           return "redirect:/trade/list";
        }
        return "trade/add";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        // TODO: get Trade by Id and to model then show to the form
        Trade trade = tradeService.getTradeById(id);
        model.addAttribute("trade", trade);
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                             BindingResult result, Model model) {

        // TODO: check required fields, if valid call service to update Trade and return Trade list
        if (result.hasErrors()) {
           return "trade/update";
        }
        tradeService.updateTrade(id, trade);
        model.addAttribute("trades", tradeService.getAllTrades());
        return "redirect:/trade/list";
    }

    @PostMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {

        // TODO: Find Trade by Id and delete the Trade, return to Trade list
        try {
            tradeService.deleteTrade(id);
        } catch (TradeNotFoundException e) {
            log.info("Erreur lors de la suppression du trade : {}", e.getMessage());
            model.addAttribute("error", "Trade introuvable");
            return "error";
        }
        //model.addAttribute("trades", tradeService.getAllTrades()); // à suppr car redirect ne conserve pas le model
        return "redirect:/trade/list";
    }
}
