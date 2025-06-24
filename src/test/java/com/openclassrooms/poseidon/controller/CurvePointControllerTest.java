package com.openclassrooms.poseidon.controller;



import com.openclassrooms.poseidon.PoseidonCapitalSolutionApplication;
import com.openclassrooms.poseidon.controllers.CurvePointController;
import com.openclassrooms.poseidon.entity.CurvePoint;
import com.openclassrooms.poseidon.service.CurvePointService;
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

@WebMvcTest(controllers = {CurvePointController.class})
@ContextConfiguration(classes = PoseidonCapitalSolutionApplication.class)
public class CurvePointControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CurvePointService curvePointService;

    @MockitoBean
    private Principal principal;


    @Test
    void testHomeShouldReturnCurvePointList() throws Exception {

        // Simuler list
        List<CurvePoint> curvePointList = List.of(new CurvePoint(), new CurvePoint());
        when(curvePointService.getAllCurvePoints()).thenReturn(curvePointList);

        mockMvc.perform(get("/curvePoint/list").with(user("Michel")))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/list"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("curvePoints"));

    }

    @WithMockUser(username = "testUser", roles = "USER")
    @Test
    void testAddCurvePointFormShouldReturnCurvePointForm() throws Exception {

        mockMvc.perform(get("/curvePoint/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"))
                .andExpect(model().attribute("user", "testUser"));

    }

    @WithMockUser(username = "testUser", roles = "USER")
    @Test
    void testValidateRedirectionSuccess() throws Exception {

        mockMvc.perform(post("/curvePoint/validate")
                        .param("curveId", String.valueOf(1))
                        .param("term", String.valueOf(10.0))
                        .param("value", String.valueOf(12.0))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

    }

    @WithMockUser(username = "testUser", roles = "USER")
    @Test
    void testValidateWithErrorOfValidation() throws Exception {

        mockMvc.perform(post("/curvePoint/validate")
                        .with(csrf())
                .param("curveId", "")  // notnull donc error
                .param("term", "13.0")
                .param("value", "12.0"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeHasFieldErrors("curvePoint", "curveId"));

    }

    @WithMockUser(username = "testUser", roles = "USER")
    @Test
    void testShowUpdateFormShouldReturnUpdateForm() throws Exception {
        Integer id = 1;
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setId(id);
        curvePoint.setCurveId(1);
        curvePoint.setTerm(15.0);
        curvePoint.setValue(12.0);

        when(curvePointService.getCurvePointById(id)).thenReturn(curvePoint);

        mockMvc.perform(get("/curvePoint/update/{id}", id)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"))
                .andExpect(model().attribute("user", "testUser"))
                .andExpect(model().attribute("curvePoint", curvePoint));
        verify(curvePointService).getCurvePointById(id);

    }


    @WithMockUser(username = "testUser", roles = "USER")
    @Test
    void testUpdateCurvePointWithErrorOfValidation() throws Exception {

        Integer id = 1;

        mockMvc.perform(post("/curvePoint/update/{id}", id)
                        .with(csrf())
                        //.param("curveId", String.valueOf(1)  // notnull donc error
                        .param("term", "13.0")
                        .param("value", "12.0"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("curvePoint"))
                .andExpect(model().hasErrors());

    }

    @WithMockUser(username = "testUser", roles = "USER")
    @Test
    void testUpdateCurvePointWithRedirectionSuccess() throws Exception {

        Integer id = 1;
        CurvePoint curvePointUpdated = new CurvePoint();
        curvePointUpdated.setId(id);
        curvePointUpdated.setCurveId(1);
        curvePointUpdated.setTerm(15.0);
        curvePointUpdated.setValue(12.0);

        when(curvePointService.updateCurvePoint(eq(id),any(CurvePoint.class))).thenReturn(curvePointUpdated);
        when(curvePointService.getAllCurvePoints()).thenReturn(Collections.singletonList(curvePointUpdated));

        mockMvc.perform(post("/curvePoint/update/{id}", id)
                        .with(csrf())
                        .param("curveId", String.valueOf(6))
                        .param("term", String.valueOf(16.0))
                        .param("value", "10.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

    }

    @WithMockUser(username = "testUser", roles = "USER")
    @Test
    void testDeleteCurvePointWithRedirectionSuccess() throws Exception {

        Integer id = 1;

        when(curvePointService.deleteCurvePoint(id)).thenReturn(true);

        mockMvc.perform(post("/curvePoint/delete/{id}", id)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

        verify(curvePointService, times(1)).deleteCurvePoint(id);

    }



















}
