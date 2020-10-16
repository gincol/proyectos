package es.bcn.imi.framework.vigia.inventari.web.rest.controller;

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

import es.bcn.imi.framework.vigia.inventari.business.SensorService;
import es.bcn.vigia.fmw.libcommons.business.dto.QueryParameterBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.SensorBDTO;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.SensorRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnSensorDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnSensorMassiveRDTO;
import es.bcn.vigia.fmw.libutils.convert.SensorConvert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.opentrends.openframe.services.rest.controller.AbstractRestController;
import net.opentrends.openframe.services.rest.http.ResponseEntity;

@RestController
@RequestMapping("/sensor")
@Lazy(true)
@Api("IVENTARI API")
public class SensorController extends AbstractRestController {

	private Log logger = LogFactory.getLog(this.getClass());

	@Autowired(required = true)
	@Qualifier(ServicesConstants.SRV_SENSOR)
	private SensorService sensorService;

	private ReturnRDTO returnRDTO;

	private QueryParameterBDTO queryParameterBDTO;	

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Interfície d’alta d’un Sensor", tags = { "sensor" })
	public ResponseEntity<ReturnRDTO> save(@RequestBody SensorRDTO sensorRDTO) {

		logger.info(LogsConstants.LOG_START);

		try {

			SensorBDTO sensorBDTO = SensorConvert.rdto2bdto(sensorRDTO);
			
			returnRDTO = new ReturnRDTO();
			returnRDTO = sensorService.insert(sensorBDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, sensorRDTO), ex);
			return new ResponseEntity<ReturnRDTO>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<ReturnRDTO>(returnRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{codi}", method = RequestMethod.PUT)
	@ResponseBody
	@ApiOperation(value = "Interfície de modificació d’un Sensor", tags = { "sensors" })
	public ResponseEntity<ReturnRDTO> update(@PathVariable(value = "codi", required = true) String code,
											 @RequestBody SensorRDTO sensorRDTO) {

		logger.info(LogsConstants.LOG_START);

		try {

			returnRDTO = new ReturnRDTO();

			sensorRDTO.setCode(code);

			SensorBDTO sensorBDTO = SensorConvert.rdto2bdto(sensorRDTO);

			returnRDTO = sensorService.update(sensorBDTO);

		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, sensorRDTO), ex);
			return new ResponseEntity<ReturnRDTO>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<ReturnRDTO>(returnRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{codi}", method = RequestMethod.DELETE)
	@ResponseBody
	@ApiOperation(value = "Interfície de baixa d’un Sensor", tags = { "sensors" })
	public ResponseEntity<ReturnRDTO> delete(@PathVariable(value = "codi", required = true) String code,
											 @RequestBody SensorRDTO sensorRDTO) {

		logger.info(LogsConstants.LOG_START);

		try {

			returnRDTO = new ReturnRDTO();

			sensorRDTO.setCode(code);

			SensorBDTO sensorBDTO = SensorConvert.rdto2bdto(sensorRDTO);

			returnRDTO = sensorService.delete(sensorBDTO);

		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, sensorRDTO), ex);
			return new ResponseEntity<ReturnRDTO>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<ReturnRDTO>(returnRDTO, HttpStatus.OK);
	}

	
	@RequestMapping(value = "/{codi}/contracta/{codiContracta}/detall", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta detallada d'sensors", tags = { "sensors" })
	public ResponseEntity<ReturnSensorDetailedRDTO> selectDetailed(
			@PathVariable(value = "codi", required = true) String code,
			@PathVariable(value = "codiContracta", required = true) String codeContract,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestParam(required = false) String transactionId) {

		logger.info(LogsConstants.LOG_START);
		ReturnSensorDetailedRDTO returnSensorDetailedRDTO = new ReturnSensorDetailedRDTO();
		queryParameterBDTO = new QueryParameterBDTO();		

		try {
			queryParameterBDTO.setCode(code);
			queryParameterBDTO.setCodeContract(codeContract);
			queryParameterBDTO.setDateReference(dateReference);
			queryParameterBDTO.setCodeUser(codeUser);
			
			returnSensorDetailedRDTO = sensorService.selectDetailed(queryParameterBDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnSensorDetailedRDTO), ex);
			return new ResponseEntity<>(returnSensorDetailedRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnSensorDetailedRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/contracta/{codi}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta massiva de sensors", tags = { "sensors" })
	public ResponseEntity<ReturnSensorMassiveRDTO> selectMassive(
			@PathVariable(value = "codi", required = true) String code,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestParam(required = false) String transactionId) {

		logger.info(LogsConstants.LOG_START);
		ReturnSensorMassiveRDTO returnSensorMassiveRDTO = new ReturnSensorMassiveRDTO();
		queryParameterBDTO = new QueryParameterBDTO();		

		try {
			queryParameterBDTO.setCodeContract(code);
			queryParameterBDTO.setDateReference(dateReference);
			queryParameterBDTO.setCodeUser(codeUser);
			
			returnSensorMassiveRDTO = sensorService.selectMassive(queryParameterBDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnSensorMassiveRDTO), ex);
			return new ResponseEntity<>(returnSensorMassiveRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnSensorMassiveRDTO, HttpStatus.OK);
	}

}