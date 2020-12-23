/*
 * @Author: cristianmarint
 * @Date: 21/12/20 10:27
 */

package com.biblioteca.demeter.controllerTest;

import com.biblioteca.demeter.model.VerificationToken;
import com.biblioteca.demeter.repository.UserRepository;
import com.biblioteca.demeter.repository.VerificationTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    private static final String EMAIL = "testuser@email.com";
    private static final String USERNAME = "testuser";
    private static final String PASSWORD_PLAIN  = "123456789";

    @Test
    @Order(1)
    public void shouldRegisterUser() throws Exception{
        String body = "{\"username\":\"" + USERNAME + "\", \"password\":\""+ PASSWORD_PLAIN + "\",\"email\":\""+EMAIL+"\"}";
        log.info("REGISTER-> "+body);
        mvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(body)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string("User Registration Successful"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    public void shouldVerifyAccount() throws Exception {
        Long testUserId = userRepository.findByUsername(USERNAME).get().getId();
        Optional<VerificationToken> token = verificationTokenRepository.findByUserId(testUserId);
        mvc.perform(get("/api/auth/account-verification/"+token.get().getToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.ALL))
                .andExpect(content().string("Account activated successfully"))
                .andExpect(status().isOk());
    }

//    TODO: Fix-> springframework.security.authentication.BadCredentialsException: Bad credentials
//    @Test
//    @Order(3)
//    public void shouldAuthenticateUser() throws Exception{
//        String body = "{\"username\":\"" + "testuser" + "\", \"password\":\""+ "123456789" + "\"}";
//        mvc.perform(post("/api/auth/login")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(body)
//                .accept(MediaType.ALL))
//                .andExpect(status().isOk());
//    }
}
