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

import es.bcn.imi.framework.vigia.frontal.business.inventary.MaterialResourceService;
import es.bcn.imi.framework.vigia.frontal.validator.ValidatorMaterialResource;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.ActuationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.DocumentarySupportRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.MaterialResourceRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterDocumentarySupportRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.commons.BaseTransactionIdRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnBreakdownAmortizationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnDocumentarySupportRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMaterialResourceDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMaterialResourceVehicleDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMaterialResourceVehicleExpensesRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMaterialResourceVehiclePeriodRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.enums.ReturnEnum;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import es.bcn.vigia.fmw.libutils.services.Utils;
import es.bcn.vigia.fmw.libutils.services.rest.RestCall;
import es.bcn.vigia.fmw.libutils.validator.front.FrontValidateMaterialResourceService;

@Lazy
@Service(ServicesConstants.SRV_FRONTAL_MATERIAL_RESOURCE)
public class MaterialResourceServiceImpl implements MaterialResourceService {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ServicesConstants.SRV_REST_CALL)
	private RestCall restCall;

	@Autowired
	@Qualifier(ServicesConstants.SRV_UTILS)
	private Utils utils;

	@Autowired
	@Qualifier(ServicesConstants.SRV_FRONT_VALIDATE_MATERIAL_RESOURCE)
	private FrontValidateMaterialResourceService frontValidateMaterialResourceService;

	@Autowired
	@Qualifier(ServicesConstants.SRV_FRONT_VALIDATE_MATERIAL_RESOURCE_FMW)
	private ValidatorMaterialResource validator;

	@Value("${url.api.orquestrador}")
	private String urlApiOrquestrador;

	@Value("${url.path.inventary}")
	private String pathInventary;

	@Value("${url.path.materialresource}")
	private String pathMaterialResource;

	@Value("${url.path.materialresource.getMassive}")
	private String pathMaterialResourceGetMassive;

	@Value("${url.path.materialresource.getDetailed}")
	private String pathMaterialResourceGetDetailed;

	@Value("${url.path.materialresource.put}")
	private String pathMaterialResourcePut;

	@Value("${url.path.materialresource.delete}")
	private String pathMaterialResourceDelete;

	@Value("${url.path.materialresource.vehicle}")
	private String pathMaterialResourceVehicle;

	@Value("${url.path.materialresource.vehicle.getMassive}")
	private String pathMaterialResourceVehicleGetMassive;

	@Value("${url.path.materialresource.vehicle.getDetailed}")
	private String pathMaterialResourceVehicleGetDetailed;

	@Value("${url.path.materialresource.vehicle.getAmortization}")
	private String pathMaterialResourceVehicleGetAmortization;

	@Value("${url.path.materialresource.vehicle.getExpenses}")
	private String pathMaterialResourceVehicleGetExpenses;

	@Value("${url.path.materialresource.vehicle.getPeriod}")
	private String pathMaterialResourceVehicleGetPeriod;
	
	@Value("${url.path.materialresource.expense}")
	private String pathMaterialResourceExpense;
	
	@Value("${url.path.materialresource.apportionment}")
	private String pathMaterialResourceApportionment;

	@Value("${url.path.materialresource.amortization}")
	private String pathMaterialResourceAmortization;

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
	public ReturnRDTO insert(MaterialResourceRDTO materialResourceRDTO) throws ImiException {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {

			materialResourceRDTO.generateTransactionId();
			
			returnRDTO = validator.validateContract(materialResourceRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateMaterialResourceService.validateSyntaxInsert(materialResourceRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				returnRDTO = validator.validateInsert(materialResourceRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH).concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
						.concat(pathInventary).concat(pathMaterialResource);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				Map<String, String> urlParams = new HashMap<>();
				urlParams.put("codi", materialResourceRDTO.getCode());

				ResponseEntity<Object> resp = restCall.executePOST(url, materialResourceRDTO);

				processResponse(resp);
			}

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_ACCESSING_POST_URL, url));
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}

		returnRDTO = getTransactionId(materialResourceRDTO);

		return returnRDTO;
	}

	@Override
	public ReturnRDTO update(MaterialResourceRDTO materialResourceRDTO) throws ImiException {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {

			materialResourceRDTO .generateTransactionId();
			
			returnRDTO = validator.validateContract(materialResourceRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateMaterialResourceService.validateSyntaxUpdate(materialResourceRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				returnRDTO = validator.validateUpdate(materialResourceRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH).concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
						.concat(pathInventary).concat(pathMaterialResource).concat(pathMaterialResourcePut);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				Map<String, String> urlParams = new HashMap<>();
				urlParams.put("codi", materialResourceRDTO.getCode());

				ResponseEntity<Object> resp = restCall.executePUT(url, urlParams, materialResourceRDTO);

				processResponse(resp);
			}

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_ACCESSING_PUT_URL, url));
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}

		returnRDTO = getTransactionId(materialResourceRDTO);

		return returnRDTO;
	}

	@Override
	public ReturnRDTO delete(MaterialResourceRDTO materialResourceRDTO) throws ImiException {

		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {

			materialResourceRDTO.generateTransactionId();
			
			returnRDTO = validator.validateContract(materialResourceRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateMaterialResourceService.validateSyntaxDelete(materialResourceRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				returnRDTO = validator.validateDelete(materialResourceRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH).concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
						.concat(pathInventary).concat(pathMaterialResource).concat(pathMaterialResourceDelete);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				Map<String, String> urlParams = new HashMap<>();
				urlParams.put("codi", materialResourceRDTO.getCode());

				ResponseEntity<Object> resp = restCall.executeDELETE(url, urlParams, materialResourceRDTO);

				processResponse(resp);
			}

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_ACCESSING_DELETE_URL, url));
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}

		returnRDTO = getTransactionId(materialResourceRDTO);

		return returnRDTO;
	}

	@Override
	public ReturnMassiveRDTO selectMassive(QueryParameterRDTO queryParameterRDTO) throws ImiException {
		ReturnMassiveRDTO returnMassiveRDTO = new ReturnMassiveRDTO();

		try {
			queryParameterRDTO.generateTransactionId();
			
			returnRDTO = validator.validateContract(queryParameterRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateMaterialResourceService.validateSyntaxSelectMassive(queryParameterRDTO);
			}
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH)
										.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
										.concat(pathInventary)
										.concat(pathMaterialResource)
										.concat(pathMaterialResourceGetMassive);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				Map<String, String> urlParams = new HashMap<String, String>();
				
				urlParams.put("codi", queryParameterRDTO.getCodeContract());
				urlParams.put("dataReferencia", queryParameterRDTO.getDateReference());
				urlParams.put("codiUsuari", queryParameterRDTO.getCodeUser());
				urlParams.put("transactionId", queryParameterRDTO.getTransactionId());
				
				ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterRDTO);
				
				returnMassiveRDTO =  (ReturnMassiveRDTO) utils.rest2Object(resp, returnMassiveRDTO);
								
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
	public ReturnMaterialResourceDetailedRDTO selectDetailed(QueryParameterRDTO queryParameterRDTO) throws ImiException {
		ReturnMaterialResourceDetailedRDTO returnMaterialResourceDetailedRDTO = new ReturnMaterialResourceDetailedRDTO();

		try {
			queryParameterRDTO.generateTransactionId();
			
			returnRDTO = validator.validateContract(queryParameterRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateMaterialResourceService.validateSyntaxSelectDetailed(queryParameterRDTO);
			}
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH)
										.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
										.concat(pathInventary)
										.concat(pathMaterialResource)
										.concat(pathMaterialResourceGetDetailed);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				Map<String, String> urlParams = new HashMap<String, String>();
				
				urlParams.put("codi", queryParameterRDTO.getCode());
				urlParams.put("codiContracta", queryParameterRDTO.getCodeContract());
				urlParams.put("dataReferencia", queryParameterRDTO.getDateReference());
				urlParams.put("codiUsuari", queryParameterRDTO.getCodeUser());
				urlParams.put("transactionId", queryParameterRDTO.getTransactionId());
				
				ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterRDTO);
				
				returnMaterialResourceDetailedRDTO =  (ReturnMaterialResourceDetailedRDTO) utils.rest2Object(resp, returnMaterialResourceDetailedRDTO);
								
				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));
			} else {
				returnMaterialResourceDetailedRDTO.setReturnRDTO(returnRDTO);
			}

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}
		
		returnMaterialResourceDetailedRDTO.getReturnRDTO().setTransactionId(queryParameterRDTO.getTransactionId());
		
		return returnMaterialResourceDetailedRDTO;
	}

	@Override
	public ReturnMassiveRDTO selectVehicleMassive(QueryParameterRDTO queryParameterRDTO) throws ImiException {
		ReturnMassiveRDTO returnMassiveRDTO = new ReturnMassiveRDTO();

		try {
			queryParameterRDTO.generateTransactionId();
			
			returnRDTO = validator.validateContract(queryParameterRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateMaterialResourceService.validateSyntaxSelectMassive(queryParameterRDTO);
			}
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH)
										.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
										.concat(pathInventary)
										.concat(pathMaterialResource)
										.concat(pathMaterialResourceVehicle)
										.concat(pathMaterialResourceVehicleGetMassive);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				Map<String, String> urlParams = new HashMap<String, String>();
				
				urlParams.put("codi", queryParameterRDTO.getCodeContract());
				urlParams.put("dataReferencia", queryParameterRDTO.getDateReference());
				urlParams.put("codiUsuari", queryParameterRDTO.getCodeUser());
				urlParams.put("transactionId", queryParameterRDTO.getTransactionId());
				
				ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterRDTO);
				
				returnMassiveRDTO =  (ReturnMassiveRDTO) utils.rest2Object(resp, returnMassiveRDTO);
								
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
	public ReturnMaterialResourceVehicleDetailedRDTO selectVehicleDetailed(QueryParameterRDTO queryParameterRDTO) throws ImiException {
		ReturnMaterialResourceVehicleDetailedRDTO returnMaterialResourceVehicleDetailedRDTO = new ReturnMaterialResourceVehicleDetailedRDTO();

		try {
			queryParameterRDTO.generateTransactionId();
			
			returnRDTO = validator.validateContract(queryParameterRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateMaterialResourceService.validateSyntaxSelectDetailed(queryParameterRDTO);
			}
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH)
										.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
										.concat(pathInventary)
										.concat(pathMaterialResource)
										.concat(pathMaterialResourceVehicle)
										.concat(pathMaterialResourceVehicleGetDetailed);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				Map<String, String> urlParams = new HashMap<String, String>();
				
				urlParams.put("codi", queryParameterRDTO.getCode());
				urlParams.put("codiContracta", queryParameterRDTO.getCodeContract());
				urlParams.put("dataReferencia", queryParameterRDTO.getDateReference());
				urlParams.put("codiUsuari", queryParameterRDTO.getCodeUser());
				urlParams.put("transactionId", queryParameterRDTO.getTransactionId());
				
				ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterRDTO);
				
				returnMaterialResourceVehicleDetailedRDTO =  (ReturnMaterialResourceVehicleDetailedRDTO) utils.rest2Object(resp, returnMaterialResourceVehicleDetailedRDTO);
								
				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));
			} else {
				returnMaterialResourceVehicleDetailedRDTO.setReturnRDTO(returnRDTO);
			}

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}
		
		returnMaterialResourceVehicleDetailedRDTO.getReturnRDTO().setTransactionId(queryParameterRDTO.getTransactionId());
		
		return returnMaterialResourceVehicleDetailedRDTO;
	}

	@Override
	public ReturnRDTO insertExpense(MaterialResourceRDTO materialResourceRDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();
		try {

			materialResourceRDTO.generateTransactionId();
			
			returnRDTO = validator.validateContract(materialResourceRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateMaterialResourceService.validateSyntaxInsertExpense(materialResourceRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				returnRDTO = validator.validateInsertExpense(materialResourceRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH)
										.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
										.concat(pathInventary)
										.concat(pathMaterialResource)
										.concat(pathMaterialResourceExpense);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				ResponseEntity<Object> resp = restCall.executePOST(url, materialResourceRDTO);

				processResponse(resp);

			}

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_ACCESSING_POST_URL, url));
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}

		returnRDTO.setTransactionId(materialResourceRDTO.getTransactionId());

		return returnRDTO;

	}

	@Override
	public ReturnRDTO insertAmortizationBase(MaterialResourceRDTO materialResourceRDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {

			materialResourceRDTO.generateTransactionId();
			
			returnRDTO = validator.validateContract(materialResourceRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateMaterialResourceService.validateSyntaxInsertAmortizationBase(materialResourceRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				returnRDTO = validator.validateInsertAmortizationBase(materialResourceRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH)
										.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
										.concat(pathInventary)
										.concat(pathMaterialResource)
										.concat(pathMaterialResourceAmortization);
				
				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				ResponseEntity<Object> resp = restCall.executePOST(url, materialResourceRDTO);

				processResponse(resp);
			}

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_ACCESSING_POST_URL, url));
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}

		returnRDTO.setTransactionId(materialResourceRDTO.getTransactionId());

		return returnRDTO;
	}

	@Override
	public ReturnRDTO insertApportionment(MaterialResourceRDTO materialResourceRDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();
		try {

			materialResourceRDTO.generateTransactionId();
			
			returnRDTO = validator.validateContract(materialResourceRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateMaterialResourceService.validateSyntaxInsertApportionment(materialResourceRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				returnRDTO = validator.validateInsertApportionment(materialResourceRDTO);
			}

			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH)
										.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
										.concat(pathInventary)
										.concat(pathMaterialResource)
										.concat(pathMaterialResourceApportionment);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				ResponseEntity<Object> resp = restCall.executePOST(url, materialResourceRDTO);

				processResponse(resp);
			}

		} catch (Exception ex) {
			logger.error(String.format(LogsConstants.LOG_ACCESSING_POST_URL, url));
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}

		returnRDTO.setTransactionId(materialResourceRDTO.getTransactionId());

		return returnRDTO;

	}

	@Override
	public ReturnBreakdownAmortizationRDTO selectAmortization(QueryParameterRDTO queryParameterRDTO) throws ImiException {
		ReturnBreakdownAmortizationRDTO returnBreakdownAmortizationRDTO = new ReturnBreakdownAmortizationRDTO();

		try {
			queryParameterRDTO.generateTransactionId();
			
			returnRDTO = validator.validateContract(queryParameterRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateMaterialResourceService.validateSyntaxSelectAmortitzacio(queryParameterRDTO);
			}
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH)
										.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
										.concat(pathInventary)
										.concat(pathMaterialResource)
										.concat(pathMaterialResourceVehicle)
										.concat(pathMaterialResourceVehicleGetAmortization);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				Map<String, String> urlParams = new HashMap<String, String>();
				
				urlParams.put("codi", queryParameterRDTO.getCode());
				urlParams.put("codiContracta", queryParameterRDTO.getCodeContract());
				urlParams.put("codiUsuari", queryParameterRDTO.getCodeUser());
				urlParams.put("transactionId", queryParameterRDTO.getTransactionId());
				
				ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterRDTO);
				
				returnBreakdownAmortizationRDTO =  (ReturnBreakdownAmortizationRDTO) utils.rest2Object(resp, returnBreakdownAmortizationRDTO);
								
				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));
			} else {
				returnBreakdownAmortizationRDTO.setReturnRDTO(returnRDTO);
			}

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}
		
		returnBreakdownAmortizationRDTO.getReturnRDTO().setTransactionId(queryParameterRDTO.getTransactionId());
		
		return returnBreakdownAmortizationRDTO;
	}

	@Override
	public ReturnMaterialResourceVehicleExpensesRDTO selectExpenses(QueryParameterRDTO queryParameterRDTO) throws ImiException {
		ReturnMaterialResourceVehicleExpensesRDTO returnMaterialResourceVehicleAmortizationRDTO = new ReturnMaterialResourceVehicleExpensesRDTO();

		try {
			queryParameterRDTO.generateTransactionId();
			
			returnRDTO = validator.validateContract(queryParameterRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateMaterialResourceService.validateSyntaxSelectExpenses(queryParameterRDTO);
			}
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH)
										.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
										.concat(pathInventary)
										.concat(pathMaterialResource)
										.concat(pathMaterialResourceVehicle)
										.concat(pathMaterialResourceVehicleGetExpenses);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				Map<String, String> urlParams = new HashMap<String, String>();
				
				urlParams.put("codi", queryParameterRDTO.getCode());
				urlParams.put("codiContracta", queryParameterRDTO.getCodeContract());
				urlParams.put("codiUsuari", queryParameterRDTO.getCodeUser());
				urlParams.put("transactionId", queryParameterRDTO.getTransactionId());
				
				ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterRDTO);
				
				returnMaterialResourceVehicleAmortizationRDTO =  (ReturnMaterialResourceVehicleExpensesRDTO) utils.rest2Object(resp, returnMaterialResourceVehicleAmortizationRDTO);
								
				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));
			} else {
				returnMaterialResourceVehicleAmortizationRDTO.setReturnRDTO(returnRDTO);
			}

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}
		
		returnMaterialResourceVehicleAmortizationRDTO.getReturnRDTO().setTransactionId(queryParameterRDTO.getTransactionId());
		
		return returnMaterialResourceVehicleAmortizationRDTO;
	}

	@Override
	public ReturnMaterialResourceVehiclePeriodRDTO selectPeriod(QueryParameterRDTO queryParameterRDTO) throws ImiException {
		
		ReturnMaterialResourceVehiclePeriodRDTO returnMaterialResourceVehiclePeriodRDTO = new ReturnMaterialResourceVehiclePeriodRDTO();

		try {
			queryParameterRDTO.generateTransactionId();
			
			returnRDTO = validator.validateContract(queryParameterRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateMaterialResourceService.validateSyntaxSelectPeriod(queryParameterRDTO);
			}
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {

				url = urlApiOrquestrador.concat(ImiConstants.SLASH)
										.concat(ImiConstants.TYPE_REQUEST_SYNCHRONOUS)
										.concat(pathInventary)
										.concat(pathMaterialResource)
										.concat(pathMaterialResourceVehicle)
										.concat(pathMaterialResourceVehicleGetPeriod);

				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));

				Map<String, String> urlParams = new HashMap<String, String>();
				
				urlParams.put("codi", queryParameterRDTO.getCode());
				urlParams.put("codiContracta", queryParameterRDTO.getCodeContract());
				urlParams.put("periode", queryParameterRDTO.getPeriod());
				urlParams.put("codiUsuari", queryParameterRDTO.getCodeUser());
				urlParams.put("transactionId", queryParameterRDTO.getTransactionId());
				
				ResponseEntity<Object> resp = restCall.executeGET(url, urlParams, queryParameterRDTO);
				
				returnMaterialResourceVehiclePeriodRDTO =  (ReturnMaterialResourceVehiclePeriodRDTO) utils.rest2Object(resp, returnMaterialResourceVehiclePeriodRDTO);
								
				logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));
			} else {
				returnMaterialResourceVehiclePeriodRDTO.setReturnRDTO(returnRDTO);
			}

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		} finally {
			logger.debug(LogsConstants.LOG_END);
		}
		
		returnMaterialResourceVehiclePeriodRDTO.getReturnRDTO().setTransactionId(queryParameterRDTO.getTransactionId());
		
		return returnMaterialResourceVehiclePeriodRDTO;
	}

	@Override
	public ReturnRDTO insertDocumentalSupport(DocumentarySupportRDTO documentarySupportRDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);

		returnRDTO = new ReturnRDTO();

		try {

			documentarySupportRDTO.generateTransactionId();
			
			returnRDTO = validator.validateContract(documentarySupportRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {	
				returnRDTO = frontValidateMaterialResourceService.validateSyntaxInsertDocumentarySupport(documentarySupportRDTO);
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
				returnRDTO = frontValidateMaterialResourceService.validateSyntaxDeleteDocumentarySupport(documentarySupportRDTO);
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
				returnRDTO = frontValidateMaterialResourceService.validateSyntaxSelectDocumentarySupport(queryParameterDocumentarySupportRDTO);
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
				returnRDTO = frontValidateMaterialResourceService.validateSyntaxInsertActuation(actuationRDTO);
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
				returnRDTO = frontValidateMaterialResourceService.validateSyntaxDeleteActuation(actuationRDTO);
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