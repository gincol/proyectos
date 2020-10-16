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

import es.bcn.imi.framework.vigia.frontal.business.inventary.PhysicalModelService;
import es.bcn.imi.framework.vigia.frontal.validator.ValidatorPhysicalModel;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.ActuationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.AggregateAmortizationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.DocumentarySupportRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.PhysicalModelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterDocumentarySupportRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.commons.BaseTransactionIdRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnDocumentarySupportRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnPhysicalModelAmortizationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnPhysicalModelDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnPhysicalModelMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnPhysicalModelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.enums.ReturnEnum;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import es.bcn.vigia.fmw.libutils.services.Utils;
import es.bcn.vigia.fmw.libutils.services.rest.RestCall;
import es.bcn.vigia.fmw.libutils.validator.front.FrontValidatePhysicalModelService;

@Lazy
@Service(ServicesConstants.SRV_FRONTAL_PHYSICAL_MODEL)
public class PhysicalModelServiceImpl implements PhysicalModelService {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ServicesConstants.SRV_REST_CALL)
	private RestCall restCall;

	@Autowired
	@Qualifier(ServicesConstants.SRV_UTILS)
	private Utils utils;

	@Autowired
	@Qualifier(ServicesConstants.SRV_FRONT_VALIDATE_PHYSICAL_MODEL)
	private FrontValidatePhysicalModelService frontValidatePhysicalModelService;

	@Autowired
	@Qualifier(ServicesConstants.SRV_FRONT_VALIDATE_PHYSICAL_MODEL_FMW)
	private ValidatorPhysicalModel validator;

	@Value("${url.api.orquestrador}")
	private String urlApiOrquestrador;

	@Value("${url.path.inventary}")
	private String pathInventary;

	@Value("${url.path.physicalmodel}")
	private String pathPhysicalModel;

	@Value("${url.path.physicalmodel.get}")
	private String pathPhysicalModelGet;

	@Value("${url.path.physicalmodel.getMassive}")
	private String pathPhysicalModelGetMassive;

	@Value("${url.path.physicalmodel.getDetailed}")
	private String pathPhysicalModelGetDetailed;

	@Value("${url.path.physicalmodel.getAmortization}")
	private String pathPhysicalModelGetAmortization;

	@Value("${url.path.physicalmodel.put}")
	private String pathPhysicalModelPut;

	@Value("${url.path.physicalmodel.delete}")
	private String pathPhysicalModelDelete;

	@Value("${url.path.physicalmodel.type}")
	private String pathPhysicalModelType;

	@Value("${url.path.physicalmodel.aggregate.amortization}")
	private String pathPhysicalModelAggregateAmortization;
	
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

	
	
	private void processResponse(ResponseEntity<Object> resp) throws ImiException
	{
		returnRDTO = (ReturnRDTO) utils.rest2Object(resp, returnRDTO);

		logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

	}

	private ReturnRDTO getTransactionId(BaseTransactionIdRDTO baseTransactionIdRDTO) {

		returnRDTO.setTransactionId(baseTransactionIdRDTO.getTransactionId());

		return returnRDTO;
	}
	@Override
	public ReturnRDTO insert(PhysicalModelRDTO physicalModelRDTO) throws ImiException {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {

			physicalModelRDTO.generateTransactionId();
			
			returnRDTO = validator.validateContract(physicalModelRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidatePhysicalModelService.validateSyntaxInsert(physicalModelRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				returnRDTO = validator.validateInsert(physicalModelRDTO);
			}
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH).concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
						.concat(pathInventary).concat(pathPhysicalModel);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				ResponseEntity<Object> resp = restCall.executePOST(url, physicalModelRDTO);

				processResponse(resp);
			}

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_ACCESSING_POST_URL, url));
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}

		returnRDTO = getTransactionId(physicalModelRDTO);

		return returnRDTO;
	}

	@Override
	public ReturnRDTO update(PhysicalModelRDTO physicalModelRDTO) throws ImiException {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {

			physicalModelRDTO.generateTransactionId();
			
			returnRDTO = validator.validateContract(physicalModelRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidatePhysicalModelService.validateSyntaxUpdate(physicalModelRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				returnRDTO = validator.validateUpdate(physicalModelRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH).concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
						.concat(pathInventary).concat(pathPhysicalModel).concat(pathPhysicalModelPut);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				Map<String, String> urlParams = new HashMap<>();
				urlParams.put("codi", physicalModelRDTO.getCode());

				ResponseEntity<Object> resp = restCall.executePUT(url, urlParams, physicalModelRDTO);

				processResponse(resp);
			}

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_ACCESSING_PUT_URL, url));
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}

		returnRDTO = getTransactionId(physicalModelRDTO);

		return returnRDTO;
	}

	@Override
	public ReturnRDTO delete(PhysicalModelRDTO physicalModelRDTO) throws ImiException {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {

			physicalModelRDTO.generateTransactionId();
			
			returnRDTO = validator.validateContract(physicalModelRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidatePhysicalModelService.validateSyntaxDelete(physicalModelRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				returnRDTO = validator.validateDelete(physicalModelRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH).concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
						.concat(pathInventary).concat(pathPhysicalModel).concat(pathPhysicalModelDelete);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				Map<String, String> urlParams = new HashMap<>();
				urlParams.put("codi", physicalModelRDTO.getCode());

				ResponseEntity<Object> resp = restCall.executeDELETE(url, urlParams, physicalModelRDTO);

				processResponse(resp);
			}

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_ACCESSING_DELETE_URL, url));
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}

		returnRDTO = getTransactionId(physicalModelRDTO);

		return returnRDTO;
	}

	@Override
	public ReturnPhysicalModelRDTO select(PhysicalModelRDTO physicalModelRDTO) throws ImiException {

		ReturnPhysicalModelRDTO returnPhysicalModelRDTO = new ReturnPhysicalModelRDTO();

		try {

			physicalModelRDTO.generateTransactionId();
			
			returnRDTO = validator.validateContract(physicalModelRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				url = urlApiOrquestrador.concat(ImiConstants.SLASH).concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
						.concat(pathInventary).concat(pathPhysicalModel).concat(pathPhysicalModelGet);
				
				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));
				
				Map<String, String> urlParams = new HashMap<>();
				
				urlParams.put("codi", physicalModelRDTO.getCode());
				
				ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, physicalModelRDTO);
				returnPhysicalModelRDTO = (ReturnPhysicalModelRDTO) utils.rest2Object(resp, returnPhysicalModelRDTO);
				
				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));
			}


		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}

		returnPhysicalModelRDTO.getReturnRDTO().setTransactionId(physicalModelRDTO.getTransactionId());

		return returnPhysicalModelRDTO;
	}

	@Override
	public ReturnRDTO insert(AggregateAmortizationRDTO aggregateAmortizationRDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {

			aggregateAmortizationRDTO.generateTransactionId();
			
			returnRDTO = validator.validateContract(aggregateAmortizationRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidatePhysicalModelService.validateSyntaxInsert(aggregateAmortizationRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				returnRDTO = validator.validateInsert(aggregateAmortizationRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH).concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
						.concat(pathInventary).concat(pathPhysicalModel).concat(pathPhysicalModelType)
						.concat(pathPhysicalModelAggregateAmortization);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				ResponseEntity<Object> resp = restCall.executePOST(url, aggregateAmortizationRDTO);

				processResponse(resp);

			}

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_ACCESSING_POST_URL, url));
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}

		returnRDTO.setTransactionId(aggregateAmortizationRDTO.getTransactionId());
		
		
		return returnRDTO;
	}

	@Override
	public ReturnPhysicalModelMassiveRDTO selectMassive(QueryParameterRDTO queryParameterRDTO) throws ImiException {
		
		ReturnPhysicalModelMassiveRDTO returnPhysicalModelMassiveRDTO = new ReturnPhysicalModelMassiveRDTO();

		try {
			queryParameterRDTO.generateTransactionId();
			
			returnRDTO = validator.validateContract(queryParameterRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidatePhysicalModelService.validateSyntaxSelectMassive(queryParameterRDTO);
			}
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH)
										.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
										.concat(pathInventary)
										.concat(pathPhysicalModel)
										.concat(pathPhysicalModelGetMassive);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				Map<String, String> urlParams = new HashMap<>();
				
				urlParams.put("codi", queryParameterRDTO.getCodeContract());
				urlParams.put("dataReferencia", queryParameterRDTO.getDateReference());
				urlParams.put("codiUsuari", queryParameterRDTO.getCodeUser());
				urlParams.put("transactionId", queryParameterRDTO.getTransactionId());
				
				ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterRDTO);
				
				returnPhysicalModelMassiveRDTO =  (ReturnPhysicalModelMassiveRDTO) utils.rest2Object(resp, returnPhysicalModelMassiveRDTO);
								
				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));
			} else {
				returnPhysicalModelMassiveRDTO.setReturnRDTO(returnRDTO);
			}

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}
		
		returnPhysicalModelMassiveRDTO.getReturnRDTO().setTransactionId(queryParameterRDTO.getTransactionId());
		
		return returnPhysicalModelMassiveRDTO;
	}

	@Override
	public ReturnPhysicalModelDetailedRDTO selectDetailed(QueryParameterRDTO queryParameterRDTO) throws ImiException {
		ReturnPhysicalModelDetailedRDTO returnPhysicalModelDetailedRDTO = new ReturnPhysicalModelDetailedRDTO();

		try {
			queryParameterRDTO.generateTransactionId();
			
			returnRDTO = validator.validateContract(queryParameterRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidatePhysicalModelService.validateSyntaxSelectDetailed(queryParameterRDTO);
			}
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH)
										.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
										.concat(pathInventary)
										.concat(pathPhysicalModel)
										.concat(pathPhysicalModelGetDetailed);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				Map<String, String> urlParams = new HashMap<>();
				
				urlParams.put("codi", queryParameterRDTO.getCode());
				urlParams.put("codiContracta", queryParameterRDTO.getCodeContract());
				urlParams.put("dataReferencia", queryParameterRDTO.getDateReference());
				urlParams.put("codiUsuari", queryParameterRDTO.getCodeUser());
				urlParams.put("transactionId", queryParameterRDTO.getTransactionId());
				
				ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterRDTO);
				
				returnPhysicalModelDetailedRDTO =  (ReturnPhysicalModelDetailedRDTO) utils.rest2Object(resp, returnPhysicalModelDetailedRDTO);
								
				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));
			} else {
				returnPhysicalModelDetailedRDTO.setReturnRDTO(returnRDTO);
			}

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}
		
		returnPhysicalModelDetailedRDTO.getReturnRDTO().setTransactionId(queryParameterRDTO.getTransactionId());
		
		return returnPhysicalModelDetailedRDTO;
	}

	@Override
	public ReturnPhysicalModelAmortizationRDTO selectAmortization(QueryParameterRDTO queryParameterRDTO) throws ImiException {
		ReturnPhysicalModelAmortizationRDTO returnPhysicalModelAmortizationRDTO = new ReturnPhysicalModelAmortizationRDTO();

		try {
			queryParameterRDTO.generateTransactionId();
			
			returnRDTO = validator.validateContract(queryParameterRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidatePhysicalModelService.validateSyntaxSelectAmortization(queryParameterRDTO);
			}
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH)
										.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
										.concat(pathInventary)
										.concat(pathPhysicalModel)
										.concat(pathPhysicalModelGetAmortization);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				Map<String, String> urlParams = new HashMap<>();
				
				urlParams.put("codi", queryParameterRDTO.getCodeContract());
				urlParams.put("codiFraccio", queryParameterRDTO.getCodeFraction());
				urlParams.put("codiTerritori", queryParameterRDTO.getCodeTerritory());
				urlParams.put("codiTipus", queryParameterRDTO.getCodeType());
				urlParams.put("codiGrup", queryParameterRDTO.getCodeGroup());
				urlParams.put("codiUsuari", queryParameterRDTO.getCodeUser());
				urlParams.put("transactionId", queryParameterRDTO.getTransactionId());
				
				ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterRDTO);
				
				returnPhysicalModelAmortizationRDTO =  (ReturnPhysicalModelAmortizationRDTO) utils.rest2Object(resp, returnPhysicalModelAmortizationRDTO);
								
				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));
			} else {
				returnPhysicalModelAmortizationRDTO.setReturnRDTO(returnRDTO);
			}

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}
		
		returnPhysicalModelAmortizationRDTO.getReturnRDTO().setTransactionId(queryParameterRDTO.getTransactionId());
		
		return returnPhysicalModelAmortizationRDTO;
	}

	@Override
	public ReturnRDTO insertDocumentalSupport(DocumentarySupportRDTO documentarySupportRDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {

			documentarySupportRDTO.generateTransactionId();
			
			returnRDTO = validator.validateContract(documentarySupportRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidatePhysicalModelService.validateSyntaxInsertDocumentarySupport(documentarySupportRDTO);
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
				returnRDTO = frontValidatePhysicalModelService.validateSyntaxDeleteDocumentarySupport(documentarySupportRDTO);
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
				returnRDTO = frontValidatePhysicalModelService.validateSyntaxSelectDocumentarySupport(queryParameterDocumentarySupportRDTO);
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
				returnRDTO = frontValidatePhysicalModelService.validateSyntaxInsertActuation(actuationRDTO);
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
				returnRDTO = frontValidatePhysicalModelService.validateSyntaxDeleteActuation(actuationRDTO);
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