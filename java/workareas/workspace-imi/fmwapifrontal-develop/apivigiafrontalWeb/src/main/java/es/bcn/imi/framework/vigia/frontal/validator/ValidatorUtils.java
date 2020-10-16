package es.bcn.imi.framework.vigia.frontal.validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import es.bcn.imi.framework.vigia.frontal.execution.services.TimeClockSummaryService;
import es.bcn.imi.framework.vigia.frontal.gap.certification.services.CertificationConceptGapService;
import es.bcn.imi.framework.vigia.frontal.gap.execution.services.EventGapService;
import es.bcn.imi.framework.vigia.frontal.gap.execution.services.GroupEventGapService;
import es.bcn.imi.framework.vigia.frontal.gap.execution.services.PlantWeighingGapService;
import es.bcn.imi.framework.vigia.frontal.gap.execution.services.ServiceGapService;
import es.bcn.imi.framework.vigia.frontal.gap.inventary.services.BrandModelGapService;
import es.bcn.imi.framework.vigia.frontal.gap.inventary.services.CommerceGapService;
import es.bcn.imi.framework.vigia.frontal.gap.inventary.services.GroupFurnitureGapService;
import es.bcn.imi.framework.vigia.frontal.gap.inventary.services.InstallationGapService;
import es.bcn.imi.framework.vigia.frontal.gap.inventary.services.LogicalModelGapService;
import es.bcn.imi.framework.vigia.frontal.gap.inventary.services.MaterialResourceGapService;
import es.bcn.imi.framework.vigia.frontal.gap.inventary.services.PhysicalModelGapService;
import es.bcn.imi.framework.vigia.frontal.gap.inventary.services.TypeSubtypeExpenseGapService;
import es.bcn.imi.framework.vigia.frontal.gap.inventary.services.UbicationGapService;
import es.bcn.imi.framework.vigia.frontal.gap.inventary.services.ValueListGapService;
import es.bcn.imi.framework.vigia.frontal.gap.itinerary.services.ItineraryGapService;
import es.bcn.imi.framework.vigia.frontal.gap.planification.services.PlanificationGapService;
import es.bcn.imi.framework.vigia.frontal.inventary.services.ActuationService;
import es.bcn.imi.framework.vigia.frontal.inventary.services.CompatibilityService;
import es.bcn.imi.framework.vigia.frontal.inventary.services.DocumentarySupportService;
import es.bcn.imi.framework.vigia.frontal.inventary.services.MaterialResourceNoVehicleService;
import es.bcn.imi.framework.vigia.frontal.inventary.services.SensorService;
import es.bcn.imi.framework.vigia.frontal.inventary.services.ValueListService;
import es.bcn.imi.framework.vigia.frontal.services.ContractService;
import es.bcn.vigia.fmw.libcommons.business.dto.ActuationBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.ContractBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.DocumentarySupportBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.MaterialResourceBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.SensorBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.TimeClockSummaryBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.ValueListBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.BrandModelGapBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.CommerceGapBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.EventGapBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.GroupEventGapBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.GroupFurnitureGapBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.InstallationGapBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.ItineraryGapBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.LogicalModelGapBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.MaterialResourceGapBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.PhysicalModelGapBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.PlanificationGapBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.PlantWeighingGapBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.ServiceGapBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.TypeSubtypeExpenseGapBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.UbicationGapBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.ValueListGapBDTO;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.MessageContansts;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.constants.ValidationConstants;
import es.bcn.vigia.fmw.libcommons.constants.ValueListConstants;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.CompatibilityTypesMCFSensor;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.CompatibilityTypesMCLUbication;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.CompatibilityTypesRRMMSensor;
import es.bcn.vigia.fmw.libcommons.valueobject.Entity;
import es.bcn.vigia.fmw.libcommons.valueobject.Type;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.SensorRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.commons.BaseTransactionIdRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.Utils;
import es.bcn.vigia.fmw.libutils.enums.ReturnEnum;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

@Lazy
@Service(ServicesConstants.SRV_FRONT_VALIDATE_FMW)
public class ValidatorUtils {
	
	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ServicesConstants.SRV_VALUE_LIST)
	private ValueListService valueListService;
	
	@Autowired
	@Qualifier(ServicesConstants.SRV_SENSOR)
	private SensorService sensorService;
	
	@Autowired
	@Qualifier(ServicesConstants.SRV_VALUE_LIST_GAP)
	private ValueListGapService valueListGapService;
	
	@Autowired
	@Qualifier(ServicesConstants.SRV_UBICATION_GAP)
	private UbicationGapService ubicationGapService;
	
	@Autowired
	@Qualifier(ServicesConstants.SRV_LOGICAL_MODEL_GAP)
	private LogicalModelGapService logicalModelGapService;

	
	@Autowired
	@Qualifier(ServicesConstants.SRV_PHYSICAL_MODEL_GAP)
	private PhysicalModelGapService physicalModelGapService;

	@Autowired
	@Qualifier(ServicesConstants.SRV_MATERIAL_RESOURCE_GAP)
	private MaterialResourceGapService materialResourceGapService;

	@Autowired
	@Qualifier(ServicesConstants.SRV_INSTALLATION_GAP)
	private InstallationGapService installationGapService;

	@Autowired
	@Qualifier(ServicesConstants.SRV_BRAND_MODEL_GAP)
	private BrandModelGapService brandModelGapService;

	@Autowired
	@Qualifier(ServicesConstants.SRV_COMPATIBILITY)
	private CompatibilityService compatibilityService;

	@Autowired
	@Qualifier(ServicesConstants.SRV_RM_NO_VEHICLES)
	private MaterialResourceNoVehicleService rmNoVehiclesService;

	@Autowired
	@Qualifier(ServicesConstants.SRV_TYPE_SUBTYPE_EXPENSE_GAP)
	private TypeSubtypeExpenseGapService typeSubtypeExpenseGapService;

	@Autowired
	@Qualifier(ServicesConstants.SRV_COMMERCE_GAP)
	private CommerceGapService commerceGapService;

	@Autowired
	@Qualifier(ServicesConstants.SRV_ITINERARY_GAP)
	private ItineraryGapService itineraryGapService;

	@Autowired
	@Qualifier(ServicesConstants.SRV_EVENT_GAP)
	private EventGapService eventGapService;

	@Autowired
	@Qualifier(ServicesConstants.SRV_SERVICE_GAP)
	private ServiceGapService serviceGapService;

	@Autowired
	@Qualifier(ServicesConstants.SRV_PLANIFICATION_GAP)
	private PlanificationGapService planificationGapService;

	@Autowired
	@Qualifier(ServicesConstants.SRV_GROUP_EVENT_GAP)
	private GroupEventGapService groupEventGapService;
	
	@Autowired
	@Qualifier(ServicesConstants.SRV_PLANT_WEIGHING_GAP)
	private PlantWeighingGapService plantWeighingGapService;

	@Autowired
	@Qualifier(ServicesConstants.SRV_TIME_CLOCK_SUMMARY)
	private TimeClockSummaryService timeClockSummaryService;

	@Autowired
	@Qualifier(ServicesConstants.SRV_DOCUMENTARY_SUPPORT)
	private DocumentarySupportService documentarySupportService;
	
	@Autowired
	@Qualifier(ServicesConstants.SRV_ACTUATION)
	private ActuationService actuationService;

	@Autowired
	@Qualifier(ServicesConstants.SRV_GROUP_FURNITURE_GAP)
	private GroupFurnitureGapService groupFurnitureGapService;

	@Autowired
	@Qualifier(ServicesConstants.SRV_CONCEPT_GAP)
	private CertificationConceptGapService conceptGapService;
	
	@Autowired
	@Qualifier(ServicesConstants.SRV_CONTRACT)
	private ContractService contractService;

	public boolean validateValueList(String field, Type type) {
		boolean existsValue = false;
		try {
			List<ValueListBDTO> valuesList = valueListService.getValueList(type);
			if (!valuesList.isEmpty()) {
				List<String> codesValueList = new ArrayList<>();	
				for (ValueListBDTO value : valuesList) {
					codesValueList.add(value.getValue());
				}
				if (codesValueList.contains(field))
				{
					existsValue = true;
				}
			}

		} catch (ImiException e) {
			logger.error(LogsConstants.LOG_KO_VALIDATION, e);
		}
		return existsValue;
	}
	

	public ReturnRDTO validateSensorsContract(String codeContract, List<SensorRDTO> sensorsRDTO ) {
		ReturnRDTO returnRDTO = new ReturnRDTO();
		returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription());
		
		try {
			
			List<SensorBDTO> sensorsContract = sensorService.getSensorsContract(codeContract);
			List<String> sensorCodes = new ArrayList<>();
			for (SensorBDTO sensorBDTO : sensorsContract)
			{
				sensorCodes.add(sensorBDTO.getCode());
				
			}
			for (SensorRDTO sensor : sensorsRDTO)
			{
				if (!sensorCodes.contains(sensor.getCode()))
				{
					returnRDTO.setCode(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription());
					returnRDTO.setMessage(String.format(MessageContansts.MSG_SENSORS_NOT_FOUND,
							sensor.getCode()));
					break;
				}
				
			}

		} catch (ImiException e) {
			logger.error(LogsConstants.LOG_KO_VALIDATION, e);
			returnRDTO.setCode( ReturnEnum.CODE_ERROR_GENERIC.getCodeDescription());
			returnRDTO.setMessage(String.format(ReturnEnum.CODE_ERROR_GENERIC.getMessage()));

		}
		return returnRDTO;
	}
	
	public boolean validateValueListGap(String field, Entity entity) {
		boolean existsValue = false;
		try {
			List<ValueListGapBDTO> valuesList = valueListGapService.getValueListGap(entity);
			if (!valuesList.isEmpty()) {
				List<String> codesValueListGap = new ArrayList<>();
				for (ValueListGapBDTO value : valuesList) {
					codesValueListGap.add(value.getCode());
				}
				if (codesValueListGap.contains(field))
				{
					existsValue = true;
				}
			}

		} catch (ImiException e) {
			logger.error(LogsConstants.LOG_KO_VALIDATION, e);
		}
		return existsValue;
	}
	
	
	public boolean validateExistsUbicationGap(String codeUbication,boolean checkStatusDelete) {
		try {
			Map<String,Object> params = new HashMap<>();
			params.put("code", codeUbication);
			if (checkStatusDelete)
			{
				params.put(ImiConstants.PARAM_CHECK_STATUS_DELETE, ImiConstants.Y);
			}
			List<UbicationGapBDTO> ubications = ubicationGapService.getUbicationsGap(params);
			return !ubications.isEmpty();

		} catch (ImiException e) {
			logger.error(LogsConstants.LOG_KO_VALIDATION, e);
		}
		return false;
	}
	
	public boolean validateExistsLogicalModelUbication(String codeUbication) {
		try {
			List<LogicalModelGapBDTO> logicalModels = logicalModelGapService.getLogicalModelsUbicationGap(codeUbication);
			return !logicalModels.isEmpty();
		} catch (ImiException e) {
			logger.error(LogsConstants.LOG_KO_VALIDATION, e);
		}
		return false;
	}
	
	public boolean validateExistsMaterialResourceGap(String codeRRMM) {
		try {
			List<MaterialResourceGapBDTO> materialResources = materialResourceGapService.getMaterialResourcesGap(codeRRMM);
			return !materialResources.isEmpty();

		} catch (ImiException e) {
			logger.error(LogsConstants.LOG_KO_VALIDATION, e);
		}
		return false;
	}
	public boolean validateExistsLogicalModelGap(String codeMCL, boolean checkStatusDelete) {
		try {
			Map<String,Object> params = new HashMap<>();
			params.put("code", codeMCL);
			if (checkStatusDelete)
			{
				params.put(ImiConstants.PARAM_CHECK_STATUS_DELETE, ImiConstants.Y);
			}
			List<LogicalModelGapBDTO> logicalModels = logicalModelGapService.getLogicalModelsGap(params);
			return !logicalModels.isEmpty();

		} catch (ImiException e) {
			logger.error(LogsConstants.LOG_KO_VALIDATION, e);
		}
		return false;
	}

	public boolean validateExistsLogicalModelPositionUbication(String codeUbication, long position, String codeMCL) {
		try {
			List<LogicalModelGapBDTO> logicalModels = logicalModelGapService.getLogicalModelsPositionUbicationGap(codeUbication, position, codeMCL);
			return !logicalModels.isEmpty();
		} catch (ImiException e) {
			logger.error(LogsConstants.LOG_KO_VALIDATION, e);
		}
		return false;
	}

	public boolean validateExistsPhysicalModelGap(String codeMCF) {
		try {
			List<PhysicalModelGapBDTO> physicalModels = physicalModelGapService.getPhysicalModelsGap(codeMCF);
			return !physicalModels.isEmpty();

		} catch (ImiException e) {
			logger.error(LogsConstants.LOG_KO_VALIDATION, e);
		}
		return false;
	}

	public boolean validateExistsPhysicalModelLogicalModelGap(String codeMCL) {
		try {
			List<PhysicalModelGapBDTO> physicalModels = physicalModelGapService.getPhysicalModelsLogicalModelGap(codeMCL);
			return !physicalModels.isEmpty();
		} catch (ImiException e) {
			logger.error(LogsConstants.LOG_KO_VALIDATION, e);
		}
		return false;
	}
	
	public boolean validateExistsVacantLogicalModelGap(String codeMCL, String codeMCF) {
		try {
			List<LogicalModelGapBDTO> logicalModels = logicalModelGapService.getVacantLogicalModelsGap(codeMCL, codeMCF);
			return !logicalModels.isEmpty();
		} catch (ImiException e) {
			logger.error(LogsConstants.LOG_KO_VALIDATION, e);
		}
		return false;
	}
	
	public boolean validateExistsInstallationGap(String codeInstallation, boolean checkStatusDelete) {
		try {
			Map<String,Object> params = new HashMap<>();
			params.put("code", codeInstallation);
			if (checkStatusDelete)
			{
				params.put(ImiConstants.PARAM_CHECK_STATUS_DELETE, ImiConstants.Y);
			}
			List<InstallationGapBDTO> installations = installationGapService.getInstallationsGap(params);
			return !installations.isEmpty();

		} catch (ImiException e) {
			logger.error(LogsConstants.LOG_KO_VALIDATION, e);
		}
		return false;
	}
	
	public ReturnRDTO validateBrandModelGap(String codeBrand, String codeModel) {
		ReturnRDTO returnRDTO = new ReturnRDTO();
		try {
			List<BrandModelGapBDTO> brandModels = brandModelGapService.getBrandModelsGap(codeBrand);
			if (brandModels.isEmpty())
			{
				returnRDTO.setCode(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription());
				returnRDTO.setMessage(String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
						MessageContansts.MSG_FIELD_BRAND, 
						MessageContansts.MSG_ENTITY_MATERIAL_RESOURCE,
						codeBrand));
			}else{
				returnRDTO.setCode(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription());
				returnRDTO.setMessage(String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
						MessageContansts.MSG_FIELD_MODEL,
						MessageContansts.MSG_ENTITY_MATERIAL_RESOURCE,
						codeModel));
				
				for (BrandModelGapBDTO brandModelGapBDTO : brandModels)
				{
					if (codeModel.equals(brandModelGapBDTO.getModelCode()))
					{
						returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription());
						break;
					}
				}
			}

		} catch (ImiException e) {
			logger.error(LogsConstants.LOG_KO_VALIDATION, e);
		}
		return returnRDTO;
	}

	
	public boolean validateCompatibilityTypeMCLUbication(String codeMCLType, String codeUbication,boolean checkStatusDelete ) {
		try {
			String codeUbicationType = "";
			Map<String,Object> params = new HashMap<>();
			params.put("code", codeUbication);
			if (checkStatusDelete)
			{
				params.put(ImiConstants.PARAM_CHECK_STATUS_DELETE, ImiConstants.Y);
			}
			List<UbicationGapBDTO> ubications = ubicationGapService.getUbicationsGap(params);
			if (!ubications.isEmpty())
			{
				codeUbicationType = ubications.get(0).getUbicationTypeCode();
			}
			List<CompatibilityTypesMCLUbication> compatibilities = compatibilityService.getCompatilibityTypeMCLUbication(codeMCLType, codeUbicationType);
			return !compatibilities.isEmpty();

		} catch (ImiException e) {
			logger.error(LogsConstants.LOG_KO_VALIDATION, e);
		}
		return false;
	}

	public boolean validateCompatibilityLogicalModelsUbication(String codeUbication, String codeTypeUbication) {
		
		boolean hasLogicalModels = false;
		boolean hasCompatibilities = false;
		
		List<String> listCodesTypeMCL = new ArrayList<>();
		List<CompatibilityTypesMCLUbication> compatibilities = new ArrayList<>(); 
		try {
			List<LogicalModelGapBDTO> logicalModels = logicalModelGapService.getLogicalModelsUbicationGap(codeUbication);
			hasLogicalModels = !logicalModels.isEmpty();
			
		
			if (hasLogicalModels)
			{
				compatibilities = compatibilityService.getCompatibleTypesMCLByTypeUbication(codeTypeUbication);
				hasCompatibilities = !compatibilities.isEmpty();
			}
			if (hasLogicalModels && hasCompatibilities)
			{
				
				listCodesTypeMCL = Utils.getListMCLTypes(logicalModels);
				
				for (String codeTypeMCL : listCodesTypeMCL)
				{
					boolean compatibleType = false;
					for (CompatibilityTypesMCLUbication compatibility : compatibilities )
					{
						if (compatibility.getCodeMCLType().equals(codeTypeMCL))
						{
							compatibleType = true;
							break;
						}
					}
					if (!compatibleType) {
						return false;
					}
				}
			}
		} catch (ImiException e) {
			logger.error(LogsConstants.LOG_KO_VALIDATION, e);
		}
		return !hasLogicalModels ||  hasCompatibilities;
	}

	public boolean validateExistsUbicationGapByCodeType(String codeUbication,String codeUbicationType) {
		try {
			List<UbicationGapBDTO> ubications = ubicationGapService.getUbicationsGapByCodeType(codeUbication,codeUbicationType);
			return !ubications.isEmpty();

		} catch (ImiException e) {
			logger.error(LogsConstants.LOG_KO_VALIDATION, e);
		}
		return false;
	}

	public boolean validateCompatibilityMCFTypeSensorByCodeMCF(String codeMCF,String codeSensorType) {
		try {
			List<PhysicalModelGapBDTO> physicalModels = physicalModelGapService.getPhysicalModelsGap(codeMCF);

			String codeMCFType = physicalModels.get(0).getTypeCode();
			List<CompatibilityTypesMCFSensor> compatibilities = compatibilityService.getCompatibilityTypeMCFTypeSensor(codeMCFType, codeSensorType);
			return !compatibilities.isEmpty();
			
		} catch (ImiException e) {
			logger.error(LogsConstants.LOG_KO_VALIDATION, e);
		}
		return false;
	}
	public boolean validateCompatibilityRRMMTypeSensorByCodeRRMM(String codeRRMM,String codeSensorType) {
		try {
			List<MaterialResourceGapBDTO> materialResources = materialResourceGapService.getMaterialResourcesGap(codeRRMM);
			String codeRRMMType = materialResources.get(0).getTypeCode();
			List<CompatibilityTypesRRMMSensor> compatibilities = compatibilityService.getCompatibilityTypeRRMMTypeSensor(codeRRMMType, codeSensorType);
			return !compatibilities.isEmpty();


		} catch (ImiException e) {
			logger.error(LogsConstants.LOG_KO_VALIDATION, e);
		}
		return false;
	}

	public ReturnRDTO validateCompatibilityTypeMCFTypesSensor(String codeMCFType, List<String> codeSensors) {
		ReturnRDTO returnRDTO = new ReturnRDTO();
		returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription());
		try {
			
			List<SensorBDTO> sensors = sensorService.getSensorsByCodes(codeSensors);
			
			List<CompatibilityTypesMCFSensor> compatibilities = compatibilityService.getCompatibleTypesSensorByTypeMCF(codeMCFType);
			
			List<String> sensorTypeCompatibles = new ArrayList<>();
			for (CompatibilityTypesMCFSensor compatibility : compatibilities )
			{
				sensorTypeCompatibles.add(compatibility.getCodeSensorType());
				
			}
			for (SensorBDTO sensor : sensors)
			{
				if (!sensorTypeCompatibles.contains(sensor.getCodeType()))
				{
					returnRDTO.setCode( ReturnEnum.CODE_ERROR_INCOMPATIBILITY.getCodeDescription());
					returnRDTO.setMessage(String.format(MessageContansts.MSG_INCOMPATIBLE_TYPE_SENSOR,
							MessageContansts.MSG_ENTITY_PHYSICAL_MODEL, 
							codeMCFType,
							MessageContansts.MSG_ENTITY_SENSOR,
							sensor.getCodeType(), sensor.getCode()));
					break;
				}
				
			}

		} catch (ImiException e) {
			logger.error(LogsConstants.LOG_KO_VALIDATION, e);
			returnRDTO.setCode( ReturnEnum.CODE_ERROR_GENERIC.getCodeDescription());
			returnRDTO.setMessage(String.format(ReturnEnum.CODE_ERROR_GENERIC.getMessage()));
			
		}
		return returnRDTO;
	}

	public ReturnRDTO validateCompatibilityTypeRRMMTypesSensor(String codeMaterialResourceType, List<String> codeSensors) {
		ReturnRDTO returnRDTO = new ReturnRDTO();
		returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription());
		try {
			
			List<SensorBDTO> sensors = sensorService.getSensorsByCodes(codeSensors);
			
			List<CompatibilityTypesRRMMSensor> compatibilities = compatibilityService.getCompatibleTypesSensorByTypeRRMM(codeMaterialResourceType);
			
			List<String> sensorTypeCompatibles = new ArrayList<>();
			for (CompatibilityTypesRRMMSensor compatibility : compatibilities )
			{
				sensorTypeCompatibles.add(compatibility.getCodeSensorType());
				
			}
			for (SensorBDTO sensor : sensors)
			{
				if (!sensorTypeCompatibles.contains(sensor.getCodeType()))
				{
					returnRDTO.setCode( ReturnEnum.CODE_ERROR_INCOMPATIBILITY.getCodeDescription());
					returnRDTO.setMessage(String.format(MessageContansts.MSG_INCOMPATIBLE_TYPE_SENSOR,
							MessageContansts.MSG_ENTITY_MATERIAL_RESOURCE, 
							codeMaterialResourceType,
							MessageContansts.MSG_ENTITY_SENSOR,
							sensor.getCodeType(), sensor.getCode()));
					break;
				}
			}

		} catch (ImiException e) {
			logger.error(LogsConstants.LOG_KO_VALIDATION, e);
			returnRDTO.setCode( ReturnEnum.CODE_ERROR_GENERIC.getCodeDescription());
			returnRDTO.setMessage(String.format(ReturnEnum.CODE_ERROR_GENERIC.getMessage()));

		}
		return returnRDTO;
	}

	public boolean validateExistsAssociationMCLMCF(String codeUbication, String codeMCL, String codeMCF) {
		try {
			
			List<PhysicalModelGapBDTO> physicalModels = physicalModelGapService.getPhysicalModelsGap(codeUbication, codeMCL,codeMCF);
			return !physicalModels.isEmpty();

		} catch (ImiException e) {
			logger.error(LogsConstants.LOG_KO_VALIDATION, e);
		}
		return false;

	}

	public boolean validateExistsMaterialResourceNoVehicle(String codeContract, String codeRRMM ) {
		try {
			Map<String,Object> params = new HashMap<>();
			params.put("codeRRMM", codeRRMM);
			params.put("codeContract", codeContract);
			
			List<MaterialResourceBDTO> materialResources = rmNoVehiclesService.getMaterialResourcesNoVehicleByCode(params);
			return !materialResources.isEmpty();

		} catch (ImiException e) {
			logger.error(LogsConstants.LOG_KO_VALIDATION, e);
		}
		return false;
	}
	
	public ReturnRDTO validateTypeSubtypeExpenseGap(String codeType, String codeSubtype, String entityName) {
		ReturnRDTO returnRDTO = new ReturnRDTO();
		try {
			List<TypeSubtypeExpenseGapBDTO> typeSubtypesExpense = typeSubtypeExpenseGapService.getTypeSubtypesExpenseGap(codeType);
			if (typeSubtypesExpense.isEmpty())
			{
				returnRDTO.setCode(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription());
				returnRDTO.setMessage(String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
						MessageContansts.MSG_FIELD_TYPE, 
						entityName,
						codeType));
			}else{
				List<String> lstCodesSubtype = new ArrayList<>();
				for (TypeSubtypeExpenseGapBDTO typeSubtypeExpenseGapBDTO : typeSubtypesExpense)
				{
					lstCodesSubtype.add(typeSubtypeExpenseGapBDTO.getSubtypeCode());
					
				}
				if (lstCodesSubtype.contains(codeSubtype))
				{
					returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription());
				}else{
					returnRDTO.setCode(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription());
					returnRDTO.setMessage(String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
							MessageContansts.MSG_FIELD_SUBTYPE,
							entityName,
							codeSubtype));
				}
			}

		} catch (ImiException e) {
			logger.error(LogsConstants.LOG_KO_VALIDATION, e);
		}
		return returnRDTO;
	}

	public ReturnRDTO validateListValueListGap(List<String> fields, Entity entity, String attributeName, String entityName) {

		ReturnRDTO returnRDTO = new ReturnRDTO();
		returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription());
		try {

			List<ValueListGapBDTO> valuesList = valueListGapService.getValueListGap(entity);
			
			
				List<String> codesValueList = new ArrayList<>();	
				for (ValueListGapBDTO value : valuesList) {
					codesValueList.add(value.getCode());
				}
				for (String code : fields)
				{
					if (!codesValueList.contains(code))
					{
						returnRDTO.setCode(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription());
						returnRDTO.setMessage(String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
								attributeName,
								entityName,
								code));
						return returnRDTO;
					}
				}
			

		} catch (ImiException e) {
			logger.error(LogsConstants.LOG_KO_VALIDATION, e);
			returnRDTO.setCode(ReturnEnum.CODE_ERROR_GENERIC.getCodeDescription());
			returnRDTO.setMessage(String.format(ReturnEnum.CODE_ERROR_GENERIC.getMessage()));
			
		}
		return returnRDTO;
	}

	public ReturnRDTO validateExistsListPhysicalModelGap(List<String> listCodesMCF, boolean checkStatusDelete) {
		
		ReturnRDTO returnRDTO = new ReturnRDTO();
		returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription());
		
		try {
			Map<String,Object> params = new HashMap<>();
			params.put("listCodesMCF", listCodesMCF);
			if (checkStatusDelete)
			{
				params.put(ImiConstants.PARAM_CHECK_STATUS_DELETE, ImiConstants.Y);
			}
			
			
			List<PhysicalModelGapBDTO> physicalModelsGap = physicalModelGapService.getPhysicalModelsGap(params);
			
			List<String> listCodesMCFGap = new ArrayList<>();	
			for (PhysicalModelGapBDTO mcfGap : physicalModelsGap) {
				listCodesMCFGap.add(mcfGap.getCode());
			}
			for (String code : listCodesMCF)
			{
				if (!listCodesMCFGap.contains(code))
				{
					returnRDTO.setCode(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription());
					returnRDTO.setMessage(String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
							MessageContansts.MSG_FIELD_CODE_MCF,
							MessageContansts.MSG_ENTITY_ELEMENT_COMMERCE,
							code));
					return returnRDTO;
				}
			}
			

		} catch (ImiException e) {
			logger.error(LogsConstants.LOG_KO_VALIDATION, e);
		}
		return returnRDTO;
	}
	
	public boolean validateExistsCommerceGap(String codeCommerce, boolean checkStatusDelete) {
		try {
			Map<String,Object> params = new HashMap<>();
			params.put("code", codeCommerce);
			if (checkStatusDelete)
			{
				params.put(ImiConstants.PARAM_CHECK_STATUS_DELETE, ImiConstants.Y);
			}
			List<CommerceGapBDTO> commerces = commerceGapService.getCommercesGap(params);
			return !commerces.isEmpty();

		} catch (ImiException e) {
			logger.error(LogsConstants.LOG_KO_VALIDATION, e);
		}
		return false;
	}

	public ReturnRDTO validateExistsAmortizableMaterialResourceGap(String codeRRMM) {
		ReturnRDTO returnRDTO = new ReturnRDTO();
		returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription());
		try {
			List<MaterialResourceGapBDTO> materialResources = materialResourceGapService.getMaterialResourcesGap(codeRRMM);
			if (materialResources.isEmpty())
			{

				returnRDTO.setCode(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription());
				returnRDTO.setMessage(String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(),
						MessageContansts.MSG_ENTITY_MATERIAL_RESOURCE,
						codeRRMM));
				return returnRDTO;
			}else{
				if (!ValidationConstants.VALUE_RRMM_AMORTIZABLE.equals(materialResources.get(0).getAmortizationSubject()))
				{
					returnRDTO.setCode(ReturnEnum.CODE_ERROR_ENTITY_NOT_AMORTIZABLE.getCodeDescription());
					returnRDTO.setMessage(String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_AMORTIZABLE.getMessage(),
							MessageContansts.MSG_ENTITY_MATERIAL_RESOURCE,
							codeRRMM));
				}
			}

		} catch (ImiException e) {
			logger.error(LogsConstants.LOG_KO_VALIDATION, e);
		}
		return returnRDTO;
	}


	public boolean validateExistsItineraryGap(String codeItinerary) {
		try {
			Map<String,Object> params = new HashMap<>();
			params.put("code", codeItinerary);
			List<ItineraryGapBDTO> itineraries = itineraryGapService.getItinerariesGap(params);
			return !itineraries.isEmpty();

		} catch (ImiException e) {
			logger.error(LogsConstants.LOG_KO_VALIDATION, e);
		}
		return false;

	}
	
	public boolean validateExistsEventGap(String codeEvent) {
		try {
			List<EventGapBDTO> events = eventGapService.getEventsGap(codeEvent);
			return !events.isEmpty();

		} catch (ImiException e) {
			logger.error(LogsConstants.LOG_KO_VALIDATION, e);
		}
		return false;

	}
	
	public ReturnRDTO validateCodeItinerary(String codeItinerary) {
		ReturnRDTO returnRDTO = new ReturnRDTO();
		returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription());
		
		boolean validCode = true;
		try {
			String codeTreatment = codeItinerary.substring(ValidationConstants.CODE_ITINERARY_POS_INICIAL_TREATMENT,
					ValidationConstants.CODE_ITINERARY_POS_FINAL_TREATMENT);
			String codeTypeRRMM = codeItinerary.substring(ValidationConstants.CODE_ITINERARY_POS_INICIAL_RRMM_TYPE,
					ValidationConstants.CODE_ITINERARY_POS_FINAL_RRMM_TYPE);
			String codeEnergyType = codeItinerary.substring(ValidationConstants.CODE_ITINERARY_POS_INICIAL_ENERGY_TYPE,
					ValidationConstants.CODE_ITINERARY_POS_FINAL_ENERGY_TYPE);
			String turn = codeItinerary.substring(ValidationConstants.CODE_ITINERARY_POS_INICIAL_TURN,
					ValidationConstants.CODE_ITINERARY_POS_FINAL_TURN);
			
			Map<String,Object> params = new HashMap<>();
			
			params.put("codeTreatment", codeTreatment);
			
			List<ServiceGapBDTO> services = serviceGapService.getServicesGap(params);
			if (services.isEmpty())
			{
				validCode = false;
				returnRDTO.setCode(ReturnEnum.CODE_ERROR_CODE_ITINERARY_INCORRECT.getCodeDescription());
				returnRDTO.setMessage(String.format(ReturnEnum.CODE_ERROR_CODE_ITINERARY_INCORRECT.getMessage(),
						MessageContansts.MSG_FIELD_CODE_TREATMENT,
						codeTreatment));
			}
			if (validCode && !validateValueListGap(codeTypeRRMM, new Entity(ValueListConstants.RRMM_TYPE)))
			{
				validCode = false;
				returnRDTO.setCode(ReturnEnum.CODE_ERROR_CODE_ITINERARY_INCORRECT.getCodeDescription());
				returnRDTO.setMessage(String.format(ReturnEnum.CODE_ERROR_CODE_ITINERARY_INCORRECT.getMessage(),
						MessageContansts.MSG_FIELD_RRMM_TYPE,
						codeTypeRRMM));
			}
			if (validCode && !validateValueListGap(codeEnergyType, new Entity(ValueListConstants.MOTOR_ENERGY_TYPE)))
			{
				validCode = false;
				returnRDTO.setCode(ReturnEnum.CODE_ERROR_CODE_ITINERARY_INCORRECT.getCodeDescription());
				returnRDTO.setMessage(String.format(ReturnEnum.CODE_ERROR_CODE_ITINERARY_INCORRECT.getMessage(),
						MessageContansts.MSG_FIELD_MOTOR_ENERGY_TYPE,
						codeEnergyType));
			}
			if (validCode && !validateValueListGap(turn, new Entity(ValueListConstants.TURN)))
			{
				returnRDTO.setCode(ReturnEnum.CODE_ERROR_CODE_ITINERARY_INCORRECT.getCodeDescription());
				returnRDTO.setMessage(String.format(ReturnEnum.CODE_ERROR_CODE_ITINERARY_INCORRECT.getMessage(),
						MessageContansts.MSG_FIELD_TURN,
						turn));
			}

		} catch (Exception e) {
			logger.error(LogsConstants.LOG_KO_VALIDATION, e);
			returnRDTO.setCode(ReturnEnum.CODE_ERROR_GENERIC_FRONTAL.getCodeDescription());
			returnRDTO.setMessage(String.format(ReturnEnum.CODE_ERROR_GENERIC_FRONTAL.getMessage()));
		}
		return returnRDTO;
	}
	
	public boolean validateExistsPlanificatedItinerary(String codeItinerary, String dateExecution) {
		try {
			Map<String,Object> params = new HashMap<>();
			params.put("codeItinerary", codeItinerary);
			params.put("dateExecution", dateExecution);
			List<PlanificationGapBDTO> planifications = planificationGapService.getPlanificationsGap(params);
			return !planifications.isEmpty();

		} catch (ImiException e) {
			logger.error(LogsConstants.LOG_KO_VALIDATION, e);
		}
		return false;
	}

	public boolean validateExistsGroupEventType(String codeGroup, String codeEventType) {
		try {
			Map<String,Object> params = new HashMap<>();
			params.put("codeGroup", codeGroup);
			params.put("codeEventType", codeEventType);
			List<GroupEventGapBDTO> groupEventsGap = groupEventGapService.getGroupEventsGap(params);
			return !groupEventsGap.isEmpty();

		} catch (ImiException e) {
			logger.error(LogsConstants.LOG_KO_VALIDATION, e);
		}
		return false;
	}

	public boolean validateExistsPlantWeighingGap(String codePlantWeighing) {
		try {
			List<PlantWeighingGapBDTO> plantWeighings = plantWeighingGapService.getPlantWeighingsGap(codePlantWeighing);
			return !plantWeighings.isEmpty();
		} catch (ImiException e) {
			logger.error(LogsConstants.LOG_KO_VALIDATION, e);
		}
		return false;
	}
	
	public boolean validateExistsTimeClockSummary(String codeContract,String codeInstallation, String date ) {
		
		try {
			Map<String,Object> params = new HashMap<>();
			params.put("codeContract", codeContract);
			if (codeInstallation!=null)
			{
				params.put("codeInstallation", codeInstallation);
			}
			params.put("date", date);
			
			List<TimeClockSummaryBDTO> timeClockSummaries = timeClockSummaryService.getTimeClockSummaries(params);
			return !timeClockSummaries.isEmpty();
		} catch (ImiException e) {
			logger.error(LogsConstants.LOG_KO_VALIDATION, e);
		}
		return false;
	}

	public boolean validateExistsDocumentarySupport(String codeContract,String codeDocumentarySupport,String codeEntity, boolean checkStatusDelete) {
		
		try {
			Map<String,Object> params = new HashMap<>();
			params.put("codeContract", codeContract);
			params.put("codeContractDocument", codeDocumentarySupport);
			params.put("codeEntity", codeEntity);
			if (checkStatusDelete)
			{
				params.put(ImiConstants.PARAM_CHECK_STATUS_DELETE, ImiConstants.Y);
			}
			List<DocumentarySupportBDTO> documentarySupports = documentarySupportService.getDocumentarySupports(params);
			return !documentarySupports.isEmpty();
		} catch (ImiException e) {
			logger.error(LogsConstants.LOG_KO_VALIDATION, e);
		}
		return false;
	}

	public boolean validateExistsActuation(String codeContract,String codeActuation, String codeEntity, boolean checkStatusDelete) {
		
		try {
			Map<String,Object> params = new HashMap<>();
			params.put("codeContract", codeContract);
			params.put("code", codeActuation);
			params.put("codeEntity", codeEntity);
			if (checkStatusDelete)
			{
				params.put(ImiConstants.PARAM_CHECK_STATUS_DELETE, ImiConstants.Y);
			}			
			List<ActuationBDTO> actuations = actuationService.getActuations(params);
			return !actuations.isEmpty();
		} catch (ImiException e) {
			logger.error(LogsConstants.LOG_KO_VALIDATION, e);
		}
		return false;
	}

	public boolean validateExistsGroupFurniture(String codeGroup) {
		try {
			Map<String,Object> params = new HashMap<>();
			params.put("codeGroup", codeGroup);
			
			List<GroupFurnitureGapBDTO> groupsFurnitureGap = groupFurnitureGapService.getGroupsFurnitureGap(params);
			return !groupsFurnitureGap.isEmpty();

		} catch (ImiException e) {
			logger.error(LogsConstants.LOG_KO_VALIDATION, e);
		}
		return false;
	}

	public boolean validateExistsCodeTypeExtraordinaryConceptGap(String codeTypeExtraordinaryConcept) {
		boolean existsValue = false;
		try {
			
			List<ValueListGapBDTO> codes = conceptGapService.getCodesTypeExtraordinaryConceptGap();
			if (!codes.isEmpty()) {
				List<String> codesValueListGap = new ArrayList<>();
				for (ValueListGapBDTO value : codes) {
					codesValueListGap.add(value.getCode());
				}
				if (codesValueListGap.contains(codeTypeExtraordinaryConcept))
				{
					existsValue = true;
				}
			}

		} catch (ImiException e) {
			logger.error(LogsConstants.LOG_KO_VALIDATION, e);
		}
		return existsValue;
	}
	
	public boolean validateExistsCodeSubtypeConceptInstallationGap(String codeSubtypeConceptInstallation) {
		boolean existsValue = false;
		try {
			
			List<ValueListGapBDTO> codes = conceptGapService.getCodesSubtypeConceptInstallationGap();
			if (!codes.isEmpty()) {
				List<String> codesValueListGap = new ArrayList<>();
				for (ValueListGapBDTO value : codes) {
					codesValueListGap.add(value.getCode());
				}
				if (codesValueListGap.contains(codeSubtypeConceptInstallation))
				{
					existsValue = true;
				}
			}

		} catch (ImiException e) {
			logger.error(LogsConstants.LOG_KO_VALIDATION, e);
		}
		return existsValue;
	}
	
	public ReturnRDTO validateContract(BaseTransactionIdRDTO rdto) {
		ReturnRDTO returnRDTO = new ReturnRDTO();
		returnRDTO.setCode(ReturnEnum.CODE_ERROR_CONTRACT_CODE_INCORRECT.getCodeDescription());
		returnRDTO.setMessage(ReturnEnum.CODE_ERROR_CONTRACT_CODE_INCORRECT.getMessage());
		try {
			Map<String,String> params = new HashMap<>();
			params.put("clientId", rdto.getClientId());	
			List<ContractBDTO> contracts = contractService.getContract(params);
			
			if(contracts != null && !contracts.isEmpty()) {
				ContractBDTO contractBDTO = contracts.get(0);
				if(rdto.getCodeContract() != null && !rdto.getCodeContract().isEmpty() && (ImiConstants.ROL_ADMIN.equals(contractBDTO.getRol()) || rdto.getCodeContract().equals(contractBDTO.getCode()))) {
					returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription());
					returnRDTO.setMessage(ReturnEnum.CODE_SUCCESS_VALIDATE.getMessage());
				}
			}
		} catch (ImiException e) {
			logger.error(LogsConstants.LOG_KO_VALIDATION, e);
		}
		return returnRDTO;
	}
	
	public boolean validateCoherenceServiceItinerary(String codeItinerary,String codeService) {
		ReturnRDTO returnRDTO = new ReturnRDTO();
		returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription());
		
		boolean validCode = false;
		try {
			String codeTreatment = codeItinerary.substring(ValidationConstants.CODE_ITINERARY_POS_INICIAL_TREATMENT,
					ValidationConstants.CODE_ITINERARY_POS_FINAL_TREATMENT);
			validCode = codeTreatment.startsWith(codeService);
			
		} catch (Exception e) {
			logger.error(LogsConstants.LOG_KO_VALIDATION, e);
			validCode = false;
		}
		return validCode;
	}


	public boolean validateExistsCodeTypeIvaGap(String codeTypeIva) {
		boolean existsValue = false;
		try {
			
			List<ValueListGapBDTO> codes = conceptGapService.getCodesTypeIvaGap();
			if (!codes.isEmpty()) {
				List<String> codesValueListGap = new ArrayList<>();
				for (ValueListGapBDTO value : codes) {
					codesValueListGap.add(value.getCode());
				}
				if (codesValueListGap.contains(codeTypeIva))
				{
					existsValue = true;
				}
			}

		} catch (ImiException e) {
			logger.error(LogsConstants.LOG_KO_VALIDATION, e);
		}
		return existsValue;
	}


	public boolean validateExistsCodeConceptCertificationGap(String codeCertificationConcept) {
		boolean existsValue = false;
		try {
			
			List<ValueListGapBDTO> codes = conceptGapService.getCodesConceptCertificationGap();
			if (!codes.isEmpty()) {
				List<String> codesValueListGap = new ArrayList<>();
				for (ValueListGapBDTO value : codes) {
					codesValueListGap.add(value.getCode());
				}
				if (codesValueListGap.contains(codeCertificationConcept))
				{
					existsValue = true;
				}
			}

		} catch (ImiException e) {
			logger.error(LogsConstants.LOG_KO_VALIDATION, e);
		}
		return existsValue;
	}


	public boolean validateExistsSensor(String codeSensor, String codeContract, boolean checkStatusDelete) {
		ReturnRDTO returnRDTO = new ReturnRDTO();
		returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription());
		
		try {
			Map<String,Object> params = new HashMap<>();
			params.put("codeContract", codeContract);
			params.put("code", codeSensor);
			if (checkStatusDelete)
			{
				params.put(ImiConstants.PARAM_CHECK_STATUS_DELETE, ImiConstants.Y);
			}	
			List<SensorBDTO> sensors = sensorService.getSensorsByParams(params);
			return !sensors.isEmpty();
		} catch (ImiException e) {
			logger.error(LogsConstants.LOG_KO_VALIDATION, e);
		}
		return false;
	}


	public boolean validateExistsAssociationMCFSensor(String code, String codeMCF, String codeContract) {
		ReturnRDTO returnRDTO = new ReturnRDTO();
		returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription());
		
		try {
			Map<String,Object> params = new HashMap<>();
			params.put("codeContract", codeContract);
			params.put("codeMCF", codeMCF);
			params.put("code", code);
				
			List<SensorBDTO> sensors = sensorService.getSensorsByCodeMCF(params);
			return !sensors.isEmpty();
		} catch (ImiException e) {
			logger.error(LogsConstants.LOG_KO_VALIDATION, e);
		}
		return false;
	}


	public boolean validateExistsAssociationRRMMSensor(String code, String codeRRMM, String codeContract) {
		ReturnRDTO returnRDTO = new ReturnRDTO();
		returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription());
		
		try {
			Map<String,Object> params = new HashMap<>();
			params.put("codeContract", codeContract);
			params.put("codeRRMM", codeRRMM);
			params.put("code", code);
				
			List<SensorBDTO> sensors = sensorService.getSensorsByCodeRRMM(params);
			return !sensors.isEmpty();
		} catch (ImiException e) {
			logger.error(LogsConstants.LOG_KO_VALIDATION, e);
		}
		return false;
	}
	
	public boolean validateHasAssociationsSensor(String code, String codeContract) {
		ReturnRDTO returnRDTO = new ReturnRDTO();
		returnRDTO.setCode(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription());
		
		try {
			Map<String,Object> params = new HashMap<>();
			params.put("codeContract", codeContract);
			params.put("code", code);
				
			List<SensorBDTO> sensorsMCF = sensorService.getSensorsByCodeMCF(params);
			List<SensorBDTO> sensorsRRMM = sensorService.getSensorsByCodeRRMM(params);
			return !sensorsMCF.isEmpty() || !sensorsRRMM.isEmpty() ;
		} catch (ImiException e) {
			logger.error(LogsConstants.LOG_KO_VALIDATION, e);
		}
		return false;
	}


}