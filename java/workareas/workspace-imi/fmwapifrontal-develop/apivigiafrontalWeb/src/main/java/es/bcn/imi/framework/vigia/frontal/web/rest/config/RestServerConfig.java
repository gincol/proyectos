package es.bcn.imi.framework.vigia.frontal.web.rest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import net.opentrends.openframe.services.rest.apidocs.config.RestServiceDefaultSwaggerConfiguration;

@Configuration
@ComponentScan(basePackages = "es.bcn.imi.framework.vigia.frontal.web.rest.controller")
@Lazy(true)
public class RestServerConfig extends RestServiceDefaultSwaggerConfiguration {
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedMethods("GET", "POST", "PUT", "HEAD", "DELETE");
	}
	
	@Value("${rest.server.multipart.defaultEncoding}")
    private String defaultEncoding;
    @Value("${rest.server.multipart.maxInMemorySize}")
    private int maxInMemorySize;
    @Value("${rest.server.multipart.maxUploadSize}")
    private long maxUploadSize;
    @Value("${rest.server.multipart.maxUploadSizePerFile}")
    private long maxUploadSizePerFile;

    
	
	
    @Override
    public MultipartResolver multipartResolver() {
    	
        final CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setDefaultEncoding(this.defaultEncoding);
        commonsMultipartResolver.setMaxInMemorySize(this.maxInMemorySize);
        commonsMultipartResolver.setMaxUploadSize(this.maxUploadSize);
        commonsMultipartResolver.setMaxUploadSizePerFile(this.maxUploadSizePerFile);
        return (MultipartResolver)commonsMultipartResolver;

    }

	
}