/*
 * @Author: cristianmarint
 * @Date: 3/1/21 10:28
 */

package com.api.clio;

import com.api.clio.config.SwaggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@Import(SwaggerConfiguration.class)
public class ClioApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClioApplication.class, args);
	}

}
