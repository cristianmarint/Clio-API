package com.biblioteca.demeter;

import com.biblioteca.demeter.config.SwaggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@Import(SwaggerConfiguration.class)
public class BibliotecaDemeterApplication {

	public static void main(String[] args) {
		SpringApplication.run(BibliotecaDemeterApplication.class, args);
	}

}
