package es.bcn.imi.framework.vigia.inventari.test.parent;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.opentrends.openframe.services.configuration.context.ContextPropertySourcesInitializer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {}, initializers = ContextPropertySourcesInitializer.class)
public abstract class ParentTest {
}
