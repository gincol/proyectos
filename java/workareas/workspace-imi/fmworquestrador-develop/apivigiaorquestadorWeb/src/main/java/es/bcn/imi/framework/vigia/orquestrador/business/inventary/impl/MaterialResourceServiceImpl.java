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

import es.bcn.imi.framework.vigia.orquestrador.business.inventary.MaterialResourceService;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.MaterialResourceRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnBreakdownAmortizationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMaterialResourceDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMaterialResourceVehicleDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMaterialResourceVehicleExpensesRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMaterialResourceVehiclePeriodRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import es.bcn.vigia.fmw.libutils.services.Utils;
import es.bcn.vigia.fmw.libutils.services.rest.RestCall;

@Lazy
@Service(ServicesConstants.SRV_INVENTARY_MATERIAL_RESOURCE)
public class MaterialResourceServiceImpl implements MaterialResourceService {

	private Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ServicesConstants.SRV_REST_CALL)
	private RestCall restCall;

	@Autowired
	@Qualifier(ServicesConstants.SRV_UTILS)
	private Utils utils;

	@Value("${url.api.inventary}")
	private String urlApiInventary;

	@Value("${url.path.materialresource}")
	private String pathMaterialResource;

	@Value("${url.path.materialresource.getMassive}")
	private String pathMaterialResourceGetMassive;

	@Value("${url.path.materialresource.getDetailed}")
	private String pathMaterialResourceGetDetailed;

	@Value("${url.path.materialresource.put}")
	private String pathMaterialResourcePut;

	@Value("${url.path.materialresource.delete}")
	private String pathMaterialResourceDelete;

	@Value("${url.path.materialresource.vehicle}")
	private String pathMaterialResourceVehicle;

	@Value("${url.path.materialresource.vehicle.getMassive}")
	private String pathMaterialResourceVehicleGetMassive;

	@Value("${url.path.materialresource.vehicle.getDetailed}")
	private String pathMaterialResourceVehicleGetDetailed;

	@Value("${url.path.materialresource.vehicle.getAmortization}")
	private String pathMaterialResourceVehicleGetAmortization;

	@Value("${url.path.materialresource.vehicle.getExpenses}")
	private String pathMaterialResourceVehicleGetExpenses;

	@Value("${url.path.materialresource.vehicle.getPeriod}")
	private String pathMaterialResourceVehicleGetPeriod;
	
	@Value("${url.path.materialresource.expense}")
	private String pathMaterialResourceExpense;
	
	@Value("${url.path.materialresource.amortization}")
	private String pathMaterialResourceAmortization;
	
	@Value("${url.path.materialresource.apportionment}")
	private String pathMaterialResourceApportionment;

	private ReturnRDTO returnRDTO;

	@Override
	public ReturnRDTO redirect(MaterialResourceRDTO materialResourceRDTO, HttpServletRequest request)
			throws ImiException {

		if (request.getMethod().equals(String.valueOf(RequestMethod.POST))) {

			returnRDTO = insert(materialResourceRDTO);
		}

		if (request.getMethod().equals(String.valueOf(RequestMethod.PUT))) {

			returnRDTO = update(materialResourceRDTO);
		}

		if (request.getMethod().equals(String.valueOf(RequestMethod.DELETE))) {

			returnRDTO = delete(materialResourceRDTO);
		}

		return returnRDTO;
	}

	@Override
	public ReturnRDTO insert(MaterialResourceRDTO materialResourceRDTO) throws ImiException {
		try {

			returnRDTO = new ReturnRDTO();

			String url = urlApiInventary + pathMaterialResource;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			ResponseEntity<Object> resp = restCall.executePOST(url, materialResourceRDTO);

			returnRDTO = (ReturnRDTO) utils.rest2Object(resp, returnRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} 
		
		return returnRDTO;
	}

	@Override
	public ReturnRDTO update(MaterialResourceRDTO materialResourceRDTO) throws ImiException {
		try {

			returnRDTO = new ReturnRDTO();

			String url = urlApiInventary + pathMaterialResource + pathMaterialResourcePut;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			Map<String, String> urlParams = new HashMap<String, String>();
			urlParams.put("codi", materialResourceRDTO.getCode());

			ResponseEntity<Object> resp = restCall.executePUT(url, urlParams, materialResourceRDTO);

			returnRDTO = (ReturnRDTO) utils.rest2Object(resp, returnRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} 
		
		return returnRDTO;
	}

	@Override
	public ReturnRDTO delete(MaterialResourceRDTO materialResourceRDTO) throws ImiException {
		try {

			returnRDTO = new ReturnRDTO();

			String url = urlApiInventary + pathMaterialResource + pathMaterialResourceDelete;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			Map<String, String> urlParams = new HashMap<String, String>();
			urlParams.put("codi", materialResourceRDTO.getCode());

			ResponseEntity<Object> resp = restCall.executeDELETE(url, urlParams, materialResourceRDTO);

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

			String url = urlApiInventary + pathMaterialResource + pathMaterialResourceGetMassive;

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
	public ReturnMaterialResourceDetailedRDTO selectDetailed(QueryParameterRDTO queryParameterRDTO) throws ImiException {
		ReturnMaterialResourceDetailedRDTO returnMaterialResourceDetailedRDTO = new ReturnMaterialResourceDetailedRDTO();

		try {

			String url = urlApiInventary + pathMaterialResource + pathMaterialResourceGetDetailed;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			Map<String, String> urlParams = new HashMap<String, String>();
			urlParams.put("codi", queryParameterRDTO.getCode());
			urlParams.put("codiContracta", queryParameterRDTO.getCodeContract());
			urlParams.put("dataReferencia", queryParameterRDTO.getDateReference());
			urlParams.put("codiUsuari", queryParameterRDTO.getCodeUser());
			urlParams.put("transactionId", queryParameterRDTO.getTransactionId());

			ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterRDTO);

			returnMaterialResourceDetailedRDTO = (ReturnMaterialResourceDetailedRDTO) utils.rest2Object(resp, returnMaterialResourceDetailedRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnMaterialResourceDetailedRDTO;
	}

	@Override
	public ReturnMassiveRDTO selectVehicleMassive(QueryParameterRDTO queryParameterRDTO) throws ImiException {
		ReturnMassiveRDTO returnMaterialResourceMassiveRDTO = new ReturnMassiveRDTO();

		try {

			String url = urlApiInventary + pathMaterialResource + pathMaterialResourceVehicle + pathMaterialResourceVehicleGetMassive;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			Map<String, String> urlParams = new HashMap<String, String>();
			urlParams.put("codi", queryParameterRDTO.getCodeContract());
			urlParams.put("dataReferencia", queryParameterRDTO.getDateReference());
			urlParams.put("codiUsuari", queryParameterRDTO.getCodeUser());
			urlParams.put("transactionId", queryParameterRDTO.getTransactionId());

			ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterRDTO);

			returnMaterialResourceMassiveRDTO = (ReturnMassiveRDTO) utils.rest2Object(resp, returnMaterialResourceMassiveRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnMaterialResourceMassiveRDTO;
	}

	@Override
	public ReturnMaterialResourceVehicleDetailedRDTO selectVehicleDetailed(QueryParameterRDTO queryParameterRDTO) throws ImiException {
		ReturnMaterialResourceVehicleDetailedRDTO returnMaterialResourceVehicleDetailedRDTO = new ReturnMaterialResourceVehicleDetailedRDTO();

		try {

			String url = urlApiInventary + pathMaterialResource + pathMaterialResourceVehicle + pathMaterialResourceVehicleGetDetailed;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			Map<String, String> urlParams = new HashMap<String, String>();
			urlParams.put("codi", queryParameterRDTO.getCode());
			urlParams.put("codiContracta", queryParameterRDTO.getCodeContract());
			urlParams.put("dataReferencia", queryParameterRDTO.getDateReference());
			urlParams.put("codiUsuari", queryParameterRDTO.getCodeUser());
			urlParams.put("transactionId", queryParameterRDTO.getTransactionId());

			ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterRDTO);

			returnMaterialResourceVehicleDetailedRDTO = (ReturnMaterialResourceVehicleDetailedRDTO) utils.rest2Object(resp, returnMaterialResourceVehicleDetailedRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnMaterialResourceVehicleDetailedRDTO;
	}

	@Override
	public ReturnRDTO insertExpense(MaterialResourceRDTO materialResourceRDTO) throws ImiException {
		try {

			returnRDTO = new ReturnRDTO();

			String url = urlApiInventary + pathMaterialResource + pathMaterialResourceExpense;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			ResponseEntity<Object> resp = restCall.executePOST(url, materialResourceRDTO);

			returnRDTO = (ReturnRDTO) utils.rest2Object(resp, returnRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnRDTO;
	}

	@Override
	public ReturnRDTO insertAmortizationBase(MaterialResourceRDTO materialResourceRDTO) throws ImiException {
		try {

			returnRDTO = new ReturnRDTO();

			String url = urlApiInventary + pathMaterialResource + pathMaterialResourceAmortization;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			ResponseEntity<Object> resp = restCall.executePOST(url, materialResourceRDTO);

			returnRDTO = (ReturnRDTO) utils.rest2Object(resp, returnRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnRDTO;
	}

	@Override
	public ReturnRDTO insertApportionment(MaterialResourceRDTO materialResourceRDTO) throws ImiException {
		try {

			returnRDTO = new ReturnRDTO();

			String url = urlApiInventary + pathMaterialResource + pathMaterialResourceApportionment;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			ResponseEntity<Object> resp = restCall.executePOST(url, materialResourceRDTO);

			returnRDTO = (ReturnRDTO) utils.rest2Object(resp, returnRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnRDTO;
	}

	@Override
	public ReturnBreakdownAmortizationRDTO selectAmortization(QueryParameterRDTO queryParameterRDTO) throws ImiException {
		ReturnBreakdownAmortizationRDTO returnBreakdownAmortizationRDTO = new ReturnBreakdownAmortizationRDTO();

		try {

			String url = urlApiInventary + pathMaterialResource + pathMaterialResourceVehicle + pathMaterialResourceVehicleGetAmortization;

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
	public ReturnMaterialResourceVehicleExpensesRDTO selectExpenses(QueryParameterRDTO queryParameterRDTO) throws ImiException {
		ReturnMaterialResourceVehicleExpensesRDTO returnMaterialResourceVehicleExpensesRDTO = new ReturnMaterialResourceVehicleExpensesRDTO();

		try {

			String url = urlApiInventary + pathMaterialResource + pathMaterialResourceVehicle + pathMaterialResourceVehicleGetExpenses;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			Map<String, String> urlParams = new HashMap<String, String>();
			urlParams.put("codi", queryParameterRDTO.getCode());
			urlParams.put("codiContracta", queryParameterRDTO.getCodeContract());
			urlParams.put("codiUsuari", queryParameterRDTO.getCodeUser());
			urlParams.put("transactionId", queryParameterRDTO.getTransactionId());

			ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterRDTO);

			returnMaterialResourceVehicleExpensesRDTO = (ReturnMaterialResourceVehicleExpensesRDTO) utils.rest2Object(resp, returnMaterialResourceVehicleExpensesRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnMaterialResourceVehicleExpensesRDTO;
	}

	@Override
	public ReturnMaterialResourceVehiclePeriodRDTO selectPeriod(QueryParameterRDTO queryParameterRDTO) throws ImiException {
		ReturnMaterialResourceVehiclePeriodRDTO returnMaterialResourceVehiclePeriodRDTO = new ReturnMaterialResourceVehiclePeriodRDTO();

		try {

			String url = urlApiInventary + pathMaterialResource + pathMaterialResourceVehicle + pathMaterialResourceVehicleGetPeriod;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			Map<String, String> urlParams = new HashMap<String, String>();
			urlParams.put("codi", queryParameterRDTO.getCode());
			urlParams.put("codiContracta", queryParameterRDTO.getCodeContract());
			urlParams.put("periode", queryParameterRDTO.getPeriod());
			urlParams.put("codiUsuari", queryParameterRDTO.getCodeUser());
			urlParams.put("transactionId", queryParameterRDTO.getTransactionId());

			ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterRDTO);

			returnMaterialResourceVehiclePeriodRDTO = (ReturnMaterialResourceVehiclePeriodRDTO) utils.rest2Object(resp, returnMaterialResourceVehiclePeriodRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnMaterialResourceVehiclePeriodRDTO;
	}

}
