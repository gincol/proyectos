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

import es.bcn.imi.framework.vigia.frontal.business.execution.impl.EventServiceImpl;
import es.bcn.imi.framework.vigia.frontal.validator.ValidatorAPSEvent;
import es.bcn.imi.framework.vigia.frontal.validator.ValidatorAnnulmentEvent;
import es.bcn.imi.framework.vigia.frontal.validator.ValidatorInformativeEvent;
import es.bcn.imi.framework.vigia.frontal.validator.ValidatorServiceEvent;
import es.bcn.imi.framework.vigia.frontal.validator.ValidatorUnloadingEvent;
import es.bcn.imi.framework.vigia.frontal.validator.ValidatorUtils;
import es.bcn.imi.framework.vigia.frontal.web.rest.config.RestServerConfig;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.APSEventRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.AnnulmentEventRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.EventServiceRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.InformativeEventRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterEventRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.UnloadingEventRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.commons.BaseTransactionIdRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnEventApprovedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnEventDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnEventPendingRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnEventRejectedRDTO;
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
import es.bcn.vigia.fmw.libutils.validator.front.FrontValidateAPSEventService;
import es.bcn.vigia.fmw.libutils.validator.front.FrontValidateAnnulmentEventService;
import es.bcn.vigia.fmw.libutils.validator.front.FrontValidateInformativeEventService;
import es.bcn.vigia.fmw.libutils.validator.front.FrontValidateServiceEventService;
import es.bcn.vigia.fmw.libutils.validator.front.FrontValidateUnloadingEventService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RestServerConfig.class })
@TestPropertySource(locations = {"classpath:app/config/application.properties"})
@WebAppConfiguration
public class EventServiceImplTest {
	
	@Value("${url.api.orquestrador}")
	private String urlApiOrquestrador;

	@Value("${url.path.execution}")
	private String pathExecution;

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
	private String pathServiceEvent;

	@Value("${url.path.event.cancellation}")
	private String pathCancellationEvent;

	@Value("${url.path.event.aps}")
	private String pathAPSEvent;

	@Value("${url.path.event.informative}")
	private String pathInformativeEvent;

	@Value("${url.path.event.unloading}")
	private String pathUnloadingEvent;
	
	@Mock
	private RestCall restCall;
	
	@Mock
	private Utils utils;

	@Mock
	private FrontValidateServiceEventService frontValidateServiceEventService;
	
	@Mock
	private ValidatorServiceEvent validatorServiceEvent;

	@Mock
	private FrontValidateAnnulmentEventService frontValidateCancellationEventService;
	
	@Mock
	private ValidatorAnnulmentEvent validatorAnnulmentEvent;

	@Mock
	private FrontValidateAPSEventService frontValidateAPSEventService;
	
	@Mock
	private ValidatorAPSEvent validatorAPSEvent;
	
	@Mock
	private FrontValidateInformativeEventService frontValidateInformativeEventService;
	
	@Mock
	private ValidatorInformativeEvent validatorInformativeEvent;

	@Mock
	private FrontValidateUnloadingEventService frontValidateUnloadingEventService;
	
	@Mock
	private ValidatorUnloadingEvent validatorUnloadingEvent;
	
	@Mock
	private ValidatorUtils validator;
	
	@InjectMocks
	private EventServiceImpl service;
	
	private ReturnRDTO returnRDTO;
	
	private ReturnRDTO returnRDTOKO;
	
	private EventServiceRDTO eventServiceRDTO;
	
	private InformativeEventRDTO informativeEventRDTO;
	
	private APSEventRDTO apsEventRDTO;
	
	private UnloadingEventRDTO unloadingEventRDTO;
	
	private AnnulmentEventRDTO annulmentEventRDTO;

	private QueryParameterEventRDTO queryParameterEventRDTO;	

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		returnRDTO = ReturnRDTOStub.getSuccessMessage();
		eventServiceRDTO = EventServiceRDTOStub.defaultOne();
		informativeEventRDTO = InformativeEventRDTOStub.defaultOne();
		apsEventRDTO = APSEventRDTOStub.defaultOne();
		unloadingEventRDTO = UnloadingEventRDTOStub.defaultOne();
		annulmentEventRDTO = AnnulmentEventRDTOStub.defaultOne();
		returnRDTOKO = ReturnRDTOStub.getErrorMessage();
		queryParameterEventRDTO = QueryParameterEventRDTOStub.defaultOne();
	}
	
	@Test
	public void caseOkInsertAnnulment() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathExecution", pathExecution);
		ReflectionTestUtils.setField(service, "pathCancellationEvent", pathCancellationEvent);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathExecution)
				.concat(pathCancellationEvent);

		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(url, annulmentEventRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		Mockito.when(frontValidateCancellationEventService.validateSyntaxInsert(annulmentEventRDTO)).thenReturn(returnRDTO);
		Mockito.when(validatorAnnulmentEvent.validateInsert(annulmentEventRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.insertAnnulment(annulmentEventRDTO);
	}

	@Test
	public void caseKoInsertAnnulmentValidationsSyntax() throws ImiException {
		Mockito.when(frontValidateCancellationEventService.validateSyntaxInsert(annulmentEventRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.insertAnnulment(annulmentEventRDTO);
	}
	
	@Test(expected = ImiException.class)
	public void caseKoInsertAnnulment() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.insertAnnulment(annulmentEventRDTO);
	}

	@Test
	public void caseOkInsertAPS() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathExecution", pathExecution);
		ReflectionTestUtils.setField(service, "pathAPSEvent", pathAPSEvent);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathExecution)
				.concat(pathAPSEvent);

		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(url, apsEventRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		Mockito.when(frontValidateAPSEventService.validateSyntaxInsert(apsEventRDTO)).thenReturn(returnRDTO);
		Mockito.when(validatorAPSEvent.validateInsert(apsEventRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.insertAPS(apsEventRDTO);
	}

	@Test
	public void caseKoInsertAPSValidationsSyntax() throws ImiException {
		Mockito.when(frontValidateAPSEventService.validateSyntaxInsert(apsEventRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.insertAPS(apsEventRDTO);
	}
	
	@Test(expected = ImiException.class)
	public void caseKoInsertAPS() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.insertAPS(apsEventRDTO);
	}

	@Test
	public void caseOkInsertInformative() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathExecution", pathExecution);
		ReflectionTestUtils.setField(service, "pathInformativeEvent", pathInformativeEvent);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathExecution)
				.concat(pathInformativeEvent);

		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(url, informativeEventRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		Mockito.when(frontValidateInformativeEventService.validateSyntaxInsert(informativeEventRDTO)).thenReturn(returnRDTO);
		Mockito.when(validatorInformativeEvent.validateInsert(informativeEventRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.insertInformative(informativeEventRDTO);
	}

	@Test
	public void caseKoInsertInformativeValidationsSyntax() throws ImiException {
		Mockito.when(frontValidateInformativeEventService.validateSyntaxInsert(informativeEventRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.insertInformative(informativeEventRDTO);
	}
	
	@Test(expected = ImiException.class)
	public void caseKoInsertInformative() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.insertInformative(informativeEventRDTO);
	}

	@Test
	public void caseOkInsertUnloading() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathExecution", pathExecution);
		ReflectionTestUtils.setField(service, "pathUnloadingEvent", pathUnloadingEvent);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathExecution)
				.concat(pathUnloadingEvent);

		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(url, unloadingEventRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		Mockito.when(frontValidateUnloadingEventService.validateSyntaxInsert(unloadingEventRDTO)).thenReturn(returnRDTO);
		Mockito.when(validatorUnloadingEvent.validateInsert(unloadingEventRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.insertUnloading(unloadingEventRDTO);
	}

	@Test
	public void caseKoInsertUnloadingValidationsSyntax() throws ImiException {
		Mockito.when(frontValidateUnloadingEventService.validateSyntaxInsert(unloadingEventRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.insertUnloading(unloadingEventRDTO);
	}
	
	@Test(expected = ImiException.class)
	public void caseKoInsertUnloading() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.insertUnloading(unloadingEventRDTO);
	}

	
	@Test
	public void caseOkInsertServiceEvent() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathExecution", pathExecution);
		ReflectionTestUtils.setField(service, "pathServiceEvent", pathServiceEvent);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathExecution)
				.concat(pathServiceEvent);

		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(url, eventServiceRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		Mockito.when(frontValidateServiceEventService.validateSyntaxInsert(eventServiceRDTO)).thenReturn(returnRDTO);
		Mockito.when(validatorServiceEvent.validateInsert(eventServiceRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.insertService(eventServiceRDTO);
	}

	@Test
	public void caseKoInsertServiceEventValidationsSyntax() throws ImiException {
		Mockito.when(frontValidateServiceEventService.validateSyntaxInsert(eventServiceRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.insertService(eventServiceRDTO);
	}
	
	@Test(expected = ImiException.class)
	public void caseKoInsertServiceEvent() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.insertService(eventServiceRDTO);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void caseOkSelectDetailed() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathExecution", pathExecution);
		ReflectionTestUtils.setField(service, "pathEvent", pathEvent);
		ReflectionTestUtils.setField(service, "pathEventGetDetailed", pathEventGetDetailed);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathExecution)
				.concat(pathEvent)
				.concat(pathEventGetDetailed);
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", queryParameterEventRDTO.getCode());
		urlParams.put("codiContracta", queryParameterEventRDTO.getCodeContract());
		
		Mockito.when(frontValidateServiceEventService.validateSyntaxSelectDetailed(queryParameterEventRDTO)).thenReturn(returnRDTO);
		Mockito.when(restCall.executeGET(url, urlParams, queryParameterEventRDTO)).thenReturn(ss);
		
		ReturnEventDetailedRDTO returnEventDetailedRDTO = new ReturnEventDetailedRDTO();
		returnEventDetailedRDTO.setReturnRDTO(returnRDTO);
		Mockito.when(utils.rest2Object(Mockito.any(ss.getClass()), Mockito.any(returnEventDetailedRDTO.getClass()))).thenReturn(returnEventDetailedRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.selectDetailed(queryParameterEventRDTO);
	}

	@Test
	public void caseSelectDetailedKoValidateSyntax() throws ImiException {
		Mockito.when(frontValidateServiceEventService.validateSyntaxSelectDetailed(queryParameterEventRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.selectDetailed(queryParameterEventRDTO);
	}

	@Test(expected = ImiException.class)
	public void caseKoSelectDetailed() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.selectDetailed(queryParameterEventRDTO);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void caseOkSelectApproved() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathExecution", pathExecution);
		ReflectionTestUtils.setField(service, "pathEvent", pathEvent);
		ReflectionTestUtils.setField(service, "pathEventGetApproved", pathEventGetApproved);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathExecution)
				.concat(pathEvent)
				.concat(pathEventGetApproved);
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", queryParameterEventRDTO.getCodeContract());
		
		Mockito.when(frontValidateServiceEventService.validateSyntaxSelectApproved(queryParameterEventRDTO)).thenReturn(returnRDTO);
		Mockito.when(restCall.executeGET(url, urlParams, queryParameterEventRDTO)).thenReturn(ss);
		
		ReturnEventApprovedRDTO returnEventApprovedRDTO = new ReturnEventApprovedRDTO();
		returnEventApprovedRDTO.setReturnRDTO(returnRDTO);
		Mockito.when(utils.rest2Object(Mockito.any(ss.getClass()), Mockito.any(returnEventApprovedRDTO.getClass()))).thenReturn(returnEventApprovedRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.selectApproved(queryParameterEventRDTO);
	}

	@Test
	public void caseSelectApprovedKoValidateSyntax() throws ImiException {
		Mockito.when(frontValidateServiceEventService.validateSyntaxSelectApproved(queryParameterEventRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.selectApproved(queryParameterEventRDTO);
	}

	@Test(expected = ImiException.class)
	public void caseKoSelectApproved() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.selectApproved(queryParameterEventRDTO);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void caseOkSelectRejected() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathExecution", pathExecution);
		ReflectionTestUtils.setField(service, "pathEvent", pathEvent);
		ReflectionTestUtils.setField(service, "pathEventGetRejected", pathEventGetRejected);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathExecution)
				.concat(pathEvent)
				.concat(pathEventGetRejected);
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", queryParameterEventRDTO.getCodeContract());
		
		Mockito.when(frontValidateServiceEventService.validateSyntaxSelectRejected(queryParameterEventRDTO)).thenReturn(returnRDTO);
		Mockito.when(restCall.executeGET(url, urlParams, queryParameterEventRDTO)).thenReturn(ss);
		
		ReturnEventRejectedRDTO returnEventRejectedRDTO = new ReturnEventRejectedRDTO();
		returnEventRejectedRDTO.setReturnRDTO(returnRDTO);
		Mockito.when(utils.rest2Object(Mockito.any(ss.getClass()), Mockito.any(returnEventRejectedRDTO.getClass()))).thenReturn(returnEventRejectedRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.selectRejected(queryParameterEventRDTO);
	}

	@Test
	public void caseSelectRejectedKoValidateSyntax() throws ImiException {
		Mockito.when(frontValidateServiceEventService.validateSyntaxSelectRejected(queryParameterEventRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.selectRejected(queryParameterEventRDTO);
	}

	@Test(expected = ImiException.class)
	public void caseKoSelectRejected() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.selectRejected(queryParameterEventRDTO);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void caseOkSelectPending() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathExecution", pathExecution);
		ReflectionTestUtils.setField(service, "pathEvent", pathEvent);
		ReflectionTestUtils.setField(service, "pathEventGetPending", pathEventGetPending);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathExecution)
				.concat(pathEvent)
				.concat(pathEventGetPending);
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", queryParameterEventRDTO.getCodeContract());
		
		Mockito.when(frontValidateServiceEventService.validateSyntaxSelectPending(queryParameterEventRDTO)).thenReturn(returnRDTO);
		Mockito.when(restCall.executeGET(url, urlParams, queryParameterEventRDTO)).thenReturn(ss);
		
		ReturnEventPendingRDTO returnEventPendingRDTO = new ReturnEventPendingRDTO();
		returnEventPendingRDTO.setReturnRDTO(returnRDTO);
		Mockito.when(utils.rest2Object(Mockito.any(ss.getClass()), Mockito.any(returnEventPendingRDTO.getClass()))).thenReturn(returnEventPendingRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.selectPending(queryParameterEventRDTO);
	}

	@Test
	public void caseSelectPendingKoValidateSyntax() throws ImiException {
		Mockito.when(frontValidateServiceEventService.validateSyntaxSelectPending(queryParameterEventRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.selectPending(queryParameterEventRDTO);
	}

	@Test(expected = ImiException.class)
	public void caseKoSelectPending() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.selectPending(queryParameterEventRDTO);
	}
}
