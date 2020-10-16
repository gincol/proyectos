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
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.ActuationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.enums.ReturnEnum;

@Lazy
@Service(ServicesConstants.SRV_FRONT_VALIDATE_ACTUATION_FMW)
public class ValidatorActuation extends ValidatorContract {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ServicesConstants.SRV_FRONT_VALIDATE_FMW)
	private ValidatorUtils validator;

	private String isCode;

	private String isMessage;

	private ReturnRDTO returnRDTO;

	public ReturnRDTO validateInsert(ActuationRDTO actuationRDTO) {

		logger.info(LogsConstants.LOG_START);
		
		returnRDTO = new ReturnRDTO();
		
		isCode = ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription();
		isMessage = ReturnEnum.CODE_SUCCESS_VALIDATE.getMessage();
		
		
		if (validator.validateExistsActuation(actuationRDTO.getCodeContract(), actuationRDTO.getCode(),null,false) ){
			isCode 		= ReturnEnum.CODE_ERROR_DUPLICATE_ENTITY.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_DUPLICATE_ENTITY.getMessage(),
										MessageContansts.MSG_ENTITY_ACTUATION,
										actuationRDTO.getCode()
										);
		}
		
				
		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);

		return returnRDTO;
	}

	public ReturnRDTO validateDelete(ActuationRDTO actuationRDTO) {
		logger.info(LogsConstants.LOG_START);
		
		returnRDTO = new ReturnRDTO();
		
		isCode = ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription();
		isMessage = ReturnEnum.CODE_SUCCESS_VALIDATE.getMessage();
		
		
		if (!validator.validateExistsActuation(actuationRDTO.getCodeContract(), actuationRDTO.getCode(),actuationRDTO.getCodeEntity(),true) ){
			isCode 		= ReturnEnum.CODE_ERROR_ENTITY_ELEMENT_NOT_FOUND.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_ENTITY_ELEMENT_NOT_FOUND.getMessage(),
										MessageContansts.MSG_ENTITY_ACTUATION,
										actuationRDTO.getCode(),
										actuationRDTO.getCodeEntity());
		}
		
				
		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);

		return returnRDTO;

	}

	
}
