package es.bcn.imi.framework.vigia.frontal.test.gap.business.inventary.impl;

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

import es.bcn.imi.framework.vigia.frontal.gap.inventary.services.impl.LogicalModelGapServiceImpl;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisTemplateGap;
import es.bcn.imi.framework.vigia.frontal.web.rest.config.RestServerConfig;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RestServerConfig.class })
@TestPropertySource(locations = {"classpath:app/config/application.properties"})
@WebAppConfiguration
public class LogicalModelGapServiceImplTest {
	
	@Mock
	private MyBatisTemplateGap myBatisTemplateGap;
	
	@InjectMocks
	private LogicalModelGapServiceImpl service;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void caseOk() throws ImiException {
		service.getLogicalModelsGap(new HashMap<String,Object>());
	}
	
	@Test
	public void caseLogicalModelsUbicationOk() throws ImiException {
		service.getLogicalModelsUbicationGap("");
	}

	@Test
	public void caseLogicalModelsPositionUbicationOk() throws ImiException {
		service.getLogicalModelsPositionUbicationGap("", 1,"");
	}
	
	@Test
	public void caseVacantLogicalModelOk() throws ImiException {
		service.getVacantLogicalModelsGap("", "");
	}
}
