package es.bcn.imi.framework.vigia.frontal.web.rest.controller.domains.sync;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.bcn.imi.framework.vigia.frontal.business.domains.DomainsService;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterDomainsRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnDomainValuesRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnDomainsRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.Utils;
import es.bcn.vigia.fmw.libutils.enums.ReturnEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.opentrends.openframe.services.rest.controller.AbstractRestController;
import net.opentrends.openframe.services.rest.http.ResponseEntity;

@RestController
@RequestMapping("/sync/dominis")
@Lazy(true)
@Api("FRONTAL API")
public class DomainsController extends AbstractRestController {

	private Log logger = LogFactory.getLog(this.getClass());

	@Autowired(required = true)
	@Qualifier(ServicesConstants.SRV_FRONTAL_DOMAINS)
	private DomainsService domainsService;
	
	private ReturnRDTO returnRDTO;

	private QueryParameterDomainsRDTO queryParameterDomainsRDTO;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta massiva de dominis", tags = { "dominis" })
	public ResponseEntity<ReturnDomainsRDTO> selectDomains() {

		logger.info(LogsConstants.LOG_START);		
		ReturnDomainsRDTO returnDomainsRDTO = new ReturnDomainsRDTO();
		
		try {		
			
			returnDomainsRDTO = domainsService.selectDomains();

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnDomainsRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO,ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			returnDomainsRDTO.setReturnRDTO(returnRDTO);
			return new ResponseEntity<>(returnDomainsRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		
		}
		return new ResponseEntity<>(returnDomainsRDTO, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{nom}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta de valors d'un domini", tags = { "dominis" })
	public ResponseEntity<ReturnDomainValuesRDTO> selectDomainValues(
			@PathVariable(value = "nom", required = false) String name,
			@RequestParam(value = "dataReferencia", required = false) String dateReference) {

		logger.info(LogsConstants.LOG_START);		
		ReturnDomainValuesRDTO returnDomainValuesRDTO = new ReturnDomainValuesRDTO();
		queryParameterDomainsRDTO = new QueryParameterDomainsRDTO();
		
		try {		
			queryParameterDomainsRDTO.setName(name);
			queryParameterDomainsRDTO.setDateReference(dateReference);
			
			returnDomainValuesRDTO = domainsService.selectDomainValues(queryParameterDomainsRDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnDomainValuesRDTO), ex);
			returnRDTO = Utils.returnError(returnRDTO,ReturnEnum.CODE_ERROR_GENERIC_FRONTAL);
			returnDomainValuesRDTO.setReturnRDTO(returnRDTO);
			return new ResponseEntity<>(returnDomainValuesRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		
		}
		return new ResponseEntity<>(returnDomainValuesRDTO, HttpStatus.OK);
	}
	
}
