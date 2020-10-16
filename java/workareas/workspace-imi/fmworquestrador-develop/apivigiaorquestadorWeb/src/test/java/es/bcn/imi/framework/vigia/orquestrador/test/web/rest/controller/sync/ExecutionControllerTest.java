package es.bcn.imi.framework.vigia.orquestrador.test.web.rest.controller.sync;

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

import es.bcn.imi.framework.vigia.orquestrador.business.execution.EventService;
import es.bcn.imi.framework.vigia.orquestrador.business.execution.TimeClockService;
import es.bcn.imi.framework.vigia.orquestrador.test.parent.RestServerParentTest;
import es.bcn.imi.framework.vigia.orquestrador.web.rest.controller.sync.ExecutionController;
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
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.QueryParameterEventRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.QueryParameterTimeRegisterRDTOStub;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public class ExecutionControllerTest extends RestServerParentTest {

	private MockMvc mockMvc;

	@Mock
	private  EventService eventService;

	@Mock
	private  TimeClockService timeClockService;

	@InjectMocks
	private ExecutionController controller;
	
	private ReturnRDTO returnRDTO;
	
	private ReturnTimeRegisterInstantRDTO returnTimeRegisterInstantRDTO;
	
	private ReturnTimeRegisterSummaryRDTO returnTimeRegisterSummaryRDTO;

	private QueryParameterTimeRegisterRDTO queryParameterTimeRegisterRDTO;
	
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
	public void caseOkEventServiceSync() throws Exception {
		
		EventServiceRDTO eventServiceRDTO = new EventServiceRDTO();
		
		Mockito.when(eventService.insertService(eventServiceRDTO)).thenReturn(returnRDTO);

		mockMvc.perform(post("/sync/execucio/esdeveniment/servei").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\r\n" + 
						"   \"codi\":\"P40400218181940\",\r\n" + 
						"   \"codiContracta\":\"C\",\r\n" + 
						"   \"codiItinerari\":\"3CNNBEMEEV1D4GO004M\",\r\n" + 
						"   \"codiTipus\":\"INS\",\r\n" + 
						"   \"codiMotiu\":\"01\",\r\n" + 
						"   \"codiSubMotiu\":\"01\",\r\n" + 
						"   \"data\":\"27/04/2020 00:00:00\",\r\n" + 
						"   \"dataExecucio\":\"27/04/2020 00:00:00\",\r\n" + 
						"   \"observacio\":\"WEBSERVICE ESDEVENIMENT SERVEI INICI\",\r\n" + 
						"   \"usuariInsercio\":\"JROJAST\"\r\n" + 
						"}"))
				.andDo(print()).andExpect(status().isOk());

	}
	
	@Test
	public void caseOkEventInformativeSync() throws Exception {
		
		InformativeEventRDTO informativeEventRDTO = new InformativeEventRDTO();
		
		Mockito.when(eventService.insertInformative(informativeEventRDTO)).thenReturn(returnRDTO);

		mockMvc.perform(post("/sync/execucio/esdeveniment/informatiu").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\r\n" + 
						"   \"codi\":\"P40400218181940\",\r\n" + 
						"   \"codiContracta\":\"C\",\r\n" + 
						"   \"codiItinerari\":\"3CNNBEMEEV1D4GO004M\",\r\n" + 
						"   \"codiTipus\":\"INS\",\r\n" + 
						"   \"codiMotiu\":\"01\",\r\n" + 
						"   \"codiSubMotiu\":\"01\",\r\n" + 
						"   \"data\":\"27/04/2020 00:00:00\",\r\n" + 
						"   \"dataExecucio\":\"27/04/2020 00:00:00\",\r\n" + 
						"   \"observacio\":\"WEBSERVICE ESDEVENIMENT SERVEI INICI\",\r\n" + 
						"   \"usuariInsercio\":\"JROJAST\"\r\n" + 
						"}"))
				.andDo(print()).andExpect(status().isOk());

	}
	
	@Test
	public void caseOkEventAPSSync() throws Exception {
		
		APSEventRDTO apsEventRDTO = new APSEventRDTO();
		
		Mockito.when(eventService.insertAps(apsEventRDTO)).thenReturn(returnRDTO);

		mockMvc.perform(post("/sync/execucio/esdeveniment/aps").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\r\n" + 
						"   \"codi\":\"P40400218181940\",\r\n" + 
						"   \"codiContracta\":\"C\",\r\n" + 
						"   \"codiItinerari\":\"3CNNBEMEEV1D4GO004M\",\r\n" + 
						"   \"codiTipus\":\"INS\",\r\n" + 
						"   \"codiMotiu\":\"01\",\r\n" + 
						"   \"codiSubMotiu\":\"01\",\r\n" + 
						"   \"data\":\"27/04/2020 00:00:00\",\r\n" + 
						"   \"dataExecucio\":\"27/04/2020 00:00:00\",\r\n" + 
						"   \"observacio\":\"WEBSERVICE ESDEVENIMENT SERVEI INICI\",\r\n" + 
						"   \"usuariInsercio\":\"JROJAST\"\r\n" + 
						"}"))
				.andDo(print()).andExpect(status().isOk());

	}
	
	@Test
	public void caseOkEventUnloadingSync() throws Exception {
		
		UnloadingEventRDTO unloadingEventRDTO = new UnloadingEventRDTO();
		
		Mockito.when(eventService.insertUnloading(unloadingEventRDTO)).thenReturn(returnRDTO);

		mockMvc.perform(post("/sync/execucio/esdeveniment/descarrega").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\r\n" + 
						"   \"codi\":\"P40400218181940\",\r\n" + 
						"   \"codiContracta\":\"C\",\r\n" + 
						"   \"codiItinerari\":\"3CNNBEMEEV1D4GO004M\",\r\n" + 
						"   \"codiTipus\":\"INS\",\r\n" + 
						"   \"codiMotiu\":\"01\",\r\n" + 
						"   \"codiSubMotiu\":\"01\",\r\n" + 
						"   \"data\":\"27/04/2020 00:00:00\",\r\n" + 
						"   \"dataExecucio\":\"27/04/2020 00:00:00\",\r\n" + 
						"   \"observacio\":\"WEBSERVICE ESDEVENIMENT SERVEI INICI\",\r\n" + 
						"   \"usuariInsercio\":\"JROJAST\"\r\n" + 
						"}"))
				.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void caseOkEventAnnulmentSync() throws Exception {
		
		AnnulmentEventRDTO annulmentEventRDTO = new AnnulmentEventRDTO();
		
		Mockito.when(eventService.insertAnnulment(annulmentEventRDTO)).thenReturn(returnRDTO);

		mockMvc.perform(post("/sync/execucio/esdeveniment/anullacio").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\r\n" + 
						"   \"codi\":\"P40400218181940\",\r\n" + 
						"   \"codiContracta\":\"C\",\r\n" + 
						"   \"codiItinerari\":\"3CNNBEMEEV1D4GO004M\",\r\n" + 
						"   \"codiTipus\":\"INS\",\r\n" + 
						"   \"codiMotiu\":\"01\",\r\n" + 
						"   \"codiSubMotiu\":\"01\",\r\n" + 
						"   \"data\":\"27/04/2020 00:00:00\",\r\n" + 
						"   \"dataExecucio\":\"27/04/2020 00:00:00\",\r\n" + 
						"   \"observacio\":\"WEBSERVICE ESDEVENIMENT SERVEI INICI\",\r\n" + 
						"   \"usuariInsercio\":\"JROJAST\"\r\n" + 
						"}"))
				.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void caseOkTimeClockInstantSync() throws Exception {
		
		TimeClockInstantRDTO timeClockInstantRDTO = new TimeClockInstantRDTO();
		
		Mockito.when(timeClockService.insertInstant(timeClockInstantRDTO)).thenReturn(returnRDTO);

		mockMvc.perform(post("/sync/execucio/fitxatge/instantani").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\r\n" + 
						"   \"codi\":\"P40400218181940\",\r\n" + 
						"   \"codiContracta\":\"C\",\r\n" + 
						"   \"codiItinerari\":\"3CNNBEMEEV1D4GO004M\",\r\n" + 
						"   \"codiTipus\":\"INS\",\r\n" + 
						"   \"codiMotiu\":\"01\",\r\n" + 
						"   \"codiSubMotiu\":\"01\",\r\n" + 
						"   \"data\":\"27/04/2020 00:00:00\",\r\n" + 
						"   \"dataExecucio\":\"27/04/2020 00:00:00\",\r\n" + 
						"   \"observacio\":\"WEBSERVICE ESDEVENIMENT SERVEI INICI\",\r\n" + 
						"   \"usuariInsercio\":\"JROJAST\"\r\n" + 
						"}"))
				.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void caseOkTimeClockSummarySync() throws Exception {
		
		TimeClockSummaryRDTO timeSummaryRDTO = new TimeClockSummaryRDTO();
		
		Mockito.when(timeClockService.insertSummary(timeSummaryRDTO)).thenReturn(returnRDTO);

		mockMvc.perform(post("/sync/execucio/fitxatge/resum").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\r\n" + 
						"   \"codi\":\"P40400218181940\",\r\n" + 
						"   \"codiContracta\":\"C\",\r\n" + 
						"   \"codiItinerari\":\"3CNNBEMEEV1D4GO004M\",\r\n" + 
						"   \"codiTipus\":\"INS\",\r\n" + 
						"   \"codiMotiu\":\"01\",\r\n" + 
						"   \"codiSubMotiu\":\"01\",\r\n" + 
						"   \"data\":\"27/04/2020 00:00:00\",\r\n" + 
						"   \"dataExecucio\":\"27/04/2020 00:00:00\",\r\n" + 
						"   \"observacio\":\"WEBSERVICE ESDEVENIMENT SERVEI INICI\",\r\n" + 
						"   \"usuariInsercio\":\"JROJAST\"\r\n" + 
						"}"))
				.andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void caseOkSelectTimeRegisterInstant() throws Exception {
		Mockito.when(timeClockService.selectTimeRegisterInstant(Mockito.any(queryParameterTimeRegisterRDTO.getClass())))
				.thenReturn(returnTimeRegisterInstantRDTO);
		mockMvc.perform(get("/sync/execucio/fitxatge/instantani/contracta/" + queryParameterTimeRegisterRDTO.getCodeContract()
		+ "?data=" + queryParameterTimeRegisterRDTO.getDate() + "&codiRellotge="
		+ queryParameterTimeRegisterRDTO.getCodeWatch() + "&codiTreballador="
		+ queryParameterTimeRegisterRDTO.getCodeEmployee() + "&transactionId=" + queryParameterTimeRegisterRDTO.getTransactionId()))
				.andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void caseKoSelectTimeRegisterInstant() throws Exception {
		Mockito.doThrow(ImiException.class).when(timeClockService)
				.selectTimeRegisterInstant(Mockito.any(queryParameterTimeRegisterRDTO.getClass()));
		mockMvc.perform(get("/sync/execucio/fitxatge/instantani/contracta/" + queryParameterTimeRegisterRDTO.getCodeContract()
				+ "?data=" + queryParameterTimeRegisterRDTO.getDate() + "&codiRellotge="
				+ queryParameterTimeRegisterRDTO.getCodeWatch() + "&codiTreballador="
				+ queryParameterTimeRegisterRDTO.getCodeEmployee() + "&transactionId=" + queryParameterTimeRegisterRDTO.getTransactionId()))
				.andDo(print()).andExpect(status().isInternalServerError());
	}

	@Test
	public void caseOkSelectTimeRegisterSummary() throws Exception {
		Mockito.when(timeClockService.selectTimeRegisterSummary(Mockito.any(queryParameterTimeRegisterRDTO.getClass())))
				.thenReturn(returnTimeRegisterSummaryRDTO);
		mockMvc.perform(get("/sync/execucio/fitxatge/resum/contracta/" + queryParameterTimeRegisterRDTO.getCodeContract()
		+ "?data=" + queryParameterTimeRegisterRDTO.getDate() + "&codiInstallacio="
		+ queryParameterTimeRegisterRDTO.getCodeInstallation() + "&transactionId=" + queryParameterTimeRegisterRDTO.getTransactionId()))
				.andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void caseKoSelectTimeRegisterSummary() throws Exception {
		Mockito.doThrow(ImiException.class).when(timeClockService)
				.selectTimeRegisterSummary(Mockito.any(queryParameterTimeRegisterRDTO.getClass()));
		mockMvc.perform(get("/sync/execucio/fitxatge/resum/contracta/" + queryParameterTimeRegisterRDTO.getCodeContract()
				+ "?data=" + queryParameterTimeRegisterRDTO.getDate() + "&codiInstallacio="
				+ queryParameterTimeRegisterRDTO.getCodeInstallation() + "&transactionId=" + queryParameterTimeRegisterRDTO.getTransactionId()))
				.andDo(print()).andExpect(status().isInternalServerError());
	}

	@Test
	public void caseOkSelectEventDetailed() throws Exception {
		Mockito.when(eventService.selectDetailed(Mockito.any(queryParameterEventRDTO.getClass())))
				.thenReturn(returnEventDetailedRDTO);
		mockMvc.perform(get("/sync/execucio/esdeveniment/" + queryParameterEventRDTO.getCode() + "/contracta/"
				+ queryParameterEventRDTO.getCodeContract() + "/detall?codiUsuari=" + queryParameterEventRDTO.getCodeUser()
				+ "&transactionId=" + queryParameterEventRDTO.getTransactionId())).andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void caseKoSelectEventDetailed() throws Exception {
		Mockito.doThrow(ImiException.class).when(eventService)
				.selectDetailed(Mockito.any(queryParameterEventRDTO.getClass()));
		mockMvc.perform(get("/sync/execucio/esdeveniment/" + queryParameterEventRDTO.getCode() + "/contracta/"
				+ queryParameterEventRDTO.getCodeContract() + "/detall?codiUsuari=" + queryParameterEventRDTO.getCodeUser()
				+ "&transactionId=" + queryParameterEventRDTO.getTransactionId())).andDo(print())
				.andExpect(status().isInternalServerError());
	}

	@Test
	public void caseOkSelectEventApproved() throws Exception {
		Mockito.when(eventService.selectApproved(Mockito.any(queryParameterEventRDTO.getClass())))
				.thenReturn(returnEventApprovedRDTO);
		mockMvc.perform(get("/sync/execucio/esdeveniment/contracta/"
				+ queryParameterEventRDTO.getCodeContract() + "/aprovats?codiUsuari=" + queryParameterEventRDTO.getCodeUser()
				+ "&dataExecucio=" + queryParameterEventRDTO.getDateExecution()
				+ "&transactionId=" + queryParameterEventRDTO.getTransactionId())).andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void caseKoSelectEventApproved() throws Exception {
		Mockito.doThrow(ImiException.class).when(eventService)
				.selectApproved(Mockito.any(queryParameterEventRDTO.getClass()));
		mockMvc.perform(get("/sync/execucio/esdeveniment/contracta/"
				+ queryParameterEventRDTO.getCodeContract() + "/aprovats?codiUsuari=" + queryParameterEventRDTO.getCodeUser()
				+ "&dataExecucio=" + queryParameterEventRDTO.getDateExecution()
				+ "&transactionId=" + queryParameterEventRDTO.getTransactionId())).andDo(print())
				.andExpect(status().isInternalServerError());
	}

	@Test
	public void caseOkSelectEventRejected() throws Exception {
		Mockito.when(eventService.selectRejected(Mockito.any(queryParameterEventRDTO.getClass())))
				.thenReturn(returnEventRejectedRDTO);
		mockMvc.perform(get("/sync/execucio/esdeveniment/contracta/"
				+ queryParameterEventRDTO.getCodeContract() + "/rebutjats?codiUsuari=" + queryParameterEventRDTO.getCodeUser()
				+ "&dataExecucio=" + queryParameterEventRDTO.getDateExecution()
				+ "&transactionId=" + queryParameterEventRDTO.getTransactionId())).andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void caseKoSelectEventRejected() throws Exception {
		Mockito.doThrow(ImiException.class).when(eventService)
				.selectRejected(Mockito.any(queryParameterEventRDTO.getClass()));
		mockMvc.perform(get("/sync/execucio/esdeveniment/contracta/"
				+ queryParameterEventRDTO.getCodeContract() + "/rebutjats?codiUsuari=" + queryParameterEventRDTO.getCodeUser()
				+ "&dataExecucio=" + queryParameterEventRDTO.getDateExecution()
				+ "&transactionId=" + queryParameterEventRDTO.getTransactionId())).andDo(print())
				.andExpect(status().isInternalServerError());
	}

	@Test
	public void caseOkSelectEventPending() throws Exception {
		Mockito.when(eventService.selectPending(Mockito.any(queryParameterEventRDTO.getClass())))
				.thenReturn(returnEventPendingRDTO);
		mockMvc.perform(get("/sync/execucio/esdeveniment/contracta/"
				+ queryParameterEventRDTO.getCodeContract() + "/pendents?codiUsuari=" + queryParameterEventRDTO.getCodeUser()
				+ "&dataInici=" + queryParameterEventRDTO.getDateStart()
				+ "&dataFi=" + queryParameterEventRDTO.getDateEnd()
				+ "&transactionId=" + queryParameterEventRDTO.getTransactionId())).andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void caseKoSelectEventPending() throws Exception {
		Mockito.doThrow(ImiException.class).when(eventService)
				.selectPending(Mockito.any(queryParameterEventRDTO.getClass()));
		mockMvc.perform(get("/sync/execucio/esdeveniment/contracta/"
				+ queryParameterEventRDTO.getCodeContract() + "/pendents?codiUsuari=" + queryParameterEventRDTO.getCodeUser()
				+ "&dataInici=" + queryParameterEventRDTO.getDateStart()
				+ "&dataFi=" + queryParameterEventRDTO.getDateEnd()
				+ "&transactionId=" + queryParameterEventRDTO.getTransactionId())).andDo(print())
				.andExpect(status().isInternalServerError());
	}

}
