package es.bcn.imi.framework.vigia.orquestrador.business.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;

import net.opentrends.openframe.services.configuration.annotation.EntornPropertySource;
import net.opentrends.openframe.services.configuration.config.ConfigurationServiceDefaultConfiguration;
import net.opentrends.openframe.services.core.config.CoreServiceDefaultConfiguration;

@Configuration
@Lazy(true)
@Import({ 
	ConfigurationServiceDefaultConfiguration.class, 
	CoreServiceDefaultConfiguration.class })
@EntornPropertySource(value = { "classpath:/app/config/application.properties" })
public class ContainerConditionConfig {
	
}
