package es.bcn.imi.framework.vigia.frontal.business.itinerary.impl;

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

import es.bcn.imi.framework.vigia.frontal.business.itinerary.PlanningService;
import es.bcn.imi.framework.vigia.frontal.validator.ValidatorPlanning;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.PlanningRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterPlanningRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.commons.BaseTransactionIdRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnPlanningDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnPlanningMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.enums.ReturnEnum;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import es.bcn.vigia.fmw.libutils.services.Utils;
import es.bcn.vigia.fmw.libutils.services.rest.RestCall;
import es.bcn.vigia.fmw.libutils.validator.front.FrontValidatePlanningService;

@Lazy
@Service(ServicesConstants.SRV_FRONTAL_PLANNING)
public class PlanningServiceImpl implements PlanningService {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ServicesConstants.SRV_REST_CALL)
	private RestCall restCall;

	@Autowired
	@Qualifier(ServicesConstants.SRV_UTILS)
	private Utils utils;
	
	@Autowired
	@Qualifier(ServicesConstants.SRV_FRONT_VALIDATE_SERVICE_PLANNING)
	private FrontValidatePlanningService frontValidatePlanningService;
	
	@Autowired
	@Qualifier(ServicesConstants.SRV_FRONT_VALIDATE_PLANNING_FMW)
	private ValidatorPlanning validator;

	@Value("${url.api.orquestrador}")
	private String urlApiOrquestrador;

	@Value("${url.path.itinerary}")
	private String pathItinerary;

	@Value("${url.path.planning}")
	private String pathPlanning;

	@Value("${url.path.planning.getMassive}")
	private String pathPlanningGetMassive;

	@Value("${url.path.planning.getDetailed}")
	private String pathPlanningGetDetailed;

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
	public ReturnPlanningMassiveRDTO selectMassive(QueryParameterPlanningRDTO queryParameterPlanningRDTO) throws ImiException {
		ReturnPlanningMassiveRDTO returnPlanningMassiveRDTO = new ReturnPlanningMassiveRDTO();

		try {
			queryParameterPlanningRDTO.generateTransactionId();

			returnRDTO = validator.validateContract(queryParameterPlanningRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidatePlanningService.validateSyntaxSelectMassive(queryParameterPlanningRDTO);
			}
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH)
										.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
										.concat(pathItinerary)
										.concat(pathPlanning)
										.concat(pathPlanningGetMassive);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				Map<String, String> urlParams = new HashMap<String, String>();

				urlParams.put("codi", queryParameterPlanningRDTO.getCodeContract());
				urlParams.put("any", queryParameterPlanningRDTO.getYear());				
				urlParams.put("codiUsuari", queryParameterPlanningRDTO.getCodeUser());
				urlParams.put("transactionId", queryParameterPlanningRDTO.getTransactionId());
				
				ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterPlanningRDTO);
				
				returnPlanningMassiveRDTO =  (ReturnPlanningMassiveRDTO) utils.rest2Object(resp, returnPlanningMassiveRDTO);
								
				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));
			} else {
				returnPlanningMassiveRDTO.setReturnRDTO(returnRDTO);
			}

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}
		
		returnPlanningMassiveRDTO.getReturnRDTO().setTransactionId(queryParameterPlanningRDTO.getTransactionId());
		
		return returnPlanningMassiveRDTO;
	}

	@Override
	public ReturnPlanningDetailedRDTO selectDetailed(QueryParameterPlanningRDTO queryParameterPlanningRDTO) throws ImiException {
		ReturnPlanningDetailedRDTO returnPlanningDetailedRDTO = new ReturnPlanningDetailedRDTO();

		try {
			queryParameterPlanningRDTO.generateTransactionId();

			returnRDTO = validator.validateContract(queryParameterPlanningRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidatePlanningService.validateSyntaxSelectDetailed(queryParameterPlanningRDTO);
			}
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH)
										.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
										.concat(pathItinerary)
										.concat(pathPlanning)
										.concat(pathPlanningGetDetailed);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				Map<String, String> urlParams = new HashMap<String, String>();
	
				urlParams.put("codiContracta", queryParameterPlanningRDTO.getCodeContract());
				urlParams.put("codi", queryParameterPlanningRDTO.getCode());				
				urlParams.put("any", queryParameterPlanningRDTO.getYear());				
				urlParams.put("dataReferencia", queryParameterPlanningRDTO.getDateReference());				
				urlParams.put("codiUsuari", queryParameterPlanningRDTO.getCodeUser());
				urlParams.put("transactionId", queryParameterPlanningRDTO.getTransactionId());
				
				ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterPlanningRDTO);
				
				returnPlanningDetailedRDTO =  (ReturnPlanningDetailedRDTO) utils.rest2Object(resp, returnPlanningDetailedRDTO);
								
				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));
			} else {
				returnPlanningDetailedRDTO.setReturnRDTO(returnRDTO);
			}

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}
		
		returnPlanningDetailedRDTO.getReturnRDTO().setTransactionId(queryParameterPlanningRDTO.getTransactionId());
		
		return returnPlanningDetailedRDTO;
	}

	@Override
	public ReturnRDTO insert(PlanningRDTO planningRDTO) throws ImiException {

		logger.info(LogsConstants.LOG_START);

		try {
			planningRDTO.generateTransactionId();

			returnRDTO = validator.validateContract(planningRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidatePlanningService.validateSyntaxInsert(planningRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				returnRDTO = validator.validateInsert(planningRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH)
										.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
										.concat(pathItinerary)
										.concat(pathPlanning);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				ResponseEntity<Object> resp = restCall.executePOST(url, planningRDTO);

				processResponse(resp);
			}

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}

		returnRDTO = getTransactionId(planningRDTO);

		return returnRDTO;
	}


}