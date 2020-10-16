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

import es.bcn.imi.framework.vigia.inventari.business.CommerceService;
import es.bcn.imi.framework.vigia.inventari.test.parent.RestServerParentTest;
import es.bcn.imi.framework.vigia.inventari.web.rest.controller.CommerceController;
import es.bcn.vigia.fmw.libcommons.business.dto.CommerceBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.QueryParameterBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.stub.CommerceBDTOStub;
import es.bcn.vigia.fmw.libcommons.business.dto.stub.QueryParameterBDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CommerceRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnCommerceDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnCommerceElementsRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnCommerceDetailedRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnCommerceElementsRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnMassiveRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.CommerceRDTOStub;

public class CommerceControllerTest extends RestServerParentTest{
	
	private MockMvc mockMvc;
	
	@Mock
	private CommerceService service;
	
	@InjectMocks
	private CommerceController controller;

	private ReturnRDTO returnRDTO;
	
	private ReturnMassiveRDTO returnMassiveRDTO;
	
	private ReturnCommerceDetailedRDTO returnCommerceDetailedRDTO;
	
	private ReturnCommerceElementsRDTO returnCommerceElementsRDTO;
	
	private CommerceBDTO comercBDTO;
	
	private CommerceRDTO comercRDTO;
	
	private QueryParameterBDTO queryParameterBDTO;
	
	@Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        returnRDTO = ReturnRDTOStub.getSuccessMessage();
        comercBDTO = CommerceBDTOStub.defaultOne();
        comercRDTO = CommerceRDTOStub.defaultOne();
        queryParameterBDTO = QueryParameterBDTOStub.defaultOne();
        returnMassiveRDTO = ReturnMassiveRDTOStub.getSuccessMessage();
        returnCommerceDetailedRDTO = ReturnCommerceDetailedRDTOStub.getSuccessMessage();
        returnCommerceElementsRDTO = ReturnCommerceElementsRDTOStub.getSuccessMessage();
    }

	@Test
    public void caseOkInsert() throws Exception{
    	Mockito.when(service.insert(comercBDTO)).thenReturn(returnRDTO);
    	 
    	mockMvc.perform(post("/comerc")
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{\r\n" + 
        		"   \"codi\":\"9001\",\r\n" + 
        		"   \"codiContracta\":\"C\",\r\n" + 
        		"   \"cif\":\"D4459000\",\r\n" + 
        		"   \"estat\":\"A\",\r\n" + 
        		"   \"coordinadaX\":\"1000\",\r\n" + 
        		"   \"coordinadaY\":\"1000\",\r\n" + 
        		"   \"raoSocial\":\"FRAMEWORK VIGIA\",\r\n" + 
        		"   \"expedient\":\"123\",\r\n" + 
        		"   \"zonaComercial\":false,\r\n" + 
        		"   \"granProductor\":false,\r\n" + 
        		"   \"localitzacio\":{\r\n" + 
        		"      \"codiBarri\":\"01\",\r\n" + 
        		"      \"codiPostal\":\"08040\",\r\n" + 
        		"      \"codiTerritori\":\"1\",\r\n" + 
        		"      \"codiTipusVia\":\"C\",\r\n" + 
        		"      \"nomVia\":\"WEBSERVICE UNIT TEST\",\r\n" + 
        		"      \"numeroInici\":\"16\",\r\n" + 
        		"      \"numeroFi\":\"22\"\r\n" + 
        		"   }\r\n" + 
        		"}"))
    	.andDo(print()).andExpect(status().isOk());
    }
	
	@Test
    public void caseOkUpdate() throws Exception{
    	Mockito.when(service.update(comercBDTO)).thenReturn(returnRDTO);
    	
    	mockMvc.perform(put("/comerc/"+ comercRDTO.getCode())
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{\r\n" + 
        		"   \"codiContracta\":\"C\",\r\n" + 
        		"   \"cif\":\"D4459000\",\r\n" + 
        		"   \"estat\":\"A\",\r\n" + 
        		"   \"coordinadaX\":\"1002\",\r\n" + 
        		"   \"coordinadaY\":\"1003\",\r\n" + 
        		"   \"raoSocial\":\"FRAMEWORK VIGIA\",\r\n" + 
        		"   \"expedient\":\"123\",\r\n" + 
        		"   \"zonaComercial\":false,\r\n" + 
        		"   \"granProductor\":false,\r\n" + 
        		"   \"localitzacio\":{\r\n" + 
        		"      \"codiBarri\":\"01\",\r\n" + 
        		"      \"codiPostal\":\"08040\",\r\n" + 
        		"      \"codiTerritori\":\"1\",\r\n" + 
        		"      \"codiTipusVia\":\"C\",\r\n" + 
        		"      \"nomVia\":\"WEBSERVICE UNIT TEST\",\r\n" + 
        		"      \"numeroInici\":\"16\",\r\n" + 
        		"      \"numeroFi\":\"22\"\r\n" + 
        		"   }\r\n" + 
        		"}"))
    	.andDo(print()).andExpect(status().isOk());
    }
	
	@Test
    public void caseOkDelete() throws Exception{
    	Mockito.when(service.delete(comercBDTO)).thenReturn(returnRDTO);
    	
    	mockMvc.perform(delete("/comerc/"+ comercRDTO.getCode())
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{\r\n" + 
        		"   \"codi\":\"9001\",\r\n" + 
        		"   \"codiContracta\":\"C\",\r\n" + 
        		"   \"cif\":\"D4459000\",\r\n" + 
        		"   \"estat\":\"B\",\r\n" + 
        		"   \"coordinadaX\":\"1000\",\r\n" + 
        		"   \"coordinadaY\":\"1000\",\r\n" + 
        		"   \"raoSocial\":\"FRAMEWORK VIGIA\",\r\n" + 
        		"   \"expedient\":\"123\",\r\n" + 
        		"   \"zonaComercial\":false,\r\n" + 
        		"   \"granProductor\":false,\r\n" + 
        		"   \"localitzacio\":{\r\n" + 
        		"      \"codiBarri\":\"01\",\r\n" + 
        		"      \"codiPostal\":\"08040\",\r\n" + 
        		"      \"codiTerritori\":\"1\",\r\n" + 
        		"      \"codiTipusVia\":\"C\",\r\n" + 
        		"      \"nomVia\":\"WEBSERVICE UNIT TEST\",\r\n" + 
        		"      \"numeroInici\":\"16\",\r\n" + 
        		"      \"numeroFi\":\"22\"\r\n" + 
        		"   }\r\n" + 
        		"}"))
    	.andDo(print()).andExpect(status().isOk());
    }
	
	@Test
    public void caseOkInsertElements() throws Exception{
    	Mockito.when(service.insertElements(comercBDTO)).thenReturn(returnRDTO);
    	 
    	mockMvc.perform(post("/comerc/elements")
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{\r\n" + 
        		"   \"codi\":\"9001\",\r\n" + 
        		"   \"codiContracta\":\"C\",\r\n" + 
        		"   \"cif\":\"D4459000\",\r\n" + 
        		"   \"estat\":\"A\",\r\n" + 
        		"   \"coordinadaX\":\"1000\",\r\n" + 
        		"   \"coordinadaY\":\"1000\",\r\n" + 
        		"   \"raoSocial\":\"FRAMEWORK VIGIA\",\r\n" + 
        		"   \"expedient\":\"123\",\r\n" + 
        		"   \"zonaComercial\":false,\r\n" + 
        		"   \"granProductor\":false,\r\n" + 
        		"   \"localitzacio\":{\r\n" + 
        		"      \"codiBarri\":\"01\",\r\n" + 
        		"      \"codiPostal\":\"08040\",\r\n" + 
        		"      \"codiTerritori\":\"1\",\r\n" + 
        		"      \"codiTipusVia\":\"C\",\r\n" + 
        		"      \"nomVia\":\"WEBSERVICE UNIT TEST\",\r\n" + 
        		"      \"numeroInici\":\"16\",\r\n" + 
        		"      \"numeroFi\":\"22\"\r\n" + 
        		"   }\r\n" + 
        		"}"))
    	.andDo(print()).andExpect(status().isOk());
    }
	
	@Test
    public void caseOkSelectMassive() throws Exception{
    	Mockito.when(service.selectMassive(queryParameterBDTO)).thenReturn(returnMassiveRDTO);
    	
    	mockMvc.perform(get("/comerc/contracta/"+queryParameterBDTO.getCodeContract()+"?dataReferencia="+queryParameterBDTO.getDateReference()+"&codiUsuari="+queryParameterBDTO.getCodeUser()+"&transactionId=asfasf-asdf"))
    	    	.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
    public void caseOkSelectDetailed() throws Exception{
    	Mockito.when(service.selectDetailed(queryParameterBDTO)).thenReturn(returnCommerceDetailedRDTO);
    	
    	mockMvc.perform(get("/comerc/"+queryParameterBDTO.getCode()+"/contracta/"+queryParameterBDTO.getCodeContract()+"/detall?dataReferencia="+queryParameterBDTO.getDateReference()+"&codiUsuari="+queryParameterBDTO.getCodeUser()+"&transactionId=asfasf-asdf"))
    	    	.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
    public void caseOkSelectElements() throws Exception{
		Mockito.when(service.selectElements(queryParameterBDTO)).thenReturn(returnCommerceElementsRDTO);
    	
    	mockMvc.perform(get("/comerc/"+queryParameterBDTO.getCode()+"/contracta/"+queryParameterBDTO.getCodeContract()+"/elements?dataReferencia="+queryParameterBDTO.getDateReference()+"&codiUsuari="+queryParameterBDTO.getCodeUser()+"&transactionId=asfasf-asdf"))
    	    	.andDo(print()).andExpect(status().isOk());
	}
}
