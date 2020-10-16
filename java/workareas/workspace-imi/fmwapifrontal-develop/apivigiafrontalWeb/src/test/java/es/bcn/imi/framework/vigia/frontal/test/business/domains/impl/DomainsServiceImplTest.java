package es.bcn.imi.framework.vigia.frontal.test.business.domains.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import es.bcn.imi.framework.vigia.frontal.business.domains.impl.DomainsServiceImpl;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisTemplateGap;
import es.bcn.imi.framework.vigia.frontal.web.rest.config.RestServerConfig;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterDomainsRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnDomainValuesRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnDomainsRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnDomainValuesRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnDomainsRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.QueryParameterDomainsRDTOStub;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisTemplate;
@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RestServerConfig.class })
@TestPropertySource(locations = {"classpath:app/config/application.properties"})
@WebAppConfiguration
public class DomainsServiceImplTest {
	
	@Mock
	private MyBatisTemplate myBatisTemplate;
	
	@Mock
	private MyBatisTemplateGap myBatisTemplateGap;
	
	@InjectMocks
	private DomainsServiceImpl service;

	private ReturnDomainsRDTO returnDomainsRDTO;
	
	
	private ReturnDomainValuesRDTO returnDomainValuesRDTO;
	
	private ReturnRDTO returnRDTOKO;

	private QueryParameterDomainsRDTO queryParameterDomainsRDTO;	

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		returnDomainsRDTO = ReturnDomainsRDTOStub.getSuccessMessage();
		returnDomainValuesRDTO = ReturnDomainValuesRDTOStub.getSuccessMessage();
		queryParameterDomainsRDTO = QueryParameterDomainsRDTOStub.defaultOne();
	}

	@Test
	public void caseOkSelectDomains() throws Exception {
		Mockito.when(myBatisTemplateGap.execute(Mockito.any(), Mockito.any())).thenReturn(returnDomainsRDTO.getNames());
		Mockito.when(myBatisTemplate.execute(Mockito.any(), Mockito.any())).thenReturn(returnDomainsRDTO.getNames());
		service.selectDomains();
	}
	
	@Test(expected = Exception.class)
	public void caseKoSelectDomains() throws Exception {
		service.selectDomains();
	}

	@Test
	public void caseOkGetDomainsGap() throws Exception {
		service.getDomainsGap();
	}
	
	@Test(expected = Exception.class)
	public void caseKoGetDomainsGap() throws Exception {
		Mockito.doThrow(ImiException.class).when(myBatisTemplateGap.execute(Mockito.any(), Mockito.any()));
		service.getDomainsGap();
	}

	@Test
	public void caseOkGetDomains() throws Exception {
		service.getDomains();
	}
	
	@Test(expected = Exception.class)
	public void caseKoGetDomains() throws Exception {
		Mockito.doThrow(ImiException.class).when(myBatisTemplate.execute(Mockito.any(), Mockito.any()));
		service.getDomains();
	}

	@Test
	public void caseOkSelectDomainValues() throws Exception {
		service.selectDomainValues(queryParameterDomainsRDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoSelectDomainValues() throws Exception {
		service.selectDomainValues(null);
	}

	@Test
	public void caseOkGetDomainValuesGap() throws Exception {
		service.getDomainValuesGap(queryParameterDomainsRDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoGetDomainValuesGap() throws Exception {
		service.getDomainValuesGap(null);
	}

	@Test
	public void caseOkGetDomainValues() throws Exception {
		service.getDomainValues(queryParameterDomainsRDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoGetDomainValues() throws Exception {
		service.getDomainValues(null);
	}
}