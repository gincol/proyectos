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

import es.bcn.imi.framework.vigia.inventari.business.ActuationService;
import es.bcn.vigia.fmw.libcommons.business.dto.ActuationBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.QueryParameterActuationBDTO;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.ActuationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnActuationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.convert.ActuationConvert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.opentrends.openframe.services.rest.controller.AbstractRestController;
import net.opentrends.openframe.services.rest.http.ResponseEntity;

@RestController
@RequestMapping("/actuacio")
@Lazy(true)
@Api("ACTUATION API")
public class ActuationController extends AbstractRestController {

	private Log logger = LogFactory.getLog(this.getClass());

	@Autowired(required = true)
	@Qualifier(ServicesConstants.SRV_ACTUATION)
	private ActuationService service;

	
	private ReturnRDTO returnRDTO;

	private QueryParameterActuationBDTO queryParameterActuationBDTO;

			
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Interfície d'alta de actuacio", tags = { "actuacio" })
	public ResponseEntity<ReturnRDTO> save(@RequestBody ActuationRDTO actuationRDTO) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {
			
			ActuationBDTO actuationBDTO = ActuationConvert.rdto2bdto(actuationRDTO);
			
			returnRDTO = service.insert(actuationBDTO);

		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, actuationRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{codi}/entitat/{codiEntitat}", method = RequestMethod.PUT)
	@ResponseBody
	@ApiOperation(value = "Interfície d'anullacio de actuacio", tags = { "actuacio" })
	public ResponseEntity<ReturnRDTO> delete(@PathVariable(value = "codiEntitat", required = true) String codeEntity,
			@PathVariable(value = "codi", required = true) String code,
			@RequestBody ActuationRDTO actuationRDTO) {

		logger.info(LogsConstants.LOG_START);

		try {

			returnRDTO = new ReturnRDTO();

			actuationRDTO.setCode(code);
			actuationRDTO.setCodeEntity(codeEntity);
			ActuationBDTO actuationBDTO = ActuationConvert.rdto2bdto(actuationRDTO);

			returnRDTO = service.delete(actuationBDTO);

		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, actuationRDTO), ex);
			return new ResponseEntity<ReturnRDTO>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<ReturnRDTO>(returnRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/contracta/{codi}/tipusEntitat/{tipusEntitat}/codiEntitat/{codiEntitat}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta actuacions", tags = { "suportdocumental" })
	public ResponseEntity<ReturnActuationRDTO> selectActuation(
			@PathVariable(value = "codi", required = true) String codeContract,
			@PathVariable(value = "tipusEntitat", required = true) String codeTypeEntity,
			@PathVariable(value = "codiEntitat", required = true) String codeEntity,
			@RequestParam(value = "considerarAnulacions", required = false) String considerAnnulments,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestParam(required = false) String transactionId) {

		logger.info(LogsConstants.LOG_START);
		ReturnActuationRDTO returnActuationRDTO = new ReturnActuationRDTO();
		queryParameterActuationBDTO = new QueryParameterActuationBDTO();		

		try {
			queryParameterActuationBDTO.setCodeEntity(codeEntity);
			queryParameterActuationBDTO.setCodeContract(codeContract);
			queryParameterActuationBDTO.setConsiderAnnulments(considerAnnulments);
			queryParameterActuationBDTO.setCodeUser(codeUser);
			queryParameterActuationBDTO.setCodeTypeEntity(codeTypeEntity);
			
			returnActuationRDTO = service.selectActuation(queryParameterActuationBDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnActuationRDTO), ex);
			return new ResponseEntity<>(returnActuationRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnActuationRDTO, HttpStatus.OK);
	}

	
}