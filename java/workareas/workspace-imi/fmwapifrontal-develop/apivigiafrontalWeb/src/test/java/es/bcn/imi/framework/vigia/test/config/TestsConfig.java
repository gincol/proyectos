package es.bcn.imi.framework.vigia.test.config;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;

@Configuration
@Lazy(true)
public class TestsConfig implements EnvironmentAware {

	@Override
	public void setEnvironment(Environment environment) {
	}

}
