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

import es.bcn.imi.framework.vigia.frontal.validator.ValidatorPlanning;
import es.bcn.imi.framework.vigia.frontal.validator.ValidatorUtils;
import es.bcn.imi.framework.vigia.frontal.web.rest.config.RestServerConfig;
import es.bcn.vigia.fmw.libcommons.valueobject.Entity;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.LabelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.PlanningRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.PlanningRDTOStub;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RestServerConfig.class })
@TestPropertySource(locations = {"classpath:app/config/application.properties"})
@SuppressWarnings("unchecked")
@WebAppConfiguration
public class ValidatorPlanningTest {
	
	@Mock
	private ValidatorUtils validator;
	
	@InjectMocks
	private ValidatorPlanning validatorPlanning;
	
	private PlanningRDTO planningRDTO;
	
	ReturnRDTO returnRDTO;
	
	ReturnRDTO returnRDTOKO;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		planningRDTO = PlanningRDTOStub.defaultOne();
		returnRDTO = ReturnRDTOStub.getSuccessMessage();
		returnRDTOKO = new ReturnRDTO();
		returnRDTOKO.setCode("KO");
	}

	@Test
	public void caseOkValidateInsert() {
		
		
		Mockito.when(validator.validateCodeItinerary(Mockito.anyString())).thenReturn(returnRDTO);
		Mockito.when(validator.validateExistsItineraryGap(Mockito.anyString())).thenReturn(true);
			
		
		validatorPlanning.validateInsert(planningRDTO);
	}

	@Test
	public void caseOkValidateInsertNotValidItinerary() {
		
		
		Mockito.when(validator.validateCodeItinerary(Mockito.anyString())).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateExistsItineraryGap(Mockito.anyString())).thenReturn(true);
			
		
		validatorPlanning.validateInsert(planningRDTO);
	}
	
	@Test
	public void caseOkValidateInsertNotExistsItinerary() {
		
		
		Mockito.when(validator.validateCodeItinerary(Mockito.anyString())).thenReturn(returnRDTO);
		Mockito.when(validator.validateExistsItineraryGap(Mockito.anyString())).thenReturn(false);
			
		
		validatorPlanning.validateInsert(planningRDTO);
	}
	
	@Test
	public void caseValidateInsertExistsLabel() {
		LabelRDTO label = new LabelRDTO();
		label.setValue("LABEL");

		planningRDTO.getLstLabelRDTO().add(label);

		
		Mockito.when(validator.validateCodeItinerary(Mockito.anyString())).thenReturn(returnRDTO);
		Mockito.when(validator.validateExistsItineraryGap(Mockito.anyString())).thenReturn(true);
			
		Mockito.when(validator.validateListValueListGap(Mockito.anyList(),Mockito.any(Entity.class),Mockito.anyString(),Mockito.anyString())).thenReturn(returnRDTO);
		validatorPlanning.validateInsert(planningRDTO);
	}
	
	@Test
	public void caseValidateInsertNotExistsLabel() {
		LabelRDTO label = new LabelRDTO();
		label.setValue("LABEL");

		planningRDTO.getLstLabelRDTO().add(label);

		
		Mockito.when(validator.validateCodeItinerary(Mockito.anyString())).thenReturn(returnRDTO);
		Mockito.when(validator.validateExistsItineraryGap(Mockito.anyString())).thenReturn(true);
			
		Mockito.when(validator.validateListValueListGap(Mockito.anyList(),Mockito.any(Entity.class),Mockito.anyString(),Mockito.anyString())).thenReturn(returnRDTOKO);
		validatorPlanning.validateInsert(planningRDTO);
	}
}
