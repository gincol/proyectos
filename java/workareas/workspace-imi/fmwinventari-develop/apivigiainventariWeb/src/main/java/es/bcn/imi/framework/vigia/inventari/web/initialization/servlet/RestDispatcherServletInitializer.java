package es.bcn.imi.framework.vigia.inventari.web.initialization.servlet;

import es.bcn.imi.framework.vigia.inventari.web.rest.config.RestServerConfig;
import net.opentrends.openframe.services.web.servlet.support.AbstractAnnotationConfigRootApplicationContextlessDispatcherServletInitializer;

public class RestDispatcherServletInitializer
		extends AbstractAnnotationConfigRootApplicationContextlessDispatcherServletInitializer {

	private static final String SERVLET_NAME = "restDispatcher";

	@Override
	protected Class<?>[] getServletApplicationContextSecurityConfigClasses() {
		return new Class[0];
	}

	@Override
	protected Class<?>[] getServletApplicationContextApplicationConfigClasses() {
		return new Class[] { RestServerConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/fmw/inventari/*" };
	}

	@Override
	protected String getServletName() {
		return SERVLET_NAME;
	}

}