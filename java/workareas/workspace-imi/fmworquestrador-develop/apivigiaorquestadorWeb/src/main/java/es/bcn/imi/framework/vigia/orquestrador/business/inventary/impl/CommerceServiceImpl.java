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

import es.bcn.imi.framework.vigia.orquestrador.business.inventary.CommerceService;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CommerceRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnCommerceDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnCommerceElementsRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import es.bcn.vigia.fmw.libutils.services.Utils;
import es.bcn.vigia.fmw.libutils.services.rest.RestCall;

@Lazy
@Service(ServicesConstants.SRV_INVENTARY_COMMERCE)
public class CommerceServiceImpl implements CommerceService {

	private Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ServicesConstants.SRV_REST_CALL)
	private RestCall restCall;

	@Autowired
	@Qualifier(ServicesConstants.SRV_UTILS)
	private Utils utils;

	@Value("${url.api.inventary}")
	private String urlApiInventary;

	@Value("${url.path.commerce}")
	private String pathCommerce;

	@Value("${url.path.commerce.getMassive}")
	private String pathCommerceGetMassive;

	@Value("${url.path.commerce.getDetailed}")
	private String pathCommerceGetDetailed;

	@Value("${url.path.commerce.getElements}")
	private String pathCommerceGetElements;

	@Value("${url.path.commerce.put}")
	private String pathCommercePut;

	@Value("${url.path.commerce.delete}")
	private String pathCommerceDelete;
	
	@Value("${url.path.commerce.elements}")
	private String pathCommerceElements;

	private ReturnRDTO returnRDTO;
	
	@Override
	public ReturnRDTO redirect(CommerceRDTO commerceRDTO, HttpServletRequest request) throws ImiException {

		if (request.getMethod().equals(String.valueOf(RequestMethod.POST))) {

			returnRDTO = insert(commerceRDTO);
		}

		if (request.getMethod().equals(String.valueOf(RequestMethod.PUT))) {

			returnRDTO = update(commerceRDTO);
		}

		if (request.getMethod().equals(String.valueOf(RequestMethod.DELETE))) {

			returnRDTO = delete(commerceRDTO);
		}

		return returnRDTO;
	}

	@Override
	public ReturnRDTO insert(CommerceRDTO commerceRDTO) throws ImiException {

		try {

			returnRDTO = new ReturnRDTO();

			String url = urlApiInventary + pathCommerce;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			ResponseEntity<Object> resp = restCall.executePOST(url, commerceRDTO);

			returnRDTO = (ReturnRDTO) utils.rest2Object(resp, returnRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnRDTO;
	}

	@Override
	public ReturnRDTO update(CommerceRDTO commerceRDTO) throws ImiException {

		try {

			returnRDTO = new ReturnRDTO();

			String url = urlApiInventary + pathCommerce + pathCommercePut;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			Map<String, String> urlParams = new HashMap<String, String>();
			urlParams.put("codi", commerceRDTO.getCode());

			ResponseEntity<Object> resp = restCall.executePUT(url, urlParams, commerceRDTO);

			returnRDTO = (ReturnRDTO) utils.rest2Object(resp, returnRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnRDTO;
	}

	@Override
	public ReturnRDTO delete(CommerceRDTO commerceRDTO) throws ImiException {

		try {

			returnRDTO = new ReturnRDTO();

			String url = urlApiInventary + pathCommerce + pathCommerceDelete;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			Map<String, String> urlParams = new HashMap<String, String>();
			urlParams.put("codi", commerceRDTO.getCode());

			ResponseEntity<Object> resp = restCall.executeDELETE(url, urlParams, commerceRDTO);

			returnRDTO = (ReturnRDTO) utils.rest2Object(resp, returnRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnRDTO;
	}

	@Override
	public ReturnRDTO insertElements(CommerceRDTO commerceRDTO) throws ImiException {
		try {

			returnRDTO = new ReturnRDTO();

			String url = urlApiInventary + pathCommerce + pathCommerceElements;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));
			
			ResponseEntity<Object> resp = restCall.executePOST(url, commerceRDTO);

			returnRDTO = (ReturnRDTO) utils.rest2Object(resp, returnRDTO);

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

			String url = urlApiInventary + pathCommerce + pathCommerceGetMassive;

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
	public ReturnCommerceDetailedRDTO selectDetailed(QueryParameterRDTO queryParameterRDTO) throws ImiException {
		ReturnCommerceDetailedRDTO returnCommerceDetailedRDTO = new ReturnCommerceDetailedRDTO();

		try {

			String url = urlApiInventary + pathCommerce + pathCommerceGetDetailed;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			Map<String, String> urlParams = new HashMap<String, String>();
			urlParams.put("codi", queryParameterRDTO.getCode());
			urlParams.put("codiContracta", queryParameterRDTO.getCodeContract());
			urlParams.put("dataReferencia", queryParameterRDTO.getDateReference());
			urlParams.put("codiUsuari", queryParameterRDTO.getCodeUser());
			urlParams.put("transactionId", queryParameterRDTO.getTransactionId());

			ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterRDTO);

			returnCommerceDetailedRDTO = (ReturnCommerceDetailedRDTO) utils.rest2Object(resp, returnCommerceDetailedRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnCommerceDetailedRDTO;
	}

	@Override
	public ReturnCommerceElementsRDTO selectElements(QueryParameterRDTO queryParameterRDTO) throws ImiException {
		ReturnCommerceElementsRDTO returnCommerceElementsRDTO = new ReturnCommerceElementsRDTO();

		try {

			String url = urlApiInventary + pathCommerce + pathCommerceGetElements;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			Map<String, String> urlParams = new HashMap<String, String>();
			urlParams.put("codi", queryParameterRDTO.getCode());
			urlParams.put("codiContracta", queryParameterRDTO.getCodeContract());
			urlParams.put("dataReferencia", queryParameterRDTO.getDateReference());
			urlParams.put("codiUsuari", queryParameterRDTO.getCodeUser());
			urlParams.put("transactionId", queryParameterRDTO.getTransactionId());

			ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterRDTO);

			returnCommerceElementsRDTO = (ReturnCommerceElementsRDTO) utils.rest2Object(resp, returnCommerceElementsRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnCommerceElementsRDTO;
	}
}
