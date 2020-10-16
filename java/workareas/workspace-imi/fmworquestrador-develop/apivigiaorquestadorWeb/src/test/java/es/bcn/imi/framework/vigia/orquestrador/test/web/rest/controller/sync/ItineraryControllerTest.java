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

import es.bcn.imi.framework.vigia.orquestrador.business.itinerary.ItineraryService;
import es.bcn.imi.framework.vigia.orquestrador.business.itinerary.PlanningService;
import es.bcn.imi.framework.vigia.orquestrador.test.parent.RestServerParentTest;
import es.bcn.imi.framework.vigia.orquestrador.web.rest.controller.sync.ItineraryController;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.PlanningRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterPlanningRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnItineraryDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnItineraryMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnPlanningDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnPlanningMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnItineraryDetailedRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnItineraryMassiveRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnPlanningDetailedRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnPlanningMassiveRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.QueryParameterPlanningRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.QueryParameterRDTOStub;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public class ItineraryControllerTest extends RestServerParentTest {

	private MockMvc mockMvc;

	@Mock
	private  PlanningService planningService;

	@Mock
	private  ItineraryService itineraryService;

	@InjectMocks
	private ItineraryController controller;
	
	private ReturnRDTO returnRDTO;
	
	private QueryParameterPlanningRDTO queryParameterPlanningRDTO;
	
	private QueryParameterRDTO queryParameterRDTO;
	
	private ReturnPlanningMassiveRDTO returnPlanningMassiveRDTO;
	
	private ReturnPlanningDetailedRDTO returnPlanningDetailedRDTO;
	
	private ReturnItineraryMassiveRDTO returnItineraryMassiveRDTO;
	
	private ReturnItineraryDetailedRDTO returnItineraryDetailedRDTO;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		returnRDTO = ReturnRDTOStub.getSuccessMessage();	
		queryParameterPlanningRDTO = QueryParameterPlanningRDTOStub.defaultOne();
		queryParameterRDTO = QueryParameterRDTOStub.defaultOne();
		returnPlanningMassiveRDTO = ReturnPlanningMassiveRDTOStub.getSuccessMessage();
		returnPlanningDetailedRDTO = ReturnPlanningDetailedRDTOStub.getSuccessMessage();
		returnItineraryMassiveRDTO = ReturnItineraryMassiveRDTOStub.getSuccessMessage();
		returnItineraryDetailedRDTO = ReturnItineraryDetailedRDTOStub.getSuccessMessage();
	}
	
	@Test
	public void caseOkPlanningSync() throws Exception {
		
		PlanningRDTO planningRDTO = new PlanningRDTO();
		
		Mockito.when(planningService.insert(planningRDTO)).thenReturn(returnRDTO);

		mockMvc.perform(post("/sync/itinerari/planificacio").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\r\n" + 
						"   \"codiContracta\":\"C\",\r\n" + 
						"   \"codiItinerari\":\"1CNNBEMAEM6A1TH006M\",\r\n" + 
						"   \"any\":\"2020\",\r\n" + 
						"   \"etiqueta\":[\r\n" + 
						"      {\r\n" + 
						"         \"dataInici\":\"PLANIFICACIO-ETIQUETA2\",\r\n" + 
						"         \"valor\":\"60\"\r\n" + 
						"      },\r\n" + 
						"      {\r\n" + 
						"         \"dataInici\":\"PLANIFICACIO-ETIQUETA4\",\r\n" + 
						"         \"valor\":\"40\"\r\n" + 
						"      }\r\n" + 
						"   ],\r\n" + 
						"   \"calendari\":{\r\n" + 
						"      \"datesExecucio\":[\r\n" + 
						"         {\r\n" + 
						"            \"dataExecucio\":\"12/12/2021\"\r\n" + 
						"         },\r\n" + 
						"         {\r\n" + 
						"            \"dataExecucio\":\"13/12/2021\"\r\n" + 
						"         },\r\n" + 
						"         {\r\n" + 
						"            \"dataExecucio\":\"14/12/2021\"\r\n" + 
						"         }\r\n" + 
						"      ]\r\n" + 
						"   },\r\n" + 
						"   \"usuariInsercio\":\"JROJAST\"\r\n" + 
						"}"))
				.andDo(print()).andExpect(status().isOk());

	}
	
	@Test
	public void caseKoPlanningSyncInsert() throws Exception {

		Mockito.doThrow(ImiException.class).when(planningService).insert(Mockito.any(PlanningRDTO.class));

		mockMvc.perform(post("/sync/itinerari/planificacio").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\r\n" + 
						"   \"codiContracta\":\"C\",\r\n" + 
						"   \"codiItinerari\":\"1CNNBEMAEM6A1TH006M\",\r\n" + 
						"   \"any\":\"2020\",\r\n" + 
						"   \"etiqueta\":[\r\n" + 
						"      {\r\n" + 
						"         \"dataInici\":\"PLANIFICACIO-ETIQUETA2\",\r\n" + 
						"         \"valor\":\"60\"\r\n" + 
						"      },\r\n" + 
						"      {\r\n" + 
						"         \"dataInici\":\"PLANIFICACIO-ETIQUETA4\",\r\n" + 
						"         \"valor\":\"40\"\r\n" + 
						"      }\r\n" + 
						"   ],\r\n" + 
						"   \"calendari\":{\r\n" + 
						"      \"datesExecucio\":[\r\n" + 
						"         {\r\n" + 
						"            \"dataExecucio\":\"12/12/2021\"\r\n" + 
						"         },\r\n" + 
						"         {\r\n" + 
						"            \"dataExecucio\":\"13/12/2021\"\r\n" + 
						"         },\r\n" + 
						"         {\r\n" + 
						"            \"dataExecucio\":\"14/12/2021\"\r\n" + 
						"         }\r\n" + 
						"      ]\r\n" + 
						"   },\r\n" + 
						"   \"usuariInsercio\":\"JROJAST\"\r\n" + 
						"}"))
				.andDo(print()).andExpect(status().isInternalServerError());

	}
	
	@Test
	public void caseOkSelectPlanningMassive() throws Exception {
		Mockito.when(planningService.selectMassive(Mockito.any(queryParameterPlanningRDTO.getClass())))
				.thenReturn(returnPlanningMassiveRDTO);
		mockMvc.perform(get("/sync/itinerari/planificacio/contracta/"
				+ queryParameterPlanningRDTO.getCodeContract() + "/any/"+queryParameterPlanningRDTO.getYear()+"?codiUsuari=" + queryParameterPlanningRDTO.getCodeUser()
				+ "&transactionId=" + queryParameterPlanningRDTO.getTransactionId())).andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void caseKoSelectPlanningMassive() throws Exception {
		Mockito.doThrow(ImiException.class).when(planningService)
				.selectMassive(Mockito.any(queryParameterPlanningRDTO.getClass()));
		mockMvc.perform(get("/sync/itinerari/planificacio/contracta/"
				+ queryParameterPlanningRDTO.getCodeContract() + "/any/"+queryParameterPlanningRDTO.getYear()+"?codiUsuari=" + queryParameterPlanningRDTO.getCodeUser()
				+ "&transactionId=" + queryParameterPlanningRDTO.getTransactionId())).andDo(print())
				.andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseOkSelectPlanningDetailed() throws Exception {
		Mockito.when(planningService.selectDetailed(Mockito.any(queryParameterPlanningRDTO.getClass())))
				.thenReturn(returnPlanningDetailedRDTO);
		mockMvc.perform(get("/sync/itinerari/planificacio/" + queryParameterPlanningRDTO.getCode() + "/contracta/"
				+ queryParameterPlanningRDTO.getCodeContract() + "/any/"+queryParameterPlanningRDTO.getYear()+"/detall?codiUsuari=" + queryParameterPlanningRDTO.getCodeUser()
				+ "&dataReferencia=" + queryParameterPlanningRDTO.getDateReference()
				+ "&transactionId=" + queryParameterPlanningRDTO.getTransactionId())).andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void caseKoSelectPlanningDetailed() throws Exception {
		Mockito.doThrow(ImiException.class).when(planningService)
				.selectDetailed(Mockito.any(queryParameterPlanningRDTO.getClass()));
		mockMvc.perform(get("/sync/itinerari/planificacio/" + queryParameterPlanningRDTO.getCode() + "/contracta/"
				+ queryParameterPlanningRDTO.getCodeContract() + "/any/"+queryParameterPlanningRDTO.getYear()+"/detall?codiUsuari=" + queryParameterPlanningRDTO.getCodeUser()
				+ "&dataReferencia=" + queryParameterPlanningRDTO.getDateReference()
				+ "&transactionId=" + queryParameterPlanningRDTO.getTransactionId())).andDo(print())
				.andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseOkSelectItineraryMassive() throws Exception {
		Mockito.when(itineraryService.selectMassive(Mockito.any(queryParameterRDTO.getClass())))
				.thenReturn(returnItineraryMassiveRDTO);
		mockMvc.perform(get("/sync/itinerari/contracta/"+ queryParameterRDTO.getCodeContract()+"?codiUsuari=" + queryParameterRDTO.getCodeUser()
				+ "&dataReferencia=" + queryParameterRDTO.getDateReference()
				+ "&transactionId=" + queryParameterRDTO.getTransactionId())).andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void caseKoSelectItineraryMassive() throws Exception {
		Mockito.doThrow(ImiException.class).when(itineraryService)
				.selectMassive(Mockito.any(queryParameterRDTO.getClass()));
		mockMvc.perform(get("/sync/itinerari/contracta/"+ queryParameterRDTO.getCodeContract() + "?codiUsuari=" + queryParameterRDTO.getCodeUser()
				+ "&dataReferencia=" + queryParameterRDTO.getDateReference()
				+ "&transactionId=" + queryParameterRDTO.getTransactionId())).andDo(print())
				.andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseOkSelectItineraryDetailed() throws Exception {
		Mockito.when(itineraryService.selectDetailed(Mockito.any(queryParameterRDTO.getClass())))
				.thenReturn(returnItineraryDetailedRDTO);
		mockMvc.perform(get("/sync/itinerari/" + queryParameterRDTO.getCode() + "/contracta/"
				+ queryParameterRDTO.getCodeContract() + "/detall?codiUsuari=" + queryParameterRDTO.getCodeUser()
				+ "&dataReferencia=" + queryParameterRDTO.getDateReference()
				+ "&transactionId=" + queryParameterRDTO.getTransactionId())).andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void caseKoSelectItineraryDetailed() throws Exception {
		Mockito.doThrow(ImiException.class).when(itineraryService)
				.selectDetailed(Mockito.any(queryParameterRDTO.getClass()));
		mockMvc.perform(get("/sync/itinerari/" + queryParameterRDTO.getCode() + "/contracta/"
				+ queryParameterRDTO.getCodeContract() + "/detall?codiUsuari=" + queryParameterRDTO.getCodeUser()
				+ "&dataReferencia=" + queryParameterRDTO.getDateReference()
				+ "&transactionId=" + queryParameterRDTO.getTransactionId())).andDo(print())
				.andExpect(status().isInternalServerError());
	}

}
