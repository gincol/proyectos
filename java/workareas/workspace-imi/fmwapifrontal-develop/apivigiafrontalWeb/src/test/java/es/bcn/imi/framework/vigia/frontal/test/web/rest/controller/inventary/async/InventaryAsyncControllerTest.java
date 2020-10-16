package es.bcn.imi.framework.vigia.frontal.test.web.rest.controller.inventary.async;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import es.bcn.imi.framework.vigia.frontal.business.inventary.AsyncService;
import es.bcn.imi.framework.vigia.frontal.web.rest.controller.inventary.async.InventaryAsyncController;
import es.bcn.imi.framework.vigia.test.parent.RestServerParentTest;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.AsyncRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CommerceRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.InstallationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.LogicalModelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.MaterialResourceRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.PhysicalModelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterStatesRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.UbicationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnStatesRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnStatesRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.AsyncRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.QueryParameterStatesRDTOStub;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public class InventaryAsyncControllerTest extends RestServerParentTest {

	private MockMvc mockMvc;

	@Mock
	private AsyncService asyncService;

	@InjectMocks
	private InventaryAsyncController controller;

	private ReturnRDTO returnRDTO;

	private ReturnRDTO returnRDTOKO;

	private QueryParameterStatesRDTO queryParameterStatesRDTO;

	private ReturnStatesRDTO returnStatesRDTO;

	private AsyncRDTO<UbicationRDTO> asyncRDTOUbication;

	private AsyncRDTO<InstallationRDTO> asyncRDTOInstallation;

	private AsyncRDTO<LogicalModelRDTO> asyncRDTOLogicalModel;

	private AsyncRDTO<PhysicalModelRDTO> asyncRDTOPhysicalModel;

	private AsyncRDTO<MaterialResourceRDTO> asyncRDTOMaterialResource;

	private AsyncRDTO<CommerceRDTO> asyncRDTOCommerce;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		returnRDTO = ReturnRDTOStub.getSuccessMessage();
		returnRDTOKO = new ReturnRDTO();
		returnRDTOKO.setCode("KO");

		queryParameterStatesRDTO = QueryParameterStatesRDTOStub.defaultOne();
		returnStatesRDTO = ReturnStatesRDTOStub.getSuccessMessage();
		
		asyncRDTOUbication = AsyncRDTOStub.defaultOneUbication();
		
		asyncRDTOInstallation = AsyncRDTOStub.defaultOneInstallation();
		
		asyncRDTOLogicalModel = AsyncRDTOStub.defaultOneMCL();
		
		asyncRDTOPhysicalModel = AsyncRDTOStub.defaultOneMCF();
		
		asyncRDTOMaterialResource = AsyncRDTOStub.defaultOneRRMM();
		
		asyncRDTOCommerce = AsyncRDTOStub.defaultOneCommerce();

	}

	@Test
	public void caseOkSelectStates() throws Exception {

		Mockito.when(asyncService.selectStates(queryParameterStatesRDTO)).thenReturn(returnStatesRDTO);
		mockMvc.perform(get("/async/inventari/estats/" + queryParameterStatesRDTO.getIdTransaction())).andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void caseKoSelectStates() throws Exception {

		Mockito.doThrow(ImiException.class).when(asyncService)
				.selectStates(Mockito.any(queryParameterStatesRDTO.getClass()));

		mockMvc.perform(get("/async/inventari/estats/" + queryParameterStatesRDTO.getIdTransaction())).andDo(print())
				.andExpect(status().isInternalServerError());
	}

	@Test
	public void caseOkAsyncUbication() throws Exception {

		Mockito.when(asyncService.redirect(Mockito.any(asyncRDTOUbication.getClass()), Mockito.anyString(), Mockito.any(HttpServletRequest.class))).thenReturn(returnRDTO);

		mockMvc.perform(post("/async/inventari/ubicacio").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + asyncRDTOUbication.getCodeContract() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void caseOkAsyncInstallation() throws Exception {

		Mockito.when(asyncService.redirect(Mockito.any(asyncRDTOInstallation.getClass()), Mockito.anyString(), Mockito.any(HttpServletRequest.class))).thenReturn(returnRDTO);

		mockMvc.perform(post("/async/inventari/installacio").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + asyncRDTOInstallation.getCodeContract() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void caseOkAsyncMCL() throws Exception {

		Mockito.when(asyncService.redirect(Mockito.any(asyncRDTOLogicalModel.getClass()), Mockito.anyString(), Mockito.any(HttpServletRequest.class))).thenReturn(returnRDTO);

		mockMvc.perform(post("/async/inventari/ubicacio/mcl").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + asyncRDTOLogicalModel.getCodeContract() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void caseOkAsyncMCF() throws Exception {

		Mockito.when(asyncService.redirect(Mockito.any(asyncRDTOPhysicalModel.getClass()), Mockito.anyString(), Mockito.any(HttpServletRequest.class))).thenReturn(returnRDTO);

		mockMvc.perform(post("/async/inventari/ubicacio/mcl/mcf").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + asyncRDTOPhysicalModel.getCodeContract() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void caseOkAsyncRRMM() throws Exception {

		Mockito.when(asyncService.redirect(Mockito.any(asyncRDTOMaterialResource.getClass()), Mockito.anyString(), Mockito.any(HttpServletRequest.class))).thenReturn(returnRDTO);

		mockMvc.perform(post("/async/inventari/recursmaterial").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + asyncRDTOMaterialResource.getCodeContract() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void caseOkAsyncCommerce() throws Exception {

		Mockito.when(asyncService.redirect(Mockito.any(asyncRDTOCommerce.getClass()), Mockito.anyString(), Mockito.any(HttpServletRequest.class))).thenReturn(returnRDTO);

		mockMvc.perform(post("/async/inventari/comerc").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + asyncRDTOCommerce.getCodeContract() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isOk());
	}

}
