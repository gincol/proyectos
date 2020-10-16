package es.bcn.imi.framework.vigia.frontal.web.rest.controller.masters.sync;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.bcn.imi.framework.vigia.frontal.business.masters.MastersService;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterMastersRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnBranchModelsVehicleRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnCompatibilityMclUbicationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnCompatibilitySensorMcfRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnCompatibilitySensorRRMMRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnEventClassificationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnHolidayCalendarRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnPricesTableRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnServiceContractRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnServiceHierarchyRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnServiceSubContractRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnWasteManagementPlantDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnWasteManagementPlantMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnWorkRegimeRelationshipRDTO;
import es.bcn.vigia.fmw.libutils.Utils;
import es.bcn.vigia.fmw.libutils.enums.ReturnEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.opentrends.openframe.services.rest.controller.AbstractRestController;
import net.opentrends.openframe.services.rest.http.ResponseEntity;

@RestController
@RequestMapping("/sync/mestres")
@Lazy(true)
@Api("FRONTAL API")
public class MastersController extends AbstractRestController {

	private Log logger = LogFactory.getLog(this.getClass());

	@Autowired(required = true)
	@Qualifier(ServicesConstants.SRV_FRONTAL_MASTERS)
	private MastersService mastersService;
	
	private ReturnRDTO returnRDTO;
	
	private QueryParameterMastersRDTO queryParameterMastersRDTO;

	
	@RequestMapping(value = "/serveis/jerarquia", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta massiva de Jerarquia de Serveis", tags = { "mestres" })
	public ResponseEntity<ReturnServiceHierarchyRDTO> selectServiceHierarchy(
			@RequestParam(value = "dataReferencia", required = false) String dateReference) {

		logger.info(LogsConstants.LOG_START);		
		ReturnServiceHierarchyRDTO returnServiceHierarchyRDTO = new ReturnServiceHierarchyRDTO();
		queryParameterMastersRDTO = new QueryParameterMastersRDTO();
		
		try {					
			queryParameterMastersRDTO.setDateReference(dateReference);
			returnServiceHierarchyRDTO = mastersService.selectServiceHierarchy(queryParameterMastersRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnServiceHierarchyRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO,ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			returnServiceHierarchyRDTO.setReturnRDTO(returnRDTO);
			return new ResponseEntity<>(returnServiceHierarchyRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		
		}
		return new ResponseEntity<>(returnServiceHierarchyRDTO, HttpStatus.OK);
	}

	
	@RequestMapping(value = "/contracta/{codi}/serveis", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta de Serveis de la Contracta", tags = { "mestres" })
	public ResponseEntity<ReturnServiceContractRDTO> selectServiceContract(
			@PathVariable(value = "codi", required = false) String codeContract,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);		
		ReturnServiceContractRDTO returnServiceContractRDTO = new ReturnServiceContractRDTO();
		queryParameterMastersRDTO = new QueryParameterMastersRDTO();
		
		try {					
			queryParameterMastersRDTO.setClientId(clientId);
			queryParameterMastersRDTO.setCodeContract(codeContract);
			queryParameterMastersRDTO.setDateReference(dateReference);
			returnServiceContractRDTO = mastersService.selectServiceContract(queryParameterMastersRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnServiceContractRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO,ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			returnServiceContractRDTO.setReturnRDTO(returnRDTO);
			return new ResponseEntity<>(returnServiceContractRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		
		}
		return new ResponseEntity<>(returnServiceContractRDTO, HttpStatus.OK);
	}

	
	@RequestMapping(value = {"/contracta/{codi}/subcontracta","/contracta/{codi}/subcontracta/{codiSubcontracta}"}, method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta massiva de Subcontractistes de la contracta", tags = { "mestres" })
	public ResponseEntity<ReturnServiceSubContractRDTO> selectServiceSubContract(
			@PathVariable(value = "codi", required = false) String codeContract,
			@PathVariable(value = "codiSubcontracta", required = false) String codeSubContract,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);		
		ReturnServiceSubContractRDTO returnServiceSubContractRDTO = new ReturnServiceSubContractRDTO();
		queryParameterMastersRDTO = new QueryParameterMastersRDTO();
		
		try {					
			queryParameterMastersRDTO.setClientId(clientId);
			queryParameterMastersRDTO.setCodeContract(codeContract);
			queryParameterMastersRDTO.setCodeSubContract(codeSubContract);
			queryParameterMastersRDTO.setDateReference(dateReference);
			returnServiceSubContractRDTO = mastersService.selectServiceSubContract(queryParameterMastersRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnServiceSubContractRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO,ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			returnServiceSubContractRDTO.setReturnRDTO(returnRDTO);
			return new ResponseEntity<>(returnServiceSubContractRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		
		}
		return new ResponseEntity<>(returnServiceSubContractRDTO, HttpStatus.OK);
	}

	
	@RequestMapping(value = "/plantagestioresidus", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta massiva de Planta de gestió de residus", tags = { "mestres" })
	public ResponseEntity<ReturnWasteManagementPlantMassiveRDTO> selectWasteManagementPlantMassive(
			@RequestParam(value = "dataReferencia", required = false) String dateReference) {

		logger.info(LogsConstants.LOG_START);		
		ReturnWasteManagementPlantMassiveRDTO returnWasteManagementPlantMassiveRDTO = new ReturnWasteManagementPlantMassiveRDTO();
		queryParameterMastersRDTO = new QueryParameterMastersRDTO();
		
		try {			
			queryParameterMastersRDTO.setDateReference(dateReference);		
			returnWasteManagementPlantMassiveRDTO = mastersService.selectWasteManagementPlantMassive(queryParameterMastersRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnWasteManagementPlantMassiveRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO,ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			returnWasteManagementPlantMassiveRDTO.setReturnRDTO(returnRDTO);
			return new ResponseEntity<>(returnWasteManagementPlantMassiveRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		
		}
		return new ResponseEntity<>(returnWasteManagementPlantMassiveRDTO, HttpStatus.OK);
	}

	
	@RequestMapping(value = "/plantagestioresidus/{codi}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta detallada de Planta de gestió de residus", tags = { "mestres" })
	public ResponseEntity<ReturnWasteManagementPlantDetailedRDTO> selectWasteManagementPlantDetailed(
			@PathVariable(value = "codi", required = false) String code,
			@RequestParam(value = "dataReferencia", required = false) String dateReference) {

		logger.info(LogsConstants.LOG_START);		
		ReturnWasteManagementPlantDetailedRDTO returnWasteManagementPlantDetailedRDTO = new ReturnWasteManagementPlantDetailedRDTO();
		queryParameterMastersRDTO = new QueryParameterMastersRDTO();
		
		try {					
			queryParameterMastersRDTO.setCode(code);
			queryParameterMastersRDTO.setDateReference(dateReference);
			returnWasteManagementPlantDetailedRDTO = mastersService.selectWasteManagementPlantDetailed(queryParameterMastersRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnWasteManagementPlantDetailedRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO,ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			returnWasteManagementPlantDetailedRDTO.setReturnRDTO(returnRDTO);
			return new ResponseEntity<>(returnWasteManagementPlantDetailedRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		
		}
		return new ResponseEntity<>(returnWasteManagementPlantDetailedRDTO, HttpStatus.OK);
	}

	
	@RequestMapping(value = "/contracta/{codi}/taulapreus", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta taula de preus", tags = { "mestres" })
	public ResponseEntity<ReturnPricesTableRDTO> selectPricesTable(
			@PathVariable(value = "codi", required = false) String codeContract,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);		
		ReturnPricesTableRDTO returnPricesTableRDTO = new ReturnPricesTableRDTO();
		queryParameterMastersRDTO = new QueryParameterMastersRDTO();
		
		try {			
			queryParameterMastersRDTO.setClientId(clientId);		
			queryParameterMastersRDTO.setCodeContract(codeContract);
			queryParameterMastersRDTO.setDateReference(dateReference);
			returnPricesTableRDTO = mastersService.selectPricesTable(queryParameterMastersRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnPricesTableRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO,ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			returnPricesTableRDTO.setReturnRDTO(returnRDTO);
			return new ResponseEntity<>(returnPricesTableRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		
		}
		return new ResponseEntity<>(returnPricesTableRDTO, HttpStatus.OK);
	}

	
	@RequestMapping(value = "/classificacioesdeveniments", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta de la classificació dels esdeveniments", tags = { "mestres" })
	public ResponseEntity<ReturnEventClassificationRDTO> selectEventClassification(
			@RequestParam(value = "dataReferencia", required = false) String dateReference) {

		logger.info(LogsConstants.LOG_START);		
		ReturnEventClassificationRDTO returnEventClassificationRDTO = new ReturnEventClassificationRDTO();
		queryParameterMastersRDTO = new QueryParameterMastersRDTO();
		
		try {			
			queryParameterMastersRDTO.setDateReference(dateReference);		
			returnEventClassificationRDTO = mastersService.selectEventClassification(queryParameterMastersRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnEventClassificationRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO,ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			returnEventClassificationRDTO.setReturnRDTO(returnRDTO);
			return new ResponseEntity<>(returnEventClassificationRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		
		}
		return new ResponseEntity<>(returnEventClassificationRDTO, HttpStatus.OK);
	}

	
	@RequestMapping(value = "/marquesmodelsvehicle", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta de marques i models de vehicles", tags = { "mestres" })
	public ResponseEntity<ReturnBranchModelsVehicleRDTO> selectBranchModelsVehicle(
			@RequestParam(value = "dataReferencia", required = false) String dateReference) {

		logger.info(LogsConstants.LOG_START);		
		ReturnBranchModelsVehicleRDTO returnBranchModelsVehicleRDTO = new ReturnBranchModelsVehicleRDTO();
		queryParameterMastersRDTO = new QueryParameterMastersRDTO();
		
		try {					
			queryParameterMastersRDTO.setDateReference(dateReference);
			returnBranchModelsVehicleRDTO = mastersService.selectBranchModelsVehicle(queryParameterMastersRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnBranchModelsVehicleRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO,ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			returnBranchModelsVehicleRDTO.setReturnRDTO(returnRDTO);
			return new ResponseEntity<>(returnBranchModelsVehicleRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		
		}
		return new ResponseEntity<>(returnBranchModelsVehicleRDTO, HttpStatus.OK);
	}

	
	@RequestMapping(value = "/festius/{any}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta de Calendari de Festius", tags = { "mestres" })
	public ResponseEntity<ReturnHolidayCalendarRDTO> selectHolidayCalendar(
			@PathVariable(value = "any", required = false) String year) {

		logger.info(LogsConstants.LOG_START);		
		ReturnHolidayCalendarRDTO returnHolidayCalendarRDTO = new ReturnHolidayCalendarRDTO();
		queryParameterMastersRDTO = new QueryParameterMastersRDTO();
		
		try {					
			queryParameterMastersRDTO.setYear(year);
			returnHolidayCalendarRDTO = mastersService.selectHolidayCalendar(queryParameterMastersRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnHolidayCalendarRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO,ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			returnHolidayCalendarRDTO.setReturnRDTO(returnRDTO);
			return new ResponseEntity<>(returnHolidayCalendarRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		
		}
		return new ResponseEntity<>(returnHolidayCalendarRDTO, HttpStatus.OK);
	}

	
	@RequestMapping(value = "/contracta/{codi}/relacioregimtreball", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta de relació règim de treball amb contractista, servei, i hores/jornada", tags = { "mestres" })
	public ResponseEntity<ReturnWorkRegimeRelationshipRDTO> selectWorkRegimeRelationship(
			@PathVariable(value = "codi", required = false) String codeContract,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);		
		ReturnWorkRegimeRelationshipRDTO returnWorkRegimeRelationshipRDTO = new ReturnWorkRegimeRelationshipRDTO();
		queryParameterMastersRDTO = new QueryParameterMastersRDTO();
		
		try {	
			queryParameterMastersRDTO.setClientId(clientId);
			queryParameterMastersRDTO.setCodeContract(codeContract);
			queryParameterMastersRDTO.setDateReference(dateReference);				
			returnWorkRegimeRelationshipRDTO = mastersService.selectWorkRegimeRelationship(queryParameterMastersRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnWorkRegimeRelationshipRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO,ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			returnWorkRegimeRelationshipRDTO.setReturnRDTO(returnRDTO);
			return new ResponseEntity<>(returnWorkRegimeRelationshipRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		
		}
		return new ResponseEntity<>(returnWorkRegimeRelationshipRDTO, HttpStatus.OK);
	}

	
	@RequestMapping(value = "/compatibilitat/mobiliarilogic/ubicacio", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta de Compatibilitat tipus MCL amb tipus d'Ubicació", tags = { "mestres" })
	public ResponseEntity<ReturnCompatibilityMclUbicationRDTO> selectCompatibilityMclUbication() {

		logger.info(LogsConstants.LOG_START);		
		ReturnCompatibilityMclUbicationRDTO returnCompatibilityMclUbicationRDTO = new ReturnCompatibilityMclUbicationRDTO();
		
		try {					
			returnCompatibilityMclUbicationRDTO = mastersService.selectCompatibilityMclUbication();

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnCompatibilityMclUbicationRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO,ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			returnCompatibilityMclUbicationRDTO.setReturnRDTO(returnRDTO);
			return new ResponseEntity<>(returnCompatibilityMclUbicationRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		
		}
		return new ResponseEntity<>(returnCompatibilityMclUbicationRDTO, HttpStatus.OK);
	}

	
	@RequestMapping(value = "/compatibilitat/sensor/recursmaterial", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta de Compatibilitat Tipus Sensor amb tipus RRMM", tags = { "mestres" })
	public ResponseEntity<ReturnCompatibilitySensorRRMMRDTO> selectCompatibilitySensorRRMM() {

		logger.info(LogsConstants.LOG_START);		
		ReturnCompatibilitySensorRRMMRDTO returnCompatibilitySensorRRMMRDTO = new ReturnCompatibilitySensorRRMMRDTO();
		
		try {					
			returnCompatibilitySensorRRMMRDTO = mastersService.selectCompatibilitySensorRRMM();

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnCompatibilitySensorRRMMRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO,ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			returnCompatibilitySensorRRMMRDTO.setReturnRDTO(returnRDTO);
			return new ResponseEntity<>(returnCompatibilitySensorRRMMRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		
		}
		return new ResponseEntity<>(returnCompatibilitySensorRRMMRDTO, HttpStatus.OK);
	}

	
	@RequestMapping(value = "/compatibilitat/sensor/mobiliarifisic", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta de Compatibilitat tipus Sensor amb tipus MCF", tags = { "mestres" })
	public ResponseEntity<ReturnCompatibilitySensorMcfRDTO> selectCompatibilitySensorMcf() {

		logger.info(LogsConstants.LOG_START);		
		ReturnCompatibilitySensorMcfRDTO returnCompatibilitySensorMcfRDTO = new ReturnCompatibilitySensorMcfRDTO();
		
		try {					
			returnCompatibilitySensorMcfRDTO = mastersService.selectCompatibilitySensorMcf();

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnCompatibilitySensorMcfRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO,ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			returnCompatibilitySensorMcfRDTO.setReturnRDTO(returnRDTO);
			return new ResponseEntity<>(returnCompatibilitySensorMcfRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		
		}
		return new ResponseEntity<>(returnCompatibilitySensorMcfRDTO, HttpStatus.OK);
	}

}
