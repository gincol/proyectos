package es.bcn.imi.framework.vigia.inventari.test.web.rest.controller;

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

import es.bcn.imi.framework.vigia.inventari.business.ActuationService;
import es.bcn.imi.framework.vigia.inventari.test.parent.RestServerParentTest;
import es.bcn.imi.framework.vigia.inventari.web.rest.controller.ActuationController;
import es.bcn.vigia.fmw.libcommons.business.dto.QueryParameterActuationBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.stub.QueryParameterActuationBDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnActuationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnActuationRDTOStub;
import es.bcn.vigia.fmw.libcommons.business.dto.ActuationBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.stub.ActuationBDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnRDTOStub;

public class ActuationControllerTest extends RestServerParentTest{
	
	private MockMvc mockMvc;
	
	@Mock
	private ActuationService service;
	
	@InjectMocks
	private ActuationController controller;
	
	private ReturnRDTO returnRDTO;
	
	private ReturnActuationRDTO returnActuationRDTO;
	
	private QueryParameterActuationBDTO queryParameterActuationBDTO;
	
	private ActuationBDTO actuationBDTO;
	@Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        returnRDTO = ReturnRDTOStub.getSuccessMessage();
        returnActuationRDTO = ReturnActuationRDTOStub.getSuccessMessage();
        queryParameterActuationBDTO = QueryParameterActuationBDTOStub.defaultOne();
        actuationBDTO = ActuationBDTOStub.defaultOne();
    }
	
	@Test
    public void caseOkSelectActuation() throws Exception{
    	Mockito.when(service.selectActuation(queryParameterActuationBDTO)).thenReturn(returnActuationRDTO);
    	
    	mockMvc.perform(get("/actuacio/contracta/"+queryParameterActuationBDTO.getCodeContract()+"/tipusEntitat/"+queryParameterActuationBDTO.getCodeTypeEntity()+"/codiEntitat/"+queryParameterActuationBDTO.getCodeEntity()+"?codiUsuari="+queryParameterActuationBDTO.getCodeUser()+"&transactionId=asfasf-asdf"))
    	    	.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
    public void caseOkInsert() throws Exception{
    	Mockito.when(service.insert(Mockito.any(ActuationBDTO.class))).thenReturn(returnRDTO);
    	 
    	mockMvc.perform(post("/actuacio")
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{\r\n" + 
        		"   \"codi\":\"9001\",\r\n" + 
        		"   \"codiContracta\":\"C\"\r\n" + 
        		    		"   }\r\n" + 
        		"}"))
    	.andDo(print()).andExpect(status().isOk());
    }
	
	@Test
    public void caseOkUpdate() throws Exception{
    	Mockito.when(service.delete(Mockito.any(ActuationBDTO.class))).thenReturn(returnRDTO);
    	
    	mockMvc.perform(put("/actuacio/"+ actuationBDTO.getCode() + "/entitat/" +  actuationBDTO.getCodeEntity() )
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{\r\n" + 
        		"   \"codiContracta\":\"C\"\r\n" + 
        		    		"   }\r\n" + 
        		"}"))
    	.andDo(print()).andExpect(status().isOk());
    }
	
	
}
