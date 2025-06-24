package com.openclassrooms.poseidon.controller;

import com.openclassrooms.poseidon.PoseidonCapitalSolutionApplication;
import com.openclassrooms.poseidon.controllers.TradeController;
import com.openclassrooms.poseidon.entity.Trade;
import com.openclassrooms.poseidon.service.TradeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;



@WebMvcTest(controllers = {TradeController.class})
@ContextConfiguration(classes = PoseidonCapitalSolutionApplication.class)
public class TradeControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TradeService tradeService;

    @MockitoBean
    private Principal principal;


    @Test
    void testHomeShouldReturnTradeList() throws Exception {

        // Simuler list
        List<Trade> tradeList = List.of(new Trade(), new Trade());
        when(tradeService.getAllTrades()).thenReturn(tradeList);

        mockMvc.perform(get("/trade/list").with(user("Michel")))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/list"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("trades"));

    }

    @WithMockUser(username = "testUser", roles = "USER")
    @Test
    void testAddTradeFormShouldReturnTradeForm() throws Exception {

        mockMvc.perform(get("/trade/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"))
                .andExpect(model().attribute("user", "testUser"));

    }

    @WithMockUser(username = "testUser", roles = "USER")
    @Test
    void testValidateRedirectionSuccess() throws Exception {

        mockMvc.perform(post("/trade/validate")
                        .param("account", "Test")
                        .param("type", "Test")
                        .param("buyQuantity", String.valueOf(15.0))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));

    }

    @WithMockUser(username = "testUser", roles = "USER")
    @Test
    void testValidateWithErrorOfValidation() throws Exception {

        mockMvc.perform(post("/trade/validate")
                        .with(csrf())
                .param("account", "")     // notblank
                .param("type", "Test")
                .param("buyQuantity", String.valueOf(15.0)))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeHasFieldErrors("trade", "account"));

    }

    @WithMockUser(username = "testUser", roles = "USER")
    @Test
    void testShowUpdateFormShouldReturnUpdateForm() throws Exception {
        Integer id = 1;
        Trade trade = new Trade();
        trade.setId(id);
        trade.setAccount("Test");
        trade.setType("Test");

        when(tradeService.getTradeById(id)).thenReturn(trade);

        mockMvc.perform(get("/trade/update/{id}", id)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"))
                .andExpect(model().attribute("user", "testUser"))
                .andExpect(model().attribute("trade", trade));
        verify(tradeService).getTradeById(id);

    }


    @WithMockUser(username = "testUser", roles = "USER")
    @Test
    void testUpdateTradeWithErrorOfValidation() throws Exception {

        Integer id = 1;

        mockMvc.perform(post("/trade/update/{id}", id)
                        .with(csrf())
                        .param("account", "")
                        .param("type", "Test")
                        .param("buyQuantity", String.valueOf(15.0)))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("trade"))
                .andExpect(model().hasErrors());

    }

    @WithMockUser(username = "testUser", roles = "USER")
    @Test
    void testUpdateTradeWithRedirectionSuccess() throws Exception {

        Integer id = 1;
        Trade tradeUpdated = new Trade();
        tradeUpdated.setId(id);
        tradeUpdated.setAccount("Test");
        tradeUpdated.setType("Test");

        when(tradeService.updateTrade(eq(id),any(Trade.class))).thenReturn(tradeUpdated);
        when(tradeService.getAllTrades()).thenReturn(Collections.singletonList(tradeUpdated));

        mockMvc.perform(post("/trade/update/{id}", id)
                        .with(csrf())
                        .param("account", "Test")
                        .param("type", "Test")
                        .param("buyQuantity", String.valueOf(15.0)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));

    }

    @WithMockUser(username = "testUser", roles = "USER")
    @Test
    void testDeleteTradeWithRedirectionSuccess() throws Exception {

        Integer id = 1;

        when(tradeService.deleteTrade(id)).thenReturn(true);

        mockMvc.perform(post("/trade/delete/{id}", id)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));

        verify(tradeService, times(1)).deleteTrade(id);

    }


}
