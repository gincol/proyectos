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

import es.bcn.imi.framework.vigia.frontal.validator.ValidatorCommerce;
import es.bcn.imi.framework.vigia.frontal.validator.ValidatorUtils;
import es.bcn.imi.framework.vigia.frontal.web.rest.config.RestServerConfig;
import es.bcn.vigia.fmw.libcommons.constants.ValueListConstants;
import es.bcn.vigia.fmw.libcommons.valueobject.Entity;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CommerceRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.LocationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.CommerceRDTOStub;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RestServerConfig.class })
@TestPropertySource(locations = {"classpath:app/config/application.properties"})
@SuppressWarnings("unchecked")
@WebAppConfiguration
public class ValidatorCommerceTest {
	
	@Mock
	private ValidatorUtils validator;
	
	@InjectMocks
	private ValidatorCommerce validatorCommerce;
	
	private CommerceRDTO commerceRDTO;
	
	private ReturnRDTO returnRDTO;
	
	private ReturnRDTO returnRDTOKO;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		commerceRDTO = CommerceRDTOStub.defaultOne();
		returnRDTO = ReturnRDTOStub.getSuccessMessage();
		returnRDTOKO = new ReturnRDTO();
		returnRDTOKO.setCode("KO");
	}

	@Test
	public void caseOkInsert() {
		validatorCommerce.validateInsert(commerceRDTO);
	}



	@Test
	public void caseOkInsert2() {

		LocationRDTO locationRDTO = new LocationRDTO();
		locationRDTO.setCodeTypeRoad("C");
		commerceRDTO.setLocationRDTO(locationRDTO);
		
		Mockito.when(validator.validateValueListGap(commerceRDTO.getLocationRDTO().getCodeTypeRoad(),new Entity(ValueListConstants.CODE_TYPE_ROAD))).thenReturn(true);
		
		validatorCommerce.validateInsert(commerceRDTO);
	}

	@Test
	public void caseOkInsert3() {
		
		LocationRDTO locationRDTO = new LocationRDTO();
		locationRDTO.setCodeTypeRoad("C");
		commerceRDTO.setLocationRDTO(locationRDTO);
		commerceRDTO.setStatus("A");
		Mockito.when(validator.validateValueListGap(commerceRDTO.getLocationRDTO().getCodeTypeRoad(),new Entity(ValueListConstants.CODE_TYPE_ROAD))).thenReturn(true);
		
		Mockito.when(validator.validateValueListGap(commerceRDTO.getStatus(),new Entity(ValueListConstants.COMMERCE_STATUS))).thenReturn(true);
		
		validatorCommerce.validateInsert(commerceRDTO);
	}
	
	@Test
	public void caseOkInsert4() {
		
		LocationRDTO locationRDTO = new LocationRDTO();
		locationRDTO.setCodeTypeRoad("C");
		commerceRDTO.setLocationRDTO(locationRDTO);
		commerceRDTO.setStatus("A");
		Mockito.when(validator.validateValueListGap(commerceRDTO.getLocationRDTO().getCodeTypeRoad(),new Entity(ValueListConstants.CODE_TYPE_ROAD))).thenReturn(true);
		
		Mockito.when(validator.validateValueListGap(commerceRDTO.getStatus(),new Entity(ValueListConstants.COMMERCE_STATUS))).thenReturn(true);
		Mockito.when(validator.validateExistsCommerceGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		validatorCommerce.validateInsert(commerceRDTO);
	}

	@Test
	public void caseOkUpdate() {
		validatorCommerce.validateUpdate(commerceRDTO);
	}

	@Test
	public void caseOkUpdate2() {
		
		LocationRDTO locationRDTO = new LocationRDTO();
		locationRDTO.setCodeTypeRoad("C");
		commerceRDTO.setLocationRDTO(locationRDTO);
		commerceRDTO.setStatus("A");
		Mockito.when(validator.validateValueListGap(commerceRDTO.getLocationRDTO().getCodeTypeRoad(),new Entity(ValueListConstants.CODE_TYPE_ROAD))).thenReturn(true);
		
		Mockito.when(validator.validateValueListGap(commerceRDTO.getStatus(),new Entity(ValueListConstants.COMMERCE_STATUS))).thenReturn(true);
		Mockito.when(validator.validateExistsCommerceGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(false);
		validatorCommerce.validateUpdate(commerceRDTO);
	}
	
	@Test
	public void caseOkUpdate3() {
		
		LocationRDTO locationRDTO = new LocationRDTO();
		locationRDTO.setCodeTypeRoad("C");
		commerceRDTO.setLocationRDTO(locationRDTO);
		commerceRDTO.setStatus("A");
		Mockito.when(validator.validateValueListGap(commerceRDTO.getLocationRDTO().getCodeTypeRoad(),new Entity(ValueListConstants.CODE_TYPE_ROAD))).thenReturn(true);
		
		Mockito.when(validator.validateValueListGap(commerceRDTO.getStatus(),new Entity(ValueListConstants.COMMERCE_STATUS))).thenReturn(true);
		Mockito.when(validator.validateExistsCommerceGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		validatorCommerce.validateUpdate(commerceRDTO);
	}
	@Test
	public void caseOkDelete() {
		validatorCommerce.validateDelete(commerceRDTO);
	}

	@Test
	public void caseOkCommons() {
		validatorCommerce.validateInsertUpdate(commerceRDTO);
	}
	
	@Test
	public void caseOkInsertElements() {
		validatorCommerce.validateInsertElements(commerceRDTO);
	}
	
	@Test
	public void caseOkInsertElements2() {
		Mockito.when(validator.validateExistsCommerceGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		Mockito.when(validator.validateExistsListPhysicalModelGap(Mockito.anyList(),Mockito.anyBoolean())).thenReturn(returnRDTO);
		
		validatorCommerce.validateInsertElements(commerceRDTO);
	}

	@Test
	public void caseOkInsertElements3() {
		Mockito.when(validator.validateExistsCommerceGap(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(true);
		Mockito.when(validator.validateExistsListPhysicalModelGap(Mockito.anyList(),Mockito.anyBoolean())).thenReturn(returnRDTOKO);
		
		validatorCommerce.validateInsertElements(commerceRDTO);
	}
	}
