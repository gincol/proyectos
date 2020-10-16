package es.bcn.imi.framework.vigia.orquestrador.test.parent;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import es.bcn.imi.framework.vigia.orquestrador.test.config.RestServerTestsConfig;
import net.opentrends.openframe.services.web.test.servlet.setup.ServletBaseContextReplacer;

@ContextConfiguration(classes = { RestServerTestsConfig.class })
@WebAppConfiguration
public abstract class RestServerParentTest extends ParentTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders
				.webAppContextSetup(this.wac)
				.apply(new ServletBaseContextReplacer("/services/vigia", ""))
				.build();
	}

	protected MockMvc getMockMvc() {
		return mockMvc;
	}

}
