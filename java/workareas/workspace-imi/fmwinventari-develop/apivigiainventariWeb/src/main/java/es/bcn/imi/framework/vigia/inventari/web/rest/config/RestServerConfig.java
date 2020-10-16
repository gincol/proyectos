package es.bcn.imi.framework.vigia.inventari.web.rest.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import net.opentrends.openframe.services.rest.apidocs.config.RestServiceDefaultSwaggerConfiguration;

@Configuration
@ComponentScan(basePackages = "es.bcn.imi.framework.vigia.inventari.web.rest.controller")
@Lazy(true)
public class RestServerConfig extends RestServiceDefaultSwaggerConfiguration {
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedMethods("GET", "POST", "PUT", "HEAD", "DELETE");
	}

}