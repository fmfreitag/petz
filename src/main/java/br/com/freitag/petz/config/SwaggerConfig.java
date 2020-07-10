package br.com.freitag.petz.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {


    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
          .select()
          .apis(RequestHandlerSelectors.basePackage("br.com.freitag.petz.controller"))
          .paths(PathSelectors.any())
          .build()
          .apiInfo(apiInfo()).securitySchemes(Arrays.asList(apiKey()))
          .useDefaultResponseMessages(false)
          .securityContexts(Arrays.asList(securityContext()));
    }


    private ApiKey apiKey() {
		return new ApiKey("Bearer", "Authorization", "header");
	}

    private ApiInfo apiInfo() {
        return new ApiInfo(
          "Desafio PetZ",
          "API de integração",
          "1.0",
          "",
          null,
          "",
          "",
          Collections.emptyList());
   }

    private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.any()).build();
	}

	private List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Arrays.asList(new SecurityReference("Bearer", authorizationScopes));
	}
  }