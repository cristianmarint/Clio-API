/*
 * @Author: cristianmarint
 * @Date: 7/12/20 9:47
 */

package com.biblioteca.demeter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    @Bean
    public Docket demeterApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo());
    }

    private ApiInfo getApiInfo(){
        return new ApiInfoBuilder()
                .title("Biblioteca Demeter")
                .version("1.0")
                .description("API para Biblioteca de eBooks y tracker de lectura")
                .contact(new Contact("Cristian Marin","https://www.cristianmarint.com","cristianmarint@gmail.com"))
                .license("Mmm let us think about it")
                .build();
    }
}