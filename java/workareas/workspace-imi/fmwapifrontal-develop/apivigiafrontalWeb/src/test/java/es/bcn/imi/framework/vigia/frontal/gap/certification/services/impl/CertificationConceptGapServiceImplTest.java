package es.bcn.imi.framework.vigia.frontal.gap.certification.services.impl;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;

import es.bcn.imi.framework.vigia.frontal.web.rest.config.RestServerConfig;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnRDTOStub;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import es.bcn.vigia.fmw.libutils.services.Utils;
import es.bcn.vigia.fmw.libutils.services.rest.RestCall;

@SuppressWarnings({ "unused", "unchecked" })
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RestServerConfig.class })
@TestPropertySource(locations = {"classpath:app/config/application.properties"})
@WebAppConfiguration
public class CertificationConceptGapServiceImplTest {
	
	@Value("${url.api.certification}")
	private String urlCertification;

	@Value("${url.path.certification.gap}")
	private String pathCertification;

	@Value("${url.path.certification.getMasters}")
	private String pathCertificationGetMasters;

	@Value("${load.properties.client.id.key.ibm}")
	private String clientIdIbmKey;

	@Value("${load.properties.client.id.value.ibm}")
	private String clientIdIbmValue;

	@Mock
	private RestCall restCall;
	
	@Mock
	private Utils utils;
	
	
	
	@InjectMocks
	private CertificationConceptGapServiceImpl service;
	
		
	
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	
	}  
	
	
	
	@Test
	public void caseOkGetCodesSubtypeConceptInstallationGap() throws ImiException {
		ReflectionTestUtils.setField(service, "urlCertification", urlCertification);
		ReflectionTestUtils.setField(service, "pathCertification", pathCertification);
		ReflectionTestUtils.setField(service, "pathCertificationGetMasters", pathCertificationGetMasters);
		ReflectionTestUtils.setField(service, "clientIdIbmKey", clientIdIbmKey);
		ReflectionTestUtils.setField(service, "clientIdIbmValue", clientIdIbmValue);
		
		
		String url = urlCertification + pathCertification + pathCertificationGetMasters;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		

		Mockito.when(restCall.executeGETClientId(Mockito.anyString(), Mockito.anyMap(), Mockito.anyList(),Mockito.anyMap())).thenReturn(ss);
		service.getCodesSubtypeConceptInstallationGap();
	}
	

	@Test
	public void caseOkGetCodesConceptCertificationGap() throws ImiException {
		ReflectionTestUtils.setField(service, "urlCertification", urlCertification);
		ReflectionTestUtils.setField(service, "pathCertification", pathCertification);
		ReflectionTestUtils.setField(service, "pathCertificationGetMasters", pathCertificationGetMasters);
		ReflectionTestUtils.setField(service, "clientIdIbmKey", clientIdIbmKey);
		ReflectionTestUtils.setField(service, "clientIdIbmValue", clientIdIbmValue);
		
		String url = urlCertification + pathCertification + pathCertificationGetMasters;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		

		Mockito.when(restCall.executeGETClientId(Mockito.anyString(), Mockito.anyMap(), Mockito.anyList(),Mockito.anyMap())).thenReturn(ss);
		service.getCodesConceptCertificationGap();
	}

	@Test
	public void caseOkGetCodesTypeIvaGap() throws ImiException {
		ReflectionTestUtils.setField(service, "urlCertification", urlCertification);
		ReflectionTestUtils.setField(service, "pathCertification", pathCertification);
		ReflectionTestUtils.setField(service, "pathCertificationGetMasters", pathCertificationGetMasters);
		ReflectionTestUtils.setField(service, "clientIdIbmKey", clientIdIbmKey);
		ReflectionTestUtils.setField(service, "clientIdIbmValue", clientIdIbmValue);
		
		String url = urlCertification + pathCertification + pathCertificationGetMasters;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		

		Mockito.when(restCall.executeGETClientId(Mockito.anyString(), Mockito.anyMap(), Mockito.anyList(),Mockito.anyMap())).thenReturn(ss);
		service.getCodesTypeIvaGap();
	}

	
	@Test
	public void caseOkgetCodesTypeExtraordinaryConceptGap() throws ImiException {
		ReflectionTestUtils.setField(service, "urlCertification", urlCertification);
		ReflectionTestUtils.setField(service, "pathCertification", pathCertification);
		ReflectionTestUtils.setField(service, "pathCertificationGetMasters", pathCertificationGetMasters);
		ReflectionTestUtils.setField(service, "clientIdIbmKey", clientIdIbmKey);
		ReflectionTestUtils.setField(service, "clientIdIbmValue", clientIdIbmValue);
		
		String url = urlCertification + pathCertification + pathCertificationGetMasters;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		

		Mockito.when(restCall.executeGETClientId(Mockito.anyString(), Mockito.anyMap(), Mockito.anyList(),Mockito.anyMap())).thenReturn(ss);
		service.getCodesTypeExtraordinaryConceptGap();
	}

}
