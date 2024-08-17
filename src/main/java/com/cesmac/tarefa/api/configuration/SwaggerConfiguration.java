package com.cesmac.tarefa.api.configuration;

import com.google.common.base.Predicates;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static java.util.Collections.singletonList;

@Configuration
@EnableSwagger2
@Profile("swagger")
public class SwaggerConfiguration {

    @Value("${app.nome}")
    private String nomeApi;

    @Value("${app.versao}")
    private String versaoApi;

    @Bean
    public Docket docketDocumentacaoPadrao() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(Predicates.not(PathSelectors.regex("/error")))
                .build()
                .useDefaultResponseMessages(false)
                .genericModelSubstitutes(ResponseEntity.class)
                .securitySchemes(singletonList(obterTokenAcesso()))
                .apiInfo(obterInformacoesApi());
    }

    private ApiInfo obterInformacoesApi() {
        return new ApiInfoBuilder()
                .title(nomeApi)
                .version(versaoApi)
                .build();
    }

    private ApiKey obterTokenAcesso() {
        return new ApiKey("Bearer", "Authorization", "Header");
    }
}
