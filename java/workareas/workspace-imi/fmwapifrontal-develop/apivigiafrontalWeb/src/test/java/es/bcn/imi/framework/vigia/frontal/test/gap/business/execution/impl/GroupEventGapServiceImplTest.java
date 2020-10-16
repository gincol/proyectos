package es.bcn.imi.framework.vigia.frontal.test.gap.business.execution.impl;

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

import es.bcn.imi.framework.vigia.frontal.gap.execution.services.impl.GroupEventGapServiceImpl;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisTemplateGap;
import es.bcn.imi.framework.vigia.frontal.web.rest.config.RestServerConfig;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RestServerConfig.class })
@TestPropertySource(locations = {"classpath:app/config/application.properties"})
@WebAppConfiguration
public class GroupEventGapServiceImplTest {
	
	
	@Mock
	private MyBatisTemplateGap myBatisTemplateGap;
	
	
	@InjectMocks
	private GroupEventGapServiceImpl service;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void caseOk() throws ImiException {
		service.getGroupEventsGap(new HashMap<String,Object>());
	}

}