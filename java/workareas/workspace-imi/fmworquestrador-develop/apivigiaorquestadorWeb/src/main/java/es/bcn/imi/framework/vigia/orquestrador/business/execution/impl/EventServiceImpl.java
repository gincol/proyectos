package es.bcn.imi.framework.vigia.orquestrador.business.execution.impl;

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

import es.bcn.imi.framework.vigia.orquestrador.business.execution.EventService;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.APSEventRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.AnnulmentEventRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.EventServiceRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.InformativeEventRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterEventRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.UnloadingEventRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnEventApprovedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnEventDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnEventPendingRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnEventRejectedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import es.bcn.vigia.fmw.libutils.services.Utils;
import es.bcn.vigia.fmw.libutils.services.rest.RestCall;

@Lazy
@Service(ServicesConstants.SRV_EXECUTION_EVENT)
public class EventServiceImpl implements EventService {

	private Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ServicesConstants.SRV_REST_CALL)
	private RestCall restCall;

	@Autowired
	@Qualifier(ServicesConstants.SRV_UTILS)
	private Utils utils;

	@Value("${url.api.execution}")
	private String urlApiExecution;
	
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
	private String pathService;
	
	@Value("${url.path.event.informative}")
	private String pathInformative;
	
	@Value("${url.path.event.aps}")
	private String pathAps;
	
	@Value("${url.path.event.unloading}")
	private String pathUnloading;
	
	@Value("${url.path.event.annulment}")
	private String pathAnnulment;

	private ReturnRDTO returnRDTO;

	@Override
	public ReturnRDTO insertService(EventServiceRDTO eventServiceRDTO) throws ImiException {
		try {

			returnRDTO = new ReturnRDTO();

			String url = urlApiExecution + pathEvent + pathService;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			ResponseEntity<Object> resp = restCall.executePOST(url, eventServiceRDTO);

			returnRDTO = (ReturnRDTO) utils.rest2Object(resp, returnRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnRDTO;
	}

	@Override
	public ReturnRDTO insertInformative(InformativeEventRDTO informativeEventRDTO) throws ImiException {
		try {

			returnRDTO = new ReturnRDTO();

			String url = urlApiExecution + pathEvent + pathInformative;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			ResponseEntity<Object> resp = restCall.executePOST(url, informativeEventRDTO);

			returnRDTO = (ReturnRDTO) utils.rest2Object(resp, returnRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnRDTO;
	}

	@Override
	public ReturnRDTO insertAps(APSEventRDTO apsEventRDTO) throws ImiException {
		try {

			returnRDTO = new ReturnRDTO();

			String url = urlApiExecution + pathEvent + pathAps;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			ResponseEntity<Object> resp = restCall.executePOST(url, apsEventRDTO);

			returnRDTO = (ReturnRDTO) utils.rest2Object(resp, returnRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnRDTO;
	}

	@Override
	public ReturnRDTO insertUnloading(UnloadingEventRDTO unloadingEventRDTO) throws ImiException {
		try {

			returnRDTO = new ReturnRDTO();

			String url = urlApiExecution + pathEvent + pathUnloading;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			ResponseEntity<Object> resp = restCall.executePOST(url, unloadingEventRDTO);

			returnRDTO = (ReturnRDTO) utils.rest2Object(resp, returnRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnRDTO;
	}

	@Override
	public ReturnRDTO insertAnnulment(AnnulmentEventRDTO annulmentEventRDTO) throws ImiException {
		try {

			returnRDTO = new ReturnRDTO();

			String url = urlApiExecution + pathEvent + pathAnnulment;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			ResponseEntity<Object> resp = restCall.executePOST(url, annulmentEventRDTO);

			returnRDTO = (ReturnRDTO) utils.rest2Object(resp, returnRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnRDTO;
	}

	@Override
	public ReturnEventDetailedRDTO selectDetailed(QueryParameterEventRDTO queryParameterEventRDTO) throws ImiException {
		ReturnEventDetailedRDTO returnUbicationDetailedRDTO = new ReturnEventDetailedRDTO();

		try {

			String url = urlApiExecution + pathEvent + pathEventGetDetailed;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			Map<String, String> urlParams = new HashMap<String, String>();
			urlParams.put("codi", queryParameterEventRDTO.getCode());
			urlParams.put("codiContracta", queryParameterEventRDTO.getCodeContract());
			urlParams.put("codiUsuari", queryParameterEventRDTO.getCodeUser());
			urlParams.put("transactionId", queryParameterEventRDTO.getTransactionId());

			ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterEventRDTO);

			returnUbicationDetailedRDTO = (ReturnEventDetailedRDTO) utils.rest2Object(resp, returnUbicationDetailedRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnUbicationDetailedRDTO;
	}

	@Override
	public ReturnEventApprovedRDTO selectApproved(QueryParameterEventRDTO queryParameterEventRDTO) throws ImiException {
		ReturnEventApprovedRDTO returnUbicationApprovedRDTO = new ReturnEventApprovedRDTO();

		try {

			String url = urlApiExecution + pathEvent + pathEventGetApproved;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			Map<String, String> urlParams = new HashMap<String, String>();
			urlParams.put("codi", queryParameterEventRDTO.getCodeContract());
			urlParams.put("dataExecucio", queryParameterEventRDTO.getDateExecution());
			urlParams.put("codiUsuari", queryParameterEventRDTO.getCodeUser());
			urlParams.put("transactionId", queryParameterEventRDTO.getTransactionId());

			ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterEventRDTO);

			returnUbicationApprovedRDTO = (ReturnEventApprovedRDTO) utils.rest2Object(resp, returnUbicationApprovedRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnUbicationApprovedRDTO;
	}

	@Override
	public ReturnEventRejectedRDTO selectRejected(QueryParameterEventRDTO queryParameterEventRDTO) throws ImiException {
		ReturnEventRejectedRDTO returnUbicationRejectedRDTO = new ReturnEventRejectedRDTO();

		try {

			String url = urlApiExecution + pathEvent + pathEventGetRejected;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			Map<String, String> urlParams = new HashMap<String, String>();
			urlParams.put("codi", queryParameterEventRDTO.getCodeContract());
			urlParams.put("dataExecucio", queryParameterEventRDTO.getDateExecution());
			urlParams.put("codiUsuari", queryParameterEventRDTO.getCodeUser());
			urlParams.put("transactionId", queryParameterEventRDTO.getTransactionId());

			ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterEventRDTO);

			returnUbicationRejectedRDTO = (ReturnEventRejectedRDTO) utils.rest2Object(resp, returnUbicationRejectedRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnUbicationRejectedRDTO;
	}

	@Override
	public ReturnEventPendingRDTO selectPending(QueryParameterEventRDTO queryParameterEventRDTO) throws ImiException {
		ReturnEventPendingRDTO returnUbicationPendingRDTO = new ReturnEventPendingRDTO();

		try {

			String url = urlApiExecution + pathEvent + pathEventGetPending;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			Map<String, String> urlParams = new HashMap<String, String>();
			urlParams.put("codi", queryParameterEventRDTO.getCodeContract());
			urlParams.put("dataInici", queryParameterEventRDTO.getDateStart());
			urlParams.put("dataFi", queryParameterEventRDTO.getDateEnd());
			urlParams.put("codiUsuari", queryParameterEventRDTO.getCodeUser());
			urlParams.put("transactionId", queryParameterEventRDTO.getTransactionId());

			ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterEventRDTO);

			returnUbicationPendingRDTO = (ReturnEventPendingRDTO) utils.rest2Object(resp, returnUbicationPendingRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnUbicationPendingRDTO;
	}
}
