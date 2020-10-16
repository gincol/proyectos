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

import es.bcn.imi.framework.vigia.frontal.validator.ValidatorNoVehicle;
import es.bcn.imi.framework.vigia.frontal.validator.ValidatorUtils;
import es.bcn.imi.framework.vigia.frontal.web.rest.config.RestServerConfig;
import es.bcn.vigia.fmw.libcommons.constants.ValueListConstants;
import es.bcn.vigia.fmw.libcommons.valueobject.Entity;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.MaterialResourceRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.MaterialResourceRDTOStub;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RestServerConfig.class })
@TestPropertySource(locations = {"classpath:app/config/application.properties"})
@WebAppConfiguration
@SuppressWarnings("unchecked")
public class ValidatorNoVehicleTest {

	@Mock
	private ValidatorUtils validator;

	@InjectMocks
	private ValidatorNoVehicle validatorNoVehicle;
	
	private MaterialResourceRDTO materialResourceRDTO;
	
	private ReturnRDTO returnRDTOKO;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		materialResourceRDTO = MaterialResourceRDTOStub.defaultOne();
		returnRDTOKO = new ReturnRDTO();
		returnRDTOKO.setCode("KO");

	}
	

	@Test
	public void caseOkValidateInsert() {
		
		materialResourceRDTO.setCodeState("STATE");
		materialResourceRDTO.setCodeType("TYPE");
		materialResourceRDTO.setCodeTypeTitularity("TITULARITY");
		materialResourceRDTO.setCodeInstallation("CAAA");
		Mockito.when(validator.validateSensorsContract(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		Mockito.when(validator.validateCompatibilityTypeRRMMTypesSensor(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		
		
		Mockito.when(validator.validateValueListGap(materialResourceRDTO.getCodeState(), new Entity(ValueListConstants.RRMM_STATUS))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(materialResourceRDTO.getCodeType(), new Entity(ValueListConstants.RRMM_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(materialResourceRDTO.getCodeTypeTitularity(), new Entity(ValueListConstants.TITULARITY_TYPE))).thenReturn(true);
		Mockito.when(validator.validateExistsInstallationGap(materialResourceRDTO.getCodeInstallation(), true)).thenReturn(true);
		
		Mockito.when(validator.validateExistsMaterialResourceNoVehicle(materialResourceRDTO.getCodeContract(),materialResourceRDTO.getCode())).thenReturn(false);
		
		validatorNoVehicle.validateInsert(materialResourceRDTO);
	}
	
	@Test
	public void caseValidateInsertExistsRRMMNoVehicle() {
		
		materialResourceRDTO.setCodeState("STATE");
		materialResourceRDTO.setCodeType("TYPE");
		materialResourceRDTO.setCodeTypeTitularity("TITULARITY");
		materialResourceRDTO.setCodeInstallation("CAAA");
		materialResourceRDTO.setCode("codeRRMM");
		
		Mockito.when(validator.validateSensorsContract(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		Mockito.when(validator.validateCompatibilityTypeRRMMTypesSensor(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		
		
		Mockito.when(validator.validateValueListGap(materialResourceRDTO.getCodeState(), new Entity(ValueListConstants.RRMM_STATUS))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(materialResourceRDTO.getCodeType(), new Entity(ValueListConstants.RRMM_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(materialResourceRDTO.getCodeTypeTitularity(), new Entity(ValueListConstants.TITULARITY_TYPE))).thenReturn(true);
		Mockito.when(validator.validateExistsInstallationGap(materialResourceRDTO.getCodeInstallation(), true)).thenReturn(true);
		
		Mockito.when(validator.validateExistsMaterialResourceNoVehicle(materialResourceRDTO.getCodeContract(), materialResourceRDTO.getCode())).thenReturn(true);
		
		
		validatorNoVehicle.validateInsert(materialResourceRDTO);
	}
	
	@Test
	public void caseValidateInsertExistsRRMM() {
		
		materialResourceRDTO.setCodeState("STATE");
		materialResourceRDTO.setCodeType("TYPE");
		materialResourceRDTO.setCodeTypeTitularity("TITULARITY");
		materialResourceRDTO.setCodeInstallation("CAAA");
		materialResourceRDTO.setCode("codeRRMM");
		
		Mockito.when(validator.validateSensorsContract(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		Mockito.when(validator.validateCompatibilityTypeRRMMTypesSensor(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		
		
		Mockito.when(validator.validateValueListGap(materialResourceRDTO.getCodeState(), new Entity(ValueListConstants.RRMM_STATUS))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(materialResourceRDTO.getCodeType(), new Entity(ValueListConstants.RRMM_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(materialResourceRDTO.getCodeTypeTitularity(), new Entity(ValueListConstants.TITULARITY_TYPE))).thenReturn(true);
		Mockito.when(validator.validateExistsInstallationGap(materialResourceRDTO.getCodeInstallation(), true)).thenReturn(true);
		
		Mockito.when(validator.validateExistsMaterialResourceGap(materialResourceRDTO.getCode())).thenReturn(true);
		
		
		validatorNoVehicle.validateInsert(materialResourceRDTO);
	}

	@Test
	public void caseValidateInsertNotExistsInstallation() {
		
		materialResourceRDTO.setCodeState("STATE");
		materialResourceRDTO.setCodeType("TYPE");
		materialResourceRDTO.setCodeTypeTitularity("TITULARITY");
		materialResourceRDTO.setCodeInstallation("CAAA");
		materialResourceRDTO.setCode("codeRRMM");
		
		Mockito.when(validator.validateSensorsContract(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		Mockito.when(validator.validateCompatibilityTypeRRMMTypesSensor(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		
		
		Mockito.when(validator.validateValueListGap(materialResourceRDTO.getCodeState(), new Entity(ValueListConstants.RRMM_STATUS))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(materialResourceRDTO.getCodeType(), new Entity(ValueListConstants.RRMM_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(materialResourceRDTO.getCodeTypeTitularity(), new Entity(ValueListConstants.TITULARITY_TYPE))).thenReturn(true);
		Mockito.when(validator.validateExistsInstallationGap(materialResourceRDTO.getCodeInstallation(), true)).thenReturn(false);
		
		Mockito.when(validator.validateExistsMaterialResourceGap(materialResourceRDTO.getCode())).thenReturn(true);
		
		
		validatorNoVehicle.validateInsert(materialResourceRDTO);
	}
	
	@Test
	public void caseValidateInsertNotExistsTitularity() {
		
		materialResourceRDTO.setCodeState("STATE");
		materialResourceRDTO.setCodeType("TYPE");
		materialResourceRDTO.setCodeTypeTitularity("TITULARITY");
		materialResourceRDTO.setCodeInstallation("CAAA");
		materialResourceRDTO.setCode("codeRRMM");
		
		Mockito.when(validator.validateSensorsContract(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		Mockito.when(validator.validateCompatibilityTypeRRMMTypesSensor(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		
		
		Mockito.when(validator.validateValueListGap(materialResourceRDTO.getCodeState(), new Entity(ValueListConstants.RRMM_STATUS))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(materialResourceRDTO.getCodeType(), new Entity(ValueListConstants.RRMM_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(materialResourceRDTO.getCodeTypeTitularity(), new Entity(ValueListConstants.TITULARITY_TYPE))).thenReturn(false);
		
		
		validatorNoVehicle.validateInsert(materialResourceRDTO);
	}
	
	@Test
	public void caseValidateInsertNotExistsType() {
		
		materialResourceRDTO.setCodeState("STATE");
		materialResourceRDTO.setCodeType("TYPE");
		materialResourceRDTO.setCodeTypeTitularity("TITULARITY");
		materialResourceRDTO.setCodeInstallation("CAAA");
		materialResourceRDTO.setCode("codeRRMM");
		
		Mockito.when(validator.validateSensorsContract(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		Mockito.when(validator.validateCompatibilityTypeRRMMTypesSensor(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		
		
		Mockito.when(validator.validateValueListGap(materialResourceRDTO.getCodeState(), new Entity(ValueListConstants.RRMM_STATUS))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(materialResourceRDTO.getCodeType(), new Entity(ValueListConstants.RRMM_TYPE))).thenReturn(false);
		
		
		
		validatorNoVehicle.validateInsert(materialResourceRDTO);
	}
	
	@Test
	public void caseValidateInsertNotExistsState() {
		
		materialResourceRDTO.setCodeState("STATE");
		materialResourceRDTO.setCodeType("TYPE");
		materialResourceRDTO.setCodeTypeTitularity("TITULARITY");
		materialResourceRDTO.setCodeInstallation("CAAA");
		materialResourceRDTO.setCode("codeRRMM");
		
		Mockito.when(validator.validateSensorsContract(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		Mockito.when(validator.validateCompatibilityTypeRRMMTypesSensor(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		Mockito.when(validator.validateValueListGap(materialResourceRDTO.getCodeType(), new Entity(ValueListConstants.RRMM_TYPE))).thenReturn(true);
		
		Mockito.when(validator.validateValueListGap(materialResourceRDTO.getCodeState(), new Entity(ValueListConstants.RRMM_STATUS))).thenReturn(false);
		
		
		validatorNoVehicle.validateInsert(materialResourceRDTO);
	}
	
	@Test
	public void caseValidateInsertNotCompatibleSensors() {
		
		materialResourceRDTO.setCodeState("STATE");
		materialResourceRDTO.setCodeType("TYPE");
		materialResourceRDTO.setCodeTypeTitularity("TITULARITY");
		materialResourceRDTO.setCodeInstallation("CAAA");
		materialResourceRDTO.setCode("codeRRMM");
		
		Mockito.when(validator.validateValueListGap(materialResourceRDTO.getCodeType(), new Entity(ValueListConstants.RRMM_TYPE))).thenReturn(true);
		Mockito.when(validator.validateSensorsContract(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		Mockito.when(validator.validateCompatibilityTypeRRMMTypesSensor(Mockito.anyString(), Mockito.anyList())).thenReturn(returnRDTOKO);
		
		
		
		validatorNoVehicle.validateInsert(materialResourceRDTO);
	}
	
	@Test
	public void caseValidateInsertNotExistSensors() {
		
		materialResourceRDTO.setCodeState("STATE");
		materialResourceRDTO.setCodeType("TYPE");
		materialResourceRDTO.setCodeTypeTitularity("TITULARITY");
		materialResourceRDTO.setCodeInstallation("CAAA");
		materialResourceRDTO.setCode("codeRRMM");
		
		Mockito.when(validator.validateSensorsContract(Mockito.anyString(), Mockito.anyList())).thenReturn(returnRDTOKO);
		
		
		
		validatorNoVehicle.validateInsert(materialResourceRDTO);
	}
	@Test
	public void caseOkValidateUpdate() {
		
		materialResourceRDTO.setCodeState("STATE");
		materialResourceRDTO.setCodeType("TYPE");
		materialResourceRDTO.setCodeTypeTitularity("TITULARITY");
		materialResourceRDTO.setCodeInstallation("CAAA");
		Mockito.when(validator.validateSensorsContract(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		Mockito.when(validator.validateCompatibilityTypeRRMMTypesSensor(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		
		
		Mockito.when(validator.validateValueListGap(materialResourceRDTO.getCodeState(), new Entity(ValueListConstants.RRMM_STATUS))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(materialResourceRDTO.getCodeType(), new Entity(ValueListConstants.RRMM_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(materialResourceRDTO.getCodeTypeTitularity(), new Entity(ValueListConstants.TITULARITY_TYPE))).thenReturn(true);
		Mockito.when(validator.validateExistsInstallationGap(materialResourceRDTO.getCodeInstallation(), true)).thenReturn(true);
		
		Mockito.when(validator.validateExistsMaterialResourceNoVehicle(materialResourceRDTO.getCodeContract(), materialResourceRDTO.getCode())).thenReturn(true);
		
		validatorNoVehicle.validateUpdate(materialResourceRDTO);
	}

	@Test
	public void caseValidateUpdateNotExistsRRMM() {
		
		materialResourceRDTO.setCodeState("STATE");
		materialResourceRDTO.setCodeType("TYPE");
		materialResourceRDTO.setCodeTypeTitularity("TITULARITY");
		materialResourceRDTO.setCodeInstallation("CAAA");
		Mockito.when(validator.validateSensorsContract(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		Mockito.when(validator.validateCompatibilityTypeRRMMTypesSensor(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		
		
		Mockito.when(validator.validateValueListGap(materialResourceRDTO.getCodeState(), new Entity(ValueListConstants.RRMM_STATUS))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(materialResourceRDTO.getCodeType(), new Entity(ValueListConstants.RRMM_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(materialResourceRDTO.getCodeTypeTitularity(), new Entity(ValueListConstants.TITULARITY_TYPE))).thenReturn(true);
		Mockito.when(validator.validateExistsInstallationGap(materialResourceRDTO.getCodeInstallation(), true)).thenReturn(true);
		
		Mockito.when(validator.validateExistsMaterialResourceNoVehicle(materialResourceRDTO.getCodeContract(),materialResourceRDTO.getCode())).thenReturn(false);
		
		validatorNoVehicle.validateUpdate(materialResourceRDTO);
	}
	@Test
	public void caseOkValidateUpdateNotExistsState() {
		
		materialResourceRDTO.setCodeState("STATE");
		materialResourceRDTO.setCodeType("TYPE");
		materialResourceRDTO.setCodeTypeTitularity("TITULARITY");
		materialResourceRDTO.setCodeInstallation("CAAA");
		Mockito.when(validator.validateSensorsContract(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		Mockito.when(validator.validateCompatibilityTypeRRMMTypesSensor(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		
		
		Mockito.when(validator.validateValueListGap(materialResourceRDTO.getCodeState(), new Entity(ValueListConstants.RRMM_STATUS))).thenReturn(false);
		
		
		validatorNoVehicle.validateUpdate(materialResourceRDTO);
	}
	
	@Test
	public void caseOkValidateDelete() {
		
		materialResourceRDTO.setCodeState("STATE");
		materialResourceRDTO.setCodeType("TYPE");
		materialResourceRDTO.setCodeTypeTitularity("TITULARITY");
		materialResourceRDTO.setCodeInstallation("CAAA");
		Mockito.when(validator.validateSensorsContract(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		Mockito.when(validator.validateCompatibilityTypeRRMMTypesSensor(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		
		
		Mockito.when(validator.validateValueListGap(materialResourceRDTO.getCodeState(), new Entity(ValueListConstants.RRMM_STATUS))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(materialResourceRDTO.getCodeType(), new Entity(ValueListConstants.RRMM_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(materialResourceRDTO.getCodeTypeTitularity(), new Entity(ValueListConstants.TITULARITY_TYPE))).thenReturn(true);
		Mockito.when(validator.validateExistsInstallationGap(materialResourceRDTO.getCodeInstallation(), true)).thenReturn(true);
		
		Mockito.when(validator.validateExistsMaterialResourceNoVehicle(materialResourceRDTO.getCodeContract(),materialResourceRDTO.getCode())).thenReturn(true);
		
		validatorNoVehicle.validateDelete(materialResourceRDTO);
	}
	
	@Test
	public void caseValidateDeleteNotExistsRRMM() {
		
		materialResourceRDTO.setCodeState("STATE");
		materialResourceRDTO.setCodeType("TYPE");
		materialResourceRDTO.setCodeTypeTitularity("TITULARITY");
		materialResourceRDTO.setCodeInstallation("CAAA");
		Mockito.when(validator.validateSensorsContract(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		Mockito.when(validator.validateCompatibilityTypeRRMMTypesSensor(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		
		
		Mockito.when(validator.validateValueListGap(materialResourceRDTO.getCodeState(), new Entity(ValueListConstants.RRMM_STATUS))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(materialResourceRDTO.getCodeType(), new Entity(ValueListConstants.RRMM_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(materialResourceRDTO.getCodeTypeTitularity(), new Entity(ValueListConstants.TITULARITY_TYPE))).thenReturn(true);
		Mockito.when(validator.validateExistsInstallationGap(materialResourceRDTO.getCodeInstallation(), true)).thenReturn(true);
		
		Mockito.when(validator.validateExistsMaterialResourceNoVehicle(materialResourceRDTO.getCodeContract(),materialResourceRDTO.getCode())).thenReturn(false);
		
		validatorNoVehicle.validateDelete(materialResourceRDTO);
	}
	@Test
	public void caseOkValidateDeleteNotExistsState() {
		
		materialResourceRDTO.setCodeState("STATE");
		materialResourceRDTO.setCodeType("TYPE");
		materialResourceRDTO.setCodeTypeTitularity("TITULARITY");
		materialResourceRDTO.setCodeInstallation("CAAA");
		Mockito.when(validator.validateSensorsContract(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		Mockito.when(validator.validateCompatibilityTypeRRMMTypesSensor(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		
		
		Mockito.when(validator.validateValueListGap(materialResourceRDTO.getCodeState(), new Entity(ValueListConstants.RRMM_STATUS))).thenReturn(false);
		
		
		validatorNoVehicle.validateDelete(materialResourceRDTO);
	}
}
