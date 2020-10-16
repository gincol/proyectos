package es.bcn.imi.framework.vigia.frontal.test.web.rest.controller.inventary.sync;

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

import es.bcn.imi.framework.vigia.frontal.business.inventary.CommerceService;
import es.bcn.imi.framework.vigia.frontal.business.inventary.EmployeeCatalogService;
import es.bcn.imi.framework.vigia.frontal.business.inventary.InstallationService;
import es.bcn.imi.framework.vigia.frontal.business.inventary.LogicalModelService;
import es.bcn.imi.framework.vigia.frontal.business.inventary.MaterialResourceService;
import es.bcn.imi.framework.vigia.frontal.business.inventary.PhysicalModelService;
import es.bcn.imi.framework.vigia.frontal.business.inventary.UbicationService;
import es.bcn.imi.framework.vigia.frontal.web.rest.controller.inventary.sync.InventaryController;
import es.bcn.imi.framework.vigia.test.parent.RestServerParentTest;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.ActuationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.AggregateAmortizationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CommerceRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.DocumentarySupportRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.EmployeeCatalogRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.InstallationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.LogicalModelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.MaterialResourceRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.PhysicalModelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.UbicationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnBreakdownAmortizationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnCommerceDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnCommerceElementsRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnEmployeeCatalogDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnInstallationDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnInstallationExpensesRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnInstallationPeriodRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnLogicalModelDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnLogicalModelMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMaterialResourceDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMaterialResourceVehicleDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMaterialResourceVehicleExpensesRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMaterialResourceVehiclePeriodRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnPhysicalModelAmortizationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnPhysicalModelDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnPhysicalModelMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnUbicationDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnBreakdownAmortizationRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnCommerceDetailedRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnCommerceElementsRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnEmployeeCatalogDetailedRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnInstallationDetailedRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnInstallationExpensesRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnInstallationPeriodRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnMassiveRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnMaterialResourceDetailedRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnMaterialResourceVehicleDetailedRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnMaterialResourceVehicleExpensesRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnMaterialResourceVehiclePeriodRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnMclDetailedRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnMclMassiveRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnPhysicalModelAmortizationRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnPhysicalModelDetailedRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnPhysicalModelMassiveRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnUbicationDetailedRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.ActuationRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.AggregateAmortizationRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.CommerceRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.DocumentarySupportRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.EmployeeCatalogRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.InstallationRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.LogicalModelRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.MaterialResourceRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.PhysicalModelRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.QueryParameterRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.UbicationRDTOStub;
import es.bcn.vigia.fmw.libutils.enums.ReturnEnum;
import es.bcn.vigia.fmw.libutils.exception.ImiException;


public class InventaryControllerTest extends RestServerParentTest {

	private MockMvc mockMvc;

	@Mock
	private UbicationService ubicationService;
	
	@Mock
	private PhysicalModelService physicalModelService;

	@Mock
	private LogicalModelService logicalModelService;

	@Mock
	private MaterialResourceService materialResourceService;

	@Mock
	private InstallationService installationService;

	@Mock
	private CommerceService commerceService;

	@Mock
	private EmployeeCatalogService employeeCatalogService;

	@InjectMocks
	private InventaryController controller;

	private ReturnRDTO returnRDTO;
	
	private ReturnRDTO returnRDTOKO;

	private UbicationRDTO ubicationRDTO;
	
	private LogicalModelRDTO logicalModelRDTO;
	
	private PhysicalModelRDTO physicalModelRDTO;
	
	private MaterialResourceRDTO materialResourceRDTO;
	
	private AggregateAmortizationRDTO aggregateAmortizationRDTO;
	
	private InstallationRDTO installationRDTO;
	
	private CommerceRDTO commerceRDTO;
	
	private QueryParameterRDTO queryParameterRDTO;

	private ReturnInstallationDetailedRDTO returnInstallationDetailedRDTO;

	private ReturnBreakdownAmortizationRDTO returnBreakdownAmortizationRDTO;

	private ReturnInstallationExpensesRDTO returnInstallationExpensesRDTO;

	private ReturnInstallationPeriodRDTO returnInstallationPeriodRDTO;

	private ReturnMassiveRDTO returnMassiveRDTO;

	private ReturnCommerceDetailedRDTO returnCommerceDetailedRDTO;

	private ReturnCommerceElementsRDTO returnCommerceElementsRDTO;

	private ReturnEmployeeCatalogDetailedRDTO returnEmployeeCatalogDetailedRDTO;

	private ReturnMaterialResourceDetailedRDTO returnMaterialResourceDetailedRDTO;

	private ReturnMaterialResourceVehicleDetailedRDTO returnMaterialResourceVehicleDetailedRDTO;

	private ReturnMaterialResourceVehicleExpensesRDTO returnMaterialResourceVehicleExpensesRDTO;

	private ReturnMaterialResourceVehiclePeriodRDTO returnMaterialResourceVehiclePeriodRDTO;

	private ReturnUbicationDetailedRDTO returnUbicationDetailedRDTO;

	private ReturnLogicalModelMassiveRDTO returnLogicalModelMassiveRDTO;

	private ReturnLogicalModelDetailedRDTO returnLogicalModelDetailedRDTO;

	private ReturnPhysicalModelMassiveRDTO returnPhysicalModelMassiveRDTO;

	private ReturnPhysicalModelDetailedRDTO returnPhysicalModelDetailedRDTO;

	private ReturnPhysicalModelAmortizationRDTO returnPhysicalModelAmortizationRDTO;
	
	private EmployeeCatalogRDTO employeeCatalogRDTO;
	
	private DocumentarySupportRDTO documentarySupportRDTO;
	
	private ActuationRDTO actuationRDTO;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		returnRDTO = ReturnRDTOStub.getSuccessMessage();
		returnRDTOKO = new ReturnRDTO();
		returnRDTOKO.setCode("KO");
		
		ubicationRDTO = UbicationRDTOStub.defaultOne();
		logicalModelRDTO = LogicalModelRDTOStub.defaultOne();
		physicalModelRDTO = PhysicalModelRDTOStub.defaultOne();
		materialResourceRDTO = MaterialResourceRDTOStub.defaultOne();
		aggregateAmortizationRDTO = AggregateAmortizationRDTOStub.defaultOne();
		installationRDTO = InstallationRDTOStub.defaultOne();
		queryParameterRDTO = QueryParameterRDTOStub.defaultOne();
		returnInstallationDetailedRDTO = ReturnInstallationDetailedRDTOStub.getSuccessMessage();
		returnBreakdownAmortizationRDTO = ReturnBreakdownAmortizationRDTOStub.getSuccessMessage();
		returnInstallationExpensesRDTO = ReturnInstallationExpensesRDTOStub.getSuccessMessage();
		returnInstallationPeriodRDTO = ReturnInstallationPeriodRDTOStub.getSuccessMessage();
		returnMassiveRDTO = ReturnMassiveRDTOStub.getSuccessMessage();
		returnCommerceDetailedRDTO = ReturnCommerceDetailedRDTOStub.getSuccessMessage();
		returnCommerceElementsRDTO = ReturnCommerceElementsRDTOStub.getSuccessMessage();
		returnEmployeeCatalogDetailedRDTO = ReturnEmployeeCatalogDetailedRDTOStub.getSuccessMessage();
		returnMaterialResourceDetailedRDTO = ReturnMaterialResourceDetailedRDTOStub.getSuccessMessage();
		returnMaterialResourceVehicleDetailedRDTO = ReturnMaterialResourceVehicleDetailedRDTOStub.getSuccessMessage();
		returnMaterialResourceVehicleExpensesRDTO = ReturnMaterialResourceVehicleExpensesRDTOStub.getSuccessMessage();
		returnMaterialResourceVehiclePeriodRDTO = ReturnMaterialResourceVehiclePeriodRDTOStub.getSuccessMessage();
		returnUbicationDetailedRDTO = ReturnUbicationDetailedRDTOStub.getSuccessMessage();
		returnLogicalModelMassiveRDTO = ReturnMclMassiveRDTOStub.getSuccessMessage();
		returnLogicalModelDetailedRDTO = ReturnMclDetailedRDTOStub.getSuccessMessage();
		returnPhysicalModelMassiveRDTO = ReturnPhysicalModelMassiveRDTOStub.getSuccessMessage();
		returnPhysicalModelDetailedRDTO = ReturnPhysicalModelDetailedRDTOStub.getSuccessMessage();
		returnPhysicalModelAmortizationRDTO = ReturnPhysicalModelAmortizationRDTOStub.getSuccessMessage();
	
		commerceRDTO = CommerceRDTOStub.defaultOne();
		employeeCatalogRDTO = EmployeeCatalogRDTOStub.defaultOne();
		documentarySupportRDTO = DocumentarySupportRDTOStub.defaultOne();
		actuationRDTO = ActuationRDTOStub.defaultOne();
	}

	@Test
	public void caseOkSaveUbication() throws Exception {

		Mockito.when(ubicationService.insert(Mockito.any(ubicationRDTO.getClass()))).thenReturn(returnRDTO);

		mockMvc.perform(post("/sync/inventari/ubicacio").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + ubicationRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void caseKOSaveUbicationBadRequest() throws Exception {

		Mockito.when(ubicationService.insert(Mockito.any(ubicationRDTO.getClass()))).thenReturn(returnRDTOKO);

		mockMvc.perform(post("/sync/inventari/ubicacio").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + ubicationRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}
	@Test
	public void caseKoSaveUbicationException() throws Exception {

		Mockito.when(ubicationService.insert(ubicationRDTO)).thenReturn(returnRDTO);
		mockMvc.perform(post("/sync/inventari/ubicacio").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + ubicationRDTO.getCode() + "\",\r\n"
						+ "	\"coordinatesX\": 100001,\r\n" + "	\"coordinatesY\": 200001,\r\n"
						+ "	\"coordinates_1_X\": 100001,\r\n" + "	\"coordinates_1_Y\": 200001,\r\n"
						+ "	\"coordinates_2_X\": 300001,\r\n" + "	\"coordinates_2_Y\": 400001,\r\n"
						+ "	\"coordinates_3_X\": 500001,\r\n" + "	\"coordinates_3_Y\": 600001,\r\n"
						+ "	\"coordinates_4_X\": 700001,\r\n" + "	\"coordinates_4_Y\": 800001,\r\n"
						+ "	\"startDate\":\"30/05/2021 00:00:00\",\r\n" + "	\"endDate\":\"30/05/2020 00:00:00\",\r\n"
						+ "	\"reasonChange\": \"creacio de nou ubicacio test amb ddd\",\r\n"
						+ "	\"userInsert\": \"jrojast\",\r\n" + "	\"dateInsert\": \"28-11-2019 17:33:00\",\r\n"
						+ "	\"status\":\"X\",\r\n" + "	\"type\":\"RO\",\r\n" + "	\"codeContract\":\"" + ubicationRDTO.getCodeContract() + "\"\r\n" + "	},\r\n"
						+ "	\"location\":{\r\n" + "				\"codePostal\":\"08040\",\r\n"
						+ "				\"codeTypeRoad\":\"C\",\r\n"
						+ "				\"streetName\":\"Polgon industrial Zona Franca, Carrer B Sector B\",\r\n"
						+ "				\"endNumber\":\"16\",\r\n" + "				\"startNumber\":\"22\",\r\n"
						+ "				\"population\":\"BARCELONA\"\r\n" + "	}\r\n" + "}"))
				.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
    public void caseKoUpdateUbication() throws Exception{
		
    	Mockito.when(ubicationService.update(ubicationRDTO)).thenReturn(returnRDTO);
    	
    	mockMvc.perform(put("/sync/inventari/ubicacio/"+ubicationRDTO.getCode())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
    	.content("{\r\n" + "	\"code\": \"" + ubicationRDTO.getCode() + "\"\r\n" + "}"))
    	.andDo(print()).andExpect(status().isInternalServerError());
    }

	@Test
	public void caseOkUpdateUbication() throws Exception {

		Mockito.when(ubicationService.update(Mockito.any(ubicationRDTO.getClass()))).thenReturn(returnRDTO);

		mockMvc.perform(put("/sync/inventari/ubicacio/"+ubicationRDTO.getCode())
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
		    	.contentType(MediaType.APPLICATION_JSON_UTF8)
		    	.content("{\r\n" + "	\"code\": \"" + ubicationRDTO.getCode() + "\"\r\n" + "}"))
		    	.andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void caseKOUpdateUbicationBadRequest() throws Exception {

		Mockito.when(ubicationService.update(Mockito.any(ubicationRDTO.getClass()))).thenReturn(returnRDTOKO);

		mockMvc.perform(put("/sync/inventari/ubicacio/"+ubicationRDTO.getCode())
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
		    	.contentType(MediaType.APPLICATION_JSON_UTF8)
		    	.content("{\r\n" + "	\"code\": \"" + ubicationRDTO.getCode() + "\"\r\n" + "}"))
		    	.andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
	public void caseKoDeleteUbication() throws Exception{
		
		Mockito.when(ubicationService.delete(ubicationRDTO)).thenReturn(returnRDTO);
		
		mockMvc.perform(put("/sync/inventari/ubicacio/"+ubicationRDTO.getCode()+"/baixa")
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\r\n" + "	\"code\": \"" + ubicationRDTO.getCode() + "\"\r\n" + "}"))
		.andDo(print()).andExpect(status().isInternalServerError());
	}

	@Test
	public void caseOkDeleteUbication() throws Exception {

		Mockito.when(ubicationService.delete(Mockito.any(ubicationRDTO.getClass()))).thenReturn(returnRDTO);

		mockMvc.perform(put("/sync/inventari/ubicacio/"+ubicationRDTO.getCode()+"/baixa")
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
		    	.contentType(MediaType.APPLICATION_JSON_UTF8)
		    	.content("{\r\n" + "	\"code\": \"" + ubicationRDTO.getCode() + "\"\r\n" + "}"))
		    	.andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void caseKODeleteUbicationBadRequest() throws Exception {

		Mockito.when(ubicationService.delete(Mockito.any(ubicationRDTO.getClass()))).thenReturn(returnRDTOKO);

		mockMvc.perform(put("/sync/inventari/ubicacio/"+ubicationRDTO.getCode()+"/baixa")
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
		    	.contentType(MediaType.APPLICATION_JSON_UTF8)
		    	.content("{\r\n" + "	\"code\": \"" + ubicationRDTO.getCode() + "\"\r\n" + "}"))
		    	.andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
	public void caseOkSelectLogicModel() throws Exception {
		mockMvc.perform(get("/sync/inventari/mcl/CL001").header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")).andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void caseKoSelectLogicModel() throws Exception {
		Mockito.doThrow(ImiException.class).when(logicalModelService).select(Mockito.any(logicalModelRDTO.getClass()));
		mockMvc.perform(get("/sync/inventari/mcl/CL001").header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")).andDo(print())
		.andExpect(status().isInternalServerError());
	}

	@Test
	public void caseOkSelectPhysicModel() throws Exception {
		mockMvc.perform(get("/sync/inventari/mcf/CF001").header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")).andDo(print())
		.andExpect(status().isOk());
	}

	@Test
	public void caseKoSelectPhysicModel() throws Exception {
		Mockito.doThrow(ImiException.class).when(physicalModelService).select(Mockito.any(physicalModelRDTO.getClass()));
		mockMvc.perform(get("/sync/inventari/mcf/CF001").header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")).andDo(print())
		.andExpect(status().isInternalServerError());
	}
	@Test
	public void caseKOSaveLogicModel() throws Exception {

		Mockito.when(logicalModelService.insert(LogicalModelRDTOStub.defaultOne())).thenReturn(returnRDTO);

		mockMvc.perform(post("/sync/inventari/ubicacio/mcl").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + LogicalModelRDTOStub.defaultOne().getCode() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isInternalServerError());
	}

	@Test
	public void caseOkSaveLogicModel() throws Exception {

		Mockito.when(logicalModelService.insert(Mockito.any(LogicalModelRDTO.class))).thenReturn(returnRDTO);

		mockMvc.perform(post("/sync/inventari/ubicacio/mcl").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + LogicalModelRDTOStub.defaultOne().getCode() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isOk());
	}
	
	@Test
	public void caseKoSaveLogicModelBadRequest() throws Exception {

		Mockito.when(logicalModelService.insert(Mockito.any(LogicalModelRDTO.class))).thenReturn(returnRDTOKO);

		mockMvc.perform(post("/sync/inventari/ubicacio/mcl").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + LogicalModelRDTOStub.defaultOne().getCode() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void caseOkUpdateLogicModel() throws Exception {

		Mockito.when(logicalModelService.update(Mockito.any(LogicalModelRDTO.class))).thenReturn(returnRDTO);

    	mockMvc.perform(put("/sync/inventari/ubicacio/"+ logicalModelRDTO.getUbicationRDTO().getCode() +"/mcl/"+logicalModelRDTO.getCode())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
    	.content("{\r\n" + "	\"code\": \"" + logicalModelRDTO.getCode() + "\"\r\n" + "}"))
    	.andDo(print()).andExpect(status().isOk());

	}
	
	@Test
	public void caseKoUpdateLogicModelBadRequest() throws Exception {

		Mockito.when(logicalModelService.update(Mockito.any(LogicalModelRDTO.class))).thenReturn(returnRDTOKO);

    	mockMvc.perform(put("/sync/inventari/ubicacio/"+ logicalModelRDTO.getUbicationRDTO().getCode() +"/mcl/"+logicalModelRDTO.getCode())
    			.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
    	.content("{\r\n" + "	\"code\": \"" + logicalModelRDTO.getCode() + "\"\r\n" + "}"))
    	.andDo(print()).andExpect(status().isBadRequest());

	}

	
	@Test
    public void caseKoUpdateLogicModel() throws Exception{
		
    	Mockito.when(logicalModelService.update(logicalModelRDTO)).thenReturn(returnRDTO);
    	
    	mockMvc.perform(put("/sync/inventari/ubicacio/"+ logicalModelRDTO.getUbicationRDTO().getCode() +"/mcl/"+logicalModelRDTO.getCode())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
    	.content("{\r\n" + "	\"code\": \"" + logicalModelRDTO.getCode() + "\"\r\n" + "}"))
    	.andDo(print()).andExpect(status().isInternalServerError());
    }
	
	@Test
	public void caseKoDeleteLogicModel() throws Exception{
		
		Mockito.when(logicalModelService.delete(logicalModelRDTO)).thenReturn(returnRDTO);
		
		mockMvc.perform(put("/sync/inventari/ubicacio/+"+logicalModelRDTO.getUbicationRDTO().getCode()+"/mcl/"+logicalModelRDTO.getCode()+"/baixa")
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\r\n" + "	\"code\": \"" + logicalModelRDTO.getCode() + "\"\r\n" + "}"))
		.andDo(print()).andExpect(status().isInternalServerError());
	}

	
	

	@Test
	public void caseOkDeleteLogicModel() throws Exception {

		Mockito.when(logicalModelService.delete(Mockito.any(logicalModelRDTO.getClass()))).thenReturn(returnRDTO);
		
		mockMvc.perform(put("/sync/inventari/ubicacio/+"+logicalModelRDTO.getUbicationRDTO().getCode()+"/mcl/"+logicalModelRDTO.getCode()+"/baixa")
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\r\n" + "	\"code\": \"" + logicalModelRDTO.getCode() + "\"\r\n" + "}"))
		.andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void caseKODeleteLogicModelBadRequest() throws Exception {

		Mockito.when(logicalModelService.delete(Mockito.any(logicalModelRDTO.getClass()))).thenReturn(returnRDTOKO);

		mockMvc.perform(put("/sync/inventari/ubicacio/+"+logicalModelRDTO.getUbicationRDTO().getCode()+"/mcl/"+logicalModelRDTO.getCode()+"/baixa")
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\r\n" + "	\"code\": \"" + logicalModelRDTO.getCode() + "\"\r\n" + "}"))
		.andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
	public void caseOkSavePhysicalModel() throws Exception {

		Mockito.when(physicalModelService.insert(Mockito.any(physicalModelRDTO.getClass()))).thenReturn(returnRDTO);

		mockMvc.perform(post("/sync/inventari/ubicacio/mcl/mcf").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + physicalModelRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isOk());
	}
	
	@Test
	public void caseKOSavePhysicalModelBadRequest() throws Exception {

		Mockito.when(physicalModelService.insert(Mockito.any(physicalModelRDTO.getClass()))).thenReturn(returnRDTOKO);

		mockMvc.perform(post("/sync/inventari/ubicacio/mcl/mcf").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + physicalModelRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}
	@Test
	public void caseKoSavePhysicalModel() throws Exception {
		mockMvc.perform(post("/sync/inventari/ubicacio/mcl/mcf").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + physicalModelRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isInternalServerError());
	}
	
	@Test
    public void caseOkUpdatePhysicalModel() throws Exception{
		
    	Mockito.when(physicalModelService.update(Mockito.any(physicalModelRDTO.getClass()))).thenReturn(returnRDTO);
    	
    	mockMvc.perform(put("/sync/inventari/ubicacio/"+physicalModelRDTO.getCodeUbication()+"/mcl/mcf/"+physicalModelRDTO.getCode())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
    	.content("{\r\n" + "	\"code\": \"" + physicalModelRDTO.getCode() + "\"\r\n" + "}"))
    	.andDo(print()).andExpect(status().isOk());
    }
	
	@Test
    public void caseKoUpdatePhysicalModelBadRequest() throws Exception{
		
		Mockito.when(physicalModelService.update(Mockito.any(physicalModelRDTO.getClass()))).thenReturn(returnRDTOKO);
    	
    	mockMvc.perform(put("/sync/inventari/ubicacio/"+physicalModelRDTO.getCodeUbication()+"/mcl/mcf/"+physicalModelRDTO.getCode())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
    	.content("{\r\n" + "	\"code\": \"" + physicalModelRDTO.getCode() + "\"\r\n" + "}"))
    	.andDo(print()).andExpect(status().isBadRequest());
    }
	@Test
	public void caseKoUpdatePhysicalModel() throws Exception{
		
		mockMvc.perform(put("/sync/inventari/ubicacio/"+physicalModelRDTO.getCodeUbication()+"/mcl/mcf/"+physicalModelRDTO.getCode())
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\r\n" + "	\"code\": \"" + physicalModelRDTO.getCode() + "\"\r\n" + "}"))
		.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseOkDeletePhysicalModel() throws Exception{
		
		Mockito.when(physicalModelService.delete(Mockito.any(physicalModelRDTO.getClass()))).thenReturn(returnRDTO);
		
		mockMvc.perform(put("/sync/inventari/ubicacio/"+physicalModelRDTO.getCodeUbication()+"/mcl/mcf/"+physicalModelRDTO.getCode()+"/baixa")
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\r\n" + "	\"codi\": \"" + physicalModelRDTO.getCode() + "\"\r\n" + "}"))
		.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void caseKoDeletePhysicalModelBadRequest() throws Exception{
		
		Mockito.when(physicalModelService.delete(Mockito.any(physicalModelRDTO.getClass()))).thenReturn(returnRDTOKO);
    	
    	mockMvc.perform(put("/sync/inventari/ubicacio/"+physicalModelRDTO.getCodeUbication()+"/mcl/mcf/"+physicalModelRDTO.getCode()+"/baixa")
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
    	.content("{\r\n" + "	\"code\": \"" + physicalModelRDTO.getCode() + "\"\r\n" + "}"))
    	.andDo(print()).andExpect(status().isBadRequest());
    }
	@Test
	public void caseKoDeletePhysicalModel() throws Exception{
		
		mockMvc.perform(put("/sync/inventari/ubicacio/"+physicalModelRDTO.getCodeUbication()+"/mcl/mcf/"+physicalModelRDTO.getCode()+"/baixa")
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\r\n" + "	\"code\": \"" + physicalModelRDTO.getCode() + "\"\r\n" + "}"))
		.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseKoSaveMaterialResource() throws Exception {
		mockMvc.perform(post("/sync/inventari/recursmaterial").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + materialResourceRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseOkSaveMaterialResource() throws Exception{
		
		Mockito.when(materialResourceService.insert(Mockito.any(materialResourceRDTO.getClass()))).thenReturn(returnRDTO);
		
		mockMvc.perform(post("/sync/inventari/recursmaterial").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + materialResourceRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isOk());
	}
	
	@Test
	public void caseKoSaveMaterialResourceBadRequest() throws Exception{
		
		Mockito.when(materialResourceService.insert(Mockito.any(materialResourceRDTO.getClass()))).thenReturn(returnRDTOKO);
    	
		mockMvc.perform(post("/sync/inventari/recursmaterial").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + materialResourceRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isBadRequest());
    }
	
	@Test
    public void caseKoUpdateMaterialResource() throws Exception{
		
    	Mockito.when(materialResourceService.update(materialResourceRDTO)).thenReturn(returnRDTO);
    	
    	mockMvc.perform(put("/sync/inventari/recursmaterial/"+materialResourceRDTO.getCode())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
    	.content("{\r\n" + "	\"code\": \"" + materialResourceRDTO.getCode() + "\"\r\n" + "}"))
    	.andDo(print()).andExpect(status().isInternalServerError());
    }
	
	@Test
	public void caseOkUpdateMaterialResource() throws Exception{
		
		Mockito.when(materialResourceService.update(Mockito.any(materialResourceRDTO.getClass()))).thenReturn(returnRDTO);
		
		mockMvc.perform(put("/sync/inventari/recursmaterial/"+materialResourceRDTO.getCode())
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
		    	.contentType(MediaType.APPLICATION_JSON_UTF8)
		    	.content("{\r\n" + "	\"code\": \"" + materialResourceRDTO.getCode() + "\"\r\n" + "}"))
		    	.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void caseKoUpdateMaterialResourceBadRequest() throws Exception{
		
		Mockito.when(materialResourceService.update(Mockito.any(materialResourceRDTO.getClass()))).thenReturn(returnRDTOKO);
    	
		mockMvc.perform(put("/sync/inventari/recursmaterial/"+materialResourceRDTO.getCode())
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
		    	.contentType(MediaType.APPLICATION_JSON_UTF8)
		    	.content("{\r\n" + "	\"code\": \"" + materialResourceRDTO.getCode() + "\"\r\n" + "}"))
		    	.andDo(print()).andExpect(status().isBadRequest());
    }
	@Test
	public void caseKoDeleteMaterialResource() throws Exception{
		
		Mockito.when(materialResourceService.delete(materialResourceRDTO)).thenReturn(returnRDTO);
		
		mockMvc.perform(put("/sync/inventari/recursmaterial/"+materialResourceRDTO.getCode()+"/baixa")
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\r\n" + "	\"code\": \"" + materialResourceRDTO.getCode() + "\"\r\n" + "}"))
		.andDo(print()).andExpect(status().isInternalServerError());
	}
	@Test
	public void caseOkDeleteMaterialResource() throws Exception{
		
		Mockito.when(materialResourceService.delete(Mockito.any(materialResourceRDTO.getClass()))).thenReturn(returnRDTO);
		
		mockMvc.perform(put("/sync/inventari/recursmaterial/"+materialResourceRDTO.getCode()+"/baixa")
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
		    	.contentType(MediaType.APPLICATION_JSON_UTF8)
		    	.content("{\r\n" + "	\"code\": \"" + materialResourceRDTO.getCode() + "\"\r\n" + "}"))
		    	.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void caseKoDeleteMaterialResourceBadRequest() throws Exception{
		
		Mockito.when(materialResourceService.delete(Mockito.any(materialResourceRDTO.getClass()))).thenReturn(returnRDTOKO);
    	
		mockMvc.perform(put("/sync/inventari/recursmaterial/"+materialResourceRDTO.getCode()+"/baixa")
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
		    	.contentType(MediaType.APPLICATION_JSON_UTF8)
		    	.content("{\r\n" + "	\"code\": \"" + materialResourceRDTO.getCode() + "\"\r\n" + "}"))
		    	.andDo(print()).andExpect(status().isBadRequest());
    }
	
	@Test
	public void caseOkSaveAggregateAmortizacion() throws Exception {

		Mockito.when(physicalModelService.insert(Mockito.any(aggregateAmortizationRDTO.getClass()))).thenReturn(returnRDTO);

		mockMvc.perform(post("/sync/inventari/mcf/tipus/amortitzacio/agregada").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"codeContract\": \"\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isOk());
	}
	
	@Test
	public void caseKoExceptionSaveAggregateAmortizacion() throws Exception {
		
		Mockito.when(physicalModelService.insert(Mockito.any(aggregateAmortizationRDTO.getClass()))).thenReturn(null);
		mockMvc.perform(post("/sync/inventari/mcf/tipus/amortitzacio/agregada").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"codeContract\": \"\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isInternalServerError());
	}
	@Test
	public void caseKoSaveAggregateAmortizacion() throws Exception {
		
		Mockito.when(physicalModelService.insert(Mockito.any(aggregateAmortizationRDTO.getClass()))).thenReturn(returnRDTOKO);
		
		mockMvc.perform(post("/sync/inventari/mcf/tipus/amortitzacio/agregada").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"codeContract\": \"\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}

	@Test
	public void caseOkSaveInstallation() throws Exception {

		Mockito.when(installationService.insert(Mockito.any(installationRDTO.getClass()))).thenReturn(returnRDTO);

		mockMvc.perform(post("/sync/inventari/installacio").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + installationRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void caseKoSaveInstallationInternalServerError() throws Exception {

		Mockito.when(installationService.insert(installationRDTO)).thenReturn(returnRDTO);

		mockMvc.perform(post("/sync/inventari/installacio").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"codi\": \"" + installationRDTO.getCode() + "\",\r\n"
						+ "	\"codiContracta\": \"C\",\r\n" 
						+ "	\"estat\":\"A\",\r\n" + "	\"tipus\":\"PA\",\r\n"
						+ "	\"descripcio\": \"descripcio\",\r\n"
						+ "	\"userInsert\": \"jrojast\",\r\n" + "	\"superficie\": \"28\",\r\n"
						+ "	\"codiTipusTitularitat\":\"M\",\r\n" 
						+ "	\"localitzacio\":{\r\n" + "				\"codiPostal\":\"08040\",\r\n"
						+ "				\"codiTipusVia\":\"C\",\r\n"
						+ "				\"nomVia\":\"Polgon industrial Zona Franca, Carrer B Sector B\",\r\n"
						+ "				\"numeroFi\":\"16\",\r\n" + "				\"numeroInici\":\"22\",\r\n"
						+ "				\"nomVia\":\"nomVia\"\r\n" + "	}\r\n" + "}"))
				.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseKoSaveInstallationBadRequest() throws Exception {

		Mockito.when(installationService.insert(Mockito.any(installationRDTO.getClass()))).thenReturn(returnRDTOKO);

		mockMvc.perform(post("/sync/inventari/installacio").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + installationRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}	
	
	@Test
    public void caseOkUpdateInstallation() throws Exception{
		
    	Mockito.when(installationService.update(Mockito.any(installationRDTO.getClass()))).thenReturn(returnRDTO);
    	
    	mockMvc.perform(put("/sync/inventari/installacio/"+installationRDTO.getCode())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
    	.content("{\r\n" + "	\"codiContracta\": \"" + installationRDTO.getCodeContract() + "\"\r\n" + "}"))
    	.andDo(print()).andExpect(status().isOk());
    }
	
	@Test
    public void caseKoUpdateInstallationInternalServerError() throws Exception{
		
    	Mockito.when(installationService.update(installationRDTO)).thenReturn(returnRDTO);
    	
    	mockMvc.perform(put("/sync/inventari/installacio/"+installationRDTO.getCode())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
    	.content("{\r\n" + "	\"codiContracta\": \"" + installationRDTO.getCodeContract() + "\"\r\n" + "}"))
    	.andDo(print()).andExpect(status().isInternalServerError());
    }

	@Test
	public void caseKoUpdateInstallationBadRequest() throws Exception {

		Mockito.when(installationService.update(Mockito.any(installationRDTO.getClass()))).thenReturn(returnRDTOKO);

		mockMvc.perform(put("/sync/inventari/installacio/"+installationRDTO.getCode())
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\r\n" + "	\"code\": \"" + installationRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}	
	
	@Test
	public void caseOkDeleteInstallation() throws Exception{
		
		Mockito.when(installationService.delete(Mockito.any(installationRDTO.getClass()))).thenReturn(returnRDTO);
    	
    	mockMvc.perform(put("/sync/inventari/installacio/"+installationRDTO.getCode()+"/baixa")
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
    	.content("{\r\n" + "	\"codiContracta\": \"" + installationRDTO.getCodeContract() + "\"\r\n" + "}"))
    	.andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void caseKoDeleteInstallation() throws Exception{
		
		Mockito.when(installationService.delete(installationRDTO)).thenReturn(returnRDTO);
    	
    	mockMvc.perform(put("/sync/inventari/installacio/"+installationRDTO.getCode()+"/baixa")
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
    	.content("{\r\n" + "	\"codiContracta\": \"" + installationRDTO.getCodeContract() + "\"\r\n" + "}"))
    	.andDo(print()).andExpect(status().isInternalServerError());
	}

	@Test
	public void caseKoDeleteInstallationBadRequest() throws Exception {

		Mockito.when(installationService.delete(Mockito.any(installationRDTO.getClass()))).thenReturn(returnRDTOKO);

		mockMvc.perform(put("/sync/inventari/installacio/"+installationRDTO.getCode()+"/baixa")
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\r\n" + "	\"code\": \"" + installationRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void caseOkSelectInstallationDetailed() throws Exception{
		
		Mockito.when(installationService.selectDetailed(Mockito.any(queryParameterRDTO.getClass()))).thenReturn(returnInstallationDetailedRDTO);
    	mockMvc.perform(get("/sync/inventari/contracta/"+queryParameterRDTO.getCodeContract()+"/installacio/"+queryParameterRDTO.getCode()+"/detallada?dataReferencia="+queryParameterRDTO.getDateReference()+"&codiUsuari="+queryParameterRDTO.getCodeUser())
		.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isOk());
	}
	@Test
	public void caseKoSelectInstallationDetailed() throws Exception{
		
		Mockito.doThrow(ImiException.class).when(installationService).selectDetailed(Mockito.any(queryParameterRDTO.getClass()));
    	
    	mockMvc.perform(get("/sync/inventari/contracta/"+queryParameterRDTO.getCodeContract()+"/installacio/"+queryParameterRDTO.getCode()+"/detallada?dataReferencia="+queryParameterRDTO.getDateReference()+"&codiUsuari="+queryParameterRDTO.getCodeUser())
		.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseOkSelectInstallationMassive() throws Exception{
		
		Mockito.when(installationService.selectMassive(Mockito.any(queryParameterRDTO.getClass()))).thenReturn(returnMassiveRDTO);
    	mockMvc.perform(get("/sync/inventari/contracta/"+queryParameterRDTO.getCodeContract()+"/installacio?codiUsuari="+queryParameterRDTO.getCodeUser()+"&dataReferencia="+queryParameterRDTO.getDateReference())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isOk());
	}
	@Test
	public void caseKoSelectInstallationMassive() throws Exception{
		
		Mockito.doThrow(ImiException.class).when(installationService).selectMassive(Mockito.any(queryParameterRDTO.getClass()));
    	
    	mockMvc.perform(get("/sync/inventari/contracta/"+queryParameterRDTO.getCodeContract()+"/installacio?codiUsuari="+queryParameterRDTO.getCodeUser()+"&dataReferencia="+queryParameterRDTO.getDateReference())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseOkSelectInstallationAmortization() throws Exception{
		
		Mockito.when(installationService.selectAmortization(Mockito.any(queryParameterRDTO.getClass()))).thenReturn(returnBreakdownAmortizationRDTO);
    	mockMvc.perform(get("/sync/inventari/contracta/"+queryParameterRDTO.getCodeContract()+"/installacio/"+queryParameterRDTO.getCode()+"/amortitzacio?codiUsuari="+queryParameterRDTO.getCodeUser())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isOk());
	}
	@Test
	public void caseKoSelectInstallationAmortization() throws Exception{
		
		Mockito.doThrow(ImiException.class).when(installationService).selectAmortization(Mockito.any(queryParameterRDTO.getClass()));
    	
    	mockMvc.perform(get("/sync/inventari/contracta/"+queryParameterRDTO.getCodeContract()+"/installacio/"+queryParameterRDTO.getCode()+"/amortitzacio?codiUsuari="+queryParameterRDTO.getCodeUser())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseOkSelectInstallationExpenses() throws Exception{
		
		Mockito.when(installationService.selectExpenses(Mockito.any(queryParameterRDTO.getClass()))).thenReturn(returnInstallationExpensesRDTO);
    	mockMvc.perform(get("/sync/inventari/contracta/"+queryParameterRDTO.getCodeContract()+"/installacio/"+queryParameterRDTO.getCode()+"/despeses?codiUsuari="+queryParameterRDTO.getCodeUser())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isOk());
	}
	@Test
	public void caseKoSelectInstallationExpenses() throws Exception{
		
		Mockito.doThrow(ImiException.class).when(installationService).selectExpenses(Mockito.any(queryParameterRDTO.getClass()));
    	
    	mockMvc.perform(get("/sync/inventari/contracta/"+queryParameterRDTO.getCodeContract()+"/installacio/"+queryParameterRDTO.getCode()+"/despeses?codiUsuari="+queryParameterRDTO.getCodeUser())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseOkSelectInstallationPeriod() throws Exception{
		
		Mockito.when(installationService.selectPeriod(Mockito.any(queryParameterRDTO.getClass()))).thenReturn(returnInstallationPeriodRDTO);
    	mockMvc.perform(get("/sync/inventari/contracta/"+queryParameterRDTO.getCodeContract()+"/installacio/"+queryParameterRDTO.getCode()+"/periode/"+queryParameterRDTO.getPeriod()+"?codiUsuari="+queryParameterRDTO.getCodeUser())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isOk());
	}
	@Test
	public void caseKoSelectInstallationPeriod() throws Exception{
		
		Mockito.doThrow(ImiException.class).when(installationService).selectPeriod(Mockito.any(queryParameterRDTO.getClass()));
    	
    	mockMvc.perform(get("/sync/inventari/contracta/"+queryParameterRDTO.getCodeContract()+"/installacio/"+queryParameterRDTO.getCode()+"/periode/"+queryParameterRDTO.getPeriod()+"?codiUsuari="+queryParameterRDTO.getCodeUser())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isInternalServerError());
	}

	@Test
	public void caseOkSaveExpenseInstallation() throws Exception {

		Mockito.when(installationService.insertExpense(Mockito.any(installationRDTO.getClass()))).thenReturn(returnRDTO);

		mockMvc.perform(post("/sync/inventari/installacio/despeses").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + installationRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void caseKoSaveExpenseInstallationInternalServerError() throws Exception {

		Mockito.when(installationService.insertExpense(Mockito.any(installationRDTO.getClass()))).thenReturn(null);

		mockMvc.perform(post("/sync/inventari/installacio/despeses").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + installationRDTO.getCode() + "\"\r\n" + "}")) 
				.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseKoSaveExpenseInstallationBadRequest() throws Exception {

		Mockito.when(installationService.insertExpense(Mockito.any(installationRDTO.getClass()))).thenReturn(returnRDTOKO);

		mockMvc.perform(post("/sync/inventari/installacio/despeses").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + installationRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}	

	
	@Test
	public void caseOkSaveApportionmentInstallation() throws Exception {

		Mockito.when(installationService.insertApportionment(Mockito.any(installationRDTO.getClass()))).thenReturn(returnRDTO);

		mockMvc.perform(post("/sync/inventari/installacio/prorrateig").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + installationRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void caseKoSaveApportionmentInstallationInternalServerError() throws Exception {

		Mockito.when(installationService.insertApportionment(Mockito.any(installationRDTO.getClass()))).thenReturn(null);

		mockMvc.perform(post("/sync/inventari/installacio/prorrateig").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + installationRDTO.getCode() + "\"\r\n" + "}")) 
				.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseKoSaveApportionmentInstallationBadRequest() throws Exception {

		Mockito.when(installationService.insertApportionment(Mockito.any(installationRDTO.getClass()))).thenReturn(returnRDTOKO);

		mockMvc.perform(post("/sync/inventari/installacio/prorrateig").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + installationRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}	

	@Test
	public void caseOkSaveAmortizationBaseInstallation() throws Exception {

		Mockito.when(installationService.insertAmortizationBase(Mockito.any(installationRDTO.getClass()))).thenReturn(returnRDTO);

		mockMvc.perform(post("/sync/inventari/installacio/amortitzacio").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + installationRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void caseKoSaveAmortizationBaseInstallationInternalServerError() throws Exception {

		Mockito.when(installationService.insertAmortizationBase(Mockito.any(installationRDTO.getClass()))).thenReturn(null);

		mockMvc.perform(post("/sync/inventari/installacio/amortitzacio").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + installationRDTO.getCode() + "\"\r\n" + "}")) 
				.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseKoSaveAmortizationBaseInstallationBadRequest() throws Exception {

		Mockito.when(installationService.insertAmortizationBase(Mockito.any(installationRDTO.getClass()))).thenReturn(returnRDTOKO);

		mockMvc.perform(post("/sync/inventari/installacio/amortitzacio").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + installationRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void caseOkSaveCommerce() throws Exception {

		Mockito.when(commerceService.insert(Mockito.any(commerceRDTO.getClass()))).thenReturn(returnRDTO);

		mockMvc.perform(post("/sync/inventari/comerc").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + commerceRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void caseKoSaveCommerceInternalServerError() throws Exception {

		Mockito.when(commerceService.insert(commerceRDTO)).thenReturn(returnRDTO);

		mockMvc.perform(post("/sync/inventari/comerc").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + commerceRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseKoSaveCommerceBadRequest() throws Exception {

		Mockito.when(commerceService.insert(Mockito.any(commerceRDTO.getClass()))).thenReturn(returnRDTOKO);

		mockMvc.perform(post("/sync/inventari/comerc").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + commerceRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}	
	
	@Test
    public void caseOkUpdateCommerce() throws Exception{
		
    	Mockito.when(commerceService.update(Mockito.any(commerceRDTO.getClass()))).thenReturn(returnRDTO);
    	
    	mockMvc.perform(put("/sync/inventari/comerc/"+commerceRDTO.getCode())
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
    	.content("{\r\n" + "	\"codiContracta\": \"" + commerceRDTO.getCodeContract() + "\"\r\n" + "}"))
    	.andDo(print()).andExpect(status().isOk());
    }
	
	@Test
    public void caseKoUpdateCommerceInternalServerError() throws Exception{
		
    	Mockito.when(commerceService.update(commerceRDTO)).thenReturn(returnRDTO);
    	
    	mockMvc.perform(put("/sync/inventari/comerc/"+commerceRDTO.getCode())
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
    	.content("{\r\n" + "	\"codiContracta\": \"" + commerceRDTO.getCodeContract() + "\"\r\n" + "}"))
    	.andDo(print()).andExpect(status().isInternalServerError());
    }

	@Test
	public void caseKoUpdateCommerceBadRequest() throws Exception {

		ReturnRDTO returnRDTOKO = new ReturnRDTO();
		returnRDTOKO.setCode(ReturnEnum.CODE_ERROR_FIELD_INCORRECT.getCodeDescription());
		Mockito.when(commerceService.update(Mockito.any(commerceRDTO.getClass()))).thenReturn(returnRDTOKO);

		mockMvc.perform(put("/sync/inventari/comerc/"+commerceRDTO.getCode())
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + commerceRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}	
	
	@Test
	public void caseOkDeleteCommerce() throws Exception{
		
		Mockito.when(commerceService.delete(Mockito.any(commerceRDTO.getClass()))).thenReturn(returnRDTO);
    	
    	mockMvc.perform(put("/sync/inventari/comerc/"+commerceRDTO.getCode()+"/baixa")
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
    	.content("{\r\n" + "	\"codiContracta\": \"" + commerceRDTO.getCodeContract() + "\"\r\n" + "}"))
    	.andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void caseKoDeleteCommerce() throws Exception{
		
		Mockito.when(commerceService.delete(commerceRDTO)).thenReturn(returnRDTO);
    	
    	mockMvc.perform(put("/sync/inventari/comerc/"+commerceRDTO.getCode()+"/baixa")
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
    	.content("{\r\n" + "	\"codiContracta\": \"" + commerceRDTO.getCodeContract() + "\"\r\n" + "}"))
    	.andDo(print()).andExpect(status().isInternalServerError());
	}

	@Test
	public void caseKoDeleteCommerceBadRequest() throws Exception {

		Mockito.when(commerceService.delete(Mockito.any(commerceRDTO.getClass()))).thenReturn(returnRDTOKO);

		mockMvc.perform(put("/sync/inventari/comerc/"+commerceRDTO.getCode()+"/baixa")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + commerceRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void caseOkSaveCommerceElements() throws Exception {

		Mockito.when(commerceService.insertElements(Mockito.any(commerceRDTO.getClass()))).thenReturn(returnRDTO);

		mockMvc.perform(post("/sync/inventari/comerc/elements").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + commerceRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void caseKoSaveCommerceElementsInternalServerError() throws Exception {

		Mockito.when(commerceService.insertElements(commerceRDTO)).thenReturn(returnRDTO);

		mockMvc.perform(post("/sync/inventari/comerc/elements").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + commerceRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseKoSaveCommerceElementsBadRequest() throws Exception {

		Mockito.when(commerceService.insertElements(Mockito.any(commerceRDTO.getClass()))).thenReturn(returnRDTOKO);

		mockMvc.perform(post("/sync/inventari/comerc/elements").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + commerceRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}	
	
	@Test
	public void caseOkSelectCommerceMassive() throws Exception{
		
		Mockito.when(commerceService.selectMassive(Mockito.any(queryParameterRDTO.getClass()))).thenReturn(returnMassiveRDTO);
    	mockMvc.perform(get("/sync/inventari/contracta/"+queryParameterRDTO.getCodeContract()+"/comerc?codiUsuari="+queryParameterRDTO.getCodeUser()+"&dataReferencia="+queryParameterRDTO.getDateReference())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isOk());
	}
	@Test
	public void caseKoSelectCommerceMassive() throws Exception{
		
		Mockito.doThrow(ImiException.class).when(commerceService).selectMassive(Mockito.any(queryParameterRDTO.getClass()));
    	
    	mockMvc.perform(get("/sync/inventari/contracta/"+queryParameterRDTO.getCodeContract()+"/comerc?codiUsuari="+queryParameterRDTO.getCodeUser()+"&dataReferencia="+queryParameterRDTO.getDateReference())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseOkSelectCommerceDetailed() throws Exception{
		
		Mockito.when(commerceService.selectDetailed(Mockito.any(queryParameterRDTO.getClass()))).thenReturn(returnCommerceDetailedRDTO);
    	mockMvc.perform(get("/sync/inventari/contracta/"+queryParameterRDTO.getCodeContract()+"/comerc/"+queryParameterRDTO.getCode()+"/detall?dataReferencia="+queryParameterRDTO.getDateReference()+"&codiUsuari="+queryParameterRDTO.getCodeUser())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isOk());
	}
	@Test
	public void caseKoSelectCommerceDetailed() throws Exception{
		
		Mockito.doThrow(ImiException.class).when(commerceService).selectDetailed(Mockito.any(queryParameterRDTO.getClass()));
    	
    	mockMvc.perform(get("/sync/inventari/contracta/"+queryParameterRDTO.getCodeContract()+"/comerc/"+queryParameterRDTO.getCode()+"/detall?dataReferencia="+queryParameterRDTO.getDateReference()+"&codiUsuari="+queryParameterRDTO.getCodeUser())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseOkSelectCommerceElements() throws Exception{
		
		Mockito.when(commerceService.selectElements(Mockito.any(queryParameterRDTO.getClass()))).thenReturn(returnCommerceElementsRDTO);
    	mockMvc.perform(get("/sync/inventari/contracta/"+queryParameterRDTO.getCodeContract()+"/comerc/"+queryParameterRDTO.getCode()+"/elements?dataReferencia="+queryParameterRDTO.getDateReference()+"&codiUsuari="+queryParameterRDTO.getCodeUser())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isOk());
	}
	@Test
	public void caseKoSelectCommerceElements() throws Exception{
		
		Mockito.doThrow(ImiException.class).when(commerceService).selectElements(Mockito.any(queryParameterRDTO.getClass()));
    	
    	mockMvc.perform(get("/sync/inventari/contracta/"+queryParameterRDTO.getCodeContract()+"/comerc/"+queryParameterRDTO.getCode()+"/elements?dataReferencia="+queryParameterRDTO.getDateReference()+"&codiUsuari="+queryParameterRDTO.getCodeUser())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseOkSaveEmployeeCatalog() throws Exception {

		Mockito.when(employeeCatalogService.insert(Mockito.any(employeeCatalogRDTO.getClass()))).thenReturn(returnRDTO);

		mockMvc.perform(post("/sync/inventari/recursoshumans/cataleg/personal/treballador").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + employeeCatalogRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void caseKoSaveEmployeeCatalogInternalServerError() throws Exception {

		Mockito.when(employeeCatalogService.insert(employeeCatalogRDTO)).thenReturn(returnRDTO);

		mockMvc.perform(post("/sync/inventari/recursoshumans/cataleg/personal/treballador").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + employeeCatalogRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseKoSaveEmployeeCatalogElementsBadRequest() throws Exception {

		Mockito.when(employeeCatalogService.insert(Mockito.any(employeeCatalogRDTO.getClass()))).thenReturn(returnRDTOKO);

		mockMvc.perform(post("/sync/inventari/recursoshumans/cataleg/personal/treballador").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + employeeCatalogRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}	
	
	@Test
	public void caseOkSelectEmployeeCatalogMassive() throws Exception{
		
		Mockito.when(employeeCatalogService.selectMassive(Mockito.any(queryParameterRDTO.getClass()))).thenReturn(returnMassiveRDTO);
    	mockMvc.perform(get("/sync/inventari/contracta/"+queryParameterRDTO.getCodeContract()+"/recursoshumans/cataleg/personal/treballador?codiUsuari="+queryParameterRDTO.getCodeUser()+"&dataReferencia="+queryParameterRDTO.getDateReference())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isOk());
	}
	@Test
	public void caseKoSelectEmployeeCatalogMassive() throws Exception{
		
		Mockito.doThrow(ImiException.class).when(employeeCatalogService).selectMassive(Mockito.any(queryParameterRDTO.getClass()));
    	
    	mockMvc.perform(get("/sync/inventari/contracta/"+queryParameterRDTO.getCodeContract()+"/recursoshumans/cataleg/personal/treballador?codiUsuari="+queryParameterRDTO.getCodeUser()+"&dataReferencia="+queryParameterRDTO.getDateReference())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseOkSelectEmployeeCatalogDetailed() throws Exception{
		
		Mockito.when(employeeCatalogService.selectDetailed(Mockito.any(queryParameterRDTO.getClass()))).thenReturn(returnEmployeeCatalogDetailedRDTO);
    	mockMvc.perform(get("/sync/inventari/contracta/"+queryParameterRDTO.getCodeContract()+"/recursoshumans/cataleg/personal/treballador/detall?codiUsuari="+queryParameterRDTO.getCodeUser()+"&dataReferencia="+queryParameterRDTO.getDateReference())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isOk());
	}
	@Test
	public void caseKoSelectEmployeeCatalogDetailed() throws Exception{
		
		Mockito.doThrow(ImiException.class).when(employeeCatalogService).selectDetailed(Mockito.any(queryParameterRDTO.getClass()));
    	
    	mockMvc.perform(get("/sync/inventari/contracta/"+queryParameterRDTO.getCodeContract()+"/recursoshumans/cataleg/personal/treballador/detall?codiUsuari="+queryParameterRDTO.getCodeUser()+"&dataReferencia="+queryParameterRDTO.getDateReference())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isInternalServerError());
	}	
	
	@Test
	public void caseOkSelectMaterialResourceMassive() throws Exception{
		
		Mockito.when(materialResourceService.selectMassive(Mockito.any(queryParameterRDTO.getClass()))).thenReturn(returnMassiveRDTO);
    	mockMvc.perform(get("/sync/inventari/contracta/"+queryParameterRDTO.getCodeContract()+"/recursmaterial?codiUsuari="+queryParameterRDTO.getCodeUser()+"&dataReferencia="+queryParameterRDTO.getDateReference())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isOk());
	}
	@Test
	public void caseKoSelectMaterialResourceMassive() throws Exception{
		
		Mockito.doThrow(ImiException.class).when(materialResourceService).selectMassive(Mockito.any(queryParameterRDTO.getClass()));
    	
    	mockMvc.perform(get("/sync/inventari/contracta/"+queryParameterRDTO.getCodeContract()+"/recursmaterial?codiUsuari="+queryParameterRDTO.getCodeUser()+"&dataReferencia="+queryParameterRDTO.getDateReference())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseOkSelectMaterialResourceDetailed() throws Exception{
		
		Mockito.when(materialResourceService.selectDetailed(Mockito.any(queryParameterRDTO.getClass()))).thenReturn(returnMaterialResourceDetailedRDTO);
    	mockMvc.perform(get("/sync/inventari/contracta/"+queryParameterRDTO.getCodeContract()+"/recursmaterial/"+queryParameterRDTO.getCode()+"/detall?dataReferencia="+queryParameterRDTO.getDateReference()+"&codiUsuari="+queryParameterRDTO.getCodeUser())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isOk());
	}
	@Test
	public void caseKoSelectMaterialResourceDetailed() throws Exception{
		
		Mockito.doThrow(ImiException.class).when(materialResourceService).selectDetailed(Mockito.any(queryParameterRDTO.getClass()));
    	
    	mockMvc.perform(get("/sync/inventari/contracta/"+queryParameterRDTO.getCodeContract()+"/recursmaterial/"+queryParameterRDTO.getCode()+"/detall?dataReferencia="+queryParameterRDTO.getDateReference()+"&codiUsuari="+queryParameterRDTO.getCodeUser())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseOkSelectMaterialResourceVehicleMassive() throws Exception{
		
		Mockito.when(materialResourceService.selectVehicleMassive(Mockito.any(queryParameterRDTO.getClass()))).thenReturn(returnMassiveRDTO);
    	mockMvc.perform(get("/sync/inventari/contracta/"+queryParameterRDTO.getCodeContract()+"/recursmaterial/vehicle?codiUsuari="+queryParameterRDTO.getCodeUser()+"&dataReferencia="+queryParameterRDTO.getDateReference())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void caseKoSelectMaterialResourceVehicleMassive() throws Exception{
		
		Mockito.doThrow(ImiException.class).when(materialResourceService).selectVehicleMassive(Mockito.any(queryParameterRDTO.getClass()));
    	
    	mockMvc.perform(get("/sync/inventari/contracta/"+queryParameterRDTO.getCodeContract()+"/recursmaterial/vehicle?codiUsuari="+queryParameterRDTO.getCodeUser()+"&dataReferencia="+queryParameterRDTO.getDateReference())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseOkSelectMaterialResourceVehicleDetailed() throws Exception{
		
		Mockito.when(materialResourceService.selectVehicleDetailed(Mockito.any(queryParameterRDTO.getClass()))).thenReturn(returnMaterialResourceVehicleDetailedRDTO);
    	mockMvc.perform(get("/sync/inventari/contracta/"+queryParameterRDTO.getCodeContract()+"/recursmaterial/vehicle/"+queryParameterRDTO.getCode()+"/detall?dataReferencia="+queryParameterRDTO.getDateReference()+"&codiUsuari="+queryParameterRDTO.getCodeUser())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void caseKoSelectMaterialResourceVehicleDetailed() throws Exception{
		
		Mockito.doThrow(ImiException.class).when(materialResourceService).selectVehicleDetailed(Mockito.any(queryParameterRDTO.getClass()));
    	
    	mockMvc.perform(get("/sync/inventari/contracta/"+queryParameterRDTO.getCodeContract()+"/recursmaterial/vehicle/"+queryParameterRDTO.getCode()+"/detall?dataReferencia="+queryParameterRDTO.getDateReference()+"&codiUsuari="+queryParameterRDTO.getCodeUser())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isInternalServerError());
	}

	@Test
	public void caseOkSaveExpenseRRMM() throws Exception {

		Mockito.when(materialResourceService.insertExpense(Mockito.any(materialResourceRDTO.getClass()))).thenReturn(returnRDTO);

		mockMvc.perform(post("/sync/inventari/recursmaterial/despeses").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + materialResourceRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void caseKoSaveExpenseRRMMInternalServerError() throws Exception {

		Mockito.when(materialResourceService.insertExpense(Mockito.any(materialResourceRDTO.getClass()))).thenReturn(null);

		mockMvc.perform(post("/sync/inventari/recursmaterial/despeses").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + materialResourceRDTO.getCode() + "\"\r\n" + "}")) 
				.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseKoSaveExpenseRRMMBadRequest() throws Exception {

		Mockito.when(materialResourceService.insertExpense(Mockito.any(materialResourceRDTO.getClass()))).thenReturn(returnRDTOKO);

		mockMvc.perform(post("/sync/inventari/recursmaterial/despeses").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + materialResourceRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}	

	
	@Test
	public void caseOkSaveApportionmentRRMM() throws Exception {

		Mockito.when(materialResourceService.insertApportionment(Mockito.any(materialResourceRDTO.getClass()))).thenReturn(returnRDTO);

		mockMvc.perform(post("/sync/inventari/recursmaterial/prorrateig").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + materialResourceRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void caseKoSaveApportionmentRRMMInternalServerError() throws Exception {

		Mockito.when(materialResourceService.insertApportionment(Mockito.any(materialResourceRDTO.getClass()))).thenReturn(null);

		mockMvc.perform(post("/sync/inventari/recursmaterial/prorrateig").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + materialResourceRDTO.getCode() + "\"\r\n" + "}")) 
				.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseKoSaveApportionmentRRMMBadRequest() throws Exception {

		Mockito.when(materialResourceService.insertApportionment(Mockito.any(materialResourceRDTO.getClass()))).thenReturn(returnRDTOKO);

		mockMvc.perform(post("/sync/inventari/recursmaterial/prorrateig").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + materialResourceRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}	

	@Test
	public void caseOkSaveAmortizationBaseRRMM() throws Exception {

		Mockito.when(materialResourceService.insertAmortizationBase(Mockito.any(materialResourceRDTO.getClass()))).thenReturn(returnRDTO);

		mockMvc.perform(post("/sync/inventari/recursmaterial/amortitzacio").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + materialResourceRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void caseKoSaveAmortizationBaseRRMMInternalServerError() throws Exception {

		Mockito.when(materialResourceService.insertAmortizationBase(Mockito.any(materialResourceRDTO.getClass()))).thenReturn(null);

		mockMvc.perform(post("/sync/inventari/recursmaterial/amortitzacio").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + materialResourceRDTO.getCode() + "\"\r\n" + "}")) 
				.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseKoSaveAmortizationBaseRRMMBadRequest() throws Exception {

		Mockito.when(materialResourceService.insertAmortizationBase(Mockito.any(materialResourceRDTO.getClass()))).thenReturn(returnRDTOKO);

		mockMvc.perform(post("/sync/inventari/recursmaterial/amortitzacio").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + materialResourceRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void caseOkSelectMaterialResourceVehicleAmortization() throws Exception{
		
		Mockito.when(materialResourceService.selectAmortization(Mockito.any(queryParameterRDTO.getClass()))).thenReturn(returnBreakdownAmortizationRDTO);
    	mockMvc.perform(get("/sync/inventari/contracta/"+queryParameterRDTO.getCodeContract()+"/recursmaterial/vehicle/"+queryParameterRDTO.getCode()+"/amortitzacio?codiUsuari="+queryParameterRDTO.getCodeUser())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isOk());
	}
	@Test
	public void caseKoSelectMaterialResourceVehicleAmortization() throws Exception{
		
		Mockito.doThrow(ImiException.class).when(materialResourceService).selectAmortization(Mockito.any(queryParameterRDTO.getClass()));
    	
    	mockMvc.perform(get("/sync/inventari/contracta/"+queryParameterRDTO.getCodeContract()+"/recursmaterial/vehicle/"+queryParameterRDTO.getCode()+"/amortitzacio?codiUsuari="+queryParameterRDTO.getCodeUser())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseOkSelectMaterialResourceVehicleExpenses() throws Exception{
		
		Mockito.when(materialResourceService.selectExpenses(Mockito.any(queryParameterRDTO.getClass()))).thenReturn(returnMaterialResourceVehicleExpensesRDTO);
    	mockMvc.perform(get("/sync/inventari/contracta/"+queryParameterRDTO.getCodeContract()+"/recursmaterial/vehicle/"+queryParameterRDTO.getCode()+"/despeses?codiUsuari="+queryParameterRDTO.getCodeUser())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isOk());
	}
	@Test
	public void caseKoSelectMaterialResourceVehicleExpenses() throws Exception{
		
		Mockito.doThrow(ImiException.class).when(materialResourceService).selectExpenses(Mockito.any(queryParameterRDTO.getClass()));
    	
    	mockMvc.perform(get("/sync/inventari/contracta/"+queryParameterRDTO.getCodeContract()+"/recursmaterial/vehicle/"+queryParameterRDTO.getCode()+"/despeses?codiUsuari="+queryParameterRDTO.getCodeUser())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseOkSelectMaterialResourceVehiclePeriod() throws Exception{
		
		Mockito.when(materialResourceService.selectPeriod(Mockito.any(queryParameterRDTO.getClass()))).thenReturn(returnMaterialResourceVehiclePeriodRDTO);
    	mockMvc.perform(get("/sync/inventari/contracta/"+queryParameterRDTO.getCodeContract()+"/recursmaterial/vehicle/"+queryParameterRDTO.getCode()+"/periode/"+queryParameterRDTO.getPeriod()+"?codiUsuari="+queryParameterRDTO.getCodeUser())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isOk());
	}
	@Test
	public void caseKoSelectMaterialResourceVehiclePeriod() throws Exception{
		
		Mockito.doThrow(ImiException.class).when(materialResourceService).selectPeriod(Mockito.any(queryParameterRDTO.getClass()));
    	
    	mockMvc.perform(get("/sync/inventari/contracta/"+queryParameterRDTO.getCodeContract()+"/recursmaterial/vehicle/"+queryParameterRDTO.getCode()+"/periode/"+queryParameterRDTO.getPeriod()+"?codiUsuari="+queryParameterRDTO.getCodeUser())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseOkSelectUbicationMassive() throws Exception{
		
		Mockito.when(ubicationService.selectMassive(Mockito.any(queryParameterRDTO.getClass()))).thenReturn(returnMassiveRDTO);
    	mockMvc.perform(get("/sync/inventari/contracta/"+queryParameterRDTO.getCodeContract()+"/ubicacio?codiUsuari="+queryParameterRDTO.getCodeUser()+"&dataReferencia="+queryParameterRDTO.getDateReference())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isOk());
	}
	@Test
	public void caseKoSelectUbicationMassive() throws Exception{
		
		Mockito.doThrow(ImiException.class).when(ubicationService).selectMassive(Mockito.any(queryParameterRDTO.getClass()));
    	
    	mockMvc.perform(get("/sync/inventari/contracta/"+queryParameterRDTO.getCodeContract()+"/ubicacio?codiUsuari="+queryParameterRDTO.getCodeUser()+"&dataReferencia="+queryParameterRDTO.getDateReference())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseOkSelectUbicationDetailed() throws Exception{
		
		Mockito.when(ubicationService.selectDetailed(Mockito.any(queryParameterRDTO.getClass()))).thenReturn(returnUbicationDetailedRDTO);
    	mockMvc.perform(get("/sync/inventari/contracta/"+queryParameterRDTO.getCodeContract()+"/ubicacio/"+queryParameterRDTO.getCode()+"/detall?dataReferencia="+queryParameterRDTO.getDateReference()+"&codiUsuari="+queryParameterRDTO.getCodeUser())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isOk());
	}
	@Test
	public void caseKoSelectUbicationDetailed() throws Exception{
		
		Mockito.doThrow(ImiException.class).when(ubicationService).selectDetailed(Mockito.any(queryParameterRDTO.getClass()));
    	
    	mockMvc.perform(get("/sync/inventari/contracta/"+queryParameterRDTO.getCodeContract()+"/ubicacio/"+queryParameterRDTO.getCode()+"/detall?dataReferencia="+queryParameterRDTO.getDateReference()+"&codiUsuari="+queryParameterRDTO.getCodeUser())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseOkSelectLogicalModelMassive() throws Exception{
		
		Mockito.when(logicalModelService.selectMassive(Mockito.any(queryParameterRDTO.getClass()))).thenReturn(returnLogicalModelMassiveRDTO);
    	mockMvc.perform(get("/sync/inventari/contracta/"+queryParameterRDTO.getCodeContract()+"/mobiliarilogic?codiUsuari="+queryParameterRDTO.getCodeUser()+"&dataReferencia="+queryParameterRDTO.getDateReference())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isOk());
	}
	@Test
	public void caseKoSelectLogicalModelMassive() throws Exception{
		
		Mockito.doThrow(ImiException.class).when(logicalModelService).selectMassive(Mockito.any(queryParameterRDTO.getClass()));
    	
    	mockMvc.perform(get("/sync/inventari/contracta/"+queryParameterRDTO.getCodeContract()+"/mobiliarilogic?codiUsuari="+queryParameterRDTO.getCodeUser()+"&dataReferencia="+queryParameterRDTO.getDateReference())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseOkSelectLogicalModelDetailed() throws Exception{
		
		Mockito.when(logicalModelService.selectDetailed(Mockito.any(queryParameterRDTO.getClass()))).thenReturn(returnLogicalModelDetailedRDTO);
    	mockMvc.perform(get("/sync/inventari/contracta/"+queryParameterRDTO.getCodeContract()+"/mobiliarilogic/"+queryParameterRDTO.getCode()+"/detall?dataReferencia="+queryParameterRDTO.getDateReference()+"&codiUsuari="+queryParameterRDTO.getCodeUser())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isOk());
	}	@Test
	public void caseKoSelectLogicalModelDetailed() throws Exception{
		
		Mockito.doThrow(ImiException.class).when(logicalModelService).selectDetailed(Mockito.any(queryParameterRDTO.getClass()));
    	
    	mockMvc.perform(get("/sync/inventari/contracta/"+queryParameterRDTO.getCodeContract()+"/mobiliarilogic/"+queryParameterRDTO.getCode()+"/detall?dataReferencia="+queryParameterRDTO.getDateReference()+"&codiUsuari="+queryParameterRDTO.getCodeUser())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseOkSelectPhysicalModelMassive() throws Exception{
		
		Mockito.when(physicalModelService.selectMassive(Mockito.any(queryParameterRDTO.getClass()))).thenReturn(returnPhysicalModelMassiveRDTO);
    	mockMvc.perform(get("/sync/inventari/contracta/"+queryParameterRDTO.getCodeContract()+"/mobiliarifisic?codiUsuari="+queryParameterRDTO.getCodeUser()+"&dataReferencia="+queryParameterRDTO.getDateReference())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void caseKoSelectPhysicalModelMassive() throws Exception{
		
		Mockito.doThrow(ImiException.class).when(physicalModelService).selectMassive(Mockito.any(queryParameterRDTO.getClass()));
    	
    	mockMvc.perform(get("/sync/inventari/contracta/"+queryParameterRDTO.getCodeContract()+"/mobiliarifisic?codiUsuari="+queryParameterRDTO.getCodeUser()+"&dataReferencia="+queryParameterRDTO.getDateReference())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseOkSelectPhysicalModelDetailed() throws Exception{
		
		Mockito.when(physicalModelService.selectDetailed(Mockito.any(queryParameterRDTO.getClass()))).thenReturn(returnPhysicalModelDetailedRDTO);
    	mockMvc.perform(get("/sync/inventari/contracta/"+queryParameterRDTO.getCodeContract()+"/mobiliarifisic/"+queryParameterRDTO.getCode()+"/detall?dataReferencia="+queryParameterRDTO.getDateReference()+"&codiUsuari="+queryParameterRDTO.getCodeUser())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void caseKoSelectPhysicalModelDetailed() throws Exception{
		
		Mockito.doThrow(ImiException.class).when(physicalModelService).selectDetailed(Mockito.any(queryParameterRDTO.getClass()));
    	
    	mockMvc.perform(get("/sync/inventari/contracta/"+queryParameterRDTO.getCodeContract()+"/mobiliarifisic/"+queryParameterRDTO.getCode()+"/detall?dataReferencia="+queryParameterRDTO.getDateReference()+"&codiUsuari="+queryParameterRDTO.getCodeUser())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void caseOkSelectPhysicalModelAmortization() throws Exception{
		
		Mockito.when(physicalModelService.selectAmortization(Mockito.any(queryParameterRDTO.getClass()))).thenReturn(returnPhysicalModelAmortizationRDTO);
		mockMvc.perform(get("/sync/inventari/contracta/"+queryParameterRDTO.getCodeContract()+"/mobiliarifisic/amortitzacio/fraccio/"+queryParameterRDTO.getCodeFraction()+"/territori/"+queryParameterRDTO.getCodeTerritory()+"/tipus/"+queryParameterRDTO.getCodeType()+"/grup/"+queryParameterRDTO.getCodeGroup()+"?codiUsuari="+queryParameterRDTO.getCodeUser())
		.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isOk());
	}
	@Test
	public void caseKoSelectPhysicalModelAmortization() throws Exception{
		
		Mockito.doThrow(ImiException.class).when(physicalModelService).selectAmortization(Mockito.any(queryParameterRDTO.getClass()));
    	mockMvc.perform(get("/sync/inventari/contracta/"+queryParameterRDTO.getCodeContract()+"/mobiliarifisic/amortitzacio/fraccio/"+queryParameterRDTO.getCodeFraction()+"/territori/"+queryParameterRDTO.getCodeTerritory()+"/tipus/"+queryParameterRDTO.getCodeType()+"/grup/"+queryParameterRDTO.getCodeGroup()+"?codiUsuari="+queryParameterRDTO.getCodeUser())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd"))
    	.andDo(print()).andExpect(status().isInternalServerError());
	}

	@Test
    public void caseOkDeleteDocumentarySupportUbication() throws Exception{
		
    	Mockito.when(ubicationService.deleteDocumentalSupport(Mockito.any(DocumentarySupportRDTO.class))).thenReturn(returnRDTO);
    	
    	mockMvc.perform(put("/sync/inventari/ubicacio/"+ubicationRDTO.getCode()+"/suportdocumental/"+documentarySupportRDTO.getCode())
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
    	.content("{\r\n" + "	\"code\": \"" + documentarySupportRDTO.getCode() + "\"\r\n" + "}"))
    	.andDo(print()).andExpect(status().isOk());
    }

	@Test
    public void caseKoDeleteDocumentarySupportUbication() throws Exception{
		
    	Mockito.when(ubicationService.deleteDocumentalSupport(documentarySupportRDTO)).thenReturn(returnRDTO);
    	
    	mockMvc.perform(put("/sync/inventari/ubicacio/"+ubicationRDTO.getCode()+"/suportdocumental/"+documentarySupportRDTO.getCode())
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
    	.content("{\r\n" + "	\"code\": \"" + documentarySupportRDTO.getCode() + "\"\r\n" + "}"))
    	.andDo(print()).andExpect(status().isInternalServerError());
    }


	@Test
	public void caseKODeleteDocumentarySupportUbicationBadRequest() throws Exception {

		Mockito.when(ubicationService.deleteDocumentalSupport(Mockito.any(documentarySupportRDTO.getClass()))).thenReturn(returnRDTOKO);

		mockMvc.perform(put("/sync/inventari/ubicacio/"+ubicationRDTO.getCode()+"/suportdocumental/"+documentarySupportRDTO.getCode())
		    	.contentType(MediaType.APPLICATION_JSON_UTF8)
		    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
		    	.content("{\r\n" + "	\"code\": \"" + ubicationRDTO.getCode() + "\"\r\n" + "}"))
		    	.andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
    public void caseOkDeleteDocumentarySupportMCL() throws Exception{
		
    	Mockito.when(logicalModelService.deleteDocumentalSupport(Mockito.any(DocumentarySupportRDTO.class))).thenReturn(returnRDTO);
    	
    	mockMvc.perform(put("/sync/inventari/ubicacio/"+ubicationRDTO.getCode()+"/mcl/"+logicalModelRDTO.getCode()+"/suportdocumental/"+documentarySupportRDTO.getCode())
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
    	.content("{\r\n" + "	\"code\": \"" + documentarySupportRDTO.getCode() + "\"\r\n" + "}"))
    	.andDo(print()).andExpect(status().isOk());
    }

	@Test
    public void caseKoDeleteDocumentarySupportMCL() throws Exception{
		
    	Mockito.when(logicalModelService.deleteDocumentalSupport(documentarySupportRDTO)).thenReturn(returnRDTO);
    	
    	mockMvc.perform(put("/sync/inventari/ubicacio/"+ubicationRDTO.getCode()+"/mcl/"+logicalModelRDTO.getCode()+"/suportdocumental/"+documentarySupportRDTO.getCode())
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
    	.content("{\r\n" + "	\"code\": \"" + documentarySupportRDTO.getCode() + "\"\r\n" + "}"))
    	.andDo(print()).andExpect(status().isInternalServerError());
    }


	@Test
	public void caseKODeleteDocumentarySupportMCLBadRequest() throws Exception {

		Mockito.when(logicalModelService.deleteDocumentalSupport(Mockito.any(documentarySupportRDTO.getClass()))).thenReturn(returnRDTOKO);

		mockMvc.perform(put("/sync/inventari/ubicacio/"+ubicationRDTO.getCode()+"/mcl/"+logicalModelRDTO.getCode()+"/suportdocumental/"+documentarySupportRDTO.getCode())
		    	.contentType(MediaType.APPLICATION_JSON_UTF8)
		    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
		    	.content("{\r\n" + "	\"code\": \"" + documentarySupportRDTO.getCode() + "\"\r\n" + "}"))
		    	.andDo(print()).andExpect(status().isBadRequest());
	}
	
	@Test
    public void caseOkDeleteDocumentarySupportMCF() throws Exception{
		
    	Mockito.when(physicalModelService.deleteDocumentalSupport(Mockito.any(DocumentarySupportRDTO.class))).thenReturn(returnRDTO);
    	
    	mockMvc.perform(put("/sync/inventari/ubicacio/"+ubicationRDTO.getCode()+"/mcl/mcf/"+physicalModelRDTO.getCode()+"/suportdocumental/"+documentarySupportRDTO.getCode())
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
    	.content("{\r\n" + "	\"code\": \"" + documentarySupportRDTO.getCode() + "\"\r\n" + "}"))
    	.andDo(print()).andExpect(status().isOk());
    }

	@Test
    public void caseKoDeleteDocumentarySupportMCF() throws Exception{
		
    	Mockito.when(physicalModelService.deleteDocumentalSupport(documentarySupportRDTO)).thenReturn(returnRDTO);
    	
    	mockMvc.perform(put("/sync/inventari/ubicacio/"+ubicationRDTO.getCode()+"/mcl/mcf/"+physicalModelRDTO.getCode()+"/suportdocumental/"+documentarySupportRDTO.getCode())
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
    	.content("{\r\n" + "	\"code\": \"" + documentarySupportRDTO.getCode() + "\"\r\n" + "}"))
    	.andDo(print()).andExpect(status().isInternalServerError());
    }


	@Test
	public void caseKODeleteDocumentarySupportMCFBadRequest() throws Exception {

		Mockito.when(physicalModelService.deleteDocumentalSupport(Mockito.any(documentarySupportRDTO.getClass()))).thenReturn(returnRDTOKO);

		mockMvc.perform(put("/sync/inventari/ubicacio/"+ubicationRDTO.getCode()+"/mcl/mcf/"+physicalModelRDTO.getCode()+"/suportdocumental/"+documentarySupportRDTO.getCode())
		    	.contentType(MediaType.APPLICATION_JSON_UTF8)
		    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
		    	.content("{\r\n" + "	\"code\": \"" + documentarySupportRDTO.getCode() + "\"\r\n" + "}"))
		    	.andDo(print()).andExpect(status().isBadRequest());
	}
	
	@Test
    public void caseOkDeleteDocumentarySupportRRMM() throws Exception{
		
    	Mockito.when(materialResourceService.deleteDocumentalSupport(Mockito.any(DocumentarySupportRDTO.class))).thenReturn(returnRDTO);
    	
    	mockMvc.perform(put("/sync/inventari/recursmaterial/"+materialResourceRDTO.getCode()+"/suportdocumental/"+documentarySupportRDTO.getCode())
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
    	.content("{\r\n" + "	\"code\": \"" + documentarySupportRDTO.getCode() + "\"\r\n" + "}"))
    	.andDo(print()).andExpect(status().isOk());
    }

	@Test
    public void caseKoDeleteDocumentarySupportRRMM() throws Exception{
		
    	Mockito.when(materialResourceService.deleteDocumentalSupport(documentarySupportRDTO)).thenReturn(returnRDTO);
    	
    	mockMvc.perform(put("/sync/inventari/recursmaterial/"+materialResourceRDTO.getCode()+"/suportdocumental/"+documentarySupportRDTO.getCode())
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
    	.content("{\r\n" + "	\"code\": \"" + documentarySupportRDTO.getCode() + "\"\r\n" + "}"))
    	.andDo(print()).andExpect(status().isInternalServerError());
    }


	@Test
	public void caseKODeleteDocumentarySupportRRMMBadRequest() throws Exception {

		Mockito.when(materialResourceService.deleteDocumentalSupport(Mockito.any(documentarySupportRDTO.getClass()))).thenReturn(returnRDTOKO);

		mockMvc.perform(put("/sync/inventari/recursmaterial/"+materialResourceRDTO.getCode()+"/suportdocumental/"+documentarySupportRDTO.getCode())
		    	.contentType(MediaType.APPLICATION_JSON_UTF8)
		    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
		    	.content("{\r\n" + "	\"code\": \"" + documentarySupportRDTO.getCode() + "\"\r\n" + "}"))
		    	.andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
    public void caseOkDeleteActuationUbication() throws Exception{
		
    	Mockito.when(ubicationService.deleteActuation(Mockito.any(ActuationRDTO.class))).thenReturn(returnRDTO);
    	
    	mockMvc.perform(put("/sync/inventari/ubicacio/"+ubicationRDTO.getCode()+"/actuacio/"+actuationRDTO.getCode())
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
    	.content("{\r\n" + "	\"code\": \"" + actuationRDTO.getCode() + "\"\r\n" + "}"))
    	.andDo(print()).andExpect(status().isOk());
    }

	@Test
    public void caseKoDeleteActuationUbication() throws Exception{
		
    	Mockito.when(ubicationService.deleteActuation(actuationRDTO)).thenReturn(returnRDTO);
    	
    	mockMvc.perform(put("/sync/inventari/ubicacio/"+ubicationRDTO.getCode()+"/actuacio/"+actuationRDTO.getCode())
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
    	.content("{\r\n" + "	\"code\": \"" + actuationRDTO.getCode() + "\"\r\n" + "}"))
    	.andDo(print()).andExpect(status().isInternalServerError());
    }


	@Test
	public void caseKODeleteActuationUbicationBadRequest() throws Exception {

		Mockito.when(ubicationService.deleteActuation(Mockito.any(actuationRDTO.getClass()))).thenReturn(returnRDTOKO);

		mockMvc.perform(put("/sync/inventari/ubicacio/"+ubicationRDTO.getCode()+"/actuacio/"+actuationRDTO.getCode())
		    	.contentType(MediaType.APPLICATION_JSON_UTF8)
		    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
		    	.content("{\r\n" + "	\"code\": \"" + ubicationRDTO.getCode() + "\"\r\n" + "}"))
		    	.andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
    public void caseOkDeleteActuationMCL() throws Exception{
		
    	Mockito.when(logicalModelService.deleteActuation(Mockito.any(ActuationRDTO.class))).thenReturn(returnRDTO);
    	
    	mockMvc.perform(put("/sync/inventari/ubicacio/"+ubicationRDTO.getCode()+"/mcl/"+logicalModelRDTO.getCode()+"/actuacio/"+actuationRDTO.getCode())
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
    	.content("{\r\n" + "	\"code\": \"" + actuationRDTO.getCode() + "\"\r\n" + "}"))
    	.andDo(print()).andExpect(status().isOk());
    }

	@Test
    public void caseKoDeleteActuationMCL() throws Exception{
		
    	Mockito.when(logicalModelService.deleteActuation(actuationRDTO)).thenReturn(returnRDTO);
    	
    	mockMvc.perform(put("/sync/inventari/ubicacio/"+ubicationRDTO.getCode()+"/mcl/"+logicalModelRDTO.getCode()+"/actuacio/"+actuationRDTO.getCode())
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
    	.content("{\r\n" + "	\"code\": \"" + actuationRDTO.getCode() + "\"\r\n" + "}"))
    	.andDo(print()).andExpect(status().isInternalServerError());
    }


	@Test
	public void caseKODeleteActuationMCLBadRequest() throws Exception {

		Mockito.when(logicalModelService.deleteActuation(Mockito.any(actuationRDTO.getClass()))).thenReturn(returnRDTOKO);

		mockMvc.perform(put("/sync/inventari/ubicacio/"+ubicationRDTO.getCode()+"/mcl/"+logicalModelRDTO.getCode()+"/actuacio/"+actuationRDTO.getCode())
		    	.contentType(MediaType.APPLICATION_JSON_UTF8)
		    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
		    	.content("{\r\n" + "	\"code\": \"" + actuationRDTO.getCode() + "\"\r\n" + "}"))
		    	.andDo(print()).andExpect(status().isBadRequest());
	}
	
	@Test
    public void caseOkDeleteActuationMCF() throws Exception{
		
    	Mockito.when(physicalModelService.deleteActuation(Mockito.any(ActuationRDTO.class))).thenReturn(returnRDTO);
    	
    	mockMvc.perform(put("/sync/inventari/ubicacio/"+ubicationRDTO.getCode()+"/mcl/mcf/"+physicalModelRDTO.getCode()+"/actuacio/"+actuationRDTO.getCode())
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
    	.content("{\r\n" + "	\"code\": \"" + actuationRDTO.getCode() + "\"\r\n" + "}"))
    	.andDo(print()).andExpect(status().isOk());
    }

	@Test
    public void caseKoDeleteActuationMCF() throws Exception{
		
    	Mockito.when(physicalModelService.deleteActuation(actuationRDTO)).thenReturn(returnRDTO);
    	
    	mockMvc.perform(put("/sync/inventari/ubicacio/"+ubicationRDTO.getCode()+"/mcl/mcf/"+physicalModelRDTO.getCode()+"/actuacio/"+actuationRDTO.getCode())
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
    	.content("{\r\n" + "	\"code\": \"" + actuationRDTO.getCode() + "\"\r\n" + "}"))
    	.andDo(print()).andExpect(status().isInternalServerError());
    }


	@Test
	public void caseKODeleteActuationMCFBadRequest() throws Exception {

		Mockito.when(physicalModelService.deleteActuation(Mockito.any(actuationRDTO.getClass()))).thenReturn(returnRDTOKO);

		mockMvc.perform(put("/sync/inventari/ubicacio/"+ubicationRDTO.getCode()+"/mcl/mcf/"+physicalModelRDTO.getCode()+"/actuacio/"+actuationRDTO.getCode())
		    	.contentType(MediaType.APPLICATION_JSON_UTF8)
		    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
		    	.content("{\r\n" + "	\"code\": \"" + actuationRDTO.getCode() + "\"\r\n" + "}"))
		    	.andDo(print()).andExpect(status().isBadRequest());
	}
	
	@Test
    public void caseOkDeleteActuationRRMM() throws Exception{
		
    	Mockito.when(materialResourceService.deleteActuation(Mockito.any(ActuationRDTO.class))).thenReturn(returnRDTO);
    	
    	mockMvc.perform(put("/sync/inventari/recursmaterial/"+materialResourceRDTO.getCode()+"/actuacio/"+actuationRDTO.getCode())
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
    	.content("{\r\n" + "	\"code\": \"" + actuationRDTO.getCode() + "\"\r\n" + "}"))
    	.andDo(print()).andExpect(status().isOk());
    }

	@Test
    public void caseKoDeleteActuationRRMM() throws Exception{
		
    	Mockito.when(materialResourceService.deleteActuation(actuationRDTO)).thenReturn(returnRDTO);
    	
    	mockMvc.perform(put("/sync/inventari/recursmaterial/"+materialResourceRDTO.getCode()+"/actuacio/"+actuationRDTO.getCode())
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
    	.content("{\r\n" + "	\"code\": \"" + actuationRDTO.getCode() + "\"\r\n" + "}"))
    	.andDo(print()).andExpect(status().isInternalServerError());
    }


	@Test
	public void caseKODeleteActuationRRMMBadRequest() throws Exception {

		Mockito.when(materialResourceService.deleteActuation(Mockito.any(actuationRDTO.getClass()))).thenReturn(returnRDTOKO);

		mockMvc.perform(put("/sync/inventari/recursmaterial/"+materialResourceRDTO.getCode()+"/actuacio/"+actuationRDTO.getCode())
		    	.contentType(MediaType.APPLICATION_JSON_UTF8)
		    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
		    	.content("{\r\n" + "	\"code\": \"" + actuationRDTO.getCode() + "\"\r\n" + "}"))
		    	.andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
    public void caseOkDeleteActuationInstallation() throws Exception{
		
    	Mockito.when(installationService.deleteActuation(Mockito.any(ActuationRDTO.class))).thenReturn(returnRDTO);
    	
    	mockMvc.perform(put("/sync/inventari/installacio/"+installationRDTO.getCode()+"/actuacio/"+actuationRDTO.getCode())
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
    	.content("{\r\n" + "	\"code\": \"" + actuationRDTO.getCode() + "\"\r\n" + "}"))
    	.andDo(print()).andExpect(status().isOk());
    }

	@Test
    public void caseKoDeleteActuationInstallation() throws Exception{
		
    	Mockito.when(installationService.deleteActuation(actuationRDTO)).thenReturn(returnRDTO);
    	
    	mockMvc.perform(put("/sync/inventari/installacio/"+installationRDTO.getCode()+"/actuacio/"+actuationRDTO.getCode())
    	.contentType(MediaType.APPLICATION_JSON_UTF8)
    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
    	.content("{\r\n" + "	\"code\": \"" + actuationRDTO.getCode() + "\"\r\n" + "}"))
    	.andDo(print()).andExpect(status().isInternalServerError());
    }


	@Test
	public void caseKODeleteActuationInstallationBadRequest() throws Exception {

		Mockito.when(installationService.deleteActuation(Mockito.any(actuationRDTO.getClass()))).thenReturn(returnRDTOKO);

		mockMvc.perform(put("/sync/inventari/installacio/"+installationRDTO.getCode()+"/actuacio/"+actuationRDTO.getCode())
		    	.contentType(MediaType.APPLICATION_JSON_UTF8)
		    	.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
		    	.content("{\r\n" + "	\"code\": \"" + actuationRDTO.getCode() + "\"\r\n" + "}"))
		    	.andDo(print()).andExpect(status().isBadRequest());
	}

	
	@Test
	public void caseOkSaveActuationUbication() throws Exception {

		Mockito.when(ubicationService.insertActuation(Mockito.any(actuationRDTO.getClass()))).thenReturn(returnRDTO);

		mockMvc.perform(post("/sync/inventari/ubicacio/actuacio").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + ubicationRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void caseKOSaveActuationUbicationBadRequest() throws Exception {

		Mockito.when(ubicationService.insertActuation(Mockito.any(actuationRDTO.getClass()))).thenReturn(returnRDTOKO);

		mockMvc.perform(post("/sync/inventari/ubicacio/actuacio").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + ubicationRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}
	@Test
	public void caseKoSaveActuationUbicationException() throws Exception {

		Mockito.when(ubicationService.insertActuation(actuationRDTO)).thenReturn(returnRDTO);
		mockMvc.perform(post("/sync/inventari/ubicacio/actuacio").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + ubicationRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isInternalServerError());
	}

	@Test
	public void caseOkSaveActuationMCL() throws Exception {

		Mockito.when(logicalModelService.insertActuation(Mockito.any(actuationRDTO.getClass()))).thenReturn(returnRDTO);

		mockMvc.perform(post("/sync/inventari/ubicacio/mcl/actuacio").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + logicalModelRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void caseKOSaveActuationMCLBadRequest() throws Exception {

		Mockito.when(logicalModelService.insertActuation(Mockito.any(actuationRDTO.getClass()))).thenReturn(returnRDTOKO);

		mockMvc.perform(post("/sync/inventari/ubicacio/mcl/actuacio").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + logicalModelRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}
	@Test
	public void caseKoSaveActuationMCLException() throws Exception {

		Mockito.when(logicalModelService.insertActuation(actuationRDTO)).thenReturn(returnRDTO);
		mockMvc.perform(post("/sync/inventari/ubicacio/mcl/actuacio").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + logicalModelRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isInternalServerError());
	}

	@Test
	public void caseOkSaveActuationMCF() throws Exception {

		Mockito.when(physicalModelService.insertActuation(Mockito.any(actuationRDTO.getClass()))).thenReturn(returnRDTO);

		mockMvc.perform(post("/sync/inventari/ubicacio/mcl/mcf/actuacio").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + physicalModelRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void caseKOSaveActuationMCFBadRequest() throws Exception {

		Mockito.when(physicalModelService.insertActuation(Mockito.any(actuationRDTO.getClass()))).thenReturn(returnRDTOKO);

		mockMvc.perform(post("/sync/inventari/ubicacio/mcl/mcf/actuacio").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + physicalModelRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}
	@Test
	public void caseKoSaveActuationMCFException() throws Exception {

		Mockito.when(physicalModelService.insertActuation(actuationRDTO)).thenReturn(returnRDTO);
		mockMvc.perform(post("/sync/inventari/ubicacio/mcl/mcf/actuacio").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + physicalModelRDTO.getCode() + "\"\r\n"+ "}"))
				.andDo(print()).andExpect(status().isInternalServerError());
	}

	@Test
	public void caseOkSaveActuationRRMM() throws Exception {

		Mockito.when(materialResourceService.insertActuation(Mockito.any(actuationRDTO.getClass()))).thenReturn(returnRDTO);

		mockMvc.perform(post("/sync/inventari/recursmaterial/actuacio").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + materialResourceRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void caseKOSaveActuationRRMMBadRequest() throws Exception {

		Mockito.when(materialResourceService.insertActuation(Mockito.any(actuationRDTO.getClass()))).thenReturn(returnRDTOKO);

		mockMvc.perform(post("/sync/inventari/recursmaterial/actuacio").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + materialResourceRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}
	@Test
	public void caseKoSaveActuationRRMMException() throws Exception {

		Mockito.when(materialResourceService.insertActuation(actuationRDTO)).thenReturn(returnRDTO);
		mockMvc.perform(post("/sync/inventari/recursmaterial/actuacio").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + materialResourceRDTO.getCode() + "\"\r\n"+ "}"))
				.andDo(print()).andExpect(status().isInternalServerError());
	}

	@Test
	public void caseOkSaveActuationInstallation() throws Exception {

		Mockito.when(installationService.insertActuation(Mockito.any(actuationRDTO.getClass()))).thenReturn(returnRDTO);

		mockMvc.perform(post("/sync/inventari/installacio/actuacio").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + installationRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void caseKOSaveActuationInstallationBadRequest() throws Exception {

		Mockito.when(installationService.insertActuation(Mockito.any(actuationRDTO.getClass()))).thenReturn(returnRDTOKO);

		mockMvc.perform(post("/sync/inventari/installacio/actuacio").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + installationRDTO.getCode() + "\"\r\n" + "}"))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}
	@Test
	public void caseKoSaveActuationInstallationException() throws Exception {

		Mockito.when(installationService.insertActuation(actuationRDTO)).thenReturn(returnRDTO);
		mockMvc.perform(post("/sync/inventari/installacio/actuacio").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(ImiConstants.HEADER_X_IBM_CLIENT_ID, "abcd")
				.content("{\r\n" + "	\"code\": \"" + installationRDTO.getCode() + "\"\r\n"+ "}"))
				.andDo(print()).andExpect(status().isInternalServerError());
	}

}
	