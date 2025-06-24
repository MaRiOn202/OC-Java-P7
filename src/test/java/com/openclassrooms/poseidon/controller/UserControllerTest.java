package com.openclassrooms.poseidon.controller;

import com.openclassrooms.poseidon.PoseidonCapitalSolutionApplication;
import com.openclassrooms.poseidon.controllers.UserController;
import com.openclassrooms.poseidon.entity.User;
import com.openclassrooms.poseidon.service.UserService;
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


@WebMvcTest(controllers = {UserController.class})
@ContextConfiguration(classes = PoseidonCapitalSolutionApplication.class)
public class UserControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;


    @Test
    void testHomeShouldReturnUserList() throws Exception {

        // Simuler list
        List<User> userList = List.of(new User(), new User());
        when(userService.getAllUsers()).thenReturn(userList);

        mockMvc.perform(get("/user/list").with(user("Michel")))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"))
                .andExpect(model().attributeExists("users"));

    }

    @WithMockUser(username = "testUser", roles = "USER")
    @Test
    void testAddUserFormShouldReturnUserForm() throws Exception {

        mockMvc.perform(get("/user/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"))
                .andExpect(model().attributeExists("user"));

    }

    @WithMockUser(username = "testUser", roles = "USER")
    @Test
    void testValidateRedirectionSuccess() throws Exception {

        mockMvc.perform(post("/user/validate")
                        .param("username", "Test")
                        .param("password", "TestTest@20")
                        .param("fullname", "Test Test")
                        .param("role", "USER")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));

    }

    @WithMockUser(username = "testUser", roles = "USER")
    @Test
    void testValidateWithErrorOfValidation() throws Exception {

        mockMvc.perform(post("/user/validate")
                .with(csrf())
                .param("username", "")  // notblank
                .param("password", "TestTest@20")
                .param("fullname", "Test Test")
                .param("role", "USER"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"))
                .andExpect(model().attributeExists("userForm"))
                .andExpect(model().attributeHasFieldErrors("userForm", "username"));

    }

    @WithMockUser(username = "testUser", roles = "USER")
    @Test
    void testShowUpdateFormShouldReturnUpdateForm() throws Exception {
        Integer id = 1;
        User user = new User();
        user.setId(id);
        user.setUsername("Test");
        user.setPassword("");
        user.setFullname("Test Test");
        user.setRole("USER");

        when(userService.getUserById(id)).thenReturn(user);

        mockMvc.perform(get("/user/update/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"))
                .andExpect(model().attribute("userForm", user));
        verify(userService).getUserById(id);

    }


    @WithMockUser(username = "testUser", roles = "USER")
    @Test
    void testUpdateUserWithErrorOfValidation() throws Exception {

        Integer id = 1;

        mockMvc.perform(post("/user/update/{id}", id)
                        .with(csrf())
                        .param("username", "")  // notblank
                        .param("password", "TestTest@20")
                        .param("fullname", "Test Test")
                        .param("role", "USER"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"))
                .andExpect(model().attributeExists("userForm"))
                .andExpect(model().attributeHasFieldErrors("userForm", "username"));

    }

    @WithMockUser(username = "testUser", roles = "USER")
    @Test
    void testUpdateUserWithRedirectionSuccess() throws Exception {

        Integer id = 1;
        User userUpdated = new User();
        userUpdated.setUsername("Test");
        userUpdated.setPassword("");
        userUpdated.setFullname("Test Test");
        userUpdated.setRole("USER");

        when(userService.updateUser(eq(id),any(User.class))).thenReturn(userUpdated);
        when(userService.getAllUsers()).thenReturn(Collections.singletonList(userUpdated));

        mockMvc.perform(post("/user/update/{id}", id)
                        .with(csrf())
                        .param("username", "Test")
                        .param("password", "TestTest@20")
                        .param("fullname", "Test Test")
                        .param("role", "USER"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));

    }

    @WithMockUser(username = "testUser", roles = "USER")
    @Test
    void testDeleteUserWithRedirectionSuccess() throws Exception {

        Integer id = 1;

        when(userService.deleteUser(id)).thenReturn(true);

        mockMvc.perform(post("/user/delete/{id}", id)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));

        verify(userService, times(1)).deleteUser(id);

    }

}
