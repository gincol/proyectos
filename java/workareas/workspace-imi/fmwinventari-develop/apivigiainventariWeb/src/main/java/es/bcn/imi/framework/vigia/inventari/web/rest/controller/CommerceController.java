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

import es.bcn.imi.framework.vigia.inventari.business.CommerceService;
import es.bcn.vigia.fmw.libcommons.business.dto.CommerceBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.QueryParameterBDTO;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CommerceRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnCommerceDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnCommerceElementsRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.convert.CommerceConvert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.opentrends.openframe.services.rest.controller.AbstractRestController;
import net.opentrends.openframe.services.rest.http.ResponseEntity;

@RestController
@RequestMapping("/comerc")
@Lazy(true)
@Api("INVENTARI API")
public class CommerceController extends AbstractRestController {

	private Log logger = LogFactory.getLog(this.getClass());

	@Autowired(required = true)
	@Qualifier(ServicesConstants.SRV_COMMERCE)
	private CommerceService commerceService;

	private ReturnRDTO returnRDTO;

	private QueryParameterBDTO queryParameterBDTO;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Interfície d’alta de Comerço", tags = { "comerco" })
	public ResponseEntity<ReturnRDTO> save(@RequestBody CommerceRDTO commerceRDTO) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {
			
			CommerceBDTO commerceBDTO = CommerceConvert.rdto2bto(commerceRDTO);
			
			returnRDTO = commerceService.insert(commerceBDTO);

		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, commerceRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{codi}", method = RequestMethod.PUT)
	@ResponseBody
	@ApiOperation(value = "Interfície d modificació de Comerço", tags = { "comerco" })
	public ResponseEntity<ReturnRDTO> update(@PathVariable(value = "codi", required = true) String code,
											 @RequestBody CommerceRDTO commerceRDTO) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {

			commerceRDTO.setCode(code);
			
			CommerceBDTO commerceBDTO = CommerceConvert.rdto2bto(commerceRDTO);

			returnRDTO = commerceService.update(commerceBDTO);

		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, commerceRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{codi}", method = RequestMethod.DELETE)
	@ResponseBody
	@ApiOperation(value = "Interfície d baixa de Comerço", tags = { "comerco" })
	public ResponseEntity<ReturnRDTO> delete(@PathVariable(value = "codi", required = true) String code,
											 @RequestBody CommerceRDTO commerceRDTO) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {

			commerceRDTO.setCode(code);
			
			CommerceBDTO commerceBDTO = CommerceConvert.rdto2bto(commerceRDTO);

			returnRDTO = commerceService.delete(commerceBDTO);

		} catch (Exception ex) {
 
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, commerceRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/elements", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Interfície d’alta de Elements de Comerço", tags = { "comerco" })
	public ResponseEntity<ReturnRDTO> saveExpense(@RequestBody CommerceRDTO commerceRDTO) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {
			
			CommerceBDTO commerceBDTO = CommerceConvert.rdto2bto(commerceRDTO);

			returnRDTO = commerceService.insertElements(commerceBDTO);

		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, commerceRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/contracta/{codi}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta massiva de Comerços", tags = { "comerc" })
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
			
			returnMassiveRDTO = commerceService.selectMassive(queryParameterBDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnMassiveRDTO), ex);
			return new ResponseEntity<>(returnMassiveRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnMassiveRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{codi}/contracta/{codiContracta}/detall", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta individual d'un Comerç", tags = { "comerc" })
	public ResponseEntity<ReturnCommerceDetailedRDTO> selectDetailed(
			@PathVariable(value = "codi", required = true) String code,
			@PathVariable(value = "codiContracta", required = true) String codeContract,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestParam(required = false) String transactionId) {

		logger.info(LogsConstants.LOG_START);
		ReturnCommerceDetailedRDTO returnCommerceDetailedRDTO = new ReturnCommerceDetailedRDTO();
		queryParameterBDTO = new QueryParameterBDTO();		

		try {
			queryParameterBDTO.setCode(code);
			queryParameterBDTO.setCodeContract(codeContract);
			queryParameterBDTO.setDateReference(dateReference);
			queryParameterBDTO.setCodeUser(codeUser);
			
			returnCommerceDetailedRDTO = commerceService.selectDetailed(queryParameterBDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnCommerceDetailedRDTO), ex);
			return new ResponseEntity<>(returnCommerceDetailedRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnCommerceDetailedRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{codi}/contracta/{codiContracta}/elements", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta d'Elements d'un Comerç", tags = { "comerc" })
	public ResponseEntity<ReturnCommerceElementsRDTO> selectElements(
			@PathVariable(value = "codi", required = true) String code,
			@PathVariable(value = "codiContracta", required = true) String codeContract,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestParam(required = false) String transactionId) {

		logger.info(LogsConstants.LOG_START);
		ReturnCommerceElementsRDTO returnCommerceElementsRDTO = new ReturnCommerceElementsRDTO();
		queryParameterBDTO = new QueryParameterBDTO();		

		try {
			queryParameterBDTO.setCode(code);
			queryParameterBDTO.setCodeContract(codeContract);
			queryParameterBDTO.setDateReference(dateReference);
			queryParameterBDTO.setCodeUser(codeUser);
			
			returnCommerceElementsRDTO = commerceService.selectElements(queryParameterBDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnCommerceElementsRDTO), ex);
			return new ResponseEntity<>(returnCommerceElementsRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnCommerceElementsRDTO, HttpStatus.OK);
	}
}
