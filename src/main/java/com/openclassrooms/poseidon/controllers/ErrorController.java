package com.openclassrooms.poseidon.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ErrorController {

    @GetMapping("/access-denied")
    public String accessDenied(HttpServletRequest request, Model model) {
        model.addAttribute("errorCode", request.getAttribute("errorCode"));
        model.addAttribute("errorMsg", request.getAttribute("errorMsg"));
        model.addAttribute("user", request.getAttribute("user"));
        return "error";
    }
}
