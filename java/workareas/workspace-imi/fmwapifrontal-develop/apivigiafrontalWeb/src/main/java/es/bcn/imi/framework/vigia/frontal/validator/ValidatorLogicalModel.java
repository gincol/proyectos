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
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.LogicalModelRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterDocumentarySupportRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.enums.ReturnEnum;

@Lazy
@Service(ServicesConstants.SRV_FRONT_VALIDATE_LOGICAL_MODEL_FMW)
public class ValidatorLogicalModel extends ValidatorContract {

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

	public ReturnRDTO validateInsert(LogicalModelRDTO logicalModelRDTO) {

		boolean isRequestValid = true;
		
		returnRDTO = new ReturnRDTO();

		logger.info(LogsConstants.LOG_START);

		//ubicación existente
		if (!validator.validateExistsUbicationGapByCodeType(logicalModelRDTO.getUbicationRDTO().getCode(),logicalModelRDTO.getUbicationRDTO().getType())) {
			isRequestValid = false;
			isCode = ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription();
			isMessage = String.format(MessageContansts.MSG_NOT_FOUND_ENTITY_AND_TYPE,
					MessageContansts.MSG_ENTITY_UBICATION, 
					logicalModelRDTO.getUbicationRDTO().getCode(),
					logicalModelRDTO.getUbicationRDTO().getType());
		}

		if (isRequestValid)
		{
			returnRDTO = validateCommonsInsertUpdate(logicalModelRDTO);
		}
		
		if (isRequestValid && !returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
			isRequestValid = false;
		}
		//misma posición en ubicación
		if (isRequestValid && validator.validateExistsLogicalModelPositionUbication(logicalModelRDTO.getUbicationRDTO().getCode(),
				logicalModelRDTO.getUbicationRDTO().getPosition(),logicalModelRDTO.getCode())) {
			isRequestValid = false;
			isCode = ReturnEnum.CODE_ERROR_DUPLICATE_POSITION.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_DUPLICATE_POSITION.getMessage(),
										MessageContansts.MSG_ENTITY_LOGICAL_MODEL, 
										logicalModelRDTO.getUbicationRDTO().getPosition());
		}
		if (isRequestValid && validator.validateExistsLogicalModelGap(logicalModelRDTO.getCode(),false)){
			isCode 		= ReturnEnum.CODE_ERROR_DUPLICATE_ENTITY.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_DUPLICATE_ENTITY.getMessage(),
										MessageContansts.MSG_ENTITY_LOGICAL_MODEL,
										logicalModelRDTO.getCode());
		}
		
		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);
		
		logger.info(LogsConstants.LOG_END);

		return returnRDTO;
	}

	public ReturnRDTO validateUpdate(LogicalModelRDTO logicalModelRDTO) {
		
		boolean isRequestValid = true;
		
		returnRDTO = new ReturnRDTO();
		
		logger.info(LogsConstants.LOG_START);

		//ubicación existente
		if (!validator.validateExistsUbicationGap(logicalModelRDTO.getUbicationRDTO().getCode(),true)) {
			isRequestValid = false;
			isCode = ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(),
					MessageContansts.MSG_ENTITY_UBICATION, 
					logicalModelRDTO.getUbicationRDTO().getCode());
		}
		
		if (isRequestValid)
		{
			returnRDTO = validateCommonsInsertUpdate(logicalModelRDTO);
		}
		if (isRequestValid && !returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
			isRequestValid = false;
		}

		//misma posición en ubicación
		if (isRequestValid && validator.validateExistsLogicalModelPositionUbication(logicalModelRDTO.getUbicationRDTO().getCode(),
				logicalModelRDTO.getUbicationRDTO().getPosition(),logicalModelRDTO.getCode())) {
			isRequestValid = false;
			isCode = ReturnEnum.CODE_ERROR_DUPLICATE_POSITION.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_DUPLICATE_POSITION.getMessage(),
										MessageContansts.MSG_ENTITY_LOGICAL_MODEL, 
										logicalModelRDTO.getUbicationRDTO().getPosition());
		}
		
		if (isRequestValid && !validator.validateExistsLogicalModelGap(logicalModelRDTO.getCode(),true)){
			
			isCode 		= ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(),
										MessageContansts.MSG_ENTITY_LOGICAL_MODEL,
										logicalModelRDTO.getCode());
		}

		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);

		logger.info(LogsConstants.LOG_END);

		return returnRDTO;
	}

	public ReturnRDTO validateCommonsInsertUpdate(LogicalModelRDTO logicalModelRDTO) {

		returnRDTO = new ReturnRDTO();

		boolean isRequestValid = true;
		
		isCode = ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription();
		isMessage = ReturnEnum.CODE_SUCCESS_VALIDATE.getMessage();

		
		//Validaciones contra GAP
		//Tipus Mobiliari
		if (!validator.validateValueListGap(logicalModelRDTO.getCodeType(),
				new Entity(ValueListConstants.FURNITURE_TYPE))) {
			isRequestValid = false;
			isCode = ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage = String.format(MessageContansts.MSG_TYPE_NOT_FOUND,
					MessageContansts.MSG_ENTITY_LOGICAL_MODEL, logicalModelRDTO.getCodeType());
		}
		//Tipus Fraccio
		if (isRequestValid && !validator.validateValueListGap(logicalModelRDTO.getCodeFractionType(),
				new Entity(ValueListConstants.FRACTION_TYPE))) {
			isRequestValid = false;
			isCode = ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage = String.format(MessageContansts.MSG_FRACTION_TYPE_NOT_FOUND,
					MessageContansts.MSG_ENTITY_LOGICAL_MODEL, logicalModelRDTO.getCodeFractionType());
		}
		
		
		if (isRequestValid && !validator.validateCompatibilityTypeMCLUbication(logicalModelRDTO.getCodeType(), logicalModelRDTO.getUbicationRDTO().getCode(),true)){
			
			isCode 		= ReturnEnum.CODE_ERROR_INCOMPATIBILITY.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_INCOMPATIBILITY.getMessage(), 
										MessageContansts.MSG_ENTITY_UBICATION,
										logicalModelRDTO.getUbicationRDTO().getCode(),
										MessageContansts.MSG_ENTITY_LOGICAL_MODEL,
										logicalModelRDTO.getCodeType());
		}
		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);

		return returnRDTO;
	}
	
	public ReturnRDTO validateDelete(LogicalModelRDTO logicalModelRDTO) {
		
		boolean isRequestValid = true;
		
		returnRDTO = new ReturnRDTO();
		
		logger.info(LogsConstants.LOG_START);

		//ubicación existente
		if (!validator.validateExistsUbicationGap(logicalModelRDTO.getUbicationRDTO().getCode(),true)) {
			isRequestValid = false;
			isCode = ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(),
					MessageContansts.MSG_ENTITY_UBICATION, 
					logicalModelRDTO.getUbicationRDTO().getCode());
		}
		
		if (isRequestValid)
		{
			returnRDTO = validateCommonsInsertUpdate(logicalModelRDTO);
		}
		if (isRequestValid && !returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
			isRequestValid = false;
		}

		if (isRequestValid && !validator.validateExistsLogicalModelGap(logicalModelRDTO.getCode(),true)){
			isRequestValid = false;
			isCode 		= ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(), 
										MessageContansts.MSG_ENTITY_LOGICAL_MODEL,
										logicalModelRDTO.getCode());
		}
		
		if (isRequestValid && validator.validateExistsPhysicalModelLogicalModelGap(logicalModelRDTO.getCode())){
			
			isCode 		= ReturnEnum.CODE_ERROR_DELETE_DEPENDENCIES.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_DELETE_DEPENDENCIES.getMessage(), 
										MessageContansts.MSG_ENTITY_LOGICAL_MODEL,
										logicalModelRDTO.getCode(),
										MessageContansts.MSG_ENTITY_PHYSICAL_MODEL);
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
		if (isRequestValid && !validator.validateExistsLogicalModelGap(documentarySupportRDTO.getCode(),true)){
			isCode 		= ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(), 
										MessageContansts.MSG_ENTITY_LOGICAL_MODEL,
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
		//ubicación existente
		if (isRequestValid && !validator.validateExistsUbicationGap(documentarySupportRDTO.getCodeUbication(),true)) {
			isRequestValid = false;
			isCode = ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(),
					MessageContansts.MSG_ENTITY_UBICATION, 
					documentarySupportRDTO.getCodeUbication());
		}

				
		if (isRequestValid && !validator.validateExistsLogicalModelGap(documentarySupportRDTO.getCode(),true)){
			isCode 		= ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(), 
										MessageContansts.MSG_ENTITY_LOGICAL_MODEL,
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

				
		if (!validator.validateExistsLogicalModelGap(queryParameterDocumentarySupportRDTO.getCode(),true)){
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
		if (isRequestValid && !validator.validateExistsLogicalModelGap(actuationRDTO.getCodeEntity(),true)){
			isRequestValid = false;
			isCode 		= ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(), 
										MessageContansts.MSG_ENTITY_LOGICAL_MODEL,
										actuationRDTO.getCodeEntity());
		}
		if (isRequestValid && ! validator.validateValueList(actuationRDTO.getTypeActuation(), new Type(ValueListConstants.TYPE_ACTUATION_MCL ))) {
			
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
		if (isRequestValid && !validator.validateExistsLogicalModelGap(actuationRDTO.getCodeEntity(),true)){
			isCode 		= ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(), 
										MessageContansts.MSG_ENTITY_LOGICAL_MODEL,
										actuationRDTO.getCodeEntity());
		}
		
		
		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);
		
		logger.info(LogsConstants.LOG_END);

		return returnRDTO;

	}

}