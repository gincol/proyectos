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
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.PlanningRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.Utils;
import es.bcn.vigia.fmw.libutils.enums.ReturnEnum;

@Lazy
@Service(ServicesConstants.SRV_FRONT_VALIDATE_PLANNING_FMW)
public class ValidatorPlanning extends ValidatorContract {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ServicesConstants.SRV_FRONT_VALIDATE_FMW)
	private ValidatorUtils validator;

	private String isCode;

	private String isMessage;

	private ReturnRDTO returnRDTO;



	public ReturnRDTO validateInsert(PlanningRDTO planningRDTO) {

		

		returnRDTO = new ReturnRDTO();
		
		boolean isRequestValid = true;
		
		isCode = ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription();
		isMessage = ReturnEnum.CODE_SUCCESS_VALIDATE.getMessage();

		logger.info(LogsConstants.LOG_START);
//		//Validaciones contra GAP
		returnRDTO = validator.validateCodeItinerary(planningRDTO.getCodeItinerary());
		if (!returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
			isRequestValid = false;
			isCode = returnRDTO.getCode();
			isMessage = returnRDTO.getMessage();
		}	
		
		if (isRequestValid && !validator.validateExistsItineraryGap(planningRDTO.getCodeItinerary())){
			isRequestValid = false;	
			isCode 		= ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(), 
					MessageContansts.MSG_ENTITY_ITINERARY,
					MessageContansts.MSG_ENTITY_PLANNING,
					planningRDTO.getCodeItinerary());
		}
		//etiquetes
		if (isRequestValid && !planningRDTO.getLstLabelRDTO().isEmpty())
		{
			returnRDTO = validator.validateListValueListGap(Utils.getListLabelCodes(planningRDTO.getLstLabelRDTO()), 
					new Entity(ValueListConstants.LABEL), 
					MessageContansts.MSG_FIELD_LABEL, 
					MessageContansts.MSG_ENTITY_PLANNING); 
				
			if (!returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
				isCode = returnRDTO.getCode();
				isMessage = returnRDTO.getMessage();
			}
			
			
		}
				
		logger.info(LogsConstants.LOG_END);
		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);

		return returnRDTO;
	}

}
