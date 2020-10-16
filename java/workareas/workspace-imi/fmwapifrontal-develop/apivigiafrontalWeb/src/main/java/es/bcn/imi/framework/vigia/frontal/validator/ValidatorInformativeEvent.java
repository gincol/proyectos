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
import es.bcn.vigia.fmw.libcommons.constants.ValidationConstants;
import es.bcn.vigia.fmw.libcommons.constants.ValueListConstants;
import es.bcn.vigia.fmw.libcommons.valueobject.Entity;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.InformativeEventRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.enums.ReturnEnum;

@Lazy
@Service(ServicesConstants.SRV_FRONT_VALIDATE_INFORMATIVE_EVENT_FMW)
public class ValidatorInformativeEvent {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ServicesConstants.SRV_FRONT_VALIDATE_FMW)
	private ValidatorUtils validator;

	private String isCode;

	private String isMessage;

	private ReturnRDTO returnRDTO;

	public ReturnRDTO validateInsert(InformativeEventRDTO eventRDTO) {

		logger.info(LogsConstants.LOG_START);
		
		boolean isRequestValid = true;

		returnRDTO = new ReturnRDTO();
		
		isCode = ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription();
		isMessage = ReturnEnum.CODE_SUCCESS_VALIDATE.getMessage();
		
		returnRDTO = validator.validateCodeItinerary(eventRDTO.getCodeItinerary());
		if (!returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
			isRequestValid = false;
			isCode = returnRDTO.getCode();
			isMessage = returnRDTO.getMessage();
		}	
		
		if (isRequestValid && !validator.validateExistsItineraryGap(eventRDTO.getCodeItinerary())){
			isRequestValid = false;	
			isCode 		= ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(), 
					MessageContansts.MSG_ENTITY_ITINERARY,
					MessageContansts.MSG_ENTITY_INFORMATIVE_EVENT,
					eventRDTO.getCodeItinerary());
		}
		if (isRequestValid && !validator.validateExistsPlanificatedItinerary(eventRDTO.getCodeItinerary(),eventRDTO.getDateExercise())){
			isRequestValid = false;	
			isCode 		= ReturnEnum.CODE_ERROR_ITINERARY_NOT_PLANIFIED.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_ITINERARY_NOT_PLANIFIED.getMessage(), 
					eventRDTO.getCodeItinerary(),
					eventRDTO.getDateExercise());
		}
		if (isRequestValid && validator.validateExistsEventGap(eventRDTO.getCode())){
			isRequestValid = false;	
			isCode 		= ReturnEnum.CODE_ERROR_DUPLICATE_ENTITY.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_DUPLICATE_ENTITY.getMessage(), 
					MessageContansts.MSG_ENTITY_EVENT,
					eventRDTO.getCode());
		}

		//tipo evento
		if (isRequestValid && !validator.validateValueListGap(eventRDTO.getCodeType(),
				new Entity(ValueListConstants.CODE_TYPE_EVENT))) {
			isRequestValid = false;
			isCode = ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
					MessageContansts.MSG_FIELD_TYPE,
					MessageContansts.MSG_ENTITY_EVENT, eventRDTO.getCodeType());
		}
		if (isRequestValid && !validator.validateExistsGroupEventType(ValidationConstants.GROUP_INFORMATIVE_EVENT, eventRDTO.getCodeType())) {
			isRequestValid = false;
			isCode = ReturnEnum.CODE_ERROR_INCORRECT_EVENT_TYPE.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_INCORRECT_EVENT_TYPE.getMessage(),
					eventRDTO.getCodeType(),
					MessageContansts.MSG_ENTITY_INFORMATIVE_EVENT );
		}
		//motiu
		if (isRequestValid && !StringUtils.isEmpty(eventRDTO.getCodeReason()) && !validator.validateValueListGap(eventRDTO.getCodeReason(),
				new Entity(ValueListConstants.CODE_TYPE_REASON))) {
			isRequestValid = false;
			isCode = ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
					MessageContansts.MSG_FIELD_REASON,
					MessageContansts.MSG_ENTITY_INFORMATIVE_EVENT, eventRDTO.getCodeReason());
		}
		//submotiu
		if (isRequestValid && !StringUtils.isEmpty(eventRDTO.getCodeSubReason()) && !validator.validateValueListGap(eventRDTO.getCodeSubReason(),
				new Entity(ValueListConstants.CODE_TYPE_SUBREASON))) {
			isCode = ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
					MessageContansts.MSG_FIELD_SUBREASON,
					MessageContansts.MSG_ENTITY_INFORMATIVE_EVENT, eventRDTO.getCodeSubReason());
		}		
		
		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);

		return returnRDTO;
	}

}
