package es.bcn.imi.framework.vigia.orquestrador.test.web.rest.controller.sync;

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

import es.bcn.imi.framework.vigia.orquestrador.business.inventary.ActuationService;
import es.bcn.imi.framework.vigia.orquestrador.business.inventary.CommerceService;
import es.bcn.imi.framework.vigia.orquestrador.business.inventary.DocumentarySupportService;
import es.bcn.imi.framework.vigia.orquestrador.business.inventary.EmployeeCatalogService;
import es.bcn.imi.framework.vigia.orquestrador.business.inventary.InstallationService;
import es.bcn.imi.framework.vigia.orquestrador.business.inventary.LogicalModelService;
import es.bcn.imi.framework.vigia.orquestrador.business.inventary.MaterialResourceService;
import es.bcn.imi.framework.vigia.orquestrador.business.inventary.PhysicalModelService;
import es.bcn.imi.framework.vigia.orquestrador.business.inventary.UbicationService;
import es.bcn.imi.framework.vigia.orquestrador.test.parent.RestServerParentTest;
import es.bcn.imi.framework.vigia.orquestrador.web.rest.controller.sync.InventaryController;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.ActuationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CommerceRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.DocumentarySupportRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.EmployeeCatalogRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.UbicationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnBreakdownAmortizationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnCommerceDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnCommerceElementsRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnEmployeeCatalogDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnInstallationDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnInstallationExpensesRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnInstallationPeriodRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMaterialResourceDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMaterialResourceVehicleDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMaterialResourceVehicleExpensesRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMaterialResourceVehiclePeriodRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnLogicalModelDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnLogicalModelMassiveRDTO;
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
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnLogicalModelRDTOStub;
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
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnPhysicalModelRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnUbicationDetailedRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.stub.EmployeeCatalogRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.ActuationRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.AggregateAmortizationRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.CommerceRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.DocumentarySupportRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.InstallationRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.LogicalModelRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.MaterialResourceRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.PhysicalModelRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.QueryParameterRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.UbicationRDTOStub;
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

	@Mock
	private DocumentarySupportService documentarySupportService;

	@Mock
	private ActuationService actuationService;

	@InjectMocks
	private InventaryController controller;

	private ReturnRDTO returnRDTO;

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

	private QueryParameterRDTO queryParameterRDTO;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		returnRDTO = ReturnRDTOStub.getSuccessMessage();
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
	}

	@Test
	public void caseOkUbicationSyncInsert() throws Exception {
		UbicationRDTO ubicationRDTOin = new UbicationRDTO();
		Mockito.when(ubicationService.insert(ubicationRDTOin)).thenReturn(returnRDTO);

		mockMvc.perform(post("/sync/inventari/ubicacio").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\r\n" + "	\"code\": \"WSTD234\",\r\n" + "	\"coordinatesX\": 100001,\r\n"
						+ "	\"coordinatesY\": 200001,\r\n" + "	\"coordinates_1_X\": 100001,\r\n"
						+ "	\"coordinates_1_Y\": 200001,\r\n" + "	\"coordinates_2_X\": 300001,\r\n"
						+ "	\"coordinates_2_Y\": 400001,\r\n" + "	\"coordinates_3_X\": 500001,\r\n"
						+ "	\"coordinates_3_Y\": 600001,\r\n" + "	\"coordinates_4_X\": 700001,\r\n"
						+ "	\"coordinates_4_Y\": 800001,\r\n" + "	\"startDate\":\"30/05/2021 00:00:00\",\r\n"
						+ "	\"endDate\":\"30/05/2020 00:00:00\",\r\n"
						+ "	\"reasonChange\": \"creacio de nou ubicacio test amb ddd\",\r\n"
						+ "	\"userInsert\": \"jrojast\",\r\n" + "	\"dateInsert\": \"28-11-2019 17:33:00\",\r\n"
						+ "	\"status\":\"X\",\r\n" + "	\"type\":\"RO\",\r\n" + "	\"contract\":{\r\n"
						+ "				\"code\":\"C\"\r\n" + "	},\r\n" + "	\"location\":{\r\n"
						+ "				\"codePostal\":\"08040\",\r\n" + "				\"codeTypeRoad\":\"C\",\r\n"
						+ "				\"streetName\":\"Polgon industrial Zona Franca, Carrer B Sector B\",\r\n"
						+ "				\"endNumber\":\"16\",\r\n" + "				\"startNumber\":\"22\",\r\n"
						+ "				\"population\":\"BARCELONA\"\r\n" + "	}\r\n" + "}"))
				.andDo(print()).andExpect(status().isOk());

	}

	@Test
	public void caseOkUbicationSyncUpdate() throws Exception {
		Mockito.when(ubicationService.update(UbicationRDTOStub.defaultOne())).thenReturn(returnRDTO);

		mockMvc.perform(put("/sync/inventari/ubicacio/" + UbicationRDTOStub.defaultOne().getCode())
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\r\n" + "	\"codeContract\": \"C\",\r\n" + "	\"coordinatesX\": 100001,\r\n"
						+ "	\"coordinatesY\": 300001,\r\n" + "	\"coordinates_1_X\": 100001,\r\n"
						+ "	\"coordinates_1_Y\": 200001,\r\n" + "	\"coordinates_2_X\": 300001,\r\n"
						+ "	\"coordinates_2_Y\": 400001,\r\n" + "	\"coordinates_3_X\": 500001,\r\n"
						+ "	\"coordinates_3_Y\": 600001,\r\n" + "	\"coordinates_4_X\": 700001,\r\n"
						+ "	\"coordinates_4_Y\": 800001,\r\n" + "	\"startDate\":\"30/05/2021 00:00:00\",\r\n"
						+ "	\"endDate\":\"30/05/2020 00:00:00\",\r\n"
						+ "	\"reasonChange\": \"creacio de nou ubicacio test amb ddd\",\r\n"
						+ "	\"userInsert\": \"jrojast\",\r\n" + "	\"dateInsert\": \"28-11-2019 17:33:00\",\r\n"
						+ "	\"status\":\"X\",\r\n" + "	\"type\":\"RO\",\r\n" + "	\"contract\":{\r\n"
						+ "				\"code\":\"C\"\r\n" + "	},\r\n" + "	\"location\":{\r\n"
						+ "				\"codePostal\":\"08040\",\r\n" + "				\"codeTypeRoad\":\"C\",\r\n"
						+ "				\"streetName\":\"Polgon industrial Zona Franca, Carrer B Sector B\",\r\n"
						+ "				\"endNumber\":\"16\",\r\n" + "				\"startNumber\":\"22\",\r\n"
						+ "				\"population\":\"BARCELONA\"\r\n" + "	}\r\n" + "}"))
				.andDo(print()).andExpect(status().isOk());

	}

	@Test
	public void caseOkUbicationSyncDelete() throws Exception {
		Mockito.when(ubicationService.delete(UbicationRDTOStub.defaultOne())).thenReturn(returnRDTO);

		mockMvc.perform(delete("/sync/inventari/ubicacio/" + UbicationRDTOStub.defaultOne().getCode())
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\r\n" + "	\"codeContract\": \"C\",\r\n" + "	\"coordinatesX\": 100001,\r\n"
						+ "	\"coordinatesY\": 300001,\r\n" + "	\"coordinates_1_X\": 100001,\r\n"
						+ "	\"coordinates_1_Y\": 200001,\r\n" + "	\"coordinates_2_X\": 300001,\r\n"
						+ "	\"coordinates_2_Y\": 400001,\r\n" + "	\"coordinates_3_X\": 500001,\r\n"
						+ "	\"coordinates_3_Y\": 600001,\r\n" + "	\"coordinates_4_X\": 700001,\r\n"
						+ "	\"coordinates_4_Y\": 800001,\r\n" + "	\"startDate\":\"30/05/2021 00:00:00\",\r\n"
						+ "	\"endDate\":\"30/05/2020 00:00:00\",\r\n"
						+ "	\"reasonChange\": \"creacio de nou ubicacio test amb ddd\",\r\n"
						+ "	\"userInsert\": \"jrojast\",\r\n" + "	\"dateInsert\": \"28-11-2019 17:33:00\",\r\n"
						+ "	\"status\":\"X\",\r\n" + "	\"type\":\"RO\",\r\n" + "	\"contract\":{\r\n"
						+ "				\"code\":\"C\"\r\n" + "	},\r\n" + "	\"location\":{\r\n"
						+ "				\"codePostal\":\"08040\",\r\n" + "				\"codeTypeRoad\":\"C\",\r\n"
						+ "				\"streetName\":\"Polgon industrial Zona Franca, Carrer B Sector B\",\r\n"
						+ "				\"endNumber\":\"16\",\r\n" + "				\"startNumber\":\"22\",\r\n"
						+ "				\"population\":\"BARCELONA\"\r\n" + "	}\r\n" + "}"))
				.andDo(print()).andExpect(status().isOk());

	}

	@Test
	public void caseOkSelectMobiliariFisic() throws Exception {
		Mockito.when(physicalModelService.select(PhysicalModelRDTOStub.defaultOne()))
				.thenReturn(ReturnPhysicalModelRDTOStub.getSuccessMessage());
		mockMvc.perform(get("/sync/inventari/mobiliarifisic/WSTD234")).andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void caseOkMobiliariFisicSyncInsert() throws Exception {

		Mockito.when(physicalModelService.insert(PhysicalModelRDTOStub.defaultOne())).thenReturn(returnRDTO);

		mockMvc.perform(post("/sync/inventari/mobiliarifisic").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\r\n" + "	\"code\":\"900050\",\r\n" + "	\"sensors\":[{\r\n"
						+ "				\"code\":\"SEN2\"\r\n" + "	}],\r\n" + "	\"contract\":{\r\n"
						+ "				\"code\":\"C\"\r\n" + "	},\r\n" + "	\"codeStateMCF\":\"SE\",\r\n"
						+ "	\"codeInstallation\":\"ZZA\",\r\n" + "	\"codeBrand\":\"A\",\r\n"
						+ "	\"codeMCL\":\"WS42\",\r\n" + "	\"codeRFID\":\"124\",\r\n"
						+ "	\"codeFractionType\":\"FO\",\r\n" + "	\"codeTypeMCF\":\"C01\",\r\n"
						+ "	\"reasonChange\": \"creacio de nou mcf\",\r\n" + "	\"userInsert\": \"tgutierrez\",\r\n"
						+ "	\"dateInsert\": \"28-11-2019 17:33:00\"\r\n" + "}"))
				.andDo(print()).andExpect(status().isOk());

	}

	@Test
	public void caseOkMobiliariFisicSyncUpdate() throws Exception {

		Mockito.when(physicalModelService.update(PhysicalModelRDTOStub.defaultOne())).thenReturn(returnRDTO);

		mockMvc.perform(post("/sync/inventari/mobiliarifisic/" + PhysicalModelRDTOStub.defaultOne().getCode())
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\r\n" + "	\"sensors\":[{\r\n" + "				\"code\":\"SEN2\"\r\n" + "	}],\r\n"
						+ "	\"contract\":{\r\n" + "				\"code\":\"C\"\r\n" + "	},\r\n"
						+ "	\"codeStateMCF\":\"SE\",\r\n" + "	\"codeInstallation\":\"ZZA\",\r\n"
						+ "	\"codeBrand\":\"A\",\r\n" + "	\"codeMCL\":\"WS42\",\r\n" + "	\"codeRFID\":\"124\",\r\n"
						+ "	\"codeFractionType\":\"FO\",\r\n" + "	\"codeTypeMCF\":\"C01\",\r\n"
						+ "	\"reasonChange\": \"creacio de nou mcf\",\r\n" + "	\"userInsert\": \"tgutierrez\",\r\n"
						+ "	\"dateInsert\": \"28-11-2019 17:33:00\"\r\n" + "}"))
				.andDo(print()).andExpect(status().isOk());

	}

	@Test
	public void caseOkMobiliariFisicSyncDelete() throws Exception {

		Mockito.when(physicalModelService.delete(PhysicalModelRDTOStub.defaultOne())).thenReturn(returnRDTO);

		mockMvc.perform(delete("/sync/inventari/mobiliarifisic/" + PhysicalModelRDTOStub.defaultOne().getCode())
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\r\n" + "	\"sensors\":[{\r\n" + "				\"code\":\"SEN2\"\r\n" + "	}],\r\n"
						+ "	\"contract\":{\r\n" + "				\"code\":\"C\"\r\n" + "	},\r\n"
						+ "	\"codeStateMCF\":\"SE\",\r\n" + "	\"codeInstallation\":\"ZZA\",\r\n"
						+ "	\"codeBrand\":\"A\",\r\n" + "	\"codeMCL\":\"WS42\",\r\n" + "	\"codeRFID\":\"124\",\r\n"
						+ "	\"codeFractionType\":\"FO\",\r\n" + "	\"codeTypeMCF\":\"C01\",\r\n"
						+ "	\"reasonChange\": \"creacio de nou mcf\",\r\n" + "	\"userInsert\": \"tgutierrez\",\r\n"
						+ "	\"dateInsert\": \"28-11-2019 17:33:00\"\r\n" + "}"))
				.andDo(print()).andExpect(status().isOk());

	}

	@Test
	public void caseOkMaterialResourceSyncInsert() throws Exception {

		Mockito.when(materialResourceService.insert(MaterialResourceRDTOStub.defaultOne())).thenReturn(returnRDTO);

		mockMvc.perform(post("/sync/inventari/recursmaterial").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\r\n" + "	\"frame\":\"ACM264916\",\r\n" + "	\"calca\":\"227\",\r\n"
						+ "	\"sensors\":[{\r\n" + "				\"code\":\"SEN2\"\r\n" + "	}],\r\n"
						+ "	\"code\":\"99999990056\",\r\n" + "		\"contract\":{\r\n"
						+ "				\"code\":\"C\"\r\n" + "	},\r\n" + "	\"codeState\":\"A\",\r\n"
						+ "	\"codeInstallation\":\"UAL\",\r\n" + "	\"codeModel\":\"A\",\r\n"
						+ "	\"codeMotorEnergyType\":\"GNC\",\r\n" + "	\"codeMaterialResourceType\":\"1\",\r\n"
						+ "	\"codeTituralotyType\":\"MU\",\r\n" + "	\"registration\":\"WEBSERVICE\",\r\n"
						+ "	\"provisionalRegistration\":\"true\",\r\n" + "	\"pma\":9000,\r\n"
						+ "	\"amortizationSubject\":\"true\",\r\n" + "	\"tara\":9999,\r\n"
						+ "	\"chassis\":\"ACM264916\",\r\n" + "	\"description\":\"desc\",\r\n"
						+ "	\"dateInsert\":\"28-11-2019 17:33:00\",\r\n" + "	\"userInsert\":\"tgutierrez\",\r\n"
						+ "	\"type_rrmm\":\"t\",\r\n" + "	\"reasonChange\":\"create inv\",\r\n"
						+ "	\"startDate\":\"28-11-2019 17:33:00\",\r\n" + "	\"endDate\":\"28-11-2019 17:33:00\",\r\n"
						+ "	\"euroStandart\":\"e\",\r\n" + "	\"typeEmissions\":\"ty\",\r\n"
						+ "	\"loadingTechnology\":\"RO\"\r\n" + "}"))
				.andDo(print()).andExpect(status().isOk());

	}

	@Test
	public void caseOkMaterialResourceSyncUpdate() throws Exception {

		Mockito.when(materialResourceService.update(MaterialResourceRDTOStub.defaultOne())).thenReturn(returnRDTO);

		mockMvc.perform(put("/sync/inventari/recursmaterial/" + MaterialResourceRDTOStub.defaultOne().getCode())
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\r\n" + "	\"frame\":\"ACM264916\",\r\n" + "	\"calca\":\"227\",\r\n"
						+ "	\"sensors\":[{\r\n" + "				\"code\":\"SEN2\"\r\n" + "	}],\r\n"
						+ "		\"contract\":{\r\n" + "				\"code\":\"C\"\r\n" + "	},\r\n"
						+ "	\"codeState\":\"A\",\r\n" + "	\"codeInstallation\":\"UAL\",\r\n"
						+ "	\"codeModel\":\"A\",\r\n" + "	\"codeMotorEnergyType\":\"GNC\",\r\n"
						+ "	\"codeMaterialResourceType\":\"1\",\r\n" + "	\"codeTituralotyType\":\"MU\",\r\n"
						+ "	\"registration\":\"WEBSERVICE\",\r\n" + "	\"provisionalRegistration\":\"true\",\r\n"
						+ "	\"pma\":9000,\r\n" + "	\"amortizationSubject\":\"true\",\r\n" + "	\"tara\":9999,\r\n"
						+ "	\"chassis\":\"ACM264916\",\r\n" + "	\"description\":\"desc\",\r\n"
						+ "	\"dateInsert\":\"28-11-2019 17:33:00\",\r\n" + "	\"userInsert\":\"tgutierrez\",\r\n"
						+ "	\"type_rrmm\":\"t\",\r\n" + "	\"reasonChange\":\"create inv\",\r\n"
						+ "	\"startDate\":\"28-11-2019 17:33:00\",\r\n" + "	\"endDate\":\"28-11-2019 17:33:00\",\r\n"
						+ "	\"euroStandart\":\"e\",\r\n" + "	\"typeEmissions\":\"ty\",\r\n"
						+ "	\"loadingTechnology\":\"RO\"\r\n" + "}"))
				.andDo(print()).andExpect(status().isOk());

	}

	@Test
	public void caseOkMaterialResourceSyncDelete() throws Exception {

		Mockito.when(materialResourceService.delete(MaterialResourceRDTOStub.defaultOne())).thenReturn(returnRDTO);

		mockMvc.perform(delete("/sync/inventari/recursmaterial/" + MaterialResourceRDTOStub.defaultOne().getCode())
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\r\n" + "	\"frame\":\"ACM264916\",\r\n" + "	\"calca\":\"227\",\r\n"
						+ "	\"sensors\":[{\r\n" + "				\"code\":\"SEN2\"\r\n" + "	}],\r\n"
						+ "		\"contract\":{\r\n" + "				\"code\":\"C\"\r\n" + "	},\r\n"
						+ "	\"codeState\":\"A\",\r\n" + "	\"codeInstallation\":\"UAL\",\r\n"
						+ "	\"codeModel\":\"A\",\r\n" + "	\"codeMotorEnergyType\":\"GNC\",\r\n"
						+ "	\"codeMaterialResourceType\":\"1\",\r\n" + "	\"codeTituralotyType\":\"MU\",\r\n"
						+ "	\"registration\":\"WEBSERVICE\",\r\n" + "	\"provisionalRegistration\":\"true\",\r\n"
						+ "	\"pma\":9000,\r\n" + "	\"amortizationSubject\":\"true\",\r\n" + "	\"tara\":9999,\r\n"
						+ "	\"chassis\":\"ACM264916\",\r\n" + "	\"description\":\"desc\",\r\n"
						+ "	\"dateInsert\":\"28-11-2019 17:33:00\",\r\n" + "	\"userInsert\":\"tgutierrez\",\r\n"
						+ "	\"type_rrmm\":\"t\",\r\n" + "	\"reasonChange\":\"create inv\",\r\n"
						+ "	\"startDate\":\"28-11-2019 17:33:00\",\r\n" + "	\"endDate\":\"28-11-2019 17:33:00\",\r\n"
						+ "	\"euroStandart\":\"e\",\r\n" + "	\"typeEmissions\":\"ty\",\r\n"
						+ "	\"loadingTechnology\":\"RO\"\r\n" + "}"))
				.andDo(print()).andExpect(status().isOk());

	}

	@Test
	public void caseOkSelectMobiliariLogic() throws Exception {
		Mockito.when(logicalModelService.select(LogicalModelRDTOStub.defaultOne()))
				.thenReturn(ReturnLogicalModelRDTOStub.getSuccessMessage());
		mockMvc.perform(get("/sync/inventari/mobiliarilogic/WSTD234")).andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void caseOkMobiliariLogicSyncInsert() throws Exception {

		Mockito.when(logicalModelService.insert(LogicalModelRDTOStub.defaultOne())).thenReturn(returnRDTO);

		mockMvc.perform(post("/sync/inventari/mobiliarilogic").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\r\n" + "	\"code\": \"CL531\",\r\n" + "	\"reasonChange\": \"creacio de nou mcl\",\r\n"
						+ "	\"dateInsert\": \"09-01-2019 17:33:00\",\r\n" + "	\"userInsert\": \"tgutierrez\",\r\n"
						+ "	\"codeMCLType\": \"C01\",\r\n" + "	\"codeFractionType\": \"FO\",\r\n"
						+ "	\"coordinatedX\": 100001.123,\r\n" + "	\"coordinatedY\": 100001.123,\r\n"
						+ "	\"ubication\":{\r\n" + "		\"contract\":{\r\n" + "			\"code\":\"C\"\r\n"
						+ "		},\r\n" + "		\"code\": \"C243\",\r\n" + "		\"position\" : 5,\r\n"
						+ "		\"type\":\"PN\",\r\n" + "		\"refTypeEnvironment\":\"D\",\r\n"
						+ "		\"coordinatesX\": 100001,\r\n" + "		\"coordinatesY\": 200001,\r\n"
						+ "		\"startDate\":\"30/05/2021 00:00:00\",\r\n"
						+ "		\"endDate\":\"30/05/2020 00:00:00\",\r\n"
						+ "		\"reasonChange\": \"creacio de nou ubicacio test amb ddd\",\r\n"
						+ "		\"userInsert\": \"jrojast\",\r\n"
						+ "		\"dateInsert\": \"28-11-2019 17:33:00\",\r\n" + "		\"status\":\"X\",\r\n"
						+ "		\"location\":{\r\n" + "			\"codePostal\":\"08040\",\r\n"
						+ "			\"codeNeighborhood\" : \"40\",\r\n" + "			\"codeTypeRoad\":\"C\",\r\n"
						+ "			\"streetName\":\"Pol�gon industrial Zona Franca, Carrer B Sector B\",\r\n"
						+ "			\"endNumber\":\"16\",\r\n" + "			\"startNumber\":\"22\",\r\n"
						+ "			\"startLetter\":\"B\",\r\n" + "			\"endLetter\":\"F\",\r\n"
						+ "			\"population\":\"BARCELONA\"\r\n" + "		},\r\n" + "		\"label\":{\r\n"
						+ "			\"value\":\"UBICACIO-ETIQUETA2\"\r\n" + "		}\r\n" + "	},\r\n"
						+ "	\"startDate\":\"30/05/2021 00:00:00\",\r\n" + "	\"endDate\":\"30/05/2020 00:00:00\"\r\n"
						+ "} "))
				.andDo(print()).andExpect(status().isOk());

	}

	@Test
	public void caseOkMobiliariLogicSyncUpdate() throws Exception {

		Mockito.when(logicalModelService.update(LogicalModelRDTOStub.defaultOne())).thenReturn(returnRDTO);

		mockMvc.perform(put("/sync/inventari/mobiliarilogic/" + LogicalModelRDTOStub.defaultOne().getCode())
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\r\n" + "	\"reasonChange\": \"creacio de nou mcl\",\r\n"
						+ "	\"dateInsert\": \"09-01-2019 17:33:00\",\r\n" + "	\"userInsert\": \"tgutierrez\",\r\n"
						+ "	\"codeMCLType\": \"C01\",\r\n" + "	\"codeFractionType\": \"FO\",\r\n"
						+ "	\"coordinatedX\": 100001.123,\r\n" + "	\"coordinatedY\": 100001.123,\r\n"
						+ "	\"ubication\":{\r\n" + "		\"contract\":{\r\n" + "			\"code\":\"C\"\r\n"
						+ "		},\r\n" + "		\"code\": \"C243\",\r\n" + "		\"position\" : 5,\r\n"
						+ "		\"type\":\"PN\",\r\n" + "		\"refTypeEnvironment\":\"D\",\r\n"
						+ "		\"coordinatesX\": 100001,\r\n" + "		\"coordinatesY\": 200001,\r\n"
						+ "		\"startDate\":\"30/05/2021 00:00:00\",\r\n"
						+ "		\"endDate\":\"30/05/2020 00:00:00\",\r\n"
						+ "		\"reasonChange\": \"creacio de nou ubicacio test amb ddd\",\r\n"
						+ "		\"userInsert\": \"jrojast\",\r\n"
						+ "		\"dateInsert\": \"28-11-2019 17:33:00\",\r\n" + "		\"status\":\"X\",\r\n"
						+ "		\"location\":{\r\n" + "			\"codePostal\":\"08040\",\r\n"
						+ "			\"codeNeighborhood\" : \"40\",\r\n" + "			\"codeTypeRoad\":\"C\",\r\n"
						+ "			\"streetName\":\"Pol�gon industrial Zona Franca, Carrer B Sector B\",\r\n"
						+ "			\"endNumber\":\"16\",\r\n" + "			\"startNumber\":\"22\",\r\n"
						+ "			\"startLetter\":\"B\",\r\n" + "			\"endLetter\":\"F\",\r\n"
						+ "			\"population\":\"BARCELONA\"\r\n" + "		},\r\n" + "		\"label\":{\r\n"
						+ "			\"value\":\"UBICACIO-ETIQUETA2\"\r\n" + "		}\r\n" + "	},\r\n"
						+ "	\"startDate\":\"30/05/2021 00:00:00\",\r\n" + "	\"endDate\":\"30/05/2020 00:00:00\"\r\n"
						+ "} "))
				.andDo(print()).andExpect(status().isOk());

	}

	@Test
	public void caseOkMobiliariLogicSyncDelete() throws Exception {

		Mockito.when(logicalModelService.delete(LogicalModelRDTOStub.defaultOne())).thenReturn(returnRDTO);

		mockMvc.perform(delete("/sync/inventari/mobiliarilogic/" + LogicalModelRDTOStub.defaultOne().getCode())
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\r\n" + "	\"reasonChange\": \"creacio de nou mcl\",\r\n"
						+ "	\"dateInsert\": \"09-01-2019 17:33:00\",\r\n" + "	\"userInsert\": \"tgutierrez\",\r\n"
						+ "	\"codeMCLType\": \"C01\",\r\n" + "	\"codeFractionType\": \"FO\",\r\n"
						+ "	\"coordinatedX\": 100001.123,\r\n" + "	\"coordinatedY\": 100001.123,\r\n"
						+ "	\"ubication\":{\r\n" + "		\"contract\":{\r\n" + "			\"code\":\"C\"\r\n"
						+ "		},\r\n" + "		\"code\": \"C243\",\r\n" + "		\"position\" : 5,\r\n"
						+ "		\"type\":\"PN\",\r\n" + "		\"refTypeEnvironment\":\"D\",\r\n"
						+ "		\"coordinatesX\": 100001,\r\n" + "		\"coordinatesY\": 200001,\r\n"
						+ "		\"startDate\":\"30/05/2021 00:00:00\",\r\n"
						+ "		\"endDate\":\"30/05/2020 00:00:00\",\r\n"
						+ "		\"reasonChange\": \"creacio de nou ubicacio test amb ddd\",\r\n"
						+ "		\"userInsert\": \"jrojast\",\r\n"
						+ "		\"dateInsert\": \"28-11-2019 17:33:00\",\r\n" + "		\"status\":\"X\",\r\n"
						+ "		\"location\":{\r\n" + "			\"codePostal\":\"08040\",\r\n"
						+ "			\"codeNeighborhood\" : \"40\",\r\n" + "			\"codeTypeRoad\":\"C\",\r\n"
						+ "			\"streetName\":\"Pol�gon industrial Zona Franca, Carrer B Sector B\",\r\n"
						+ "			\"endNumber\":\"16\",\r\n" + "			\"startNumber\":\"22\",\r\n"
						+ "			\"startLetter\":\"B\",\r\n" + "			\"endLetter\":\"F\",\r\n"
						+ "			\"population\":\"BARCELONA\"\r\n" + "		},\r\n" + "		\"label\":{\r\n"
						+ "			\"value\":\"UBICACIO-ETIQUETA2\"\r\n" + "		}\r\n" + "	},\r\n"
						+ "	\"startDate\":\"30/05/2021 00:00:00\",\r\n" + "	\"endDate\":\"30/05/2020 00:00:00\"\r\n"
						+ "} "))
				.andDo(print()).andExpect(status().isOk());

	}

	@Test
	public void caseOkAggregateAmortizationSyncInsert() throws Exception {

		Mockito.when(physicalModelService.insert(AggregateAmortizationRDTOStub.defaultOne())).thenReturn(returnRDTO);

		mockMvc.perform(post("/sync/inventari/mobiliarifisic/tipus/amortitzacio/agregada")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\r\n" + "	\"codiContracta\": \"C\",\r\n" + "	\"codiGrupServei\": \"NAD\",\r\n"
						+ "	\"codiFraccio\": \"FO\",\r\n" + "	\"codiTerritori\": \"3\",\r\n"
						+ "	\"numElements\": \"1\",\r\n" + "	\"percentFinancament\": \"100\",\r\n"
						+ "	\"interessos\":\"0\",\r\n" + "	\"valorAmortitzacio\":\"0\",\r\n"
						+ "	\"codiMarcaMobiliari\":\"A\",\r\n" + "	\"codiTipusMobiliari\":\"B01\",\r\n"
						+ "	\"concepte\":\"0\",\r\n" + "	\"valor\":\"1\"\r\n" + "} "))
				.andDo(print()).andExpect(status().isOk());

	}

	@Test
	public void caseOkInstallationSyncInsert() throws Exception {

		Mockito.when(installationService.insert(InstallationRDTOStub.defaultOne())).thenReturn(returnRDTO);

		mockMvc.perform(post("/sync/inventari/installacio").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(" {\r\n" + "   \"codiContracta\":\"C\",\r\n" + "   \"codi\":\"CI01\",\r\n"
						+ "   \"estat\":\"A\",\r\n" + "   \"descripcio\":\"Prueba FMW\",\r\n"
						+ "   \"coordenadaX\":\"1000\",\r\n" + "   \"coordenadaY\":\"1000\",\r\n"
						+ "   \"superficie\":10,\r\n" + "   \"tipus\":\"PC\",\r\n"
						+ "   \"codiTipusTitularitat\":\"M\",\r\n" + "   \"motiuCanvi\":\"Creaci�\",\r\n"
						+ "   \"subjecteAmortitzacio\":true,\r\n" + "   \"localitzacio\":{\r\n"
						+ "      \"codiBarri\":\"01\",\r\n" + "      \"codiPostal\":\"08040\",\r\n"
						+ "      \"codiTerritori\":\"1\",\r\n" + "      \"codiTipusVia\":\"C\",\r\n"
						+ "      \"lletraInici\":\"C\",\r\n" + "      \"lletraFi\":\"\",\r\n"
						+ "      \"nomVia\":\"Via Favencia\",\r\n" + "      \"numeroInici\":\"02\",\r\n"
						+ "      \"numeroFi\":\"01\"\r\n" + "   }\r\n" + "}"))
				.andDo(print()).andExpect(status().isOk());

	}

	@Test
	public void caseOkInstallationSyncUpdate() throws Exception {

		Mockito.when(installationService.update(InstallationRDTOStub.defaultOne())).thenReturn(returnRDTO);

		mockMvc.perform(put("/sync/inventari/installacio/" + InstallationRDTOStub.defaultOne().getCode())
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(" {\r\n" + "   \"codiContracta\":\"C\",\r\n" + "   \"estat\":\"A\",\r\n"
						+ "   \"descripcio\":\"Prueba FMW\",\r\n" + "   \"coordenadaX\":\"1000\",\r\n"
						+ "   \"coordenadaY\":\"1000\",\r\n" + "   \"superficie\":10,\r\n" + "   \"tipus\":\"PC\",\r\n"
						+ "   \"codiTipusTitularitat\":\"M\",\r\n" + "   \"motiuCanvi\":\"Creaci�\",\r\n"
						+ "   \"subjecteAmortitzacio\":true,\r\n" + "   \"localitzacio\":{\r\n"
						+ "      \"codiBarri\":\"01\",\r\n" + "      \"codiPostal\":\"08040\",\r\n"
						+ "      \"codiTerritori\":\"1\",\r\n" + "      \"codiTipusVia\":\"C\",\r\n"
						+ "      \"lletraInici\":\"C\",\r\n" + "      \"lletraFi\":\"\",\r\n"
						+ "      \"nomVia\":\"Via Favencia\",\r\n" + "      \"numeroInici\":\"02\",\r\n"
						+ "      \"numeroFi\":\"01\"\r\n" + "   }\r\n" + "}"))
				.andDo(print()).andExpect(status().isOk());

	}

	@Test
	public void caseOkInstallationSyncDelete() throws Exception {

		Mockito.when(installationService.delete(InstallationRDTOStub.defaultOne())).thenReturn(returnRDTO);

		mockMvc.perform(delete("/sync/inventari/installacio/" + InstallationRDTOStub.defaultOne().getCode())
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(" {\r\n" + "   \"codiContracta\":\"C\",\r\n" + "   \"estat\":\"A\",\r\n"
						+ "   \"descripcio\":\"Prueba FMW\",\r\n" + "   \"coordenadaX\":\"1000\",\r\n"
						+ "   \"coordenadaY\":\"1000\",\r\n" + "   \"superficie\":10,\r\n" + "   \"tipus\":\"PC\",\r\n"
						+ "   \"codiTipusTitularitat\":\"M\",\r\n" + "   \"motiuCanvi\":\"Creaci�\",\r\n"
						+ "   \"subjecteAmortitzacio\":true,\r\n" + "   \"localitzacio\":{\r\n"
						+ "      \"codiBarri\":\"01\",\r\n" + "      \"codiPostal\":\"08040\",\r\n"
						+ "      \"codiTerritori\":\"1\",\r\n" + "      \"codiTipusVia\":\"C\",\r\n"
						+ "      \"lletraInici\":\"C\",\r\n" + "      \"lletraFi\":\"\",\r\n"
						+ "      \"nomVia\":\"Via Favencia\",\r\n" + "      \"numeroInici\":\"02\",\r\n"
						+ "      \"numeroFi\":\"01\"\r\n" + "   }\r\n" + "}"))
				.andDo(print()).andExpect(status().isOk());

	}

	@Test
	public void caseOkInstallationSyncInsertExpense() throws Exception {

		Mockito.when(installationService.insertExpense(InstallationRDTOStub.defaultOne())).thenReturn(returnRDTO);

		mockMvc.perform(post("/sync/inventari/installacio/despesa").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\r\n" + "   \"codi\":\"I104\",\r\n" + "   \"despese\":{\r\n"
						+ "   		\"codiSubtipus\":\"LLO\",\r\n" + "   		\"codiZona\":\"C\",\r\n"
						+ "	   	\"concepte\":\"FMW VIGIA\",\r\n" + "   		\"importAnual\":120,\r\n"
						+ "   		\"mesosPagats\":6,\r\n" + "   		\"periode\":12,\r\n"
						+ "   		\"preuUnitari\":5,\r\n" + "   		\"unitats\":15\r\n" + "   }\r\n" + "}"))
				.andDo(print()).andExpect(status().isOk());

	}

	@Test
	public void caseOkInstallationSyncInsertAmortizationBase() throws Exception {

		Mockito.when(installationService.insertAmortizationBase(InstallationRDTOStub.defaultOne()))
				.thenReturn(returnRDTO);

		mockMvc.perform(post("/sync/inventari/installacio/amortitzacio").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\r\n" + "   \"codi\":\"I103\",\r\n" + "   \"amortitzacio\":{\r\n"
						+ "   		\"concepte\":\"TEST FMW-VIGIA JROJAS\",\r\n" + "   		\"import\":99,\r\n"
						+ "   		\"importRestant\":11,\r\n" + "   		\"interessos\":10,\r\n"
						+ "   		\"percentatgeFinancament\":10,\r\n" + "   		\"valor\":120,\r\n"
						+ "   		\"mesosPagats\":1,\r\n" + "   		\"periode\":10\r\n" + "   }\r\n" + "}"))
				.andDo(print()).andExpect(status().isOk());

	}

	@Test
	public void caseOkInstallationSyncInsertApportionment() throws Exception {

		Mockito.when(installationService.insertApportionment(InstallationRDTOStub.defaultOne())).thenReturn(returnRDTO);

		mockMvc.perform(post("/sync/inventari/installacio/prorrateig").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\r\n" + "   \"codi\":\"I103\",\r\n" + "   \"prorrateig\":[\r\n"
						+ "   		{	\"codiServei\":\"N\",\r\n" + "   			\"percentatgeUS\":50\r\n"
						+ "   		},\r\n" + "   		{	\"codiServei\":\"R\",\r\n"
						+ "   			\"percentatgeUS\":50\r\n" + "   		}\r\n" + "   		]\r\n" + "}"))
				.andDo(print()).andExpect(status().isOk());

	}

	@Test
	public void caseOkSelectInstallationDetailed() throws Exception {
		Mockito.when(installationService.selectDetailed(Mockito.any(queryParameterRDTO.getClass())))
				.thenReturn(returnInstallationDetailedRDTO);
		mockMvc.perform(get("/sync/inventari/installacio/" + queryParameterRDTO.getCode() + "/contracta/"
				+ queryParameterRDTO.getCodeContract() + "/detall?dataReferencia="
				+ queryParameterRDTO.getDateReference() + "&codiUsuari=" + queryParameterRDTO.getCodeUser()
				+ "&transactionId=" + queryParameterRDTO.getTransactionId())).andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void caseKoSelectInstallationDetailed() throws Exception {
		Mockito.doThrow(ImiException.class).when(installationService)
				.selectDetailed(Mockito.any(queryParameterRDTO.getClass()));
		mockMvc.perform(get("/sync/inventari/installacio/" + queryParameterRDTO.getCode() + "/contracta/"
				+ queryParameterRDTO.getCodeContract() + "/detall?dataReferencia="
				+ queryParameterRDTO.getDateReference() + "&codiUsuari=" + queryParameterRDTO.getCodeUser()
				+ "&transactionId=" + queryParameterRDTO.getTransactionId())).andDo(print())
				.andExpect(status().isInternalServerError());
	}

	@Test
	public void caseOkSelectInstallationMassive() throws Exception {
		Mockito.when(installationService.selectMassive(Mockito.any(queryParameterRDTO.getClass())))
				.thenReturn(returnMassiveRDTO);
		mockMvc.perform(get("/sync/inventari/installacio/contracta/" + queryParameterRDTO.getCodeContract()
				+ "?dataReferencia=" + queryParameterRDTO.getDateReference() + "&codiUsuari="
				+ queryParameterRDTO.getCodeUser() + "&transactionId=" + queryParameterRDTO.getTransactionId()))
				.andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void caseKoSelectInstallationMassive() throws Exception {
		Mockito.doThrow(ImiException.class).when(installationService)
				.selectMassive(Mockito.any(queryParameterRDTO.getClass()));
		mockMvc.perform(get("/sync/inventari/installacio/contracta/" + queryParameterRDTO.getCodeContract()
				+ "?dataReferencia=" + queryParameterRDTO.getDateReference() + "&codiUsuari="
				+ queryParameterRDTO.getCodeUser() + "&transactionId=" + queryParameterRDTO.getTransactionId()))
				.andDo(print()).andExpect(status().isInternalServerError());
	}

	@Test
	public void caseOkSelectInstallationAmortization() throws Exception {
		Mockito.when(installationService.selectAmortization(Mockito.any(queryParameterRDTO.getClass())))
				.thenReturn(returnBreakdownAmortizationRDTO);
		mockMvc.perform(get("/sync/inventari/installacio/" + queryParameterRDTO.getCode() + "/contracta/"
				+ queryParameterRDTO.getCodeContract() + "/amortitzacio?dataReferencia="
				+ queryParameterRDTO.getDateReference() + "&codiUsuari=" + queryParameterRDTO.getCodeUser()
				+ "&transactionId=" + queryParameterRDTO.getTransactionId())).andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void caseKoSelectInstallationAmortization() throws Exception {
		Mockito.doThrow(ImiException.class).when(installationService)
				.selectAmortization(Mockito.any(queryParameterRDTO.getClass()));
		mockMvc.perform(get("/sync/inventari/installacio/" + queryParameterRDTO.getCode() + "/contracta/"
				+ queryParameterRDTO.getCodeContract() + "/amortitzacio?dataReferencia="
				+ queryParameterRDTO.getDateReference() + "&codiUsuari=" + queryParameterRDTO.getCodeUser()
				+ "&transactionId=" + queryParameterRDTO.getTransactionId())).andDo(print())
				.andExpect(status().isInternalServerError());
	}

	@Test
	public void caseOkSelectInstallationExpenses() throws Exception {
		Mockito.when(installationService.selectExpenses(Mockito.any(queryParameterRDTO.getClass())))
				.thenReturn(returnInstallationExpensesRDTO);
		mockMvc.perform(get("/sync/inventari/installacio/" + queryParameterRDTO.getCode() + "/contracta/"
				+ queryParameterRDTO.getCodeContract() + "/despesa?dataReferencia="
				+ queryParameterRDTO.getDateReference() + "&codiUsuari=" + queryParameterRDTO.getCodeUser()
				+ "&transactionId=" + queryParameterRDTO.getTransactionId())).andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void caseKoSelectInstallationExpenses() throws Exception {
		Mockito.doThrow(ImiException.class).when(installationService)
				.selectExpenses(Mockito.any(queryParameterRDTO.getClass()));
		mockMvc.perform(get("/sync/inventari/installacio/" + queryParameterRDTO.getCode() + "/contracta/"
				+ queryParameterRDTO.getCodeContract() + "/despesa?dataReferencia="
				+ queryParameterRDTO.getDateReference() + "&codiUsuari=" + queryParameterRDTO.getCodeUser()
				+ "&transactionId=" + queryParameterRDTO.getTransactionId())).andDo(print())
				.andExpect(status().isInternalServerError());
	}

	@Test
	public void caseOkSelectInstallationPeriod() throws Exception {
		Mockito.when(installationService.selectPeriod(Mockito.any(queryParameterRDTO.getClass())))
				.thenReturn(returnInstallationPeriodRDTO);
		mockMvc.perform(get("/sync/inventari/installacio/" + queryParameterRDTO.getCode() + "/contracta/"
				+ queryParameterRDTO.getCodeContract() + "/periode/" + queryParameterRDTO.getPeriod()
				+ "?dataReferencia=" + queryParameterRDTO.getDateReference() + "&codiUsuari="
				+ queryParameterRDTO.getCodeUser() + "&transactionId=" + queryParameterRDTO.getTransactionId()))
				.andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void caseKoSelectInstallationPeriod() throws Exception {
		Mockito.doThrow(ImiException.class).when(installationService)
				.selectPeriod(Mockito.any(queryParameterRDTO.getClass()));
		mockMvc.perform(get("/sync/inventari/installacio/" + queryParameterRDTO.getCode() + "/contracta/"
				+ queryParameterRDTO.getCodeContract() + "/periode/" + queryParameterRDTO.getPeriod()
				+ "?dataReferencia=" + queryParameterRDTO.getDateReference() + "&codiUsuari="
				+ queryParameterRDTO.getCodeUser() + "&transactionId=" + queryParameterRDTO.getTransactionId()))
				.andDo(print()).andExpect(status().isInternalServerError());
	}

	@Test
	public void caseOkCommerceSyncInsert() throws Exception {

		Mockito.when(commerceService.insert(CommerceRDTOStub.defaultOne())).thenReturn(returnRDTO);

		mockMvc.perform(post("/sync/inventari/comerc").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\r\n" + "   \"codi\":\"9001\",\r\n" + "   \"codiContracta\":\"C\",\r\n"
						+ "   \"cif\":\"D4459000\",\r\n" + "   \"estat\":\"A\",\r\n"
						+ "   \"coordinadaX\":\"1000\",\r\n" + "   \"coordinadaY\":\"1000\",\r\n"
						+ "   \"raoSocial\":\"FRAMEWORK VIGIA\",\r\n" + "   \"expedient\":\"123\",\r\n"
						+ "   \"zonaComercial\":false,\r\n" + "   \"granProductor\":false,\r\n"
						+ "   \"localitzacio\":{\r\n" + "      \"codiBarri\":\"01\",\r\n"
						+ "      \"codiPostal\":\"08040\",\r\n" + "      \"codiTerritori\":\"1\",\r\n"
						+ "      \"codiTipusVia\":\"C\",\r\n" + "      \"nomVia\":\"WEBSERVICE UNIT TEST\",\r\n"
						+ "      \"numeroInici\":\"16\",\r\n" + "      \"numeroFi\":\"22\"\r\n" + "   }\r\n" + "}"))
				.andDo(print()).andExpect(status().isOk());

	}

	@Test
	public void caseOkCommerceSyncUpdate() throws Exception {

		Mockito.when(commerceService.update(CommerceRDTOStub.defaultOne())).thenReturn(returnRDTO);

		mockMvc.perform(put("/sync/inventari/comerc/" + CommerceRDTOStub.defaultOne().getCode())
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\r\n" + "   \"codi\":\"9001\",\r\n" + "   \"codiContracta\":\"C\",\r\n"
						+ "   \"cif\":\"D4459000\",\r\n" + "   \"estat\":\"A\",\r\n"
						+ "   \"coordinadaX\":\"3000\",\r\n" + "   \"coordinadaY\":\"2000\",\r\n"
						+ "   \"raoSocial\":\"FRAMEWORK VIGIA\",\r\n" + "   \"expedient\":\"123\",\r\n"
						+ "   \"zonaComercial\":false,\r\n" + "   \"granProductor\":false,\r\n"
						+ "   \"localitzacio\":{\r\n" + "      \"codiBarri\":\"01\",\r\n"
						+ "      \"codiPostal\":\"08040\",\r\n" + "      \"codiTerritori\":\"1\",\r\n"
						+ "      \"codiTipusVia\":\"C\",\r\n" + "      \"nomVia\":\"WEBSERVICE UNIT TEST\",\r\n"
						+ "      \"numeroInici\":\"16\",\r\n" + "      \"numeroFi\":\"22\"\r\n" + "   }\r\n" + "}"))
				.andDo(print()).andExpect(status().isOk());

	}

	@Test
	public void caseOkCommerceSyncDelete() throws Exception {

		Mockito.when(commerceService.delete(CommerceRDTOStub.defaultOne())).thenReturn(returnRDTO);

		mockMvc.perform(delete("/sync/inventari/comerc/" + CommerceRDTOStub.defaultOne().getCode())
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\r\n" + "   \"codi\":\"9001\",\r\n" + "   \"codiContracta\":\"C\",\r\n"
						+ "   \"cif\":\"D4459000\",\r\n" + "   \"estat\":\"B\",\r\n"
						+ "   \"coordinadaX\":\"2000\",\r\n" + "   \"coordinadaY\":\"4000\",\r\n"
						+ "   \"raoSocial\":\"FRAMEWORK VIGIA\",\r\n" + "   \"expedient\":\"123\",\r\n"
						+ "   \"zonaComercial\":false,\r\n" + "   \"granProductor\":false,\r\n"
						+ "   \"localitzacio\":{\r\n" + "      \"codiBarri\":\"01\",\r\n"
						+ "      \"codiPostal\":\"08040\",\r\n" + "      \"codiTerritori\":\"1\",\r\n"
						+ "      \"codiTipusVia\":\"C\",\r\n" + "      \"nomVia\":\"WEBSERVICE UNIT TEST\",\r\n"
						+ "      \"numeroInici\":\"16\",\r\n" + "      \"numeroFi\":\"22\"\r\n" + "   }\r\n" + "}"))
				.andDo(print()).andExpect(status().isOk());

	}

	@Test
	public void caseOkCommerceSyncInsertElements() throws Exception {

		Mockito.when(commerceService.insertElements(CommerceRDTOStub.defaultOne())).thenReturn(returnRDTO);

		mockMvc.perform(post("/sync/inventari/comerc/elements").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\r\n" + "   \"codi\":\"9001\",\r\n" + "   \"codiContracta\":\"C\",\r\n"
						+ "   \"cif\":\"D4459000\",\r\n" + "   \"estat\":\"A\",\r\n"
						+ "   \"coordinadaX\":\"1000\",\r\n" + "   \"coordinadaY\":\"1000\",\r\n"
						+ "   \"raoSocial\":\"FRAMEWORK VIGIA\",\r\n" + "   \"expedient\":\"123\",\r\n"
						+ "   \"zonaComercial\":false,\r\n" + "   \"granProductor\":false,\r\n"
						+ "   \"localitzacio\":{\r\n" + "      \"codiBarri\":\"01\",\r\n"
						+ "      \"codiPostal\":\"08040\",\r\n" + "      \"codiTerritori\":\"1\",\r\n"
						+ "      \"codiTipusVia\":\"C\",\r\n" + "      \"nomVia\":\"WEBSERVICE UNIT TEST\",\r\n"
						+ "      \"numeroInici\":\"16\",\r\n" + "      \"numeroFi\":\"22\"\r\n" + "   }\r\n" + "}"))
				.andDo(print()).andExpect(status().isOk());

	}

	@Test
	public void caseKoCommerceSyncInsertElements() throws Exception {

		Mockito.doThrow(ImiException.class).when(commerceService).insertElements(Mockito.any(CommerceRDTO.class));

		mockMvc.perform(post("/sync/inventari/comerc/elements").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\r\n" + "   \"codi\":\"9001\",\r\n" + "   \"codiContracta\":\"C\",\r\n"
						+ "   \"cif\":\"D4459000\",\r\n" + "   \"estat\":\"A\",\r\n"
						+ "   \"coordinadaX\":\"1000\",\r\n" + "   \"coordinadaY\":\"1000\",\r\n"
						+ "   \"raoSocial\":\"FRAMEWORK VIGIA\",\r\n" + "   \"expedient\":\"123\",\r\n"
						+ "   \"zonaComercial\":false,\r\n" + "   \"granProductor\":false,\r\n"
						+ "   \"localitzacio\":{\r\n" + "      \"codiBarri\":\"01\",\r\n"
						+ "      \"codiPostal\":\"08040\",\r\n" + "      \"codiTerritori\":\"1\",\r\n"
						+ "      \"codiTipusVia\":\"C\",\r\n" + "      \"nomVia\":\"WEBSERVICE UNIT TEST\",\r\n"
						+ "      \"numeroInici\":\"16\",\r\n" + "      \"numeroFi\":\"22\"\r\n" + "   }\r\n" + "}"))
				.andDo(print()).andExpect(status().isInternalServerError());

	}

	@Test
	public void caseOkSelectCommerceMassive() throws Exception {
		Mockito.when(commerceService.selectMassive(Mockito.any(queryParameterRDTO.getClass())))
				.thenReturn(returnMassiveRDTO);
		mockMvc.perform(get("/sync/inventari/comerc/contracta/" + queryParameterRDTO.getCodeContract()
				+ "?dataReferencia=" + queryParameterRDTO.getDateReference() + "&codiUsuari="
				+ queryParameterRDTO.getCodeUser() + "&transactionId=" + queryParameterRDTO.getTransactionId()))
				.andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void caseKoSelectCommerceMassive() throws Exception {
		Mockito.doThrow(ImiException.class).when(commerceService)
				.selectMassive(Mockito.any(queryParameterRDTO.getClass()));
		mockMvc.perform(get("/sync/inventari/comerc/contracta/" + queryParameterRDTO.getCodeContract()
				+ "?dataReferencia=" + queryParameterRDTO.getDateReference() + "&codiUsuari="
				+ queryParameterRDTO.getCodeUser() + "&transactionId=" + queryParameterRDTO.getTransactionId()))
				.andDo(print()).andExpect(status().isInternalServerError());
	}

	@Test
	public void caseOkSelectCommerceDetailed() throws Exception {
		Mockito.when(commerceService.selectDetailed(Mockito.any(queryParameterRDTO.getClass())))
				.thenReturn(returnCommerceDetailedRDTO);
		mockMvc.perform(get("/sync/inventari/comerc/" + queryParameterRDTO.getCode() + "/contracta/"
				+ queryParameterRDTO.getCodeContract() + "/detall?dataReferencia="
				+ queryParameterRDTO.getDateReference() + "&codiUsuari=" + queryParameterRDTO.getCodeUser()
				+ "&transactionId=" + queryParameterRDTO.getTransactionId())).andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void caseKoSelectCommerceDetailed() throws Exception {
		Mockito.doThrow(ImiException.class).when(commerceService)
				.selectDetailed(Mockito.any(queryParameterRDTO.getClass()));
		mockMvc.perform(get("/sync/inventari/comerc/" + queryParameterRDTO.getCode() + "/contracta/"
				+ queryParameterRDTO.getCodeContract() + "/detall?dataReferencia="
				+ queryParameterRDTO.getDateReference() + "&codiUsuari=" + queryParameterRDTO.getCodeUser()
				+ "&transactionId=" + queryParameterRDTO.getTransactionId())).andDo(print())
				.andExpect(status().isInternalServerError());
	}

	@Test
	public void caseOkSelectCommerceElements() throws Exception {
		Mockito.when(commerceService.selectElements(Mockito.any(queryParameterRDTO.getClass())))
				.thenReturn(returnCommerceElementsRDTO);
		mockMvc.perform(get("/sync/inventari/comerc/" + queryParameterRDTO.getCode() + "/contracta/"
				+ queryParameterRDTO.getCodeContract() + "/elements?dataReferencia="
				+ queryParameterRDTO.getDateReference() + "&codiUsuari=" + queryParameterRDTO.getCodeUser()
				+ "&transactionId=" + queryParameterRDTO.getTransactionId())).andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void caseKoSelectCommerceElements() throws Exception {
		Mockito.doThrow(ImiException.class).when(commerceService)
				.selectElements(Mockito.any(queryParameterRDTO.getClass()));
		mockMvc.perform(get("/sync/inventari/comerc/" + queryParameterRDTO.getCode() + "/contracta/"
				+ queryParameterRDTO.getCodeContract() + "/elements?dataReferencia="
				+ queryParameterRDTO.getDateReference() + "&codiUsuari=" + queryParameterRDTO.getCodeUser()
				+ "&transactionId=" + queryParameterRDTO.getTransactionId())).andDo(print())
				.andExpect(status().isInternalServerError());
	}

	@Test
	public void caseOkEmployeeCatalogSyncInsert() throws Exception {

		Mockito.when(employeeCatalogService.insert(EmployeeCatalogRDTOStub.defaultOne())).thenReturn(returnRDTO);

		mockMvc.perform(post("/sync/inventari/recursoshumans/cataleg/personal/treballador")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\r\n" + "\"codi\":\"CP0001\",\r\n" + "\"codiContracta\":\"C\",\r\n" + "\"sexe\":\"H\",\r\n"
						+ "\"codiCategoriaProfessional\":\"CO\",\r\n" + "\"codiRegimTreball\":\"C1\",\r\n"
						+ "\"codiRelacioLaboral\":\"FX\",\r\n" + "\"minusvalidesa\":false,\r\n"
						+ "\"codiProcedenciaContractacio\":\"SR\",\r\n" + "\"tipusPersonal\" :\"PD\",\r\n"
						+ "\"dataReferencia\":\"11/05/2020 00:00:00\", \r\n" + "\"usuariInsercio\":\"JROJAST\"\r\n"
						+ "}"))
				.andDo(print()).andExpect(status().isOk());

	}

	@Test
	public void caseKoEmployeeCatalogSyncInsert() throws Exception {

		Mockito.doThrow(ImiException.class).when(employeeCatalogService).insert(Mockito.any(EmployeeCatalogRDTO.class));

		mockMvc.perform(post("/sync/inventari/recursoshumans/cataleg/personal/treballador")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\r\n" + "\"codi\":\"CP0001\",\r\n" + "\"codiContracta\":\"C\",\r\n" + "\"sexe\":\"H\",\r\n"
						+ "\"codiCategoriaProfessional\":\"CO\",\r\n" + "\"codiRegimTreball\":\"C1\",\r\n"
						+ "\"codiRelacioLaboral\":\"FX\",\r\n" + "\"minusvalidesa\":\"N\",\r\n"
						+ "\"codiProcedenciaContractacio\":\"SR\",\r\n" + "\"tipusPersonal\" :\"PD\",\r\n"
						+ "\"dataReferencia\":\"11/05/2020 00:00:00\", \r\n" + "\"usuariInsercio\":\"JROJAST\"\r\n"
						+ "}"))
				.andDo(print()).andExpect(status().is5xxServerError());

	}

	@Test
	public void caseOkSelectEmployeeCatalogMassive() throws Exception {
		Mockito.when(employeeCatalogService.selectMassive(Mockito.any(queryParameterRDTO.getClass())))
				.thenReturn(returnMassiveRDTO);
		mockMvc.perform(get("/sync/inventari/recursoshumans/cataleg/personal/treballador/contracta/"
				+ queryParameterRDTO.getCodeContract() + "?dataReferencia=" + queryParameterRDTO.getDateReference()
				+ "&codiUsuari=" + queryParameterRDTO.getCodeUser() + "&transactionId="
				+ queryParameterRDTO.getTransactionId())).andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void caseKoSelectEmployeeCatalogMassive() throws Exception {
		Mockito.doThrow(ImiException.class).when(employeeCatalogService)
				.selectMassive(Mockito.any(queryParameterRDTO.getClass()));
		mockMvc.perform(get("/sync/inventari/recursoshumans/cataleg/personal/treballador/contracta/"
				+ queryParameterRDTO.getCodeContract() + "?dataReferencia=" + queryParameterRDTO.getDateReference()
				+ "&codiUsuari=" + queryParameterRDTO.getCodeUser() + "&transactionId="
				+ queryParameterRDTO.getTransactionId())).andDo(print()).andExpect(status().isInternalServerError());
	}

	@Test
	public void caseOkSelectEmployeeCatalogDetailed() throws Exception {
		Mockito.when(employeeCatalogService.selectDetailed(Mockito.any(queryParameterRDTO.getClass())))
				.thenReturn(returnEmployeeCatalogDetailedRDTO);
		mockMvc.perform(get("/sync/inventari/recursoshumans/cataleg/personal/treballador/contracta/"
				+ queryParameterRDTO.getCodeContract() + "/detall?dataReferencia="
				+ queryParameterRDTO.getDateReference() + "&codiUsuari=" + queryParameterRDTO.getCodeUser()
				+ "&transactionId=" + queryParameterRDTO.getTransactionId())).andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void caseKoSelectEmployeeCatalogDetailed() throws Exception {
		Mockito.doThrow(ImiException.class).when(employeeCatalogService)
				.selectDetailed(Mockito.any(queryParameterRDTO.getClass()));
		mockMvc.perform(get("/sync/inventari/recursoshumans/cataleg/personal/treballador/contracta/"
				+ queryParameterRDTO.getCodeContract() + "/detall?dataReferencia="
				+ queryParameterRDTO.getDateReference() + "&codiUsuari=" + queryParameterRDTO.getCodeUser()
				+ "&transactionId=" + queryParameterRDTO.getTransactionId())).andDo(print())
				.andExpect(status().isInternalServerError());
	}

	@Test
	public void caseOkSelectMaterialResourceMassive() throws Exception {
		Mockito.when(materialResourceService.selectMassive(Mockito.any(queryParameterRDTO.getClass())))
				.thenReturn(returnMassiveRDTO);
		mockMvc.perform(get("/sync/inventari/recursmaterial/contracta/" + queryParameterRDTO.getCodeContract()
				+ "?dataReferencia=" + queryParameterRDTO.getDateReference() + "&codiUsuari="
				+ queryParameterRDTO.getCodeUser() + "&transactionId=" + queryParameterRDTO.getTransactionId()))
				.andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void caseKoSelectMaterialResourceMassive() throws Exception {
		Mockito.doThrow(ImiException.class).when(materialResourceService)
				.selectMassive(Mockito.any(queryParameterRDTO.getClass()));
		mockMvc.perform(get("/sync/inventari/recursmaterial/contracta/" + queryParameterRDTO.getCodeContract()
				+ "?dataReferencia=" + queryParameterRDTO.getDateReference() + "&codiUsuari="
				+ queryParameterRDTO.getCodeUser() + "&transactionId=" + queryParameterRDTO.getTransactionId()))
				.andDo(print()).andExpect(status().isInternalServerError());
	}

	@Test
	public void caseOkSelectMaterialResourceDetailed() throws Exception {
		Mockito.when(materialResourceService.selectDetailed(Mockito.any(queryParameterRDTO.getClass())))
				.thenReturn(returnMaterialResourceDetailedRDTO);
		mockMvc.perform(get("/sync/inventari/recursmaterial/" + queryParameterRDTO.getCode() + "/contracta/"
				+ queryParameterRDTO.getCodeContract() + "/detall?dataReferencia="
				+ queryParameterRDTO.getDateReference() + "&codiUsuari=" + queryParameterRDTO.getCodeUser()
				+ "&transactionId=" + queryParameterRDTO.getTransactionId())).andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void caseKoSelectMaterialResourceDetailed() throws Exception {
		Mockito.doThrow(ImiException.class).when(materialResourceService)
				.selectDetailed(Mockito.any(queryParameterRDTO.getClass()));
		mockMvc.perform(get("/sync/inventari/recursmaterial/" + queryParameterRDTO.getCode() + "/contracta/"
				+ queryParameterRDTO.getCodeContract() + "/detall?dataReferencia="
				+ queryParameterRDTO.getDateReference() + "&codiUsuari=" + queryParameterRDTO.getCodeUser()
				+ "&transactionId=" + queryParameterRDTO.getTransactionId())).andDo(print())
				.andExpect(status().isInternalServerError());
	}

	@Test
	public void caseOkMaterialResourceSyncInsertExpense() throws Exception {

		Mockito.when(materialResourceService.insertExpense(MaterialResourceRDTOStub.defaultOne()))
				.thenReturn(returnRDTO);

		mockMvc.perform(post("/sync/inventari/recursmaterial/despesa").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\r\n" + "   \"codi\":\"I104\",\r\n" + "   \"despese\":{\r\n"
						+ "   		\"codiSubtipus\":\"LLO\",\r\n" + "   		\"codiZona\":\"C\",\r\n"
						+ "	   	\"concepte\":\"FMW VIGIA\",\r\n" + "   		\"importAnual\":120,\r\n"
						+ "   		\"mesosPagats\":6,\r\n" + "   		\"periode\":12,\r\n"
						+ "   		\"preuUnitari\":5,\r\n" + "   		\"unitats\":15\r\n" + "   }\r\n" + "}"))
				.andDo(print()).andExpect(status().isOk());

	}

	@Test
	public void caseOkMaterialResourceSyncInsertAmortizationBase() throws Exception {

		Mockito.when(materialResourceService.insertAmortizationBase(MaterialResourceRDTOStub.defaultOne()))
				.thenReturn(returnRDTO);

		mockMvc.perform(post("/sync/inventari/recursmaterial/amortitzacio").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\r\n" + "   \"codi\":\"I103\",\r\n" + "   \"amortitzacio\":{\r\n"
						+ "   		\"concepte\":\"TEST FMW-VIGIA JROJAS\",\r\n" + "   		\"import\":99,\r\n"
						+ "   		\"importRestant\":11,\r\n" + "   		\"interessos\":10,\r\n"
						+ "   		\"percentatgeFinancament\":10,\r\n" + "   		\"valor\":120,\r\n"
						+ "   		\"mesosPagats\":1,\r\n" + "   		\"periode\":10\r\n" + "   }\r\n" + "}"))
				.andDo(print()).andExpect(status().isOk());

	}

	@Test
	public void caseOkMaterialResourceSyncInsertApportionment() throws Exception {

		Mockito.when(materialResourceService.insertApportionment(MaterialResourceRDTOStub.defaultOne()))
				.thenReturn(returnRDTO);

		mockMvc.perform(post("/sync/inventari/recursmaterial/prorrateig").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\r\n" + "   \"codi\":\"I103\",\r\n" + "   \"prorrateig\":[\r\n"
						+ "   		{	\"codiServei\":\"N\",\r\n" + "   			\"percentatgeUS\":50\r\n"
						+ "   		},\r\n" + "   		{	\"codiServei\":\"R\",\r\n"
						+ "   			\"percentatgeUS\":50\r\n" + "   		}\r\n" + "   		]\r\n" + "}"))
				.andDo(print()).andExpect(status().isOk());

	}

	@Test
	public void caseOkSelectMaterialResourceVehicleMassive() throws Exception {
		Mockito.when(materialResourceService.selectVehicleMassive(Mockito.any(queryParameterRDTO.getClass())))
				.thenReturn(returnMassiveRDTO);
		mockMvc.perform(get("/sync/inventari/recursmaterial/vehicle/contracta/" + queryParameterRDTO.getCodeContract()
				+ "?dataReferencia=" + queryParameterRDTO.getDateReference() + "&codiUsuari="
				+ queryParameterRDTO.getCodeUser() + "&transactionId=" + queryParameterRDTO.getTransactionId()))
				.andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void caseKoSelectMaterialResourceVehicleMassive() throws Exception {
		Mockito.doThrow(ImiException.class).when(materialResourceService)
				.selectVehicleMassive(Mockito.any(queryParameterRDTO.getClass()));
		mockMvc.perform(get("/sync/inventari/recursmaterial/vehicle/contracta/" + queryParameterRDTO.getCodeContract()
				+ "?dataReferencia=" + queryParameterRDTO.getDateReference() + "&codiUsuari="
				+ queryParameterRDTO.getCodeUser() + "&transactionId=" + queryParameterRDTO.getTransactionId()))
				.andDo(print()).andExpect(status().isInternalServerError());
	}

	@Test
	public void caseOkSelectMaterialResourceVehicleDetailed() throws Exception {
		Mockito.when(materialResourceService.selectVehicleDetailed(Mockito.any(queryParameterRDTO.getClass())))
				.thenReturn(returnMaterialResourceVehicleDetailedRDTO);
		mockMvc.perform(get("/sync/inventari/recursmaterial/vehicle/" + queryParameterRDTO.getCode() + "/contracta/"
				+ queryParameterRDTO.getCodeContract() + "/detall?dataReferencia="
				+ queryParameterRDTO.getDateReference() + "&codiUsuari=" + queryParameterRDTO.getCodeUser()
				+ "&transactionId=" + queryParameterRDTO.getTransactionId())).andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void caseKoSelectMaterialResourceVehicleDetailed() throws Exception {
		Mockito.doThrow(ImiException.class).when(materialResourceService)
				.selectVehicleDetailed(Mockito.any(queryParameterRDTO.getClass()));
		mockMvc.perform(get("/sync/inventari/recursmaterial/vehicle/" + queryParameterRDTO.getCode() + "/contracta/"
				+ queryParameterRDTO.getCodeContract() + "/detall?dataReferencia="
				+ queryParameterRDTO.getDateReference() + "&codiUsuari=" + queryParameterRDTO.getCodeUser()
				+ "&transactionId=" + queryParameterRDTO.getTransactionId())).andDo(print())
				.andExpect(status().isInternalServerError());
	}

	@Test
	public void caseOkSelectMaterialResourceVehicleAmortization() throws Exception {
		Mockito.when(materialResourceService.selectAmortization(Mockito.any(queryParameterRDTO.getClass())))
				.thenReturn(returnBreakdownAmortizationRDTO);
		mockMvc.perform(get("/sync/inventari/recursmaterial/vehicle/" + queryParameterRDTO.getCode() + "/contracta/"
				+ queryParameterRDTO.getCodeContract() + "/amortitzacio?dataReferencia="
				+ queryParameterRDTO.getDateReference() + "&codiUsuari=" + queryParameterRDTO.getCodeUser()
				+ "&transactionId=" + queryParameterRDTO.getTransactionId())).andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void caseKoSelectMaterialResourceVehicleAmortization() throws Exception {
		Mockito.doThrow(ImiException.class).when(materialResourceService)
				.selectAmortization(Mockito.any(queryParameterRDTO.getClass()));
		mockMvc.perform(get("/sync/inventari/recursmaterial/vehicle/" + queryParameterRDTO.getCode() + "/contracta/"
				+ queryParameterRDTO.getCodeContract() + "/amortitzacio?dataReferencia="
				+ queryParameterRDTO.getDateReference() + "&codiUsuari=" + queryParameterRDTO.getCodeUser()
				+ "&transactionId=" + queryParameterRDTO.getTransactionId())).andDo(print())
				.andExpect(status().isInternalServerError());
	}

	@Test
	public void caseOkSelectMaterialResourceVehicleExpenses() throws Exception {
		Mockito.when(materialResourceService.selectExpenses(Mockito.any(queryParameterRDTO.getClass())))
				.thenReturn(returnMaterialResourceVehicleExpensesRDTO);
		mockMvc.perform(get("/sync/inventari/recursmaterial/vehicle/" + queryParameterRDTO.getCode() + "/contracta/"
				+ queryParameterRDTO.getCodeContract() + "/despesa?dataReferencia="
				+ queryParameterRDTO.getDateReference() + "&codiUsuari=" + queryParameterRDTO.getCodeUser()
				+ "&transactionId=" + queryParameterRDTO.getTransactionId())).andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void caseKoSelectMaterialResourceVehicleExpenses() throws Exception {
		Mockito.doThrow(ImiException.class).when(materialResourceService)
				.selectExpenses(Mockito.any(queryParameterRDTO.getClass()));
		mockMvc.perform(get("/sync/inventari/recursmaterial/vehicle/" + queryParameterRDTO.getCode() + "/contracta/"
				+ queryParameterRDTO.getCodeContract() + "/despesa?dataReferencia="
				+ queryParameterRDTO.getDateReference() + "&codiUsuari=" + queryParameterRDTO.getCodeUser()
				+ "&transactionId=" + queryParameterRDTO.getTransactionId())).andDo(print())
				.andExpect(status().isInternalServerError());
	}

	@Test
	public void caseOkSelectMaterialResourceVehiclePeriod() throws Exception {
		Mockito.when(materialResourceService.selectPeriod(Mockito.any(queryParameterRDTO.getClass())))
				.thenReturn(returnMaterialResourceVehiclePeriodRDTO);
		mockMvc.perform(get("/sync/inventari/recursmaterial/vehicle/" + queryParameterRDTO.getCode() + "/contracta/"
				+ queryParameterRDTO.getCodeContract() + "/periode/" + queryParameterRDTO.getPeriod()
				+ "?dataReferencia=" + queryParameterRDTO.getDateReference() + "&codiUsuari="
				+ queryParameterRDTO.getCodeUser() + "&transactionId=" + queryParameterRDTO.getTransactionId()))
				.andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void caseKoSelectMaterialResourceVehiclePeriod() throws Exception {
		Mockito.doThrow(ImiException.class).when(materialResourceService)
				.selectPeriod(Mockito.any(queryParameterRDTO.getClass()));
		mockMvc.perform(get("/sync/inventari/recursmaterial/vehicle/" + queryParameterRDTO.getCode() + "/contracta/"
				+ queryParameterRDTO.getCodeContract() + "/periode/" + queryParameterRDTO.getPeriod()
				+ "?dataReferencia=" + queryParameterRDTO.getDateReference() + "&codiUsuari="
				+ queryParameterRDTO.getCodeUser() + "&transactionId=" + queryParameterRDTO.getTransactionId()))
				.andDo(print()).andExpect(status().isInternalServerError());
	}

	@Test
	public void caseOkSelectUbicationMassive() throws Exception {
		Mockito.when(ubicationService.selectMassive(Mockito.any(queryParameterRDTO.getClass())))
				.thenReturn(returnMassiveRDTO);
		mockMvc.perform(get("/sync/inventari/ubicacio/contracta/" + queryParameterRDTO.getCodeContract()
				+ "?dataReferencia=" + queryParameterRDTO.getDateReference() + "&codiUsuari="
				+ queryParameterRDTO.getCodeUser() + "&transactionId=" + queryParameterRDTO.getTransactionId()))
				.andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void caseKoSelectUbicationMassive() throws Exception {
		Mockito.doThrow(ImiException.class).when(ubicationService)
				.selectMassive(Mockito.any(queryParameterRDTO.getClass()));
		mockMvc.perform(get("/sync/inventari/ubicacio/contracta/" + queryParameterRDTO.getCodeContract()
				+ "?dataReferencia=" + queryParameterRDTO.getDateReference() + "&codiUsuari="
				+ queryParameterRDTO.getCodeUser() + "&transactionId=" + queryParameterRDTO.getTransactionId()))
				.andDo(print()).andExpect(status().isInternalServerError());
	}

	@Test
	public void caseOkSelectUbicationDetailed() throws Exception {
		Mockito.when(ubicationService.selectDetailed(Mockito.any(queryParameterRDTO.getClass())))
				.thenReturn(returnUbicationDetailedRDTO);
		mockMvc.perform(get("/sync/inventari/ubicacio/" + queryParameterRDTO.getCode() + "/contracta/"
				+ queryParameterRDTO.getCodeContract() + "/detall?dataReferencia="
				+ queryParameterRDTO.getDateReference() + "&codiUsuari=" + queryParameterRDTO.getCodeUser()
				+ "&transactionId=" + queryParameterRDTO.getTransactionId())).andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void caseKoSelectUbicationDetailed() throws Exception {
		Mockito.doThrow(ImiException.class).when(ubicationService)
				.selectDetailed(Mockito.any(queryParameterRDTO.getClass()));
		mockMvc.perform(get("/sync/inventari/ubicacio/" + queryParameterRDTO.getCode() + "/contracta/"
				+ queryParameterRDTO.getCodeContract() + "/detall?dataReferencia="
				+ queryParameterRDTO.getDateReference() + "&codiUsuari=" + queryParameterRDTO.getCodeUser()
				+ "&transactionId=" + queryParameterRDTO.getTransactionId())).andDo(print())
				.andExpect(status().isInternalServerError());
	}

	@Test
	public void caseOkSelectMclMassive() throws Exception {
		Mockito.when(logicalModelService.selectMassive(Mockito.any(queryParameterRDTO.getClass())))
				.thenReturn(returnLogicalModelMassiveRDTO);
		mockMvc.perform(get("/sync/inventari/mobiliarilogic/contracta/" + queryParameterRDTO.getCodeContract()
				+ "?dataReferencia=" + queryParameterRDTO.getDateReference() + "&codiUsuari="
				+ queryParameterRDTO.getCodeUser() + "&transactionId=" + queryParameterRDTO.getTransactionId()))
				.andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void caseKoSelectMclMassive() throws Exception {
		Mockito.doThrow(ImiException.class).when(logicalModelService)
				.selectMassive(Mockito.any(queryParameterRDTO.getClass()));
		mockMvc.perform(get("/sync/inventari/mobiliarilogic/contracta/" + queryParameterRDTO.getCodeContract()
				+ "?dataReferencia=" + queryParameterRDTO.getDateReference() + "&codiUsuari="
				+ queryParameterRDTO.getCodeUser() + "&transactionId=" + queryParameterRDTO.getTransactionId()))
				.andDo(print()).andExpect(status().isInternalServerError());
	}

	@Test
	public void caseOkSelectMclDetailed() throws Exception {
		Mockito.when(logicalModelService.selectDetailed(Mockito.any(queryParameterRDTO.getClass())))
				.thenReturn(returnLogicalModelDetailedRDTO);
		mockMvc.perform(get("/sync/inventari/mobiliarilogic/" + queryParameterRDTO.getCode() + "/contracta/"
				+ queryParameterRDTO.getCodeContract() + "/detall?dataReferencia="
				+ queryParameterRDTO.getDateReference() + "&codiUsuari=" + queryParameterRDTO.getCodeUser()
				+ "&transactionId=" + queryParameterRDTO.getTransactionId())).andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void caseKoSelectMclDetailed() throws Exception {
		Mockito.doThrow(ImiException.class).when(logicalModelService)
				.selectDetailed(Mockito.any(queryParameterRDTO.getClass()));
		mockMvc.perform(get("/sync/inventari/mobiliarilogic/" + queryParameterRDTO.getCode() + "/contracta/"
				+ queryParameterRDTO.getCodeContract() + "/detall?dataReferencia="
				+ queryParameterRDTO.getDateReference() + "&codiUsuari=" + queryParameterRDTO.getCodeUser()
				+ "&transactionId=" + queryParameterRDTO.getTransactionId())).andDo(print())
				.andExpect(status().isInternalServerError());
	}

	@Test
	public void caseOkSelectPhysicalModelMassive() throws Exception {
		Mockito.when(physicalModelService.selectMassive(Mockito.any(queryParameterRDTO.getClass())))
				.thenReturn(returnPhysicalModelMassiveRDTO);
		mockMvc.perform(get("/sync/inventari/mobiliarifisic/contracta/" + queryParameterRDTO.getCodeContract()
				+ "?dataReferencia=" + queryParameterRDTO.getDateReference() + "&codiUsuari="
				+ queryParameterRDTO.getCodeUser() + "&transactionId=" + queryParameterRDTO.getTransactionId()))
				.andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void caseKoSelectPhysicalModelMassive() throws Exception {
		Mockito.doThrow(ImiException.class).when(physicalModelService)
				.selectMassive(Mockito.any(queryParameterRDTO.getClass()));
		mockMvc.perform(get("/sync/inventari/mobiliarifisic/contracta/" + queryParameterRDTO.getCodeContract()
				+ "?dataReferencia=" + queryParameterRDTO.getDateReference() + "&codiUsuari="
				+ queryParameterRDTO.getCodeUser() + "&transactionId=" + queryParameterRDTO.getTransactionId()))
				.andDo(print()).andExpect(status().isInternalServerError());
	}

	@Test
	public void caseOkSelectPhysicalModelDetailed() throws Exception {
		Mockito.when(physicalModelService.selectDetailed(Mockito.any(queryParameterRDTO.getClass())))
				.thenReturn(returnPhysicalModelDetailedRDTO);
		mockMvc.perform(get("/sync/inventari/mobiliarifisic/" + queryParameterRDTO.getCode() + "/contracta/"
				+ queryParameterRDTO.getCodeContract() + "/detall?dataReferencia="
				+ queryParameterRDTO.getDateReference() + "&codiUsuari=" + queryParameterRDTO.getCodeUser()
				+ "&transactionId=" + queryParameterRDTO.getTransactionId())).andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void caseKoSelectPhysicalModelDetailed() throws Exception {
		Mockito.doThrow(ImiException.class).when(physicalModelService)
				.selectDetailed(Mockito.any(queryParameterRDTO.getClass()));
		mockMvc.perform(get("/sync/inventari/mobiliarifisic/" + queryParameterRDTO.getCode() + "/contracta/"
				+ queryParameterRDTO.getCodeContract() + "/detall?dataReferencia="
				+ queryParameterRDTO.getDateReference() + "&codiUsuari=" + queryParameterRDTO.getCodeUser()
				+ "&transactionId=" + queryParameterRDTO.getTransactionId())).andDo(print())
				.andExpect(status().isInternalServerError());
	}

	@Test
	public void caseOkSelectPhysicalModelAmortization() throws Exception {
		Mockito.when(physicalModelService.selectAmortization(Mockito.any(queryParameterRDTO.getClass())))
				.thenReturn(returnPhysicalModelAmortizationRDTO);
		mockMvc.perform(get("/sync/inventari/mobiliarifisic/contracta/"+queryParameterRDTO.getCodeContract() 
				+"/fraccio/"+queryParameterRDTO.getCodeFraction()+"/territori/"+queryParameterRDTO.getCodeTerritory() 
				+"/tipus/"+queryParameterRDTO.getCodeType()+"/grup/"+queryParameterRDTO.getCodeGroup()
				+"?codiUsuari="+queryParameterRDTO.getCodeUser()
				+ "&transactionId=" + queryParameterRDTO.getTransactionId())).andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void caseKoSelectPhysicalModelAmortization() throws Exception {
		Mockito.doThrow(ImiException.class).when(physicalModelService)
				.selectAmortization(Mockito.any(queryParameterRDTO.getClass()));
		mockMvc.perform(get("/sync/inventari/mobiliarifisic/contracta/"+queryParameterRDTO.getCodeContract()
				+"/fraccio/"+queryParameterRDTO.getCodeFraction()+"/territori/"+queryParameterRDTO.getCodeTerritory()
				+"/tipus/"+queryParameterRDTO.getCodeType()+"/grup/"+queryParameterRDTO.getCodeGroup()
				+"?codiUsuari="+queryParameterRDTO.getCodeUser()
				+ "&transactionId=" + queryParameterRDTO.getTransactionId())).andDo(print())
				.andExpect(status().isInternalServerError());
	}


	@Test
	public void caseOkDocumentarySupportSyncInsert() throws Exception {
		DocumentarySupportRDTO documentarySupportRDTOin = new DocumentarySupportRDTO();
		Mockito.when(documentarySupportService.insert(documentarySupportRDTOin)).thenReturn(returnRDTO);
		
	
		mockMvc.perform(post("/sync/inventari/suportdocumental").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\r\n" + "	\"code\": \"WSTD234\"\r\n" + "	}\r\n" + "}"))
				.andDo(print()).andExpect(status().isOk());

	}

	@Test
	public void caseOkDocumentarySupportSyncDelete() throws Exception {
		Mockito.when(documentarySupportService.delete(DocumentarySupportRDTOStub.defaultOne())).thenReturn(returnRDTO);

		mockMvc.perform(put("/sync/inventari/suportdocumental/" + DocumentarySupportRDTOStub.defaultOne().getCode() + "/entitat/" + UbicationRDTOStub.defaultOne().getCode())
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\r\n" + "	\"code\": \"WSTD234\"\r\n" + "	}\r\n" + "}"))
				.andDo(print()).andExpect(status().isOk());

	}

	
	@Test
	public void caseOkActuationSyncInsert() throws Exception {
		ActuationRDTO actuationRDTOin = new ActuationRDTO();
		Mockito.when(actuationService.insert(actuationRDTOin)).thenReturn(returnRDTO);
		
	
		mockMvc.perform(post("/sync/inventari/actuacio").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\r\n" + "	\"code\": \"WSTD234\"\r\n" + "	}\r\n" + "}"))
				.andDo(print()).andExpect(status().isOk());

	}

	@Test
	public void caseOkActuationSyncDelete() throws Exception {
		Mockito.when(actuationService.delete(ActuationRDTOStub.defaultOne())).thenReturn(returnRDTO);

		mockMvc.perform(put("/sync/inventari/actuacio/" + ActuationRDTOStub.defaultOne().getCode() + "/entitat/" + UbicationRDTOStub.defaultOne().getCode())
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\r\n" + "	\"code\": \"WSTD234\"\r\n" + "	}\r\n" + "}"))
				.andDo(print()).andExpect(status().isOk());

	}

}
