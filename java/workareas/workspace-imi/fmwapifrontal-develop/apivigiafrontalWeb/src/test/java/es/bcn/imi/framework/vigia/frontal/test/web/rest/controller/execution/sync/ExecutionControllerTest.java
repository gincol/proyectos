package es.bcn.imi.framework.vigia.frontal.test.web.rest.controller.execution.sync;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import es.bcn.imi.framework.vigia.frontal.business.execution.EventService;
import es.bcn.imi.framework.vigia.frontal.business.execution.TimeClockService;
import es.bcn.imi.framework.vigia.frontal.web.rest.controller.execution.sync.ExecutionController;
import es.bcn.imi.framework.vigia.test.parent.RestServerParentTest;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.APSEventRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.AnnulmentEventRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.EventServiceRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.InformativeEventRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterEventRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterTimeRegisterRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.TimeClockInstantRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.TimeClockSummaryRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.UnloadingEventRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnEventApprovedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnEventDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnEventPendingRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnEventRejectedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnTimeRegisterInstantRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnTimeRegisterSummaryRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnEventApprovedRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnEventDetailedRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnEventPendingRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnEventRejectedRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnTimeRegisterInstantRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnTimeRegisterSummaryRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.stub.APSEventRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.stub.AnnulmentEventRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.stub.EventServiceRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.stub.InformativeEventRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.stub.TimeClockInstantRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.stub.TimeClockSummaryRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.stub.UnloadingEventRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.QueryParameterEventRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.QueryParameterTimeRegisterRDTOStub;
import es.bcn.vigia.fmw.libutils.exception.ImiException;


public class ExecutionControllerTest extends RestServerParentTest {

	private MockMvc mockMvc;

	@Mock
	private EventService eventService;

	@Mock
	private TimeClockService timeClockService;

	
	@InjectMocks
	private ExecutionController controller;

	private ReturnRDTO returnRDTO;
	
	private ReturnRDTO returnRDTOKO;

	private EventServiceRDTO eventServiceRDTO;
	
	private InformativeEventRDTO informativeEventRDTO;
	
	private APSEventRDTO apsEventRDTO;
	
	private UnloadingEventRDTO unloadingEventRDTO;
	
	private AnnulmentEventRDTO annulmentEventRDTO;
	
	private TimeClockInstantRDTO timeClockInstantRDTO;

	private TimeClockSummaryRDTO timeClockSummaryRDTO;
	
	private QueryParameterTimeRegisterRDTO queryParameterTimeRegisterRDTO;
	
	private ReturnTimeRegisterInstantRDTO returnTimeRegisterInstantRDTO;
	
	private ReturnTimeRegisterSummaryRDTO returnTimeRegisterSummaryRDTO;
	
	private QueryParameterEventRDTO queryParameterEventRDTO;
	
	private ReturnEventDetailedRDTO returnEventDetailedRDTO;
	
	private ReturnEventApprovedRDTO returnEventApprovedRDTO;
	
	private ReturnEventRejectedRDTO returnEventRejectedRDTO;
	
	private ReturnEventPendingRDTO returnEventPendingRDTO;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		returnRDTO = ReturnRDTOStub.getSuccessMessage();
		returnRDTOKO = new ReturnRDTO();
		returnRDTOKO.setCode("KO");
		eventServiceRDTO = EventServiceRDTOStub.defaultOne();
		informativeEventRDTO = InformativeEventRDTOStub.defaultOne();
		apsEventRDTO = APSEventRDTOStub.defaultOne();
		unloadingEventRDTO = UnloadingEventRDTOStub.defaultOne();
		annulmentEventRDTO = AnnulmentEventRDTOStub.defaultOne();
		timeClockInstantRDTO = TimeClockInstantRDTOStub.defaultOne();
		timeClockSummaryRDTO = TimeClockSummaryRDTOStub.defaultOne();
		queryParameterTimeRegisterRDTO = QueryParameterTimeRegisterRDTOStub.defaultOne();
		returnTimeRegisterInstantRDTO = ReturnTimeRegisterInstantRDTOStub.getSuccessMessage();
		returnTimeRegisterSummaryRDTO = ReturnTimeRegisterSummaryRDTOStub.getSuccessMessage();
		queryParameterEventRDTO = QueryParameterEventRDTOStub.defaultOne();
		returnEventDetailedRDTO = ReturnEventDetailedRDTOStub.getSuccessMessage();
		returnEventApprovedRDTO = ReturnEventApprovedRDTOStub.getSuccessMessage();
		returnEventRejectedRDTO = ReturnEventRejectedRDTOStub.getSuccessMessage();
		returnEventPendingRDTO = ReturnEventPendingRDTOStub.getSuccessMessage();
	}

	@Test
	public void caseOkSaveServiceEvent() throws Exception {

		Mockito.when(eventService.insertService(Mockito.any(eventServiceRDTO.getClass()))).thenReturn(returnRDTO);

		mockMvc.perform(post("/sync/execucio/esdeveniment/servei").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + eventServiceRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void caseKOSaveServiceEventBadRequest() throws Exception {

		Mockito.when(eventService.insertService(Mockito.any(eventServiceRDTO.getClass()))).thenReturn(returnRDTOKO);

		mockMvc.perform(post("/sync/execucio/esdeveniment/servei").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + eventServiceRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}
	@Test
	public void caseKoSaveServiceEventException() throws Exception {

		Mockito.when(eventService.insertService(eventServiceRDTO)).thenReturn(returnRDTO);
		mockMvc.perform(post("/sync/execucio/esdeveniment/servei").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + eventServiceRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	
	@Test
	public void caseOkSaveInformativeEvent() throws Exception {

		Mockito.when(eventService.insertInformative(Mockito.any(informativeEventRDTO.getClass()))).thenReturn(returnRDTO);

		mockMvc.perform(post("/sync/execucio/esdeveniment/informatiu").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + informativeEventRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void caseKOSaveInformativeEventBadRequest() throws Exception {

		Mockito.when(eventService.insertInformative(Mockito.any(informativeEventRDTO.getClass()))).thenReturn(returnRDTOKO);

		mockMvc.perform(post("/sync/execucio/esdeveniment/informatiu").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + informativeEventRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}
	@Test
	public void caseKoSaveSInformativeEventException() throws Exception {

		Mockito.when(eventService.insertInformative(informativeEventRDTO)).thenReturn(returnRDTO);
		mockMvc.perform(post("/sync/execucio/esdeveniment/informatiu").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + informativeEventRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseOkSaveAnnulmentEvent() throws Exception {

		Mockito.when(eventService.insertAnnulment(Mockito.any(annulmentEventRDTO.getClass()))).thenReturn(returnRDTO);

		mockMvc.perform(post("/sync/execucio/esdeveniment/anulacio").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + annulmentEventRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void caseKOSaveAnnulmentEventBadRequest() throws Exception {

		Mockito.when(eventService.insertAnnulment(Mockito.any(annulmentEventRDTO.getClass()))).thenReturn(returnRDTOKO);

		mockMvc.perform(post("/sync/execucio/esdeveniment/anulacio").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + annulmentEventRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}
	@Test
	public void caseKoSaveSAnnulmentEventException() throws Exception {

		Mockito.when(eventService.insertAnnulment(annulmentEventRDTO)).thenReturn(returnRDTO);
		mockMvc.perform(post("/sync/execucio/esdeveniment/anulacio").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + annulmentEventRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseOkSaveUnloadingEvent() throws Exception {

		Mockito.when(eventService.insertUnloading(Mockito.any(unloadingEventRDTO.getClass()))).thenReturn(returnRDTO);

		mockMvc.perform(post("/sync/execucio/esdeveniment/descarrega").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + unloadingEventRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void caseKOSaveUnloadingEventBadRequest() throws Exception {

		Mockito.when(eventService.insertUnloading(Mockito.any(unloadingEventRDTO.getClass()))).thenReturn(returnRDTOKO);

		mockMvc.perform(post("/sync/execucio/esdeveniment/descarrega").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + unloadingEventRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}
	@Test
	public void caseKoSaveUnloadingEventException() throws Exception {

		Mockito.when(eventService.insertUnloading(unloadingEventRDTO)).thenReturn(returnRDTO);
		mockMvc.perform(post("/sync/execucio/esdeveniment/descarrega").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + unloadingEventRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseOkSaveAPSEvent() throws Exception {

		Mockito.when(eventService.insertAPS(Mockito.any(apsEventRDTO.getClass()))).thenReturn(returnRDTO);

		mockMvc.perform(post("/sync/execucio/esdeveniment/aps").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + apsEventRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void caseKOSaveAPSEventBadRequest() throws Exception {

		Mockito.when(eventService.insertAPS(Mockito.any(apsEventRDTO.getClass()))).thenReturn(returnRDTOKO);

		mockMvc.perform(post("/sync/execucio/esdeveniment/aps").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + apsEventRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}
	@Test
	public void caseKoSaveAPSEventException() throws Exception {

		Mockito.when(eventService.insertAPS(apsEventRDTO)).thenReturn(returnRDTO);
		mockMvc.perform(post("/sync/execucio/esdeveniment/aps").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + apsEventRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseOkSaveTimeClockInstant() throws Exception {

		Mockito.when(timeClockService.insertInstant(Mockito.any(timeClockInstantRDTO.getClass()))).thenReturn(returnRDTO);

		mockMvc.perform(post("/sync/execucio/fitxatge/instantani").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"codiContracta\": \"" + timeClockInstantRDTO.getCodeContract() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void caseKOSaveTimeClockInstantBadRequest() throws Exception {

		Mockito.when(timeClockService.insertInstant(Mockito.any(timeClockInstantRDTO.getClass()))).thenReturn(returnRDTOKO);

		mockMvc.perform(post("/sync/execucio/fitxatge/instantani").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"codiContracta\": \"" + timeClockInstantRDTO.getCodeContract() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}
	@Test
	public void caseKoTimeClockInstantException() throws Exception {

		Mockito.when(timeClockService.insertInstant(timeClockInstantRDTO)).thenReturn(returnRDTO);
		mockMvc.perform(post("/sync/execucio/fitxatge/instantani").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"codiContracta\": \"" + timeClockInstantRDTO.getCodeContract() + "\"\r\n" + "}"))
				.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseOkSaveTimeClockSummary() throws Exception {

		Mockito.when(timeClockService.insertSummary(Mockito.any(timeClockSummaryRDTO.getClass()))).thenReturn(returnRDTO);

		mockMvc.perform(post("/sync/execucio/fitxatge/resum").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"codiContracta\": \"" + timeClockSummaryRDTO.getCodeContract() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void caseKOSaveTimeClockSummaryBadRequest() throws Exception {

		Mockito.when(timeClockService.insertSummary(Mockito.any(timeClockSummaryRDTO.getClass()))).thenReturn(returnRDTOKO);

		mockMvc.perform(post("/sync/execucio/fitxatge/resum").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"codiContracta\": \"" + timeClockSummaryRDTO.getCodeContract() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}
	@Test
	public void caseKoTimeClockSummaryException() throws Exception {

		Mockito.when(timeClockService.insertSummary(timeClockSummaryRDTO)).thenReturn(returnRDTO);
		mockMvc.perform(post("/sync/execucio/fitxatge/resum").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"codiContracta\": \"" + timeClockSummaryRDTO.getCodeContract() + "\"\r\n" + "}"))
				.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseOkSelectTimeRegisterInstant() throws Exception{
		
		Mockito.when(timeClockService.selectTimeRegisterInstant(Mockito.any(queryParameterTimeRegisterRDTO.getClass()))).thenReturn(returnTimeRegisterInstantRDTO);
    	mockMvc.perform(get("/sync/execucio/contracta/"+queryParameterTimeRegisterRDTO.getCodeContract()+"/fitxatge/instantani?data="+queryParameterTimeRegisterRDTO.getDate()+"&codiRellotge="+queryParameterTimeRegisterRDTO.getCodeWatch()+"&codiTreballador="+queryParameterTimeRegisterRDTO.getCodeEmployee())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void caseKoSelectTimeRegisterInstant() throws Exception{
		
		Mockito.doThrow(ImiException.class).when(timeClockService).selectTimeRegisterInstant(Mockito.any(queryParameterTimeRegisterRDTO.getClass()));
    	
		mockMvc.perform(get("/sync/execucio/contracta/"+queryParameterTimeRegisterRDTO.getCodeContract()+"/fitxatge/instantani?data="+queryParameterTimeRegisterRDTO.getDate()+"&codiRellotge="+queryParameterTimeRegisterRDTO.getCodeWatch()+"&codiTreballador="+queryParameterTimeRegisterRDTO.getCodeEmployee())
		.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseOkSelectTimeRegisterSummary() throws Exception{
		
		Mockito.when(timeClockService.selectTimeRegisterSummary(Mockito.any(queryParameterTimeRegisterRDTO.getClass()))).thenReturn(returnTimeRegisterSummaryRDTO);
    	mockMvc.perform(get("/sync/execucio/contracta/"+queryParameterTimeRegisterRDTO.getCodeContract()+"/fitxatge/resum?data="+queryParameterTimeRegisterRDTO.getDate()+"&codiInstallation="+queryParameterTimeRegisterRDTO.getCodeInstallation())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void caseKoSelectTimeRegisterSummary() throws Exception{
		
		Mockito.doThrow(ImiException.class).when(timeClockService).selectTimeRegisterSummary(Mockito.any(queryParameterTimeRegisterRDTO.getClass()));
    	
		mockMvc.perform(get("/sync/execucio/contracta/"+queryParameterTimeRegisterRDTO.getCodeContract()+"/fitxatge/resum?data="+queryParameterTimeRegisterRDTO.getDate()+"&codiInstallation="+queryParameterTimeRegisterRDTO.getCodeInstallation())
		.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseOkSelectEventDetailed() throws Exception{
		
		Mockito.when(eventService.selectDetailed(Mockito.any(queryParameterEventRDTO.getClass()))).thenReturn(returnEventDetailedRDTO);
    	mockMvc.perform(get("/sync/execucio/contracta/"+queryParameterEventRDTO.getCodeContract()+"/esdeveniment/"+queryParameterEventRDTO.getCode()+"/detall?codiUsuari="+queryParameterEventRDTO.getCodeUser())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void caseKoSelectEventDetailed() throws Exception{
		
		Mockito.doThrow(ImiException.class).when(eventService).selectDetailed(Mockito.any(queryParameterEventRDTO.getClass()));
    	
    	mockMvc.perform(get("/sync/execucio/contracta/"+queryParameterEventRDTO.getCodeContract()+"/esdeveniment/"+queryParameterEventRDTO.getCode()+"/detall?codiUsuari="+queryParameterEventRDTO.getCodeUser())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseOkSelectEventApproved() throws Exception{
		
		Mockito.when(eventService.selectApproved(Mockito.any(queryParameterEventRDTO.getClass()))).thenReturn(returnEventApprovedRDTO);
    	mockMvc.perform(get("/sync/execucio/contracta/"+queryParameterEventRDTO.getCodeContract()+"/esdeveniment/aprovats?codiUsuari="+queryParameterEventRDTO.getCodeUser()+"&dataExecucio="+queryParameterEventRDTO.getDateExecution())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void caseKoSelectEventApproved() throws Exception{
		
		Mockito.doThrow(ImiException.class).when(eventService).selectApproved(Mockito.any(queryParameterEventRDTO.getClass()));
    	
    	mockMvc.perform(get("/sync/execucio/contracta/"+queryParameterEventRDTO.getCodeContract()+"/esdeveniment/aprovats?codiUsuari="+queryParameterEventRDTO.getCodeUser()+"&dataExecucio="+queryParameterEventRDTO.getDateExecution())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseOkSelectEventRejected() throws Exception{
		
		Mockito.when(eventService.selectRejected(Mockito.any(queryParameterEventRDTO.getClass()))).thenReturn(returnEventRejectedRDTO);
    	mockMvc.perform(get("/sync/execucio/contracta/"+queryParameterEventRDTO.getCodeContract()+"/esdeveniment/rebutjats?codiUsuari="+queryParameterEventRDTO.getCodeUser()+"&dataExecucio="+queryParameterEventRDTO.getDateExecution())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void caseKoSelectEventRejected() throws Exception{
		
		Mockito.doThrow(ImiException.class).when(eventService).selectRejected(Mockito.any(queryParameterEventRDTO.getClass()));
    	
    	mockMvc.perform(get("/sync/execucio/contracta/"+queryParameterEventRDTO.getCodeContract()+"/esdeveniment/rebutjats?codiUsuari="+queryParameterEventRDTO.getCodeUser()+"&dataExecucio="+queryParameterEventRDTO.getDateExecution())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseOkSelectEventPending() throws Exception{
		
		Mockito.when(eventService.selectPending(Mockito.any(queryParameterEventRDTO.getClass()))).thenReturn(returnEventPendingRDTO);
    	mockMvc.perform(get("/sync/execucio/contracta/"+queryParameterEventRDTO.getCodeContract()+"/esdeveniment/pendents?codiUsuari="+queryParameterEventRDTO.getCodeUser()+"&dataInici="+queryParameterEventRDTO.getDateStart()+"&dataFi="+queryParameterEventRDTO.getDateEnd())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void caseKoSelectEventPending() throws Exception{
		
		Mockito.doThrow(ImiException.class).when(eventService).selectPending(Mockito.any(queryParameterEventRDTO.getClass()));
    	
    	mockMvc.perform(get("/sync/execucio/contracta/"+queryParameterEventRDTO.getCodeContract()+"/esdeveniment/pendents?codiUsuari="+queryParameterEventRDTO.getCodeUser()+"&dataInici="+queryParameterEventRDTO.getDateStart()+"&dataFi="+queryParameterEventRDTO.getDateEnd())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isInternalServerError());
	}
}
	