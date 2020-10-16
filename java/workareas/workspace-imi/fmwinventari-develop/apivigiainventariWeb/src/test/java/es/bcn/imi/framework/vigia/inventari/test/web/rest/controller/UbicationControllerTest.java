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

import es.bcn.imi.framework.vigia.inventari.business.UbicationService;
import es.bcn.imi.framework.vigia.inventari.test.parent.RestServerParentTest;
import es.bcn.imi.framework.vigia.inventari.web.rest.controller.UbicationController;
import es.bcn.vigia.fmw.libcommons.business.dto.QueryParameterBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.UbicationBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.stub.QueryParameterBDTOStub;
import es.bcn.vigia.fmw.libcommons.business.dto.stub.UbicationBDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.UbicationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnUbicationDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnUbicationDetailedRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.UbicationRDTOStub;

public class UbicationControllerTest extends RestServerParentTest{
	
	private MockMvc mockMvc;
	
	@Mock
	private UbicationService service;
	
	@InjectMocks
	private UbicationController controller;

	private ReturnRDTO returnRDTO;
	
	private ReturnMassiveRDTO returnMassiveRDTO;
	
	private ReturnUbicationDetailedRDTO returnUbicationDetailedRDTO;
	
	private UbicationBDTO ubicationBDTO;
	
	private UbicationRDTO ubicationRDTO;
	
	private QueryParameterBDTO queryParameterBDTO;
	
	@Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        returnRDTO = ReturnRDTOStub.getSuccessMessage();
        ubicationBDTO = UbicationBDTOStub.defaultOne();
        ubicationRDTO = UbicationRDTOStub.defaultOne();
        queryParameterBDTO = QueryParameterBDTOStub.defaultOne();
    	returnUbicationDetailedRDTO = ReturnUbicationDetailedRDTOStub.getSuccessMessage();
    }
	
	@Test
    public void caseOkInsert() throws Exception{
    	Mockito.when(service.insert(ubicationBDTO)).thenReturn(returnRDTO);
    	 
    	mockMvc.perform(post("/ubicacio")
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{\r\n" + 
        		"	\"codi\": \""+ ubicationRDTO.getCode() +"\",\r\n" + 
        		"	\"codiContracta\":\""+ ubicationRDTO.getCodeContract() +"\",\r\n" + 
        		"	\"tipus\":\"PN\",\r\n" + 
        		"	\"tipusReferencia\":\"D\",\r\n" + 
        		"	\"motiuCanvi\": \"creacio de nou ubicacio test amb ddd\",\r\n" + 
        		"	\"coordenadaX\": 11111111.999,\r\n" + 
        		"	\"coordenadaY\": 111111.998,\r\n" + 
        		"	\"coordenada_1_X\": 11111111.999,\r\n" + 
        		"	\"coordenada_1_Y\": 111111.998,\r\n" + 
        		"	\"coordenada_2_X\": 11111111.999,\r\n" + 
        		"	\"coordenada_2_Y\": 111111.998,\r\n" + 
        		"	\"coordenada_3_X\": 11111111.999,\r\n" + 
        		"	\"coordenada_3_Y\": 11111111.999,\r\n" + 
        		"	\"coordenada_4_X\": 111111.998,\r\n" + 
        		"	\"coordenada_4_Y\": 111111.998,\r\n" + 
        		"	\"dataInici\":\"30/05/2021 00:00:00\",\r\n" + 
        		"	\"dataFi\":\"30/05/2020 00:00:00\",\r\n" + 
        		"	\"usuariInsercio\": \"jrojast\",\r\n" + 
        		"	\"estat\":\"A\",\r\n" + 
        		"	\"localitzacio\":{\r\n" + 
        		"		\"codiPostal\":\"08040\",\r\n" + 
        		"		\"codiTerritori\":\"1\",\r\n" + 
        		"		\"codiBarri\" : \"40\",\r\n" + 
        		"		\"codiTipusVia\":\"C\",\r\n" + 
        		"		\"nomVia\":\"Polígon industrial Zona Franca, Carrer B Sector B\",\r\n" + 
        		"		\"numeroInici\":\"A\"\r\n" + 
        		"	},\r\n" + 
        		"	\"etiquetes\":[\r\n" + 
        		"		{\"valor\":\"UBICACIO-ETIQUETA2\"},\r\n" + 
        		"		{\"valor\":\"UBICACIO-ETIQUETA4\"}\r\n" + 
        		"	]\r\n" + 
        		"} "))
    	.andDo(print()).andExpect(status().isOk());
    }
	
	@Test
    public void caseOkUpdate() throws Exception{
    	Mockito.when(service.update(ubicationBDTO)).thenReturn(returnRDTO);
    	
    	mockMvc.perform(put("/ubicacio/"+ubicationRDTO.getCode())
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{	\r\n" + 
        		"	\"tipus\":\"PN\",\r\n" + 
        		"	\"tipusReferencia\":\"D\",\r\n" + 
        		"	\"motiuCanvi\": \"creacio de nou ubicacio test update\",\r\n" + 
        		"	\"coordenadaX\": 11111111.999,\r\n" + 
        		"	\"coordenadaY\": 111111.998,\r\n" + 
        		"	\"coordenada_1_X\": 21111111.999,\r\n" + 
        		"	\"coordenada_1_Y\": 111111.998,\r\n" + 
        		"	\"coordenada_2_X\": 21111111.999,\r\n" + 
        		"	\"coordenada_2_Y\": 111111.998,\r\n" + 
        		"	\"coordenada_3_X\": 11111111.999,\r\n" + 
        		"	\"coordenada_3_Y\": 11111111.999,\r\n" + 
        		"	\"coordenada_4_X\": 111111.998,\r\n" + 
        		"	\"coordenada_4_Y\": 111111.998,\r\n" + 
        		"	\"dataInici\":\"30/05/2021 00:00:00\",\r\n" + 
        		"	\"dataFi\":\"30/05/2021 00:00:00\",\r\n" + 
        		"	\"usuariActualitzacio\": \"jrojast\",\r\n" + 
        		"	\"estat\":\"A\",\r\n" + 
        		"	\"localitzacio\":{\r\n" + 
        		"		\"codiPostal\":\"08040\",\r\n" + 
        		"		\"codiTerritori\":\"1\",\r\n" + 
        		"		\"codiBarri\" : \"40\",\r\n" + 
        		"		\"codiTipusVia\":\"C\",\r\n" + 
        		"		\"nomVia\":\"Polígon industrial Zona Franca, Carrer B Sector B\",\r\n" + 
        		"		\"numeroInici\":\"A\"\r\n" + 
        		"	},\r\n" + 
        		"	\"etiquetes\":[\r\n" + 
        		"		{\"valor\":\"UBICACIO-ETIQUETA2\"}\r\n" + 
        		"	]\r\n" + 
        		"} "))
    	.andDo(print()).andExpect(status().isOk());
    }
	
	@Test
    public void caseOkDelete() throws Exception{
    	Mockito.when(service.delete(ubicationBDTO)).thenReturn(returnRDTO);
    	
    	mockMvc.perform(delete("/ubicacio/"+ubicationRDTO.getCode())
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{	\r\n" + 
        		"	\"tipus\":\"PN\",\r\n" + 
        		"	\"tipusReferencia\":\"D\",\r\n" + 
        		"	\"motiuCanvi\": \"delete de nou ubicacio test update\",\r\n" + 
        		"	\"coordenadaX\": 11111111.999,\r\n" + 
        		"	\"coordenadaY\": 111111.998,\r\n" + 
        		"	\"coordenada_1_X\": 21111111.999,\r\n" + 
        		"	\"coordenada_1_Y\": 111111.998,\r\n" + 
        		"	\"coordenada_2_X\": 21111111.999,\r\n" + 
        		"	\"coordenada_2_Y\": 311111.998,\r\n" + 
        		"	\"coordenada_3_X\": 11111111.999,\r\n" + 
        		"	\"coordenada_3_Y\": 11111111.999,\r\n" + 
        		"	\"coordenada_4_X\": 111111.998,\r\n" + 
        		"	\"coordenada_4_Y\": 111111.998,\r\n" + 
        		"	\"dataInici\":\"30/05/2021 00:00:00\",\r\n" + 
        		"	\"dataFi\":\"30/05/2021 00:00:00\",\r\n" + 
        		"	\"usuariEliminacio\": \"jrojast\",\r\n" + 
        		"	\"estat\":\"A\",\r\n" + 
        		"	\"localitzacio\":{\r\n" + 
        		"		\"codiPostal\":\"08040\",\r\n" + 
        		"		\"codiTerritori\":\"1\",\r\n" + 
        		"		\"codiBarri\" : \"40\",\r\n" + 
        		"		\"codiTipusVia\":\"C\",\r\n" + 
        		"		\"nomVia\":\"Polígon industrial Zona Franca, Carrer B Sector B\",\r\n" + 
        		"		\"numeroInici\":\"A\"\r\n" + 
        		"	},\r\n" + 
        		"	\"etiquetes\":[\r\n" + 
        		"		{\"valor\":\"UBICACIO-ETIQUETA2\"}\r\n" + 
        		"	]\r\n" + 
        		"} "))
    	.andDo(print()).andExpect(status().isOk());
    }
	
	@Test
    public void caseOkSelectMassive() throws Exception{
    	Mockito.when(service.selectMassive(queryParameterBDTO)).thenReturn(returnMassiveRDTO);
    	
    	mockMvc.perform(get("/ubicacio/contracta/"+queryParameterBDTO.getCodeContract()+"?dataReferencia="+queryParameterBDTO.getDateReference()+"&codiUsuari="+queryParameterBDTO.getCodeUser()+"&transactionId=asfasf-asdf"))
    	    	.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
    public void caseOkSelectDetailed() throws Exception{
    	Mockito.when(service.selectDetailed(queryParameterBDTO)).thenReturn(returnUbicationDetailedRDTO);
    	
    	mockMvc.perform(get("/ubicacio/"+queryParameterBDTO.getCode()+"/contracta/"+queryParameterBDTO.getCodeContract()+"/detall?dataReferencia="+queryParameterBDTO.getDateReference()+"&codiUsuari="+queryParameterBDTO.getCodeUser()+"&transactionId=asfasf-asdf"))
    	    	.andDo(print()).andExpect(status().isOk());
	}
}
