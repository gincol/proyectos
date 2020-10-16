package es.bcn.imi.framework.vigia.frontal.web.rest.controller.certification.sync;

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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import es.bcn.imi.framework.vigia.frontal.business.certification.CertificationService;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
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
import es.bcn.vigia.fmw.libutils.Utils;
import es.bcn.vigia.fmw.libutils.enums.ReturnEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.opentrends.openframe.services.rest.controller.AbstractRestController;
import net.opentrends.openframe.services.rest.http.ResponseEntity;

@RestController
@RequestMapping("/sync/certificacio")
@Lazy(true)
@Api("FRONTAL API")
public class CertificationController extends AbstractRestController {

	private Log logger = LogFactory.getLog(this.getClass());

	@Autowired(required = true)
	@Qualifier(ServicesConstants.SRV_FRONTAL_CERTIFICATION)


	private CertificationService certificationService;
	
	private ReturnRDTO returnRDTO;

	private QueryParameterCertificationProposalsRDTO queryParameterCertificationProposalsRDTO;

	private ResponseEntity<ReturnRDTO> processReturnRDTO(ReturnRDTO returnRDTO)
	{
		if (!returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_REGISTERED.getCodeDescription())) {

			logger.error(String.format(LogsConstants.LOG_HTTP_BAD_REQUEST, returnRDTO.toString()));

			return new ResponseEntity<>(returnRDTO, HttpStatus.BAD_REQUEST);
		}else{
			logger.info(LogsConstants.LOG_END);
			return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
		}
	}

	@RequestMapping(value = { "/versio" }, method = { RequestMethod.POST })
	@ResponseBody
	@ApiOperation(value = "Interfície de enviament version certificacio", tags = { "versio" })
	public ResponseEntity<ReturnRDTO> sendCertificationVersion(@RequestBody CertificationRDTO certificationRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		try {
			certificationRDTO.setClientId(clientId);
			returnRDTO = certificationService.sendVersionAndHeader(certificationRDTO);

			return processReturnRDTO(returnRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, certificationRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = { "/detall/despeses/altres" }, method = { RequestMethod.POST })
	@ResponseBody
	@ApiOperation(value = "Interfície de enviament altres despeses", tags = { "altres despese" })
	public ResponseEntity<ReturnRDTO> sendCertificationOtherExpenses(
			@RequestBody CertificationOthersRDTO certificationOthersRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		try {
			certificationOthersRDTO.setClientId(clientId);
			returnRDTO = certificationService.sendOthersExpensesDetails(certificationOthersRDTO);

			return processReturnRDTO(returnRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, certificationOthersRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = { "/detall/conceptes/extraordinari" }, method = { RequestMethod.POST })
	@ResponseBody
	@ApiOperation(value = "Interfície de enviament de conceptes extraordinari", tags = { "extraordinari" })
	public ResponseEntity<ReturnRDTO> sendCertificationExtraordinary(
			@RequestBody CertificationExtraordinaryRDTO certificationExtraordinaryRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		try {
			certificationExtraordinaryRDTO.setClientId(clientId);
			returnRDTO = certificationService.sendExtraordinaryConceptsDetails(certificationExtraordinaryRDTO);

			return processReturnRDTO(returnRDTO);
			
		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, certificationExtraordinaryRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = { "/detall/factura" }, method = { RequestMethod.POST }, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseBody
	@ApiOperation(value = "Interfície de enviament de factura", tags = { "factura" }, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ReturnRDTO> sendCertificationInvoice(
			@RequestParam(value = "factura", required = true) MultipartFile attachedFile,
			@RequestPart(value = "certificacioFactura", required = true) @Valid CertificationInvoiceRDTO certificationInvoiceRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {
			certificationInvoiceRDTO.setClientId(clientId);

			returnRDTO = certificationService.sendInvoicesDetails(certificationInvoiceRDTO, attachedFile);

			return processReturnRDTO(returnRDTO);
			
		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, certificationInvoiceRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		
	}

	@RequestMapping(value = { "/detall/inspeccio" }, method = { RequestMethod.POST })
	@ResponseBody
	@ApiOperation(value = "Interfície de enviament de detail de inspeccio", tags = { "inspeccio" })
	public ResponseEntity<ReturnRDTO> sendCertificationInspection(
			@RequestBody CertificationInspectionRDTO certificationInspectionRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		try {
			certificationInspectionRDTO.setClientId(clientId);
			returnRDTO = certificationService.sendInspectionsDetails(certificationInspectionRDTO);

			return processReturnRDTO(returnRDTO);
			
		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, certificationInspectionRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = { "/detall/despeses/installacio" }, method = { RequestMethod.POST })
	@ResponseBody
	@ApiOperation(value = "Interfície de enviament de detail de despeses de installacio", tags = { "despeses de installacio" })
	public ResponseEntity<ReturnRDTO> sendCertificationExpenseInstallation(
			@RequestBody CertificationExpenseInstallationRDTO certificationExpenseInstallationRDTO,
			HttpServletRequest request,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		try {
			certificationExpenseInstallationRDTO.setClientId(clientId);
			returnRDTO = certificationService.sendInstallationExpensesDetails(certificationExpenseInstallationRDTO);

			return processReturnRDTO(returnRDTO);
			
		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, certificationExpenseInstallationRDTO),
					ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = { "/detall/inversio/recursmaterial" }, method = { RequestMethod.POST })
	@ResponseBody
	@ApiOperation(value = "Interfície de enviament de detail de inversio de recurs material", tags = {
			"inversio RRMM" })
	public ResponseEntity<ReturnRDTO> sendCertificationInvestmentRRMM(
			@RequestBody CertificationInvestmentRDTO certificationInvestmentRRMMRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		try {
			certificationInvestmentRRMMRDTO.setClientId(clientId);
			returnRDTO = certificationService.sendInvestmentRRMMDetails(certificationInvestmentRRMMRDTO);

			return processReturnRDTO(returnRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, certificationInvestmentRRMMRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = { "/detall/inversio/mobiliari" }, method = { RequestMethod.POST })
	@ResponseBody
	@ApiOperation(value = "Interfície de enviament de detail de inversio de mobiliari", tags = { "inversio mobiliari" })
	public ResponseEntity<ReturnRDTO> sendCertificationInvestmentFurniture(
			@RequestBody CertificationInvestmentFurnitureRDTO certificationInvestmentFurnitureRDTO,
			HttpServletRequest request,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		try {
			certificationInvestmentFurnitureRDTO.setClientId(clientId);
			returnRDTO = certificationService.sendInvestmentFurnitureDetails(certificationInvestmentFurnitureRDTO);

			return processReturnRDTO(returnRDTO);
			
		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, certificationInvestmentFurnitureRDTO),
					ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = { "/detall/servei/neteja" }, method = { RequestMethod.POST })
	@ResponseBody
	@ApiOperation(value = "Interfície de enviament de detail de servei de neteja", tags = { "servei neteja" })
	public ResponseEntity<ReturnRDTO> sendCertificationCleaningService(
			@RequestBody CertificationCleaningServiceRDTO certificationCleaningServiceRDTO,
			HttpServletRequest request,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		try {
			certificationCleaningServiceRDTO.setClientId(clientId);
			returnRDTO = certificationService.sendCleaningServiceDetails(certificationCleaningServiceRDTO);

			return processReturnRDTO(returnRDTO);
			
		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, certificationCleaningServiceRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = { "/detall/despeses/personal" }, method = { RequestMethod.POST })
	@ResponseBody
	@ApiOperation(value = "Interfície de enviament de detail de despeses de personal", tags = { "despeses personal" })
	public ResponseEntity<ReturnRDTO> sendCertificationStaffExpenses(
			@RequestBody CertificationPersonalRDTO certificationPersonalRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		try {
			certificationPersonalRDTO.setClientId(clientId);
			returnRDTO = certificationService.sendStaffExpensesDetails(certificationPersonalRDTO);

			return processReturnRDTO(returnRDTO);
			
		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, certificationPersonalRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}


	@RequestMapping(value = { "/detall/servei/recollida" }, method = { RequestMethod.POST })
	@ResponseBody
	@ApiOperation(value = "Interfície de enviament de detail de servei de recollida", tags = { "servei recollida" })
	public ResponseEntity<ReturnRDTO> sendCertificationCollectionService(
			@RequestBody CertificationCollectionServiceRDTO certificationCollectionServiceRDTO,
			HttpServletRequest request,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		try {
			certificationCollectionServiceRDTO.setClientId(clientId);
			returnRDTO = certificationService.sendCollectionServiceDetails(certificationCollectionServiceRDTO);

			return processReturnRDTO(returnRDTO);
			
		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, certificationCollectionServiceRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		
	}

	@RequestMapping(value = { "/detall/regularitzacio" }, method = { RequestMethod.POST })
	@ResponseBody
	@ApiOperation(value = "Interfície de enviament de detail de servei de recollida", tags = { "servei recollida" })
	public ResponseEntity<ReturnRDTO> sendCertificationRegularizatione(
			@RequestBody CertificationRegularizationRDTO certificationRegularizationRDTO,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);

		try {
			certificationRegularizationRDTO.setClientId(clientId);
			returnRDTO = certificationService.sendRegularizationsDetails(certificationRegularizationRDTO);

			return processReturnRDTO(returnRDTO);
			
		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, certificationRegularizationRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}


	@RequestMapping(value = "/contracta/{codi}/certificacio/{idCertificacio}/propostes", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta de propostes de Certificació", tags = { "certificacio" })
	public ResponseEntity<ReturnCertificationProposalsRDTO> selectCertificationProposals(
			@PathVariable(value = "codi", required = true) String codeContract,
			@PathVariable(value = "idCertificacio", required = true) String idCertification,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId) {

		logger.info(LogsConstants.LOG_START);		
		ReturnCertificationProposalsRDTO returnCertificationProposalsRDTO = new ReturnCertificationProposalsRDTO();
		queryParameterCertificationProposalsRDTO = new QueryParameterCertificationProposalsRDTO();

		try {
			queryParameterCertificationProposalsRDTO.setClientId(clientId);
			queryParameterCertificationProposalsRDTO.setCodeContract(codeContract);	
			queryParameterCertificationProposalsRDTO.setIdCertification(idCertification);		
			
			returnCertificationProposalsRDTO = certificationService.selectCertificationProposals(queryParameterCertificationProposalsRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnCertificationProposalsRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO,ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			returnCertificationProposalsRDTO.setReturnRDTO(returnRDTO);
			return new ResponseEntity<>(returnCertificationProposalsRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		
		}
		return new ResponseEntity<>(returnCertificationProposalsRDTO, HttpStatus.OK);
	}
	
}
