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

import es.bcn.imi.framework.vigia.frontal.business.inventary.CommerceService;
import es.bcn.imi.framework.vigia.frontal.validator.ValidatorCommerce;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CommerceRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.commons.BaseTransactionIdRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnCommerceDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnCommerceElementsRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.enums.ReturnEnum;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import es.bcn.vigia.fmw.libutils.services.Utils;
import es.bcn.vigia.fmw.libutils.services.rest.RestCall;
import es.bcn.vigia.fmw.libutils.validator.front.FrontValidateCommerceService;

@Lazy
@Service(ServicesConstants.SRV_FRONTAL_COMMERCE)
public class CommerceServiceImpl implements CommerceService {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ServicesConstants.SRV_REST_CALL)
	private RestCall restCall;

	@Autowired
	@Qualifier(ServicesConstants.SRV_UTILS)
	private Utils utils;
	
	@Autowired
	@Qualifier(ServicesConstants.SRV_FRONT_VALIDATE_COMMERCE)
	private FrontValidateCommerceService frontValidateCommerceService;
	
	@Autowired
	@Qualifier(ServicesConstants.SRV_FRONT_VALIDATE_COMMERCE_FMW)
	private ValidatorCommerce validator;
	
	@Value("${url.api.orquestrador}")
	private String urlApiOrquestrador;

	@Value("${url.path.inventary}")
	private String pathInventary;

	@Value("${url.path.commerce}")
	private String pathCommerce;

	@Value("${url.path.commerce.getMassive}")
	private String pathCommerceGetMassive;

	@Value("${url.path.commerce.getDetailed}")
	private String pathCommerceGetDetailed;

	@Value("${url.path.commerce.getElements}")
	private String pathCommerceGetElements;

	@Value("${url.path.commerce.put}")
	private String pathCommercePut;

	@Value("${url.path.commerce.delete}")
	private String pathCommerceDelete;
	
	@Value("${url.path.commerce.elements}")
	private String pathCommerceElements;

	private ReturnRDTO returnRDTO;
	
	private String url;

	
	private void processResponse(ResponseEntity<Object> resp) throws ImiException

	{
		returnRDTO = (ReturnRDTO) utils.rest2Object(resp, returnRDTO);

		logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

	}

	
	private ReturnRDTO getTransactionId(BaseTransactionIdRDTO commerceRDTO) {

		returnRDTO.setTransactionId(commerceRDTO.getTransactionId());

		return returnRDTO;
	}

	@Override
	public ReturnRDTO insert(CommerceRDTO commerceRDTO) throws ImiException {

		logger.info(LogsConstants.LOG_START);
		
		try {
			
			commerceRDTO.generateTransactionId();

			returnRDTO = validator.validateContract(commerceRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateCommerceService.validateSyntaxInsert(commerceRDTO);
			}
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
				 
				returnRDTO = validator.validateInsert(commerceRDTO);
			}
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
				
				url = urlApiOrquestrador
						.concat(ImiConstants.SLASH)
						.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
						.concat(pathInventary)
						.concat(pathCommerce);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));
				
				ResponseEntity<Object> resp = restCall.executePOST(url, commerceRDTO);

				processResponse(resp);
			}
			

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}
		
		returnRDTO = getTransactionId(commerceRDTO);
		
		return returnRDTO;
	}

	@Override
	public ReturnRDTO update(CommerceRDTO commerceRDTO) throws ImiException {
		
		try {
			
			commerceRDTO.generateTransactionId();

			returnRDTO = validator.validateContract(commerceRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateCommerceService.validateSyntaxUpdate(commerceRDTO);
			}
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
				
				returnRDTO = validator.validateUpdate(commerceRDTO);
			}
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription()) && !commerceRDTO.getLstElementsRDTO().isEmpty()) {
				 
				returnRDTO = validator.validateInsertElements(commerceRDTO);
			}
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
			
				url = urlApiOrquestrador
						.concat(ImiConstants.SLASH)
						.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
						.concat(pathInventary)
						.concat(pathCommerce)
						.concat(pathCommercePut);
				
				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));
				
				Map<String, String> urlParams = new HashMap<>();
				urlParams.put("codi", commerceRDTO.getCode());
				
				
				ResponseEntity<Object> resp = restCall.executePUT(url, urlParams, commerceRDTO);
				
				processResponse(resp);
			}
			
		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}
		
		returnRDTO = getTransactionId(commerceRDTO);

		return returnRDTO;
	}

	@Override
	public ReturnRDTO delete(CommerceRDTO commerceRDTO) throws ImiException {
		
		try {
			
			commerceRDTO.generateTransactionId();

			returnRDTO = validator.validateContract(commerceRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateCommerceService.validateSyntaxDelete(commerceRDTO);
			}
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
					
				returnRDTO = validator.validateDelete(commerceRDTO);
			}
			
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
			
				url = urlApiOrquestrador
						.concat(ImiConstants.SLASH)
						.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
						.concat(pathInventary)
						.concat(pathCommerce)
						.concat(pathCommerceDelete);
				
				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));
				
				Map<String, String> urlParams = new HashMap<>();
				urlParams.put("codi", commerceRDTO.getCode());
				
				
				ResponseEntity<Object> resp = restCall.executeDELETE(url, urlParams, commerceRDTO);
				
				processResponse(resp);
			}
			
			
			
		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}
		
		returnRDTO = getTransactionId(commerceRDTO);

		return returnRDTO;
	}

	@Override
	public ReturnRDTO insertElements(CommerceRDTO commerceRDTO) throws ImiException {
	
		logger.info(LogsConstants.LOG_START);
		
		try {

			commerceRDTO.generateTransactionId();

			returnRDTO = validator.validateContract(commerceRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateCommerceService.validateSyntaxInsertElements(commerceRDTO);
			}
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription()) && !commerceRDTO.getLstElementsRDTO().isEmpty()) {
				 
				returnRDTO = validator.validateInsertElements(commerceRDTO);
			}
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
				
				url = urlApiOrquestrador
						.concat(ImiConstants.SLASH)
						.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
						.concat(pathInventary)
						.concat(pathCommerce)
						.concat(pathCommerceElements);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));
				
				ResponseEntity<Object> resp = restCall.executePOST(url, commerceRDTO);

				processResponse(resp);
			}
			

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}
		
		returnRDTO = getTransactionId(commerceRDTO);

		return returnRDTO;
	}

	@Override
	public ReturnMassiveRDTO selectMassive(QueryParameterRDTO queryParameterRDTO) throws ImiException {
		
		ReturnMassiveRDTO returnMassiveRDTO = new ReturnMassiveRDTO();

		try {
			queryParameterRDTO.generateTransactionId();

			returnRDTO = validator.validateContract(queryParameterRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateCommerceService.validateSyntaxSelectMassive(queryParameterRDTO);
			}
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH)
										.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
										.concat(pathInventary)
										.concat(pathCommerce)
										.concat(pathCommerceGetMassive);

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
	public ReturnCommerceDetailedRDTO selectDetailed(QueryParameterRDTO queryParameterRDTO) throws ImiException {
		ReturnCommerceDetailedRDTO returnCommerceDetailedRDTO = new ReturnCommerceDetailedRDTO();

		try {
			queryParameterRDTO.generateTransactionId();

			returnRDTO = validator.validateContract(queryParameterRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateCommerceService.validateSyntaxSelectDetailed(queryParameterRDTO);
			}
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH)
										.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
										.concat(pathInventary)
										.concat(pathCommerce)
										.concat(pathCommerceGetDetailed);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				Map<String, String> urlParams = new HashMap<String, String>();
				
				urlParams.put("codi", queryParameterRDTO.getCode());
				urlParams.put("codiContracta", queryParameterRDTO.getCodeContract());
				urlParams.put("dataReferencia", queryParameterRDTO.getDateReference());
				urlParams.put("codiUsuari", queryParameterRDTO.getCodeUser());
				urlParams.put("transactionId", queryParameterRDTO.getTransactionId());
				
				ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterRDTO);
				
				returnCommerceDetailedRDTO =  (ReturnCommerceDetailedRDTO) utils.rest2Object(resp, returnCommerceDetailedRDTO);
								
				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));
			} else {
				returnCommerceDetailedRDTO.setReturnRDTO(returnRDTO);
			}

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}
		
		returnCommerceDetailedRDTO.getReturnRDTO().setTransactionId(queryParameterRDTO.getTransactionId());
		
		return returnCommerceDetailedRDTO;
	}

	@Override
	public ReturnCommerceElementsRDTO selectElements(QueryParameterRDTO queryParameterRDTO) throws ImiException {
		ReturnCommerceElementsRDTO returnCommerceElementsRDTO = new ReturnCommerceElementsRDTO();

		try {
			queryParameterRDTO.generateTransactionId();

			returnRDTO = validator.validateContract(queryParameterRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateCommerceService.validateSyntaxSelectElements(queryParameterRDTO);
			}
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH)
										.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
										.concat(pathInventary)
										.concat(pathCommerce)
										.concat(pathCommerceGetElements);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				Map<String, String> urlParams = new HashMap<String, String>();

				urlParams.put("codi", queryParameterRDTO.getCode());
				urlParams.put("codiContracta", queryParameterRDTO.getCodeContract());
				urlParams.put("dataReferencia", queryParameterRDTO.getDateReference());
				urlParams.put("codiUsuari", queryParameterRDTO.getCodeUser());
				urlParams.put("transactionId", queryParameterRDTO.getTransactionId());
				
				ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterRDTO);
				
				returnCommerceElementsRDTO =  (ReturnCommerceElementsRDTO) utils.rest2Object(resp, returnCommerceElementsRDTO);
								
				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));
			} else {
				returnCommerceElementsRDTO.setReturnRDTO(returnRDTO);
			}

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}
		
		returnCommerceElementsRDTO.getReturnRDTO().setTransactionId(queryParameterRDTO.getTransactionId());
		
		return returnCommerceElementsRDTO;
	}
}