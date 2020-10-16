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

import es.bcn.imi.framework.vigia.orquestrador.business.inventary.ActuationService;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.ActuationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterActuationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnActuationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import es.bcn.vigia.fmw.libutils.services.Utils;
import es.bcn.vigia.fmw.libutils.services.rest.RestCall;

@Lazy
@Service(ServicesConstants.SRV_INVENTARY_ACTUATION)
public class ActuationServiceImpl implements ActuationService {
	
	private Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	@Qualifier(ServicesConstants.SRV_REST_CALL)
	private RestCall restCall;
	
	@Autowired
	@Qualifier(ServicesConstants.SRV_UTILS)
	private Utils utils;
	
	@Value("${url.api.inventary}")
	private String urlApiInventary;

	@Value("${url.path.actuation}")
	private String pathActuation;

	@Value("${url.path.actuation.get}")
	private String pathActuationGet;
	
	@Value("${url.path.actuation.delete}")
	private String pathActuationDelete;


	private ReturnRDTO returnRDTO;
	
	@Override
	public ReturnRDTO redirect(ActuationRDTO actuationRDTO, HttpServletRequest request) throws ImiException {

		if (request.getMethod().equals(String.valueOf(RequestMethod.POST))) {

			returnRDTO = insert(actuationRDTO);
		}

		if (request.getMethod().equals(String.valueOf(RequestMethod.PUT))) {

			returnRDTO = delete(actuationRDTO);
		}

		

		return returnRDTO;
	}

		
	@Override
	public ReturnRDTO insert(ActuationRDTO actuationRDTO)
			throws ImiException {
		try {
			
			returnRDTO = new ReturnRDTO();
			
			String url = urlApiInventary + pathActuation;
			
			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));
			
			ResponseEntity<Object> resp = restCall.executePOST(url, actuationRDTO);
			
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
	public ReturnRDTO delete(ActuationRDTO actuationRDTO)
			throws ImiException {
		try {
			
			returnRDTO = new ReturnRDTO();
			
			String url = urlApiInventary +  pathActuation + pathActuationDelete;
			
			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			Map<String, String> urlParams = new HashMap<>();
			urlParams.put("codi", actuationRDTO.getCode());
			urlParams.put("codiEntitat", actuationRDTO.getCodeEntity());
			
			ResponseEntity<Object> resp = restCall.executePUT(url, urlParams, actuationRDTO);

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
	public ReturnActuationRDTO selectActuation(QueryParameterActuationRDTO queryParameterActuationRDTO)
			throws ImiException {
		ReturnActuationRDTO returnActuationRDTO = new ReturnActuationRDTO();

		try {

			String url = urlApiInventary + pathActuation + pathActuationGet;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			Map<String, String> urlParams = new HashMap<String, String>();
			urlParams.put("codi", queryParameterActuationRDTO.getCodeContract());
			urlParams.put("tipusEntitat", queryParameterActuationRDTO.getCodeTypeEntity());
			urlParams.put("codiEntitat", queryParameterActuationRDTO.getCodeEntity());
			urlParams.put("considerarAnulacions", queryParameterActuationRDTO.getConsiderAnnulments());
			urlParams.put("codiUsuari", queryParameterActuationRDTO.getCodeUser());
			urlParams.put("transactionId", queryParameterActuationRDTO.getTransactionId());

			
			ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterActuationRDTO);

			returnActuationRDTO = (ReturnActuationRDTO) utils.rest2Object(resp, returnActuationRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnActuationRDTO;

	}



}
