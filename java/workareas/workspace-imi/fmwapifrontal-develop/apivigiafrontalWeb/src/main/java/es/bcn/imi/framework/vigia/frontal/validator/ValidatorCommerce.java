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
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CommerceRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.Utils;
import es.bcn.vigia.fmw.libutils.enums.ReturnEnum;

@Lazy
@Service(ServicesConstants.SRV_FRONT_VALIDATE_COMMERCE_FMW)
public class ValidatorCommerce extends ValidatorContract {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ServicesConstants.SRV_FRONT_VALIDATE_FMW)
	private ValidatorUtils validator;

	private String isCode;

	private String isMessage;

	private ReturnRDTO returnRDTO;

	public ReturnRDTO validateInsert(CommerceRDTO commerceRDTO) {

		logger.info(LogsConstants.LOG_START);
		
		boolean isRequestValid = true;
		
		returnRDTO = new ReturnRDTO();

		returnRDTO = validateInsertUpdate(commerceRDTO);
		
		if (!returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
			isRequestValid = false;
		}
		if (isRequestValid && validator.validateExistsCommerceGap(commerceRDTO.getCode(),false)){
			
			isCode 		= ReturnEnum.CODE_ERROR_DUPLICATE_ENTITY.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_DUPLICATE_ENTITY.getMessage(), 
					MessageContansts.MSG_ENTITY_COMMERCE,
					commerceRDTO.getCode());
			
		}

		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);
		
		logger.info(LogsConstants.LOG_END);
		return returnRDTO;
	}

	public ReturnRDTO validateUpdate(CommerceRDTO commerceRDTO) {

		logger.info(LogsConstants.LOG_START);
		
		boolean isRequestValid = true;
		
		returnRDTO = new ReturnRDTO();

		returnRDTO = validateInsertUpdate(commerceRDTO);
		
		if (!returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
			isRequestValid = false;
		}
		if (isRequestValid && !validator.validateExistsCommerceGap(commerceRDTO.getCode(),true)){
				
			isCode 		= ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(), 
										MessageContansts.MSG_ENTITY_COMMERCE,
										commerceRDTO.getCode());
		}

		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);
		
		logger.info(LogsConstants.LOG_END);

		return returnRDTO;
	}

	public ReturnRDTO validateInsertUpdate(CommerceRDTO commerceRDTO) {

		boolean isRequestValid = true;

		returnRDTO = new ReturnRDTO();
		
		isCode = ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription();
		isMessage = ReturnEnum.CODE_SUCCESS_VALIDATE.getMessage();

		
//		//Validaciones contra GAP
		//tipo via
		if (commerceRDTO.getLocationRDTO().getCodeTypeRoad()!=null && !commerceRDTO.getLocationRDTO().getCodeTypeRoad().trim().isEmpty() && 
				!validator.validateValueListGap(commerceRDTO.getLocationRDTO().getCodeTypeRoad(),
				new Entity(ValueListConstants.CODE_TYPE_ROAD))) {
			isRequestValid = false;
			isCode = ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
					MessageContansts.MSG_FIELD_CODE_TYPE_ROAD,
					MessageContansts.MSG_ENTITY_COMMERCE, commerceRDTO.getLocationRDTO().getCodeTypeRoad());
		}
		
		//estat commerce
		if (isRequestValid && !validator.validateValueListGap(commerceRDTO.getStatus(), new Entity(ValueListConstants.COMMERCE_STATUS))) {
			isCode = ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
					MessageContansts.MSG_FIELD_STATUS, MessageContansts.MSG_ENTITY_COMMERCE,
					commerceRDTO.getStatus());
		}
		
		
		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);

		return returnRDTO;
	}

	public ReturnRDTO validateDelete(CommerceRDTO commerceRDTO) {
		
		return validateUpdate(commerceRDTO);
	}

	public ReturnRDTO validateInsertElements(CommerceRDTO commerceRDTO) {
		boolean isRequestValid = true;

		returnRDTO = new ReturnRDTO();
		
		isCode = ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription();
		isMessage = ReturnEnum.CODE_SUCCESS_VALIDATE.getMessage();

		if (!validator.validateExistsCommerceGap(commerceRDTO.getCode(),true)){
			isRequestValid = false;
			isCode 		= ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(), 
										MessageContansts.MSG_ENTITY_COMMERCE,
										commerceRDTO.getCode());
		}		
		if (isRequestValid)
		{
			returnRDTO = validator.validateExistsListPhysicalModelGap(Utils.getListCodesMCF(commerceRDTO.getLstElementsRDTO()), true);
			if (!returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
				isCode = returnRDTO.getCode();
				isMessage = returnRDTO.getMessage();
			}
		}

		
		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);

		return returnRDTO;
	}
}
