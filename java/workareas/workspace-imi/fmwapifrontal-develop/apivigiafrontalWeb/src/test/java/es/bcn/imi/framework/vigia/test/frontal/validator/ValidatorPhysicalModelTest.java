package es.bcn.imi.framework.vigia.test.frontal.validator;

import java.util.ArrayList;
import java.util.List;

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

import es.bcn.imi.framework.vigia.frontal.validator.ValidatorDocumentarySupport;
import es.bcn.imi.framework.vigia.frontal.validator.ValidatorPhysicalModel;
import es.bcn.imi.framework.vigia.frontal.validator.ValidatorUtils;
import es.bcn.imi.framework.vigia.frontal.web.rest.config.RestServerConfig;
import es.bcn.vigia.fmw.libcommons.constants.ValueListConstants;
import es.bcn.vigia.fmw.libcommons.valueobject.Entity;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.AggregateAmortizationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.DocumentarySupportRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.PhysicalModelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.AggregateAmortizationRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.DocumentarySupportRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.SensorRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.PhysicalModelRDTOStub;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RestServerConfig.class })
@TestPropertySource(locations = {"classpath:app/config/application.properties"})
@WebAppConfiguration
@SuppressWarnings("unchecked")
public class ValidatorPhysicalModelTest {
	
	@Mock
	private ValidatorUtils validator;

	@Mock
	private ValidatorDocumentarySupport validatorDocumentarySupport;

	@InjectMocks
	private ValidatorPhysicalModel validatorPhysicalModel;
	
	private PhysicalModelRDTO physicalModelRDTO;

	private AggregateAmortizationRDTO aggregateAmortizationRDTO;

	private ReturnRDTO returnRDTO_KO;

	private ReturnRDTO returnRDTO;
	
	

	private DocumentarySupportRDTO documentarySupportRDTO;
	 
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		physicalModelRDTO = PhysicalModelRDTOStub.defaultOne();
		aggregateAmortizationRDTO = AggregateAmortizationRDTOStub.defaultOne();
		returnRDTO_KO = new ReturnRDTO();
		returnRDTO_KO.setCode("KO");
		returnRDTO = ReturnRDTOStub.getSuccessMessage();
		documentarySupportRDTO = DocumentarySupportRDTOStub.defaultOne();
		
	}

	@Test
	public void caseOkValidateInsert() {
		
		physicalModelRDTO.setCodeContract("C");
		
		List<SensorRDTO> sensorsRDTO = new ArrayList<>();
		SensorRDTO sensorRDTO = new SensorRDTO();
		sensorRDTO.setCode("code");
		sensorRDTO.setCodeMCF(physicalModelRDTO.getCode());
		sensorsRDTO.add(sensorRDTO);
		physicalModelRDTO.setSensorsRDTO(sensorsRDTO);
		
		physicalModelRDTO.setCodeUbication("codeUbication");
		physicalModelRDTO.setCodeState("codeStateMCF");
		physicalModelRDTO.setCodeFractionType("codeFractionType");
		physicalModelRDTO.setCodeBrand("BRAND");
		physicalModelRDTO.setCodeInstallation("CAAA");
		physicalModelRDTO.setCodeMCL("codeMCL");
		
		Mockito.when(validator.validateSensorsContract(physicalModelRDTO.getCodeContract(),physicalModelRDTO.getSensorsRDTO())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		Mockito.when(validator.validateCompatibilityTypeMCFTypesSensor(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeState(), new Entity(ValueListConstants.MCF_STATUS))).thenReturn(true);
		
		
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeType(),new Entity(ValueListConstants.FURNITURE_TYPE ))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeFractionType(),new Entity(ValueListConstants.FRACTION_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeBrand(),new Entity(ValueListConstants.CODE_BRAND))).thenReturn(true);
		
		Mockito.when(validator.validateExistsUbicationGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		Mockito.when(validator.validateExistsInstallationGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		Mockito.when(validator.validateExistsVacantLogicalModelGap(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsPhysicalModelGap(Mockito.anyString())).thenReturn(false);
		
		validatorPhysicalModel.validateInsert(physicalModelRDTO);
	}

	@Test
	public void caseValidateInsertExistsMCF() {
		
		physicalModelRDTO.setCodeContract("C");
		
		List<SensorRDTO> sensorsRDTO = new ArrayList<>();
		SensorRDTO sensorRDTO = new SensorRDTO();
		sensorRDTO.setCode("code");
		sensorRDTO.setCodeMCF(physicalModelRDTO.getCode());
		sensorsRDTO.add(sensorRDTO);
		physicalModelRDTO.setSensorsRDTO(sensorsRDTO);
		
		physicalModelRDTO.setCodeUbication("codeUbication");
		physicalModelRDTO.setCodeState("codeStateMCF");
		physicalModelRDTO.setCodeFractionType("codeFractionType");
		physicalModelRDTO.setCodeBrand("BRAND");
		physicalModelRDTO.setCodeInstallation("CAAA");
		physicalModelRDTO.setCodeMCL("codeMCL");
		
		Mockito.when(validator.validateSensorsContract(physicalModelRDTO.getCodeContract(),physicalModelRDTO.getSensorsRDTO())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		Mockito.when(validator.validateCompatibilityTypeMCFTypesSensor(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeState(), new Entity(ValueListConstants.MCF_STATUS))).thenReturn(true);
		
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeType(),new Entity(ValueListConstants.FURNITURE_TYPE ))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeFractionType(),new Entity(ValueListConstants.FRACTION_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeBrand(),new Entity(ValueListConstants.CODE_BRAND))).thenReturn(true);
		
		Mockito.when(validator.validateExistsUbicationGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		Mockito.when(validator.validateExistsInstallationGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		Mockito.when(validator.validateExistsVacantLogicalModelGap(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsPhysicalModelGap(Mockito.anyString())).thenReturn(true);
		
		validatorPhysicalModel.validateInsert(physicalModelRDTO);
	}
	
	@Test
	public void caseValidateInsertNotVacantMCL() {
		
		physicalModelRDTO.setCodeContract("C");
		
		List<SensorRDTO> sensorsRDTO = new ArrayList<>();
		SensorRDTO sensorRDTO = new SensorRDTO();
		sensorRDTO.setCode("code");
		sensorRDTO.setCodeMCF(physicalModelRDTO.getCode());
		sensorsRDTO.add(sensorRDTO);
		physicalModelRDTO.setSensorsRDTO(sensorsRDTO);
		
		physicalModelRDTO.setCodeUbication("codeUbication");
		physicalModelRDTO.setCodeState("codeStateMCF");
		physicalModelRDTO.setCodeFractionType("codeFractionType");
		physicalModelRDTO.setCodeBrand("BRAND");
		physicalModelRDTO.setCodeInstallation("CAAA");
		physicalModelRDTO.setCodeMCL("codeMCL");
		
		Mockito.when(validator.validateSensorsContract(physicalModelRDTO.getCodeContract(),physicalModelRDTO.getSensorsRDTO())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		Mockito.when(validator.validateCompatibilityTypeMCFTypesSensor(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeState(), new Entity(ValueListConstants.MCF_STATUS))).thenReturn(true);
		
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeType(),new Entity(ValueListConstants.FURNITURE_TYPE ))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeFractionType(),new Entity(ValueListConstants.FRACTION_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeBrand(),new Entity(ValueListConstants.CODE_BRAND))).thenReturn(true);
		
		Mockito.when(validator.validateExistsUbicationGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		Mockito.when(validator.validateExistsInstallationGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		Mockito.when(validator.validateExistsVacantLogicalModelGap(Mockito.anyString(),Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateExistsPhysicalModelGap(Mockito.anyString())).thenReturn(true);
		
		validatorPhysicalModel.validateInsert(physicalModelRDTO);
	}
	
	@Test
	public void caseValidateInsertMCLnull() {
		
		physicalModelRDTO.setCodeContract("C");
		
		List<SensorRDTO> sensorsRDTO = new ArrayList<>();
		SensorRDTO sensorRDTO = new SensorRDTO();
		sensorRDTO.setCode("code");
		sensorRDTO.setCodeMCF(physicalModelRDTO.getCode());
		sensorsRDTO.add(sensorRDTO);
		physicalModelRDTO.setSensorsRDTO(sensorsRDTO);
		
		physicalModelRDTO.setCodeUbication("codeUbication");
		physicalModelRDTO.setCodeState("codeStateMCF");
		physicalModelRDTO.setCodeFractionType("codeFractionType");
		physicalModelRDTO.setCodeBrand("BRAND");
		physicalModelRDTO.setCodeInstallation("CAAA");
		physicalModelRDTO.setCodeMCL(null);
		
		Mockito.when(validator.validateSensorsContract(physicalModelRDTO.getCodeContract(),physicalModelRDTO.getSensorsRDTO())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		Mockito.when(validator.validateCompatibilityTypeMCFTypesSensor(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeState(), new Entity(ValueListConstants.MCF_STATUS))).thenReturn(true);
		
		
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeType(),new Entity(ValueListConstants.FURNITURE_TYPE ))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeFractionType(),new Entity(ValueListConstants.FRACTION_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeBrand(),new Entity(ValueListConstants.CODE_BRAND))).thenReturn(true);
		
		Mockito.when(validator.validateExistsUbicationGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		Mockito.when(validator.validateExistsInstallationGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		Mockito.when(validator.validateExistsVacantLogicalModelGap(Mockito.anyString(),Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateExistsPhysicalModelGap(Mockito.anyString())).thenReturn(true);
		
		validatorPhysicalModel.validateInsert(physicalModelRDTO);
	}
	
	@Test
	public void caseValidateInsertInstallationnull() {
		
		physicalModelRDTO.setCodeContract("C");
		
		List<SensorRDTO> sensorsRDTO = new ArrayList<>();
		SensorRDTO sensorRDTO = new SensorRDTO();
		sensorRDTO.setCode("code");
		sensorRDTO.setCodeMCF(physicalModelRDTO.getCode());
		sensorsRDTO.add(sensorRDTO);
		physicalModelRDTO.setSensorsRDTO(sensorsRDTO);
		
		physicalModelRDTO.setCodeUbication("codeUbication");
		physicalModelRDTO.setCodeState("codeStateMCF");
		physicalModelRDTO.setCodeFractionType("codeFractionType");
		physicalModelRDTO.setCodeBrand("BRAND");
		physicalModelRDTO.setCodeInstallation(null);
		
		
		Mockito.when(validator.validateSensorsContract(physicalModelRDTO.getCodeContract(),physicalModelRDTO.getSensorsRDTO())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		Mockito.when(validator.validateCompatibilityTypeMCFTypesSensor(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeState(), new Entity(ValueListConstants.MCF_STATUS))).thenReturn(true);
		
		
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeType(),new Entity(ValueListConstants.FURNITURE_TYPE ))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeFractionType(),new Entity(ValueListConstants.FRACTION_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeBrand(),new Entity(ValueListConstants.CODE_BRAND))).thenReturn(true);
		
		Mockito.when(validator.validateExistsUbicationGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		Mockito.when(validator.validateExistsInstallationGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		
		
		
		validatorPhysicalModel.validateInsert(physicalModelRDTO);
	}
	
	@Test
	public void caseValidateInsertNotExistsInstallation() {
		
		physicalModelRDTO.setCodeContract("C");
		
		List<SensorRDTO> sensorsRDTO = new ArrayList<>();
		SensorRDTO sensorRDTO = new SensorRDTO();
		sensorRDTO.setCode("code");
		sensorRDTO.setCodeMCF(physicalModelRDTO.getCode());
		sensorsRDTO.add(sensorRDTO);
		physicalModelRDTO.setSensorsRDTO(sensorsRDTO);
		
		physicalModelRDTO.setCodeUbication("codeUbication");
		physicalModelRDTO.setCodeState("codeStateMCF");
		physicalModelRDTO.setCodeFractionType("codeFractionType");
		physicalModelRDTO.setCodeBrand("BRAND");
		physicalModelRDTO.setCodeInstallation("CAAA");
		physicalModelRDTO.setCodeMCL("codeMCL");
		
		Mockito.when(validator.validateSensorsContract(physicalModelRDTO.getCodeContract(),physicalModelRDTO.getSensorsRDTO())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		Mockito.when(validator.validateCompatibilityTypeMCFTypesSensor(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeState(), new Entity(ValueListConstants.MCF_STATUS))).thenReturn(true);
		
		
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeType(),new Entity(ValueListConstants.FURNITURE_TYPE ))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeFractionType(),new Entity(ValueListConstants.FRACTION_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeBrand(),new Entity(ValueListConstants.CODE_BRAND))).thenReturn(true);
		
		Mockito.when(validator.validateExistsUbicationGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		Mockito.when(validator.validateExistsInstallationGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(false);
		Mockito.when(validator.validateExistsVacantLogicalModelGap(Mockito.anyString(),Mockito.anyString())).thenReturn(false);
		
		
		validatorPhysicalModel.validateInsert(physicalModelRDTO);
	}	
	
	@Test
	public void caseValidateInsertNotExistsUbication() {
		
		physicalModelRDTO.setCodeContract("C");
		
		List<SensorRDTO> sensorsRDTO = new ArrayList<>();
		SensorRDTO sensorRDTO = new SensorRDTO();
		sensorRDTO.setCode("code");
		sensorRDTO.setCodeMCF(physicalModelRDTO.getCode());
		sensorsRDTO.add(sensorRDTO);
		physicalModelRDTO.setSensorsRDTO(sensorsRDTO);
		
		physicalModelRDTO.setCodeUbication("codeUbication");
		physicalModelRDTO.setCodeState("codeStateMCF");
		physicalModelRDTO.setCodeFractionType("codeFractionType");
		physicalModelRDTO.setCodeBrand("BRAND");
		physicalModelRDTO.setCodeInstallation(null);
		
		
		Mockito.when(validator.validateSensorsContract(physicalModelRDTO.getCodeContract(),physicalModelRDTO.getSensorsRDTO())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		Mockito.when(validator.validateCompatibilityTypeMCFTypesSensor(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeState(), new Entity(ValueListConstants.MCF_STATUS))).thenReturn(true);
		
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeType(),new Entity(ValueListConstants.FURNITURE_TYPE ))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeFractionType(),new Entity(ValueListConstants.FRACTION_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeBrand(),new Entity(ValueListConstants.CODE_BRAND))).thenReturn(true);
		
		Mockito.when(validator.validateExistsUbicationGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(false);
		Mockito.when(validator.validateExistsInstallationGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		
		
		
		validatorPhysicalModel.validateInsert(physicalModelRDTO);
	}
	
	@Test
	public void caseValidateInsertNotExistsBrand() {
		
		physicalModelRDTO.setCodeContract("C");
		
		List<SensorRDTO> sensorsRDTO = new ArrayList<>();
		SensorRDTO sensorRDTO = new SensorRDTO();
		sensorRDTO.setCode("code");
		sensorRDTO.setCodeMCF(physicalModelRDTO.getCode());
		sensorsRDTO.add(sensorRDTO);
		physicalModelRDTO.setSensorsRDTO(sensorsRDTO);
		
		physicalModelRDTO.setCodeUbication("codeUbication");
		physicalModelRDTO.setCodeState("codeStateMCF");
		physicalModelRDTO.setCodeFractionType("codeFractionType");
		physicalModelRDTO.setCodeBrand("BRAND");
		physicalModelRDTO.setCodeInstallation(null);
		
		
		Mockito.when(validator.validateSensorsContract(physicalModelRDTO.getCodeContract(),physicalModelRDTO.getSensorsRDTO())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		Mockito.when(validator.validateCompatibilityTypeMCFTypesSensor(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeState(), new Entity(ValueListConstants.MCF_STATUS))).thenReturn(true);
		
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeType(),new Entity(ValueListConstants.FURNITURE_TYPE ))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeFractionType(),new Entity(ValueListConstants.FRACTION_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeBrand(),new Entity(ValueListConstants.CODE_BRAND))).thenReturn(false);
		
		
		
		validatorPhysicalModel.validateInsert(physicalModelRDTO);
	}
	
	@Test
	public void caseValidateInsertNotExistsFractionType() {
		
		physicalModelRDTO.setCodeContract("C");
		
		List<SensorRDTO> sensorsRDTO = new ArrayList<>();
		SensorRDTO sensorRDTO = new SensorRDTO();
		sensorRDTO.setCode("code");
		sensorRDTO.setCodeMCF(physicalModelRDTO.getCode());
		sensorsRDTO.add(sensorRDTO);
		physicalModelRDTO.setSensorsRDTO(sensorsRDTO);
		
		physicalModelRDTO.setCodeUbication("codeUbication");
		physicalModelRDTO.setCodeState("codeStateMCF");
		physicalModelRDTO.setCodeFractionType("codeFractionType");
		physicalModelRDTO.setCodeBrand("BRAND");
		physicalModelRDTO.setCodeInstallation(null);
		
		
		Mockito.when(validator.validateSensorsContract(physicalModelRDTO.getCodeContract(),physicalModelRDTO.getSensorsRDTO())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		Mockito.when(validator.validateCompatibilityTypeMCFTypesSensor(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeState(), new Entity(ValueListConstants.MCF_STATUS))).thenReturn(true);
		
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeType(),new Entity(ValueListConstants.FURNITURE_TYPE ))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeFractionType(),new Entity(ValueListConstants.FRACTION_TYPE))).thenReturn(false);
		
		
		validatorPhysicalModel.validateInsert(physicalModelRDTO);
	}
	
	@Test
	public void caseValidateInsertNotExistsType() {
		
		physicalModelRDTO.setCodeContract("C");
		
		List<SensorRDTO> sensorsRDTO = new ArrayList<>();
		SensorRDTO sensorRDTO = new SensorRDTO();
		sensorRDTO.setCode("code");
		sensorRDTO.setCodeMCF(physicalModelRDTO.getCode());
		sensorsRDTO.add(sensorRDTO);
		physicalModelRDTO.setSensorsRDTO(sensorsRDTO);
		
		physicalModelRDTO.setCodeUbication("codeUbication");
		physicalModelRDTO.setCodeState("codeStateMCF");
		physicalModelRDTO.setCodeFractionType("codeFractionType");
		physicalModelRDTO.setCodeBrand("BRAND");
		physicalModelRDTO.setCodeInstallation(null);
		
		
		Mockito.when(validator.validateSensorsContract(physicalModelRDTO.getCodeContract(),physicalModelRDTO.getSensorsRDTO())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		Mockito.when(validator.validateCompatibilityTypeMCFTypesSensor(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeState(), new Entity(ValueListConstants.MCF_STATUS))).thenReturn(true);
		
		
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeType(),new Entity(ValueListConstants.FURNITURE_TYPE ))).thenReturn(false);
		
		
		validatorPhysicalModel.validateInsert(physicalModelRDTO);
	}
	
	@Test
	public void caseValidateInsertNotExistsStatus() {
		
		physicalModelRDTO.setCodeContract("C");
		
		List<SensorRDTO> sensorsRDTO = new ArrayList<>();
		SensorRDTO sensorRDTO = new SensorRDTO();
		sensorRDTO.setCode("code");
		sensorRDTO.setCodeMCF(physicalModelRDTO.getCode());
		sensorsRDTO.add(sensorRDTO);
		physicalModelRDTO.setSensorsRDTO(sensorsRDTO);
		
		physicalModelRDTO.setCodeUbication("codeUbication");
		physicalModelRDTO.setCodeState("codeStateMCF");
		physicalModelRDTO.setCodeFractionType("codeFractionType");
		physicalModelRDTO.setCodeBrand("BRAND");
		physicalModelRDTO.setCodeInstallation(null);
		
		
		Mockito.when(validator.validateSensorsContract(physicalModelRDTO.getCodeContract(),physicalModelRDTO.getSensorsRDTO())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		Mockito.when(validator.validateCompatibilityTypeMCFTypesSensor(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeState(), new Entity(ValueListConstants.MCF_STATUS))).thenReturn(false);
		
		
		validatorPhysicalModel.validateInsert(physicalModelRDTO);
	}
	
	@Test
	public void caseValidateInsertIncompatiblSensors() {
		
		physicalModelRDTO.setCodeContract("C");
		
		List<SensorRDTO> sensorsRDTO = new ArrayList<>();
		SensorRDTO sensorRDTO = new SensorRDTO();
		sensorRDTO.setCode("code");
		sensorRDTO.setCodeMCF(physicalModelRDTO.getCode());
		sensorsRDTO.add(sensorRDTO);
		physicalModelRDTO.setSensorsRDTO(sensorsRDTO);
		
		physicalModelRDTO.setCodeUbication("codeUbication");
		physicalModelRDTO.setCodeState("codeStateMCF");
		physicalModelRDTO.setCodeFractionType("codeFractionType");
		physicalModelRDTO.setCodeBrand("BRAND");
		physicalModelRDTO.setCodeInstallation(null);
		
		
		Mockito.when(validator.validateSensorsContract(physicalModelRDTO.getCodeContract(),physicalModelRDTO.getSensorsRDTO())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		Mockito.when(validator.validateCompatibilityTypeMCFTypesSensor(Mockito.anyString(), Mockito.anyList())).thenReturn(returnRDTO_KO);
		
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeState(), new Entity(ValueListConstants.MCF_STATUS))).thenReturn(true);
		
		
		validatorPhysicalModel.validateInsert(physicalModelRDTO);
	}
	
	@Test
	public void caseValidateInsertNoSensors() {
		
		physicalModelRDTO.setCodeContract("C");
		
		
		
		physicalModelRDTO.setCodeUbication("codeUbication");
		physicalModelRDTO.setCodeState("codeStateMCF");
		physicalModelRDTO.setCodeFractionType("codeFractionType");
		physicalModelRDTO.setCodeBrand("BRAND");
		physicalModelRDTO.setCodeInstallation(null);
		
		
		Mockito.when(validator.validateSensorsContract(physicalModelRDTO.getCodeContract(),physicalModelRDTO.getSensorsRDTO())).thenReturn(returnRDTO_KO);
		
		
		
		validatorPhysicalModel.validateInsert(physicalModelRDTO);
	}
	
	@Test
	public void caseOkValidateUpdate() {
		
		physicalModelRDTO.setCodeContract("C");
		
		List<SensorRDTO> sensorsRDTO = new ArrayList<>();
		SensorRDTO sensorRDTO = new SensorRDTO();
		sensorRDTO.setCode("code");
		sensorRDTO.setCodeMCF(physicalModelRDTO.getCode());
		sensorsRDTO.add(sensorRDTO);
		physicalModelRDTO.setSensorsRDTO(sensorsRDTO);
		
		physicalModelRDTO.setCodeUbication("codeUbication");
		physicalModelRDTO.setCodeState("codeStateMCF");
		physicalModelRDTO.setCodeFractionType("codeFractionType");
		physicalModelRDTO.setCodeBrand("BRAND");
		physicalModelRDTO.setCodeInstallation("CAAA");
		physicalModelRDTO.setCodeMCL("codeMCL");
		
		Mockito.when(validator.validateSensorsContract(physicalModelRDTO.getCodeContract(),physicalModelRDTO.getSensorsRDTO())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		Mockito.when(validator.validateCompatibilityTypeMCFTypesSensor(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeState(), new Entity(ValueListConstants.MCF_STATUS))).thenReturn(true);
		
		
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeType(),new Entity(ValueListConstants.FURNITURE_TYPE ))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeFractionType(),new Entity(ValueListConstants.FRACTION_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeBrand(),new Entity(ValueListConstants.CODE_BRAND))).thenReturn(true);
		
		Mockito.when(validator.validateExistsUbicationGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		Mockito.when(validator.validateExistsInstallationGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		Mockito.when(validator.validateExistsVacantLogicalModelGap(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsPhysicalModelGap(Mockito.anyString())).thenReturn(true);
		
		validatorPhysicalModel.validateUpdate(physicalModelRDTO);
	}

	@Test
	public void caseValidateUpdateNotExistsMCF() {
		
		physicalModelRDTO.setCodeContract("C");
		
		List<SensorRDTO> sensorsRDTO = new ArrayList<>();
		SensorRDTO sensorRDTO = new SensorRDTO();
		sensorRDTO.setCode("code");
		sensorRDTO.setCodeMCF(physicalModelRDTO.getCode());
		sensorsRDTO.add(sensorRDTO);
		physicalModelRDTO.setSensorsRDTO(sensorsRDTO);
		
		physicalModelRDTO.setCodeUbication("codeUbication");
		physicalModelRDTO.setCodeState("codeStateMCF");
		physicalModelRDTO.setCodeFractionType("codeFractionType");
		physicalModelRDTO.setCodeBrand("BRAND");
		physicalModelRDTO.setCodeInstallation("CAAA");
		physicalModelRDTO.setCodeMCL("codeMCL");
		
		Mockito.when(validator.validateSensorsContract(physicalModelRDTO.getCodeContract(),physicalModelRDTO.getSensorsRDTO())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		Mockito.when(validator.validateCompatibilityTypeMCFTypesSensor(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeState(), new Entity(ValueListConstants.MCF_STATUS))).thenReturn(true);
		
		
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeType(),new Entity(ValueListConstants.FURNITURE_TYPE ))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeFractionType(),new Entity(ValueListConstants.FRACTION_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeBrand(),new Entity(ValueListConstants.CODE_BRAND))).thenReturn(true);
		
		Mockito.when(validator.validateExistsUbicationGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		Mockito.when(validator.validateExistsInstallationGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		Mockito.when(validator.validateExistsVacantLogicalModelGap(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsPhysicalModelGap(Mockito.anyString())).thenReturn(false);
		
		validatorPhysicalModel.validateUpdate(physicalModelRDTO);
	}

	
	@Test
	public void caseValidateUpdateNotExistsVacantMCF() {
		
		physicalModelRDTO.setCodeContract("C");
		
		List<SensorRDTO> sensorsRDTO = new ArrayList<>();
		SensorRDTO sensorRDTO = new SensorRDTO();
		sensorRDTO.setCode("code");
		sensorRDTO.setCodeMCF(physicalModelRDTO.getCode());
		sensorsRDTO.add(sensorRDTO);
		physicalModelRDTO.setSensorsRDTO(sensorsRDTO);
		
		physicalModelRDTO.setCodeUbication("codeUbication");
		physicalModelRDTO.setCodeState("codeStateMCF");
		physicalModelRDTO.setCodeFractionType("codeFractionType");
		physicalModelRDTO.setCodeBrand("BRAND");
		physicalModelRDTO.setCodeInstallation("CAAA");
		physicalModelRDTO.setCodeMCL("codeMCL");
		
		Mockito.when(validator.validateSensorsContract(physicalModelRDTO.getCodeContract(),physicalModelRDTO.getSensorsRDTO())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		Mockito.when(validator.validateCompatibilityTypeMCFTypesSensor(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeState(), new Entity(ValueListConstants.MCF_STATUS))).thenReturn(true);
		
		
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeType(),new Entity(ValueListConstants.FURNITURE_TYPE ))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeFractionType(),new Entity(ValueListConstants.FRACTION_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeBrand(),new Entity(ValueListConstants.CODE_BRAND))).thenReturn(true);
		
		Mockito.when(validator.validateExistsUbicationGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		Mockito.when(validator.validateExistsInstallationGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		Mockito.when(validator.validateExistsVacantLogicalModelGap(Mockito.anyString(),Mockito.anyString())).thenReturn(false);
		
		
		validatorPhysicalModel.validateUpdate(physicalModelRDTO);
	}
	
	@Test
	public void caseValidateUpdateMCLNull() {
		
		physicalModelRDTO.setCodeContract("C");
		
		List<SensorRDTO> sensorsRDTO = new ArrayList<>();
		SensorRDTO sensorRDTO = new SensorRDTO();
		sensorRDTO.setCode("code");
		sensorRDTO.setCodeMCF(physicalModelRDTO.getCode());
		sensorsRDTO.add(sensorRDTO);
		physicalModelRDTO.setSensorsRDTO(sensorsRDTO);
		
		physicalModelRDTO.setCodeUbication("codeUbication");
		physicalModelRDTO.setCodeState("codeStateMCF");
		physicalModelRDTO.setCodeFractionType("codeFractionType");
		physicalModelRDTO.setCodeBrand("BRAND");
		physicalModelRDTO.setCodeInstallation("CAAA");
		physicalModelRDTO.setCodeMCL(null);
		
		Mockito.when(validator.validateSensorsContract(physicalModelRDTO.getCodeContract(),physicalModelRDTO.getSensorsRDTO())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		Mockito.when(validator.validateCompatibilityTypeMCFTypesSensor(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeState(), new Entity(ValueListConstants.MCF_STATUS))).thenReturn(true);
		
		
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeType(),new Entity(ValueListConstants.FURNITURE_TYPE ))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeFractionType(),new Entity(ValueListConstants.FRACTION_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeBrand(),new Entity(ValueListConstants.CODE_BRAND))).thenReturn(true);
		
		Mockito.when(validator.validateExistsUbicationGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		Mockito.when(validator.validateExistsInstallationGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		Mockito.when(validator.validateExistsVacantLogicalModelGap(Mockito.anyString(),Mockito.anyString())).thenReturn(false);
		
		
		validatorPhysicalModel.validateUpdate(physicalModelRDTO);
	}
	
	@Test
	public void caseValidateUpdateNotExistsInstallation() {
		
		physicalModelRDTO.setCodeContract("C");
		
		List<SensorRDTO> sensorsRDTO = new ArrayList<>();
		SensorRDTO sensorRDTO = new SensorRDTO();
		sensorRDTO.setCode("code");
		sensorRDTO.setCodeMCF(physicalModelRDTO.getCode());
		sensorsRDTO.add(sensorRDTO);
		physicalModelRDTO.setSensorsRDTO(sensorsRDTO);
		
		physicalModelRDTO.setCodeUbication("codeUbication");
		physicalModelRDTO.setCodeState("codeStateMCF");
		physicalModelRDTO.setCodeFractionType("codeFractionType");
		physicalModelRDTO.setCodeBrand("BRAND");
		physicalModelRDTO.setCodeInstallation("CAAA");
		physicalModelRDTO.setCodeMCL("codeMCL");
		
		Mockito.when(validator.validateSensorsContract(physicalModelRDTO.getCodeContract(),physicalModelRDTO.getSensorsRDTO())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		Mockito.when(validator.validateCompatibilityTypeMCFTypesSensor(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeState(), new Entity(ValueListConstants.MCF_STATUS))).thenReturn(true);
		
		
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeType(),new Entity(ValueListConstants.FURNITURE_TYPE ))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeFractionType(),new Entity(ValueListConstants.FRACTION_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeBrand(),new Entity(ValueListConstants.CODE_BRAND))).thenReturn(true);
		
		Mockito.when(validator.validateExistsUbicationGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		Mockito.when(validator.validateExistsInstallationGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(false);
		Mockito.when(validator.validateExistsVacantLogicalModelGap(Mockito.anyString(),Mockito.anyString())).thenReturn(false);
		
		
		validatorPhysicalModel.validateUpdate(physicalModelRDTO);
	}	

	@Test
	public void caseOkValidateDelete() {
		
		physicalModelRDTO.setCodeContract("C");
		
		List<SensorRDTO> sensorsRDTO = new ArrayList<>();
		SensorRDTO sensorRDTO = new SensorRDTO();
		sensorRDTO.setCode("code");
		sensorRDTO.setCodeMCF(physicalModelRDTO.getCode());
		sensorsRDTO.add(sensorRDTO);
		physicalModelRDTO.setSensorsRDTO(sensorsRDTO);
		
		physicalModelRDTO.setCodeUbication("codeUbication");
		physicalModelRDTO.setCodeState("codeStateMCF");
		physicalModelRDTO.setCodeFractionType("codeFractionType");
		physicalModelRDTO.setCodeBrand("BRAND");
		physicalModelRDTO.setCodeInstallation("CAAA");
		physicalModelRDTO.setCodeMCL("codeMCL");
		
		Mockito.when(validator.validateSensorsContract(physicalModelRDTO.getCodeContract(),physicalModelRDTO.getSensorsRDTO())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		Mockito.when(validator.validateCompatibilityTypeMCFTypesSensor(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeState(), new Entity(ValueListConstants.MCF_STATUS))).thenReturn(true);
		
		
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeType(),new Entity(ValueListConstants.FURNITURE_TYPE ))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeFractionType(),new Entity(ValueListConstants.FRACTION_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeBrand(),new Entity(ValueListConstants.CODE_BRAND))).thenReturn(true);
		
		Mockito.when(validator.validateExistsUbicationGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		Mockito.when(validator.validateExistsInstallationGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		
		Mockito.when(validator.validateExistsPhysicalModelGap(Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsAssociationMCLMCF(Mockito.anyString(),Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		
		validatorPhysicalModel.validateDelete(physicalModelRDTO);
	}

	
	@Test
	public void caseValidateDeleteNotExistsAssociationMCLMCF() {
		
		physicalModelRDTO.setCodeContract("C");
		
		List<SensorRDTO> sensorsRDTO = new ArrayList<>();
		SensorRDTO sensorRDTO = new SensorRDTO();
		sensorRDTO.setCode("code");
		sensorRDTO.setCodeMCF(physicalModelRDTO.getCode());
		sensorsRDTO.add(sensorRDTO);
		physicalModelRDTO.setSensorsRDTO(sensorsRDTO);
		
		physicalModelRDTO.setCodeUbication("codeUbication");
		physicalModelRDTO.setCodeState("codeStateMCF");
		physicalModelRDTO.setCodeFractionType("codeFractionType");
		physicalModelRDTO.setCodeBrand("BRAND");
		physicalModelRDTO.setCodeInstallation("CAAA");
		physicalModelRDTO.setCodeMCL("codeMCL");
		
		Mockito.when(validator.validateSensorsContract(physicalModelRDTO.getCodeContract(),physicalModelRDTO.getSensorsRDTO())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		Mockito.when(validator.validateCompatibilityTypeMCFTypesSensor(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeState(), new Entity(ValueListConstants.MCF_STATUS))).thenReturn(true);
		
		
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeType(),new Entity(ValueListConstants.FURNITURE_TYPE ))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeFractionType(),new Entity(ValueListConstants.FRACTION_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeBrand(),new Entity(ValueListConstants.CODE_BRAND))).thenReturn(true);
		
		Mockito.when(validator.validateExistsUbicationGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		Mockito.when(validator.validateExistsInstallationGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		
		Mockito.when(validator.validateExistsPhysicalModelGap(Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsAssociationMCLMCF(Mockito.anyString(),Mockito.anyString(),Mockito.anyString())).thenReturn(false);
		
		validatorPhysicalModel.validateDelete(physicalModelRDTO);
	}

	@Test
	public void caseValidateDeleteMCLNull() {
		
		physicalModelRDTO.setCodeContract("C");
		
		List<SensorRDTO> sensorsRDTO = new ArrayList<>();
		SensorRDTO sensorRDTO = new SensorRDTO();
		sensorRDTO.setCode("code");
		sensorRDTO.setCodeMCF(physicalModelRDTO.getCode());
		sensorsRDTO.add(sensorRDTO);
		physicalModelRDTO.setSensorsRDTO(sensorsRDTO);
		
		physicalModelRDTO.setCodeUbication("codeUbication");
		physicalModelRDTO.setCodeState("codeStateMCF");
		physicalModelRDTO.setCodeFractionType("codeFractionType");
		physicalModelRDTO.setCodeBrand("BRAND");
		physicalModelRDTO.setCodeInstallation("CAAA");
		physicalModelRDTO.setCodeMCL("");
		
		Mockito.when(validator.validateSensorsContract(physicalModelRDTO.getCodeContract(),physicalModelRDTO.getSensorsRDTO())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		Mockito.when(validator.validateCompatibilityTypeMCFTypesSensor(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeState(), new Entity(ValueListConstants.MCF_STATUS))).thenReturn(true);
		
		
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeType(),new Entity(ValueListConstants.FURNITURE_TYPE ))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeFractionType(),new Entity(ValueListConstants.FRACTION_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeBrand(),new Entity(ValueListConstants.CODE_BRAND))).thenReturn(true);
		
		Mockito.when(validator.validateExistsUbicationGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		Mockito.when(validator.validateExistsInstallationGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		
		Mockito.when(validator.validateExistsPhysicalModelGap(Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsAssociationMCLMCF(Mockito.anyString(),Mockito.anyString(),Mockito.anyString())).thenReturn(false);
		
		validatorPhysicalModel.validateDelete(physicalModelRDTO);
	}

	@Test
	public void caseValidateDeleteNotExistsMCF() {
		
		physicalModelRDTO.setCodeContract("C");
		
		List<SensorRDTO> sensorsRDTO = new ArrayList<>();
		SensorRDTO sensorRDTO = new SensorRDTO();
		sensorRDTO.setCode("code");
		sensorRDTO.setCodeMCF(physicalModelRDTO.getCode());
		sensorsRDTO.add(sensorRDTO);
		physicalModelRDTO.setSensorsRDTO(sensorsRDTO);
		
		physicalModelRDTO.setCodeUbication("codeUbication");
		physicalModelRDTO.setCodeState("codeStateMCF");
		physicalModelRDTO.setCodeFractionType("codeFractionType");
		physicalModelRDTO.setCodeBrand("BRAND");
		physicalModelRDTO.setCodeInstallation("CAAA");
		physicalModelRDTO.setCodeMCL(null);
		
		Mockito.when(validator.validateSensorsContract(physicalModelRDTO.getCodeContract(),physicalModelRDTO.getSensorsRDTO())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		Mockito.when(validator.validateCompatibilityTypeMCFTypesSensor(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeState(), new Entity(ValueListConstants.MCF_STATUS))).thenReturn(true);
		
		
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeType(),new Entity(ValueListConstants.FURNITURE_TYPE ))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeFractionType(),new Entity(ValueListConstants.FRACTION_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeBrand(),new Entity(ValueListConstants.CODE_BRAND))).thenReturn(true);
		
		Mockito.when(validator.validateExistsUbicationGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		Mockito.when(validator.validateExistsInstallationGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		
		Mockito.when(validator.validateExistsPhysicalModelGap(Mockito.anyString())).thenReturn(false);
		
		
		validatorPhysicalModel.validateDelete(physicalModelRDTO);
	}

	@Test
	public void caseValidateDeleteNotExistsInstallation() {
		
		physicalModelRDTO.setCodeContract("C");
		
		List<SensorRDTO> sensorsRDTO = new ArrayList<>();
		SensorRDTO sensorRDTO = new SensorRDTO();
		sensorRDTO.setCode("code");
		sensorRDTO.setCodeMCF(physicalModelRDTO.getCode());
		sensorsRDTO.add(sensorRDTO);
		physicalModelRDTO.setSensorsRDTO(sensorsRDTO);
		
		physicalModelRDTO.setCodeUbication("codeUbication");
		physicalModelRDTO.setCodeState("codeStateMCF");
		physicalModelRDTO.setCodeFractionType("codeFractionType");
		physicalModelRDTO.setCodeBrand("BRAND");
		physicalModelRDTO.setCodeInstallation("CAAA");
		physicalModelRDTO.setCodeMCL("codeMCL");
		
		Mockito.when(validator.validateSensorsContract(physicalModelRDTO.getCodeContract(),physicalModelRDTO.getSensorsRDTO())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		Mockito.when(validator.validateCompatibilityTypeMCFTypesSensor(Mockito.anyString(), Mockito.anyList())).thenReturn(ReturnRDTOStub.getSuccessMessage());
		
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeState(), new Entity(ValueListConstants.MCF_STATUS))).thenReturn(true);
		
		
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeType(),new Entity(ValueListConstants.FURNITURE_TYPE ))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeFractionType(),new Entity(ValueListConstants.FRACTION_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(physicalModelRDTO.getCodeBrand(),new Entity(ValueListConstants.CODE_BRAND))).thenReturn(true);
		
		Mockito.when(validator.validateExistsUbicationGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		Mockito.when(validator.validateExistsInstallationGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(false);
		Mockito.when(validator.validateExistsVacantLogicalModelGap(Mockito.anyString(),Mockito.anyString())).thenReturn(false);
		
		
		validatorPhysicalModel.validateDelete(physicalModelRDTO);
	}	

	@Test
	public void caseOkValidateInsertAmortitzation() {
		aggregateAmortizationRDTO.setCodeFraction("FRACTION");
		aggregateAmortizationRDTO.setCodeGroupService("GROUP_SERVICE");
		aggregateAmortizationRDTO.setCodeTerritory("DISTRICT");
		aggregateAmortizationRDTO.setCodeBrandFurniture("BRAND");
		aggregateAmortizationRDTO.setCodeTypeFurniture("MCF_TYPE");
		
		Mockito.when(validator.validateValueListGap(aggregateAmortizationRDTO.getCodeFraction(), new Entity(ValueListConstants.FRACTION_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(aggregateAmortizationRDTO.getCodeGroupService(), new Entity(ValueListConstants.GROUP_SERVICE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(aggregateAmortizationRDTO.getCodeTerritory(), new Entity(ValueListConstants.CODE_DISTRICT))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(aggregateAmortizationRDTO.getCodeBrandFurniture(), new Entity(ValueListConstants.CODE_BRAND))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(aggregateAmortizationRDTO.getCodeTypeFurniture(), new Entity(ValueListConstants.MCF_TYPE))).thenReturn(true);
		
		validatorPhysicalModel.validateInsert(aggregateAmortizationRDTO);
	}
	
	@Test
	public void caseValidateInsertAmortitzationNotExistsMCFType() {
		aggregateAmortizationRDTO.setCodeFraction("FRACTION");
		aggregateAmortizationRDTO.setCodeGroupService("GROUP_SERVICE");
		aggregateAmortizationRDTO.setCodeTerritory("DISTRICT");
		aggregateAmortizationRDTO.setCodeBrandFurniture("BRAND");
		aggregateAmortizationRDTO.setCodeTypeFurniture("MCF_TYPE");
		
		Mockito.when(validator.validateValueListGap(aggregateAmortizationRDTO.getCodeFraction(), new Entity(ValueListConstants.FRACTION_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(aggregateAmortizationRDTO.getCodeGroupService(), new Entity(ValueListConstants.GROUP_SERVICE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(aggregateAmortizationRDTO.getCodeTerritory(), new Entity(ValueListConstants.CODE_DISTRICT))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(aggregateAmortizationRDTO.getCodeBrandFurniture(), new Entity(ValueListConstants.CODE_BRAND))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(aggregateAmortizationRDTO.getCodeTypeFurniture(), new Entity(ValueListConstants.MCF_TYPE))).thenReturn(false);
		
		validatorPhysicalModel.validateInsert(aggregateAmortizationRDTO);
	}
	
	@Test
	public void caseValidateInsertAmortitzationNotExistsBrand() {
		aggregateAmortizationRDTO.setCodeFraction("FRACTION");
		aggregateAmortizationRDTO.setCodeGroupService("GROUP_SERVICE");
		aggregateAmortizationRDTO.setCodeTerritory("DISTRICT");
		aggregateAmortizationRDTO.setCodeBrandFurniture("BRAND");
		
		
		Mockito.when(validator.validateValueListGap(aggregateAmortizationRDTO.getCodeFraction(), new Entity(ValueListConstants.FRACTION_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(aggregateAmortizationRDTO.getCodeGroupService(), new Entity(ValueListConstants.GROUP_SERVICE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(aggregateAmortizationRDTO.getCodeTerritory(), new Entity(ValueListConstants.CODE_DISTRICT))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(aggregateAmortizationRDTO.getCodeBrandFurniture(), new Entity(ValueListConstants.CODE_BRAND))).thenReturn(false);
		
		
		validatorPhysicalModel.validateInsert(aggregateAmortizationRDTO);
	}
	
	@Test
	public void caseValidateInsertAmortitzationNotExistsDistrict() {
		aggregateAmortizationRDTO.setCodeFraction("FRACTION");
		aggregateAmortizationRDTO.setCodeGroupService("GROUP_SERVICE");
		aggregateAmortizationRDTO.setCodeTerritory("DISTRICT");
		
		Mockito.when(validator.validateValueListGap(aggregateAmortizationRDTO.getCodeFraction(), new Entity(ValueListConstants.FRACTION_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(aggregateAmortizationRDTO.getCodeGroupService(), new Entity(ValueListConstants.GROUP_SERVICE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(aggregateAmortizationRDTO.getCodeTerritory(), new Entity(ValueListConstants.CODE_DISTRICT))).thenReturn(false);
		
		
		validatorPhysicalModel.validateInsert(aggregateAmortizationRDTO);
	}
	
	@Test
	public void caseValidateInsertAmortitzationNotExistsGroupService() {
		aggregateAmortizationRDTO.setCodeFraction("FRACTION");
		aggregateAmortizationRDTO.setCodeGroupService("GROUP_SERVICE");
		
		
		Mockito.when(validator.validateValueListGap(aggregateAmortizationRDTO.getCodeFraction(), new Entity(ValueListConstants.FRACTION_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(aggregateAmortizationRDTO.getCodeGroupService(), new Entity(ValueListConstants.GROUP_SERVICE))).thenReturn(false);
		
		
		validatorPhysicalModel.validateInsert(aggregateAmortizationRDTO);
	}
	
	@Test
	public void caseValidateInsertAmortitzationNotExistsFractionType() {
		aggregateAmortizationRDTO.setCodeFraction("FRACTION");
		
		Mockito.when(validator.validateValueListGap(aggregateAmortizationRDTO.getCodeFraction(), new Entity(ValueListConstants.FRACTION_TYPE))).thenReturn(false);
		
		validatorPhysicalModel.validateInsert(aggregateAmortizationRDTO);
	}
	
	@Test
	public void caseOkValidateInsertDocumentarySupport() {
		
		Mockito.when(validator.validateExistsPhysicalModelGap(documentarySupportRDTO.getCode())).thenReturn(true);
		Mockito.when(validatorDocumentarySupport.validateInsert(Mockito.any(documentarySupportRDTO.getClass()))).thenReturn(returnRDTO);
			
		validatorPhysicalModel.validateInsertDocumentarySupport(documentarySupportRDTO);
	}

	@Test
	public void caseOkValidateInsertDocumentarySupportNotExistsMCF() {
		
		Mockito.when(validator.validateExistsPhysicalModelGap(documentarySupportRDTO.getCode())).thenReturn(false);
		Mockito.when(validatorDocumentarySupport.validateInsert(Mockito.any(documentarySupportRDTO.getClass()))).thenReturn(returnRDTO);		
			
		validatorPhysicalModel.validateInsertDocumentarySupport(documentarySupportRDTO);
	}

}
