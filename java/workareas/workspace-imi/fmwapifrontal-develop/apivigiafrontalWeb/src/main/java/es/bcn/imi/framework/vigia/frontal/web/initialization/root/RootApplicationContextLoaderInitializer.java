package es.bcn.imi.framework.vigia.frontal.web.initialization.root;

import es.bcn.imi.framework.vigia.frontal.business.config.BusinessConfig;
import es.bcn.imi.framework.vigia.frontal.business.config.ContainerConditionConfig;
import es.bcn.imi.framework.vigia.frontal.business.config.PersistenceServiceFmwConfig;
import es.bcn.imi.framework.vigia.frontal.business.config.PersistenceServiceGapConfig;
import net.opentrends.openframe.services.web.context.AbstractAnnotationConfigRootApplicationContextLoaderInitializer;

public class RootApplicationContextLoaderInitializer
		extends AbstractAnnotationConfigRootApplicationContextLoaderInitializer {

	@Override
	protected Class<?>[] getRootApplicationContextSecurityConfigClasses() {
		return new Class[0];
	}

	@Override
	protected Class<?>[] getRootApplicationContextApplicationConfigClasses() {
		return new Class[] { BusinessConfig.class,
							 ContainerConditionConfig.class,
							 PersistenceServiceFmwConfig.class,
							 PersistenceServiceGapConfig.class
							};
	}

}