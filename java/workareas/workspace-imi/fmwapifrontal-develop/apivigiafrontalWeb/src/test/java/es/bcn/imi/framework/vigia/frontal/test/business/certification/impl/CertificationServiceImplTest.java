package es.bcn.imi.framework.vigia.frontal.test.business.certification.impl;

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

import es.bcn.imi.framework.vigia.frontal.business.certification.impl.CertificationServiceImpl;
import es.bcn.imi.framework.vigia.frontal.validator.ValidatorCertification;
import es.bcn.imi.framework.vigia.frontal.web.rest.config.RestServerConfig;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CertificationCleaningServiceRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CertificationCollectionServiceRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CertificationExpenseInstallationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CertificationExtraordinaryRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CertificationInspectionRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CertificationInvestmentFurnitureRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CertificationInvestmentRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CertificationOthersRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CertificationPersonalRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CertificationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CertificationRegularizationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterCertificationProposalsRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.commons.BaseTransactionIdRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnCertificationProposalsRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.stub.ReturnRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.CertificationCleaningServiceRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.CertificationCollectionServiceRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.CertificationExpenseInstallationRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.CertificationExtraordinaryRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.CertificationInspectionRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.CertificationInvestmentFurnitureRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.CertificationInvestmentRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.CertificationOthersRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.CertificationPersonalRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.CertificationRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.CertificationRegularizationRDTOStub;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.stub.QueryParameterCertificationProposalsRDTOStub;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import es.bcn.vigia.fmw.libutils.services.Utils;
import es.bcn.vigia.fmw.libutils.services.rest.RestCall;
import es.bcn.vigia.fmw.libutils.validator.front.FrontValidateCertificationService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RestServerConfig.class })
@TestPropertySource(locations = {"classpath:app/config/application.properties"})
@WebAppConfiguration
public class CertificationServiceImplTest {
	
	@Value("${url.api.orquestrador}")
	private String urlApiOrquestrador;

	@Value("${url.path.certification}")
	private String pathCertification;
	
	@Value("${url.path.certification.proposals.get}")
	private String pathCertificationProposalsGet;
	
	@Value("${url.path.certification.postHeaders}")
	private String pathCertificationPostHeaders;

	@Value("${url.path.certification.other.expenses}")
	private String pathCertificationOtherExpenses;

	@Value("${url.path.certification.extra.concepts}")
	private String pathCertificationExtraordinaryConcepts;

	@Value("${url.path.certification.inspection}")
	private String pathCertificationInspection;

	@Value("${url.path.certification.installation.expense}")
	private String pathCertificationInstallationExpenses;

	@Value("${url.path.certification.investment.material.resource}")
	private String pathCertificationInvestmentRRMM;

	@Value("${url.path.certification.investment.furniture}")
	private String pathCertificationInvestmentFurniture;

	@Value("${url.path.certification.cleaning.service}")
	private String pathCertificationCleaningService;

	@Value("${url.path.certification.collection.service}")
	private String pathCertificationCollectionService;

	@Value("${url.path.certification.regularization}")
	private String pathCertificationRegularization;

	@Value("${url.path.certification.staff.expenses}")
	private String pathCertificationStaffExpense;

	@Value("${url.path.certification.invoice}")
	private String pathCertificationInvoice;
	
	@Mock
	private RestCall restCall;
	
	@Mock
	private Utils utils;

	@Mock
	private FrontValidateCertificationService frontValidateCertificationService;

	@Mock
	private ValidatorCertification validator;
	
	@InjectMocks
	private CertificationServiceImpl service;
	
	private ReturnRDTO returnRDTO;
	
	private ReturnRDTO returnRDTOKO;
	
	private CertificationRDTO certificationRDTO;
	
	private CertificationOthersRDTO certificationOthersRDTO;

	private CertificationExtraordinaryRDTO certificationExtraordinaryRDTO;

	private CertificationInspectionRDTO certificationInspectionRDTO;

	private CertificationExpenseInstallationRDTO certificationExpenseInstallationRDTO;

	private CertificationInvestmentRDTO certificationInvestmentRDTO ;

	private CertificationInvestmentFurnitureRDTO certificationInvestmentFurnitureRDTO;

	private CertificationCleaningServiceRDTO certificationCleaningServiceRDTO;
	
	private CertificationPersonalRDTO certificationPersonalRDTO; 
	
	private CertificationCollectionServiceRDTO certificationCollectionServiceRDTO;
	
	private CertificationRegularizationRDTO certificationRegularizationRDTO;

	private QueryParameterCertificationProposalsRDTO queryParameterCertificationProposalsRDTO;	

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		returnRDTO = ReturnRDTOStub.getSuccessMessage();
		certificationRDTO = CertificationRDTOStub.defaultOne();
		returnRDTOKO = ReturnRDTOStub.getErrorMessage();
		queryParameterCertificationProposalsRDTO = QueryParameterCertificationProposalsRDTOStub.defaultOne();
		certificationExtraordinaryRDTO = CertificationExtraordinaryRDTOStub.defaultOne();
		certificationInspectionRDTO = CertificationInspectionRDTOStub.defaultOne();
		certificationExpenseInstallationRDTO = CertificationExpenseInstallationRDTOStub.defaultOne();
		certificationInvestmentRDTO = CertificationInvestmentRDTOStub.defaultOne();
		certificationInvestmentFurnitureRDTO = CertificationInvestmentFurnitureRDTOStub.defaultOne();
		certificationCleaningServiceRDTO = CertificationCleaningServiceRDTOStub.defaultOne();
		certificationPersonalRDTO = CertificationPersonalRDTOStub.defaultOne();
		certificationCollectionServiceRDTO = CertificationCollectionServiceRDTOStub.defaultOne();
		certificationRegularizationRDTO = CertificationRegularizationRDTOStub.defaultOne(); 
		certificationOthersRDTO = CertificationOthersRDTOStub.defaultOne();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void caseOkSelectCertificationProposals() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathCertification", pathCertification);
		ReflectionTestUtils.setField(service, "pathCertificationProposalsGet", pathCertificationProposalsGet);
		
		String url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathCertification)
				.concat(pathCertificationProposalsGet);
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codi", queryParameterCertificationProposalsRDTO.getCodeContract());
		
		Mockito.when(frontValidateCertificationService.validateSyntaxSelectCertification(queryParameterCertificationProposalsRDTO)).thenReturn(returnRDTO);
		Mockito.when(restCall.executeGET(url, urlParams, queryParameterCertificationProposalsRDTO)).thenReturn(ss);
		
		ReturnCertificationProposalsRDTO returnCertificationProposalsRDTO = new ReturnCertificationProposalsRDTO();
		returnCertificationProposalsRDTO.setReturnRDTO(returnRDTO);
		Mockito.when(utils.rest2Object(Mockito.any(ss.getClass()), Mockito.any(returnCertificationProposalsRDTO.getClass()))).thenReturn(returnCertificationProposalsRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.selectCertificationProposals(queryParameterCertificationProposalsRDTO);
	}

	@Test
	public void caseSelectPendingKoValidateSyntax() throws ImiException {
		Mockito.when(frontValidateCertificationService.validateSyntaxSelectCertification(queryParameterCertificationProposalsRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.selectCertificationProposals(queryParameterCertificationProposalsRDTO);
	}

	@Test(expected = ImiException.class)
	public void caseKoSelectPending() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.selectCertificationProposals(queryParameterCertificationProposalsRDTO);
	}
	
	@Test
	public void caseOkSendVersionAndHeader() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathCertification" , pathCertification);
		ReflectionTestUtils.setField(service, "pathCertificationPostHeaders", pathCertificationPostHeaders);
		
		String url = urlApiOrquestrador + pathCertification + pathCertificationPostHeaders;
		url = urlApiOrquestrador.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathCertification)
				.concat(pathCertificationPostHeaders);
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(url, certificationRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		Mockito.when(frontValidateCertificationService.validateSyntaxInsertVersion(certificationRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateSendVersionAndHeader(certificationRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.sendVersionAndHeader(certificationRDTO);
	}

	@Test
	public void caseKoSendVersionAndHeaderValidationsSyntax() throws ImiException {
		Mockito.when(frontValidateCertificationService.validateSyntaxInsertVersion(certificationRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.sendVersionAndHeader(certificationRDTO);
	}
	
	@Test(expected = ImiException.class)
	public void caseKoSendVersionAndHeader() throws ImiException {
		service.sendVersionAndHeader(certificationRDTO);
	}


	@Test
	public void caseOkSendOtherExpenses() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathCertification", pathCertification);
		ReflectionTestUtils.setField(service, "pathCertificationOtherExpenses", pathCertificationOtherExpenses);

		String url = urlApiOrquestrador + pathCertification + pathCertificationOtherExpenses;
		url = urlApiOrquestrador.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathCertification)
				.concat(pathCertificationOtherExpenses);
		ResponseEntity<Object> ss = new ResponseEntity<Object>(HttpStatus.OK);

		Mockito.when(restCall.executePOST(url, certificationOthersRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		Mockito.when(frontValidateCertificationService.validateSyntaxInsertOtherExpenses(certificationOthersRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateSendOthersExpensesDetails(certificationOthersRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.sendOthersExpensesDetails(certificationOthersRDTO);
	}

	@Test
	public void caseKoSendOtherExpensesValidationsSyntax() throws ImiException {
		Mockito.when(frontValidateCertificationService.validateSyntaxInsertOtherExpenses(certificationOthersRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.sendOthersExpensesDetails(certificationOthersRDTO);
	}
	
	@Test(expected = ImiException.class)
	public void caseKoSendOtherExpenses() throws ImiException {
		service.sendOthersExpensesDetails(certificationOthersRDTO);
	}
	
	@Test
	public void caseOkSendExtraordinaryConcepts() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathCertification", pathCertification);
		ReflectionTestUtils.setField(service, "pathCertificationExtraordinaryConcepts", pathCertificationExtraordinaryConcepts);
		
		String url = urlApiOrquestrador + pathCertification + pathCertificationExtraordinaryConcepts;
		url = urlApiOrquestrador.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathCertification)
				.concat(pathCertificationExtraordinaryConcepts);
		ResponseEntity<Object> ss = new ResponseEntity<Object>(HttpStatus.OK);

		Mockito.when(restCall.executePOST(url, certificationExtraordinaryRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		Mockito.when(frontValidateCertificationService.validateSyntaxInsertExtraordinaryConcept(certificationExtraordinaryRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateSendExtraordinaryConceptsDetails(certificationExtraordinaryRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.sendExtraordinaryConceptsDetails(certificationExtraordinaryRDTO);
	}

	@Test
	public void caseKoSendExtraordinaryConceptsValidationsSyntax() throws ImiException {
		Mockito.when(frontValidateCertificationService.validateSyntaxInsertExtraordinaryConcept(certificationExtraordinaryRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.sendExtraordinaryConceptsDetails(certificationExtraordinaryRDTO);
	}
	
	@Test(expected = ImiException.class)
	public void caseKoSendExtraordinaryConcepts() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.sendExtraordinaryConceptsDetails(certificationExtraordinaryRDTO);
	}
	
	@Test
	public void caseOkSendInspectionsDetails() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathCertification", pathCertification);
		ReflectionTestUtils.setField(service, "pathCertificationInspection", pathCertificationInspection);
		
		String url = urlApiOrquestrador + pathCertification + pathCertificationInspection;
		url = urlApiOrquestrador.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathCertification)
				.concat(pathCertificationInspection);
		ResponseEntity<Object> ss = new ResponseEntity<Object>(HttpStatus.CREATED);

		Mockito.when(restCall.executePOST(url, certificationInspectionRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		Mockito.when(frontValidateCertificationService.validateSyntaxInsertInspection(certificationInspectionRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateSendInspectionsDetails(certificationInspectionRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.sendInspectionsDetails(certificationInspectionRDTO);
	}

	@Test
	public void caseKoSendInspectionsValidationsSyntax() throws ImiException {
		Mockito.when(frontValidateCertificationService.validateSyntaxInsertInspection(certificationInspectionRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.sendInspectionsDetails(certificationInspectionRDTO);
	}
	
	@Test(expected = ImiException.class)
	public void caseKoSendInspectons() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.sendInspectionsDetails(certificationInspectionRDTO);
	}

	@Test
	public void caseOkSendInstallationsExpensesDetails() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathCertification", pathCertification);
		ReflectionTestUtils.setField(service, "pathCertificationInstallationExpenses", pathCertificationInstallationExpenses);
	
		String url = urlApiOrquestrador + pathCertification + pathCertificationInstallationExpenses;
		url = urlApiOrquestrador.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathCertification)
				.concat(pathCertificationInstallationExpenses);
		ResponseEntity<Object> ss = new ResponseEntity<Object>(HttpStatus.CREATED);

		Mockito.when(restCall.executePOST(url, certificationExpenseInstallationRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		Mockito.when(frontValidateCertificationService.validateSyntaxInsertInstallationExpenses(certificationExpenseInstallationRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateSendInstallationExpensesDetails(certificationExpenseInstallationRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.sendInstallationExpensesDetails(certificationExpenseInstallationRDTO);
	}

	@Test
	public void caseKoSendInstallationsExpensesValidationsSyntax() throws ImiException {
		Mockito.when(frontValidateCertificationService.validateSyntaxInsertInstallationExpenses(certificationExpenseInstallationRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.sendInstallationExpensesDetails(certificationExpenseInstallationRDTO);
	}
	
	@Test(expected = ImiException.class)
	public void caseKoSendInstallationsExpenses() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.sendInstallationExpensesDetails(certificationExpenseInstallationRDTO);
	}
	
	@Test
	public void caseOkSendInvestmentsDetails() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathCertification", pathCertification);
		ReflectionTestUtils.setField(service, "pathCertificationInvestmentRRMM", pathCertificationInvestmentRRMM);
		
		String url = urlApiOrquestrador + pathCertification + pathCertificationInvestmentRRMM;
		url = urlApiOrquestrador.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathCertification)
				.concat(pathCertificationInvestmentRRMM);
		ResponseEntity<Object> ss = new ResponseEntity<Object>(HttpStatus.CREATED);

		Mockito.when(restCall.executePOST(url, certificationInvestmentRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		Mockito.when(frontValidateCertificationService.validateSyntaxInsertInvestmentRRMM(certificationInvestmentRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateSendInvestmentRRMMDetails(certificationInvestmentRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.sendInvestmentRRMMDetails(certificationInvestmentRDTO);
	}

	@Test
	public void caseKoSendInvestmentsValidationsSyntax() throws ImiException {
		Mockito.when(frontValidateCertificationService.validateSyntaxInsertInvestmentRRMM(certificationInvestmentRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.sendInvestmentRRMMDetails(certificationInvestmentRDTO);
	}
	
	@Test(expected = ImiException.class)
	public void caseKoSendInvestments() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.sendInvestmentRRMMDetails(certificationInvestmentRDTO);
	}
	
	@Test
	public void caseOkSendInvestmentsFurnitureDetails() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathCertification", pathCertification);
		ReflectionTestUtils.setField(service, "pathCertificationInvestmentFurniture", pathCertificationInvestmentFurniture);

		String url = urlApiOrquestrador + pathCertification + pathCertificationInvestmentFurniture;
		url = urlApiOrquestrador
				.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathCertification)
				.concat(pathCertificationInvestmentFurniture);
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>(HttpStatus.CREATED);

		Mockito.when(restCall.executePOST(url, certificationInvestmentFurnitureRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		Mockito.when(frontValidateCertificationService.validateSyntaxInsertInvestmentFurniture(certificationInvestmentFurnitureRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateSendInvestmentFurnitureDetails(certificationInvestmentFurnitureRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.sendInvestmentFurnitureDetails(certificationInvestmentFurnitureRDTO);
	}

	@Test
	public void caseKoSendInvestmentFurnitureValidationsSyntax() throws ImiException {
		Mockito.when(frontValidateCertificationService.validateSyntaxInsertInvestmentFurniture(certificationInvestmentFurnitureRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.sendInvestmentFurnitureDetails(certificationInvestmentFurnitureRDTO);
	}
	
	@Test(expected = ImiException.class)
	public void caseKoSendInvestmentFurniture() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.sendInvestmentFurnitureDetails(certificationInvestmentFurnitureRDTO);
	}
		
	@Test
	public void caseOkSendCleaningServiceDetails() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathCertification", pathCertification);
		ReflectionTestUtils.setField(service, "pathCertificationCleaningService", pathCertificationCleaningService);

		String url = urlApiOrquestrador + pathCertification + pathCertificationCleaningService;
		url = urlApiOrquestrador.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathCertification)
				.concat(pathCertificationCleaningService);
		ResponseEntity<Object> ss = new ResponseEntity<Object>(HttpStatus.CREATED);

		Mockito.when(restCall.executePOST(url, certificationCleaningServiceRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		Mockito.when(frontValidateCertificationService.validateSyntaxInsertCleaningService(certificationCleaningServiceRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateSendCleaningServiceDetails(certificationCleaningServiceRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.sendCleaningServiceDetails(certificationCleaningServiceRDTO);
	}

	@Test
	public void caseKoSendCleaningServiceValidationsSyntax() throws ImiException {
		Mockito.when(frontValidateCertificationService.validateSyntaxInsertCleaningService(certificationCleaningServiceRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.sendCleaningServiceDetails(certificationCleaningServiceRDTO);
	}
	
	@Test(expected = ImiException.class)
	public void caseKoSendCleaningService() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.sendCleaningServiceDetails(certificationCleaningServiceRDTO);
	}
	
	@Test
	public void caseOkSendStaffCostDetails() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathCertification", pathCertification);
		ReflectionTestUtils.setField(service, "pathCertificationStaffExpense", pathCertificationStaffExpense);
		
		String url = urlApiOrquestrador + pathCertification + pathCertificationStaffExpense;
		url = urlApiOrquestrador.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathCertification)
				.concat(pathCertificationStaffExpense);
		ResponseEntity<Object> ss = new ResponseEntity<Object>(HttpStatus.CREATED);

		Mockito.when(restCall.executePOST(url, certificationPersonalRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		Mockito.when(frontValidateCertificationService.validateSyntaxInsertStaffExpenses(certificationPersonalRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateSendStaffExpensesDetails(certificationPersonalRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.sendStaffExpensesDetails(certificationPersonalRDTO);
	}

	@Test
	public void caseKoSendStaffCostDetailsValidationsSyntax() throws ImiException {
		Mockito.when(frontValidateCertificationService.validateSyntaxInsertStaffExpenses(certificationPersonalRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.sendStaffExpensesDetails(certificationPersonalRDTO);
	}
	
	@Test(expected = ImiException.class)
	public void caseKoSendStaffCostDetails() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.sendStaffExpensesDetails(certificationPersonalRDTO);
	}
	
	@Test
	public void caseOkSendCollectionSeviceDetails() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathCertification", pathCertification);
		ReflectionTestUtils.setField(service, "pathCertificationCollectionService", pathCertificationCollectionService);

		String url = urlApiOrquestrador + pathCertification + pathCertificationCollectionService;
		url = urlApiOrquestrador.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathCertification)
				.concat(pathCertificationCollectionService);
		ResponseEntity<Object> ss = new ResponseEntity<Object>(HttpStatus.CREATED);

		Mockito.when(restCall.executePOST(url, certificationCollectionServiceRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		Mockito.when(frontValidateCertificationService.validateSyntaxInsertColectionService(certificationCollectionServiceRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateSendCollectionServiceDetails(certificationCollectionServiceRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.sendCollectionServiceDetails(certificationCollectionServiceRDTO);
	}

	@Test
	public void caseKoSendCollectionSeviceDetailsValidationsSyntax() throws ImiException {
		Mockito.when(frontValidateCertificationService.validateSyntaxInsertColectionService(certificationCollectionServiceRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.sendCollectionServiceDetails(certificationCollectionServiceRDTO);
	}
	
	@Test(expected = ImiException.class)
	public void caseKoSendCollectionSeviceDetails() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.sendCollectionServiceDetails(certificationCollectionServiceRDTO);
	}
		
	@Test
	public void caseOkSendRegularizationsDetails() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiOrquestrador", urlApiOrquestrador);
		ReflectionTestUtils.setField(service, "pathCertification", pathCertification);
		ReflectionTestUtils.setField(service, "pathCertificationRegularization", pathCertificationRegularization);
		
		String url = urlApiOrquestrador + pathCertification + pathCertificationRegularization;
		url = urlApiOrquestrador.concat(ImiConstants.SLASH)
				.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
				.concat(pathCertification)
				.concat(pathCertificationRegularization);
		ResponseEntity<Object> ss = new ResponseEntity<Object>(HttpStatus.CREATED);

		Mockito.when(restCall.executePOST(url, certificationRegularizationRDTO)).thenReturn(ss);
		Mockito.when(utils.rest2Object(ss, returnRDTO)).thenReturn(returnRDTO);
		Mockito.when(frontValidateCertificationService.validateSyntaxInsertRegularization(certificationRegularizationRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateSendRegularizationsDetails(certificationRegularizationRDTO)).thenReturn(returnRDTO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.sendRegularizationsDetails(certificationRegularizationRDTO);
	}

	@Test
	public void caseKoSendRegularizationsDetailsValidationsSyntax() throws ImiException {
		Mockito.when(frontValidateCertificationService.validateSyntaxInsertRegularization(certificationRegularizationRDTO)).thenReturn(returnRDTOKO);
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		
		service.sendRegularizationsDetails(certificationRegularizationRDTO);
	}
	
	@Test(expected = ImiException.class)
	public void caseKoSendRegularizationsDetails() throws ImiException {
		Mockito.when(validator.validateContract(Mockito.any(BaseTransactionIdRDTO.class))).thenReturn(returnRDTO);
		service.sendRegularizationsDetails(certificationRegularizationRDTO);
	}	

}
