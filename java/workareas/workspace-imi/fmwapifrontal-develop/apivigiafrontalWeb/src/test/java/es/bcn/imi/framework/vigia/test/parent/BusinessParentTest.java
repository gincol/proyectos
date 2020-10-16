package es.bcn.imi.framework.vigia.test.parent;

import org.springframework.test.context.ContextConfiguration;

import es.bcn.imi.framework.vigia.test.config.BusinessTestsConfig;

@ContextConfiguration(classes = { BusinessTestsConfig.class })
public abstract class BusinessParentTest extends ParentTest {
}
