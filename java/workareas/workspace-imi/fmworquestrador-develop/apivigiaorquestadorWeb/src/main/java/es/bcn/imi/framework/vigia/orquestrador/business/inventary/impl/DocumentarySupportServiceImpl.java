package es.bcn.imi.framework.vigia.orquestrador.business.inventary.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import es.bcn.imi.framework.vigia.orquestrador.business.inventary.DocumentarySupportService;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.DocumentarySupportExpenseRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.DocumentarySupportRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterDocumentarySupportRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnDocumentarySupportRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import es.bcn.vigia.fmw.libutils.services.Utils;
import es.bcn.vigia.fmw.libutils.services.rest.RestCall;

@Lazy
@Service(ServicesConstants.SRV_INVENTARY_DOCUMENTARY_SUPPORT)
public class DocumentarySupportServiceImpl implements DocumentarySupportService {
	
	private Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	@Qualifier(ServicesConstants.SRV_REST_CALL)
	private RestCall restCall;
	
	@Autowired
	@Qualifier(ServicesConstants.SRV_UTILS)
	private Utils utils;
	
	@Value("${url.api.inventary}")
	private String urlApiInventary;

	@Value("${url.path.documentary.support}")
	private String pathDocumentarySupport;
	
	@Value("${url.path.documentary.support.delete}")
	private String pathDocumentarySupportDelete;

	@Value("${url.path.documentary.support.expense}")
	private String pathDocumentarySupportExpense;
	
	@Value("${url.path.documentary.support.expense.delete}")
	private String pathDocumentarySupportExpenseDelete;

	@Value("${url.path.documentary.support.get}")
	private String pathDocumentarySupportsGet;

	private ReturnRDTO returnRDTO;
	
	@Override
	public ReturnRDTO redirect(DocumentarySupportRDTO documentarySupportRDTO, HttpServletRequest request) throws ImiException {

		if (request.getMethod().equals(String.valueOf(RequestMethod.POST))) {

			returnRDTO = insert(documentarySupportRDTO);
		}

		if (request.getMethod().equals(String.valueOf(RequestMethod.PUT))) {

			returnRDTO = delete(documentarySupportRDTO);
		}

		

		return returnRDTO;
	}

		
	@Override
	public ReturnRDTO insert(DocumentarySupportRDTO documentarySupportRDTO)
			throws ImiException {
		try {
			
			returnRDTO = new ReturnRDTO();
			
			String url = urlApiInventary + pathDocumentarySupport;
			
			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));
			
			ResponseEntity<Object> resp = restCall.executePOST(url, documentarySupportRDTO);
			
			returnRDTO =  (ReturnRDTO) utils.rest2Object(resp, returnRDTO);
							
			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));
			
			
		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}
		return returnRDTO;

	}
	
	@Override
	public ReturnRDTO delete(DocumentarySupportRDTO documentarySupportRDTO)
			throws ImiException {
		try {
			
			returnRDTO = new ReturnRDTO();
			
			String url = urlApiInventary +  pathDocumentarySupport + pathDocumentarySupportDelete;
			
			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			Map<String, String> urlParams = new HashMap<String, String>();
			urlParams.put("codi", documentarySupportRDTO.getCodeContractDocument());
			urlParams.put("codiEntitat", documentarySupportRDTO.getCode());
			
			ResponseEntity<Object> resp = restCall.executePUT(url, urlParams, documentarySupportRDTO);

			returnRDTO =  (ReturnRDTO) utils.rest2Object(resp, returnRDTO);
							
			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));
			
			
		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}
		return returnRDTO;

	}

	@Override
	public ReturnDocumentarySupportRDTO selectDocumentarySupport(QueryParameterDocumentarySupportRDTO queryParameterDocumentarySupportRDTO)
			throws ImiException {
		ReturnDocumentarySupportRDTO returnDocumentarySupportRDTO = new ReturnDocumentarySupportRDTO();

		try {

			String url = urlApiInventary + pathDocumentarySupport + pathDocumentarySupportsGet;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			Map<String, String> urlParams = new HashMap<String, String>();
			urlParams.put("codi", queryParameterDocumentarySupportRDTO.getCodeContract());
			urlParams.put("codiEntitat", queryParameterDocumentarySupportRDTO.getCode());
			urlParams.put("considerarAnulacions", queryParameterDocumentarySupportRDTO.getConsiderAnnulments());
			urlParams.put("codiUsuari", queryParameterDocumentarySupportRDTO.getCodeUser());
			urlParams.put("tipusEntitat", queryParameterDocumentarySupportRDTO.getCodeTypeEntity());
			urlParams.put("transactionId", queryParameterDocumentarySupportRDTO.getTransactionId());

			
			ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterDocumentarySupportRDTO);

			returnDocumentarySupportRDTO = (ReturnDocumentarySupportRDTO) utils.rest2Object(resp, returnDocumentarySupportRDTO);

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		return returnDocumentarySupportRDTO;

	}
	
	@Override
	public ReturnRDTO redirectExpense(DocumentarySupportExpenseRDTO documentarySupportExpenseRDTO, HttpServletRequest request) throws ImiException {

		if (request.getMethod().equals(String.valueOf(RequestMethod.POST))) {

			returnRDTO = insertExpense(documentarySupportExpenseRDTO);
		}

		if (request.getMethod().equals(String.valueOf(RequestMethod.PUT))) {

			returnRDTO = deleteExpense(documentarySupportExpenseRDTO);
		}

		

		return returnRDTO;
	}

		
	@Override
	public ReturnRDTO insertExpense(DocumentarySupportExpenseRDTO documentarySupportExpenseRDTO)
			throws ImiException {
		try {
			
			returnRDTO = new ReturnRDTO();
			
			String url = urlApiInventary + pathDocumentarySupportExpense;
			
			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));
			
			ResponseEntity<Object> resp = restCall.executePOST(url, documentarySupportExpenseRDTO);
			
			returnRDTO =  (ReturnRDTO) utils.rest2Object(resp, returnRDTO);
							
			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));
			
			
		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}
		return returnRDTO;

	}
	
	@Override
	public ReturnRDTO deleteExpense(DocumentarySupportExpenseRDTO documentarySupportExpenseRDTO)
			throws ImiException {
		try {
			
			returnRDTO = new ReturnRDTO();
			
			String url = urlApiInventary +  pathDocumentarySupportExpense + pathDocumentarySupportExpenseDelete;
			
			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

			Map<String, String> urlParams = new HashMap<String, String>();
			urlParams.put("codi", documentarySupportExpenseRDTO.getCodeContractDocument());
			urlParams.put("codiEntitat", documentarySupportExpenseRDTO.getCode());
			
			ResponseEntity<Object> resp = restCall.executePUT(url, urlParams, documentarySupportExpenseRDTO);

			returnRDTO =  (ReturnRDTO) utils.rest2Object(resp, returnRDTO);
							
			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));
			
			
		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}
		return returnRDTO;

	}



}
