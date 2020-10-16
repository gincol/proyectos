package es.bcn.imi.framework.vigia.frontal.web.rest.controller.execution.sync;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.bcn.imi.framework.vigia.frontal.business.execution.EventService;
import es.bcn.imi.framework.vigia.frontal.business.execution.TimeClockService;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.APSEventRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.AnnulmentEventRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.EventServiceRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.InformativeEventRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterEventRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterTimeRegisterRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.TimeClockInstantRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.TimeClockSummaryRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.UnloadingEventRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnEventApprovedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnEventDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnEventPendingRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnEventRejectedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnTimeRegisterInstantRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnTimeRegisterSummaryRDTO;
import es.bcn.vigia.fmw.libutils.Utils;
import es.bcn.vigia.fmw.libutils.enums.ReturnEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.opentrends.openframe.services.rest.controller.AbstractRestController;
import net.opentrends.openframe.services.rest.http.ResponseEntity;

@RestController
@RequestMapping("/sync/execucio")
@Lazy(true)
@Api("FRONTAL API")
public class ExecutionController extends AbstractRestController {

	private Log logger = LogFactory.getLog(this.getClass());

	@Autowired(required = true)
	@Qualifier(ServicesConstants.SRV_FRONTAL_EVENT)
	private EventService eventService;

	@Autowired(required = true)
	@Qualifier(ServicesConstants.SRV_FRONTAL_TIME_CLOCK)
	private TimeClockService timeClockService;


	private ReturnRDTO returnRDTO;

	private QueryParameterTimeRegisterRDTO queryParameterTimeRegisterRDTO;	

	private QueryParameterEventRDTO queryParameterEventRDTO;	

	private ResponseEntity<ReturnRDTO> processReturnRDTO(ReturnRDTO returnRDTO)
	{
		if (!returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_REGISTERED.getCodeDescription())) {

			logger.error(String.format(LogsConstants.LOG_HTTP_BAD_REQUEST, returnRDTO.toString()));

			return new ResponseEntity<>(returnRDTO, HttpStatus.BAD_REQUEST);
		}else{
			logger.info(LogsConstants.LOG_END);
			return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/esdeveniment/servei", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Interfície d'alta de Esdeveniment de Servei", tags = { "esdeveniment de servei" })
	public ResponseEntity<ReturnRDTO> saveServiceEvent(@RequestBody EventServiceRDTO eventRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();
		
		try {
			eventRDTO.setClientId(clientId);
			returnRDTO = eventService.insertService(eventRDTO);
			
			return processReturnRDTO(returnRDTO); 
				
		} catch (Exception ex) {
		
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, eventRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO,ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	@RequestMapping(value = "/esdeveniment/informatiu", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Interfície d'alta de Esdeveniment informatiu", tags = { "esdeveniment informatiu" })
	public ResponseEntity<ReturnRDTO> saveInformativeEvent(@RequestBody InformativeEventRDTO eventRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();
		
		try {
			eventRDTO.setClientId(clientId);
			returnRDTO = eventService.insertInformative(eventRDTO);
			
			return processReturnRDTO(returnRDTO);
			
		} catch (Exception ex) {
		
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, eventRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO,ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}


	@RequestMapping(value = "/esdeveniment/aps", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Interfície d'alta de Esdeveniment APS", tags = { "esdeveniment APS" })
	public ResponseEntity<ReturnRDTO> saveAPSEvent(@RequestBody APSEventRDTO eventRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();
		
		try {
			eventRDTO.setClientId(clientId);
			returnRDTO = eventService.insertAPS(eventRDTO);
			
			return processReturnRDTO(returnRDTO);
			
		} catch (Exception ex) {
		
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, eventRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO,ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@RequestMapping(value = "/esdeveniment/descarrega", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Interfície d'alta de Esdeveniment Descàrrega", tags = { "esdeveniment Descàrrega" })
	public ResponseEntity<ReturnRDTO> saveUnloadingEvent(@RequestBody UnloadingEventRDTO eventRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();
		
		try {
			eventRDTO.setClientId(clientId);
			returnRDTO = eventService.insertUnloading(eventRDTO);
			
			return processReturnRDTO(returnRDTO);
			
		} catch (Exception ex) {
		
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, eventRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO,ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@RequestMapping(value = "/esdeveniment/anulacio", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Interfície d'alta de Esdeveniment d’Anul·lació", tags = { "esdeveniment d’Anul·lació" })
	public ResponseEntity<ReturnRDTO> saveAnulmentEvent(@RequestBody AnnulmentEventRDTO eventRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();
		
		try {
			eventRDTO.setClientId(clientId);
			returnRDTO = eventService.insertAnnulment(eventRDTO);
			
			return processReturnRDTO(returnRDTO); 
				
		} catch (Exception ex) {
		
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, eventRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO,ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@RequestMapping(value = "/fitxatge/instantani", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Interfície d’alta de Fitxatge Instantatni", tags = { "fitxatge" })
	public ResponseEntity<ReturnRDTO> saveTimeClockInstant(@RequestBody TimeClockInstantRDTO timeClockInstantRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {
			timeClockInstantRDTO.setClientId(clientId);
			returnRDTO = timeClockService.insertInstant(timeClockInstantRDTO);
			
			return processReturnRDTO(returnRDTO);
			
		} catch (Exception ex) {
		
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, timeClockInstantRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO,ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	
	}
	
	@RequestMapping(value = "/fitxatge/resum", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Interfície d’alta de Resum de Fitxatge", tags = { "fitxatge" })
	public ResponseEntity<ReturnRDTO> saveTimeClockSummary(@RequestBody TimeClockSummaryRDTO timeClockSummaryRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();
		
		try {
			timeClockSummaryRDTO.setClientId(clientId);
			returnRDTO = timeClockService.insertSummary(timeClockSummaryRDTO);
			
			return processReturnRDTO(returnRDTO);
			
		} catch (Exception ex) {
		
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, timeClockSummaryRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO,ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@RequestMapping(value = "/contracta/{codi}/fitxatge/instantani", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta de Fitxatges instantani", tags = { "fitxatge" })
	public ResponseEntity<ReturnTimeRegisterInstantRDTO> selectTimeRegisterInstant(
			@PathVariable(value = "codi", required = true) String codeContract,
			@RequestParam(value = "data", required = false) String date,
			@RequestParam(value = "codiRellotge", required = false) String codeWatch,
			@RequestParam(value = "codiTreballador", required = false) String codeEmployee,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);		
		ReturnTimeRegisterInstantRDTO returnTimeRegisterInstantRDTO = new ReturnTimeRegisterInstantRDTO();
		queryParameterTimeRegisterRDTO = new QueryParameterTimeRegisterRDTO();

		try {
			queryParameterTimeRegisterRDTO.setClientId(clientId);
			queryParameterTimeRegisterRDTO.setCodeContract(codeContract);	
			queryParameterTimeRegisterRDTO.setDate(date);
			queryParameterTimeRegisterRDTO.setCodeWatch(codeWatch);
			queryParameterTimeRegisterRDTO.setCodeEmployee(codeEmployee);
			
			returnTimeRegisterInstantRDTO = timeClockService.selectTimeRegisterInstant(queryParameterTimeRegisterRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnTimeRegisterInstantRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO,ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			returnTimeRegisterInstantRDTO.setReturnRDTO(returnRDTO);
			return new ResponseEntity<>(returnTimeRegisterInstantRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnTimeRegisterInstantRDTO, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/contracta/{codi}/fitxatge/resum", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta de Resums de Fitxatges", tags = { "fitxatge" })
	public ResponseEntity<ReturnTimeRegisterSummaryRDTO> selectTimeRegisterSummary(
			@PathVariable(value = "codi", required = true) String codeContract,
			@RequestParam(value = "data", required = false) String date,
			@RequestParam(value = "codiInstallacio", required = false) String codeInstallation,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);		
		ReturnTimeRegisterSummaryRDTO returnTimeRegisterSummaryRDTO = new ReturnTimeRegisterSummaryRDTO();
		queryParameterTimeRegisterRDTO = new QueryParameterTimeRegisterRDTO();

		try {
			queryParameterTimeRegisterRDTO.setClientId(clientId);
			queryParameterTimeRegisterRDTO.setCodeContract(codeContract);	
			queryParameterTimeRegisterRDTO.setDate(date);
			queryParameterTimeRegisterRDTO.setCodeInstallation(codeInstallation);
			
			returnTimeRegisterSummaryRDTO = timeClockService.selectTimeRegisterSummary(queryParameterTimeRegisterRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnTimeRegisterSummaryRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO,ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			returnTimeRegisterSummaryRDTO.setReturnRDTO(returnRDTO);
			return new ResponseEntity<>(returnTimeRegisterSummaryRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnTimeRegisterSummaryRDTO, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/contracta/{codi}/esdeveniment/{codiEsdeveniment}/detall", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta Detall Esdeveniment", tags = { "esdeveniment" })
	public ResponseEntity<ReturnEventDetailedRDTO> selectEventDetailed(
			@PathVariable(value = "codiEsdeveniment", required = true) String code,
			@PathVariable(value = "codi", required = true) String codeContract,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);		
		ReturnEventDetailedRDTO returnEventDetailedRDTO = new ReturnEventDetailedRDTO();
		queryParameterEventRDTO = new QueryParameterEventRDTO();

		try {
			queryParameterEventRDTO.setClientId(clientId);
			queryParameterEventRDTO.setCode(code);
			queryParameterEventRDTO.setCodeContract(codeContract);
			queryParameterEventRDTO.setCodeUser(codeUser);
			
			returnEventDetailedRDTO = eventService.selectDetailed(queryParameterEventRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnEventDetailedRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO,ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			returnEventDetailedRDTO.setReturnRDTO(returnRDTO);
			return new ResponseEntity<>(returnEventDetailedRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnEventDetailedRDTO, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/contracta/{codi}/esdeveniment/aprovats", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta de Esdeveniments aprovats en una data", tags = { "esdeveniment" })
	public ResponseEntity<ReturnEventApprovedRDTO> selectEventApproved(
			@PathVariable(value = "codi", required = true) String codeContract,
			@RequestParam(value = "dataExecucio", required = false) String dateExecution,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);		
		ReturnEventApprovedRDTO returnEventApprovedRDTO = new ReturnEventApprovedRDTO();
		queryParameterEventRDTO = new QueryParameterEventRDTO();

		try {
			queryParameterEventRDTO.setClientId(clientId);
			queryParameterEventRDTO.setCodeContract(codeContract);
			queryParameterEventRDTO.setDateExecution(dateExecution);
			queryParameterEventRDTO.setCodeUser(codeUser);
			
			returnEventApprovedRDTO = eventService.selectApproved(queryParameterEventRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnEventApprovedRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO,ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			returnEventApprovedRDTO.setReturnRDTO(returnRDTO);
			return new ResponseEntity<>(returnEventApprovedRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnEventApprovedRDTO, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/contracta/{codi}/esdeveniment/rebutjats", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta de Esdeveniments rebutjats en una data", tags = { "esdeveniment" })
	public ResponseEntity<ReturnEventRejectedRDTO> selectEventRejected(
			@PathVariable(value = "codi", required = true) String codeContract,
			@RequestParam(value = "dataExecucio", required = false) String dateExecution,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);		
		ReturnEventRejectedRDTO returnEventRejectedRDTO = new ReturnEventRejectedRDTO();
		queryParameterEventRDTO = new QueryParameterEventRDTO();

		try {
			queryParameterEventRDTO.setClientId(clientId);
			queryParameterEventRDTO.setCodeContract(codeContract);
			queryParameterEventRDTO.setDateExecution(dateExecution);
			queryParameterEventRDTO.setCodeUser(codeUser);
			
			returnEventRejectedRDTO = eventService.selectRejected(queryParameterEventRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnEventRejectedRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO,ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			returnEventRejectedRDTO.setReturnRDTO(returnRDTO);
			return new ResponseEntity<>(returnEventRejectedRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnEventRejectedRDTO, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/contracta/{codi}/esdeveniment/pendents", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta de Esdeveniments pendens en un rang de dates", tags = { "esdeveniment" })
	public ResponseEntity<ReturnEventPendingRDTO> selectEventPending(
			@PathVariable(value = "codi", required = true) String codeContract,
			@RequestParam(value = "dataInici", required = false) String dateStart,
			@RequestParam(value = "dataFi", required = false) String dateEnd,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);		
		ReturnEventPendingRDTO returnEventPendingRDTO = new ReturnEventPendingRDTO();
		queryParameterEventRDTO = new QueryParameterEventRDTO();

		try {
			queryParameterEventRDTO.setClientId(clientId);
			queryParameterEventRDTO.setCodeContract(codeContract);
			queryParameterEventRDTO.setDateStart(dateStart);
			queryParameterEventRDTO.setDateEnd(dateEnd);
			queryParameterEventRDTO.setCodeUser(codeUser);
			
			returnEventPendingRDTO = eventService.selectPending(queryParameterEventRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnEventPendingRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO,ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			returnEventPendingRDTO.setReturnRDTO(returnRDTO);
			return new ResponseEntity<>(returnEventPendingRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnEventPendingRDTO, HttpStatus.OK);
	}
}