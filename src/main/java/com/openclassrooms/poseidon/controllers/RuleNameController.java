package com.openclassrooms.poseidon.controllers;


import com.openclassrooms.poseidon.entity.RuleName;
import com.openclassrooms.poseidon.service.RuleNameService;
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
public class RuleNameController {

    @Autowired
    private RuleNameService ruleNameService;

    private static final Logger log = LoggerFactory.getLogger(RuleNameController.class);




    @GetMapping("/ruleName/list")
    public String home(Model model, Principal principal)
    {
        if (principal == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", principal.getName());
        model .addAttribute("ruleNames", ruleNameService.getAllRuleNames());
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName ruleName, Model model, Principal principal) {

        model.addAttribute("user", principal.getName());
        log.info("Accès à la page d'ajout de la règle");
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model, Principal principal) {

        model.addAttribute("user", principal.getName());
        if (result.hasErrors()) {
            log.error("Validation erreur : {}", result.getAllErrors());
            return "ruleName/add";
        }
        ruleNameService.createRuleName(ruleName);
        model.addAttribute("ruleNames", ruleNameService.getAllRuleNames());
        return "redirect:/ruleName/list";

    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, Principal principal) {

        model.addAttribute("user", principal.getName());
        RuleName ruleName = ruleNameService.getRuleNameById(id);
        model.addAttribute("ruleName", ruleName);
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                             BindingResult result, Model model, Principal principal) {

        model.addAttribute("user", principal.getName());
        if (result.hasErrors()) {
            return "ruleName/update";
        }
        ruleNameService.updateRuleName(id, ruleName);
        model.addAttribute("ruleNames", ruleNameService.getAllRuleNames());
        return "redirect:/ruleName/list";
    }

    @PostMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model, Principal principal) {

        model.addAttribute("user", principal.getName());
        ruleNameService.deleteRuleName(id);
        return "redirect:/ruleName/list";
    }
}
