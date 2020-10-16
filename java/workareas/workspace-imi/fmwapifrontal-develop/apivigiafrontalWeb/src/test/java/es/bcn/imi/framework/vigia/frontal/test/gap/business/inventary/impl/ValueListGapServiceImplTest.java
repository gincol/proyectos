package es.bcn.imi.framework.vigia.frontal.test.gap.business.inventary.impl;

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

import es.bcn.imi.framework.vigia.frontal.gap.inventary.services.impl.ValueListGapServiceImpl;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisTemplateGap;
import es.bcn.imi.framework.vigia.frontal.web.rest.config.RestServerConfig;
import es.bcn.vigia.fmw.libcommons.valueobject.Entity;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RestServerConfig.class })
@TestPropertySource(locations = {"classpath:app/config/application.properties"})
@WebAppConfiguration
public class ValueListGapServiceImplTest {
	
	@Mock
	private MyBatisTemplateGap myBatisTemplateGap;
	
	@InjectMocks
	private ValueListGapServiceImpl service;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void caseOk() throws IllegalArgumentException, ImiException {
		service.getValueListGap(new Entity(""));
	}
	
	@Test
	public void caseOk2() throws IllegalArgumentException, ImiException {
		service.getValueListGap(new Entity(""));
	}
}
