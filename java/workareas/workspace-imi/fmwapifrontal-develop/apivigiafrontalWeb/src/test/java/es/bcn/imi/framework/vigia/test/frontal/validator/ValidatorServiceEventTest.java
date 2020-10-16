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

import es.bcn.imi.framework.vigia.frontal.validator.ValidatorServiceEvent;
import es.bcn.imi.framework.vigia.frontal.validator.ValidatorUtils;
import es.bcn.imi.framework.vigia.frontal.web.rest.config.RestServerConfig;
import es.bcn.vigia.fmw.libcommons.constants.ValueListConstants;
import es.bcn.vigia.fmw.libcommons.valueobject.Entity;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.EventServiceRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.stub.EventServiceRDTOStub;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RestServerConfig.class })
@TestPropertySource(locations = {"classpath:app/config/application.properties"})
@WebAppConfiguration
public class ValidatorServiceEventTest {
	
	@Mock
	private ValidatorUtils validator;
	
	@InjectMocks
	private ValidatorServiceEvent validatorServiceEvent;
	
	private EventServiceRDTO  eventServiceRDTO ;
	
	private ReturnRDTO returnRDTO;
	
	private ReturnRDTO returnRDTOKO;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		eventServiceRDTO = EventServiceRDTOStub.defaultOne();
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
		validatorServiceEvent.validateInsert(eventServiceRDTO);
	}

	@Test
	public void caseValidateInsertInvalidCodeItinerary() {
		
		
		Mockito.when(validator.validateCodeItinerary(Mockito.anyString())).thenReturn(returnRDTOKO);
		
		Mockito.when(validator.validateExistsItineraryGap(Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsPlanificatedItinerary(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsEventGap(Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateValueListGap(Mockito.anyString(),Mockito.any(Entity.class))).thenReturn(true);
		Mockito.when(validator.validateExistsGroupEventType(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		validatorServiceEvent.validateInsert(eventServiceRDTO);
	}
	
	@Test
	public void caseValidateInsertNotExistsItinerary() {
		
		
		Mockito.when(validator.validateCodeItinerary(Mockito.anyString())).thenReturn(returnRDTO);
		
		Mockito.when(validator.validateExistsItineraryGap(Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateExistsPlanificatedItinerary(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsEventGap(Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateValueListGap(Mockito.anyString(),Mockito.any(Entity.class))).thenReturn(true);
		Mockito.when(validator.validateExistsGroupEventType(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		validatorServiceEvent.validateInsert(eventServiceRDTO);
	}
	
	@Test
	public void caseValidateInsertNotExistsPlanificatedItinerary() {
		
		
		Mockito.when(validator.validateCodeItinerary(Mockito.anyString())).thenReturn(returnRDTO);
		
		Mockito.when(validator.validateExistsItineraryGap(Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsPlanificatedItinerary(Mockito.anyString(),Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateExistsEventGap(Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateValueListGap(Mockito.anyString(),Mockito.any(Entity.class))).thenReturn(true);
		Mockito.when(validator.validateExistsGroupEventType(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		validatorServiceEvent.validateInsert(eventServiceRDTO);
	}
	
	@Test
	public void caseValidateInsertExistsEventGap() {
		
		
		Mockito.when(validator.validateCodeItinerary(Mockito.anyString())).thenReturn(returnRDTO);
		
		Mockito.when(validator.validateExistsItineraryGap(Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsPlanificatedItinerary(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsEventGap(Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateValueListGap(Mockito.anyString(),Mockito.any(Entity.class))).thenReturn(true);
		Mockito.when(validator.validateExistsGroupEventType(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		validatorServiceEvent.validateInsert(eventServiceRDTO);
	}
	
	@Test
	public void caseValidateInsertNotExistsTypeEvent() {
		
		eventServiceRDTO.setCodeType("type");
		Mockito.when(validator.validateCodeItinerary(Mockito.anyString())).thenReturn(returnRDTO);
		
		Mockito.when(validator.validateExistsItineraryGap(Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsPlanificatedItinerary(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsEventGap(Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateValueListGap(eventServiceRDTO.getCodeType(),new Entity(ValueListConstants.CODE_TYPE_EVENT))).thenReturn(false);
		Mockito.when(validator.validateExistsGroupEventType(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		validatorServiceEvent.validateInsert(eventServiceRDTO);
	}
	
	@Test
	public void caseValidateInsertNotExistsGroupEventTypeTypeEvent() {
		
		eventServiceRDTO.setCodeType("type");
		Mockito.when(validator.validateCodeItinerary(Mockito.anyString())).thenReturn(returnRDTO);
		
		Mockito.when(validator.validateExistsItineraryGap(Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsPlanificatedItinerary(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsEventGap(Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateValueListGap(eventServiceRDTO.getCodeType(),new Entity(ValueListConstants.CODE_TYPE_EVENT))).thenReturn(true);
		Mockito.when(validator.validateExistsGroupEventType(Mockito.anyString(),Mockito.anyString())).thenReturn(false);
		validatorServiceEvent.validateInsert(eventServiceRDTO);
	}
	
	@Test
	public void caseValidateInsertNotExistsReason() {
		
		eventServiceRDTO.setCodeType("type");
		eventServiceRDTO.setCodeReason("reason");
		Mockito.when(validator.validateCodeItinerary(Mockito.anyString())).thenReturn(returnRDTO);
		
		Mockito.when(validator.validateExistsItineraryGap(Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsPlanificatedItinerary(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsEventGap(Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateValueListGap(eventServiceRDTO.getCodeType(),new Entity(ValueListConstants.CODE_TYPE_EVENT))).thenReturn(true);
		Mockito.when(validator.validateExistsGroupEventType(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateValueListGap(eventServiceRDTO.getCodeReason(),new Entity(ValueListConstants.CODE_TYPE_REASON))).thenReturn(false);
		validatorServiceEvent.validateInsert(eventServiceRDTO);
	}

	@Test
	public void caseValidateInsertNotExistsSubReason() {
		
		eventServiceRDTO.setCodeType("type");
		eventServiceRDTO.setCodeReason("reason");
		eventServiceRDTO.setCodeSubReason("subreason");
		Mockito.when(validator.validateCodeItinerary(Mockito.anyString())).thenReturn(returnRDTO);
		
		Mockito.when(validator.validateExistsItineraryGap(Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsPlanificatedItinerary(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsEventGap(Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateValueListGap(eventServiceRDTO.getCodeType(),new Entity(ValueListConstants.CODE_TYPE_EVENT))).thenReturn(true);
		Mockito.when(validator.validateExistsGroupEventType(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateValueListGap(eventServiceRDTO.getCodeReason(),new Entity(ValueListConstants.CODE_TYPE_REASON))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(eventServiceRDTO.getCodeReason(),new Entity(ValueListConstants.CODE_TYPE_SUBREASON))).thenReturn(false);
		validatorServiceEvent.validateInsert(eventServiceRDTO);
	}
}
