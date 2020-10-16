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
import es.bcn.vigia.fmw.libcommons.constants.ValidationConstants;
import es.bcn.vigia.fmw.libcommons.constants.ValueListConstants;
import es.bcn.vigia.fmw.libcommons.valueobject.Entity;
import es.bcn.vigia.fmw.libcommons.valueobject.Type;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.ActuationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.DocumentarySupportRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterDocumentarySupportRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.UbicationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.Utils;
import es.bcn.vigia.fmw.libutils.enums.ReturnEnum;

@Lazy
@Service(ServicesConstants.SRV_FRONT_VALIDATE_UBICATION_FMW)
public class ValidatorUbication extends ValidatorContract {

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

	public ReturnRDTO validateInsert(UbicationRDTO ubicationRDTO) {

		boolean isRequestValid = true;
		
		returnRDTO = new ReturnRDTO();

		logger.info(LogsConstants.LOG_START);

		returnRDTO = validateInsertUpdate(ubicationRDTO);
		
		if (!returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
			isRequestValid = false;
		}
		if (isRequestValid && validator.validateExistsUbicationGap(ubicationRDTO.getCode(),false)){
			
			isCode 		= ReturnEnum.CODE_ERROR_DUPLICATE_ENTITY.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_DUPLICATE_ENTITY.getMessage(), 
										MessageContansts.MSG_ENTITY_UBICATION,
										ubicationRDTO.getCode());
		}

		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);
		
		logger.info(LogsConstants.LOG_END);
		return returnRDTO;
	}

	public ReturnRDTO validateUpdate(UbicationRDTO ubicationRDTO) {

		boolean isRequestValid = true;
		
		returnRDTO = new ReturnRDTO();

		logger.info(LogsConstants.LOG_START);

		returnRDTO = validateInsertUpdate(ubicationRDTO);
		
		if (!returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
			isRequestValid = false;
		}
		if (isRequestValid && !validator.validateExistsUbicationGap(ubicationRDTO.getCode(),true)){
			isRequestValid = false;
			isCode 		= ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(), 
										MessageContansts.MSG_ENTITY_UBICATION,
										ubicationRDTO.getCode());
		}
		

		if (isRequestValid && !validator.validateCompatibilityLogicalModelsUbication(ubicationRDTO.getCode(),ubicationRDTO.getType()))
		{	
			isCode 		= ReturnEnum.CODE_ERROR_INCOMPATIBILITY.getCodeDescription();
			isMessage	= String.format(MessageContansts.MSG_INCOMPATIBLE_DEPENDENCIES, MessageContansts.MSG_ENTITY_UBICATION,
					ubicationRDTO.getCode(),
					MessageContansts.MSG_ENTITY_LOGICAL_MODEL);
										
		}
		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);
		
		logger.info(LogsConstants.LOG_END);

		return returnRDTO;
	}
	
	

	public ReturnRDTO validateInsertUpdate(UbicationRDTO ubicationRDTO) {
		
		returnRDTO = new ReturnRDTO();

		boolean isRequestValid = true;
		
		isCode = ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription();
		isMessage = ReturnEnum.CODE_SUCCESS_VALIDATE.getMessage();
		
		if (!validator.validateValueList(ubicationRDTO.getRefTypeEnvironment(),
				new Type(ValueListConstants.ENVIRONMENT_REF_TYPE))) {
			isRequestValid = false;
			isCode = ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
					MessageContansts.MSG_FIELD_REF_TYPE_ENVIRONMENT,
					MessageContansts.MSG_ENTITY_UBICATION, ubicationRDTO.getRefTypeEnvironment());
		}
		//Validaciones contra GAP
		//tipus ubicacio
		if (isRequestValid && !validator.validateValueListGap(ubicationRDTO.getType(), new Entity(ValueListConstants.UBICATION_TYPE))) {
			isRequestValid = false;
			isCode = ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
					MessageContansts.MSG_FIELD_TYPE, MessageContansts.MSG_ENTITY_UBICATION,
					ubicationRDTO.getType());
		}
		//estat ubicacio
		if (isRequestValid && !validator.validateValueListGap(ubicationRDTO.getCodeState(), new Entity(ValueListConstants.UBICATION_STATUS))) {
			isRequestValid = false;
			isCode = ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
					MessageContansts.MSG_FIELD_STATUS, MessageContansts.MSG_ENTITY_UBICATION,
					ubicationRDTO.getCodeState());
		}
		//Districte
		if (isRequestValid && ubicationRDTO.getLocationRDTO().getPopulation().equalsIgnoreCase(ValidationConstants.POPULATION_BARCELONA)
				&& ubicationRDTO.getLocationRDTO().getCodeTerritory() != null
				&& !ubicationRDTO.getLocationRDTO().getCodeTerritory().trim().isEmpty()
				&& !validator.validateValueListGap(ubicationRDTO.getLocationRDTO().getCodeTerritory(),
						new Entity(ValueListConstants.CODE_DISTRICT))) {
			isRequestValid = false;
			isCode = ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
					MessageContansts.MSG_FIELD_CODE_DISTRICT, MessageContansts.MSG_ENTITY_UBICATION,
					ubicationRDTO.getLocationRDTO().getCodeTerritory());
		}
		//Barri
		if (isRequestValid && ubicationRDTO.getLocationRDTO().getPopulation().equalsIgnoreCase(ValidationConstants.POPULATION_BARCELONA) 
				&& ubicationRDTO.getLocationRDTO().getCodeNeighborhood() != null
				&& !ubicationRDTO.getLocationRDTO().getCodeNeighborhood().trim().isEmpty()
				&& !validator.validateValueListGap(ubicationRDTO.getLocationRDTO().getCodeNeighborhood(),
				new Entity(ValueListConstants.CODE_NEIGHBORHOOD))) {
			isRequestValid = false;
			isCode = ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
					MessageContansts.MSG_FIELD_CODE_NEIGHBORHOOD, MessageContansts.MSG_ENTITY_UBICATION,
					ubicationRDTO.getLocationRDTO().getCodeNeighborhood());
		}
		if (isRequestValid && ubicationRDTO.getLocationRDTO().getCodeTypeRoad()!=null && !ubicationRDTO.getLocationRDTO().getCodeTypeRoad().trim().isEmpty() && 
				!validator.validateValueListGap(ubicationRDTO.getLocationRDTO().getCodeTypeRoad(),
				new Entity(ValueListConstants.CODE_TYPE_ROAD))) {
			isRequestValid = false;	
			isCode = ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
					MessageContansts.MSG_FIELD_CODE_TYPE_ROAD,
					MessageContansts.MSG_ENTITY_LOCALITZACIO, ubicationRDTO.getLocationRDTO().getCodeTypeRoad());
		}
		if (isRequestValid && !ubicationRDTO.getListLabelRDTO().isEmpty())
		{
			returnRDTO = validator.validateListValueListGap(Utils.getListLabelCodes(ubicationRDTO.getListLabelRDTO()), 
					new Entity(ValueListConstants.LABEL), 
					MessageContansts.MSG_FIELD_LABEL, 
					MessageContansts.MSG_ENTITY_UBICATION); 
				
			if (!returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
				isCode = returnRDTO.getCode();
				isMessage = returnRDTO.getMessage();
			}
			
		}
		
		
		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);

		return returnRDTO;
	}

	public ReturnRDTO validateDelete(UbicationRDTO ubicationRDTO) {
		
		boolean isRequestValid = true;
		
		returnRDTO = new ReturnRDTO();

		logger.info(LogsConstants.LOG_START);

		returnRDTO = validateInsertUpdate(ubicationRDTO);
		
		if (!returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
			isRequestValid = false;
		}		
		if (isRequestValid && !validator.validateExistsUbicationGap(ubicationRDTO.getCode(),true)){
			isRequestValid = false;
			isCode 		= ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(), 
										MessageContansts.MSG_ENTITY_UBICATION,
										ubicationRDTO.getCode());
		}
		if (isRequestValid && validator.validateExistsLogicalModelUbication(ubicationRDTO.getCode())){
			isCode 		= ReturnEnum.CODE_ERROR_DELETE_DEPENDENCIES.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_DELETE_DEPENDENCIES.getMessage(),
										MessageContansts.MSG_ENTITY_UBICATION,
										ubicationRDTO.getCode(),
										MessageContansts.MSG_ENTITY_LOGICAL_MODEL
										);
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
		
		
		if (isRequestValid && !validator.validateExistsUbicationGap(documentarySupportRDTO.getCode(),true)){
			isCode 		= ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(), 
										MessageContansts.MSG_ENTITY_UBICATION,
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
		if (isRequestValid && !validator.validateExistsUbicationGap(documentarySupportRDTO.getCode(),true)){
			isCode 		= ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(), 
										MessageContansts.MSG_ENTITY_UBICATION,
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

				
		if (!validator.validateExistsUbicationGap(queryParameterDocumentarySupportRDTO.getCode(),true)){
			isCode 		= ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(), 
										MessageContansts.MSG_ENTITY_UBICATION,
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
		if (isRequestValid && !validator.validateExistsUbicationGap(actuationRDTO.getCodeEntity(),true)){
			isRequestValid = false;
			isCode 		= ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(), 
										MessageContansts.MSG_ENTITY_UBICATION,
										actuationRDTO.getCodeEntity());
		}
		if (isRequestValid && ! validator.validateValueList(actuationRDTO.getTypeActuation(), new Type(ValueListConstants.TYPE_ACTUATION_UBICATION ))) {
			
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
		if (isRequestValid && !validator.validateExistsUbicationGap(actuationRDTO.getCodeEntity(),true)){
			
			isCode 		= ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(), 
										MessageContansts.MSG_ENTITY_UBICATION,
										actuationRDTO.getCodeEntity());
		}
		
		
		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);
		
		logger.info(LogsConstants.LOG_END);

		return returnRDTO;

	}
}
