package com.sean.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @description: some desc
 * @author: congjun
 * @email: 66@7788.com
 * @date: 2022-09-21 12:46
 */
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket customDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.myblog.controller"))
                .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("Team", "http://www.my.com", "my@my.com");
        return new ApiInfoBuilder()
                .title("Docment Title")
                .description("Document description")
                .contact(contact)   // 联系方式
                .version("1.1.0")  // 版本
                .build();
    }
}
