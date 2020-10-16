package es.bcn.imi.framework.vigia.orquestrador.web.rest.controller.async;

import javax.servlet.http.HttpServletRequest;

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

import es.bcn.imi.framework.vigia.orquestrador.business.async.AsyncService;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.AsyncRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CommerceRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.InstallationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.LogicalModelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.MaterialResourceRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.PhysicalModelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.UbicationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.opentrends.openframe.services.rest.controller.AbstractRestController;
import net.opentrends.openframe.services.rest.http.ResponseEntity;

@RestController
@RequestMapping("/async/inventari")
@Lazy(true)
@Api("ORQUESTRATOR API")
public class InventaryAsyncController extends AbstractRestController {

	private Log logger = LogFactory.getLog(this.getClass());

	@Autowired(required = true)
	@Qualifier(ServicesConstants.SRV_ASYNC)
	private AsyncService asyncService;

	private ReturnRDTO returnRDTO;

	@RequestMapping(value = "/ubicacio", method = { RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
	@ResponseBody
	@ApiOperation(value = "Interfície Sync for POST, PUT and DELETE", tags = { "orchestrator" })
	public ResponseEntity<ReturnRDTO> asyncUbication(@RequestBody AsyncRDTO<UbicationRDTO> rdto,
			HttpServletRequest request) {

		logger.info(LogsConstants.LOG_START);
		
		returnRDTO = new ReturnRDTO();

		try {

			returnRDTO = asyncService.ubications(rdto, request);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, rdto), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/installacio", method = { RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
	@ResponseBody
	@ApiOperation(value = "Interfície Sync for POST, PUT and DELETE", tags = { "orchestrator" })
	public ResponseEntity<ReturnRDTO> asyncInstallation(@RequestBody AsyncRDTO<InstallationRDTO> rdto,
			HttpServletRequest request) {

		logger.info(LogsConstants.LOG_START);
		
		returnRDTO = new ReturnRDTO();

		try {

			returnRDTO = asyncService.installation(rdto, request);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, rdto), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/ubicacio/mcl", method = { RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
	@ResponseBody
	@ApiOperation(value = "Interfície Sync for POST, PUT and DELETE", tags = { "orchestrator" })
	public ResponseEntity<ReturnRDTO> asyncMcl(@RequestBody AsyncRDTO<LogicalModelRDTO> rdto,
			HttpServletRequest request) {

		logger.info(LogsConstants.LOG_START);
		
		returnRDTO = new ReturnRDTO();

		try {

			returnRDTO = asyncService.mcl(rdto, request);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, rdto), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/ubicacio/mcl/mcf", method = { RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
	@ResponseBody
	@ApiOperation(value = "Interfície Sync for POST, PUT and DELETE", tags = { "orchestrator" })
	public ResponseEntity<ReturnRDTO> asyncMcf(@RequestBody AsyncRDTO<PhysicalModelRDTO> rdto,
			HttpServletRequest request) {

		logger.info(LogsConstants.LOG_START);
		
		returnRDTO = new ReturnRDTO();

		try {

			returnRDTO = asyncService.mcf(rdto, request);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, rdto), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/recursmaterial", method = { RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
	@ResponseBody
	@ApiOperation(value = "Interfície Sync for POST, PUT and DELETE", tags = { "orchestrator" })
	public ResponseEntity<ReturnRDTO> asyncRRMM(@RequestBody AsyncRDTO<MaterialResourceRDTO> rdto,
			HttpServletRequest request) {

		logger.info(LogsConstants.LOG_START);
		
		returnRDTO = new ReturnRDTO();

		try {

			returnRDTO = asyncService.rrmm(rdto, request);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, rdto), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/comerc", method = { RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
	@ResponseBody
	@ApiOperation(value = "Interfície Sync for POST, PUT and DELETE", tags = { "orchestrator" })
	public ResponseEntity<ReturnRDTO> asyncCommerce(@RequestBody AsyncRDTO<CommerceRDTO> rdto,
			HttpServletRequest request) {

		logger.info(LogsConstants.LOG_START);
		
		returnRDTO = new ReturnRDTO();

		try {

			returnRDTO = asyncService.commerce(rdto, request);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, rdto), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}
	
}