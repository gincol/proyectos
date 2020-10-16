package es.bcn.imi.framework.vigia.frontal.validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.MessageContansts;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.constants.ValueListConstants;
import es.bcn.vigia.fmw.libcommons.valueobject.Entity;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.MaterialResourceRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.Utils;
import es.bcn.vigia.fmw.libutils.enums.ReturnEnum;

@Lazy
@Service(ServicesConstants.SRV_FRONT_VALIDATE_NO_VEHICLE_FMW)
public class ValidatorNoVehicle {
	
	private final Log logger = LogFactory.getLog(this.getClass());
	
	private String isCode;
	
	private String isMessage;
	
	private ReturnRDTO returnRDTO;
	
	@Autowired
	@Qualifier(ServicesConstants.SRV_FRONT_VALIDATE_FMW)
	private ValidatorUtils validator;
	
	public ReturnRDTO validateInsert(MaterialResourceRDTO materialResourceRDTO) {
		
		
		logger.info(LogsConstants.LOG_START);
		
		boolean isRequestValid = true;
		
		returnRDTO = validateInsertUpdate(materialResourceRDTO);
		
		
		if (!returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
			isRequestValid = false;
		}

		if (isRequestValid && validator.validateExistsMaterialResourceGap(materialResourceRDTO.getCode())) {
			isRequestValid = false;
			isCode 		= ReturnEnum.CODE_ERROR_DUPLICATE_ENTITY.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_DUPLICATE_ENTITY.getMessage(),
						MessageContansts.MSG_ENTITY_MATERIAL_RESOURCE,
						materialResourceRDTO.getCode());
		}
		if (isRequestValid && validator.validateExistsMaterialResourceNoVehicle(materialResourceRDTO.getCodeContract(),materialResourceRDTO.getCode())) {
			isCode 		= ReturnEnum.CODE_ERROR_DUPLICATE_ENTITY.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_DUPLICATE_ENTITY.getMessage(),
						MessageContansts.MSG_ENTITY_MATERIAL_RESOURCE,
						materialResourceRDTO.getCode());
		}
		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);
		
		logger.info(LogsConstants.LOG_END);

		return returnRDTO;
	}
	
	public ReturnRDTO validateUpdate(MaterialResourceRDTO materialResourceRDTO) {
		
		logger.info(LogsConstants.LOG_START);
		
		boolean isRequestValid = true;
		
		returnRDTO = validateInsertUpdate(materialResourceRDTO);
		
		if (!returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
			isRequestValid = false;
		}

		if (isRequestValid && !validator.validateExistsMaterialResourceNoVehicle(materialResourceRDTO.getCodeContract(),materialResourceRDTO.getCode())) {
			
			isCode 		= ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(),
						MessageContansts.MSG_ENTITY_MATERIAL_RESOURCE_NO_VEHICLE,
						materialResourceRDTO.getCode());
		}
		
		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);
		logger.info(LogsConstants.LOG_END);
		
		return returnRDTO;
	}
	
	public ReturnRDTO validateInsertUpdate(MaterialResourceRDTO materialResourceRDTO){
		
		boolean isRequestValid = true;
		
		returnRDTO = new ReturnRDTO();		
		
		isCode = ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription();
		isMessage = ReturnEnum.CODE_SUCCESS_VALIDATE.getMessage();
		
		returnRDTO = validator.validateSensorsContract(materialResourceRDTO.getCodeContract(),
				materialResourceRDTO.getSensorsRDTO()); 
		if (!returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
			isRequestValid = false;
			isCode = returnRDTO.getCode();
			isMessage = returnRDTO.getMessage();
		}
		//Tipus RRMM
		if (isRequestValid && !validator.validateValueListGap(materialResourceRDTO.getCodeType(),
				new Entity(ValueListConstants.RRMM_TYPE))) {
			isRequestValid = false;
			isCode 		= ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
					MessageContansts.MSG_FIELD_TYPE, 
					MessageContansts.MSG_ENTITY_MATERIAL_RESOURCE,
					materialResourceRDTO.getCodeType());
		}
		//compatibiitat sensor - rrmm
		if (isRequestValid)
		{
			returnRDTO =  validator.validateCompatibilityTypeRRMMTypesSensor(materialResourceRDTO.getCodeType(), Utils.getListSensorCodes(materialResourceRDTO.getSensorsRDTO())); 
			if (!returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
				isRequestValid = false;
				isCode = returnRDTO.getCode();
				isMessage = returnRDTO.getMessage();
			}
		}
		//Validaciones contra GAP
		//estat
		if (isRequestValid && !validator.validateValueListGap(materialResourceRDTO.getCodeState(),
				new Entity(ValueListConstants.RRMM_STATUS))) {
			isRequestValid = false;
			isCode 		= ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(), 
						MessageContansts.MSG_FIELD_STATUS,
						MessageContansts.MSG_ENTITY_MATERIAL_RESOURCE,
						materialResourceRDTO.getCodeState());
		}
		
		//Tipus Titularitat
		if (isRequestValid && !validator.validateValueListGap(materialResourceRDTO.getCodeTypeTitularity(),
				new Entity(ValueListConstants.TITULARITY_TYPE))) {
			isRequestValid = false;
			isCode 		= ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
					MessageContansts.MSG_FIELD_CODE_TYPE_TITULARITY,
					MessageContansts.MSG_ENTITY_MATERIAL_RESOURCE, 
					materialResourceRDTO.getCodeTypeTitularity());
		}
		//codigo instalación
		if (isRequestValid && !validator.validateExistsInstallationGap(materialResourceRDTO.getCodeInstallation(),true)){
		
				isCode 		= ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
				isMessage	= String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
						MessageContansts.MSG_FIELD_CODE_INSTALLATION,
						MessageContansts.MSG_ENTITY_MATERIAL_RESOURCE, 
						materialResourceRDTO.getCodeInstallation());
		}

		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);
			
		return returnRDTO;
	}

	public ReturnRDTO validateDelete(MaterialResourceRDTO materialResourceRDTO) {
		
		logger.info(LogsConstants.LOG_START);
		
		boolean isRequestValid = true;
		
		returnRDTO = validateInsertUpdate(materialResourceRDTO);
		
		if (!returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
			isRequestValid = false;
		}

		
		if (isRequestValid && !validator.validateExistsMaterialResourceNoVehicle(materialResourceRDTO.getCodeContract(),materialResourceRDTO.getCode())) {
			
			isCode 		= ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(),
						MessageContansts.MSG_ENTITY_MATERIAL_RESOURCE_NO_VEHICLE,
						materialResourceRDTO.getCode());
		}
		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);
		
		logger.info(LogsConstants.LOG_END);

		return returnRDTO;
	}	
}