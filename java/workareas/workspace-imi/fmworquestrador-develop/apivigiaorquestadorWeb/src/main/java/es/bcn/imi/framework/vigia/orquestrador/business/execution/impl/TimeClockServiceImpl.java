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

import es.bcn.imi.framework.vigia.orquestrador.business.execution.TimeClockService;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterTimeRegisterRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.TimeClockInstantRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.TimeClockSummaryRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnTimeRegisterInstantRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnTimeRegisterSummaryRDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import es.bcn.vigia.fmw.libutils.services.Utils;
import es.bcn.vigia.fmw.libutils.services.rest.RestCall;

@Lazy
@Service(ServicesConstants.SRV_EXECUTION_TIME_CLOCK)
public class TimeClockServiceImpl implements TimeClockService {

	private Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ServicesConstants.SRV_REST_CALL)
	private RestCall restCall;

	@Autowired
	@Qualifier(ServicesConstants.SRV_UTILS)
	private Utils utils;

	@Value("${url.api.execution}")
	private String urlApiExecution;
	
	@Value("${url.path.time.clock}")
	private String pathTimeClock;

	@Value("${url.path.time.clock.instant}")
	private String pathTimeClockInstant;

	@Value("${url.path.time.clock.instantGet}")
	private String pathTimeClockInstantGet;
	
	@Value("${url.path.time.clock.summary}")
	private String pathTimeClockSummary;
	
	@Value("${url.path.time.clock.summaryGet}")
	private String pathTimeClockSummaryGet;
	
	private ReturnRDTO returnRDTO;

	@Override
	public ReturnRDTO insertInstant	(TimeClockInstantRDTO timeClockInstantRDTO) throws ImiException {
		try {

			returnRDTO = new ReturnRDTO();

			String url = urlApiExecution + pathTimeClock + pathTimeClockInstant;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			ResponseEntity<Object> resp = restCall.executePOST(url, timeClockInstantRDTO);

			returnRDTO = (ReturnRDTO) utils.rest2Object(resp, returnRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnRDTO;
	}

	@Override
	public ReturnRDTO insertSummary	(TimeClockSummaryRDTO timeClockSummaryRDTO) throws ImiException {
		try {

			returnRDTO = new ReturnRDTO();

			String url = urlApiExecution + pathTimeClock + pathTimeClockSummary;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			ResponseEntity<Object> resp = restCall.executePOST(url, timeClockSummaryRDTO);

			returnRDTO = (ReturnRDTO) utils.rest2Object(resp, returnRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnRDTO;
	}

	@Override
	public ReturnTimeRegisterInstantRDTO selectTimeRegisterInstant(QueryParameterTimeRegisterRDTO queryParameterRDTO) throws ImiException {
		ReturnTimeRegisterInstantRDTO returnTimeRegisterInstantRDTO = new ReturnTimeRegisterInstantRDTO();

		try {

			String url = urlApiExecution + pathTimeClock + pathTimeClockInstant + pathTimeClockInstantGet;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			Map<String, String> urlParams = new HashMap<String, String>();
			urlParams.put("codi", queryParameterRDTO.getCodeContract());
			urlParams.put("data", queryParameterRDTO.getDate());
			urlParams.put("codiRellotge", queryParameterRDTO.getCodeWatch());
			urlParams.put("codiTreballador", queryParameterRDTO.getCodeEmployee());
			urlParams.put("transactionId", queryParameterRDTO.getTransactionId());

			ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterRDTO);

			returnTimeRegisterInstantRDTO = (ReturnTimeRegisterInstantRDTO) utils.rest2Object(resp, returnTimeRegisterInstantRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnTimeRegisterInstantRDTO;
	}

	@Override
	public ReturnTimeRegisterSummaryRDTO selectTimeRegisterSummary(QueryParameterTimeRegisterRDTO queryParameterRDTO) throws ImiException {
		ReturnTimeRegisterSummaryRDTO returnTimeRegisterSummaryRDTO = new ReturnTimeRegisterSummaryRDTO();

		try {

			String url = urlApiExecution + pathTimeClock + pathTimeClockSummary + pathTimeClockSummaryGet;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			Map<String, String> urlParams = new HashMap<String, String>();
			urlParams.put("codi", queryParameterRDTO.getCodeContract());
			urlParams.put("data", queryParameterRDTO.getDate());
			urlParams.put("codiInstallacio", queryParameterRDTO.getCodeInstallation());
			urlParams.put("transactionId", queryParameterRDTO.getTransactionId());

			ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterRDTO);

			returnTimeRegisterSummaryRDTO = (ReturnTimeRegisterSummaryRDTO) utils.rest2Object(resp, returnTimeRegisterSummaryRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnTimeRegisterSummaryRDTO;
	}
}
