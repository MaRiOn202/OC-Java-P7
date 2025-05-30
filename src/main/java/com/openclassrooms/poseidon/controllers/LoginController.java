package com.openclassrooms.poseidon.controllers;



import com.openclassrooms.poseidon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public ModelAndView login(@RequestParam(value = "error", required = false) String error) {

        ModelAndView mav = new ModelAndView("login");
        if (error != null) {
           mav.addObject("errorMsg", "Nom d'utilisateur ou mot de passe incorrect");
        }
        return mav;
    }


/*    // Après login, user/list pour admin
    @GetMapping("secure/article-details")
    public ModelAndView getAllUserArticles() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("users", userService.getAllUsers());
        mav.setViewName("user/list");
        return mav;
    }*/

    @GetMapping("error")
    public ModelAndView error() {
        ModelAndView mav = new ModelAndView();
        String errorMessage= "Vous n'êtes pas autorisé à accéder à la ressource.";
        mav.addObject("errorCode", 403);
        mav.addObject("errorMsg", errorMessage);
        mav.setViewName("error");
        return mav;
    }
}
