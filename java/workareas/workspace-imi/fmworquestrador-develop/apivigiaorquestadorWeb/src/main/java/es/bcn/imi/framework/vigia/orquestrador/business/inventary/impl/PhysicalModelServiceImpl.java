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

import es.bcn.imi.framework.vigia.orquestrador.business.inventary.PhysicalModelService;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.AggregateAmortizationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.PhysicalModelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnPhysicalModelAmortizationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnPhysicalModelDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnPhysicalModelMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnPhysicalModelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import es.bcn.vigia.fmw.libutils.services.Utils;
import es.bcn.vigia.fmw.libutils.services.rest.RestCall;

@Lazy
@Service(ServicesConstants.SRV_INVENTARY_PHYSICAL_MODEL)
public class PhysicalModelServiceImpl implements PhysicalModelService {

	private Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ServicesConstants.SRV_REST_CALL)
	private RestCall restCall;

	@Autowired
	@Qualifier(ServicesConstants.SRV_UTILS)
	private Utils utils;

	@Value("${url.api.inventary}")
	private String urlApiInventary;

	@Value("${url.path.physicalmodel}")
	private String pathPhysicalModel;
	
	@Value("${url.path.physicalmodel.type}")
	private String pathPhysicalModelType;
	
	@Value("${url.path.physicalmodel.aggregate.amortization}")
	private String pathPhysicalModelAggregateAmortization;
	
	@Value("${url.path.physicalmodel.get}")
	private String pathPhysicalModelGet;
	
	@Value("${url.path.physicalmodel.getMassive}")
	private String pathPhysicalModelGetMassive;
	
	@Value("${url.path.physicalmodel.getDetailed}")
	private String pathPhysicalModelGetDetailed;
	
	@Value("${url.path.physicalmodel.getAmortization}")
	private String pathPhysicalModelGetAmortization;

	@Value("${url.path.physicalmodel.put}")
	private String pathPhysicalModelPut;

	@Value("${url.path.physicalmodel.delete}")
	private String pathPhysicalModelDelete;

	private ReturnRDTO returnRDTO;

	@Override
	public ReturnRDTO redirect(PhysicalModelRDTO physicalModelRDTO, HttpServletRequest request) throws ImiException {

		if (request.getMethod().equals(String.valueOf(RequestMethod.POST))) {

			returnRDTO = insert(physicalModelRDTO);
		}

		if (request.getMethod().equals(String.valueOf(RequestMethod.PUT))) {

			returnRDTO = update(physicalModelRDTO);
		}

		if (request.getMethod().equals(String.valueOf(RequestMethod.DELETE))) {

			returnRDTO = delete(physicalModelRDTO);
		}

		return returnRDTO;
	}

	@Override
	public ReturnRDTO insert(PhysicalModelRDTO physicalModelRDTO) throws ImiException {
		try {

			returnRDTO = new ReturnRDTO();

			String url = urlApiInventary + pathPhysicalModel;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			ResponseEntity<Object> resp = restCall.executePOST(url, physicalModelRDTO);

			returnRDTO = (ReturnRDTO) utils.rest2Object(resp, returnRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} 
		
		return returnRDTO;
	}

	@Override
	public ReturnRDTO update(PhysicalModelRDTO physicalModelRDTO) throws ImiException {
		try {

			returnRDTO = new ReturnRDTO();

			String url = urlApiInventary + pathPhysicalModel + pathPhysicalModelPut;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			Map<String, String> urlParams = new HashMap<String, String>();
			urlParams.put("codi", physicalModelRDTO.getCode());

			ResponseEntity<Object> resp = restCall.executePUT(url, urlParams, physicalModelRDTO);

			returnRDTO = (ReturnRDTO) utils.rest2Object(resp, returnRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} 
		
		return returnRDTO;
	}

	@Override
	public ReturnRDTO delete(PhysicalModelRDTO physicalModelRDTO) throws ImiException {
		try {

			returnRDTO = new ReturnRDTO();

			String url = urlApiInventary + pathPhysicalModel + pathPhysicalModelDelete;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			Map<String, String> urlParams = new HashMap<String, String>();
			urlParams.put("codi", physicalModelRDTO.getCode());

			ResponseEntity<Object> resp = restCall.executeDELETE(url, urlParams, physicalModelRDTO);

			returnRDTO = (ReturnRDTO) utils.rest2Object(resp, returnRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} 
		
		return returnRDTO;
	}

	@Override
	public ReturnPhysicalModelRDTO select(PhysicalModelRDTO physicalModelRDTO) throws ImiException {

		ReturnPhysicalModelRDTO returnPhysicalModelRDTO = new ReturnPhysicalModelRDTO();

		try {

			String url = urlApiInventary + pathPhysicalModel + pathPhysicalModelGet;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			Map<String, String> urlParams = new HashMap<String, String>();
			urlParams.put("codi", physicalModelRDTO.getCode());

			ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, physicalModelRDTO);
			
			returnPhysicalModelRDTO = (ReturnPhysicalModelRDTO) utils.rest2Object(resp, returnPhysicalModelRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} 
		
		return returnPhysicalModelRDTO;
	}

	@Override
	public ReturnRDTO insert(AggregateAmortizationRDTO aggregateAmortizationRDTO) throws ImiException {
		try {

			returnRDTO = new ReturnRDTO();
		
			String url = urlApiInventary + pathPhysicalModel + pathPhysicalModelType + pathPhysicalModelAggregateAmortization;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			ResponseEntity<Object> resp = restCall.executePOST(url, aggregateAmortizationRDTO);

			returnRDTO = (ReturnRDTO) utils.rest2Object(resp, returnRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} 
		
		return returnRDTO;
	}

	@Override
	public ReturnPhysicalModelMassiveRDTO selectMassive(QueryParameterRDTO queryParameterRDTO) throws ImiException {
		ReturnPhysicalModelMassiveRDTO returnMassiveRDTO = new ReturnPhysicalModelMassiveRDTO();

		try {

			String url = urlApiInventary + pathPhysicalModel + pathPhysicalModelGetMassive;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			Map<String, String> urlParams = new HashMap<String, String>();
			urlParams.put("codi", queryParameterRDTO.getCodeContract());
			urlParams.put("dataReferencia", queryParameterRDTO.getDateReference());
			urlParams.put("codiUsuari", queryParameterRDTO.getCodeUser());
			urlParams.put("transactionId", queryParameterRDTO.getTransactionId());

			ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterRDTO);

			returnMassiveRDTO = (ReturnPhysicalModelMassiveRDTO) utils.rest2Object(resp, returnMassiveRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnMassiveRDTO;
	}

	@Override
	public ReturnPhysicalModelDetailedRDTO selectDetailed(QueryParameterRDTO queryParameterRDTO) throws ImiException {
		ReturnPhysicalModelDetailedRDTO returnUbicationDetailedRDTO = new ReturnPhysicalModelDetailedRDTO();

		try {

			String url = urlApiInventary + pathPhysicalModel + pathPhysicalModelGetDetailed;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			Map<String, String> urlParams = new HashMap<String, String>();
			urlParams.put("codi", queryParameterRDTO.getCode());
			urlParams.put("codiContracta", queryParameterRDTO.getCodeContract());
			urlParams.put("dataReferencia", queryParameterRDTO.getDateReference());
			urlParams.put("codiUsuari", queryParameterRDTO.getCodeUser());
			urlParams.put("transactionId", queryParameterRDTO.getTransactionId());

			ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterRDTO);

			returnUbicationDetailedRDTO = (ReturnPhysicalModelDetailedRDTO) utils.rest2Object(resp, returnUbicationDetailedRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnUbicationDetailedRDTO;
	}

	@Override
	public ReturnPhysicalModelAmortizationRDTO selectAmortization(QueryParameterRDTO queryParameterRDTO) throws ImiException {
		ReturnPhysicalModelAmortizationRDTO returnPhysicalModelAmortizationRDTO = new ReturnPhysicalModelAmortizationRDTO();

		try {

			String url = urlApiInventary + pathPhysicalModel + pathPhysicalModelGetAmortization;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			Map<String, String> urlParams = new HashMap<String, String>();			
			urlParams.put("codi", queryParameterRDTO.getCodeContract());
			urlParams.put("codiFraccio", queryParameterRDTO.getCodeFraction());
			urlParams.put("codiTerritori", queryParameterRDTO.getCodeTerritory());
			urlParams.put("codiTipus", queryParameterRDTO.getCodeType());
			urlParams.put("codiGrup", queryParameterRDTO.getCodeGroup());
			urlParams.put("codiUsuari", queryParameterRDTO.getCodeUser());
			urlParams.put("transactionId", queryParameterRDTO.getTransactionId());

			ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterRDTO);

			returnPhysicalModelAmortizationRDTO = (ReturnPhysicalModelAmortizationRDTO) utils.rest2Object(resp, returnPhysicalModelAmortizationRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnPhysicalModelAmortizationRDTO;
	}


}
