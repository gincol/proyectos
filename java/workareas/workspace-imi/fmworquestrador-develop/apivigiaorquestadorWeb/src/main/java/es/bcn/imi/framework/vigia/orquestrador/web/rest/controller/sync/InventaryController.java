package es.bcn.imi.framework.vigia.orquestrador.web.rest.controller.sync;

import javax.servlet.http.HttpServletRequest;

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

import es.bcn.imi.framework.vigia.orquestrador.business.inventary.ActuationService;
import es.bcn.imi.framework.vigia.orquestrador.business.inventary.CommerceService;
import es.bcn.imi.framework.vigia.orquestrador.business.inventary.DocumentarySupportService;
import es.bcn.imi.framework.vigia.orquestrador.business.inventary.EmployeeCatalogService;
import es.bcn.imi.framework.vigia.orquestrador.business.inventary.InstallationService;
import es.bcn.imi.framework.vigia.orquestrador.business.inventary.LogicalModelService;
import es.bcn.imi.framework.vigia.orquestrador.business.inventary.MaterialResourceService;
import es.bcn.imi.framework.vigia.orquestrador.business.inventary.PhysicalModelService;
import es.bcn.imi.framework.vigia.orquestrador.business.inventary.SensorService;
import es.bcn.imi.framework.vigia.orquestrador.business.inventary.UbicationService;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
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
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnLogicalModelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMaterialResourceDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMaterialResourceVehicleDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMaterialResourceVehicleExpensesRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMaterialResourceVehiclePeriodRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnLogicalModelDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnLogicalModelMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnPhysicalModelAmortizationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnPhysicalModelDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnPhysicalModelMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnPhysicalModelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnSensorDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnSensorMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnUbicationDetailedRDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.opentrends.openframe.services.rest.controller.AbstractRestController;
import net.opentrends.openframe.services.rest.http.ResponseEntity;

@RestController
@RequestMapping("/sync/inventari")
@Lazy(true)
@Api("ORQUESTRATOR API")
public class InventaryController extends AbstractRestController {

	private Log logger = LogFactory.getLog(this.getClass());

	@Autowired(required = true)
	@Qualifier(ServicesConstants.SRV_INVENTARY_UBICATION)
	private UbicationService ubicationService;

	@Autowired(required = true)
	@Qualifier(ServicesConstants.SRV_INVENTARY_PHYSICAL_MODEL)
	private PhysicalModelService physicalModelService;

	@Autowired(required = true)
	@Qualifier(ServicesConstants.SRV_INVENTARY_LOGICAL_MODEL)
	private LogicalModelService logicalModelService;
	
	@Autowired(required = true)
	@Qualifier(ServicesConstants.SRV_INVENTARY_MATERIAL_RESOURCE)
	private MaterialResourceService materialResourceService;
	
	@Autowired(required = true)
	@Qualifier(ServicesConstants.SRV_INVENTARY_INSTALLATION)
	private InstallationService installationService;
	
	@Autowired(required = true)
	@Qualifier(ServicesConstants.SRV_INVENTARY_COMMERCE)
	private CommerceService commerceService;
	
	@Autowired(required = true)
	@Qualifier(ServicesConstants.SRV_INVENTARY_EMPLOYEE_CATALOG)
	private EmployeeCatalogService employeeCatalogService;

	@Autowired(required = true)
	@Qualifier(ServicesConstants.SRV_INVENTARY_DOCUMENTARY_SUPPORT)
	private DocumentarySupportService documentarySupportService;

	@Autowired(required = true)
	@Qualifier(ServicesConstants.SRV_INVENTARY_ACTUATION)
	private ActuationService actuationService;

	@Autowired(required = true)
	@Qualifier(ServicesConstants.SRV_INVENTARY_SENSOR)
	private SensorService sensorService;

	private ReturnRDTO returnRDTO;

	private QueryParameterRDTO queryParameterRDTO;

	private QueryParameterDocumentarySupportRDTO queryParameterDocumentarySupportRDTO;

	@RequestMapping(value = { "/ubicacio", "/ubicacio/{codi}" }, method = {RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
	@ResponseBody
	@ApiOperation(value = "Interfície Sync for POST, PUT and DELETE", tags = { "orchestrator" })
	public ResponseEntity<ReturnRDTO> ubicationSync(@RequestBody UbicationRDTO ubicationRDTO,
													@PathVariable(value = "codi", required = false) String code,
													HttpServletRequest request) {

		logger.info(LogsConstants.LOG_START); 

		try {
			
			if(code != null) {
			   ubicationRDTO.setCode(code);
			}
		
			returnRDTO = ubicationService.redirect(ubicationRDTO, request);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, ubicationRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}

	@RequestMapping(path = { "/mobiliarifisic", "/mobiliarifisic/{codi}" }, method = { RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
	@ResponseBody
	@ApiOperation(value = "Interfície Sync for POST, PUT and DELETE", tags = { "orchestrator" })
	public ResponseEntity<ReturnRDTO> physicalModelSync(@RequestBody PhysicalModelRDTO physicalModelRDTO,
														@PathVariable(value = "codi", required = false) String code, 
														HttpServletRequest request) {

		logger.info(LogsConstants.LOG_START);

		try {
			
			if(code != null) {
			   physicalModelRDTO.setCode(code);	
			}
			
			returnRDTO = physicalModelService.redirect(physicalModelRDTO, request);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, physicalModelRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/mobiliarifisic/{codi}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície Sync only GET", tags = { "orchestrator" })
	public ResponseEntity<ReturnPhysicalModelRDTO> physicalModelSyncGET(@PathVariable(value = "codi") String code) {

		logger.info(LogsConstants.LOG_START);

		ReturnPhysicalModelRDTO returnPhysicalModelRDTO = new ReturnPhysicalModelRDTO();
		PhysicalModelRDTO physicalModelRDTO = new PhysicalModelRDTO();

		try {

			physicalModelRDTO.setCode(code);
			returnPhysicalModelRDTO = physicalModelService.select(physicalModelRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, ex));
			return new ResponseEntity<ReturnPhysicalModelRDTO>(returnPhysicalModelRDTO,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<ReturnPhysicalModelRDTO>(returnPhysicalModelRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = { "/mobiliarilogic", "/mobiliarilogic/{codi}" }, method = { RequestMethod.POST,	RequestMethod.PUT, RequestMethod.DELETE })
	@ResponseBody
	@ApiOperation(value = "Interfície Sync for POST, PUT and DELETE", tags = { "orchestrator" })
	public ResponseEntity<ReturnRDTO> logicalModelSync(@RequestBody LogicalModelRDTO logicalModelRDTO,
													   @PathVariable(value = "codi", required = false) String code, 
													   HttpServletRequest request) {

		logger.info(LogsConstants.LOG_START);

		try {
			
			if(code != null) {
			   logicalModelRDTO.setCode(code);
			}
			
			returnRDTO = logicalModelService.redirect(logicalModelRDTO, request);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, logicalModelRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/mobiliarilogic/{codi}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície Sync only GET", tags = { "orchestrator" })
	public ResponseEntity<ReturnLogicalModelRDTO> logicalModelSyncGET(@PathVariable(value = "codi") String code) {

		logger.info(LogsConstants.LOG_START);

		ReturnLogicalModelRDTO returnLogicalModelRDTO = new ReturnLogicalModelRDTO();
		LogicalModelRDTO logicalModelRDTO = new LogicalModelRDTO();

		try {

			logicalModelRDTO.setCode(code);
			returnLogicalModelRDTO = logicalModelService.select(logicalModelRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, ex));
			return new ResponseEntity<>(returnLogicalModelRDTO,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnLogicalModelRDTO, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/recursmaterial/contracta/{codi}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície Sync massive GET", tags = { "orchestrator" })
	public ResponseEntity<ReturnMassiveRDTO> materialResourceMassiveSyncGET(
			@PathVariable(value = "codi") String code,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestParam(required = false) String transactionId) {

		logger.info(LogsConstants.LOG_START);
		ReturnMassiveRDTO returnMassiveRDTO = new ReturnMassiveRDTO();
		queryParameterRDTO = new QueryParameterRDTO();

		try {
			queryParameterRDTO.setCodeContract(code);
			queryParameterRDTO.setDateReference(dateReference);
			queryParameterRDTO.setCodeUser(codeUser);
			queryParameterRDTO.setTransactionId(transactionId);
			
			returnMassiveRDTO = materialResourceService.selectMassive(queryParameterRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, ex));
			return new ResponseEntity<>(returnMassiveRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnMassiveRDTO, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/recursmaterial/{codi}/contracta/{codiContracta}/detall", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície Sync only GET", tags = { "orchestrator" })
	public ResponseEntity<ReturnMaterialResourceDetailedRDTO> materialResourceDetailedSyncGET(
			@PathVariable(value = "codi") String code,
			@PathVariable(value = "codiContracta") String codeContract,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestParam(required = false) String transactionId) {

		logger.info(LogsConstants.LOG_START);
		ReturnMaterialResourceDetailedRDTO returnMaterialResourceDetailedRDTO = new ReturnMaterialResourceDetailedRDTO();
		queryParameterRDTO = new QueryParameterRDTO();

		try {
			queryParameterRDTO.setCode(code);
			queryParameterRDTO.setCodeContract(codeContract);
			queryParameterRDTO.setDateReference(dateReference);
			queryParameterRDTO.setCodeUser(codeUser);
			queryParameterRDTO.setTransactionId(transactionId);
			
			returnMaterialResourceDetailedRDTO = materialResourceService.selectDetailed(queryParameterRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, ex));
			return new ResponseEntity<>(returnMaterialResourceDetailedRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnMaterialResourceDetailedRDTO, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/recursmaterial/vehicle/contracta/{codi}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície Sync massive GET", tags = { "orchestrator" })
	public ResponseEntity<ReturnMassiveRDTO> materialResourceVehicleMassiveSyncGET(
			@PathVariable(value = "codi") String code,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestParam(required = false) String transactionId) {

		logger.info(LogsConstants.LOG_START);
		ReturnMassiveRDTO returnMassiveRDTO = new ReturnMassiveRDTO();
		queryParameterRDTO = new QueryParameterRDTO();

		try {
			queryParameterRDTO.setCodeContract(code);
			queryParameterRDTO.setDateReference(dateReference);
			queryParameterRDTO.setCodeUser(codeUser);
			queryParameterRDTO.setTransactionId(transactionId);
			
			returnMassiveRDTO = materialResourceService.selectVehicleMassive(queryParameterRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, ex));
			return new ResponseEntity<>(returnMassiveRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnMassiveRDTO, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/recursmaterial/vehicle/{codi}/contracta/{codiContracta}/detall", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície Sync only GET", tags = { "orchestrator" })
	public ResponseEntity<ReturnMaterialResourceVehicleDetailedRDTO> materialResourceVehicleDetailedSyncGET(
			@PathVariable(value = "codi") String code,
			@PathVariable(value = "codiContracta") String codeContract,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestParam(required = false) String transactionId) {

		logger.info(LogsConstants.LOG_START);
		ReturnMaterialResourceVehicleDetailedRDTO returnMaterialResourceVehicleDetailedRDTO = new ReturnMaterialResourceVehicleDetailedRDTO();
		queryParameterRDTO = new QueryParameterRDTO();

		try {
			queryParameterRDTO.setCode(code);
			queryParameterRDTO.setCodeContract(codeContract);
			queryParameterRDTO.setDateReference(dateReference);
			queryParameterRDTO.setCodeUser(codeUser);
			queryParameterRDTO.setTransactionId(transactionId);
			
			returnMaterialResourceVehicleDetailedRDTO = materialResourceService.selectVehicleDetailed(queryParameterRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, ex));
			return new ResponseEntity<>(returnMaterialResourceVehicleDetailedRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnMaterialResourceVehicleDetailedRDTO, HttpStatus.OK);
	}
	
	@RequestMapping(value = { "/recursmaterial", "/recursmaterial/{codi}" }, method = { RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
	@ResponseBody
	@ApiOperation(value = "Interfície Sync for POST, PUT and DELETE", tags = { "orchestrator" })
	public ResponseEntity<ReturnRDTO> materialResourceSync(@RequestBody MaterialResourceRDTO materialResourceRDTO,
														   @PathVariable(value = "codi", required = false) String code, 
														   HttpServletRequest request) {

		logger.info(LogsConstants.LOG_START);

		try {

			if (code != null) {
				materialResourceRDTO.setCode(code);
			}

			returnRDTO = materialResourceService.redirect(materialResourceRDTO, request);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, materialResourceRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/recursmaterial/despesa", method = { RequestMethod.POST })
	@ResponseBody
	@ApiOperation(value = "Interfície Sync for POST", tags = { "orchestrator" })
	public ResponseEntity<ReturnRDTO>  materialResourceExpenseSync(@RequestBody MaterialResourceRDTO materialResourceRDTO) {

		logger.info(LogsConstants.LOG_START);

		try {
			
			returnRDTO = materialResourceService.insertExpense(materialResourceRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, materialResourceRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/recursmaterial/amortitzacio", method = { RequestMethod.POST })
	@ResponseBody
	@ApiOperation(value = "Interfície Sync for POST", tags = { "orchestrator" })
	public ResponseEntity<ReturnRDTO>  materialResourceAmortizationBaseSync(@RequestBody MaterialResourceRDTO materialResourceRDTO) {

		logger.info(LogsConstants.LOG_START);

		try {

			returnRDTO = materialResourceService.insertAmortizationBase(materialResourceRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, materialResourceRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/recursmaterial/prorrateig", method = { RequestMethod.POST })
	@ResponseBody
	@ApiOperation(value = "Interfície Sync for POST", tags = { "orchestrator" })
	public ResponseEntity<ReturnRDTO>  materialResourceApportionmentSync(@RequestBody MaterialResourceRDTO materialResourceRDTO) {

		logger.info(LogsConstants.LOG_START);

		try {
			
			returnRDTO = materialResourceService.insertApportionment(materialResourceRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, materialResourceRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/mobiliarifisic/tipus/amortitzacio/agregada", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Interfície Sync for POST", tags = { "orchestrator" })
	public ResponseEntity<ReturnRDTO> aggregateAmortizationSync(@RequestBody AggregateAmortizationRDTO aggregateAmortizationRDTO,
																HttpServletRequest request) {

		logger.info(LogsConstants.LOG_START);

		try {
			
			returnRDTO = physicalModelService.insert(aggregateAmortizationRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, aggregateAmortizationRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}
	
	@RequestMapping(value = {"/installacio", "/installacio/{codi}"}, method = {RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
	@ResponseBody
	@ApiOperation(value = "Interfície Sync for POST, PUT and DELETE", tags = { "orchestrator" })
	public ResponseEntity<ReturnRDTO> installationSync(@RequestBody InstallationRDTO installationRDTO,
													   @PathVariable(value = "codi", required = false) String code, 
													   HttpServletRequest request) {

		logger.info(LogsConstants.LOG_START);

		try {

			if (code != null) {
				installationRDTO.setCode(code);
			}

			returnRDTO = installationService.redirect(installationRDTO, request);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, installationRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/installacio/{codi}/contracta/{codiContracta}/detall", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície Sync only GET", tags = { "orchestrator" })
	public ResponseEntity<ReturnInstallationDetailedRDTO> installationDetailedSyncGET(
			@PathVariable(value = "codi") String code,
			@PathVariable(value = "codiContracta") String codeContract,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestParam(required = false) String transactionId) {

		logger.info(LogsConstants.LOG_START);
		ReturnInstallationDetailedRDTO returnInstallationDetailedRDTO = new ReturnInstallationDetailedRDTO();
		queryParameterRDTO = new QueryParameterRDTO();

		try {
			queryParameterRDTO.setCode(code);
			queryParameterRDTO.setCodeContract(codeContract);
			queryParameterRDTO.setDateReference(dateReference);
			queryParameterRDTO.setCodeUser(codeUser);
			queryParameterRDTO.setTransactionId(transactionId);
			
			returnInstallationDetailedRDTO = installationService.selectDetailed(queryParameterRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, ex));
			return new ResponseEntity<>(returnInstallationDetailedRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnInstallationDetailedRDTO, HttpStatus.OK);
	}	
	
	@RequestMapping(path = "/installacio/contracta/{codi}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície Sync massive GET", tags = { "orchestrator" })
	public ResponseEntity<ReturnMassiveRDTO> installationMassiveSyncGET(
			@PathVariable(value = "codi") String code,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestParam(required = false) String transactionId) {

		logger.info(LogsConstants.LOG_START);
		ReturnMassiveRDTO returnMassiveRDTO = new ReturnMassiveRDTO();
		queryParameterRDTO = new QueryParameterRDTO();

		try {
			queryParameterRDTO.setCodeContract(code);
			queryParameterRDTO.setDateReference(dateReference);
			queryParameterRDTO.setCodeUser(codeUser);
			queryParameterRDTO.setTransactionId(transactionId);
			
			returnMassiveRDTO = installationService.selectMassive(queryParameterRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, ex));
			return new ResponseEntity<>(returnMassiveRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnMassiveRDTO, HttpStatus.OK);
	}

	@RequestMapping(path = "/installacio/{codi}/contracta/{codiContracta}/amortitzacio", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície Sync only GET", tags = { "orchestrator" })
	public ResponseEntity<ReturnBreakdownAmortizationRDTO> installationAmortizationSyncGET(
			@PathVariable(value = "codi") String code,
			@PathVariable(value = "codiContracta") String codeContract,
			@RequestParam(value = "codiUsuari",required = true) String codeUser,
			@RequestParam(required = true) String transactionId) {

		logger.info(LogsConstants.LOG_START);
		ReturnBreakdownAmortizationRDTO returnBreakdownAmortizationRDTO = new ReturnBreakdownAmortizationRDTO();
		queryParameterRDTO = new QueryParameterRDTO();

		try {
			queryParameterRDTO.setCode(code);
			queryParameterRDTO.setCodeContract(codeContract);
			queryParameterRDTO.setCodeUser(codeUser);
			queryParameterRDTO.setTransactionId(transactionId);
			
			returnBreakdownAmortizationRDTO = installationService.selectAmortization(queryParameterRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, ex));
			return new ResponseEntity<>(returnBreakdownAmortizationRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnBreakdownAmortizationRDTO, HttpStatus.OK);
	}	
	
	@RequestMapping(path = "/installacio/{codi}/contracta/{codiContracta}/despesa", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície Sync only GET", tags = { "orchestrator" })
	public ResponseEntity<ReturnInstallationExpensesRDTO> installationExpensesSyncGET(
			@PathVariable(value = "codi") String code,
			@PathVariable(value = "codiContracta") String codeContract,
			@RequestParam(value = "codiUsuari", required = true) String codeUser,
			@RequestParam(required = true) String transactionId) {

		logger.info(LogsConstants.LOG_START);
		ReturnInstallationExpensesRDTO returnInstallationExpensesRDTO = new ReturnInstallationExpensesRDTO();
		queryParameterRDTO = new QueryParameterRDTO();

		try {
			queryParameterRDTO.setCode(code);
			queryParameterRDTO.setCodeContract(codeContract);
			queryParameterRDTO.setCodeUser(codeUser);
			queryParameterRDTO.setTransactionId(transactionId);
			
			returnInstallationExpensesRDTO = installationService.selectExpenses(queryParameterRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, ex));
			return new ResponseEntity<>(returnInstallationExpensesRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnInstallationExpensesRDTO, HttpStatus.OK);
	}	
	
	@RequestMapping(path = "/installacio/{codi}/contracta/{codiContracta}/periode/{periode}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície Sync only GET", tags = { "orchestrator" })
	public ResponseEntity<ReturnInstallationPeriodRDTO> installationPeriodSyncGET(
			@PathVariable(value = "codi") String code,
			@PathVariable(value = "codiContracta") String codeContract,
			@PathVariable(value = "periode") String period,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestParam(required = false) String transactionId) {

		logger.info(LogsConstants.LOG_START);
		ReturnInstallationPeriodRDTO returnInstallationPeriodRDTO = new ReturnInstallationPeriodRDTO();
		queryParameterRDTO = new QueryParameterRDTO();

		try {
			queryParameterRDTO.setCode(code);
			queryParameterRDTO.setCodeContract(codeContract);
			queryParameterRDTO.setPeriod(period);
			queryParameterRDTO.setCodeUser(codeUser);
			queryParameterRDTO.setTransactionId(transactionId);
			
			returnInstallationPeriodRDTO = installationService.selectPeriod(queryParameterRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, ex));
			return new ResponseEntity<>(returnInstallationPeriodRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnInstallationPeriodRDTO, HttpStatus.OK);
	}	

	@RequestMapping(value = "/installacio/despesa", method = { RequestMethod.POST })
	@ResponseBody
	@ApiOperation(value = "Interfície Sync for POST", tags = { "orchestrator" })
	public ResponseEntity<ReturnRDTO> installationExpenseSync(@RequestBody InstallationRDTO installationRDTO) {

		logger.info(LogsConstants.LOG_START);

		try {
			
			returnRDTO = installationService.insertExpense(installationRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, installationRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/installacio/amortitzacio", method = { RequestMethod.POST })
	@ResponseBody
	@ApiOperation(value = "Interfície Sync for POST", tags = { "orchestrator" })
	public ResponseEntity<ReturnRDTO> installationAmortizationBaseSync(@RequestBody InstallationRDTO installationRDTO) {

		logger.info(LogsConstants.LOG_START);

		try {

			returnRDTO = installationService.insertAmortizationBase(installationRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, installationRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/installacio/prorrateig", method = { RequestMethod.POST })
	@ResponseBody
	@ApiOperation(value = "Interfície Sync for POST", tags = { "orchestrator" })
	public ResponseEntity<ReturnRDTO> installationApportionmentSync(@RequestBody InstallationRDTO installationRDTO) {

		logger.info(LogsConstants.LOG_START);

		try {
			
			returnRDTO = installationService.insertApportionment(installationRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, installationRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}
	
	@RequestMapping(value = {"/comerc", "/comerc/{codi}"}, method = {RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
	@ResponseBody
	@ApiOperation(value = "Interfície Sync for POST, PUT and DELETE", tags = { "orchestrator" })
	public ResponseEntity<ReturnRDTO> commerceSync(@RequestBody CommerceRDTO commerceRDTO,
													   @PathVariable(value = "codi", required = false) String code, 
													   HttpServletRequest request) {

		logger.info(LogsConstants.LOG_START);

		try {

			if (code != null) {
				commerceRDTO.setCode(code);
			}

			returnRDTO = commerceService.redirect(commerceRDTO, request);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, commerceRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}
		
	@RequestMapping(value = "/comerc/elements", method = { RequestMethod.POST })
	@ResponseBody
	@ApiOperation(value = "Interfície Sync for POST", tags = { "orchestrator" })
	public ResponseEntity<ReturnRDTO> commerceElementsSync(@RequestBody CommerceRDTO commerceRDTO) {

		logger.info(LogsConstants.LOG_START);

		try {
			
			returnRDTO = commerceService.insertElements(commerceRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, commerceRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/comerc/contracta/{codi}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície Sync massive GET", tags = { "orchestrator" })
	public ResponseEntity<ReturnMassiveRDTO> commerceMassiveSyncGET(
			@PathVariable(value = "codi") String code,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestParam(required = false) String transactionId) {

		logger.info(LogsConstants.LOG_START);
		ReturnMassiveRDTO returnMassiveRDTO = new ReturnMassiveRDTO();
		queryParameterRDTO = new QueryParameterRDTO();

		try {
			queryParameterRDTO.setCodeContract(code);
			queryParameterRDTO.setDateReference(dateReference);
			queryParameterRDTO.setCodeUser(codeUser);
			queryParameterRDTO.setTransactionId(transactionId);
			
			returnMassiveRDTO = commerceService.selectMassive(queryParameterRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, ex));
			return new ResponseEntity<>(returnMassiveRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnMassiveRDTO, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/comerc/{codi}/contracta/{codiContracta}/detall", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície Sync only GET", tags = { "orchestrator" })
	public ResponseEntity<ReturnCommerceDetailedRDTO> commerceDetailedSyncGET(
			@PathVariable(value = "codi") String code,
			@PathVariable(value = "codiContracta") String codeContract,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestParam(required = false) String transactionId) {

		logger.info(LogsConstants.LOG_START);
		ReturnCommerceDetailedRDTO returnCommerceDetailedRDTO = new ReturnCommerceDetailedRDTO();
		queryParameterRDTO = new QueryParameterRDTO();

		try {
			queryParameterRDTO.setCode(code);
			queryParameterRDTO.setCodeContract(codeContract);
			queryParameterRDTO.setDateReference(dateReference);
			queryParameterRDTO.setCodeUser(codeUser);
			queryParameterRDTO.setTransactionId(transactionId);
			
			returnCommerceDetailedRDTO = commerceService.selectDetailed(queryParameterRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, ex));
			return new ResponseEntity<>(returnCommerceDetailedRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnCommerceDetailedRDTO, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/comerc/{codi}/contracta/{codiContracta}/elements", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície Sync only GET", tags = { "orchestrator" })
	public ResponseEntity<ReturnCommerceElementsRDTO> commerceElementsSyncGET(
			@PathVariable(value = "codi") String code,
			@PathVariable(value = "codiContracta") String codeContract,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestParam(required = false) String transactionId) {

		logger.info(LogsConstants.LOG_START);
		ReturnCommerceElementsRDTO returnCommerceElementsRDTO = new ReturnCommerceElementsRDTO();
		queryParameterRDTO = new QueryParameterRDTO();

		try {
			queryParameterRDTO.setCode(code);
			queryParameterRDTO.setCodeContract(codeContract);
			queryParameterRDTO.setDateReference(dateReference);
			queryParameterRDTO.setCodeUser(codeUser);
			queryParameterRDTO.setTransactionId(transactionId);
			
			returnCommerceElementsRDTO = commerceService.selectElements(queryParameterRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, ex));
			return new ResponseEntity<>(returnCommerceElementsRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnCommerceElementsRDTO, HttpStatus.OK);
	}	
	
	@RequestMapping(value = "/recursoshumans/cataleg/personal/treballador", method = { RequestMethod.POST })
	@ResponseBody
	@ApiOperation(value = "Interfície Sync for POST", tags = { "orchestrator" })
	public ResponseEntity<ReturnRDTO> personalCatalogSync(@RequestBody EmployeeCatalogRDTO employeeCatalogRDTO) {

		logger.info(LogsConstants.LOG_START);

		try {
			
			returnRDTO = employeeCatalogService.insert(employeeCatalogRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, employeeCatalogRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/recursoshumans/cataleg/personal/treballador/contracta/{codi}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície Sync massive GET", tags = { "orchestrator" })
	public ResponseEntity<ReturnMassiveRDTO> employeCatalogMassiveSyncGET(
			@PathVariable(value = "codi") String code,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestParam(required = false) String transactionId) {

		logger.info(LogsConstants.LOG_START);
		ReturnMassiveRDTO returnMassiveRDTO = new ReturnMassiveRDTO();
		queryParameterRDTO = new QueryParameterRDTO();

		try {
			queryParameterRDTO.setCodeContract(code);
			queryParameterRDTO.setDateReference(dateReference);
			queryParameterRDTO.setCodeUser(codeUser);
			queryParameterRDTO.setTransactionId(transactionId);
			
			returnMassiveRDTO = employeeCatalogService.selectMassive(queryParameterRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, ex));
			return new ResponseEntity<>(returnMassiveRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnMassiveRDTO, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/recursoshumans/cataleg/personal/treballador/contracta/{codi}/detall", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície Sync only GET", tags = { "orchestrator" })
	public ResponseEntity<ReturnEmployeeCatalogDetailedRDTO> employeCatalogDetailedSyncGET(
			@PathVariable(value = "codi") String code,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestParam(required = false) String transactionId) {

		logger.info(LogsConstants.LOG_START);
		ReturnEmployeeCatalogDetailedRDTO returnEmployeeCatalogDetailedRDTO = new ReturnEmployeeCatalogDetailedRDTO();
		queryParameterRDTO = new QueryParameterRDTO();

		try {
			queryParameterRDTO.setCodeContract(code);
			queryParameterRDTO.setDateReference(dateReference);
			queryParameterRDTO.setCodeUser(codeUser);
			queryParameterRDTO.setTransactionId(transactionId);
			
			returnEmployeeCatalogDetailedRDTO = employeeCatalogService.selectDetailed(queryParameterRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, ex));
			return new ResponseEntity<>(returnEmployeeCatalogDetailedRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnEmployeeCatalogDetailedRDTO, HttpStatus.OK);
	}

	@RequestMapping(path = "/recursmaterial/vehicle/{codi}/contracta/{codiContracta}/amortitzacio", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície Sync only GET", tags = { "orchestrator" })
	public ResponseEntity<ReturnBreakdownAmortizationRDTO> materialResourceVehicleAmortizationSyncGET(
			@PathVariable(value = "codi") String code,
			@PathVariable(value = "codiContracta") String codeContract,
			@RequestParam(value = "codiUsuari",required = true) String codeUser,
			@RequestParam(required = true) String transactionId) {

		logger.info(LogsConstants.LOG_START);
		ReturnBreakdownAmortizationRDTO returnBreakdownAmortizationRDTO = new ReturnBreakdownAmortizationRDTO();
		queryParameterRDTO = new QueryParameterRDTO();

		try {
			queryParameterRDTO.setCode(code);
			queryParameterRDTO.setCodeContract(codeContract);
			queryParameterRDTO.setCodeUser(codeUser);
			queryParameterRDTO.setTransactionId(transactionId);
			
			returnBreakdownAmortizationRDTO = materialResourceService.selectAmortization(queryParameterRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, ex));
			return new ResponseEntity<>(returnBreakdownAmortizationRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnBreakdownAmortizationRDTO, HttpStatus.OK);
	}	
	
	@RequestMapping(path = "/recursmaterial/vehicle/{codi}/contracta/{codiContracta}/despesa", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície Sync only GET", tags = { "orchestrator" })
	public ResponseEntity<ReturnMaterialResourceVehicleExpensesRDTO> materialResourceVehicleExpensesSyncGET(
			@PathVariable(value = "codi") String code,
			@PathVariable(value = "codiContracta") String codeContract,
			@RequestParam(value = "codiUsuari", required = true) String codeUser,
			@RequestParam(required = true) String transactionId) {

		logger.info(LogsConstants.LOG_START);
		ReturnMaterialResourceVehicleExpensesRDTO returnMaterialResourceVehicleExpensesRDTO = new ReturnMaterialResourceVehicleExpensesRDTO();
		queryParameterRDTO = new QueryParameterRDTO();

		try {
			queryParameterRDTO.setCode(code);
			queryParameterRDTO.setCodeContract(codeContract);
			queryParameterRDTO.setCodeUser(codeUser);
			queryParameterRDTO.setTransactionId(transactionId);
			
			returnMaterialResourceVehicleExpensesRDTO = materialResourceService.selectExpenses(queryParameterRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, ex));
			return new ResponseEntity<>(returnMaterialResourceVehicleExpensesRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnMaterialResourceVehicleExpensesRDTO, HttpStatus.OK);
	}	
	
	@RequestMapping(path = "/recursmaterial/vehicle/{codi}/contracta/{codiContracta}/periode/{periode}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície Sync only GET", tags = { "orchestrator" })
	public ResponseEntity<ReturnMaterialResourceVehiclePeriodRDTO> materialResourceVehiclePeriodSyncGET(
			@PathVariable(value = "codi") String code,
			@PathVariable(value = "codiContracta") String codeContract,
			@PathVariable(value = "periode") String period,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestParam(required = false) String transactionId) {

		logger.info(LogsConstants.LOG_START);
		ReturnMaterialResourceVehiclePeriodRDTO returnMaterialResourceVehiclePeriodRDTO = new ReturnMaterialResourceVehiclePeriodRDTO();
		queryParameterRDTO = new QueryParameterRDTO();

		try {
			queryParameterRDTO.setCode(code);
			queryParameterRDTO.setCodeContract(codeContract);
			queryParameterRDTO.setPeriod(period);
			queryParameterRDTO.setCodeUser(codeUser);
			queryParameterRDTO.setTransactionId(transactionId);
			
			returnMaterialResourceVehiclePeriodRDTO = materialResourceService.selectPeriod(queryParameterRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, ex));
			return new ResponseEntity<>(returnMaterialResourceVehiclePeriodRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnMaterialResourceVehiclePeriodRDTO, HttpStatus.OK);
	}	
	
	@RequestMapping(path = "/ubicacio/contracta/{codi}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície Sync massive GET", tags = { "orchestrator" })
	public ResponseEntity<ReturnMassiveRDTO> ubicationMassiveSyncGET(
			@PathVariable(value = "codi") String code,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestParam(required = false) String transactionId) {

		logger.info(LogsConstants.LOG_START);
		ReturnMassiveRDTO returnMassiveRDTO = new ReturnMassiveRDTO();
		queryParameterRDTO = new QueryParameterRDTO();

		try {
			queryParameterRDTO.setCodeContract(code);
			queryParameterRDTO.setDateReference(dateReference);
			queryParameterRDTO.setCodeUser(codeUser);
			queryParameterRDTO.setTransactionId(transactionId);
			
			returnMassiveRDTO = ubicationService.selectMassive(queryParameterRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, ex));
			return new ResponseEntity<>(returnMassiveRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnMassiveRDTO, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/ubicacio/{codi}/contracta/{codiContracta}/detall", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície Sync only GET", tags = { "orchestrator" })
	public ResponseEntity<ReturnUbicationDetailedRDTO> ubicationDetailedSyncGET(
			@PathVariable(value = "codi") String code,
			@PathVariable(value = "codiContracta") String codeContract,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestParam(required = false) String transactionId) {

		logger.info(LogsConstants.LOG_START);
		ReturnUbicationDetailedRDTO returnUbicationDetailedRDTO = new ReturnUbicationDetailedRDTO();
		queryParameterRDTO = new QueryParameterRDTO();

		try {
			queryParameterRDTO.setCode(code);
			queryParameterRDTO.setCodeContract(codeContract);
			queryParameterRDTO.setDateReference(dateReference);
			queryParameterRDTO.setCodeUser(codeUser);
			queryParameterRDTO.setTransactionId(transactionId);
			
			returnUbicationDetailedRDTO = ubicationService.selectDetailed(queryParameterRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, ex));
			return new ResponseEntity<>(returnUbicationDetailedRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnUbicationDetailedRDTO, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/mobiliarilogic/contracta/{codi}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície Sync massive GET", tags = { "orchestrator" })
	public ResponseEntity<ReturnLogicalModelMassiveRDTO> mclMassiveSyncGET(
			@PathVariable(value = "codi") String code,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestParam(required = false) String transactionId) {

		logger.info(LogsConstants.LOG_START);
		ReturnLogicalModelMassiveRDTO returnLogicalModelMassiveRDTO = new ReturnLogicalModelMassiveRDTO();
		queryParameterRDTO = new QueryParameterRDTO();

		try {
			queryParameterRDTO.setCodeContract(code);
			queryParameterRDTO.setDateReference(dateReference);
			queryParameterRDTO.setCodeUser(codeUser);
			queryParameterRDTO.setTransactionId(transactionId);
			
			returnLogicalModelMassiveRDTO = logicalModelService.selectMassive(queryParameterRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, ex));
			return new ResponseEntity<>(returnLogicalModelMassiveRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnLogicalModelMassiveRDTO, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/mobiliarilogic/{codi}/contracta/{codiContracta}/detall", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície Sync only GET", tags = { "orchestrator" })
	public ResponseEntity<ReturnLogicalModelDetailedRDTO> mclDetailedSyncGET(
			@PathVariable(value = "codi") String code,
			@PathVariable(value = "codiContracta") String codeContract,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestParam(required = false) String transactionId) {

		logger.info(LogsConstants.LOG_START);
		ReturnLogicalModelDetailedRDTO returnLogicalModelDetailedRDTO = new ReturnLogicalModelDetailedRDTO();
		queryParameterRDTO = new QueryParameterRDTO();

		try {
			queryParameterRDTO.setCode(code);
			queryParameterRDTO.setCodeContract(codeContract);
			queryParameterRDTO.setDateReference(dateReference);
			queryParameterRDTO.setCodeUser(codeUser);
			queryParameterRDTO.setTransactionId(transactionId);
			
			returnLogicalModelDetailedRDTO = logicalModelService.selectDetailed(queryParameterRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, ex));
			return new ResponseEntity<>(returnLogicalModelDetailedRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnLogicalModelDetailedRDTO, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/mobiliarifisic/contracta/{codi}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície Sync massive GET", tags = { "orchestrator" })
	public ResponseEntity<ReturnPhysicalModelMassiveRDTO> physicalModelMassiveSyncGET(
			@PathVariable(value = "codi") String codeContract,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestParam(required = false) String transactionId) {

		logger.info(LogsConstants.LOG_START);
		ReturnPhysicalModelMassiveRDTO returnPhysicalModelMassiveRDTO = new ReturnPhysicalModelMassiveRDTO();
		queryParameterRDTO = new QueryParameterRDTO();

		try {
			queryParameterRDTO.setCodeContract(codeContract);
			queryParameterRDTO.setDateReference(dateReference);
			queryParameterRDTO.setCodeUser(codeUser);
			queryParameterRDTO.setTransactionId(transactionId);
			
			returnPhysicalModelMassiveRDTO = physicalModelService.selectMassive(queryParameterRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, ex));
			return new ResponseEntity<>(returnPhysicalModelMassiveRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnPhysicalModelMassiveRDTO, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/mobiliarifisic/{codi}/contracta/{codiContracta}/detall", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície Sync only GET", tags = { "orchestrator" })
	public ResponseEntity<ReturnPhysicalModelDetailedRDTO> physicalModelDetailedSyncGET(
			@PathVariable(value = "codi") String code,
			@PathVariable(value = "codiContracta") String codeContract,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestParam(required = false) String transactionId) {

		logger.info(LogsConstants.LOG_START);
		ReturnPhysicalModelDetailedRDTO returnPhysicalModelDetailedRDTO = new ReturnPhysicalModelDetailedRDTO();
		queryParameterRDTO = new QueryParameterRDTO();

		try {
			queryParameterRDTO.setCode(code);
			queryParameterRDTO.setCodeContract(codeContract);
			queryParameterRDTO.setDateReference(dateReference);
			queryParameterRDTO.setCodeUser(codeUser);
			queryParameterRDTO.setTransactionId(transactionId);
			
			returnPhysicalModelDetailedRDTO = physicalModelService.selectDetailed(queryParameterRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, ex));
			return new ResponseEntity<>(returnPhysicalModelDetailedRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnPhysicalModelDetailedRDTO, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/mobiliarifisic/contracta/{codi}/fraccio/{codiFraccio}/territori/{codiTerritori}/tipus/{codiTipus}/grup/{codiGrup}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície Sync only GET", tags = { "orchestrator" })
	public ResponseEntity<ReturnPhysicalModelAmortizationRDTO> physicalModelAmortizaionSyncGET(
			@PathVariable(value = "codi", required = true) String codeContract,
			@PathVariable(value = "codiFraccio", required = true) String codeFraction,
			@PathVariable(value = "codiTerritori", required = true) String codeTerritory,
			@PathVariable(value = "codiTipus", required = true) String codeType,
			@PathVariable(value = "codiGrup", required = true) String codeGroup,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestParam(required = false) String transactionId) {

		logger.info(LogsConstants.LOG_START);
		ReturnPhysicalModelAmortizationRDTO returnPhysicalModelAmortizationRDTO = new ReturnPhysicalModelAmortizationRDTO();
		queryParameterRDTO = new QueryParameterRDTO();

		try {
			queryParameterRDTO.setCodeContract(codeContract);
			queryParameterRDTO.setCodeFraction(codeFraction);
			queryParameterRDTO.setCodeTerritory(codeTerritory);
			queryParameterRDTO.setCodeType(codeType);
			queryParameterRDTO.setCodeGroup(codeGroup);
			queryParameterRDTO.setCodeUser(codeUser);
			queryParameterRDTO.setTransactionId(transactionId);
			
			returnPhysicalModelAmortizationRDTO = physicalModelService.selectAmortization(queryParameterRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, ex));
			return new ResponseEntity<>(returnPhysicalModelAmortizationRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnPhysicalModelAmortizationRDTO, HttpStatus.OK);
	}
	
	

		@RequestMapping(value = { "/suportdocumental","/suportdocumental/{codi}/entitat/{codiEntitat}" }, method = {RequestMethod.POST, RequestMethod.PUT })
		@ResponseBody
		@ApiOperation(value = "Interfície Sync for POST Documentary Support", tags = { "orchestrator" })
		public ResponseEntity<ReturnRDTO> documentarySupportSync(@RequestBody DocumentarySupportRDTO documentarySupportRDTO,
				@PathVariable(value = "codi", required = false) String codeDocumentarySupport,
				@PathVariable(value = "codiEntitat", required = false) String codeEntity,
														HttpServletRequest request) {

			logger.info(LogsConstants.LOG_START); 

			try {
				if(codeDocumentarySupport != null) {
					documentarySupportRDTO.setCodeContractDocument(codeDocumentarySupport);
				}
				if(codeEntity != null) {
					documentarySupportRDTO.setCode(codeEntity);
				}
				
				returnRDTO = documentarySupportService.redirect(documentarySupportRDTO, request);

			} catch (Exception ex) {
				logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, documentarySupportRDTO), ex);
				return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			logger.info(LogsConstants.LOG_END);

			return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
		}

		@RequestMapping(path = "/suportdocumental/contracta/{codi}/tipusEntitat/{tipusEntitat}/codiEntitat/{codiEntitat}", method = RequestMethod.GET)
		@ResponseBody
		@ApiOperation(value = "Interfície Sync only GET", tags = { "orchestrator" })
		public ResponseEntity<ReturnDocumentarySupportRDTO> documentarySupportSyncGET(
				@PathVariable(value = "codi", required = true) String codeContract,
				@PathVariable(value = "tipusEntitat", required = true) String codeTypeEntity,
				@PathVariable(value = "codiEntitat", required = true) String code,
				@RequestParam(value = "codiUsuari", required = false) String codeUser,
				@RequestParam(value = "considerarAnulacions", required = false) String considerAnnulments,
				@RequestParam(required = false) String transactionId) {
			
			logger.info(LogsConstants.LOG_START);
			ReturnDocumentarySupportRDTO returnDocumentarySupportRDTO = new ReturnDocumentarySupportRDTO();
			queryParameterDocumentarySupportRDTO = new QueryParameterDocumentarySupportRDTO();

			try {
				queryParameterDocumentarySupportRDTO.setCodeContract(codeContract);
				queryParameterDocumentarySupportRDTO.setCode(code);
				queryParameterDocumentarySupportRDTO.setConsiderAnnulments(considerAnnulments);
				queryParameterDocumentarySupportRDTO.setCodeUser(codeUser);
				queryParameterDocumentarySupportRDTO.setCodeTypeEntity(codeTypeEntity);
				queryParameterDocumentarySupportRDTO.setTransactionId(transactionId);
				
				returnDocumentarySupportRDTO = documentarySupportService.selectDocumentarySupport(queryParameterDocumentarySupportRDTO);

			} catch (Exception ex) {
				logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, ex));
				return new ResponseEntity<>(returnDocumentarySupportRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			logger.info(LogsConstants.LOG_END);

			return new ResponseEntity<>(returnDocumentarySupportRDTO, HttpStatus.OK);
		}

		@RequestMapping(value = { "/actuacio","/actuacio/{codi}/entitat/{codiEntitat}" }, method = {RequestMethod.POST, RequestMethod.PUT })
		@ResponseBody
		@ApiOperation(value = "Interfície Sync for POST and PUT Actuacio", tags = { "orchestrator" })
		public ResponseEntity<ReturnRDTO> acttuationSync(@RequestBody ActuationRDTO actuationRDTO,
				@PathVariable(value = "codi", required = false) String code,
				@PathVariable(value = "codiEntitat", required = false) String codeEntity,
				HttpServletRequest request) {

			logger.info(LogsConstants.LOG_START); 

			try {
				if(code != null) {
					actuationRDTO.setCode(code);
				}
				if(codeEntity != null) {
					actuationRDTO.setCodeEntity(codeEntity);
				}								
				
				returnRDTO = actuationService.redirect(actuationRDTO, request);

			} catch (Exception ex) {
				logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, actuationRDTO), ex);
				return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			logger.info(LogsConstants.LOG_END);

			return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
		}

		@RequestMapping(path = "/actuacio/contracta/{codi}/tipusEntitat/{tipusEntitat}/codiEntitat/{codiEntitat}", method = RequestMethod.GET)
		@ResponseBody
		@ApiOperation(value = "Interfície Sync only GET", tags = { "orchestrator" })
		public ResponseEntity<ReturnActuationRDTO> actuationSyncGET(
				@PathVariable(value = "codi", required = true) String codeContract,
				@PathVariable(value = "tipusEntitat", required = true) String codeTypeEntity,
				@PathVariable(value = "codiEntitat", required = true) String codeEntity,
				@RequestParam(value = "considerarAnulacions", required = false) String considerAnnulments,
				@RequestParam(value = "codiUsuari", required = false) String codeUser,
				@RequestParam(required = false) String transactionId) {
			
			logger.info(LogsConstants.LOG_START);
			ReturnActuationRDTO returnActuationRDTO = new ReturnActuationRDTO();
			QueryParameterActuationRDTO queryParameterActuationRDTO = new QueryParameterActuationRDTO();

			try {
				queryParameterActuationRDTO.setCodeContract(codeContract);
				queryParameterActuationRDTO.setCodeTypeEntity(codeTypeEntity);
				queryParameterActuationRDTO.setCodeEntity(codeEntity);
				queryParameterActuationRDTO.setConsiderAnnulments(considerAnnulments);
				queryParameterActuationRDTO.setCodeUser(codeUser);
				queryParameterActuationRDTO.setTransactionId(transactionId);
				
				returnActuationRDTO = actuationService.selectActuation(queryParameterActuationRDTO);

			} catch (Exception ex) {
				logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, ex));
				return new ResponseEntity<>(returnActuationRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			logger.info(LogsConstants.LOG_END);

			return new ResponseEntity<>(returnActuationRDTO, HttpStatus.OK);
		}

		@RequestMapping(value = { "/suportdocumental/despesa","/suportdocumental/despesa/{codi}/entitat/{codiEntitat}" }, method = {RequestMethod.POST, RequestMethod.PUT })
		@ResponseBody
		@ApiOperation(value = "Interfície Sync for POST Documentary Support", tags = { "orchestrator" })
		public ResponseEntity<ReturnRDTO> documentarySupportExpenseSync(@RequestBody DocumentarySupportExpenseRDTO documentarySupportExpenseRDTO,
				@PathVariable(value = "codi", required = false) String codeDocumentarySupport,
				@PathVariable(value = "codiEntitat", required = false) String codeEntity,
														HttpServletRequest request) {

			logger.info(LogsConstants.LOG_START); 

			try {
				if(codeDocumentarySupport != null) {
					documentarySupportExpenseRDTO.setCodeContractDocument(codeDocumentarySupport);
				}
				if(codeEntity != null) {
					documentarySupportExpenseRDTO.setCode(codeEntity);
				}
				
				returnRDTO = documentarySupportService.redirectExpense(documentarySupportExpenseRDTO, request);

			} catch (Exception ex) {
				logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, documentarySupportExpenseRDTO), ex);
				return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			logger.info(LogsConstants.LOG_END);

			return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
		}
		
		@RequestMapping(value = { "/sensor", "/sensor/{codi}" }, method = {RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
		@ResponseBody
		@ApiOperation(value = "Interfície Sync for POST, PUT and DELETE", tags = { "orchestrator" })
		public ResponseEntity<ReturnRDTO> sensorSync(@RequestBody SensorRDTO sensorRDTO,
														@PathVariable(value = "codi", required = false) String code,
														HttpServletRequest request) {

			logger.info(LogsConstants.LOG_START); 

			try {
				
				if(code != null) {
				   sensorRDTO.setCode(code);
				}
			
				returnRDTO = sensorService.redirect(sensorRDTO, request);

			} catch (Exception ex) {
				logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, sensorRDTO), ex);
				return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			logger.info(LogsConstants.LOG_END);

			return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
		}
		
		@RequestMapping(path = "/sensor/{codi}/contracta/{codiContracta}/detall", method = RequestMethod.GET)
		@ResponseBody
		@ApiOperation(value = "Interfície Sync only GET", tags = { "orchestrator" })
		public ResponseEntity<ReturnSensorDetailedRDTO> sensorDetailedSyncGET(
				@PathVariable(value = "codi") String code,
				@PathVariable(value = "codiContracta") String codeContract,
				@RequestParam(value = "dataReferencia", required = false) String dateReference,
				@RequestParam(value = "codiUsuari", required = false) String codeUser,
				@RequestParam(required = false) String transactionId) {

			logger.info(LogsConstants.LOG_START);
			ReturnSensorDetailedRDTO returnSensorDetailedRDTO = new ReturnSensorDetailedRDTO();
			queryParameterRDTO = new QueryParameterRDTO();

			try {
				queryParameterRDTO.setCode(code);
				queryParameterRDTO.setCodeContract(codeContract);
				queryParameterRDTO.setDateReference(dateReference);
				queryParameterRDTO.setCodeUser(codeUser);
				queryParameterRDTO.setTransactionId(transactionId);
				
				returnSensorDetailedRDTO = sensorService.selectDetailed(queryParameterRDTO);

			} catch (Exception ex) {
				logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, ex));
				return new ResponseEntity<>(returnSensorDetailedRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			logger.info(LogsConstants.LOG_END);

			return new ResponseEntity<>(returnSensorDetailedRDTO, HttpStatus.OK);
		}	

		@RequestMapping(path = "/sensor/contracta/{codi}", method = RequestMethod.GET)
		@ResponseBody
		@ApiOperation(value = "Interfície Sync massive GET", tags = { "orchestrator" })
		public ResponseEntity<ReturnSensorMassiveRDTO> sensorMassiveSyncGET(
				@PathVariable(value = "codi") String code,
				@RequestParam(value = "dataReferencia", required = false) String dateReference,
				@RequestParam(value = "codiUsuari", required = false) String codeUser,
				@RequestParam(required = false) String transactionId) {

			logger.info(LogsConstants.LOG_START);
			ReturnSensorMassiveRDTO returnSensorMassiveRDTO = new ReturnSensorMassiveRDTO();
			queryParameterRDTO = new QueryParameterRDTO();

			try {
				queryParameterRDTO.setCodeContract(code);
				queryParameterRDTO.setDateReference(dateReference);
				queryParameterRDTO.setCodeUser(codeUser);
				queryParameterRDTO.setTransactionId(transactionId);
				
				returnSensorMassiveRDTO = sensorService.selectMassive(queryParameterRDTO);

			} catch (Exception ex) {
				logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, ex));
				return new ResponseEntity<>(returnSensorMassiveRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			logger.info(LogsConstants.LOG_END);

			return new ResponseEntity<>(returnSensorMassiveRDTO, HttpStatus.OK);
		}


}