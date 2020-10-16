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

import es.bcn.imi.framework.vigia.frontal.business.inventary.UbicationService;
import es.bcn.imi.framework.vigia.frontal.validator.ValidatorUbication;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.ActuationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.DocumentarySupportRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterDocumentarySupportRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.UbicationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.commons.BaseTransactionIdRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnDocumentarySupportRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnUbicationDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnsRDTO;
import es.bcn.vigia.fmw.libutils.enums.ReturnEnum;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import es.bcn.vigia.fmw.libutils.services.Utils;
import es.bcn.vigia.fmw.libutils.services.rest.RestCall;
import es.bcn.vigia.fmw.libutils.validator.front.FrontValidateUbicationService;

@Lazy
@Service(ServicesConstants.SRV_FRONTAL_UBICATION)
public class UbicationServiceImpl implements UbicationService {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ServicesConstants.SRV_REST_CALL)
	private RestCall restCall;

	@Autowired
	@Qualifier(ServicesConstants.SRV_UTILS)
	private Utils utils;

	@Autowired
	@Qualifier(ServicesConstants.SRV_FRONT_VALIDATE_UBICATION)
	private FrontValidateUbicationService frontValidateUbicationService;

	@Autowired
	@Qualifier(ServicesConstants.SRV_FRONT_VALIDATE_UBICATION_FMW)
	private ValidatorUbication validator;

	@Value("${url.api.orquestrador}")
	private String urlApiOrquestrador;

	@Value("${url.path.inventary}")
	private String pathInventary;

	@Value("${url.path.ubications}")
	private String pathUbication;

	@Value("${url.path.ubications.getMassive}")
	private String pathUbicationGetMassive;

	@Value("${url.path.ubications.getDetailed}")
	private String pathUbicationGetDetailed;

	@Value("${url.path.ubications.put}")
	private String pathUbicationPut;

	@Value("${url.path.ubications.delete}")
	private String pathUbicationDelete;

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

	private ReturnsRDTO returnsRDTO;

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
	public ReturnRDTO insert(UbicationRDTO ubicationRDTO) throws ImiException {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {

			ubicationRDTO.generateTransactionId();

			returnRDTO = validator.validateContract(ubicationRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateUbicationService.validateSyntaxInsert(ubicationRDTO);
			}
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				returnRDTO = validator.validateInsert(ubicationRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH).concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
						.concat(pathInventary).concat(pathUbication);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				ResponseEntity<Object> resp = restCall.executePOST(url, ubicationRDTO);

				processResponse(resp);
			}

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_ACCESSING_POST_URL, url));
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}

		returnRDTO = getTransactionId(ubicationRDTO);

		return returnRDTO;
	}

	@Override
	public ReturnRDTO update(UbicationRDTO ubicationRDTO) throws ImiException {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {

			ubicationRDTO.generateTransactionId();

			returnRDTO = validator.validateContract(ubicationRDTO);

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				returnRDTO = frontValidateUbicationService.validateSyntaxUpdate(ubicationRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				returnRDTO = validator.validateUpdate(ubicationRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH).concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
						.concat(pathInventary).concat(pathUbication).concat(pathUbicationPut);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				Map<String, String> urlParams = new HashMap<>();
				urlParams.put("codi", ubicationRDTO.getCode());

				ResponseEntity<Object> resp = restCall.executePUT(url, urlParams, ubicationRDTO);

				processResponse(resp);
			}

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_ACCESSING_PUT_URL, url));
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}

		returnRDTO = getTransactionId(ubicationRDTO);

		return returnRDTO;
	}

	@Override
	public ReturnRDTO delete(UbicationRDTO ubicationRDTO) throws ImiException {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {

			ubicationRDTO.generateTransactionId();

			returnRDTO = validator.validateContract(ubicationRDTO);

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
				returnRDTO = frontValidateUbicationService.validateSyntaxDelete(ubicationRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				returnRDTO = validator.validateDelete(ubicationRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH).concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
						.concat(pathInventary).concat(pathUbication).concat(pathUbicationDelete);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				Map<String, String> urlParams = new HashMap<>();
				urlParams.put("codi", ubicationRDTO.getCode());

				ResponseEntity<Object> resp = restCall.executeDELETE(url, urlParams, ubicationRDTO);

				processResponse(resp);
			}

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_ACCESSING_DELETE_URL, url));
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}

		returnRDTO = getTransactionId(ubicationRDTO);

		return returnRDTO;
	}

	@Override
	public ReturnMassiveRDTO selectMassive(QueryParameterRDTO queryParameterRDTO) throws ImiException {

		ReturnMassiveRDTO returnMassiveRDTO = new ReturnMassiveRDTO();

		try {
			queryParameterRDTO.generateTransactionId();

			returnRDTO = validator.validateContract(queryParameterRDTO);

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
				returnRDTO = frontValidateUbicationService.validateSyntaxSelectMassive(queryParameterRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH).concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
						.concat(pathInventary).concat(pathUbication).concat(pathUbicationGetMassive);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				Map<String, String> urlParams = new HashMap<>();

				urlParams.put("codi", queryParameterRDTO.getCodeContract());
				urlParams.put("dataReferencia", queryParameterRDTO.getDateReference());
				urlParams.put("codiUsuari", queryParameterRDTO.getCodeUser());
				urlParams.put("transactionId", queryParameterRDTO.getTransactionId());

				ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterRDTO);

				returnMassiveRDTO = (ReturnMassiveRDTO) utils.rest2Object(resp, returnMassiveRDTO);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));
			} else {
				returnMassiveRDTO.setReturnRDTO(returnRDTO);
			}

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}

		returnMassiveRDTO.getReturnRDTO().setTransactionId(queryParameterRDTO.getTransactionId());

		return returnMassiveRDTO;
	}

	@Override
	public ReturnUbicationDetailedRDTO selectDetailed(QueryParameterRDTO queryParameterRDTO) throws ImiException {
		ReturnUbicationDetailedRDTO returnUbicationDetailedRDTO = new ReturnUbicationDetailedRDTO();

		try {
			queryParameterRDTO.generateTransactionId();

			returnRDTO = validator.validateContract(queryParameterRDTO);

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
				returnRDTO = frontValidateUbicationService.validateSyntaxSelectDetailed(queryParameterRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH).concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
						.concat(pathInventary).concat(pathUbication).concat(pathUbicationGetDetailed);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				Map<String, String> urlParams = new HashMap<>();

				urlParams.put("codi", queryParameterRDTO.getCode());
				urlParams.put("codiContracta", queryParameterRDTO.getCodeContract());
				urlParams.put("dataReferencia", queryParameterRDTO.getDateReference());
				urlParams.put("codiUsuari", queryParameterRDTO.getCodeUser());
				urlParams.put("transactionId", queryParameterRDTO.getTransactionId());

				ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterRDTO);

				returnUbicationDetailedRDTO = (ReturnUbicationDetailedRDTO) utils.rest2Object(resp,
						returnUbicationDetailedRDTO);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));
			} else {
				returnUbicationDetailedRDTO.setReturnRDTO(returnRDTO);
			}

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}

		returnUbicationDetailedRDTO.getReturnRDTO().setTransactionId(queryParameterRDTO.getTransactionId());

		return returnUbicationDetailedRDTO;
	}

	@Override
	public ReturnRDTO insertDocumentalSupport(DocumentarySupportRDTO documentarySupportRDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {

			documentarySupportRDTO.generateTransactionId();

			returnRDTO = validator.validateContract(documentarySupportRDTO);

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
				returnRDTO = frontValidateUbicationService
						.validateSyntaxInsertDocumentarySupport(documentarySupportRDTO);
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
				returnRDTO = frontValidateUbicationService
						.validateSyntaxDeleteDocumentarySupport(documentarySupportRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				returnRDTO = validator.validateDeleteDocumentarySupport(documentarySupportRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
				url = urlApiOrquestrador.concat(ImiConstants.SLASH).concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
						.concat(pathInventary).concat(pathDocumentarySupport).concat(pathDocumentarySupportDelete);

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
	public ReturnDocumentarySupportRDTO selectDocumentarySupports(
			QueryParameterDocumentarySupportRDTO queryParameterDocumentarySupportRDTO) throws ImiException {
		ReturnDocumentarySupportRDTO returnDocumentarySupportRDTO = new ReturnDocumentarySupportRDTO();

		try {
			queryParameterDocumentarySupportRDTO.generateTransactionId();

			returnRDTO = validator.validateContract(queryParameterDocumentarySupportRDTO);

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
				returnRDTO = frontValidateUbicationService
						.validateSyntaxSelectDocumentarySupport(queryParameterDocumentarySupportRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				returnRDTO = validator.validateSelectDocumentarySupport(queryParameterDocumentarySupportRDTO);
			}
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH).concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
						.concat(pathInventary).concat(pathDocumentarySupport).concat(pathDocumentarySupportGet);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				Map<String, String> urlParams = new HashMap<>();

				urlParams.put("codi", queryParameterDocumentarySupportRDTO.getCodeContract());
				urlParams.put("codiEntitat", queryParameterDocumentarySupportRDTO.getCode());
				urlParams.put("considerarAnulacions", queryParameterDocumentarySupportRDTO.getConsiderAnnulments());
				urlParams.put("codiUsuari", queryParameterDocumentarySupportRDTO.getCodeUser());
				urlParams.put("tipusEntitat", queryParameterDocumentarySupportRDTO.getCodeTypeEntity());
				urlParams.put("transactionId", queryParameterDocumentarySupportRDTO.getTransactionId());

				ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterDocumentarySupportRDTO);

				returnDocumentarySupportRDTO = (ReturnDocumentarySupportRDTO) utils.rest2Object(resp,
						returnDocumentarySupportRDTO);

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

		returnDocumentarySupportRDTO.getReturnRDTO()
				.setTransactionId(queryParameterDocumentarySupportRDTO.getTransactionId());

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
				returnRDTO = frontValidateUbicationService.validateSyntaxInsertActuation(actuationRDTO);
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
				returnRDTO = frontValidateUbicationService.validateSyntaxDeleteActuation(actuationRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				returnRDTO = validator.validateDeleteActuation(actuationRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
				url = urlApiOrquestrador.concat(ImiConstants.SLASH).concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
						.concat(pathInventary).concat(pathActuation).concat(pathActuationDelete);

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

//	@Override
//	public ReturnRDTO redirectAsync(AsyncRDTO<UbicationRDTO> ubicationsRDTO, HttpServletRequest request)
//			throws ImiException {
//		logger.info(LogsConstants.LOG_START);
//
//		returnsRDTO = new ReturnsRDTO();
//		List<ReturnRDTO> returnRDTOs = new ArrayList<>();
//
//		try {
//
//			ubicationsRDTO.generateTransactionId();
//
//			url = urlApiOrquestrador.concat(ImiConstants.SLASH).concat(ImiConstants.TYPE_REQUEST_ASYNCHRONOUS)
//					.concat(pathInventary).concat(pathUbication);
//
//			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));
//
//			ResponseEntity<Object> resp = null;
//
//			if (request.getMethod().equals(String.valueOf(RequestMethod.POST))) {
//				resp = restCall.executePOST(url, ubicationsRDTO);
//			} else if (request.getMethod().equals(String.valueOf(RequestMethod.PUT))) {
//				resp = restCall.executePUT(url, null, ubicationsRDTO);
//			} else if (request.getMethod().equals(String.valueOf(RequestMethod.DELETE))) {
//				resp = restCall.executeDELETE(url, null, ubicationsRDTO);
//			}
//
//			returnsRDTO = (ReturnsRDTO) utils.rest2Object(resp, returnsRDTO);
//
//			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));
//
//		} catch (Exception ex) {
//			logger.error(String.format(LogsConstants.LOG_ACCESSING_POST_URL, url));
//			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
//		} finally {
//			logger.debug(LogsConstants.LOG_END);
//		}
//
//		returnRDTO = getTransactionId(ubicationsRDTO);
//
//		return returnRDTO;
//	}

}