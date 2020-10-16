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

import es.bcn.imi.framework.vigia.inventari.business.DocumentarySupportService;
import es.bcn.vigia.fmw.libcommons.business.dto.DocumentarySupportBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.DocumentarySupportExpenseBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.QueryParameterDocumentarySupportBDTO;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.DocumentarySupportExpenseRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.DocumentarySupportRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnDocumentarySupportRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.convert.DocumentarySupportConvert;
import es.bcn.vigia.fmw.libutils.convert.DocumentarySupportExpenseConvert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.opentrends.openframe.services.rest.controller.AbstractRestController;
import net.opentrends.openframe.services.rest.http.ResponseEntity;

@RestController
@RequestMapping("/suportdocumental")
@Lazy(true)
@Api("SUPPORT DOCUMENTAL API")
public class DocumentarySupportController extends AbstractRestController {

	private Log logger = LogFactory.getLog(this.getClass());

	@Autowired(required = true)
	@Qualifier(ServicesConstants.SRV_INVENTARY_DOCUMENTARY_SUPPORT)
	private DocumentarySupportService service;

	
	private ReturnRDTO returnRDTO;

	private QueryParameterDocumentarySupportBDTO queryParameterDocumentarySupportBDTO;

		
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Interfície d'alta de suport documental", tags = { "suportdocumental" })
	public ResponseEntity<ReturnRDTO> save(@RequestBody DocumentarySupportRDTO documentarySupportRDTO) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {
			
			DocumentarySupportBDTO documentarySupportBDTO = DocumentarySupportConvert.rdto2bdto(documentarySupportRDTO);
			
			returnRDTO = service.insert(documentarySupportBDTO);

		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, documentarySupportRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{codi}/entitat/{codiEntitat}", method = RequestMethod.PUT)
	@ResponseBody
	@ApiOperation(value = "Interfície d'anullacio de suport documental", tags = { "suportdocumental" })
	public ResponseEntity<ReturnRDTO> delete(@PathVariable(value = "codiEntitat", required = true) String codeEntity,
			@PathVariable(value = "codi", required = true) String codeDocumentarySupport,
			@RequestBody DocumentarySupportRDTO documentarySupportRDTO) {

		logger.info(LogsConstants.LOG_START);

		try {

			returnRDTO = new ReturnRDTO();

			documentarySupportRDTO.setCode(codeEntity);
			documentarySupportRDTO.setCodeContractDocument(codeDocumentarySupport);
			DocumentarySupportBDTO documentarySupportBDTO = DocumentarySupportConvert.rdto2bdto(documentarySupportRDTO);

			returnRDTO = service.update(documentarySupportBDTO);

		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, documentarySupportRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/contracta/{codi}/tipusEntitat/{tipusEntitat}/codiEntitat/{codiEntitat}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Interfície de consulta suport documental", tags = { "suportdocumental" })
	public ResponseEntity<ReturnDocumentarySupportRDTO> selectDocumentarySupport(
			@PathVariable(value = "codi", required = true) String codeContract,
			@PathVariable(value = "codiEntitat", required = true) String code,
			@PathVariable(value = "tipusEntitat", required = true) String codeTypeEntity,
			@RequestParam(value = "considerarAnulacions", required = false) String considerAnnulments,
			@RequestParam(value = "codiUsuari", required = false) String codeUser,
			@RequestParam(required = false) String transactionId) {

		logger.info(LogsConstants.LOG_START);
		ReturnDocumentarySupportRDTO returnDocumentarySupportRDTO = new ReturnDocumentarySupportRDTO();
		queryParameterDocumentarySupportBDTO = new QueryParameterDocumentarySupportBDTO();		

		try {
			queryParameterDocumentarySupportBDTO.setCode(code);
			queryParameterDocumentarySupportBDTO.setCodeContract(codeContract);
			queryParameterDocumentarySupportBDTO.setConsiderAnnulments(considerAnnulments);
			queryParameterDocumentarySupportBDTO.setCodeUser(codeUser);
			queryParameterDocumentarySupportBDTO.setCodeTypeEntity(codeTypeEntity);
			
			returnDocumentarySupportRDTO = service.selectDocumentarySupport(queryParameterDocumentarySupportBDTO);

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, returnDocumentarySupportRDTO), ex);
			return new ResponseEntity<>(returnDocumentarySupportRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnDocumentarySupportRDTO, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/despesa",method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Interfície d'alta de suport documental de de", tags = { "despesa" })
	public ResponseEntity<ReturnRDTO> saveDocumentarySupportExpense(@RequestBody DocumentarySupportExpenseRDTO documentarySupportExpenseRDTO) {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {
			
			DocumentarySupportExpenseBDTO documentarySupportExpenseBDTO = DocumentarySupportExpenseConvert.rdto2bdto(documentarySupportExpenseRDTO);
			
			returnRDTO = service.insertExpense(documentarySupportExpenseBDTO);

		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, documentarySupportExpenseRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/despesa/{codi}/entitat/{codiEntitat}", method = RequestMethod.PUT)
	@ResponseBody
	@ApiOperation(value = "Interfície d'anullacio de suport documental", tags = { "suportdocumental" })
	public ResponseEntity<ReturnRDTO> deleteDocumentarySupportExpense(@PathVariable(value = "codiEntitat", required = true) String codeEntity,
			@PathVariable(value = "codi", required = true) String codeDocumentarySupport,
			@RequestBody DocumentarySupportExpenseRDTO documentarySupportExpenseRDTO) {

		logger.info(LogsConstants.LOG_START);

		try {

			returnRDTO = new ReturnRDTO();

			documentarySupportExpenseRDTO.setCode(codeEntity);
			documentarySupportExpenseRDTO.setCodeContractDocument(codeDocumentarySupport);
			DocumentarySupportExpenseBDTO documentarySupportExpenseBDTO = DocumentarySupportExpenseConvert.rdto2bdto(documentarySupportExpenseRDTO);

			returnRDTO = service.updateExpense(documentarySupportExpenseBDTO);

		} catch (Exception ex) {

			logger.error(String.format(LogsConstants.LOG_HTTP_INTERNAL_ERROR, documentarySupportExpenseRDTO), ex);
			return new ResponseEntity<>(returnRDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info(LogsConstants.LOG_END);

		return new ResponseEntity<>(returnRDTO, HttpStatus.OK);
	}


}