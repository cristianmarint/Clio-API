/*
 * @Author: cristianmarint
 * @Date: 30/12/20 7:52
 */

package com.biblioteca.demeter.controllerTest;

import com.biblioteca.demeter.dto.LoginRequest;
import com.biblioteca.demeter.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoryControllerTest{
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
    public void getAllCategories_statusOkAndContentJSon_IfAuthenticated() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/categories")
                .header("Authorization", getAuthorizationBearerToken(loginRequest)))

                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getCategory_statusOkAndContentJson_ifAuthenticated() throws Exception{
        String expected = "{\"id\":1,\"name\":\"Novela de aventuras\",\"description\":\"[Description] Novela de aventuras\",\"numberOfBooks\":1}";

        mvc.perform(MockMvcRequestBuilders.get("/api/categories/1")
                .header("Authorization", getAuthorizationBearerToken(loginRequest)))

                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expected))
                .andExpect(status().isOk());
    }

    @Test
    public void getCategory_statusNotFound_ifAuthenticated() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/api/categories/654915461414")
                .header("Authorization", getAuthorizationBearerToken(loginRequest)))

                .andExpect(status().isNotFound());
    }

    @Test
    public void getCategory_statusBadRequest_ifAuthenticated() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/api/categories/badRequest/")
                .header("Authorization", getAuthorizationBearerToken(loginRequest)))

                .andExpect(status().isBadRequest());
    }

    @Test
    public void createCategory_statusCreated_ifAllDataIsValidAndAuthenticated() throws Exception{
        String body = "{\n    \"name\": \"El categories POST\",\n    \"description\": \"[Description] El categories POST\"\n}";
        mvc.perform(post("/api/categories")
                .header("Authorization", getAuthorizationBearerToken(loginRequest))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(body)
                .accept(MediaType.APPLICATION_JSON_VALUE))

                .andExpect(status().isCreated());
    }

    @Test
    @Order(5)
    public void createCategory_statusBadRequest_ifDataIsNotValidAndAuthenticated() throws Exception{
        String body = "{\"description\": \"[Description] El categories POST\"\n}";
        mvc.perform(post("/api/categories")
                .header("Authorization", getAuthorizationBearerToken(loginRequest))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(body)
                .accept(MediaType.APPLICATION_JSON_VALUE))

                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateCategory_statusOk_ifDataIsValidAndAuthenticated() throws Exception{
        String body = "{\n    \"name\": \"El Pepe\",\n    \"description\": \"[Description] El Pepe\"\n}";
        mvc.perform(put("/api/categories/2")
                .header("Authorization", getAuthorizationBearerToken(loginRequest))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(body)
                .accept(MediaType.APPLICATION_JSON_VALUE))

                .andExpect(status().isOk());
    }

    @Test
    public void updateCategory_statusBadRequest_ifDataIsInValidAndAuthenticated() throws Exception{
        String body = "{\n    \"description\": \"[Description] El Pepe\"\n}";
        mvc.perform(put("/api/categories/1")
                .header("Authorization", getAuthorizationBearerToken(loginRequest))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(body)
                .accept(MediaType.APPLICATION_JSON_VALUE))

                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateCategory_statusNotFound_ifDataIsValidAndAuthenticated() throws Exception{
        String body = "{\n    \"name\": \"El Pepe\",\n    \"description\": \"[Description] El Pepe\"\n}";
        mvc.perform(put("/api/categories/654915461414")
                .header("Authorization", getAuthorizationBearerToken(loginRequest))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(body)
                .accept(MediaType.APPLICATION_JSON_VALUE))

                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteCategory_statusNotContent_ifAuthenticated() throws Exception{
        mvc.perform(delete("/api/categories/1")
                .header("Authorization", getAuthorizationBearerToken(loginRequest)))

                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteCategory_statusNotFound_ifAuthenticated() throws Exception{
        mvc.perform(delete("/api/categories/654915461414")
                .header("Authorization",getAuthorizationBearerToken(loginRequest)))

                .andExpect(status().isNotFound());
    }

    //    /api/categories/{id}/books/{id}
    @Test
    public void getAllCategoryBooks_statusOk_ifAuthenticated() throws Exception{
        createCategory_statusCreated_ifAllDataIsValidAndAuthenticated();
        mvc.perform(get("/api/categories/2/books")
                .header("Authorization",getAuthorizationBearerToken(loginRequest)))

                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllCategoryBooks_statusNotFound_ifAuthenticated() throws Exception{
        mvc.perform(get("/api/categories/123456789/books")
                .header("Authorization",getAuthorizationBearerToken(loginRequest)))

                .andExpect(status().isNotFound());
    }

    @Test
    public void getAllCategoryBooks_statusBadRequest_ifAuthenticated() throws Exception{
        mvc.perform(get("/api/categories/badRequest/books")
                .header("Authorization",getAuthorizationBearerToken(loginRequest)))

                .andExpect(status().isBadRequest());
    }

    @Test
    public void getCategoryBook_statusOk_ifAuthenticated() throws Exception{
        createCategory_statusCreated_ifAllDataIsValidAndAuthenticated();
        mvc.perform(get("/api/categories/2/books/2")
                .header("Authorization",getAuthorizationBearerToken(loginRequest)))

                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getCategoryBook_statusNotFound_ifAuthenticated() throws Exception{
        createCategory_statusCreated_ifAllDataIsValidAndAuthenticated();
        mvc.perform(get("/api/categories/2/books/123456789")
                .header("Authorization",getAuthorizationBearerToken(loginRequest)))

                .andExpect(status().isNotFound());
    }

    @Test
    public void getCategoryBook_statusBadRequest_ifAuthenticated() throws Exception{
        createCategory_statusCreated_ifAllDataIsValidAndAuthenticated();
        mvc.perform(get("/api/categories/2/books/badRequest")
                .header("Authorization",getAuthorizationBearerToken(loginRequest)))

                .andExpect(status().isBadRequest());
    }

    @Test
    public void createCategoryBookRelation_statusCreated_ifAuthenticated() throws Exception{
        mvc.perform(post("/api/categories/2/books/1")
                .header("Authorization", getAuthorizationBearerToken(loginRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    public void createCategoryBookRelation_statusBadRequest_ifAuthenticated() throws Exception{
        mvc.perform(post("/api/categories/2/books/badRequest")
                .header("Authorization", getAuthorizationBearerToken(loginRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createCategoryBookRelation_statusNotFound_ifAuthenticated() throws Exception{
        mvc.perform(post("/api/categories/2/books/123456789")
                .header("Authorization", getAuthorizationBearerToken(loginRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteCategoryBookRelation_statusNoContent_ifAuthenticated() throws Exception{
        createCategoryBookRelation_statusCreated_ifAuthenticated();
        mvc.perform(delete("/api/categories/2/books/1")
                .header("Authorization",getAuthorizationBearerToken(loginRequest)))

                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteCategoryBookRelation_statusNotfound_ifAuthenticated() throws Exception{
        mvc.perform(delete("/api/categories/2/books/123456789")
                .header("Authorization",getAuthorizationBearerToken(loginRequest)))

                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteCategoryBookRelation_statusBadRequest_ifAuthenticated() throws Exception{
        mvc.perform(delete("/api/categories/2/books/badRequest")
                .header("Authorization",getAuthorizationBearerToken(loginRequest)))

                .andExpect(status().isBadRequest());
    }
}
