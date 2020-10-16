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
import es.bcn.imi.framework.vigia.frontal.validator.ValidatorUtils;
import es.bcn.imi.framework.vigia.frontal.web.rest.config.RestServerConfig;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.ActuationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.ActuationRDTOStub;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RestServerConfig.class })
@TestPropertySource(locations = {"classpath:app/config/application.properties"})
@WebAppConfiguration
public class ValidatorActuationTest {
	
	@Mock
	private ValidatorUtils validator;
	
	@InjectMocks
	private ValidatorActuation validatorActuation;
	
	private ActuationRDTO actuationRDTO;
	
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		actuationRDTO = ActuationRDTOStub.defaultOne();
	}

	
	@Test
	public void caseValidateInsert() {
		
		Mockito.when(validator.validateExistsActuation(actuationRDTO.getCodeContract(),actuationRDTO.getCode(),null,false)).thenReturn(false);
		validatorActuation.validateInsert(actuationRDTO);
	}

	@Test
	public void caseValidateInsertExistsActuation() {
		
		Mockito.when(validator.validateExistsActuation(actuationRDTO.getCodeContract(),actuationRDTO.getCode(),null,false)).thenReturn(true);
		validatorActuation.validateInsert(actuationRDTO);
	}
	
	@Test
	public void caseValidateDelete() {
		actuationRDTO.setCodeContract("C");
		actuationRDTO.setCode("1");
		actuationRDTO.setCodeEntity("C1");
		Mockito.when(validator.validateExistsActuation(actuationRDTO.getCodeContract(),actuationRDTO.getCode(),actuationRDTO.getCodeEntity(),true)).thenReturn(true);
		validatorActuation.validateDelete(actuationRDTO);
	}

	@Test
	public void caseValidateDeleteNotExistsActuation() {
		
		Mockito.when(validator.validateExistsActuation(actuationRDTO.getCodeContract(),actuationRDTO.getCode(),null,true)).thenReturn(false);
		validatorActuation.validateDelete(actuationRDTO);
	}}
