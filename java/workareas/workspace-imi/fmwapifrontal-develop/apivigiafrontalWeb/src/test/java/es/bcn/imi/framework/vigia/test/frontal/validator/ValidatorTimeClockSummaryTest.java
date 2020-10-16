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

import es.bcn.imi.framework.vigia.frontal.validator.ValidatorTimeClockSummary;
import es.bcn.imi.framework.vigia.frontal.validator.ValidatorUtils;
import es.bcn.imi.framework.vigia.frontal.web.rest.config.RestServerConfig;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.TimeClockSummaryRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.stub.TimeClockSummaryRDTOStub;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RestServerConfig.class })
@TestPropertySource(locations = {"classpath:app/config/application.properties"})
@WebAppConfiguration
public class ValidatorTimeClockSummaryTest {
	
	@Mock
	private ValidatorUtils validator;
	
	@InjectMocks
	private ValidatorTimeClockSummary validatorTimeClockSummary;
	
	private TimeClockSummaryRDTO timeClockSummaryRDTO;
	
	
	private ReturnRDTO returnRDTOKO;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		timeClockSummaryRDTO = TimeClockSummaryRDTOStub.defaultOne();
		returnRDTOKO = new ReturnRDTO();
		returnRDTOKO.setCode("KO");
	}

	@Test
	public void caseOkInsert() {
		validatorTimeClockSummary.validateInsert(timeClockSummaryRDTO);
	}



	@Test
	public void caseValidateExistsInstallationGap() {

		Mockito.when(validator.validateExistsInstallationGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		
		validatorTimeClockSummary.validateInsert(timeClockSummaryRDTO);
	}

	@Test
	public void caseValidateObservationEmpty() {
		timeClockSummaryRDTO.setObservation("");
		
		Mockito.when(validator.validateExistsInstallationGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		
		validatorTimeClockSummary.validateInsert(timeClockSummaryRDTO);
	}
	
	
	@Test
	public void caseValidateExistsInstallationGapExistsTimeClockSummary() {

		timeClockSummaryRDTO.setObservation(null);
		Mockito.when(validator.validateExistsInstallationGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		Mockito.when(validator.validateExistsTimeClockSummary(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(true);
		validatorTimeClockSummary.validateInsert(timeClockSummaryRDTO);
	}

	
	@Test
	public void caseValidateNotExistsInstallationGapObservationEmpty() {
		timeClockSummaryRDTO.setObservation("");
		
		Mockito.when(validator.validateExistsInstallationGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(false);
		
		validatorTimeClockSummary.validateInsert(timeClockSummaryRDTO);
	}
	
	@Test
	public void caseValidateExistsInstallationGapExistsTimeClockSummaryObservationNotempty() {
		timeClockSummaryRDTO.setObservation("obs");
		
		Mockito.when(validator.validateExistsInstallationGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		Mockito.when(validator.validateExistsTimeClockSummary(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(true);
		validatorTimeClockSummary.validateInsert(timeClockSummaryRDTO);
	}

	}
