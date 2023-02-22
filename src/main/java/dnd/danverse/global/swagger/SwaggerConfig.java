package dnd.danverse.global.swagger;

import dnd.danverse.domain.jwt.service.SessionUser;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  private static final Contact DEFAULT_CONTACT = new Contact("이수찬", "http://www.danverse.com", "tncksdl05@gmail.com");

  private static final ApiInfo DEFAULT_API_INFO = new ApiInfo("REST API", "My REST API", "1.0",
      "urn:tos", DEFAULT_CONTACT.toString(), "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0");

  private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<>(Arrays.asList("application/json"));

  @Bean
  public Docket api(){
    return new Docket(DocumentationType.SWAGGER_2)
        .protocols(new HashSet<>(List.of("https")))
        .produces(DEFAULT_PRODUCES_AND_CONSUMES)
        .consumes(DEFAULT_PRODUCES_AND_CONSUMES)
        .ignoredParameterTypes(SessionUser.class)
        .apiInfo(DEFAULT_API_INFO).select()
        .apis(RequestHandlerSelectors.basePackage("dnd.danverse.domain").or(RequestHandlerSelectors.basePackage("dnd.danverse.global.s3.controller")))
        .paths(PathSelectors.any())
        .build();
  }
}
