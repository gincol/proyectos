package es.bcn.imi.framework.vigia.inventari.business.config;

import org.apache.ibatis.type.JdbcType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;

import net.opentrends.openframe.services.configuration.annotation.EntornPropertySource;
import net.opentrends.openframe.services.persistence.config.PersistenceServiceDefaultConfiguration;

@Configuration
@Import({ PersistenceServiceDefaultConfiguration.class })
@EntornPropertySource(value = { "classpath:/app/mybatis/fmw/config/persistence.properties" })
@Lazy(true)
public class PersistenceServiceFmwConfig {

	public static final String BEAN_SERVICE_NAME = "myBatisConfiguration";

	@Bean(name = PersistenceServiceFmwConfig.BEAN_SERVICE_NAME)
	public org.apache.ibatis.session.Configuration myBatisConfiguration() {
		org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
		configuration.setMapUnderscoreToCamelCase(true);
		configuration.setJdbcTypeForNull(JdbcType.NULL);
		return configuration;
	}
}