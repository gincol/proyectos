package es.bcn.imi.framework.vigia.orquestrador.web.initialization.root;

import es.bcn.imi.framework.vigia.orquestrador.business.config.BusinessConfig;
import es.bcn.imi.framework.vigia.orquestrador.business.config.ContainerConditionConfig;
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
							 ContainerConditionConfig.class};
	}

}