package es.bcn.imi.framework.vigia.frontal.web.rest.controller.inventary.sync;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import es.bcn.imi.framework.vigia.frontal.business.inventary.ActuationService;
import es.bcn.imi.framework.vigia.frontal.business.inventary.CommerceService;
import es.bcn.imi.framework.vigia.frontal.business.inventary.EmployeeCatalogService;
import es.bcn.imi.framework.vigia.frontal.business.inventary.InstallationService;
import es.bcn.imi.framework.vigia.frontal.business.inventary.LogicalModelService;
import es.bcn.imi.framework.vigia.frontal.business.inventary.MaterialResourceService;
import es.bcn.imi.framework.vigia.frontal.business.inventary.PhysicalModelService;
import es.bcn.imi.framework.vigia.frontal.business.inventary.SensorsService;
import es.bcn.imi.framework.vigia.frontal.business.inventary.UbicationService;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.constants.ValidationConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.ActuationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.AggregateAmortizationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CommerceRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.DocumentarySupportExpenseRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.DocumentarySupportRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.EmployeeCatalogRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.InstallationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.LogicalModelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.MaterialResourceRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.PhysicalModelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterActuationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterDocumentarySupportRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.SensorRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.UbicationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnActuationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnBreakdownAmortizationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnCommerceDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnCommerceElementsRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnDocumentarySupportRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnEmployeeCatalogDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnInstallationDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnInstallationExpensesRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnInstallationPeriodRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnLogicalModelDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnLogicalModelMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnLogicalModelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMaterialResourceDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMaterialResourceVehicleDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMaterialResourceVehicleExpensesRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMaterialResourceVehiclePeriodRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnPhysicalModelAmortizationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnPhysicalModelDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnPhysicalModelMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnPhysicalModelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnSensorDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnSensorMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnUbicationDetailedRDTO;
import es.bcn.vigia.fmw.libutils.Utils;
import es.bcn.vigia.fmw.libutils.enums.ReturnEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.opentrends.openframe.services.rest.controller.AbstractRestController;
import net.opentrends.openframe.services.rest.http.ResponseEntity;

@RestController
@RequestMapping("/sync/inventari")
@Lazy(true)
@Api("FRONTAL API")
public class InventaryController extends AbstractRestController {

	private Log logger = LogFactory.getLog(this.getClass());

	@Autowired(required = true)
	@Qualifier(ServicesConstants.SRV_FRONTAL_UBICATION)
	private UbicationService ubicationService;

	@Autowired(required = true)
	@Qualifier(ServicesConstants.SRV_FRONTAL_PHYSICAL_MODEL)
	private PhysicalModelService physicalModelService;

	@Autowired(required = true)
	@Qualifier(ServicesConstants.SRV_FRONTAL_LOGICAL_MODEL)
	private LogicalModelService logicalModelService;

	@Autowired(required = true)
	@Qualifier(ServicesConstants.SRV_FRONTAL_MATERIAL_RESOURCE)
	private MaterialResourceService materialResourceService;

	@Autowired(required = true)
	@Qualifier(ServicesConstants.SRV_FRONTAL_INSTALLATION)
	private InstallationService installationService;

	@Autowired(required = true)
	@Qualifier(ServicesConstants.SRV_FRONTAL_COMMERCE)
	private CommerceService commerceService;

	@Autowired(required = true)
	@Qualifier(ServicesConstants.SRV_FRONTAL_EMPLOYEE_CATALOG)
	private EmployeeCatalogService employeeCatalogService;

	@Autowired(required = true)
	@Qualifier(ServicesConstants.SRV_FRONTAL_ACTUATION)
	private ActuationService actuationService;

	@Autowired(required = true)
	@Qualifier(ServicesConstants.SRV_FRONTAL_SENSOR)
	private SensorsService sensorService;

	private ReturnRDTO returnRDTO;

	private QueryParameterRDTO queryParameterRDTO;

	private QueryParameterDocumentarySupportRDTO queryParameterDocumentarySupportRDTO;

	private QueryParameterActuationRDTO queryParameterActuationRDTO;

	private ResponseEntity<ReturnRDTO> processReturnRDTO(ReturnRDTO returnRDTO)
	{
		if ( returnRDTO.getCode().equals(ReturnEnum.CODE_ERROR_TRANSACTION_CREATE_NOT_COMPLETE.getCodeDescription()) || 
				returnRDTO.getCode().equals(ReturnEnum.CODE_ERROR_TRANSACTION_UPDATE_NOT_COMPLETE.getCodeDescription()) ||
				returnRDTO.getCode().equals(ReturnEnum.CODE_ERROR_TRANSACTION_DELETE_NOT_COMPLETE.getCodeDescription()))
		{
			logger.info(LogsConstants.LOG_END);
			return new ResponseEntity<>(returnRDTO, HttpStatus.ACCEPTED);
		}
		else if (!returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_REGISTERED.getCodeDescription())) {

			logger.error(String.format(LogsConstants.LOG_HTTP_BAD_REQUEST, returnRDTO.toString()));

			return new ResponseEntity<>(returnRDTO, HttpStatus.BAD_REQUEST);
		}else{
			logger.info(LogsConstants.LOG_END);
			return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/ubicacio", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Interfície d'alta d'una Ubicació", tags = { "ubicacio" })
	public ResponseEntity<ReturnRDTO> saveUbication(@RequestBody UbicationRDTO ubicationRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		
		try {
			ubicationRDTO.setClientId(clientId);
			returnRDTO = ubicationService.insert(ubicationRDTO);

			return processReturnRDTO(returnRDTO);

		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, ubicationRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/ubicacio/{codi}", method = RequestMethod.PUT)
	@ResponseBody
	@ApiOperation(value = "Interfície de modificació d'una Ubicació", tags = { "ubicacio" })
	public ResponseEntity<ReturnRDTO> updateUbication(@PathVariable(value = "codi", required = true) String code,
			@RequestBody UbicationRDTO ubicationRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);
		returnRDTO = new ReturnRDTO();

		try {
			ubicationRDTO.setClientId(clientId);
			ubicationRDTO.setCode(code);

			returnRDTO = ubicationService.update(ubicationRDTO);

			return processReturnRDTO(returnRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, ubicationRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		
	}

	@RequestMapping(value = "/ubicacio/{codi}/baixa", method = RequestMethod.PUT)
	@ResponseBody
	@ApiOperation(value = "Interfície de baixa d'una Ubicació", tags = { "ubicacio" })
	public ResponseEntity<ReturnRDTO> deleteUbication(@PathVariable(value = "codi", required = true) String code,
			@RequestBody UbicationRDTO ubicationRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {
			ubicationRDTO.setClientId(clientId);
			ubicationRDTO.setCode(code);

			returnRDTO = ubicationService.delete(ubicationRDTO);

			return processReturnRDTO(returnRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, ubicationRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		
	}

	@RequestMapping(value = "/ubicacio/mcl", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Interfície d'alta d'un mobiliari lògic", tags = { "mobiliarilogic" })
	public ResponseEntity<ReturnRDTO> saveLogicalModel(@RequestBody LogicalModelRDTO logicalModelRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);
		returnRDTO = new ReturnRDTO();

		try {
			logicalModelRDTO.setClientId(clientId);
			returnRDTO = logicalModelService.insert(logicalModelRDTO);

			return processReturnRDTO(returnRDTO);
			
		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, logicalModelRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		
	}

	@RequestMapping(value = "/ubicacio/{codi}/mcl/{codiMCL}", method = RequestMethod.PUT)
	@ResponseBody
	@ApiOperation(value = "Interfície de modificació d'un mobiliari lògic", tags = { "mobiliarilogic" })
	public ResponseEntity<ReturnRDTO> updateLogicalModel(@PathVariable(value = "codi", required = true) String code,
			@PathVariable(value = "codiMCL", required = true) String codeMCL,
			@RequestBody LogicalModelRDTO logicalModelRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);
		returnRDTO = new ReturnRDTO();

		try {
			logicalModelRDTO.setClientId(clientId);
			logicalModelRDTO.getUbicationRDTO().setCode(code);
			logicalModelRDTO.setCode(codeMCL);

			returnRDTO = logicalModelService.update(logicalModelRDTO);

			return processReturnRDTO(returnRDTO);
			
		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, logicalModelRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		
	}

	@RequestMapping(value = "/mcl/{codi}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta d'un mobiliari lògic", tags = { "mobiliarilogic" })
	public ResponseEntity<ReturnLogicalModelRDTO> selectLogicalModel(
			@PathVariable(value = "codi", required = true) String code,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);
		ReturnLogicalModelRDTO returnLogicalModelRDTO = new ReturnLogicalModelRDTO();
		LogicalModelRDTO logicalModelRDTOin = new LogicalModelRDTO();

		try {
			logicalModelRDTOin.setClientId(clientId);
			logicalModelRDTOin.setCode(code);

			returnLogicalModelRDTO = logicalModelService.select(logicalModelRDTOin);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnLogicalModelRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnLogicalModelRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnLogicalModelRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/ubicacio/{codi}/mcl/{codiMCL}/baixa", method = RequestMethod.PUT)
	@ResponseBody
	@ApiOperation(value = "Interfície de baixa d'un mobiliari lògic", tags = { "mobiliarilogic" })
	public ResponseEntity<ReturnRDTO> deleteLogicalModel(@PathVariable(value = "codi", required = true) String code,
			@PathVariable(value = "codiMCL", required = true) String codeMCL,
			@RequestBody LogicalModelRDTO logicalModelRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);
		returnRDTO = new ReturnRDTO();

		try {
			logicalModelRDTO.setClientId(clientId);
			logicalModelRDTO.getUbicationRDTO().setCode(code);
			logicalModelRDTO.setCode(codeMCL);
			logicalModelRDTO.setCodeState(ValidationConstants.DELETE_MCL_STATUS_CODE);

			returnRDTO = logicalModelService.delete(logicalModelRDTO);

			return processReturnRDTO(returnRDTO);
			
		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, logicalModelRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/ubicacio/mcl/mcf", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Interfície d'alta d'un mobiliari físic", tags = { "mobiliarifisic" })
	public ResponseEntity<ReturnRDTO> savePhysicalModel(@RequestBody PhysicalModelRDTO physicalModelRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);
		returnRDTO = new ReturnRDTO();

		try {
			physicalModelRDTO.setClientId(clientId);
			returnRDTO = physicalModelService.insert(physicalModelRDTO);

			return processReturnRDTO(returnRDTO);
			
		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, physicalModelRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/ubicacio/{codi}/mcl/mcf/{codiMCF}", method = RequestMethod.PUT)
	@ResponseBody
	@ApiOperation(value = "Interfície de modificació d'un mobiliari físic", tags = { "mobiliarifisic" })
	public ResponseEntity<ReturnRDTO> updatePhysicalModel(@PathVariable(value = "codi", required = true) String code,
			@PathVariable(value = "codiMCF", required = true) String codeMCF,
			@RequestBody PhysicalModelRDTO physicalModelRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {
			physicalModelRDTO.setClientId(clientId);
			physicalModelRDTO.setCodeUbication(code);
			;
			physicalModelRDTO.setCode(codeMCF);

			returnRDTO = physicalModelService.update(physicalModelRDTO);

			return processReturnRDTO(returnRDTO);
			
		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, physicalModelRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	
	}

	@RequestMapping(value = "/mcf/{codi}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta d'un mobiliari fisic", tags = { "mobiliarifisic" })
	public ResponseEntity<ReturnPhysicalModelRDTO> selectPhysicalModel(
			@PathVariable(value = "codi", required = true) String code,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		ReturnPhysicalModelRDTO returnPhysicalModelRDTO = new ReturnPhysicalModelRDTO();

		PhysicalModelRDTO physicalModelRDTOin = new PhysicalModelRDTO();

		try {
			physicalModelRDTOin.setClientId(clientId);
			physicalModelRDTOin.setCode(code);
			returnPhysicalModelRDTO = physicalModelService.select(physicalModelRDTOin);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnPhysicalModelRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnPhysicalModelRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnPhysicalModelRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/ubicacio/{codi}/mcl/mcf/{codiMCF}/baixa", method = RequestMethod.PUT)
	@ResponseBody
	@ApiOperation(value = "Interfície de baixa d'un mobiliari fisic", tags = { "mobiliarifisic" })
	public ResponseEntity<ReturnRDTO> deletePhysicalModel(@PathVariable(value = "codi", required = true) String code,
			@PathVariable(value = "codiMCF", required = true) String codeMCF,
			@RequestBody PhysicalModelRDTO physicalModelRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {
			physicalModelRDTO.setClientId(clientId);
			physicalModelRDTO.setCodeUbication(code);
			physicalModelRDTO.setCode(codeMCF);

			returnRDTO = physicalModelService.delete(physicalModelRDTO);

			return processReturnRDTO(returnRDTO);
			
		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, physicalModelRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/recursmaterial", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Interfície d'alta d'un recurs material", tags = { "recursmaterial" })
	public ResponseEntity<ReturnRDTO> saveMaterialResource(@RequestBody MaterialResourceRDTO materialResourceRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		try {
			materialResourceRDTO.setClientId(clientId);
			returnRDTO = new ReturnRDTO();

			returnRDTO = materialResourceService.insert(materialResourceRDTO);

			return processReturnRDTO(returnRDTO);
			
		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, materialResourceRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/recursmaterial/{codi}", method = RequestMethod.PUT)
	@ResponseBody
	@ApiOperation(value = "Interfície de modificació d'un recurs material", tags = { "recursmaterial" })
	public ResponseEntity<ReturnRDTO> updateMaterialResource(@PathVariable(value = "codi", required = true) String code,
			@RequestBody MaterialResourceRDTO materialResourceRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {
			materialResourceRDTO.setClientId(clientId);
			materialResourceRDTO.setCode(code);

			returnRDTO = materialResourceService.update(materialResourceRDTO);

			return processReturnRDTO(returnRDTO);
			
		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, materialResourceRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		
	}

	@RequestMapping(value = "/recursmaterial/{codi}/baixa", method = RequestMethod.PUT)
	@ResponseBody
	@ApiOperation(value = "Interfície de baixa d'un recurs material", tags = { "recursmaterial" })
	public ResponseEntity<ReturnRDTO> deleteMaterialResource(@PathVariable(value = "codi", required = true) String code,
			@RequestBody MaterialResourceRDTO materialResourceRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {
		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {
			materialResourceRDTO.setClientId(clientId);
			materialResourceRDTO.setCode(code);

			returnRDTO = materialResourceService.delete(materialResourceRDTO);

			return processReturnRDTO(returnRDTO);
			
		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, materialResourceRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/mcf/tipus/amortitzacio/agregada", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Interfície d'alta d'Amortització agregada d'un tipus de MCF", tags = { "amortitzacio" })
	public ResponseEntity<ReturnRDTO> saveAggregateAmortitzation(
			@RequestBody AggregateAmortizationRDTO aggregateAmortizationRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {
			aggregateAmortizationRDTO.setClientId(clientId);
			returnRDTO = physicalModelService.insert(aggregateAmortizationRDTO);

			return processReturnRDTO(returnRDTO);
			
		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, aggregateAmortizationRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		
	}

	@RequestMapping(path = "/installacio", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Interfície d'alta d'una Installacio", tags = { "installacio" })
	public ResponseEntity<ReturnRDTO> saveInstallation(@RequestBody InstallationRDTO installationRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {
			installationRDTO.setClientId(clientId);
			returnRDTO = installationService.insert(installationRDTO);

			return processReturnRDTO(returnRDTO);
			
		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, installationRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		
	}

	@RequestMapping(path = "/installacio/{codi}", method = RequestMethod.PUT)
	@ResponseBody
	@ApiOperation(value = "Interfície de modificació d'una Installacio", tags = { "installacio" })
	public ResponseEntity<ReturnRDTO> updateInstallation(@PathVariable(value = "codi", required = true) String code,
			@RequestBody InstallationRDTO installationRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);
		returnRDTO = new ReturnRDTO();

		try {
			installationRDTO.setClientId(clientId);
			installationRDTO.setCode(code);

			returnRDTO = installationService.update(installationRDTO);

			return processReturnRDTO(returnRDTO);
			
		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, installationRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/installacio/{codi}/baixa", method = RequestMethod.PUT)
	@ResponseBody
	@ApiOperation(value = "Interfície de baixa d'una Instal·lació", tags = { "installacions" })
	public ResponseEntity<ReturnRDTO> deleteInstallation(@PathVariable(value = "codi", required = true) String code,
			@RequestBody InstallationRDTO installationRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);
		returnRDTO = new ReturnRDTO();

		try {
			installationRDTO.setClientId(clientId);
			installationRDTO.setCode(code);
			returnRDTO = installationService.delete(installationRDTO);

			return processReturnRDTO(returnRDTO);
			
		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, installationRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/contracta/{codi}/installacio/{codiInstallacio}/detallada", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta detallada d'Instal·lació", tags = { "installacions" })
	public ResponseEntity<ReturnInstallationDetailedRDTO> selectInstallationDetailed(
			@PathVariable(value = "codiInstallacio", required = true) String code,
			@PathVariable(value = "codi", required = true) String codeContract,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);
		ReturnInstallationDetailedRDTO returnInstallationDetailedRDTO = new ReturnInstallationDetailedRDTO();
		queryParameterRDTO = new QueryParameterRDTO();

		try {
			queryParameterRDTO.setClientId(clientId);
			queryParameterRDTO.setCode(code);
			queryParameterRDTO.setCodeContract(codeContract);
			queryParameterRDTO.setDateReference(dateReference);
			queryParameterRDTO.setCodeUser(codeUser);

			returnInstallationDetailedRDTO = installationService.selectDetailed(queryParameterRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnInstallationDetailedRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			returnInstallationDetailedRDTO.setReturnRDTO(returnRDTO);
			return new ResponseEntity<>(returnInstallationDetailedRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnInstallationDetailedRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/contracta/{codi}/installacio", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta massiva d'Instal·lació", tags = { "installacions" })
	public ResponseEntity<ReturnMassiveRDTO> selectInstallationMassive(
			@PathVariable(value = "codi", required = true) String codeContract,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);
		ReturnMassiveRDTO returnMassiveRDTO = new ReturnMassiveRDTO();
		queryParameterRDTO = new QueryParameterRDTO();

		try {
			queryParameterRDTO.setClientId(clientId);
			queryParameterRDTO.setCodeContract(codeContract);
			queryParameterRDTO.setDateReference(dateReference);
			queryParameterRDTO.setCodeUser(codeUser);

			returnMassiveRDTO = installationService.selectMassive(queryParameterRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnMassiveRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			returnMassiveRDTO.setReturnRDTO(returnRDTO);
			return new ResponseEntity<>(returnMassiveRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnMassiveRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/contracta/{codi}/installacio/{codiInstallacio}/amortitzacio", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta Desglossament d'amortització d'Instal·lació", tags = {
			"installacions" })
	public ResponseEntity<ReturnBreakdownAmortizationRDTO> selectInstallationAmortization(
			@PathVariable(value = "codiInstallacio", required = true) String code,
			@PathVariable(value = "codi", required = true) String codeContract,
			@RequestParam(value = "codiUsuari", required = true) String codeUser,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);
		ReturnBreakdownAmortizationRDTO returnBreakdownAmortizationRDTO = new ReturnBreakdownAmortizationRDTO();
		queryParameterRDTO = new QueryParameterRDTO();

		try {
			queryParameterRDTO.setClientId(clientId);
			queryParameterRDTO.setCode(code);
			queryParameterRDTO.setCodeContract(codeContract);
			queryParameterRDTO.setCodeUser(codeUser);

			returnBreakdownAmortizationRDTO = installationService.selectAmortization(queryParameterRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnBreakdownAmortizationRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			returnBreakdownAmortizationRDTO.setReturnRDTO(returnRDTO);
			return new ResponseEntity<>(returnBreakdownAmortizationRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnBreakdownAmortizationRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/contracta/{codi}/installacio/{codiInstallacio}/despeses", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta Desglossament de Despeses d'Instal·lació", tags = { "installacions" })
	public ResponseEntity<ReturnInstallationExpensesRDTO> selectInstallationExpenses(
			@PathVariable(value = "codiInstallacio", required = true) String code,
			@PathVariable(value = "codi", required = true) String codeContract,
			@RequestParam(value = "codiUsuari", required = true) String codeUser,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);
		ReturnInstallationExpensesRDTO returnInstallationExpensesRDTO = new ReturnInstallationExpensesRDTO();
		queryParameterRDTO = new QueryParameterRDTO();

		try {
			queryParameterRDTO.setClientId(clientId);
			queryParameterRDTO.setCode(code);
			queryParameterRDTO.setCodeContract(codeContract);
			queryParameterRDTO.setCodeUser(codeUser);

			returnInstallationExpensesRDTO = installationService.selectExpenses(queryParameterRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnInstallationExpensesRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			returnInstallationExpensesRDTO.setReturnRDTO(returnRDTO);
			return new ResponseEntity<>(returnInstallationExpensesRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnInstallationExpensesRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/contracta/{codi}/installacio/{codiInstallacio}/periode/{periode}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta d'Instal·lació per un període de certificació", tags = {
			"installacions" })
	public ResponseEntity<ReturnInstallationPeriodRDTO> selectInstallationPeriod(
			@PathVariable(value = "codiInstallacio", required = true) String code,
			@PathVariable(value = "codi", required = true) String codeContract,
			@PathVariable(value = "periode", required = true) String period,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);
		ReturnInstallationPeriodRDTO returnInstallationPeriodRDTO = new ReturnInstallationPeriodRDTO();
		queryParameterRDTO = new QueryParameterRDTO();

		try {
			queryParameterRDTO.setClientId(clientId);
			queryParameterRDTO.setCode(code);
			queryParameterRDTO.setCodeContract(codeContract);
			queryParameterRDTO.setCodeUser(codeUser);
			queryParameterRDTO.setPeriod(period);

			returnInstallationPeriodRDTO = installationService.selectPeriod(queryParameterRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnInstallationPeriodRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			returnInstallationPeriodRDTO.setReturnRDTO(returnRDTO);
			return new ResponseEntity<>(returnInstallationPeriodRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnInstallationPeriodRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/installacio/despeses", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Interfície d'alta despess de Instal·lació", tags = { "despese" })
	public ResponseEntity<ReturnRDTO> saveInstallationExpense(@RequestBody InstallationRDTO installationRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {
			installationRDTO.setClientId(clientId);
			returnRDTO = installationService.insertExpense(installationRDTO);

			return processReturnRDTO(returnRDTO);
			
		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, installationRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/installacio/amortitzacio", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Interfície d'alta amortitzacio de Instal·lació", tags = { "amortitzacio" })
	public ResponseEntity<ReturnRDTO> saveInstallationAmortizationBase(@RequestBody InstallationRDTO installationRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {
			installationRDTO.setClientId(clientId);
			returnRDTO = installationService.insertAmortizationBase(installationRDTO);

			return processReturnRDTO(returnRDTO);
			
		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, installationRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/installacio/prorrateig", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Interfície d'alta prorrateig de Instal·lació", tags = { "prorrateig" })
	public ResponseEntity<ReturnRDTO> saveInstallationApportionment(@RequestBody InstallationRDTO installationRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {
			installationRDTO.setClientId(clientId);
			returnRDTO = installationService.insertApportionment(installationRDTO);

			return processReturnRDTO(returnRDTO);
			
		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, installationRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(path = "/comerc", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Interfície d'alta d'un comerç", tags = { "comercos" })
	public ResponseEntity<ReturnRDTO> saveCommerce(@RequestBody CommerceRDTO commerceRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {
			commerceRDTO.setClientId(clientId);
			returnRDTO = commerceService.insert(commerceRDTO);

			return processReturnRDTO(returnRDTO);
			
		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, commerceRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	@RequestMapping(path = "/comerc/{code}", method = RequestMethod.PUT)
	@ResponseBody
	@ApiOperation(value = "Interfície de modificació d'un comerç", tags = { "comercos" })
	public ResponseEntity<ReturnRDTO> updateCommerce(@PathVariable(required = true) String code,
			@RequestBody CommerceRDTO commerceRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);
		returnRDTO = new ReturnRDTO();

		try {
			commerceRDTO.setClientId(clientId);
			commerceRDTO.setCode(code);

			returnRDTO = commerceService.update(commerceRDTO);

			return processReturnRDTO(returnRDTO);
			
		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, commerceRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		
	}

	@RequestMapping(value = "/comerc/{code}/baixa", method = RequestMethod.PUT)
	@ResponseBody
	@ApiOperation(value = "Interfície de baixa d'un comerç", tags = { "comercos" })
	public ResponseEntity<ReturnRDTO> deleteCommerce(@PathVariable(required = true) String code,
			@RequestBody CommerceRDTO commerceRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);
		returnRDTO = new ReturnRDTO();

		try {
			commerceRDTO.setClientId(clientId);
			commerceRDTO.setCode(code);

			returnRDTO = commerceService.delete(commerceRDTO);

			return processReturnRDTO(returnRDTO);
			
		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, commerceRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(path = "/comerc/elements", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Interfície d'alta d'un elements de comerç", tags = { "comercos" })
	public ResponseEntity<ReturnRDTO> saveCommerceElements(@RequestBody CommerceRDTO commerceRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {
			commerceRDTO.setClientId(clientId);
			returnRDTO = commerceService.insertElements(commerceRDTO);

			return processReturnRDTO(returnRDTO);
			
		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, commerceRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/contracta/{codi}/comerc", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta massiva de Comerços", tags = { "comercos" })
	public ResponseEntity<ReturnMassiveRDTO> selectCommerceMassive(
			@PathVariable(value = "codi", required = true) String codeContract,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);
		ReturnMassiveRDTO returnMassiveRDTO = new ReturnMassiveRDTO();
		queryParameterRDTO = new QueryParameterRDTO();

		try {
			queryParameterRDTO.setClientId(clientId);
			queryParameterRDTO.setCodeContract(codeContract);
			queryParameterRDTO.setDateReference(dateReference);
			queryParameterRDTO.setCodeUser(codeUser);

			returnMassiveRDTO = commerceService.selectMassive(queryParameterRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnMassiveRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			returnMassiveRDTO.setReturnRDTO(returnRDTO);
			return new ResponseEntity<>(returnMassiveRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnMassiveRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/contracta/{codi}/comerc/{codiComerc}/detall", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta individual d'un Comerç", tags = { "comercos" })
	public ResponseEntity<ReturnCommerceDetailedRDTO> selectCommerceDetailed(
			@PathVariable(value = "codiComerc", required = true) String code,
			@PathVariable(value = "codi", required = true) String codeContract,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);
		ReturnCommerceDetailedRDTO returnCommerceDetailedRDTO = new ReturnCommerceDetailedRDTO();
		queryParameterRDTO = new QueryParameterRDTO();

		try {
			queryParameterRDTO.setClientId(clientId);
			queryParameterRDTO.setCode(code);
			queryParameterRDTO.setCodeContract(codeContract);
			queryParameterRDTO.setDateReference(dateReference);
			queryParameterRDTO.setCodeUser(codeUser);

			returnCommerceDetailedRDTO = commerceService.selectDetailed(queryParameterRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnCommerceDetailedRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			returnCommerceDetailedRDTO.setReturnRDTO(returnRDTO);
			return new ResponseEntity<>(returnCommerceDetailedRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnCommerceDetailedRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/contracta/{codi}/comerc/{codiComerc}/elements", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta d'Elements d'un Comerç", tags = { "comercos" })
	public ResponseEntity<ReturnCommerceElementsRDTO> selectCommerceElements(
			@PathVariable(value = "codi", required = true) String codeContract,
			@PathVariable(value = "codiComerc", required = true) String code,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);
		ReturnCommerceElementsRDTO returnCommerceElementsRDTO = new ReturnCommerceElementsRDTO();
		queryParameterRDTO = new QueryParameterRDTO();

		try {
			queryParameterRDTO.setClientId(clientId);
			queryParameterRDTO.setCode(code);
			queryParameterRDTO.setCodeContract(codeContract);
			queryParameterRDTO.setDateReference(dateReference);
			queryParameterRDTO.setCodeUser(codeUser);

			returnCommerceElementsRDTO = commerceService.selectElements(queryParameterRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnCommerceElementsRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			returnCommerceElementsRDTO.setReturnRDTO(returnRDTO);
			return new ResponseEntity<>(returnCommerceElementsRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnCommerceElementsRDTO, HttpStatus.OK);
	}

	@RequestMapping(path = "/recursoshumans/cataleg/personal/treballador", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Interfície d'alta de treballadors en el catàleg de personal", tags = { "treballador" })
	public ResponseEntity<ReturnRDTO> saveEmployee(@RequestBody EmployeeCatalogRDTO employeeCatalogRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {
			employeeCatalogRDTO.setClientId(clientId);
			returnRDTO = employeeCatalogService.insert(employeeCatalogRDTO);

			return processReturnRDTO(returnRDTO);
			
		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, employeeCatalogRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/contracta/{codi}/recursoshumans/cataleg/personal/treballador", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta massiva del catàleg del personal", tags = { "treballador" })
	public ResponseEntity<ReturnMassiveRDTO> selectEmployeeCatalogMassive(
			@PathVariable(value = "codi", required = true) String codeContract,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);
		ReturnMassiveRDTO returnMassiveRDTO = new ReturnMassiveRDTO();
		queryParameterRDTO = new QueryParameterRDTO();

		try {
			queryParameterRDTO.setClientId(clientId);
			queryParameterRDTO.setCodeContract(codeContract);
			queryParameterRDTO.setDateReference(dateReference);
			queryParameterRDTO.setCodeUser(codeUser);

			returnMassiveRDTO = employeeCatalogService.selectMassive(queryParameterRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnMassiveRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			returnMassiveRDTO.setReturnRDTO(returnRDTO);
			return new ResponseEntity<>(returnMassiveRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnMassiveRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/contracta/{codi}/recursoshumans/cataleg/personal/treballador/detall", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta detallada del catàleg del personal", tags = { "treballador" })
	public ResponseEntity<ReturnEmployeeCatalogDetailedRDTO> selectEmployeeCatalogDetailed(
			@PathVariable(value = "codi", required = true) String codeContract,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);
		ReturnEmployeeCatalogDetailedRDTO returnEmployeeCatalogDetailedRDTO = new ReturnEmployeeCatalogDetailedRDTO();
		queryParameterRDTO = new QueryParameterRDTO();

		try {
			queryParameterRDTO.setClientId(clientId);
			queryParameterRDTO.setCodeContract(codeContract);
			queryParameterRDTO.setDateReference(dateReference);
			queryParameterRDTO.setCodeUser(codeUser);

			returnEmployeeCatalogDetailedRDTO = employeeCatalogService.selectDetailed(queryParameterRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnEmployeeCatalogDetailedRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			returnEmployeeCatalogDetailedRDTO.setReturnRDTO(returnRDTO);
			return new ResponseEntity<>(returnEmployeeCatalogDetailedRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnEmployeeCatalogDetailedRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/contracta/{codi}/recursmaterial", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta massiva de RRMM - No vehicle", tags = { "recursmaterial" })
	public ResponseEntity<ReturnMassiveRDTO> selectMaterialResourceMassive(
			@PathVariable(value = "codi", required = true) String codeContract,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);
		ReturnMassiveRDTO returnMassiveRDTO = new ReturnMassiveRDTO();
		queryParameterRDTO = new QueryParameterRDTO();

		try {
			queryParameterRDTO.setClientId(clientId);
			queryParameterRDTO.setCodeContract(codeContract);
			queryParameterRDTO.setDateReference(dateReference);
			queryParameterRDTO.setCodeUser(codeUser);

			returnMassiveRDTO = materialResourceService.selectMassive(queryParameterRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnMassiveRDTO), ex);
			return new ResponseEntity<>(returnMassiveRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnMassiveRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/contracta/{codi}/recursmaterial/{codiRRMM}/detall", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta detallada de RRMM - No vehicle", tags = { "recursmaterial" })
	public ResponseEntity<ReturnMaterialResourceDetailedRDTO> selectMaterialResourceDetailed(
			@PathVariable(value = "codiRRMM", required = true) String code,
			@PathVariable(value = "codi", required = true) String codeContract,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);
		ReturnMaterialResourceDetailedRDTO returnMaterialResourceDetailedRDTO = new ReturnMaterialResourceDetailedRDTO();
		queryParameterRDTO = new QueryParameterRDTO();

		try {
			queryParameterRDTO.setClientId(clientId);
			queryParameterRDTO.setCode(code);
			queryParameterRDTO.setCodeContract(codeContract);
			queryParameterRDTO.setDateReference(dateReference);
			queryParameterRDTO.setCodeUser(codeUser);

			returnMaterialResourceDetailedRDTO = materialResourceService.selectDetailed(queryParameterRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnMaterialResourceDetailedRDTO), ex);
			return new ResponseEntity<>(returnMaterialResourceDetailedRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnMaterialResourceDetailedRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/contracta/{codi}/recursmaterial/vehicle", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta massiva de RRMM - Vehicle", tags = { "recursmaterial" })
	public ResponseEntity<ReturnMassiveRDTO> selectMaterialResourceVehicleMassive(
			@PathVariable(value = "codi", required = true) String codeContract,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);
		ReturnMassiveRDTO returnMassiveRDTO = new ReturnMassiveRDTO();
		queryParameterRDTO = new QueryParameterRDTO();

		try {
			queryParameterRDTO.setClientId(clientId);
			queryParameterRDTO.setCodeContract(codeContract);
			queryParameterRDTO.setDateReference(dateReference);
			queryParameterRDTO.setCodeUser(codeUser);

			returnMassiveRDTO = materialResourceService.selectVehicleMassive(queryParameterRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnMassiveRDTO), ex);
			return new ResponseEntity<>(returnMassiveRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnMassiveRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/contracta/{codi}/recursmaterial/vehicle/{codiRRMM}/detall", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta detallada de RRMM - Vehicle", tags = { "recursmaterial" })
	public ResponseEntity<ReturnMaterialResourceVehicleDetailedRDTO> selectMaterialResourceVehicleDetailed(
			@PathVariable(value = "codiRRMM", required = true) String code,
			@PathVariable(value = "codi", required = true) String codeContract,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);
		ReturnMaterialResourceVehicleDetailedRDTO returnMaterialResourceVehicleDetailedRDTO = new ReturnMaterialResourceVehicleDetailedRDTO();
		queryParameterRDTO = new QueryParameterRDTO();

		try {
			queryParameterRDTO.setClientId(clientId);
			queryParameterRDTO.setCode(code);
			queryParameterRDTO.setCodeContract(codeContract);
			queryParameterRDTO.setDateReference(dateReference);
			queryParameterRDTO.setCodeUser(codeUser);

			returnMaterialResourceVehicleDetailedRDTO = materialResourceService
					.selectVehicleDetailed(queryParameterRDTO);

		} catch (Exception ex) {
			logger.error(
					String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnMaterialResourceVehicleDetailedRDTO),
					ex);
			return new ResponseEntity<>(returnMaterialResourceVehicleDetailedRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnMaterialResourceVehicleDetailedRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/recursmaterial/despeses", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Interfície d'alta despess de recurs material", tags = { "despese" })
	public ResponseEntity<ReturnRDTO> saveMaterialResourceExpense(
			@RequestBody MaterialResourceRDTO materialResourceRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {
			materialResourceRDTO.setClientId(clientId);
			returnRDTO = materialResourceService.insertExpense(materialResourceRDTO);

			return processReturnRDTO(returnRDTO);
			
		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, materialResourceRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/recursmaterial/amortitzacio", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Interfície d'alta amortitzacio de recurs material", tags = { "amortitzacio" })
	public ResponseEntity<ReturnRDTO> saveMaterialResourceAmortizationBase(
			@RequestBody MaterialResourceRDTO materialResourceRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {
			materialResourceRDTO.setClientId(clientId);
			returnRDTO = materialResourceService.insertAmortizationBase(materialResourceRDTO);

			return processReturnRDTO(returnRDTO);
			
		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, materialResourceRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/recursmaterial/prorrateig", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Interfície d'alta prorrateig de recurs material", tags = { "prorrateig" })
	public ResponseEntity<ReturnRDTO> saveMaterialResourceApportionment(
			@RequestBody MaterialResourceRDTO materialResourceRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {
			materialResourceRDTO.setClientId(clientId);
			returnRDTO = materialResourceService.insertApportionment(materialResourceRDTO);

			return processReturnRDTO(returnRDTO);
			
		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, materialResourceRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/contracta/{codi}/recursmaterial/vehicle/{codiRRMM}/amortitzacio", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta Desglossament d'amortització de Recurs Material Vehicle", tags = {
			"recursmaterial" })
	public ResponseEntity<ReturnBreakdownAmortizationRDTO> selectMaterialResourceVehicleAmortization(
			@PathVariable(value = "codiRRMM", required = true) String code,
			@PathVariable(value = "codi", required = true) String codeContract,
			@RequestParam(value = "codiUsuari", required = true) String codeUser,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);
		ReturnBreakdownAmortizationRDTO returnBreakdownAmortizationRDTO = new ReturnBreakdownAmortizationRDTO();
		queryParameterRDTO = new QueryParameterRDTO();

		try {
			queryParameterRDTO.setClientId(clientId);
			queryParameterRDTO.setCode(code);
			queryParameterRDTO.setCodeContract(codeContract);
			queryParameterRDTO.setCodeUser(codeUser);

			returnBreakdownAmortizationRDTO = materialResourceService.selectAmortization(queryParameterRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnBreakdownAmortizationRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			returnBreakdownAmortizationRDTO.setReturnRDTO(returnRDTO);
			return new ResponseEntity<>(returnBreakdownAmortizationRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnBreakdownAmortizationRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/contracta/{codi}/recursmaterial/vehicle/{codiRRMM}/despeses", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta Desglossament de Despeses de Recurs Material Vehicle", tags = {
			"recursmaterial" })
	public ResponseEntity<ReturnMaterialResourceVehicleExpensesRDTO> selectMaterialResourceVehicleExpenses(
			@PathVariable(value = "codiRRMM", required = true) String code,
			@PathVariable(value = "codi", required = true) String codeContract,
			@RequestParam(value = "codiUsuari", required = true) String codeUser,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);
		ReturnMaterialResourceVehicleExpensesRDTO returnMaterialResourceVehicleExpensesRDTO = new ReturnMaterialResourceVehicleExpensesRDTO();
		queryParameterRDTO = new QueryParameterRDTO();

		try {
			queryParameterRDTO.setClientId(clientId);
			queryParameterRDTO.setCode(code);
			queryParameterRDTO.setCodeContract(codeContract);
			queryParameterRDTO.setCodeUser(codeUser);

			returnMaterialResourceVehicleExpensesRDTO = materialResourceService.selectExpenses(queryParameterRDTO);

		} catch (Exception ex) {
			logger.error(
					String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnMaterialResourceVehicleExpensesRDTO),
					ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			returnMaterialResourceVehicleExpensesRDTO.setReturnRDTO(returnRDTO);
			return new ResponseEntity<>(returnMaterialResourceVehicleExpensesRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnMaterialResourceVehicleExpensesRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/contracta/{codi}/recursmaterial/vehicle/{codiRRMM}/periode/{periode}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta de RRMM - Vehicle per un període de certificació", tags = {
			"recursmaterial" })
	public ResponseEntity<ReturnMaterialResourceVehiclePeriodRDTO> selectMaterialResourceVehiclePeriod(
			@PathVariable(value = "codiRRMM", required = true) String code,
			@PathVariable(value = "codi", required = true) String codeContract,
			@PathVariable(value = "periode", required = true) String period,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);
		ReturnMaterialResourceVehiclePeriodRDTO returnMaterialResourceVehiclePeriodRDTO = new ReturnMaterialResourceVehiclePeriodRDTO();
		queryParameterRDTO = new QueryParameterRDTO();

		try {
			queryParameterRDTO.setClientId(clientId);
			queryParameterRDTO.setCode(code);
			queryParameterRDTO.setCodeContract(codeContract);
			queryParameterRDTO.setCodeUser(codeUser);
			queryParameterRDTO.setPeriod(period);

			returnMaterialResourceVehiclePeriodRDTO = materialResourceService.selectPeriod(queryParameterRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnMaterialResourceVehiclePeriodRDTO),
					ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			returnMaterialResourceVehiclePeriodRDTO.setReturnRDTO(returnRDTO);
			return new ResponseEntity<>(returnMaterialResourceVehiclePeriodRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnMaterialResourceVehiclePeriodRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/contracta/{codi}/ubicacio", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta massiva d'ubicacions", tags = { "ubicacio" })
	public ResponseEntity<ReturnMassiveRDTO> selectUbicationMassive(
			@PathVariable(value = "codi", required = true) String codeContract,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);
		ReturnMassiveRDTO returnMassiveRDTO = new ReturnMassiveRDTO();
		queryParameterRDTO = new QueryParameterRDTO();

		try {
			queryParameterRDTO.setClientId(clientId);
			queryParameterRDTO.setCodeContract(codeContract);
			queryParameterRDTO.setDateReference(dateReference);
			queryParameterRDTO.setCodeUser(codeUser);

			returnMassiveRDTO = ubicationService.selectMassive(queryParameterRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnMassiveRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			returnMassiveRDTO.setReturnRDTO(returnRDTO);
			return new ResponseEntity<>(returnMassiveRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnMassiveRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/contracta/{codi}/ubicacio/{codiUbicacio}/detall", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta detallada d'ubicacions", tags = { "ubicacio" })
	public ResponseEntity<ReturnUbicationDetailedRDTO> selectUbicationDetailed(
			@PathVariable(value = "codiUbicacio", required = true) String code,
			@PathVariable(value = "codi", required = true) String codeContract,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);
		ReturnUbicationDetailedRDTO returnUbicationDetailedRDTO = new ReturnUbicationDetailedRDTO();
		queryParameterRDTO = new QueryParameterRDTO();

		try {
			queryParameterRDTO.setClientId(clientId);
			queryParameterRDTO.setCode(code);
			queryParameterRDTO.setCodeContract(codeContract);
			queryParameterRDTO.setDateReference(dateReference);
			queryParameterRDTO.setCodeUser(codeUser);

			returnUbicationDetailedRDTO = ubicationService.selectDetailed(queryParameterRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnUbicationDetailedRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			returnUbicationDetailedRDTO.setReturnRDTO(returnRDTO);
			return new ResponseEntity<>(returnUbicationDetailedRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnUbicationDetailedRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/contracta/{codi}/mobiliarilogic", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta massiva de MCLs", tags = { "mobiliarilogic" })
	public ResponseEntity<ReturnLogicalModelMassiveRDTO> selectMclMassive(
			@PathVariable(value = "codi", required = true) String codeContract,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);
		ReturnLogicalModelMassiveRDTO returnLogicalModelMassiveRDTO = new ReturnLogicalModelMassiveRDTO();
		queryParameterRDTO = new QueryParameterRDTO();

		try {
			queryParameterRDTO.setClientId(clientId);
			queryParameterRDTO.setCodeContract(codeContract);
			queryParameterRDTO.setDateReference(dateReference);
			queryParameterRDTO.setCodeUser(codeUser);

			returnLogicalModelMassiveRDTO = logicalModelService.selectMassive(queryParameterRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnLogicalModelMassiveRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			returnLogicalModelMassiveRDTO.setReturnRDTO(returnRDTO);
			return new ResponseEntity<>(returnLogicalModelMassiveRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnLogicalModelMassiveRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/contracta/{codi}/mobiliarilogic/{codiMCL}/detall", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta detallada de MCLs", tags = { "mobiliarilogic" })
	public ResponseEntity<ReturnLogicalModelDetailedRDTO> selectLogicalModelDetailed(
			@PathVariable(value = "codiMCL", required = true) String code,
			@PathVariable(value = "codi", required = true) String codeContract,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);
		ReturnLogicalModelDetailedRDTO returnLogicalModelDetailedRDTO = new ReturnLogicalModelDetailedRDTO();
		queryParameterRDTO = new QueryParameterRDTO();

		try {
			queryParameterRDTO.setClientId(clientId);
			queryParameterRDTO.setCode(code);
			queryParameterRDTO.setCodeContract(codeContract);
			queryParameterRDTO.setDateReference(dateReference);
			queryParameterRDTO.setCodeUser(codeUser);

			returnLogicalModelDetailedRDTO = logicalModelService.selectDetailed(queryParameterRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnLogicalModelDetailedRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			returnLogicalModelDetailedRDTO.setReturnRDTO(returnRDTO);
			return new ResponseEntity<>(returnLogicalModelDetailedRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnLogicalModelDetailedRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/contracta/{codi}/mobiliarifisic", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta massiva de MCFs", tags = { "mobiliarifisic" })
	public ResponseEntity<ReturnPhysicalModelMassiveRDTO> selectPhysicalModelMassive(
			@PathVariable(value = "codi", required = true) String codeContract,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);
		ReturnPhysicalModelMassiveRDTO returnPhysicalModelMassiveRDTO = new ReturnPhysicalModelMassiveRDTO();
		queryParameterRDTO = new QueryParameterRDTO();

		try {
			queryParameterRDTO.setClientId(clientId);
			queryParameterRDTO.setCodeContract(codeContract);
			queryParameterRDTO.setDateReference(dateReference);
			queryParameterRDTO.setCodeUser(codeUser);

			returnPhysicalModelMassiveRDTO = physicalModelService.selectMassive(queryParameterRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnPhysicalModelMassiveRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			returnPhysicalModelMassiveRDTO.setReturnRDTO(returnRDTO);
			return new ResponseEntity<>(returnPhysicalModelMassiveRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnPhysicalModelMassiveRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/contracta/{codi}/mobiliarifisic/{codiMCF}/detall", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta detallada de MCFs", tags = { "mobiliarifisic" })
	public ResponseEntity<ReturnPhysicalModelDetailedRDTO> selectPhysicalModelDetailed(
			@PathVariable(value = "codiMCF", required = true) String code,
			@PathVariable(value = "codi", required = true) String codeContract,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);
		ReturnPhysicalModelDetailedRDTO returnPhysicalModelDetailedRDTO = new ReturnPhysicalModelDetailedRDTO();
		queryParameterRDTO = new QueryParameterRDTO();

		try {
			queryParameterRDTO.setClientId(clientId);
			queryParameterRDTO.setCode(code);
			queryParameterRDTO.setCodeContract(codeContract);
			queryParameterRDTO.setDateReference(dateReference);
			queryParameterRDTO.setCodeUser(codeUser);

			returnPhysicalModelDetailedRDTO = physicalModelService.selectDetailed(queryParameterRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnPhysicalModelDetailedRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			returnPhysicalModelDetailedRDTO.setReturnRDTO(returnRDTO);
			return new ResponseEntity<>(returnPhysicalModelDetailedRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnPhysicalModelDetailedRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/contracta/{codi}/mobiliarifisic/amortitzacio/fraccio/{codiFraccio}/territori/{codiTerritori}/tipus/{codiTipus}/grup/{codiGrup}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta d'amortització agregada de MCF", tags = { "mobiliarifisic" })
	public ResponseEntity<ReturnPhysicalModelAmortizationRDTO> selectPhysicalModelAmortization(
			@PathVariable(value = "codi", required = true) String codeContract,
			@PathVariable(value = "codiFraccio", required = true) String codeFraction,
			@PathVariable(value = "codiTerritori", required = true) String codeTerritory,
			@PathVariable(value = "codiTipus", required = true) String codeType,
			@PathVariable(value = "codiGrup", required = true) String codeGroup,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);
		ReturnPhysicalModelAmortizationRDTO returnPhysicalModelAmortizationRDTO = new ReturnPhysicalModelAmortizationRDTO();
		queryParameterRDTO = new QueryParameterRDTO();

		try {
			queryParameterRDTO.setClientId(clientId);
			queryParameterRDTO.setCodeContract(codeContract);
			queryParameterRDTO.setCodeFraction(codeFraction);
			queryParameterRDTO.setCodeTerritory(codeTerritory);
			queryParameterRDTO.setCodeType(codeType);
			queryParameterRDTO.setCodeGroup(codeGroup);
			queryParameterRDTO.setCodeUser(codeUser);

			returnPhysicalModelAmortizationRDTO = physicalModelService.selectAmortization(queryParameterRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnPhysicalModelAmortizationRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			returnPhysicalModelAmortizationRDTO.setReturnRDTO(returnRDTO);
			return new ResponseEntity<>(returnPhysicalModelAmortizationRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnPhysicalModelAmortizationRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/ubicacio/suportdocumental", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ApiOperation(value = "Interfície d'alta de suport documental de Ubicació", tags = {
			"ubicacio" }, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseBody
	public ResponseEntity<ReturnRDTO> saveDocumentalSupportUbication(
			@RequestParam(value = "fitxerAdjunt", required = true) MultipartFile attachedFile,
			@RequestPart(name = "suportDocumentalRDTO", required = true) @Valid DocumentarySupportRDTO documentarySupportRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {
			documentarySupportRDTO.setClientId(clientId);
			documentarySupportRDTO.setCodeTypeEntity(ImiConstants.CODE_TYPE_DOCUMENTARY_SUPPORT_UBICATION);

			documentarySupportRDTO.setDocumentarySupportFileRDTO(Utils.getDocumentContentName(attachedFile));

			returnRDTO = ubicationService.insertDocumentalSupport(documentarySupportRDTO);

			return processReturnRDTO(returnRDTO);
			
		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, documentarySupportRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/ubicacio/{codiUbicacio}/suportdocumental/{codiSuportDocumental}", method = RequestMethod.PUT)
	@ResponseBody
	@ApiOperation(value = "Interfície d'baixa de suport documental de Ubicació", tags = { "ubicacio" })
	public ResponseEntity<ReturnRDTO> deleteDocumentalSupportUbication(
			@PathVariable(value = "codiUbicacio", required = true) String codeUbication,
			@PathVariable(value = "codiSuportDocumental", required = true) String codeDocumentarySupport,
			@RequestBody DocumentarySupportRDTO documentarySupportRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		documentarySupportRDTO.setCode(codeUbication);
		documentarySupportRDTO.setCodeContractDocument(codeDocumentarySupport);
		documentarySupportRDTO.setCodeTypeEntity(ImiConstants.CODE_TYPE_DOCUMENTARY_SUPPORT_UBICATION);

		try {
			documentarySupportRDTO.setClientId(clientId);
			returnRDTO = ubicationService.deleteDocumentalSupport(documentarySupportRDTO);

			return processReturnRDTO(returnRDTO);

		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, documentarySupportRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/contracta/{codi}/ubicacio/{codiUbicacio}/suportdocumental", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta de suport documental de ubicacio", tags = { "ubicacio" })
	public ResponseEntity<ReturnDocumentarySupportRDTO> selectDocumentarySupportUbication(
			@PathVariable(value = "codiUbicacio", required = true) String code,
			@PathVariable(value = "codi", required = true) String codeContract,
			@RequestParam(value = "considerarAnulacions", required = false) String considerAnnulments,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);
		ReturnDocumentarySupportRDTO returnDocumentarySupportRDTO = new ReturnDocumentarySupportRDTO();
		queryParameterDocumentarySupportRDTO = new QueryParameterDocumentarySupportRDTO();

		try {
			queryParameterDocumentarySupportRDTO.setClientId(clientId);
			queryParameterDocumentarySupportRDTO.setCode(code);
			queryParameterDocumentarySupportRDTO.setCodeContract(codeContract);
			queryParameterDocumentarySupportRDTO.setConsiderAnnulments(considerAnnulments);
			queryParameterDocumentarySupportRDTO.setCodeUser(codeUser);
			queryParameterDocumentarySupportRDTO
					.setCodeTypeEntity(ImiConstants.CODE_TYPE_DOCUMENTARY_SUPPORT_UBICATION);

			returnDocumentarySupportRDTO = ubicationService
					.selectDocumentarySupports(queryParameterDocumentarySupportRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnDocumentarySupportRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			returnDocumentarySupportRDTO.setReturnRDTO(returnRDTO);
			return new ResponseEntity<>(returnDocumentarySupportRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnDocumentarySupportRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/ubicacio/mcl/suportdocumental", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ApiOperation(value = "Interfície d'alta de suport documental de mobiliari logic", tags = {
			"mobiliarilogic" }, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseBody
	public ResponseEntity<ReturnRDTO> saveDocumentalSupportMCL(
			@RequestParam(value = "fitxerAdjunt", required = true) MultipartFile attachedFile,
			@RequestPart(name = "suportDocumentalRDTO", required = true) @Valid DocumentarySupportRDTO documentarySupportRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {
			documentarySupportRDTO.setClientId(clientId);
			documentarySupportRDTO.setCodeTypeEntity(ImiConstants.CODE_TYPE_DOCUMENTARY_SUPPORT_MCL);
			documentarySupportRDTO.setDocumentarySupportFileRDTO(Utils.getDocumentContentName(attachedFile));
			returnRDTO = logicalModelService.insertDocumentalSupport(documentarySupportRDTO);

			return processReturnRDTO(returnRDTO);

		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, documentarySupportRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/ubicacio/{codiUbicacio}/mcl/{codiMCL}/suportdocumental/{codiSuportDocumental}", method = RequestMethod.PUT)
	@ResponseBody
	@ApiOperation(value = "Interfície d'baixa de suport documental de MCL", tags = { "mobiliarilogic" })
	public ResponseEntity<ReturnRDTO> deleteDocumentalSupportMCL(
			@PathVariable(value = "codiMCL", required = true) String codeMCL,
			@PathVariable(value = "codiUbicacio", required = true) String codeUbication,
			@PathVariable(value = "codiSuportDocumental", required = true) String codeDocumentarySupport,
			@RequestBody DocumentarySupportRDTO documentarySupportRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		documentarySupportRDTO.setCode(codeMCL);
		documentarySupportRDTO.setCodeContractDocument(codeDocumentarySupport);
		documentarySupportRDTO.setCodeTypeEntity(ImiConstants.CODE_TYPE_DOCUMENTARY_SUPPORT_MCL);
		documentarySupportRDTO.setCodeUbication(codeUbication);
		try {
			documentarySupportRDTO.setClientId(clientId);
			returnRDTO = logicalModelService.deleteDocumentalSupport(documentarySupportRDTO);

			return processReturnRDTO(returnRDTO);

		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, documentarySupportRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/contracta/{codi}/mobiliarilogic/{codiMCL}/suportdocumental", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta detallada de suport documental MCL", tags = { "mobiliarilogic" })
	public ResponseEntity<ReturnDocumentarySupportRDTO> selectDocumentarySupportLogicalModel(
			@PathVariable(value = "codiMCL", required = true) String code,
			@PathVariable(value = "codi", required = true) String codeContract,
			@RequestParam(value = "considerarAnulacions", required = false) String considerAnnulments,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);
		ReturnDocumentarySupportRDTO returnDocumentarySupportRDTO = new ReturnDocumentarySupportRDTO();
		queryParameterDocumentarySupportRDTO = new QueryParameterDocumentarySupportRDTO();

		try {
			queryParameterDocumentarySupportRDTO.setClientId(clientId);
			queryParameterDocumentarySupportRDTO.setCode(code);
			queryParameterDocumentarySupportRDTO.setCodeContract(codeContract);
			queryParameterDocumentarySupportRDTO.setConsiderAnnulments(considerAnnulments);
			queryParameterDocumentarySupportRDTO.setCodeUser(codeUser);
			queryParameterDocumentarySupportRDTO.setCodeTypeEntity(ImiConstants.CODE_TYPE_DOCUMENTARY_SUPPORT_MCL);

			returnDocumentarySupportRDTO = logicalModelService
					.selectDocumentarySupports(queryParameterDocumentarySupportRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnDocumentarySupportRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			returnDocumentarySupportRDTO.setReturnRDTO(returnRDTO);
			return new ResponseEntity<>(returnDocumentarySupportRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnDocumentarySupportRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/ubicacio/mcl/mcf/suportdocumental", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ApiOperation(value = "Interfície d'alta de suport documental de mobiliari fisic", tags = {
			"mobiliarifisic" }, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseBody
	public ResponseEntity<ReturnRDTO> saveDocumentalSupportMCF(
			@RequestParam(value = "fitxerAdjunt", required = true) MultipartFile attachedFile,
			@RequestPart(name = "suportDocumentalRDTO", required = true) @Valid DocumentarySupportRDTO documentarySupportRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {
			documentarySupportRDTO.setClientId(clientId);
			documentarySupportRDTO.setCodeTypeEntity(ImiConstants.CODE_TYPE_DOCUMENTARY_SUPPORT_MCF);
			documentarySupportRDTO.setDocumentarySupportFileRDTO(Utils.getDocumentContentName(attachedFile));
			documentarySupportRDTO.setCodeTypeEntity(ImiConstants.CODE_TYPE_DOCUMENTARY_SUPPORT_MCF);
			returnRDTO = physicalModelService.insertDocumentalSupport(documentarySupportRDTO);

			return processReturnRDTO(returnRDTO);

		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, documentarySupportRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "ubicacio/{codiUbicacio}/mcl/mcf/{codiMCF}/suportdocumental/{codiSuportDocumental}", method = RequestMethod.PUT)
	@ResponseBody
	@ApiOperation(value = "Interfície d'baixa de suport documental de MCF", tags = { "mobiliarifisic" })
	public ResponseEntity<ReturnRDTO> deleteDocumentalSupportMCF(
			@PathVariable(value = "codiMCF", required = true) String codeMCF,
			@PathVariable(value = "codiUbicacio", required = true) String codeUbication,
			@PathVariable(value = "codiSuportDocumental", required = true) String codeDocumentarySupport,
			@RequestBody DocumentarySupportRDTO documentarySupportRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		documentarySupportRDTO.setCode(codeMCF);
		documentarySupportRDTO.setCodeContractDocument(codeDocumentarySupport);
		documentarySupportRDTO.setCodeTypeEntity(ImiConstants.CODE_TYPE_DOCUMENTARY_SUPPORT_MCF);
		documentarySupportRDTO.setCodeUbication(codeUbication);

		try {
			documentarySupportRDTO.setClientId(clientId);
			returnRDTO = physicalModelService.deleteDocumentalSupport(documentarySupportRDTO);

			return processReturnRDTO(returnRDTO);
			
		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, documentarySupportRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/contracta/{codi}/mobiliarifisic/{codiMCF}/suportdocumental", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta detallada de suport documental MCF", tags = { "mobiliarifisic" })
	public ResponseEntity<ReturnDocumentarySupportRDTO> selectDocumentarySupportPhysicalModel(
			@PathVariable(value = "codiMCF", required = true) String code,
			@PathVariable(value = "codi", required = true) String codeContract,
			@RequestParam(value = "considerarAnulacions", required = false) String considerAnnulments,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);
		ReturnDocumentarySupportRDTO returnDocumentarySupportRDTO = new ReturnDocumentarySupportRDTO();
		queryParameterDocumentarySupportRDTO = new QueryParameterDocumentarySupportRDTO();

		try {
			queryParameterDocumentarySupportRDTO.setClientId(clientId);
			queryParameterDocumentarySupportRDTO.setCode(code);
			queryParameterDocumentarySupportRDTO.setCodeContract(codeContract);
			queryParameterDocumentarySupportRDTO.setConsiderAnnulments(considerAnnulments);
			queryParameterDocumentarySupportRDTO.setCodeUser(codeUser);
			queryParameterDocumentarySupportRDTO.setCodeTypeEntity(ImiConstants.CODE_TYPE_DOCUMENTARY_SUPPORT_MCF);

			returnDocumentarySupportRDTO = physicalModelService
					.selectDocumentarySupports(queryParameterDocumentarySupportRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnDocumentarySupportRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			returnDocumentarySupportRDTO.setReturnRDTO(returnRDTO);
			return new ResponseEntity<>(returnDocumentarySupportRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnDocumentarySupportRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/recursmaterial/suportdocumental", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ApiOperation(value = "Interfície d'alta de suport documental de recurs material", tags = {
			"recursmaterial" }, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseBody
	public ResponseEntity<ReturnRDTO> saveDocumentalSupportRRMM(
			@RequestParam(value = "fitxerAdjunt", required = true) MultipartFile attachedFile,
			@RequestPart(name = "suportDocumentalRDTO", required = true) @Valid DocumentarySupportRDTO documentarySupportRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {
			documentarySupportRDTO.setClientId(clientId);
			documentarySupportRDTO.setCodeTypeEntity(ImiConstants.CODE_TYPE_DOCUMENTARY_SUPPORT_RRMM);
			documentarySupportRDTO.setDocumentarySupportFileRDTO(Utils.getDocumentContentName(attachedFile));

			returnRDTO = materialResourceService.insertDocumentalSupport(documentarySupportRDTO);

			return processReturnRDTO(returnRDTO);
			
		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, documentarySupportRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/recursmaterial/{codiRRMM}/suportdocumental/{codiSuportDocumental}", method = RequestMethod.PUT)
	@ResponseBody
	@ApiOperation(value = "Interfície d'baixa de suport documental de recurs material", tags = { "recursmaterial" })
	public ResponseEntity<ReturnRDTO> deleteDocumentalSupportRRMM(
			@PathVariable(value = "codiRRMM", required = true) String codeRRMM,
			@PathVariable(value = "codiSuportDocumental", required = true) String codeDocumentarySupport,
			@RequestBody DocumentarySupportRDTO documentarySupportRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		documentarySupportRDTO.setCode(codeRRMM);
		documentarySupportRDTO.setCodeContractDocument(codeDocumentarySupport);
		documentarySupportRDTO.setCodeTypeEntity(ImiConstants.CODE_TYPE_DOCUMENTARY_SUPPORT_RRMM);
		try {
			documentarySupportRDTO.setClientId(clientId);
			returnRDTO = materialResourceService.deleteDocumentalSupport(documentarySupportRDTO);

			return processReturnRDTO(returnRDTO);
			
		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, documentarySupportRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/contracta/{codi}/recursmaterial/{codiRRMM}/suportdocumental", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta detallada de suport documental RRMM", tags = { "recursmaterial" })
	public ResponseEntity<ReturnDocumentarySupportRDTO> selectDocumentarySupportMaterialResource(
			@PathVariable(value = "codiRRMM", required = true) String code,
			@PathVariable(value = "codi", required = true) String codeContract,
			@RequestParam(value = "considerarAnulacions", required = false) String considerAnnulments,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);
		ReturnDocumentarySupportRDTO returnDocumentarySupportRDTO = new ReturnDocumentarySupportRDTO();
		queryParameterDocumentarySupportRDTO = new QueryParameterDocumentarySupportRDTO();

		try {
			queryParameterDocumentarySupportRDTO.setClientId(clientId);
			queryParameterDocumentarySupportRDTO.setCode(code);
			queryParameterDocumentarySupportRDTO.setCodeContract(codeContract);
			queryParameterDocumentarySupportRDTO.setConsiderAnnulments(considerAnnulments);
			queryParameterDocumentarySupportRDTO.setCodeUser(codeUser);
			queryParameterDocumentarySupportRDTO.setCodeTypeEntity(ImiConstants.CODE_TYPE_DOCUMENTARY_SUPPORT_RRMM);

			returnDocumentarySupportRDTO = materialResourceService
					.selectDocumentarySupports(queryParameterDocumentarySupportRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnDocumentarySupportRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			returnDocumentarySupportRDTO.setReturnRDTO(returnRDTO);
			return new ResponseEntity<>(returnDocumentarySupportRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnDocumentarySupportRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/ubicacio/actuacio", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Interfície d'alta de actuacio de ubicacio", tags = { "ubicacio" })
	public ResponseEntity<ReturnRDTO> saveActuationUbication(@RequestBody ActuationRDTO actuationRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {
			actuationRDTO.setClientId(clientId);
			actuationRDTO.setCodeTypeEntity(ImiConstants.CODE_TYPE_ACTUATION_UBICATION);

			returnRDTO = ubicationService.insertActuation(actuationRDTO);

			return processReturnRDTO(returnRDTO);

		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, actuationRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/ubicacio/{codiUbicacio}/actuacio/{codiActuacio}", method = RequestMethod.PUT)
	@ResponseBody
	@ApiOperation(value = "Interfície d'baixa de actuacio de ubicacio", tags = { "ubicacio" })
	public ResponseEntity<ReturnRDTO> deleteActuationUbication(
			@PathVariable(value = "codiUbicacio", required = true) String codeUbication,
			@PathVariable(value = "codiActuacio", required = true) String codeActuation,
			@RequestBody ActuationRDTO actuationRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		actuationRDTO.setCode(codeActuation);
		actuationRDTO.setCodeEntity(codeUbication);
		actuationRDTO.setCodeTypeEntity(ImiConstants.CODE_TYPE_ACTUATION_UBICATION);

		try {
			actuationRDTO.setClientId(clientId);
			returnRDTO = ubicationService.deleteActuation(actuationRDTO);

			return processReturnRDTO(returnRDTO);
			
		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, actuationRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	@RequestMapping(value = "/ubicacio/mcl/actuacio", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Interfície d'alta de actuacio de mcl", tags = { "mobiliarilogic" })
	public ResponseEntity<ReturnRDTO> saveActuationMCL(@RequestBody ActuationRDTO actuationRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {
			actuationRDTO.setClientId(clientId);
			actuationRDTO.setCodeTypeEntity(ImiConstants.CODE_TYPE_ACTUATION_MCL);

			returnRDTO = logicalModelService.insertActuation(actuationRDTO);

			return processReturnRDTO(returnRDTO);
			
		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, actuationRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/ubicacio/{codiUbicacio}/mcl/{codiMCL}/actuacio/{codiActuacio}", method = RequestMethod.PUT)
	@ResponseBody
	@ApiOperation(value = "Interfície d'baixa de actuacio de MCL", tags = { "mobiliarilogic" })
	public ResponseEntity<ReturnRDTO> deleteActuationMCL(
			@PathVariable(value = "codiMCL", required = true) String codeMCL,
			@PathVariable(value = "codiUbicacio", required = true) String codeUbication,
			@PathVariable(value = "codiActuacio", required = true) String codeActuation,
			@RequestBody ActuationRDTO actuationRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		actuationRDTO.setCode(codeActuation);
		actuationRDTO.setCodeEntity(codeMCL);
		actuationRDTO.setCodeTypeEntity(ImiConstants.CODE_TYPE_ACTUATION_MCL);
		actuationRDTO.setCodeUbication(codeUbication);
		try {
			actuationRDTO.setClientId(clientId);
			returnRDTO = logicalModelService.deleteActuation(actuationRDTO);

			return processReturnRDTO(returnRDTO);
			
		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, actuationRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/ubicacio/mcl/mcf/actuacio", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Interfície d'alta de actuacio de mcf", tags = { "mobiliarifisic" })
	public ResponseEntity<ReturnRDTO> saveActuationMCF(@RequestBody ActuationRDTO actuationRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {
			actuationRDTO.setClientId(clientId);
			actuationRDTO.setCodeTypeEntity(ImiConstants.CODE_TYPE_ACTUATION_MCF);

			returnRDTO = physicalModelService.insertActuation(actuationRDTO);

			return processReturnRDTO(returnRDTO);
			
		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, actuationRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "ubicacio/{codiUbicacio}/mcl/mcf/{codiMCF}/actuacio/{codiActuacio}", method = RequestMethod.PUT)
	@ResponseBody
	@ApiOperation(value = "Interfície d'baixa de actuacio de MCF", tags = { "mobiliarifisic" })
	public ResponseEntity<ReturnRDTO> deleteActuationMCF(
			@PathVariable(value = "codiMCF", required = true) String codeMCF,
			@PathVariable(value = "codiUbicacio", required = true) String codeUbication,
			@PathVariable(value = "codiActuacio", required = true) String codeActuation,
			@RequestBody ActuationRDTO actuationRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		actuationRDTO.setCode(codeActuation);
		actuationRDTO.setCodeEntity(codeMCF);
		actuationRDTO.setCodeTypeEntity(ImiConstants.CODE_TYPE_ACTUATION_MCF);
		actuationRDTO.setCodeUbication(codeUbication);

		try {
			actuationRDTO.setClientId(clientId);
			returnRDTO = physicalModelService.deleteActuation(actuationRDTO);

			return processReturnRDTO(returnRDTO);
			
		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, actuationRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/recursmaterial/actuacio", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Interfície d'alta de actuacio de recurs material", tags = { "recursmaterial" })
	public ResponseEntity<ReturnRDTO> saveActuationRRMM(@RequestBody ActuationRDTO actuationRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {
			actuationRDTO.setClientId(clientId);
			actuationRDTO.setCodeTypeEntity(ImiConstants.CODE_TYPE_ACTUATION_RRMM);

			returnRDTO = materialResourceService.insertActuation(actuationRDTO);

			return processReturnRDTO(returnRDTO);
			
		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, actuationRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/recursmaterial/{codiRRMM}/actuacio/{codiActuacio}", method = RequestMethod.PUT)
	@ResponseBody
	@ApiOperation(value = "Interfície d'baixa de actuacio de recurs material", tags = { "recursmaterial" })
	public ResponseEntity<ReturnRDTO> deleteActuationRRMM(
			@PathVariable(value = "codiRRMM", required = true) String codeRRMM,
			@PathVariable(value = "codiActuacio", required = true) String codeActuation,
			@RequestBody ActuationRDTO actuationRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		actuationRDTO.setCode(codeActuation);
		actuationRDTO.setCodeEntity(codeRRMM);
		actuationRDTO.setCodeTypeEntity(ImiConstants.CODE_TYPE_ACTUATION_RRMM);
		try {
			actuationRDTO.setClientId(clientId);
			returnRDTO = materialResourceService.deleteActuation(actuationRDTO);

			return processReturnRDTO(returnRDTO);

		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, actuationRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/installacio/actuacio", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Interfície d'alta de actuacio de installacio", tags = { "installacio" })
	public ResponseEntity<ReturnRDTO> saveActuationInstallation(@RequestBody ActuationRDTO actuationRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {
			actuationRDTO.setClientId(clientId);
			actuationRDTO.setCodeTypeEntity(ImiConstants.CODE_TYPE_ACTUATION_INSTALLATION);

			returnRDTO = installationService.insertActuation(actuationRDTO);

			return processReturnRDTO(returnRDTO);
			
		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, actuationRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/installacio/{codiInstalacio}/actuacio/{codiActuacio}", method = RequestMethod.PUT)
	@ResponseBody
	@ApiOperation(value = "Interfície d'baixa de actuacio de installacio", tags = { "installacio" })
	public ResponseEntity<ReturnRDTO> deleteActuationInstallation(
			@PathVariable(value = "codiInstalacio", required = true) String codeInstallation,
			@PathVariable(value = "codiActuacio", required = true) String codeActuation,
			@RequestBody ActuationRDTO actuationRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		actuationRDTO.setCode(codeActuation);
		actuationRDTO.setCodeEntity(codeInstallation);
		actuationRDTO.setCodeTypeEntity(ImiConstants.CODE_TYPE_ACTUATION_INSTALLATION);
		try {
			actuationRDTO.setClientId(clientId);
			returnRDTO = installationService.deleteActuation(actuationRDTO);

			return processReturnRDTO(returnRDTO);

		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, actuationRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/contracta/{codi}/tipusEntitat/{tipusEntitat}/codiEntitat/{codiEntitat}/actuacio", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta d'actuacions", tags = { "actuacions" })
	public ResponseEntity<ReturnActuationRDTO> selectActuation(
			@PathVariable(value = "codi", required = true) String codeContract,
			@PathVariable(value = "tipusEntitat", required = true) String codeTypeEntity,
			@PathVariable(value = "codiEntitat", required = true) String codeEntity,
			@RequestParam(value = "considerarAnulacions", required = false) String considerAnnulments,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);
		ReturnActuationRDTO returnActuationRDTO = new ReturnActuationRDTO();
		queryParameterActuationRDTO = new QueryParameterActuationRDTO();

		try {
			queryParameterActuationRDTO.setClientId(clientId);
			queryParameterActuationRDTO.setCodeContract(codeContract);
			queryParameterActuationRDTO.setCodeTypeEntity(codeTypeEntity);
			queryParameterActuationRDTO.setCodeEntity(codeEntity);
			queryParameterActuationRDTO.setConsiderAnnulments(considerAnnulments);
			queryParameterActuationRDTO.setCodeUser(codeUser);

			returnActuationRDTO = actuationService.selectActuations(queryParameterActuationRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnActuationRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			returnActuationRDTO.setReturnRDTO(returnRDTO);
			return new ResponseEntity<>(returnActuationRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnActuationRDTO, HttpStatus.OK);
	}

	
	@RequestMapping(value = "/installacio/despesa/suportdocumental", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ApiOperation(value = "Interfície d'alta de suport documental de despeses de installacio", tags = { "installacio" },consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseBody
	public ResponseEntity<ReturnRDTO> saveDocumentalSupportInstallationExpense(
			@RequestParam(value = "fitxerAdjunt", required = true) MultipartFile attachedFile,
			@RequestPart(name = "suportDocumentalDespesaRDTO", required = true)@Valid DocumentarySupportExpenseRDTO documentarySupportExpenseRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {
			documentarySupportExpenseRDTO.setClientId(clientId);
			documentarySupportExpenseRDTO.setCodeTypeEntity(ImiConstants.CODE_TYPE_DOCUMENTARY_SUPPORT_INSTALLATION);
			documentarySupportExpenseRDTO.setDocumentarySupportFileRDTO(Utils.getDocumentContentName(attachedFile));
			returnRDTO = installationService.insertExpenseDocumentalSupport(documentarySupportExpenseRDTO);

			return processReturnRDTO(returnRDTO);

		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, documentarySupportExpenseRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/installacio/{codiInstalacio}/despesa/suportdocumental/{codiSuportDocumental}", method = RequestMethod.PUT)
	@ResponseBody
	@ApiOperation(value = "Interfície d'baixa de suport documental de recurs material", tags = { "recursmaterial" })
	public ResponseEntity<ReturnRDTO> deleteDocumentalSupportExpenseInstallation(
			@PathVariable(value = "codiInstalacio", required = true) String codeInstallation,
			@PathVariable(value = "codiSuportDocumental", required = true) String codeDocumentarySupport,
			@RequestBody DocumentarySupportExpenseRDTO documentarySupportExpenseRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		documentarySupportExpenseRDTO.setCode(codeInstallation);
		documentarySupportExpenseRDTO.setCodeContractDocument(codeDocumentarySupport);
		documentarySupportExpenseRDTO.setCodeTypeEntity(ImiConstants.CODE_TYPE_DOCUMENTARY_SUPPORT_INSTALLATION);
		try {
			documentarySupportExpenseRDTO.setClientId(clientId);
			returnRDTO = installationService.deleteExpenseDocumentalSupport(documentarySupportExpenseRDTO);

			return processReturnRDTO(returnRDTO);
			
		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, documentarySupportExpenseRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/installacio/suportdocumental", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ApiOperation(value = "Interfície d'alta de suport documental de installacio", tags = { "installacio" }, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseBody
	public ResponseEntity<ReturnRDTO> saveDocumentalSupportInstallation(
			@RequestParam(value = "fitxerAdjunt", required = true) MultipartFile attachedFile,
			@RequestPart(name = "suportDocumentalRDTO", required = true) @Valid DocumentarySupportRDTO documentarySupportRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {
			documentarySupportRDTO.setClientId(clientId);
			documentarySupportRDTO.setCodeTypeEntity(ImiConstants.CODE_TYPE_DOCUMENTARY_SUPPORT_INSTALLATION);
			documentarySupportRDTO.setDocumentarySupportFileRDTO(Utils.getDocumentContentName(attachedFile));

			returnRDTO = installationService.insertDocumentalSupport(documentarySupportRDTO);

			return processReturnRDTO(returnRDTO);

		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, documentarySupportRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/installacio/{codiInstallacio}/suportdocumental/{codiSuportDocumental}", method = RequestMethod.PUT)
	@ResponseBody
	@ApiOperation(value = "Interfície d'baixa de suport documental de installacio", tags = { "installacio" })
	public ResponseEntity<ReturnRDTO> deleteDocumentalSupportInstallation(
			@PathVariable(value = "codiInstallacio", required = true) String codeInstallation,
			@PathVariable(value = "codiSuportDocumental", required = true) String codeDocumentarySupport,
			@RequestBody DocumentarySupportRDTO documentarySupportRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		documentarySupportRDTO.setCode(codeInstallation);
		documentarySupportRDTO.setCodeContractDocument(codeDocumentarySupport);
		documentarySupportRDTO.setCodeTypeEntity(ImiConstants.CODE_TYPE_DOCUMENTARY_SUPPORT_INSTALLATION);
		try {
			documentarySupportRDTO.setClientId(clientId);
			returnRDTO = installationService.deleteDocumentalSupport(documentarySupportRDTO);

			return processReturnRDTO(returnRDTO);
			
		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, documentarySupportRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/sensor", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Interfície d'alta d'un Sensor", tags = { "sensor" })
	public ResponseEntity<ReturnRDTO> saveSensor(@RequestBody SensorRDTO sensorRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		
		try {
			sensorRDTO.setClientId(clientId);
			returnRDTO = sensorService.insert(sensorRDTO);

			return processReturnRDTO(returnRDTO);

		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, sensorRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	
	@RequestMapping(value = "/sensor/{codi}", method = RequestMethod.PUT)
	@ResponseBody
	@ApiOperation(value = "Interfície de modificació d'un Sensor", tags = { "sensor" })
	public ResponseEntity<ReturnRDTO> updateSensor(@PathVariable(value = "codi", required = true) String code,
			@RequestBody SensorRDTO sensorRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);
		returnRDTO = new ReturnRDTO();

		try {
			sensorRDTO.setClientId(clientId);
			sensorRDTO.setCode(code);

			returnRDTO = sensorService.update(sensorRDTO);

			return processReturnRDTO(returnRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, sensorRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		
	}
	
	@RequestMapping(value = "/sensor/{codi}/baixa", method = RequestMethod.PUT)
	@ResponseBody
	@ApiOperation(value = "Interfície de baja d'un Sensor", tags = { "sensor" })
	public ResponseEntity<ReturnRDTO> deleteSensor(@PathVariable(value = "codi", required = true) String code,
			@RequestBody SensorRDTO sensorRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);
		returnRDTO = new ReturnRDTO();

		try {
			sensorRDTO.setClientId(clientId);
			sensorRDTO.setCode(code);

			returnRDTO = sensorService.delete(sensorRDTO);

			return processReturnRDTO(returnRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, sensorRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		
	}
	
	@RequestMapping(value = "/contracta/{codi}/sensor/{codiSensor}/detall", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta detallada de Sensors", tags = { "sensor" })
	public ResponseEntity<ReturnSensorDetailedRDTO> selectSensorDetailed(
			@PathVariable(value = "codiSensor", required = true) String code,
			@PathVariable(value = "codi", required = true) String codeContract,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);
		ReturnSensorDetailedRDTO returnSensorDetailedRDTO = new ReturnSensorDetailedRDTO();
		queryParameterRDTO = new QueryParameterRDTO();

		try {
			queryParameterRDTO.setClientId(clientId);
			queryParameterRDTO.setCode(code);
			queryParameterRDTO.setCodeContract(codeContract);
			queryParameterRDTO.setDateReference(dateReference);
			queryParameterRDTO.setCodeUser(codeUser);

			returnSensorDetailedRDTO = sensorService.selectDetailed(queryParameterRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnSensorDetailedRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			returnSensorDetailedRDTO.setReturnRDTO(returnRDTO);
			return new ResponseEntity<>(returnSensorDetailedRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnSensorDetailedRDTO, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/contracta/{codi}/sensor", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta massiva d'sensores", tags = { "sensors" })
	public ResponseEntity<ReturnSensorMassiveRDTO> selectSensorMassive(
			@PathVariable(value = "codi", required = true) String codeContract,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);
		ReturnSensorMassiveRDTO returnSensorMassiveRDTO = new ReturnSensorMassiveRDTO();
		queryParameterRDTO = new QueryParameterRDTO();

		try {
			queryParameterRDTO.setClientId(clientId);
			queryParameterRDTO.setCodeContract(codeContract);
			queryParameterRDTO.setDateReference(dateReference);
			queryParameterRDTO.setCodeUser(codeUser);

			returnSensorMassiveRDTO = sensorService.selectMassive(queryParameterRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnSensorMassiveRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			returnSensorMassiveRDTO.setReturnRDTO(returnRDTO);
			return new ResponseEntity<>(returnSensorMassiveRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnSensorMassiveRDTO, HttpStatus.OK);
	}

}