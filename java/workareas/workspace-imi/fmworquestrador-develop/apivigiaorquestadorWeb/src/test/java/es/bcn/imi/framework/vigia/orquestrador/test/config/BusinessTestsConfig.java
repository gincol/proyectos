package es.bcn.imi.framework.vigia.orquestrador.test.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;

import es.bcn.imi.framework.vigia.orquestrador.business.config.BusinessConfig;

@Configuration
@Import({ BusinessConfig.class, TestsConfig.class })
@Lazy(true)
public class BusinessTestsConfig {
}
