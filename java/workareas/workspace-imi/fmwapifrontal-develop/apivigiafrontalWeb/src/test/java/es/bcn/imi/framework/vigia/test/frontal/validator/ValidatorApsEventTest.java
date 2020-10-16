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

import es.bcn.imi.framework.vigia.frontal.validator.ValidatorAPSEvent;
import es.bcn.imi.framework.vigia.frontal.validator.ValidatorUtils;
import es.bcn.imi.framework.vigia.frontal.web.rest.config.RestServerConfig;
import es.bcn.vigia.fmw.libcommons.constants.ValueListConstants;
import es.bcn.vigia.fmw.libcommons.valueobject.Entity;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.APSEventRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.stub.APSEventRDTOStub;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RestServerConfig.class })
@TestPropertySource(locations = {"classpath:app/config/application.properties"})
@WebAppConfiguration
public class ValidatorApsEventTest {
	
	@Mock
	private ValidatorUtils validator;
	
	@InjectMocks
	private ValidatorAPSEvent validatorAPSEvent;
	
	private APSEventRDTO  apsEventRDTO;
	
	private ReturnRDTO returnRDTO;
	
	private ReturnRDTO returnRDTOKO;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		apsEventRDTO = APSEventRDTOStub.defaultOne();
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
		validatorAPSEvent.validateInsert(apsEventRDTO);
	}

	@Test
	public void caseValidateInsertInvalidCodeItinerary() {
		
		
		Mockito.when(validator.validateCodeItinerary(Mockito.anyString())).thenReturn(returnRDTOKO);
		
		Mockito.when(validator.validateExistsItineraryGap(Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsPlanificatedItinerary(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsEventGap(Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateValueListGap(Mockito.anyString(),Mockito.any(Entity.class))).thenReturn(true);
		Mockito.when(validator.validateExistsGroupEventType(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		validatorAPSEvent.validateInsert(apsEventRDTO);
	}
	
	@Test
	public void caseValidateInsertNotExistsItinerary() {
		
		
		Mockito.when(validator.validateCodeItinerary(Mockito.anyString())).thenReturn(returnRDTO);
		
		Mockito.when(validator.validateExistsItineraryGap(Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateExistsPlanificatedItinerary(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsEventGap(Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateValueListGap(Mockito.anyString(),Mockito.any(Entity.class))).thenReturn(true);
		Mockito.when(validator.validateExistsGroupEventType(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		validatorAPSEvent.validateInsert(apsEventRDTO);
	}
	
	@Test
	public void caseValidateInsertNotExistsPlanificatedItinerary() {
		
		
		Mockito.when(validator.validateCodeItinerary(Mockito.anyString())).thenReturn(returnRDTO);
		
		Mockito.when(validator.validateExistsItineraryGap(Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsPlanificatedItinerary(Mockito.anyString(),Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateExistsEventGap(Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateValueListGap(Mockito.anyString(),Mockito.any(Entity.class))).thenReturn(true);
		Mockito.when(validator.validateExistsGroupEventType(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		validatorAPSEvent.validateInsert(apsEventRDTO);
	}
	
	@Test
	public void caseValidateInsertExistsEventGap() {
		
		
		Mockito.when(validator.validateCodeItinerary(Mockito.anyString())).thenReturn(returnRDTO);
		
		Mockito.when(validator.validateExistsItineraryGap(Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsPlanificatedItinerary(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsEventGap(Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateValueListGap(Mockito.anyString(),Mockito.any(Entity.class))).thenReturn(true);
		Mockito.when(validator.validateExistsGroupEventType(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		validatorAPSEvent.validateInsert(apsEventRDTO);
	}
	
	@Test
	public void caseValidateInsertNotExistsTypeEvent() {
		
		apsEventRDTO.setCodeType("type");
		Mockito.when(validator.validateCodeItinerary(Mockito.anyString())).thenReturn(returnRDTO);
		
		Mockito.when(validator.validateExistsItineraryGap(Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsPlanificatedItinerary(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsEventGap(Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateValueListGap(apsEventRDTO.getCodeType(),new Entity(ValueListConstants.CODE_TYPE_EVENT))).thenReturn(false);
		Mockito.when(validator.validateExistsGroupEventType(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		validatorAPSEvent.validateInsert(apsEventRDTO);
	}
	
	@Test
	public void caseValidateInsertNotExistsGroupEventTypeTypeEvent() {
		
		apsEventRDTO.setCodeType("type");
		Mockito.when(validator.validateCodeItinerary(Mockito.anyString())).thenReturn(returnRDTO);
		
		Mockito.when(validator.validateExistsItineraryGap(Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsPlanificatedItinerary(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsEventGap(Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateValueListGap(apsEventRDTO.getCodeType(),new Entity(ValueListConstants.CODE_TYPE_EVENT))).thenReturn(true);
		Mockito.when(validator.validateExistsGroupEventType(Mockito.anyString(),Mockito.anyString())).thenReturn(false);
		validatorAPSEvent.validateInsert(apsEventRDTO);
	}
	
	@Test
	public void caseValidateInsertNotExistsReason() {
		
		apsEventRDTO.setCodeType("type");
		apsEventRDTO.setCodeReason("reason");
		Mockito.when(validator.validateCodeItinerary(Mockito.anyString())).thenReturn(returnRDTO);
		
		Mockito.when(validator.validateExistsItineraryGap(Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsPlanificatedItinerary(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsEventGap(Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateValueListGap(apsEventRDTO.getCodeType(),new Entity(ValueListConstants.CODE_TYPE_EVENT))).thenReturn(true);
		Mockito.when(validator.validateExistsGroupEventType(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateValueListGap(apsEventRDTO.getCodeReason(),new Entity(ValueListConstants.CODE_TYPE_REASON))).thenReturn(false);
		validatorAPSEvent.validateInsert(apsEventRDTO);
	}

	@Test
	public void caseValidateInsertNotExistsSubReason() {
		
		apsEventRDTO.setCodeType("type");
		apsEventRDTO.setCodeReason("reason");
		apsEventRDTO.setCodeSubReason("subreason");
		Mockito.when(validator.validateCodeItinerary(Mockito.anyString())).thenReturn(returnRDTO);
		
		Mockito.when(validator.validateExistsItineraryGap(Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsPlanificatedItinerary(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsEventGap(Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateValueListGap(apsEventRDTO.getCodeType(),new Entity(ValueListConstants.CODE_TYPE_EVENT))).thenReturn(true);
		Mockito.when(validator.validateExistsGroupEventType(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateValueListGap(apsEventRDTO.getCodeReason(),new Entity(ValueListConstants.CODE_TYPE_REASON))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(apsEventRDTO.getCodeReason(),new Entity(ValueListConstants.CODE_TYPE_SUBREASON))).thenReturn(false);
		validatorAPSEvent.validateInsert(apsEventRDTO);
	}
	

	@Test
	public void caseValidateInsertNotExistsRRMM() {
		
		apsEventRDTO.setCodeType("type");
		apsEventRDTO.setCodeReason("reason");
		apsEventRDTO.setCodeSubReason("subreason");
		apsEventRDTO.setCodeRRMM("codiRRMM");
		Mockito.when(validator.validateCodeItinerary(Mockito.anyString())).thenReturn(returnRDTO);
		
		Mockito.when(validator.validateExistsItineraryGap(Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsPlanificatedItinerary(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsEventGap(Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateValueListGap(apsEventRDTO.getCodeType(),new Entity(ValueListConstants.CODE_TYPE_EVENT))).thenReturn(true);
		Mockito.when(validator.validateExistsGroupEventType(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateValueListGap(apsEventRDTO.getCodeReason(),new Entity(ValueListConstants.CODE_TYPE_REASON))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(apsEventRDTO.getCodeSubReason(),new Entity(ValueListConstants.CODE_TYPE_SUBREASON))).thenReturn(true);
		Mockito.when(validator.validateExistsMaterialResourceGap(apsEventRDTO.getCodeRRMM())).thenReturn(false);
		validatorAPSEvent.validateInsert(apsEventRDTO);
	}
	
	@Test
	public void caseValidateInsertExistsRRMM() {
		
		apsEventRDTO.setCodeType("type");
		apsEventRDTO.setCodeReason("reason");
		apsEventRDTO.setCodeSubReason("subreason");
		apsEventRDTO.setCodeRRMM("codiRRMM");
		Mockito.when(validator.validateCodeItinerary(Mockito.anyString())).thenReturn(returnRDTO);
		
		Mockito.when(validator.validateExistsItineraryGap(Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsPlanificatedItinerary(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsEventGap(Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateValueListGap(apsEventRDTO.getCodeType(),new Entity(ValueListConstants.CODE_TYPE_EVENT))).thenReturn(true);
		Mockito.when(validator.validateExistsGroupEventType(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateValueListGap(apsEventRDTO.getCodeReason(),new Entity(ValueListConstants.CODE_TYPE_REASON))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(apsEventRDTO.getCodeSubReason(),new Entity(ValueListConstants.CODE_TYPE_SUBREASON))).thenReturn(true);
		Mockito.when(validator.validateExistsMaterialResourceGap(Mockito.anyString())).thenReturn(true);
		validatorAPSEvent.validateInsert(apsEventRDTO);
	}
	@Test
	public void caseValidateInsertEmptyRRMM() {
		
		apsEventRDTO.setCodeType("type");
		apsEventRDTO.setCodeReason("reason");
		apsEventRDTO.setCodeSubReason("subreason");
		apsEventRDTO.setCodeRRMM(null);
		Mockito.when(validator.validateCodeItinerary(Mockito.anyString())).thenReturn(returnRDTO);
		
		Mockito.when(validator.validateExistsItineraryGap(Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsPlanificatedItinerary(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsEventGap(Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateValueListGap(apsEventRDTO.getCodeType(),new Entity(ValueListConstants.CODE_TYPE_EVENT))).thenReturn(true);
		Mockito.when(validator.validateExistsGroupEventType(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateValueListGap(apsEventRDTO.getCodeReason(),new Entity(ValueListConstants.CODE_TYPE_REASON))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(apsEventRDTO.getCodeSubReason(),new Entity(ValueListConstants.CODE_TYPE_SUBREASON))).thenReturn(true);
		Mockito.when(validator.validateExistsMaterialResourceGap(Mockito.anyString())).thenReturn(false);
		validatorAPSEvent.validateInsert(apsEventRDTO);
	}
}
