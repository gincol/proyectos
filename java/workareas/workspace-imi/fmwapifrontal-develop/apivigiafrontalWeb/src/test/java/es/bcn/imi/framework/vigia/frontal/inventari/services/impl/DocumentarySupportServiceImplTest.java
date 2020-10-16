package es.bcn.imi.framework.vigia.frontal.inventari.services.impl;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import es.bcn.imi.framework.vigia.frontal.inventary.services.impl.DocumentarySupportServiceImpl;
import es.bcn.imi.framework.vigia.frontal.web.rest.config.RestServerConfig;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RestServerConfig.class })
@TestPropertySource(locations = {"classpath:app/config/application.properties"})
@WebAppConfiguration
public class DocumentarySupportServiceImplTest {
	
	@Mock
	private MyBatisTemplate myBatisTemplate;
	
	@InjectMocks
	private DocumentarySupportServiceImpl service;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void caseOkCompatibleTypesMCLByTypeUbication() throws  ImiException {
		service.getDocumentarySupports(new HashMap<String,Object>());
	}
	
	
}