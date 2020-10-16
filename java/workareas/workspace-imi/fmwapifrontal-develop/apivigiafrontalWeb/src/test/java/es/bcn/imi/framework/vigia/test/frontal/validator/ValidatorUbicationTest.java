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

import es.bcn.imi.framework.vigia.frontal.validator.ValidatorDocumentarySupport;
import es.bcn.imi.framework.vigia.frontal.validator.ValidatorUbication;
import es.bcn.imi.framework.vigia.frontal.validator.ValidatorUtils;
import es.bcn.imi.framework.vigia.frontal.web.rest.config.RestServerConfig;
import es.bcn.vigia.fmw.libcommons.constants.ValidationConstants;
import es.bcn.vigia.fmw.libcommons.constants.ValueListConstants;
import es.bcn.vigia.fmw.libcommons.valueobject.Entity;
import es.bcn.vigia.fmw.libcommons.valueobject.Type;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.DocumentarySupportRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.LabelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.LocationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.UbicationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.DocumentarySupportRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.UbicationRDTOStub;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RestServerConfig.class })
@TestPropertySource(locations = {"classpath:app/config/application.properties"})
@SuppressWarnings("unchecked")
@WebAppConfiguration
public class ValidatorUbicationTest {
	
	@Mock
	private ValidatorUtils validator;

	@Mock
	private ValidatorDocumentarySupport validatorDocumentarySupport;

	@InjectMocks
	private ValidatorUbication validatorUbication;
	
	private UbicationRDTO ubicationRDTO;
	
	ReturnRDTO returnRDTO;
	
	ReturnRDTO returnRDTOKO;

	private DocumentarySupportRDTO documentarySupportRDTO;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		ubicationRDTO = UbicationRDTOStub.defaultOne();
		returnRDTO = ReturnRDTOStub.getSuccessMessage();
		returnRDTOKO = new ReturnRDTO();
		returnRDTOKO.setCode("KO");
		documentarySupportRDTO = DocumentarySupportRDTOStub.defaultOne();
	}

	@Test
	public void caseOkValidateInsert() {
		
		ubicationRDTO.setType("TIPUS_UBICACIO");
		ubicationRDTO.setRefTypeEnvironment("TIPUS_REFERENCIA_RESPECTE_ENTORN");
		ubicationRDTO.setCodeState("STATUS");
		LocationRDTO locationRDTO = new LocationRDTO();
		locationRDTO.setPopulation(ValidationConstants.POPULATION_BARCELONA);
		locationRDTO.setCodeNeighborhood("BARRI");
		locationRDTO.setCodeTerritory("TERRITORI");
		locationRDTO.setCodeTypeRoad("TYPE ROAD");
		ubicationRDTO.setLocationRDTO(locationRDTO);
		
		Mockito.when(validator.validateValueList(ubicationRDTO.getRefTypeEnvironment(),new Type(ValueListConstants.ENVIRONMENT_REF_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(ubicationRDTO.getType(), new Entity(ValueListConstants.UBICATION_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(ubicationRDTO.getCodeState(),new Entity(ValueListConstants.UBICATION_STATUS))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(ubicationRDTO.getLocationRDTO().getCodeTerritory(),new Entity(ValueListConstants.CODE_DISTRICT))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(ubicationRDTO.getLocationRDTO().getCodeNeighborhood(),new Entity(ValueListConstants.CODE_NEIGHBORHOOD))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(ubicationRDTO.getLocationRDTO().getCodeTypeRoad(),new Entity(ValueListConstants.CODE_TYPE_ROAD))).thenReturn(true);
		
		Mockito.when(validator.validateListValueListGap(Mockito.anyList(),Mockito.any(Entity.class),Mockito.anyString(),Mockito.anyString())).thenReturn(returnRDTO);
		
		
		validatorUbication.validateInsert(ubicationRDTO);
	}

	
	@Test
	public void caseValidateInsertExistsUbication() {
		
		ubicationRDTO.setType("TIPUS_UBICACIO");
		ubicationRDTO.setRefTypeEnvironment("TIPUS_REFERENCIA_RESPECTE_ENTORN");
		ubicationRDTO.setCodeState("STATUS");
		LocationRDTO locationRDTO = new LocationRDTO();
		locationRDTO.setPopulation(ValidationConstants.POPULATION_BARCELONA);
		locationRDTO.setCodeNeighborhood("BARRI");
		locationRDTO.setCodeTerritory("TERRITORI");
		locationRDTO.setCodeTypeRoad("TYPE ROAD");
		ubicationRDTO.setLocationRDTO(locationRDTO);
		
		LabelRDTO label = new LabelRDTO();
		label.setValue("LABEL");
		
		ubicationRDTO.getListLabelRDTO().add(label);

		
		Mockito.when(validator.validateValueList(ubicationRDTO.getRefTypeEnvironment(),new Type(ValueListConstants.ENVIRONMENT_REF_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(ubicationRDTO.getType(), new Entity(ValueListConstants.UBICATION_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(ubicationRDTO.getCodeState(),new Entity(ValueListConstants.UBICATION_STATUS))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(ubicationRDTO.getLocationRDTO().getCodeTerritory(),new Entity(ValueListConstants.CODE_DISTRICT))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(ubicationRDTO.getLocationRDTO().getCodeNeighborhood(),new Entity(ValueListConstants.CODE_NEIGHBORHOOD))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(ubicationRDTO.getLocationRDTO().getCodeTypeRoad(),new Entity(ValueListConstants.CODE_TYPE_ROAD))).thenReturn(true);
		
		Mockito.when(validator.validateListValueListGap(Mockito.anyList(),Mockito.any(Entity.class),Mockito.anyString(),Mockito.anyString())).thenReturn(returnRDTO);
		Mockito.when(validator.validateExistsUbicationGap(Mockito.anyString(), Mockito.anyBoolean())).thenReturn(true);
		
		validatorUbication.validateInsert(ubicationRDTO);
	}
	@Test
	public void caseValidateInsertNotValidLabels() {
		
		ubicationRDTO.setType("TIPUS_UBICACIO");
		ubicationRDTO.setRefTypeEnvironment("TIPUS_REFERENCIA_RESPECTE_ENTORN");
		ubicationRDTO.setCodeState("STATUS");
		LocationRDTO locationRDTO = new LocationRDTO();
		locationRDTO.setPopulation(ValidationConstants.POPULATION_BARCELONA);
		locationRDTO.setCodeNeighborhood("BARRI");
		locationRDTO.setCodeTerritory("TERRITORI");
		locationRDTO.setCodeTypeRoad("TYPE ROAD");
		ubicationRDTO.setLocationRDTO(locationRDTO);
		
		LabelRDTO label = new LabelRDTO();
		label.setValue("LABEL");
		
		ubicationRDTO.getListLabelRDTO().add(label);
		
		Mockito.when(validator.validateValueList(ubicationRDTO.getRefTypeEnvironment(),new Type(ValueListConstants.ENVIRONMENT_REF_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(ubicationRDTO.getType(), new Entity(ValueListConstants.UBICATION_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(ubicationRDTO.getCodeState(),new Entity(ValueListConstants.UBICATION_STATUS))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(ubicationRDTO.getLocationRDTO().getCodeTerritory(),new Entity(ValueListConstants.CODE_DISTRICT))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(ubicationRDTO.getLocationRDTO().getCodeNeighborhood(),new Entity(ValueListConstants.CODE_NEIGHBORHOOD))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(ubicationRDTO.getLocationRDTO().getCodeTypeRoad(),new Entity(ValueListConstants.CODE_TYPE_ROAD))).thenReturn(true);
		
		Mockito.when(validator.validateListValueListGap(Mockito.anyList(),Mockito.any(Entity.class),Mockito.anyString(),Mockito.anyString())).thenReturn(returnRDTOKO);
		
		validatorUbication.validateInsert(ubicationRDTO);
	}
	@Test
	public void caseValidateInsertNotValidTypeRoad() {
		
		ubicationRDTO.setType("TIPUS_UBICACIO");
		ubicationRDTO.setRefTypeEnvironment("TIPUS_REFERENCIA_RESPECTE_ENTORN");
		ubicationRDTO.setCodeState("STATUS");
		LocationRDTO locationRDTO = new LocationRDTO();
		locationRDTO.setPopulation(ValidationConstants.POPULATION_BARCELONA);
		locationRDTO.setCodeNeighborhood("BARRI");
		locationRDTO.setCodeTerritory("TERRITORI");
		locationRDTO.setCodeTypeRoad("TYPE ROAD");
		ubicationRDTO.setLocationRDTO(locationRDTO);
		
		
		Mockito.when(validator.validateValueList(ubicationRDTO.getRefTypeEnvironment(),new Type(ValueListConstants.ENVIRONMENT_REF_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(ubicationRDTO.getType(), new Entity(ValueListConstants.UBICATION_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(ubicationRDTO.getCodeState(),new Entity(ValueListConstants.UBICATION_STATUS))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(ubicationRDTO.getLocationRDTO().getCodeTerritory(),new Entity(ValueListConstants.CODE_DISTRICT))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(ubicationRDTO.getLocationRDTO().getCodeNeighborhood(),new Entity(ValueListConstants.CODE_NEIGHBORHOOD))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(ubicationRDTO.getLocationRDTO().getCodeTypeRoad(),new Entity(ValueListConstants.CODE_TYPE_ROAD))).thenReturn(false);
	
		
		validatorUbication.validateInsert(ubicationRDTO);
	}
	
	@Test
	public void caseValidateInsertNotValidNeighborhood() {
		
		ubicationRDTO.setType("TIPUS_UBICACIO");
		ubicationRDTO.setRefTypeEnvironment("TIPUS_REFERENCIA_RESPECTE_ENTORN");
		ubicationRDTO.setCodeState("STATUS");
		LocationRDTO locationRDTO = new LocationRDTO();
		locationRDTO.setPopulation(ValidationConstants.POPULATION_BARCELONA);
		locationRDTO.setCodeNeighborhood("BARRI");
		locationRDTO.setCodeTerritory("TERRITORI");
		locationRDTO.setCodeTypeRoad("TYPE ROAD");
		ubicationRDTO.setLocationRDTO(locationRDTO);
		
		
		Mockito.when(validator.validateValueList(ubicationRDTO.getRefTypeEnvironment(),new Type(ValueListConstants.ENVIRONMENT_REF_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(ubicationRDTO.getType(), new Entity(ValueListConstants.UBICATION_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(ubicationRDTO.getCodeState(),new Entity(ValueListConstants.UBICATION_STATUS))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(ubicationRDTO.getLocationRDTO().getCodeTerritory(),new Entity(ValueListConstants.CODE_DISTRICT))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(ubicationRDTO.getLocationRDTO().getCodeNeighborhood(),new Entity(ValueListConstants.CODE_NEIGHBORHOOD))).thenReturn(false);

		validatorUbication.validateInsert(ubicationRDTO);
	}

	@Test
	public void caseValidateInsertNotValidTerritory() {
		
		ubicationRDTO.setType("TIPUS_UBICACIO");
		ubicationRDTO.setRefTypeEnvironment("TIPUS_REFERENCIA_RESPECTE_ENTORN");
		ubicationRDTO.setCodeState("STATUS");
		LocationRDTO locationRDTO = new LocationRDTO();
		locationRDTO.setPopulation(ValidationConstants.POPULATION_BARCELONA);
		locationRDTO.setCodeNeighborhood("BARRI");
		locationRDTO.setCodeTerritory("TERRITORI");
		locationRDTO.setCodeTypeRoad("TYPE ROAD");
		ubicationRDTO.setLocationRDTO(locationRDTO);
		
		
		Mockito.when(validator.validateValueList(ubicationRDTO.getRefTypeEnvironment(),new Type(ValueListConstants.ENVIRONMENT_REF_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(ubicationRDTO.getType(), new Entity(ValueListConstants.UBICATION_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(ubicationRDTO.getCodeState(),new Entity(ValueListConstants.UBICATION_STATUS))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(ubicationRDTO.getLocationRDTO().getCodeTerritory(),new Entity(ValueListConstants.CODE_DISTRICT))).thenReturn(false);
	

		validatorUbication.validateInsert(ubicationRDTO);
	}
	
	
	@Test
	public void caseValidateInsertNotValidStatus() {
		
		ubicationRDTO.setType("TIPUS_UBICACIO");
		ubicationRDTO.setRefTypeEnvironment("TIPUS_REFERENCIA_RESPECTE_ENTORN");
		ubicationRDTO.setCodeState("STATUS");
		LocationRDTO locationRDTO = new LocationRDTO();
		locationRDTO.setCodeNeighborhood("BARRI");
		locationRDTO.setCodeTerritory("TERRITORI");
		locationRDTO.setCodeTypeRoad("TYPE ROAD");
		ubicationRDTO.setLocationRDTO(locationRDTO);
		
		
		Mockito.when(validator.validateValueList(ubicationRDTO.getRefTypeEnvironment(),new Type(ValueListConstants.ENVIRONMENT_REF_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(ubicationRDTO.getType(), new Entity(ValueListConstants.UBICATION_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(ubicationRDTO.getCodeState(),new Entity(ValueListConstants.UBICATION_STATUS))).thenReturn(false);
	
	

		validatorUbication.validateInsert(ubicationRDTO);
	}	


	@Test
	public void caseValidateInsertNotValidType() {
		
		ubicationRDTO.setType("TIPUS_UBICACIO");
		ubicationRDTO.setRefTypeEnvironment("TIPUS_REFERENCIA_RESPECTE_ENTORN");
		ubicationRDTO.setCodeState("STATUS");
		LocationRDTO locationRDTO = new LocationRDTO();
		locationRDTO.setCodeNeighborhood("BARRI");
		locationRDTO.setCodeTerritory("TERRITORI");
		locationRDTO.setCodeTypeRoad("TYPE ROAD");
		ubicationRDTO.setLocationRDTO(locationRDTO);
		
		
		Mockito.when(validator.validateValueList(ubicationRDTO.getRefTypeEnvironment(),new Type(ValueListConstants.ENVIRONMENT_REF_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(ubicationRDTO.getType(), new Entity(ValueListConstants.UBICATION_TYPE))).thenReturn(false);
		

		validatorUbication.validateInsert(ubicationRDTO);
	}	

	@Test
	public void caseValidateInsertNotRefTypeEnvironment() {
		
		ubicationRDTO.setType("TIPUS_UBICACIO");
		ubicationRDTO.setRefTypeEnvironment("TIPUS_REFERENCIA_RESPECTE_ENTORN");
		ubicationRDTO.setCodeState("STATUS");
		LocationRDTO locationRDTO = new LocationRDTO();
		locationRDTO.setCodeNeighborhood("BARRI");
		locationRDTO.setCodeTerritory("TERRITORI");
		locationRDTO.setCodeTypeRoad("TYPE ROAD");
		ubicationRDTO.setLocationRDTO(locationRDTO);
		
		
		Mockito.when(validator.validateValueList(ubicationRDTO.getRefTypeEnvironment(),new Type(ValueListConstants.ENVIRONMENT_REF_TYPE))).thenReturn(false);
		
		validatorUbication.validateInsert(ubicationRDTO);
	}

	@Test
	public void caseOkValidateUpdate() {
		
		ubicationRDTO.setType("TIPUS_UBICACIO");
		ubicationRDTO.setRefTypeEnvironment("TIPUS_REFERENCIA_RESPECTE_ENTORN");
		ubicationRDTO.setCodeState("STATUS");
		LocationRDTO locationRDTO = new LocationRDTO();
		locationRDTO.setPopulation(ValidationConstants.POPULATION_BARCELONA);
		locationRDTO.setCodeNeighborhood("BARRI");
		locationRDTO.setCodeTerritory("TERRITORI");
		locationRDTO.setCodeTypeRoad("TYPE ROAD");
		ubicationRDTO.setLocationRDTO(locationRDTO);

		LabelRDTO label = new LabelRDTO();
		label.setValue("LABEL");
		
		ubicationRDTO.getListLabelRDTO().add(label);

		Mockito.when(validator.validateValueList(ubicationRDTO.getRefTypeEnvironment(),new Type(ValueListConstants.ENVIRONMENT_REF_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(ubicationRDTO.getType(), new Entity(ValueListConstants.UBICATION_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(ubicationRDTO.getCodeState(),new Entity(ValueListConstants.UBICATION_STATUS))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(ubicationRDTO.getLocationRDTO().getCodeTerritory(),new Entity(ValueListConstants.CODE_DISTRICT))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(ubicationRDTO.getLocationRDTO().getCodeNeighborhood(),new Entity(ValueListConstants.CODE_NEIGHBORHOOD))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(ubicationRDTO.getLocationRDTO().getCodeTypeRoad(),new Entity(ValueListConstants.CODE_TYPE_ROAD))).thenReturn(true);
		
		Mockito.when(validator.validateListValueListGap(Mockito.anyList(),Mockito.any(Entity.class),Mockito.anyString(),Mockito.anyString())).thenReturn(returnRDTO);
		Mockito.when(validator.validateExistsUbicationGap(Mockito.anyString(), Mockito.anyBoolean())).thenReturn(true);		
		Mockito.when(validator.validateCompatibilityLogicalModelsUbication(Mockito.anyString(), Mockito.anyString())).thenReturn(true);		

		validatorUbication.validateUpdate(ubicationRDTO);
	}

	@Test
	public void caseValidateUpdateInvalidUbicationType() {
		
		ubicationRDTO.setType("TIPUS_UBICACIO");
		ubicationRDTO.setRefTypeEnvironment("TIPUS_REFERENCIA_RESPECTE_ENTORN");
		ubicationRDTO.setCodeState("STATUS");
		LocationRDTO locationRDTO = new LocationRDTO();
		locationRDTO.setCodeNeighborhood("BARRI");
		locationRDTO.setCodeTerritory("TERRITORI");
		locationRDTO.setCodeTypeRoad("TYPE ROAD");
		ubicationRDTO.setLocationRDTO(locationRDTO);

		LabelRDTO label = new LabelRDTO();
		label.setValue("LABEL");
		
		ubicationRDTO.getListLabelRDTO().add(label);

		Mockito.when(validator.validateValueList(ubicationRDTO.getRefTypeEnvironment(),new Type(ValueListConstants.ENVIRONMENT_REF_TYPE))).thenReturn(false);
		
		validatorUbication.validateUpdate(ubicationRDTO);
	}

	@Test
	public void caseValidateUpdateNotExistsUbication() {
		
		ubicationRDTO.setType("TIPUS_UBICACIO");
		ubicationRDTO.setRefTypeEnvironment("TIPUS_REFERENCIA_RESPECTE_ENTORN");
		ubicationRDTO.setCodeState("STATUS");
		LocationRDTO locationRDTO = new LocationRDTO();
		locationRDTO.setPopulation(ValidationConstants.POPULATION_BARCELONA);
		locationRDTO.setCodeNeighborhood("BARRI");
		locationRDTO.setCodeTerritory("TERRITORI");
		locationRDTO.setCodeTypeRoad("TYPE ROAD");
		ubicationRDTO.setLocationRDTO(locationRDTO);
		
		LabelRDTO label = new LabelRDTO();
		label.setValue("LABEL");
		
		ubicationRDTO.getListLabelRDTO().add(label);

		
		Mockito.when(validator.validateValueList(ubicationRDTO.getRefTypeEnvironment(),new Type(ValueListConstants.ENVIRONMENT_REF_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(ubicationRDTO.getType(), new Entity(ValueListConstants.UBICATION_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(ubicationRDTO.getCodeState(),new Entity(ValueListConstants.UBICATION_STATUS))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(ubicationRDTO.getLocationRDTO().getCodeTerritory(),new Entity(ValueListConstants.CODE_DISTRICT))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(ubicationRDTO.getLocationRDTO().getCodeNeighborhood(),new Entity(ValueListConstants.CODE_NEIGHBORHOOD))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(ubicationRDTO.getLocationRDTO().getCodeTypeRoad(),new Entity(ValueListConstants.CODE_TYPE_ROAD))).thenReturn(true);
		
		Mockito.when(validator.validateListValueListGap(Mockito.anyList(),Mockito.any(Entity.class),Mockito.anyString(),Mockito.anyString())).thenReturn(returnRDTO);
		Mockito.when(validator.validateExistsUbicationGap(Mockito.anyString(), Mockito.anyBoolean())).thenReturn(false);
		
		validatorUbication.validateUpdate(ubicationRDTO);
	}

	@Test
	public void caseValidateUpdateNotValidCompatibility() {
		
		ubicationRDTO.setType("TIPUS_UBICACIO");
		ubicationRDTO.setRefTypeEnvironment("TIPUS_REFERENCIA_RESPECTE_ENTORN");
		ubicationRDTO.setCodeState("STATUS");
		LocationRDTO locationRDTO = new LocationRDTO();
		locationRDTO.setPopulation(ValidationConstants.POPULATION_BARCELONA);
		locationRDTO.setCodeNeighborhood("BARRI");
		locationRDTO.setCodeTerritory("TERRITORI");
		locationRDTO.setCodeTypeRoad("TYPE ROAD");
		ubicationRDTO.setLocationRDTO(locationRDTO);
		
		LabelRDTO label = new LabelRDTO();
		label.setValue("LABEL");
		
		ubicationRDTO.getListLabelRDTO().add(label);

		
		Mockito.when(validator.validateValueList(ubicationRDTO.getRefTypeEnvironment(),new Type(ValueListConstants.ENVIRONMENT_REF_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(ubicationRDTO.getType(), new Entity(ValueListConstants.UBICATION_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(ubicationRDTO.getCodeState(),new Entity(ValueListConstants.UBICATION_STATUS))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(ubicationRDTO.getLocationRDTO().getCodeTerritory(),new Entity(ValueListConstants.CODE_DISTRICT))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(ubicationRDTO.getLocationRDTO().getCodeNeighborhood(),new Entity(ValueListConstants.CODE_NEIGHBORHOOD))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(ubicationRDTO.getLocationRDTO().getCodeTypeRoad(),new Entity(ValueListConstants.CODE_TYPE_ROAD))).thenReturn(true);
		Mockito.when(validator.validateListValueListGap(Mockito.anyList(),Mockito.any(Entity.class),Mockito.anyString(),Mockito.anyString())).thenReturn(returnRDTO);
		Mockito.when(validator.validateExistsUbicationGap(Mockito.anyString(), Mockito.anyBoolean())).thenReturn(true);
		Mockito.when(validator.validateCompatibilityLogicalModelsUbication(Mockito.anyString(), Mockito.anyString())).thenReturn(false);
		validatorUbication.validateUpdate(ubicationRDTO);
	}

	@Test
	public void caseOkValidateDelete() {
		
		ubicationRDTO.setType("TIPUS_UBICACIO");
		ubicationRDTO.setRefTypeEnvironment("TIPUS_REFERENCIA_RESPECTE_ENTORN");
		ubicationRDTO.setCodeState("STATUS");
		LocationRDTO locationRDTO = new LocationRDTO();
		locationRDTO.setPopulation(ValidationConstants.POPULATION_BARCELONA);
		locationRDTO.setCodeNeighborhood("BARRI");
		locationRDTO.setCodeTerritory("TERRITORI");
		locationRDTO.setCodeTypeRoad("TYPE ROAD");
		ubicationRDTO.setLocationRDTO(locationRDTO);

		LabelRDTO label = new LabelRDTO();
		label.setValue("LABEL");
		
		ubicationRDTO.getListLabelRDTO().add(label);

		Mockito.when(validator.validateValueList(ubicationRDTO.getRefTypeEnvironment(),new Type(ValueListConstants.ENVIRONMENT_REF_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(ubicationRDTO.getType(), new Entity(ValueListConstants.UBICATION_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(ubicationRDTO.getCodeState(),new Entity(ValueListConstants.UBICATION_STATUS))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(ubicationRDTO.getLocationRDTO().getCodeTerritory(),new Entity(ValueListConstants.CODE_DISTRICT))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(ubicationRDTO.getLocationRDTO().getCodeNeighborhood(),new Entity(ValueListConstants.CODE_NEIGHBORHOOD))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(ubicationRDTO.getLocationRDTO().getCodeTypeRoad(),new Entity(ValueListConstants.CODE_TYPE_ROAD))).thenReturn(true);
		
		Mockito.when(validator.validateListValueListGap(Mockito.anyList(),Mockito.any(Entity.class),Mockito.anyString(),Mockito.anyString())).thenReturn(returnRDTO);
		Mockito.when(validator.validateExistsUbicationGap(Mockito.anyString(), Mockito.anyBoolean())).thenReturn(true);
		
		validatorUbication.validateDelete(ubicationRDTO);
	}


	@Test
	public void caseValidateDeleteNotExistLogicalModel() {
		
		ubicationRDTO.setType("TIPUS_UBICACIO");
		ubicationRDTO.setRefTypeEnvironment("TIPUS_REFERENCIA_RESPECTE_ENTORN");
		ubicationRDTO.setCodeState("STATUS");
		LocationRDTO locationRDTO = new LocationRDTO();
		locationRDTO.setPopulation(ValidationConstants.POPULATION_BARCELONA);
		locationRDTO.setCodeNeighborhood("BARRI");
		locationRDTO.setCodeTerritory("TERRITORI");
		locationRDTO.setCodeTypeRoad("TYPE ROAD");
		ubicationRDTO.setLocationRDTO(locationRDTO);

		LabelRDTO label = new LabelRDTO();
		label.setValue("LABEL");
		
		ubicationRDTO.getListLabelRDTO().add(label);

		Mockito.when(validator.validateValueList(ubicationRDTO.getRefTypeEnvironment(),new Type(ValueListConstants.ENVIRONMENT_REF_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(ubicationRDTO.getType(), new Entity(ValueListConstants.UBICATION_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(ubicationRDTO.getCodeState(),new Entity(ValueListConstants.UBICATION_STATUS))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(ubicationRDTO.getLocationRDTO().getCodeTerritory(),new Entity(ValueListConstants.CODE_DISTRICT))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(ubicationRDTO.getLocationRDTO().getCodeNeighborhood(),new Entity(ValueListConstants.CODE_NEIGHBORHOOD))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(ubicationRDTO.getLocationRDTO().getCodeTypeRoad(),new Entity(ValueListConstants.CODE_TYPE_ROAD))).thenReturn(true);
		Mockito.when(validator.validateListValueListGap(Mockito.anyList(),Mockito.any(Entity.class),Mockito.anyString(),Mockito.anyString())).thenReturn(returnRDTO);
		Mockito.when(validator.validateExistsUbicationGap(Mockito.anyString(), Mockito.anyBoolean())).thenReturn(true);
		Mockito.when(validator.validateExistsLogicalModelUbication(Mockito.anyString())).thenReturn(true);
		validatorUbication.validateDelete(ubicationRDTO);
	}

	@Test
	public void caseValidateDeleteInvalidUbicationType() {
		
		ubicationRDTO.setType("TIPUS_UBICACIO");
		ubicationRDTO.setRefTypeEnvironment("TIPUS_REFERENCIA_RESPECTE_ENTORN");
		ubicationRDTO.setCodeState("STATUS");
		LocationRDTO locationRDTO = new LocationRDTO();
		locationRDTO.setCodeNeighborhood("BARRI");
		locationRDTO.setCodeTerritory("TERRITORI");
		locationRDTO.setCodeTypeRoad("TYPE ROAD");
		ubicationRDTO.setLocationRDTO(locationRDTO);

		LabelRDTO label = new LabelRDTO();
		label.setValue("LABEL");
		
		ubicationRDTO.getListLabelRDTO().add(label);

		Mockito.when(validator.validateValueList(ubicationRDTO.getRefTypeEnvironment(),new Type(ValueListConstants.ENVIRONMENT_REF_TYPE))).thenReturn(false);
		
		validatorUbication.validateDelete(ubicationRDTO);
	}

	@Test
	public void caseValidateDeleteNotExistsUbication() {
		
		ubicationRDTO.setType("TIPUS_UBICACIO");
		ubicationRDTO.setRefTypeEnvironment("TIPUS_REFERENCIA_RESPECTE_ENTORN");
		ubicationRDTO.setCodeState("STATUS");
		LocationRDTO locationRDTO = new LocationRDTO();
		locationRDTO.setPopulation(ValidationConstants.POPULATION_BARCELONA);
		locationRDTO.setCodeNeighborhood("BARRI");
		locationRDTO.setCodeTerritory("TERRITORI");
		locationRDTO.setCodeTypeRoad("TYPE ROAD");
		ubicationRDTO.setLocationRDTO(locationRDTO);
		
		LabelRDTO label = new LabelRDTO();
		label.setValue("LABEL");
		
		ubicationRDTO.getListLabelRDTO().add(label);

		
		Mockito.when(validator.validateValueList(ubicationRDTO.getRefTypeEnvironment(),new Type(ValueListConstants.ENVIRONMENT_REF_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(ubicationRDTO.getType(), new Entity(ValueListConstants.UBICATION_TYPE))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(ubicationRDTO.getCodeState(),new Entity(ValueListConstants.UBICATION_STATUS))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(ubicationRDTO.getLocationRDTO().getCodeTerritory(),new Entity(ValueListConstants.CODE_DISTRICT))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(ubicationRDTO.getLocationRDTO().getCodeNeighborhood(),new Entity(ValueListConstants.CODE_NEIGHBORHOOD))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(ubicationRDTO.getLocationRDTO().getCodeTypeRoad(),new Entity(ValueListConstants.CODE_TYPE_ROAD))).thenReturn(true);
		
		Mockito.when(validator.validateListValueListGap(Mockito.anyList(),Mockito.any(Entity.class),Mockito.anyString(),Mockito.anyString())).thenReturn(returnRDTO);
		Mockito.when(validator.validateExistsUbicationGap(Mockito.anyString(), Mockito.anyBoolean())).thenReturn(false);
		
		validatorUbication.validateDelete(ubicationRDTO);
	}
	
	@Test
	public void caseOkValidateInsertDocumentarySupport() {
		
		Mockito.when(validator.validateExistsUbicationGap(documentarySupportRDTO.getCode(),true)).thenReturn(true);
		Mockito.when(validatorDocumentarySupport.validateInsert(Mockito.any(documentarySupportRDTO.getClass()))).thenReturn(returnRDTO);		
		validatorUbication.validateInsertDocumentarySupport(documentarySupportRDTO);
	}
	
	@Test
	public void caseOkValidateInsertDocumentarySupportNotExitsUbication() {
		
		Mockito.when(validator.validateExistsUbicationGap(documentarySupportRDTO.getCode(),true)).thenReturn(false);
		Mockito.when(validatorDocumentarySupport.validateInsert(Mockito.any(documentarySupportRDTO.getClass()))).thenReturn(returnRDTO);
		validatorUbication.validateInsertDocumentarySupport(documentarySupportRDTO);
	}

}
