package cat.vn.owasp.config;

import static springfox.documentation.builders.PathSelectors.regex;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Value("${swagger.basepackage}")
	private String basePackage;

	@Bean
	public Docket homeApi(ServletContext servletContext) {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage(basePackage))
				.paths(homePaths())
				.build()
				.apiInfo(apiInfo());
	}

	private Predicate<String> homePaths() {
		return regex("/.*");
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Gincol API")
				.description("gincol API reference for developers")
				.termsOfServiceUrl("https://www.apache.org/licenses/LICENSE-2.0")
				.license("Apache License Version 2.0")
				.licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
				.version("1.0")
				.build();
	}

}
