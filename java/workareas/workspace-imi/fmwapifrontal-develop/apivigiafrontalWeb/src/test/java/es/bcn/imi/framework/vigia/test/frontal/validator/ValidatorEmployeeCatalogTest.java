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

import es.bcn.imi.framework.vigia.frontal.validator.ValidatorEmployeeCatalog;
import es.bcn.imi.framework.vigia.frontal.validator.ValidatorUtils;
import es.bcn.imi.framework.vigia.frontal.web.rest.config.RestServerConfig;
import es.bcn.vigia.fmw.libcommons.constants.ValueListConstants;
import es.bcn.vigia.fmw.libcommons.valueobject.Type;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.EmployeeCatalogRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.EmployeeCatalogRDTOStub;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RestServerConfig.class })
@TestPropertySource(locations = {"classpath:app/config/application.properties"})
@WebAppConfiguration
public class ValidatorEmployeeCatalogTest {
	
	@Mock
	private ValidatorUtils validator;
	
	@InjectMocks
	private ValidatorEmployeeCatalog validatorEmployeeCatalog;
	
	private EmployeeCatalogRDTO employeeCatalogRDTO;
	
	
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		employeeCatalogRDTO = EmployeeCatalogRDTOStub.defaultOne();
	
	}

	@Test
	public void caseOkInsert() {
		validatorEmployeeCatalog.validateInsert(employeeCatalogRDTO);
	}



	@Test
	public void caseOkInsert2() {

		employeeCatalogRDTO.setCodeProfessionalCategory("A");
		
		Mockito.when(validator.validateValueList(employeeCatalogRDTO.getCodeProfessionalCategory(),new Type(ValueListConstants.PROFESSIONAL_CATEGORY))).thenReturn(true);
		
		validatorEmployeeCatalog.validateInsert(employeeCatalogRDTO);
	}

	@Test
	public void caseOkInsert3() {
		
		employeeCatalogRDTO.setCodeProfessionalCategory("A");
		employeeCatalogRDTO.setCodeWorkRegime("A");
		Mockito.when(validator.validateValueList(employeeCatalogRDTO.getCodeProfessionalCategory(),new Type(ValueListConstants.PROFESSIONAL_CATEGORY))).thenReturn(true);
		Mockito.when(validator.validateValueList(employeeCatalogRDTO.getCodeWorkRegime(),new Type(ValueListConstants.WORK_REGIME))).thenReturn(true);
		validatorEmployeeCatalog.validateInsert(employeeCatalogRDTO);
	}
	
	@Test
	public void caseOkInsert4() {
		
		employeeCatalogRDTO.setCodeProfessionalCategory("A");
		employeeCatalogRDTO.setCodeWorkRegime("A");
		employeeCatalogRDTO.setCodeLaborRelationship("A");
		Mockito.when(validator.validateValueList(employeeCatalogRDTO.getCodeProfessionalCategory(),new Type(ValueListConstants.PROFESSIONAL_CATEGORY))).thenReturn(true);
		Mockito.when(validator.validateValueList(employeeCatalogRDTO.getCodeWorkRegime(),new Type(ValueListConstants.WORK_REGIME))).thenReturn(true);
		Mockito.when(validator.validateValueList(employeeCatalogRDTO.getCodeLaborRelationship(),new Type(ValueListConstants.LABOR_RELATION_SHIP))).thenReturn(true);
		validatorEmployeeCatalog.validateInsert(employeeCatalogRDTO);
	}

	@Test
	public void caseOkInsert5() {
		
		employeeCatalogRDTO.setCodeProfessionalCategory("A");
		employeeCatalogRDTO.setCodeWorkRegime("A");
		employeeCatalogRDTO.setCodeLaborRelationship("A");
		employeeCatalogRDTO.setCodeContractingProvenance("A");
		
		Mockito.when(validator.validateValueList(employeeCatalogRDTO.getCodeProfessionalCategory(),new Type(ValueListConstants.PROFESSIONAL_CATEGORY))).thenReturn(true);
		Mockito.when(validator.validateValueList(employeeCatalogRDTO.getCodeWorkRegime(),new Type(ValueListConstants.WORK_REGIME))).thenReturn(true);
		Mockito.when(validator.validateValueList(employeeCatalogRDTO.getCodeLaborRelationship(),new Type(ValueListConstants.LABOR_RELATION_SHIP))).thenReturn(true);
		Mockito.when(validator.validateValueList(employeeCatalogRDTO.getCodeContractingProvenance(),new Type(ValueListConstants.CONTRACTING_PROVENANCE))).thenReturn(true);
		
		validatorEmployeeCatalog.validateInsert(employeeCatalogRDTO);
	}
	
	@Test
	public void caseOkInsert6() {
		
		employeeCatalogRDTO.setCodeProfessionalCategory("A");
		employeeCatalogRDTO.setCodeWorkRegime("A");
		employeeCatalogRDTO.setCodeLaborRelationship("A");
		employeeCatalogRDTO.setCodeContractingProvenance("A");
		employeeCatalogRDTO.setTypePersonal("A");
		
		Mockito.when(validator.validateValueList(employeeCatalogRDTO.getCodeProfessionalCategory(),new Type(ValueListConstants.PROFESSIONAL_CATEGORY))).thenReturn(true);
		Mockito.when(validator.validateValueList(employeeCatalogRDTO.getCodeWorkRegime(),new Type(ValueListConstants.WORK_REGIME))).thenReturn(true);
		Mockito.when(validator.validateValueList(employeeCatalogRDTO.getCodeLaborRelationship(),new Type(ValueListConstants.LABOR_RELATION_SHIP))).thenReturn(true);
		Mockito.when(validator.validateValueList(employeeCatalogRDTO.getCodeContractingProvenance(),new Type(ValueListConstants.CONTRACTING_PROVENANCE))).thenReturn(true);
		Mockito.when(validator.validateValueList(employeeCatalogRDTO.getTypePersonal(),new Type(ValueListConstants.TYPE_PERSONAL))).thenReturn(true);
		
		validatorEmployeeCatalog.validateInsert(employeeCatalogRDTO);
	}

	}
