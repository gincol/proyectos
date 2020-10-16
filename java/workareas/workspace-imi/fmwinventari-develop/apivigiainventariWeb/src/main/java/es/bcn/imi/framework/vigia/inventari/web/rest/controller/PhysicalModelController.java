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

import es.bcn.imi.framework.vigia.inventari.business.PhysicalModelService;
import es.bcn.vigia.fmw.libcommons.business.dto.AggregateAmortizationBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.PhysicalModelBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.QueryParameterBDTO;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.AggregateAmortizationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.PhysicalModelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnPhysicalModelAmortizationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnPhysicalModelDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnPhysicalModelMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.convert.AggregateAmortizationConvert;
import es.bcn.vigia.fmw.libutils.convert.PhysicalModelConvert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.opentrends.openframe.services.rest.controller.AbstractRestController;
import net.opentrends.openframe.services.rest.http.ResponseEntity;

@RestController
@RequestMapping("/mobiliarifisic")
@Lazy(true)
@Api("IVENTARI API")
public class PhysicalModelController extends AbstractRestController {

	private Log logger = LogFactory.getLog(this.getClass());

	@Autowired(required = true)
	@Qualifier(ServicesConstants.SRV_PHYSICAL_MODEL)
	private PhysicalModelService service;

	private ReturnRDTO returnRDTO;

	private QueryParameterBDTO queryParameterBDTO;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Interfície d’alta d’un mobiliari físic", tags = { "mobiliarifisic" })
	public ResponseEntity<ReturnRDTO> save(@RequestBody PhysicalModelRDTO physicalModelRDTO) {

		logger.info(LogsConstants.LOG_START);

		try {
			
			PhysicalModelBDTO physicalModelBDTO = PhysicalModelConvert.rdto2bto(physicalModelRDTO);
			
			returnRDTO = new ReturnRDTO();

			returnRDTO = service.insert(physicalModelBDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, physicalModelRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{codi}", method = RequestMethod.PUT)
	@ResponseBody
	@ApiOperation(value = "Interfície d’actualització d’un mobiliari físic", tags = { "mobiliarifisic" })
	public ResponseEntity<ReturnRDTO> update(@PathVariable(value = "codi", required = true) String code,
			@RequestBody PhysicalModelRDTO physicalModelRDTO) {

		logger.info(LogsConstants.LOG_START);

		try {

			physicalModelRDTO.setCode(code);

			PhysicalModelBDTO physicalModelBDTO = PhysicalModelConvert.rdto2bto(physicalModelRDTO);
			
			returnRDTO = new ReturnRDTO();

			returnRDTO = service.update(physicalModelBDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, physicalModelRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{codi}", method = RequestMethod.DELETE)
	@ResponseBody
	@ApiOperation(value = "Interfície d’eliminació d’un mobiliari físic", tags = { "mobiliarifisic" })
	public ResponseEntity<ReturnRDTO> delete(@PathVariable(value = "codi", required = true) String code,
			@RequestBody PhysicalModelRDTO physicalModelRDTO) {

		logger.info(LogsConstants.LOG_START);
		returnRDTO = new ReturnRDTO();

		try {

			physicalModelRDTO.setCode(code);

			PhysicalModelBDTO physicalModelBDTO = PhysicalModelConvert.rdto2bto(physicalModelRDTO);
			returnRDTO = new ReturnRDTO();

			returnRDTO = service.delete(physicalModelBDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, physicalModelRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/tipus/amortitzacio/agregada", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Interfície d’alta d’un amortitzacio agregada", tags = { "amortitzacio_agregada" })
	public ResponseEntity<ReturnRDTO> save(@RequestBody AggregateAmortizationRDTO aggregateAmortizationRDTO) {

		logger.info(LogsConstants.LOG_START);

		try {
			
			AggregateAmortizationBDTO aggregateAmortizationBDTO = AggregateAmortizationConvert.rdto2bdto(aggregateAmortizationRDTO);
			
			returnRDTO = new ReturnRDTO();
			returnRDTO = service.insert(aggregateAmortizationBDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, aggregateAmortizationRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/contracta/{codi}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta massiva de MCFs", tags = { "mobiliarifisic" })
	public ResponseEntity<ReturnPhysicalModelMassiveRDTO> selectMassive(
			@PathVariable(value = "codi", required = true) String codeContract,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestParam(required = false) String transactionId) {

		logger.info(LogsConstants.LOG_START);
		
		ReturnPhysicalModelMassiveRDTO returnPhysicalModelMassiveRDTO = new ReturnPhysicalModelMassiveRDTO();
		queryParameterBDTO = new QueryParameterBDTO();		

		try {
			queryParameterBDTO.setCodeContract(codeContract);
			queryParameterBDTO.setDateReference(dateReference);
			queryParameterBDTO.setCodeUser(codeUser);
			
			returnPhysicalModelMassiveRDTO = service.selectMassive(queryParameterBDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnPhysicalModelMassiveRDTO), ex);
			return new ResponseEntity<>(returnPhysicalModelMassiveRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnPhysicalModelMassiveRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{codi}/contracta/{codiContracta}/detall", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta detallada de MCFs", tags = { "mobiliarifisic" })
	public ResponseEntity<ReturnPhysicalModelDetailedRDTO> selectDetailed(
			@PathVariable(value = "codi", required = true) String code,
			@PathVariable(value = "codiContracta", required = true) String codeContract,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestParam(required = false) String transactionId) {

		logger.info(LogsConstants.LOG_START);
		ReturnPhysicalModelDetailedRDTO returnPhysicalModelDetailedRDTO = new ReturnPhysicalModelDetailedRDTO();
		queryParameterBDTO = new QueryParameterBDTO();		

		try {
			queryParameterBDTO.setCode(code);
			queryParameterBDTO.setCodeContract(codeContract);
			queryParameterBDTO.setDateReference(dateReference);
			queryParameterBDTO.setCodeUser(codeUser);
			
			returnPhysicalModelDetailedRDTO = service.selectDetailed(queryParameterBDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnPhysicalModelDetailedRDTO), ex);
			return new ResponseEntity<>(returnPhysicalModelDetailedRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnPhysicalModelDetailedRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/contracta/{codi}/fraccio/{codiFraccio}/territori/{codiTerritori}/tipus/{codiTipus}/grup/{codiGrup}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta d'amortització agregada de MCFs", tags = { "mobiliarifisic" })
	public ResponseEntity<ReturnPhysicalModelAmortizationRDTO> selectAmortization(
			@PathVariable(value = "codi", required = true) String codeContract,
			@PathVariable(value = "codiFraccio", required = true) String codeFraction,
			@PathVariable(value = "codiTerritori", required = true) String codeTerritory,
			@PathVariable(value = "codiTipus", required = true) String codeType,
			@PathVariable(value = "codiGrup", required = true) String codeGroup,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestParam(required = false) String transactionId) {

		logger.info(LogsConstants.LOG_START);
		ReturnPhysicalModelAmortizationRDTO returnPhysicalModelAmortizationRDTO = new ReturnPhysicalModelAmortizationRDTO();
		queryParameterBDTO = new QueryParameterBDTO();		

		try {
			queryParameterBDTO.setCodeContract(codeContract);
			queryParameterBDTO.setCodeFraction(codeFraction);
			queryParameterBDTO.setCodeTerritory(codeTerritory);
			queryParameterBDTO.setCodeType(codeType);
			queryParameterBDTO.setCodeGroup(codeGroup);
			queryParameterBDTO.setCodeUser(codeUser);
			
			returnPhysicalModelAmortizationRDTO = service.selectAmortization(queryParameterBDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnPhysicalModelAmortizationRDTO), ex);
			return new ResponseEntity<>(returnPhysicalModelAmortizationRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnPhysicalModelAmortizationRDTO, HttpStatus.OK);
	}


}
