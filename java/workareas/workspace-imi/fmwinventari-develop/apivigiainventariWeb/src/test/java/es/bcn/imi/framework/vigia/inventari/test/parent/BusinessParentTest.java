package es.bcn.imi.framework.vigia.inventari.test.parent;

import org.springframework.test.context.ContextConfiguration;

import es.bcn.imi.framework.vigia.inventari.test.config.BusinessTestsConfig;

@ContextConfiguration(classes = { BusinessTestsConfig.class })
public abstract class BusinessParentTest extends ParentTest {
}
