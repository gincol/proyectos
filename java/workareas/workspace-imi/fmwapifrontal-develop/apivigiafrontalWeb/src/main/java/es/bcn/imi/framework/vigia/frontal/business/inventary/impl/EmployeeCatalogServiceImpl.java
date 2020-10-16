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

import es.bcn.imi.framework.vigia.frontal.business.inventary.EmployeeCatalogService;
import es.bcn.imi.framework.vigia.frontal.validator.ValidatorEmployeeCatalog;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.EmployeeCatalogRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.commons.BaseTransactionIdRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnEmployeeCatalogDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.enums.ReturnEnum;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import es.bcn.vigia.fmw.libutils.services.Utils;
import es.bcn.vigia.fmw.libutils.services.rest.RestCall;
import es.bcn.vigia.fmw.libutils.validator.front.FrontValidateEmployeeCatalogService;


@Lazy
@Service(ServicesConstants.SRV_FRONTAL_EMPLOYEE_CATALOG)
public class EmployeeCatalogServiceImpl implements EmployeeCatalogService {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ServicesConstants.SRV_REST_CALL)
	private RestCall restCall;

	@Autowired
	@Qualifier(ServicesConstants.SRV_UTILS)
	private Utils utils;
	
	@Autowired
	@Qualifier(ServicesConstants.SRV_FRONT_VALIDATE_EMPLOYEE_CATALOG)
	private FrontValidateEmployeeCatalogService frontValidateEmployeeCatalogService;
	
	@Autowired
	@Qualifier(ServicesConstants.SRV_FRONT_VALIDATE_EMPLOYEE_CATALOG_FMW)
	private ValidatorEmployeeCatalog validator;
	
	@Value("${url.api.orquestrador}")
	private String urlApiOrquestrador;

	@Value("${url.path.inventary}")
	private String pathInventary;

	@Value("${url.path.humanresources}")
	private String pathHumanResources;

	@Value("${url.path.employee.catalog}")
	private String pathEmployeeCatalog;

	@Value("${url.path.employee.catalog.getMassive}")
	private String pathEmployeeCatalogGetMassive;

	@Value("${url.path.employee.catalog.getDetailed}")
	private String pathEmployeeCatalogGetDetailed;

	private ReturnRDTO returnRDTO;
	
	private String url;

	
	private void processResponse(ResponseEntity<Object> resp) throws ImiException

	{
		returnRDTO = (ReturnRDTO) utils.rest2Object(resp, returnRDTO);

		logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

	}

	
	private ReturnRDTO getTransactionId(BaseTransactionIdRDTO employeeCatalogRDTO) {

		returnRDTO.setTransactionId(employeeCatalogRDTO.getTransactionId());

		return returnRDTO;
	}

	@Override
	public ReturnRDTO insert(EmployeeCatalogRDTO employeeCatalogRDTO) throws ImiException {

		logger.info(LogsConstants.LOG_START);
		
		try {
			
			employeeCatalogRDTO.generateTransactionId();

			returnRDTO = validator.validateContract(employeeCatalogRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateEmployeeCatalogService.validateSyntaxInsert(employeeCatalogRDTO);
			}			
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
				 
				returnRDTO = validator.validateInsert(employeeCatalogRDTO);
			}
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
				
				url = urlApiOrquestrador
						.concat(ImiConstants.SLASH)
						.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
						.concat(pathInventary)
						.concat(pathHumanResources)
						.concat(pathEmployeeCatalog);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));
				
				ResponseEntity<Object> resp = restCall.executePOST(url, employeeCatalogRDTO);

				processResponse(resp);
			}
			

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}
		
		returnRDTO = getTransactionId(employeeCatalogRDTO);
		
		return returnRDTO;
	}

	@Override
	public ReturnMassiveRDTO selectMassive(QueryParameterRDTO queryParameterRDTO) throws ImiException {
		
		ReturnMassiveRDTO returnMassiveRDTO = new ReturnMassiveRDTO();

		try {
			queryParameterRDTO.generateTransactionId();

			returnRDTO = validator.validateContract(queryParameterRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateEmployeeCatalogService.validateSyntaxSelectMassive(queryParameterRDTO);
			}
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH)
										.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
										.concat(pathInventary)
										.concat(pathHumanResources)
										.concat(pathEmployeeCatalog)
										.concat(pathEmployeeCatalogGetMassive);

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
	public ReturnEmployeeCatalogDetailedRDTO selectDetailed(QueryParameterRDTO queryParameterRDTO) throws ImiException {
		
		ReturnEmployeeCatalogDetailedRDTO returnEmployeeCatalogDetailedRDTO = new ReturnEmployeeCatalogDetailedRDTO();

		try {
			queryParameterRDTO.generateTransactionId();

			returnRDTO = validator.validateContract(queryParameterRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateEmployeeCatalogService.validateSyntaxSelectDetailed(queryParameterRDTO);
			}
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH)
										.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
										.concat(pathInventary)
										.concat(pathHumanResources)
										.concat(pathEmployeeCatalog)
										.concat(pathEmployeeCatalogGetDetailed);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				Map<String, String> urlParams = new HashMap<String, String>();
				
				urlParams.put("codi", queryParameterRDTO.getCodeContract());
				urlParams.put("dataReferencia", queryParameterRDTO.getDateReference());
				urlParams.put("codiUsuari", queryParameterRDTO.getCodeUser());
				urlParams.put("transactionId", queryParameterRDTO.getTransactionId());
				
				ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterRDTO);
				
				returnEmployeeCatalogDetailedRDTO =  (ReturnEmployeeCatalogDetailedRDTO) utils.rest2Object(resp, returnEmployeeCatalogDetailedRDTO);
								
				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));
			} else {
				returnEmployeeCatalogDetailedRDTO.setReturnRDTO(returnRDTO);
			}

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}
		
		returnEmployeeCatalogDetailedRDTO.getReturnRDTO().setTransactionId(queryParameterRDTO.getTransactionId());
		
		return returnEmployeeCatalogDetailedRDTO;
	}

	
}