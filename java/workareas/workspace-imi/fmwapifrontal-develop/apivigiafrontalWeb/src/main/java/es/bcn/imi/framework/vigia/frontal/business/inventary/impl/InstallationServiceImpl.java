package es.bcn.imi.framework.vigia.frontal.business.inventary.impl;

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

import es.bcn.imi.framework.vigia.frontal.business.inventary.InstallationService;
import es.bcn.imi.framework.vigia.frontal.validator.ValidatorInstallation;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.ActuationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.DocumentarySupportExpenseRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.DocumentarySupportRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.InstallationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.commons.BaseTransactionIdRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnBreakdownAmortizationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnInstallationDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnInstallationExpensesRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnInstallationPeriodRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.enums.ReturnEnum;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import es.bcn.vigia.fmw.libutils.services.Utils;
import es.bcn.vigia.fmw.libutils.services.rest.RestCall;
import es.bcn.vigia.fmw.libutils.validator.front.FrontValidateInstallationService;

@Lazy
@Service(ServicesConstants.SRV_FRONTAL_INSTALLATION)
public class InstallationServiceImpl implements InstallationService {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ServicesConstants.SRV_REST_CALL)
	private RestCall restCall;

	@Autowired
	@Qualifier(ServicesConstants.SRV_UTILS)
	private Utils utils;

	@Autowired
	@Qualifier(ServicesConstants.SRV_FRONT_VALIDATE_INSTALLATION)
	private FrontValidateInstallationService frontValidateInstallationService;

	@Autowired
	@Qualifier(ServicesConstants.SRV_FRONT_VALIDATE_INSTALLATION_FMW)
	private ValidatorInstallation validator;

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

	@Value("${url.path.documentary.support.expense}")
	private String pathDocumentarySupportExpense;

	@Value("${url.path.documentary.support.expense.delete}")
	private String pathDocumentarySupportExpenseDelete;

	@Value("${url.path.documentary.support}")
	private String pathDocumentarySupport;

	@Value("${url.path.documentary.support.delete}")
	private String pathDocumentarySupportDelete;

	private ReturnRDTO returnRDTO;

	private String url;

	private void processResponse(ResponseEntity<Object> resp) throws ImiException

	{
		returnRDTO = (ReturnRDTO) utils.rest2Object(resp, returnRDTO);

		logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

	}

	private ReturnRDTO getTransactionId(BaseTransactionIdRDTO baseTransactionIdRDTO) {

		returnRDTO.setTransactionId(baseTransactionIdRDTO.getTransactionId());

		return returnRDTO;
	}

	@Override
	public ReturnRDTO insert(InstallationRDTO installationRDTO) throws ImiException {

		logger.info(LogsConstants.LOG_START);

		try {
			installationRDTO.generateTransactionId();
			
			returnRDTO = validator.validateContract(installationRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateInstallationService.validateSyntaxInsert(installationRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				returnRDTO = validator.validateInsert(installationRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH)
										.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
										.concat(pathInventary)
										.concat(pathInstallation);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				ResponseEntity<Object> resp = restCall.executePOST(url, installationRDTO);

				processResponse(resp);
			}

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}

		returnRDTO = getTransactionId(installationRDTO);

		return returnRDTO;
	}

	@Override
	public ReturnRDTO update(InstallationRDTO installationRDTO) throws ImiException {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {

			installationRDTO.generateTransactionId();

			returnRDTO = validator.validateContract(installationRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateInstallationService.validateSyntaxUpdate(installationRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				returnRDTO = validator.validateUpdate(installationRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH)
										.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
										.concat(pathInventary)
										.concat(pathInstallation)
										.concat(pathInstallationPut);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				Map<String, String> urlParams = new HashMap<String, String>();
				urlParams.put("codi", installationRDTO.getCode());

				ResponseEntity<Object> resp = restCall.executePUT(url, urlParams, installationRDTO);

				processResponse(resp);
			}

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}

		returnRDTO = getTransactionId(installationRDTO);

		return returnRDTO;
	}

	@Override
	public ReturnRDTO delete(InstallationRDTO installationRDTO) throws ImiException {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {

			installationRDTO.generateTransactionId();
			
			returnRDTO = validator.validateContract(installationRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateInstallationService.validateSyntaxDelete(installationRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				returnRDTO = validator.validateDelete(installationRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH)
										.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
										.concat(pathInventary)
										.concat(pathInstallation)
										.concat(pathInstallationDelete);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				Map<String, String> urlParams = new HashMap<String, String>();
				urlParams.put("codi", installationRDTO.getCode());

				ResponseEntity<Object> resp = restCall.executeDELETE(url, urlParams, installationRDTO);

				processResponse(resp);

			}

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}

		returnRDTO = getTransactionId(installationRDTO);

		return returnRDTO;
	}

	@Override
	public ReturnInstallationDetailedRDTO selectDetailed(QueryParameterRDTO queryParameterRDTO) throws ImiException {
		
		ReturnInstallationDetailedRDTO returnInstallationDetailedRDTO = new ReturnInstallationDetailedRDTO();

		try {
			queryParameterRDTO.generateTransactionId();

			returnRDTO = validator.validateContract(queryParameterRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateInstallationService.validateSyntaxSelectDetailed(queryParameterRDTO);
			}
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH)
										.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
										.concat(pathInventary)
										.concat(pathInstallation)
										.concat(pathInstallationGetDetailed);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				Map<String, String> urlParams = new HashMap<String, String>();
				
				urlParams.put("codi", queryParameterRDTO.getCode());
				urlParams.put("codiContracta", queryParameterRDTO.getCodeContract());
				urlParams.put("dataReferencia", queryParameterRDTO.getDateReference());
				urlParams.put("codiUsuari", queryParameterRDTO.getCodeUser());
				urlParams.put("transactionId", queryParameterRDTO.getTransactionId());
				
				ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterRDTO);
				
				returnInstallationDetailedRDTO =  (ReturnInstallationDetailedRDTO) utils.rest2Object(resp, returnInstallationDetailedRDTO);
								
				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));
			} else {
				returnInstallationDetailedRDTO.setReturnRDTO(returnRDTO);
			}

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}
		
		returnInstallationDetailedRDTO.getReturnRDTO().setTransactionId(queryParameterRDTO.getTransactionId());
		
		return returnInstallationDetailedRDTO;
	}

	@Override
	public ReturnMassiveRDTO selectMassive(QueryParameterRDTO queryParameterRDTO) throws ImiException {
		
		ReturnMassiveRDTO returnMassiveRDTO = new ReturnMassiveRDTO();

		try {
			queryParameterRDTO.generateTransactionId();
			
			returnRDTO = validator.validateContract(queryParameterRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateInstallationService.validateSyntaxSelectMassive(queryParameterRDTO);
			}			
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH)
										.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
										.concat(pathInventary)
										.concat(pathInstallation)
										.concat(pathInstallationGetMassive);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				Map<String, String> urlParams = new HashMap<String, String>();
				
				urlParams.put("codi", queryParameterRDTO.getCodeContract());
				urlParams.put("dataReferencia", queryParameterRDTO.getDateReference());
				urlParams.put("codiUsuari", queryParameterRDTO.getCodeUser());
				urlParams.put("transactionId", queryParameterRDTO.getTransactionId());
				
				ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterRDTO);
				
				returnMassiveRDTO =  (ReturnMassiveRDTO) utils.rest2Object(resp, returnMassiveRDTO);
								
				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));
			} else {
				returnMassiveRDTO.setReturnRDTO(returnRDTO);
			}

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}
		
		returnMassiveRDTO.getReturnRDTO().setTransactionId(queryParameterRDTO.getTransactionId());
		
		return returnMassiveRDTO;
	}

	@Override
	public ReturnRDTO insertExpense(InstallationRDTO installationRDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();
		try {

			installationRDTO.generateTransactionId();
			
			returnRDTO = validator.validateContract(installationRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateInstallationService.validateSyntaxInsertExpense(installationRDTO);
			}	

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				returnRDTO = validator.validateInsertExpense(installationRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH)
										.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
										.concat(pathInventary)
										.concat(pathInstallation)
										.concat(pathInstallationExpense);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				ResponseEntity<Object> resp = restCall.executePOST(url, installationRDTO);

				processResponse(resp);

			}

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_ACCESSING_POST_URL, url));
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}

		returnRDTO.setTransactionId(installationRDTO.getTransactionId());

		return returnRDTO;

	}

	@Override
	public ReturnRDTO insertAmortizationBase(InstallationRDTO installationRDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {

			installationRDTO.generateTransactionId();
			
			returnRDTO = validator.validateContract(installationRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateInstallationService.validateSyntaxInsertAmortizationBase(installationRDTO);
			}	

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				returnRDTO = validator.validateInsertAmortizationBase(installationRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH)
										.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
										.concat(pathInventary)
										.concat(pathInstallation)
										.concat(pathInstallationAmortization);
				
				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				ResponseEntity<Object> resp = restCall.executePOST(url, installationRDTO);

				processResponse(resp);
			}

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_ACCESSING_POST_URL, url));
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}

		returnRDTO.setTransactionId(installationRDTO.getTransactionId());

		return returnRDTO;
	}

	@Override
	public ReturnRDTO insertApportionment(InstallationRDTO installationRDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();
		try {

			installationRDTO.generateTransactionId();
			
			returnRDTO = validator.validateContract(installationRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateInstallationService.validateSyntaxInsertApportionment(installationRDTO);
			}	

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				returnRDTO = validator.validateInsertApportionment(installationRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH)
										.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
										.concat(pathInventary)
										.concat(pathInstallation)
										.concat(pathInstallationApportionment);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				ResponseEntity<Object> resp = restCall.executePOST(url, installationRDTO);

				processResponse(resp);
			}

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_ACCESSING_POST_URL, url));
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}

		returnRDTO.setTransactionId(installationRDTO.getTransactionId());

		return returnRDTO;

	}

	@Override
	public ReturnBreakdownAmortizationRDTO selectAmortization(QueryParameterRDTO queryParameterRDTO) throws ImiException {
		ReturnBreakdownAmortizationRDTO returnBreakdownAmortizationRDTO = new ReturnBreakdownAmortizationRDTO();

		try {
			queryParameterRDTO.generateTransactionId();
			
			returnRDTO = validator.validateContract(queryParameterRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateInstallationService.validateSyntaxSelectAmortitzacio(queryParameterRDTO);
			}	
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH)
										.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
										.concat(pathInventary)
										.concat(pathInstallation)
										.concat(pathInstallationGetAmortization);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				Map<String, String> urlParams = new HashMap<String, String>();
				
				urlParams.put("codi", queryParameterRDTO.getCode());
				urlParams.put("codiContracta", queryParameterRDTO.getCodeContract());
				urlParams.put("codiUsuari", queryParameterRDTO.getCodeUser());
				urlParams.put("transactionId", queryParameterRDTO.getTransactionId());
				
				ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterRDTO);
				
				returnBreakdownAmortizationRDTO =  (ReturnBreakdownAmortizationRDTO) utils.rest2Object(resp, returnBreakdownAmortizationRDTO);
								
				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));
			} else {
				returnBreakdownAmortizationRDTO.setReturnRDTO(returnRDTO);
			}

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}
		
		returnBreakdownAmortizationRDTO.getReturnRDTO().setTransactionId(queryParameterRDTO.getTransactionId());
		
		return returnBreakdownAmortizationRDTO;
	}

	@Override
	public ReturnInstallationExpensesRDTO selectExpenses(QueryParameterRDTO queryParameterRDTO) throws ImiException {
		ReturnInstallationExpensesRDTO returnInstallationAmortizationRDTO = new ReturnInstallationExpensesRDTO();

		try {
			queryParameterRDTO.generateTransactionId();
			
			returnRDTO = validator.validateContract(queryParameterRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateInstallationService.validateSyntaxSelectExpenses(queryParameterRDTO);
			}	
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH)
										.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
										.concat(pathInventary)
										.concat(pathInstallation)
										.concat(pathInstallationGetExpenses);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				Map<String, String> urlParams = new HashMap<String, String>();
				
				urlParams.put("codi", queryParameterRDTO.getCode());
				urlParams.put("codiContracta", queryParameterRDTO.getCodeContract());
				urlParams.put("codiUsuari", queryParameterRDTO.getCodeUser());
				urlParams.put("transactionId", queryParameterRDTO.getTransactionId());
				
				ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterRDTO);
				
				returnInstallationAmortizationRDTO =  (ReturnInstallationExpensesRDTO) utils.rest2Object(resp, returnInstallationAmortizationRDTO);
								
				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));
			} else {
				returnInstallationAmortizationRDTO.setReturnRDTO(returnRDTO);
			}

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}
		
		returnInstallationAmortizationRDTO.getReturnRDTO().setTransactionId(queryParameterRDTO.getTransactionId());
		
		return returnInstallationAmortizationRDTO;
	}

	@Override
	public ReturnInstallationPeriodRDTO selectPeriod(QueryParameterRDTO queryParameterRDTO) throws ImiException {
		
		ReturnInstallationPeriodRDTO returnInstallationPeriodRDTO = new ReturnInstallationPeriodRDTO();

		try {
			queryParameterRDTO.generateTransactionId();
			
			returnRDTO = validator.validateContract(queryParameterRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateInstallationService.validateSyntaxSelectPeriod(queryParameterRDTO);
			}	
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH)
										.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
										.concat(pathInventary)
										.concat(pathInstallation)
										.concat(pathInstallationGetPeriod);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				Map<String, String> urlParams = new HashMap<String, String>();
				
				urlParams.put("codi", queryParameterRDTO.getCode());
				urlParams.put("codiContracta", queryParameterRDTO.getCodeContract());
				urlParams.put("periode", queryParameterRDTO.getPeriod());
				urlParams.put("codiUsuari", queryParameterRDTO.getCodeUser());
				urlParams.put("transactionId", queryParameterRDTO.getTransactionId());
				
				ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterRDTO);
				
				returnInstallationPeriodRDTO =  (ReturnInstallationPeriodRDTO) utils.rest2Object(resp, returnInstallationPeriodRDTO);
								
				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));
			} else {
				returnInstallationPeriodRDTO.setReturnRDTO(returnRDTO);
			}

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}
		
		returnInstallationPeriodRDTO.getReturnRDTO().setTransactionId(queryParameterRDTO.getTransactionId());
		
		return returnInstallationPeriodRDTO;
	}

	@Override
	public ReturnRDTO insertActuation(ActuationRDTO actuationRDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {

			actuationRDTO.generateTransactionId();
			
			returnRDTO = validator.validateContract(actuationRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateInstallationService.validateSyntaxInsertActuation(actuationRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				returnRDTO = validator.validateInsertActuation(actuationRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH).concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
						.concat(pathInventary).concat(pathActuation);
				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				ResponseEntity<Object> resp = restCall.executePOST(url, actuationRDTO);

				processResponse(resp);
			}

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_ACCESSING_POST_URL, url));
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}

		returnRDTO = getTransactionId(actuationRDTO);

		return returnRDTO;

	}

	@Override
	public ReturnRDTO deleteActuation(ActuationRDTO actuationRDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {

			actuationRDTO.generateTransactionId();
			
			returnRDTO = validator.validateContract(actuationRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateInstallationService.validateSyntaxDeleteActuation(actuationRDTO);
			}	

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				returnRDTO = validator.validateDeleteActuation(actuationRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
				url = urlApiOrquestrador.concat(ImiConstants.SLASH).concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
						.concat(pathInventary)
						.concat(pathActuation)
						.concat(pathActuationDelete);

				Map<String, String> urlParams = new HashMap<>();
				urlParams.put("codi", actuationRDTO.getCode());
				urlParams.put("codiEntitat", actuationRDTO.getCodeEntity());
				
				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				ResponseEntity<Object> resp = restCall.executePUT(url, urlParams, actuationRDTO);
				
				processResponse(resp);
			}

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_ACCESSING_POST_URL, url));
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}

		returnRDTO = getTransactionId(actuationRDTO);

		return returnRDTO;

	}

	@Override
	public ReturnRDTO insertExpenseDocumentalSupport(DocumentarySupportExpenseRDTO documentarySupportExpenseRDTO)
			throws ImiException {
		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {

			documentarySupportExpenseRDTO.generateTransactionId();
			
			returnRDTO = validator.validateContract(documentarySupportExpenseRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateInstallationService.validateSyntaxInsertDocumentarySupportExpense(documentarySupportExpenseRDTO);
			}	

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				returnRDTO = validator.validateInsertDocumentarySupportExpense(documentarySupportExpenseRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH).concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
						.concat(pathInventary).concat(pathDocumentarySupportExpense);
				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				ResponseEntity<Object> resp = restCall.executePOST(url, documentarySupportExpenseRDTO);

				processResponse(resp);
			}

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_ACCESSING_POST_URL, url));
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}

		returnRDTO = getTransactionId(documentarySupportExpenseRDTO);

		return returnRDTO;

	}

	@Override
	public ReturnRDTO deleteExpenseDocumentalSupport(DocumentarySupportExpenseRDTO documentarySupportExpenseRDTO)
			throws ImiException {
		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {

			documentarySupportExpenseRDTO.generateTransactionId();
			
			returnRDTO = validator.validateContract(documentarySupportExpenseRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateInstallationService.validateSyntaxDeleteDocumentarySupportExpense(documentarySupportExpenseRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				returnRDTO = validator.validateDeleteDocumentarySupportExpense(documentarySupportExpenseRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
				url = urlApiOrquestrador.concat(ImiConstants.SLASH).concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
						.concat(pathInventary)
						.concat(pathDocumentarySupportExpense)
						.concat(pathDocumentarySupportExpenseDelete);

				Map<String, String> urlParams = new HashMap<>();
				urlParams.put("codi", documentarySupportExpenseRDTO.getCodeContractDocument());
				urlParams.put("codiEntitat", documentarySupportExpenseRDTO.getCode());
				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				ResponseEntity<Object> resp = restCall.executePUT(url, urlParams, documentarySupportExpenseRDTO);
				
				processResponse(resp);
			}

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_ACCESSING_POST_URL, url));
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}

		returnRDTO = getTransactionId(documentarySupportExpenseRDTO);

		return returnRDTO;

	}
	
	@Override
	public ReturnRDTO insertDocumentalSupport(DocumentarySupportRDTO documentarySupportRDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {

			documentarySupportRDTO.generateTransactionId();

			returnRDTO = validator.validateContract(documentarySupportRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateInstallationService.validateSyntaxInsertDocumentarySupport(documentarySupportRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				returnRDTO = validator.validateInsertDocumentarySupport(documentarySupportRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH).concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
						.concat(pathInventary).concat(pathDocumentarySupport);
				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				ResponseEntity<Object> resp = restCall.executePOST(url, documentarySupportRDTO);

				processResponse(resp);
			}

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_ACCESSING_POST_URL, url));
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}

		returnRDTO = getTransactionId(documentarySupportRDTO);

		return returnRDTO;
	}

	@Override
	public ReturnRDTO deleteDocumentalSupport(DocumentarySupportRDTO documentarySupportRDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {

			documentarySupportRDTO.generateTransactionId();

			returnRDTO = validator.validateContract(documentarySupportRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateInstallationService.validateSyntaxDeleteDocumentarySupport(documentarySupportRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				returnRDTO = validator.validateDeleteDocumentarySupport(documentarySupportRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
				url = urlApiOrquestrador.concat(ImiConstants.SLASH).concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
						.concat(pathInventary)
						.concat(pathDocumentarySupport)
						.concat(pathDocumentarySupportDelete);

				Map<String, String> urlParams = new HashMap<>();
				urlParams.put("codi", documentarySupportRDTO.getCodeContractDocument());
				urlParams.put("codiEntitat", documentarySupportRDTO.getCode());
				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				ResponseEntity<Object> resp = restCall.executePUT(url, urlParams, documentarySupportRDTO);
				
				processResponse(resp);
			}

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_ACCESSING_POST_URL, url));
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}

		returnRDTO = getTransactionId(documentarySupportRDTO);

		return returnRDTO;

	}

}