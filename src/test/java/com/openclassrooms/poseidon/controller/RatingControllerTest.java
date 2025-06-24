package com.openclassrooms.poseidon.controller;


import com.openclassrooms.poseidon.PoseidonCapitalSolutionApplication;
import com.openclassrooms.poseidon.controllers.RatingController;
import com.openclassrooms.poseidon.entity.Rating;
import com.openclassrooms.poseidon.service.RatingService;
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


@WebMvcTest(controllers = {RatingController.class})
@ContextConfiguration(classes = PoseidonCapitalSolutionApplication.class)
public class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RatingService ratingService;

    @MockitoBean
    private Principal principal;


    @Test
    void testHomeShouldReturnRatingList() throws Exception {

        // Simuler list
        List<Rating> ratingList = List.of(new Rating(), new Rating());
        when(ratingService.getAllRatings()).thenReturn(ratingList);

        mockMvc.perform(get("/rating/list").with(user("Michel")))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("ratings"));

    }

    @WithMockUser(username = "testUser", roles = "USER")
    @Test
    void testAddRatingFormShouldReturnRatingForm() throws Exception {

        mockMvc.perform(get("/rating/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"))
                .andExpect(model().attribute("user", "testUser"));

    }

    @WithMockUser(username = "testUser", roles = "USER")
    @Test
    void testValidateRedirectionSuccess() throws Exception {

        mockMvc.perform(post("/rating/validate")
                        .param("moodysRating", "Test")
                        .param("sandPRating", "Test")
                        .param("fitchRating", "Test")
                        .param("orderNumber", String.valueOf(3))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

    }

    @WithMockUser(username = "testUser", roles = "USER")
    @Test
    void testValidateWithErrorOfValidation() throws Exception {

        mockMvc.perform(post("/rating/validate")
                        .with(csrf())
                .param("moodysRating", "")
                .param("sandPRating", "Test")
                .param("fitchRating", "Test")
                .param("orderNumber", String.valueOf(3)))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeHasFieldErrors("rating", "moodysRating"));

    }

    @WithMockUser(username = "testUser", roles = "USER")
    @Test
    void testShowUpdateFormShouldReturnUpdateForm() throws Exception {
        Integer id = 1;
        Rating rating = new Rating();
        rating.setId(id);
        rating.setMoodysRating("Test");
        rating.setSandPRating("Test");
        rating.setFitchRating("Test");
        rating.setOrderNumber(12);

        when(ratingService.getRatingById(id)).thenReturn(rating);

        mockMvc.perform(get("/rating/update/{id}", id)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"))
                .andExpect(model().attribute("user", "testUser"))
                .andExpect(model().attribute("rating", rating));
        verify(ratingService).getRatingById(id);

    }


    @WithMockUser(username = "testUser", roles = "USER")
    @Test
    void testUpdateRatingWithErrorOfValidation() throws Exception {

        Integer id = 1;

        mockMvc.perform(post("/rating/update/{id}", id)
                        .with(csrf())
                        .param("moodysRating", "")  // notblank donc error
                        .param("sandPRating", "Test")
                        .param("fitchRating", "Test")
                        .param("orderNumber", String.valueOf(3)))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("rating"))
                .andExpect(model().hasErrors());

    }

    @WithMockUser(username = "testUser", roles = "USER")
    @Test
    void testUpdateRatingWithRedirectionSuccess() throws Exception {

        Integer id = 1;
        Rating ratingUpdated = new Rating();
        ratingUpdated.setId(id);
        ratingUpdated.setMoodysRating("Test");
        ratingUpdated.setSandPRating("Test");
        ratingUpdated.setFitchRating("Test");
        ratingUpdated.setOrderNumber(12);

        when(ratingService.updateRating(eq(id),any(Rating.class))).thenReturn(ratingUpdated);
        when(ratingService.getAllRatings()).thenReturn(Collections.singletonList(ratingUpdated));

        mockMvc.perform(post("/rating/update/{id}", id)
                        .with(csrf())
                        .param("moodysRating", "Test")  // notblank donc error
                        .param("sandPRating", "Test")
                        .param("fitchRating", "Test")
                        .param("orderNumber", String.valueOf(3)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

    }

    @WithMockUser(username = "testUser", roles = "USER")
    @Test
    void testDeleteRatingWithRedirectionSuccess() throws Exception {

        Integer id = 1;

        when(ratingService.deleteRating(id)).thenReturn(true);

        mockMvc.perform(post("/rating/delete/{id}", id)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

        verify(ratingService, times(1)).deleteRating(id);

    }


}
