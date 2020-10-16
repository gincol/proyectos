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

import es.bcn.imi.framework.vigia.frontal.business.inventary.impl.UbicationServiceImpl;
import es.bcn.imi.framework.vigia.frontal.validator.ValidatorUbication;
import es.bcn.imi.framework.vigia.frontal.web.rest.config.RestServerConfig;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.ActuationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.DocumentarySupportRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.UbicationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.commons.BaseTransactionIdRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnUbicationDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.ActuationRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.DocumentarySupportRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.QueryParameterRDTOStub;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import es.bcn.vigia.fmw.libutils.services.Utils;
import es.bcn.vigia.fmw.libutils.services.rest.RestCall;
import es.bcn.vigia.fmw.libutils.validator.front.FrontValidateUbicationService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RestServerConfig.class })
@TestPropertySource(locations = {"classpath:app/config/application.properties"})
@SuppressWarnings("unchecked")
@WebAppConfiguration
public class UbicationServiceImplTest {
	
	@Value("${url.api.orquestrador}")
	private String urlApiOrquestrador;

	@Value("${url.path.inventary}")
	private String pathInventary;

	@Value("${url.path.ubications}")
	private String pathUbication;

	@Value("${url.path.ubications.getMassive}")
	private String pathUbicationGetMassive;

	@Value("${url.path.ubications.getDetailed}")
	private String pathUbicationGetDetailed;

	@Value("${url.path.ubications.put}")
	private String pathUbicationPut;

	@Value("${url.path.ubications.delete}")
	private String pathUbicationDelete;

	@Value("${url.path.documentary.support}")
	private String pathDocumentarySupport;

	@Value("${url.path.documentary.support.delete}")
	private String pathDocumentarySupportDelete;

	@Value("${url.path.actuation}")
	private String pathActuation;

	@Value("${url.path.actuation.delete}")
	private String pathActuationDelete;
	
	@Mock
	private RestCall restCall;
	
	@Mock
	private Utils utils;

	@Mock
	private FrontValidateUbicationService frontValidateUbicationService;
	
	@Mock
	private ValidatorUbication validator;
	
	@InjectMocks
	private UbicationServiceImpl service;
	
	private ReturnRDTO returnRDTO;

	private ReturnRDTO returnRDTOKO;
	
	private UbicationRDTO ubicationRDTO;

	private QueryParameterRDTO queryParameterRDTO;

	private DocumentarySupportRDTO documentarySupportRDTO;

	private ActuationRDTO actuationRDTO;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		returnRDTO = ReturnRDTOStub.getSuccessMessage();
		returnRDTOKO = ReturnRDTOStub.getErrorMessage();
        ubicationRDTO = new UbicationRDTO();
        ubicationRDTO.setCode("codi");
		queryParameterRDTO = QueryParameterRDTOStub.defaultOne();
		documentarySupportRDTO = DocumentarySupportRDTOStub.defaultOne();
		actuationRDTO = ActuationRDTOStub.defaultOne();
	}
	
	@Test
	public void caseInsertKOValidaciones() throws ImiException {
		
		ReturnRDTO returnRDTOKO = returnRDTO;
		returnRDTOKO.setCode("KO");
		
		Mockito.when(frontValidateUbicationService.validateSyntaxInsert(ubicationRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.insert(ubicationRDTO);
	}

	@Test
	public void caseOkInsert() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathUbication", pathUbication);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathUbication);

		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(url, ubicationRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		Mockito.when(frontValidateUbicationService.validateSyntaxInsert(Mockito.any(ubicationRDTO.getClass()))).thenReturn(returnRDTO);
		Mockito.when(validator.validateInsert(ubicationRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.insert(ubicationRDTO);
	}

	@Test(expected = ImiException.class)
	public void caseKoInsert() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.insert(ubicationRDTO);
	}
	
	@Test
	public void caseOkUpdate() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathUbication", pathUbication);
		ReflectionTestUtils.setField(service, "pathUbicationPut", pathUbicationPut);
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathUbication)
				.concat(pathUbicationPut);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", ubicationRDTO.getCode());
		
		Mockito.when(restCall.executePUT(url, urlParams, ubicationRDTO)).thenReturn(ss);
		Mockito.when(frontValidateUbicationService.validateSyntaxUpdate(ubicationRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateUpdate(ubicationRDTO)).thenReturn(returnRDTO);
		Mockito.when(utils.rest2Object(Mockito.any(ss.getClass()), Mockito.any(returnRDTO.getClass()))).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.update(ubicationRDTO);
	}
	
	@Test
	public void caseUpdateKOValidaciones() throws ImiException {
		
		ReturnRDTO returnRDTOKO = returnRDTO;
		returnRDTOKO.setCode("KO");
		
		Mockito.when(frontValidateUbicationService.validateSyntaxUpdate(ubicationRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.update(ubicationRDTO);
	}
	
	@Test(expected = ImiException.class)
	public void caseKoUpdate() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.update(ubicationRDTO);
	}
	
	@Test
	public void caseOkDelete() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathUbication", pathUbication);
		ReflectionTestUtils.setField(service, "pathUbicationDelete", pathUbicationDelete);
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathUbication)
				.concat(pathUbicationDelete);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", ubicationRDTO.getCode());
		
		Mockito.when(restCall.executeDELETE(url, urlParams,ubicationRDTO)).thenReturn(ss);
		Mockito.when(frontValidateUbicationService.validateSyntaxDelete(ubicationRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateDelete(ubicationRDTO)).thenReturn(returnRDTO);	
		Mockito.when(utils.rest2Object(Mockito.any(ss.getClass()), Mockito.any(returnRDTO.getClass()))).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.delete(ubicationRDTO);
	}
	
	@Test(expected = ImiException.class)
	public void caseKoDelete() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.delete(ubicationRDTO);
	}
	
	@Test
	public void caseDeleteKOValidaciones() throws ImiException {
		
		ReturnRDTO returnRDTOKO = returnRDTO;
		returnRDTOKO.setCode("KO");
		
		Mockito.when(frontValidateUbicationService.validateSyntaxDelete(ubicationRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.delete(ubicationRDTO);
	}
	
	
	@Test
	public void caseOkSelectMassive() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathUbication", pathUbication);
		ReflectionTestUtils.setField(service, "pathUbicationGetMassive", pathUbicationGetMassive);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathUbication)
				.concat(pathUbicationGetMassive);
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", queryParameterRDTO.getCode());
		urlParams.put("codiContracta", queryParameterRDTO.getCodeContract());
		
		Mockito.when(frontValidateUbicationService.validateSyntaxSelectMassive(queryParameterRDTO)).thenReturn(returnRDTO);
		Mockito.when(restCall.executeGET(url, urlParams, queryParameterRDTO)).thenReturn(ss);
		
		ReturnMassiveRDTO returnMassiveRDTO = new ReturnMassiveRDTO();
		returnMassiveRDTO.setReturnRDTO(returnRDTO);
		Mockito.when(utils.rest2Object(Mockito.any(ss.getClass()), Mockito.any(returnMassiveRDTO.getClass()))).thenReturn(returnMassiveRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.selectMassive(queryParameterRDTO);
	}

	@Test
	public void caseSelectMassiveKoValidations() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathUbication", pathUbication);
		ReflectionTestUtils.setField(service, "pathUbicationGetMassive", pathUbicationGetMassive);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathUbication)
				.concat(pathUbicationGetMassive);
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", queryParameterRDTO.getCode());
		urlParams.put("codiContracta", queryParameterRDTO.getCodeContract());
		
		Mockito.when(frontValidateUbicationService.validateSyntaxSelectMassive(queryParameterRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(restCall.executeGET(url, urlParams, queryParameterRDTO)).thenReturn(ss);
		
		ReturnMassiveRDTO returnMassiveRDTO = new ReturnMassiveRDTO();
		returnMassiveRDTO.setReturnRDTO(returnRDTO);
		Mockito.when(utils.rest2Object(Mockito.any(ss.getClass()), Mockito.any(returnMassiveRDTO.getClass()))).thenReturn(returnMassiveRDTO);
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
		ReflectionTestUtils.setField(service, "pathUbication", pathUbication);
		ReflectionTestUtils.setField(service, "pathUbicationGetDetailed", pathUbicationGetDetailed);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathUbication)
				.concat(pathUbicationGetDetailed);
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", queryParameterRDTO.getCode());
		urlParams.put("codiContracta", queryParameterRDTO.getCodeContract());
		
		Mockito.when(frontValidateUbicationService.validateSyntaxSelectDetailed(queryParameterRDTO)).thenReturn(returnRDTO);
		Mockito.when(restCall.executeGET(url, urlParams, queryParameterRDTO)).thenReturn(ss);
		
		ReturnUbicationDetailedRDTO returnUbicationRDTOOk = new ReturnUbicationDetailedRDTO();
		returnUbicationRDTOOk.setReturnRDTO(returnRDTO);
		Mockito.when(utils.rest2Object(Mockito.any(ss.getClass()), Mockito.any(returnUbicationRDTOOk.getClass()))).thenReturn(returnUbicationRDTOOk);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.selectDetailed(queryParameterRDTO);
	}

	@Test
	public void caseSelectDetailedKOValidations() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathUbication", pathUbication);
		ReflectionTestUtils.setField(service, "pathUbicationGetDetailed", pathUbicationGetDetailed);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathUbication)
				.concat(pathUbicationGetDetailed);
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", queryParameterRDTO.getCode());
		urlParams.put("codiContracta", queryParameterRDTO.getCodeContract());
		
		Mockito.when(frontValidateUbicationService.validateSyntaxSelectDetailed(queryParameterRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(restCall.executeGET(url, urlParams, queryParameterRDTO)).thenReturn(ss);
		
		ReturnUbicationDetailedRDTO returnUbicationRDTOOk = new ReturnUbicationDetailedRDTO();
		returnUbicationRDTOOk.setReturnRDTO(returnRDTO);
		Mockito.when(utils.rest2Object(Mockito.any(ss.getClass()), Mockito.any(returnUbicationRDTOOk.getClass()))).thenReturn(returnUbicationRDTOOk);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.selectDetailed(queryParameterRDTO);
	}

	@Test(expected = ImiException.class)
	public void caseKoSelectDetailed() throws ImiException {
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
		Mockito.when(frontValidateUbicationService.validateSyntaxInsertDocumentarySupport(documentarySupportRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateInsertDocumentarySupport(documentarySupportRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.insertDocumentalSupport(documentarySupportRDTO);
	}

	@Test
	public void caseInsertSupporDocumentaltKoValidateSyntax() throws ImiException {
		Mockito.when(frontValidateUbicationService.validateSyntaxInsertDocumentarySupport(documentarySupportRDTO)).thenReturn(returnRDTOKO);
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
		Mockito.when(frontValidateUbicationService.validateSyntaxInsertActuation(actuationRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateInsertActuation(actuationRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.insertActuation(actuationRDTO);
	}

	@Test
	public void caseInsertActuationKoValidateSyntax() throws ImiException {
		Mockito.when(frontValidateUbicationService.validateSyntaxInsertActuation(actuationRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.insertActuation(actuationRDTO);
	}
	@Test(expected = ImiException.class)
	public void caseKoInsertActuation() throws ImiException {
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
		urlParams.put("codiEntitat", ubicationRDTO.getCode());
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		Mockito.when(restCall.executePUT(Mockito.anyString(), Mockito.anyMap(), Mockito.any(actuationRDTO.getClass()))).thenReturn(ss);
		Mockito.when(frontValidateUbicationService.validateSyntaxDeleteActuation(actuationRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateDeleteActuation(actuationRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.deleteActuation(actuationRDTO);
	}
	
	@Test
	public void caseDeleteActuationKoValidateSyntax() throws ImiException {
		Mockito.when(frontValidateUbicationService.validateSyntaxDeleteActuation(actuationRDTO)).thenReturn(returnRDTOKO);
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
		urlParams.put("codiEntitat", ubicationRDTO.getCode());
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		Mockito.when(restCall.executePUT(Mockito.anyString(), Mockito.anyMap(), Mockito.any(documentarySupportRDTO.getClass()))).thenReturn(ss);
		Mockito.when(frontValidateUbicationService.validateSyntaxDeleteDocumentarySupport(documentarySupportRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateDeleteDocumentarySupport(documentarySupportRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.deleteDocumentalSupport(documentarySupportRDTO);
	}
	
	@Test
	public void caseDeleteDocumentarySupportKoValidateSyntax() throws ImiException {
		Mockito.when(frontValidateUbicationService.validateSyntaxDeleteDocumentarySupport(documentarySupportRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.deleteDocumentalSupport(documentarySupportRDTO);
	}
	
	@Test(expected = ImiException.class)
	public void caseKoDeleteDocumentarySupport() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.deleteDocumentalSupport(documentarySupportRDTO);
	}

}
