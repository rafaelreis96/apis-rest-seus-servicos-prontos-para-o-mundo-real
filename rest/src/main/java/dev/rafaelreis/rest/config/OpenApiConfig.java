package dev.rafaelreis.rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI openApiDocumentation() {
		return new OpenAPI()
				.info(
					new Info()
						.title("C.A.R. API")
						.description("API do sistema C.A.R de facilitação e de mobilidade urbana")
						.version("v1.0")
						.contact(new Contact()
							.name("Alexandre Saudate")
							.email("alesaudate@gmail.com")
						)
				);
	}
}
