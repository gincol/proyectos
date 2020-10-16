package es.bcn.imi.framework.vigia.frontal.business.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;

//import es.bcn.imi.libmybatis.business.config.SharedLibPersistenceServiceConfig;
import net.opentrends.openframe.services.configuration.config.ConfigurationServiceDefaultConfiguration;
import net.opentrends.openframe.services.core.config.CoreServiceDefaultConfiguration;

@Configuration
@Import({ 
	ConfigurationServiceDefaultConfiguration.class, 
	CoreServiceDefaultConfiguration.class})
@Lazy(true)
@ComponentScan(basePackages = { "es.bcn.imi.framework.vigia", "es.bcn.vigia.fmw.libutils", "es.bcn.vigia.fmw.libcommons"})

public class BusinessConfig {
}