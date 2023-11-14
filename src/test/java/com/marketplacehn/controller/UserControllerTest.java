package com.marketplacehn.controller;

import com.marketplacehn.entity.User;
import com.marketplacehn.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = UserController.class)
class UserControllerTest extends AbstractTestController {
    @MockBean
    UserService underTest;
    User user;
    private static final String USER_ID = UUID.randomUUID().toString();
    private static final String USER_EMAIL = "test@gmail.com";
    private static final String PREFIX = "/api/v1/users";

    @BeforeEach
    void setUp() {
        user = User.builder()
                .userId(USER_ID)
                .build();
    }

    @Test
    void itShouldFindUserById() throws Exception {
        when(underTest.findUserById(USER_ID))
                .thenReturn(user);
        ResultActions result = doRequestFindUserById();
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.payload.userId").value(USER_ID));
    }

    @Test
    void itShouldFindUserByEmail() throws Exception {
        when(underTest.findUserByUserName(USER_EMAIL))
                .thenReturn(user);
        ResultActions result = doRequestFindUserByEmail();
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User found successfully."));
    }

    @Test
    void itShouldSaveUser() throws Exception {
        when(underTest.saveUser(user))
                .thenReturn(new User());
        ResultActions result = doRequestSaveUser();
        result
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("User was created."));
    }

    @Test
    void itShouldDeleteUserById() throws Exception {
        doNothing()
                .when(underTest).deleteUserById(USER_ID);
        ResultActions result = doRequestDeleteUserById();
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message")
                        .value("User deleted successfully."));
    }

    private ResultActions doRequestFindUserById() throws Exception {
        return mvc
                .perform(get(PREFIX + "/{userId}", USER_ID)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON));
    }

    private ResultActions doRequestFindUserByEmail() throws Exception {
        return mvc
                .perform(get(PREFIX)
                        .param("userName", USER_EMAIL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON));
    }

    private ResultActions doRequestSaveUser() throws Exception {
        return mvc.perform(post(PREFIX, user)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(user)));
    }

    private ResultActions doRequestDeleteUserById() throws Exception {
        return mvc
                .perform(delete(PREFIX + "/{userId}", USER_ID)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON));
    }
}
