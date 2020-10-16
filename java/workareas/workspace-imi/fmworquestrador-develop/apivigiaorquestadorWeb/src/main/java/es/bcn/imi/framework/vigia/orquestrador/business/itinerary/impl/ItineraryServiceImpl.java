package es.bcn.imi.framework.vigia.orquestrador.business.itinerary.impl;

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

import es.bcn.imi.framework.vigia.orquestrador.business.itinerary.ItineraryService;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.ItineraryRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.NotificationItineraryRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnItineraryDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnItineraryMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import es.bcn.vigia.fmw.libutils.services.Utils;
import es.bcn.vigia.fmw.libutils.services.rest.RestCall;

@Lazy
@Service(ServicesConstants.SRV_ITINERARY_ITINERARY)
public class ItineraryServiceImpl implements ItineraryService {

	private Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ServicesConstants.SRV_REST_CALL)
	private RestCall restCall;

	@Autowired
	@Qualifier(ServicesConstants.SRV_UTILS)
	private Utils utils;

	@Value("${url.api.itinerary}")
	private String urlApiItinerary;
	
	@Value("${url.path.itinerary}")
	private String pathItinerary;
	
	@Value("${url.path.itinerary.getMassive}")
	private String pathItineraryGetMassive;
	
	@Value("${url.path.itinerary.getDetailed}")
	private String pathItineraryGetDetailed;

	@Value("${url.path.itinerary.notification}")
	private String pathNotificationItinerary;

	private ReturnRDTO returnRDTO;

	@Override
	public ReturnItineraryMassiveRDTO selectMassive(QueryParameterRDTO queryParameterRDTO) throws ImiException {
		ReturnItineraryMassiveRDTO returnUbicationMassiveRDTO = new ReturnItineraryMassiveRDTO();

		try {

			String url = urlApiItinerary + pathItinerary + pathItineraryGetMassive;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			Map<String, String> urlParams = new HashMap<String, String>();
			urlParams.put("codi", queryParameterRDTO.getCodeContract());
			urlParams.put("dataReferencia", queryParameterRDTO.getDateReference());
			urlParams.put("codiUsuari", queryParameterRDTO.getCodeUser());
			urlParams.put("transactionId", queryParameterRDTO.getTransactionId());

			ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterRDTO);

			returnUbicationMassiveRDTO = (ReturnItineraryMassiveRDTO) utils.rest2Object(resp, returnUbicationMassiveRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnUbicationMassiveRDTO;
	}

	@Override
	public ReturnItineraryDetailedRDTO selectDetailed(QueryParameterRDTO queryParameterRDTO) throws ImiException {
		ReturnItineraryDetailedRDTO returnUbicationDetailedRDTO = new ReturnItineraryDetailedRDTO();

		try {

			String url = urlApiItinerary + pathItinerary + pathItineraryGetDetailed;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			Map<String, String> urlParams = new HashMap<String, String>();
			urlParams.put("codi", queryParameterRDTO.getCode());
			urlParams.put("codiContracta", queryParameterRDTO.getCodeContract());
			urlParams.put("dataReferencia", queryParameterRDTO.getDateReference());
			urlParams.put("codiUsuari", queryParameterRDTO.getCodeUser());
			urlParams.put("transactionId", queryParameterRDTO.getTransactionId());

			ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterRDTO);

			returnUbicationDetailedRDTO = (ReturnItineraryDetailedRDTO) utils.rest2Object(resp, returnUbicationDetailedRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnUbicationDetailedRDTO;
	}
	
	@Override
	public ReturnRDTO insert(ItineraryRDTO itineraryRDTO) throws ImiException {
		try {

			returnRDTO = new ReturnRDTO();

			String url = urlApiItinerary + pathItinerary;
			
			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			ResponseEntity<Object> resp = restCall.executePOST(url, itineraryRDTO);

			returnRDTO = (ReturnRDTO) utils.rest2Object(resp, returnRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnRDTO;
	}

	@Override
	public ReturnRDTO notify(NotificationItineraryRDTO notificationItineraryRDTO) throws ImiException {
		try {

			returnRDTO = new ReturnRDTO();

			String url = urlApiItinerary + pathItinerary + pathNotificationItinerary;
			
			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			Map<String, String> urlParams = new HashMap<String, String>();
			
			urlParams.put("codi", notificationItineraryRDTO.getCodeItinerary());
			
			ResponseEntity<Object> resp = restCall.executePOST(url, urlParams, notificationItineraryRDTO);


			returnRDTO = (ReturnRDTO) utils.rest2Object(resp, returnRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnRDTO;

	}


}
