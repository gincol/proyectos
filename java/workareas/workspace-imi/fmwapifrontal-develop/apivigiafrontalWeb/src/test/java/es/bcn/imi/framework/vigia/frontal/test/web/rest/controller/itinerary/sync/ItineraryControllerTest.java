package es.bcn.imi.framework.vigia.frontal.test.web.rest.controller.itinerary.sync;

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

import es.bcn.imi.framework.vigia.frontal.business.itinerary.ItineraryService;
import es.bcn.imi.framework.vigia.frontal.business.itinerary.PlanningService;
import es.bcn.imi.framework.vigia.frontal.web.rest.controller.itinerary.sync.ItineraryController;
import es.bcn.imi.framework.vigia.test.parent.RestServerParentTest;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
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
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.PlanningRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.QueryParameterPlanningRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.QueryParameterRDTOStub;
import es.bcn.vigia.fmw.libutils.exception.ImiException;


public class ItineraryControllerTest extends RestServerParentTest {

	private MockMvc mockMvc;

	@Mock
	private PlanningService planningService;

	@Mock
	private ItineraryService itineraryService;
	
	@InjectMocks
	private ItineraryController controller;

	private ReturnRDTO returnRDTO;
	
	private ReturnRDTO returnRDTOKO;
		
	private PlanningRDTO planningRDTO;
	
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
		returnRDTOKO = new ReturnRDTO();
		returnRDTOKO.setCode("KO");
		
		planningRDTO = PlanningRDTOStub.defaultOne();
		queryParameterPlanningRDTO = QueryParameterPlanningRDTOStub.defaultOne();
		queryParameterRDTO = QueryParameterRDTOStub.defaultOne();
		returnPlanningMassiveRDTO = ReturnPlanningMassiveRDTOStub.getSuccessMessage();
		returnPlanningDetailedRDTO = ReturnPlanningDetailedRDTOStub.getSuccessMessage();
		returnItineraryMassiveRDTO = ReturnItineraryMassiveRDTOStub.getSuccessMessage();
		returnItineraryDetailedRDTO = ReturnItineraryDetailedRDTOStub.getSuccessMessage();
	}

	@Test
	public void caseOkSavePlanning() throws Exception {

		Mockito.when(planningService.insert(Mockito.any(planningRDTO.getClass()))).thenReturn(returnRDTO);

		mockMvc.perform(post("/sync/itinerari/planificacio").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + planningRDTO.getCodeItinerary() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void caseKOSavePlanningBadRequest() throws Exception {

		Mockito.when(planningService.insert(Mockito.any(planningRDTO.getClass()))).thenReturn(returnRDTOKO);

		mockMvc.perform(post("/sync/itinerari/planificacio").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + planningRDTO.getCodeItinerary() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}
	@Test
	public void caseKoSavePlanningException() throws Exception {

		Mockito.when(planningService.insert(planningRDTO)).thenReturn(returnRDTO);
		mockMvc.perform(post("/sync/itinerari/planificacio").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + planningRDTO.getCodeItinerary() + "\"\r\n" + "}"))
				.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseOkSelectPlanningMassive() throws Exception{
		
		Mockito.when(planningService.selectMassive(Mockito.any(queryParameterPlanningRDTO.getClass()))).thenReturn(returnPlanningMassiveRDTO);
    	mockMvc.perform(get("/sync/itinerari/contracta/"+queryParameterPlanningRDTO.getCodeContract()+"/planificacio/any/"+queryParameterPlanningRDTO.getYear()+"?codiUsuari="+queryParameterPlanningRDTO.getCodeUser())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void caseKoSelectPlanningMassive() throws Exception{
		
		Mockito.doThrow(ImiException.class).when(planningService).selectMassive(Mockito.any(queryParameterPlanningRDTO.getClass()));
    	
    	mockMvc.perform(get("/sync/itinerari/contracta/"+queryParameterPlanningRDTO.getCodeContract()+"/planificacio/any/"+queryParameterPlanningRDTO.getYear()+"?codiUsuari="+queryParameterPlanningRDTO.getCodeUser())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseOkSelectPlanningDetailed() throws Exception{
		
		Mockito.when(planningService.selectDetailed(Mockito.any(queryParameterPlanningRDTO.getClass()))).thenReturn(returnPlanningDetailedRDTO);
    	mockMvc.perform(get("/sync/itinerari/contracta/"+queryParameterPlanningRDTO.getCodeContract()+"/planificacio/"+queryParameterPlanningRDTO.getCode()+"/any/"+queryParameterPlanningRDTO.getYear()+"/detall?codiUsuari="+queryParameterPlanningRDTO.getCodeUser()+"&dataReferencia="+queryParameterPlanningRDTO.getDateReference())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void caseKoSelectPlanningDetailed() throws Exception{
		
		Mockito.doThrow(ImiException.class).when(planningService).selectDetailed(Mockito.any(queryParameterPlanningRDTO.getClass()));
    	
    	mockMvc.perform(get("/sync/itinerari/contracta/"+queryParameterPlanningRDTO.getCodeContract()+"/planificacio/"+queryParameterPlanningRDTO.getCode()+"/any/"+queryParameterPlanningRDTO.getYear()+"/detall?codiUsuari="+queryParameterPlanningRDTO.getCodeUser()+"&dataReferencia="+queryParameterPlanningRDTO.getDateReference())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseOkSelectItineraryMassive() throws Exception{
		
		Mockito.when(itineraryService.selectMassive(Mockito.any(queryParameterRDTO.getClass()))).thenReturn(returnItineraryMassiveRDTO);
    	mockMvc.perform(get("/sync/itinerari/contracta/"+queryParameterRDTO.getCodeContract()+"?codiUsuari="+queryParameterRDTO.getCodeUser()+"&dataReferencia="+queryParameterRDTO.getDateReference())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void caseKoSelectItineraryMassive() throws Exception{
		
		Mockito.doThrow(ImiException.class).when(itineraryService).selectMassive(Mockito.any(queryParameterRDTO.getClass()));
    	
    	mockMvc.perform(get("/sync/itinerari/contracta/"+queryParameterRDTO.getCodeContract()+"?codiUsuari="+queryParameterRDTO.getCodeUser()+"&dataReferencia="+queryParameterRDTO.getDateReference())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseOkSelectItineraryDetailed() throws Exception{
		
		Mockito.when(itineraryService.selectDetailed(Mockito.any(queryParameterRDTO.getClass()))).thenReturn(returnItineraryDetailedRDTO);
    	mockMvc.perform(get("/sync/itinerari/contracta/"+queryParameterRDTO.getCodeContract()+"/itinerari/"+queryParameterRDTO.getCode()+"/detall?codiUsuari="+queryParameterRDTO.getCodeUser()+"&dataReferencia="+queryParameterRDTO.getDateReference())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void caseKoSelectItineraryDetailed() throws Exception{
		
		Mockito.doThrow(ImiException.class).when(itineraryService).selectDetailed(Mockito.any(queryParameterRDTO.getClass()));
    	
    	mockMvc.perform(get("/sync/itinerari/contracta/"+queryParameterRDTO.getCodeContract()+"/itinerari/"+queryParameterRDTO.getCode()+"/detall?codiUsuari="+queryParameterRDTO.getCodeUser()+"&dataReferencia="+queryParameterRDTO.getDateReference())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	
}
	