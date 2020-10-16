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

import es.bcn.imi.framework.vigia.orquestrador.business.inventary.InstallationService;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.InstallationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnBreakdownAmortizationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnInstallationDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnInstallationExpensesRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnInstallationPeriodRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import es.bcn.vigia.fmw.libutils.services.Utils;
import es.bcn.vigia.fmw.libutils.services.rest.RestCall;

@Lazy
@Service(ServicesConstants.SRV_INVENTARY_INSTALLATION)
public class InstallationServiceImpl implements InstallationService {

	private Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ServicesConstants.SRV_REST_CALL)
	private RestCall restCall;

	@Autowired
	@Qualifier(ServicesConstants.SRV_UTILS)
	private Utils utils;

	@Value("${url.api.inventary}")
	private String urlApiInventary;

	@Value("${url.path.installation}")
	private String pathInstallation;

	@Value("${url.path.installation.getDetailed}")
	private String pathInstallationGetDetailed;

	@Value("${url.path.installation.getMassive}")
	private String pathInstallationGetMassive;

	@Value("${url.path.installation.getAmortization}")
	private String pathInstallationGetAmortization;

	@Value("${url.path.installation.getExpenses}")
	private String pathInstallationGetExpenses;

	@Value("${url.path.installation.getPeriod}")
	private String pathInstallationGetPeriod;

	@Value("${url.path.installation.put}")
	private String pathInstallationPut;

	@Value("${url.path.installation.delete}")
	private String pathInstallationDelete;

	@Value("${url.path.installation.expense}")
	private String pathInstallationExpense;
	
	@Value("${url.path.installation.amortization}")
	private String pathInstallationAmortization;
	
	@Value("${url.path.installation.apportionment}")
	private String pathInstallationApportionment;

	private ReturnRDTO returnRDTO;

	@Override
	public ReturnRDTO redirect(InstallationRDTO installationRDTO, HttpServletRequest request) throws ImiException {

		if (request.getMethod().equals(String.valueOf(RequestMethod.POST))) {

			returnRDTO = insert(installationRDTO);
		}

		if (request.getMethod().equals(String.valueOf(RequestMethod.PUT))) {

			returnRDTO = update(installationRDTO);
		}

		if (request.getMethod().equals(String.valueOf(RequestMethod.DELETE))) {

			returnRDTO = delete(installationRDTO);
		}

		return returnRDTO;
	}

	@Override
	public ReturnRDTO insert(InstallationRDTO installationRDTO) throws ImiException {

		try {

			returnRDTO = new ReturnRDTO();

			String url = urlApiInventary + pathInstallation;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			ResponseEntity<Object> resp = restCall.executePOST(url, installationRDTO);

			returnRDTO = (ReturnRDTO) utils.rest2Object(resp, returnRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnRDTO;
	}

	@Override
	public ReturnRDTO update(InstallationRDTO installationRDTO) throws ImiException {

		try {

			returnRDTO = new ReturnRDTO();

			String url = urlApiInventary + pathInstallation + pathInstallationPut;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			Map<String, String> urlParams = new HashMap<String, String>();
			urlParams.put("codi", installationRDTO.getCode());

			ResponseEntity<Object> resp = restCall.executePUT(url, urlParams, installationRDTO);

			returnRDTO = (ReturnRDTO) utils.rest2Object(resp, returnRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnRDTO;
	}

	@Override
	public ReturnRDTO delete(InstallationRDTO installationRDTO) throws ImiException {

		try {

			returnRDTO = new ReturnRDTO();

			String url = urlApiInventary + pathInstallation + pathInstallationDelete;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			Map<String, String> urlParams = new HashMap<String, String>();
			urlParams.put("codi", installationRDTO.getCode());

			ResponseEntity<Object> resp = restCall.executeDELETE(url, urlParams, installationRDTO);

			returnRDTO = (ReturnRDTO) utils.rest2Object(resp, returnRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnRDTO;
	}

	@Override
	public ReturnRDTO insertExpense(InstallationRDTO installationRDTO) throws ImiException {
		try {

			returnRDTO = new ReturnRDTO();

			String url = urlApiInventary + pathInstallation + pathInstallationExpense;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			ResponseEntity<Object> resp = restCall.executePOST(url, installationRDTO);

			returnRDTO = (ReturnRDTO) utils.rest2Object(resp, returnRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnRDTO;
	}

	@Override
	public ReturnRDTO insertAmortizationBase(InstallationRDTO installationRDTO) throws ImiException {
		try {

			returnRDTO = new ReturnRDTO();

			String url = urlApiInventary + pathInstallation + pathInstallationAmortization;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			ResponseEntity<Object> resp = restCall.executePOST(url, installationRDTO);

			returnRDTO = (ReturnRDTO) utils.rest2Object(resp, returnRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnRDTO;
	}

	@Override
	public ReturnRDTO insertApportionment(InstallationRDTO installationRDTO) throws ImiException {
		try {

			returnRDTO = new ReturnRDTO();

			String url = urlApiInventary + pathInstallation + pathInstallationApportionment;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			ResponseEntity<Object> resp = restCall.executePOST(url, installationRDTO);

			returnRDTO = (ReturnRDTO) utils.rest2Object(resp, returnRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnRDTO;
	}

	@Override
	public ReturnInstallationDetailedRDTO selectDetailed(QueryParameterRDTO queryParameterRDTO) throws ImiException {
		ReturnInstallationDetailedRDTO returnInstallationDetailedRDTO = new ReturnInstallationDetailedRDTO();

		try {

			String url = urlApiInventary + pathInstallation + pathInstallationGetDetailed;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			Map<String, String> urlParams = new HashMap<String, String>();
			urlParams.put("codi", queryParameterRDTO.getCode());
			urlParams.put("codiContracta", queryParameterRDTO.getCodeContract());
			urlParams.put("dataReferencia", queryParameterRDTO.getDateReference());
			urlParams.put("codiUsuari", queryParameterRDTO.getCodeUser());
			urlParams.put("transactionId", queryParameterRDTO.getTransactionId());

			ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterRDTO);

			returnInstallationDetailedRDTO = (ReturnInstallationDetailedRDTO) utils.rest2Object(resp, returnInstallationDetailedRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnInstallationDetailedRDTO;
	}

	@Override
	public ReturnMassiveRDTO selectMassive(QueryParameterRDTO queryParameterRDTO) throws ImiException {
		ReturnMassiveRDTO returnMassiveRDTO = new ReturnMassiveRDTO();

		try {

			String url = urlApiInventary + pathInstallation + pathInstallationGetMassive;

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
	public ReturnBreakdownAmortizationRDTO selectAmortization(QueryParameterRDTO queryParameterRDTO) throws ImiException {
		ReturnBreakdownAmortizationRDTO returnBreakdownAmortizationRDTO = new ReturnBreakdownAmortizationRDTO();

		try {

			String url = urlApiInventary + pathInstallation + pathInstallationGetAmortization;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			Map<String, String> urlParams = new HashMap<String, String>();
			urlParams.put("codi", queryParameterRDTO.getCode());
			urlParams.put("codiContracta", queryParameterRDTO.getCodeContract());
			urlParams.put("codiUsuari", queryParameterRDTO.getCodeUser());
			urlParams.put("transactionId", queryParameterRDTO.getTransactionId());

			ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterRDTO);

			returnBreakdownAmortizationRDTO = (ReturnBreakdownAmortizationRDTO) utils.rest2Object(resp, returnBreakdownAmortizationRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnBreakdownAmortizationRDTO;
	}

	@Override
	public ReturnInstallationExpensesRDTO selectExpenses(QueryParameterRDTO queryParameterRDTO) throws ImiException {
		ReturnInstallationExpensesRDTO returnInstallationExpensesRDTO = new ReturnInstallationExpensesRDTO();

		try {

			String url = urlApiInventary + pathInstallation + pathInstallationGetExpenses;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			Map<String, String> urlParams = new HashMap<String, String>();
			urlParams.put("codi", queryParameterRDTO.getCode());
			urlParams.put("codiContracta", queryParameterRDTO.getCodeContract());
			urlParams.put("codiUsuari", queryParameterRDTO.getCodeUser());
			urlParams.put("transactionId", queryParameterRDTO.getTransactionId());

			ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterRDTO);

			returnInstallationExpensesRDTO = (ReturnInstallationExpensesRDTO) utils.rest2Object(resp, returnInstallationExpensesRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnInstallationExpensesRDTO;
	}

	@Override
	public ReturnInstallationPeriodRDTO selectPeriod(QueryParameterRDTO queryParameterRDTO) throws ImiException {
		ReturnInstallationPeriodRDTO returnInstallationPeriodRDTO = new ReturnInstallationPeriodRDTO();

		try {

			String url = urlApiInventary + pathInstallation + pathInstallationGetPeriod;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			Map<String, String> urlParams = new HashMap<String, String>();
			urlParams.put("codi", queryParameterRDTO.getCode());
			urlParams.put("codiContracta", queryParameterRDTO.getCodeContract());
			urlParams.put("periode", queryParameterRDTO.getPeriod());
			urlParams.put("codiUsuari", queryParameterRDTO.getCodeUser());
			urlParams.put("transactionId", queryParameterRDTO.getTransactionId());

			ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterRDTO);

			returnInstallationPeriodRDTO = (ReturnInstallationPeriodRDTO) utils.rest2Object(resp, returnInstallationPeriodRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnInstallationPeriodRDTO;
	}

}
