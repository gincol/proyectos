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
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.DocumentarySupportRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.enums.ReturnEnum;

@Lazy
@Service(ServicesConstants.SRV_FRONT_VALIDATE_DOCUMENTAY_SUPPORT_FMW)
public class ValidatorDocumentarySupport {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ServicesConstants.SRV_FRONT_VALIDATE_FMW)
	private ValidatorUtils validator;

	private String isCode;

	private String isMessage;

	private ReturnRDTO returnRDTO;

	public ReturnRDTO validateInsert(DocumentarySupportRDTO documentarySupportRDTO) {

		logger.info(LogsConstants.LOG_START);
		
		returnRDTO = new ReturnRDTO();
		
		boolean isRequestValid = true;
		
		isCode = ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription();
		isMessage = ReturnEnum.CODE_SUCCESS_VALIDATE.getMessage();
		
		
		
		if (validator.validateExistsDocumentarySupport(documentarySupportRDTO.getCodeContract(), documentarySupportRDTO.getCodeContractDocument(),null,false) ){
			isRequestValid = false;
			isCode 		= ReturnEnum.CODE_ERROR_DUPLICATE_ENTITY.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_DUPLICATE_ENTITY.getMessage(),
										MessageContansts.MSG_ENTITY_DOCUMENTARY_SUPPORT,
										documentarySupportRDTO.getCodeContractDocument()
										);
		}
		if (isRequestValid && !StringUtils.isEmpty(documentarySupportRDTO.getCodeActuation())
				&& !validator.validateExistsActuation(documentarySupportRDTO.getCodeContract(),
						documentarySupportRDTO.getCodeActuation(), documentarySupportRDTO.getCode(), true)) {
			isCode 		= ReturnEnum.CODE_ERROR_ENTITY_ELEMENT_NOT_FOUND.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_ENTITY_ELEMENT_NOT_FOUND.getMessage(),
										MessageContansts.MSG_ENTITY_ACTUATION,
										documentarySupportRDTO.getCodeActuation(),
										documentarySupportRDTO.getCode());
		}
				
		
		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);

		return returnRDTO;
	}

	
	public ReturnRDTO validateDelete(DocumentarySupportRDTO documentarySupportRDTO) {

		logger.info(LogsConstants.LOG_START);
		
		returnRDTO = new ReturnRDTO();
		
		isCode = ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription();
		isMessage = ReturnEnum.CODE_SUCCESS_VALIDATE.getMessage();
		
		
		if (!validator.validateExistsDocumentarySupport(documentarySupportRDTO.getCodeContract(), documentarySupportRDTO.getCodeContractDocument(),documentarySupportRDTO.getCode(),true) ){
			
			isCode 		= ReturnEnum.CODE_ERROR_ENTITY_ELEMENT_NOT_FOUND.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_ENTITY_ELEMENT_NOT_FOUND.getMessage(),
										MessageContansts.MSG_ENTITY_DOCUMENTARY_SUPPORT,
										documentarySupportRDTO.getCodeContractDocument(),
										documentarySupportRDTO.getCode());
		}
		
				
		
		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);

		return returnRDTO;
	}

}
