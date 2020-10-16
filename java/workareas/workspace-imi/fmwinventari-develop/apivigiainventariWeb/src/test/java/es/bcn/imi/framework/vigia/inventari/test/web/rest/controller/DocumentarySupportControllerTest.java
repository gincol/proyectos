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

import es.bcn.imi.framework.vigia.inventari.business.DocumentarySupportService;
import es.bcn.imi.framework.vigia.inventari.test.parent.RestServerParentTest;
import es.bcn.imi.framework.vigia.inventari.web.rest.controller.DocumentarySupportController;
import es.bcn.vigia.fmw.libcommons.business.dto.DocumentarySupportBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.DocumentarySupportExpenseBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.QueryParameterDocumentarySupportBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.stub.DocumentarySupportBDTOStub;
import es.bcn.vigia.fmw.libcommons.business.dto.stub.DocumentarySupportExpenseBDTOStub;
import es.bcn.vigia.fmw.libcommons.business.dto.stub.QueryParameterDocumentarySupportBDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnDocumentarySupportRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnDocumentarySupportRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnRDTOStub;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public class DocumentarySupportControllerTest extends RestServerParentTest{
	
	private MockMvc mockMvc;
	
	@Mock
	private DocumentarySupportService service;
	
	@InjectMocks
	private DocumentarySupportController controller;
	
	private ReturnRDTO returnRDTO;
	
	private ReturnDocumentarySupportRDTO returnDocumentarySupportRDTO;
	
	private DocumentarySupportBDTO documentarySupportBDTO;
	
	private QueryParameterDocumentarySupportBDTO queryParameterDocumentarySupportBDTO;

	private DocumentarySupportExpenseBDTO documentarySupportExpenseBDTO;

	
	@Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        returnRDTO = ReturnRDTOStub.getSuccessMessage();
        returnDocumentarySupportRDTO = ReturnDocumentarySupportRDTOStub.getSuccessMessage();
        queryParameterDocumentarySupportBDTO = QueryParameterDocumentarySupportBDTOStub.defaultOne();
        documentarySupportBDTO = DocumentarySupportBDTOStub.defaultOne();
        documentarySupportExpenseBDTO = DocumentarySupportExpenseBDTOStub.defaultOne();
    }
	
	@Test
    public void caseOkSaveDocumentarySupport() throws Exception{
    	Mockito.when(service.insert(documentarySupportBDTO)).thenReturn(returnRDTO);
    	
    	
		mockMvc.perform(post("/suportdocumental")
		    	.contentType(MediaType.APPLICATION_JSON_UTF8)
		        .content("{\r\n" + 
		        		"   \"codi\":\"9001\",\r\n" + 
		        		"   \"codiContracta\":\"C\"\r\n" + 
		        		"   }\r\n" + 
		        		"}"))
		    	.andDo(print()).andExpect(status().isOk());	
	}

	@Test
    public void caseKoSaveDocumentarySupport() throws Exception{
		Mockito.doThrow(ImiException.class).when(service).insert(Mockito.any(documentarySupportBDTO.getClass()));    	
		mockMvc.perform(post("/suportdocumental")
		    	.contentType(MediaType.APPLICATION_JSON_UTF8)
		        .content("{\r\n" + 
		        		"   \"codi\":\"9001\",\r\n" + 
		        		"   \"codiContracta\":\"C\"\r\n" + 
		        		"   }\r\n" + 
		        		"}"))
		    	.andDo(print()).andExpect(status().isInternalServerError());	
	}
	
	@Test
    public void caseOkUpdateDocumentarySupport() throws Exception{
    	Mockito.when(service.update(documentarySupportBDTO)).thenReturn(returnRDTO);
    	
    	mockMvc.perform(put("/suportdocumental/"+documentarySupportBDTO.getCode()+"/entitat/"+documentarySupportBDTO.getCodeTypeEntity())
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{\r\n" + 
        		"   \"codiContracta\":\"C\"\r\n" + 
        		"}"))
    	.andDo(print()).andExpect(status().isOk());
    }
	
	@Test
    public void caseKoUpdateDocumentarySupport() throws Exception{
		Mockito.doThrow(ImiException.class).when(service).update(Mockito.any(documentarySupportBDTO.getClass()));  	
    	mockMvc.perform(put("/suportdocumental/"+documentarySupportBDTO.getCode()+"/entitat/"+documentarySupportBDTO.getCodeTypeEntity())
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{\r\n" + 
        		"   \"codiContracta\":\"C\"\r\n" + 
        		"}"))
    	.andDo(print()).andExpect(status().isInternalServerError());
    }
	
	@Test
    public void caseOkSelectDocumentarySupport() throws Exception{
    	Mockito.when(service.selectDocumentarySupport(queryParameterDocumentarySupportBDTO)).thenReturn(returnDocumentarySupportRDTO);
    	
    	mockMvc.perform(get("/suportdocumental/contracta/"+queryParameterDocumentarySupportBDTO.getCodeContract()+"/tipusEntitat/"+queryParameterDocumentarySupportBDTO.getCodeTypeEntity()+"/codiEntitat/"+queryParameterDocumentarySupportBDTO.getCode()+"?codiUsuari="+queryParameterDocumentarySupportBDTO.getCodeUser()+"&transactionId=asfasf-asdf"))
    	    	.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
    public void caseKoSelectDocumentarySupport() throws Exception{
    	Mockito.doThrow(ImiException.class).when(service).selectDocumentarySupport(Mockito.any(queryParameterDocumentarySupportBDTO.getClass()));
    	mockMvc.perform(get("/suportdocumental/contracta/"+queryParameterDocumentarySupportBDTO.getCodeContract()+"/tipusEntitat/"+queryParameterDocumentarySupportBDTO.getCodeTypeEntity()+"/codiEntitat/"+queryParameterDocumentarySupportBDTO.getCode()+"?codiUsuari="+queryParameterDocumentarySupportBDTO.getCodeUser()+"&transactionId=asfasf-asdf"))
    	    	.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
    public void caseOkSaveDocumentarySupportExpense() throws Exception{
    	Mockito.when(service.insertExpense(documentarySupportExpenseBDTO)).thenReturn(returnRDTO);
    	
    	
		mockMvc.perform(post("/suportdocumental/despesa")
		    	.contentType(MediaType.APPLICATION_JSON_UTF8)
		        .content("{\r\n" + 
		        		"   \"codi\":\"9001\",\r\n" + 
		        		"   \"codiContracta\":\"C\"\r\n" + 
		        		"   }\r\n" + 
		        		"}"))
		    	.andDo(print()).andExpect(status().isOk());	
	}

	@Test
    public void caseKoSaveDocumentarySupportExpense() throws Exception{
		Mockito.doThrow(ImiException.class).when(service).insertExpense(Mockito.any(documentarySupportExpenseBDTO.getClass()));    	
		mockMvc.perform(post("/suportdocumental/despesa")
		    	.contentType(MediaType.APPLICATION_JSON_UTF8)
		        .content("{\r\n" + 
		        		"   \"codi\":\"9001\",\r\n" + 
		        		"   \"codiContracta\":\"C\"\r\n" + 
		        		"   }\r\n" + 
		        		"}"))
		    	.andDo(print()).andExpect(status().isInternalServerError());	
	}
	
	@Test
    public void caseOkUpdateDocumentarySupportExpense() throws Exception{
    	Mockito.when(service.updateExpense(documentarySupportExpenseBDTO)).thenReturn(returnRDTO);
    	
    	mockMvc.perform(put("/suportdocumental/despesa/"+documentarySupportExpenseBDTO.getCode()+"/entitat/"+documentarySupportExpenseBDTO.getCodeTypeEntity())
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{\r\n" + 
        		"   \"codiContracta\":\"C\"\r\n" + 
        		"}"))
    	.andDo(print()).andExpect(status().isOk());
    }
	
	@Test
    public void caseKoUpdateDocumentarySupportExpense() throws Exception{
		Mockito.doThrow(ImiException.class).when(service).updateExpense(Mockito.any(documentarySupportExpenseBDTO.getClass()));  	
    	mockMvc.perform(put("/suportdocumental/despesa/"+documentarySupportExpenseBDTO.getCode()+"/entitat/"+documentarySupportExpenseBDTO.getCodeTypeEntity())
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{\r\n" + 
        		"   \"codiContracta\":\"C\"\r\n" + 
        		"}"))
    	.andDo(print()).andExpect(status().isInternalServerError());
    }

}
