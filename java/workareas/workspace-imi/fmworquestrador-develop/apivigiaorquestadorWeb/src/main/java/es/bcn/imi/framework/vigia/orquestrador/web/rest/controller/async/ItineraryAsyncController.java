package es.bcn.imi.framework.vigia.orquestrador.web.rest.controller.async;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.bcn.imi.framework.vigia.orquestrador.business.itinerary.ItineraryService;
import es.bcn.imi.framework.vigia.orquestrador.business.itinerary.PlanningService;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.ItineraryRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.opentrends.openframe.services.rest.controller.AbstractRestController;
import net.opentrends.openframe.services.rest.http.ResponseEntity;

@RestController
@RequestMapping("/async/itinerari")
@Lazy(true)
@Api("ORQUESTRATOR API")
public class ItineraryAsyncController extends AbstractRestController{
	
	private Log logger = LogFactory.getLog(this.getClass());

	@Autowired(required = true)
	@Qualifier(ServicesConstants.SRV_ITINERARY_PLANNING)
	private PlanningService planningService;

	@Autowired(required = true)
	@Qualifier(ServicesConstants.SRV_ITINERARY_ITINERARY)
	private ItineraryService itineraryService;
	
	private ReturnRDTO returnRDTO;

		
	@RequestMapping( method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Interfície Sync for POST", tags = { "orchestrator" })
	public ResponseEntity<ReturnRDTO> itineraryAsync(@RequestBody ItineraryRDTO itineraryRDTO) {

		logger.info(LogsConstants.LOG_START); 

		try {
			
			returnRDTO = new ReturnRDTO();
			returnRDTO = itineraryService.insert(itineraryRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, itineraryRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}

}
