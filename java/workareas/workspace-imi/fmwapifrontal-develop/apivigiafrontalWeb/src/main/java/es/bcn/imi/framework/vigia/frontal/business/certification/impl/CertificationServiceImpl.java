package es.bcn.imi.framework.vigia.frontal.business.certification.impl;

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

import es.bcn.imi.framework.vigia.frontal.business.certification.CertificationService;
import es.bcn.imi.framework.vigia.frontal.validator.ValidatorCertification;
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
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.commons.BaseTransactionIdRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnCertificationProposalsRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.enums.ReturnEnum;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import es.bcn.vigia.fmw.libutils.services.Utils;
import es.bcn.vigia.fmw.libutils.services.rest.RestCall;
import es.bcn.vigia.fmw.libutils.validator.front.FrontValidateCertificationService;

@Lazy
@Service(ServicesConstants.SRV_FRONTAL_CERTIFICATION)
public class CertificationServiceImpl implements CertificationService{
	
	private Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ServicesConstants.SRV_REST_CALL)
	private RestCall restCall;

	@Autowired
	@Qualifier(ServicesConstants.SRV_UTILS)
	private Utils utils;

	@Autowired
	@Qualifier(ServicesConstants.SRV_FRONT_VALIDATE_CERTIFICATION)
	private FrontValidateCertificationService frontValidateCertificationService;

	@Autowired
	@Qualifier(ServicesConstants.SRV_FRONT_VALIDATE_CERTIFICATION_FMW)
	private ValidatorCertification validator;

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
	
	private ReturnRDTO returnRDTO;

	private String url;

	private void processResponse(ResponseEntity<Object> resp) throws ImiException

	{
		returnRDTO = (ReturnRDTO) utils.rest2Object(resp, returnRDTO);

		logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

	}

	private ReturnRDTO getTransactionId(BaseTransactionIdRDTO eventRDTO) {

		returnRDTO.setTransactionId(eventRDTO.getTransactionId());

		return returnRDTO;
	}

	@Override
	public ReturnRDTO sendVersionAndHeader(CertificationRDTO certificationRDTO) throws ImiException {
		try {

			logger.info(LogsConstants.LOG_START);

			certificationRDTO.generateTransactionId();

			returnRDTO = validator.validateContract(certificationRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateCertificationService.validateSyntaxInsertVersion(certificationRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				returnRDTO = validator.validateSendVersionAndHeader(certificationRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador + pathCertification + pathCertificationPostHeaders;
				url = urlApiOrquestrador.concat(ImiConstants.SLASH)
						.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
						.concat(pathCertification)
						.concat(pathCertificationPostHeaders);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				ResponseEntity<Object> resp = restCall.executePOST(url, certificationRDTO);

				processResponse(resp);
			}
		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}
		returnRDTO = getTransactionId(certificationRDTO);

		return returnRDTO;
	}

	@Override
	public ReturnRDTO sendOthersExpensesDetails(CertificationOthersRDTO certificationOthersRDTO) throws ImiException {
		try {

			logger.info(LogsConstants.LOG_START);

			certificationOthersRDTO.generateTransactionId();

			returnRDTO = validator.validateContract(certificationOthersRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateCertificationService.validateSyntaxInsertOtherExpenses(certificationOthersRDTO);
			}
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				returnRDTO = validator.validateSendOthersExpensesDetails(certificationOthersRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH)
						.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
						.concat(pathCertification)
						.concat(pathCertificationOtherExpenses);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				ResponseEntity<Object> resp = restCall.executePOST(url, certificationOthersRDTO);

				processResponse(resp);
			}
		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}
		returnRDTO = getTransactionId(certificationOthersRDTO);

		return returnRDTO;

	}

	@Override
	public ReturnRDTO sendExtraordinaryConceptsDetails(CertificationExtraordinaryRDTO certificationExtraordinaryRDTO)
			throws ImiException {
		try {

			logger.info(LogsConstants.LOG_START);

			certificationExtraordinaryRDTO.generateTransactionId();

			returnRDTO = validator.validateContract(certificationExtraordinaryRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateCertificationService.validateSyntaxInsertExtraordinaryConcept(certificationExtraordinaryRDTO);
			}
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				returnRDTO = validator.validateSendExtraordinaryConceptsDetails(certificationExtraordinaryRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH)
						.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
						.concat(pathCertification)
						.concat(pathCertificationExtraordinaryConcepts);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				ResponseEntity<Object> resp = restCall.executePOST(url, certificationExtraordinaryRDTO);

				processResponse(resp);
			}
		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}
		returnRDTO = getTransactionId(certificationExtraordinaryRDTO);

		return returnRDTO;
	}

	@Override
	public ReturnRDTO sendInvoicesDetails(CertificationInvoiceRDTO certificationInvoiceRDTO, MultipartFile attachedFile) throws ImiException {
		try {

			logger.info(LogsConstants.LOG_START);

			certificationInvoiceRDTO.generateTransactionId();

			returnRDTO = new ReturnRDTO();
			returnRDTO = validator.validateContract(certificationInvoiceRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateCertificationService.validateSyntaxInsertInvoice(certificationInvoiceRDTO);
			}
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				returnRDTO = validator.validateSendInvoiceDetails(certificationInvoiceRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH)
						.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
						.concat(pathCertification)
						.concat(pathCertificationInvoice);

				
				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));
				
				ResponseEntity<Object> resp = restCall.executeFilesPOST(url, ImiConstants.NAME_PARAMETER_INVOICE_FILE, attachedFile,ImiConstants.NAME_PARAMETER_INVOICE_JSON, certificationInvoiceRDTO);
				
				processResponse(resp);
				
			}
		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}
		returnRDTO = getTransactionId(certificationInvoiceRDTO);

		return returnRDTO;
}

	@Override
	public ReturnRDTO sendInspectionsDetails(CertificationInspectionRDTO certificationInspectionRDTO)
			throws ImiException {
		try {

			logger.info(LogsConstants.LOG_START);

			certificationInspectionRDTO.generateTransactionId();

			returnRDTO = validator.validateContract(certificationInspectionRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateCertificationService.validateSyntaxInsertInspection(certificationInspectionRDTO);
			}
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				returnRDTO = validator.validateSendInspectionsDetails(certificationInspectionRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH)
						.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
						.concat(pathCertification)
						.concat(pathCertificationInspection);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				ResponseEntity<Object> resp = restCall.executePOST(url, certificationInspectionRDTO);

				processResponse(resp);
			}
		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}
		returnRDTO = getTransactionId(certificationInspectionRDTO);

		return returnRDTO;
	}

	@Override
	public ReturnRDTO sendInstallationExpensesDetails(
			CertificationExpenseInstallationRDTO certificationExpenseInstallationRDTO) throws ImiException {
		try {

			logger.info(LogsConstants.LOG_START);

			certificationExpenseInstallationRDTO.generateTransactionId();

			returnRDTO = validator.validateContract(certificationExpenseInstallationRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateCertificationService.validateSyntaxInsertInstallationExpenses(certificationExpenseInstallationRDTO);
			}
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				returnRDTO = validator.validateSendInstallationExpensesDetails(certificationExpenseInstallationRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH)
						.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
						.concat(pathCertification)
						.concat(pathCertificationInstallationExpenses);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				ResponseEntity<Object> resp = restCall.executePOST(url, certificationExpenseInstallationRDTO);

				processResponse(resp);
			}
		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}
		returnRDTO = getTransactionId(certificationExpenseInstallationRDTO);

		return returnRDTO;
	}

	@Override
	public ReturnRDTO sendInvestmentRRMMDetails(CertificationInvestmentRDTO certificationInvestmentRRMMRDTO)
			throws ImiException {
		try {

			logger.info(LogsConstants.LOG_START);

			certificationInvestmentRRMMRDTO.generateTransactionId();

			returnRDTO = validator.validateContract(certificationInvestmentRRMMRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateCertificationService.validateSyntaxInsertInvestmentRRMM(certificationInvestmentRRMMRDTO);
			}
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				returnRDTO = validator.validateSendInvestmentRRMMDetails(certificationInvestmentRRMMRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH)
						.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
						.concat(pathCertification)
						.concat(pathCertificationInvestmentRRMM);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				ResponseEntity<Object> resp = restCall.executePOST(url, certificationInvestmentRRMMRDTO);

				processResponse(resp);
			}
		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}
		returnRDTO = getTransactionId(certificationInvestmentRRMMRDTO);

		return returnRDTO;
	}

	@Override
	public ReturnRDTO sendInvestmentFurnitureDetails(
			CertificationInvestmentFurnitureRDTO certificationInvestmentFurnitureRDTO) throws ImiException {
		try {

			logger.info(LogsConstants.LOG_START);

			certificationInvestmentFurnitureRDTO.generateTransactionId();

			returnRDTO = validator.validateContract(certificationInvestmentFurnitureRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateCertificationService.validateSyntaxInsertInvestmentFurniture(certificationInvestmentFurnitureRDTO);
			}
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				returnRDTO = validator.validateSendInvestmentFurnitureDetails(certificationInvestmentFurnitureRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH)
						.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
						.concat(pathCertification)
						.concat(pathCertificationInvestmentFurniture);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				ResponseEntity<Object> resp = restCall.executePOST(url, certificationInvestmentFurnitureRDTO);

				processResponse(resp);
			}
		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}
		returnRDTO = getTransactionId(certificationInvestmentFurnitureRDTO);

		return returnRDTO;

	}

	@Override
	public ReturnRDTO sendCleaningServiceDetails(CertificationCleaningServiceRDTO certificationCleaningServiceRDTO)
			throws ImiException {
		try {

			logger.info(LogsConstants.LOG_START);

			certificationCleaningServiceRDTO.generateTransactionId();

			returnRDTO = validator.validateContract(certificationCleaningServiceRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateCertificationService.validateSyntaxInsertCleaningService(certificationCleaningServiceRDTO);
			}
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				returnRDTO = validator.validateSendCleaningServiceDetails(certificationCleaningServiceRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH)
						.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
						.concat(pathCertification)
						.concat(pathCertificationCleaningService);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				ResponseEntity<Object> resp = restCall.executePOST(url, certificationCleaningServiceRDTO);

				processResponse(resp);
			}
		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}
		returnRDTO = getTransactionId(certificationCleaningServiceRDTO);

		return returnRDTO;

	}

	@Override
	public ReturnRDTO sendStaffExpensesDetails(CertificationPersonalRDTO certificationPersonalRDTO)
			throws ImiException {
		try {

			logger.info(LogsConstants.LOG_START);

			certificationPersonalRDTO.generateTransactionId();

			returnRDTO = validator.validateContract(certificationPersonalRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateCertificationService.validateSyntaxInsertStaffExpenses(certificationPersonalRDTO);
			}
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				returnRDTO = validator.validateSendStaffExpensesDetails(certificationPersonalRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH)
						.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
						.concat(pathCertification)
						.concat(pathCertificationStaffExpense);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				ResponseEntity<Object> resp = restCall.executePOST(url, certificationPersonalRDTO);

				processResponse(resp);
			}
		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}
		returnRDTO = getTransactionId(certificationPersonalRDTO);

		return returnRDTO;

	}

	@Override
	public ReturnRDTO sendCollectionServiceDetails(
			CertificationCollectionServiceRDTO certificationCollectionServiceRDTO) throws ImiException {
		try {

			logger.info(LogsConstants.LOG_START);

			certificationCollectionServiceRDTO.generateTransactionId();

			returnRDTO = validator.validateContract(certificationCollectionServiceRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateCertificationService.validateSyntaxInsertColectionService(certificationCollectionServiceRDTO);
			}
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				returnRDTO = validator.validateSendCollectionServiceDetails(certificationCollectionServiceRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH)
						.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
						.concat(pathCertification)
						.concat(pathCertificationCollectionService);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				ResponseEntity<Object> resp = restCall.executePOST(url, certificationCollectionServiceRDTO);

				processResponse(resp);
			}
		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}
		returnRDTO = getTransactionId(certificationCollectionServiceRDTO);

		return returnRDTO;
	}

	@Override
	public ReturnRDTO sendRegularizationsDetails(CertificationRegularizationRDTO certificationRegularizationRDTO)
			throws ImiException {
		try {

			logger.info(LogsConstants.LOG_START);

			certificationRegularizationRDTO.generateTransactionId();

			returnRDTO = validator.validateContract(certificationRegularizationRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateCertificationService.validateSyntaxInsertRegularization(certificationRegularizationRDTO);
			}
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				returnRDTO = validator.validateSendRegularizationsDetails(certificationRegularizationRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH)
						.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
						.concat(pathCertification)
						.concat(pathCertificationRegularization);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				ResponseEntity<Object> resp = restCall.executePOST(url, certificationRegularizationRDTO);

				processResponse(resp);
			}
		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}
		returnRDTO = getTransactionId(certificationRegularizationRDTO);

		return returnRDTO;
	}

	@Override
	public ReturnCertificationProposalsRDTO selectCertificationProposals(QueryParameterCertificationProposalsRDTO queryParameterCertificationProposalsRDTO) throws ImiException {
		ReturnCertificationProposalsRDTO returnCertificationProposalsRDTO = new ReturnCertificationProposalsRDTO();

		try {
			queryParameterCertificationProposalsRDTO.generateTransactionId();

			returnRDTO = validator.validateContract(queryParameterCertificationProposalsRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateCertificationService.validateSyntaxSelectCertification(queryParameterCertificationProposalsRDTO);
			}
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH)
						.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
						.concat(pathCertification)
						.concat(pathCertificationProposalsGet);
				
				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				Map<String, String> urlParams = new HashMap<>();
				
				urlParams.put("codi", queryParameterCertificationProposalsRDTO.getCodeContract());
				urlParams.put("idCertificacio", queryParameterCertificationProposalsRDTO.getIdCertification());
				urlParams.put("transactionId", queryParameterCertificationProposalsRDTO.getTransactionId());
				
				ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterCertificationProposalsRDTO);
				
				returnCertificationProposalsRDTO =  (ReturnCertificationProposalsRDTO) utils.rest2Object(resp, returnCertificationProposalsRDTO);
								
				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));
			} else {
				returnCertificationProposalsRDTO.setReturnRDTO(returnRDTO);
			}

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}
		
		returnCertificationProposalsRDTO.getReturnRDTO().setTransactionId(queryParameterCertificationProposalsRDTO.getTransactionId());
		
		return returnCertificationProposalsRDTO;
	}

}
