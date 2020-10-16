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

import es.bcn.imi.framework.vigia.orquestrador.business.itinerary.PlanningService;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.PlanningRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterPlanningRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnPlanningDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnPlanningMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import es.bcn.vigia.fmw.libutils.services.Utils;
import es.bcn.vigia.fmw.libutils.services.rest.RestCall;

@Lazy
@Service(ServicesConstants.SRV_ITINERARY_PLANNING)
public class PlanningServiceImpl implements PlanningService {

	private Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ServicesConstants.SRV_REST_CALL)
	private RestCall restCall;

	@Autowired
	@Qualifier(ServicesConstants.SRV_UTILS)
	private Utils utils;

	@Value("${url.api.itinerary}")
	private String urlApiItinerary;
	
	@Value("${url.path.planning}")
	private String pathPlanning;
	
	@Value("${url.path.planning.getMassive}")
	private String pathPlanningGetMassive;
	
	@Value("${url.path.planning.getDetailed}")
	private String pathPlanningGetDetailed;

	private ReturnRDTO returnRDTO;
	
	@Override
	public ReturnRDTO insert(PlanningRDTO planningRDTO) throws ImiException {
		try {

			returnRDTO = new ReturnRDTO();

			String url = urlApiItinerary + pathPlanning;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			ResponseEntity<Object> resp = restCall.executePOST(url, planningRDTO);

			returnRDTO = (ReturnRDTO) utils.rest2Object(resp, returnRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnRDTO;
	}

	@Override
	public ReturnPlanningMassiveRDTO selectMassive(QueryParameterPlanningRDTO queryParameterPlanningRDTO) throws ImiException {
		ReturnPlanningMassiveRDTO returnUbicationMassiveRDTO = new ReturnPlanningMassiveRDTO();

		try {

			String url = urlApiItinerary + pathPlanning + pathPlanningGetMassive;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			Map<String, String> urlParams = new HashMap<String, String>();
			urlParams.put("codi", queryParameterPlanningRDTO.getCodeContract());
			urlParams.put("any", queryParameterPlanningRDTO.getYear());
			urlParams.put("codiUsuari", queryParameterPlanningRDTO.getCodeUser());
			urlParams.put("transactionId", queryParameterPlanningRDTO.getTransactionId());

			ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterPlanningRDTO);

			returnUbicationMassiveRDTO = (ReturnPlanningMassiveRDTO) utils.rest2Object(resp, returnUbicationMassiveRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnUbicationMassiveRDTO;
	}

	@Override
	public ReturnPlanningDetailedRDTO selectDetailed(QueryParameterPlanningRDTO queryParameterPlanningRDTO) throws ImiException {
		ReturnPlanningDetailedRDTO returnUbicationDetailedRDTO = new ReturnPlanningDetailedRDTO();

		try {

			String url = urlApiItinerary + pathPlanning + pathPlanningGetDetailed;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			Map<String, String> urlParams = new HashMap<String, String>();
			urlParams.put("codi", queryParameterPlanningRDTO.getCode());
			urlParams.put("codiContracta", queryParameterPlanningRDTO.getCodeContract());
			urlParams.put("any", queryParameterPlanningRDTO.getYear());
			urlParams.put("dataReferencia", queryParameterPlanningRDTO.getDateReference());
			urlParams.put("codiUsuari", queryParameterPlanningRDTO.getCodeUser());
			urlParams.put("transactionId", queryParameterPlanningRDTO.getTransactionId());

			ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterPlanningRDTO);

			returnUbicationDetailedRDTO = (ReturnPlanningDetailedRDTO) utils.rest2Object(resp, returnUbicationDetailedRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnUbicationDetailedRDTO;
	}
}
