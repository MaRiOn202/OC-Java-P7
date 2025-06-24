package com.openclassrooms.poseidon.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ResponseStatus;


// exception globale pour centraliser tous les catch des controllers
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGenericException(Exception e, Model model) {
        log.error("Erreur inattendue : {}", e.getMessage(), e);
        model.addAttribute("errorMsg", "Une erreur inattendue s'est produite. Veuillez contacter votre administrateur." );
        model.addAttribute("errorCode", 500);
        return "error";
    }

    @ExceptionHandler(TradeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleTradeNotFound(TradeNotFoundException e, Model model) {
        log.error("Trade introuvable : {}", e.getMessage());
        model.addAttribute("errorMsg", "Le Trade n'existe pas");
        model.addAttribute("errorCode", 404);
        return "error";
    }

    @ExceptionHandler(CurvePointNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleCurvePointNotFound(CurvePointNotFoundException e, Model model) {

        log.error("Point de courbe introuvable : {}", e.getMessage());
        model.addAttribute("errorMsg", "Le point de courbe demandé n'existe pas.");
        model.addAttribute("errorCode", 404);
        return "error";
    }

    @ExceptionHandler(BidNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleBidNotFound(BidNotFoundException e, Model model) {

        log.error("Offre introuvable : {}", e.getMessage());
        model.addAttribute("errorMsg", "L'offre demandée n'existe pas.");
        model.addAttribute("errorCode", 404);
        return "error";
    }

    @ExceptionHandler(RatingNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleRatingNotFound(RatingNotFoundException e, Model model) {

        log.error("Notation introuvable : {}", e.getMessage());
        model.addAttribute("errorMsg", "La notation demandée n'existe pas.");
        model.addAttribute("errorCode", 404);
        return "error";
    }

    @ExceptionHandler(RuleNameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleRuleNameNotFound(RuleNameNotFoundException e, Model model) {

        log.error("Règle introuvable : {}", e.getMessage());
        model.addAttribute("errorMsg", "La règle demandée n'existe pas.");
        model.addAttribute("errorCode", 404);
        return "error";
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleUserNotFound(UserNotFoundException e, Model model) {

        log.error("User introuvable : {}", e.getMessage());
        model.addAttribute("errorMsg", "L'utilisateur demandé n'existe pas.");
        model.addAttribute("errorCode", 404);
        return "error";
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleUsernameAlreadyExists(UsernameAlreadyExistsException e, Model model) {

        log.error("Username déjà utilisé : {}", e.getMessage());
        model.addAttribute("errorMsg", "Le username existe déjà.");
        model.addAttribute("errorCode", 409);
        return "error";
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleAccessDenied(AccessDeniedException e, Model model) {

        log.error("Accès refusé : {}", e.getMessage());
        model.addAttribute("errorMsg", "Vous n'êtes pas autorisé à accéder à cette ressource.");
        model.addAttribute("errorCode", 403);
        return "error";
    }


}
