/*
 * @Author: cristianmarint
 * @Date: 30/12/20 7:52
 */

package com.api.clio.controllerTest;

import com.api.clio.dto.LoginRequest;
import com.api.clio.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthorControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private AuthService authService;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    private static final LoginRequest loginRequest = LoginRequest.builder().username("testdatauser").password("123456789").build();
    public String getAuthorizationBearerToken(LoginRequest loginRequest){
        return authService.login(loginRequest).getAuthenticationToken();
    }

    @Test
    public void getAllAuthors_statusOkAndContentJSon_IfAuthenticated() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/authors")
                .header("Authorization", getAuthorizationBearerToken(loginRequest)))

                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getAuthor_statusOkAndContentJson_ifAuthenticated() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/api/authors/1")
                .header("Authorization", getAuthorizationBearerToken(loginRequest)))

                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getAuthor_statusNotFound_ifAuthenticated() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/api/authors/654915461414")
                .header("Authorization", getAuthorizationBearerToken(loginRequest)))

                .andExpect(status().isNotFound());
    }

    @Test
    public void getAuthor_statusBadRequest_ifAuthenticated() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/api/authors/badRequest/")
                .header("Authorization", getAuthorizationBearerToken(loginRequest)))

                .andExpect(status().isBadRequest());
    }

    @Test
    public void createAuthor_statusCreated_ifAllDataIsValidAndAuthenticated() throws Exception{
        String body = "{\n" +
                "    \"name\": \"El authors POST\",\n" +
                "    \"alive\": false,\n" +
                "    \"dateOfBirth\": \"1985-08-12T08:25:24.00Z\"\n" +
                "}";
        mvc.perform(post("/api/authors")
                .header("Authorization", getAuthorizationBearerToken(loginRequest))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(body)
                .accept(MediaType.APPLICATION_JSON_VALUE))

                .andExpect(status().isCreated());
    }

    @Test
    public void createAuthor_statusBadRequest_ifDataIsNotValidAndAuthenticated() throws Exception{
        String body = "{\"alive\": \"[Description] El authors POST\"\n}";
        mvc.perform(post("/api/authors")
                .header("Authorization", getAuthorizationBearerToken(loginRequest))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(body)
                .accept(MediaType.APPLICATION_JSON_VALUE))

                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateAuthor_statusOk_ifDataIsValidAndAuthenticated() throws Exception{
        String body = "{\n" +
                "    \"name\": \"El Pepe\",\n" +
                "    \"description\": \"[Description] El Pepe\",\n" +
                "    \"alive\": false,\n" +
                "    \"dateOfBirth\": \"1985-08-12T08:25:24.00Z\"\n" +
                "}";
        mvc.perform(put("/api/authors/2")
                .header("Authorization", getAuthorizationBearerToken(loginRequest))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(body)
                .accept(MediaType.APPLICATION_JSON_VALUE))

                .andExpect(status().isOk());
    }

    @Test
    public void updateAuthor_statusBadRequest_ifDataIsInValidAndAuthenticated() throws Exception{
        String body = "{\n    \"alive\": \"[Description] El Pepe\"\n}";
        mvc.perform(put("/api/authors/1")
                .header("Authorization", getAuthorizationBearerToken(loginRequest))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(body)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateAuthor_statusNotFound_ifDataIsValidAndAuthenticated() throws Exception{
        String body = "{\n" +
                "    \"name\": \"El Pepe\",\n" +
                "    \"description\": \"[Description] El Pepe\",\n" +
                "    \"alive\": false,\n" +
                "    \"dateOfBirth\": \"1985-08-12T08:25:24.00Z\"\n" +
                "}";
        mvc.perform(put("/api/authors/123456789")
                .header("Authorization", getAuthorizationBearerToken(loginRequest))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(body)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteAuthor_statusNotContent_ifAuthenticated() throws Exception{
        mvc.perform(delete("/api/authors/1")
                .header("Authorization", getAuthorizationBearerToken(loginRequest)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteAuthor_statusNotFound_ifAuthenticated() throws Exception{
        mvc.perform(delete("/api/authors/123456789")
                .header("Authorization",getAuthorizationBearerToken(loginRequest)))
                .andExpect(status().isNotFound());
    }

    //    /api/authors/{id}/books/{id}
    @Test
    public void getAllAuthorBooks_statusOk_ifAuthenticated() throws Exception{
        createAuthor_statusCreated_ifAllDataIsValidAndAuthenticated();
        mvc.perform(get("/api/authors/2/books")
                .header("Authorization",getAuthorizationBearerToken(loginRequest)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllAuthorBooks_statusNotFound_ifAuthenticated() throws Exception{
        mvc.perform(get("/api/authors/123456789/books")
                .header("Authorization",getAuthorizationBearerToken(loginRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAllAuthorBooks_statusBadRequest_ifAuthenticated() throws Exception{
        mvc.perform(get("/api/authors/badRequest/books")
                .header("Authorization",getAuthorizationBearerToken(loginRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getAuthorBook_statusOk_ifAuthenticated() throws Exception{
        createAuthor_statusCreated_ifAllDataIsValidAndAuthenticated();
        mvc.perform(get("/api/authors/3/books/2")
                .header("Authorization",getAuthorizationBearerToken(loginRequest)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getAuthorBook_statusNotFound_ifAuthenticated() throws Exception{
        createAuthor_statusCreated_ifAllDataIsValidAndAuthenticated();
        mvc.perform(get("/api/authors/2/books/123456789")
                .header("Authorization",getAuthorizationBearerToken(loginRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAuthorBook_statusBadRequest_ifAuthenticated() throws Exception{
        createAuthor_statusCreated_ifAllDataIsValidAndAuthenticated();
        mvc.perform(get("/api/authors/2/books/badRequest")
                .header("Authorization",getAuthorizationBearerToken(loginRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createAuthorBookRelation_statusCreated_ifAuthenticated() throws Exception{
        mvc.perform(post("/api/authors/2/books/2")
                .header("Authorization", getAuthorizationBearerToken(loginRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    public void createAuthorBookRelation_statusBadRequest_ifAuthenticated() throws Exception{
        mvc.perform(post("/api/authors/2/books/badRequest")
                .header("Authorization", getAuthorizationBearerToken(loginRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createAuthorBookRelation_statusNotFound_ifAuthenticated() throws Exception{
        mvc.perform(post("/api/authors/2/books/123456789")
                .header("Authorization", getAuthorizationBearerToken(loginRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteAuthorBookRelation_statusNoContent_ifAuthenticated() throws Exception{
        createAuthorBookRelation_statusCreated_ifAuthenticated();
        mvc.perform(delete("/api/authors/2/books/2")
                .header("Authorization",getAuthorizationBearerToken(loginRequest)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteAuthorBookRelation_statusNotfound_ifAuthenticated() throws Exception{
        mvc.perform(delete("/api/authors/2/books/123456789")
                .header("Authorization",getAuthorizationBearerToken(loginRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteAuthorBookRelation_statusBadRequest_ifAuthenticated() throws Exception{
        mvc.perform(delete("/api/authors/2/books/badRequest")
                .header("Authorization",getAuthorizationBearerToken(loginRequest)))
                .andExpect(status().isBadRequest());
    }
}