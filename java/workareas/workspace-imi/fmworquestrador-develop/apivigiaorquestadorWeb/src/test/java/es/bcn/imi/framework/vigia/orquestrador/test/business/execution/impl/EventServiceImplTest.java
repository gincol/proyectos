package es.bcn.imi.framework.vigia.orquestrador.test.business.execution.impl;

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

import es.bcn.imi.framework.vigia.orquestrador.business.execution.impl.EventServiceImpl;
import es.bcn.imi.framework.vigia.orquestrador.web.rest.config.RestServerConfig;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.APSEventRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.AnnulmentEventRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.EventServiceRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.InformativeEventRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterEventRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.UnloadingEventRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.stub.APSEventRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.stub.AnnulmentEventRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.stub.EventServiceRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.stub.InformativeEventRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.stub.UnloadingEventRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.QueryParameterEventRDTOStub;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import es.bcn.vigia.fmw.libutils.services.Utils;
import es.bcn.vigia.fmw.libutils.services.rest.RestCall;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RestServerConfig.class })
@TestPropertySource(locations = {"classpath:app/config/application.properties"})
@WebAppConfiguration
public class EventServiceImplTest{
	
	@Value("${url.api.execution}")
	private String urlApiExecution;
	
	@Value("${url.path.event}")
	private String pathEvent;
	
	@Value("${url.path.event.getDetailed}")
	private String pathEventGetDetailed;
	
	@Value("${url.path.event.getApproved}")
	private String pathEventGetApproved;
	
	@Value("${url.path.event.getRejected}")
	private String pathEventGetRejected;
	
	@Value("${url.path.event.getPending}")
	private String pathEventGetPending;

	@Value("${url.path.event.service}")
	private String pathService;
	
	@Value("${url.path.event.informative}")
	private String pathInformative;
	
	@Value("${url.path.event.aps}")
	private String pathAps;
	
	@Value("${url.path.event.unloading}")
	private String pathUnloading;
	
	@Value("${url.path.event.annulment}")
	private String pathAnnulment;
	
	@Mock
	private RestCall restCall;
	
	@Mock
	private Utils utils;
	
	@InjectMocks
	private EventServiceImpl service;
	
	private ReturnRDTO returnRDTO;
	
	private EventServiceRDTO eventServiceRDTO;
	
	private InformativeEventRDTO informativeEventRDTO;
	
	private APSEventRDTO apsEventRDTO;
	
	private UnloadingEventRDTO unloadingEventRDTO;
	
	private AnnulmentEventRDTO annulmentEventRDTO;
	
	private QueryParameterEventRDTO queryParameterEventRDTO;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		returnRDTO 				= ReturnRDTOStub.getSuccessMessage();
		eventServiceRDTO 		= EventServiceRDTOStub.defaultOne();
		informativeEventRDTO 	= InformativeEventRDTOStub.defaultOne();
		apsEventRDTO 			= APSEventRDTOStub.defaultOne();
		unloadingEventRDTO 		= UnloadingEventRDTOStub.defaultOne();
		annulmentEventRDTO 		= AnnulmentEventRDTOStub.defaultOne();
		queryParameterEventRDTO = QueryParameterEventRDTOStub.defaultOne();
	}

	@Test
	public void caseOkInsertEventService() throws ImiException {
		
		ReflectionTestUtils.setField(service, "urlApiExecution", urlApiExecution);
		ReflectionTestUtils.setField(service, "pathEvent", pathEvent);
		ReflectionTestUtils.setField(service, "pathService", pathService);
		
		String url = urlApiExecution + pathEvent + pathService;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(url, eventServiceRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		service.insertService(eventServiceRDTO);
	}

	@Test(expected = ImiException.class)
	public void caseKoInsertEventService() throws ImiException {
		
		ReflectionTestUtils.setField(service, "urlApiExecution", urlApiExecution);
		ReflectionTestUtils.setField(service, "pathEvent", pathEvent);
		ReflectionTestUtils.setField(service, "pathService", pathService);
		
		String url = urlApiExecution + pathEvent + pathService;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.doThrow(ImiException.class).when(restCall).executePOST(url, eventServiceRDTO);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		service.insertService(eventServiceRDTO);
	}
	
	@Test
	public void caseOkInsertEventInformative() throws ImiException {
		
		ReflectionTestUtils.setField(service, "urlApiExecution", urlApiExecution);
		ReflectionTestUtils.setField(service, "pathEvent", pathEvent);
		ReflectionTestUtils.setField(service, "pathInformative", pathInformative);
		
		String url = urlApiExecution + pathEvent + pathInformative;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(url, informativeEventRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		service.insertInformative(informativeEventRDTO);
	}

	@Test(expected = ImiException.class)
	public void caseKoInsertEventInformative() throws ImiException {
		
		ReflectionTestUtils.setField(service, "urlApiExecution", urlApiExecution);
		ReflectionTestUtils.setField(service, "pathEvent", pathEvent);
		ReflectionTestUtils.setField(service, "pathInformative", pathInformative);
		
		String url = urlApiExecution + pathEvent + pathInformative;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.doThrow(ImiException.class).when(restCall).executePOST(url, informativeEventRDTO);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		service.insertInformative(informativeEventRDTO);
	}
	
	@Test
	public void caseOkInsertEventUnloading() throws ImiException {
		
		ReflectionTestUtils.setField(service, "urlApiExecution", urlApiExecution);
		ReflectionTestUtils.setField(service, "pathEvent", pathEvent);
		ReflectionTestUtils.setField(service, "pathUnloading", pathUnloading);
		
		String url = urlApiExecution + pathEvent + pathUnloading;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(url, unloadingEventRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		service.insertUnloading(unloadingEventRDTO);
	}

	@Test(expected = ImiException.class)
	public void caseKoInsertEventUnloading() throws ImiException {
		
		ReflectionTestUtils.setField(service, "urlApiExecution", urlApiExecution);
		ReflectionTestUtils.setField(service, "pathEvent", pathEvent);
		ReflectionTestUtils.setField(service, "pathUnloading", pathUnloading);
		
		String url = urlApiExecution + pathEvent + pathUnloading;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.doThrow(ImiException.class).when(restCall).executePOST(url, unloadingEventRDTO);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		service.insertUnloading(unloadingEventRDTO);
	}
	
	@Test
	public void caseOkInsertEventAPS() throws ImiException {
		
		ReflectionTestUtils.setField(service, "urlApiExecution", urlApiExecution);
		ReflectionTestUtils.setField(service, "pathEvent", pathEvent);
		ReflectionTestUtils.setField(service, "pathAps", pathAps);
		
		String url = urlApiExecution + pathEvent + pathAps;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(url, eventServiceRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		service.insertAps(apsEventRDTO);
	}

	@Test(expected = ImiException.class)
	public void caseKoInsertEventAPS() throws ImiException {
		
		ReflectionTestUtils.setField(service, "urlApiExecution", urlApiExecution);
		ReflectionTestUtils.setField(service, "pathEvent", pathEvent);
		ReflectionTestUtils.setField(service, "pathAps", pathAps);
		
		String url = urlApiExecution + pathEvent + pathAps;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.doThrow(ImiException.class).when(restCall).executePOST(url, apsEventRDTO);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		service.insertAps(apsEventRDTO);
	}
	
	@Test
	public void caseOkInsertEventAnnulment() throws ImiException {
		
		ReflectionTestUtils.setField(service, "urlApiExecution", urlApiExecution);
		ReflectionTestUtils.setField(service, "pathEvent", pathEvent);
		ReflectionTestUtils.setField(service, "pathAnnulment", pathAnnulment);
		
		String url = urlApiExecution + pathEvent + pathAnnulment;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(url, annulmentEventRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		service.insertAnnulment(annulmentEventRDTO);
	}

	@Test(expected = ImiException.class)
	public void caseKoInsertEventAnnulment() throws ImiException {
		
		ReflectionTestUtils.setField(service, "urlApiExecution", urlApiExecution);
		ReflectionTestUtils.setField(service, "pathEvent", pathEvent);
		ReflectionTestUtils.setField(service, "pathAnnulment", pathAnnulment);
		
		String url = urlApiExecution + pathEvent + pathAnnulment;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.doThrow(ImiException.class).when(restCall).executePOST(url, annulmentEventRDTO);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		service.insertAnnulment(annulmentEventRDTO);
	}
	
	@Test
	public void caseOkSelectDetailed() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiExecution", urlApiExecution);
		ReflectionTestUtils.setField(service, "pathEvent", pathEvent);
		ReflectionTestUtils.setField(service, "pathEventGetDetailed", pathEventGetDetailed);
		String url = urlApiExecution + pathEvent + pathEventGetDetailed;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", queryParameterEventRDTO.getCode());
		urlParams.put("codiContracta", queryParameterEventRDTO.getCodeContract());
		urlParams.put("codiUsuari", queryParameterEventRDTO.getCodeUser());
		urlParams.put("trasactionId", queryParameterEventRDTO.getTransactionId());
		Mockito.when(restCall.executeGET(url, urlParams, queryParameterEventRDTO)).thenReturn(ss);
		service.selectDetailed(queryParameterEventRDTO);
	}
	
	@Test
	public void caseOkSelectApproved() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiExecution", urlApiExecution);
		ReflectionTestUtils.setField(service, "pathEvent", pathEvent);
		ReflectionTestUtils.setField(service, "pathEventGetApproved", pathEventGetApproved);
		String url = urlApiExecution + pathEvent + pathEventGetApproved;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", queryParameterEventRDTO.getCodeContract());
		urlParams.put("dataExecucio", queryParameterEventRDTO.getDateExecution());
		urlParams.put("codiUsuari", queryParameterEventRDTO.getCodeUser());
		urlParams.put("trasactionId", queryParameterEventRDTO.getTransactionId());
		Mockito.when(restCall.executeGET(url, urlParams, queryParameterEventRDTO)).thenReturn(ss);
		service.selectApproved(queryParameterEventRDTO);
	}
	
	@Test
	public void caseOkSelectRejected() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiExecution", urlApiExecution);
		ReflectionTestUtils.setField(service, "pathEvent", pathEvent);
		ReflectionTestUtils.setField(service, "pathEventGetRejected", pathEventGetRejected);
		String url = urlApiExecution + pathEvent + pathEventGetRejected;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", queryParameterEventRDTO.getCodeContract());
		urlParams.put("dataExecucio", queryParameterEventRDTO.getDateExecution());
		urlParams.put("codiUsuari", queryParameterEventRDTO.getCodeUser());
		urlParams.put("trasactionId", queryParameterEventRDTO.getTransactionId());
		Mockito.when(restCall.executeGET(url, urlParams, queryParameterEventRDTO)).thenReturn(ss);
		service.selectRejected(queryParameterEventRDTO);
	}
	
	@Test
	public void caseOkSelectPending() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiExecution", urlApiExecution);
		ReflectionTestUtils.setField(service, "pathEvent", pathEvent);
		ReflectionTestUtils.setField(service, "pathEventGetPending", pathEventGetPending);
		String url = urlApiExecution + pathEvent + pathEventGetPending;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", queryParameterEventRDTO.getCodeContract());
		urlParams.put("dataInici", queryParameterEventRDTO.getDateStart());
		urlParams.put("dataFi", queryParameterEventRDTO.getDateEnd());
		urlParams.put("codiUsuari", queryParameterEventRDTO.getCodeUser());
		urlParams.put("trasactionId", queryParameterEventRDTO.getTransactionId());
		Mockito.when(restCall.executeGET(url, urlParams, queryParameterEventRDTO)).thenReturn(ss);
		service.selectPending(queryParameterEventRDTO);
	}
}
