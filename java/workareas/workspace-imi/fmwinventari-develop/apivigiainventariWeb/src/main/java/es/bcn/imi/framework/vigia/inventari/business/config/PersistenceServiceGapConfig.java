package es.bcn.imi.framework.vigia.inventari.business.config;

import org.apache.ibatis.type.JdbcType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;

import es.bcn.imi.framework.vigia.inventari.persistence.config.PersistenceServiceGapConfiguration;
import net.opentrends.openframe.services.configuration.annotation.EntornPropertySource;

@Configuration
@Import({ PersistenceServiceGapConfiguration.class })
@EntornPropertySource(value = { "classpath:/app/mybatis/gap/config/persistence.properties" })
@Lazy(true)
public class PersistenceServiceGapConfig {

	public static final String BEAN_SERVICE_NAME_GAP = "myBatisConfigurationGap";

	@Bean(name = PersistenceServiceGapConfig.BEAN_SERVICE_NAME_GAP)
	public org.apache.ibatis.session.Configuration myBatisConfigurationGap() {
		org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
		configuration.setMapUnderscoreToCamelCase(true);
		configuration.setJdbcTypeForNull(JdbcType.NULL);
		return configuration;
	}
}
