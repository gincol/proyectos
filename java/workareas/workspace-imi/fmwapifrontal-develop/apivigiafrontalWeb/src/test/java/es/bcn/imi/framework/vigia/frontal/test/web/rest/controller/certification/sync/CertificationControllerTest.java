package es.bcn.imi.framework.vigia.frontal.test.web.rest.controller.certification.sync;

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

import es.bcn.imi.framework.vigia.frontal.business.certification.CertificationService;
import es.bcn.imi.framework.vigia.frontal.web.rest.controller.certification.sync.CertificationController;
import es.bcn.imi.framework.vigia.test.parent.RestServerParentTest;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterCertificationProposalsRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnCertificationProposalsRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnCertificationProposalsRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.QueryParameterCertificationProposalsRDTOStub;
import es.bcn.vigia.fmw.libutils.exception.ImiException;


public class CertificationControllerTest extends RestServerParentTest {

	private MockMvc mockMvc;

	@Mock
	private CertificationService certificationService;

	
	@InjectMocks
	private CertificationController controller;

	@SuppressWarnings("unused")
	private ReturnRDTO returnRDTO;
	
	private ReturnRDTO returnRDTOKO;
	
	private QueryParameterCertificationProposalsRDTO queryParameterCertificationProposalsRDTO;
	
	private ReturnCertificationProposalsRDTO returnCertificationProposalsRDTO;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		returnRDTO = ReturnRDTOStub.getSuccessMessage();
		returnRDTOKO = new ReturnRDTO();
		returnRDTOKO.setCode("KO");
		queryParameterCertificationProposalsRDTO = QueryParameterCertificationProposalsRDTOStub.defaultOne();
		returnCertificationProposalsRDTO = ReturnCertificationProposalsRDTOStub.getSuccessMessage();
	}
	
	@Test
	public void caseOkSelectCertificationProposals() throws Exception{
		
		Mockito.when(certificationService.selectCertificationProposals(Mockito.any(queryParameterCertificationProposalsRDTO.getClass()))).thenReturn(returnCertificationProposalsRDTO);
    	mockMvc.perform(get("/sync/certificacio/contracta/"+queryParameterCertificationProposalsRDTO.getCodeContract()+"/certificacio/"+queryParameterCertificationProposalsRDTO.getIdCertification()+"/propostes")
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void caseKoSelectCertificationProposals() throws Exception{
		
		Mockito.doThrow(ImiException.class).when(certificationService).selectCertificationProposals(Mockito.any(queryParameterCertificationProposalsRDTO.getClass()));
    	
    	mockMvc.perform(get("/sync/certificacio/contracta/"+queryParameterCertificationProposalsRDTO.getCodeContract()+"/certificacio/"+queryParameterCertificationProposalsRDTO.getIdCertification()+"/propostes")
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isInternalServerError());
	}
}
	