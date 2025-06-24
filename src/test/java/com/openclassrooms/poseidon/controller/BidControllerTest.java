package com.openclassrooms.poseidon.controller;


import com.openclassrooms.poseidon.PoseidonCapitalSolutionApplication;
import com.openclassrooms.poseidon.controllers.BidController;
import com.openclassrooms.poseidon.entity.Bid;
import com.openclassrooms.poseidon.service.BidService;
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

@WebMvcTest(controllers = {BidController.class})
@ContextConfiguration(classes = PoseidonCapitalSolutionApplication.class)
public class BidControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BidService bidService;

    @MockitoBean
    private Principal principal;


    @Test
    void testHomeShouldReturnBidList() throws Exception {

        // Simuler list
        List<Bid> bidList = List.of(new Bid(), new Bid());
        when(bidService.getAllBids()).thenReturn(bidList);

        mockMvc.perform(get("/bidList/list").with(user("Michel")))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("bids"));

    }

    @WithMockUser(username = "testUser", roles = "USER")
    @Test
    void testAddBidFormShouldReturnBidForm() throws Exception {

        mockMvc.perform(get("/bidList/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"))
                .andExpect(model().attribute("user", "testUser"));

    }

    @WithMockUser(username = "testUser", roles = "USER")
    @Test
    void testValidateRedirectionSuccess() throws Exception {

        mockMvc.perform(post("/bidList/validate")
                .param("account", "account1")
                .param("type", "type1")
                .param("bidQuantity", "10.0")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

    }

    @WithMockUser(username = "testUser", roles = "USER")
    @Test
    void testValidateWithErrorOfValidation() throws Exception {

        mockMvc.perform(post("/bidList/validate")
                        .with(csrf())
                        .param("account", "") // obligatoire donc erreur de validation
                        .param("type", "type1")
                        .param("bidQuantity", "10.0"))
                        .andExpect(status().isOk())
                        .andExpect(view().name("bidList/add"))
                        .andExpect(model().attributeExists("user"))
                        .andExpect(model().attributeHasFieldErrors("bid", "account"));

    }

    @WithMockUser(username = "testUser", roles = "USER")
    @Test
    void testShowUpdateFormShouldReturnUpdateForm() throws Exception {
            Integer id = 1;
            Bid bid = new Bid();
            bid.setId(id);
            bid.setAccount("account1");
            bid.setType("Type1");
            bid.setBidQuantity(12.0);

            when(bidService.getBidById(id)).thenReturn(bid);

            mockMvc.perform(get("/bidList/update/{id}", id)
                            .with(csrf()))
                    .andExpect(status().isOk())
                    .andExpect(view().name("bidList/update"))
                    .andExpect(model().attribute("user", "testUser"))
                    .andExpect(model().attribute("bid", bid));
            verify(bidService).getBidById(id);

    }


    @WithMockUser(username = "testUser", roles = "USER")
    @Test
    void testUpdateBidWithErrorOfValidation() throws Exception {

        Integer id = 1;

        mockMvc.perform(post("/bidList/update/{id}", id)
                        .with(csrf())
                        //.param("account", "account1") // obligatoire donc erreur de validation
                        .param("type", "type1")
                        .param("bidQuantity", "10.0"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("bid"))
                .andExpect(model().hasErrors());

    }

    @WithMockUser(username = "testUser", roles = "USER")
    @Test
    void testUpdateBidWithRedirectionSuccess() throws Exception {

        Integer id = 1;
        Bid bidUpdated = new Bid();
        bidUpdated.setId(id);
        bidUpdated.setAccount("account1");
        bidUpdated.setType("type1");
        bidUpdated.setBidQuantity(10.0);

        when(bidService.updateBid(eq(id),any(Bid.class))).thenReturn(bidUpdated);
        when(bidService.getAllBids()).thenReturn(Collections.singletonList(bidUpdated));

        mockMvc.perform(post("/bidList/update/{id}", id)
                        .with(csrf())
                        .param("account", "account1")
                        .param("type", "type1")
                        .param("bidQuantity", "10.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

    }

    @WithMockUser(username = "testUser", roles = "USER")
    @Test
    void testDeleteBidWithRedirectionSuccess() throws Exception {

        Integer id = 1;

        when(bidService.deleteBid(id)).thenReturn(true);

        mockMvc.perform(post("/bidList/delete/{id}", id)
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

        verify(bidService, times(1)).deleteBid(id);

    }

}
