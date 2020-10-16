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

import es.bcn.imi.framework.vigia.frontal.business.inventary.impl.MaterialResourceServiceImpl;
import es.bcn.imi.framework.vigia.frontal.validator.ValidatorMaterialResource;
import es.bcn.imi.framework.vigia.frontal.web.rest.config.RestServerConfig;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.ActuationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.DocumentarySupportRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.MaterialResourceRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.commons.BaseTransactionIdRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnBreakdownAmortizationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMaterialResourceDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMaterialResourceRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMaterialResourceVehicleDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMaterialResourceVehicleExpensesRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMaterialResourceVehiclePeriodRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.ActuationRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.DocumentarySupportRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.MaterialResourceRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.QueryParameterRDTOStub;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import es.bcn.vigia.fmw.libutils.services.Utils;
import es.bcn.vigia.fmw.libutils.services.rest.RestCall;
import es.bcn.vigia.fmw.libutils.validator.front.FrontValidateMaterialResourceService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RestServerConfig.class })
@TestPropertySource(locations = {"classpath:app/config/application.properties"})
@SuppressWarnings("unchecked")
@WebAppConfiguration
public class MaterialResourceServiceImplTest {
	
	@Mock
	private RestCall restCall;

	@Mock
	private Utils utils;

	@Mock
	private FrontValidateMaterialResourceService frontValidateMaterialResourceService;
	
	@InjectMocks
	private MaterialResourceServiceImpl service;
	
	@Value("${url.api.orquestrador}")
	private String urlApiOrquestrador;

	@Value("${url.path.inventary}")
	private String pathInventary;

	@Value("${url.path.materialresource}")
	private String pathMaterialResource;

	@Value("${url.path.materialresource.getMassive}")
	private String pathMaterialResourceGetMassive;

	@Value("${url.path.materialresource.getDetailed}")
	private String pathMaterialResourceGetDetailed;

	@Value("${url.path.materialresource.vehicle}")
	private String pathMaterialResourceVehicle;

	@Value("${url.path.materialresource.vehicle.getMassive}")
	private String pathMaterialResourceVehicleGetMassive;

	@Value("${url.path.materialresource.vehicle.getDetailed}")
	private String pathMaterialResourceVehicleGetDetailed;

	@Value("${url.path.materialresource.vehicle.getAmortization}")
	private String pathMaterialResourceVehicleGetAmortization;

	@Value("${url.path.materialresource.vehicle.getExpenses}")
	private String pathMaterialResourceVehicleGetExpenses;

	@Value("${url.path.materialresource.vehicle.getPeriod}")
	private String pathMaterialResourceVehicleGetPeriod;

	@Value("${url.path.materialresource.put}")
	private String pathMaterialResourcePut;

	@Value("${url.path.materialresource.delete}")
	private String pathMaterialResourceDelete;

	@Value("${url.path.materialresource.expense}")
	private String pathMaterialResourceExpense;
	
	@Value("${url.path.materialresource.apportionment}")
	private String pathMaterialResourceApportionment;

	@Value("${url.path.materialresource.amortization}")
	private String pathMaterialResourceAmortization;

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
	private ValidatorMaterialResource validator;

	private ReturnRDTO returnRDTO;
	
	private ReturnRDTO returnRDTOKO;
	
	private ReturnMaterialResourceRDTO returnMaterialResourceRDTO;

	private MaterialResourceRDTO materialResourceRDTO;

	private QueryParameterRDTO queryParameterRDTO;

	private DocumentarySupportRDTO documentarySupportRDTO;
	
	private ActuationRDTO actuationRDTO;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		returnRDTO = ReturnRDTOStub.getSuccessMessage();
		returnRDTOKO = ReturnRDTOStub.getErrorMessage();
        materialResourceRDTO = MaterialResourceRDTOStub.defaultOne();
        returnMaterialResourceRDTO = new ReturnMaterialResourceRDTO();
        returnMaterialResourceRDTO.setReturnRDTO(returnRDTO);
		queryParameterRDTO = QueryParameterRDTOStub.defaultOne();
		documentarySupportRDTO = DocumentarySupportRDTOStub.defaultOne();
		actuationRDTO = ActuationRDTOStub.defaultOne();
	}
	
	@Test
	public void caseOkInsert() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathMaterialResource", pathMaterialResource);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathMaterialResource);

		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(url, materialResourceRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		Mockito.when(frontValidateMaterialResourceService.validateSyntaxInsert(materialResourceRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateInsert(materialResourceRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.insert(materialResourceRDTO);
	}

	@Test(expected = ImiException.class)
	public void caseKoInsert() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.insert(materialResourceRDTO);
	}

	@Test
	public void caseInsertKoValidationsSyntax() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathMaterialResource", pathMaterialResource);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathMaterialResource);

		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(url, materialResourceRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		Mockito.when(frontValidateMaterialResourceService.validateSyntaxInsert(materialResourceRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateInsert(materialResourceRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.insert(materialResourceRDTO);
	}

	@Test
	public void caseOkUpdate() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathMaterialResource", pathMaterialResource);
		ReflectionTestUtils.setField(service, "pathMaterialResourcePut", pathMaterialResourcePut);
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathMaterialResource)
				.concat(pathMaterialResourcePut);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", materialResourceRDTO.getCode());
		
		Mockito.when(restCall.executePUT(url, urlParams, materialResourceRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		Mockito.when(frontValidateMaterialResourceService.validateSyntaxUpdate(materialResourceRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateUpdate(materialResourceRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.update(materialResourceRDTO);
		
		
		
	}
	
	@Test
	public void caseUpdateKOValidationsSyntax() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathMaterialResource", pathMaterialResource);
		ReflectionTestUtils.setField(service, "pathMaterialResourcePut", pathMaterialResourcePut);
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathMaterialResource)
				.concat(pathMaterialResourcePut);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", materialResourceRDTO.getCode());
		
		Mockito.when(restCall.executePUT(url, urlParams, materialResourceRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		Mockito.when(frontValidateMaterialResourceService.validateSyntaxUpdate(materialResourceRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateUpdate(materialResourceRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.update(materialResourceRDTO);
		
	}

	@Test(expected = ImiException.class)
	public void caseKoUpdate() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.update(materialResourceRDTO);
	}
	
	@Test
	public void caseOkDelete() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathMaterialResource", pathMaterialResource);
		ReflectionTestUtils.setField(service, "pathMaterialResourceDelete", pathMaterialResourceDelete);
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathMaterialResource)
				.concat(pathMaterialResourceDelete);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", materialResourceRDTO.getCode());
		
		Mockito.when(restCall.executeDELETE(url, urlParams, materialResourceRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		Mockito.when(frontValidateMaterialResourceService.validateSyntaxDelete(materialResourceRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateDelete(materialResourceRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.delete(materialResourceRDTO);
	}
	
	@Test
	public void caseDeleteKOValidationSyntax() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathMaterialResource", pathMaterialResource);
		ReflectionTestUtils.setField(service, "pathMaterialResourceDelete", pathMaterialResourceDelete);
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathMaterialResource)
				.concat(pathMaterialResourceDelete);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", materialResourceRDTO.getCode());
		
		Mockito.when(restCall.executeDELETE(url, urlParams, materialResourceRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		Mockito.when(frontValidateMaterialResourceService.validateSyntaxDelete(materialResourceRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateDelete(materialResourceRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.delete(materialResourceRDTO);
	}
	
	@Test(expected = ImiException.class)
	public void caseKoDelete() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.delete(materialResourceRDTO);
	}	
	
	@Test
	public void caseOkSelectMassive() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathMaterialResource", pathMaterialResource);
		ReflectionTestUtils.setField(service, "pathMaterialResourceGetMassive", pathMaterialResourceGetMassive);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathMaterialResource)
				.concat(pathMaterialResourceGetMassive);
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", queryParameterRDTO.getCodeContract());
		Mockito.when(frontValidateMaterialResourceService.validateSyntaxSelectMassive(queryParameterRDTO)).thenReturn(returnRDTO);
		Mockito.when(restCall.executeGET(url, urlParams, queryParameterRDTO)).thenReturn(ss);
		
		ReturnMassiveRDTO returnMassiveRDTO = new ReturnMassiveRDTO();
		returnMassiveRDTO.setReturnRDTO(returnRDTO);
		Mockito.when(utils.rest2Object(Mockito.any(ss.getClass()), Mockito.any(returnMassiveRDTO.getClass()))).thenReturn(returnMassiveRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.selectMassive(queryParameterRDTO);
	}
	
	@Test
	public void caseSelectMassiveKoValidationSyntax() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathMaterialResource", pathMaterialResource);
		ReflectionTestUtils.setField(service, "pathMaterialResourceGetMassive", pathMaterialResourceGetMassive);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathMaterialResource)
				.concat(pathMaterialResourceGetMassive);
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", queryParameterRDTO.getCodeContract());
		Mockito.when(frontValidateMaterialResourceService.validateSyntaxSelectMassive(queryParameterRDTO)).thenReturn(returnRDTOKO);
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
		ReflectionTestUtils.setField(service, "pathMaterialResource", pathMaterialResource);
		ReflectionTestUtils.setField(service, "pathMaterialResourceGetDetailed", pathMaterialResourceGetDetailed);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathMaterialResource)
				.concat(pathMaterialResourceGetDetailed);
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", queryParameterRDTO.getCode());
		urlParams.put("codiContracta", queryParameterRDTO.getCodeContract());
		
		Mockito.when(frontValidateMaterialResourceService.validateSyntaxSelectDetailed(queryParameterRDTO)).thenReturn(returnRDTO);
		Mockito.when(restCall.executeGET(url, urlParams, queryParameterRDTO)).thenReturn(ss);
		
		ReturnMaterialResourceDetailedRDTO returnMaterialResourceRDTOOk = new ReturnMaterialResourceDetailedRDTO();
		returnMaterialResourceRDTOOk.setReturnRDTO(returnRDTO);
		Mockito.when(utils.rest2Object(Mockito.any(ss.getClass()), Mockito.any(returnMaterialResourceRDTOOk.getClass()))).thenReturn(returnMaterialResourceRDTOOk);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.selectDetailed(queryParameterRDTO);
	}
	
	@Test
	public void caseSelectDetailedKoValidationSyntax() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathMaterialResource", pathMaterialResource);
		ReflectionTestUtils.setField(service, "pathMaterialResourceGetDetailed", pathMaterialResourceGetDetailed);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathMaterialResource)
				.concat(pathMaterialResourceGetDetailed);
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", queryParameterRDTO.getCode());
		urlParams.put("codiContracta", queryParameterRDTO.getCodeContract());
		
		Mockito.when(frontValidateMaterialResourceService.validateSyntaxSelectDetailed(queryParameterRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(restCall.executeGET(url, urlParams, queryParameterRDTO)).thenReturn(ss);
		
		ReturnMaterialResourceDetailedRDTO returnMaterialResourceRDTOOk = new ReturnMaterialResourceDetailedRDTO();
		returnMaterialResourceRDTOOk.setReturnRDTO(returnRDTO);
		Mockito.when(utils.rest2Object(Mockito.any(ss.getClass()), Mockito.any(returnMaterialResourceRDTOOk.getClass()))).thenReturn(returnMaterialResourceRDTOOk);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.selectDetailed(queryParameterRDTO);
	}
	
	@Test(expected = ImiException.class)
	public void caseKoSelectDetailed() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.selectDetailed(queryParameterRDTO);
	}	
	
	@Test
	public void caseOkSelectVehicleMassive() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathMaterialResource", pathMaterialResource);
		ReflectionTestUtils.setField(service, "pathMaterialResourceVehicle", pathMaterialResourceVehicle);
		ReflectionTestUtils.setField(service, "pathMaterialResourceVehicleGetMassive", pathMaterialResourceVehicleGetMassive);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathMaterialResource)
				.concat(pathMaterialResourceVehicle)
				.concat(pathMaterialResourceVehicleGetMassive);
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", queryParameterRDTO.getCodeContract());
		Mockito.when(frontValidateMaterialResourceService.validateSyntaxSelectMassive(queryParameterRDTO)).thenReturn(returnRDTO);
		Mockito.when(restCall.executeGET(url, urlParams, queryParameterRDTO)).thenReturn(ss);
		
		ReturnMassiveRDTO returnMassiveRDTO = new ReturnMassiveRDTO();
		returnMassiveRDTO.setReturnRDTO(returnRDTO);
		Mockito.when(utils.rest2Object(Mockito.any(ss.getClass()), Mockito.any(returnMassiveRDTO.getClass()))).thenReturn(returnMassiveRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.selectVehicleMassive(queryParameterRDTO);
	}
	
	@Test
	public void caseSelectVehicleMassiveKoValidationSyntax() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathMaterialResource", pathMaterialResource);
		ReflectionTestUtils.setField(service, "pathMaterialResourceVehicle", pathMaterialResourceVehicle);
		ReflectionTestUtils.setField(service, "pathMaterialResourceVehicleGetMassive", pathMaterialResourceVehicleGetMassive);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathMaterialResource)
				.concat(pathMaterialResourceVehicle)
				.concat(pathMaterialResourceVehicleGetMassive);
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", queryParameterRDTO.getCodeContract());
		Mockito.when(frontValidateMaterialResourceService.validateSyntaxSelectMassive(queryParameterRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(restCall.executeGET(url, urlParams, queryParameterRDTO)).thenReturn(ss);
		
		ReturnMassiveRDTO returnMassiveRDTO = new ReturnMassiveRDTO();
		returnMassiveRDTO.setReturnRDTO(returnRDTO);
		Mockito.when(utils.rest2Object(Mockito.any(ss.getClass()), Mockito.any(returnMassiveRDTO.getClass()))).thenReturn(returnMassiveRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.selectVehicleMassive(queryParameterRDTO);
	}
	
	@Test(expected = ImiException.class)
	public void caseKoSelectVehicleMassive() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.selectVehicleMassive(queryParameterRDTO);
	}
	
	@Test
	public void caseOkSelectVehicleDetailed() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathMaterialResource", pathMaterialResource);
		ReflectionTestUtils.setField(service, "pathMaterialResourceVehicle", pathMaterialResourceVehicle);
		ReflectionTestUtils.setField(service, "pathMaterialResourceVehicleGetDetailed", pathMaterialResourceVehicleGetDetailed);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathMaterialResource)
				.concat(pathMaterialResourceVehicle)
				.concat(pathMaterialResourceVehicleGetDetailed);
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", queryParameterRDTO.getCode());
		urlParams.put("codiContracta", queryParameterRDTO.getCodeContract());
		
		Mockito.when(frontValidateMaterialResourceService.validateSyntaxSelectDetailed(queryParameterRDTO)).thenReturn(returnRDTO);
		Mockito.when(restCall.executeGET(url, urlParams, queryParameterRDTO)).thenReturn(ss);
		
		ReturnMaterialResourceVehicleDetailedRDTO returnMaterialResourceVehicleRDTOOk = new ReturnMaterialResourceVehicleDetailedRDTO();
		returnMaterialResourceVehicleRDTOOk.setReturnRDTO(returnRDTO);
		Mockito.when(utils.rest2Object(Mockito.any(ss.getClass()), Mockito.any(returnMaterialResourceVehicleRDTOOk.getClass()))).thenReturn(returnMaterialResourceVehicleRDTOOk);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.selectVehicleDetailed(queryParameterRDTO);
	}
	
	@Test
	public void caseSelectVehicleDetailedKoValidationSyntax() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathMaterialResource", pathMaterialResource);
		ReflectionTestUtils.setField(service, "pathMaterialResourceVehicle", pathMaterialResourceVehicle);
		ReflectionTestUtils.setField(service, "pathMaterialResourceVehicleGetDetailed", pathMaterialResourceVehicleGetDetailed);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathMaterialResource)
				.concat(pathMaterialResourceVehicle)
				.concat(pathMaterialResourceVehicleGetDetailed);
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", queryParameterRDTO.getCode());
		urlParams.put("codiContracta", queryParameterRDTO.getCodeContract());
		
		Mockito.when(frontValidateMaterialResourceService.validateSyntaxSelectDetailed(queryParameterRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(restCall.executeGET(url, urlParams, queryParameterRDTO)).thenReturn(ss);
		
		ReturnMaterialResourceVehicleDetailedRDTO returnMaterialResourceVehicleRDTOOk = new ReturnMaterialResourceVehicleDetailedRDTO();
		returnMaterialResourceVehicleRDTOOk.setReturnRDTO(returnRDTO);
		Mockito.when(utils.rest2Object(Mockito.any(ss.getClass()), Mockito.any(returnMaterialResourceVehicleRDTOOk.getClass()))).thenReturn(returnMaterialResourceVehicleRDTOOk);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.selectVehicleDetailed(queryParameterRDTO);
	}
	
	@Test(expected = ImiException.class)
	public void caseKoSelectVehicleDetailed() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.selectVehicleDetailed(queryParameterRDTO);
	}
	
	@Test
	public void caseOkInsertExpense() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathMaterialResource", pathMaterialResource);
		ReflectionTestUtils.setField(service, "pathMaterialResourceExpense", pathMaterialResourceExpense);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathMaterialResource)
				.concat(pathMaterialResourceExpense);

		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(url, materialResourceRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		Mockito.when(frontValidateMaterialResourceService.validateSyntaxInsertExpense(materialResourceRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateInsertExpense(materialResourceRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.insertExpense(materialResourceRDTO);
	}

	@Test
	public void caseInsertExpenseKOValidationSyntax() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathMaterialResource", pathMaterialResource);
		ReflectionTestUtils.setField(service, "pathMaterialResourceExpense", pathMaterialResourceExpense);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathMaterialResource)
				.concat(pathMaterialResourceExpense);

		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(url, materialResourceRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		Mockito.when(frontValidateMaterialResourceService.validateSyntaxInsertExpense(materialResourceRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateInsertExpense(materialResourceRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.insertExpense(materialResourceRDTO);
	}
	@Test(expected = ImiException.class)
	public void caseKoInsertExpense() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.insertExpense(materialResourceRDTO);
	}
	
	@Test
	public void caseOkInsertApportionment() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathMaterialResource", pathMaterialResource);
		ReflectionTestUtils.setField(service, "pathMaterialResourceApportionment", pathMaterialResourceApportionment);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathMaterialResource)
				.concat(pathMaterialResourceApportionment);

		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(url, materialResourceRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		Mockito.when(frontValidateMaterialResourceService.validateSyntaxInsertApportionment(materialResourceRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateInsertApportionment(materialResourceRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.insertApportionment(materialResourceRDTO);
	}

	@Test
	public void caseInsertApportionmentKOValidationSyntax() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathMaterialResource", pathMaterialResource);
		ReflectionTestUtils.setField(service, "pathMaterialResourceApportionment", pathMaterialResourceApportionment);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathMaterialResource)
				.concat(pathMaterialResourceApportionment);

		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(url, materialResourceRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		Mockito.when(frontValidateMaterialResourceService.validateSyntaxInsertApportionment(materialResourceRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateInsertApportionment(materialResourceRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.insertApportionment(materialResourceRDTO);
	}
	
	@Test(expected = ImiException.class)
	public void caseKoInsertApportionment() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.insertApportionment(materialResourceRDTO);
	}
	
	@Test
	public void caseOkInsertAmortizationBase() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathMaterialResource", pathMaterialResource);
		ReflectionTestUtils.setField(service, "pathMaterialResourceAmortization", pathMaterialResourceAmortization);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathMaterialResource)
				.concat(pathMaterialResourceAmortization);

		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(url, materialResourceRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		Mockito.when(frontValidateMaterialResourceService.validateSyntaxInsertAmortizationBase(materialResourceRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateInsertAmortizationBase(materialResourceRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.insertAmortizationBase(materialResourceRDTO);
	}

	@Test
	public void caseInsertAmortizationBaseKOValidationSyntax() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathMaterialResource", pathMaterialResource);
		ReflectionTestUtils.setField(service, "pathMaterialResourceAmortization", pathMaterialResourceAmortization);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathMaterialResource)
				.concat(pathMaterialResourceAmortization);

		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(url, materialResourceRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		Mockito.when(frontValidateMaterialResourceService.validateSyntaxInsertAmortizationBase(materialResourceRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateInsertAmortizationBase(materialResourceRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.insertAmortizationBase(materialResourceRDTO);
	}
	
	@Test(expected = ImiException.class)
	public void caseKoInsertAmortizationBase() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.insertAmortizationBase(materialResourceRDTO);
	}

	
	@Test
	public void caseOkSelectAmortization() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathMaterialResource", pathMaterialResource);
		ReflectionTestUtils.setField(service, "pathMaterialResourceVehicle", pathMaterialResourceVehicle);
		ReflectionTestUtils.setField(service, "pathMaterialResourceVehicleGetAmortization", pathMaterialResourceVehicleGetAmortization);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathMaterialResource)
				.concat(pathMaterialResourceVehicle)
				.concat(pathMaterialResourceVehicleGetAmortization);
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", queryParameterRDTO.getCode());
		urlParams.put("codiContracta", queryParameterRDTO.getCodeContract());
		
		Mockito.when(frontValidateMaterialResourceService.validateSyntaxSelectAmortitzacio(queryParameterRDTO)).thenReturn(returnRDTO);
		Mockito.when(restCall.executeGET(url, urlParams, queryParameterRDTO)).thenReturn(ss);
		
		ReturnBreakdownAmortizationRDTO returnBreakdownAmortizationRDTO = new ReturnBreakdownAmortizationRDTO();
		returnBreakdownAmortizationRDTO.setReturnRDTO(returnRDTO);
		Mockito.when(utils.rest2Object(Mockito.any(ss.getClass()), Mockito.any(returnBreakdownAmortizationRDTO.getClass()))).thenReturn(returnBreakdownAmortizationRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.selectAmortization(queryParameterRDTO);
	}
	
	@Test
	public void caseSelectAmortizationKoValidateSyntax() throws ImiException {
		Mockito.when(frontValidateMaterialResourceService.validateSyntaxSelectAmortitzacio(queryParameterRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.selectAmortization(queryParameterRDTO);
	}
	
	@Test(expected = ImiException.class)
	public void caseKoSelectAmortization() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.selectAmortization(queryParameterRDTO);
	}
	
	@Test
	public void caseOkSelectExpenses() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathMaterialResource", pathMaterialResource);
		ReflectionTestUtils.setField(service, "pathMaterialResourceVehicle", pathMaterialResourceVehicle);
		ReflectionTestUtils.setField(service, "pathMaterialResourceVehicleGetExpenses", pathMaterialResourceVehicleGetExpenses);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathMaterialResource)
				.concat(pathMaterialResourceVehicle)
				.concat(pathMaterialResourceVehicleGetExpenses);
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", queryParameterRDTO.getCode());
		urlParams.put("codiContracta", queryParameterRDTO.getCodeContract());
		
		Mockito.when(frontValidateMaterialResourceService.validateSyntaxSelectExpenses(queryParameterRDTO)).thenReturn(returnRDTO);
		Mockito.when(restCall.executeGET(url, urlParams, queryParameterRDTO)).thenReturn(ss);
		
		ReturnMaterialResourceVehicleExpensesRDTO returnMaterialResourceVehicleExpensesRDTO = new ReturnMaterialResourceVehicleExpensesRDTO();
		returnMaterialResourceVehicleExpensesRDTO.setReturnRDTO(returnRDTO);
		Mockito.when(utils.rest2Object(Mockito.any(ss.getClass()), Mockito.any(returnMaterialResourceVehicleExpensesRDTO.getClass()))).thenReturn(returnMaterialResourceVehicleExpensesRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.selectExpenses(queryParameterRDTO);
	}
	
	@Test
	public void caseSelectExpensesKoValidateSyntax() throws ImiException {
		Mockito.when(frontValidateMaterialResourceService.validateSyntaxSelectExpenses(queryParameterRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.selectExpenses(queryParameterRDTO);
	}
	
	@Test(expected = ImiException.class)
	public void caseKoSelectExpenses() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.selectExpenses(queryParameterRDTO);
	}
	
	@Test
	public void caseOkSelectPeriod() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathMaterialResource", pathMaterialResource);
		ReflectionTestUtils.setField(service, "pathMaterialResourceVehicle", pathMaterialResourceVehicle);
		ReflectionTestUtils.setField(service, "pathMaterialResourceVehicleGetPeriod", pathMaterialResourceVehicleGetPeriod);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathMaterialResource)
				.concat(pathMaterialResourceVehicle)
				.concat(pathMaterialResourceVehicleGetPeriod);
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", queryParameterRDTO.getCode());
		urlParams.put("codiContracta", queryParameterRDTO.getCodeContract());
		urlParams.put("periode", queryParameterRDTO.getPeriod());
		
		Mockito.when(frontValidateMaterialResourceService.validateSyntaxSelectPeriod(queryParameterRDTO)).thenReturn(returnRDTO);
		Mockito.when(restCall.executeGET(url, urlParams, queryParameterRDTO)).thenReturn(ss);
		
		ReturnMaterialResourceVehiclePeriodRDTO returnMaterialResourceVehicleRDTOOk = new ReturnMaterialResourceVehiclePeriodRDTO();
		returnMaterialResourceVehicleRDTOOk.setReturnRDTO(returnRDTO);
		Mockito.when(utils.rest2Object(Mockito.any(ss.getClass()), Mockito.any(returnMaterialResourceVehicleRDTOOk.getClass()))).thenReturn(returnMaterialResourceVehicleRDTOOk);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.selectPeriod(queryParameterRDTO);
	}

	@Test
	public void caseSelectPeriodKoValidateSyntax() throws ImiException {
		Mockito.when(frontValidateMaterialResourceService.validateSyntaxSelectPeriod(queryParameterRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);

		service.selectPeriod(queryParameterRDTO);
	}

	@Test(expected = ImiException.class)
	public void caseKoSelectPeriod() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.selectPeriod(queryParameterRDTO);
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
		Mockito.when(frontValidateMaterialResourceService.validateSyntaxInsertDocumentarySupport(documentarySupportRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateInsertDocumentarySupport(documentarySupportRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.insertDocumentalSupport(documentarySupportRDTO);
	}

	@Test
	public void caseInsertSupporDocumentaltKoValidateSyntax() throws ImiException {
		Mockito.when(frontValidateMaterialResourceService.validateSyntaxInsertDocumentarySupport(documentarySupportRDTO)).thenReturn(returnRDTOKO);
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
		Mockito.when(frontValidateMaterialResourceService.validateSyntaxInsertActuation(actuationRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateInsertActuation(actuationRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.insertActuation(actuationRDTO);
	}

	@Test
	public void caseInsertActuationKoValidateSyntax() throws ImiException {
		Mockito.when(frontValidateMaterialResourceService.validateSyntaxInsertActuation(actuationRDTO)).thenReturn(returnRDTOKO);
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
		urlParams.put("codiEntitat", materialResourceRDTO.getCode());
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		Mockito.when(restCall.executePUT(Mockito.anyString(), Mockito.anyMap(), Mockito.any(actuationRDTO.getClass()))).thenReturn(ss);
		Mockito.when(frontValidateMaterialResourceService.validateSyntaxDeleteActuation(actuationRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateDeleteActuation(actuationRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.deleteActuation(actuationRDTO);
	}
	
	@Test
	public void caseDeleteActuationKoValidateSyntax() throws ImiException {
		Mockito.when(frontValidateMaterialResourceService.validateSyntaxDeleteActuation(actuationRDTO)).thenReturn(returnRDTOKO);
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
		urlParams.put("codiEntitat", materialResourceRDTO.getCode());
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		Mockito.when(restCall.executePUT(Mockito.anyString(), Mockito.anyMap(), Mockito.any(documentarySupportRDTO.getClass()))).thenReturn(ss);
		Mockito.when(frontValidateMaterialResourceService.validateSyntaxDeleteDocumentarySupport(documentarySupportRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateDeleteDocumentarySupport(documentarySupportRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.deleteDocumentalSupport(documentarySupportRDTO);
	}
	
	@Test
	public void caseDeleteDocumentarySupportKoValidateSyntax() throws ImiException {
		Mockito.when(frontValidateMaterialResourceService.validateSyntaxDeleteDocumentarySupport(documentarySupportRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.deleteDocumentalSupport(documentarySupportRDTO);
	}
	
	@Test(expected = ImiException.class)
	public void caseKoDeleteDocumentarySupport() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.deleteDocumentalSupport(documentarySupportRDTO);
	}

}
