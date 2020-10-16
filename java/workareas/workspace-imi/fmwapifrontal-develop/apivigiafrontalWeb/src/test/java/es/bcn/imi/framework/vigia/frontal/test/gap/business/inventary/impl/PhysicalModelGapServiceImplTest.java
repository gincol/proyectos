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

import es.bcn.imi.framework.vigia.frontal.gap.inventary.services.impl.PhysicalModelGapServiceImpl;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisTemplateGap;
import es.bcn.imi.framework.vigia.frontal.web.rest.config.RestServerConfig;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RestServerConfig.class })
@TestPropertySource(locations = {"classpath:app/config/application.properties"})
@WebAppConfiguration
public class PhysicalModelGapServiceImplTest {
	
	@Mock
	private MyBatisTemplateGap myBatisTemplateGap;
	
	@InjectMocks
	private PhysicalModelGapServiceImpl service;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void caseOk() throws ImiException {
		service.getPhysicalModelsGap("");
	}
	
	@Test
	public void casePhysicalModelsLogicalModelOk() throws ImiException {
		service.getPhysicalModelsLogicalModelGap("");
	}

	@Test
	public void casePhysicalModelsLogicalModelOk2() throws ImiException {
		service.getPhysicalModelsGap("", "", "");
	}
	
	@Test
	public void casePhysicalModelsGapParamaOk() throws ImiException {
		service.getPhysicalModelsGap(new HashMap<String,Object>());
	}

	
	
}
