package es.bcn.imi.framework.vigia.frontal.business.inventary.impl;

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

import es.bcn.imi.framework.vigia.frontal.business.inventary.ActuationService;
import es.bcn.imi.framework.vigia.frontal.validator.ValidatorActuation;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterActuationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnActuationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.enums.ReturnEnum;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import es.bcn.vigia.fmw.libutils.services.Utils;
import es.bcn.vigia.fmw.libutils.services.rest.RestCall;
import es.bcn.vigia.fmw.libutils.validator.front.FrontValidateActuationService;

@Lazy
@Service(ServicesConstants.SRV_FRONTAL_ACTUATION)
public class ActuationServiceImpl implements ActuationService {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ServicesConstants.SRV_REST_CALL)
	private RestCall restCall;

	@Autowired
	@Qualifier(ServicesConstants.SRV_UTILS)
	private Utils utils;

	@Autowired
	@Qualifier(ServicesConstants.SRV_FRONT_VALIDATE_ACTUATION)
	private FrontValidateActuationService frontValidateActuationService;
	
	@Autowired
	@Qualifier(ServicesConstants.SRV_FRONT_VALIDATE_ACTUATION_FMW)
	private ValidatorActuation validator;

	@Value("${url.api.orquestrador}")
	private String urlApiOrquestrador;

	@Value("${url.path.inventary}")
	private String pathInventary;

	@Value("${url.path.actuation}")
	private String pathActuation;

	@Value("${url.path.actuation.get}")
	private String pathActuationGet;

	private ReturnRDTO returnRDTO;

	private String url;
	
	@Override
	public ReturnActuationRDTO selectActuations(QueryParameterActuationRDTO queryParameterActuationRDTO) throws ImiException {
		ReturnActuationRDTO returnActuationRDTO = new ReturnActuationRDTO();

		try {
			queryParameterActuationRDTO.generateTransactionId();

			returnRDTO = validator.validateContract(queryParameterActuationRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateActuationService.validateSyntaxSelectActuation(queryParameterActuationRDTO);
			}
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH)
						.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
						.concat(pathInventary)
						.concat(pathActuation)
						.concat(pathActuationGet);
				
				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				Map<String, String> urlParams = new HashMap<>();
				
				urlParams.put("codi", queryParameterActuationRDTO.getCodeContract());
				urlParams.put("tipusEntitat", queryParameterActuationRDTO.getCodeTypeEntity());
				urlParams.put("codiEntitat", queryParameterActuationRDTO.getCodeEntity());
				urlParams.put("considerarAnulacions", queryParameterActuationRDTO.getConsiderAnnulments());
				urlParams.put("codiUsuari", queryParameterActuationRDTO.getCodeUser());
				urlParams.put("transactionId", queryParameterActuationRDTO.getTransactionId());
				
				ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterActuationRDTO);
				
				returnActuationRDTO =  (ReturnActuationRDTO) utils.rest2Object(resp, returnActuationRDTO);
								
				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));
			} else {
				returnActuationRDTO.setReturnRDTO(returnRDTO);
			}

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}
		
		returnActuationRDTO.getReturnRDTO().setTransactionId(queryParameterActuationRDTO.getTransactionId());
		
		return returnActuationRDTO;

	}

}