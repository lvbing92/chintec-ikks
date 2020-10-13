package com.chintec.ikks.erp.conifg;

import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    /**
     * @return
     * @Description
     * @Date 2020/06/19
     */
    @Bean
    public Docket controllerApi() {
        ParameterBuilder ticketPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        ticketPar.name("access_token").description("user token")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build();
        pars.add(ticketPar.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("标题:Erp管理")
                        .description("Erp管理")
                        .contact(new Contact("", "www.chintec.net", "@chintec.net"))
                        .version("1.0.0")
                        .build())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.chintec.ikks.erp"))
                .build()
                .globalOperationParameters(pars);

    }

    @SuppressWarnings("unchecked")
    private Predicate<String> paths() {
        return or(regex("/user/api/.*?"));
    }
}