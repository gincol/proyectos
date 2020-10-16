package es.bcn.imi.framework.vigia.orquestrador.test.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;

import es.bcn.imi.framework.vigia.orquestrador.business.config.BusinessConfig;
import es.bcn.imi.framework.vigia.orquestrador.web.rest.config.RestServerConfig;

@Configuration
@Import({ BusinessConfig.class, RestServerConfig.class, TestsConfig.class })
@Lazy(true)
public class RestServerTestsConfig {
}
