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
import es.bcn.imi.framework.vigia.frontal.validator.ValidatorUtils;
import es.bcn.imi.framework.vigia.frontal.web.rest.config.RestServerConfig;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.DocumentarySupportRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.DocumentarySupportRDTOStub;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RestServerConfig.class })
@TestPropertySource(locations = {"classpath:app/config/application.properties"})
@WebAppConfiguration
public class ValidatorDocumentarySupportTest {
	
	@Mock
	private ValidatorUtils validator;
	
	@InjectMocks
	private ValidatorDocumentarySupport validatorDocumentarySupport;
	
	private DocumentarySupportRDTO documentarySupportRDTO;
	
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		documentarySupportRDTO = DocumentarySupportRDTOStub.defaultOne();
	}

	
	@Test
	public void caseValidateInsert() {
		documentarySupportRDTO.setCodeContract("C");
		documentarySupportRDTO.setCodeActuation("1");
		Mockito.when(validator.validateExistsDocumentarySupport(documentarySupportRDTO.getCodeContract(),documentarySupportRDTO.getCodeContractDocument(),null,false)).thenReturn(true);
		Mockito.when(validator.validateExistsActuation(documentarySupportRDTO.getCodeContract(),documentarySupportRDTO.getCodeActuation(),null,false)).thenReturn(true);
		validatorDocumentarySupport.validateInsert(documentarySupportRDTO);
	}

	@Test
	public void caseValidateInsertNotExistsActuation() {
		
		Mockito.when(validator.validateExistsActuation(documentarySupportRDTO.getCodeContract(),documentarySupportRDTO.getCodeActuation(),null,false)).thenReturn(false);
		validatorDocumentarySupport.validateInsert(documentarySupportRDTO);
	}

	@Test
	public void caseValidateInsertNotEmptyActuation() {
		documentarySupportRDTO.setCodeActuation("1");
		Mockito.when(validator.validateExistsActuation(documentarySupportRDTO.getCodeContract(),documentarySupportRDTO.getCodeActuation(),null,false)).thenReturn(true);
		validatorDocumentarySupport.validateInsert(documentarySupportRDTO);
	}

	@Test
	public void caseValidateDelete() {
		
		Mockito.when(validator.validateExistsDocumentarySupport(documentarySupportRDTO.getCodeContract(),documentarySupportRDTO.getCodeContractDocument(),documentarySupportRDTO.getCode(),true)).thenReturn(true);
		validatorDocumentarySupport.validateDelete(documentarySupportRDTO);
	}

	@Test
	public void caseValidateDeleteNotExistsDocumentarySupport() {
		
		Mockito.when(validator.validateExistsDocumentarySupport(documentarySupportRDTO.getCodeContract(),documentarySupportRDTO.getCodeContractDocument(),documentarySupportRDTO.getCode(),true)).thenReturn(false);
		validatorDocumentarySupport.validateDelete(documentarySupportRDTO);
	}}
