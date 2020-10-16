	package es.bcn.imi.framework.vigia.orquestrador.test.web.rest.controller.async;

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
import es.bcn.imi.framework.vigia.orquestrador.web.rest.controller.async.ItineraryAsyncController;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.ItineraryRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnRDTOStub;

public class ItineraryControllerTest extends RestServerParentTest {

	private MockMvc mockMvc;

	@Mock
	private  PlanningService planningService;

	@Mock
	private  ItineraryService itineraryService;

	@InjectMocks
	private ItineraryAsyncController controller;
	
	private ReturnRDTO returnRDTO;
	
	private ItineraryRDTO itineraryRDTO;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		returnRDTO = ReturnRDTOStub.getSuccessMessage();	
		itineraryRDTO = new ItineraryRDTO();
	}
	
	@Test
	public void caseOkitineraryAsync() throws Exception {
		
		
		
		Mockito.when(itineraryService.insert(Mockito.any(itineraryRDTO.getClass()))).thenReturn(returnRDTO);

		mockMvc.perform(post("/async/itinerari").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\r\n" + 
						"   \"codiContracta\":\"C\",\r\n" + 
						"   \"codiItinerari\":\"1CNNBEMAEM6A1TH006M\",\r\n" + 
						"   \"usuariInsercio\":\"JROJAST\"\r\n" + 
						"}"))
					.andDo(print()).andExpect(status().isOk());

	}
	
	}
