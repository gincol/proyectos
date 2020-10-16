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

import es.bcn.imi.framework.vigia.orquestrador.business.inventary.UbicationService;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.UbicationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnUbicationDetailedRDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import es.bcn.vigia.fmw.libutils.services.Utils;
import es.bcn.vigia.fmw.libutils.services.rest.RestCall;

@Lazy
@Service(ServicesConstants.SRV_INVENTARY_UBICATION)
public class UbicationServiceImpl implements UbicationService {
	
	private Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	@Qualifier(ServicesConstants.SRV_REST_CALL)
	private RestCall restCall;
	
	@Autowired
	@Qualifier(ServicesConstants.SRV_UTILS)
	private Utils utils;
	
	@Value("${url.api.inventary}")
	private String urlApiInventary;

	@Value("${url.path.ubications}")
	private String pathUbication;

	@Value("${url.path.ubications.getMassive}")
	private String pathUbicationGetMassive;

	@Value("${url.path.ubications.getDetailed}")
	private String pathUbicationGetDetailed;
	
	@Value("${url.path.ubications.put}")
	private String pathUbicationPut;
	
	@Value("${url.path.ubications.delete}")
	private String pathUbicationDelete;
	
	private ReturnRDTO returnRDTO;
	
	@Override
	public ReturnRDTO redirect(UbicationRDTO ubicationRDTO, HttpServletRequest request) throws ImiException {

		if (request.getMethod().equals(String.valueOf(RequestMethod.POST))) {

			returnRDTO = insert(ubicationRDTO);
		}

		if (request.getMethod().equals(String.valueOf(RequestMethod.PUT))) {

			returnRDTO = update(ubicationRDTO);
		}

		if (request.getMethod().equals(String.valueOf(RequestMethod.DELETE))) {
			
			returnRDTO = delete(ubicationRDTO);
		}

		return returnRDTO;
	}


	@Override
	public ReturnRDTO insert(UbicationRDTO ubicationRDTO) throws ImiException {
		
		try {
			
			returnRDTO = new ReturnRDTO();
			
			String url = urlApiInventary + pathUbication;
			
			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));
			
			ResponseEntity<Object> resp = restCall.executePOST(url, ubicationRDTO);
			
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
	public ReturnRDTO update(UbicationRDTO ubicationRDTO) throws ImiException {

		try {
			
			returnRDTO = new ReturnRDTO();
			
			String url = urlApiInventary + pathUbication + pathUbicationPut;
			
			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));
			
			Map<String, String> urlParams = new HashMap<String, String>();
			urlParams.put("codi", ubicationRDTO.getCode());
			
			ResponseEntity<Object> resp = restCall.executePUT(url, urlParams, ubicationRDTO);
			
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
	public ReturnRDTO delete(UbicationRDTO ubicationRDTO) throws ImiException {
		
		try {
			
			returnRDTO = new ReturnRDTO();
			
			String url = urlApiInventary + pathUbication + pathUbicationDelete;
			
			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));
			
			Map<String, String> urlParams = new HashMap<String, String>();
			urlParams.put("codi", ubicationRDTO.getCode());
			
			ResponseEntity<Object> resp = restCall.executeDELETE(url, urlParams, ubicationRDTO);
			
			returnRDTO =  (ReturnRDTO) utils.rest2Object(resp, returnRDTO);
							
			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));
			
			
		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} 
		
		return returnRDTO;
	}	

	@Override
	public ReturnMassiveRDTO selectMassive(QueryParameterRDTO queryParameterRDTO) throws ImiException {
		ReturnMassiveRDTO returnMassiveRDTO = new ReturnMassiveRDTO();

		try {

			String url = urlApiInventary + pathUbication + pathUbicationGetMassive;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			Map<String, String> urlParams = new HashMap<String, String>();
			urlParams.put("codi", queryParameterRDTO.getCodeContract());
			urlParams.put("dataReferencia", queryParameterRDTO.getDateReference());
			urlParams.put("codiUsuari", queryParameterRDTO.getCodeUser());
			urlParams.put("transactionId", queryParameterRDTO.getTransactionId());

			ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterRDTO);

			returnMassiveRDTO = (ReturnMassiveRDTO) utils.rest2Object(resp, returnMassiveRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnMassiveRDTO;
	}

	@Override
	public ReturnUbicationDetailedRDTO selectDetailed(QueryParameterRDTO queryParameterRDTO) throws ImiException {
		ReturnUbicationDetailedRDTO returnUbicationDetailedRDTO = new ReturnUbicationDetailedRDTO();

		try {

			String url = urlApiInventary + pathUbication + pathUbicationGetDetailed;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			Map<String, String> urlParams = new HashMap<String, String>();
			urlParams.put("codi", queryParameterRDTO.getCode());
			urlParams.put("codiContracta", queryParameterRDTO.getCodeContract());
			urlParams.put("dataReferencia", queryParameterRDTO.getDateReference());
			urlParams.put("codiUsuari", queryParameterRDTO.getCodeUser());
			urlParams.put("transactionId", queryParameterRDTO.getTransactionId());

			ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterRDTO);

			returnUbicationDetailedRDTO = (ReturnUbicationDetailedRDTO) utils.rest2Object(resp, returnUbicationDetailedRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnUbicationDetailedRDTO;
	}


}
