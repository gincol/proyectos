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
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.APSEventRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.enums.ReturnEnum;

@Lazy
@Service(ServicesConstants.SRV_FRONT_VALIDATE_APS_EVENT_FMW)
public class ValidatorAPSEvent {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ServicesConstants.SRV_FRONT_VALIDATE_FMW)
	private ValidatorUtils validator;

	private String isCode;

	private String isMessage;

	private ReturnRDTO returnRDTO;

	public ReturnRDTO validateInsert(APSEventRDTO apsEventRDTO) {

		logger.info(LogsConstants.LOG_START);
		
		boolean isRequestValid = true;

		returnRDTO = new ReturnRDTO();
		
		isCode = ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription();
		isMessage = ReturnEnum.CODE_SUCCESS_VALIDATE.getMessage();

		returnRDTO = validator.validateCodeItinerary(apsEventRDTO.getCodeItinerary());
		if (!returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
			isRequestValid = false;
			isCode = returnRDTO.getCode();
			isMessage = returnRDTO.getMessage();
		}	
		
		if (isRequestValid && !validator.validateExistsItineraryGap(apsEventRDTO.getCodeItinerary())){
			isRequestValid = false;	
			isCode 		= ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(), 
					MessageContansts.MSG_ENTITY_ITINERARY,
					MessageContansts.MSG_ENTITY_APS_EVENT,
					apsEventRDTO.getCodeItinerary());
		}
		if (isRequestValid && !validator.validateExistsPlanificatedItinerary(apsEventRDTO.getCodeItinerary(),apsEventRDTO.getDateExercise())){
			isRequestValid = false;	
			isCode 		= ReturnEnum.CODE_ERROR_ITINERARY_NOT_PLANIFIED.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_ITINERARY_NOT_PLANIFIED.getMessage(), 
					apsEventRDTO.getCodeItinerary(),
					apsEventRDTO.getDateExercise());
		}
		if (isRequestValid && validator.validateExistsEventGap(apsEventRDTO.getCode())){
			isRequestValid = false;	
			isCode 		= ReturnEnum.CODE_ERROR_DUPLICATE_ENTITY.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_DUPLICATE_ENTITY.getMessage(), 
					MessageContansts.MSG_ENTITY_EVENT,
					apsEventRDTO.getCode());
		}

		//tipo evento
		if (isRequestValid && !validator.validateValueListGap(apsEventRDTO.getCodeType(),
				new Entity(ValueListConstants.CODE_TYPE_EVENT))) {
			isRequestValid = false;
			isCode = ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
					MessageContansts.MSG_FIELD_TYPE,
					MessageContansts.MSG_ENTITY_EVENT, apsEventRDTO.getCodeType());
		}
		if (isRequestValid && !validator.validateExistsGroupEventType(ValidationConstants.GROUP_APS_EVENT, apsEventRDTO.getCodeType())) {
			isRequestValid = false;
			isCode = ReturnEnum.CODE_ERROR_INCORRECT_EVENT_TYPE.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_INCORRECT_EVENT_TYPE.getMessage(),
					apsEventRDTO.getCodeType(),
					MessageContansts.MSG_ENTITY_APS_EVENT );
		}
		//motiu
		if (isRequestValid && !validator.validateValueListGap(apsEventRDTO.getCodeReason(),
				new Entity(ValueListConstants.CODE_TYPE_REASON))) {
			isRequestValid = false;
			isCode = ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
					MessageContansts.MSG_FIELD_REASON,
					MessageContansts.MSG_ENTITY_APS_EVENT, apsEventRDTO.getCodeReason());
		}
		//submotiu
		if (isRequestValid && !validator.validateValueListGap(apsEventRDTO.getCodeSubReason(),
				new Entity(ValueListConstants.CODE_TYPE_SUBREASON))) {
			isRequestValid = false;
			isCode = ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
					MessageContansts.MSG_FIELD_SUBREASON,
					MessageContansts.MSG_ENTITY_APS_EVENT, apsEventRDTO.getCodeSubReason());
		}
		//codi RRMM
		if (isRequestValid && !StringUtils.isEmpty(apsEventRDTO.getCodeRRMM()) && !validator.validateExistsMaterialResourceGap(apsEventRDTO.getCodeRRMM())) {
			isCode = ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
					MessageContansts.MSG_FIELD_CODE_RRMM,
					MessageContansts.MSG_ENTITY_APS_EVENT, apsEventRDTO.getCodeRRMM());
		}
		
		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);

		return returnRDTO;
	}

}
