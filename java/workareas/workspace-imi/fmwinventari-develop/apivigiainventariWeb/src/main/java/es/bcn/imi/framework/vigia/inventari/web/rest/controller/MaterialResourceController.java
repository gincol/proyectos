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

import es.bcn.imi.framework.vigia.inventari.business.MaterialResourceService;
import es.bcn.vigia.fmw.libcommons.business.dto.MaterialResourceBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.QueryParameterBDTO;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.MaterialResourceRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnBreakdownAmortizationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMaterialResourceDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMaterialResourceVehicleDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMaterialResourceVehicleExpensesRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMaterialResourceVehiclePeriodRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.convert.MaterialResourceConvert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.opentrends.openframe.services.rest.controller.AbstractRestController;
import net.opentrends.openframe.services.rest.http.ResponseEntity;

@RestController
@RequestMapping("/recursmaterial")
@Lazy(true)
@Api("IVENTARI API")
public class MaterialResourceController extends AbstractRestController {

	private Log logger = LogFactory.getLog(this.getClass());

	@Autowired(required = true)
	@Qualifier(ServicesConstants.SRV_MATERIAL_RESOURCE)
	private MaterialResourceService service;

	private ReturnRDTO returnRDTO;

	private QueryParameterBDTO queryParameterBDTO;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Interfície d’alta d’un recurs material", tags = { "recursmaterial" })
	public ResponseEntity<ReturnRDTO> save(@RequestBody MaterialResourceRDTO materialResourceRDTO) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {

			MaterialResourceBDTO materialResourceBDTO = MaterialResourceConvert.rdto2bto(materialResourceRDTO);

			returnRDTO = service.insert(materialResourceBDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, materialResourceRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{codi}", method = RequestMethod.PUT)
	@ResponseBody
	@ApiOperation(value = "Interfície d’actualització d’un recurs material", tags = { "recursmaterial" })
	public ResponseEntity<ReturnRDTO> update(@PathVariable(value = "codi", required = true) String code,
			@RequestBody MaterialResourceRDTO materialResourceRDTO) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {

			materialResourceRDTO.setCode(code);

			MaterialResourceBDTO materialResourceBDTO = MaterialResourceConvert.rdto2bto(materialResourceRDTO);

			returnRDTO = service.update(materialResourceBDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, materialResourceRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{codi}", method = RequestMethod.DELETE)
	@ResponseBody
	@ApiOperation(value = "Interfície d’eliminació d’un recurs material", tags = { "recursmaterial" })
	public ResponseEntity<ReturnRDTO> delete(@PathVariable(value = "codi", required = true) String code, @RequestBody MaterialResourceRDTO materialResourceRDTO) {

		logger.info(LogsConstants.LOG_START);
		returnRDTO = new ReturnRDTO();
		
		try {

			materialResourceRDTO.setCode(code);
			
			MaterialResourceBDTO materialResourceBDTO = MaterialResourceConvert.rdto2bto(materialResourceRDTO);
			
			returnRDTO = service.delete(materialResourceBDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, materialResourceRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/despesa", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Interfície d'alta d'una Despesa d'un recurs material", tags = { "recursmaterial" })
	public ResponseEntity<ReturnRDTO> saveExpense(@RequestBody MaterialResourceRDTO materialResourceRDTO) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {

			MaterialResourceBDTO materialResourceBDTO = MaterialResourceConvert.rdto2bto(materialResourceRDTO);

			returnRDTO = service.insertExpense(materialResourceBDTO);

		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, materialResourceRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/amortitzacio", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Interfície d'alta d'una Base de càlcul d’Amortització d'un recurs material", tags = { "recursmaterial" })
	public ResponseEntity<ReturnRDTO> saveAmortizationBase(@RequestBody MaterialResourceRDTO materialResourceRDTO) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {

			MaterialResourceBDTO materialResourceBDTO = MaterialResourceConvert.rdto2bto(materialResourceRDTO);

			returnRDTO = service.insertAmortizationBase(materialResourceBDTO);

		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, materialResourceRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/prorrateig", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Interfície d'alta d'un prorrateig per grup de servei d'un recurs material", tags = { "recursmaterial" })
	public ResponseEntity<ReturnRDTO> saveApportionment(@RequestBody MaterialResourceRDTO materialResourceRDTO) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {

			MaterialResourceBDTO materialResourceBDTO = MaterialResourceConvert.rdto2bto(materialResourceRDTO);

			returnRDTO = service.insertApportionment(materialResourceBDTO);

		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, materialResourceRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/contracta/{codi}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta massiva de RRMM - No vehicle", tags = { "recursmaterial" })
	public ResponseEntity<ReturnMassiveRDTO> selectMassive(
			@PathVariable(value = "codi", required = true) String code,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestParam(required = false) String transactionId) {

		logger.info(LogsConstants.LOG_START);
		ReturnMassiveRDTO returnMassiveRDTO = new ReturnMassiveRDTO();
		queryParameterBDTO = new QueryParameterBDTO();		

		try {
			queryParameterBDTO.setCodeContract(code);
			queryParameterBDTO.setDateReference(dateReference);
			queryParameterBDTO.setCodeUser(codeUser);
			
			returnMassiveRDTO = service.selectMassive(queryParameterBDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnMassiveRDTO), ex);
			return new ResponseEntity<>(returnMassiveRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnMassiveRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{codi}/contracta/{codiContracta}/detall", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta individual de RRMM - No vehicle", tags = { "recursmaterial" })
	public ResponseEntity<ReturnMaterialResourceDetailedRDTO> selectDetailed(
			@PathVariable(value = "codi", required = true) String code,
			@PathVariable(value = "codiContracta", required = true) String codeContract,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestParam(required = false) String transactionId) {

		logger.info(LogsConstants.LOG_START);
		ReturnMaterialResourceDetailedRDTO returnMaterialResourceDetailedRDTO = new ReturnMaterialResourceDetailedRDTO();
		queryParameterBDTO = new QueryParameterBDTO();		

		try {
			queryParameterBDTO.setCode(code);
			queryParameterBDTO.setCodeContract(codeContract);
			queryParameterBDTO.setDateReference(dateReference);
			queryParameterBDTO.setCodeUser(codeUser);
			
			returnMaterialResourceDetailedRDTO = service.selectDetailed(queryParameterBDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnMaterialResourceDetailedRDTO), ex);
			return new ResponseEntity<>(returnMaterialResourceDetailedRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnMaterialResourceDetailedRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/vehicle/contracta/{codi}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta massiva de RRMM - Vehicle", tags = { "recursmaterial" })
	public ResponseEntity<ReturnMassiveRDTO> selectVehicleMassive(
			@PathVariable(value = "codi", required = true) String code,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestParam(required = false) String transactionId) {

		logger.info(LogsConstants.LOG_START);
		ReturnMassiveRDTO returnMassiveRDTO = new ReturnMassiveRDTO();
		queryParameterBDTO = new QueryParameterBDTO();		

		try {
			queryParameterBDTO.setCodeContract(code);
			queryParameterBDTO.setDateReference(dateReference);
			queryParameterBDTO.setCodeUser(codeUser);
			
			returnMassiveRDTO = service.selectVehicleMassive(queryParameterBDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnMassiveRDTO), ex);
			return new ResponseEntity<>(returnMassiveRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnMassiveRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/vehicle/{codi}/contracta/{codiContracta}/detall", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta individual de RRMM - Vehicle", tags = { "recursmaterial" })
	public ResponseEntity<ReturnMaterialResourceVehicleDetailedRDTO> selectVehicleDetailed(
			@PathVariable(value = "codi", required = true) String code,
			@PathVariable(value = "codiContracta", required = true) String codeContract,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestParam(required = false) String transactionId) {

		logger.info(LogsConstants.LOG_START);
		ReturnMaterialResourceVehicleDetailedRDTO returnMaterialResourceVehicleDetailedRDTO = new ReturnMaterialResourceVehicleDetailedRDTO();
		queryParameterBDTO = new QueryParameterBDTO();		

		try {
			queryParameterBDTO.setCode(code);
			queryParameterBDTO.setCodeContract(codeContract);
			queryParameterBDTO.setDateReference(dateReference);
			queryParameterBDTO.setCodeUser(codeUser);
			
			returnMaterialResourceVehicleDetailedRDTO = service.selectVehicleDetailed(queryParameterBDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnMaterialResourceVehicleDetailedRDTO), ex);
			return new ResponseEntity<>(returnMaterialResourceVehicleDetailedRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnMaterialResourceVehicleDetailedRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/vehicle/{codi}/contracta/{codiContracta}/amortitzacio", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta Desglossament d'amortització d'Instal·lació", tags = { "installacio" })
	public ResponseEntity<ReturnBreakdownAmortizationRDTO> selectAmortization(
			@PathVariable(value = "codi", required = true) String code,
			@PathVariable(value = "codiContracta", required = true) String codeContract,
			@RequestParam(value = "codiUsuari", required = true) String codeUser,
			@RequestParam(required = true) String transactionId) {

		logger.info(LogsConstants.LOG_START);
		ReturnBreakdownAmortizationRDTO returnBreakdownAmortizationRDTO = new ReturnBreakdownAmortizationRDTO();
		queryParameterBDTO = new QueryParameterBDTO();		

		try {
			queryParameterBDTO.setCode(code);
			queryParameterBDTO.setCodeContract(codeContract);
			queryParameterBDTO.setCodeUser(codeUser);
			
			returnBreakdownAmortizationRDTO = service.selectAmortization(queryParameterBDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnBreakdownAmortizationRDTO), ex);
			return new ResponseEntity<>(returnBreakdownAmortizationRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnBreakdownAmortizationRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/vehicle/{codi}/contracta/{codiContracta}/despesa", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta Desglossament de Despeses d'Instal·lació", tags = { "installacio" })
	public ResponseEntity<ReturnMaterialResourceVehicleExpensesRDTO> selectExpenses(
			@PathVariable(value = "codi", required = true) String code,
			@PathVariable(value = "codiContracta", required = true) String codeContract,
			@RequestParam(value = "codiUsuari", required = true) String codeUser,
			@RequestParam(required = true) String transactionId) {

		logger.info(LogsConstants.LOG_START);
		ReturnMaterialResourceVehicleExpensesRDTO returnMaterialResourceVehicleExpensesRDTO = new ReturnMaterialResourceVehicleExpensesRDTO();
		queryParameterBDTO = new QueryParameterBDTO();		

		try {
			queryParameterBDTO.setCode(code);
			queryParameterBDTO.setCodeContract(codeContract);
			queryParameterBDTO.setCodeUser(codeUser);
			
			returnMaterialResourceVehicleExpensesRDTO = service.selectExpenses(queryParameterBDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnMaterialResourceVehicleExpensesRDTO), ex);
			return new ResponseEntity<>(returnMaterialResourceVehicleExpensesRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnMaterialResourceVehicleExpensesRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/vehicle/{codi}/contracta/{codiContracta}/periode/{periode}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta d'Instal·lació per un període de certificació", tags = { "installacio" })
	public ResponseEntity<ReturnMaterialResourceVehiclePeriodRDTO> selectPeriod(
			@PathVariable(value = "codi", required = true) String code,
			@PathVariable(value = "codiContracta", required = true) String codeContract,
			@PathVariable(value = "periode", required = true) String period,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestParam(required = false) String transactionId) {

		logger.info(LogsConstants.LOG_START);
		ReturnMaterialResourceVehiclePeriodRDTO returnMaterialResourceVehiclePeriodRDTO = new ReturnMaterialResourceVehiclePeriodRDTO();
		queryParameterBDTO = new QueryParameterBDTO();		

		try {
			queryParameterBDTO.setCode(code);
			queryParameterBDTO.setCodeContract(codeContract);
			queryParameterBDTO.setCodeUser(codeUser);
			queryParameterBDTO.setPeriod(period);
			
			returnMaterialResourceVehiclePeriodRDTO = service.selectPeriod(queryParameterBDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnMaterialResourceVehiclePeriodRDTO), ex);
			return new ResponseEntity<>(returnMaterialResourceVehiclePeriodRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnMaterialResourceVehiclePeriodRDTO, HttpStatus.OK);
	}
}
