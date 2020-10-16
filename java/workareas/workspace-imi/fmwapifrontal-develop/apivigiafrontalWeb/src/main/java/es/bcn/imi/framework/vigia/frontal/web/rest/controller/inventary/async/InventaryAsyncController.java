package es.bcn.imi.framework.vigia.frontal.web.rest.controller.inventary.async;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.bcn.imi.framework.vigia.frontal.business.inventary.AsyncService;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.AsyncRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CommerceRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.InstallationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.LogicalModelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.MaterialResourceRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.PhysicalModelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterActuationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterDocumentarySupportRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterStatesRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.UbicationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnStatesRDTO;
import es.bcn.vigia.fmw.libutils.Utils;
import es.bcn.vigia.fmw.libutils.enums.ReturnEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.opentrends.openframe.services.rest.controller.AbstractRestController;
import net.opentrends.openframe.services.rest.http.ResponseEntity;

@RestController
@RequestMapping("/async/inventari")
@Lazy(true)
@Api("FRONTAL API")
public class InventaryAsyncController extends AbstractRestController {

	private Log logger = LogFactory.getLog(this.getClass());

	@Autowired(required = true)
	@Qualifier(ServicesConstants.SRV_FRONTAL_ASYNC)
	private AsyncService asyncService;

	private ReturnRDTO returnRDTO;

	private QueryParameterRDTO queryParameterRDTO;

	private QueryParameterDocumentarySupportRDTO queryParameterDocumentarySupportRDTO;

	private QueryParameterActuationRDTO queryParameterActuationRDTO;

	private ResponseEntity<ReturnRDTO> processReturnRDTO(ReturnRDTO returnRDTO) {
		if (returnRDTO.getCode().equals(ReturnEnum.CODE_ERROR_TRANSACTION_CREATE_NOT_COMPLETE.getCodeDescription())
				|| returnRDTO.getCode()
						.equals(ReturnEnum.CODE_ERROR_TRANSACTION_UPDATE_NOT_COMPLETE.getCodeDescription())
				|| returnRDTO.getCode()
						.equals(ReturnEnum.CODE_ERROR_TRANSACTION_DELETE_NOT_COMPLETE.getCodeDescription())) {
			logger.info(LogsConstants.LOG_END);
			return new ResponseEntity<>(returnRDTO, HttpStatus.ACCEPTED);
		} else if (!returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_REGISTERED.getCodeDescription())) {

			logger.error(String.format(LogsConstants.LOG_HTTP_BAD_REQUEST, returnRDTO.toString()));

			return new ResponseEntity<>(returnRDTO, HttpStatus.BAD_REQUEST);
		} else {
			logger.info(LogsConstants.LOG_END);
			return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
		}
	}

	@RequestMapping(value = { "/estats/{idTransaccio}", "/estats/{idTransaccio}/{idSubtransaccio}" }, method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície asyncrona", tags = { "async" })
	public ResponseEntity<ReturnStatesRDTO> selectStates(
			@PathVariable(value = "idTransaccio", required = true) String transactionId,
			@PathVariable(value = "idSubtransaccio", required = false) String subtransactionId) {

		logger.info(LogsConstants.LOG_START);

		ReturnStatesRDTO returnStatesRDTO = new ReturnStatesRDTO();

		QueryParameterStatesRDTO queryParameterStatesRDTO = new QueryParameterStatesRDTO();

		try {
			queryParameterStatesRDTO.setIdTransaction(transactionId);
			queryParameterStatesRDTO.setSubtransactionId(subtransactionId);
			returnStatesRDTO = asyncService.selectStates(queryParameterStatesRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnStatesRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			returnStatesRDTO.setReturnRDTO(returnRDTO);
			return new ResponseEntity<>(returnStatesRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnStatesRDTO, HttpStatus.OK);

	}

	@RequestMapping(value = "/ubicacio", method = { RequestMethod.POST })
	@ResponseBody
	@ApiOperation(value = "Interfície asyncrona", tags = { "async" })
	public ResponseEntity<ReturnRDTO> asyncUbication(@RequestBody AsyncRDTO<UbicationRDTO> rdto,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId, HttpServletRequest request) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {
			rdto.setClientId(clientId);
			returnRDTO = asyncService.redirect(rdto, ImiConstants.ASYNC_TYPE_UBICATION, request);
			return processReturnRDTO(returnRDTO);

		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, rdto), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/installacio", method = { RequestMethod.POST })
	@ResponseBody
	@ApiOperation(value = "Interfície asyncrona", tags = { "async" })
	public ResponseEntity<ReturnRDTO> asyncInstallation(@RequestBody AsyncRDTO<InstallationRDTO> rdto,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId, HttpServletRequest request) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {
			rdto.setClientId(clientId);
			returnRDTO = asyncService.redirect(rdto, ImiConstants.ASYNC_TYPE_INSTALLATION, request);
			return processReturnRDTO(returnRDTO);

		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, rdto), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/ubicacio/mcl", method = { RequestMethod.POST })
	@ResponseBody
	@ApiOperation(value = "Interfície asyncrona", tags = { "async" })
	public ResponseEntity<ReturnRDTO> asyncMCL(@RequestBody AsyncRDTO<LogicalModelRDTO> rdto,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId, HttpServletRequest request) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {
			rdto.setClientId(clientId);
			returnRDTO = asyncService.redirect(rdto, ImiConstants.ASYNC_TYPE_MCL, request);
			return processReturnRDTO(returnRDTO);

		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, rdto), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/ubicacio/mcl/mcf", method = { RequestMethod.POST })
	@ResponseBody
	@ApiOperation(value = "Interfície asyncrona", tags = { "async" })
	public ResponseEntity<ReturnRDTO> asyncMCF(@RequestBody AsyncRDTO<PhysicalModelRDTO> rdto,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId, HttpServletRequest request) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {
			rdto.setClientId(clientId);
			returnRDTO = asyncService.redirect(rdto, ImiConstants.ASYNC_TYPE_MCF, request);
			return processReturnRDTO(returnRDTO);

		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, rdto), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/recursmaterial", method = { RequestMethod.POST })
	@ResponseBody
	@ApiOperation(value = "Interfície asyncrona", tags = { "async" })
	public ResponseEntity<ReturnRDTO> asyncRRMM(@RequestBody AsyncRDTO<MaterialResourceRDTO> rdto,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId, HttpServletRequest request) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {
			rdto.setClientId(clientId);
			returnRDTO = asyncService.redirect(rdto, ImiConstants.ASYNC_TYPE_RRMM, request);
			return processReturnRDTO(returnRDTO);

		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, rdto), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/comerc", method = { RequestMethod.POST })
	@ResponseBody
	@ApiOperation(value = "Interfície asyncrona", tags = { "async" })
	public ResponseEntity<ReturnRDTO> asyncCommerce(@RequestBody AsyncRDTO<CommerceRDTO> rdto,
			@RequestHeader(ImiConstants.HEADER_X_IBM_CLIENT_ID) String clientId, HttpServletRequest request) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {
			rdto.setClientId(clientId);
			returnRDTO = asyncService.redirect(rdto, ImiConstants.ASYNC_TYPE_COMMERCE, request);
			return processReturnRDTO(returnRDTO);

		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, rdto), ex);
			returnRDTO = Utils.returnError(returnRDTO, ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}