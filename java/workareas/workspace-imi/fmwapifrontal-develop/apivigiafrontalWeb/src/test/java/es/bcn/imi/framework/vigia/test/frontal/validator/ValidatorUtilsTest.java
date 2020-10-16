package es.bcn.imi.framework.vigia.test.frontal.validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import es.bcn.imi.framework.vigia.frontal.execution.services.TimeClockSummaryService;
import es.bcn.imi.framework.vigia.frontal.gap.execution.services.EventGapService;
import es.bcn.imi.framework.vigia.frontal.gap.execution.services.GroupEventGapService;
import es.bcn.imi.framework.vigia.frontal.gap.execution.services.PlantWeighingGapService;
import es.bcn.imi.framework.vigia.frontal.gap.execution.services.ServiceGapService;
import es.bcn.imi.framework.vigia.frontal.gap.inventary.services.BrandModelGapService;
import es.bcn.imi.framework.vigia.frontal.gap.inventary.services.CommerceGapService;
import es.bcn.imi.framework.vigia.frontal.gap.inventary.services.InstallationGapService;
import es.bcn.imi.framework.vigia.frontal.gap.inventary.services.LogicalModelGapService;
import es.bcn.imi.framework.vigia.frontal.gap.inventary.services.MaterialResourceGapService;
import es.bcn.imi.framework.vigia.frontal.gap.inventary.services.PhysicalModelGapService;
import es.bcn.imi.framework.vigia.frontal.gap.inventary.services.TypeSubtypeExpenseGapService;
import es.bcn.imi.framework.vigia.frontal.gap.inventary.services.UbicationGapService;
import es.bcn.imi.framework.vigia.frontal.gap.inventary.services.ValueListGapService;
import es.bcn.imi.framework.vigia.frontal.gap.itinerary.services.ItineraryGapService;
import es.bcn.imi.framework.vigia.frontal.gap.planification.services.PlanificationGapService;
import es.bcn.imi.framework.vigia.frontal.inventary.services.CompatibilityService;
import es.bcn.imi.framework.vigia.frontal.inventary.services.MaterialResourceNoVehicleService;
import es.bcn.imi.framework.vigia.frontal.inventary.services.SensorService;
import es.bcn.imi.framework.vigia.frontal.inventary.services.ValueListService;
import es.bcn.imi.framework.vigia.frontal.validator.ValidatorUtils;
import es.bcn.imi.framework.vigia.frontal.web.rest.config.RestServerConfig;
import es.bcn.vigia.fmw.libcommons.business.dto.MaterialResourceBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.SensorBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.TimeClockSummaryBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.ValueListBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.BrandModelGapBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.CommerceGapBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.EventGapBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.GroupEventGapBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.InstallationGapBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.ItineraryGapBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.LogicalModelGapBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.MaterialResourceGapBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.PhysicalModelGapBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.PlanificationGapBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.PlantWeighingGapBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.ServiceGapBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.TypeSubtypeExpenseGapBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.UbicationGapBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.ValueListGapBDTO;
import es.bcn.vigia.fmw.libcommons.constants.ValidationConstants;
import es.bcn.vigia.fmw.libcommons.constants.ValueListConstants;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.CompatibilityTypesMCFSensor;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.CompatibilityTypesMCLUbication;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.CompatibilityTypesRRMMSensor;
import es.bcn.vigia.fmw.libcommons.valueobject.Entity;
import es.bcn.vigia.fmw.libcommons.valueobject.Type;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.SensorRDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RestServerConfig.class })
@TestPropertySource(locations = { "classpath:app/config/application.properties" })
@WebAppConfiguration
@SuppressWarnings("unchecked")
public class ValidatorUtilsTest {

	@Mock
	private ValueListService valueListService;

	@Mock
	private SensorService sensorService;

	@Mock
	private ValueListGapService valueListGapService;
	
	@Mock
	private UbicationGapService ubicationGapService;
	
	@Mock
	private LogicalModelGapService logicalModelGapService;

	@Mock
	private PhysicalModelGapService physicalModelGapService;

	@Mock
	private MaterialResourceGapService materialResourceGapService;

	@Mock
	private InstallationGapService installationGapService;

	@Mock
	private BrandModelGapService brandModelGapService;

	@Mock
	private CompatibilityService compatibilityService;

	@Mock
	private TypeSubtypeExpenseGapService typeSubtypeExpenseGapService;

	@Mock
	private MaterialResourceNoVehicleService rmNoVehiclesService;
	
	@Mock
	private CommerceGapService commerceGapService;

	@Mock
	private ItineraryGapService itineraryGapService;
	
	@Mock
	private EventGapService eventGapService;

	@Mock
	private PlanificationGapService planificationGapService;

	@Mock
	private ServiceGapService serviceGapService;

	@Mock
	private GroupEventGapService groupEventGapService;
	
	@Mock
	private PlantWeighingGapService plantWeighingGapService;

	@Mock
	private TimeClockSummaryService timeClockSummaryService;

	
	@InjectMocks
	private ValidatorUtils validatorUtils;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void caseOkValidateValueList() throws ImiException {
		Type type = new Type("TIPUS_UBICACIO");

		List<ValueListBDTO> ss = new ArrayList<>();

		ValueListBDTO valueListBdto = new ValueListBDTO();
		valueListBdto.setDescription("descripcio");
		valueListBdto.setValue("TIPUS_UBICACIO");

		ss.add(valueListBdto);

		Mockito.when(valueListService.getValueList(type)).thenReturn(ss);
		validatorUtils.validateValueList("TIPUS_UBICACIO", type);
	}

	@Test
	public void caseValidateValueListEmptyValueList() throws ImiException {
		Type type = new Type("TIPUS_UBICACIO");

		ValueListBDTO valueListBdto = new ValueListBDTO();
		valueListBdto.setDescription("descripcio");
		valueListBdto.setValue("TIPUS_UBICACIO");

		validatorUtils.validateValueList("TIPUS_UBICACIO", type);
	}
	
	@Test
	public void caseValidateValueListNotFound() throws ImiException {
		Type type = new Type("TIPUS_UBICACIO");

		List<ValueListBDTO> ss = new ArrayList<>();

		ValueListBDTO valueListBdto = new ValueListBDTO();
		valueListBdto.setDescription("descripcio");
		valueListBdto.setValue("TIPUS_UBICACIO");

		ss.add(valueListBdto);

		Mockito.when(valueListService.getValueList(type)).thenReturn(ss);
		validatorUtils.validateValueList("NE", type);
	}
	@Test
	public void caseKoValidateValueListException() throws ImiException {
		Type type = new Type("TIPUS_UBICACIO");		
		Mockito.doThrow(ImiException.class).when(valueListService).getValueList(type);
		validatorUtils.validateValueList("TIPUS_UBICACIO", type);
	}

	@Test
	public void caseOkValidateSensorsContract() throws ImiException {
		List<SensorRDTO> sensorsRDTO = new ArrayList<SensorRDTO>();
		SensorRDTO sensorRDTO = new SensorRDTO();
		sensorRDTO.setCode("SEN1");
		sensorsRDTO.add(sensorRDTO);
		
		List<SensorBDTO> sensorsContract = new ArrayList<>();
		SensorBDTO sensorBDTO = new SensorBDTO();
		sensorBDTO.setCode("SEN1");
		sensorsContract.add(sensorBDTO);
		Mockito.when(sensorService.getSensorsContract("C")).thenReturn(sensorsContract);
		
		validatorUtils.validateSensorsContract("C", sensorsRDTO);
	}

	@Test
	public void caseKoValidateSensorsContractException() throws ImiException {
		List<SensorRDTO> sensorsRDTO = new ArrayList<SensorRDTO>();
		SensorRDTO sensorRDTO = new SensorRDTO();
		sensorRDTO.setCode("SEN1");
		sensorsRDTO.add(sensorRDTO);
		
		List<SensorBDTO> sensorsContract = new ArrayList<>();
		SensorBDTO sensorBDTO = new SensorBDTO();
		sensorBDTO.setCode("C");
		sensorsContract.add(sensorBDTO);

		Mockito.doThrow(ImiException.class).when(sensorService).getSensorsContract("C");
		validatorUtils.validateSensorsContract("C", sensorsRDTO);
	}

	@Test
	public void caseValidateSensorsContractNotFound() throws ImiException {
		List<SensorRDTO> sensorsRDTO = new ArrayList<SensorRDTO>();
		SensorRDTO sensorRDTO = new SensorRDTO();
		sensorRDTO.setCode("SEN1");
		sensorsRDTO.add(sensorRDTO);
		
		List<SensorBDTO> sensorsContract = new ArrayList<>();
		SensorBDTO sensorBDTO = new SensorBDTO();
		sensorBDTO.setCode("C");
		sensorsContract.add(sensorBDTO);
		Mockito.when(sensorService.getSensorsContract("C")).thenReturn(sensorsContract);
		
		validatorUtils.validateSensorsContract("C", sensorsRDTO);
	}


	@Test
	public void caseOkvalidateValueListGap() throws ImiException {
		Entity entity = new Entity("entity");
		List<ValueListGapBDTO> valuesList = new ArrayList<>();
		ValueListGapBDTO valueListGapBDTO = new ValueListGapBDTO();
		valueListGapBDTO.setCode("entity");
		valueListGapBDTO.setDescription("description");
		valueListGapBDTO.setEntity("entity");
		
		valuesList.add(valueListGapBDTO);
		
		Mockito.when(valueListGapService.getValueListGap(entity)).thenReturn(valuesList);
		validatorUtils.validateValueListGap("entity",entity);
	}

	@Test
	public void caseOkvalidateValueListGapListEmpty() throws ImiException {
		Entity entity = new Entity("entity");
		List<ValueListGapBDTO> valuesList = new ArrayList<>();
		ValueListGapBDTO valueListGapBDTO = new ValueListGapBDTO();
		valueListGapBDTO.setCode("entity");
		valueListGapBDTO.setDescription("description");
		valueListGapBDTO.setEntity("entity");
		
		
		Mockito.when(valueListGapService.getValueListGap(entity)).thenReturn(valuesList);
		validatorUtils.validateValueListGap("entity",entity);
	}
	
	@Test
	public void caseOkvalidateValueListGapNotFound() throws ImiException {
		Entity entity = new Entity("entity");
		List<ValueListGapBDTO> valuesList = new ArrayList<>();
		ValueListGapBDTO valueListGapBDTO = new ValueListGapBDTO();
		valueListGapBDTO.setCode("entity");
		valueListGapBDTO.setDescription("description");
		valueListGapBDTO.setEntity("entity");
		
		valuesList.add(valueListGapBDTO);
		
		Mockito.when(valueListGapService.getValueListGap(entity)).thenReturn(valuesList);
		validatorUtils.validateValueListGap("NE",entity);
	}
	@Test
	public void caseKoValidateValueListGapException() throws ImiException {
		Entity entity = new Entity("entity");
		
		Mockito.doThrow(ImiException.class).when(valueListGapService).getValueListGap(entity);
		
		validatorUtils.validateValueListGap("entity",entity);
	}

	@Test
	public void caseOkValidateValueListFieldsGap() throws ImiException {
		Entity entity = new Entity("entity");
		List<ValueListGapBDTO> valuesList = new ArrayList<>();
		ValueListGapBDTO valueListGapBDTO = new ValueListGapBDTO();
		valueListGapBDTO.setCode("entity");
		valueListGapBDTO.setDescription("description");
		valueListGapBDTO.setEntity("entity");
		
		valuesList.add(valueListGapBDTO);
		
		List<String> fields = new ArrayList<String>();
		fields.add("entity");
		
		Mockito.when(valueListGapService.getValueListGap(entity)).thenReturn(valuesList);
		validatorUtils.validateListValueListGap(fields,entity,"","");
		
		
	}

	@Test
	public void caseOkValidateValueListFieldsGapNotFound() throws ImiException {
		Entity entity = new Entity("entity");
		List<ValueListGapBDTO> valuesList = new ArrayList<>();
		ValueListGapBDTO valueListGapBDTO = new ValueListGapBDTO();
		valueListGapBDTO.setCode("entity");
		valueListGapBDTO.setDescription("description");
		valueListGapBDTO.setEntity("entity");
		
		valuesList.add(valueListGapBDTO);
		
		List<String> fields = new ArrayList<String>();
		fields.add("notEntity");
		
		Mockito.when(valueListGapService.getValueListGap(entity)).thenReturn(valuesList);
		validatorUtils.validateListValueListGap(fields,entity,"","");
		
		
	}
	@Test
	public void caseKoValidateValueListFieldsGap() throws ImiException {
		Entity entity = new Entity("entity");
		List<ValueListGapBDTO> valuesList = new ArrayList<>();
		ValueListGapBDTO valueListGapBDTO = new ValueListGapBDTO();
		valueListGapBDTO.setCode("entity");
		valueListGapBDTO.setDescription("description");
		valueListGapBDTO.setEntity("entity");
		
		valuesList.add(valueListGapBDTO);
		
		List<String> fields = new ArrayList<String>();
		fields.add("entity");
		
		Mockito.doThrow(ImiException.class).when(valueListGapService).getValueListGap(entity);
		
		validatorUtils.validateListValueListGap(fields,entity,"","");
	}

	
	
	@Test
	public void caseOkValidateExistsUbicationGap() throws ImiException {
		
		List<UbicationGapBDTO> list = new ArrayList<>();
		
		UbicationGapBDTO ubicationGapBDTO = new UbicationGapBDTO();
		ubicationGapBDTO.setCode("code");
		
		list.add(ubicationGapBDTO);
		
		Mockito.when(ubicationGapService.getUbicationsGap(Mockito.anyMap())).thenReturn(list);
		validatorUtils.validateExistsUbicationGap("code",true);
	}
	
	@Test
	public void caseOkValidateExistsUbicationGapNoCheckStatusDelete() throws ImiException {
		
		List<UbicationGapBDTO> list = new ArrayList<>();
		
		UbicationGapBDTO ubicationGapBDTO = new UbicationGapBDTO();
		ubicationGapBDTO.setCode("code");
		
		list.add(ubicationGapBDTO);
		
		Mockito.when(ubicationGapService.getUbicationsGap(Mockito.anyMap())).thenReturn(list);
		validatorUtils.validateExistsUbicationGap("code",false);
	}

	@Test
	public void caseValidateExistsUbicationGapEmptyList() throws ImiException {
		
		List<UbicationGapBDTO> list = new ArrayList<>();
		
		
		Mockito.when(ubicationGapService.getUbicationsGap(Mockito.anyMap())).thenReturn(list);
		validatorUtils.validateExistsUbicationGap("code",true);
	}
	@Test
	public void caseKoValidateExistsUbicationGap() throws ImiException {
		Mockito.doThrow(ImiException.class).when(ubicationGapService).getUbicationsGap(Mockito.anyMap());
		validatorUtils.validateExistsUbicationGap("code",true);
	}
	
	@Test
	public void caseOkValidateExistsLogicalModelGap() throws ImiException {
		List<LogicalModelGapBDTO> logicalModels = new ArrayList<>();
		
		LogicalModelGapBDTO log = new LogicalModelGapBDTO();
		log.setCode("code");

		logicalModels.add(log);
		
		Mockito.when(logicalModelGapService.getLogicalModelsGap(Mockito.anyMap())).thenReturn(logicalModels);
		validatorUtils.validateExistsLogicalModelGap("code",true);
	}
	
	@Test
	public void caseOkValidateExistsLogicalModelGapNoCheckStatusDelete() throws ImiException {
		List<LogicalModelGapBDTO> logicalModels = new ArrayList<>();
		
		LogicalModelGapBDTO log = new LogicalModelGapBDTO();
		log.setCode("code");

		logicalModels.add(log);
		
		Mockito.when(logicalModelGapService.getLogicalModelsGap(Mockito.anyMap())).thenReturn(logicalModels);
		validatorUtils.validateExistsLogicalModelGap("code",false);
	}

	@Test
	public void caseOkValidateExistsLogicalModelGapEmptyList() throws ImiException {
		List<LogicalModelGapBDTO> logicalModels = new ArrayList<>();
		
				
		Mockito.when(logicalModelGapService.getLogicalModelsGap(Mockito.anyMap())).thenReturn(logicalModels);
		validatorUtils.validateExistsLogicalModelGap("code",true);
	}
	@Test
	public void caseValidateExistsLogicalModelGapEmptyList() throws ImiException {
		List<LogicalModelGapBDTO> logicalModels = new ArrayList<>();
				
		Mockito.when(logicalModelGapService.getLogicalModelsGap(Mockito.anyMap())).thenReturn(logicalModels);
		validatorUtils.validateExistsLogicalModelGap("code",true);
	}
	@Test
	public void caseKoValidateExistsLogicalModelGap() throws ImiException {
		Mockito.doThrow(ImiException.class).when(logicalModelGapService).getLogicalModelsGap(Mockito.anyMap());
		validatorUtils.validateExistsLogicalModelGap("code",true);
	}

	
	@Test
	public void caseOkValidateExistsLogicalModelUbicationGap() throws ImiException {
		List<LogicalModelGapBDTO> logicalModels = new ArrayList<>();
		
		LogicalModelGapBDTO log = new LogicalModelGapBDTO();
		log.setCode("code");

		logicalModels.add(log);
		
		Mockito.when(logicalModelGapService.getLogicalModelsUbicationGap("code")).thenReturn(logicalModels);
		validatorUtils.validateExistsLogicalModelUbication("code");
	}
	
	@Test
	public void caseOkValidateExistsLogicalModelUbicationGapEmptyList() throws ImiException {
		List<LogicalModelGapBDTO> logicalModels = new ArrayList<>();
		
		Mockito.when(logicalModelGapService.getLogicalModelsUbicationGap("code")).thenReturn(logicalModels);
		validatorUtils.validateExistsLogicalModelUbication("code");
	}
	@Test
	public void caseKoValidateExistsLogicalModelUbicationGap() throws ImiException {
		Mockito.doThrow(ImiException.class).when(logicalModelGapService).getLogicalModelsUbicationGap("code");
		validatorUtils.validateExistsLogicalModelUbication("code");
	}
	
	@Test
	public void caseOkValidateExistsLogicalModelPositionUbicationGap() throws ImiException {
		List<LogicalModelGapBDTO> logicalModels = new ArrayList<>();
		
		LogicalModelGapBDTO log = new LogicalModelGapBDTO();
		log.setCode("code");

		logicalModels.add(log);
		
		Mockito.when(logicalModelGapService.getLogicalModelsPositionUbicationGap("code",1,"codeMCL")).thenReturn(logicalModels);
		validatorUtils.validateExistsLogicalModelPositionUbication("code",1,"codeMCL");
	}

	@Test
	public void caseOkValidateExistsLogicalModelPositionUbicationGapEmptyList() throws ImiException {
		List<LogicalModelGapBDTO> logicalModels = new ArrayList<>();
		
		Mockito.when(logicalModelGapService.getLogicalModelsPositionUbicationGap("code",1,"codeMCL")).thenReturn(logicalModels);
		validatorUtils.validateExistsLogicalModelPositionUbication("code",1,"codeMCL");
	}
	
	@Test
	public void caseKoValidateExistsLogicalModelPositionUbicationGap() throws ImiException {
		Mockito.doThrow(ImiException.class).when(logicalModelGapService).getLogicalModelsPositionUbicationGap("code",1,"codeMCL");
		validatorUtils.validateExistsLogicalModelPositionUbication("code",1,"codeMCL");
	}

	@Test
	public void caseOkValidateExistsPhysicalModelGap() throws ImiException {
		List<PhysicalModelGapBDTO> physicalModels = new ArrayList<>();
		
		PhysicalModelGapBDTO fis = new PhysicalModelGapBDTO();
		fis.setCode("code");

		physicalModels.add(fis);
		
		Mockito.when(physicalModelGapService.getPhysicalModelsGap("code")).thenReturn(physicalModels);
		validatorUtils.validateExistsPhysicalModelGap("code");
	}

	@Test
	public void caseOkValidateExistsPhysicalModelGapEmptyList() throws ImiException {
		List<PhysicalModelGapBDTO> physicalModels = new ArrayList<>();
		
		Mockito.when(physicalModelGapService.getPhysicalModelsGap("code")).thenReturn(physicalModels);
		validatorUtils.validateExistsPhysicalModelGap("code");
	}
	
	@Test
	public void caseKoValidateExistsPhysicalModelGap() throws ImiException {
		Mockito.doThrow(ImiException.class).when(physicalModelGapService).getPhysicalModelsGap("code");
		validatorUtils.validateExistsPhysicalModelGap("code");
	}

	@Test
	public void caseOkValidateExistsPhysicalModelLogicalModelGap() throws ImiException {
		List<PhysicalModelGapBDTO> physicalModels = new ArrayList<>();
		
		PhysicalModelGapBDTO fis = new PhysicalModelGapBDTO();
		fis.setCode("code");

		physicalModels.add(fis);
		
		Mockito.when(physicalModelGapService.getPhysicalModelsLogicalModelGap("code")).thenReturn(physicalModels);
		validatorUtils.validateExistsPhysicalModelLogicalModelGap("code");
	}

	@Test
	public void caseOkValidateExistsPhysicalModelLogicalModelGapEmptyist() throws ImiException {
		List<PhysicalModelGapBDTO> physicalModels = new ArrayList<>();
		
		Mockito.when(physicalModelGapService.getPhysicalModelsLogicalModelGap("code")).thenReturn(physicalModels);
		validatorUtils.validateExistsPhysicalModelLogicalModelGap("code");
	}
	
	@Test
	public void caseKoValidateExistsPhysicalModelLogicalModelGap() throws ImiException {
		Mockito.doThrow(ImiException.class).when(physicalModelGapService).getPhysicalModelsLogicalModelGap("code");
		validatorUtils.validateExistsPhysicalModelLogicalModelGap("code");
	}
	@Test
	public void caseOkValidateExistsMaterialResourceGap() throws ImiException {
		
		List<MaterialResourceGapBDTO> list = new ArrayList<>();
		
		MaterialResourceGapBDTO materialResourceGapBDTO = new MaterialResourceGapBDTO();
		materialResourceGapBDTO.setCode("code");
		
		list.add(materialResourceGapBDTO);
		
		Mockito.when(materialResourceGapService.getMaterialResourcesGap("code")).thenReturn(list);
		validatorUtils.validateExistsMaterialResourceGap("code");
	}

	@Test
	public void caseValidateExistsMaterialResourceGapEmptyList() throws ImiException {
		
		List<MaterialResourceGapBDTO> list = new ArrayList<>();
		
		
		Mockito.when(materialResourceGapService.getMaterialResourcesGap("code")).thenReturn(list);
		validatorUtils.validateExistsMaterialResourceGap("code");
	}
	
	@Test
	public void caseKoValidateExistsMaterialResourceGap() throws ImiException {
		Mockito.doThrow(ImiException.class).when(materialResourceGapService).getMaterialResourcesGap("code");
		validatorUtils.validateExistsMaterialResourceGap("code");
	}

	@Test
	public void caseOkvalidateExistsVacantLogicalModelGap() throws ImiException {
		
		List<LogicalModelGapBDTO> list = new ArrayList<>();
		
		LogicalModelGapBDTO logicalModelGapBDTO = new LogicalModelGapBDTO();
		logicalModelGapBDTO.setCode("code");
		
		list.add(logicalModelGapBDTO);
		
		Mockito.when(logicalModelGapService.getVacantLogicalModelsGap("codeMCL", "codeMCF")).thenReturn(list);
		validatorUtils.validateExistsVacantLogicalModelGap("codeMCL", "codeMCF");
	}

	@Test
	public void caseOkvalidateExistsVacantLogicalModelGapEmptyList() throws ImiException {
		
		List<LogicalModelGapBDTO> list = new ArrayList<>();
		
		
		Mockito.when(logicalModelGapService.getVacantLogicalModelsGap("codeMCL", "codeMCF")).thenReturn(list);
		validatorUtils.validateExistsVacantLogicalModelGap("codeMCL", "codeMCF");
	}
	@Test
	public void caseKovalidateExistsVacantLogicalModelGap() throws ImiException {
		Mockito.doThrow(ImiException.class).when(logicalModelGapService).getVacantLogicalModelsGap("codeMCL", "codeMCF");
		validatorUtils.validateExistsVacantLogicalModelGap("codeMCL", "codeMCF");
	}
	
	@Test
	public void caseOkvalidateExistsInstalationGap() throws ImiException {
		
		List<InstallationGapBDTO> list = new ArrayList<>();
		
		InstallationGapBDTO installationGapBDTO = new InstallationGapBDTO();
		installationGapBDTO.setCode("code");
		
		list.add(installationGapBDTO);
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("code","code");
		Mockito.when(installationGapService.getInstallationsGap(params)).thenReturn(list);
		validatorUtils.validateExistsInstallationGap("code",false);
	}

	@Test
	public void caseOkvalidateExistsInstalationGapCheckStatusDelete() throws ImiException {
		
		List<InstallationGapBDTO> list = new ArrayList<>();
		
		InstallationGapBDTO installationGapBDTO = new InstallationGapBDTO();
		installationGapBDTO.setCode("code");
		
		list.add(installationGapBDTO);
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("code","code");
		Mockito.when(installationGapService.getInstallationsGap(params)).thenReturn(list);
		validatorUtils.validateExistsInstallationGap("code",true);
	}
	
	@Test
	public void caseOkvalidateExistsInstalationGapEmptyList() throws ImiException {
		
		List<InstallationGapBDTO> list = new ArrayList<>();
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("code","code");
		Mockito.when(installationGapService.getInstallationsGap(params)).thenReturn(list);
		validatorUtils.validateExistsInstallationGap("code",false);
	}
	@Test
	public void caseKovalidateExistsInstalationGap() throws ImiException {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("code","code");
		Mockito.doThrow(ImiException.class).when(installationGapService).getInstallationsGap(params);
		validatorUtils.validateExistsInstallationGap("code",false);
	}
	
	@Test
	public void caseOkValidateBrandModelGap() throws ImiException {
		
		List<BrandModelGapBDTO> list = new ArrayList<>();
		
		BrandModelGapBDTO brandModelGapBDTO = new BrandModelGapBDTO();
		brandModelGapBDTO.setCode("code");
		
		list.add(brandModelGapBDTO);
		
		Mockito.when(brandModelGapService.getBrandModelsGap("code")).thenReturn(list);
		validatorUtils.validateBrandModelGap("code","");
	}

	@Test
	public void caseOkValidateBrandModelGapEmptyList() throws ImiException {
		
		List<BrandModelGapBDTO> list = new ArrayList<>();
		
		Mockito.when(brandModelGapService.getBrandModelsGap("code")).thenReturn(list);
		validatorUtils.validateBrandModelGap("code","");
	}
	
	@Test
	public void caseOkModelValidateBrandModelGap() throws ImiException {
		
		List<BrandModelGapBDTO> list = new ArrayList<>();
		
		BrandModelGapBDTO brandModelGapBDTO = new BrandModelGapBDTO();
		brandModelGapBDTO.setCode("code");
		brandModelGapBDTO.setModelCode("modelCode");	
		
		list.add(brandModelGapBDTO);
		
		Mockito.when(brandModelGapService.getBrandModelsGap("code")).thenReturn(list);
		validatorUtils.validateBrandModelGap("code","modelCode");
	}
	@Test
	public void caseKoValidateBrandModelGap() throws ImiException {
		Mockito.doThrow(ImiException.class).when(brandModelGapService).getBrandModelsGap("code");
		validatorUtils.validateBrandModelGap("code","");
	}

	@Test
	public void caseOkValidateCompatibilityTypeMCLUbication() throws ImiException {
		
		List<UbicationGapBDTO> list = new ArrayList<>();
		
		UbicationGapBDTO ubicationGapBDTO = new UbicationGapBDTO();
		ubicationGapBDTO.setCode("codeUbication");
		ubicationGapBDTO.setUbicationTypeCode("typeCode");
		list.add(ubicationGapBDTO);
		
		Mockito.when(ubicationGapService.getUbicationsGap(Mockito.anyMap())).thenReturn(list);
		validatorUtils.validateCompatibilityTypeMCLUbication("codeMclType","codeUbication",true);
	}
	
	@Test
	public void caseOkValidateCompatibilityTypeMCLUbicationNoCheckStatusDelete() throws ImiException {
		
		List<UbicationGapBDTO> list = new ArrayList<>();
		
		UbicationGapBDTO ubicationGapBDTO = new UbicationGapBDTO();
		ubicationGapBDTO.setCode("codeUbication");
		ubicationGapBDTO.setUbicationTypeCode("typeCode");
		list.add(ubicationGapBDTO);
		
		Mockito.when(ubicationGapService.getUbicationsGap(Mockito.anyMap())).thenReturn(list);
		validatorUtils.validateCompatibilityTypeMCLUbication("codeMclType","codeUbication",false);
	}
	
	@Test
	public void caseOkValidateCompatibilityTypeMCLUbicationEmptyList() throws ImiException {
		
		List<UbicationGapBDTO> list = new ArrayList<>();
		
		UbicationGapBDTO ubicationGapBDTO = new UbicationGapBDTO();
		ubicationGapBDTO.setCode("codeUbication");
		ubicationGapBDTO.setUbicationTypeCode("typeCode");
		list.add(ubicationGapBDTO);
		
		Mockito.when(ubicationGapService.getUbicationsGap(Mockito.anyMap())).thenReturn(list);
		validatorUtils.validateCompatibilityTypeMCLUbication("codeMclType","codeUbication",false);
	}
	
	@Test
	public void caseOkValidateCompatibilityTypeMCLUbicationReturnCompatibilities() throws ImiException {
		
		List<CompatibilityTypesMCLUbication> list = new ArrayList<>();
		
		CompatibilityTypesMCLUbication comp = new CompatibilityTypesMCLUbication();
		comp.setCodeMCLType("MCLType");
		comp.setCodeUbicationType("ubicationtypeCode");
		list.add(comp);
		
		Mockito.when(compatibilityService.getCompatilibityTypeMCLUbication(Mockito.anyString(), Mockito.anyString())).thenReturn(list);
		validatorUtils.validateCompatibilityTypeMCLUbication("codeMclType","codeUbication",false);
	}

	@Test
	public void caseKoValidateCompatibilityTypeMCLUbication() throws ImiException {
		Mockito.doThrow(ImiException.class).when(ubicationGapService).getUbicationsGap(Mockito.anyMap());
		validatorUtils.validateCompatibilityTypeMCLUbication("codeMclType","codeUbication",true);
	}
	
	@Test
	public void caseOkValidateCompatibilityLogicalModelsUbicationLogicalModelsEmpty() throws ImiException {
		
		List<LogicalModelGapBDTO> list = new ArrayList<>();
		
		List<CompatibilityTypesMCLUbication> compatibilities = new ArrayList<>(); 
		CompatibilityTypesMCLUbication compatibility = new CompatibilityTypesMCLUbication();
		compatibility.setCodeUbicationType("codeTypeUbication");
		compatibility.setCodeMCLType("codeTypeMCL");
		compatibilities.add(compatibility);
		Mockito.when(logicalModelGapService.getLogicalModelsUbicationGap(("codeUbication"))).thenReturn(list);
		Mockito.when(compatibilityService.getCompatibleTypesMCLByTypeUbication("codeTypeUbication")).thenReturn(compatibilities);
		validatorUtils.validateCompatibilityLogicalModelsUbication("codeUbication","codeTypeUbication");
	}

	@Test
	public void caseOkValidateCompatibilityLogicalModelsUbicationCompatibilitiesEmpty() throws ImiException {
		
		List<LogicalModelGapBDTO> list = new ArrayList<>();
		LogicalModelGapBDTO mcl = new LogicalModelGapBDTO();
		mcl.setCode("code");
		list.add(mcl);
		List<CompatibilityTypesMCLUbication> compatibilities = new ArrayList<>(); 
		CompatibilityTypesMCLUbication compatibility = new CompatibilityTypesMCLUbication();
		compatibility.setCodeUbicationType("codeTypeUbication");
		compatibility.setCodeMCLType("codeTypeMCL");
		Mockito.when(logicalModelGapService.getLogicalModelsUbicationGap(("codeUbication"))).thenReturn(list);
		Mockito.when(compatibilityService.getCompatibleTypesMCLByTypeUbication("codeTypeUbication")).thenReturn(compatibilities);
		validatorUtils.validateCompatibilityLogicalModelsUbication("codeUbication","codeTypeUbication");
	}
	
	@Test
	public void caseOkValidateCompatibilityLogicalModelsUbication() throws ImiException {
		
		List<LogicalModelGapBDTO> list = new ArrayList<>();
		LogicalModelGapBDTO mcl = new LogicalModelGapBDTO();
		mcl.setCode("code");
		mcl.setTypeCode("codeTypeMCL");
		list.add(mcl);
		List<CompatibilityTypesMCLUbication> compatibilities = new ArrayList<>(); 
		CompatibilityTypesMCLUbication compatibility = new CompatibilityTypesMCLUbication();
		compatibility.setCodeUbicationType("codeTypeUbication");
		compatibility.setCodeMCLType("codeTypeMCL");
		compatibilities.add(compatibility);
		Mockito.when(logicalModelGapService.getLogicalModelsUbicationGap(("codeUbication"))).thenReturn(list);
		Mockito.when(compatibilityService.getCompatibleTypesMCLByTypeUbication("codeTypeUbication")).thenReturn(compatibilities);
		validatorUtils.validateCompatibilityLogicalModelsUbication("codeUbication","codeTypeUbication");
	}
	
	@Test
	public void caseOKValidateCompatibilityLogicalModelsUbicationNotFound() throws ImiException {
		
		List<LogicalModelGapBDTO> list = new ArrayList<>();
		LogicalModelGapBDTO mcl = new LogicalModelGapBDTO();
		mcl.setCode("code");
		
		list.add(mcl);
		List<CompatibilityTypesMCLUbication> compatibilities = new ArrayList<>(); 
		CompatibilityTypesMCLUbication compatibility = new CompatibilityTypesMCLUbication();
		compatibility.setCodeUbicationType("codeTypeUbication");
		compatibility.setCodeMCLType("codeTypeMCL");
		compatibilities.add(compatibility);
		Mockito.when(logicalModelGapService.getLogicalModelsUbicationGap(("codeUbication"))).thenReturn(list);
		Mockito.when(compatibilityService.getCompatibleTypesMCLByTypeUbication("codeTypeUbication")).thenReturn(compatibilities);
		validatorUtils.validateCompatibilityLogicalModelsUbication("codeUbication","codeTypeUbication");
	}
	
	
	@Test
	public void caseKoValidateCompatibilityLogicalModelsUbication() throws ImiException {
		Mockito.doThrow(ImiException.class).when(logicalModelGapService).getLogicalModelsUbicationGap(Mockito.anyString());
		validatorUtils.validateCompatibilityLogicalModelsUbication("codeUbication","");
	}
	
	@Test
	public void caseOkvalidateExistsUbicationGapByCodeType() throws ImiException {
		
		List<UbicationGapBDTO> list = new ArrayList<>();
		
		UbicationGapBDTO ubicationGapBDTO = new UbicationGapBDTO();
		ubicationGapBDTO.setCode("code");
		
		list.add(ubicationGapBDTO);
		
		Mockito.when(ubicationGapService.getUbicationsGapByCodeType("code","type")).thenReturn(list);
		validatorUtils.validateExistsUbicationGapByCodeType("code","type");
	}

	@Test
	public void caseOkvalidateNotExistsUbicationGapByCodeType() throws ImiException {
		
		List<UbicationGapBDTO> list = new ArrayList<>();
		
		Mockito.when(ubicationGapService.getUbicationsGapByCodeType("code","type")).thenReturn(list);
		validatorUtils.validateExistsUbicationGapByCodeType("code","type");
	}
	@Test
	public void caseKovalidateExistsUbicationGapByCodeType() throws ImiException {
		Mockito.doThrow(ImiException.class).when(ubicationGapService).getUbicationsGapByCodeType("code","type");
		validatorUtils.validateExistsUbicationGapByCodeType("code","type");
	}

	@Test
	public void caseOkValidateCompatibilitySensorsMCFEmptyLists() throws ImiException {
		
		List<String> sensorTypeCodes = new ArrayList<>();
		sensorTypeCodes.add("sensorTypeCode");
		validatorUtils.validateCompatibilityTypeMCFTypesSensor("MCFTypeCode",sensorTypeCodes);
	}
	
	@Test
	public void caseOkValidateCompatibilitySensorsMCF() throws ImiException {
	
		List<CompatibilityTypesMCFSensor> compatibilities = new ArrayList<>();
		CompatibilityTypesMCFSensor comp = new CompatibilityTypesMCFSensor();
		comp.setCodeSensorType("sensorType");
		compatibilities.add(comp);
		
		List<SensorBDTO> sensors = new ArrayList<>();
		SensorBDTO sensorBDTO = new SensorBDTO();
		sensorBDTO.setCode("SEN1");
		sensorBDTO.setCodeType("sensorType");
		sensors.add(sensorBDTO);
		
		List<String> sensorTypeCodes = new ArrayList<>();
		sensorTypeCodes.add("sensorTypeCode");
		Mockito.when(sensorService.getSensorsByCodes(Mockito.anyList())).thenReturn(sensors);
		Mockito.when(compatibilityService.getCompatibleTypesSensorByTypeMCF("MCFTypeCode")).thenReturn(compatibilities);
		
		validatorUtils.validateCompatibilityTypeMCFTypesSensor("MCFTypeCode",sensorTypeCodes);
	}
	
	@Test
	public void caseOkValidateCompatibilitySensorsMCFNotFound() throws ImiException {
	
		List<CompatibilityTypesMCFSensor> compatibilities = new ArrayList<>();
		CompatibilityTypesMCFSensor comp = new CompatibilityTypesMCFSensor();
		comp.setCodeSensorType("sensorType");
		compatibilities.add(comp);
		
		List<SensorBDTO> sensors = new ArrayList<>();
		SensorBDTO sensorBDTO = new SensorBDTO();
		sensorBDTO.setCode("SEN1");
		sensorBDTO.setCodeType("notSensorType");
		sensors.add(sensorBDTO);
		
		List<String> sensorTypeCodes = new ArrayList<>();
		sensorTypeCodes.add("sensorTypeCode");
		Mockito.when(sensorService.getSensorsByCodes(Mockito.anyList())).thenReturn(sensors);
		Mockito.when(compatibilityService.getCompatibleTypesSensorByTypeMCF("MCFTypeCode")).thenReturn(compatibilities);
		
		validatorUtils.validateCompatibilityTypeMCFTypesSensor("MCFTypeCode",sensorTypeCodes);
	}
	
	@Test
	public void caseKOValidateCompatibilitySensorsMCF() throws ImiException {
		
		List<String> sensorTypeCodes = new ArrayList<>();
		sensorTypeCodes.add("sensorTypeCode");
		Mockito.doThrow(ImiException.class).when(compatibilityService).getCompatibleTypesSensorByTypeMCF("MCFTypeCode");
		validatorUtils.validateCompatibilityTypeMCFTypesSensor("MCFTypeCode",sensorTypeCodes);
	}

	@Test
	public void caseOkValidateCompatibilitySensorsRRMMEmptyLists() throws ImiException {
		
		List<String> sensorTypeCodes = new ArrayList<>();
		sensorTypeCodes.add("sensorTypeCode");
		validatorUtils.validateCompatibilityTypeRRMMTypesSensor("RRMMTypeCode",sensorTypeCodes);
	}
	
	@Test
	public void caseOkValidateCompatibilitySensorsRRMM() throws ImiException {
	
		List<CompatibilityTypesRRMMSensor> compatibilities = new ArrayList<>();
		CompatibilityTypesRRMMSensor comp = new CompatibilityTypesRRMMSensor();
		comp.setCodeSensorType("sensorType");
		compatibilities.add(comp);
		
		List<SensorBDTO> sensors = new ArrayList<>();
		SensorBDTO sensorBDTO = new SensorBDTO();
		sensorBDTO.setCode("SEN1");
		sensorBDTO.setCodeType("sensorType");
		sensors.add(sensorBDTO);
		
		List<String> sensorTypeCodes = new ArrayList<>();
		sensorTypeCodes.add("sensorTypeCode");
		Mockito.when(sensorService.getSensorsByCodes(Mockito.anyList())).thenReturn(sensors);
		Mockito.when(compatibilityService.getCompatibleTypesSensorByTypeRRMM("RRMMTypeCode")).thenReturn(compatibilities);
		
		validatorUtils.validateCompatibilityTypeRRMMTypesSensor("RRMMTypeCode",sensorTypeCodes);
	}
	
	@Test
	public void caseOkValidateCompatibilitySensorsRRMMNotFound() throws ImiException {
	
		List<CompatibilityTypesRRMMSensor> compatibilities = new ArrayList<>();
		CompatibilityTypesRRMMSensor comp = new CompatibilityTypesRRMMSensor();
		comp.setCodeSensorType("sensorType");
		compatibilities.add(comp);
		
		List<SensorBDTO> sensors = new ArrayList<>();
		SensorBDTO sensorBDTO = new SensorBDTO();
		sensorBDTO.setCode("SEN1");
		sensorBDTO.setCodeType("notSensorType");
		sensors.add(sensorBDTO);
		
		List<String> sensorTypeCodes = new ArrayList<>();
		sensorTypeCodes.add("sensorTypeCode");
		Mockito.when(sensorService.getSensorsByCodes(Mockito.anyList())).thenReturn(sensors);
		Mockito.when(compatibilityService.getCompatibleTypesSensorByTypeRRMM("RRMMTypeCode")).thenReturn(compatibilities);
		
		validatorUtils.validateCompatibilityTypeRRMMTypesSensor("RRMMTypeCode",sensorTypeCodes);
	}
	
	@Test
	public void caseKOValidateCompatibilitySensorsRRMM() throws ImiException {
		
		List<String> sensorTypeCodes = new ArrayList<>();
		sensorTypeCodes.add("sensorTypeCode");
		Mockito.doThrow(ImiException.class).when(compatibilityService).getCompatibleTypesSensorByTypeRRMM("RRMMTypeCode");
		validatorUtils.validateCompatibilityTypeRRMMTypesSensor("RRMMTypeCode",sensorTypeCodes);
	}

	@Test
	public void caseOkValidateTypeSubTypeExpenseGap() throws ImiException {
		
		List<TypeSubtypeExpenseGapBDTO> list = new ArrayList<>();
		
		TypeSubtypeExpenseGapBDTO typeSubtypeExpenseGapBDTO = new TypeSubtypeExpenseGapBDTO();
		typeSubtypeExpenseGapBDTO.setTypeCode("code");
		typeSubtypeExpenseGapBDTO.setSubtypeCode("subcode");
		
		list.add(typeSubtypeExpenseGapBDTO);
		
		Mockito.when(typeSubtypeExpenseGapService.getTypeSubtypesExpenseGap("code")).thenReturn(list);
		validatorUtils.validateTypeSubtypeExpenseGap("code","subcode","entityName");
	}
	
	@Test
	public void caseOkValidateTypeSubTypeExpenseGapNotFound() throws ImiException {
		
		List<TypeSubtypeExpenseGapBDTO> list = new ArrayList<>();
		
		TypeSubtypeExpenseGapBDTO typeSubtypeExpenseGapBDTO = new TypeSubtypeExpenseGapBDTO();
		typeSubtypeExpenseGapBDTO.setTypeCode("code");
		
		list.add(typeSubtypeExpenseGapBDTO);
		
		Mockito.when(typeSubtypeExpenseGapService.getTypeSubtypesExpenseGap("code")).thenReturn(list);
		validatorUtils.validateTypeSubtypeExpenseGap("code","","entityName");
	}

	@Test
	public void caseOkValidateTypeSubTypeExpenseGapEmptyList() throws ImiException {
		
		List<TypeSubtypeExpenseGapBDTO> list = new ArrayList<>();
		
		
		Mockito.when(typeSubtypeExpenseGapService.getTypeSubtypesExpenseGap("code")).thenReturn(list);
		validatorUtils.validateTypeSubtypeExpenseGap("code","","entityName");
	}
	@Test
	public void caseKoValidateTypeSubTypeExpenseGap() throws ImiException {
		Mockito.doThrow(ImiException.class).when(typeSubtypeExpenseGapService).getTypeSubtypesExpenseGap("code");
		validatorUtils.validateTypeSubtypeExpenseGap("code","","entityName");
	}
	
	@Test
	public void caseOkValidateExistAsssociationMCLMCF() throws ImiException {
		
		List<PhysicalModelGapBDTO> list = new ArrayList<>();
		
		PhysicalModelGapBDTO physicalModelGapBDTO = new PhysicalModelGapBDTO();
		physicalModelGapBDTO.setCode("code");
		
		list.add(physicalModelGapBDTO);
		
		Mockito.when(physicalModelGapService.getPhysicalModelsGap(Mockito.anyString(),Mockito.anyString(),Mockito.anyString())).thenReturn(list);
		validatorUtils.validateExistsAssociationMCLMCF("codeUbication", "codeMCL", "codeMCF");
	}
	
	@Test
	public void caseOkValidateExistAsssociationMCLMCFNotFound() throws ImiException {
		
		List<PhysicalModelGapBDTO> list = new ArrayList<>();
		
		
		Mockito.when(physicalModelGapService.getPhysicalModelsGap(Mockito.anyString(),Mockito.anyString(),Mockito.anyString())).thenReturn(list);
		validatorUtils.validateExistsAssociationMCLMCF("codeUbication", "codeMCL", "codeMCF");
	}
	@Test
	public void caseKoValidateExistAsssociationMCLMCF() throws ImiException {
		Mockito.doThrow(ImiException.class).when(physicalModelGapService).getPhysicalModelsGap(Mockito.anyString(),Mockito.anyString(),Mockito.anyString());
		validatorUtils.validateExistsAssociationMCLMCF("codeUbication", "codeMCL", "codeMCF");
	}
	
	@Test
	public void caseOkValidateExistMaterialResourceNoVehicle() throws ImiException {
		
		List<MaterialResourceBDTO> list = new ArrayList<>();
		
		MaterialResourceBDTO materialResourceBDTO = new MaterialResourceBDTO();
		materialResourceBDTO.setCode("code");
		
		list.add(materialResourceBDTO);
		
		Mockito.when(rmNoVehiclesService.getMaterialResourcesNoVehicleByCode(Mockito.anyMap())).thenReturn(list);
		validatorUtils.validateExistsMaterialResourceNoVehicle("code","codeRRMM");
	}
	
	@Test
	public void caseOkValidateNotExistMaterialResourceNoVehicle() throws ImiException {
		
		List<MaterialResourceBDTO> list = new ArrayList<>();
		
		Mockito.when(rmNoVehiclesService.getMaterialResourcesNoVehicleByCode(Mockito.anyMap())).thenReturn(list);
		validatorUtils.validateExistsMaterialResourceNoVehicle("code","codeRRMM");
	}
	@Test
	public void caseKoValidateExistMaterialResourceNoVehicle() throws ImiException {
		Mockito.doThrow(ImiException.class).when(rmNoVehiclesService).getMaterialResourcesNoVehicleByCode(Mockito.anyMap());
		validatorUtils.validateExistsMaterialResourceNoVehicle("code","codeRRMM");
	}
	

	@Test
	public void caseOkValidateExistCommerce() throws ImiException {
		
		List<CommerceGapBDTO> list = new ArrayList<>();
		
		CommerceGapBDTO commerceGapBDTO = new CommerceGapBDTO();
		commerceGapBDTO.setCode("code");
		
		list.add(commerceGapBDTO);
		
		Mockito.when(commerceGapService.getCommercesGap(Mockito.anyMap())).thenReturn(list);
		validatorUtils.validateExistsCommerceGap("codeRRMM",true);
	}
	
	@Test
	public void caseOkValidateExistCommerceNoCheckStatusDelete() throws ImiException {
		
		List<CommerceGapBDTO> list = new ArrayList<>();
		
		CommerceGapBDTO commerceGapBDTO = new CommerceGapBDTO();
		commerceGapBDTO.setCode("code");
		
		list.add(commerceGapBDTO);
		
		Mockito.when(commerceGapService.getCommercesGap(Mockito.anyMap())).thenReturn(list);
		validatorUtils.validateExistsCommerceGap("codeRRMM",false);
	}
	@Test
	public void caseOkValidateNotExistCommerce() throws ImiException {
		
		List<CommerceGapBDTO> list = new ArrayList<>();
		
		Mockito.when(commerceGapService.getCommercesGap(Mockito.anyMap())).thenReturn(list);
		validatorUtils.validateExistsCommerceGap("codeRRMM",true);
	}
	@Test
	public void caseKoValidateExistCommerce() throws ImiException {
		Mockito.doThrow(ImiException.class).when(commerceGapService).getCommercesGap(Mockito.anyMap());
		validatorUtils.validateExistsCommerceGap("codeRRMM",true);
	}

	
	@Test
	public void caseOkValidateExistListPhysicalModelGap() throws ImiException {
		
		List<String> listCodesMcf = new ArrayList<>();
		listCodesMcf.add("codeMcf");
	
		List<PhysicalModelGapBDTO> physicalModelsGap = new ArrayList<>(); 
		
		PhysicalModelGapBDTO physicalModelGapBDTO = new PhysicalModelGapBDTO();
		physicalModelGapBDTO.setCode("codeMcf");
		physicalModelsGap.add(physicalModelGapBDTO);
		Mockito.when(physicalModelGapService.getPhysicalModelsGap(Mockito.anyMap())).thenReturn(physicalModelsGap);
		
		validatorUtils.validateExistsListPhysicalModelGap(listCodesMcf,true);
	}
	
	@Test
	public void caseOkValidateNotExistListPhysicalModelGap() throws ImiException {
		
		List<String> listCodesMcf = new ArrayList<>();
		listCodesMcf.add("notCodeMcf");
	
		List<PhysicalModelGapBDTO> physicalModelsGap = new ArrayList<>(); 
		
		PhysicalModelGapBDTO physicalModelGapBDTO = new PhysicalModelGapBDTO();
		physicalModelGapBDTO.setCode("codeMcf");
		physicalModelsGap.add(physicalModelGapBDTO);
		Mockito.when(physicalModelGapService.getPhysicalModelsGap(Mockito.anyMap())).thenReturn(physicalModelsGap);
		
		validatorUtils.validateExistsListPhysicalModelGap(listCodesMcf,true);
	}
	@Test
	public void caseValidateExistListPhysicalModelGapNotCheckStatusDelete() throws ImiException {
		
		List<String> listCodesMcf = new ArrayList<>();
		listCodesMcf.add("codeMcf");
	
		validatorUtils.validateExistsListPhysicalModelGap(listCodesMcf,false);
	}
	
	@Test
	public void caseKoValidateExistListPhysicalModelGapNotCheckStatusDelete() throws ImiException {
		List<String> listCodesMcf = new ArrayList<>();
		listCodesMcf.add("codeMcf");
		
		Mockito.doThrow(ImiException.class).when(physicalModelGapService).getPhysicalModelsGap(Mockito.anyMap());
		validatorUtils.validateExistsListPhysicalModelGap(listCodesMcf,true);
	}
	
	@Test
	public void caseOkValidateExistsAmortizableMaterialResourceGap() throws ImiException {
		
		List<MaterialResourceGapBDTO> list = new ArrayList<>();
		
		MaterialResourceGapBDTO materialResourceGapBDTO = new MaterialResourceGapBDTO();
		materialResourceGapBDTO.setCode("code");
		materialResourceGapBDTO.setAmortizationSubject(ValidationConstants.VALUE_RRMM_AMORTIZABLE);
		list.add(materialResourceGapBDTO);
		
		Mockito.when(materialResourceGapService.getMaterialResourcesGap("code")).thenReturn(list);
		
		validatorUtils.validateExistsAmortizableMaterialResourceGap("code");
	}
	
	@Test
	public void caseValidateExistsAmortizableMaterialResourceGapEmptyList() throws ImiException {
		
		List<MaterialResourceGapBDTO> list = new ArrayList<>();
		
		
		Mockito.when(materialResourceGapService.getMaterialResourcesGap("code")).thenReturn(list);
		
		validatorUtils.validateExistsAmortizableMaterialResourceGap("code");
	}
	
	@Test
	public void caseValidateExistsAmortizableMaterialResourceGapNotAmortizable() throws ImiException {
		
		List<MaterialResourceGapBDTO> list = new ArrayList<>();
		
		MaterialResourceGapBDTO materialResourceGapBDTO = new MaterialResourceGapBDTO();
		materialResourceGapBDTO.setCode("code");
		materialResourceGapBDTO.setAmortizationSubject("0");
		list.add(materialResourceGapBDTO);
		
		
		Mockito.when(materialResourceGapService.getMaterialResourcesGap("code")).thenReturn(list);
		
		validatorUtils.validateExistsAmortizableMaterialResourceGap("code");
	}
	@Test
	public void caseKoValidateExistsAmortizableMaterialResourceGap() throws ImiException {
		
		Mockito.doThrow(ImiException.class).when(materialResourceGapService).getMaterialResourcesGap(Mockito.anyString());
		validatorUtils.validateExistsAmortizableMaterialResourceGap("code");
	}
	
	
	@Test
	public void caseOkValidateExistsItineraryGap() throws ImiException {
		
		List<ItineraryGapBDTO> list = new ArrayList<>();
		
		ItineraryGapBDTO itineraryGapBDTO = new ItineraryGapBDTO();
		itineraryGapBDTO.setCode("code");
		list.add(itineraryGapBDTO);
		
		Mockito.when(itineraryGapService.getItinerariesGap(Mockito.anyMap())).thenReturn(list);
		
		validatorUtils.validateExistsItineraryGap("code");
	}
	
	@Test
	public void caseValidateExistsItineraryGapEmptyList() throws ImiException {
		
		List<ItineraryGapBDTO> list = new ArrayList<>();
		
		
		Mockito.when(itineraryGapService.getItinerariesGap(Mockito.anyMap())).thenReturn(list);
		
		validatorUtils.validateExistsItineraryGap("code");
	}
	
	
	@Test
	public void caseKoValidateExistsItineraryGap() throws ImiException {
		
		Mockito.doThrow(ImiException.class).when(itineraryGapService).getItinerariesGap(Mockito.anyMap());
		validatorUtils.validateExistsItineraryGap("code");
	}

	@Test
	public void caseOkValidateExistsEventGap() throws ImiException {
		
		List<EventGapBDTO> list = new ArrayList<>();
		
		EventGapBDTO eventGapBDTO = new EventGapBDTO();
		eventGapBDTO.setCode("code");
		list.add(eventGapBDTO);
		
		Mockito.when(eventGapService.getEventsGap(Mockito.anyString())).thenReturn(list);
		
		validatorUtils.validateExistsEventGap("code");
	}
	
	@Test
	public void caseValidateExistsEventGapEmptyList() throws ImiException {
		
		List<EventGapBDTO> list = new ArrayList<>();
		
		
		Mockito.when(eventGapService.getEventsGap(Mockito.anyString())).thenReturn(list);
		
		validatorUtils.validateExistsEventGap("code");
	}
	
	
	@Test
	public void caseKoValidateExistsEventGap() throws ImiException {
		
		Mockito.doThrow(ImiException.class).when(eventGapService).getEventsGap(Mockito.anyString());
		validatorUtils.validateExistsEventGap("code");
	}
	
	@Test
	public void caseOkvalidateExistsPlanificatedItinerary() throws ImiException {
		
		List<PlanificationGapBDTO> list = new ArrayList<>();
		
		PlanificationGapBDTO planificationGapBDTO = new PlanificationGapBDTO();
		planificationGapBDTO.setCodeItinerary("code");
		list.add(planificationGapBDTO);
		
		Mockito.when(planificationGapService.getPlanificationsGap(Mockito.anyMap())).thenReturn(list);
		
		validatorUtils.validateExistsPlanificatedItinerary("codeItinerary", "dateExecution");
	}

	@Test
	public void caseOkvalidateExistsPlanificatedItineraryEmptyList() throws ImiException {
		
		List<PlanificationGapBDTO> list = new ArrayList<>();
		
		
		Mockito.when(planificationGapService.getPlanificationsGap(Mockito.anyMap())).thenReturn(list);
		
		validatorUtils.validateExistsPlanificatedItinerary("codeItinerary", "dateExecution");
	}
	@Test
	public void caseKovalidateExistsPlanificatedItinerary() throws ImiException {
		
		Mockito.doThrow(ImiException.class).when(planificationGapService).getPlanificationsGap(Mockito.anyMap());		
		
		validatorUtils.validateExistsPlanificatedItinerary("codeItinerary", "dateExecution");
	}

	@Test
	public void caseOkvalidateCodeItinerary() throws ImiException {
		
		List<ServiceGapBDTO> list = new ArrayList<>();
		
		ServiceGapBDTO serviceGapBDTO = new ServiceGapBDTO();
		serviceGapBDTO.setCodeService("code");
		list.add(serviceGapBDTO);

		List<ValueListGapBDTO> valuesList = new ArrayList<>();
		
		ValueListGapBDTO valueListRRMMGapBDTO = new ValueListGapBDTO();
		ValueListGapBDTO valueListTurnGapBDTO = new ValueListGapBDTO();
		valueListRRMMGapBDTO.setCode("R2");
		valueListRRMMGapBDTO.setDescription("description");
		valueListRRMMGapBDTO.setEntity("entity");
		valueListTurnGapBDTO.setCode("M");
		valueListTurnGapBDTO.setDescription("description");
		valueListTurnGapBDTO.setEntity("entity");
		valuesList.add(valueListRRMMGapBDTO);
		valuesList.add(valueListTurnGapBDTO);
		
		Mockito.when(valueListGapService.getValueListGap(new Entity(ValueListConstants.RRMM_TYPE))).thenReturn(valuesList);
		Mockito.when(valueListGapService.getValueListGap(new Entity(ValueListConstants.TURN))).thenReturn(valuesList);
		Mockito.when(serviceGapService.getServicesGap(Mockito.anyMap())).thenReturn(list);
		validatorUtils.validateCodeItinerary("5CNPLALTRX1R2EL034M");
	}

	

	@Test
	public void casevalidateCodeItineraryNotExistsTurn() throws ImiException {
		
		List<ServiceGapBDTO> list = new ArrayList<>();
		
		ServiceGapBDTO serviceGapBDTO = new ServiceGapBDTO();
		serviceGapBDTO.setCodeService("code");
		list.add(serviceGapBDTO);

		List<ValueListGapBDTO> valuesList = new ArrayList<>();
		List<ValueListGapBDTO> valuesListEmpty = new ArrayList<>();
		ValueListGapBDTO valueListRRMMGapBDTO = new ValueListGapBDTO();
		ValueListGapBDTO valueListTurnGapBDTO = new ValueListGapBDTO();
		valueListRRMMGapBDTO.setCode("R2");
		valueListRRMMGapBDTO.setDescription("description");
		valueListRRMMGapBDTO.setEntity("entity");
		valueListTurnGapBDTO.setCode("M");
		valueListTurnGapBDTO.setDescription("description");
		valueListTurnGapBDTO.setEntity("entity");
		valuesList.add(valueListRRMMGapBDTO);
		valuesList.add(valueListTurnGapBDTO);
		
		Mockito.when(valueListGapService.getValueListGap(new Entity(ValueListConstants.RRMM_TYPE))).thenReturn(valuesList);
		Mockito.when(valueListGapService.getValueListGap(new Entity(ValueListConstants.TURN))).thenReturn(valuesListEmpty);
		Mockito.when(serviceGapService.getServicesGap(Mockito.anyMap())).thenReturn(list);
		validatorUtils.validateCodeItinerary("5CNPLALTRX1R2EL034M");
	}
	
	@Test
	public void casevalidateCodeItineraryNotExistsRRMM() throws ImiException {
		
		List<ServiceGapBDTO> list = new ArrayList<>();
		
		ServiceGapBDTO serviceGapBDTO = new ServiceGapBDTO();
		serviceGapBDTO.setCodeService("code");
		list.add(serviceGapBDTO);

		List<ValueListGapBDTO> valuesList = new ArrayList<>();
		List<ValueListGapBDTO> valuesListEmpty = new ArrayList<>();
		ValueListGapBDTO valueListRRMMGapBDTO = new ValueListGapBDTO();
		ValueListGapBDTO valueListTurnGapBDTO = new ValueListGapBDTO();
		valueListRRMMGapBDTO.setCode("R2");
		valueListRRMMGapBDTO.setDescription("description");
		valueListRRMMGapBDTO.setEntity("entity");
		valueListTurnGapBDTO.setCode("M");
		valueListTurnGapBDTO.setDescription("description");
		valueListTurnGapBDTO.setEntity("entity");
		valuesList.add(valueListRRMMGapBDTO);
		valuesList.add(valueListTurnGapBDTO);
		
		Mockito.when(valueListGapService.getValueListGap(new Entity(ValueListConstants.RRMM_TYPE))).thenReturn(valuesListEmpty);
		Mockito.when(valueListGapService.getValueListGap(new Entity(ValueListConstants.TURN))).thenReturn(valuesList);
		Mockito.when(serviceGapService.getServicesGap(Mockito.anyMap())).thenReturn(list);
		validatorUtils.validateCodeItinerary("5CNPLALTRX1R2EL034M");

	}
	
	@Test
	public void casevalidateCodeItineraryNotExistsService() throws ImiException {
		
		List<ServiceGapBDTO> list = new ArrayList<>();
		
		ServiceGapBDTO serviceGapBDTO = new ServiceGapBDTO();
		serviceGapBDTO.setCodeService("code");
		
		
		Mockito.when(serviceGapService.getServicesGap(Mockito.anyMap())).thenReturn(list);
		validatorUtils.validateCodeItinerary("5CNPLALTRX1R2EL034M");
	}
	
	@Test 
	public void caseKOValidateCodeItinerary() throws ImiException {
		
		List<ServiceGapBDTO> list = new ArrayList<>();
		
		ServiceGapBDTO serviceGapBDTO = new ServiceGapBDTO();
		serviceGapBDTO.setCodeService("code");
		
		
		Mockito.when(serviceGapService.getServicesGap(Mockito.anyMap())).thenReturn(list);
		validatorUtils.validateCodeItinerary("");
	}
	
	@Test
	public void caseOkValidateGroupEventType() throws ImiException {
		
		List<GroupEventGapBDTO> list = new ArrayList<>();
		
		GroupEventGapBDTO groupEventGapBDTO = new GroupEventGapBDTO();
		groupEventGapBDTO.setCodeGroup("code");
		
		list.add(groupEventGapBDTO);
		
		Mockito.when(groupEventGapService.getGroupEventsGap(Mockito.anyMap())).thenReturn(list);
		validatorUtils.validateExistsGroupEventType("code","code");
	}

	@Test
	public void caseOkValidateGroupEventTypeEmptyList() throws ImiException {
		
		List<GroupEventGapBDTO> list = new ArrayList<>();
		
		Mockito.when(groupEventGapService.getGroupEventsGap(Mockito.anyMap())).thenReturn(list);
		validatorUtils.validateExistsGroupEventType("code","code");
	}
	
	
	@Test
	public void caseKoGroupEventTypeGap() throws ImiException {
		Mockito.doThrow(ImiException.class).when(groupEventGapService).getGroupEventsGap(Mockito.anyMap());
		validatorUtils.validateExistsGroupEventType("code","code");
	}

	@Test
	public void caseOkValidatePlantWeighingGap() throws ImiException {
		
		List<PlantWeighingGapBDTO> list = new ArrayList<>();
		
		PlantWeighingGapBDTO plantWeighingGapBDTO = new PlantWeighingGapBDTO();
		plantWeighingGapBDTO.setCode("code");
		
		list.add(plantWeighingGapBDTO);
		
		Mockito.when(plantWeighingGapService.getPlantWeighingsGap(Mockito.anyString())).thenReturn(list);
		validatorUtils.validateExistsPlantWeighingGap("code");
	}

	@Test
	public void caseValidatePlantWeighingEmptyList() throws ImiException {
		
		List<PlantWeighingGapBDTO> list = new ArrayList<>();
		
		
		Mockito.when(plantWeighingGapService.getPlantWeighingsGap(Mockito.anyString())).thenReturn(list);
		validatorUtils.validateExistsPlantWeighingGap("code");
	}
	
	
	@Test
	public void caseKoValidatePlantWeighingGap() throws ImiException {
		Mockito.doThrow(ImiException.class).when(plantWeighingGapService).getPlantWeighingsGap(Mockito.anyString());
		validatorUtils.validateExistsPlantWeighingGap("code");
	}
	
	@Test
	public void caseOkValidateExistsTimeClockSummary() throws ImiException {
		
		List<TimeClockSummaryBDTO> list = new ArrayList<>();
		
		TimeClockSummaryBDTO timeClockSummaryBDTO = new TimeClockSummaryBDTO();
		timeClockSummaryBDTO.setCodeInstallation("code");
		timeClockSummaryBDTO.setCodeContract("C");
		
		list.add(timeClockSummaryBDTO);
		
		Mockito.when(timeClockSummaryService.getTimeClockSummaries(Mockito.anyMap())).thenReturn(list);
		validatorUtils.validateExistsTimeClockSummary("codeContract", "codeInstallation", "date") ;
	}

	@Test
	public void caseValidateTimeClockSummaryEmptyList() throws ImiException {
		
		List<TimeClockSummaryBDTO> list = new ArrayList<>();
		
		
		Mockito.when(timeClockSummaryService.getTimeClockSummaries(Mockito.anyMap())).thenReturn(list);
		validatorUtils.validateExistsTimeClockSummary("codeContract", "codeInstallation", "date") ;
	}
	
	
	@Test
	public void caseKoValidateTimeClockSummary() throws ImiException {
		Mockito.doThrow(ImiException.class).when(timeClockSummaryService).getTimeClockSummaries(Mockito.anyMap());
		validatorUtils.validateExistsTimeClockSummary("codeContract", "codeInstallation", "date") ;
	}
}
