package com.openclassrooms.poseidon.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;


import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

        request.setAttribute("errorCode", 403);
        request.setAttribute("errorMsg", "Vous n'êtes pas autorisé à accéder à cette ressource.");
        request.setAttribute("user", request.getUserPrincipal() != null ? request.getUserPrincipal().getName() : "Utilisateur inconnu");

        request.getRequestDispatcher("/access-denied").forward(request, response); // renvoie à la pge
    }
}

