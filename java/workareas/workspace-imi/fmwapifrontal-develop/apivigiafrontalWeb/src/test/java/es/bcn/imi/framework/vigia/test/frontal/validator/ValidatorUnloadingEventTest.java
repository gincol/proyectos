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

import es.bcn.imi.framework.vigia.frontal.validator.ValidatorUnloadingEvent;
import es.bcn.imi.framework.vigia.frontal.validator.ValidatorUtils;
import es.bcn.imi.framework.vigia.frontal.web.rest.config.RestServerConfig;
import es.bcn.vigia.fmw.libcommons.constants.ValueListConstants;
import es.bcn.vigia.fmw.libcommons.valueobject.Entity;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.UnloadingEventRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.stub.UnloadingEventRDTOStub;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RestServerConfig.class })
@TestPropertySource(locations = {"classpath:app/config/application.properties"})
@WebAppConfiguration
public class ValidatorUnloadingEventTest {
	
	@Mock
	private ValidatorUtils validator;
	
	@InjectMocks
	private ValidatorUnloadingEvent validatorUnloadingEvent;
	
	private UnloadingEventRDTO  unloadingEventRDTO;
	
	private ReturnRDTO returnRDTO;
	
	private ReturnRDTO returnRDTOKO;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		unloadingEventRDTO = UnloadingEventRDTOStub.defaultOne();
		returnRDTO = ReturnRDTOStub.getSuccessMessage();
		returnRDTOKO = ReturnRDTOStub.getErrorMessage();
		
	}

	
	@Test
	public void caseOkValidateInsert() {
		
		Mockito.when(validator.validateCodeItinerary(Mockito.anyString())).thenReturn(returnRDTO);
		
		Mockito.when(validator.validateExistsItineraryGap(Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsPlanificatedItinerary(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsEventGap(Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateValueListGap(Mockito.anyString(),Mockito.any(Entity.class))).thenReturn(true);
		Mockito.when(validator.validateExistsGroupEventType(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsPlantWeighingGap(Mockito.anyString())).thenReturn(false);
		validatorUnloadingEvent.validateInsert(unloadingEventRDTO);
	}

	@Test
	public void caseValidateInsertInvalidCodeItinerary() {
		
		
		Mockito.when(validator.validateCodeItinerary(Mockito.anyString())).thenReturn(returnRDTOKO);
		
		Mockito.when(validator.validateExistsItineraryGap(Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsPlanificatedItinerary(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsEventGap(Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateValueListGap(Mockito.anyString(),Mockito.any(Entity.class))).thenReturn(true);
		Mockito.when(validator.validateExistsGroupEventType(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		validatorUnloadingEvent.validateInsert(unloadingEventRDTO);
	}
	
	@Test
	public void caseValidateInsertNotExistsItinerary() {
		
		
		Mockito.when(validator.validateCodeItinerary(Mockito.anyString())).thenReturn(returnRDTO);
		
		Mockito.when(validator.validateExistsItineraryGap(Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateExistsPlanificatedItinerary(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsEventGap(Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateValueListGap(Mockito.anyString(),Mockito.any(Entity.class))).thenReturn(true);
		Mockito.when(validator.validateExistsGroupEventType(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		validatorUnloadingEvent.validateInsert(unloadingEventRDTO);
	}
	
	@Test
	public void caseValidateInsertNotExistsPlanificatedItinerary() {
		
		
		Mockito.when(validator.validateCodeItinerary(Mockito.anyString())).thenReturn(returnRDTO);
		
		Mockito.when(validator.validateExistsItineraryGap(Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsPlanificatedItinerary(Mockito.anyString(),Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateExistsEventGap(Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateValueListGap(Mockito.anyString(),Mockito.any(Entity.class))).thenReturn(true);
		Mockito.when(validator.validateExistsGroupEventType(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		validatorUnloadingEvent.validateInsert(unloadingEventRDTO);
	}
	
	@Test
	public void caseValidateInsertExistsEventGap() {
		
		
		Mockito.when(validator.validateCodeItinerary(Mockito.anyString())).thenReturn(returnRDTO);
		
		Mockito.when(validator.validateExistsItineraryGap(Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsPlanificatedItinerary(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsEventGap(Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateValueListGap(Mockito.anyString(),Mockito.any(Entity.class))).thenReturn(true);
		Mockito.when(validator.validateExistsGroupEventType(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		validatorUnloadingEvent.validateInsert(unloadingEventRDTO);
	}
	
	@Test
	public void caseValidateInsertNotExistsTypeEvent() {
		
		unloadingEventRDTO.setCodeType("type");
		Mockito.when(validator.validateCodeItinerary(Mockito.anyString())).thenReturn(returnRDTO);
		
		Mockito.when(validator.validateExistsItineraryGap(Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsPlanificatedItinerary(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsEventGap(Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateValueListGap(unloadingEventRDTO.getCodeType(),new Entity(ValueListConstants.CODE_TYPE_EVENT))).thenReturn(false);
		Mockito.when(validator.validateExistsGroupEventType(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		validatorUnloadingEvent.validateInsert(unloadingEventRDTO);
	}
	
	@Test
	public void caseValidateInsertNotExistsGroupEventTypeTypeEvent() {
		
		unloadingEventRDTO.setCodeType("type");
		Mockito.when(validator.validateCodeItinerary(Mockito.anyString())).thenReturn(returnRDTO);
		
		Mockito.when(validator.validateExistsItineraryGap(Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsPlanificatedItinerary(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsEventGap(Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateValueListGap(unloadingEventRDTO.getCodeType(),new Entity(ValueListConstants.CODE_TYPE_EVENT))).thenReturn(true);
		Mockito.when(validator.validateExistsGroupEventType(Mockito.anyString(),Mockito.anyString())).thenReturn(false);
		validatorUnloadingEvent.validateInsert(unloadingEventRDTO);
	}
	
	@Test
	public void caseValidateInsertNotExistsReason() {
		
		unloadingEventRDTO.setCodeType("type");
		unloadingEventRDTO.setCodeReason("reason");
		Mockito.when(validator.validateCodeItinerary(Mockito.anyString())).thenReturn(returnRDTO);
		
		Mockito.when(validator.validateExistsItineraryGap(Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsPlanificatedItinerary(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsEventGap(Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateValueListGap(unloadingEventRDTO.getCodeType(),new Entity(ValueListConstants.CODE_TYPE_EVENT))).thenReturn(true);
		Mockito.when(validator.validateExistsGroupEventType(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateValueListGap(unloadingEventRDTO.getCodeReason(),new Entity(ValueListConstants.CODE_TYPE_REASON))).thenReturn(false);
		validatorUnloadingEvent.validateInsert(unloadingEventRDTO);
	}

	@Test
	public void caseValidateInsertNotExistsSubReason() {
		
		unloadingEventRDTO.setCodeType("type");
		unloadingEventRDTO.setCodeReason("reason");
		unloadingEventRDTO.setCodeSubReason("subreason");
		Mockito.when(validator.validateCodeItinerary(Mockito.anyString())).thenReturn(returnRDTO);
		
		Mockito.when(validator.validateExistsItineraryGap(Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsPlanificatedItinerary(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsEventGap(Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateValueListGap(unloadingEventRDTO.getCodeType(),new Entity(ValueListConstants.CODE_TYPE_EVENT))).thenReturn(true);
		Mockito.when(validator.validateExistsGroupEventType(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateValueListGap(unloadingEventRDTO.getCodeReason(),new Entity(ValueListConstants.CODE_TYPE_REASON))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(unloadingEventRDTO.getCodeReason(),new Entity(ValueListConstants.CODE_TYPE_SUBREASON))).thenReturn(false);
		validatorUnloadingEvent.validateInsert(unloadingEventRDTO);
	}
	
	@Test
	public void caseValidateInsertReasonNull() {
		
		unloadingEventRDTO.setCodeReason(null);
		Mockito.when(validator.validateCodeItinerary(Mockito.anyString())).thenReturn(returnRDTO);
		
		Mockito.when(validator.validateExistsItineraryGap(Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsPlanificatedItinerary(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsEventGap(Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateValueListGap(Mockito.anyString(),Mockito.any(Entity.class))).thenReturn(true);
		Mockito.when(validator.validateExistsGroupEventType(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		validatorUnloadingEvent.validateInsert(unloadingEventRDTO);
	}

	@Test
	public void caseValidateInsertReasonEmpty() {
		
		unloadingEventRDTO.setCodeReason("");
		Mockito.when(validator.validateCodeItinerary(Mockito.anyString())).thenReturn(returnRDTO);
		
		Mockito.when(validator.validateExistsItineraryGap(Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsPlanificatedItinerary(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsEventGap(Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateValueListGap(Mockito.anyString(),Mockito.any(Entity.class))).thenReturn(true);
		Mockito.when(validator.validateExistsGroupEventType(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		validatorUnloadingEvent.validateInsert(unloadingEventRDTO);
	}

	@Test
	public void caseValidateInsertExistsReasonSubReasonNull() {
		
		unloadingEventRDTO.setCodeSubReason(null);
		Mockito.when(validator.validateCodeItinerary(Mockito.anyString())).thenReturn(returnRDTO);
		
		Mockito.when(validator.validateExistsItineraryGap(Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsPlanificatedItinerary(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsEventGap(Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateValueListGap(Mockito.anyString(),Mockito.any(Entity.class))).thenReturn(true);
		Mockito.when(validator.validateExistsGroupEventType(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		validatorUnloadingEvent.validateInsert(unloadingEventRDTO);
	}

	@Test
	public void caseValidateInsertSubReasonEmpty() {
		
		unloadingEventRDTO.setCodeSubReason("");
		Mockito.when(validator.validateCodeItinerary(Mockito.anyString())).thenReturn(returnRDTO);
		Mockito.when(validator.validateExistsItineraryGap(Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsPlanificatedItinerary(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsEventGap(Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateValueListGap(Mockito.anyString(),Mockito.any(Entity.class))).thenReturn(false);
		Mockito.when(validator.validateExistsGroupEventType(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		validatorUnloadingEvent.validateInsert(unloadingEventRDTO);
	}
	
	@Test
	public void caseValidateInsertExistsPlant() {
	
		unloadingEventRDTO.setCodePlantWeighing("codi");
		Mockito.when(validator.validateCodeItinerary(Mockito.anyString())).thenReturn(returnRDTO);
		
		Mockito.when(validator.validateExistsItineraryGap(Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsPlanificatedItinerary(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsEventGap(Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateValueListGap(Mockito.anyString(),Mockito.any(Entity.class))).thenReturn(true);
		Mockito.when(validator.validateExistsGroupEventType(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsPlantWeighingGap(Mockito.anyString())).thenReturn(true);
		validatorUnloadingEvent.validateInsert(unloadingEventRDTO);
	}

	@Test
	public void caseValidateInsertNotExistsPlant() {
		
		
		unloadingEventRDTO.setCodePlantWeighing("codi");
		Mockito.when(validator.validateCodeItinerary(Mockito.anyString())).thenReturn(returnRDTO);
		
		Mockito.when(validator.validateExistsItineraryGap(Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsPlanificatedItinerary(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsEventGap(Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateValueListGap(Mockito.anyString(),Mockito.any(Entity.class))).thenReturn(true);
		Mockito.when(validator.validateExistsGroupEventType(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsPlantWeighingGap(Mockito.anyString())).thenReturn(false);
		validatorUnloadingEvent.validateInsert(unloadingEventRDTO);
	}

	@Test
	public void caseValidateInsertNotExistsNotEmptyPlant() {
		
		unloadingEventRDTO.setCodePlantWeighing("codi");
		
		Mockito.when(validator.validateCodeItinerary(Mockito.anyString())).thenReturn(returnRDTO);
		
		Mockito.when(validator.validateExistsItineraryGap(Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsPlanificatedItinerary(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsEventGap(Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateValueListGap(Mockito.anyString(),Mockito.any(Entity.class))).thenReturn(true);
		Mockito.when(validator.validateExistsGroupEventType(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsPlantWeighingGap(Mockito.anyString())).thenReturn(false);
		validatorUnloadingEvent.validateInsert(unloadingEventRDTO);
	}

	@Test
	public void caseValidateInsertExistsEventNotExistsPlant() {
		
		
		unloadingEventRDTO.setCodePlantWeighing("codi");
		Mockito.when(validator.validateCodeItinerary(Mockito.anyString())).thenReturn(returnRDTO);
		
		Mockito.when(validator.validateExistsItineraryGap(Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsPlanificatedItinerary(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsEventGap(Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateValueListGap(Mockito.anyString(),Mockito.any(Entity.class))).thenReturn(true);
		Mockito.when(validator.validateExistsGroupEventType(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsPlantWeighingGap(Mockito.anyString())).thenReturn(true);
		validatorUnloadingEvent.validateInsert(unloadingEventRDTO);
	}

}
