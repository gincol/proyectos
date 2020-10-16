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
import es.bcn.vigia.fmw.libcommons.valueobject.Entity;
import es.bcn.vigia.fmw.libcommons.valueobject.Type;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.ActuationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.AggregateAmortizationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.DocumentarySupportRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.PhysicalModelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterDocumentarySupportRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.Utils;
import es.bcn.vigia.fmw.libutils.enums.ReturnEnum;

@Lazy
@Service(ServicesConstants.SRV_FRONT_VALIDATE_PHYSICAL_MODEL_FMW)
public class ValidatorPhysicalModel extends ValidatorContract {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ServicesConstants.SRV_FRONT_VALIDATE_FMW)
	private ValidatorUtils validator;

	@Autowired
	@Qualifier(ServicesConstants.SRV_FRONT_VALIDATE_DOCUMENTAY_SUPPORT_FMW)
	private ValidatorDocumentarySupport validatorDocumentarySupport;

	@Autowired
	@Qualifier(ServicesConstants.SRV_FRONT_VALIDATE_ACTUATION_FMW)
	private ValidatorActuation validatorActuation;

	private String isCode;

	private String isMessage;

	private ReturnRDTO returnRDTO;

	public ReturnRDTO validateInsert(PhysicalModelRDTO physicalModelRDTO) {

		returnRDTO = new ReturnRDTO();

		boolean isRequestValid = true;
		
		logger.info(LogsConstants.LOG_START);

		returnRDTO = validateCommonsInsertUpdate(physicalModelRDTO);

		if (!returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
			isRequestValid = false;
		}
		//No asociar más de un MCF a un MCL
		if (isRequestValid && (!StringUtils.isEmpty(physicalModelRDTO.getCodeMCL())) && 
				!validator.validateExistsVacantLogicalModelGap(physicalModelRDTO.getCodeMCL(),physicalModelRDTO.getCode())){
			isRequestValid = false;
			isCode 		= ReturnEnum.CODE_ERROR_MCL_BUSY.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_MCL_BUSY.getMessage(), 
										physicalModelRDTO.getCodeMCL());
		}

		if (isRequestValid && validator.validateExistsPhysicalModelGap(physicalModelRDTO.getCode())){
			isCode 		= ReturnEnum.CODE_ERROR_DUPLICATE_ENTITY.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_DUPLICATE_ENTITY.getMessage(), 
										MessageContansts.MSG_ENTITY_PHYSICAL_MODEL,
										physicalModelRDTO.getCode());
		}
		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);
		
		logger.info(LogsConstants.LOG_END);

		return returnRDTO;
	}

	public ReturnRDTO validateUpdate(PhysicalModelRDTO physicalModelRDTO) {

		logger.info(LogsConstants.LOG_START);
		
		boolean isRequestValid = true;
		
		returnRDTO = new ReturnRDTO();

		returnRDTO = validateCommonsInsertUpdate(physicalModelRDTO);

		if (!returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
			isRequestValid = false;
		}
		//No asociar más de un MCF a un MCL
		if (isRequestValid && (!StringUtils.isEmpty(physicalModelRDTO.getCodeMCL())) && 
				!validator.validateExistsVacantLogicalModelGap(physicalModelRDTO.getCodeMCL(),physicalModelRDTO.getCode())){
			isRequestValid = false;
			isCode 		= ReturnEnum.CODE_ERROR_MCL_BUSY.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_MCL_BUSY.getMessage(), 
										physicalModelRDTO.getCodeMCL());
		}
		if (isRequestValid && !validator.validateExistsPhysicalModelGap(physicalModelRDTO.getCode())){
			isCode 		= ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(),
										MessageContansts.MSG_ENTITY_PHYSICAL_MODEL,
										physicalModelRDTO.getCode());
		}
		
		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);
		
		logger.info(LogsConstants.LOG_END);
		return returnRDTO;
	}

	public ReturnRDTO validateCommonsInsertUpdate(PhysicalModelRDTO physicalModelRDTO) {

		returnRDTO = new ReturnRDTO();

		boolean isRequestValid = true;
		
		isCode = ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription();
		isMessage = ReturnEnum.CODE_SUCCESS_VALIDATE.getMessage();

		
		returnRDTO = validator.validateSensorsContract(physicalModelRDTO.getCodeContract(),
				physicalModelRDTO.getSensorsRDTO()); 
		if (!returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
			isRequestValid = false;
			isCode = returnRDTO.getCode();
			isMessage = returnRDTO.getMessage();
		}
		//compatibiitat sensor - mcf
			if (isRequestValid)
			{
				returnRDTO =  validator.validateCompatibilityTypeMCFTypesSensor(physicalModelRDTO.getCodeType(), Utils.getListSensorCodes(physicalModelRDTO.getSensorsRDTO())); 
				if (!returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
					isRequestValid = false;
					isCode = returnRDTO.getCode();
					isMessage = returnRDTO.getMessage();
				}
			}
		//estat
		if (isRequestValid && !validator.validateValueListGap(physicalModelRDTO.getCodeState(),
				new Entity(ValueListConstants.MCF_STATUS))) {
			isRequestValid = false;
			isCode = ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
					MessageContansts.MSG_FIELD_STATUS,
					MessageContansts.MSG_ENTITY_PHYSICAL_MODEL, physicalModelRDTO.getCodeState());
		}
		//tipus
		if (isRequestValid && !validator.validateValueListGap(physicalModelRDTO.getCodeType(),
				new Entity(ValueListConstants.MCF_TYPE))) {
			isRequestValid = false;
			isCode = ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
					MessageContansts.MSG_FIELD_TYPE,
					MessageContansts.MSG_ENTITY_PHYSICAL_MODEL, physicalModelRDTO.getCodeType());
		}
		//Tipus Fraccio
		if (isRequestValid && !validator.validateValueListGap(physicalModelRDTO.getCodeFractionType(),
				new Entity(ValueListConstants.FRACTION_TYPE))) {
			isRequestValid = false;
			isCode = ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
					MessageContansts.MSG_FIELD_FRACTION_TYPE_CODE,
					MessageContansts.MSG_ENTITY_PHYSICAL_MODEL, physicalModelRDTO.getCodeFractionType());
		}
		//Marca
		if (isRequestValid && !validator.validateValueListGap(physicalModelRDTO.getCodeBrand(),
				new Entity(ValueListConstants.CODE_BRAND))) {
			isRequestValid = false;
			isCode = ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
					MessageContansts.MSG_FIELD_BRAND,
					MessageContansts.MSG_ENTITY_PHYSICAL_MODEL, physicalModelRDTO.getCodeBrand());
		}
		
		//Validaciones contra GAP
		//codi Ubicacio
		if (isRequestValid && !validator.validateExistsUbicationGap(physicalModelRDTO.getCodeUbication(),true)){
			isRequestValid = false;
			isCode 		= ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(), 
										MessageContansts.MSG_ENTITY_UBICATION,
										physicalModelRDTO.getCodeUbication());
		}
		//codigo instalación
		if (isRequestValid && (!StringUtils.isEmpty(physicalModelRDTO.getCodeInstallation()))
				&& !validator.validateExistsInstallationGap(physicalModelRDTO.getCodeInstallation(),true)){
			
			isCode 		= ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
					MessageContansts.MSG_ENTITY_INSTALLATION,
					MessageContansts.MSG_ENTITY_PHYSICAL_MODEL,
					physicalModelRDTO.getCodeInstallation());
			
		}
		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);

		return returnRDTO;
	}
	
	public ReturnRDTO validateDelete(PhysicalModelRDTO physicalModelRDTO) {
		
		logger.info(LogsConstants.LOG_START);
		
		boolean isRequestValid = true;
		
		returnRDTO = new ReturnRDTO();

		returnRDTO = validateCommonsInsertUpdate(physicalModelRDTO);

		if (!returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
			isRequestValid = false;
		}
		
		
		if (isRequestValid && !validator.validateExistsPhysicalModelGap(physicalModelRDTO.getCode())){
			isRequestValid = false;
			isCode 		= ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(),
										MessageContansts.MSG_ENTITY_PHYSICAL_MODEL,
										physicalModelRDTO.getCode());
		}
		if (isRequestValid && !physicalModelRDTO.getCodeMCL().isEmpty() && !validator.validateExistsAssociationMCLMCF(physicalModelRDTO.getCodeUbication(),
				physicalModelRDTO.getCodeMCL(),physicalModelRDTO.getCode())){
			isCode 		= ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage	= String.format(MessageContansts.MSG_MCF_MCL_NOT_ASSOCIATED, 
					MessageContansts.MSG_ENTITY_PHYSICAL_MODEL,
					physicalModelRDTO.getCode(),
					MessageContansts.MSG_ENTITY_LOGICAL_MODEL,
					physicalModelRDTO.getCodeMCL());
			
		}

		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);
		
		logger.info(LogsConstants.LOG_END);

		return returnRDTO;
	}
	
	public ReturnRDTO validateInsert(AggregateAmortizationRDTO aggregateAmortizationRDTO) {
		
		returnRDTO = new ReturnRDTO();

		boolean isRequestValid = true;

		logger.info(LogsConstants.LOG_START);
		
		isCode = ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription();
		isMessage = ReturnEnum.CODE_SUCCESS_VALIDATE.getMessage();
		
		//Tipus Fraccio
		if (!validator.validateValueListGap(aggregateAmortizationRDTO.getCodeFraction(),
				new Entity(ValueListConstants.FRACTION_TYPE))) {
			isRequestValid = false;
			isCode = ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
					MessageContansts.MSG_FIELD_FRACTION_TYPE_CODE,
					MessageContansts.MSG_ENTITY_MCF_AGGREGATE_AMORTITZATION, aggregateAmortizationRDTO.getCodeFraction());
		}
		
		//group servei
		if (isRequestValid && !validator.validateValueListGap(aggregateAmortizationRDTO.getCodeGroupService(),
				new Entity(ValueListConstants.GROUP_SERVICE))) {
			isRequestValid = false;
			isCode = ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
					MessageContansts.MSG_FIELD_SERVICE_GROUP_CODE,
					MessageContansts.MSG_ENTITY_MCF_AGGREGATE_AMORTITZATION, aggregateAmortizationRDTO.getCodeGroupService());
		}
		//Districte
		if (isRequestValid && !validator.validateValueListGap(aggregateAmortizationRDTO.getCodeTerritory(),
				new Entity(ValueListConstants.CODE_DISTRICT))) {
			isRequestValid = false;
			isCode = ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
					MessageContansts.MSG_FIELD_DISTRICT_CODE,
					MessageContansts.MSG_ENTITY_MCF_AGGREGATE_AMORTITZATION, aggregateAmortizationRDTO.getCodeTerritory());
		}
		//Marca
		if (isRequestValid && !validator.validateValueListGap(aggregateAmortizationRDTO.getCodeBrandFurniture(),
				new Entity(ValueListConstants.CODE_BRAND))) {
			isRequestValid = false;
			isCode = ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
					MessageContansts.MSG_FIELD_BRAND,
					MessageContansts.MSG_ENTITY_MCF_AGGREGATE_AMORTITZATION, aggregateAmortizationRDTO.getCodeBrandFurniture());
		}
		//tipus
		if (isRequestValid && !validator.validateValueListGap(aggregateAmortizationRDTO.getCodeTypeFurniture(),
				new Entity(ValueListConstants.MCF_TYPE))) {
			isCode = ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
					MessageContansts.MSG_FIELD_MCF_TYPE_CODE,
					MessageContansts.MSG_ENTITY_MCF_AGGREGATE_AMORTITZATION, aggregateAmortizationRDTO.getCodeTypeFurniture());
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
		if (isRequestValid && !validator.validateExistsPhysicalModelGap(documentarySupportRDTO.getCode())){
			isCode 		= ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(), 
										MessageContansts.MSG_ENTITY_PHYSICAL_MODEL,
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

		if (isRequestValid && !validator.validateExistsPhysicalModelGap(documentarySupportRDTO.getCode())){
			isRequestValid = false;
			isCode 		= ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(), 
										MessageContansts.MSG_ENTITY_PHYSICAL_MODEL,
										documentarySupportRDTO.getCode());
		}
		//codi Ubicacio
		if (isRequestValid && !validator.validateExistsUbicationGap(documentarySupportRDTO.getCodeUbication(),true)){
			isCode 		= ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(), 
										MessageContansts.MSG_ENTITY_UBICATION,
										documentarySupportRDTO.getCodeUbication());
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

				
		if (!validator.validateExistsPhysicalModelGap(queryParameterDocumentarySupportRDTO.getCode())){
			isCode 		= ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(), 
										MessageContansts.MSG_ENTITY_LOGICAL_MODEL,
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
		if (isRequestValid && !validator.validateExistsPhysicalModelGap(actuationRDTO.getCodeEntity())){
			isRequestValid = false;
			isCode 		= ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(), 
										MessageContansts.MSG_ENTITY_PHYSICAL_MODEL,
										actuationRDTO.getCodeEntity());
		}
		if (isRequestValid && ! validator.validateValueList(actuationRDTO.getTypeActuation(), new Type(ValueListConstants.TYPE_ACTUATION_MCF ))) {
			
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
		//ubicación existente
		if (isRequestValid && !validator.validateExistsUbicationGap(actuationRDTO.getCodeUbication(),true)) {
			isRequestValid = false;
			isCode = ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(),
					MessageContansts.MSG_ENTITY_UBICATION, 
					actuationRDTO.getCodeUbication());
		}
		if (isRequestValid && !validator.validateExistsPhysicalModelGap(actuationRDTO.getCodeEntity())){
		
			isCode 		= ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(), 
										MessageContansts.MSG_ENTITY_PHYSICAL_MODEL,
										actuationRDTO.getCodeEntity());
		}
		
		
		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);
		
		logger.info(LogsConstants.LOG_END);

		return returnRDTO;

	}



}