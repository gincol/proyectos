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

import es.bcn.imi.framework.vigia.inventari.business.EmployeeCatalogService;
import es.bcn.vigia.fmw.libcommons.business.dto.EmployeeCatalogBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.QueryParameterBDTO;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.EmployeeCatalogRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnEmployeeCatalogDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.convert.EmployeeCatalogConvert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.opentrends.openframe.services.rest.controller.AbstractRestController;
import net.opentrends.openframe.services.rest.http.ResponseEntity;

@RestController
@RequestMapping("/recursoshumans")
@Lazy(true)
@Api("INVENTARI API")
public class EmployeeCatalogController extends AbstractRestController {

	private Log logger = LogFactory.getLog(this.getClass());

	@Autowired(required = true)
	@Qualifier(ServicesConstants.SRV_EMPLOYEE_CATALOG)
	private EmployeeCatalogService employeeCatalogService;

	private ReturnRDTO returnRDTO;

	private QueryParameterBDTO queryParameterBDTO;

	@RequestMapping(value = "/cataleg/personal/treballador",  method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Interfície d'alta de treballadors en el catàleg de personal", tags = { "recursoshumans" })
	public ResponseEntity<ReturnRDTO> save(@RequestBody EmployeeCatalogRDTO employeeCatalogRDTO) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {
			
			EmployeeCatalogBDTO employeeCatalogBDTO = EmployeeCatalogConvert.rdto2bto(employeeCatalogRDTO);
			
			returnRDTO = employeeCatalogService.insert(employeeCatalogBDTO);

		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, employeeCatalogRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/cataleg/personal/treballador/contracta/{codi}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta massiva del catàleg del personal", tags = { "recursoshumans" })
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
			
			returnMassiveRDTO = employeeCatalogService.selectMassive(queryParameterBDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnMassiveRDTO), ex);
			return new ResponseEntity<>(returnMassiveRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnMassiveRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/cataleg/personal/treballador/contracta/{codi}/detall", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta detallada del catàleg del personal", tags = { "recursoshumans" })
	public ResponseEntity<ReturnEmployeeCatalogDetailedRDTO> selectDetailed(
			@PathVariable(value = "codi", required = true) String code,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestParam(value = "dataReferencia", required = false) String dateReference,
			@RequestParam(required = false) String transactionId) {

		logger.info(LogsConstants.LOG_START);
		ReturnEmployeeCatalogDetailedRDTO returnEmployeeCatalogDetailedRDTO = new ReturnEmployeeCatalogDetailedRDTO();
		queryParameterBDTO = new QueryParameterBDTO();		

		try {
			queryParameterBDTO.setCodeContract(code);
			queryParameterBDTO.setDateReference(dateReference);
			queryParameterBDTO.setCodeUser(codeUser);
			
			returnEmployeeCatalogDetailedRDTO = employeeCatalogService.selectDetailed(queryParameterBDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnEmployeeCatalogDetailedRDTO), ex);
			return new ResponseEntity<>(returnEmployeeCatalogDetailedRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnEmployeeCatalogDetailedRDTO, HttpStatus.OK);
	}

}
