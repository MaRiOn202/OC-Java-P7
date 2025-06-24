package com.openclassrooms.poseidon.controllers;


import com.openclassrooms.poseidon.entity.CurvePoint;
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

import java.security.Principal;


@Controller
public class CurvePointController {

    @Autowired
    private CurvePointService curvePointService;

    private static final Logger log = LoggerFactory.getLogger(CurvePointController.class);



    @GetMapping("/curvePoint/list")
    public String home(Model model, Principal principal)
    {
        if (principal == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", principal.getName());
        model.addAttribute("curvePoints", curvePointService.getAllCurvePoints());
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addCurvePointForm(CurvePoint curvePoint, Model model, Principal principal) {

        model.addAttribute("user", principal.getName());
        log.info("Accès à la page d'ajout de curvePoint");
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model, Principal principal) {

        model.addAttribute("user", principal.getName());
        if (result.hasErrors()) {
            log.error("Validation erreur : {}", result.getAllErrors());
            return "curvePoint/add";
        }
        curvePointService.createCurvePoint(curvePoint);
        model.addAttribute("curvePoints", curvePointService.getAllCurvePoints());
        return "redirect:/curvePoint/list";

    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, Principal principal) {

        model.addAttribute("user", principal.getName());
        CurvePoint curvePoint = curvePointService.getCurvePointById(id);
        model.addAttribute("curvePoint", curvePoint);
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateCurvePoint(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                            BindingResult result, Model model, Principal principal) {

        model.addAttribute("user", principal.getName());
        if (result.hasErrors()) {
            return "curvePoint/update";
        }
        curvePointService.updateCurvePoint(id, curvePoint);
        model.addAttribute("curvePoints", curvePointService.getAllCurvePoints());
        return "redirect:/curvePoint/list";
    }

    @PostMapping("/curvePoint/delete/{id}")
    public String deleteCurvePoint(@PathVariable("id") Integer id, Model model, Principal principal) {

        model.addAttribute("user", principal.getName());
        curvePointService.deleteCurvePoint(id);
        return "redirect:/curvePoint/list";
    }



















}
