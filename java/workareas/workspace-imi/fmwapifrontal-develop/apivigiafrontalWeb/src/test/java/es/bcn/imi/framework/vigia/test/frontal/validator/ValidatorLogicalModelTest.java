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
import es.bcn.imi.framework.vigia.frontal.validator.ValidatorLogicalModel;
import es.bcn.imi.framework.vigia.frontal.validator.ValidatorUtils;
import es.bcn.imi.framework.vigia.frontal.web.rest.config.RestServerConfig;
import es.bcn.vigia.fmw.libcommons.constants.ValueListConstants;
import es.bcn.vigia.fmw.libcommons.valueobject.Entity;
import es.bcn.vigia.fmw.libcommons.valueobject.Type;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.ActuationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.DocumentarySupportRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.LogicalModelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.ActuationRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.DocumentarySupportRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.LogicalModelRDTOStub;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RestServerConfig.class })
@TestPropertySource(locations = {"classpath:app/config/application.properties"})
@WebAppConfiguration
public class ValidatorLogicalModelTest {

	@Mock
	private ValidatorUtils validator;
	
	@Mock
	private ValidatorDocumentarySupport validatorDocumentarySupport;

	@Mock
	private ValidatorActuation validatorActuation;

	@InjectMocks
	private ValidatorLogicalModel validatorLogicalModel;
	
	private LogicalModelRDTO logicalModelRDTO;

	private DocumentarySupportRDTO documentarySupportRDTO;
	
	private ActuationRDTO actuationRDTO;

	private ReturnRDTO returnRDTO;
	
	@SuppressWarnings("unused")
	private ReturnRDTO returnRDTOKO;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		logicalModelRDTO = LogicalModelRDTOStub.defaultOne();
		documentarySupportRDTO = DocumentarySupportRDTOStub.defaultOne();
		returnRDTO = ReturnRDTOStub.getSuccessMessage();
		returnRDTOKO = ReturnRDTOStub.getErrorMessage();
		actuationRDTO = ActuationRDTOStub.defaultOne();
	}
	
	@Test
	public void caseOkValidateInsert() {
		logicalModelRDTO.setCodeType("codeType");
		logicalModelRDTO.setCodeFractionType("codeFractionType");
		
		Mockito.when(validator.validateValueListGap(logicalModelRDTO.getCodeType(), new Entity(ValueListConstants.FURNITURE_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(logicalModelRDTO.getCodeFractionType(), new Entity(ValueListConstants.FRACTION_TYPE))).thenReturn(true);
		
		Mockito.when(validator.validateCompatibilityTypeMCLUbication(Mockito.anyString(),Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		Mockito.when(validator.validateExistsUbicationGapByCodeType(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsLogicalModelPositionUbication(Mockito.anyString(),Mockito.anyLong(),Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateExistsLogicalModelGap(logicalModelRDTO.getCode(),false)).thenReturn(false);
		
			
		validatorLogicalModel.validateInsert(logicalModelRDTO);
	}

	@Test
	public void caseValidateInsertExistsLogicalModel() {
		logicalModelRDTO.setCodeType("codeType");
		logicalModelRDTO.setCodeFractionType("codeFractionType");
		
		Mockito.when(validator.validateValueListGap(logicalModelRDTO.getCodeType(), new Entity(ValueListConstants.FURNITURE_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(logicalModelRDTO.getCodeFractionType(), new Entity(ValueListConstants.FRACTION_TYPE))).thenReturn(true);
		
		Mockito.when(validator.validateCompatibilityTypeMCLUbication(Mockito.anyString(),Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		Mockito.when(validator.validateExistsUbicationGapByCodeType(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsLogicalModelPositionUbication(Mockito.anyString(),Mockito.anyLong(),Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateExistsLogicalModelGap(logicalModelRDTO.getCode(),false)).thenReturn(true);
		
			
		validatorLogicalModel.validateInsert(logicalModelRDTO);
	}
	
	@Test
	public void caseValidateInsertExistsOtherLogicalModelPosition() {
		logicalModelRDTO.setCodeType("codeType");
		logicalModelRDTO.setCodeFractionType("codeFractionType");
		
		Mockito.when(validator.validateValueListGap(logicalModelRDTO.getCodeType(), new Entity(ValueListConstants.FURNITURE_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(logicalModelRDTO.getCodeFractionType(), new Entity(ValueListConstants.FRACTION_TYPE))).thenReturn(true);
		
		Mockito.when(validator.validateCompatibilityTypeMCLUbication(Mockito.anyString(),Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		Mockito.when(validator.validateExistsUbicationGapByCodeType(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsLogicalModelPositionUbication(Mockito.anyString(),Mockito.anyLong(),Mockito.anyString())).thenReturn(true);
	
			
		validatorLogicalModel.validateInsert(logicalModelRDTO);
	}
	
	@Test
	public void caseValidateInsertNotExistsUbication() {
		logicalModelRDTO.setCodeType("codeType");
		logicalModelRDTO.setCodeFractionType("codeFractionType");
		
		Mockito.when(validator.validateValueListGap(logicalModelRDTO.getCodeType(), new Entity(ValueListConstants.FURNITURE_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(logicalModelRDTO.getCodeFractionType(), new Entity(ValueListConstants.FRACTION_TYPE))).thenReturn(true);
		
		Mockito.when(validator.validateCompatibilityTypeMCLUbication(Mockito.anyString(),Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		Mockito.when(validator.validateExistsUbicationGapByCodeType(Mockito.anyString(),Mockito.anyString())).thenReturn(false);
	
			
		validatorLogicalModel.validateInsert(logicalModelRDTO);
	}
	
	@Test
	public void caseValidateInsertNotCompatibleUbication() {
		logicalModelRDTO.setCodeType("codeType");
		logicalModelRDTO.setCodeFractionType("codeFractionType");
		
		Mockito.when(validator.validateValueListGap(logicalModelRDTO.getCodeType(), new Entity(ValueListConstants.FURNITURE_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(logicalModelRDTO.getCodeFractionType(), new Entity(ValueListConstants.FRACTION_TYPE))).thenReturn(true);
		Mockito.when(validator.validateExistsUbicationGapByCodeType(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateCompatibilityTypeMCLUbication(Mockito.anyString(),Mockito.anyString(),Mockito.anyBoolean())).thenReturn(false);
		
			
		validatorLogicalModel.validateInsert(logicalModelRDTO);
	}
	
	@Test
	public void caseValidateInsertNotFoundFraction() {
		logicalModelRDTO.setCodeType("codeType");
		logicalModelRDTO.setCodeFractionType("codeFractionType");
		
		Mockito.when(validator.validateValueListGap(logicalModelRDTO.getCodeType(), new Entity(ValueListConstants.FURNITURE_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(logicalModelRDTO.getCodeFractionType(), new Entity(ValueListConstants.FRACTION_TYPE))).thenReturn(false);
		
		Mockito.when(validator.validateExistsUbicationGapByCodeType(Mockito.anyString(),Mockito.anyString())).thenReturn(true);	
		validatorLogicalModel.validateInsert(logicalModelRDTO);
	}
	
	@Test
	public void caseValidateInsertNotFoundType() {
		logicalModelRDTO.setCodeType("codeType");
		logicalModelRDTO.setCodeFractionType("codeFractionType");
		
		Mockito.when(validator.validateExistsUbicationGapByCodeType(Mockito.anyString(),Mockito.anyString())).thenReturn(true);	
		Mockito.when(validator.validateValueListGap(logicalModelRDTO.getCodeType(), new Entity(ValueListConstants.FURNITURE_TYPE))).thenReturn(false);
			
		validatorLogicalModel.validateInsert(logicalModelRDTO);
	}
	@Test
	public void caseOkValidateUpdate() {
		logicalModelRDTO.setCodeType("codeType");
		logicalModelRDTO.setCodeFractionType("codeFractionType");
		
		Mockito.when(validator.validateValueListGap(logicalModelRDTO.getCodeType(), new Entity(ValueListConstants.FURNITURE_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(logicalModelRDTO.getCodeFractionType(), new Entity(ValueListConstants.FRACTION_TYPE))).thenReturn(true);
		
		Mockito.when(validator.validateCompatibilityTypeMCLUbication(Mockito.anyString(),Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		Mockito.when(validator.validateExistsUbicationGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		Mockito.when(validator.validateExistsLogicalModelPositionUbication(Mockito.anyString(),Mockito.anyLong(),Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateExistsLogicalModelGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		
			
		validatorLogicalModel.validateUpdate(logicalModelRDTO);
	}
	@Test
	public void caseValidateUpdateNotExistsUbication() {
		logicalModelRDTO.setCodeType("codeType");
		logicalModelRDTO.setCodeFractionType("codeFractionType");
		
		Mockito.when(validator.validateValueListGap(logicalModelRDTO.getCodeType(), new Entity(ValueListConstants.FURNITURE_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(logicalModelRDTO.getCodeFractionType(), new Entity(ValueListConstants.FRACTION_TYPE))).thenReturn(true);
		
		Mockito.when(validator.validateCompatibilityTypeMCLUbication(Mockito.anyString(),Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		Mockito.when(validator.validateExistsUbicationGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(false);
		Mockito.when(validator.validateExistsLogicalModelPositionUbication(Mockito.anyString(),Mockito.anyLong(),Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateExistsLogicalModelGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		
			
		validatorLogicalModel.validateUpdate(logicalModelRDTO);
	}
	
	@Test
	public void caseValidateUpdateMotExistsType() {
		logicalModelRDTO.setCodeType("codeType");
		logicalModelRDTO.setCodeFractionType("codeFractionType");
		
		Mockito.when(validator.validateValueListGap(logicalModelRDTO.getCodeType(), new Entity(ValueListConstants.FURNITURE_TYPE))).thenReturn(false);
		Mockito.when(validator.validateValueListGap(logicalModelRDTO.getCodeFractionType(), new Entity(ValueListConstants.FRACTION_TYPE))).thenReturn(true);
		
		Mockito.when(validator.validateCompatibilityTypeMCLUbication(Mockito.anyString(),Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		Mockito.when(validator.validateExistsUbicationGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		Mockito.when(validator.validateExistsLogicalModelPositionUbication(Mockito.anyString(),Mockito.anyLong(),Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateExistsLogicalModelGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		
			
		validatorLogicalModel.validateUpdate(logicalModelRDTO);
	}
	
	@Test
	public void caseValidateUpdatePositionUbicationBusy() {
		logicalModelRDTO.setCodeType("codeType");
		logicalModelRDTO.setCodeFractionType("codeFractionType");
		
		Mockito.when(validator.validateValueListGap(logicalModelRDTO.getCodeType(), new Entity(ValueListConstants.FURNITURE_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(logicalModelRDTO.getCodeFractionType(), new Entity(ValueListConstants.FRACTION_TYPE))).thenReturn(true);
		
		Mockito.when(validator.validateCompatibilityTypeMCLUbication(Mockito.anyString(),Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		Mockito.when(validator.validateExistsUbicationGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		Mockito.when(validator.validateExistsLogicalModelPositionUbication(Mockito.anyString(),Mockito.anyLong(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsLogicalModelGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		
			
		validatorLogicalModel.validateUpdate(logicalModelRDTO);
	}
	
	@Test
	public void caseValidateUpdateNotExistsMCL() {
		logicalModelRDTO.setCodeType("codeType");
		logicalModelRDTO.setCodeFractionType("codeFractionType");
		
		Mockito.when(validator.validateValueListGap(logicalModelRDTO.getCodeType(), new Entity(ValueListConstants.FURNITURE_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(logicalModelRDTO.getCodeFractionType(), new Entity(ValueListConstants.FRACTION_TYPE))).thenReturn(true);
		
		Mockito.when(validator.validateCompatibilityTypeMCLUbication(Mockito.anyString(),Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		Mockito.when(validator.validateExistsUbicationGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		Mockito.when(validator.validateExistsLogicalModelPositionUbication(Mockito.anyString(),Mockito.anyLong(),Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateExistsLogicalModelGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(false);
		
			
		validatorLogicalModel.validateUpdate(logicalModelRDTO);
	}
	@Test
	public void caseOkValidateDelete() {
		logicalModelRDTO.setCodeType("codeType");
		logicalModelRDTO.setCodeFractionType("codeFractionType");
		
		Mockito.when(validator.validateValueListGap(logicalModelRDTO.getCodeType(), new Entity(ValueListConstants.FURNITURE_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(logicalModelRDTO.getCodeFractionType(), new Entity(ValueListConstants.FRACTION_TYPE))).thenReturn(true);
		
		Mockito.when(validator.validateCompatibilityTypeMCLUbication(Mockito.anyString(),Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		Mockito.when(validator.validateExistsUbicationGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		Mockito.when(validator.validateExistsLogicalModelPositionUbication(Mockito.anyString(),Mockito.anyLong(),Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateExistsLogicalModelGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		
			
		validatorLogicalModel.validateDelete(logicalModelRDTO);
	}

	@Test
	public void caseValidateDeleteNotExistsUbication() {
		logicalModelRDTO.setCodeType("codeType");
		logicalModelRDTO.setCodeFractionType("codeFractionType");
		
		Mockito.when(validator.validateValueListGap(logicalModelRDTO.getCodeType(), new Entity(ValueListConstants.FURNITURE_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(logicalModelRDTO.getCodeFractionType(), new Entity(ValueListConstants.FRACTION_TYPE))).thenReturn(true);
		
		Mockito.when(validator.validateCompatibilityTypeMCLUbication(Mockito.anyString(),Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		Mockito.when(validator.validateExistsUbicationGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(false);
		Mockito.when(validator.validateExistsLogicalModelPositionUbication(Mockito.anyString(),Mockito.anyLong(),Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateExistsLogicalModelGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		
			
		validatorLogicalModel.validateDelete(logicalModelRDTO);
	}
	
	@Test
	public void caseValidateDeleteMotExistsType() {
		logicalModelRDTO.setCodeType("codeType");
		logicalModelRDTO.setCodeFractionType("codeFractionType");
		
		Mockito.when(validator.validateValueListGap(logicalModelRDTO.getCodeType(), new Entity(ValueListConstants.FURNITURE_TYPE))).thenReturn(false);
		Mockito.when(validator.validateValueListGap(logicalModelRDTO.getCodeFractionType(), new Entity(ValueListConstants.FRACTION_TYPE))).thenReturn(true);
		
		Mockito.when(validator.validateCompatibilityTypeMCLUbication(Mockito.anyString(),Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		Mockito.when(validator.validateExistsUbicationGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		Mockito.when(validator.validateExistsLogicalModelPositionUbication(Mockito.anyString(),Mockito.anyLong(),Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateExistsLogicalModelGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		
			
		validatorLogicalModel.validateDelete(logicalModelRDTO);
	}
	
	@Test
	public void caseValidateDeletePositionUbicationBusy() {
		logicalModelRDTO.setCodeType("codeType");
		logicalModelRDTO.setCodeFractionType("codeFractionType");
		
		Mockito.when(validator.validateValueListGap(logicalModelRDTO.getCodeType(), new Entity(ValueListConstants.FURNITURE_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(logicalModelRDTO.getCodeFractionType(), new Entity(ValueListConstants.FRACTION_TYPE))).thenReturn(true);
		
		Mockito.when(validator.validateCompatibilityTypeMCLUbication(Mockito.anyString(),Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		Mockito.when(validator.validateExistsUbicationGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		Mockito.when(validator.validateExistsLogicalModelPositionUbication(Mockito.anyString(),Mockito.anyLong(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsLogicalModelGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		
			
		validatorLogicalModel.validateDelete(logicalModelRDTO);
	}
	
	@Test
	public void caseValidateDeleteNotExistsMCL() {
		logicalModelRDTO.setCodeType("codeType");
		logicalModelRDTO.setCodeFractionType("codeFractionType");
		
		Mockito.when(validator.validateValueListGap(logicalModelRDTO.getCodeType(), new Entity(ValueListConstants.FURNITURE_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(logicalModelRDTO.getCodeFractionType(), new Entity(ValueListConstants.FRACTION_TYPE))).thenReturn(true);
		
		Mockito.when(validator.validateCompatibilityTypeMCLUbication(Mockito.anyString(),Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		Mockito.when(validator.validateExistsUbicationGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		Mockito.when(validator.validateExistsLogicalModelPositionUbication(Mockito.anyString(),Mockito.anyLong(),Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateExistsLogicalModelGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(false);
		
			
		validatorLogicalModel.validateDelete(logicalModelRDTO);
	}
	
	@Test
	public void caseValidateDeleteHasDependencies() {
		logicalModelRDTO.setCodeType("codeType");
		logicalModelRDTO.setCodeFractionType("codeFractionType");
		
		Mockito.when(validator.validateValueListGap(logicalModelRDTO.getCodeType(), new Entity(ValueListConstants.FURNITURE_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(logicalModelRDTO.getCodeFractionType(), new Entity(ValueListConstants.FRACTION_TYPE))).thenReturn(true);
		
		Mockito.when(validator.validateCompatibilityTypeMCLUbication(Mockito.anyString(),Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		Mockito.when(validator.validateExistsUbicationGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		Mockito.when(validator.validateExistsLogicalModelPositionUbication(Mockito.anyString(),Mockito.anyLong(),Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateExistsLogicalModelGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		Mockito.when(validator.validateExistsPhysicalModelLogicalModelGap(Mockito.anyString())).thenReturn(true);
		
			
		validatorLogicalModel.validateDelete(logicalModelRDTO);
	}
	
	@Test
	public void caseOkValidateInsertDocumentarySupport() {
		
		Mockito.when(validator.validateExistsLogicalModelGap(documentarySupportRDTO.getCode(),true)).thenReturn(true);
		Mockito.when(validatorDocumentarySupport.validateInsert(Mockito.any(documentarySupportRDTO.getClass()))).thenReturn(returnRDTO);
			
		validatorLogicalModel.validateInsertDocumentarySupport(documentarySupportRDTO);
	}

	@Test
	public void caseOkValidateInsertDocumentarySupportNotExistsMCL() {
		
		Mockito.when(validatorDocumentarySupport.validateInsert(Mockito.any(documentarySupportRDTO.getClass()))).thenReturn(returnRDTO);
		Mockito.when(validator.validateExistsLogicalModelGap(documentarySupportRDTO.getCode(),true)).thenReturn(false);
		
			
		validatorLogicalModel.validateInsertDocumentarySupport(documentarySupportRDTO);
	}
	
	@Test
	public void caseValidateInsertActuation() {
		
		
		Mockito.when(validatorActuation.validateInsert(actuationRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateExistsInstallationGap(actuationRDTO.getCodeEntity(),false)).thenReturn(true);
		Mockito.when(validator.validateValueList(actuationRDTO.getTypeActuation(),new Type(ValueListConstants.TYPE_ACTUATION_MCF))).thenReturn(true);
		
		
		validatorLogicalModel.validateInsertActuation(actuationRDTO);
	}

	@Test
	public void caseValidateDeleteActuation() {
		
		
		Mockito.when(validatorActuation.validateInsert(actuationRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateExistsLogicalModelGap(actuationRDTO.getCodeEntity(),false)).thenReturn(false);
		
		
		validatorLogicalModel.validateInsertActuation(actuationRDTO);
	}

}
