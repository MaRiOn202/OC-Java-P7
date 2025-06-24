package com.openclassrooms.poseidon.controller;


import com.openclassrooms.poseidon.PoseidonCapitalSolutionApplication;
import com.openclassrooms.poseidon.controllers.RuleNameController;
import com.openclassrooms.poseidon.entity.RuleName;
import com.openclassrooms.poseidon.service.RuleNameService;
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

@WebMvcTest(controllers = {RuleNameController.class})
@ContextConfiguration(classes = PoseidonCapitalSolutionApplication.class)
public class RuleNameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RuleNameService ruleNameService;

    @MockitoBean
    private Principal principal;


    @Test
    void testHomeShouldReturnRuleNameList() throws Exception {

        // Simuler list
        List<RuleName> ruleNameList = List.of(new RuleName(), new RuleName());
        when(ruleNameService.getAllRuleNames()).thenReturn(ruleNameList);

        mockMvc.perform(get("/ruleName/list").with(user("Michel")))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/list"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("ruleNames"));

    }

    @WithMockUser(username = "testUser", roles = "USER")
    @Test
    void testAddRuleNameFormShouldReturnRuleNameForm() throws Exception {

        mockMvc.perform(get("/ruleName/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"))
                .andExpect(model().attribute("user", "testUser"));

    }

    @WithMockUser(username = "testUser", roles = "USER")
    @Test
    void testValidateRedirectionSuccess() throws Exception {

        mockMvc.perform(post("/ruleName/validate")
                        .param("name", "Test")
                        .param("description", "Test")
                        .param("json", "Test")
                        .param("template", "Test")
                        .param("sqlStr", "Test")
                        .param("sqlPart", "Test")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));

    }

    @WithMockUser(username = "testUser", roles = "USER")
    @Test
    void testValidateWithErrorOfValidation() throws Exception {

        mockMvc.perform(post("/ruleName/validate")
                        .with(csrf())
                .param("name", "")     // notblank
                .param("description", "Test")
                .param("json", "Test")
                .param("template", "Test")
                .param("sqlStr", "Test")
                .param("sqlPart", "Test"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeHasFieldErrors("ruleName", "name"));

    }

    @WithMockUser(username = "testUser", roles = "USER")
    @Test
    void testShowUpdateFormShouldReturnUpdateForm() throws Exception {
        Integer id = 1;
        RuleName ruleName = new RuleName();
        ruleName.setId(id);
        ruleName.setName("Test");
        ruleName.setDescription("Test");
        ruleName.setJson("Test");
        ruleName.setTemplate("Test");

        when(ruleNameService.getRuleNameById(id)).thenReturn(ruleName);

        mockMvc.perform(get("/ruleName/update/{id}", id)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"))
                .andExpect(model().attribute("user", "testUser"))
                .andExpect(model().attribute("ruleName", ruleName));
        verify(ruleNameService).getRuleNameById(id);

    }


    @WithMockUser(username = "testUser", roles = "USER")
    @Test
    void testUpdateRuleNameWithErrorOfValidation() throws Exception {

        Integer id = 1;

        mockMvc.perform(post("/ruleName/update/{id}", id)
                        .with(csrf())
                        .param("name", "")     // notblank
                        .param("description", "Test")
                        .param("json", "Test")
                        .param("template", "Test")
                        .param("sqlStr", "Test")
                        .param("sqlPart", "Test"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("ruleName"))
                .andExpect(model().hasErrors());

    }

    @WithMockUser(username = "testUser", roles = "USER")
    @Test
    void testUpdateRuleNameWithRedirectionSuccess() throws Exception {

        Integer id = 1;
        RuleName ruleNameUpdated = new RuleName();
        ruleNameUpdated.setId(id);
        ruleNameUpdated.setName("Test");
        ruleNameUpdated.setDescription("Test");
        ruleNameUpdated.setJson("Test");
        ruleNameUpdated.setTemplate("Test");

        when(ruleNameService.updateRuleName(eq(id),any(RuleName.class))).thenReturn(ruleNameUpdated);
        when(ruleNameService.getAllRuleNames()).thenReturn(Collections.singletonList(ruleNameUpdated));

        mockMvc.perform(post("/ruleName/update/{id}", id)
                        .with(csrf())
                        .param("name", "Test")
                        .param("description", "Test")
                        .param("json", "Test")
                        .param("template", "Test")
                        .param("sqlStr", "Test")
                        .param("sqlPart", "Test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));

    }

    @WithMockUser(username = "testUser", roles = "USER")
    @Test
    void testDeleteRuleNameWithRedirectionSuccess() throws Exception {

        Integer id = 1;

        when(ruleNameService.deleteRuleName(id)).thenReturn(true);

        mockMvc.perform(post("/ruleName/delete/{id}", id)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));

        verify(ruleNameService, times(1)).deleteRuleName(id);

    }


}
