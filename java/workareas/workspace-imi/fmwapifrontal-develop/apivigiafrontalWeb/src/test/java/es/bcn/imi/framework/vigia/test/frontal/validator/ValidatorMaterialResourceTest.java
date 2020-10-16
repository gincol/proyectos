package es.bcn.imi.framework.vigia.test.frontal.validator;

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

import es.bcn.imi.framework.vigia.frontal.validator.ValidatorActuation;
import es.bcn.imi.framework.vigia.frontal.validator.ValidatorDocumentarySupport;
import es.bcn.imi.framework.vigia.frontal.validator.ValidatorMaterialResource;
import es.bcn.imi.framework.vigia.frontal.validator.ValidatorNoVehicle;
import es.bcn.imi.framework.vigia.frontal.validator.ValidatorUtils;
import es.bcn.imi.framework.vigia.frontal.web.rest.config.RestServerConfig;
import es.bcn.vigia.fmw.libcommons.constants.ValueListConstants;
import es.bcn.vigia.fmw.libcommons.valueobject.Entity;
import es.bcn.vigia.fmw.libcommons.valueobject.Type;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.ActuationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.DocumentarySupportRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.MaterialResourceRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.ActuationRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.DocumentarySupportRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.MaterialResourceRDTOStub;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RestServerConfig.class })
@TestPropertySource(locations = {"classpath:app/config/application.properties"})
@WebAppConfiguration
@SuppressWarnings("unchecked")
public class ValidatorMaterialResourceTest {

	@Mock
	private ValidatorUtils validator;

	@Mock
	private ValidatorNoVehicle validatorNoVehicle	;

	@Mock
	private ValidatorDocumentarySupport validatorDocumentarySupport;

	@Mock
	private ValidatorActuation validatorActuation;

	@InjectMocks
	private ValidatorMaterialResource validatorMaterialResource;
	
	private MaterialResourceRDTO materialResourceRDTO;

	private DocumentarySupportRDTO documentarySupportRDTO;
	
	private ActuationRDTO actuationRDTO;
	
	private ReturnRDTO returnRDTO;
	
	private ReturnRDTO returnRDTOKO;
	



	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		materialResourceRDTO = MaterialResourceRDTOStub.defaultOne();
		returnRDTO = ReturnRDTOStub.getSuccessMessage();
		returnRDTOKO = new ReturnRDTO();
		returnRDTOKO.setCode("KO");
		documentarySupportRDTO = DocumentarySupportRDTOStub.defaultOne();
		actuationRDTO = ActuationRDTOStub.defaultOne();

	}
	
	@Test
	public void caseValidateInsertNotExistsSensor() {
		
		Mockito.when(validator.validateSensorsContract(Mockito.anyString(), Mockito.anyList())).thenReturn(returnRDTOKO);
		
		materialResourceRDTO.setCodeBrand("");
		materialResourceRDTO.setCodeModel("");
		materialResourceRDTO.setRegistration("WEBSERVICE");
		validatorMaterialResource.validateInsert(materialResourceRDTO);
		
	}
	
	@Test
	public void caseValidateInsertNotCompatibleSensor() {
		
		Mockito.when(validator.validateBrandModelGap("", "")).thenReturn(returnRDTO);
		Mockito.when(validator.validateSensorsContract(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		Mockito.when(validator.validateCompatibilityTypeRRMMTypesSensor(Mockito.anyString(), Mockito.anyList())).thenReturn(returnRDTOKO);
		materialResourceRDTO.setCodeBrand("");
		materialResourceRDTO.setCodeModel("");
		materialResourceRDTO.setRegistration("WEBSERVICE");
		validatorMaterialResource.validateInsert(materialResourceRDTO);
		
	}
	
	@Test
	public void caseValidateNotExistsBrand() {
		
		Mockito.when(validator.validateSensorsContract(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		Mockito.when(validator.validateCompatibilityTypeRRMMTypesSensor(Mockito.anyString(), Mockito.anyList())).thenReturn(returnRDTO);
		
		Mockito.when(validator.validateBrandModelGap("", "")).thenReturn(returnRDTOKO);
		materialResourceRDTO.setCodeBrand("");
		materialResourceRDTO.setCodeModel("");
		materialResourceRDTO.setRegistration("WEBSERVICE");
		
		validatorMaterialResource.validateInsert(materialResourceRDTO);
	}
	
	@Test
	public void caseValidateNotExistsEuroEstandard() {
		materialResourceRDTO.setCodeBrand("");
		materialResourceRDTO.setCodeModel("");
		materialResourceRDTO.setEuroStandard("");
		materialResourceRDTO.setTypeEmissions("");
		materialResourceRDTO.setRegistration("WEBSERVICE");
		Mockito.when(validator.validateSensorsContract(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		Mockito.when(validator.validateCompatibilityTypeRRMMTypesSensor(Mockito.anyString(), Mockito.anyList())).thenReturn(returnRDTO);
		
		Mockito.when(validator.validateBrandModelGap("", "")).thenReturn(returnRDTO);
		Mockito.when(validator.validateValueList("",new Type(ValueListConstants.EURO_STANDARD))).thenReturn(false);
		
		
		validatorMaterialResource.validateInsert(materialResourceRDTO);
	}
	
	@Test
	public void caseValidateNotExistsEmissions() {
		materialResourceRDTO.setCodeBrand("");
		materialResourceRDTO.setCodeModel("");
		materialResourceRDTO.setEuroStandard("");
		materialResourceRDTO.setTypeEmissions("");
		materialResourceRDTO.setRegistration("WEBSERVICE");
		Mockito.when(validator.validateSensorsContract(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		Mockito.when(validator.validateCompatibilityTypeRRMMTypesSensor(Mockito.anyString(), Mockito.anyList())).thenReturn(returnRDTO);
		
		Mockito.when(validator.validateBrandModelGap("", "")).thenReturn(returnRDTO);
		Mockito.when(validator.validateValueList("",new Type(ValueListConstants.EURO_STANDARD))).thenReturn(true);
		Mockito.when(validator.validateValueList("",new Type(ValueListConstants.EMISSION_TYPE))).thenReturn(false);
		
		
		validatorMaterialResource.validateInsert(materialResourceRDTO);
	}
	
	@Test
	public void caseValidateNotExistsState() {
		materialResourceRDTO.setCodeBrand("");
		materialResourceRDTO.setCodeModel("");
		materialResourceRDTO.setEuroStandard("");
		materialResourceRDTO.setTypeEmissions("");
		materialResourceRDTO.setCodeState("");
		materialResourceRDTO.setRegistration("WEBSERVICE");
		Mockito.when(validator.validateSensorsContract(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		Mockito.when(validator.validateCompatibilityTypeRRMMTypesSensor(Mockito.anyString(), Mockito.anyList())).thenReturn(returnRDTO);
		
		Mockito.when(validator.validateBrandModelGap("", "")).thenReturn(returnRDTO);
		Mockito.when(validator.validateValueList("",new Type(ValueListConstants.EURO_STANDARD))).thenReturn(true);
		Mockito.when(validator.validateValueList("",new Type(ValueListConstants.EMISSION_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap("",new Entity(ValueListConstants.RRMM_STATUS))).thenReturn(false);
		
		validatorMaterialResource.validateInsert(materialResourceRDTO);
	}
	
	@Test
	public void caseValidateNotExistsMotorEnergy() {
		materialResourceRDTO.setCodeBrand("");
		materialResourceRDTO.setCodeModel("");
		materialResourceRDTO.setEuroStandard("");
		materialResourceRDTO.setTypeEmissions("");
		materialResourceRDTO.setCodeState("");
		materialResourceRDTO.setCodeMotorEnergyType("");
		materialResourceRDTO.setRegistration("WEBSERVICE");
		Mockito.when(validator.validateSensorsContract(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		Mockito.when(validator.validateCompatibilityTypeRRMMTypesSensor(Mockito.anyString(), Mockito.anyList())).thenReturn(returnRDTO);
		
		Mockito.when(validator.validateBrandModelGap("", "")).thenReturn(returnRDTO);
		Mockito.when(validator.validateValueList("",new Type(ValueListConstants.EURO_STANDARD))).thenReturn(true);
		Mockito.when(validator.validateValueList("",new Type(ValueListConstants.EMISSION_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap("",new Entity(ValueListConstants.RRMM_STATUS))).thenReturn(true);
		Mockito.when(validator.validateValueListGap("",new Entity(ValueListConstants.MOTOR_ENERGY_TYPE))).thenReturn(false);
		
		validatorMaterialResource.validateInsert(materialResourceRDTO);
	}
	
	@Test
	public void caseValidateNotExistsRRMMType() {
		materialResourceRDTO.setCodeBrand("");
		materialResourceRDTO.setCodeModel("");
		materialResourceRDTO.setEuroStandard("");
		materialResourceRDTO.setTypeEmissions("");
		materialResourceRDTO.setCodeState("");
		materialResourceRDTO.setCodeMotorEnergyType("");
		materialResourceRDTO.setCodeType("");
		materialResourceRDTO.setRegistration("WEBSERVICE");
		Mockito.when(validator.validateSensorsContract(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		Mockito.when(validator.validateCompatibilityTypeRRMMTypesSensor(Mockito.anyString(), Mockito.anyList())).thenReturn(returnRDTO);
		
		Mockito.when(validator.validateBrandModelGap("", "")).thenReturn(returnRDTO);
		Mockito.when(validator.validateValueList("",new Type(ValueListConstants.EURO_STANDARD))).thenReturn(true);
		Mockito.when(validator.validateValueList("",new Type(ValueListConstants.EMISSION_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap("",new Entity(ValueListConstants.RRMM_STATUS))).thenReturn(true);
		Mockito.when(validator.validateValueListGap("",new Entity(ValueListConstants.MOTOR_ENERGY_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap("",new Entity(ValueListConstants.RRMM_TYPE))).thenReturn(false);
		
		validatorMaterialResource.validateInsert(materialResourceRDTO);
	}
	
	@Test
	public void caseValidateNotExistsTitularityType() {
		materialResourceRDTO.setCodeBrand("");
		materialResourceRDTO.setCodeModel("");
		materialResourceRDTO.setEuroStandard("");
		materialResourceRDTO.setTypeEmissions("");
		materialResourceRDTO.setCodeState("");
		materialResourceRDTO.setCodeMotorEnergyType("");
		materialResourceRDTO.setCodeType("");
		materialResourceRDTO.setCodeTypeTitularity("");
		materialResourceRDTO.setRegistration("WEBSERVICE");
		Mockito.when(validator.validateSensorsContract(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		Mockito.when(validator.validateCompatibilityTypeRRMMTypesSensor(Mockito.anyString(), Mockito.anyList())).thenReturn(returnRDTO);
		
		Mockito.when(validator.validateBrandModelGap("", "")).thenReturn(returnRDTO);
		Mockito.when(validator.validateValueList("",new Type(ValueListConstants.EURO_STANDARD))).thenReturn(true);
		Mockito.when(validator.validateValueList("",new Type(ValueListConstants.EMISSION_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap("",new Entity(ValueListConstants.RRMM_STATUS))).thenReturn(true);
		Mockito.when(validator.validateValueListGap("",new Entity(ValueListConstants.MOTOR_ENERGY_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap("",new Entity(ValueListConstants.RRMM_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap("",new Entity(ValueListConstants.TITULARITY_TYPE))).thenReturn(false);
		
		validatorMaterialResource.validateInsert(materialResourceRDTO);
	}
	
	@Test
	public void caseValidateNotExistsInstallation() {
		materialResourceRDTO.setCodeBrand("");
		materialResourceRDTO.setCodeModel("");
		materialResourceRDTO.setEuroStandard("");
		materialResourceRDTO.setTypeEmissions("");
		materialResourceRDTO.setCodeState("");
		materialResourceRDTO.setCodeMotorEnergyType("");
		materialResourceRDTO.setCodeType("");
		materialResourceRDTO.setCodeTypeTitularity("");
		materialResourceRDTO.setCodeInstallation("CAAA");
		materialResourceRDTO.setRegistration("WEBSERVICE");
		Mockito.when(validator.validateSensorsContract(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		Mockito.when(validator.validateCompatibilityTypeRRMMTypesSensor(Mockito.anyString(), Mockito.anyList())).thenReturn(returnRDTO);
		
		Mockito.when(validator.validateBrandModelGap("", "")).thenReturn(returnRDTO);
		Mockito.when(validator.validateValueList("",new Type(ValueListConstants.EURO_STANDARD))).thenReturn(true);
		Mockito.when(validator.validateValueList("",new Type(ValueListConstants.EMISSION_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap("",new Entity(ValueListConstants.RRMM_STATUS))).thenReturn(true);
		Mockito.when(validator.validateValueListGap("",new Entity(ValueListConstants.MOTOR_ENERGY_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap("",new Entity(ValueListConstants.RRMM_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap("",new Entity(ValueListConstants.TITULARITY_TYPE))).thenReturn(true);
		Mockito.when(validator.validateExistsInstallationGap("CAAA", true)).thenReturn(false);
		validatorMaterialResource.validateInsert(materialResourceRDTO);
	}
	
	@Test
	public void caseValidateInsertExistsInstallation() {
		materialResourceRDTO.setCodeBrand("");
		materialResourceRDTO.setCodeModel("");
		materialResourceRDTO.setEuroStandard("");
		materialResourceRDTO.setTypeEmissions("");
		materialResourceRDTO.setCodeState("");
		materialResourceRDTO.setCodeMotorEnergyType("");
		materialResourceRDTO.setCodeType("");
		materialResourceRDTO.setCodeTypeTitularity("");
		materialResourceRDTO.setCodeInstallation("CAAA");
		materialResourceRDTO.setRegistration("WEBSERVICE");
		Mockito.when(validator.validateSensorsContract(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		Mockito.when(validator.validateCompatibilityTypeRRMMTypesSensor(Mockito.anyString(), Mockito.anyList())).thenReturn(returnRDTO);
		
		Mockito.when(validator.validateBrandModelGap("", "")).thenReturn(returnRDTO);
		Mockito.when(validator.validateValueList("",new Type(ValueListConstants.EURO_STANDARD))).thenReturn(true);
		Mockito.when(validator.validateValueList("",new Type(ValueListConstants.EMISSION_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap("",new Entity(ValueListConstants.RRMM_STATUS))).thenReturn(true);
		Mockito.when(validator.validateValueListGap("",new Entity(ValueListConstants.MOTOR_ENERGY_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap("",new Entity(ValueListConstants.RRMM_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap("",new Entity(ValueListConstants.TITULARITY_TYPE))).thenReturn(true);
		Mockito.when(validator.validateExistsInstallationGap("CAAA", true)).thenReturn(true);
		validatorMaterialResource.validateInsert(materialResourceRDTO);
	}
	
	@Test
	public void caseValidateInsertValidateExistsMaterialResourceGap() {
		materialResourceRDTO.setCodeBrand("");
		materialResourceRDTO.setCodeModel("");
		materialResourceRDTO.setEuroStandard("");
		materialResourceRDTO.setTypeEmissions("");
		materialResourceRDTO.setCodeState("");
		materialResourceRDTO.setCodeMotorEnergyType("");
		materialResourceRDTO.setCodeType("");
		materialResourceRDTO.setCodeTypeTitularity("");
		materialResourceRDTO.setCodeInstallation("CAAA");
		materialResourceRDTO.setRegistration("WEBSERVICE");
		Mockito.when(validator.validateSensorsContract(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		Mockito.when(validator.validateCompatibilityTypeRRMMTypesSensor(Mockito.anyString(), Mockito.anyList())).thenReturn(returnRDTO);
		
		Mockito.when(validator.validateBrandModelGap("", "")).thenReturn(returnRDTO);
		Mockito.when(validator.validateValueList("",new Type(ValueListConstants.EURO_STANDARD))).thenReturn(true);
		Mockito.when(validator.validateValueList("",new Type(ValueListConstants.EMISSION_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap("",new Entity(ValueListConstants.RRMM_STATUS))).thenReturn(true);
		Mockito.when(validator.validateValueListGap("",new Entity(ValueListConstants.MOTOR_ENERGY_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap("",new Entity(ValueListConstants.RRMM_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap("",new Entity(ValueListConstants.TITULARITY_TYPE))).thenReturn(true);
		Mockito.when(validator.validateExistsInstallationGap("CAAA", true)).thenReturn(true);
		Mockito.when(validator.validateExistsMaterialResourceGap(Mockito.anyString())).thenReturn(true);
		validatorMaterialResource.validateInsert(materialResourceRDTO);
	}

	@Test
	public void caseOkInsertNoVehicle() {
		Mockito.when(validator.validateBrandModelGap("", "")).thenReturn(returnRDTO);
		materialResourceRDTO.setCodeBrand("");
		materialResourceRDTO.setCodeModel("");
		
		validatorMaterialResource.validateInsert(materialResourceRDTO);
	}
	@Test
	public void caseOkUpdateNoVehicle() {
		Mockito.when(validator.validateBrandModelGap("", "")).thenReturn(returnRDTO);
		materialResourceRDTO.setCodeBrand("");
		materialResourceRDTO.setCodeModel("");
		
		validatorMaterialResource.validateUpdate(materialResourceRDTO);
	}

	@Test
	public void caseValidateUpdate() {
		Mockito.when(validator.validateBrandModelGap("", "")).thenReturn(returnRDTO);
		Mockito.when(validator.validateSensorsContract(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		Mockito.when(validator.validateCompatibilityTypeRRMMTypesSensor(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		materialResourceRDTO.setCodeBrand("");
		materialResourceRDTO.setCodeModel("");
		materialResourceRDTO.setRegistration("WEBSERVICE");
		validatorMaterialResource.validateUpdate(materialResourceRDTO);
	}
	@Test
	public void caseValidateUpdateExistsInstallation() {
		materialResourceRDTO.setCodeBrand("");
		materialResourceRDTO.setCodeModel("");
		materialResourceRDTO.setEuroStandard("");
		materialResourceRDTO.setTypeEmissions("");
		materialResourceRDTO.setCodeState("");
		materialResourceRDTO.setCodeMotorEnergyType("");
		materialResourceRDTO.setCodeType("");
		materialResourceRDTO.setCodeTypeTitularity("");
		materialResourceRDTO.setCodeInstallation("CAAA");
		materialResourceRDTO.setRegistration("WEBSERVICE");
		Mockito.when(validator.validateSensorsContract(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		Mockito.when(validator.validateCompatibilityTypeRRMMTypesSensor(Mockito.anyString(), Mockito.anyList())).thenReturn(returnRDTO);
		
		Mockito.when(validator.validateBrandModelGap("", "")).thenReturn(returnRDTO);
		Mockito.when(validator.validateValueList("",new Type(ValueListConstants.EURO_STANDARD))).thenReturn(true);
		Mockito.when(validator.validateValueList("",new Type(ValueListConstants.EMISSION_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap("",new Entity(ValueListConstants.RRMM_STATUS))).thenReturn(true);
		Mockito.when(validator.validateValueListGap("",new Entity(ValueListConstants.MOTOR_ENERGY_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap("",new Entity(ValueListConstants.RRMM_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap("",new Entity(ValueListConstants.TITULARITY_TYPE))).thenReturn(true);
		Mockito.when(validator.validateExistsInstallationGap("CAAA", true)).thenReturn(true);
		validatorMaterialResource.validateUpdate(materialResourceRDTO);
	}

	@Test
	public void caseValidateUpdateValidateNotExistsMaterialResourceGap() {
		materialResourceRDTO.setCodeBrand("");
		materialResourceRDTO.setCodeModel("");
		materialResourceRDTO.setEuroStandard("");
		materialResourceRDTO.setTypeEmissions("");
		materialResourceRDTO.setCodeState("");
		materialResourceRDTO.setCodeMotorEnergyType("");
		materialResourceRDTO.setCodeType("");
		materialResourceRDTO.setCodeTypeTitularity("");
		materialResourceRDTO.setCodeInstallation("CAAA");
		materialResourceRDTO.setRegistration("WEBSERVICE");
		Mockito.when(validator.validateSensorsContract(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		Mockito.when(validator.validateCompatibilityTypeRRMMTypesSensor(Mockito.anyString(), Mockito.anyList())).thenReturn(returnRDTO);
		
		Mockito.when(validator.validateBrandModelGap("", "")).thenReturn(returnRDTO);
		Mockito.when(validator.validateValueList("",new Type(ValueListConstants.EURO_STANDARD))).thenReturn(true);
		Mockito.when(validator.validateValueList("",new Type(ValueListConstants.EMISSION_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap("",new Entity(ValueListConstants.RRMM_STATUS))).thenReturn(true);
		Mockito.when(validator.validateValueListGap("",new Entity(ValueListConstants.MOTOR_ENERGY_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap("",new Entity(ValueListConstants.RRMM_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap("",new Entity(ValueListConstants.TITULARITY_TYPE))).thenReturn(true);
		Mockito.when(validator.validateExistsInstallationGap("CAAA", true)).thenReturn(true);
		Mockito.when(validator.validateExistsMaterialResourceGap(Mockito.anyString())).thenReturn(false);
		validatorMaterialResource.validateUpdate(materialResourceRDTO);
	}

	@Test
	public void caseValidateUpdateValidateExistsMaterialResourceGap() {
		materialResourceRDTO.setCodeBrand("");
		materialResourceRDTO.setCodeModel("");
		materialResourceRDTO.setEuroStandard("");
		materialResourceRDTO.setTypeEmissions("");
		materialResourceRDTO.setCodeState("");
		materialResourceRDTO.setCodeMotorEnergyType("");
		materialResourceRDTO.setCodeType("");
		materialResourceRDTO.setCodeTypeTitularity("");
		materialResourceRDTO.setCodeInstallation("CAAA");
		materialResourceRDTO.setRegistration("WEBSERVICE");
		Mockito.when(validator.validateSensorsContract(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		Mockito.when(validator.validateCompatibilityTypeRRMMTypesSensor(Mockito.anyString(), Mockito.anyList())).thenReturn(returnRDTO);
		
		Mockito.when(validator.validateBrandModelGap("", "")).thenReturn(returnRDTO);
		Mockito.when(validator.validateValueList("",new Type(ValueListConstants.EURO_STANDARD))).thenReturn(true);
		Mockito.when(validator.validateValueList("",new Type(ValueListConstants.EMISSION_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap("",new Entity(ValueListConstants.RRMM_STATUS))).thenReturn(true);
		Mockito.when(validator.validateValueListGap("",new Entity(ValueListConstants.MOTOR_ENERGY_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap("",new Entity(ValueListConstants.RRMM_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap("",new Entity(ValueListConstants.TITULARITY_TYPE))).thenReturn(true);
		Mockito.when(validator.validateExistsInstallationGap("CAAA", true)).thenReturn(true);
		Mockito.when(validator.validateExistsMaterialResourceGap(Mockito.anyString())).thenReturn(true);
		validatorMaterialResource.validateUpdate(materialResourceRDTO);
	}
	@Test
	public void caseOkDelete() {
		Mockito.when(validator.validateBrandModelGap("", "")).thenReturn(returnRDTO);
		Mockito.when(validator.validateSensorsContract(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		Mockito.when(validator.validateCompatibilityTypeRRMMTypesSensor(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		materialResourceRDTO.setCodeBrand("");
		materialResourceRDTO.setCodeModel("");
		materialResourceRDTO.setRegistration("WEBSERVICE");
		validatorMaterialResource.validateDelete(materialResourceRDTO);
	}

	@Test
	public void caseOkDeleteNoVehicle() {
		Mockito.when(validator.validateBrandModelGap("", "")).thenReturn(returnRDTO);
		materialResourceRDTO.setCodeBrand("");
		materialResourceRDTO.setCodeModel("");
		
		validatorMaterialResource.validateDelete(materialResourceRDTO);
	}
	
	@Test
	public void caseValidateInsertExpense() {
		validatorMaterialResource.validateInsertExpense(materialResourceRDTO);
	}
	
	@Test
	public void caseValidateInsertExpenseMaterialResourceExistent() {
		Mockito.when(validator.validateExistsMaterialResourceGap(Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateTypeSubtypeExpenseGap(Mockito.anyString(),Mockito.anyString(),Mockito.anyString())).thenReturn(returnRDTO);
		
		validatorMaterialResource.validateInsertExpense(materialResourceRDTO);
	}

	
	@Test
	public void caseInsertExpenseTypeNotExistent() {
		Mockito.when(validator.validateExistsMaterialResourceGap(Mockito.anyString())).thenReturn(true);
		
		Mockito.when(validator.validateTypeSubtypeExpenseGap(Mockito.anyString(),Mockito.anyString(),Mockito.anyString())).thenReturn(returnRDTOKO);
		
		validatorMaterialResource.validateInsertExpense(materialResourceRDTO);
	}
	
	
	@Test
	public void caseValidateInsertExpenseNotExistsTypeExpense() {
		Mockito.when(validator.validateExistsMaterialResourceGap(Mockito.anyString())).thenReturn(true);
		
		Mockito.when(validator.validateTypeSubtypeExpenseGap(Mockito.anyString(),Mockito.anyString(),Mockito.anyString())).thenReturn(returnRDTOKO);
		
		validatorMaterialResource.validateInsertExpense(materialResourceRDTO);
	}
	
	
	@Test
	public void caseOkInsertAmortizationBase() {
		validatorMaterialResource.validateInsertAmortizationBase(materialResourceRDTO);
	}
	
	@Test
	public void caseInsertAmortizationBaseInstallationExistent() {
		Mockito.when(validator.validateExistsMaterialResourceGap(Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateTypeSubtypeExpenseGap(Mockito.anyString(),Mockito.anyString(),Mockito.anyString())).thenReturn(returnRDTO);
		
		validatorMaterialResource.validateInsertAmortizationBase(materialResourceRDTO);
	}
	
	@Test
	public void caseValidateInsertApportionment() {
		Mockito.when(validator.validateExistsAmortizableMaterialResourceGap(Mockito.anyString())).thenReturn(returnRDTO);
		Mockito.when(validator.validateListValueListGap(Mockito.anyList(),Mockito.any(Entity.class),Mockito.anyString(),Mockito.anyString())).thenReturn(returnRDTO);		
		validatorMaterialResource.validateInsertApportionment(materialResourceRDTO);
	}
	
	
	@Test
	public void caseValidateInsertApportionmentNotExistsGroupService() {
		Mockito.when(validator.validateExistsAmortizableMaterialResourceGap(Mockito.anyString())).thenReturn(returnRDTO);
		Mockito.when(validator.validateListValueListGap(Mockito.anyList(),Mockito.any(Entity.class),Mockito.anyString(),Mockito.anyString())).thenReturn(returnRDTOKO);		
		validatorMaterialResource.validateInsertApportionment(materialResourceRDTO);
	}
	
	@Test
	public void caseValidateInsertApportionmentNotExistsMaterialResource() {
		Mockito.when(validator.validateExistsAmortizableMaterialResourceGap(Mockito.anyString())).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateListValueListGap(Mockito.anyList(),Mockito.any(Entity.class),Mockito.anyString(),Mockito.anyString())).thenReturn(returnRDTOKO);		
		validatorMaterialResource.validateInsertApportionment(materialResourceRDTO);
	}
	
	@Test
	public void caseOkValidateInsertDocumentarySupport() {
		
		Mockito.when(validator.validateExistsMaterialResourceGap(documentarySupportRDTO.getCode())).thenReturn(true);
		Mockito.when(validatorDocumentarySupport.validateInsert(Mockito.any(documentarySupportRDTO.getClass()))).thenReturn(returnRDTO);		
			
		validatorMaterialResource.validateInsertDocumentarySupport(documentarySupportRDTO);
	}
	@Test
	public void caseOkValidateInsertDocumentarySupportNotExistsRRMM() {
		
		Mockito.when(validator.validateExistsMaterialResourceGap(documentarySupportRDTO.getCode())).thenReturn(false);
		Mockito.when(validatorDocumentarySupport.validateInsert(Mockito.any(documentarySupportRDTO.getClass()))).thenReturn(returnRDTO);
			
		validatorMaterialResource.validateInsertDocumentarySupport(documentarySupportRDTO);
	}
	
	@Test
	public void caseValidateInsertActuation() {
		
		
		Mockito.when(validatorActuation.validateInsert(actuationRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateExistsMaterialResourceGap(actuationRDTO.getCodeEntity())).thenReturn(false);
		Mockito.when(validator.validateValueList(actuationRDTO.getTypeActuation(),new Type(ValueListConstants.TYPE_ACTUATION_UBICATION))).thenReturn(true);
		
		
		validatorMaterialResource.validateInsertActuation(actuationRDTO);
	}

	@Test
	public void caseValidateDeleteActuation() {
		
		
		Mockito.when(validatorActuation.validateDelete(actuationRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateExistsMaterialResourceGap(actuationRDTO.getCodeEntity())).thenReturn(true);
		
		
		
		validatorMaterialResource.validateDeleteActuation(actuationRDTO);
	}
}
