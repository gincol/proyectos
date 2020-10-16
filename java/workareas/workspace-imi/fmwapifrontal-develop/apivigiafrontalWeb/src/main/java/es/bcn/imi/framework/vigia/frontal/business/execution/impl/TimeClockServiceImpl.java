package es.bcn.imi.framework.vigia.frontal.business.execution.impl;

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

import es.bcn.imi.framework.vigia.frontal.business.execution.TimeClockService;
import es.bcn.imi.framework.vigia.frontal.validator.ValidatorTimeClockSummary;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterTimeRegisterRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.TimeClockInstantRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.TimeClockSummaryRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.commons.BaseTransactionIdRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnTimeRegisterInstantRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnTimeRegisterSummaryRDTO;
import es.bcn.vigia.fmw.libutils.enums.ReturnEnum;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import es.bcn.vigia.fmw.libutils.services.Utils;
import es.bcn.vigia.fmw.libutils.services.rest.RestCall;
import es.bcn.vigia.fmw.libutils.validator.front.FrontValidateTimeClockInstantService;
import es.bcn.vigia.fmw.libutils.validator.front.FrontValidateTimeClockSummaryService;

@Lazy
@Service(ServicesConstants.SRV_FRONTAL_TIME_CLOCK)
public class TimeClockServiceImpl implements TimeClockService {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ServicesConstants.SRV_REST_CALL)
	private RestCall restCall;

	@Autowired
	@Qualifier(ServicesConstants.SRV_UTILS)
	private Utils utils;
	
	@Autowired
	@Qualifier(ServicesConstants.SRV_FRONT_VALIDATE_TIME_CLOCK_INSTANT)
	private FrontValidateTimeClockInstantService frontValidateTimeClockInstantService;
	
	@Autowired
	@Qualifier(ServicesConstants.SRV_FRONT_VALIDATE_TIME_CLOCK_SUMMARY)
	private FrontValidateTimeClockSummaryService frontValidateTimeClockSummaryService;
	
	@Autowired
	@Qualifier(ServicesConstants.SRV_FRONT_VALIDATE_TIME_CLOCK_SUMMARY_FMW)
	private ValidatorTimeClockSummary validator;	
	
	@Value("${url.api.orquestrador}")
	private String urlApiOrquestrador;

	@Value("${url.path.execution}")
	private String pathExecution;

	@Value("${url.path.time.clock.instant}")
	private String pathTimeClockInstant;

	@Value("${url.path.time.clock.instantGet}")
	private String pathTimeClockInstantGet;

	@Value("${url.path.time.clock.summary}")
	private String pathTimeClockSummary;

	@Value("${url.path.time.clock.summaryGet}")
	private String pathTimeClockSummaryGet;

	
	private ReturnRDTO returnRDTO;
	
	private String url;
	
	
	private void processResponse(ResponseEntity<Object> resp) throws ImiException

	{
		returnRDTO = (ReturnRDTO) utils.rest2Object(resp, returnRDTO);

		logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

	}

	private ReturnRDTO getTransactionId(BaseTransactionIdRDTO timeClockInstantRDTO) {

		returnRDTO.setTransactionId(timeClockInstantRDTO.getTransactionId());

		return returnRDTO;
	}

	@Override
	public ReturnRDTO insertInstant(TimeClockInstantRDTO timeClockInstantRDTO) throws ImiException {
	
			logger.info(LogsConstants.LOG_START);
			
			try {
				
				timeClockInstantRDTO.generateTransactionId();

				returnRDTO = validator.validateContract(timeClockInstantRDTO);
				
				if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
					returnRDTO = frontValidateTimeClockInstantService.validateSyntaxInsert(timeClockInstantRDTO);
				}
				
				if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
					
					url = urlApiOrquestrador
							.concat(ImiConstants.SLASH)
							.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
							.concat(pathExecution)
							.concat(pathTimeClockInstant);

					logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));
					
					ResponseEntity<Object> resp = restCall.executePOST(url, timeClockInstantRDTO);

					processResponse(resp);
				}
				

			} catch (Exception ex) {
				logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
				throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
			} finally {
				logger.debug(LogsConstants.LOG_END);
			}
			
			returnRDTO = getTransactionId(timeClockInstantRDTO);
			
			return returnRDTO;
	}

	@Override
	public ReturnRDTO insertSummary(TimeClockSummaryRDTO timeClockSummaryRDTO) throws ImiException {
		
		logger.info(LogsConstants.LOG_START);
		
		try {
			
			timeClockSummaryRDTO.generateTransactionId();

			returnRDTO = validator.validateContract(timeClockSummaryRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateTimeClockSummaryService.validateSyntaxInsert(timeClockSummaryRDTO);
			}
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
				 
				returnRDTO = validator.validateInsert(timeClockSummaryRDTO);
			}
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
				
				url = urlApiOrquestrador
						.concat(ImiConstants.SLASH)
						.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
						.concat(pathExecution)
						.concat(pathTimeClockSummary);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));
				
				ResponseEntity<Object> resp = restCall.executePOST(url, timeClockSummaryRDTO);

				processResponse(resp);
			}
			

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}
		
		returnRDTO = getTransactionId(timeClockSummaryRDTO);
		
		return returnRDTO;
	}

	@Override
	public ReturnTimeRegisterInstantRDTO selectTimeRegisterInstant(QueryParameterTimeRegisterRDTO queryParameterRDTO)
			throws ImiException {

		ReturnTimeRegisterInstantRDTO returnTimeRegisterInstantRDTO = new ReturnTimeRegisterInstantRDTO();

		try {
			queryParameterRDTO.generateTransactionId();

			returnRDTO = validator.validateContract(queryParameterRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateTimeClockInstantService.validateSyntaxSelectTimeRegisterInstant(queryParameterRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH)
						.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
						.concat(pathExecution)
						.concat(pathTimeClockInstant)
						.concat(pathTimeClockInstantGet);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				Map<String, String> urlParams = new HashMap<String, String>();

				urlParams.put("codi", queryParameterRDTO.getCodeContract());
				urlParams.put("data", queryParameterRDTO.getDate());
				urlParams.put("codiRellotge", queryParameterRDTO.getCodeWatch());
				urlParams.put("codiTreballador", queryParameterRDTO.getCodeEmployee());
				urlParams.put("transactionId", queryParameterRDTO.getTransactionId());

				ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterRDTO);

				returnTimeRegisterInstantRDTO = (ReturnTimeRegisterInstantRDTO) utils.rest2Object(resp,
						returnTimeRegisterInstantRDTO);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));
			} else {
				returnTimeRegisterInstantRDTO.setReturnRDTO(returnRDTO);
			}

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}

		returnTimeRegisterInstantRDTO.getReturnRDTO().setTransactionId(queryParameterRDTO.getTransactionId());

		return returnTimeRegisterInstantRDTO;
	}

	@Override
	public ReturnTimeRegisterSummaryRDTO selectTimeRegisterSummary(QueryParameterTimeRegisterRDTO queryParameterRDTO)
			throws ImiException {

		ReturnTimeRegisterSummaryRDTO returnTimeRegisterSummaryRDTO = new ReturnTimeRegisterSummaryRDTO();

		try {
			queryParameterRDTO.generateTransactionId();

			returnRDTO = validator.validateContract(queryParameterRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateTimeClockSummaryService.validateSyntaxSelectTimeRegisterSummary(queryParameterRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH)
						.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
						.concat(pathExecution)
						.concat(pathTimeClockSummary)
						.concat(pathTimeClockSummaryGet);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				Map<String, String> urlParams = new HashMap<String, String>();

				urlParams.put("codi", queryParameterRDTO.getCodeContract());
				urlParams.put("data", queryParameterRDTO.getDate());
				urlParams.put("codiInstallacio", queryParameterRDTO.getCodeInstallation());
				urlParams.put("transactionId", queryParameterRDTO.getTransactionId());

				ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterRDTO);

				returnTimeRegisterSummaryRDTO = (ReturnTimeRegisterSummaryRDTO) utils.rest2Object(resp,
						returnTimeRegisterSummaryRDTO);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));
			} else {
				returnTimeRegisterSummaryRDTO.setReturnRDTO(returnRDTO);
			}

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}

		returnTimeRegisterSummaryRDTO.getReturnRDTO().setTransactionId(queryParameterRDTO.getTransactionId());

		return returnTimeRegisterSummaryRDTO;
	}

}