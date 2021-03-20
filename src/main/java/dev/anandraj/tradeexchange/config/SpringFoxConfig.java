package dev.anandraj.tradeexchange.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SpringFoxConfig {

    @Bean
    public Docket postsApi() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("public-api")
                .apiInfo(apiInfo()).select().paths(PathSelectors.any()).build();
    }

    /*
    Swagger UI is accessible in the location /tradeexchange/swagger-ui.html
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Trade Exchange API")
                .description("API to test Trade Exchange application")
                .termsOfServiceUrl("anandraj.dev")
                .contact(new Contact("Anandraj Elumalai", "anandraj.dev", "anandrajpro@gmail.com"))
                .version("1.0").build();
    }
}
