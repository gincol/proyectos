package es.bcn.imi.framework.vigia.inventari.web.rest.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.bcn.imi.framework.vigia.inventari.business.LogicalModelService;
import es.bcn.vigia.fmw.libcommons.business.dto.LogicalModelBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.QueryParameterBDTO;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.LogicalModelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnLogicalModelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnLogicalModelDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnLogicalModelMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.convert.LogicalModelConvert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.opentrends.openframe.services.rest.controller.AbstractRestController;

@RestController
@RequestMapping("/mobiliarilogic")
@Lazy(true)
@Api("IVENTARI API")
public class LogicalModelController extends AbstractRestController {

	private Log logger = LogFactory.getLog(this.getClass());

	@Autowired(required = true)
	@Qualifier(ServicesConstants.SRV_LOGICAL_MODEL)
	private LogicalModelService service;

	private ReturnRDTO returnRDTO;

	private QueryParameterBDTO queryParameterBDTO;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Interfície d’alta d’un mobiliari lògic", tags = { "mobiliarilogic" })
	public ResponseEntity<ReturnRDTO> save(@RequestBody LogicalModelRDTO logicalModelRDTO) {

		logger.info(LogsConstants.LOG_START);

		try {
			LogicalModelBDTO logicalModelBDTO = LogicalModelConvert.rdto2bto(logicalModelRDTO);
			
			returnRDTO = new ReturnRDTO();
			returnRDTO = service.insert(logicalModelBDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, logicalModelRDTO), ex);
			return new ResponseEntity<ReturnRDTO>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<ReturnRDTO>(returnRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{codi}", method = RequestMethod.PUT)
	@ResponseBody
	@ApiOperation(value = "Interfície d’actualització d’un mobiliari lògic", tags = { "mobiliarilogic" })
	public ResponseEntity<ReturnRDTO> update(@PathVariable(value = "codi", required = true) String code,
			@RequestBody LogicalModelRDTO logicalModelRDTO) {

		logger.info(LogsConstants.LOG_START);

		try {
			logicalModelRDTO.setCode(code);

			LogicalModelBDTO logicalModelBDTO = LogicalModelConvert.rdto2bto(logicalModelRDTO);
			returnRDTO = new ReturnRDTO();

			returnRDTO = service.update(logicalModelBDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, logicalModelRDTO), ex);
			return new ResponseEntity<ReturnRDTO>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<ReturnRDTO>(returnRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{codi}", method = RequestMethod.DELETE)
	@ResponseBody
	@ApiOperation(value = "Interfície d’eliminació d’un mobiliari lògic", tags = { "mobiliarilogic" })
	public ResponseEntity<ReturnRDTO> delete(@PathVariable(value = "codi", required = true) String code, @RequestBody LogicalModelRDTO logicalModelRDTO) {

		logger.info(LogsConstants.LOG_START);
		returnRDTO = new ReturnRDTO();
		try {
			
			logicalModelRDTO.setCode(code);

			LogicalModelBDTO logicalModelBDTO = LogicalModelConvert.rdto2bto(logicalModelRDTO);
			returnRDTO = new ReturnRDTO();

			returnRDTO = service.delete(logicalModelBDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, logicalModelRDTO), ex);
			return new ResponseEntity<ReturnRDTO>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<ReturnRDTO>(returnRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{codi}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta d’un mobiliari lògic", tags = { "mobiliarilogic" })
	public ResponseEntity<ReturnLogicalModelRDTO> select(@PathVariable(value = "codi", required = true) String code) {

		logger.info(LogsConstants.LOG_START);
		ReturnLogicalModelRDTO returnLogicalModelRDTO = new ReturnLogicalModelRDTO();
		LogicalModelRDTO logicalModelRDTO = new LogicalModelRDTO();

		try {
			logicalModelRDTO.setCode(code);
			LogicalModelBDTO logicalModelBDTO = LogicalModelConvert.rdto2bto(logicalModelRDTO);
			returnLogicalModelRDTO = service.select(logicalModelBDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnLogicalModelRDTO), ex);
			return new ResponseEntity<ReturnLogicalModelRDTO>(returnLogicalModelRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);
		return new ResponseEntity<ReturnLogicalModelRDTO>(returnLogicalModelRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/contracta/{codi}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta massiva de MCLs", tags = { "mobiliarilogic" })
	public ResponseEntity<ReturnLogicalModelMassiveRDTO> selectMassive(
			@PathVariable(value = "codi", required = true) String code,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestParam(required = false) String transactionId) {

		logger.info(LogsConstants.LOG_START);
		
		ReturnLogicalModelMassiveRDTO returnLogicalModelMassiveRDTO = new ReturnLogicalModelMassiveRDTO();
		queryParameterBDTO = new QueryParameterBDTO();		

		try {
			queryParameterBDTO.setCodeContract(code);
			queryParameterBDTO.setDateReference(dateReference);
			queryParameterBDTO.setCodeUser(codeUser);
			
			returnLogicalModelMassiveRDTO = service.selectMassive(queryParameterBDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnLogicalModelMassiveRDTO), ex);
			return new ResponseEntity<>(returnLogicalModelMassiveRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnLogicalModelMassiveRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{codi}/contracta/{codiContracta}/detall", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta individual de MCLs", tags = { "mobiliarilogic" })
	public ResponseEntity<ReturnLogicalModelDetailedRDTO> selectDetailed(
			@PathVariable(value = "codi", required = true) String code,
			@PathVariable(value = "codiContracta", required = true) String codeContract,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestParam(required = false) String transactionId) {

		logger.info(LogsConstants.LOG_START);
		ReturnLogicalModelDetailedRDTO returnLogicalModelDetailedRDTO = new ReturnLogicalModelDetailedRDTO();
		queryParameterBDTO = new QueryParameterBDTO();		

		try {
			queryParameterBDTO.setCode(code);
			queryParameterBDTO.setCodeContract(codeContract);
			queryParameterBDTO.setDateReference(dateReference);
			queryParameterBDTO.setCodeUser(codeUser);
			
			returnLogicalModelDetailedRDTO = service.selectDetailed(queryParameterBDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnLogicalModelDetailedRDTO), ex);
			return new ResponseEntity<>(returnLogicalModelDetailedRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnLogicalModelDetailedRDTO, HttpStatus.OK);
	}


}
