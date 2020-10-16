package es.bcn.imi.framework.vigia.inventari.test.web.rest.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import es.bcn.imi.framework.vigia.inventari.business.EmployeeCatalogService;
import es.bcn.imi.framework.vigia.inventari.test.parent.RestServerParentTest;
import es.bcn.imi.framework.vigia.inventari.web.rest.controller.EmployeeCatalogController;
import es.bcn.vigia.fmw.libcommons.business.dto.EmployeeCatalogBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.QueryParameterBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.stub.EmployeeCatalogBDTOStub;
import es.bcn.vigia.fmw.libcommons.business.dto.stub.QueryParameterBDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.EmployeeCatalogRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnEmployeeCatalogDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnEmployeeCatalogDetailedRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnMassiveRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.stub.EmployeeCatalogRDTOStub;
import net.opentrends.openframe.services.rest.http.ResponseEntity;

public class EmployeeCatalogControllerTest extends RestServerParentTest{
	
	private MockMvc mockMvc;
	
	@Mock
	private EmployeeCatalogService service;
	
	@InjectMocks
	private EmployeeCatalogController controller;
	
	private EmployeeCatalogBDTO employeeCatalogBDTO;
	
	private EmployeeCatalogRDTO employeeCatalogRDTO;
	
	private QueryParameterBDTO queryParameterBDTO;
	
	private ReturnMassiveRDTO returnMassiveRDTO;
	
	private ReturnEmployeeCatalogDetailedRDTO returnEmployeeCatalogDetailedRDTO;
	
	@Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        employeeCatalogBDTO = EmployeeCatalogBDTOStub.defaultOne();
        employeeCatalogRDTO = EmployeeCatalogRDTOStub.defaultOne();
        queryParameterBDTO = QueryParameterBDTOStub.defaultOne();
        returnMassiveRDTO = ReturnMassiveRDTOStub.getSuccessMessage();
        returnEmployeeCatalogDetailedRDTO = ReturnEmployeeCatalogDetailedRDTOStub.getSuccessMessage();
       }

	@Test
    public void caseOkInsert() throws Exception{
    	Mockito.when(service.insert(employeeCatalogBDTO)).thenReturn(ReturnRDTOStub.getSuccessMessage());
    	 
    	mockMvc.perform(post("/recursoshumans/cataleg/personal/treballador")
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{\r\n" + 
        		"\"codi\":\""+ employeeCatalogRDTO.getCode() +"\",\r\n" + 
        		"\"codiContracta\":\"C\",\r\n" + 
        		"\"sexe\":\"H\",\r\n" + 
        		"\"codiCategoriaProfessional\":\"CO\",\r\n" + 
        		"\"codiRegimTreball\":\"C1\",\r\n" + 
        		"\"codiRelacioLaboral\":\"FX\",\r\n" + 
        		"\"minusvalidesa\":false,\r\n" + 
        		"\"codiProcedenciaContractacio\":\"SR\",\r\n" + 
        		"\"tipusPersonal\" :\"PD\",\r\n" + 
        		"\"dataReferencia\":\"11/05/2020 00:00:00\", \r\n" + 
        		"\"usuariInsercio\":\"JROJAST\"\r\n" + 
        		"}"))
    	.andDo(print()).andExpect(status().isOk());
    }
	
	@Test
    public void caseKoInsert() throws Exception{
    	Mockito.doThrow(Exception.class).when(service).insert(Mockito.any(EmployeeCatalogBDTO.class));

    	mockMvc.perform(post("/recursoshumans/cataleg/personal/treballador")
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(""))
    	.andDo(print()).andExpect(status().is5xxServerError());   
    	
    	ResponseEntity<ReturnRDTO> r = controller.save(EmployeeCatalogRDTOStub.defaultOne());
    	
		Assert.assertEquals(r.getBody().getMessage(), null);	
    }
	
	@Test 
    public void caseKoInsertNotFound() throws Exception{
    	Mockito.when(service.insert(employeeCatalogBDTO)).thenReturn(ReturnRDTOStub.getSuccessMessage());
    	 
    	mockMvc.perform(post("/recursoshumans/cataleg/personal/treballadorNotFound")
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(""))
    	.andDo(print()).andExpect(status().is4xxClientError());
    }
	
	@Test
    public void caseOkSelectMassive() throws Exception{
    	Mockito.when(service.selectMassive(queryParameterBDTO)).thenReturn(returnMassiveRDTO);
    	
    	mockMvc.perform(get("/recursoshumans/cataleg/personal/treballador/contracta/"+queryParameterBDTO.getCodeContract()+"?dataReferencia="+queryParameterBDTO.getDateReference()+"&codiUsuari="+queryParameterBDTO.getCodeUser()+"&transactionId=asfasf-asdf"))
    	    	.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
    public void caseOkSelectDetailed() throws Exception{
    	Mockito.when(service.selectDetailed(queryParameterBDTO)).thenReturn(returnEmployeeCatalogDetailedRDTO);
    	
    	mockMvc.perform(get("/recursoshumans/cataleg/personal/treballador/contracta/"+queryParameterBDTO.getCodeContract()+"/detall?dataReferencia="+queryParameterBDTO.getDateReference()+"&codiUsuari="+queryParameterBDTO.getCodeUser()+"&transactionId=asfasf-asdf"))
    	    	.andDo(print()).andExpect(status().isOk());
	}
}
