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

import es.bcn.imi.framework.vigia.frontal.validator.ValidatorAnnulmentEvent;
import es.bcn.imi.framework.vigia.frontal.validator.ValidatorUtils;
import es.bcn.imi.framework.vigia.frontal.web.rest.config.RestServerConfig;
import es.bcn.vigia.fmw.libcommons.constants.ValueListConstants;
import es.bcn.vigia.fmw.libcommons.valueobject.Entity;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.AnnulmentEventRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.stub.AnnulmentEventRDTOStub;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RestServerConfig.class })
@TestPropertySource(locations = {"classpath:app/config/application.properties"})
@WebAppConfiguration
public class ValidatorAnnulmentEventTest {
	
	@Mock
	private ValidatorUtils validator;
	
	@InjectMocks
	private ValidatorAnnulmentEvent validatorAnnulmentEvent;
	
	private AnnulmentEventRDTO  annulmentEventRDTO;
	
	private ReturnRDTO returnRDTO;
	
	private ReturnRDTO returnRDTOKO;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		annulmentEventRDTO = AnnulmentEventRDTOStub.defaultOne();
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
		validatorAnnulmentEvent.validateInsert(annulmentEventRDTO);
	}

	@Test
	public void caseValidateInsertInvalidCodeItinerary() {
		
		
		Mockito.when(validator.validateCodeItinerary(Mockito.anyString())).thenReturn(returnRDTOKO);
		
		Mockito.when(validator.validateExistsItineraryGap(Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsPlanificatedItinerary(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsEventGap(Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateValueListGap(Mockito.anyString(),Mockito.any(Entity.class))).thenReturn(true);
		Mockito.when(validator.validateExistsGroupEventType(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		validatorAnnulmentEvent.validateInsert(annulmentEventRDTO);
	}
	
	@Test
	public void caseValidateInsertNotExistsItinerary() {
		
		
		Mockito.when(validator.validateCodeItinerary(Mockito.anyString())).thenReturn(returnRDTO);
		
		Mockito.when(validator.validateExistsItineraryGap(Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateExistsPlanificatedItinerary(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsEventGap(Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateValueListGap(Mockito.anyString(),Mockito.any(Entity.class))).thenReturn(true);
		Mockito.when(validator.validateExistsGroupEventType(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		validatorAnnulmentEvent.validateInsert(annulmentEventRDTO);
	}
	
	@Test
	public void caseValidateInsertNotExistsPlanificatedItinerary() {
		
		
		Mockito.when(validator.validateCodeItinerary(Mockito.anyString())).thenReturn(returnRDTO);
		
		Mockito.when(validator.validateExistsItineraryGap(Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsPlanificatedItinerary(Mockito.anyString(),Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateExistsEventGap(Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateValueListGap(Mockito.anyString(),Mockito.any(Entity.class))).thenReturn(true);
		Mockito.when(validator.validateExistsGroupEventType(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		validatorAnnulmentEvent.validateInsert(annulmentEventRDTO);
	}
	
	@Test
	public void caseValidateInsertExistsEventGap() {
		
		
		Mockito.when(validator.validateCodeItinerary(Mockito.anyString())).thenReturn(returnRDTO);
		
		Mockito.when(validator.validateExistsItineraryGap(Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsPlanificatedItinerary(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsEventGap(Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateValueListGap(Mockito.anyString(),Mockito.any(Entity.class))).thenReturn(true);
		Mockito.when(validator.validateExistsGroupEventType(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		validatorAnnulmentEvent.validateInsert(annulmentEventRDTO);
	}
	
	@Test
	public void caseValidateInsertNotExistsTypeEvent() {
		
		annulmentEventRDTO.setCodeType("type");
		Mockito.when(validator.validateCodeItinerary(Mockito.anyString())).thenReturn(returnRDTO);
		
		Mockito.when(validator.validateExistsItineraryGap(Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsPlanificatedItinerary(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsEventGap(Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateValueListGap(annulmentEventRDTO.getCodeType(),new Entity(ValueListConstants.CODE_TYPE_EVENT))).thenReturn(false);
		Mockito.when(validator.validateExistsGroupEventType(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		validatorAnnulmentEvent.validateInsert(annulmentEventRDTO);
	}
	
	@Test
	public void caseValidateInsertNotExistsGroupEventTypeTypeEvent() {
		
		annulmentEventRDTO.setCodeType("type");
		Mockito.when(validator.validateCodeItinerary(Mockito.anyString())).thenReturn(returnRDTO);
		
		Mockito.when(validator.validateExistsItineraryGap(Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsPlanificatedItinerary(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsEventGap(Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateValueListGap(annulmentEventRDTO.getCodeType(),new Entity(ValueListConstants.CODE_TYPE_EVENT))).thenReturn(true);
		Mockito.when(validator.validateExistsGroupEventType(Mockito.anyString(),Mockito.anyString())).thenReturn(false);
		validatorAnnulmentEvent.validateInsert(annulmentEventRDTO);
	}
	
	@Test
	public void caseValidateInsertNotExistsReason() {
		
		annulmentEventRDTO.setCodeType("type");
		annulmentEventRDTO.setCodeReason("reason");
		Mockito.when(validator.validateCodeItinerary(Mockito.anyString())).thenReturn(returnRDTO);
		
		Mockito.when(validator.validateExistsItineraryGap(Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsPlanificatedItinerary(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsEventGap(Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateValueListGap(annulmentEventRDTO.getCodeType(),new Entity(ValueListConstants.CODE_TYPE_EVENT))).thenReturn(true);
		Mockito.when(validator.validateExistsGroupEventType(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateValueListGap(annulmentEventRDTO.getCodeReason(),new Entity(ValueListConstants.CODE_TYPE_REASON))).thenReturn(false);
		validatorAnnulmentEvent.validateInsert(annulmentEventRDTO);
	}

	@Test
	public void caseValidateInsertNotExistsSubReason() {
		
		annulmentEventRDTO.setCodeType("type");
		annulmentEventRDTO.setCodeReason("reason");
		annulmentEventRDTO.setCodeSubReason("subreason");
		Mockito.when(validator.validateCodeItinerary(Mockito.anyString())).thenReturn(returnRDTO);
		
		Mockito.when(validator.validateExistsItineraryGap(Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsPlanificatedItinerary(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsEventGap(Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateValueListGap(annulmentEventRDTO.getCodeType(),new Entity(ValueListConstants.CODE_TYPE_EVENT))).thenReturn(true);
		Mockito.when(validator.validateExistsGroupEventType(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateValueListGap(annulmentEventRDTO.getCodeReason(),new Entity(ValueListConstants.CODE_TYPE_REASON))).thenReturn(true);
		Mockito.when(validator.validateValueListGap(annulmentEventRDTO.getCodeReason(),new Entity(ValueListConstants.CODE_TYPE_SUBREASON))).thenReturn(false);
		validatorAnnulmentEvent.validateInsert(annulmentEventRDTO);
	}
	
	@Test
	public void caseOkValidateInsertNotExistsAnulled() {
		
		annulmentEventRDTO.setCode("");
		annulmentEventRDTO.setCodeAnnulled("codeAnulled");
		Mockito.when(validator.validateCodeItinerary(Mockito.anyString())).thenReturn(returnRDTO);
		
		Mockito.when(validator.validateExistsItineraryGap(Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsPlanificatedItinerary(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsEventGap("")).thenReturn(false);
		Mockito.when(validator.validateExistsEventGap("codeAnulled")).thenReturn(false);
		Mockito.when(validator.validateValueListGap(Mockito.anyString(),Mockito.any(Entity.class))).thenReturn(true);
		Mockito.when(validator.validateExistsGroupEventType(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		validatorAnnulmentEvent.validateInsert(annulmentEventRDTO);
	}

	@Test
	public void caseOkValidateInsertExistsAnulled() {
		
		annulmentEventRDTO.setCode("");
		annulmentEventRDTO.setCodeAnnulled("codeAnulled");
		Mockito.when(validator.validateCodeItinerary(Mockito.anyString())).thenReturn(returnRDTO);
		
		Mockito.when(validator.validateExistsItineraryGap(Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsPlanificatedItinerary(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsEventGap("")).thenReturn(false);
		Mockito.when(validator.validateExistsEventGap("codeAnulled")).thenReturn(true);
		Mockito.when(validator.validateValueListGap(Mockito.anyString(),Mockito.any(Entity.class))).thenReturn(true);
		Mockito.when(validator.validateExistsGroupEventType(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		validatorAnnulmentEvent.validateInsert(annulmentEventRDTO);
	}
	
	@Test
	public void caseOkValidateInsertEmptyAnulled() {
		
		annulmentEventRDTO.setCode("codi");
		annulmentEventRDTO.setCodeAnnulled("");
		Mockito.when(validator.validateCodeItinerary(Mockito.anyString())).thenReturn(returnRDTO);
		
		Mockito.when(validator.validateExistsItineraryGap(Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsPlanificatedItinerary(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsEventGap("codi")).thenReturn(false);
		Mockito.when(validator.validateExistsEventGap("")).thenReturn(true);
		
		Mockito.when(validator.validateValueListGap(Mockito.anyString(),Mockito.any(Entity.class))).thenReturn(true);
		Mockito.when(validator.validateExistsGroupEventType(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		validatorAnnulmentEvent.validateInsert(annulmentEventRDTO);
	}
	
	@Test
	public void caseValidateInsertReasonNull() {
		
		annulmentEventRDTO.setCodeReason(null);
		Mockito.when(validator.validateCodeItinerary(Mockito.anyString())).thenReturn(returnRDTO);
		
		Mockito.when(validator.validateExistsItineraryGap(Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsPlanificatedItinerary(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsEventGap(Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateValueListGap(Mockito.anyString(),Mockito.any(Entity.class))).thenReturn(true);
		Mockito.when(validator.validateExistsGroupEventType(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		validatorAnnulmentEvent.validateInsert(annulmentEventRDTO);
	}

	@Test
	public void caseValidateInsertReasonEmpty() {
		
		annulmentEventRDTO.setCodeReason("");
		Mockito.when(validator.validateCodeItinerary(Mockito.anyString())).thenReturn(returnRDTO);
		
		Mockito.when(validator.validateExistsItineraryGap(Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsPlanificatedItinerary(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsEventGap(Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateValueListGap(Mockito.anyString(),Mockito.any(Entity.class))).thenReturn(true);
		Mockito.when(validator.validateExistsGroupEventType(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		validatorAnnulmentEvent.validateInsert(annulmentEventRDTO);
	}

	@Test
	public void caseValidateInsertExistsReasonSubReasonNull() {
		
		annulmentEventRDTO.setCodeSubReason(null);
		Mockito.when(validator.validateCodeItinerary(Mockito.anyString())).thenReturn(returnRDTO);
		
		Mockito.when(validator.validateExistsItineraryGap(Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsPlanificatedItinerary(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		Mockito.when(validator.validateExistsEventGap(Mockito.anyString())).thenReturn(false);
		Mockito.when(validator.validateValueListGap(Mockito.anyString(),Mockito.any(Entity.class))).thenReturn(true);
		Mockito.when(validator.validateExistsGroupEventType(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		validatorAnnulmentEvent.validateInsert(annulmentEventRDTO);
	}


	@Test
	public void caseValidateInsertSubReasonNotValid() {
			
			annulmentEventRDTO.setCodeSubReason("codi");
			Mockito.when(validator.validateCodeItinerary(Mockito.anyString())).thenReturn(returnRDTO);
			Mockito.when(validator.validateExistsItineraryGap(Mockito.anyString())).thenReturn(true);
			Mockito.when(validator.validateExistsPlanificatedItinerary(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
			Mockito.when(validator.validateExistsEventGap(Mockito.anyString())).thenReturn(false);
			Mockito.when(validator.validateValueListGap(Mockito.anyString(),Mockito.any(Entity.class))).thenReturn(true);
			Mockito.when(validator.validateValueListGap("codi",new Entity(ValueListConstants.CODE_TYPE_SUBREASON))).thenReturn(false);
			Mockito.when(validator.validateExistsGroupEventType(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		validatorAnnulmentEvent.validateInsert(annulmentEventRDTO);
	}

	@Test
	public void caseValidateInsertSubReasonValid() {
			
			annulmentEventRDTO.setCodeSubReason("codi");
			Mockito.when(validator.validateCodeItinerary(Mockito.anyString())).thenReturn(returnRDTO);
			Mockito.when(validator.validateExistsItineraryGap(Mockito.anyString())).thenReturn(true);
			Mockito.when(validator.validateExistsPlanificatedItinerary(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
			Mockito.when(validator.validateExistsEventGap(Mockito.anyString())).thenReturn(false);
			Mockito.when(validator.validateValueListGap(Mockito.anyString(),Mockito.any(Entity.class))).thenReturn(true);
			Mockito.when(validator.validateValueListGap("codi",new Entity(ValueListConstants.CODE_TYPE_SUBREASON))).thenReturn(true);
			Mockito.when(validator.validateExistsGroupEventType(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		validatorAnnulmentEvent.validateInsert(annulmentEventRDTO);
	}

	@Test
	public void caseValidateInsertSubReasonEmpty() {
			
			Mockito.when(validator.validateCodeItinerary(Mockito.anyString())).thenReturn(returnRDTO);
			Mockito.when(validator.validateExistsItineraryGap(Mockito.anyString())).thenReturn(true);
			Mockito.when(validator.validateExistsPlanificatedItinerary(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
			Mockito.when(validator.validateExistsEventGap(Mockito.anyString())).thenReturn(false);
			Mockito.when(validator.validateValueListGap(Mockito.anyString(),Mockito.any(Entity.class))).thenReturn(true);
			Mockito.when(validator.validateValueListGap("codi",new Entity(ValueListConstants.CODE_TYPE_SUBREASON))).thenReturn(true);
			Mockito.when(validator.validateExistsGroupEventType(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		validatorAnnulmentEvent.validateInsert(annulmentEventRDTO);
	}

}
