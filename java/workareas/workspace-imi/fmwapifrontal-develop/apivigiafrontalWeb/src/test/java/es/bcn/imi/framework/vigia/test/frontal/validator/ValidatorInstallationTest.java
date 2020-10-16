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
import es.bcn.imi.framework.vigia.frontal.validator.ValidatorInstallation;
import es.bcn.imi.framework.vigia.frontal.validator.ValidatorUtils;
import es.bcn.imi.framework.vigia.frontal.web.rest.config.RestServerConfig;
import es.bcn.vigia.fmw.libcommons.constants.ValidationConstants;
import es.bcn.vigia.fmw.libcommons.constants.ValueListConstants;
import es.bcn.vigia.fmw.libcommons.valueobject.Entity;
import es.bcn.vigia.fmw.libcommons.valueobject.Type;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.ActuationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.ExpenseRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.InstallationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.LocationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.ActuationRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.InstallationRDTOStub;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RestServerConfig.class })
@TestPropertySource(locations = {"classpath:app/config/application.properties"})
@SuppressWarnings("unchecked")
@WebAppConfiguration
public class ValidatorInstallationTest {
	
	@Mock
	private ValidatorUtils validator;

	@Mock
	private ValidatorActuation validatorActuation;

	@InjectMocks
	private ValidatorInstallation validatorInstallation;
	
	private InstallationRDTO installationRDTO;

	private ActuationRDTO actuationRDTO;
	
	private ReturnRDTO returnRDTO;
	
	private ReturnRDTO returnRDTOKO;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		installationRDTO = InstallationRDTOStub.defaultOne();
		returnRDTO = ReturnRDTOStub.getSuccessMessage();
		returnRDTOKO = ReturnRDTOStub.getErrorMessage();
		actuationRDTO = ActuationRDTOStub.defaultOne();
	}

	
	@Test
	public void caseValidateInsert() {
		
		installationRDTO.setType("PA");
		installationRDTO.setCodeTypeTitularity("M");
		
		LocationRDTO locationRDTO = new LocationRDTO();
		locationRDTO.setPopulation(ValidationConstants.POPULATION_BARCELONA);
		locationRDTO.setCodeNeighborhood("40");
		locationRDTO.setCodeTerritory("DI");
		locationRDTO.setCodeTypeRoad("C");
		installationRDTO.setLocationRDTO(locationRDTO);
		
		Mockito.when(validator.validateValueListGap(installationRDTO.getLocationRDTO().getCodeNeighborhood(),new Entity(ValueListConstants.CODE_NEIGHBORHOOD))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(installationRDTO.getLocationRDTO().getCodeTerritory(),new Entity(ValueListConstants.CODE_DISTRICT))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(installationRDTO.getLocationRDTO().getCodeTypeRoad(),new Entity(ValueListConstants.CODE_TYPE_ROAD))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(installationRDTO.getType(),new Entity(ValueListConstants.CODE_TYPE_INSTALLATION))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(installationRDTO.getCodeTypeTitularity(),new Entity(ValueListConstants.TITULARITY_TYPE))).thenReturn(true);
		
		Mockito.when(validator.validateExistsInstallationGap(installationRDTO.getCode(),false)).thenReturn(false);
		validatorInstallation.validateInsert(installationRDTO);
	}

	@Test
	public void caseValidateInsertExistsInstallation() {
		
		installationRDTO.setType("PA");
		installationRDTO.setCodeTypeTitularity("M");
		installationRDTO.setCode("codeInstallation");
		
		LocationRDTO locationRDTO = new LocationRDTO();
		locationRDTO.setPopulation(ValidationConstants.POPULATION_BARCELONA);
		locationRDTO.setCodeNeighborhood("40");
		locationRDTO.setCodeTerritory("DI");
		locationRDTO.setCodeTypeRoad("C");
		installationRDTO.setLocationRDTO(locationRDTO);
		
		Mockito.when(validator.validateValueListGap(installationRDTO.getLocationRDTO().getCodeNeighborhood(),new Entity(ValueListConstants.CODE_NEIGHBORHOOD))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(installationRDTO.getLocationRDTO().getCodeTerritory(),new Entity(ValueListConstants.CODE_DISTRICT))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(installationRDTO.getLocationRDTO().getCodeTypeRoad(),new Entity(ValueListConstants.CODE_TYPE_ROAD))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(installationRDTO.getType(),new Entity(ValueListConstants.CODE_TYPE_INSTALLATION))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(installationRDTO.getCodeTypeTitularity(),new Entity(ValueListConstants.TITULARITY_TYPE))).thenReturn(true);
	
		Mockito.when(validator.validateExistsInstallationGap(installationRDTO.getCode(),false)).thenReturn(true);
		validatorInstallation.validateInsert(installationRDTO);
	}
	
	@Test
	public void caseValidateInsertNotExistsTitularity() {
		
		installationRDTO.setType("PA");
		installationRDTO.setCodeTypeTitularity("M");
		installationRDTO.setCode("codeInstallation");
		
		LocationRDTO locationRDTO = new LocationRDTO();
		locationRDTO.setPopulation(ValidationConstants.POPULATION_BARCELONA);
		locationRDTO.setCodeNeighborhood("40");
		locationRDTO.setCodeTerritory("DI");
		locationRDTO.setCodeTypeRoad("C");
		installationRDTO.setLocationRDTO(locationRDTO);
		
		Mockito.when(validator.validateValueListGap(installationRDTO.getLocationRDTO().getCodeNeighborhood(),new Entity(ValueListConstants.CODE_NEIGHBORHOOD))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(installationRDTO.getLocationRDTO().getCodeTerritory(),new Entity(ValueListConstants.CODE_DISTRICT))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(installationRDTO.getLocationRDTO().getCodeTypeRoad(),new Entity(ValueListConstants.CODE_TYPE_ROAD))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(installationRDTO.getType(),new Entity(ValueListConstants.CODE_TYPE_INSTALLATION))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(installationRDTO.getCodeTypeTitularity(),new Entity(ValueListConstants.TITULARITY_TYPE))).thenReturn(false);
	
		Mockito.when(validator.validateExistsInstallationGap(installationRDTO.getCode(),false)).thenReturn(true);
		validatorInstallation.validateInsert(installationRDTO);
	}
	
	@Test
	public void caseValidateInsertNotExistsTypeInstallation() {
		
		installationRDTO.setType("PA");
		installationRDTO.setCodeTypeTitularity("M");
		installationRDTO.setCode("codeInstallation");
		
		LocationRDTO locationRDTO = new LocationRDTO();
		locationRDTO.setPopulation(ValidationConstants.POPULATION_BARCELONA);
		locationRDTO.setCodeNeighborhood("40");
		locationRDTO.setCodeTerritory("DI");
		locationRDTO.setCodeTypeRoad("C");
		installationRDTO.setLocationRDTO(locationRDTO);
		
		Mockito.when(validator.validateValueListGap(installationRDTO.getLocationRDTO().getCodeNeighborhood(),new Entity(ValueListConstants.CODE_NEIGHBORHOOD))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(installationRDTO.getLocationRDTO().getCodeTerritory(),new Entity(ValueListConstants.CODE_DISTRICT))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(installationRDTO.getLocationRDTO().getCodeTypeRoad(),new Entity(ValueListConstants.CODE_TYPE_ROAD))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(installationRDTO.getType(),new Entity(ValueListConstants.CODE_TYPE_INSTALLATION))).thenReturn(false);
		
		
		validatorInstallation.validateInsert(installationRDTO);
	}
	
	@Test
	public void caseValidateInsertNotExistsTypeRoad() {
		
		installationRDTO.setType("PA");
		installationRDTO.setCodeTypeTitularity("M");
		installationRDTO.setCode("codeInstallation");
		
		LocationRDTO locationRDTO = new LocationRDTO();
		locationRDTO.setPopulation(ValidationConstants.POPULATION_BARCELONA);
		locationRDTO.setCodeNeighborhood("40");
		locationRDTO.setCodeTerritory("DI");
		locationRDTO.setCodeTypeRoad("C");
		installationRDTO.setLocationRDTO(locationRDTO);
		
		Mockito.when(validator.validateValueListGap(installationRDTO.getLocationRDTO().getCodeNeighborhood(),new Entity(ValueListConstants.CODE_NEIGHBORHOOD))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(installationRDTO.getLocationRDTO().getCodeTerritory(),new Entity(ValueListConstants.CODE_DISTRICT))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(installationRDTO.getLocationRDTO().getCodeTypeRoad(),new Entity(ValueListConstants.CODE_TYPE_ROAD))).thenReturn(false);
		
		
		
		validatorInstallation.validateInsert(installationRDTO);
	}
	
	@Test
	public void caseValidateInsertNotExistsDistrict() {
		
		installationRDTO.setType("PA");
		installationRDTO.setCodeTypeTitularity("M");
		installationRDTO.setCode("codeInstallation");
		
		LocationRDTO locationRDTO = new LocationRDTO();
		locationRDTO.setPopulation(ValidationConstants.POPULATION_BARCELONA);
		locationRDTO.setCodeNeighborhood("40");
		locationRDTO.setCodeTerritory("DI");
		locationRDTO.setCodeTypeRoad("C");
		installationRDTO.setLocationRDTO(locationRDTO);
		
		Mockito.when(validator.validateValueListGap(installationRDTO.getLocationRDTO().getCodeNeighborhood(),new Entity(ValueListConstants.CODE_NEIGHBORHOOD))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(installationRDTO.getLocationRDTO().getCodeTerritory(),new Entity(ValueListConstants.CODE_DISTRICT))).thenReturn(false);
		
		
		validatorInstallation.validateInsert(installationRDTO);
	}
	
	@Test
	public void caseValidateInsertNotExistsNeighborhood() {
		
		installationRDTO.setType("PA");
		installationRDTO.setCodeTypeTitularity("M");
		installationRDTO.setCode("codeInstallation");
		
		LocationRDTO locationRDTO = new LocationRDTO();
		locationRDTO.setPopulation(ValidationConstants.POPULATION_BARCELONA);
		locationRDTO.setCodeNeighborhood("40");
		locationRDTO.setCodeTerritory("DI");
		locationRDTO.setCodeTypeRoad("C");
		installationRDTO.setLocationRDTO(locationRDTO);
		
		Mockito.when(validator.validateValueListGap(installationRDTO.getLocationRDTO().getCodeNeighborhood(),new Entity(ValueListConstants.CODE_NEIGHBORHOOD))).thenReturn(false);
		
		validatorInstallation.validateInsert(installationRDTO);
	}

	@Test
	public void caseValidateUpdate() {
		
		installationRDTO.setType("PA");
		installationRDTO.setCodeTypeTitularity("M");
		
		LocationRDTO locationRDTO = new LocationRDTO();
		locationRDTO.setPopulation(ValidationConstants.POPULATION_BARCELONA);
		locationRDTO.setCodeNeighborhood("40");
		locationRDTO.setCodeTerritory("DI");
		locationRDTO.setCodeTypeRoad("C");
		installationRDTO.setLocationRDTO(locationRDTO);
		
		Mockito.when(validator.validateValueListGap(installationRDTO.getLocationRDTO().getCodeNeighborhood(),new Entity(ValueListConstants.CODE_NEIGHBORHOOD))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(installationRDTO.getLocationRDTO().getCodeTerritory(),new Entity(ValueListConstants.CODE_DISTRICT))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(installationRDTO.getLocationRDTO().getCodeTypeRoad(),new Entity(ValueListConstants.CODE_TYPE_ROAD))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(installationRDTO.getType(),new Entity(ValueListConstants.CODE_TYPE_INSTALLATION))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(installationRDTO.getCodeTypeTitularity(),new Entity(ValueListConstants.TITULARITY_TYPE))).thenReturn(true);
		
		Mockito.when(validator.validateExistsInstallationGap(installationRDTO.getCode(),true)).thenReturn(true);
		validatorInstallation.validateUpdate(installationRDTO);
	}

	@Test
	public void caseValidateUpdateNotExistsInstallation() {
		
		installationRDTO.setType("PA");
		installationRDTO.setCodeTypeTitularity("M");
		
		LocationRDTO locationRDTO = new LocationRDTO();
		locationRDTO.setPopulation(ValidationConstants.POPULATION_BARCELONA);
		locationRDTO.setCodeNeighborhood("40");
		locationRDTO.setCodeTerritory("DI");
		locationRDTO.setCodeTypeRoad("C");
		installationRDTO.setLocationRDTO(locationRDTO);
		
		Mockito.when(validator.validateValueListGap(installationRDTO.getLocationRDTO().getCodeNeighborhood(),new Entity(ValueListConstants.CODE_NEIGHBORHOOD))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(installationRDTO.getLocationRDTO().getCodeTerritory(),new Entity(ValueListConstants.CODE_DISTRICT))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(installationRDTO.getLocationRDTO().getCodeTypeRoad(),new Entity(ValueListConstants.CODE_TYPE_ROAD))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(installationRDTO.getType(),new Entity(ValueListConstants.CODE_TYPE_INSTALLATION))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(installationRDTO.getCodeTypeTitularity(),new Entity(ValueListConstants.TITULARITY_TYPE))).thenReturn(true);
		
		Mockito.when(validator.validateExistsInstallationGap(installationRDTO.getCode(),true)).thenReturn(false);
		validatorInstallation.validateUpdate(installationRDTO);
	}
	
	@Test
	public void caseValidateUpdateNotExistsTitularity() {
		
		installationRDTO.setType("PA");
		installationRDTO.setCodeTypeTitularity("M");
		
		LocationRDTO locationRDTO = new LocationRDTO();
		
		locationRDTO.setPopulation(ValidationConstants.POPULATION_BARCELONA);
		locationRDTO.setCodeNeighborhood("40");
		locationRDTO.setCodeTerritory("DI");
		locationRDTO.setCodeTypeRoad("C");
		installationRDTO.setLocationRDTO(locationRDTO);
		
		Mockito.when(validator.validateValueListGap(installationRDTO.getLocationRDTO().getCodeNeighborhood(),new Entity(ValueListConstants.CODE_NEIGHBORHOOD))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(installationRDTO.getLocationRDTO().getCodeTerritory(),new Entity(ValueListConstants.CODE_DISTRICT))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(installationRDTO.getLocationRDTO().getCodeTypeRoad(),new Entity(ValueListConstants.CODE_TYPE_ROAD))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(installationRDTO.getType(),new Entity(ValueListConstants.CODE_TYPE_INSTALLATION))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(installationRDTO.getCodeTypeTitularity(),new Entity(ValueListConstants.TITULARITY_TYPE))).thenReturn(false);
		
		validatorInstallation.validateUpdate(installationRDTO);
	}
	
	@Test
	public void caseValidateDelete() {
		
		installationRDTO.setType("PA");
		installationRDTO.setCodeTypeTitularity("M");
		
		LocationRDTO locationRDTO = new LocationRDTO();
		locationRDTO.setPopulation(ValidationConstants.POPULATION_BARCELONA);
		locationRDTO.setCodeNeighborhood("40");
		locationRDTO.setCodeTerritory("DI");
		locationRDTO.setCodeTypeRoad("C");
		installationRDTO.setLocationRDTO(locationRDTO);
		
		Mockito.when(validator.validateValueListGap(installationRDTO.getLocationRDTO().getCodeNeighborhood(),new Entity(ValueListConstants.CODE_NEIGHBORHOOD))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(installationRDTO.getLocationRDTO().getCodeTerritory(),new Entity(ValueListConstants.CODE_DISTRICT))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(installationRDTO.getLocationRDTO().getCodeTypeRoad(),new Entity(ValueListConstants.CODE_TYPE_ROAD))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(installationRDTO.getType(),new Entity(ValueListConstants.CODE_TYPE_INSTALLATION))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(installationRDTO.getCodeTypeTitularity(),new Entity(ValueListConstants.TITULARITY_TYPE))).thenReturn(true);
		
		Mockito.when(validator.validateExistsInstallationGap(installationRDTO.getCode(),true)).thenReturn(true);
		validatorInstallation.validateDelete(installationRDTO);
	}


	@Test
	public void caseValidateInsertExpense() {
		validatorInstallation.validateInsertExpense(installationRDTO);
	}
	
	@Test
	public void caseValidateInsertExpenseInstallationExistent() {
		Mockito.when(validator.validateExistsInstallationGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		Mockito.when(validator.validateTypeSubtypeExpenseGap(Mockito.anyString(),Mockito.anyString(),Mockito.anyString())).thenReturn(returnRDTO);
		
		validatorInstallation.validateInsertExpense(installationRDTO);
	}

	
	@Test
	public void caseInsertExpenseTypeNotExistent() {
		Mockito.when(validator.validateExistsInstallationGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		ReturnRDTO returnRDTOKO = new ReturnRDTO();
		returnRDTOKO.setCode("KO");
		Mockito.when(validator.validateTypeSubtypeExpenseGap(Mockito.anyString(),Mockito.anyString(),Mockito.anyString())).thenReturn(returnRDTOKO);
		
		validatorInstallation.validateInsertExpense(installationRDTO);
	}
	
	@Test
	public void caseValidateInsertExpenseZoneExistent() {
		ExpenseRDTO expense = new ExpenseRDTO();
		expense.setCodeZone("Z");
		installationRDTO.setExpenseRDTO(expense);
		Mockito.when(validator.validateExistsInstallationGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		
		Mockito.when(validator.validateValueList(installationRDTO.getExpenseRDTO().getCodeZone(), new Type(ValueListConstants.ZONE_INSTALLATION))).thenReturn(true);
		Mockito.when(validator.validateTypeSubtypeExpenseGap(Mockito.anyString(),Mockito.anyString(),Mockito.anyString())).thenReturn(returnRDTO);
		validatorInstallation.validateInsertExpense(installationRDTO);
	}
	
	@Test
	public void caseValidateInsertExpenseNotExistsTypeExpense() {
		ExpenseRDTO expense = new ExpenseRDTO();
		expense.setCodeZone("Z");
		installationRDTO.setExpenseRDTO(expense);
		Mockito.when(validator.validateExistsInstallationGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		
		Mockito.when(validator.validateValueList(installationRDTO.getExpenseRDTO().getCodeZone(), new Type(ValueListConstants.ZONE_INSTALLATION))).thenReturn(true);
		Mockito.when(validator.validateTypeSubtypeExpenseGap(Mockito.anyString(),Mockito.anyString(),Mockito.anyString())).thenReturn(returnRDTOKO);
		
		validatorInstallation.validateInsertExpense(installationRDTO);
	}
	
	
	@Test
	public void caseOkInsertAmortizationBase() {
		validatorInstallation.validateInsertAmortizationBase(installationRDTO);
	}
	
	@Test
	public void caseInsertAmortizationBaseInstallationExistent() {
		Mockito.when(validator.validateExistsInstallationGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		Mockito.when(validator.validateTypeSubtypeExpenseGap(Mockito.anyString(),Mockito.anyString(),Mockito.anyString())).thenReturn(returnRDTO);
		
		validatorInstallation.validateInsertAmortizationBase(installationRDTO);
	}
	
	@Test
	public void caseValidateInsertApportionment() {
		Mockito.when(validator.validateExistsInstallationGap(installationRDTO.getCode(),true)).thenReturn(true);
		Mockito.when(validator.validateListValueListGap(Mockito.anyList(),Mockito.any(Entity.class),Mockito.anyString(),Mockito.anyString())).thenReturn(returnRDTO);		
		validatorInstallation.validateInsertApportionment(installationRDTO);
	}
	
	
	@Test
	public void caseValidateInsertApportionmentNotExistsService() {
		Mockito.when(validator.validateExistsInstallationGap(installationRDTO.getCode(),true)).thenReturn(true);
		Mockito.when(validator.validateListValueListGap(Mockito.anyList(),Mockito.any(Entity.class),Mockito.anyString(),Mockito.anyString())).thenReturn(returnRDTOKO);		
		validatorInstallation.validateInsertApportionment(installationRDTO);
	}
	
	@Test
	public void caseValidateInsertApportionmentNotExistsInstallation() {
		Mockito.when(validator.validateExistsInstallationGap(installationRDTO.getCode(),true)).thenReturn(false);
		Mockito.when(validator.validateListValueListGap(Mockito.anyList(),Mockito.any(Entity.class),Mockito.anyString(),Mockito.anyString())).thenReturn(returnRDTOKO);		
		validatorInstallation.validateInsertApportionment(installationRDTO);
	}
	
	@Test
	public void caseValidateInsertActuation() {
		
		
		Mockito.when(validatorActuation.validateInsert(actuationRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateExistsInstallationGap(actuationRDTO.getCodeEntity(),false)).thenReturn(true);
		Mockito.when(validator.validateValueList(actuationRDTO.getTypeActuation(),new Type(ValueListConstants.TYPE_ACTUATION_UBICATION))).thenReturn(true);
		
		
		validatorInstallation.validateInsertActuation(actuationRDTO);
	}

	@Test
	public void caseValidateDeleteActuation() {
		
		
		Mockito.when(validatorActuation.validateDelete(actuationRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateExistsInstallationGap(actuationRDTO.getCodeEntity(),false)).thenReturn(false);
		
		
		
		validatorInstallation.validateDeleteActuation(actuationRDTO);
	}
}
