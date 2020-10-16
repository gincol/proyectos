package es.bcn.imi.framework.vigia.frontal.web.rest.controller.itinerary.async;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import es.bcn.imi.framework.vigia.frontal.business.itinerary.ItineraryService;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.ItineraryRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.Utils;
import es.bcn.vigia.fmw.libutils.enums.ReturnEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.opentrends.openframe.services.rest.controller.AbstractRestController;
import net.opentrends.openframe.services.rest.http.ResponseEntity;

@RestController
@RequestMapping("/async/itinerari")
@Lazy(true)
@Api("FRONTAL API")
public class ItineraryAsyncController extends AbstractRestController {

	private Log logger = LogFactory.getLog(this.getClass());

	
	@Autowired(required = true)
	@Qualifier(ServicesConstants.SRV_FRONTAL_ITINERARY)
	private ItineraryService itineraryService;

	private ReturnRDTO returnRDTO;

	
		
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Interfície d'alta de itinerari", tags = { "itinerari" })
	public ResponseEntity<ReturnRDTO> saveItinerary(
		@RequestParam(value = "fitxerGeoPackage", required = false) MultipartFile attachedGeoPackageFile,
		@RequestParam(value = "fitxerPlanol", required = false) MultipartFile attachedMapFile,
		@RequestPart(name = "itinerari", required = true) @Valid ItineraryRDTO itineraryRDTO,
		@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();
		
		try {
			itineraryRDTO.setClientId(clientId);
			if (attachedGeoPackageFile!=null)
			{
				itineraryRDTO.setGeoPackageFileRDTO(Utils.getDocumentContentName(attachedGeoPackageFile));
			}
			if (attachedMapFile!=null)
			{
				itineraryRDTO.setMapFileRDTO(Utils.getDocumentContentName(attachedMapFile));
			}
			returnRDTO = itineraryService.insert(itineraryRDTO);
			
			if(!returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_REGISTERED.getCodeDescription())) {
				
				logger.error(String.format(LogsConstants.LOG_HTTP_BAD_REQUEST, returnRDTO.toString()));
				
				return new ResponseEntity<>(returnRDTO, HttpStatus.BAD_REQUEST);
			} 
				
		} catch (Exception ex) {
		
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, itineraryRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO,ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}
	
}