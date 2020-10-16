package es.bcn.imi.framework.vigia.orquestrador.business.certification.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import es.bcn.imi.framework.vigia.orquestrador.business.certification.CertificationService;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CertificationCleaningServiceRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CertificationCollectionServiceRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CertificationExpenseInstallationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CertificationExtraordinaryRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CertificationInspectionRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CertificationInvestmentFurnitureRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CertificationInvestmentRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CertificationInvoiceRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CertificationOthersRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CertificationPersonalRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CertificationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CertificationRegularizationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterCertificationProposalsRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnCertificationProposalsRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import es.bcn.vigia.fmw.libutils.services.Utils;
import es.bcn.vigia.fmw.libutils.services.rest.RestCall;

@Lazy
@Service(ServicesConstants.SRV_CERTIFICATION_CERTIFICATION)
public class CertificationServiceImpl implements CertificationService{

	private Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ServicesConstants.SRV_REST_CALL)
	private RestCall restCall;

	@Autowired
	@Qualifier(ServicesConstants.SRV_UTILS)
	private Utils utils;
	
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
	
	private ReturnRDTO returnRDTO;
	
	
	
	@Override
	public ReturnRDTO sendVersionAndHeader(CertificationRDTO certificationRDTO) throws ImiException {
		try {

			returnRDTO = new ReturnRDTO();

			String url = urlApiCertification + pathCertification;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			ResponseEntity<Object> resp = restCall.executePOST(url, certificationRDTO);

			returnRDTO = (ReturnRDTO) utils.rest2Object(resp, returnRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnRDTO;
	}

	@Override
	public ReturnRDTO sendOthersExpensesDetails(CertificationOthersRDTO certificationOthersRDTO) throws ImiException {
		try {

			returnRDTO = new ReturnRDTO();

			String url = urlApiCertification + pathCertification + pathCertificationOtherExpenses;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			ResponseEntity<Object> resp = restCall.executePOST(url, certificationOthersRDTO);

			returnRDTO = (ReturnRDTO) utils.rest2Object(resp, returnRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnRDTO;

	}

	@Override
	public ReturnRDTO sendExtraordinaryConceptsDetails(CertificationExtraordinaryRDTO certificationExtraordinaryRDTO) throws ImiException {
		try {

			returnRDTO = new ReturnRDTO();

			String url = urlApiCertification + pathCertification + pathCertificationExtraordinaryConcepts;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			ResponseEntity<Object> resp = restCall.executePOST(url, certificationExtraordinaryRDTO);

			returnRDTO = (ReturnRDTO) utils.rest2Object(resp, returnRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnRDTO;
	}

	

	@Override
	public ReturnRDTO sendInspectionsDetails(CertificationInspectionRDTO certificationInspectionRDTO) throws ImiException {
		try {

			returnRDTO = new ReturnRDTO();

			String url = urlApiCertification + pathCertification + pathCertificationInspection;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			ResponseEntity<Object> resp = restCall.executePOST(url, certificationInspectionRDTO);

			returnRDTO = (ReturnRDTO) utils.rest2Object(resp, returnRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnRDTO;

	}

	@Override
	public ReturnRDTO sendInstallationsCostDetails(CertificationExpenseInstallationRDTO certificationExpenseInstallationRDTO) throws ImiException {
		try {

			returnRDTO = new ReturnRDTO();

			String url = urlApiCertification + pathCertification + pathCertificationInstallationExpenses;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			ResponseEntity<Object> resp = restCall.executePOST(url, certificationExpenseInstallationRDTO);

			returnRDTO = (ReturnRDTO) utils.rest2Object(resp, returnRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnRDTO;

	}

	@Override
	public ReturnRDTO sendInvestmentsDetails(CertificationInvestmentRDTO certificationInvestmentRDTO) throws ImiException {
		try {

			returnRDTO = new ReturnRDTO();

			String url = urlApiCertification + pathCertification + pathCertificationInvestmentRRMM;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			ResponseEntity<Object> resp = restCall.executePOST(url, certificationInvestmentRDTO);

			returnRDTO = (ReturnRDTO) utils.rest2Object(resp, returnRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnRDTO;
	}

	@Override
	public ReturnRDTO sendInvestmentsFurnitureDetails(
			CertificationInvestmentFurnitureRDTO certificationInvestmentFurnitureRDTO) throws ImiException {
		try {

			returnRDTO = new ReturnRDTO();

			String url = urlApiCertification + pathCertification + pathCertificationInvestmentFurniture;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			ResponseEntity<Object> resp = restCall.executePOST(url, certificationInvestmentFurnitureRDTO);

			returnRDTO = (ReturnRDTO) utils.rest2Object(resp, returnRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnRDTO;
	}

	@Override
	public ReturnRDTO sendCleaningServiceDetails(CertificationCleaningServiceRDTO certificationCleaningServiceRDTO) throws ImiException {
		try {

			returnRDTO = new ReturnRDTO();

			String url = urlApiCertification + pathCertification + pathCertificationCleaningService;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			ResponseEntity<Object> resp = restCall.executePOST(url, certificationCleaningServiceRDTO);

			returnRDTO = (ReturnRDTO) utils.rest2Object(resp, returnRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnRDTO;	}

	@Override
	public ReturnRDTO sendStaffCostDetails(CertificationPersonalRDTO certificationPersonalRDTO) throws ImiException {
		try {

			returnRDTO = new ReturnRDTO();

			String url = urlApiCertification + pathCertification + pathCertificationStaffExpenses;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			ResponseEntity<Object> resp = restCall.executePOST(url, certificationPersonalRDTO);

			returnRDTO = (ReturnRDTO) utils.rest2Object(resp, returnRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnRDTO;
	}

	@Override
	public ReturnRDTO sendCollectionSeviceDetails(CertificationCollectionServiceRDTO certificationCollectionServiceRDTO) throws ImiException {
		try {

			returnRDTO = new ReturnRDTO();

			String url = urlApiCertification + pathCertification + pathCertificationCollectionService;
			ResponseEntity<Object> resp = restCall.executePOST(url, certificationCollectionServiceRDTO);

			returnRDTO = (ReturnRDTO) utils.rest2Object(resp, returnRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnRDTO;
	}

	@Override
	public ReturnRDTO sendRegularizationsDetails(CertificationRegularizationRDTO certificationRegularizationRDTO) throws ImiException {
		try {

			returnRDTO = new ReturnRDTO();

			String url = urlApiCertification + pathCertification + pathCertificationRegularization;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			ResponseEntity<Object> resp = restCall.executePOST(url, certificationRegularizationRDTO);

			returnRDTO = (ReturnRDTO) utils.rest2Object(resp, returnRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnRDTO;
	}

	

	@Override
	public ReturnCertificationProposalsRDTO selectCertificationProposals(QueryParameterCertificationProposalsRDTO queryParameterCertificationProposalsRDTO) throws ImiException {
		ReturnCertificationProposalsRDTO returnActuationRDTO = new ReturnCertificationProposalsRDTO();

		try {

			String url = urlApiCertification + pathCertification + pathCertificationProposalsGet;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			Map<String, String> urlParams = new HashMap<String, String>();
			urlParams.put("codi", queryParameterCertificationProposalsRDTO.getCodeContract());
			urlParams.put("idCertificacio", queryParameterCertificationProposalsRDTO.getIdCertification());
			urlParams.put("transactionId", queryParameterCertificationProposalsRDTO.getTransactionId());
			
			ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterCertificationProposalsRDTO);

			returnActuationRDTO = (ReturnCertificationProposalsRDTO) utils.rest2Object(resp, returnActuationRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnActuationRDTO;
	}


	@Override
	public ReturnRDTO sendInvoiceDetails(CertificationInvoiceRDTO certificationInvoiceRDTO,
			MultipartFile attachedFile) throws ImiException {
	try{
		returnRDTO = new ReturnRDTO();

		String url = urlApiCertification + pathCertification + pathCertificationInvoice;

		logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

		ResponseEntity<Object> resp = restCall.executeFilesPOST(url, ImiConstants.NAME_PARAMETER_INVOICE_FILE, attachedFile, ImiConstants.NAME_PARAMETER_INVOICE_JSON, certificationInvoiceRDTO);

		returnRDTO = (ReturnRDTO) utils.rest2Object(resp, returnRDTO);

		logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

	} catch (Exception ex) {
		logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
		throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
	}

	return returnRDTO;

	}



}
