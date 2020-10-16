package es.bcn.imi.framework.vigia.frontal.test.business.itinerary.impl;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;

import es.bcn.imi.framework.vigia.frontal.business.itinerary.impl.PlanningServiceImpl;
import es.bcn.imi.framework.vigia.frontal.validator.ValidatorPlanning;
import es.bcn.imi.framework.vigia.frontal.web.rest.config.RestServerConfig;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.PlanningRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterPlanningRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.commons.BaseTransactionIdRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnPlanningDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnPlanningMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.PlanningRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.QueryParameterPlanningRDTOStub;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import es.bcn.vigia.fmw.libutils.services.Utils;
import es.bcn.vigia.fmw.libutils.services.rest.RestCall;
import es.bcn.vigia.fmw.libutils.validator.front.FrontValidatePlanningService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RestServerConfig.class })
@TestPropertySource(locations = {"classpath:app/config/application.properties"})
@WebAppConfiguration
public class PlanningServiceImplTest {
	
	@Value("${url.api.orquestrador}")
	private String urlApiOrquestrador;

	@Value("${url.path.itinerary}")
	private String pathItinerary;

	@Value("${url.path.planning}")
	private String pathPlanning;

	@Value("${url.path.planning.getMassive}")
	private String pathPlanningGetMassive;

	@Value("${url.path.planning.getDetailed}")
	private String pathPlanningGetDetailed;
	
	@Mock
	private RestCall restCall;
	
	@Mock
	private Utils utils;

	@Mock
	private FrontValidatePlanningService frontValidatePlanningService;
	
	@Mock
	private ValidatorPlanning validatorPlanning;

	@InjectMocks
	private PlanningServiceImpl service;
	
	private ReturnRDTO returnRDTO;
	
	private ReturnRDTO returnRDTOKO;
	
	private PlanningRDTO planningRDTO;

	private QueryParameterPlanningRDTO queryParameterPlanningRDTO;	
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		returnRDTO = ReturnRDTOStub.getSuccessMessage();
		planningRDTO = PlanningRDTOStub.defaultOne();
		returnRDTOKO = ReturnRDTOStub.getErrorMessage();
		queryParameterPlanningRDTO = QueryParameterPlanningRDTOStub.defaultOne();		
	}
	
	@Test
	public void caseOkInsert() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathItinerary", pathItinerary);
		ReflectionTestUtils.setField(service, "pathPlanning", pathPlanning);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathItinerary)
				.concat(pathPlanning);

		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(url, planningRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		Mockito.when(frontValidatePlanningService.validateSyntaxInsert(planningRDTO)).thenReturn(returnRDTO);
		Mockito.when(validatorPlanning.validateInsert(planningRDTO)).thenReturn(returnRDTO);
		Mockito.when(validatorPlanning.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.insert(planningRDTO);
	}

	@Test
	public void caseKoInsertAnnulmentValidationsSyntax() throws ImiException {
		Mockito.when(frontValidatePlanningService.validateSyntaxInsert(planningRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validatorPlanning.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.insert(planningRDTO);
	}
	
	@Test(expected = ImiException.class)
	public void caseKoInsertAnnulment() throws ImiException {
		Mockito.when(validatorPlanning.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.insert(planningRDTO);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void caseOkSelectMassive() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathItinerary", pathItinerary);
		ReflectionTestUtils.setField(service, "pathPlanning", pathPlanning);
		ReflectionTestUtils.setField(service, "pathPlanningGetMassive", pathPlanningGetMassive);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathItinerary)
				.concat(pathPlanning)
				.concat(pathPlanningGetMassive);
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", queryParameterPlanningRDTO.getCode());
		urlParams.put("codiContracta", queryParameterPlanningRDTO.getCodeContract());
		
		Mockito.when(frontValidatePlanningService.validateSyntaxSelectMassive(queryParameterPlanningRDTO)).thenReturn(returnRDTO);
		Mockito.when(restCall.executeGET(url, urlParams, queryParameterPlanningRDTO)).thenReturn(ss);
		
		ReturnPlanningMassiveRDTO returnPlanningMassiveRDTO = new ReturnPlanningMassiveRDTO();
		returnPlanningMassiveRDTO.setReturnRDTO(returnRDTO);
		Mockito.when(utils.rest2Object(Mockito.any(ss.getClass()), Mockito.any(returnPlanningMassiveRDTO.getClass()))).thenReturn(returnPlanningMassiveRDTO);
		Mockito.when(validatorPlanning.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.selectMassive(queryParameterPlanningRDTO);
	}

	@Test
	public void caseSelectMassiveKoValidateSyntax() throws ImiException {
		Mockito.when(frontValidatePlanningService.validateSyntaxSelectMassive(queryParameterPlanningRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validatorPlanning.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.selectMassive(queryParameterPlanningRDTO);
	}

	@Test(expected = ImiException.class)
	public void caseKoSelectMassive() throws ImiException {
		Mockito.when(validatorPlanning.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.selectMassive(queryParameterPlanningRDTO);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void caseOkSelectDetailed() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathItinerary", pathItinerary);
		ReflectionTestUtils.setField(service, "pathPlanning", pathPlanning);
		ReflectionTestUtils.setField(service, "pathPlanningGetDetailed", pathPlanningGetDetailed);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathItinerary)
				.concat(pathPlanning)
				.concat(pathPlanningGetDetailed);
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", queryParameterPlanningRDTO.getCode());
		urlParams.put("codiContracta", queryParameterPlanningRDTO.getCodeContract());
		
		Mockito.when(frontValidatePlanningService.validateSyntaxSelectDetailed(queryParameterPlanningRDTO)).thenReturn(returnRDTO);
		Mockito.when(restCall.executeGET(url, urlParams, queryParameterPlanningRDTO)).thenReturn(ss);
		
		ReturnPlanningDetailedRDTO returnPlanningDetailedRDTO = new ReturnPlanningDetailedRDTO();
		returnPlanningDetailedRDTO.setReturnRDTO(returnRDTO);
		Mockito.when(utils.rest2Object(Mockito.any(ss.getClass()), Mockito.any(returnPlanningDetailedRDTO.getClass()))).thenReturn(returnPlanningDetailedRDTO);
		Mockito.when(validatorPlanning.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.selectDetailed(queryParameterPlanningRDTO);
	}

	@Test
	public void caseSelectDetailedKoValidateSyntax() throws ImiException {
		Mockito.when(frontValidatePlanningService.validateSyntaxSelectDetailed(queryParameterPlanningRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validatorPlanning.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.selectDetailed(queryParameterPlanningRDTO);
	}

	@Test(expected = ImiException.class)
	public void caseKoSelectDetailed() throws ImiException {
		Mockito.when(validatorPlanning.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.selectDetailed(queryParameterPlanningRDTO);
	}

}