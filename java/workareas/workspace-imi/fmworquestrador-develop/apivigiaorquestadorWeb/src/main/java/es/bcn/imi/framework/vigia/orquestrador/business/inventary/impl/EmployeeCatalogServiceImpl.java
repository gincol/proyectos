package es.bcn.imi.framework.vigia.orquestrador.business.inventary.impl;

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

import es.bcn.imi.framework.vigia.orquestrador.business.inventary.EmployeeCatalogService;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.EmployeeCatalogRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnEmployeeCatalogDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import es.bcn.vigia.fmw.libutils.services.Utils;
import es.bcn.vigia.fmw.libutils.services.rest.RestCall;

@Lazy
@Service(ServicesConstants.SRV_INVENTARY_EMPLOYEE_CATALOG)
public class EmployeeCatalogServiceImpl implements EmployeeCatalogService {

	private Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ServicesConstants.SRV_REST_CALL)
	private RestCall restCall;

	@Autowired
	@Qualifier(ServicesConstants.SRV_UTILS)
	private Utils utils;

	@Value("${url.api.inventary}")
	private String urlApiInventary;

	@Value("${url.path.humanresources}")
	private String pathHumanResources;
	
	@Value("${url.path.employee.catalog}")
	private String pathEmployeeCatalog;
	
	@Value("${url.path.employee.catalog.getMassive}")
	private String pathEmployeeCatalogGetMassive;
	
	@Value("${url.path.employee.catalog.getDetailed}")
	private String pathEmployeeCatalogGetDetailed;

	private ReturnRDTO returnRDTO;
	
	@Override
	public ReturnRDTO insert(EmployeeCatalogRDTO employeeCatalogRDTO) throws ImiException {

		try {

			returnRDTO = new ReturnRDTO();

			String url = urlApiInventary + pathHumanResources + pathEmployeeCatalog;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			ResponseEntity<Object> resp = restCall.executePOST(url, employeeCatalogRDTO);

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

			String url = urlApiInventary + pathHumanResources + pathEmployeeCatalog + pathEmployeeCatalogGetMassive;

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
	public ReturnEmployeeCatalogDetailedRDTO selectDetailed(QueryParameterRDTO queryParameterRDTO) throws ImiException {
		ReturnEmployeeCatalogDetailedRDTO returnEmployeeCatalogDetailedRDTO = new ReturnEmployeeCatalogDetailedRDTO();

		try {

			String url = urlApiInventary + pathHumanResources + pathEmployeeCatalog + pathEmployeeCatalogGetDetailed;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			Map<String, String> urlParams = new HashMap<String, String>();
			urlParams.put("codi", queryParameterRDTO.getCodeContract());
			urlParams.put("dataReferencia", queryParameterRDTO.getDateReference());
			urlParams.put("codiUsuari", queryParameterRDTO.getCodeUser());
			urlParams.put("transactionId", queryParameterRDTO.getTransactionId());

			ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterRDTO);

			returnEmployeeCatalogDetailedRDTO = (ReturnEmployeeCatalogDetailedRDTO) utils.rest2Object(resp, returnEmployeeCatalogDetailedRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnEmployeeCatalogDetailedRDTO;
	}

}
