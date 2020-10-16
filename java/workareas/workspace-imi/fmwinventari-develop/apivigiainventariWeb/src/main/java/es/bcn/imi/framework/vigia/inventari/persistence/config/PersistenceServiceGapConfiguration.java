package es.bcn.imi.framework.vigia.inventari.persistence.config;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.WebSphereUowTransactionManager;
import org.springframework.util.Assert;

import es.bcn.imi.framework.vigia.inventari.persistence.template.MyBatisTemplateGap;
import es.bcn.imi.framework.vigia.inventari.persistence.template.impl.MyBatisTemplateGapImpl;
import net.opentrends.openframe.services.configuration.ContainerInformationService;
import net.opentrends.openframe.services.configuration.annotation.EntornPropertySource;
import net.opentrends.openframe.services.configuration.condition.BatchProcessCondition;
import net.opentrends.openframe.services.configuration.condition.CommonContainerCondition;
import net.opentrends.openframe.services.configuration.condition.ConditionalOnContext;
import net.opentrends.openframe.services.configuration.condition.ConditionalOnContext.ConditionsMatching;
import net.opentrends.openframe.services.configuration.condition.WebSphereContainerCondition;
import net.opentrends.openframe.services.configuration.config.ConfigurationServiceDefaultConfiguration;
import net.opentrends.openframe.services.configuration.util.Constants;
import net.opentrends.openframe.services.persistence.builders.SqlSessionFactoryBeanBuilder;
import net.opentrends.openframe.services.persistence.config.model.JdbcSettings;
import net.opentrends.openframe.services.persistence.exception.PersistenceServiceException;
import oracle.jdbc.pool.OracleDataSource;

@Configuration
@EnableTransactionManagement
@Import({ ConfigurationServiceDefaultConfiguration.class })
@EntornPropertySource(value = { "classpath:/applibgap/mybatis/config/persistence.properties" })
@Lazy(true)
public class PersistenceServiceGapConfiguration {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Value("${gap.persistence.dataSource.jndiName}")
	private String dataSourceName;

	@Value("${gap.persistence.myBatis.mapper}")
	private String myBatisMapper;

	@Value("${gap.persistence.myBatis.mapper.additionalMappingFilesFolderLocations}")
	private String[] myBatisMapperAdditionalMappingFilesFolderLocations;

	@Autowired(required = true)
	@Qualifier(ContainerInformationService.BEAN_NAME)
	private ContainerInformationService containerInformationService;

	@Bean(name = "transactionManager")
	@Conditional({ CommonContainerCondition.class })
	public DataSourceTransactionManager dataSourceTransactionManagerCommonContainer(DataSource dataSource) {
		logger.debug("Instantiation bean dataSourceTransactionManagerCommonContainer");
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean(name = "transactionManager")
	@Conditional({ WebSphereContainerCondition.class })
	public WebSphereUowTransactionManager webSphereUowTransactionManager() {
		logger.debug("Instantiation bean webSphereUowTransactionManager");
		return new WebSphereUowTransactionManager();
	}

	@Bean(name = "dataSourceGap")
	@ConditionalOnContext(property = net.opentrends.openframe.services.persistence.util.Constants.CONTEXT_CONFIGURATION_SERVICE_PROPERTY_NAME, value = net.opentrends.openframe.services.configuration.util.Constants.CONTEXT_CONFIGURATION_FWK_MOCK_MODE)
	public DataSource dataSourceMockImpl() throws PersistenceServiceException {
		logger.error(Constants.CONTEXT_CONFIGURATION_FWK_MOCK_IMPLEMENTATION_PENDING);
		throw new PersistenceServiceException(Constants.CONTEXT_CONFIGURATION_FWK_MOCK_IMPLEMENTATION_PENDING);
	}

	@Bean(name = "dataSourceGap")
	@ConditionalOnContext(property = net.opentrends.openframe.services.persistence.util.Constants.CONTEXT_CONFIGURATION_SERVICE_PROPERTY_NAME, value = ConditionalOnContext.NOT_EXISTS_OR_IS_EMPTY)
	public DataSource dataSource() throws PersistenceServiceException {
		if (containerInformationService.isRunningBatchProcess()) {
			return dataSourceOracleJdbcPoolImpl();
		} else {
			return dataSourceJndiImpl();
		}
	}

	private DataSource dataSourceJndiImpl() {
		logger.debug("Instantiation dataSource (JNDI version)");
		Assert.hasText(dataSourceName,
				"The 'persistence.dataSource.jndiName' placeholder must have a non empty value.");
		final JndiDataSourceLookup lookup = new JndiDataSourceLookup();
		lookup.setResourceRef(true);
		return lookup.getDataSource(dataSourceName);
	}

	private DataSource dataSourceOracleJdbcPoolImpl() throws PersistenceServiceException {
		logger.debug("Instantiation dataSource (Oracle JDBC Pool version)");
		JdbcSettings settings = jdbcSettings();
		Assert.notNull(settings,
				"A not null JdbcSettings is required for instantiating a dataSource (Oracle JDBC Pool version)");
		Assert.hasText(settings.getUrl(),
				"The URL is required for instantiating a dataSource (Oracle JDBC Pool version)");
		Assert.hasText(settings.getUsername(),
				"The username is required for instantiating a dataSource (Oracle JDBC Pool version)");
		Assert.hasText(settings.getPassword(),
				"The password is required for instantiating a dataSource (Oracle JDBC Pool version)");
		try {
			OracleDataSource dataSource = new OracleDataSource();
			dataSource.setURL(settings.getUrl());
			dataSource.setUser(settings.getUsername());
			dataSource.setPassword(settings.getPassword());
			return dataSource;
		} catch (SQLException e) {
			String error = "Exception instantiating dataSource (Oracle JDBC Pool version) with {url: '"
					+ settings.getUrl() + "', username: '" + settings.getUsername() + "', password: '*****'}";
			logger.error(error, e);
			throw new PersistenceServiceException(error, e);
		}
	}

	/**
	 * Specify the settings required to established a JDBC connection during the
	 * execution of Batch processes.
	 * 
	 * @return a {@link JdbcSettings} POJO.
	 */
	@Bean(name = "jdbcSettings")
	@ConditionalOnContext(property = net.opentrends.openframe.services.persistence.util.Constants.CONTEXT_CONFIGURATION_SERVICE_PROPERTY_NAME, value = ConditionalOnContext.NOT_EXISTS_OR_IS_EMPTY, otherConditions = {
			BatchProcessCondition.class }, otherConditionsMatching = ConditionsMatching.ALL)
	public JdbcSettings jdbcSettings() {
		return null;
	}

	@Bean(name = "myBatisTemplateGap")
	public MyBatisTemplateGap myBatisTemplateGap() throws PersistenceServiceException {
		logger.debug("Instantiation bean myBatisTemplateGap with a MyBatisTemplateGapImpl");
		try {
			return new MyBatisTemplateGapImpl(sqlSessionFactoryBean().getObject());
		} catch (Exception e) {
			logger.error("Exception instantiating bean myBatisTemplateGap with a MyBatisTemplateGapImpl", e);
			throw new PersistenceServiceException(e);
		}
	}

	protected SqlSessionFactoryBean sqlSessionFactoryBean() throws PersistenceServiceException {

		// Force commons logging to prevent MyBatis Spring from autoselectig
		// SLF4J in case its jar appears in the classloader
		org.apache.ibatis.logging.LogFactory.useCommonsLogging();

		Assert.hasText(myBatisMapper, "The 'persistence.myBatis.mapper' placeholder must have a non empty value.");
		SqlSessionFactoryBeanBuilder builder = new SqlSessionFactoryBeanBuilder(dataSource(), myBatisMapper);

		if (myBatisMapperAdditionalMappingFilesFolderLocations != null
				&& myBatisMapperAdditionalMappingFilesFolderLocations.length > 0) {
			for (String additionalMappingFilesFolderLocation : myBatisMapperAdditionalMappingFilesFolderLocations) {
				builder.addMappingFilesInFolder(additionalMappingFilesFolderLocation);
			}
		}

		List<Interceptor> pluginsList = myBatisPlugins();
		if (pluginsList != null) {
			Interceptor[] plugins = pluginsList.toArray(new Interceptor[pluginsList.size()]);
			builder.plugins(plugins);
		}

		Resource configLocation = myBatisConfigLocation();
		if (configLocation != null) {
			builder.configLocation(configLocation);
		}

		org.apache.ibatis.session.Configuration configuration = myBatisConfigurationGap();
		if (configuration != null) {
			builder.configuration(configuration);
		}

		return builder.build();
	}

	/**
	 * Gives inherited configuration classes the opportunity to add further MyBatis
	 * plugins.
	 * 
	 * @return
	 */
	protected List<Interceptor> myBatisPlugins() {
		return null;
	}

	/**
	 * Gives inherited configuration classes (and also those configuration classes
	 * that import this class instead of inheriting it) the opportunity to provide
	 * the location of a MyBatis {@code SqlSessionFactory} configuration file for
	 * configuring the MyBatis {@link SqlSession}.
	 * 
	 * @return any implementation of {@link org.springframework.core.io.Resource},
	 *         typically a {@link org.springframework.core.io.ClassPathResource}
	 *         initialized with "/app/mybatis/mybatis-configuration.xml".
	 */
	@Bean(name = "myBatisConfigLocationGap")
	public Resource myBatisConfigLocation() {
		return null;
	}

	/**
	 * As an alternative to the {@code myBatisConfigLocation()} method, gives
	 * inherited configuration classes (and also those configuration classes that
	 * import this class instead of inheriting it) the opportunity to provide a
	 * customized MyBatis Configuration for configuring the MyBatis
	 * {@link SqlSession}.
	 * 
	 * @return a customized MyBatis {@link org.apache.ibatis.session.Configuration}
	 *         object.
	 */
	@Bean(name = "myBatisConfigurationGap")
	public org.apache.ibatis.session.Configuration myBatisConfigurationGap() {
		return null;
	}
}
