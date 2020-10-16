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

import es.bcn.imi.framework.vigia.frontal.business.inventary.impl.LogicalModelServiceImpl;
import es.bcn.imi.framework.vigia.frontal.validator.ValidatorLogicalModel;
import es.bcn.imi.framework.vigia.frontal.web.rest.config.RestServerConfig;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.ActuationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.DocumentarySupportRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.LogicalModelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.commons.BaseTransactionIdRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnLogicalModelDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnLogicalModelMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnLogicalModelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.ActuationRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.DocumentarySupportRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.LogicalModelRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.QueryParameterRDTOStub;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import es.bcn.vigia.fmw.libutils.services.Utils;
import es.bcn.vigia.fmw.libutils.services.rest.RestCall;
import es.bcn.vigia.fmw.libutils.validator.front.FrontValidateLogicalModelService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RestServerConfig.class })
@TestPropertySource(locations = {"classpath:app/config/application.properties"})
@SuppressWarnings("unchecked")
@WebAppConfiguration
public class LogicalModelServiceImplTest {

	@Mock
	private RestCall restCall;

	@Mock
	private Utils utils;
	
	@Mock
	private FrontValidateLogicalModelService frontValidateLogicalModelService;

	@Mock
	private ValidatorLogicalModel validator;
	
	@InjectMocks
	private LogicalModelServiceImpl service;
	
	@Value("${url.api.orquestrador}")
	private String urlApiOrquestrador;

	@Value("${url.path.inventary}")
	private String pathInventary;

	@Value("${url.path.logicmodel}")
	private String pathLogicalModel;

	@Value("${url.path.logicmodel.get}")
	private String pathLogicalModelGet;

	@Value("${url.path.logicmodel.getMassive}")
	private String pathLogicalModelGetMassive;

	@Value("${url.path.logicmodel.getDetailed}")
	private String pathLogicalModelGetDetailed;

	@Value("${url.path.logicmodel.put}")
	private String pathLogicalModelPut;

	@Value("${url.path.logicmodel.delete}")
	private String pathLogicalModelDelete;
	
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
	
	
		
	private ReturnRDTO returnRDTO;
	
	private ReturnRDTO returnRDTOKO;
	
	private LogicalModelRDTO logicalModelRDTO;

	private QueryParameterRDTO queryParameterRDTO;

	private ReturnLogicalModelRDTO returnLogicalModelRDTO;
	
	private DocumentarySupportRDTO documentarySupportRDTO;

	private ActuationRDTO actuationRDTO;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		returnRDTO = ReturnRDTOStub.getSuccessMessage();
		returnRDTOKO = ReturnRDTOStub.getErrorMessage();
		logicalModelRDTO = LogicalModelRDTOStub.defaultOne();
		returnRDTO.setTransactionId(logicalModelRDTO.getCode());
		queryParameterRDTO = QueryParameterRDTOStub.defaultOne();
		returnLogicalModelRDTO = new ReturnLogicalModelRDTO();
		documentarySupportRDTO = DocumentarySupportRDTOStub.defaultOne();
		actuationRDTO = ActuationRDTOStub.defaultOne();
	}
	
	@Test
	public void caseOkInsert() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathLogicalModel", pathLogicalModel);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathLogicalModel);

		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(url, logicalModelRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		Mockito.when(frontValidateLogicalModelService.validateSyntaxInsert(logicalModelRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateInsert(logicalModelRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.insert(logicalModelRDTO);
	}

	@Test
	public void caseInsertKoValidateSyntax() throws ImiException {
		Mockito.when(frontValidateLogicalModelService.validateSyntaxInsert(logicalModelRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.insert(logicalModelRDTO);
	}
	@Test(expected = ImiException.class)
	public void caseKoInsert() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.insert(logicalModelRDTO);
	}
	
	@Test 
	public void caseOkUpdate() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathLogicalModel", pathLogicalModel);
		ReflectionTestUtils.setField(service, "pathLogicalModelPut", pathLogicalModelPut);
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathLogicalModel)
				.concat(pathLogicalModelPut);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", logicalModelRDTO.getCode());
		
		Mockito.when(restCall.executePUT(url, urlParams, logicalModelRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		Mockito.when(frontValidateLogicalModelService.validateSyntaxUpdate(logicalModelRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateUpdate(logicalModelRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.update(logicalModelRDTO);
	}
	
	@Test 
	public void caseUpdateKoValidateSyntax() throws ImiException {
		Mockito.when(frontValidateLogicalModelService.validateSyntaxUpdate(logicalModelRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.update(logicalModelRDTO);
	}
	@Test(expected = ImiException.class)
	public void caseKoUpdate() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.update(logicalModelRDTO);
	}
	
	@Test
	public void caseOkDelete() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathLogicalModel", pathLogicalModel);
		ReflectionTestUtils.setField(service, "pathLogicalModelDelete", pathLogicalModelDelete);
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathLogicalModel)
				.concat(pathLogicalModelDelete);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", logicalModelRDTO.getCode());
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		Mockito.when(restCall.executeDELETE(url, urlParams, logicalModelRDTO)).thenReturn(ss);
		Mockito.when(frontValidateLogicalModelService.validateSyntaxDelete(logicalModelRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateDelete(logicalModelRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.delete(logicalModelRDTO);
	}
	
	@Test
	public void caseDeleteKoValidateSyntax() throws ImiException {
		Mockito.when(frontValidateLogicalModelService.validateSyntaxDelete(logicalModelRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.delete(logicalModelRDTO);
	}
	
	@Test(expected = ImiException.class)
	public void caseKoDelete() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.delete(logicalModelRDTO);
	}
	
	@Test
	public void caseOkSelect() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathLogicalModel", pathLogicalModel);
		ReflectionTestUtils.setField(service, "pathLogicalModelGet", pathLogicalModelGet);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathLogicalModel)
				.concat(pathLogicalModelGet);
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", logicalModelRDTO.getCode());
		Mockito.when(utils.rest2Object(Mockito.any(ss.getClass()), Mockito.any(returnLogicalModelRDTO.getClass()))).thenReturn(returnLogicalModelRDTO);
		Mockito.when(restCall.executeGET(url, urlParams, logicalModelRDTO)).thenReturn(ss);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.select(logicalModelRDTO);
	}
	
	@Test(expected = ImiException.class)
	public void caseKoSelect() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.select(logicalModelRDTO);
	}
	
	@Test
	public void caseOkSelectMassive() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathLogicalModel", pathLogicalModel);
		ReflectionTestUtils.setField(service, "pathLogicalModelGetMassive", pathLogicalModelGetMassive);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathLogicalModel)
				.concat(pathLogicalModelGetMassive);
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", queryParameterRDTO.getCodeContract());
		
		Mockito.when(frontValidateLogicalModelService.validateSyntaxSelectMassive(queryParameterRDTO)).thenReturn(returnRDTO);
		Mockito.when(restCall.executeGET(url, urlParams, queryParameterRDTO)).thenReturn(ss);
		
		ReturnLogicalModelMassiveRDTO returnLogicalModelMassiveRDTO = new ReturnLogicalModelMassiveRDTO();
		returnLogicalModelMassiveRDTO.setReturnRDTO(returnRDTO);
		Mockito.when(utils.rest2Object(Mockito.any(ss.getClass()), Mockito.any(returnLogicalModelMassiveRDTO.getClass()))).thenReturn(returnLogicalModelMassiveRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.selectMassive(queryParameterRDTO);
	}
	
	
	@Test
	public void caseSelectMassiveKoValidations() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathLogicalModel", pathLogicalModel);
		ReflectionTestUtils.setField(service, "pathLogicalModelGetMassive", pathLogicalModelGetMassive);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathLogicalModel)
				.concat(pathLogicalModelGetMassive);
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", queryParameterRDTO.getCodeContract());
		
		Mockito.when(frontValidateLogicalModelService.validateSyntaxSelectMassive(queryParameterRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(restCall.executeGET(url, urlParams, queryParameterRDTO)).thenReturn(ss);
		
		ReturnLogicalModelMassiveRDTO returnLogicalModelMassiveRDTO = new ReturnLogicalModelMassiveRDTO();
		returnLogicalModelMassiveRDTO.setReturnRDTO(returnRDTO);
		Mockito.when(utils.rest2Object(Mockito.any(ss.getClass()), Mockito.any(returnLogicalModelMassiveRDTO.getClass()))).thenReturn(returnLogicalModelMassiveRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.selectMassive(queryParameterRDTO);
	}
	
	@Test(expected = ImiException.class)
	public void caseSelectMassiveKo() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathLogicalModel", pathLogicalModel);
		ReflectionTestUtils.setField(service, "pathLogicalModelGetMassive", pathLogicalModelGetMassive);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathLogicalModel)
				.concat(pathLogicalModelGetMassive);
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", queryParameterRDTO.getCodeContract());
		
		
		Mockito.doThrow(ImiException.class).when(frontValidateLogicalModelService).validateSyntaxSelectMassive(queryParameterRDTO);
		Mockito.when(restCall.executeGET(url, urlParams, queryParameterRDTO)).thenReturn(ss);
		
		ReturnLogicalModelMassiveRDTO returnLogicalModelMassiveRDTO = new ReturnLogicalModelMassiveRDTO();
		returnLogicalModelMassiveRDTO.setReturnRDTO(returnRDTO);
		Mockito.when(utils.rest2Object(Mockito.any(ss.getClass()), Mockito.any(returnLogicalModelMassiveRDTO.getClass()))).thenReturn(returnLogicalModelMassiveRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.selectMassive(queryParameterRDTO);
	}
	
	@Test
	public void caseOkSelectDetailed() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathLogicalModel", pathLogicalModel);
		ReflectionTestUtils.setField(service, "pathLogicalModelGetDetailed", pathLogicalModelGetDetailed);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathLogicalModel)
				.concat(pathLogicalModelGetDetailed);
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", queryParameterRDTO.getCode());
		urlParams.put("codiContracta", queryParameterRDTO.getCodeContract());
		
		Mockito.when(frontValidateLogicalModelService.validateSyntaxSelectDetailed(queryParameterRDTO)).thenReturn(returnRDTO);
		Mockito.when(restCall.executeGET(url, urlParams, queryParameterRDTO)).thenReturn(ss);
		
		ReturnLogicalModelDetailedRDTO returnLogicalModelDetailedRDTO = new ReturnLogicalModelDetailedRDTO();
		returnLogicalModelDetailedRDTO.setReturnRDTO(returnRDTO);
		Mockito.when(utils.rest2Object(Mockito.any(ss.getClass()), Mockito.any(returnLogicalModelDetailedRDTO.getClass()))).thenReturn(returnLogicalModelDetailedRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.selectDetailed(queryParameterRDTO);
	}
	
	@Test
	public void caseSelectDetailedKoValidations() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathLogicalModel", pathLogicalModel);
		ReflectionTestUtils.setField(service, "pathLogicalModelGetDetailed", pathLogicalModelGetDetailed);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathLogicalModel)
				.concat(pathLogicalModelGetDetailed);
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", queryParameterRDTO.getCode());
		urlParams.put("codiContracta", queryParameterRDTO.getCodeContract());
		
		Mockito.when(frontValidateLogicalModelService.validateSyntaxSelectDetailed(queryParameterRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(restCall.executeGET(url, urlParams, queryParameterRDTO)).thenReturn(ss);
		
		ReturnLogicalModelDetailedRDTO returnLogicalModelDetailedRDTO = new ReturnLogicalModelDetailedRDTO();
		returnLogicalModelDetailedRDTO.setReturnRDTO(returnRDTO);
		Mockito.when(utils.rest2Object(Mockito.any(ss.getClass()), Mockito.any(returnLogicalModelDetailedRDTO.getClass()))).thenReturn(returnLogicalModelDetailedRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.selectDetailed(queryParameterRDTO);
	}
	
	@Test(expected = ImiException.class)
	public void caseSelectDetailedKO() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathLogicalModel", pathLogicalModel);
		ReflectionTestUtils.setField(service, "pathLogicalModelGetDetailed", pathLogicalModelGetDetailed);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathLogicalModel)
				.concat(pathLogicalModelGetDetailed);
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", queryParameterRDTO.getCode());
		urlParams.put("codiContracta", queryParameterRDTO.getCodeContract());
		
		Mockito.doThrow(ImiException.class).when(frontValidateLogicalModelService).validateSyntaxSelectDetailed(queryParameterRDTO);
		Mockito.when(restCall.executeGET(url, urlParams, queryParameterRDTO)).thenReturn(ss);
		
		ReturnLogicalModelDetailedRDTO returnLogicalModelDetailedRDTO = new ReturnLogicalModelDetailedRDTO();
		returnLogicalModelDetailedRDTO.setReturnRDTO(returnRDTO);
		Mockito.when(utils.rest2Object(Mockito.any(ss.getClass()), Mockito.any(returnLogicalModelDetailedRDTO.getClass()))).thenReturn(returnLogicalModelDetailedRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.selectDetailed(queryParameterRDTO);
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
		Mockito.when(frontValidateLogicalModelService.validateSyntaxInsertDocumentarySupport(documentarySupportRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateInsertDocumentarySupport(documentarySupportRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.insertDocumentalSupport(documentarySupportRDTO);
	}

	@Test
	public void caseInsertSupporDocumentaltKoValidateSyntax() throws ImiException {
		Mockito.when(frontValidateLogicalModelService.validateSyntaxInsertDocumentarySupport(documentarySupportRDTO)).thenReturn(returnRDTOKO);
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
		Mockito.when(frontValidateLogicalModelService.validateSyntaxInsertActuation(actuationRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateInsertActuation(actuationRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.insertActuation(actuationRDTO);
	}

	@Test
	public void caseInsertActuationKoValidateSyntax() throws ImiException {
		Mockito.when(frontValidateLogicalModelService.validateSyntaxInsertActuation(actuationRDTO)).thenReturn(returnRDTOKO);
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
		urlParams.put("codiEntitat", logicalModelRDTO.getCode());
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		Mockito.when(restCall.executePUT(Mockito.anyString(), Mockito.anyMap(), Mockito.any(actuationRDTO.getClass()))).thenReturn(ss);
		Mockito.when(frontValidateLogicalModelService.validateSyntaxDeleteActuation(actuationRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateDeleteActuation(actuationRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.deleteActuation(actuationRDTO);
	}
	
	@Test
	public void caseDeleteActuationKoValidateSyntax() throws ImiException {
		Mockito.when(frontValidateLogicalModelService.validateSyntaxDeleteActuation(actuationRDTO)).thenReturn(returnRDTOKO);
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
		urlParams.put("codiEntitat", logicalModelRDTO.getCode());
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		Mockito.when(restCall.executePUT(Mockito.anyString(), Mockito.anyMap(), Mockito.any(documentarySupportRDTO.getClass()))).thenReturn(ss);
		Mockito.when(frontValidateLogicalModelService.validateSyntaxDeleteDocumentarySupport(documentarySupportRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateDeleteDocumentarySupport(documentarySupportRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.deleteDocumentalSupport(documentarySupportRDTO);
	}
	
	@Test
	public void caseDeleteDocumentarySupportKoValidateSyntax() throws ImiException {
		Mockito.when(frontValidateLogicalModelService.validateSyntaxDeleteDocumentarySupport(documentarySupportRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.deleteDocumentalSupport(documentarySupportRDTO);
	}
	
	@Test(expected = ImiException.class)
	public void caseKoDeleteDocumentarySupport() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.deleteDocumentalSupport(documentarySupportRDTO);
	}
}
