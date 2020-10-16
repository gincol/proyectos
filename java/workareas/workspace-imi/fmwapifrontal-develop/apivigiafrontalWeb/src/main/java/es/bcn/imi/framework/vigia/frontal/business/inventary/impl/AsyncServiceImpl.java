package es.bcn.imi.framework.vigia.frontal.business.inventary.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

import es.bcn.imi.framework.vigia.frontal.business.inventary.AsyncService;
import es.bcn.imi.framework.vigia.frontal.validator.ValidatorUbication;
import es.bcn.vigia.fmw.libcommons.business.dto.StatesBDTO;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.MessageContansts;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.AsyncRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterStatesRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.StatesRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnStatesRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnsRDTO;
import es.bcn.vigia.fmw.libutils.convert.StatesConvert;
import es.bcn.vigia.fmw.libutils.enums.ReturnEnum;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import es.bcn.vigia.fmw.libutils.services.Utils;
import es.bcn.vigia.fmw.libutils.services.rest.RestCall;
import es.bcn.vigia.fmw.libutils.validator.front.FrontValidateUbicationService;

@Lazy
@Service(ServicesConstants.SRV_FRONTAL_ASYNC)
public class AsyncServiceImpl implements AsyncService {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ServicesConstants.SRV_REST_CALL)
	private RestCall restCall;

	@Autowired
	@Qualifier(ServicesConstants.SRV_UTILS)
	private Utils utils;

	@Autowired
	@Qualifier(ServicesConstants.SRV_FRONT_VALIDATE_UBICATION)
	private FrontValidateUbicationService frontValidateUbicationService;

	@Autowired
	@Qualifier(ServicesConstants.SRV_FRONT_VALIDATE_UBICATION_FMW)
	private ValidatorUbication validator;

	@Value("${url.api.fmw.nodejs}")
	private String urlApiNodeJS;

	@Value("${url.path.states}")
	private String pathStates;

	@Value("${url.path.states.getTransactionId}")
	private String pathStatesGetTransactionId;

	@Value("${url.path.states.getSubtransactionId}")
	private String pathStatesGetSubtransactionId;

	@Value("${url.api.orquestrador}")
	private String urlApiOrquestrador;

	@Value("${url.path.inventary}")
	private String pathInventary;

	@Value("${url.path.ubications}")
	private String pathUbications;

	private ReturnRDTO returnRDTO;

	private ReturnsRDTO returnsRDTO;

	private String url;

	private void processResponse(ResponseEntity<Object> resp) throws ImiException

	{
		returnRDTO = (ReturnRDTO) utils.rest2Object(resp, returnRDTO);

		logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

	}

	private ReturnRDTO getTransactionId(AsyncRDTO rdto) {

		returnRDTO.setTransactionId(rdto.getTransactionId());

		return returnRDTO;
	}

	@Override
	public ReturnStatesRDTO selectStates(QueryParameterStatesRDTO queryParameterStatesRDTO) throws ImiException {

		ReturnStatesRDTO returnStatesRDTO = new ReturnStatesRDTO();
		List<StatesBDTO> statesBDTOs = new ArrayList<>();
		List<StatesRDTO> statesRDTOs = new ArrayList<>();
		returnRDTO = new ReturnRDTO();

		try {
			queryParameterStatesRDTO.generateTransactionId();

			url = urlApiNodeJS.concat(pathStates).concat(pathStatesGetTransactionId);

			if (queryParameterStatesRDTO.getSubtransactionId() != null
					&& !queryParameterStatesRDTO.getSubtransactionId().isEmpty()) {
				url = url.concat(pathStatesGetSubtransactionId);
			}

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			Map<String, String> urlParams = new HashMap<>();
			urlParams.put("idTransaccio", queryParameterStatesRDTO.getIdTransaction());
			urlParams.put("idSubtransaccio", queryParameterStatesRDTO.getSubtransactionId());

			ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterStatesRDTO);

			statesBDTOs = (List<StatesBDTO>) utils.rest2States(resp);
			statesRDTOs = StatesConvert.bdto2rdto(statesBDTOs);
			returnStatesRDTO.setStatesRDTOs(statesRDTOs);
			
			returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_READ.getCodeDescription());
			returnRDTO.setMessage(ReturnEnum.CODE_SUCCESS_READ.getMessage());	
			
			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			returnRDTO.setCode(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription());
			returnRDTO.setMessage(String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(),
					MessageContansts.MSG_ENTITY_STATES,
					queryParameterStatesRDTO.getIdTransaction().concat(queryParameterStatesRDTO.getSubtransactionId()!=null?ImiConstants.SLASH.concat(queryParameterStatesRDTO.getSubtransactionId()):"")));
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}

		returnStatesRDTO.setReturnRDTO(returnRDTO);
		returnStatesRDTO.getReturnRDTO().setTransactionId(queryParameterStatesRDTO.getTransactionId());

		return returnStatesRDTO;
	}

	@Override
	public ReturnRDTO redirect(AsyncRDTO<?> rdto, String type, HttpServletRequest request) throws ImiException {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {

			rdto.generateTransactionId();

			url = urlApiOrquestrador.concat(ImiConstants.SLASH).concat(ImiConstants.TYPE_REQUEST_ASYNCHRONOUS)
					.concat(pathInventary).concat(ImiConstants.SLASH).concat(type);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			Map<String, String> urlParams = new HashMap<>();

			ResponseEntity<Object> resp = null;
			if (request.getMethod().equals(String.valueOf(RequestMethod.POST))) {
				resp = restCall.executePOST(url, rdto);
			} else if (request.getMethod().equals(String.valueOf(RequestMethod.PUT))) {
				resp = restCall.executePUT(url, urlParams, rdto);
			} else if (request.getMethod().equals(String.valueOf(RequestMethod.DELETE))) {
				resp = restCall.executeDELETE(url, urlParams, rdto);
			}

			processResponse(resp);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_ACCESSING_POST_URL, url));
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}

		returnRDTO = getTransactionId(rdto);

		return returnRDTO;
	}
}