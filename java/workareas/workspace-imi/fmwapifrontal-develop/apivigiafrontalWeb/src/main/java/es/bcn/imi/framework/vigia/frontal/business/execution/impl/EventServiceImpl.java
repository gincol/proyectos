package es.bcn.imi.framework.vigia.frontal.business.execution.impl;

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

import es.bcn.imi.framework.vigia.frontal.business.execution.EventService;
import es.bcn.imi.framework.vigia.frontal.validator.ValidatorAPSEvent;
import es.bcn.imi.framework.vigia.frontal.validator.ValidatorAnnulmentEvent;
import es.bcn.imi.framework.vigia.frontal.validator.ValidatorInformativeEvent;
import es.bcn.imi.framework.vigia.frontal.validator.ValidatorServiceEvent;
import es.bcn.imi.framework.vigia.frontal.validator.ValidatorUnloadingEvent;
import es.bcn.imi.framework.vigia.frontal.validator.ValidatorUtils;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.APSEventRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.AnnulmentEventRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.EventServiceRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.InformativeEventRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterEventRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.UnloadingEventRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.commons.BaseTransactionIdRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnEventApprovedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnEventDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnEventPendingRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnEventRejectedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.enums.ReturnEnum;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import es.bcn.vigia.fmw.libutils.services.Utils;
import es.bcn.vigia.fmw.libutils.services.rest.RestCall;
import es.bcn.vigia.fmw.libutils.validator.front.FrontValidateAPSEventService;
import es.bcn.vigia.fmw.libutils.validator.front.FrontValidateAnnulmentEventService;
import es.bcn.vigia.fmw.libutils.validator.front.FrontValidateInformativeEventService;
import es.bcn.vigia.fmw.libutils.validator.front.FrontValidateServiceEventService;
import es.bcn.vigia.fmw.libutils.validator.front.FrontValidateUnloadingEventService;

@Lazy
@Service(ServicesConstants.SRV_FRONTAL_EVENT)
public class EventServiceImpl implements EventService {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ServicesConstants.SRV_REST_CALL)
	private RestCall restCall;

	@Autowired
	@Qualifier(ServicesConstants.SRV_UTILS)
	private Utils utils;
	
	@Autowired
	@Qualifier(ServicesConstants.SRV_FRONT_VALIDATE_SERVICE_EVENT)
	private FrontValidateServiceEventService frontValidateServiceEventService;
	
	@Autowired
	@Qualifier(ServicesConstants.SRV_FRONT_VALIDATE_SERVICE_EVENT_FMW)
	private ValidatorServiceEvent validatorServiceEvent;

	@Autowired
	@Qualifier(ServicesConstants.SRV_FRONT_VALIDATE_ANNULMENT_EVENT)
	private FrontValidateAnnulmentEventService frontValidateCancellationEventService;
	
	@Autowired
	@Qualifier(ServicesConstants.SRV_FRONT_VALIDATE_ANNULMENT_EVENT_FMW)
	private ValidatorAnnulmentEvent validatorAnnulmentEvent;

	@Autowired
	@Qualifier(ServicesConstants.SRV_FRONT_VALIDATE_APS_EVENT)
	private FrontValidateAPSEventService frontValidateAPSEventService;
	
	@Autowired
	@Qualifier(ServicesConstants.SRV_FRONT_VALIDATE_APS_EVENT_FMW)
	private ValidatorAPSEvent validatorAPSEvent;
	
	@Autowired
	@Qualifier(ServicesConstants.SRV_FRONT_VALIDATE_INFORMATIVE_EVENT)
	private FrontValidateInformativeEventService frontValidateInformativeEventService;
	
	@Autowired
	@Qualifier(ServicesConstants.SRV_FRONT_VALIDATE_INFORMATIVE_EVENT_FMW)
	private ValidatorInformativeEvent validatorInformativeEvent;

	@Autowired
	@Qualifier(ServicesConstants.SRV_FRONT_VALIDATE_UNLOADING_EVENT)
	private FrontValidateUnloadingEventService frontValidateUnloadingEventService;
	
	@Autowired
	@Qualifier(ServicesConstants.SRV_FRONT_VALIDATE_UNLOADING_EVENT_FMW)
	private ValidatorUnloadingEvent validatorUnloadingEvent;

	@Autowired
	@Qualifier(ServicesConstants.SRV_FRONT_VALIDATE_FMW)
	private ValidatorUtils validator;
	
	@Value("${url.api.orquestrador}")
	private String urlApiOrquestrador;

	@Value("${url.path.execution}")
	private String pathExecution;

	@Value("${url.path.event}")
	private String pathEvent;

	@Value("${url.path.event.getDetailed}")
	private String pathEventGetDetailed;

	@Value("${url.path.event.getApproved}")
	private String pathEventGetApproved;

	@Value("${url.path.event.getRejected}")
	private String pathEventGetRejected;

	@Value("${url.path.event.getPending}")
	private String pathEventGetPending;

	@Value("${url.path.event.service}")
	private String pathServiceEvent;

	@Value("${url.path.event.cancellation}")
	private String pathCancellationEvent;

	@Value("${url.path.event.aps}")
	private String pathAPSEvent;

	@Value("${url.path.event.informative}")
	private String pathInformativeEvent;

	@Value("${url.path.event.unloading}")
	private String pathUnloadingEvent;

	private ReturnRDTO returnRDTO;
	
	private String url;

	
	private void processResponse(ResponseEntity<Object> resp) throws ImiException

	{
		returnRDTO = (ReturnRDTO) utils.rest2Object(resp, returnRDTO);

		logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

	}

	private ReturnRDTO getTransactionId(BaseTransactionIdRDTO  eventRDTO) {

		returnRDTO.setTransactionId(eventRDTO.getTransactionId());

		return returnRDTO;
	}

	@Override
	public ReturnRDTO insertAnnulment(AnnulmentEventRDTO eventRDTO) throws ImiException {

		logger.info(LogsConstants.LOG_START);
		
		try {
			
			eventRDTO.generateTransactionId();

			returnRDTO = validator.validateContract(eventRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateCancellationEventService.validateSyntaxInsert(eventRDTO);
			}
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
				 
				returnRDTO = validatorAnnulmentEvent.validateInsert(eventRDTO);
			}
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
				
				url = urlApiOrquestrador
						.concat(ImiConstants.SLASH)
						.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
						.concat(pathExecution)
						.concat(pathCancellationEvent);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));
				
				ResponseEntity<Object> resp = restCall.executePOST(url, eventRDTO);

				processResponse(resp);
			}
			

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}
		
		returnRDTO = getTransactionId(eventRDTO);
		
		return returnRDTO;
	}

	
	@Override
	public ReturnRDTO insertAPS(APSEventRDTO eventRDTO) throws ImiException {

		logger.info(LogsConstants.LOG_START);
		
		try {
			
			eventRDTO.generateTransactionId();

			returnRDTO = validator.validateContract(eventRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateAPSEventService.validateSyntaxInsert(eventRDTO);
			}
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
				 
				returnRDTO = validatorAPSEvent.validateInsert(eventRDTO);
			}
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
				
				url = urlApiOrquestrador
						.concat(ImiConstants.SLASH)
						.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
						.concat(pathExecution)
						.concat(pathAPSEvent);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));
				
				ResponseEntity<Object> resp = restCall.executePOST(url, eventRDTO);

				processResponse(resp);
			}
			

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}
		
		returnRDTO = getTransactionId(eventRDTO);
		
		return returnRDTO;
	}

	
	@Override
	public ReturnRDTO insertInformative(InformativeEventRDTO eventRDTO) throws ImiException {

		logger.info(LogsConstants.LOG_START);
		
		try {
			
			eventRDTO.generateTransactionId();

			returnRDTO = validator.validateContract(eventRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateInformativeEventService.validateSyntaxInsert(eventRDTO);
			}
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
				 
				returnRDTO = validatorInformativeEvent.validateInsert(eventRDTO);
			}
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
				
				url = urlApiOrquestrador
						.concat(ImiConstants.SLASH)
						.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
						.concat(pathExecution)
						.concat(pathInformativeEvent);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));
				
				ResponseEntity<Object> resp = restCall.executePOST(url, eventRDTO);

				processResponse(resp);
			}
			

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}
		
		returnRDTO = getTransactionId(eventRDTO);
		
		return returnRDTO;
	}

	@Override
	public ReturnRDTO insertService(EventServiceRDTO eventRDTO) throws ImiException {

		logger.info(LogsConstants.LOG_START);
		
		try {
			
			eventRDTO.generateTransactionId();

			returnRDTO = validator.validateContract(eventRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateServiceEventService.validateSyntaxInsert(eventRDTO);
			}
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
				 
				returnRDTO = validatorServiceEvent.validateInsert(eventRDTO);
			}
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
				
				url = urlApiOrquestrador
						.concat(ImiConstants.SLASH)
						.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
						.concat(pathExecution)
						.concat(pathServiceEvent);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));
				
				ResponseEntity<Object> resp = restCall.executePOST(url, eventRDTO);

				processResponse(resp);
			}
			

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}
		
		returnRDTO = getTransactionId(eventRDTO);
		
		return returnRDTO;
	}

	@Override
	public ReturnRDTO insertUnloading(UnloadingEventRDTO eventRDTO) throws ImiException {

		logger.info(LogsConstants.LOG_START);
		
		try {
			
			eventRDTO.generateTransactionId();

			returnRDTO = validator.validateContract(eventRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateUnloadingEventService.validateSyntaxInsert(eventRDTO);
			}
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
				 
				returnRDTO = validatorUnloadingEvent.validateInsert(eventRDTO);
			}
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
				
				url = urlApiOrquestrador
						.concat(ImiConstants.SLASH)
						.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
						.concat(pathExecution)
						.concat(pathUnloadingEvent);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));
				
				ResponseEntity<Object> resp = restCall.executePOST(url, eventRDTO);

				processResponse(resp);
			}
			

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}
		
		returnRDTO = getTransactionId(eventRDTO);
		
		return returnRDTO;
	}

	@Override
	public ReturnEventDetailedRDTO selectDetailed(QueryParameterEventRDTO queryParameterEventRDTO) throws ImiException {
		ReturnEventDetailedRDTO returnEventDetailedRDTO = new ReturnEventDetailedRDTO();

		try {
			queryParameterEventRDTO.generateTransactionId();

			returnRDTO = validator.validateContract(queryParameterEventRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateServiceEventService.validateSyntaxSelectDetailed(queryParameterEventRDTO);
			}
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH)
										.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
										.concat(pathExecution)
										.concat(pathEvent)
										.concat(pathEventGetDetailed);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				Map<String, String> urlParams = new HashMap<String, String>();
				
				urlParams.put("codi", queryParameterEventRDTO.getCode());
				urlParams.put("codiContracta", queryParameterEventRDTO.getCodeContract());
				urlParams.put("codiUsuari", queryParameterEventRDTO.getCodeUser());
				urlParams.put("transactionId", queryParameterEventRDTO.getTransactionId());
				
				ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterEventRDTO);
				
				returnEventDetailedRDTO =  (ReturnEventDetailedRDTO) utils.rest2Object(resp, returnEventDetailedRDTO);
								
				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));
			} else {
				returnEventDetailedRDTO.setReturnRDTO(returnRDTO);
			}

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}
		
		returnEventDetailedRDTO.getReturnRDTO().setTransactionId(queryParameterEventRDTO.getTransactionId());
		
		return returnEventDetailedRDTO;
	}

	@Override
	public ReturnEventApprovedRDTO selectApproved(QueryParameterEventRDTO queryParameterEventRDTO) throws ImiException {
		ReturnEventApprovedRDTO returnEventApprovedRDTO = new ReturnEventApprovedRDTO();

		try {
			queryParameterEventRDTO.generateTransactionId();

			returnRDTO = validator.validateContract(queryParameterEventRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateServiceEventService.validateSyntaxSelectApproved(queryParameterEventRDTO);
			}
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH)
										.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
										.concat(pathExecution)
										.concat(pathEvent)
										.concat(pathEventGetApproved);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				Map<String, String> urlParams = new HashMap<String, String>();
				
				urlParams.put("codi", queryParameterEventRDTO.getCodeContract());
				urlParams.put("dataExecucio", queryParameterEventRDTO.getDateExecution());
				urlParams.put("codiUsuari", queryParameterEventRDTO.getCodeUser());
				urlParams.put("transactionId", queryParameterEventRDTO.getTransactionId());
				
				ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterEventRDTO);
				
				returnEventApprovedRDTO =  (ReturnEventApprovedRDTO) utils.rest2Object(resp, returnEventApprovedRDTO);
								
				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));
			} else {
				returnEventApprovedRDTO.setReturnRDTO(returnRDTO);
			}

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}
		
		returnEventApprovedRDTO.getReturnRDTO().setTransactionId(queryParameterEventRDTO.getTransactionId());
		
		return returnEventApprovedRDTO;
	}

	@Override
	public ReturnEventRejectedRDTO selectRejected(QueryParameterEventRDTO queryParameterEventRDTO) throws ImiException {
		ReturnEventRejectedRDTO returnEventRejectedRDTO = new ReturnEventRejectedRDTO();

		try {
			queryParameterEventRDTO.generateTransactionId();

			returnRDTO = validator.validateContract(queryParameterEventRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateServiceEventService.validateSyntaxSelectRejected(queryParameterEventRDTO);
			}
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH)
										.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
										.concat(pathExecution)
										.concat(pathEvent)
										.concat(pathEventGetRejected);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				Map<String, String> urlParams = new HashMap<String, String>();
				
				urlParams.put("codi", queryParameterEventRDTO.getCodeContract());
				urlParams.put("dataExecucio", queryParameterEventRDTO.getDateExecution());
				urlParams.put("codiUsuari", queryParameterEventRDTO.getCodeUser());
				urlParams.put("transactionId", queryParameterEventRDTO.getTransactionId());
				
				ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterEventRDTO);
				
				returnEventRejectedRDTO =  (ReturnEventRejectedRDTO) utils.rest2Object(resp, returnEventRejectedRDTO);
								
				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));
			} else {
				returnEventRejectedRDTO.setReturnRDTO(returnRDTO);
			}

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}
		
		returnEventRejectedRDTO.getReturnRDTO().setTransactionId(queryParameterEventRDTO.getTransactionId());
		
		return returnEventRejectedRDTO;
	}

	@Override
	public ReturnEventPendingRDTO selectPending(QueryParameterEventRDTO queryParameterEventRDTO) throws ImiException {
		ReturnEventPendingRDTO returnEventPendingRDTO = new ReturnEventPendingRDTO();

		try {
			queryParameterEventRDTO.generateTransactionId();

			returnRDTO = validator.validateContract(queryParameterEventRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateServiceEventService.validateSyntaxSelectPending(queryParameterEventRDTO);
			}
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH)
										.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
										.concat(pathExecution)
										.concat(pathEvent)
										.concat(pathEventGetPending);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				Map<String, String> urlParams = new HashMap<String, String>();
				
				urlParams.put("codi", queryParameterEventRDTO.getCodeContract());
				urlParams.put("dataInici", queryParameterEventRDTO.getDateStart());
				urlParams.put("dataFi", queryParameterEventRDTO.getDateEnd());
				urlParams.put("codiUsuari", queryParameterEventRDTO.getCodeUser());
				urlParams.put("transactionId", queryParameterEventRDTO.getTransactionId());
				
				ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterEventRDTO);
				
				returnEventPendingRDTO =  (ReturnEventPendingRDTO) utils.rest2Object(resp, returnEventPendingRDTO);
								
				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));
			} else {
				returnEventPendingRDTO.setReturnRDTO(returnRDTO);
			}

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}
		
		returnEventPendingRDTO.getReturnRDTO().setTransactionId(queryParameterEventRDTO.getTransactionId());
		
		return returnEventPendingRDTO;
	}



}