package com.openclassrooms.poseidon.controllers;


import com.openclassrooms.poseidon.domain.BidList;
import com.openclassrooms.poseidon.service.BidListService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

 // Offre

@Controller
public class BidListController {

    @Autowired
    private BidListService bidListService;


    @RequestMapping("/bidList/list")
    public String home(Model model)
    {
        // TODO: call service find all bids to show to the view
        model.addAttribute("bids", bidListService.getAllBids());
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bidList) {

        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bidList, BindingResult result, Model model) {

        // TODO: check data valid and save to db, after saving return bid list
        if (!result.hasErrors()) {
            bidListService.createBid(bidList);
            model.addAttribute("bids", bidListService.getAllBids());
            return "redirect:/bidList/list";
        }
        return "bidList/add";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        // TODO: get Bid by Id and to model then show to the form
        BidList bidList = bidListService.getBidById(id);
        model.addAttribute("bid", bidList);
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                             BindingResult result, Model model) {

        // TODO: check required fields, if valid call service to update Bid and return list Bid
        if (result.hasErrors()) {
            return "bidList/update";
        }
        bidListService.updateBid(id, bidList);
        model.addAttribute("bids", bidListService.getAllBids());
        return "redirect:/bidList/list";
    }

    @PostMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {

        // TODO: Find Bid by Id and delete the bid, return to Bid list
        bidListService.deleteBid(id);
        model.addAttribute("bids", bidListService.getAllBids());
        return "redirect:/bidList/list";
    }
}
