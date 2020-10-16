package es.bcn.imi.framework.vigia.frontal.web.rest.controller.itinerary.sync;

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

import es.bcn.imi.framework.vigia.frontal.business.itinerary.ItineraryService;
import es.bcn.imi.framework.vigia.frontal.business.itinerary.PlanningService;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
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
import es.bcn.vigia.fmw.libutils.Utils;
import es.bcn.vigia.fmw.libutils.enums.ReturnEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.opentrends.openframe.services.rest.controller.AbstractRestController;
import net.opentrends.openframe.services.rest.http.ResponseEntity;

@RestController
@RequestMapping("/sync/itinerari")
@Lazy(true)
@Api("FRONTAL API")
public class ItineraryController extends AbstractRestController {

	private Log logger = LogFactory.getLog(this.getClass());

	@Autowired(required = true)
	@Qualifier(ServicesConstants.SRV_FRONTAL_PLANNING)
	private PlanningService planningService;

	@Autowired(required = true)
	@Qualifier(ServicesConstants.SRV_FRONTAL_ITINERARY)
	private ItineraryService itineraryService;

	private ReturnRDTO returnRDTO;

	private QueryParameterPlanningRDTO queryParameterPlanningRDTO;
	private QueryParameterRDTO queryParameterRDTO;	

	@RequestMapping(value = "/contracta/{codi}/planificacio/any/{any}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta massiva de planificacions", tags = { "planificacio" })
	public ResponseEntity<ReturnPlanningMassiveRDTO> selectPlanningMassive(
			@PathVariable(value = "codi", required = true) String codeContract,
			@PathVariable(value = "any", required = true) String year,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);		
		ReturnPlanningMassiveRDTO returnPlanningMassiveRDTO = new ReturnPlanningMassiveRDTO();
		queryParameterPlanningRDTO = new QueryParameterPlanningRDTO();

		try {
			queryParameterPlanningRDTO.setClientId(clientId);
			queryParameterPlanningRDTO.setCodeContract(codeContract);
			queryParameterPlanningRDTO.setYear(year);
			queryParameterPlanningRDTO.setCodeUser(codeUser);
			
			returnPlanningMassiveRDTO = planningService.selectMassive(queryParameterPlanningRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnPlanningMassiveRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO,ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			returnPlanningMassiveRDTO.setReturnRDTO(returnRDTO);
			return new ResponseEntity<>(returnPlanningMassiveRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnPlanningMassiveRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/contracta/{codi}/planificacio/{codiItinerari}/any/{any}/detall", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta de planificació d'un itinerari", tags = { "planificacio" })
	public ResponseEntity<ReturnPlanningDetailedRDTO> selectPlanningDetailed(
			@PathVariable(value = "codiItinerari", required = true) String code,
			@PathVariable(value = "codi", required = true) String codeContract,
			@PathVariable(value = "any", required = true) String year,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);		
		ReturnPlanningDetailedRDTO returnPlanningDetailedRDTO = new ReturnPlanningDetailedRDTO();
		queryParameterPlanningRDTO = new QueryParameterPlanningRDTO();

		try {
			queryParameterPlanningRDTO.setClientId(clientId);
			queryParameterPlanningRDTO.setCodeContract(codeContract);
			queryParameterPlanningRDTO.setCode(code);
			queryParameterPlanningRDTO.setYear(year);
			queryParameterPlanningRDTO.setDateReference(dateReference);
			queryParameterPlanningRDTO.setCodeUser(codeUser);
			
			returnPlanningDetailedRDTO = planningService.selectDetailed(queryParameterPlanningRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnPlanningDetailedRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO,ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			returnPlanningDetailedRDTO.setReturnRDTO(returnRDTO);
			return new ResponseEntity<>(returnPlanningDetailedRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnPlanningDetailedRDTO, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/planificacio", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Interfície d'alta o nova version de planificacio de itinerari", tags = { "planificacio" })
	public ResponseEntity<ReturnRDTO> savePlanning(@RequestBody PlanningRDTO planningRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();
		
		try {
			planningRDTO.setClientId(clientId);
			returnRDTO = planningService.insert(planningRDTO);
			
			if(!returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_REGISTERED.getCodeDescription())) {
				
				logger.error(String.format(LogsConstants.LOG_HTTP_BAD_REQUEST, returnRDTO.toString()));
				
				return new ResponseEntity<>(returnRDTO, HttpStatus.BAD_REQUEST);
			} 
				
		} catch (Exception ex) {
		
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, planningRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO,ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/contracta/{codi}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta massiva d'itinerari", tags = { "itinerari" })
	public ResponseEntity<ReturnItineraryMassiveRDTO> selectItineraryMassive(
			@PathVariable(value = "codi", required = true) String codeContract,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);
		ReturnItineraryMassiveRDTO returnItineraryMassiveRDTO = new ReturnItineraryMassiveRDTO();
		queryParameterRDTO = new QueryParameterRDTO();

		try {
			queryParameterRDTO.setClientId(clientId);
			queryParameterRDTO.setCodeContract(codeContract);
			queryParameterRDTO.setDateReference(dateReference);
			queryParameterRDTO.setCodeUser(codeUser);

			returnItineraryMassiveRDTO = itineraryService.selectMassive(queryParameterRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnItineraryMassiveRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			returnItineraryMassiveRDTO.setReturnRDTO(returnRDTO);
			return new ResponseEntity<>(returnItineraryMassiveRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnItineraryMassiveRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/contracta/{codi}/itinerari/{codiItinerari}/detall", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta detallada d'itinerari", tags = { "itinerari" })
	public ResponseEntity<ReturnItineraryDetailedRDTO> selectItineraryDetailed(
			@PathVariable(value = "codiItinerari", required = true) String code,
			@PathVariable(value = "codi", required = true) String codeContract,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);
		ReturnItineraryDetailedRDTO returnItineraryDetailedRDTO = new ReturnItineraryDetailedRDTO();
		queryParameterRDTO = new QueryParameterRDTO();

		try {
			queryParameterRDTO.setClientId(clientId);
			queryParameterRDTO.setCode(code);
			queryParameterRDTO.setCodeContract(codeContract);
			queryParameterRDTO.setDateReference(dateReference);
			queryParameterRDTO.setCodeUser(codeUser);

			returnItineraryDetailedRDTO = itineraryService.selectDetailed(queryParameterRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnItineraryDetailedRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			returnItineraryDetailedRDTO.setReturnRDTO(returnRDTO);
			return new ResponseEntity<>(returnItineraryDetailedRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnItineraryDetailedRDTO, HttpStatus.OK);
	}

	
	@RequestMapping(path = "/{codi}/notificacio/alta", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Interfície de notificació del resultat d’un alta d’itinerari", tags = { "itinerari" })
	public ResponseEntity<ReturnRDTO> notifyRegistrationItinerary(
			@PathVariable(value = "codi", required = true) String codeItinerary,
			@RequestBody NotificationItineraryRDTO notificationItineraryRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();
		
		try {
			notificationItineraryRDTO.setClientId(clientId);
			notificationItineraryRDTO.setCodeItinerary(codeItinerary);
			notificationItineraryRDTO.setCodeContract(notificationItineraryRDTO.getContract());
			
			returnRDTO = itineraryService.notify(notificationItineraryRDTO);
			
			if(!returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_REGISTERED.getCodeDescription())) {
				
				logger.error(String.format(LogsConstants.LOG_HTTP_BAD_REQUEST, returnRDTO.toString()));
				
				return new ResponseEntity<>(returnRDTO, HttpStatus.BAD_REQUEST);
			} 
				
		} catch (Exception ex) {
		
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, notificationItineraryRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO,ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}

}