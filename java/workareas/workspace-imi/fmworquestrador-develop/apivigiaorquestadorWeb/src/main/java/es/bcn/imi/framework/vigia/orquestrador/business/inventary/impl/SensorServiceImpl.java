package es.bcn.imi.framework.vigia.orquestrador.business.inventary.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import es.bcn.imi.framework.vigia.orquestrador.business.inventary.SensorService;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.SensorRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnSensorDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnSensorMassiveRDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import es.bcn.vigia.fmw.libutils.services.Utils;
import es.bcn.vigia.fmw.libutils.services.rest.RestCall;

@Lazy
@Service(ServicesConstants.SRV_INVENTARY_SENSOR)
public class SensorServiceImpl implements SensorService {
	
	private Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	@Qualifier(ServicesConstants.SRV_REST_CALL)
	private RestCall restCall;
	
	@Autowired
	@Qualifier(ServicesConstants.SRV_UTILS)
	private Utils utils;
	
	@Value("${url.api.inventary}")
	private String urlApiInventary;

	@Value("${url.path.sensor}")
	private String pathSensor;

	@Value("${url.path.sensor.getMassive}")
	private String pathSensorGetMassive;

	@Value("${url.path.sensor.getDetailed}")
	private String pathSensorGetDetailed;
	
	@Value("${url.path.sensor.put}")
	private String pathSensorPut;
	
	@Value("${url.path.sensor.delete}")
	private String pathSensorDelete;
	
	private ReturnRDTO returnRDTO;
	
	@Override
	public ReturnRDTO redirect(SensorRDTO sensorRDTO, HttpServletRequest request) throws ImiException {

		if (request.getMethod().equals(String.valueOf(RequestMethod.POST))) {

			returnRDTO = insert(sensorRDTO);
		}

		if (request.getMethod().equals(String.valueOf(RequestMethod.PUT))) {

			returnRDTO = update(sensorRDTO);
		}

		if (request.getMethod().equals(String.valueOf(RequestMethod.DELETE))) {
			
			returnRDTO = delete(sensorRDTO);
		}

		return returnRDTO;
	}


	@Override
	public ReturnRDTO insert(SensorRDTO sensorRDTO) throws ImiException {
		
		try {
			
			returnRDTO = new ReturnRDTO();
			
			String url = urlApiInventary + pathSensor;
			
			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));
			
			ResponseEntity<Object> resp = restCall.executePOST(url, sensorRDTO);
			
			returnRDTO =  (ReturnRDTO) utils.rest2Object(resp, returnRDTO);
							
			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));
			
			
		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}
		return returnRDTO;
	}

	@Override
	public ReturnRDTO update(SensorRDTO sensorRDTO) throws ImiException {

		try {
			
			returnRDTO = new ReturnRDTO();
			
			String url = urlApiInventary + pathSensor + pathSensorPut;
			
			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));
			
			Map<String, String> urlParams = new HashMap<String, String>();
			urlParams.put("codi", sensorRDTO.getCode());
			
			ResponseEntity<Object> resp = restCall.executePUT(url, urlParams, sensorRDTO);
			
			returnRDTO =  (ReturnRDTO) utils.rest2Object(resp, returnRDTO);
							
			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));
			
			
		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}
		return returnRDTO;
	}

	@Override
	public ReturnRDTO delete(SensorRDTO sensorRDTO) throws ImiException {
		
		try {
			
			returnRDTO = new ReturnRDTO();
			
			String url = urlApiInventary + pathSensor + pathSensorDelete;
			
			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));
			
			Map<String, String> urlParams = new HashMap<String, String>();
			urlParams.put("codi", sensorRDTO.getCode());
			
			ResponseEntity<Object> resp = restCall.executeDELETE(url, urlParams, sensorRDTO);
			
			returnRDTO =  (ReturnRDTO) utils.rest2Object(resp, returnRDTO);
							
			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));
			
			
		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} 
		
		return returnRDTO;
	}	

	@Override
	public ReturnSensorMassiveRDTO selectMassive(QueryParameterRDTO queryParameterRDTO) throws ImiException {
		ReturnSensorMassiveRDTO returnSensorMassiveRDTO = new ReturnSensorMassiveRDTO();

		try {

			String url = urlApiInventary + pathSensor + pathSensorGetMassive;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			Map<String, String> urlParams = new HashMap<String, String>();
			urlParams.put("codi", queryParameterRDTO.getCodeContract());
			urlParams.put("dataReferencia", queryParameterRDTO.getDateReference());
			urlParams.put("codiUsuari", queryParameterRDTO.getCodeUser());
			urlParams.put("transactionId", queryParameterRDTO.getTransactionId());

			ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterRDTO);

			returnSensorMassiveRDTO = (ReturnSensorMassiveRDTO) utils.rest2Object(resp, returnSensorMassiveRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnSensorMassiveRDTO;
	}


	@Override
	public ReturnSensorDetailedRDTO selectDetailed(QueryParameterRDTO queryParameterRDTO) throws ImiException {
		ReturnSensorDetailedRDTO returnSensorDetailedRDTO = new ReturnSensorDetailedRDTO();
		try {

			String url = urlApiInventary + pathSensor + pathSensorGetDetailed;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			Map<String, String> urlParams = new HashMap<String, String>();
			urlParams.put("codi", queryParameterRDTO.getCode());
			urlParams.put("codiContracta", queryParameterRDTO.getCodeContract());
			urlParams.put("dataReferencia", queryParameterRDTO.getDateReference());
			urlParams.put("codiUsuari", queryParameterRDTO.getCodeUser());
			urlParams.put("transactionId", queryParameterRDTO.getTransactionId());

			ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterRDTO);

			returnSensorDetailedRDTO = (ReturnSensorDetailedRDTO) utils.rest2Object(resp, returnSensorDetailedRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnSensorDetailedRDTO;

	}




}
