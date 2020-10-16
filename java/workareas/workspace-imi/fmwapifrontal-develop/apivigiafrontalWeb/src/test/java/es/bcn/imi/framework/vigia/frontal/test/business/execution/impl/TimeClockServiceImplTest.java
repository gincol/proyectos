package es.bcn.imi.framework.vigia.frontal.test.business.execution.impl;

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

import es.bcn.imi.framework.vigia.frontal.business.execution.impl.TimeClockServiceImpl;
import es.bcn.imi.framework.vigia.frontal.validator.ValidatorTimeClockSummary;
import es.bcn.imi.framework.vigia.frontal.web.rest.config.RestServerConfig;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterTimeRegisterRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.TimeClockInstantRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.TimeClockSummaryRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.commons.BaseTransactionIdRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnTimeRegisterInstantRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnTimeRegisterSummaryRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.stub.TimeClockInstantRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.stub.TimeClockSummaryRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.QueryParameterTimeRegisterRDTOStub;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import es.bcn.vigia.fmw.libutils.services.Utils;
import es.bcn.vigia.fmw.libutils.services.rest.RestCall;
import es.bcn.vigia.fmw.libutils.validator.front.FrontValidateTimeClockInstantService;
import es.bcn.vigia.fmw.libutils.validator.front.FrontValidateTimeClockSummaryService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RestServerConfig.class })
@TestPropertySource(locations = {"classpath:app/config/application.properties"})
@WebAppConfiguration
public class TimeClockServiceImplTest {
	
	@Value("${url.api.orquestrador}")
	private String urlApiOrquestrador;

	@Value("${url.path.execution}")
	private String pathExecution;

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

	@Mock
	private FrontValidateTimeClockInstantService frontValidateTimeClockInstantService;
	
	@Mock
	private FrontValidateTimeClockSummaryService frontValidateTimeClockSummaryService;
	
	@Mock
	private ValidatorTimeClockSummary validatorTimeClockSummary;	
	
	@InjectMocks
	private TimeClockServiceImpl service;
	
	private ReturnRDTO returnRDTO;
	
	private ReturnRDTO returnRDTOKO;
	
	private TimeClockInstantRDTO timeClockInstantRDTO;

	private TimeClockSummaryRDTO timeClockSummaryRDTO;

	private QueryParameterTimeRegisterRDTO queryParameterRDTO;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		returnRDTO = ReturnRDTOStub.getSuccessMessage();
		timeClockInstantRDTO = TimeClockInstantRDTOStub.defaultOne();
		timeClockSummaryRDTO = TimeClockSummaryRDTOStub.defaultOne();
		returnRDTOKO = ReturnRDTOStub.getErrorMessage();
		queryParameterRDTO = QueryParameterTimeRegisterRDTOStub.defaultOne();
	}
	
	@Test
	public void caseOkInsertInstant() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathExecution", pathExecution);
		ReflectionTestUtils.setField(service, "pathTimeClockInstant", pathTimeClockInstant);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathExecution)
				.concat(pathTimeClockInstant);

		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(url, timeClockInstantRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		Mockito.when(frontValidateTimeClockInstantService.validateSyntaxInsert(timeClockInstantRDTO)).thenReturn(returnRDTO);
		Mockito.when(validatorTimeClockSummary.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.insertInstant(timeClockInstantRDTO);
	}

	@Test
	public void caseKoInsertInstantValidationsSyntax() throws ImiException {
		Mockito.when(frontValidateTimeClockInstantService.validateSyntaxInsert(timeClockInstantRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validatorTimeClockSummary.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.insertInstant(timeClockInstantRDTO);
	}
	
	@Test(expected = ImiException.class)
	public void caseKoInsertInstant() throws ImiException {
		Mockito.when(validatorTimeClockSummary.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.insertInstant(timeClockInstantRDTO);
	}

	@Test
	public void caseOkInsertSummary() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathExecution", pathExecution);
		ReflectionTestUtils.setField(service, "pathTimeClockSummary", pathTimeClockSummary);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathExecution)
				.concat(pathTimeClockSummary);

		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(url, timeClockSummaryRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		Mockito.when(frontValidateTimeClockSummaryService.validateSyntaxInsert(timeClockSummaryRDTO)).thenReturn(returnRDTO);
		Mockito.when(validatorTimeClockSummary.validateInsert(timeClockSummaryRDTO)).thenReturn(returnRDTO);
		Mockito.when(validatorTimeClockSummary.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.insertSummary(timeClockSummaryRDTO);
	}

	@Test
	public void caseKoInsertSummaryValidationsSyntax() throws ImiException {
		Mockito.when(frontValidateTimeClockSummaryService.validateSyntaxInsert(timeClockSummaryRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validatorTimeClockSummary.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.insertSummary(timeClockSummaryRDTO);
	}
	
	@Test(expected = ImiException.class)
	public void caseKoInsertSummary() throws ImiException {
		Mockito.when(validatorTimeClockSummary.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.insertSummary(timeClockSummaryRDTO);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void caseOkSelectTimeRegisterInstant() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathExecution", pathExecution);
		ReflectionTestUtils.setField(service, "pathTimeClockInstant", pathTimeClockInstant);
		ReflectionTestUtils.setField(service, "pathTimeClockInstantGet", pathTimeClockInstantGet);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathExecution)
				.concat(pathTimeClockInstant)
				.concat(pathTimeClockInstantGet);
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", queryParameterRDTO.getCodeContract());
		Mockito.when(frontValidateTimeClockInstantService.validateSyntaxSelectTimeRegisterInstant(queryParameterRDTO)).thenReturn(returnRDTO);
		Mockito.when(restCall.executeGET(url, urlParams, queryParameterRDTO)).thenReturn(ss);
		
		ReturnTimeRegisterInstantRDTO returnTimeRegisterInstantRDTO = new ReturnTimeRegisterInstantRDTO();
		returnTimeRegisterInstantRDTO.setReturnRDTO(returnRDTO);
		Mockito.when(utils.rest2Object(Mockito.any(ss.getClass()), Mockito.any(returnTimeRegisterInstantRDTO.getClass()))).thenReturn(returnTimeRegisterInstantRDTO);
		Mockito.when(validatorTimeClockSummary.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.selectTimeRegisterInstant(queryParameterRDTO);
	}

	@Test
	public void caseSelectTimeRegisterInstantKOValidateSyntax() throws ImiException {
		Mockito.when(frontValidateTimeClockInstantService.validateSyntaxSelectTimeRegisterInstant(queryParameterRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validatorTimeClockSummary.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.selectTimeRegisterInstant(queryParameterRDTO);
	}

	@Test(expected = ImiException.class)
	public void caseKoSelectTimeRegisterInstant() throws ImiException {
		Mockito.when(validatorTimeClockSummary.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.selectTimeRegisterInstant(queryParameterRDTO);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void caseOkSelectTimeRegisterSummary() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathExecution", pathExecution);
		ReflectionTestUtils.setField(service, "pathTimeClockSummary", pathTimeClockSummary);
		ReflectionTestUtils.setField(service, "pathTimeClockSummaryGet", pathTimeClockSummaryGet);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathExecution)
				.concat(pathTimeClockSummary)
				.concat(pathTimeClockSummaryGet);
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", queryParameterRDTO.getCodeContract());
		Mockito.when(frontValidateTimeClockSummaryService.validateSyntaxSelectTimeRegisterSummary(queryParameterRDTO)).thenReturn(returnRDTO);
		Mockito.when(restCall.executeGET(url, urlParams, queryParameterRDTO)).thenReturn(ss);
		
		ReturnTimeRegisterSummaryRDTO returnTimeRegisterSummaryRDTO = new ReturnTimeRegisterSummaryRDTO();
		returnTimeRegisterSummaryRDTO.setReturnRDTO(returnRDTO);
		Mockito.when(utils.rest2Object(Mockito.any(ss.getClass()), Mockito.any(returnTimeRegisterSummaryRDTO.getClass()))).thenReturn(returnTimeRegisterSummaryRDTO);
		Mockito.when(validatorTimeClockSummary.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.selectTimeRegisterSummary(queryParameterRDTO);
	}

	@Test
	public void caseSelectTimeRegisterSummaryKOValidateSyntax() throws ImiException {
		Mockito.when(frontValidateTimeClockSummaryService.validateSyntaxSelectTimeRegisterSummary(queryParameterRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validatorTimeClockSummary.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.selectTimeRegisterSummary(queryParameterRDTO);
	}

	@Test(expected = ImiException.class)
	public void caseKoSelectTimeRegisterSummary() throws ImiException {
		Mockito.when(validatorTimeClockSummary.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.selectTimeRegisterSummary(queryParameterRDTO);
	}

}