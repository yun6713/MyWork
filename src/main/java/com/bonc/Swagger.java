package com.bonc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by ganzl on 2017/11/6.
 * Swagger2配置类 在与spring boot集成时，放在与Application.java同级的目录下。
 * 通过@Configuration注解，让Spring来加载该类配置。 再通过@EnableSwagger2注解来启用Swagger2。
 */
@Configuration
@EnableSwagger2
public class Swagger {

    @Bean
    public Docket createRestApi() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        docket = docket.select()
        		.apis(RequestHandlerSelectors.basePackage(getClass().getPackage().getName()))
        		.build();
        return docket.apiInfo(apiInfo());
    }

    /**
     * 创建该API的基本信息
     *
     * @return API服务描述信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("测试项目")
                .description("操作用户功能服务接口)")
                .contact("Tianlin")
                .termsOfServiceUrl("what's fuck?")
                .version("1.0")
                .build();
    }
}
