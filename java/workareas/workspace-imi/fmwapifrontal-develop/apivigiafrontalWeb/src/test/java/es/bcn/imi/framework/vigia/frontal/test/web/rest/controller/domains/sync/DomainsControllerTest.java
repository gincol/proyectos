package es.bcn.imi.framework.vigia.frontal.test.web.rest.controller.domains.sync;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import es.bcn.imi.framework.vigia.frontal.business.domains.DomainsService;
import es.bcn.imi.framework.vigia.frontal.web.rest.controller.domains.sync.DomainsController;
import es.bcn.imi.framework.vigia.test.parent.RestServerParentTest;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterDomainsRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnDomainValuesRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnDomainsRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnDomainValuesRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnDomainsRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.QueryParameterDomainsRDTOStub;
import es.bcn.vigia.fmw.libutils.exception.ImiException;


public class DomainsControllerTest extends RestServerParentTest {

	private MockMvc mockMvc;

	@Mock
	private DomainsService domainsService;
	
	@InjectMocks
	private DomainsController controller;
	
	private QueryParameterDomainsRDTO queryParameterDomainsRDTO;
	
	private ReturnDomainsRDTO returnDomainsRDTO;
	
	private ReturnDomainValuesRDTO returnDomainValuesRDTO;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		queryParameterDomainsRDTO = QueryParameterDomainsRDTOStub.defaultOne();
		returnDomainsRDTO = ReturnDomainsRDTOStub.getSuccessMessage();
		returnDomainValuesRDTO = ReturnDomainValuesRDTOStub.getSuccessMessage();
	}
	
	@Test
	public void caseOkSelectDomains() throws Exception{
		
		Mockito.when(domainsService.selectDomains()).thenReturn(returnDomainsRDTO);
    	mockMvc.perform(get("/sync/dominis"))
    	.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void caseKoSelectDomains() throws Exception{
		
		Mockito.doThrow(ImiException.class).when(domainsService).selectDomains();
    	
    	mockMvc.perform(get("/sync/dominis"))
    	.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseOkSelectDomainValues() throws Exception{
		
		Mockito.when(domainsService.selectDomainValues(queryParameterDomainsRDTO)).thenReturn(returnDomainValuesRDTO);
    	mockMvc.perform(get("/sync/dominis/"+queryParameterDomainsRDTO.getName()+"?dataReferencia="+queryParameterDomainsRDTO.getDateReference()))
    	.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void caseKoSelectDomainValues() throws Exception{

		Mockito.doThrow(ImiException.class).when(domainsService).selectDomainValues(Mockito.any(queryParameterDomainsRDTO.getClass()));
    	
    	mockMvc.perform(get("/sync/dominis/"+queryParameterDomainsRDTO.getName()+"?dataReferencia="+queryParameterDomainsRDTO.getDateReference()))
    	.andDo(print()).andExpect(status().isInternalServerError());
	}
}
	