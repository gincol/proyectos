package es.bcn.imi.framework.vigia.orquestrador.test.business.itinerary.impl;

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

import es.bcn.imi.framework.vigia.orquestrador.business.itinerary.impl.PlanningServiceImpl;
import es.bcn.imi.framework.vigia.orquestrador.web.rest.config.RestServerConfig;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.PlanningRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterPlanningRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.PlanningRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.QueryParameterPlanningRDTOStub;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import es.bcn.vigia.fmw.libutils.services.Utils;
import es.bcn.vigia.fmw.libutils.services.rest.RestCall;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RestServerConfig.class })
@TestPropertySource(locations = {"classpath:app/config/application.properties"})
@WebAppConfiguration
public class PlanningServiceImplTest{
	
	@Value("${url.api.itinerary}")
	private String urlApiItinerary;
	
	@Value("${url.path.planning}")
	private String pathPlanning;
	
	@Value("${url.path.planning.getMassive}")
	private String pathPlanningGetMassive;
	
	@Value("${url.path.planning.getDetailed}")
	private String pathPlanningGetDetailed;
	
	@Mock
	private RestCall restCall;
	
	@Mock
	private Utils utils;
	
	@InjectMocks
	private PlanningServiceImpl service;
	
	private ReturnRDTO returnRDTO;
	
	private PlanningRDTO planningRDTO;
	
	private QueryParameterPlanningRDTO queryParameterPlanningRDTO;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		returnRDTO 					= ReturnRDTOStub.getSuccessMessage();
		planningRDTO				= PlanningRDTOStub.defaultOne(); 
		queryParameterPlanningRDTO 	= QueryParameterPlanningRDTOStub.defaultOne();
	}
	
	@Test
	public void caseOkInsertPlanning() throws ImiException {
		
		ReflectionTestUtils.setField(service, "urlApiItinerary", urlApiItinerary);
		ReflectionTestUtils.setField(service, "pathPlanning", pathPlanning);
		
		String url = urlApiItinerary + pathPlanning;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(url, planningRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		service.insert(planningRDTO);
	}

	@Test(expected = ImiException.class)
	public void caseKoInsertPlanning() throws ImiException {
		
		ReflectionTestUtils.setField(service, "urlApiItinerary", urlApiItinerary);
		ReflectionTestUtils.setField(service, "pathPlanning", pathPlanning);
		
		String url = urlApiItinerary + pathPlanning;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.doThrow(ImiException.class).when(restCall).executePOST(url, planningRDTO);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		service.insert(planningRDTO);
	}
	
	@Test
	public void caseOkSelectMassive() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiItinerary", urlApiItinerary);
		ReflectionTestUtils.setField(service, "pathPlanning", pathPlanning);
		ReflectionTestUtils.setField(service, "pathPlanningGetMassive", pathPlanningGetMassive);
		String url = urlApiItinerary + pathPlanning + pathPlanningGetMassive;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", queryParameterPlanningRDTO.getCode());
		urlParams.put("codiContracta", queryParameterPlanningRDTO.getCodeContract());
		urlParams.put("codiUsuari", queryParameterPlanningRDTO.getCodeUser());
		urlParams.put("trasactionId", queryParameterPlanningRDTO.getTransactionId());
		Mockito.when(restCall.executeGET(url, urlParams, queryParameterPlanningRDTO)).thenReturn(ss);
		service.selectMassive(queryParameterPlanningRDTO);
	}
	
	@Test
	public void caseOkSelectDetailed() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiItinerary", urlApiItinerary);
		ReflectionTestUtils.setField(service, "pathPlanning", pathPlanning);
		ReflectionTestUtils.setField(service, "pathPlanningGetDetailed", pathPlanningGetDetailed);
		String url = urlApiItinerary + pathPlanning + pathPlanningGetDetailed;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", queryParameterPlanningRDTO.getCode());
		urlParams.put("codiContracta", queryParameterPlanningRDTO.getCodeContract());
		urlParams.put("codiUsuari", queryParameterPlanningRDTO.getCodeUser());
		urlParams.put("trasactionId", queryParameterPlanningRDTO.getTransactionId());
		Mockito.when(restCall.executeGET(url, urlParams, queryParameterPlanningRDTO)).thenReturn(ss);
		service.selectDetailed(queryParameterPlanningRDTO);
	}
}
