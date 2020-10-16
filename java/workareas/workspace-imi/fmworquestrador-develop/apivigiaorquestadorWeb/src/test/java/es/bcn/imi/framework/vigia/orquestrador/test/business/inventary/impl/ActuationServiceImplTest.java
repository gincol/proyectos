package es.bcn.imi.framework.vigia.orquestrador.test.business.inventary.impl;

import java.util.HashMap;
import java.util.Map;

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

import es.bcn.imi.framework.vigia.orquestrador.business.inventary.impl.ActuationServiceImpl;
import es.bcn.imi.framework.vigia.orquestrador.web.rest.config.RestServerConfig;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.ActuationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.ActuationRDTOStub;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import es.bcn.vigia.fmw.libutils.services.Utils;
import es.bcn.vigia.fmw.libutils.services.rest.RestCall;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RestServerConfig.class })
@TestPropertySource(locations = {"classpath:app/config/application.properties"})
@WebAppConfiguration
public class ActuationServiceImplTest{
	
	@Value("${url.api.inventary}")
	private String urlApiInventary;

	@Value("${url.path.actuation}")
	private String pathActuation;
	
	@Value("${url.path.actuation.delete}")
	private String pathActuationDelete;
	
	@Mock
	private RestCall restCall;
	
	@Mock
	private Utils utils;
	
	@InjectMocks
	private ActuationServiceImpl service;
	
	private ReturnRDTO returnRDTO;
	
	private ActuationRDTO actuationRDTO;
	
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		returnRDTO = ReturnRDTOStub.getSuccessMessage();
		actuationRDTO = ActuationRDTOStub.defaultOne();
		
	}

	@Test
	public void caseOkInsert() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathActuation", pathActuation);
		String url = urlApiInventary + pathActuation;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(url, actuationRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		service.insert(actuationRDTO);
	}

	@Test(expected = ImiException.class)
	public void caseKoInsert() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathActuation", pathActuation);
		String url = urlApiInventary + pathActuation;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.doThrow(ImiException.class).when(restCall).executePOST(url, actuationRDTO);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		service.insert(actuationRDTO);
	}
	
	

	@Test
	public void caseOkUpdate() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathActuation", pathActuation);
		ReflectionTestUtils.setField(service, "pathActuationDelete", pathActuationDelete);
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		String url = urlApiInventary + pathActuation + pathActuationDelete;
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", actuationRDTO.getCode());
		urlParams.put("codiEntitat", actuationRDTO.getCodeEntity());

		
		
		Mockito.when(restCall.executePUT(url, urlParams, actuationRDTO)).thenReturn(ss);
		service.delete(actuationRDTO);
	}

	@Test (expected = ImiException.class)
	public void caseKoUpdate() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiInventary", urlApiInventary);
		ReflectionTestUtils.setField(service, "pathActuation", pathActuation);
		ReflectionTestUtils.setField(service, "pathActuationDelete", pathActuationDelete);
	
		
		String url = urlApiInventary + pathActuation + pathActuationDelete;
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", actuationRDTO.getCode());
		urlParams.put("codiEntitat", actuationRDTO.getCodeEntity());

		
		Mockito.doThrow(ImiException.class).when(restCall).executePUT(url, urlParams, actuationRDTO);
		service.delete(actuationRDTO);
	}

	}
