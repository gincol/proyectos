package es.bcn.imi.framework.vigia.frontal.test.business.inventary.impl;

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

import es.bcn.imi.framework.vigia.frontal.business.inventary.impl.PhysicalModelServiceImpl;
import es.bcn.imi.framework.vigia.frontal.validator.ValidatorPhysicalModel;
import es.bcn.imi.framework.vigia.frontal.web.rest.config.RestServerConfig;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.ActuationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.AggregateAmortizationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.DocumentarySupportRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.PhysicalModelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.commons.BaseTransactionIdRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnPhysicalModelAmortizationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnPhysicalModelDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnPhysicalModelMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnPhysicalModelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.ActuationRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.AggregateAmortizationRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.DocumentarySupportRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.PhysicalModelRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.QueryParameterRDTOStub;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import es.bcn.vigia.fmw.libutils.services.Utils;
import es.bcn.vigia.fmw.libutils.services.rest.RestCall;
import es.bcn.vigia.fmw.libutils.validator.front.FrontValidatePhysicalModelService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RestServerConfig.class })
@TestPropertySource(locations = {"classpath:app/config/application.properties"})
@SuppressWarnings("unchecked")
@WebAppConfiguration
public class PhysicalModelServiceImplTest {
	
	@Mock
	private RestCall restCall;

	@Mock
	private Utils utils;
	
	@Mock
	private FrontValidatePhysicalModelService frontValidatePhysicalModelService;
	
	@Value("${url.api.orquestrador}")
	private String urlApiOrquestrador;

	@Value("${url.path.inventary}")
	private String pathInventary;

	@Value("${url.path.physicalmodel}")
	private String pathPhysicalModel;

	@Value("${url.path.physicalmodel.get}")
	private String pathPhysicalModelGet;

	@Value("${url.path.physicalmodel.getMassive}")
	private String pathPhysicalModelGetMassive;

	@Value("${url.path.physicalmodel.getDetailed}")
	private String pathPhysicalModelGetDetailed;

	@Value("${url.path.physicalmodel.getAmortization}")
	private String pathPhysicalModelGetAmortization;

	@Value("${url.path.physicalmodel.put}")
	private String pathPhysicalModelPut;

	@Value("${url.path.physicalmodel.delete}")
	private String pathPhysicalModelDelete;

	@Value("${url.path.physicalmodel.type}")
	private String pathPhysicalModelType;

	@Value("${url.path.physicalmodel.aggregate.amortization}")
	private String pathPhysicalModelAggregateAmortization;

	
	@Value("${url.path.documentary.support}")
	private String pathDocumentarySupport;

	@Value("${url.path.documentary.support.delete}")
	private String pathDocumentarySupportDelete;

	@Value("${url.path.documentary.support.get}")
	private String pathGetDocumentarySupport;


	@Value("${url.path.actuation}")
	private String pathActuation;

	@Value("${url.path.actuation.delete}")
	private String pathActuationDelete;


	@Mock
	private ValidatorPhysicalModel validator;
	
	@InjectMocks
	private PhysicalModelServiceImpl service;
	
	private ReturnRDTO returnRDTO;

	private ReturnRDTO returnRDTOKO;
	
	private PhysicalModelRDTO physicalModelRDTO;

	private AggregateAmortizationRDTO aggregateAmortizationRDTO;

	private QueryParameterRDTO queryParameterRDTO;
	
	private DocumentarySupportRDTO documentarySupportRDTO;
	
	private ActuationRDTO actuationRDTO;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		returnRDTO = ReturnRDTOStub.getSuccessMessage();
        physicalModelRDTO = PhysicalModelRDTOStub.defaultOne();
        returnRDTOKO = new ReturnRDTO();
		returnRDTOKO.setCode("KO");
		aggregateAmortizationRDTO = AggregateAmortizationRDTOStub.defaultOne();
		queryParameterRDTO = QueryParameterRDTOStub.defaultOne();
		documentarySupportRDTO = DocumentarySupportRDTOStub.defaultOne();
		actuationRDTO = ActuationRDTOStub.defaultOne();
	}
	
	@Test
	public void caseOkInsert() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathPhysicalModel", pathPhysicalModel);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathPhysicalModel);

		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(url, physicalModelRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		Mockito.when(frontValidatePhysicalModelService.validateSyntaxInsert(physicalModelRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateInsert(physicalModelRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.insert(physicalModelRDTO);
	}


	@Test
	public void caseInsertValidacionesSintaxKO() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathPhysicalModel", pathPhysicalModel);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathPhysicalModel);

		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(url, physicalModelRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		Mockito.when(frontValidatePhysicalModelService.validateSyntaxInsert(physicalModelRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateInsert(physicalModelRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.insert(physicalModelRDTO);
	}
	@Test(expected = ImiException.class)
	public void caseKoInsert() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.insert(physicalModelRDTO);
	}
	
	@Test
	public void caseOkUpdate() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathPhysicalModel", pathPhysicalModel);
		ReflectionTestUtils.setField(service, "pathPhysicalModelPut", pathPhysicalModelPut);
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathPhysicalModel)
				.concat(pathPhysicalModelPut);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", physicalModelRDTO.getCode());
		
		Mockito.when(restCall.executePUT(url, urlParams, physicalModelRDTO)).thenReturn(ss);
		Mockito.when(frontValidatePhysicalModelService.validateSyntaxUpdate(physicalModelRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateUpdate(physicalModelRDTO)).thenReturn(returnRDTO);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.update(physicalModelRDTO);
	}
	
	@Test
	public void caseUpdateKOValidations() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathPhysicalModel", pathPhysicalModel);
		ReflectionTestUtils.setField(service, "pathPhysicalModelPut", pathPhysicalModelPut);
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathPhysicalModel)
				.concat(pathPhysicalModelPut);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", physicalModelRDTO.getCode());
		
		Mockito.when(restCall.executePUT(url, urlParams, physicalModelRDTO)).thenReturn(ss);
		Mockito.when(frontValidatePhysicalModelService.validateSyntaxUpdate(physicalModelRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateUpdate(physicalModelRDTO)).thenReturn(returnRDTO);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.update(physicalModelRDTO);
	}
	@Test(expected = ImiException.class)
	public void caseKoUpdate() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.update(physicalModelRDTO);
	}
	
	@Test
	public void caseOkDelete() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathPhysicalModel", pathPhysicalModel);
		ReflectionTestUtils.setField(service, "pathPhysicalModelDelete", pathPhysicalModelDelete);
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathPhysicalModel)
				.concat(pathPhysicalModelDelete);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", physicalModelRDTO.getCode());
		
		Mockito.when(restCall.executeDELETE(url, urlParams, physicalModelRDTO)).thenReturn(ss);
		Mockito.when(frontValidatePhysicalModelService.validateSyntaxDelete(physicalModelRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateDelete(physicalModelRDTO)).thenReturn(returnRDTO);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.delete(physicalModelRDTO);
	}
	
	@Test
	public void caseDeleteKOValidations() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathPhysicalModel", pathPhysicalModel);
		ReflectionTestUtils.setField(service, "pathPhysicalModelDelete", pathPhysicalModelDelete);
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathPhysicalModel)
				.concat(pathPhysicalModelDelete);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", physicalModelRDTO.getCode());
		
		Mockito.when(restCall.executeDELETE(url, urlParams, physicalModelRDTO)).thenReturn(ss);
		Mockito.when(frontValidatePhysicalModelService.validateSyntaxDelete(physicalModelRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateDelete(physicalModelRDTO)).thenReturn(returnRDTO);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.delete(physicalModelRDTO);
	}
	@Test(expected = ImiException.class)
	public void caseKoDelete() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.delete(physicalModelRDTO);
	}
	
	
	@Test
	public void caseOkSelect() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathPhysicalModel", pathPhysicalModel);
		ReflectionTestUtils.setField(service, "pathPhysicalModelGet", pathPhysicalModelGet);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathPhysicalModel)
				.concat(pathPhysicalModelGet);
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", physicalModelRDTO.getCode());
		
		Mockito.when(restCall.executeGET(url, urlParams, physicalModelRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(Mockito.any(ss.getClass()), Mockito.any(ReturnPhysicalModelRDTO.class))).thenReturn(new ReturnPhysicalModelRDTO());
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.select(physicalModelRDTO);
	}
	
	@Test(expected = ImiException.class)
	public void caseKoSelect() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.select(physicalModelRDTO);
	}

	@Test
	public void caseOkInsertAmortitzation() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathPhysicalModel", pathPhysicalModel);
		ReflectionTestUtils.setField(service, "pathPhysicalModelType", pathPhysicalModelType);
		ReflectionTestUtils.setField(service, "pathPhysicalModelAggregateAmortization", pathPhysicalModelAggregateAmortization);
		

		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(Mockito.anyString(), Mockito.any(aggregateAmortizationRDTO.getClass()))).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		Mockito.when(frontValidatePhysicalModelService.validateSyntaxInsert(aggregateAmortizationRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateInsert(aggregateAmortizationRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.insert(aggregateAmortizationRDTO);
	}

	@Test
	public void caseInsertAmortitzationValidacionesSintaxKO() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathPhysicalModel", pathPhysicalModel);
		ReflectionTestUtils.setField(service, "pathPhysicalModelType", pathPhysicalModelType);
		ReflectionTestUtils.setField(service, "pathPhysicalModelAggregateAmortization", pathPhysicalModelAggregateAmortization);

		
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(Mockito.anyString(), Mockito.any(aggregateAmortizationRDTO.getClass()))).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		Mockito.when(frontValidatePhysicalModelService.validateSyntaxInsert(aggregateAmortizationRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateInsert(aggregateAmortizationRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.insert(aggregateAmortizationRDTO);
	}
	
	@Test(expected = ImiException.class)
	public void caseKoInsertAmortitzation() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.insert(aggregateAmortizationRDTO);
	}
	
	@Test
	public void caseOkSelectMassive() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathPhysicalModel", pathPhysicalModel);
		ReflectionTestUtils.setField(service, "pathPhysicalModelGetMassive", pathPhysicalModelGetMassive);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathPhysicalModel)
				.concat(pathPhysicalModelGetMassive);
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", queryParameterRDTO.getCodeContract());
		Mockito.when(frontValidatePhysicalModelService.validateSyntaxSelectMassive(queryParameterRDTO)).thenReturn(returnRDTO);
		Mockito.when(restCall.executeGET(url, urlParams, queryParameterRDTO)).thenReturn(ss);
		
		ReturnPhysicalModelMassiveRDTO returnPhysicalModelMassiveRDTO = new ReturnPhysicalModelMassiveRDTO();
		returnPhysicalModelMassiveRDTO.setReturnRDTO(returnRDTO);
		Mockito.when(utils.rest2Object(Mockito.any(ss.getClass()), Mockito.any(returnPhysicalModelMassiveRDTO.getClass()))).thenReturn(returnPhysicalModelMassiveRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.selectMassive(queryParameterRDTO);
	}

	@Test
	public void caseSelectMassiveKOValidateSyntax() throws ImiException {
		Mockito.when(frontValidatePhysicalModelService.validateSyntaxSelectMassive(queryParameterRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.selectMassive(queryParameterRDTO);
	}

	@Test(expected = ImiException.class)
	public void caseKoSelectMassive() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.selectMassive(queryParameterRDTO);
	}
	
	@Test
	public void caseOkSelectDetailed() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathPhysicalModel", pathPhysicalModel);
		ReflectionTestUtils.setField(service, "pathPhysicalModelGetDetailed", pathPhysicalModelGetDetailed);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathPhysicalModel)
				.concat(pathPhysicalModelGetDetailed);
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", queryParameterRDTO.getCode());
		urlParams.put("codiContracta", queryParameterRDTO.getCodeContract());
		
		Mockito.when(frontValidatePhysicalModelService.validateSyntaxSelectDetailed(queryParameterRDTO)).thenReturn(returnRDTO);
		Mockito.when(restCall.executeGET(url, urlParams, queryParameterRDTO)).thenReturn(ss);
		
		ReturnPhysicalModelDetailedRDTO physicalModelDetailedRDTO = new ReturnPhysicalModelDetailedRDTO();
		physicalModelDetailedRDTO.setReturnRDTO(returnRDTO);
		Mockito.when(utils.rest2Object(Mockito.any(ss.getClass()), Mockito.any(physicalModelDetailedRDTO.getClass()))).thenReturn(physicalModelDetailedRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.selectDetailed(queryParameterRDTO);
	}

	@Test
	public void caseSelectDetailedKoValidateSyntax() throws ImiException {
		Mockito.when(frontValidatePhysicalModelService.validateSyntaxSelectDetailed(queryParameterRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.selectDetailed(queryParameterRDTO);
	}

	@Test(expected = ImiException.class)
	public void caseKoSelectDetailed() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.selectDetailed(queryParameterRDTO);
	}
	
	@Test
	public void caseOkSelectAmortization() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathPhysicalModel", pathPhysicalModel);
		ReflectionTestUtils.setField(service, "pathPhysicalModelGetAmortization", pathPhysicalModelGetAmortization);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathPhysicalModel)
				.concat(pathPhysicalModelGetAmortization);
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", queryParameterRDTO.getCode());
		urlParams.put("codiContracta", queryParameterRDTO.getCodeContract());
		
		Mockito.when(frontValidatePhysicalModelService.validateSyntaxSelectAmortization(queryParameterRDTO)).thenReturn(returnRDTO);
		Mockito.when(restCall.executeGET(url, urlParams, queryParameterRDTO)).thenReturn(ss);
		
		ReturnPhysicalModelAmortizationRDTO physicalModelAmortizationRDTO = new ReturnPhysicalModelAmortizationRDTO();
		physicalModelAmortizationRDTO.setReturnRDTO(returnRDTO);
		Mockito.when(utils.rest2Object(Mockito.any(ss.getClass()), Mockito.any(physicalModelAmortizationRDTO.getClass()))).thenReturn(physicalModelAmortizationRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.selectAmortization(queryParameterRDTO);
	}

	@Test
	public void caseSelectAmortizationKoValidateSyntax() throws ImiException {
		Mockito.when(frontValidatePhysicalModelService.validateSyntaxSelectAmortization(queryParameterRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.selectAmortization(queryParameterRDTO);
	}

	@Test(expected = ImiException.class)
	public void caseKoSelectAmortization() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.selectAmortization(queryParameterRDTO);
	}

	@Test
	public void caseOkInsertSupportDocumental() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathDocumentarySupport", pathDocumentarySupport);
		
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathDocumentarySupport);

		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(url, documentarySupportRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		Mockito.when(frontValidatePhysicalModelService.validateSyntaxInsertDocumentarySupport(documentarySupportRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateInsertDocumentarySupport(documentarySupportRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.insertDocumentalSupport(documentarySupportRDTO);
	}

	@Test
	public void caseInsertSupporDocumentaltKoValidateSyntax() throws ImiException {
		Mockito.when(frontValidatePhysicalModelService.validateSyntaxInsertDocumentarySupport(documentarySupportRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.insertDocumentalSupport(documentarySupportRDTO);
	}
	@Test(expected = ImiException.class)
	public void caseKoInsertSupporDocumentalt() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.insertDocumentalSupport(documentarySupportRDTO);
	}

	@Test
	public void caseOkInsertActuation() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathActuation", pathActuation);
		
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(Mockito.anyString(), Mockito.any(actuationRDTO.getClass()))).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		Mockito.when(frontValidatePhysicalModelService.validateSyntaxInsertActuation(actuationRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateInsertActuation(actuationRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.insertActuation(actuationRDTO);
	}

	@Test
	public void caseInsertActuationKoValidateSyntax() throws ImiException {
		Mockito.when(frontValidatePhysicalModelService.validateSyntaxInsertActuation(actuationRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.insertActuation(actuationRDTO);
	}
	@Test(expected = ImiException.class)
	public void caseKoInsertActuation() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.insertActuation(actuationRDTO);
	}
	
	@Test
	public void caseOkDeleteActuation() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathActuation", pathActuation);
		ReflectionTestUtils.setField(service, "pathActuationDelete", pathActuationDelete);
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", actuationRDTO.getCode());
		urlParams.put("codiEntitat", physicalModelRDTO.getCode());
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		Mockito.when(restCall.executePUT(Mockito.anyString(), Mockito.anyMap(), Mockito.any(actuationRDTO.getClass()))).thenReturn(ss);
		Mockito.when(frontValidatePhysicalModelService.validateSyntaxDeleteActuation(actuationRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateDeleteActuation(actuationRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.deleteActuation(actuationRDTO);
	}
	
	@Test
	public void caseDeleteActuationKoValidateSyntax() throws ImiException {
		Mockito.when(frontValidatePhysicalModelService.validateSyntaxDeleteActuation(actuationRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.deleteActuation(actuationRDTO);
	}
	
	@Test(expected = ImiException.class)
	public void caseKoDeleteActuation() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.deleteActuation(actuationRDTO);
	}
	
	@Test
	public void caseOkDeleteDocumentarySupport() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathDocumentarySupport", pathDocumentarySupport);
		ReflectionTestUtils.setField(service, "pathDocumentarySupportDelete", pathDocumentarySupportDelete);
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", documentarySupportRDTO.getCode());
		urlParams.put("codiEntitat", physicalModelRDTO.getCode());
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		Mockito.when(restCall.executePUT(Mockito.anyString(), Mockito.anyMap(), Mockito.any(documentarySupportRDTO.getClass()))).thenReturn(ss);
		Mockito.when(frontValidatePhysicalModelService.validateSyntaxDeleteDocumentarySupport(documentarySupportRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateDeleteDocumentarySupport(documentarySupportRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.deleteDocumentalSupport(documentarySupportRDTO);
	}
	
	@Test
	public void caseDeleteDocumentarySupportKoValidateSyntax() throws ImiException {
		Mockito.when(frontValidatePhysicalModelService.validateSyntaxDeleteDocumentarySupport(documentarySupportRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.deleteDocumentalSupport(documentarySupportRDTO);
	}
	
	@Test(expected = ImiException.class)
	public void caseKoDeleteDocumentarySupport() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.deleteDocumentalSupport(documentarySupportRDTO);
	}

}
