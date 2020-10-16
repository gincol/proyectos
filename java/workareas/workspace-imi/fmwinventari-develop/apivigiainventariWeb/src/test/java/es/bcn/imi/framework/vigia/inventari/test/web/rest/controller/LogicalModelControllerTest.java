package es.bcn.imi.framework.vigia.inventari.test.web.rest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

import es.bcn.imi.framework.vigia.inventari.business.LogicalModelService;
import es.bcn.imi.framework.vigia.inventari.test.parent.RestServerParentTest;
import es.bcn.imi.framework.vigia.inventari.web.rest.controller.LogicalModelController;
import es.bcn.vigia.fmw.libcommons.business.dto.LogicalModelBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.QueryParameterBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.stub.LogicalModelBDTOStub;
import es.bcn.vigia.fmw.libcommons.business.dto.stub.QueryParameterBDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.LogicalModelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnLogicalModelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnLogicalModelDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnLogicalModelMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnLogicalModelRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnMclDetailedRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnMclMassiveRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.LogicalModelRDTOStub;

public class LogicalModelControllerTest extends RestServerParentTest {

	private MockMvc mockMvc;

	@Mock
	private LogicalModelService service;

	@InjectMocks
	private LogicalModelController controller;

	private ReturnRDTO returnRDTO;

	private ReturnLogicalModelRDTO returnLogicalModelRDTO;
	
	private ReturnLogicalModelMassiveRDTO returnLogicalModelMassiveRDTO;
	
	private ReturnLogicalModelDetailedRDTO returnLogicalModelDetailedRDTO;

	private LogicalModelBDTO logicalModelBDTO;

	private LogicalModelRDTO logicalModelRDTO;
	
	private QueryParameterBDTO queryParameterBDTO;


	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		returnRDTO = ReturnRDTOStub.getSuccessMessage();
		returnLogicalModelRDTO = ReturnLogicalModelRDTOStub.getSuccessMessage();
		returnLogicalModelMassiveRDTO = ReturnMclMassiveRDTOStub.getSuccessMessage();
		returnLogicalModelDetailedRDTO = ReturnMclDetailedRDTOStub.getSuccessMessage();
		logicalModelBDTO = LogicalModelBDTOStub.defaultOne();
		logicalModelRDTO = LogicalModelRDTOStub.defaultOne();
        queryParameterBDTO = QueryParameterBDTOStub.defaultOne();
	}

	@Test
	public void caseOkSelect() throws Exception {
		Mockito.when(service.select(logicalModelBDTO)).thenReturn(returnLogicalModelRDTO);
		mockMvc.perform(get("/mobiliarilogic/" + logicalModelRDTO.getCode())).andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void caseKoSelectWrongInput() throws Exception {
		mockMvc.perform(get("/mobiliarilogicError/" + logicalModelRDTO.getCode())).andDo(print())
				.andExpect(status().isNotFound());
	}
	
	@Test
    public void caseOkInsert() throws Exception{
    	Mockito.when(service.insert(logicalModelBDTO)).thenReturn(returnRDTO);
    	
    	mockMvc.perform(post("/mobiliarilogic")
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{\r\n" + 
        		"	\"codi\":\""+logicalModelRDTO.getCode()+"\",\r\n" + 
        		"	\"motiuCanvi\": \"creacio de nou mcl\",\r\n" + 
        		"	\"usuariInsercio\": \"tgutierrez\",\r\n" + 
        		"	\"codiTipusFraccio\":\"FO\",\r\n" + 
        		"	\"codiTipus\":\"C01\",\r\n" + 
        		"	\"coordenadaX\":20000,\r\n" + 
        		"	\"coordenadaY\":20005,\r\n" + 
        		"	\"ubicacio\":{\r\n" + 
        		"		\"codi\":\"A\",\r\n" + 
        		"		\"posicio\":1\r\n" + 
        		"	}\r\n" + 
        		"}"))
    	.andDo(print()).andExpect(status().isOk());
    }
	
	@Test
    public void caseOkUpdate() throws Exception{
    	Mockito.when(service.update(logicalModelBDTO)).thenReturn(returnRDTO);
    	
    	mockMvc.perform(put("/mobiliarilogic/"+logicalModelBDTO.getCode())
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{\r\n" + 
        		"	\"motiuCanvi\": \"update mcl\",\r\n" + 
        		"	\"usuariActualitzacio\": \"tgutierrez\",\r\n" + 
        		"	\"codiTipusFraccio\":\"FO\",\r\n" + 
        		"	\"codiTipus\":\"C01\",\r\n" + 
        		"	\"coordenadaX\":20000,\r\n" + 
        		"	\"coordenadaY\":20005,\r\n" + 
        		"	\"ubicacio\":{\r\n" + 
        		"		\"codi\":\"A\",\r\n" + 
        		"		\"posicio\":1\r\n" + 
        		"	},\r\n" + 
        		"	\"mobiliariFisics\":[\r\n" + 
        		"		{\r\n" + 
        		"		\"codi\":\"FC02RE006511\"\r\n" + 
        		"		}\r\n" + 
        		"	]\r\n" + 
        		"}"))
    	.andDo(print()).andExpect(status().isOk());
    }
	
	@Test
    public void caseOkDelete() throws Exception{
    	Mockito.when(service.delete(logicalModelBDTO)).thenReturn(returnRDTO);
    	
    	mockMvc.perform(delete("/mobiliarilogic/"+logicalModelRDTO.getCode())
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{\r\n" + 
        		"	\"motiuCanvi\": \"delete mcl\",\r\n" + 
        		"	\"usuariEliminacio\": \"tgutierrez\"\r\n" + 
        		"}"))
    	.andDo(print()).andExpect(status().isOk());
    }
	
	@Test
    public void caseOkSelectMassive() throws Exception{
    	Mockito.when(service.selectMassive(queryParameterBDTO)).thenReturn(returnLogicalModelMassiveRDTO);
    	
    	mockMvc.perform(get("/mobiliarilogic/contracta/"+queryParameterBDTO.getCodeContract()+"?dataReferencia="+queryParameterBDTO.getDateReference()+"&codiUsuari="+queryParameterBDTO.getCodeUser()+"&transactionId=asfasf-asdf"))
    	    	.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
    public void caseOkSelectDetailed() throws Exception{
    	Mockito.when(service.selectDetailed(queryParameterBDTO)).thenReturn(returnLogicalModelDetailedRDTO);
    	
    	mockMvc.perform(get("/mobiliarilogic/"+queryParameterBDTO.getCode()+"/contracta/"+queryParameterBDTO.getCodeContract()+"/detall?dataReferencia="+queryParameterBDTO.getDateReference()+"&codiUsuari="+queryParameterBDTO.getCodeUser()+"&transactionId=asfasf-asdf"))
    	    	.andDo(print()).andExpect(status().isOk());
	}

}
