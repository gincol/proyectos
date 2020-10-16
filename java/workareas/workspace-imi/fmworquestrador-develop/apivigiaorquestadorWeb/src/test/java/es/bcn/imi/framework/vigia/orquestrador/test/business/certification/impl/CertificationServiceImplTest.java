package es.bcn.imi.framework.vigia.orquestrador.test.business.certification.impl;

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

import es.bcn.imi.framework.vigia.orquestrador.business.certification.impl.CertificationServiceImpl;
import es.bcn.imi.framework.vigia.orquestrador.web.rest.config.RestServerConfig;
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

@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RestServerConfig.class })
@TestPropertySource(locations = {"classpath:app/config/application.properties"})
@WebAppConfiguration
public class CertificationServiceImplTest{
	
	@Value("${url.api.certification}")
	private String urlApiCertification;
	
	@Value("${url.path.certification}")
	private String pathCertification;
	
	@Value("${url.path.certification.proposals.get}")
	private String pathCertificationProposalsGet;

	@Value("${url.path.certification.other.expenses}")
	private String pathCertificationOtherExpenses;

	@Value("${url.path.certification.extra.concepts}")
	private String pathCertificationExtraordinaryConcepts;

	@Value("${url.path.certification.inspection}")
	private String pathCertificationInspection;

	@Value("${url.path.certification.installation.expenses}")
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
	private String pathCertificationStaffExpenses;

	@Value("${url.path.certification.invoice}")		
	private String pathCertificationInvoice;

	@Mock
	private RestCall restCall;
	
	@Mock
	private Utils utils;
	
	@InjectMocks
	private CertificationServiceImpl service;
	
	
	private ReturnRDTO returnRDTO;
	
	private CertificationRDTO certificationRDTO;

	private CertificationOthersRDTO certificationOthersRDTO;
	
	private QueryParameterCertificationProposalsRDTO queryParameterCertificationProposalsRDTO;

	private CertificationExtraordinaryRDTO  certificationExtraordinaryRDTO;

	private CertificationInspectionRDTO certificationInspectionRDTO;
	
	private CertificationExpenseInstallationRDTO certificationExpenseInstallationRDTO;
	
	private CertificationInvestmentRDTO certificationInvestmentRDTO;
	
	private CertificationInvestmentFurnitureRDTO certificationInvestmentFurnitureRDTO;
	
	private CertificationCleaningServiceRDTO certificationCleaningServiceRDTO;
	
	private CertificationPersonalRDTO certificationPersonalRDTO;
	
	private CertificationCollectionServiceRDTO certificationCollectionServiceRDTO;
	
	private CertificationRegularizationRDTO certificationRegularizationRDTO;
	
	
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		returnRDTO 				= ReturnRDTOStub.getSuccessMessage();
		queryParameterCertificationProposalsRDTO = QueryParameterCertificationProposalsRDTOStub.defaultOne();
		certificationRDTO = CertificationRDTOStub.defaultOne();
		certificationOthersRDTO = CertificationOthersRDTOStub.defaultOne();
		certificationExtraordinaryRDTO = CertificationExtraordinaryRDTOStub.defaultOne(); 
		certificationInspectionRDTO = CertificationInspectionRDTOStub.defaultOne();
		certificationExpenseInstallationRDTO = CertificationExpenseInstallationRDTOStub.defaultOne();
		certificationInvestmentRDTO = CertificationInvestmentRDTOStub.defaultOne();
		certificationInvestmentFurnitureRDTO = CertificationInvestmentFurnitureRDTOStub.defaultOne();
		certificationCleaningServiceRDTO = CertificationCleaningServiceRDTOStub.defaultOne();
		certificationPersonalRDTO = CertificationPersonalRDTOStub.defaultOne();
		certificationCollectionServiceRDTO = CertificationCollectionServiceRDTOStub.defaultOne();
		certificationRegularizationRDTO = CertificationRegularizationRDTOStub.defaultOne();
		
	}  
	
	@Test
	public void caseOkSelectCertificationProposals() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiCertification", urlApiCertification);
		ReflectionTestUtils.setField(service, "pathCertification", pathCertification);
		ReflectionTestUtils.setField(service, "pathCertificationProposalsGet", pathCertificationProposalsGet);
		String url = urlApiCertification + pathCertification + pathCertificationProposalsGet;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("codiContracta", queryParameterCertificationProposalsRDTO.getCodeContract());
		Mockito.when(restCall.executeGET(url, urlParams, queryParameterCertificationProposalsRDTO)).thenReturn(ss);
		service.selectCertificationProposals(queryParameterCertificationProposalsRDTO);
	}
	
	@Test
	public void caseOkSendVersionAndHeader() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiCertification", urlApiCertification);
		ReflectionTestUtils.setField(service, "pathCertification", pathCertification);
		
		String url = urlApiCertification + pathCertification;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(url, certificationRDTO)).thenReturn(ss);
		service.sendVersionAndHeader(certificationRDTO);
	}
	
	@Test
	public void caseOkSendOthersExpensesDetails() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiCertification", urlApiCertification);
		ReflectionTestUtils.setField(service, "pathCertification", pathCertification);
		ReflectionTestUtils.setField(service, "pathCertificationOtherExpenses", pathCertificationOtherExpenses);
		
		String url = urlApiCertification + pathCertification + pathCertificationOtherExpenses;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(url, certificationOthersRDTO)).thenReturn(ss);
		service.sendOthersExpensesDetails(certificationOthersRDTO);
	}
	
	@Test
	public void caseOkSendExtraordinaryConceptsDetails() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiCertification", urlApiCertification);
		ReflectionTestUtils.setField(service, "pathCertification", pathCertification);
		ReflectionTestUtils.setField(service, "pathCertificationExtraordinaryConcepts", pathCertificationExtraordinaryConcepts);
		
		String url = urlApiCertification + pathCertification + pathCertificationExtraordinaryConcepts;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(url, certificationExtraordinaryRDTO)).thenReturn(ss);
		service.sendExtraordinaryConceptsDetails(certificationExtraordinaryRDTO);
	}
	
	@Test
	public void caseOkSendInspectionsDetails() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiCertification", urlApiCertification);
		ReflectionTestUtils.setField(service, "pathCertification", pathCertification);
		ReflectionTestUtils.setField(service, "pathCertificationInspection", pathCertificationInspection);
		
		String url = urlApiCertification + pathCertification + pathCertificationInspection;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(url, certificationInspectionRDTO)).thenReturn(ss);
		service.sendInspectionsDetails(certificationInspectionRDTO);
	}
	
	@Test
	public void caseOkSendInstallationsCostDetails() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiCertification", urlApiCertification);
		ReflectionTestUtils.setField(service, "pathCertification", pathCertification);
		ReflectionTestUtils.setField(service, "pathCertificationInstallationExpenses", pathCertificationInstallationExpenses);
		
		String url = urlApiCertification + pathCertification + pathCertificationInstallationExpenses;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(url, certificationExpenseInstallationRDTO)).thenReturn(ss);
		service.sendInstallationsCostDetails(certificationExpenseInstallationRDTO);
	}
	
	@Test
	public void caseOkSendInvestmentsDetails() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiCertification", urlApiCertification);
		ReflectionTestUtils.setField(service, "pathCertification", pathCertification);
		ReflectionTestUtils.setField(service, "pathCertificationInvestmentRRMM", pathCertificationInvestmentRRMM);
		
		String url = urlApiCertification + pathCertification + pathCertificationInvestmentRRMM;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(url, certificationInvestmentRDTO)).thenReturn(ss);
		service.sendInvestmentsDetails(certificationInvestmentRDTO);
	}
	
	@Test
	public void caseOkSendInvestmentsFurnitureDetails() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiCertification", urlApiCertification);
		ReflectionTestUtils.setField(service, "pathCertification", pathCertification);
		ReflectionTestUtils.setField(service, "pathCertificationInvestmentFurniture", pathCertificationInvestmentFurniture);
		
		String url = urlApiCertification + pathCertification + pathCertificationInvestmentFurniture;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(url, certificationInvestmentFurnitureRDTO)).thenReturn(ss);
		service.sendInvestmentsFurnitureDetails(certificationInvestmentFurnitureRDTO);
	}
	
	@Test
	public void caseOkSendCleaningServiceDetails() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiCertification", urlApiCertification);
		ReflectionTestUtils.setField(service, "pathCertification", pathCertification);
		ReflectionTestUtils.setField(service, "pathCertificationCleaningService", pathCertificationCleaningService);
		
		String url = urlApiCertification + pathCertification + pathCertificationCleaningService;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(url, certificationCleaningServiceRDTO)).thenReturn(ss);
		service.sendCleaningServiceDetails(certificationCleaningServiceRDTO);
	}
	
	@Test
	public void caseOkSendStaffCostDetails() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiCertification", urlApiCertification);
		ReflectionTestUtils.setField(service, "pathCertification", pathCertification);
		ReflectionTestUtils.setField(service, "pathCertificationStaffExpenses", pathCertificationStaffExpenses);
		
		String url = urlApiCertification + pathCertification + pathCertificationStaffExpenses;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(url, certificationPersonalRDTO)).thenReturn(ss);
		service.sendStaffCostDetails(certificationPersonalRDTO);
	}
	
	@Test
	public void caseOkSendCollectionSeviceDetails() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiCertification", urlApiCertification);
		ReflectionTestUtils.setField(service, "pathCertification", pathCertification);
		ReflectionTestUtils.setField(service, "pathCertificationCollectionService", pathCertificationCollectionService);
		
		String url = urlApiCertification + pathCertification + pathCertificationCollectionService;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(url, certificationCollectionServiceRDTO)).thenReturn(ss);
		service.sendCollectionSeviceDetails(certificationCollectionServiceRDTO);
	}
	
	@Test
	public void caseOkSendRegularizationsDetails() throws ImiException {
		ReflectionTestUtils.setField(service, "urlApiCertification", urlApiCertification);
		ReflectionTestUtils.setField(service, "pathCertification", pathCertification);
		ReflectionTestUtils.setField(service, "pathCertificationRegularization", pathCertificationRegularization);
		
		String url = urlApiCertification + pathCertification + pathCertificationRegularization;
		
		ResponseEntity<Object> ss = new ResponseEntity<Object>("this is a body", HttpStatus.ACCEPTED);
		
		Mockito.when(restCall.executePOST(url, certificationRegularizationRDTO)).thenReturn(ss);
		service.sendRegularizationsDetails(certificationRegularizationRDTO);
	}
	
	
}
