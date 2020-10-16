	package es.bcn.imi.framework.vigia.orquestrador.test.web.rest.controller.sync;

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

import es.bcn.imi.framework.vigia.orquestrador.business.certification.CertificationService;
import es.bcn.imi.framework.vigia.orquestrador.test.parent.RestServerParentTest;
import es.bcn.imi.framework.vigia.orquestrador.web.rest.controller.sync.CertificationController;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterCertificationProposalsRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnCertificationProposalsRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnCertificationProposalsRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.QueryParameterCertificationProposalsRDTOStub;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

@SuppressWarnings("unused")
public class CertificationControllerTest extends RestServerParentTest {

	private MockMvc mockMvc;

	@Mock
	private CertificationService certificationService;

	@InjectMocks
	private CertificationController controller;
	
	
	private ReturnRDTO returnRDTO;
	
	private QueryParameterCertificationProposalsRDTO queryParameterCertificationProposalsRDTO;
	
	private ReturnCertificationProposalsRDTO returnCertificationProposalsRDTO;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		returnRDTO = ReturnRDTOStub.getSuccessMessage();		
		queryParameterCertificationProposalsRDTO = QueryParameterCertificationProposalsRDTOStub.defaultOne();
		returnCertificationProposalsRDTO = ReturnCertificationProposalsRDTOStub.getSuccessMessage();
	}

	@Test
	public void caseOkSelectCertificationProposalsPending() throws Exception {
		Mockito.when(certificationService.selectCertificationProposals(Mockito.any(queryParameterCertificationProposalsRDTO.getClass())))
				.thenReturn(returnCertificationProposalsRDTO);
		mockMvc.perform(get("/sync/certificacio/contracta/"+queryParameterCertificationProposalsRDTO.getCodeContract()
				+"/propostes/" + queryParameterCertificationProposalsRDTO.getIdCertification()
				+ "&transactionId=" + queryParameterCertificationProposalsRDTO.getTransactionId())).andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void caseKoSelectCertificationProposalsPending() throws Exception {
		Mockito.doThrow(ImiException.class).when(certificationService)
				.selectCertificationProposals(Mockito.any(queryParameterCertificationProposalsRDTO.getClass()));
		mockMvc.perform(get("/sync/certificacio/contracta/"+queryParameterCertificationProposalsRDTO.getCodeContract()
				+"/propostes/" + queryParameterCertificationProposalsRDTO.getIdCertification()
				+ "&transactionId=" + queryParameterCertificationProposalsRDTO.getTransactionId())).andDo(print())
				.andExpect(status().isInternalServerError());
	}

}
