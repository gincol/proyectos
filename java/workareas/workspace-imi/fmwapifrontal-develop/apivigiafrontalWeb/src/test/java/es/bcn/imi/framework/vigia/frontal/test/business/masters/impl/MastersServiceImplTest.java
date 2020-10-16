package es.bcn.imi.framework.vigia.frontal.test.business.masters.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import es.bcn.imi.framework.vigia.frontal.business.masters.impl.MastersServiceImpl;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisTemplateGap;
import es.bcn.imi.framework.vigia.frontal.validator.ValidatorUtils;
import es.bcn.imi.framework.vigia.frontal.web.rest.config.RestServerConfig;
import es.bcn.vigia.fmw.libcommons.business.dto.PricesTableBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.stub.PricesTableBDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterMastersRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.QueryParameterMastersRDTOStub;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import es.bcn.vigia.fmw.libutils.services.Utils;
import es.bcn.vigia.fmw.libutils.services.rest.RestCall;
import es.bcn.vigia.fmw.libutils.validator.front.FrontValidateMastersService;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RestServerConfig.class })
@TestPropertySource(locations = {"classpath:app/config/application.properties"})
@WebAppConfiguration
public class MastersServiceImplTest {
	
	@Mock
	private MyBatisTemplate myBatisTemplate;
	
	@Mock
	private MyBatisTemplateGap myBatisTemplateGap;
	
	@Mock
	private FrontValidateMastersService frontValidateMastersService;

	@Mock
	private ValidatorUtils validator;
	
	@Mock
	private RestCall restCall;
	
	@Mock
	private Utils utils;
	
	@InjectMocks
	private MastersServiceImpl service;

	
	private ReturnRDTO returnRDTO;

	private QueryParameterMastersRDTO queryParameterMastersRDTO;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		returnRDTO = ReturnRDTOStub.getSuccessMessage();
		queryParameterMastersRDTO = QueryParameterMastersRDTOStub.defaultOne();
	}

	@Test
	public void caseOkSelectServiceHierarchy() throws Exception {
		Mockito.when(frontValidateMastersService.validateSyntaxSelectServiceHierarchy(queryParameterMastersRDTO)).thenReturn(returnRDTO);
		service.selectServiceHierarchy(queryParameterMastersRDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoSelectServiceHierarchy() throws Exception {
		service.selectServiceHierarchy(null);
	}

	@Test
	public void caseOkGetServiceHierarchy() throws Exception {
		service.getServiceHierarchy(queryParameterMastersRDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoGetServiceHierarchy() throws Exception {
		Mockito.doThrow(ImiException.class).when(myBatisTemplateGap.execute(Mockito.any(), Mockito.any()));
		service.getServiceHierarchy(queryParameterMastersRDTO);
	}

	@Test
	public void caseOkSelectServiceContract() throws Exception {
		Mockito.when(validator.validateContract(queryParameterMastersRDTO)).thenReturn(returnRDTO);
		Mockito.when(frontValidateMastersService.validateSyntaxSelectServiceContract(queryParameterMastersRDTO)).thenReturn(returnRDTO);
		service.selectServiceContract(queryParameterMastersRDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoSelectServiceContract() throws Exception {
		service.selectServiceContract(null);
	}

	@Test
	public void caseOkGetServiceContract() throws Exception {
		service.getServiceContract(queryParameterMastersRDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoGetServiceContract() throws Exception {
		Mockito.doThrow(ImiException.class).when(myBatisTemplateGap.execute(Mockito.any(), Mockito.any()));
		service.getServiceContract(queryParameterMastersRDTO);
	}

	@Test
	public void caseOkSelectServiceSubContract() throws Exception {
		Mockito.when(validator.validateContract(queryParameterMastersRDTO)).thenReturn(returnRDTO);
		Mockito.when(frontValidateMastersService.validateSyntaxSelectServiceSubContract(queryParameterMastersRDTO)).thenReturn(returnRDTO);
		service.selectServiceSubContract(queryParameterMastersRDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoSelectServiceSubContract() throws Exception {
		service.selectServiceSubContract(null);
	}

	@Test
	public void caseOkGetServiceSubContract() throws Exception {
		service.getServiceSubContract(queryParameterMastersRDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoGetServiceSubContract() throws Exception {
		Mockito.doThrow(ImiException.class).when(myBatisTemplateGap.execute(Mockito.any(), Mockito.any()));
		service.getServiceSubContract(queryParameterMastersRDTO);
	}

	@Test
	public void caseOkSelectWasteManagementPlantMassive() throws Exception {
		Mockito.when(frontValidateMastersService.validateSyntaxSelectWasteManagementPlantMassive(queryParameterMastersRDTO)).thenReturn(returnRDTO);
		service.selectWasteManagementPlantMassive(queryParameterMastersRDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoSelectWasteManagementPlantMassive() throws Exception {
		service.selectWasteManagementPlantMassive(null);
	}

	@Test
	public void caseOkGetWasteManagementPlantMassive() throws Exception {
		service.getWasteManagementPlantMassive(queryParameterMastersRDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoGetWasteManagementPlantMassive() throws Exception {
		Mockito.doThrow(ImiException.class).when(myBatisTemplateGap.execute(Mockito.any(), Mockito.any()));
		service.getWasteManagementPlantMassive(queryParameterMastersRDTO);
	}

	@Test
	public void caseOkSelectWasteManagementPlantDetailed() throws Exception {
		Mockito.when(frontValidateMastersService.validateSyntaxSelectWasteManagementPlantDetailed(queryParameterMastersRDTO)).thenReturn(returnRDTO);
		service.selectWasteManagementPlantDetailed(queryParameterMastersRDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoSelectWasteManagementPlantDetailed() throws Exception {
		service.selectWasteManagementPlantDetailed(null);
	}

	@Test
	public void caseOkGetWasteManagementPlantDetailed() throws Exception {
		service.getWasteManagementPlantDetailed(queryParameterMastersRDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoGetWasteManagementPlantDetailed() throws Exception {
		Mockito.doThrow(ImiException.class).when(myBatisTemplateGap.execute(Mockito.any(), Mockito.any()));
		service.getWasteManagementPlantDetailed(queryParameterMastersRDTO);
	}

	@Test
	public void caseOkSelectPricesTable() throws Exception {
		Mockito.when(validator.validateContract(queryParameterMastersRDTO)).thenReturn(returnRDTO);
		Mockito.when(frontValidateMastersService.validateSyntaxSelectPricesTable(queryParameterMastersRDTO)).thenReturn(returnRDTO);
		service.selectPricesTable(queryParameterMastersRDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoSelectPricesTable() throws Exception {
		service.selectPricesTable(null);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void caseOkGetPricesTable() throws Exception {
		List<PricesTableBDTO> priceTableBDTOs = new ArrayList<>();
		PricesTableBDTO pricesTableBDTO = PricesTableBDTOStub.defaultOne();
		priceTableBDTOs.add(pricesTableBDTO);

		ResponseEntity<Object> ss = new ResponseEntity<Object>(priceTableBDTOs, HttpStatus.ACCEPTED);
		Mockito.when(restCall.executeGETClientId(Mockito.anyString(), Mockito.anyMap(), Mockito.any(queryParameterMastersRDTO.getClass()), Mockito.anyMap())).thenReturn(ss);
		
		service.getPricesTable(queryParameterMastersRDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoGetPricesTable() throws Exception {
		Mockito.doThrow(ImiException.class).when(myBatisTemplateGap.execute(Mockito.any(), Mockito.any()));
		service.getPricesTable(queryParameterMastersRDTO);
	}

	@Test
	public void caseOkSelectEventClassification() throws Exception {
		Mockito.when(frontValidateMastersService.validateSyntaxSelectEventClassification(queryParameterMastersRDTO)).thenReturn(returnRDTO);
		service.selectEventClassification(queryParameterMastersRDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoSelectEventClassification() throws Exception {
		service.selectEventClassification(null);
	}

	@Test
	public void caseOkGetEventClassification() throws Exception {
		service.getEventClassification(queryParameterMastersRDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoGetEventClassification() throws Exception {
		Mockito.doThrow(ImiException.class).when(myBatisTemplateGap.execute(Mockito.any(), Mockito.any()));
		service.getEventClassification(queryParameterMastersRDTO);
	}

	@Test
	public void caseOkSelectBranchModelsVehicle() throws Exception {
		Mockito.when(frontValidateMastersService.validateSyntaxSelectBranchModelsVehicle(queryParameterMastersRDTO)).thenReturn(returnRDTO);
		service.selectBranchModelsVehicle(queryParameterMastersRDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoSelectBranchModelsVehicle() throws Exception {
		service.selectBranchModelsVehicle(null);
	}

	@Test
	public void caseOkGetBranchModelsVehicle() throws Exception {
		service.getBranchModelsVehicle(queryParameterMastersRDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoGetBranchModelsVehicle() throws Exception {
		Mockito.doThrow(ImiException.class).when(myBatisTemplateGap.execute(Mockito.any(), Mockito.any()));
		service.getBranchModelsVehicle(queryParameterMastersRDTO);
	}

	@Test
	public void caseOkSelectHolidayCalendar() throws Exception {
		Mockito.when(frontValidateMastersService.validateSyntaxSelectHolidayCalendar(queryParameterMastersRDTO)).thenReturn(returnRDTO);
		service.selectHolidayCalendar(queryParameterMastersRDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoSelectHolidayCalendar() throws Exception {
		service.selectHolidayCalendar(null);
	}

	@Test
	public void caseOkGetHolidayCalendar() throws Exception {
		service.getHolidayCalendar(queryParameterMastersRDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoGetHolidayCalendar() throws Exception {
		Mockito.doThrow(ImiException.class).when(myBatisTemplateGap.execute(Mockito.any(), Mockito.any()));
		service.getHolidayCalendar(queryParameterMastersRDTO);
	}

	@Test
	public void caseOkSelectWorkRegimeRelationship() throws Exception {
		Mockito.when(validator.validateContract(queryParameterMastersRDTO)).thenReturn(returnRDTO);
		Mockito.when(frontValidateMastersService.validateSyntaxSelectWorkRegimeRelationship(queryParameterMastersRDTO)).thenReturn(returnRDTO);
		service.selectWorkRegimeRelationship(queryParameterMastersRDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoSelectWorkRegimeRelationship() throws Exception {
		service.selectWorkRegimeRelationship(null);
	}

	@Test
	public void caseOkGetWorkRegimeRelationship() throws Exception {
		service.getWorkRegimeRelationship(queryParameterMastersRDTO);
	}
	
	@Test(expected = Exception.class)
	public void caseKoGetWorkRegimeRelationship() throws Exception {
		Mockito.doThrow(ImiException.class).when(myBatisTemplateGap.execute(Mockito.any(), Mockito.any()));
		service.getWorkRegimeRelationship(queryParameterMastersRDTO);
	}

	@Test
	public void caseOkSelectCompatibilityMclUbication() throws Exception {
		service.selectCompatibilityMclUbication();
	}
	
	@Test(expected = Exception.class)
	public void caseKoSelectCompatibilityMclUbication() throws Exception {
		Mockito.doThrow(ImiException.class).when(service.getCompatibilityMclUbication());
		service.selectCompatibilityMclUbication();
	}

	@Test
	public void caseOkGetCompatibilityMclUbication() throws Exception {
		service.getCompatibilityMclUbication();
	}
	
	@Test(expected = Exception.class)
	public void caseKoGetCompatibilityMclUbication() throws Exception {
		Mockito.doThrow(ImiException.class).when(myBatisTemplateGap.execute(Mockito.any(), Mockito.any()));
		service.getCompatibilityMclUbication();
	}

	@Test
	public void caseOkSelectCompatibilitySensorRRMM() throws Exception {
		service.selectCompatibilitySensorRRMM();
	}
	
	@Test(expected = Exception.class)
	public void caseKoSelectCompatibilitySensorRRMM() throws Exception {
		Mockito.doThrow(ImiException.class).when(service.getCompatibilitySensorRRMM());
		service.selectCompatibilitySensorRRMM();
	}

	@Test
	public void caseOkGetCompatibilitySensorRRMM() throws Exception {
		service.getCompatibilitySensorRRMM();
	}
	
	@Test(expected = Exception.class)
	public void caseKoGetCompatibilitySensorRRMM() throws Exception {
		Mockito.doThrow(ImiException.class).when(myBatisTemplateGap.execute(Mockito.any(), Mockito.any()));
		service.getCompatibilitySensorRRMM();
	}

	@Test
	public void caseOkSelectCompatibilitySensorMcf() throws Exception {
		service.selectCompatibilitySensorMcf();
	}
	
	@Test(expected = Exception.class)
	public void caseKoSelectCompatibilitySensorMcf() throws Exception {
		Mockito.doThrow(ImiException.class).when(service.getCompatibilitySensorMcf());
		service.selectCompatibilitySensorMcf();
	}

	@Test
	public void caseOkGetCompatibilitySensorMcf() throws Exception {
		service.getCompatibilitySensorMcf();
	}
	
	@Test(expected = Exception.class)
	public void caseKoGetCompatibilitySensorMcf() throws Exception {
		Mockito.doThrow(ImiException.class).when(myBatisTemplateGap.execute(Mockito.any(), Mockito.any()));
		service.getCompatibilitySensorMcf();
	}
	
}