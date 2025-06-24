package com.openclassrooms.poseidon.controller;


import com.openclassrooms.poseidon.PoseidonCapitalSolutionApplication;
import com.openclassrooms.poseidon.controllers.LoginController;
import com.openclassrooms.poseidon.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {LoginController.class})
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = PoseidonCapitalSolutionApplication.class)
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;


    @WithMockUser
    @Test
    void loginTestReturnLoginView() throws Exception {

        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeDoesNotExist(("errorMsg")))
                .andReturn();

    }

    @WithMockUser
    @Test
    void testError() throws Exception {
        mockMvc.perform(get("/error"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("errorCode", 403))
                .andExpect(model().attribute("errorMsg", "Vous n'êtes pas autorisé à accéder à la ressource."));
    }

















}
