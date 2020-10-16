package es.bcn.imi.framework.vigia.inventari.test.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;

import es.bcn.imi.framework.vigia.inventari.business.config.BusinessConfig;
import es.bcn.imi.framework.vigia.inventari.web.rest.config.RestServerConfig;

@Configuration
@Import({ BusinessConfig.class, RestServerConfig.class, TestsConfig.class })
@Lazy(true)
public class RestServerTestsConfig {
}
