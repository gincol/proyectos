package es.bcn.imi.framework.vigia.orquestrador.web.rest.controller.sync;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import es.bcn.imi.framework.vigia.orquestrador.business.certification.CertificationService;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CertificationCleaningServiceRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CertificationCollectionServiceRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CertificationExpenseInstallationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CertificationExtraordinaryRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CertificationInspectionRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CertificationInvestmentFurnitureRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CertificationInvestmentRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CertificationInvoiceRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CertificationOthersRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CertificationPersonalRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CertificationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CertificationRegularizationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterCertificationProposalsRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnCertificationProposalsRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.opentrends.openframe.services.rest.controller.AbstractRestController;
import net.opentrends.openframe.services.rest.http.ResponseEntity;

@RestController
@RequestMapping("/sync/certificacio")
@Lazy(true)
@Api("ORQUESTRATOR API")
public class CertificationController extends AbstractRestController{
	
	private Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired(required = true)
	@Qualifier(ServicesConstants.SRV_CERTIFICATION_CERTIFICATION)
	private CertificationService certificationService;
	
	private ReturnRDTO returnRDTO;
	
	@RequestMapping(value = { "/versio" }, method = {RequestMethod.POST})
	@ResponseBody
	@ApiOperation(value = "Interfície Sync for POST", tags = { "orchestrator" })
	public ResponseEntity<ReturnRDTO> versionAndHeaderSync(@RequestBody CertificationRDTO certificationRDTO,
													HttpServletRequest request) {

		logger.info(LogsConstants.LOG_START); 

		try {
			returnRDTO = certificationService.sendVersionAndHeader(certificationRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, certificationRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}
	
	@RequestMapping(value = { "/altres" }, method = {RequestMethod.POST})
	@ResponseBody
	@ApiOperation(value = "Interfície Sync for POST", tags = { "orchestrator" })
	public ResponseEntity<ReturnRDTO> otherExpensesSync(@RequestBody CertificationOthersRDTO certificationOthersRDTO,
													HttpServletRequest request) {

		logger.info(LogsConstants.LOG_START); 

		try {
			returnRDTO = certificationService.sendOthersExpensesDetails(certificationOthersRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, certificationOthersRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = { "/extraordinari" }, method = {RequestMethod.POST})
	@ResponseBody
	@ApiOperation(value = "Interfície Sync for POST", tags = { "orchestrator" })
	public ResponseEntity<ReturnRDTO> extraordinaryConceptSync(@RequestBody CertificationExtraordinaryRDTO certificationExtraordinaryRDTO,
													HttpServletRequest request) {

		logger.info(LogsConstants.LOG_START); 

		try {
			returnRDTO = certificationService.sendExtraordinaryConceptsDetails(certificationExtraordinaryRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, certificationExtraordinaryRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}


	@RequestMapping(value = { "/inspeccio" }, method = {RequestMethod.POST})
	@ResponseBody
	@ApiOperation(value = "Interfície Sync for POST", tags = { "orchestrator" })
	public ResponseEntity<ReturnRDTO> inspectionSync(@RequestBody CertificationInspectionRDTO certificationInspectionRDTO,
													HttpServletRequest request) {

		logger.info(LogsConstants.LOG_START); 

		try {
			returnRDTO = certificationService.sendInspectionsDetails(certificationInspectionRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, certificationInspectionRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}

	
	@RequestMapping(value = { "/instalacio" }, method = {RequestMethod.POST})
	@ResponseBody
	@ApiOperation(value = "Interfície Sync for POST", tags = { "orchestrator" })
	public ResponseEntity<ReturnRDTO> installationExpensesSync(@RequestBody CertificationExpenseInstallationRDTO certificationInstallationRDTO,
													HttpServletRequest request) {

		logger.info(LogsConstants.LOG_START); 

		try {
			returnRDTO = certificationService.sendInstallationsCostDetails(certificationInstallationRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, certificationInstallationRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = { "/inversio" }, method = {RequestMethod.POST})
	@ResponseBody
	@ApiOperation(value = "Interfície Sync for POST", tags = { "orchestrator" })
	public ResponseEntity<ReturnRDTO> materialResourceInvestmentSync(@RequestBody CertificationInvestmentRDTO certificationInvestmentRDTO,
													HttpServletRequest request) {

		logger.info(LogsConstants.LOG_START); 

		try {
			returnRDTO = certificationService.sendInvestmentsDetails(certificationInvestmentRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, certificationInvestmentRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = { "/mobiliari" }, method = {RequestMethod.POST})
	@ResponseBody
	@ApiOperation(value = "Interfície Sync for POST", tags = { "orchestrator" })
	public ResponseEntity<ReturnRDTO> furnitureInvestmentSync(@RequestBody CertificationInvestmentFurnitureRDTO certificationInvestmentFurnitureRDTO,
													HttpServletRequest request) {

		logger.info(LogsConstants.LOG_START); 

		try {
			returnRDTO = certificationService.sendInvestmentsFurnitureDetails(certificationInvestmentFurnitureRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, certificationInvestmentFurnitureRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}

	
	@RequestMapping(value = { "/neteja" }, method = {RequestMethod.POST})
	@ResponseBody
	@ApiOperation(value = "Interfície Sync for POST", tags = { "orchestrator" })
	public ResponseEntity<ReturnRDTO> cleningServiceSync(@RequestBody CertificationCleaningServiceRDTO certificationCleaningServiceRDTO,
													HttpServletRequest request) {

		logger.info(LogsConstants.LOG_START); 

		try {
			returnRDTO = certificationService.sendCleaningServiceDetails(certificationCleaningServiceRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, certificationCleaningServiceRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}
	
	@RequestMapping(value = { "/recollida" }, method = {RequestMethod.POST})
	@ResponseBody
	@ApiOperation(value = "Interfície Sync for POST", tags = { "orchestrator" })
	public ResponseEntity<ReturnRDTO> collectionServiceSync(@RequestBody CertificationCollectionServiceRDTO certificationCollectionServiceRDTO,
													HttpServletRequest request) {

		logger.info(LogsConstants.LOG_START); 

		try {
			returnRDTO = certificationService.sendCollectionSeviceDetails(certificationCollectionServiceRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, certificationCollectionServiceRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}


	@RequestMapping(value = { "/regularitzacio" }, method = {RequestMethod.POST})
	@ResponseBody
	@ApiOperation(value = "Interfície Sync for POST", tags = { "orchestrator" })
	public ResponseEntity<ReturnRDTO> regularizationSync(@RequestBody CertificationRegularizationRDTO certificationRegularizationRDTO,
													HttpServletRequest request) {

		logger.info(LogsConstants.LOG_START); 

		try {
			returnRDTO = certificationService.sendRegularizationsDetails(certificationRegularizationRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, certificationRegularizationRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = { "/personal" }, method = {RequestMethod.POST})
	@ResponseBody
	@ApiOperation(value = "Interfície Sync for POST", tags = { "orchestrator" })
	public ResponseEntity<ReturnRDTO> staffExpenseSync(@RequestBody CertificationPersonalRDTO certificationPersonalRDTO,
													HttpServletRequest request) {

		logger.info(LogsConstants.LOG_START); 

		try {
			returnRDTO = certificationService.sendStaffCostDetails(certificationPersonalRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, certificationPersonalRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = { "/detall/factura" }, method = {RequestMethod.POST}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseBody
	@ApiOperation(value = "Interfície de enviament de factura", tags = { "factura" }, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ReturnRDTO> sendCertificationInvoice(
			@RequestParam(value = "factura", required = true) MultipartFile attachedFile,
			@RequestPart(value = "certificacioFactura", required = true)@Valid CertificationInvoiceRDTO certificationInvoiceRDTO
			) {
		logger.info(LogsConstants.LOG_START); 

		try {
			returnRDTO = certificationService.sendInvoiceDetails(certificationInvoiceRDTO,attachedFile);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, certificationInvoiceRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}

	@RequestMapping(path = "/contracta/{codi}/propostes/{idCertificacio}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície Sync only GET", tags = { "orchestrator" })
	public ResponseEntity<ReturnCertificationProposalsRDTO> certificationSyncGET(
			@PathVariable(value = "codi", required = true) String codeContract,
			@PathVariable(value = "idCertificacio", required = true) String idCertification,
			@RequestParam(required = false) String transactionId) {
		
		logger.info(LogsConstants.LOG_START);
		ReturnCertificationProposalsRDTO returnCertificationProposalsRDTO = new ReturnCertificationProposalsRDTO();
		QueryParameterCertificationProposalsRDTO queryParameterCertificationProposalsRDTO = new QueryParameterCertificationProposalsRDTO();

		try {
			queryParameterCertificationProposalsRDTO.setCodeContract(codeContract);
			queryParameterCertificationProposalsRDTO.setIdCertification(idCertification);
			queryParameterCertificationProposalsRDTO.setTransactionId(transactionId);
			
			returnCertificationProposalsRDTO = certificationService.selectCertificationProposals(queryParameterCertificationProposalsRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, ex));
			return new ResponseEntity<>(returnCertificationProposalsRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnCertificationProposalsRDTO, HttpStatus.OK);
	}
	
	
}
