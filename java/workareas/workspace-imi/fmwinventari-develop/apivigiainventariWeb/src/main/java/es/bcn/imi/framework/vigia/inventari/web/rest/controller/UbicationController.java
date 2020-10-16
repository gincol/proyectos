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

import es.bcn.imi.framework.vigia.inventari.business.UbicationService;
import es.bcn.vigia.fmw.libcommons.business.dto.QueryParameterBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.UbicationBDTO;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.UbicationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnUbicationDetailedRDTO;
import es.bcn.vigia.fmw.libutils.convert.UbicationConvert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.opentrends.openframe.services.rest.controller.AbstractRestController;
import net.opentrends.openframe.services.rest.http.ResponseEntity;

@RestController
@RequestMapping("/ubicacio")
@Lazy(true)
@Api("IVENTARI API")
public class UbicationController extends AbstractRestController {

	private Log logger = LogFactory.getLog(this.getClass());

	@Autowired(required = true)
	@Qualifier(ServicesConstants.SRV_UBICATION)
	private UbicationService ubicationService;

	private ReturnRDTO returnRDTO;

	private QueryParameterBDTO queryParameterBDTO;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Interfície d’alta d’una Ubicació", tags = { "ubicacions" })
	public ResponseEntity<ReturnRDTO> save(@RequestBody UbicationRDTO ubicationRDTO) {

		logger.info(LogsConstants.LOG_START);

		try {

			UbicationBDTO ubicationBDTO = UbicationConvert.rdto2bto(ubicationRDTO);
			
			returnRDTO = new ReturnRDTO();
			returnRDTO = ubicationService.insert(ubicationBDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, ubicationRDTO), ex);
			return new ResponseEntity<ReturnRDTO>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<ReturnRDTO>(returnRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{codi}", method = RequestMethod.PUT)
	@ResponseBody
	@ApiOperation(value = "Interfície de modificació d’una Ubicació", tags = { "ubicacions" })
	public ResponseEntity<ReturnRDTO> update(@PathVariable(value = "codi", required = true) String code,
											 @RequestBody UbicationRDTO ubicationRDTO) {

		logger.info(LogsConstants.LOG_START);

		try {

			returnRDTO = new ReturnRDTO();

			ubicationRDTO.setCode(code);

			UbicationBDTO ubicationBDTO = UbicationConvert.rdto2bto(ubicationRDTO);

			returnRDTO = ubicationService.update(ubicationBDTO);

		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, ubicationRDTO), ex);
			return new ResponseEntity<ReturnRDTO>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<ReturnRDTO>(returnRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{codi}", method = RequestMethod.DELETE)
	@ResponseBody
	@ApiOperation(value = "Interfície de baixa d’una Ubicació", tags = { "ubicacions" })
	public ResponseEntity<ReturnRDTO> delete(@PathVariable(value = "codi", required = true) String code,
											 @RequestBody UbicationRDTO ubicationRDTO) {

		logger.info(LogsConstants.LOG_START);

		try {

			returnRDTO = new ReturnRDTO();

			ubicationRDTO.setCode(code);

			UbicationBDTO ubicationBDTO = UbicationConvert.rdto2bto(ubicationRDTO);

			returnRDTO = ubicationService.delete(ubicationBDTO);

		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, ubicationRDTO), ex);
			return new ResponseEntity<ReturnRDTO>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<ReturnRDTO>(returnRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/contracta/{codi}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta massiva d'ubicacions", tags = { "ubicacions" })
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
			
			returnMassiveRDTO = ubicationService.selectMassive(queryParameterBDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnMassiveRDTO), ex);
			return new ResponseEntity<>(returnMassiveRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnMassiveRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{codi}/contracta/{codiContracta}/detall", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta detallada d'ubicacions", tags = { "ubicacions" })
	public ResponseEntity<ReturnUbicationDetailedRDTO> selectDetailed(
			@PathVariable(value = "codi", required = true) String code,
			@PathVariable(value = "codiContracta", required = true) String codeContract,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestParam(required = false) String transactionId) {

		logger.info(LogsConstants.LOG_START);
		ReturnUbicationDetailedRDTO returnUbicationDetailedRDTO = new ReturnUbicationDetailedRDTO();
		queryParameterBDTO = new QueryParameterBDTO();		

		try {
			queryParameterBDTO.setCode(code);
			queryParameterBDTO.setCodeContract(codeContract);
			queryParameterBDTO.setDateReference(dateReference);
			queryParameterBDTO.setCodeUser(codeUser);
			
			returnUbicationDetailedRDTO = ubicationService.selectDetailed(queryParameterBDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnUbicationDetailedRDTO), ex);
			return new ResponseEntity<>(returnUbicationDetailedRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnUbicationDetailedRDTO, HttpStatus.OK);
	}
}