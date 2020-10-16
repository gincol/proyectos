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

import es.bcn.imi.framework.vigia.orquestrador.business.execution.impl.TimeClockServiceImpl;
import es.bcn.imi.framework.vigia.orquestrador.web.rest.config.RestServerConfig;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterTimeRegisterRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.TimeClockInstantRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.TimeClockSummaryRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.stub.TimeClockInstantRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.stub.TimeClockSummaryRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.QueryParameterTimeRegisterRDTOStub;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import es.bcn.vigia.fmw.libutils.services.Utils;
import es.bcn.vigia.fmw.libutils.services.rest.RestCall;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RestServerConfig.class })
@TestPropertySource(locations = {"classpath:app/config/application.properties"})
@WebAppConfiguration
public class TimeClockServiceImplTest{
	
	@Value("${url.api.execution}")
	private String urlApiExecution;
	
	@Value("${url.path.time.clock}")
	private String pathTimeClock;

	@Value("${url.path.time.clock.instant}")
	private String pathTimeClockInstant;

	@Value("${url.path.time.clock.instantGet}")
	private String pathTimeClockInstantGet;
	
	@Value("${url.path.time.clock.summary}")
	private String pathTimeClockSummary;
	
	@Value("${url.path.time.clock.summaryGet}")
	private String pathTimeClockSummaryGet;
	
	@Mock
	private RestCall restCall;
	
	@Mock
	private Utils utils;
	
	@InjectMocks
	private TimeClockServiceImpl service;
	
	private ReturnRDTO returnRDTO;
	
	private TimeClockInstantRDTO timeClockInstantRDTO;
	
	private TimeClockSummaryRDTO timeClockSummaryRDTO;
	
	private QueryParameterTimeRegisterRDTO queryParameterRDTO;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		returnRDTO 				= ReturnRDTOStub.getSuccessMessage();
		timeClockInstantRDTO	= TimeClockInstantRDTOStub.defaultOne();
		timeClockSummaryRDTO 	= TimeClockSummaryRDTOStub.defaultOne();
		queryParameterRDTO		= QueryParameterTimeRegisterRDTOStub.defaultOne();
	}

	@Test
	public void caseOkInsertTimeClockInstant() throws ImiException {
		
		ReflectionTestUtils.setField(service, "urlApiExecution", urlApiExecution);
		ReflectionTestUtils.setField(service, "pathTimeClock", pathTimeClock);
		ReflectionTestUtils.setField(service, "pathTimeClockInstant", pathTimeClockInstant);
		
		String url = urlApiExecution + pathTimeClock + pathTimeClockInstant;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(url, timeClockInstantRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		service.insertInstant(timeClockInstantRDTO);
	}

	@Test(expected = ImiException.class)
	public void caseKoInsertTimeClockInstant() throws ImiException {
		
		ReflectionTestUtils.setField(service, "urlApiExecution", urlApiExecution);
		ReflectionTestUtils.setField(service, "pathTimeClock", pathTimeClock);
		ReflectionTestUtils.setField(service, "pathTimeClockInstant", pathTimeClockInstant);
		
		String url = urlApiExecution + pathTimeClock + pathTimeClockInstant;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.doThrow(ImiException.class).when(restCall).executePOST(url, timeClockInstantRDTO);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		service.insertInstant(timeClockInstantRDTO);
	}
	
	@Test
	public void caseOkInsertTimeClockSummary() throws ImiException {
		
		ReflectionTestUtils.setField(service, "urlApiExecution", urlApiExecution);
		ReflectionTestUtils.setField(service, "pathTimeClock", pathTimeClock);
		ReflectionTestUtils.setField(service, "pathTimeClockSummary", pathTimeClockSummary);
		
		String url = urlApiExecution + pathTimeClock + pathTimeClockSummary;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(url, timeClockSummaryRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		service.insertSummary(timeClockSummaryRDTO);
	}

	@Test(expected = ImiException.class)
	public void caseKoInsertTimeClockSummary() throws ImiException {
		
		ReflectionTestUtils.setField(service, "urlApiExecution", urlApiExecution);
		ReflectionTestUtils.setField(service, "pathTimeClock", pathTimeClock);
		ReflectionTestUtils.setField(service, "pathTimeClockSummary", pathTimeClockSummary);
		
		String url = urlApiExecution + pathTimeClock + pathTimeClockSummary;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.doThrow(ImiException.class).when(restCall).executePOST(url, timeClockSummaryRDTO);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		service.insertSummary(timeClockSummaryRDTO);
	}
	
	@Test
	public void caseOkSelectTimeRegisterInstant() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiExecution", urlApiExecution);
		ReflectionTestUtils.setField(service, "pathTimeClock", pathTimeClock);
		ReflectionTestUtils.setField(service, "pathTimeClockInstant", pathTimeClockInstant);
		ReflectionTestUtils.setField(service, "pathTimeClockInstantGet", pathTimeClockInstantGet);
		
		String url = urlApiExecution + pathTimeClock + pathTimeClockInstant + pathTimeClockInstantGet;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", queryParameterRDTO.getCodeContract());
		urlParams.put("data", queryParameterRDTO.getDate());
		urlParams.put("codiRellotge", queryParameterRDTO.getCodeWatch());
		urlParams.put("codiTreballador", queryParameterRDTO.getCodeEmployee());
		urlParams.put("trasactionId", queryParameterRDTO.getTransactionId());
		Mockito.when(restCall.executeGET(url, urlParams, queryParameterRDTO)).thenReturn(ss);
		service.selectTimeRegisterInstant(queryParameterRDTO);
	}
	
	@Test
	public void caseOkSelectTimeRegisterSummary() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiExecution", urlApiExecution);
		ReflectionTestUtils.setField(service, "pathTimeClock", pathTimeClock);
		ReflectionTestUtils.setField(service, "pathTimeClockSummary", pathTimeClockSummary);
		ReflectionTestUtils.setField(service, "pathTimeClockSummaryGet", pathTimeClockSummaryGet);
		
		String url = urlApiExecution + pathTimeClock + pathTimeClockSummary + pathTimeClockSummaryGet;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", queryParameterRDTO.getCodeContract());
		urlParams.put("data", queryParameterRDTO.getDate());
		urlParams.put("codiInstallacio", queryParameterRDTO.getCodeInstallation());
		urlParams.put("trasactionId", queryParameterRDTO.getTransactionId());
		Mockito.when(restCall.executeGET(url, urlParams, queryParameterRDTO)).thenReturn(ss);
		service.selectTimeRegisterSummary(queryParameterRDTO);
	}
	
	
}
