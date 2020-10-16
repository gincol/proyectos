package es.bcn.imi.framework.vigia.frontal.business.inventary.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import es.bcn.imi.framework.vigia.frontal.business.inventary.LogicalModelService;
import es.bcn.imi.framework.vigia.frontal.validator.ValidatorLogicalModel;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.ActuationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.DocumentarySupportRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.LogicalModelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterDocumentarySupportRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.commons.BaseTransactionIdRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnDocumentarySupportRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnLogicalModelDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnLogicalModelMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnLogicalModelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.enums.ReturnEnum;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import es.bcn.vigia.fmw.libutils.services.Utils;
import es.bcn.vigia.fmw.libutils.services.rest.RestCall;
import es.bcn.vigia.fmw.libutils.validator.front.FrontValidateLogicalModelService;

@Lazy
@Service(ServicesConstants.SRV_FRONTAL_LOGICAL_MODEL)
public class LogicalModelServiceImpl implements LogicalModelService {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ServicesConstants.SRV_REST_CALL)
	private RestCall restCall;

	@Autowired
	@Qualifier(ServicesConstants.SRV_UTILS)
	private Utils utils;

	@Autowired
	@Qualifier(ServicesConstants.SRV_FRONT_VALIDATE_LOGICAL_MODEL)
	private FrontValidateLogicalModelService frontValidateLogicalModelService;

	@Autowired
	@Qualifier(ServicesConstants.SRV_FRONT_VALIDATE_LOGICAL_MODEL_FMW)
	private ValidatorLogicalModel validator;

	@Value("${url.api.orquestrador}")
	private String urlApiOrquestrador;

	@Value("${url.path.inventary}")
	private String pathInventary;

	@Value("${url.path.logicmodel}")
	private String pathLogicalModel;

	@Value("${url.path.logicmodel.get}")
	private String pathLogicalModelGet;

	@Value("${url.path.logicmodel.getMassive}")
	private String pathLogicalModelGetMassive;

	@Value("${url.path.logicmodel.getDetailed}")
	private String pathLogicalModelGetDetailed;

	@Value("${url.path.logicmodel.put}")
	private String pathLogicalModelPut;

	@Value("${url.path.logicmodel.delete}")
	private String pathLogicalModelDelete;

	@Value("${url.path.documentary.support}")
	private String pathDocumentarySupport;

	@Value("${url.path.documentary.support.delete}")
	private String pathDocumentarySupportDelete;

	@Value("${url.path.documentary.support.get}")
	private String pathDocumentarySupportGet;

	@Value("${url.path.actuation}")
	private String pathActuation;
	
	@Value("${url.path.actuation.delete}")
	private String pathActuationDelete;
	
	private ReturnRDTO returnRDTO;

	private String url;

	

	private void processResponse(ResponseEntity<Object> resp) throws ImiException {
		
		returnRDTO = (ReturnRDTO) utils.rest2Object(resp, returnRDTO);

		logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));
	}

	private ReturnRDTO getTransactionId(BaseTransactionIdRDTO baseTransactionIdRDTO) {

		returnRDTO.setTransactionId(baseTransactionIdRDTO.getTransactionId());

		return returnRDTO;
	}

	
	@Override
	public ReturnRDTO insert(LogicalModelRDTO logicalModelRDTO) throws ImiException {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {

			logicalModelRDTO.generateTransactionId();
			
			returnRDTO = validator.validateContract(logicalModelRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateLogicalModelService.validateSyntaxInsert(logicalModelRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				returnRDTO = validator.validateInsert(logicalModelRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH).concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
						.concat(pathInventary).concat(pathLogicalModel);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				ResponseEntity<Object> resp = restCall.executePOST(url, logicalModelRDTO);

				processResponse(resp);
			}

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_ACCESSING_POST_URL, url));
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}

		returnRDTO = getTransactionId(logicalModelRDTO);

		return returnRDTO;
	}

	@Override
	public ReturnRDTO update(LogicalModelRDTO logicalModelRDTO) throws ImiException {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {

			logicalModelRDTO.generateTransactionId();
			
			returnRDTO = validator.validateContract(logicalModelRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateLogicalModelService.validateSyntaxUpdate(logicalModelRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				returnRDTO = validator.validateUpdate(logicalModelRDTO);
			}
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH).concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
						.concat(pathInventary).concat(pathLogicalModel).concat(pathLogicalModelPut);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				Map<String, String> urlParams = new HashMap<>();
				urlParams.put("codi", logicalModelRDTO.getCode());

				ResponseEntity<Object> resp = restCall.executePUT(url, urlParams, logicalModelRDTO);

				processResponse(resp);
			}

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_ACCESSING_PUT_URL, url));
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}

		returnRDTO = getTransactionId(logicalModelRDTO);

		return returnRDTO;
	}

	@Override
	public ReturnRDTO delete(LogicalModelRDTO logicalModelRDTO) throws ImiException {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {

			logicalModelRDTO.generateTransactionId();
			
			returnRDTO = validator.validateContract(logicalModelRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateLogicalModelService.validateSyntaxDelete(logicalModelRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				returnRDTO = validator.validateDelete(logicalModelRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH).concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
						.concat(pathInventary).concat(pathLogicalModel).concat(pathLogicalModelDelete);
				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				Map<String, String> urlParams = new HashMap<>();

				urlParams.put("codi", logicalModelRDTO.getCode());

				ResponseEntity<Object> resp = restCall.executeDELETE(url, urlParams, logicalModelRDTO);

				processResponse(resp);
			}

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_ACCESSING_DELETE_URL, url));
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}

		returnRDTO = getTransactionId(logicalModelRDTO);

		return returnRDTO;
	}

	@Override
	public ReturnLogicalModelRDTO select(LogicalModelRDTO logicalModelRDTO) throws ImiException {

		ReturnLogicalModelRDTO returnLogicalModelRDTO = new ReturnLogicalModelRDTO();

		try {

			logicalModelRDTO.generateTransactionId();
			
			returnRDTO = validator.validateContract(logicalModelRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				url = urlApiOrquestrador.concat(ImiConstants.SLASH).concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
						.concat(pathInventary).concat(pathLogicalModel).concat(pathLogicalModelGet);
				
				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));
				
				Map<String, String> urlParams = new HashMap<>();
				
				urlParams.put("codi", logicalModelRDTO.getCode());
				
				ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, logicalModelRDTO);
				
				returnLogicalModelRDTO = (ReturnLogicalModelRDTO) utils.rest2Object(resp, returnLogicalModelRDTO);
				
				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));
			}


		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}

		returnLogicalModelRDTO.getReturnRDTO().setTransactionId(logicalModelRDTO.getTransactionId());

		return returnLogicalModelRDTO;
	}

	@Override
	public ReturnLogicalModelMassiveRDTO selectMassive(QueryParameterRDTO queryParameterRDTO) throws ImiException {
		
		ReturnLogicalModelMassiveRDTO returnLogicalModelMassiveRDTO = new ReturnLogicalModelMassiveRDTO();

		try {
			queryParameterRDTO.generateTransactionId();
			
			returnRDTO = validator.validateContract(queryParameterRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateLogicalModelService.validateSyntaxSelectMassive(queryParameterRDTO);
			}
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH)
										.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
										.concat(pathInventary)
										.concat(pathLogicalModel)
										.concat(pathLogicalModelGetMassive);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				Map<String, String> urlParams = new HashMap<>();
				
				urlParams.put("codi", queryParameterRDTO.getCodeContract());
				urlParams.put("dataReferencia", queryParameterRDTO.getDateReference());
				urlParams.put("codiUsuari", queryParameterRDTO.getCodeUser());
				urlParams.put("transactionId", queryParameterRDTO.getTransactionId());
				
				ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterRDTO);
				
				returnLogicalModelMassiveRDTO =  (ReturnLogicalModelMassiveRDTO) utils.rest2Object(resp, returnLogicalModelMassiveRDTO);
								
				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));
			} else {
				returnLogicalModelMassiveRDTO.setReturnRDTO(returnRDTO);
			}

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}
		
		returnLogicalModelMassiveRDTO.getReturnRDTO().setTransactionId(queryParameterRDTO.getTransactionId());
		
		return returnLogicalModelMassiveRDTO;
	}

	@Override
	public ReturnLogicalModelDetailedRDTO selectDetailed(QueryParameterRDTO queryParameterRDTO) throws ImiException {
		ReturnLogicalModelDetailedRDTO returnLogicalModelDetailedRDTO = new ReturnLogicalModelDetailedRDTO();

		try {
			queryParameterRDTO.generateTransactionId();
			
			returnRDTO = validator.validateContract(queryParameterRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateLogicalModelService.validateSyntaxSelectDetailed(queryParameterRDTO);
			}
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH)
										.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
										.concat(pathInventary)
										.concat(pathLogicalModel)
										.concat(pathLogicalModelGetDetailed);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				Map<String, String> urlParams = new HashMap<>();
				
				urlParams.put("codi", queryParameterRDTO.getCode());
				urlParams.put("codiContracta", queryParameterRDTO.getCodeContract());
				urlParams.put("dataReferencia", queryParameterRDTO.getDateReference());
				urlParams.put("codiUsuari", queryParameterRDTO.getCodeUser());
				urlParams.put("transactionId", queryParameterRDTO.getTransactionId());
				
				ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterRDTO);
				
				returnLogicalModelDetailedRDTO =  (ReturnLogicalModelDetailedRDTO) utils.rest2Object(resp, returnLogicalModelDetailedRDTO);
								
				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));
			} else {
				returnLogicalModelDetailedRDTO.setReturnRDTO(returnRDTO);
			}

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}
		
		returnLogicalModelDetailedRDTO.getReturnRDTO().setTransactionId(queryParameterRDTO.getTransactionId());
		
		return returnLogicalModelDetailedRDTO;
	}

	@Override
	public ReturnRDTO insertDocumentalSupport(DocumentarySupportRDTO documentarySupportRDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {

			documentarySupportRDTO.generateTransactionId();
			
			returnRDTO = validator.validateContract(documentarySupportRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateLogicalModelService.validateSyntaxInsertDocumentarySupport(documentarySupportRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				returnRDTO = validator.validateInsertDocumentarySupport(documentarySupportRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH).concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
						.concat(pathInventary).concat(pathDocumentarySupport);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				ResponseEntity<Object> resp = restCall.executePOST(url, documentarySupportRDTO);

				processResponse(resp);
			}

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_ACCESSING_POST_URL, url));
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}

		returnRDTO = getTransactionId(documentarySupportRDTO);

		return returnRDTO;
	}

	@Override
	public ReturnRDTO deleteDocumentalSupport(DocumentarySupportRDTO documentarySupportRDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {

			documentarySupportRDTO.generateTransactionId();
			
			returnRDTO = validator.validateContract(documentarySupportRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateLogicalModelService.validateSyntaxDeleteDocumentarySupport(documentarySupportRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				returnRDTO = validator.validateDeleteDocumentarySupport(documentarySupportRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH).concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
						.concat(pathInventary)
						.concat(pathDocumentarySupport)
						.concat(pathDocumentarySupportDelete);


				Map<String, String> urlParams = new HashMap<>();
				urlParams.put("codi", documentarySupportRDTO.getCodeContractDocument());
				urlParams.put("codiEntitat", documentarySupportRDTO.getCode());
				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				ResponseEntity<Object> resp = restCall.executePUT(url, urlParams, documentarySupportRDTO);
				
				processResponse(resp);
			}

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_ACCESSING_POST_URL, url));
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}

		returnRDTO = getTransactionId(documentarySupportRDTO);

		return returnRDTO;

	}


	@Override
	public ReturnDocumentarySupportRDTO selectDocumentarySupports(QueryParameterDocumentarySupportRDTO queryParameterDocumentarySupportRDTO)
			throws ImiException {
		ReturnDocumentarySupportRDTO returnDocumentarySupportRDTO = new ReturnDocumentarySupportRDTO();

		try {
			queryParameterDocumentarySupportRDTO.generateTransactionId();
			
			returnRDTO = validator.validateContract(queryParameterDocumentarySupportRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateLogicalModelService.validateSyntaxSelectDocumentarySupport(queryParameterDocumentarySupportRDTO);
			}
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				returnRDTO = validator.validateSelectDocumentarySupport(queryParameterDocumentarySupportRDTO);
			}
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH)
						.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
						.concat(pathInventary)
						.concat(pathDocumentarySupport)
						.concat(pathDocumentarySupportGet);
			

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				Map<String, String> urlParams = new HashMap<>();
				
				urlParams.put("codi", queryParameterDocumentarySupportRDTO.getCodeContract());
				urlParams.put("codiEntitat", queryParameterDocumentarySupportRDTO.getCode());
				urlParams.put("considerarAnulacions", queryParameterDocumentarySupportRDTO.getConsiderAnnulments());
				urlParams.put("codiUsuari", queryParameterDocumentarySupportRDTO.getCodeUser());
				urlParams.put("tipusEntitat", queryParameterDocumentarySupportRDTO.getCodeTypeEntity());
				urlParams.put("transactionId", queryParameterDocumentarySupportRDTO.getTransactionId());
				
				ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterDocumentarySupportRDTO);
				
				returnDocumentarySupportRDTO =  (ReturnDocumentarySupportRDTO) utils.rest2Object(resp, returnDocumentarySupportRDTO);
								
				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));
			} else {
				returnDocumentarySupportRDTO.setReturnRDTO(returnRDTO);
			}

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}
		
		returnDocumentarySupportRDTO.getReturnRDTO().setTransactionId(queryParameterDocumentarySupportRDTO.getTransactionId());
		
		return returnDocumentarySupportRDTO;

	}

	@Override
	public ReturnRDTO insertActuation(ActuationRDTO actuationRDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {

			actuationRDTO.generateTransactionId();
			
			returnRDTO = validator.validateContract(actuationRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateLogicalModelService.validateSyntaxInsertActuation(actuationRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				returnRDTO = validator.validateInsertActuation(actuationRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH).concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
						.concat(pathInventary).concat(pathActuation);
				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				ResponseEntity<Object> resp = restCall.executePOST(url, actuationRDTO);

				processResponse(resp);
			}

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_ACCESSING_POST_URL, url));
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}

		returnRDTO = getTransactionId(actuationRDTO);

		return returnRDTO;


	}

	@Override
	public ReturnRDTO deleteActuation(ActuationRDTO actuationRDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {

			actuationRDTO.generateTransactionId();
			
			returnRDTO = validator.validateContract(actuationRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateLogicalModelService.validateSyntaxDeleteActuation(actuationRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				returnRDTO = validator.validateDeleteActuation(actuationRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
				url = urlApiOrquestrador.concat(ImiConstants.SLASH).concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
						.concat(pathInventary)
						.concat(pathActuation)
						.concat(pathActuationDelete);

				Map<String, String> urlParams = new HashMap<>();
				urlParams.put("codi", actuationRDTO.getCode());
				urlParams.put("codiEntitat", actuationRDTO.getCodeEntity());
				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				ResponseEntity<Object> resp = restCall.executePUT(url, urlParams, actuationRDTO);
				
				processResponse(resp);
			}

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_ACCESSING_POST_URL, url));
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}

		returnRDTO = getTransactionId(actuationRDTO);

		return returnRDTO;

	}
}