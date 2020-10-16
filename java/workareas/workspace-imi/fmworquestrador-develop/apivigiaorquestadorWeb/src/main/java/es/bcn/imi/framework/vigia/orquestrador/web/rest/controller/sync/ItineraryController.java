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

import es.bcn.imi.framework.vigia.orquestrador.business.itinerary.ItineraryService;
import es.bcn.imi.framework.vigia.orquestrador.business.itinerary.PlanningService;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.NotificationItineraryRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.PlanningRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterPlanningRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnItineraryDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnItineraryMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnPlanningDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnPlanningMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.opentrends.openframe.services.rest.controller.AbstractRestController;
import net.opentrends.openframe.services.rest.http.ResponseEntity;

@RestController
@RequestMapping("/sync/itinerari")
@Lazy(true)
@Api("ORQUESTRATOR API")
public class ItineraryController extends AbstractRestController{
	
	private Log logger = LogFactory.getLog(this.getClass());

	@Autowired(required = true)
	@Qualifier(ServicesConstants.SRV_ITINERARY_PLANNING)
	private PlanningService planningService;

	@Autowired(required = true)
	@Qualifier(ServicesConstants.SRV_ITINERARY_ITINERARY)
	private ItineraryService itineraryService;
	
	private ReturnRDTO returnRDTO;

	private QueryParameterPlanningRDTO queryParameterPlanningRDTO;

	private QueryParameterRDTO queryParameterRDTO;
	
	@RequestMapping(value = "/planificacio", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Interfície Sync for POST, PUT and DELETE", tags = { "orchestrator" })
	public ResponseEntity<ReturnRDTO> planningSync(@RequestBody PlanningRDTO planningRDTO) {

		logger.info(LogsConstants.LOG_START); 

		try {
			
			returnRDTO = planningService.insert(planningRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, planningRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/planificacio/contracta/{codi}/any/{any}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície Sync only GET", tags = { "orchestrator" })
	public ResponseEntity<ReturnPlanningMassiveRDTO> planningMassiveSyncGET(
			@PathVariable(value = "codi") String codeContract,
			@PathVariable(value = "any") String year,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestParam(required = false) String transactionId) {

		logger.info(LogsConstants.LOG_START);
		ReturnPlanningMassiveRDTO returnPlanningMassiveRDTO = new ReturnPlanningMassiveRDTO();
		queryParameterPlanningRDTO = new QueryParameterPlanningRDTO();

		try {
			queryParameterPlanningRDTO.setCodeContract(codeContract);
			queryParameterPlanningRDTO.setYear(year);
			queryParameterPlanningRDTO.setCodeUser(codeUser);
			queryParameterPlanningRDTO.setTransactionId(transactionId);
			
			returnPlanningMassiveRDTO = planningService.selectMassive(queryParameterPlanningRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, ex));
			return new ResponseEntity<>(returnPlanningMassiveRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnPlanningMassiveRDTO, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/planificacio/{codi}/contracta/{codiContracta}/any/{any}/detall", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície Sync only GET", tags = { "orchestrator" })
	public ResponseEntity<ReturnPlanningDetailedRDTO> planningDetailedSyncGET(
			@PathVariable(value = "codi") String code,
			@PathVariable(value = "codiContracta") String codeContract,
			@PathVariable(value = "any") String year,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestParam(required = false) String transactionId) {

		logger.info(LogsConstants.LOG_START);
		ReturnPlanningDetailedRDTO returnPlanningDetailedRDTO = new ReturnPlanningDetailedRDTO();
		queryParameterPlanningRDTO = new QueryParameterPlanningRDTO();

		try {
			queryParameterPlanningRDTO.setCode(code);
			queryParameterPlanningRDTO.setCodeContract(codeContract);
			queryParameterPlanningRDTO.setYear(year);
			queryParameterPlanningRDTO.setDateReference(dateReference);
			queryParameterPlanningRDTO.setCodeUser(codeUser);
			queryParameterPlanningRDTO.setTransactionId(transactionId);
			
			returnPlanningDetailedRDTO = planningService.selectDetailed(queryParameterPlanningRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, ex));
			return new ResponseEntity<>(returnPlanningDetailedRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnPlanningDetailedRDTO, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/contracta/{codi}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície Sync only GET", tags = { "orchestrator" })
	public ResponseEntity<ReturnItineraryMassiveRDTO> itineraryMassiveSyncGET(
			@PathVariable(value = "codi") String codeContract,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestParam(required = false) String transactionId) {

		logger.info(LogsConstants.LOG_START);
		ReturnItineraryMassiveRDTO returnItineraryMassiveRDTO = new ReturnItineraryMassiveRDTO();
		queryParameterRDTO = new QueryParameterRDTO();

		try {
			queryParameterRDTO.setCodeContract(codeContract);
			queryParameterRDTO.setDateReference(dateReference);
			queryParameterRDTO.setCodeUser(codeUser);
			queryParameterRDTO.setTransactionId(transactionId);
			
			returnItineraryMassiveRDTO = itineraryService.selectMassive(queryParameterRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, ex));
			return new ResponseEntity<>(returnItineraryMassiveRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnItineraryMassiveRDTO, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/{codi}/contracta/{codiContracta}/detall", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície Sync only GET", tags = { "orchestrator" })
	public ResponseEntity<ReturnItineraryDetailedRDTO> itineraryDetailedSyncGET(
			@PathVariable(value = "codi") String code,
			@PathVariable(value = "codiContracta") String codeContract,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestParam(required = false) String transactionId) {

		logger.info(LogsConstants.LOG_START);
		ReturnItineraryDetailedRDTO returnItineraryDetailedRDTO = new ReturnItineraryDetailedRDTO();
		queryParameterRDTO = new QueryParameterRDTO();

		try {
			queryParameterRDTO.setCode(code);
			queryParameterRDTO.setCodeContract(codeContract);
			queryParameterRDTO.setDateReference(dateReference);
			queryParameterRDTO.setCodeUser(codeUser);
			queryParameterRDTO.setTransactionId(transactionId);
			
			returnItineraryDetailedRDTO = itineraryService.selectDetailed(queryParameterRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, ex));
			return new ResponseEntity<>(returnItineraryDetailedRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnItineraryDetailedRDTO, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/{codi}/notificacio/alta", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Interfície Sync for POST", tags = { "orchestrator" })
	public ResponseEntity<ReturnRDTO> notifyRegistrationItinerarySync(
				@PathVariable(value = "codi", required = true) String codeItinerary,
				@RequestBody NotificationItineraryRDTO notificationItineraryRDTO) {

		logger.info(LogsConstants.LOG_START); 

		try {
			notificationItineraryRDTO.setCodeItinerary(codeItinerary);
			returnRDTO = new ReturnRDTO();
			returnRDTO = itineraryService.notify(notificationItineraryRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, notificationItineraryRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}

}
