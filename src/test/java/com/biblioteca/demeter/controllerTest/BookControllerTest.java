/*
 * @Author: cristianmarint
 * @Date: 31/12/20 13:34
 */

package com.biblioteca.demeter.controllerTest;

import com.biblioteca.demeter.dto.LoginRequest;
import com.biblioteca.demeter.service.AuthService;
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
public class BookControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private AuthService authService;

    @Before
    public void setUp(){
        mvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    private static final LoginRequest loginRequest = LoginRequest.builder().username("testdatauser").password("123456789").build();
    public String getAuthorizationBearerToken(LoginRequest loginRequest){
        return authService.login(loginRequest).getAuthenticationToken();
    }

//    /api/books/{id}

    @Test
    public void getAllBooks_statusOkAndContentJSon_ifAuthenticated() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/api/books")
                .header("Authorization",getAuthorizationBearerToken(loginRequest)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getBook_statusOkAndContentJSon_ifAuthenticated() throws Exception{
        String expected = "";
        mvc.perform(MockMvcRequestBuilders.get("/api/books/1")
                .header("Authorization", getAuthorizationBearerToken(loginRequest)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expected))
                .andExpect(status().isOk());
    }

    @Test
    public void getBook_statusNotFound_ifAuthenticated() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/api/books/123456789")
                .header("Authorization", getAuthorizationBearerToken(loginRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getBook_statusBadRequest_ifAuthenticated() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/api/books/badRequest/")
                .header("Authorization", getAuthorizationBearerToken(loginRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createBook_statusCreated_ifAllDataIsValidAndAuthenticated() throws Exception{
        String body = "";
        mvc.perform(post("/api/books")
                .header("Authorization", getAuthorizationBearerToken(loginRequest))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(body)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
    }

    @Test
    public void createBook_statusBadRequest_ifDataIsNotValidAndAuthenticated() throws Exception{
        String body = "";
        mvc.perform(post("/api/books")
                .header("Authorization", getAuthorizationBearerToken(loginRequest))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(body)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateBook_statusOk_ifDataIsValidAndAuthenticated() throws Exception{
        String body = "";
        mvc.perform(put("/api/books/2")
                .header("Authorization", getAuthorizationBearerToken(loginRequest))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(body)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void updateBook_statusBadRequest_ifDataIsInValidAndAuthenticated() throws Exception{
        String body = "";
        mvc.perform(put("/api/books/1")
                .header("Authorization", getAuthorizationBearerToken(loginRequest))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(body)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateBook_statusNotFound_ifDataIsValidAndAuthenticated() throws Exception{
        String body = "";
        mvc.perform(put("/api/books/123456789")
                .header("Authorization", getAuthorizationBearerToken(loginRequest))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(body)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteBook_statusNotContent_ifAuthenticated() throws Exception{
        mvc.perform(delete("/api/books/1")
                .header("Authorization", getAuthorizationBearerToken(loginRequest)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteBook_statusNotFound_ifAuthenticated() throws Exception{
        mvc.perform(delete("/api/books/123456789")
                .header("Authorization",getAuthorizationBearerToken(loginRequest)))
                .andExpect(status().isNotFound());
    }

//    /api/books/{id}/categories/{id}

    @Test
    public void getAllBookCategories_statusOk_ifAuthenticated() throws Exception{
        createBook_statusCreated_ifAllDataIsValidAndAuthenticated();
        mvc.perform(get("/api/books/2/categories")
                .header("Authorization",getAuthorizationBearerToken(loginRequest)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllBookCategories_statusNotFound_ifAuthenticated() throws Exception{
        mvc.perform(get("/api/books/123456789/categories")
                .header("Authorization",getAuthorizationBearerToken(loginRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAllBookCategories_statusBadRequest_ifAuthenticated() throws Exception{
        mvc.perform(get("/api/books/badRequest/categories")
                .header("Authorization",getAuthorizationBearerToken(loginRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getBookCategory_statusOk_ifAuthenticated() throws Exception{
        createBook_statusCreated_ifAllDataIsValidAndAuthenticated();
        mvc.perform(get("/api/books/2/categories/2")
                .header("Authorization",getAuthorizationBearerToken(loginRequest)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getBookCategory_statusNotFound_ifAuthenticated() throws Exception{
        createBook_statusCreated_ifAllDataIsValidAndAuthenticated();
        mvc.perform(get("/api/books/2/categories/123456789")
                .header("Authorization",getAuthorizationBearerToken(loginRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getBookCategory_statusBadRequest_ifAuthenticated() throws Exception{
        createBook_statusCreated_ifAllDataIsValidAndAuthenticated();
        mvc.perform(get("/api/books/2/categories/badRequest")
                .header("Authorization",getAuthorizationBearerToken(loginRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createBookCategoryRelation_statusCreated_ifAuthenticated() throws Exception{
        mvc.perform(post("/api/books/2/categories/1")
                .header("Authorization", getAuthorizationBearerToken(loginRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    public void createBookCategoryRelation_statusBadRequest_ifAuthenticated() throws Exception{
        mvc.perform(post("/api/books/2/categories/badRequest")
                .header("Authorization", getAuthorizationBearerToken(loginRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createBookCategoryRelation_statusNotFound_ifAuthenticated() throws Exception{
        mvc.perform(post("/api/books/2/categories/123456789")
                .header("Authorization", getAuthorizationBearerToken(loginRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteBookCategoryRelation_statusNoContent_ifAuthenticated() throws Exception{
        createBook_statusCreated_ifAllDataIsValidAndAuthenticated();
        mvc.perform(delete("/api/books/2/categories/2")
                .header("Authorization",getAuthorizationBearerToken(loginRequest)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteBookCategoryRelation_statusNotfound_ifAuthenticated() throws Exception{
        mvc.perform(delete("/api/books/2/categories/123456789")
                .header("Authorization",getAuthorizationBearerToken(loginRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteBookCategoryRelation_statusBadRequest_ifAuthenticated() throws Exception{
        mvc.perform(delete("/api/books/2/categories/badRequest")
                .header("Authorization",getAuthorizationBearerToken(loginRequest)))
                .andExpect(status().isBadRequest());
    }

//    /api/books/{id}/authors/{id}

    @Test
    public void getAllBookAuthors_statusOk_ifAuthenticated() throws Exception{
        createBook_statusCreated_ifAllDataIsValidAndAuthenticated();
        mvc.perform(get("/api/books/2/authors")
                .header("Authorization",getAuthorizationBearerToken(loginRequest)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllBookAuthors_statusNotFound_ifAuthenticated() throws Exception{
        mvc.perform(get("/api/books/123456789/authors")
                .header("Authorization",getAuthorizationBearerToken(loginRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAllBookAuthors_statusBadRequest_ifAuthenticated() throws Exception{
        mvc.perform(get("/api/books/badRequest/authors")
                .header("Authorization",getAuthorizationBearerToken(loginRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getBookAuthor_statusOk_ifAuthenticated() throws Exception{
        createBook_statusCreated_ifAllDataIsValidAndAuthenticated();
        mvc.perform(get("/api/books/2/authors/2")
                .header("Authorization",getAuthorizationBearerToken(loginRequest)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getBookAuthors_statusNotFound_ifAuthenticated() throws Exception{
        createBook_statusCreated_ifAllDataIsValidAndAuthenticated();
        mvc.perform(get("/api/books/2/authors/123456789")
                .header("Authorization",getAuthorizationBearerToken(loginRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getBookAuthors_statusBadRequest_ifAuthenticated() throws Exception{
        createBook_statusCreated_ifAllDataIsValidAndAuthenticated();
        mvc.perform(get("/api/books/2/authors/badRequest")
                .header("Authorization",getAuthorizationBearerToken(loginRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createBookAuthorRelation_statusCreated_ifAuthenticated() throws Exception{
        mvc.perform(post("/api/books/2/authors/1")
                .header("Authorization", getAuthorizationBearerToken(loginRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    public void createBookAuthorRelation_statusBadRequest_ifAuthenticated() throws Exception{
        mvc.perform(post("/api/books/2/authors/badRequest")
                .header("Authorization", getAuthorizationBearerToken(loginRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createBookAuthor_statusNotFound_ifAuthenticated() throws Exception{
        mvc.perform(post("/api/books/2/authors/123456789")
                .header("Authorization", getAuthorizationBearerToken(loginRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteBookAuthorRelation_statusNoContent_ifAuthenticated() throws Exception{
        createBook_statusCreated_ifAllDataIsValidAndAuthenticated();
        mvc.perform(delete("/api/books/2/authors/1")
                .header("Authorization",getAuthorizationBearerToken(loginRequest)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteBookAuthorRelation_statusNotfound_ifAuthenticated() throws Exception{
        mvc.perform(delete("/api/books/2/authors/123456789")
                .header("Authorization",getAuthorizationBearerToken(loginRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteBookAuthorRelation_statusBadRequest_ifAuthenticated() throws Exception{
        mvc.perform(delete("/api/books/2/authors/badRequest")
                .header("Authorization",getAuthorizationBearerToken(loginRequest)))
                .andExpect(status().isBadRequest());
    }
}
