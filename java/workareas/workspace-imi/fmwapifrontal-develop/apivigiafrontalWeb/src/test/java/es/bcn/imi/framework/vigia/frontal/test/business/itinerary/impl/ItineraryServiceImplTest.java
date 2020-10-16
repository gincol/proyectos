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

import es.bcn.imi.framework.vigia.frontal.business.itinerary.impl.ItineraryServiceImpl;
import es.bcn.imi.framework.vigia.frontal.validator.ValidatorItinerary;
import es.bcn.imi.framework.vigia.frontal.web.rest.config.RestServerConfig;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.commons.BaseTransactionIdRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnItineraryDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnItineraryMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.QueryParameterRDTOStub;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import es.bcn.vigia.fmw.libutils.services.Utils;
import es.bcn.vigia.fmw.libutils.services.rest.RestCall;
import es.bcn.vigia.fmw.libutils.validator.front.FrontValidateItineraryService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RestServerConfig.class })
@TestPropertySource(locations = {"classpath:app/config/application.properties"})
@WebAppConfiguration
public class ItineraryServiceImplTest {
	
	@Value("${url.api.orquestrador}")
	private String urlApiOrquestrador;

	@Value("${url.path.itinerary}")
	private String pathItinerary;

	@Value("${url.path.itinerary.getMassive}")
	private String pathItineraryGetMassive;

	@Value("${url.path.itinerary.getDetailed}")
	private String pathItineraryGetDetailed;
	
	@Mock
	private RestCall restCall;
	
	@Mock
	private Utils utils;

	@Mock
	private FrontValidateItineraryService frontValidateItineraryService;
	
	
	@Mock
	private ValidatorItinerary validator;

	@InjectMocks
	private ItineraryServiceImpl service;
	
	private ReturnRDTO returnRDTO;
	
	private ReturnRDTO returnRDTOKO;

	private QueryParameterRDTO queryParameterRDTO;	
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		returnRDTO = ReturnRDTOStub.getSuccessMessage();
		returnRDTOKO = ReturnRDTOStub.getErrorMessage();
		queryParameterRDTO = QueryParameterRDTOStub.defaultOne();		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void caseOkSelectMassive() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathItinerary", pathItinerary);
		ReflectionTestUtils.setField(service, "pathItineraryGetMassive", pathItineraryGetMassive);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathItinerary)
				.concat(pathItineraryGetMassive);
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", queryParameterRDTO.getCode());
		urlParams.put("codiContracta", queryParameterRDTO.getCodeContract());
		
		Mockito.when(frontValidateItineraryService.validateSyntaxSelectMassive(queryParameterRDTO)).thenReturn(returnRDTO);
		Mockito.when(restCall.executeGET(url, urlParams, queryParameterRDTO)).thenReturn(ss);
		
		ReturnItineraryMassiveRDTO returnItineraryMassiveRDTO = new ReturnItineraryMassiveRDTO();
		returnItineraryMassiveRDTO.setReturnRDTO(returnRDTO);
		Mockito.when(utils.rest2Object(Mockito.any(ss.getClass()), Mockito.any(returnItineraryMassiveRDTO.getClass()))).thenReturn(returnItineraryMassiveRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.selectMassive(queryParameterRDTO);
	}

	@Test
	public void caseSelectMassiveKoValidateSyntax() throws ImiException {
		Mockito.when(frontValidateItineraryService.validateSyntaxSelectMassive(queryParameterRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.selectMassive(queryParameterRDTO);
	}

	@Test(expected = ImiException.class)
	public void caseKoSelectMassive() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.selectMassive(queryParameterRDTO);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void caseOkSelectDetailed() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathItinerary", pathItinerary);
		ReflectionTestUtils.setField(service, "pathItineraryGetDetailed", pathItineraryGetDetailed);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathItinerary)
				.concat(pathItineraryGetDetailed);
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", queryParameterRDTO.getCode());
		urlParams.put("codiContracta", queryParameterRDTO.getCodeContract());
		
		Mockito.when(frontValidateItineraryService.validateSyntaxSelectDetailed(queryParameterRDTO)).thenReturn(returnRDTO);
		Mockito.when(restCall.executeGET(url, urlParams, queryParameterRDTO)).thenReturn(ss);
		
		ReturnItineraryDetailedRDTO returnItineraryDetailedRDTO = new ReturnItineraryDetailedRDTO();
		returnItineraryDetailedRDTO.setReturnRDTO(returnRDTO);
		Mockito.when(utils.rest2Object(Mockito.any(ss.getClass()), Mockito.any(returnItineraryDetailedRDTO.getClass()))).thenReturn(returnItineraryDetailedRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.selectDetailed(queryParameterRDTO);
	}

	@Test
	public void caseSelectDetailedKoValidateSyntax() throws ImiException {
		Mockito.when(frontValidateItineraryService.validateSyntaxSelectDetailed(queryParameterRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.selectDetailed(queryParameterRDTO);
	}

	@Test(expected = ImiException.class)
	public void caseKoSelectDetailed() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.selectDetailed(queryParameterRDTO);
	}

}