/*
 * @Author: cristianmarint
 * @Date: 21/12/20 10:27
 */

package com.biblioteca.demeter.controllerTest;

import com.biblioteca.demeter.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private AuthService authService;

    @Test
    public void shouldRegisterUser() throws Exception{
        String email = "testuser@email.com";
        String username = "testuser";
        String password = "123456789";
        String body = "{\"username\":\"" + username + "\", \"password\":\""+ password + "\",\"email\":\""+email+"\"}";

        mvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(body)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldAuthenticateRegisteredUser() throws Exception{
        LoginRequest loginRequest = LoginRequest.builder().username("testuser").password("123456789").build();
        String token = authService.login(loginRequest).getAuthenticationToken();
        mvc.perform(MockMvcRequestBuilders.get("/api/authors").header("Authorization", token)).andExpect(status().isOk());
    }
}
