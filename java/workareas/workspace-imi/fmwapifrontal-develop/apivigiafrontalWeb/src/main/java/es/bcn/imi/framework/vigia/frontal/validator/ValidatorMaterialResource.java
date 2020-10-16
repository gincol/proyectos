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
import es.bcn.vigia.fmw.libcommons.valueobject.Type;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.ActuationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.DocumentarySupportRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.MaterialResourceRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterDocumentarySupportRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.Utils;
import es.bcn.vigia.fmw.libutils.enums.ReturnEnum;

@Lazy
@Service(ServicesConstants.SRV_FRONT_VALIDATE_MATERIAL_RESOURCE_FMW)
public class ValidatorMaterialResource extends ValidatorContract {
	
	private final Log logger = LogFactory.getLog(this.getClass());
	
	private String isCode;
	
	private String isMessage;
	
	private ReturnRDTO returnRDTO;
	
	@Autowired
	@Qualifier(ServicesConstants.SRV_FRONT_VALIDATE_FMW)
	private ValidatorUtils validator;
	
	@Autowired
	@Qualifier(ServicesConstants.SRV_FRONT_VALIDATE_NO_VEHICLE_FMW)
	private ValidatorNoVehicle validatorNoVehicle;
	
	@Autowired
	@Qualifier(ServicesConstants.SRV_FRONT_VALIDATE_DOCUMENTAY_SUPPORT_FMW)
	private ValidatorDocumentarySupport validatorDocumentarySupport;

	@Autowired
	@Qualifier(ServicesConstants.SRV_FRONT_VALIDATE_ACTUATION_FMW)
	private ValidatorActuation validatorActuation;


	public ReturnRDTO validateInsert(MaterialResourceRDTO materialResourceRDTO) {
		
		
		logger.info(LogsConstants.LOG_START);
		
		boolean isRequestValid = true;
		if (!Utils.validateMandatoryField(materialResourceRDTO.getRegistration()))
		{
			return validatorNoVehicle.validateInsert(materialResourceRDTO);
		}
		
		returnRDTO = validateInsertUpdate(materialResourceRDTO);
		
		
		if (!returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
			isRequestValid = false;
		}

		if (isRequestValid && validator.validateExistsMaterialResourceGap(materialResourceRDTO.getCode())) {
			
			isCode 		= ReturnEnum.CODE_ERROR_DUPLICATE_ENTITY.getCodeDescription();
			isMessage	= String.format(MessageContansts.MSG_DUPLICATE_ENTITY,
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
		
		if (!Utils.validateMandatoryField(materialResourceRDTO.getRegistration()))
		{
			return validatorNoVehicle.validateUpdate(materialResourceRDTO);
		}
		
		boolean isRequestValid = true;
		
		returnRDTO = validateInsertUpdate(materialResourceRDTO);
		
		if (!returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
			isRequestValid = false;
		}

		if (isRequestValid && !validator.validateExistsMaterialResourceGap(materialResourceRDTO.getCode())) {
			
			isCode 		= ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(),
						MessageContansts.MSG_ENTITY_MATERIAL_RESOURCE,
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
		if (isRequestValid)
		{
			returnRDTO = validator.validateBrandModelGap(materialResourceRDTO.getCodeBrand(), materialResourceRDTO.getCodeModel()); 
				
			if (!returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
				isRequestValid = false;
				isCode = returnRDTO.getCode();
				isMessage = returnRDTO.getMessage();
			}
		}
		
		if (isRequestValid && ! validator.validateValueList(materialResourceRDTO.getEuroStandard(), new Type(ValueListConstants.EURO_STANDARD ))) {
			isRequestValid = false;
			
			isCode 		= ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(), 
										MessageContansts.MSG_FIELD_EURO_STANDARD,
										MessageContansts.MSG_ENTITY_MATERIAL_RESOURCE,
										materialResourceRDTO.getEuroStandard());
		}
		if (isRequestValid && !validator.validateValueList(materialResourceRDTO.getTypeEmissions(), new Type(ValueListConstants.EMISSION_TYPE ))) {
			isRequestValid = false;
			isCode 		= ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
										MessageContansts.MSG_FIELD_TYPE_EMISSIONS, 
										MessageContansts.MSG_ENTITY_MATERIAL_RESOURCE,
										materialResourceRDTO.getTypeEmissions());
		}
		//Validaciones contra GAP
		//estat
		if (isRequestValid && !validator.validateValueListGap(materialResourceRDTO.getCodeState(),
				new Entity(ValueListConstants.RRMM_STATUS))) {
			isRequestValid = false;
			isCode = ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
					MessageContansts.MSG_FIELD_STATUS,
					MessageContansts.MSG_ENTITY_MATERIAL_RESOURCE, materialResourceRDTO.getCodeState());
		}
		//Tipus Energia Motriu
		if (isRequestValid && !validator.validateValueListGap(materialResourceRDTO.getCodeMotorEnergyType(),
				new Entity(ValueListConstants.MOTOR_ENERGY_TYPE))) {
			isRequestValid = false;
			isCode = ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
					MessageContansts.MSG_FIELD_MOTOR_ENERGY_TYPE,
					MessageContansts.MSG_ENTITY_MATERIAL_RESOURCE, materialResourceRDTO.getCodeMotorEnergyType());
		}
		//Tipus RRMM
		if (isRequestValid && !validator.validateValueListGap(materialResourceRDTO.getCodeType(),
				new Entity(ValueListConstants.RRMM_TYPE))) {
			isRequestValid = false;
			isCode = ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
					MessageContansts.MSG_FIELD_TYPE,
					MessageContansts.MSG_ENTITY_MATERIAL_RESOURCE, materialResourceRDTO.getCodeType());
		}
		
		//Tipus Titularitat
		if (isRequestValid && !validator.validateValueListGap(materialResourceRDTO.getCodeTypeTitularity(),
				new Entity(ValueListConstants.TITULARITY_TYPE))) {
			isRequestValid = false;
			isCode = ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
					MessageContansts.MSG_FIELD_CODE_TYPE_TITULARITY,
					MessageContansts.MSG_ENTITY_MATERIAL_RESOURCE, materialResourceRDTO.getCodeTypeTitularity());
		}
		//codigo instalación
		if (isRequestValid && !validator.validateExistsInstallationGap(materialResourceRDTO.getCodeInstallation(),true)){
		
			isCode 		= ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
					MessageContansts.MSG_ENTITY_INSTALLATION,
					MessageContansts.MSG_ENTITY_MATERIAL_RESOURCE, 
					materialResourceRDTO.getCodeInstallation());
			
		}

		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);
		
		return returnRDTO;
	}

	public ReturnRDTO validateDelete(MaterialResourceRDTO materialResourceRDTO) {
		
		logger.info(LogsConstants.LOG_START);
		
		if (!Utils.validateMandatoryField(materialResourceRDTO.getRegistration()))
		{
			return validatorNoVehicle.validateDelete(materialResourceRDTO);
		}
		return validateUpdate(materialResourceRDTO);
	}
	
	public ReturnRDTO validateInsertExpense(MaterialResourceRDTO materialResourceRDTO) {
		logger.info(LogsConstants.LOG_START);
		
		boolean isRequestValid = true;
		
		returnRDTO = new ReturnRDTO();
		
		isCode = ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription();
		isMessage = ReturnEnum.CODE_SUCCESS_VALIDATE.getMessage();


		
		if (!validator.validateExistsMaterialResourceGap(materialResourceRDTO.getCode())){
			isRequestValid = false;
			isCode 		= ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(), 
					MessageContansts.MSG_ENTITY_MATERIAL_RESOURCE,
					materialResourceRDTO.getCode());
		}
		
		if (isRequestValid)
		{
			returnRDTO = validator.validateTypeSubtypeExpenseGap(materialResourceRDTO.getExpenseRDTO().getCodeType(), materialResourceRDTO.getExpenseRDTO().getCodeSubType(),
					MessageContansts.MSG_ENTITY_APPORTIONMENT_RRMM); 
				
			if (!returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
				isCode = returnRDTO.getCode();
				isMessage = returnRDTO.getMessage();
			}
		}

		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);
		
		logger.info(LogsConstants.LOG_END);
		return returnRDTO;
	}

	public ReturnRDTO validateInsertApportionment(MaterialResourceRDTO materialResourceRDTO) {
		logger.info(LogsConstants.LOG_START);
		
		returnRDTO = new ReturnRDTO();

		boolean isRequestValid = true;
		isCode = ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription();
		isMessage = ReturnEnum.CODE_SUCCESS_VALIDATE.getMessage();

		
		returnRDTO = validator.validateExistsAmortizableMaterialResourceGap(materialResourceRDTO.getCode()); 
				 
			
		if (!returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
			isRequestValid = false;
			isCode = returnRDTO.getCode();
			isMessage = returnRDTO.getMessage();
		}
		
		if (isRequestValid)
		{
			returnRDTO = validator.validateListValueListGap(Utils.getListCodeGroupServices(materialResourceRDTO.getLstApportionmentRDTO()), 
					new Entity(ValueListConstants.GROUP_SERVICE), 
					MessageContansts.MSG_FIELD_SERVICE_GROUP_CODE, 
					MessageContansts.MSG_ENTITY_APPORTIONMENT_RRMM); 
				
			if (!returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
				isCode = returnRDTO.getCode();
				isMessage = returnRDTO.getMessage();
			}
		}
		
		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);
		
		logger.info(LogsConstants.LOG_END);
		return returnRDTO;

	}

	public ReturnRDTO validateInsertAmortizationBase(MaterialResourceRDTO materialResourceRDTO) {
		logger.info(LogsConstants.LOG_START);
		
		
		returnRDTO = new ReturnRDTO();

		
		isCode = ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription();
		isMessage = ReturnEnum.CODE_SUCCESS_VALIDATE.getMessage();

		if (!validator.validateExistsMaterialResourceGap(materialResourceRDTO.getCode())){
			isCode 		= ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(), 
					MessageContansts.MSG_ENTITY_MATERIAL_RESOURCE,
					materialResourceRDTO.getCode());
		}
		
		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);
		
		logger.info(LogsConstants.LOG_END);
		return returnRDTO;
	}

	public ReturnRDTO validateInsertDocumentarySupport(DocumentarySupportRDTO documentarySupportRDTO) {
		
		boolean isRequestValid = true;
		
		returnRDTO = new ReturnRDTO();

		isCode = ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription();
		isMessage = ReturnEnum.CODE_SUCCESS_VALIDATE.getMessage();

		logger.info(LogsConstants.LOG_START);

		returnRDTO = validatorDocumentarySupport.validateInsert(documentarySupportRDTO);
		if (!returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
			isRequestValid = false;
			isCode = returnRDTO.getCode();
			isMessage = returnRDTO.getMessage();
		}
		if (isRequestValid && !validator.validateExistsMaterialResourceGap(documentarySupportRDTO.getCode())){
			isCode 		= ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(), 
										MessageContansts.MSG_ENTITY_MATERIAL_RESOURCE,
										documentarySupportRDTO.getCode());
		}
		
		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);
		
		logger.info(LogsConstants.LOG_END);

		return returnRDTO;

	}

	public ReturnRDTO validateDeleteDocumentarySupport(DocumentarySupportRDTO documentarySupportRDTO) {
		boolean isRequestValid = true;
		
		returnRDTO = new ReturnRDTO();

		isCode = ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription();
		isMessage = ReturnEnum.CODE_SUCCESS_VALIDATE.getMessage();

		logger.info(LogsConstants.LOG_START);

		returnRDTO = validatorDocumentarySupport.validateDelete(documentarySupportRDTO);
		if (!returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
			isRequestValid = false;
			isCode = returnRDTO.getCode();
			isMessage = returnRDTO.getMessage();
		}		
				
		if (isRequestValid  && !validator.validateExistsMaterialResourceGap(documentarySupportRDTO.getCode())){
			isCode 		= ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(), 
										MessageContansts.MSG_ENTITY_MATERIAL_RESOURCE,
										documentarySupportRDTO.getCode());
		}
		
		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);
		
		logger.info(LogsConstants.LOG_END);

		return returnRDTO;

	}

	public ReturnRDTO validateSelectDocumentarySupport(QueryParameterDocumentarySupportRDTO queryParameterDocumentarySupportRDTO) {
		returnRDTO = new ReturnRDTO();

		isCode = ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription();
		isMessage = ReturnEnum.CODE_SUCCESS_VALIDATE.getMessage();

		logger.info(LogsConstants.LOG_START);

				
		if (!validator.validateExistsMaterialResourceGap(queryParameterDocumentarySupportRDTO.getCode())){
			isCode 		= ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(), 
										MessageContansts.MSG_ENTITY_MATERIAL_RESOURCE,
										queryParameterDocumentarySupportRDTO.getCode());
		}
		
		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);
		
		logger.info(LogsConstants.LOG_END);

		return returnRDTO;

	}

	public ReturnRDTO validateInsertActuation(ActuationRDTO actuationRDTO) {
		boolean isRequestValid = true;
		
		returnRDTO = new ReturnRDTO();

		isCode = ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription();
		isMessage = ReturnEnum.CODE_SUCCESS_VALIDATE.getMessage();

		logger.info(LogsConstants.LOG_START);

		returnRDTO = validatorActuation.validateInsert(actuationRDTO);
		if (!returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
			isRequestValid = false;
			isCode = returnRDTO.getCode();
			isMessage = returnRDTO.getMessage();
		}
		if (isRequestValid && !validator.validateExistsMaterialResourceGap(actuationRDTO.getCodeEntity())){
			isRequestValid = false;
			isCode 		= ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(), 
										MessageContansts.MSG_ENTITY_MATERIAL_RESOURCE,
										actuationRDTO.getCodeEntity());
		}
		if (isRequestValid && ! validator.validateValueList(actuationRDTO.getTypeActuation(), new Type(ValueListConstants.TYPE_ACTUATION_RRMM ))) {
			
			isCode 		= ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(), 
										MessageContansts.MSG_FIELD_TYPE,
										MessageContansts.MSG_ENTITY_ACTUATION,
										actuationRDTO.getTypeActuation());
		}
		
		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);
		
		logger.info(LogsConstants.LOG_END);

		return returnRDTO;

	}

	public ReturnRDTO validateDeleteActuation(ActuationRDTO actuationRDTO) {
		boolean isRequestValid = true;
		
		returnRDTO = new ReturnRDTO();

		isCode = ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription();
		isMessage = ReturnEnum.CODE_SUCCESS_VALIDATE.getMessage();

		logger.info(LogsConstants.LOG_START);

		returnRDTO = validatorActuation.validateDelete(actuationRDTO);
		if (!returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
			isRequestValid = false;
			isCode = returnRDTO.getCode();
			isMessage = returnRDTO.getMessage();
		}
		if (isRequestValid && !validator.validateExistsMaterialResourceGap(actuationRDTO.getCodeEntity())){

			isCode 		= ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(), 
										MessageContansts.MSG_ENTITY_MATERIAL_RESOURCE,
										actuationRDTO.getCodeEntity());
		}
		
		
		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);
		
		logger.info(LogsConstants.LOG_END);

		return returnRDTO;

	}

}