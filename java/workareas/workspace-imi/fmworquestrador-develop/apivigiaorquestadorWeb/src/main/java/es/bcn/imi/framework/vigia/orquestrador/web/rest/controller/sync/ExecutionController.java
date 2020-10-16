package es.bcn.imi.framework.vigia.orquestrador.web.rest.controller.sync;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.bcn.imi.framework.vigia.orquestrador.business.execution.EventService;
import es.bcn.imi.framework.vigia.orquestrador.business.execution.TimeClockService;
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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.opentrends.openframe.services.rest.controller.AbstractRestController;
import net.opentrends.openframe.services.rest.http.ResponseEntity;

@RestController
@RequestMapping("/sync/execucio")
@Lazy(true)
@Api("ORQUESTRATOR API")
public class ExecutionController extends AbstractRestController{
	
	private Log logger = LogFactory.getLog(this.getClass());

	@Autowired(required = true)
	@Qualifier(ServicesConstants.SRV_EXECUTION_EVENT)
	private EventService eventService;
	
	@Autowired(required = true)
	@Qualifier(ServicesConstants.SRV_EXECUTION_TIME_CLOCK)
	private TimeClockService timeClockService;
	
	private ReturnRDTO returnRDTO;

	private QueryParameterTimeRegisterRDTO queryParameterTimeRegisterRDTO;

	private QueryParameterEventRDTO queryParameterEventRDTO;
	
	@RequestMapping(value = "/esdeveniment/servei", method = { RequestMethod.POST })
	@ResponseBody
	@ApiOperation(value = "Interfície Sync for POST", tags = { "orchestrator" })
	public ResponseEntity<ReturnRDTO> eventServiceSync(@RequestBody EventServiceRDTO eventServiceRDTO) {

		logger.info(LogsConstants.LOG_START);

		try {
			
			returnRDTO = eventService.insertService(eventServiceRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, eventServiceRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/esdeveniment/informatiu", method = { RequestMethod.POST })
	@ResponseBody
	@ApiOperation(value = "Interfície Sync for POST", tags = { "orchestrator" })
	public ResponseEntity<ReturnRDTO> eventInformativeSync(@RequestBody InformativeEventRDTO informativeEventRDTO) {

		logger.info(LogsConstants.LOG_START);

		try {
			
			returnRDTO = eventService.insertInformative(informativeEventRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, informativeEventRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/esdeveniment/aps", method = { RequestMethod.POST })
	@ResponseBody
	@ApiOperation(value = "Interfície Sync for POST", tags = { "orchestrator" })
	public ResponseEntity<ReturnRDTO> eventApsSync(@RequestBody APSEventRDTO apsEventRDTO) {

		logger.info(LogsConstants.LOG_START);

		try {
			
			returnRDTO = eventService.insertAps(apsEventRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, apsEventRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/esdeveniment/descarrega", method = { RequestMethod.POST })
	@ResponseBody
	@ApiOperation(value = "Interfície Sync for POST", tags = { "orchestrator" })
	public ResponseEntity<ReturnRDTO> eventUnloadingSync(@RequestBody UnloadingEventRDTO unloadingEventRDTO) {

		logger.info(LogsConstants.LOG_START);

		try {
			
			returnRDTO = eventService.insertUnloading(unloadingEventRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, unloadingEventRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/esdeveniment/anullacio", method = { RequestMethod.POST })
	@ResponseBody
	@ApiOperation(value = "Interfície Sync for POST", tags = { "orchestrator" })
	public ResponseEntity<ReturnRDTO> eventAnnulmentSync(@RequestBody AnnulmentEventRDTO annulmentEventRDTO) {

		logger.info(LogsConstants.LOG_START);

		try {
			
			returnRDTO = eventService.insertAnnulment(annulmentEventRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, annulmentEventRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/fitxatge/instantani", method = { RequestMethod.POST })
	@ResponseBody
	@ApiOperation(value = "Interfície Sync for POST", tags = { "orchestrator" })
	public ResponseEntity<ReturnRDTO> timeClockInstantSync(@RequestBody TimeClockInstantRDTO timeClockInstantRDTO) {

		logger.info(LogsConstants.LOG_START);

		try {
			
			returnRDTO = timeClockService.insertInstant(timeClockInstantRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, timeClockInstantRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/fitxatge/resum", method = { RequestMethod.POST })
	@ResponseBody
	@ApiOperation(value = "Interfície Sync for POST", tags = { "orchestrator" })
	public ResponseEntity<ReturnRDTO> timeClockSummarySync(@RequestBody TimeClockSummaryRDTO timeClockSummaryRDTO) {

		logger.info(LogsConstants.LOG_START);

		try {
			
			returnRDTO = timeClockService.insertSummary(timeClockSummaryRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, timeClockSummaryRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/fitxatge/instantani/contracta/{codi}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície Sync only GET", tags = { "orchestrator" })
	public ResponseEntity<ReturnTimeRegisterInstantRDTO> timeRegisterInstantSyncGET(
			@PathVariable(value = "codi") String codeContract,
			@RequestParam(value = "data", required = false) String date,
			@RequestParam(value = "codiRellotge", required = false) String codeWatch,
			@RequestParam(value = "codiTreballador", required = false) String codeEmployee,
			@RequestParam(required = false) String transactionId) {

		logger.info(LogsConstants.LOG_START);
		ReturnTimeRegisterInstantRDTO returnTimeRegisterInstantRDTO = new ReturnTimeRegisterInstantRDTO();
		queryParameterTimeRegisterRDTO = new QueryParameterTimeRegisterRDTO();

		try {
			queryParameterTimeRegisterRDTO.setCodeContract(codeContract);	
			queryParameterTimeRegisterRDTO.setDate(date);
			queryParameterTimeRegisterRDTO.setCodeWatch(codeWatch);
			queryParameterTimeRegisterRDTO.setCodeEmployee(codeEmployee);
			queryParameterTimeRegisterRDTO.setTransactionId(transactionId);
			
			returnTimeRegisterInstantRDTO = timeClockService.selectTimeRegisterInstant(queryParameterTimeRegisterRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, ex));
			return new ResponseEntity<>(returnTimeRegisterInstantRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnTimeRegisterInstantRDTO, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/fitxatge/resum/contracta/{codi}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície Sync only GET", tags = { "orchestrator" })
	public ResponseEntity<ReturnTimeRegisterSummaryRDTO> timeRegisterSummarySyncGET(
			@PathVariable(value = "codi", required = true) String codeContract,
			@RequestParam(value = "data", required = false) String date,
			@RequestParam(value = "codiInstallacio", required = false) String codeInstallation,
			@RequestParam(required = false) String transactionId) {

		logger.info(LogsConstants.LOG_START);
		ReturnTimeRegisterSummaryRDTO returnTimeRegisterSummaryRDTO = new ReturnTimeRegisterSummaryRDTO();
		queryParameterTimeRegisterRDTO = new QueryParameterTimeRegisterRDTO();

		try {
			queryParameterTimeRegisterRDTO.setCodeContract(codeContract);	
			queryParameterTimeRegisterRDTO.setDate(date);
			queryParameterTimeRegisterRDTO.setCodeInstallation(codeInstallation);
			queryParameterTimeRegisterRDTO.setTransactionId(transactionId);
			
			returnTimeRegisterSummaryRDTO = timeClockService.selectTimeRegisterSummary(queryParameterTimeRegisterRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, ex));
			return new ResponseEntity<>(returnTimeRegisterSummaryRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnTimeRegisterSummaryRDTO, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/esdeveniment/{codi}/contracta/{codiContracta}/detall", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície Sync only GET", tags = { "orchestrator" })
	public ResponseEntity<ReturnEventDetailedRDTO> eventDetailedSyncGET(
			@PathVariable(value = "codi") String code,
			@PathVariable(value = "codiContracta") String codeContract,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestParam(required = false) String transactionId) {

		logger.info(LogsConstants.LOG_START);
		ReturnEventDetailedRDTO returnEventDetailedRDTO = new ReturnEventDetailedRDTO();
		queryParameterEventRDTO = new QueryParameterEventRDTO();

		try {
			queryParameterEventRDTO.setCode(code);
			queryParameterEventRDTO.setCodeContract(codeContract);
			queryParameterEventRDTO.setCodeUser(codeUser);
			queryParameterEventRDTO.setTransactionId(transactionId);
			
			returnEventDetailedRDTO = eventService.selectDetailed(queryParameterEventRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, ex));
			return new ResponseEntity<>(returnEventDetailedRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnEventDetailedRDTO, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/esdeveniment/contracta/{codi}/aprovats", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície Sync only GET", tags = { "orchestrator" })
	public ResponseEntity<ReturnEventApprovedRDTO> eventApprovedSyncGET(
			@PathVariable(value = "codi") String codeContract,
			@RequestParam(value = "dataExecucio", required = false) String dateExecution,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestParam(required = false) String transactionId) {

		logger.info(LogsConstants.LOG_START);
		ReturnEventApprovedRDTO returnEventApprovedRDTO = new ReturnEventApprovedRDTO();
		queryParameterEventRDTO = new QueryParameterEventRDTO();

		try {
			queryParameterEventRDTO.setCodeContract(codeContract);
			queryParameterEventRDTO.setDateExecution(dateExecution);
			queryParameterEventRDTO.setCodeUser(codeUser);
			queryParameterEventRDTO.setTransactionId(transactionId);
			
			returnEventApprovedRDTO = eventService.selectApproved(queryParameterEventRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, ex));
			return new ResponseEntity<>(returnEventApprovedRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnEventApprovedRDTO, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/esdeveniment/contracta/{codi}/rebutjats", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície Sync only GET", tags = { "orchestrator" })
	public ResponseEntity<ReturnEventRejectedRDTO> eventRejectedSyncGET(
			@PathVariable(value = "codi") String codeContract,
			@RequestParam(value = "dataExecucio", required = false) String dateExecution,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestParam(required = false) String transactionId) {

		logger.info(LogsConstants.LOG_START);
		ReturnEventRejectedRDTO returnEventRejectedRDTO = new ReturnEventRejectedRDTO();
		queryParameterEventRDTO = new QueryParameterEventRDTO();

		try {
			queryParameterEventRDTO.setCodeContract(codeContract);
			queryParameterEventRDTO.setDateExecution(dateExecution);
			queryParameterEventRDTO.setCodeUser(codeUser);
			queryParameterEventRDTO.setTransactionId(transactionId);
			
			returnEventRejectedRDTO = eventService.selectRejected(queryParameterEventRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, ex));
			return new ResponseEntity<>(returnEventRejectedRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnEventRejectedRDTO, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/esdeveniment/contracta/{codi}/pendents", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície Sync only GET", tags = { "orchestrator" })
	public ResponseEntity<ReturnEventPendingRDTO> eventPendingSyncGET(
			@PathVariable(value = "codi") String codeContract,
			@RequestParam(value = "dataInici", required = false) String dateStart,
			@RequestParam(value = "dataFi", required = false) String dateEnd,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestParam(required = false) String transactionId) {

		logger.info(LogsConstants.LOG_START);
		ReturnEventPendingRDTO returnEventPendingRDTO = new ReturnEventPendingRDTO();
		queryParameterEventRDTO = new QueryParameterEventRDTO();

		try {
			queryParameterEventRDTO.setCodeContract(codeContract);
			queryParameterEventRDTO.setDateStart(dateStart);
			queryParameterEventRDTO.setDateEnd(dateEnd);
			queryParameterEventRDTO.setCodeUser(codeUser);
			queryParameterEventRDTO.setTransactionId(transactionId);
			
			returnEventPendingRDTO = eventService.selectPending(queryParameterEventRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, ex));
			return new ResponseEntity<>(returnEventPendingRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnEventPendingRDTO, HttpStatus.OK);
	}
}
