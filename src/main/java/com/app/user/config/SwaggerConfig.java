package com.app.user.config;

import java.util.ArrayList;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer{

	public static final Contact DEFAULT_CONTACT = new Contact("Vianey Vargas", "https://pruebatestchile.com/",
			"reciboempresas@gmail.com");
	public static final ApiInfo DEFAULT_API_INFO = new ApiInfo("Api Documentation", "PruebaTestChile Api Documentation", "1.0",
			"PREMIUM", DEFAULT_CONTACT, "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0",
			new ArrayList<VendorExtension>());

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				//.apiInfo(DEFAULT_API_INFO)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build();

	}
	private ApiInfo apiInfo() {
		return new ApiInfo("Api Documentación", 
				"Buen Creyente Api Documentation", 
				"1.0", 
				"Términos y Condiciones", new Contact("Vianey Vargas", "https://pruebatestchile.com/", "reciboempresas@gmail.com"), 
				"Licencia Apache 2.0", 
				"http://www.apache.org/licenses/LICENSE-2.0", 
				Collections.emptyList());
	}
}
