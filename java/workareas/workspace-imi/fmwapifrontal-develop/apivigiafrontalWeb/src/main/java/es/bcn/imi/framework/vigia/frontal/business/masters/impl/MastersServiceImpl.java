package es.bcn.imi.framework.vigia.frontal.business.masters.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import es.bcn.imi.framework.vigia.frontal.business.masters.MastersService;
import es.bcn.imi.framework.vigia.frontal.orm.dao.MastersDao;
import es.bcn.imi.framework.vigia.frontal.orm.dao.MastersGapDao;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisDaoCallbackGap;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisDaoGap;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisTemplateGap;
import es.bcn.imi.framework.vigia.frontal.validator.ValidatorUtils;
import es.bcn.vigia.fmw.libcommons.business.dto.PricesTableBDTO;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.MessageContansts;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.CompatibilityTypesMCFSensor;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.CompatibilityTypesMCLUbication;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.CompatibilityTypesRRMMSensor;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.BranchModelsVehicleGap;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.EventClassificationGap;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.HolidayCalendarGap;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.ServiceContractGap;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.ServiceHierarchyGap;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.ServiceSubContractGap;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.WasteManagementPlantDetailedGap;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.WasteManagementPlantMassiveGap;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.WorkRegimeRelationshipGap;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterMastersRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.BranchModelsVehicleRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.CompatibilityMclUbicationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.CompatibilitySensorMcfRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.CompatibilitySensorRRMMRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.EventClassificationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.HolidayCalendarRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.PricesTableRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.ServiceContractRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.ServiceHierarchyRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.ServiceSubContractRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.WasteManagementPlantDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.WasteManagementPlantMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.WorkRegimeRelationshipRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnBranchModelsVehicleRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnCompatibilityMclUbicationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnCompatibilitySensorMcfRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnCompatibilitySensorRRMMRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnEventClassificationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnHolidayCalendarRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnPricesTableRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnServiceContractRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnServiceHierarchyRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnServiceSubContractRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnWasteManagementPlantDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnWasteManagementPlantMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnWorkRegimeRelationshipRDTO;
import es.bcn.vigia.fmw.libutils.convert.BranchModelsVehicleGapConvert;
import es.bcn.vigia.fmw.libutils.convert.CompatibilityMclUbicationConvert;
import es.bcn.vigia.fmw.libutils.convert.CompatibilitySensorMcfConvert;
import es.bcn.vigia.fmw.libutils.convert.CompatibilitySensorRRMMConvert;
import es.bcn.vigia.fmw.libutils.convert.EventClassificationGapConvert;
import es.bcn.vigia.fmw.libutils.convert.HolidayCalendarGapConvert;
import es.bcn.vigia.fmw.libutils.convert.PricesTableConvert;
import es.bcn.vigia.fmw.libutils.convert.ServiceContractGapConvert;
import es.bcn.vigia.fmw.libutils.convert.ServiceHierarchyGapConvert;
import es.bcn.vigia.fmw.libutils.convert.ServiceSubContractGapConvert;
import es.bcn.vigia.fmw.libutils.convert.WasteManagementPlantDetailedGapConvert;
import es.bcn.vigia.fmw.libutils.convert.WasteManagementPlantMassiveGapConvert;
import es.bcn.vigia.fmw.libutils.convert.WorkRegimeRelationshipGapConvert;
import es.bcn.vigia.fmw.libutils.enums.ReturnEnum;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import es.bcn.vigia.fmw.libutils.services.rest.RestCall;
import es.bcn.vigia.fmw.libutils.validator.front.FrontValidateMastersService;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisDao;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisDaoCallback;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisTemplate;

@Lazy
@Service(ServicesConstants.SRV_FRONTAL_MASTERS)
public class MastersServiceImpl implements MastersService {

	private Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	@Qualifier(ServicesConstants.SRV_REST_CALL)
	private RestCall restCall;

	@Autowired
	@Qualifier(ServicesConstants.SRV_UTILS)
	private es.bcn.vigia.fmw.libutils.services.Utils utils;
	
	@Autowired
	@Qualifier(ServicesConstants.SRV_FRONT_VALIDATE_MASTERS)
	private FrontValidateMastersService frontValidateMastersService;

	@Autowired
	@Qualifier(ImiConstants.MY_BATIS_TEMPLATE)
	private MyBatisTemplate myBatisTemplate;

	@Autowired
	@Qualifier(ImiConstants.MY_BATIS_TEMPLATE_GAP)
	private MyBatisTemplateGap myBatisTemplateGap;

	@Autowired
	@Qualifier(ServicesConstants.SRV_FRONT_VALIDATE_FMW)
	private ValidatorUtils validator;

	@Value("${url.api.certification}")
	private String urlCertification;
	
	@Value("${url.path.certification.gap}")
	private String pathCertificationGap;
	
	@Value("${url.path.certification.prices}")
	private String pathCertificationGapPrices;

	@Value("${load.properties.client.id.key.ibm}")
	private String clientIdIbmKey;

	@Value("${load.properties.client.id.value.ibm}")
	private String clientIdIbmValue;

	private ReturnRDTO returnRDTO;

	@Override
	public ReturnServiceHierarchyRDTO selectServiceHierarchy(QueryParameterMastersRDTO queryParameterMastersRDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);
		ReturnServiceHierarchyRDTO returnServiceHierarchyRDTO = new ReturnServiceHierarchyRDTO();		
		List<ServiceHierarchyRDTO> serviceHierarchyRDTOs = new ArrayList<>();
		
		try {
			queryParameterMastersRDTO.generateTransactionId();
			returnRDTO = frontValidateMastersService.validateSyntaxSelectServiceHierarchy(queryParameterMastersRDTO);
		
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
				serviceHierarchyRDTOs = getServiceHierarchy(queryParameterMastersRDTO);				

				returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_READ.getCodeDescription());
				returnRDTO.setMessage(ReturnEnum.CODE_SUCCESS_READ.getMessage());
			}

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		returnRDTO.setTransactionId(queryParameterMastersRDTO.getTransactionId());
		returnServiceHierarchyRDTO.setReturnRDTO(returnRDTO);
		returnServiceHierarchyRDTO.setServiceHierarchyRDTOs(serviceHierarchyRDTOs);
		logger.debug(LogsConstants.LOG_END);

		return returnServiceHierarchyRDTO;
	}

	public List<ServiceHierarchyRDTO> getServiceHierarchy(QueryParameterMastersRDTO queryParameterMastersRDTO) throws ImiException {
		List<ServiceHierarchyRDTO> serviceHierarchyRDTOs = new ArrayList<>();

		Map<String, Object> map = new HashMap<>();
		map.put("dateReference", queryParameterMastersRDTO.getDateReference());
			
		List<ServiceHierarchyGap> list = myBatisTemplateGap.execute(MastersGapDao.class,
			new MyBatisDaoCallbackGap<List<ServiceHierarchyGap>>() {
				@Override
				public List<ServiceHierarchyGap> execute(MyBatisDaoGap dao) {
					return ((MastersGapDao) dao).getServiceHierarchy(map);
				}
			}
		);
		
		if(list != null && !list.isEmpty()) {
			serviceHierarchyRDTOs = ServiceHierarchyGapConvert.object2rdto(list);
		}

		return serviceHierarchyRDTOs;
	}

	@Override
	public ReturnServiceContractRDTO selectServiceContract(QueryParameterMastersRDTO queryParameterMastersRDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);
		ReturnServiceContractRDTO returnServiceContractRDTO = new ReturnServiceContractRDTO();
		List<ServiceContractRDTO> serviceContractRDTOs = new ArrayList<>();
		
		try {
			queryParameterMastersRDTO.generateTransactionId();

			returnRDTO = validator.validateContract(queryParameterMastersRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {				
				returnRDTO = frontValidateMastersService.validateSyntaxSelectServiceContract(queryParameterMastersRDTO);
			}
		
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
				serviceContractRDTOs = getServiceContract(queryParameterMastersRDTO);				

				returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_READ.getCodeDescription());
				returnRDTO.setMessage(ReturnEnum.CODE_SUCCESS_READ.getMessage());
			}

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		returnRDTO.setTransactionId(queryParameterMastersRDTO.getTransactionId());
		returnServiceContractRDTO.setReturnRDTO(returnRDTO);
		returnServiceContractRDTO.setServiceContractRDTOs(serviceContractRDTOs);
		logger.debug(LogsConstants.LOG_END);
		
		return returnServiceContractRDTO;
	}

	public List<ServiceContractRDTO> getServiceContract(QueryParameterMastersRDTO queryParameterMastersRDTO) throws ImiException {
		List<ServiceContractRDTO> serviceContractRDTOs = new ArrayList<>();

		Map<String, Object> map = new HashMap<>();
		map.put("codeContract", queryParameterMastersRDTO.getCodeContract());
		map.put("dateReference", queryParameterMastersRDTO.getDateReference());				
			
		List<ServiceContractGap> list = myBatisTemplateGap.execute(MastersGapDao.class,
			new MyBatisDaoCallbackGap<List<ServiceContractGap>>() {
				@Override
				public List<ServiceContractGap> execute(MyBatisDaoGap dao) {
					return ((MastersGapDao) dao).getServiceContract(map);
				}
			}
		);
		
		if(list != null && !list.isEmpty()) {
			serviceContractRDTOs = ServiceContractGapConvert.object2rdto(list);
		}

		return serviceContractRDTOs;
	}

	@Override
	public ReturnServiceSubContractRDTO selectServiceSubContract(QueryParameterMastersRDTO queryParameterMastersRDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);
		ReturnServiceSubContractRDTO returnServiceSubContractRDTO = new ReturnServiceSubContractRDTO();
		List<ServiceSubContractRDTO> serviceSubContractRDTOs = new ArrayList<>();
		
		try {
			queryParameterMastersRDTO.generateTransactionId();

			returnRDTO = validator.validateContract(queryParameterMastersRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {				
				returnRDTO = frontValidateMastersService.validateSyntaxSelectServiceSubContract(queryParameterMastersRDTO);
			}
		
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
				serviceSubContractRDTOs = getServiceSubContract(queryParameterMastersRDTO);				

				returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_READ.getCodeDescription());
				returnRDTO.setMessage(ReturnEnum.CODE_SUCCESS_READ.getMessage());
			}

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		returnRDTO.setTransactionId(queryParameterMastersRDTO.getTransactionId());
		returnServiceSubContractRDTO.setReturnRDTO(returnRDTO);
		returnServiceSubContractRDTO.setServiceSubContractRDTOs(serviceSubContractRDTOs);
		logger.debug(LogsConstants.LOG_END);

		return returnServiceSubContractRDTO;
	}

	public List<ServiceSubContractRDTO> getServiceSubContract(QueryParameterMastersRDTO queryParameterMastersRDTO) throws ImiException {
		List<ServiceSubContractRDTO> serviceSubContractRDTOs = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		map.put("codeContract", queryParameterMastersRDTO.getCodeContract());
		map.put("codeSubContract", queryParameterMastersRDTO.getCodeSubContract());
		map.put("dateReference", queryParameterMastersRDTO.getDateReference());
				
		List<ServiceSubContractGap> list = myBatisTemplateGap.execute(MastersGapDao.class,
			new MyBatisDaoCallbackGap<List<ServiceSubContractGap>>() {
				@Override
				public List<ServiceSubContractGap> execute(MyBatisDaoGap dao) {
					return ((MastersGapDao) dao).getServiceSubContract(map);
				}
			}
		);
		
		if(list != null && !list.isEmpty()) {
			serviceSubContractRDTOs = ServiceSubContractGapConvert.object2rdto(list);
		}

		return serviceSubContractRDTOs;
	}

	@Override
	public ReturnWasteManagementPlantMassiveRDTO selectWasteManagementPlantMassive(QueryParameterMastersRDTO queryParameterMastersRDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);
		ReturnWasteManagementPlantMassiveRDTO returnWasteManagementPlantMassiveRDTO = new ReturnWasteManagementPlantMassiveRDTO();
		List<WasteManagementPlantMassiveRDTO> wasteManagementPlantMassiveRDTOs = new ArrayList<>();
		
		try {
			queryParameterMastersRDTO.generateTransactionId();
			returnRDTO = frontValidateMastersService.validateSyntaxSelectWasteManagementPlantMassive(queryParameterMastersRDTO);
		
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
				wasteManagementPlantMassiveRDTOs = getWasteManagementPlantMassive(queryParameterMastersRDTO);				

				returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_READ.getCodeDescription());
				returnRDTO.setMessage(ReturnEnum.CODE_SUCCESS_READ.getMessage());
			}

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		returnRDTO.setTransactionId(queryParameterMastersRDTO.getTransactionId());
		returnWasteManagementPlantMassiveRDTO.setReturnRDTO(returnRDTO);
		returnWasteManagementPlantMassiveRDTO.setWasteManagementPlantMassiveRDTOs(wasteManagementPlantMassiveRDTOs);
		logger.debug(LogsConstants.LOG_END);

		return returnWasteManagementPlantMassiveRDTO;
	}

	public List<WasteManagementPlantMassiveRDTO> getWasteManagementPlantMassive(QueryParameterMastersRDTO queryParameterMastersRDTO) throws ImiException {
		List<WasteManagementPlantMassiveRDTO> wasteManagementPlantMassiveRDTOs = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		map.put("dateReference", queryParameterMastersRDTO.getDateReference());		
			
		List<WasteManagementPlantMassiveGap> list = myBatisTemplateGap.execute(MastersGapDao.class,
			new MyBatisDaoCallbackGap<List<WasteManagementPlantMassiveGap>>() {
				@Override
				public List<WasteManagementPlantMassiveGap> execute(MyBatisDaoGap dao) {
					return ((MastersGapDao) dao).getWasteManagementPlantMassive(map);
				}
			}
		);
		
		if(list != null && !list.isEmpty()) {
			wasteManagementPlantMassiveRDTOs = WasteManagementPlantMassiveGapConvert.object2rdto(list);
		}
		return wasteManagementPlantMassiveRDTOs;
	}

	@Override
	public ReturnWasteManagementPlantDetailedRDTO selectWasteManagementPlantDetailed(QueryParameterMastersRDTO queryParameterMastersRDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);
		ReturnWasteManagementPlantDetailedRDTO returnWasteManagementPlantDetailedRDTO = new ReturnWasteManagementPlantDetailedRDTO();
		WasteManagementPlantDetailedRDTO wasteManagementPlantDetailedRDTO = new WasteManagementPlantDetailedRDTO();
		
		try {
			queryParameterMastersRDTO.generateTransactionId();
			returnRDTO = frontValidateMastersService.validateSyntaxSelectWasteManagementPlantDetailed(queryParameterMastersRDTO);
		
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
				wasteManagementPlantDetailedRDTO = getWasteManagementPlantDetailed(queryParameterMastersRDTO);				

				returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_READ.getCodeDescription());
				returnRDTO.setMessage(ReturnEnum.CODE_SUCCESS_READ.getMessage());
			}

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		returnRDTO.setTransactionId(queryParameterMastersRDTO.getTransactionId());
		returnWasteManagementPlantDetailedRDTO.setReturnRDTO(returnRDTO);
		returnWasteManagementPlantDetailedRDTO.setWasteManagementPlantDetailedRDTO(wasteManagementPlantDetailedRDTO);
		logger.debug(LogsConstants.LOG_END);

		return returnWasteManagementPlantDetailedRDTO;
	}

	public WasteManagementPlantDetailedRDTO getWasteManagementPlantDetailed(QueryParameterMastersRDTO queryParameterMastersRDTO) throws ImiException {
		WasteManagementPlantDetailedRDTO wasteManagementPlantDetailedRDTO = new WasteManagementPlantDetailedRDTO();
		Map<String, Object> map = new HashMap<>();
		map.put("code", queryParameterMastersRDTO.getCode());
		map.put("dateReference", queryParameterMastersRDTO.getDateReference());
	
		List<WasteManagementPlantDetailedGap> list = myBatisTemplateGap.execute(MastersGapDao.class,
			new MyBatisDaoCallbackGap<List<WasteManagementPlantDetailedGap>>() {
				@Override
				public List<WasteManagementPlantDetailedGap> execute(MyBatisDaoGap dao) {
					return ((MastersGapDao) dao).getWasteManagementPlantDetailed(map);
				}
			}
		);
		
		if(list != null && !list.isEmpty()) {
			wasteManagementPlantDetailedRDTO = WasteManagementPlantDetailedGapConvert.object2rdto(list.get(0));
		}
		return wasteManagementPlantDetailedRDTO;
	}

	@Override
	public ReturnPricesTableRDTO selectPricesTable(QueryParameterMastersRDTO queryParameterMastersRDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);
		ReturnPricesTableRDTO returnPricesTableRDTO = new ReturnPricesTableRDTO();
		List<PricesTableRDTO> pricesTableRDTOs = new ArrayList<>();
		
		try {
			queryParameterMastersRDTO.generateTransactionId();

			returnRDTO = validator.validateContract(queryParameterMastersRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
				returnRDTO = frontValidateMastersService.validateSyntaxSelectPricesTable(queryParameterMastersRDTO);				
			}
		
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
				pricesTableRDTOs = getPricesTable(queryParameterMastersRDTO);
				
				returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_READ.getCodeDescription());
				returnRDTO.setMessage(ReturnEnum.CODE_SUCCESS_READ.getMessage());
			}

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		returnRDTO.setTransactionId(queryParameterMastersRDTO.getTransactionId());
		returnPricesTableRDTO.setReturnRDTO(returnRDTO);
		returnPricesTableRDTO.setPricesTableRDTOs(pricesTableRDTOs);
		logger.debug(LogsConstants.LOG_END);

		return returnPricesTableRDTO;
	}

	public List<PricesTableRDTO> getPricesTable(QueryParameterMastersRDTO queryParameterMastersRDTO) throws ImiException {
		List<PricesTableRDTO> pricesTableRDTOs = new ArrayList<>();

		try {
			if(returnRDTO == null) {
				returnRDTO = new ReturnRDTO();
			}

			String url = urlCertification + pathCertificationGap + pathCertificationGapPrices;

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_URL, url));
			
			Map<String, String> clientId = new HashMap<String, String>();			
			clientId.put("clientIdKey", clientIdIbmKey);
			clientId.put("clientIdValue", clientIdIbmValue);

			Map<String, String> urlParams = new HashMap<String, String>();
			urlParams.put("codiContracta", queryParameterMastersRDTO.getCodeContract());
			
			ResponseEntity<Object> resp = restCall.executeGETClientId(url, urlParams, queryParameterMastersRDTO, clientId);
			
			List<PricesTableBDTO> priceTableBDTOs = (List<PricesTableBDTO>) utils.rest2PricesTable(resp);
			
			for(PricesTableBDTO priceTableBDTO : priceTableBDTOs) {
				priceTableBDTO.setCodeContract(queryParameterMastersRDTO.getCodeContract());
			}
			
			pricesTableRDTOs = PricesTableConvert.bdto2rdto(priceTableBDTOs);						

			returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_READ.getCodeDescription());
			returnRDTO.setMessage(ReturnEnum.CODE_SUCCESS_READ.getMessage());

			logger.info(String.format(LogsConstants.LOG_CALL_INTEGRATION_RESPONSE, resp));

		} catch (Exception ex) {			
			returnRDTO.setCode(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription());
			returnRDTO.setMessage(String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(), 
					MessageContansts.MSG_ENTITY_MASTERS, queryParameterMastersRDTO.getCodeContract()));
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
		}
		return pricesTableRDTOs;
	}

	@Override
	public ReturnEventClassificationRDTO selectEventClassification(QueryParameterMastersRDTO queryParameterMastersRDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);
		ReturnEventClassificationRDTO returnEventClassificationRDTO = new ReturnEventClassificationRDTO();
		List<EventClassificationRDTO> eventClassificationRDTOs = new ArrayList<>();
		
		try {
			queryParameterMastersRDTO.generateTransactionId();
			returnRDTO = frontValidateMastersService.validateSyntaxSelectEventClassification(queryParameterMastersRDTO);
		
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
				eventClassificationRDTOs = getEventClassification(queryParameterMastersRDTO);				

				returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_READ.getCodeDescription());
				returnRDTO.setMessage(ReturnEnum.CODE_SUCCESS_READ.getMessage());
			}
			
		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		returnRDTO.setTransactionId(queryParameterMastersRDTO.getTransactionId());
		returnEventClassificationRDTO.setReturnRDTO(returnRDTO);
		returnEventClassificationRDTO.setEventClasificationRDTOs(eventClassificationRDTOs);
		logger.debug(LogsConstants.LOG_END);

		return returnEventClassificationRDTO;
	}

	public List<EventClassificationRDTO> getEventClassification(QueryParameterMastersRDTO queryParameterMastersRDTO) throws ImiException {
		List<EventClassificationRDTO> eventClassificationRDTOs = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		map.put("dateReference", queryParameterMastersRDTO.getDateReference());

		List<EventClassificationGap> list = myBatisTemplateGap.execute(MastersGapDao.class,
			new MyBatisDaoCallbackGap<List<EventClassificationGap>>() {
				@Override
				public List<EventClassificationGap> execute(MyBatisDaoGap dao) {
					return ((MastersGapDao) dao).getEventClassification(map);
				}
			}
		);
		
		if(list != null && !list.isEmpty()) {
			eventClassificationRDTOs = EventClassificationGapConvert.object2rdto(list);
		}
		return eventClassificationRDTOs;
	}

	@Override
	public ReturnBranchModelsVehicleRDTO selectBranchModelsVehicle(QueryParameterMastersRDTO queryParameterMastersRDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);
		ReturnBranchModelsVehicleRDTO returnBranchModelsVehicleRDTO = new ReturnBranchModelsVehicleRDTO();
		List<BranchModelsVehicleRDTO> branchModelsVehicleRDTOs = new ArrayList<>();
		
		try {
			queryParameterMastersRDTO.generateTransactionId();
			returnRDTO = frontValidateMastersService.validateSyntaxSelectBranchModelsVehicle(queryParameterMastersRDTO);
		
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
				branchModelsVehicleRDTOs = getBranchModelsVehicle(queryParameterMastersRDTO);				

				returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_READ.getCodeDescription());
				returnRDTO.setMessage(ReturnEnum.CODE_SUCCESS_READ.getMessage());
			}

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		returnRDTO.setTransactionId(queryParameterMastersRDTO.getTransactionId());
		returnBranchModelsVehicleRDTO.setReturnRDTO(returnRDTO);
		returnBranchModelsVehicleRDTO.setBranchModelsVehicleRDTOs(branchModelsVehicleRDTOs);

		logger.debug(LogsConstants.LOG_END);

		return returnBranchModelsVehicleRDTO;
	}

	public List<BranchModelsVehicleRDTO> getBranchModelsVehicle(QueryParameterMastersRDTO queryParameterMastersRDTO) throws ImiException {
		List<BranchModelsVehicleRDTO> branchModelsVehicleRDTOs = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		map.put("dateReference", queryParameterMastersRDTO.getDateReference());
					
		List<BranchModelsVehicleGap> list = myBatisTemplateGap.execute(MastersGapDao.class,
			new MyBatisDaoCallbackGap<List<BranchModelsVehicleGap>>() {
				@Override
				public List<BranchModelsVehicleGap> execute(MyBatisDaoGap dao) {
					return ((MastersGapDao) dao).getBranchModelsVehicle(map);
				}
			}
		);
		
		if(list != null && !list.isEmpty()) {
			branchModelsVehicleRDTOs = BranchModelsVehicleGapConvert.object2rdto(list);
		}
		
		return branchModelsVehicleRDTOs;
	}

	@Override
	public ReturnHolidayCalendarRDTO selectHolidayCalendar(QueryParameterMastersRDTO queryParameterMastersRDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);
		ReturnHolidayCalendarRDTO returnHolidayCalendarRDTO = new ReturnHolidayCalendarRDTO();
		List<HolidayCalendarRDTO> holidayCalendarRDTOs = new ArrayList<>();
		
		try {
			queryParameterMastersRDTO.generateTransactionId();
			returnRDTO = frontValidateMastersService.validateSyntaxSelectHolidayCalendar(queryParameterMastersRDTO);
		
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
				holidayCalendarRDTOs = getHolidayCalendar(queryParameterMastersRDTO);				

				returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_READ.getCodeDescription());
				returnRDTO.setMessage(ReturnEnum.CODE_SUCCESS_READ.getMessage());
			}

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}

		returnRDTO.setTransactionId(queryParameterMastersRDTO.getTransactionId());
		returnHolidayCalendarRDTO.setReturnRDTO(returnRDTO);
		returnHolidayCalendarRDTO.setHolidayCalendarRDTOs(holidayCalendarRDTOs);
		logger.debug(LogsConstants.LOG_END);

		return returnHolidayCalendarRDTO;
	}

	public List<HolidayCalendarRDTO> getHolidayCalendar(QueryParameterMastersRDTO queryParameterMastersRDTO) throws ImiException {
		List<HolidayCalendarRDTO> holidayCalendarRDTOs = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		map.put("year", queryParameterMastersRDTO.getYear());

		List<HolidayCalendarGap> list = myBatisTemplateGap.execute(MastersGapDao.class,
			new MyBatisDaoCallbackGap<List<HolidayCalendarGap>>() {
				@Override
				public List<HolidayCalendarGap> execute(MyBatisDaoGap dao) {
					return ((MastersGapDao) dao).getHolidayCalendar(map);
				}
			}
		);
		
		if(list != null && !list.isEmpty()) {
			holidayCalendarRDTOs = HolidayCalendarGapConvert.object2rdto(list);
		}
		return holidayCalendarRDTOs;
	}

	@Override
	public ReturnWorkRegimeRelationshipRDTO selectWorkRegimeRelationship(QueryParameterMastersRDTO queryParameterMastersRDTO) throws ImiException {
		logger.info(LogsConstants.LOG_START);
		ReturnWorkRegimeRelationshipRDTO returnWorkRegimeRelationshipRDTO = new ReturnWorkRegimeRelationshipRDTO();
		List<WorkRegimeRelationshipRDTO> workRegimeRelationshipRDTOs = new ArrayList<>();
		
		try {
			queryParameterMastersRDTO.generateTransactionId();

			returnRDTO = validator.validateContract(queryParameterMastersRDTO);
			
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
				returnRDTO = frontValidateMastersService.validateSyntaxSelectWorkRegimeRelationship(queryParameterMastersRDTO);				
			}
		
			if (returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
				workRegimeRelationshipRDTOs = getWorkRegimeRelationship(queryParameterMastersRDTO);				

				returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_READ.getCodeDescription());
				returnRDTO.setMessage(ReturnEnum.CODE_SUCCESS_READ.getMessage());
			}

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}
		
		returnRDTO.setTransactionId(queryParameterMastersRDTO.getTransactionId());
		returnWorkRegimeRelationshipRDTO.setReturnRDTO(returnRDTO);
		returnWorkRegimeRelationshipRDTO.setWorkRegimeRelationshipRDTOs(workRegimeRelationshipRDTOs);
		logger.debug(LogsConstants.LOG_END);

		return returnWorkRegimeRelationshipRDTO;
	}

	public List<WorkRegimeRelationshipRDTO> getWorkRegimeRelationship(QueryParameterMastersRDTO queryParameterMastersRDTO) throws ImiException {
		List<WorkRegimeRelationshipRDTO> workRegimeRelationshipRDTOs = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		map.put("codeContract", queryParameterMastersRDTO.getCodeContract());
		map.put("dateReference", queryParameterMastersRDTO.getDateReference());		
	
		List<WorkRegimeRelationshipGap> list = myBatisTemplateGap.execute(MastersGapDao.class,
			new MyBatisDaoCallbackGap<List<WorkRegimeRelationshipGap>>() {
				@Override
				public List<WorkRegimeRelationshipGap> execute(MyBatisDaoGap dao) {
					return ((MastersGapDao) dao).getWorkRegimeRelationship(map);
				}
			}
		);
		
		if(list != null && !list.isEmpty()) {
			workRegimeRelationshipRDTOs = WorkRegimeRelationshipGapConvert.object2rdto(list);
		}
		return workRegimeRelationshipRDTOs;
	}

	@Override
	public ReturnCompatibilityMclUbicationRDTO selectCompatibilityMclUbication() throws ImiException {
		logger.info(LogsConstants.LOG_START);
		ReturnCompatibilityMclUbicationRDTO returnCompatibilityMclUbicationRDTO = new ReturnCompatibilityMclUbicationRDTO();
		List<CompatibilityMclUbicationRDTO> compatibilityMclUbicationRDTOs = new ArrayList<>();
		returnRDTO = new ReturnRDTO();
		
		try {
			compatibilityMclUbicationRDTOs = getCompatibilityMclUbication();			

			returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_READ.getCodeDescription());
			returnRDTO.setMessage(ReturnEnum.CODE_SUCCESS_READ.getMessage());

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}
		
		returnCompatibilityMclUbicationRDTO.setReturnRDTO(returnRDTO);
		returnCompatibilityMclUbicationRDTO.setCompatibilityMclUbicationRDTOs(compatibilityMclUbicationRDTOs);
		logger.debug(LogsConstants.LOG_END);

		return returnCompatibilityMclUbicationRDTO;
	}

	public List<CompatibilityMclUbicationRDTO> getCompatibilityMclUbication() throws ImiException {
		List<CompatibilityMclUbicationRDTO> compatibilityMclUbicationRDTOs = new ArrayList<>();
		List<CompatibilityTypesMCLUbication> list = myBatisTemplate.execute(MastersDao.class,
			new MyBatisDaoCallback<List<CompatibilityTypesMCLUbication>>() {
				@Override
				public List<CompatibilityTypesMCLUbication> execute(MyBatisDao dao) {
					return ((MastersDao) dao).getCompatibilityMclUbication();
				}
			}
		);
		
		if(list != null && !list.isEmpty()) {
			compatibilityMclUbicationRDTOs = CompatibilityMclUbicationConvert.object2rdto(list);
		}
		return compatibilityMclUbicationRDTOs;
	}

	@Override
	public ReturnCompatibilitySensorRRMMRDTO selectCompatibilitySensorRRMM() throws ImiException {
		logger.info(LogsConstants.LOG_START);
		ReturnCompatibilitySensorRRMMRDTO returnCompatibilitySensorRRMMRDTO = new ReturnCompatibilitySensorRRMMRDTO();
		List<CompatibilitySensorRRMMRDTO> compatibilitySensorRRMMRDTOs = new ArrayList<>();
		returnRDTO = new ReturnRDTO();
		
		try {
			compatibilitySensorRRMMRDTOs = getCompatibilitySensorRRMM();			

			returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_READ.getCodeDescription());
			returnRDTO.setMessage(ReturnEnum.CODE_SUCCESS_READ.getMessage());

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}
		
		returnCompatibilitySensorRRMMRDTO.setReturnRDTO(returnRDTO);
		returnCompatibilitySensorRRMMRDTO.setCompatibilitySensorRRMMRDTOs(compatibilitySensorRRMMRDTOs);
		logger.debug(LogsConstants.LOG_END);

		return returnCompatibilitySensorRRMMRDTO;
	}

	public List<CompatibilitySensorRRMMRDTO> getCompatibilitySensorRRMM() throws ImiException {
		List<CompatibilitySensorRRMMRDTO> compatibilitySensorRRMMRDTOs = new ArrayList<>();
		List<CompatibilityTypesRRMMSensor> list = myBatisTemplate.execute(MastersDao.class,
			new MyBatisDaoCallback<List<CompatibilityTypesRRMMSensor>>() {
				@Override
				public List<CompatibilityTypesRRMMSensor> execute(MyBatisDao dao) {
					return ((MastersDao) dao).getCompatibilitySensorRRMM();
				}
			}
		);
		
		if(list != null && !list.isEmpty()) {
			compatibilitySensorRRMMRDTOs = CompatibilitySensorRRMMConvert.object2rdto(list);
		}
		return compatibilitySensorRRMMRDTOs;
	}

	@Override
	public ReturnCompatibilitySensorMcfRDTO selectCompatibilitySensorMcf() throws ImiException {
		logger.info(LogsConstants.LOG_START);
		ReturnCompatibilitySensorMcfRDTO returnCompatibilitySensorMcfRDTO = new ReturnCompatibilitySensorMcfRDTO();
		List<CompatibilitySensorMcfRDTO> compatibilitySensorMcfRDTOs = new ArrayList<>();
		returnRDTO = new ReturnRDTO();
		
		try {
			compatibilitySensorMcfRDTOs = getCompatibilitySensorMcf();			

			returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_READ.getCodeDescription());
			returnRDTO.setMessage(ReturnEnum.CODE_SUCCESS_READ.getMessage());

		} catch (Exception ex) {
			logger.error(LogsConstants.LOG_ACCESSING_KO, ex);
			throw new ImiException(LogsConstants.LOG_ACCESSING_KO, ex);
		}
		
		returnCompatibilitySensorMcfRDTO.setReturnRDTO(returnRDTO);
		returnCompatibilitySensorMcfRDTO.setCompatibilitySensorMcfRDTOs(compatibilitySensorMcfRDTOs);
		logger.debug(LogsConstants.LOG_END);

		return returnCompatibilitySensorMcfRDTO;
	}

	public List<CompatibilitySensorMcfRDTO> getCompatibilitySensorMcf() throws ImiException {
		List<CompatibilitySensorMcfRDTO> compatibilitySensorMcfRDTOs = new ArrayList<>();
		List<CompatibilityTypesMCFSensor> list = myBatisTemplate.execute(MastersDao.class,
			new MyBatisDaoCallback<List<CompatibilityTypesMCFSensor>>() {
				@Override
				public List<CompatibilityTypesMCFSensor> execute(MyBatisDao dao) {
					return ((MastersDao) dao).getCompatibilitySensorMcf();
				}
			}
		);
		
		if(list != null && !list.isEmpty()) {
			compatibilitySensorMcfRDTOs = CompatibilitySensorMcfConvert.object2rdto(list);
		}
		return compatibilitySensorMcfRDTOs;
	}
	
}
