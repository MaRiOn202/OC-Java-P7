package com.openclassrooms.poseidon.controllers;


import com.openclassrooms.poseidon.entity.CurvePoint;
import com.openclassrooms.poseidon.exception.CurvePointNotFoundException;
import com.openclassrooms.poseidon.exception.TradeNotFoundException;
import com.openclassrooms.poseidon.service.CurvePointService;
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
public class CurvePointController {
    // TODO: Inject Curve Point service
    @Autowired
    private CurvePointService curvePointService;

    private static final Logger log = LoggerFactory.getLogger(CurvePointController.class);



    @RequestMapping("/curvePoint/list")
    public String home(Model model)
    {
        // TODO: find all Curve Point, add to model
        model.addAttribute("curves", curvePointService.getAllCurvePoints());
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addBidForm(CurvePoint bid) {
        log.info("Accès à la page d'ajout de curvePoint");
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {

        // TODO: check data valid and save to db, after saving return Curve list
        if (!result.hasErrors()) {
            curvePointService.createCurvePoint(curvePoint);
            model.addAttribute("trades", curvePointService.getAllCurvePoints());
            return "redirect:/curvePoint/list";
        }
        return "curvePoint/add";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        // TODO: get CurvePoint by Id and to model then show to the form
        CurvePoint curvePoint = curvePointService.getCurvePointById(id);
        model.addAttribute("curvePoint", curvePoint);
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                             BindingResult result, Model model) {

        // TODO: check required fields, if valid call service to update Curve and return Curve list
        if (result.hasErrors()) {
            return "curvePoint/update";
        }
        curvePointService.updateCurvePoint(id, curvePoint);
        model.addAttribute("curvePoints", curvePointService.getAllCurvePoints());
        return "redirect:/curvePoint/list";
    }

    @PostMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {

        // TODO: Find Curve by Id and delete the Curve, return to Curve list
        try {
            curvePointService.deleteCurvePoint(id);
        } catch (CurvePointNotFoundException e) {
            log.info("Erreur lors de la suppression du point de courbe : {}", e.getMessage());
            model.addAttribute("error", "Point de courbe introuvable");
            return "error";
        }
        return "redirect:/curvePoint/list";
    }



















}
