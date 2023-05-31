package com.booklog.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration
@EnableSwagger2WebMvc
@ComponentScan(basePackages = {"com.booklog.member.controller"})
public class SwaggerConfig {
    //	http://localhost/swagger-ui/index.html
    @Bean
    public Docket memberApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("회원")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.booklog.member.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo("회원 api"));
    }

    private ApiInfo apiInfo(String version) {
        return new ApiInfoBuilder()
                .title("Practice Swagger")
                .description("practice swagger config")
                .version("1.0")
                .build();
    }
}
