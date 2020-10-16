package es.bcn.imi.framework.vigia.inventari.web.initialization.root;

import es.bcn.imi.framework.vigia.inventari.business.config.BusinessConfig;
import es.bcn.imi.framework.vigia.inventari.business.config.ContainerConditionConfig;
import es.bcn.imi.framework.vigia.inventari.business.config.PersistenceServiceFmwConfig;
import es.bcn.imi.framework.vigia.inventari.business.config.PersistenceServiceGapConfig;
import net.opentrends.openframe.services.web.context.AbstractAnnotationConfigRootApplicationContextLoaderInitializer;

public class RootApplicationContextLoaderInitializer
		extends AbstractAnnotationConfigRootApplicationContextLoaderInitializer {

	@Override
	protected Class<?>[] getRootApplicationContextSecurityConfigClasses() {
		return new Class[0];
	}

	@Override
	protected Class<?>[] getRootApplicationContextApplicationConfigClasses() {
		return new Class[] { BusinessConfig.class, PersistenceServiceFmwConfig.class, PersistenceServiceGapConfig.class,
				ContainerConditionConfig.class };
	}

}