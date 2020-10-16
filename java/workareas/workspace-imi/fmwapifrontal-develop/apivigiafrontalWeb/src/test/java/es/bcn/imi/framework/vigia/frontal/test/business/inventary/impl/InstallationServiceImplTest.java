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

import es.bcn.imi.framework.vigia.frontal.business.inventary.impl.InstallationServiceImpl;
import es.bcn.imi.framework.vigia.frontal.validator.ValidatorInstallation;
import es.bcn.imi.framework.vigia.frontal.web.rest.config.RestServerConfig;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.ActuationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.InstallationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.commons.BaseTransactionIdRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnBreakdownAmortizationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnInstallationDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnInstallationExpensesRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnInstallationPeriodRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.ActuationRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.InstallationRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.QueryParameterRDTOStub;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import es.bcn.vigia.fmw.libutils.services.Utils;
import es.bcn.vigia.fmw.libutils.services.rest.RestCall;
import es.bcn.vigia.fmw.libutils.validator.front.FrontValidateInstallationService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RestServerConfig.class })
@TestPropertySource(locations = {"classpath:app/config/application.properties"})
@SuppressWarnings("unchecked")
@WebAppConfiguration
public class InstallationServiceImplTest {
	
	@Value("${url.api.orquestrador}")
	private String urlApiOrquestrador;

	@Value("${url.path.inventary}")
	private String pathInventary;

	@Value("${url.path.installation}")
	private String pathInstallation;

	@Value("${url.path.installation.getDetailed}")
	private String pathInstallationGetDetailed;

	@Value("${url.path.installation.getMassive}")
	private String pathInstallationGetMassive;

	@Value("${url.path.installation.getAmortization}")
	private String pathInstallationGetAmortization;

	@Value("${url.path.installation.getExpenses}")
	private String pathInstallationGetExpenses;

	@Value("${url.path.installation.getPeriod}")
	private String pathInstallationGetPeriod;

	@Value("${url.path.installation.put}")
	private String pathInstallationPut;

	@Value("${url.path.installation.delete}")
	private String pathInstallationDelete;

	@Value("${url.path.installation.expense}")
	private String pathInstallationExpense;

	@Value("${url.path.installation.apportionment}")
	private String pathInstallationApportionment;

	@Value("${url.path.installation.amortization}")
	private String pathInstallationAmortization;
	
	@Value("${url.path.actuation}")
	private String pathActuation;

	@Value("${url.path.actuation.delete}")
	private String pathActuationDelete;

	
	@Mock
	private RestCall restCall;
	
	@Mock
	private Utils utils;

	@Mock
	private FrontValidateInstallationService frontValidateInstallationService;
	
	@Mock
	private ValidatorInstallation validator;
	
	@InjectMocks
	private InstallationServiceImpl service;
	
	private ReturnRDTO returnRDTO;
	
	private ReturnRDTO returnRDTOKO;
	
	private InstallationRDTO installationRDTO;

	private QueryParameterRDTO queryParameterRDTO;
	
	private ActuationRDTO actuationRDTO;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		returnRDTO = ReturnRDTOStub.getSuccessMessage();
		returnRDTOKO = ReturnRDTOStub.getErrorMessage();
		installationRDTO = InstallationRDTOStub.defaultOne();
		queryParameterRDTO = QueryParameterRDTOStub.defaultOne();
		actuationRDTO = ActuationRDTOStub.defaultOne();
        
	}
	
	@Test
	public void caseOkInsert() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathInstallation", pathInstallation);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathInstallation);

		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(url, installationRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		Mockito.when(frontValidateInstallationService.validateSyntaxInsert(installationRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateInsert(installationRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.insert(installationRDTO);
	}
	
	@Test
	public void caseInsertKoValidateSyntax() throws ImiException {
		Mockito.when(frontValidateInstallationService.validateSyntaxInsert(installationRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);

		service.insert(installationRDTO);
	}
	@Test(expected = ImiException.class)
	public void caseKoInsert() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.insert(installationRDTO);
	}
	
	@Test
	public void caseOkUpdate() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathInstallation", pathInstallation);
		ReflectionTestUtils.setField(service, "pathInstallationPut", pathInstallationPut);
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathInstallation)
				.concat(pathInstallationPut);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", installationRDTO.getCode());
		
		Mockito.when(restCall.executePUT(url, urlParams, installationRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(Mockito.any(ss.getClass()), Mockito.any(returnRDTO.getClass()))).thenReturn(returnRDTO);
		Mockito.when(frontValidateInstallationService.validateSyntaxUpdate(installationRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateUpdate(installationRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.update(installationRDTO);
	}
	
	
	@Test
	public void caseUpdateValidateSyntax() throws ImiException {
		Mockito.when(frontValidateInstallationService.validateSyntaxUpdate(installationRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);

		service.update(installationRDTO);
	}
	@Test(expected = ImiException.class)
	public void caseKoUpdate() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.update(installationRDTO);
	}
	
	@Test
	public void caseOkDelete() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathInstallation", pathInstallation);
		ReflectionTestUtils.setField(service, "pathInstallationDelete", pathInstallationDelete);
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathInstallation)
				.concat(pathInstallationDelete);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", installationRDTO.getCode());
		
		Mockito.when(restCall.executeDELETE(url, urlParams, installationRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(Mockito.any(ss.getClass()), Mockito.any(returnRDTO.getClass()))).thenReturn(returnRDTO);
		Mockito.when(frontValidateInstallationService.validateSyntaxDelete(installationRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateDelete(installationRDTO)).thenReturn(returnRDTO);	
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);	
		service.delete(installationRDTO);
	}
	
	@Test
	public void caseDeleteKoValidateSyntax() throws ImiException {
		Mockito.when(frontValidateInstallationService.validateSyntaxDelete(installationRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.delete(installationRDTO);
	}
	@Test(expected = ImiException.class)
	public void caseKoDelete() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.delete(installationRDTO);
	}
	
	@Test
	public void caseOkSelectDetailed() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathInstallation", pathInstallation);
		ReflectionTestUtils.setField(service, "pathInstallationGetDetailed", pathInstallationGetDetailed);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathInstallation)
				.concat(pathInstallationGetDetailed);
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", queryParameterRDTO.getCode());
		urlParams.put("codiContracta", queryParameterRDTO.getCodeContract());
		
		Mockito.when(frontValidateInstallationService.validateSyntaxSelectDetailed(queryParameterRDTO)).thenReturn(returnRDTO);
		Mockito.when(restCall.executeGET(url, urlParams, queryParameterRDTO)).thenReturn(ss);
		
		ReturnInstallationDetailedRDTO returnInstallationRDTOOk = new ReturnInstallationDetailedRDTO();
		returnInstallationRDTOOk.setReturnRDTO(returnRDTO);
		Mockito.when(utils.rest2Object(Mockito.any(ss.getClass()), Mockito.any(returnInstallationRDTOOk.getClass()))).thenReturn(returnInstallationRDTOOk);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.selectDetailed(queryParameterRDTO);
	}
	
	@Test
	public void caseSelectDetailedValidateSyntax() throws ImiException {
		Mockito.when(frontValidateInstallationService.validateSyntaxSelectDetailed(queryParameterRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.selectDetailed(queryParameterRDTO);
	}
	
	@Test(expected = ImiException.class)
	public void caseKoSelectDetailed() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.selectDetailed(queryParameterRDTO);
	}
	
	@Test
	public void caseOkSelectMassive() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathInstallation", pathInstallation);
		ReflectionTestUtils.setField(service, "pathInstallationGetMassive", pathInstallationGetMassive);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathInstallation)
				.concat(pathInstallationGetMassive);
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", queryParameterRDTO.getCodeContract());
		Mockito.when(frontValidateInstallationService.validateSyntaxSelectMassive(queryParameterRDTO)).thenReturn(returnRDTO);
		Mockito.when(restCall.executeGET(url, urlParams, queryParameterRDTO)).thenReturn(ss);
		
		ReturnMassiveRDTO returnMassiveRDTO = new ReturnMassiveRDTO();
		returnMassiveRDTO.setReturnRDTO(returnRDTO);
		Mockito.when(utils.rest2Object(Mockito.any(ss.getClass()), Mockito.any(returnMassiveRDTO.getClass()))).thenReturn(returnMassiveRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.selectMassive(queryParameterRDTO);
	}
	
	@Test
	public void caseSelectMassiveKoValidateSyntax() throws ImiException {
		Mockito.when(frontValidateInstallationService.validateSyntaxSelectMassive(queryParameterRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);

		service.selectMassive(queryParameterRDTO);
	}
	
	@Test(expected = ImiException.class)
	public void caseKoSelectMassive() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.selectMassive(queryParameterRDTO);
	}
	
	@Test
	public void caseOkSelectAmortization() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathInstallation", pathInstallation);
		ReflectionTestUtils.setField(service, "pathInstallationGetAmortization", pathInstallationGetAmortization);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathInstallation)
				.concat(pathInstallationGetAmortization);
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", queryParameterRDTO.getCode());
		urlParams.put("codiContracta", queryParameterRDTO.getCodeContract());
		
		Mockito.when(frontValidateInstallationService.validateSyntaxSelectAmortitzacio(queryParameterRDTO)).thenReturn(returnRDTO);
		Mockito.when(restCall.executeGET(url, urlParams, queryParameterRDTO)).thenReturn(ss);
		
		ReturnBreakdownAmortizationRDTO returnBreakdownAmortizationRDTO = new ReturnBreakdownAmortizationRDTO();
		returnBreakdownAmortizationRDTO.setReturnRDTO(returnRDTO);
		Mockito.when(utils.rest2Object(Mockito.any(ss.getClass()), Mockito.any(returnBreakdownAmortizationRDTO.getClass()))).thenReturn(returnBreakdownAmortizationRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.selectAmortization(queryParameterRDTO);
	}
	
	@Test
	public void caseSelectAmortizationKoValidateSyntax() throws ImiException {
		Mockito.when(frontValidateInstallationService.validateSyntaxSelectAmortitzacio(queryParameterRDTO)).thenReturn(returnRDTOKO);
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
		ReflectionTestUtils.setField(service, "pathInstallation", pathInstallation);
		ReflectionTestUtils.setField(service, "pathInstallationGetExpenses", pathInstallationGetExpenses);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathInstallation)
				.concat(pathInstallationGetExpenses);
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", queryParameterRDTO.getCode());
		urlParams.put("codiContracta", queryParameterRDTO.getCodeContract());
		
		Mockito.when(frontValidateInstallationService.validateSyntaxSelectExpenses(queryParameterRDTO)).thenReturn(returnRDTO);
		Mockito.when(restCall.executeGET(url, urlParams, queryParameterRDTO)).thenReturn(ss);
		
		ReturnInstallationExpensesRDTO returnInstallationExpensesRDTO = new ReturnInstallationExpensesRDTO();
		returnInstallationExpensesRDTO.setReturnRDTO(returnRDTO);
		Mockito.when(utils.rest2Object(Mockito.any(ss.getClass()), Mockito.any(returnInstallationExpensesRDTO.getClass()))).thenReturn(returnInstallationExpensesRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.selectExpenses(queryParameterRDTO);
	}
	
	@Test
	public void caseSelectExpensesKoValidateSyntax() throws ImiException {
		Mockito.when(frontValidateInstallationService.validateSyntaxSelectExpenses(queryParameterRDTO)).thenReturn(returnRDTOKO);
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
		ReflectionTestUtils.setField(service, "pathInstallation", pathInstallation);
		ReflectionTestUtils.setField(service, "pathInstallationGetPeriod", pathInstallationGetPeriod);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathInstallation)
				.concat(pathInstallationGetPeriod);
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", queryParameterRDTO.getCode());
		urlParams.put("codiContracta", queryParameterRDTO.getCodeContract());
		urlParams.put("periode", queryParameterRDTO.getPeriod());
		
		Mockito.when(frontValidateInstallationService.validateSyntaxSelectPeriod(queryParameterRDTO)).thenReturn(returnRDTO);
		Mockito.when(restCall.executeGET(url, urlParams, queryParameterRDTO)).thenReturn(ss);
		
		ReturnInstallationPeriodRDTO returnInstallationRDTOOk = new ReturnInstallationPeriodRDTO();
		returnInstallationRDTOOk.setReturnRDTO(returnRDTO);
		Mockito.when(utils.rest2Object(Mockito.any(ss.getClass()), Mockito.any(returnInstallationRDTOOk.getClass()))).thenReturn(returnInstallationRDTOOk);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.selectPeriod(queryParameterRDTO);
	}

	@Test
	public void caseSelectPeriodKoValidateSyntax() throws ImiException {
		Mockito.when(frontValidateInstallationService.validateSyntaxSelectPeriod(queryParameterRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);

		service.selectPeriod(queryParameterRDTO);
	}

	@Test(expected = ImiException.class)
	public void caseKoSelectPeriod() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.selectPeriod(queryParameterRDTO);
	}

	@Test
	public void caseOkInsertExpense() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathInstallation", pathInstallation);
		ReflectionTestUtils.setField(service, "pathInstallationExpense", pathInstallationExpense);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathInstallation)
				.concat(pathInstallationExpense);

		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(url, installationRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		Mockito.when(frontValidateInstallationService.validateSyntaxInsertExpense(installationRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateInsertExpense(installationRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.insertExpense(installationRDTO);
	}

	@Test
	public void caseInsertExpenseKoValidateSyntax() throws ImiException {
		Mockito.when(frontValidateInstallationService.validateSyntaxInsertExpense(installationRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.insertExpense(installationRDTO);
	}

	@Test(expected = ImiException.class)
	public void caseKoInsertExpense() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.insertExpense(installationRDTO);
	}

	@Test
	public void caseOkInsertApportionment() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathInstallation", pathInstallation);
		ReflectionTestUtils.setField(service, "pathInstallationApportionment", pathInstallationApportionment);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathInstallation)
				.concat(pathInstallationApportionment);

		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(url, installationRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		Mockito.when(frontValidateInstallationService.validateSyntaxInsertApportionment(installationRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateInsertApportionment(installationRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.insertApportionment(installationRDTO);
	}
	
	@Test
	public void caseInsertApportionmentKoValidateSyntax() throws ImiException {
		Mockito.when(frontValidateInstallationService.validateSyntaxInsertApportionment(installationRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);

		service.insertApportionment(installationRDTO);
	}

	@Test(expected = ImiException.class)
	public void caseKoInsertApportionment() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.insertApportionment(installationRDTO);
	}
	
	@Test
	public void caseOkInsertAmortizationBase() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathInstallation", pathInstallation);
		ReflectionTestUtils.setField(service, "pathInstallationAmortization", pathInstallationAmortization);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathInventary)
				.concat(pathInstallation)
				.concat(pathInstallationAmortization);

		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(url, installationRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		Mockito.when(frontValidateInstallationService.validateSyntaxInsertAmortizationBase(installationRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateInsertAmortizationBase(installationRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.insertAmortizationBase(installationRDTO);
	}

	@Test
	public void caseInsertAmortizationBaseKoValidateSyntax() throws ImiException {
		Mockito.when(frontValidateInstallationService.validateSyntaxInsertAmortizationBase(installationRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);

		service.insertAmortizationBase(installationRDTO);
	}

	@Test(expected = ImiException.class)
	public void caseKoInsertAmortizationBase() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.insertAmortizationBase(installationRDTO);
	}
	
	
	
	@Test
	public void caseOkInsertActuation() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathInventary", pathInventary);
		ReflectionTestUtils.setField(service, "pathActuation", pathActuation);
		
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(Mockito.anyString(), Mockito.any(actuationRDTO.getClass()))).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		Mockito.when(frontValidateInstallationService.validateSyntaxInsertActuation(actuationRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateInsertActuation(actuationRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.insertActuation(actuationRDTO);
	}

	@Test
	public void caseInsertActuationKoValidateSyntax() throws ImiException {
		Mockito.when(frontValidateInstallationService.validateSyntaxInsertActuation(actuationRDTO)).thenReturn(returnRDTOKO);
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
		urlParams.put("codiEntitat", installationRDTO.getCode());
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		Mockito.when(restCall.executePUT(Mockito.anyString(), Mockito.anyMap(), Mockito.any(actuationRDTO.getClass()))).thenReturn(ss);
		Mockito.when(frontValidateInstallationService.validateSyntaxDeleteActuation(actuationRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateDeleteActuation(actuationRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.deleteActuation(actuationRDTO);
	}
	
	@Test
	public void caseDeleteActuationKoValidateSyntax() throws ImiException {
		Mockito.when(frontValidateInstallationService.validateSyntaxDeleteActuation(actuationRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.deleteActuation(actuationRDTO);
	}
	
	@Test(expected = ImiException.class)
	public void caseKoDeleteActuation() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.deleteActuation(actuationRDTO);
	}
	
	
}
