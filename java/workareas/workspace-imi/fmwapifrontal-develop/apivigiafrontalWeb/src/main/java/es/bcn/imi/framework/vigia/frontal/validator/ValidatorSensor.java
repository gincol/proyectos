package es.bcn.imi.framework.vigia.frontal.validator;

import org.apache.commons.lang3.StringUtils;
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
import es.bcn.vigia.fmw.libcommons.valueobject.Type;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.SensorRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.enums.ReturnEnum;

@Lazy
@Service(ServicesConstants.SRV_FRONT_VALIDATE_SENSOR_FMW)
public class ValidatorSensor extends ValidatorContract {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ServicesConstants.SRV_FRONT_VALIDATE_FMW)
	private ValidatorUtils validator;

	private String isCode;

	private String isMessage;

	private ReturnRDTO returnRDTO;

	public ReturnRDTO validateInsert(SensorRDTO sensorRDTO) {

		boolean isRequestValid = true;
		
		returnRDTO = new ReturnRDTO();

		logger.info(LogsConstants.LOG_START);

		returnRDTO = validateInsertUpdate(sensorRDTO);
		
		if (!returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
			isRequestValid = false;
		}
		if (isRequestValid && validator.validateExistsSensor(sensorRDTO.getCode(),sensorRDTO.getCodeContract(),false)){
			isRequestValid = false;
			isCode 		= ReturnEnum.CODE_ERROR_DUPLICATE_ENTITY.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_DUPLICATE_ENTITY.getMessage(), 
										MessageContansts.MSG_ENTITY_SENSOR,
										sensorRDTO.getCode());
		}

		
	
		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);
		
		logger.info(LogsConstants.LOG_END);
		return returnRDTO;
	}

	public ReturnRDTO validateUpdate(SensorRDTO sensorRDTO) {

		boolean isRequestValid = true;
		
		returnRDTO = new ReturnRDTO();	

		logger.info(LogsConstants.LOG_START);

		returnRDTO = validateInsertUpdate(sensorRDTO);
		
		if (!returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
			isRequestValid = false;
		}
		if (isRequestValid && !validator.validateExistsSensor(sensorRDTO.getCode(),sensorRDTO.getCodeContract(),true)){
			isRequestValid = false;
			isCode 		= ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(), 
										MessageContansts.MSG_ENTITY_SENSOR,
										sensorRDTO.getCode());
		}
		if (isRequestValid)
		{
			if (!StringUtils.isEmpty(sensorRDTO.getCodeRRMM()))
			{ 
				if (!validator.validateExistsAssociationRRMMSensor(sensorRDTO.getCode(),sensorRDTO.getCodeRRMM(),sensorRDTO.getCodeContract())){
					isRequestValid = false;
					isCode 		= ReturnEnum.CODE_SENSOR_NOT_ASSOCIATED.getCodeDescription();
					isMessage	= String.format(ReturnEnum.CODE_SENSOR_NOT_ASSOCIATED.getMessage(), 
												MessageContansts.MSG_ENTITY_SENSOR,
												sensorRDTO.getCode(),
												MessageContansts.MSG_ENTITY_MATERIAL_RESOURCE,
												sensorRDTO.getCodeRRMM());
				}
				
			}
			if (!StringUtils.isEmpty(sensorRDTO.getCodeMCF()))
			{ 
				if (!validator.validateExistsAssociationMCFSensor(sensorRDTO.getCode(),sensorRDTO.getCodeMCF(),sensorRDTO.getCodeContract())){
					isRequestValid = false;
					isCode 		= ReturnEnum.CODE_SENSOR_NOT_ASSOCIATED.getCodeDescription();
					isMessage	= String.format(ReturnEnum.CODE_SENSOR_NOT_ASSOCIATED.getMessage(), 
												MessageContansts.MSG_ENTITY_SENSOR,
												sensorRDTO.getCode(),
												MessageContansts.MSG_ENTITY_PHYSICAL_MODEL,
												sensorRDTO.getCodeMCF());
				}
				
			}
			if (StringUtils.isEmpty(sensorRDTO.getCodeMCF()) && StringUtils.isEmpty(sensorRDTO.getCodeRRMM()))
			{
				if (validator.validateHasAssociationsSensor(sensorRDTO.getCode(),sensorRDTO.getCodeContract())){
					isRequestValid = false;
					isCode 		= ReturnEnum.CODE_SENSOR_ASSOCIATED.getCodeDescription();
					isMessage	= String.format(ReturnEnum.CODE_SENSOR_ASSOCIATED.getMessage(),
												MessageContansts.MSG_ENTITY_SENSOR,
												sensorRDTO.getCode());
				}


			}
			
		}
		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);
		
		logger.info(LogsConstants.LOG_END);

		return returnRDTO;
	}
	
	

	public ReturnRDTO validateInsertUpdate(SensorRDTO sensorRDTO) {
		
		returnRDTO = new ReturnRDTO();

		boolean isRequestValid = true;
		
		isCode = ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription();
		isMessage = ReturnEnum.CODE_SUCCESS_VALIDATE.getMessage();
		
		if (!validator.validateValueList(sensorRDTO.getCodeType(),
				new Type(ValueListConstants.CODE_TYPE_SENSOR))) {
			isRequestValid = false;
			isCode = ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
					MessageContansts.MSG_FIELD_TYPE,
					MessageContansts.MSG_ENTITY_SENSOR, sensorRDTO.getCodeType());
		}

		
		if (isRequestValid)
		{
			if (!StringUtils.isEmpty(sensorRDTO.getCodeRRMM()))
			{ 
				if (!validator.validateExistsMaterialResourceGap(sensorRDTO.getCodeRRMM())){
					isRequestValid = false;
					isCode 		= ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription();
					isMessage	= String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(), 
												MessageContansts.MSG_ENTITY_MATERIAL_RESOURCE,
												sensorRDTO.getCodeRRMM());
				}
				if (isRequestValid && !validator.validateCompatibilityRRMMTypeSensorByCodeRRMM(sensorRDTO.getCodeRRMM(), sensorRDTO.getCodeType()))
				{
					isRequestValid = false;
					isCode 		= ReturnEnum.CODE_ERROR_INCOMPATIBILITY.getCodeDescription();
					isMessage	= String.format(ReturnEnum.CODE_ERROR_INCOMPATIBILITY.getMessage(),
							MessageContansts.MSG_ENTITY_MATERIAL_RESOURCE, 
							sensorRDTO.getCodeRRMM(),
							MessageContansts.MSG_ENTITY_SENSOR,
							sensorRDTO.getCodeType());
				
				}
			}
			if (!StringUtils.isEmpty(sensorRDTO.getCodeMCF()))
			{
				if (!validator.validateExistsPhysicalModelGap(sensorRDTO.getCodeMCF())){
					isRequestValid = false;
					isCode 		= ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription();
					isMessage	= String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(), 
												MessageContansts.MSG_ENTITY_PHYSICAL_MODEL,
												sensorRDTO.getCodeMCF());
				}

				if (isRequestValid && !validator.validateCompatibilityMCFTypeSensorByCodeMCF(sensorRDTO.getCodeMCF(), sensorRDTO.getCodeType()))
				{
					isRequestValid = false;
					isCode 		= ReturnEnum.CODE_ERROR_INCOMPATIBILITY.getCodeDescription();
					isMessage	= String.format(ReturnEnum.CODE_ERROR_INCOMPATIBILITY.getMessage(),
							MessageContansts.MSG_ENTITY_PHYSICAL_MODEL, 
							sensorRDTO.getCodeMCF(),
							MessageContansts.MSG_ENTITY_SENSOR,
							sensorRDTO.getCodeType());
				
				}

			}
		}
			
		
		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);

		return returnRDTO;
	}

	public ReturnRDTO validateDelete(SensorRDTO sensorRDTO) {
		
				return validateUpdate(sensorRDTO);
	}





}
