package es.bcn.imi.framework.vigia.frontal.web.rest.controller.sentilo.sync;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.SentiloNotificationRDTO;
import es.bcn.vigia.fmw.libutils.enums.ReturnEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.opentrends.openframe.services.rest.controller.AbstractRestController;
import net.opentrends.openframe.services.rest.http.ResponseEntity;

@RestController
@RequestMapping("/sync/sentilo")
@Lazy
@Api("Notifications Sentilo API")
public class SentiloController extends AbstractRestController {

	private final Log logger = LogFactory.getLog(this.getClass());

;

	@RequestMapping(value = "/notificacions", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Interfície de notificacions de sentilo", tags = { "planificacio" })
	public ResponseEntity<String> saveNotification(@RequestBody SentiloNotificationRDTO sentiloNotificationRDTO) {
	
		logger.info(LogsConstants.LOG_START);
	
		
		logger.info(LogsConstants.LOG_END);
	
		return new ResponseEntity<>(new String(ReturnEnum.CODE_SUCCESS_REGISTERED.getCodeDescription()), HttpStatus.OK);
	}


}