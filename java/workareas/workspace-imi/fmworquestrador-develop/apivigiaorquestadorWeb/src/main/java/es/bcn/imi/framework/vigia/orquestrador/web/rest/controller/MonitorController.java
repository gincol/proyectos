package es.bcn.imi.framework.vigia.orquestrador.web.rest.controller;

import java.lang.management.ManagementFactory;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.bcn.vigia.fmw.libutils.exception.ImiException;
import es.bcn.vigia.fmw.libutils.monitor.MonitorControllerUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.opentrends.openframe.services.rest.controller.AbstractRestController;
import net.opentrends.openframe.services.rest.http.ResponseEntity;

@RestController
@RequestMapping("/monitor")
@Lazy
@Api("Liveness Integration API")
public class MonitorController extends AbstractRestController{

	private final Log logger = LogFactory.getLog(this.getClass());

	@Value("${app.monitor.message}")
	private String message;
	@Value("${app.name}")
	private String appName;

	@Value("${app.version}")
	private String appVersion;

	private String appNameAndVersion;

	@PostConstruct
	private void init() {
		appNameAndVersion = " (" + appName + " v" + appVersion + ")";
	}

	@RequestMapping(value = "/liveness", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Returns monitor OK", tags = { "Liveness" })
	public ResponseEntity<String> liveness() throws ImiException {

		String finalMessage = "OK " + message + appNameAndVersion;

		logger.info(finalMessage);
		return new ResponseEntity<>(finalMessage, HttpStatus.OK);
	}

	@RequestMapping(value = "/uptime", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Returns time up server(+ip)", tags = { "uptime" })
	public ResponseEntity<String> uptime() throws ImiException {

		String uptime = "Up in " + MonitorControllerUtils.getIp() + appNameAndVersion + " since "
				+ MonitorControllerUtils.beautifyUptime(ManagementFactory.getRuntimeMXBean().getUptime());

		logger.info(uptime);
		return new ResponseEntity<>(uptime, HttpStatus.OK);
	}

}