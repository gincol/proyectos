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
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.DocumentarySupportExpenseRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.DocumentarySupportRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.InstallationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.Utils;
import es.bcn.vigia.fmw.libutils.enums.ReturnEnum;

@Lazy
@Service(ServicesConstants.SRV_FRONT_VALIDATE_INSTALLATION_FMW)
public class ValidatorInstallation extends ValidatorContract {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ServicesConstants.SRV_FRONT_VALIDATE_FMW)
	private ValidatorUtils validator;

	@Autowired
	@Qualifier(ServicesConstants.SRV_FRONT_VALIDATE_ACTUATION_FMW)
	private ValidatorActuation validatorActuation;

	@Autowired
	@Qualifier(ServicesConstants.SRV_FRONT_VALIDATE_DOCUMENTAY_SUPPORT_FMW)
	private ValidatorDocumentarySupport validatorDocumentarySupport;
	
	private String isCode;

	private String isMessage;

	private ReturnRDTO returnRDTO;

	public ReturnRDTO validateInsert(InstallationRDTO installationRDTO) {

		logger.info(LogsConstants.LOG_START);
		
		boolean isRequestValid = true;
		
		returnRDTO = new ReturnRDTO();

		returnRDTO = validateInsertUpdate(installationRDTO);
		
		if (!returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
			isRequestValid = false;
		}
		if (isRequestValid && validator.validateExistsInstallationGap(installationRDTO.getCode(),false)){
			
			isCode 		= ReturnEnum.CODE_ERROR_DUPLICATE_ENTITY.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_DUPLICATE_ENTITY.getMessage(), 
					MessageContansts.MSG_ENTITY_INSTALLATION,
					installationRDTO.getCode());
			
		}

		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);
		
		logger.info(LogsConstants.LOG_END);
		return returnRDTO;
	}

	public ReturnRDTO validateUpdate(InstallationRDTO installationRDTO) {

		logger.info(LogsConstants.LOG_START);
		
		boolean isRequestValid = true;
		
		returnRDTO = new ReturnRDTO();

		returnRDTO = validateInsertUpdate(installationRDTO);
		
		if (!returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
			isRequestValid = false;
		}
		if (isRequestValid && !validator.validateExistsInstallationGap(installationRDTO.getCode(),true)){
			
			isCode 		= ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(), 
					MessageContansts.MSG_ENTITY_INSTALLATION,
					installationRDTO.getCode());
			
		}

		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);
		
		logger.info(LogsConstants.LOG_END);
		return returnRDTO;
	}

	public ReturnRDTO validateInsertUpdate(InstallationRDTO installationRDTO) {

		boolean isRequestValid = true;

		returnRDTO = new ReturnRDTO();
		
		isCode = ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription();
		isMessage = ReturnEnum.CODE_SUCCESS_VALIDATE.getMessage();

		
		//Validaciones contra GAP
		//Barri
		if (installationRDTO.getLocationRDTO().getPopulation().equalsIgnoreCase(ValidationConstants.POPULATION_BARCELONA)
				&& installationRDTO.getLocationRDTO().getCodeNeighborhood() != null
				&& !installationRDTO.getLocationRDTO().getCodeNeighborhood().trim().isEmpty()
				&& !validator.validateValueListGap(installationRDTO.getLocationRDTO().getCodeNeighborhood(),
				new Entity(ValueListConstants.CODE_NEIGHBORHOOD))) {
			isRequestValid = false;
			isCode = ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
					MessageContansts.MSG_FIELD_CODE_NEIGHBORHOOD,
					MessageContansts.MSG_ENTITY_INSTALLATION,
					installationRDTO.getLocationRDTO().getCodeNeighborhood());
		}
		//Districte
		if (isRequestValid && installationRDTO.getLocationRDTO().getPopulation().equalsIgnoreCase(ValidationConstants.POPULATION_BARCELONA) 
				&& installationRDTO.getLocationRDTO().getCodeTerritory() != null
				&& !installationRDTO.getLocationRDTO().getCodeTerritory().trim().isEmpty()
				&& !validator.validateValueListGap(installationRDTO.getLocationRDTO().getCodeTerritory(),
				new Entity(ValueListConstants.CODE_DISTRICT))) {
			isRequestValid = false;
			isCode = ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
					MessageContansts.MSG_FIELD_CODE_DISTRICT,
					MessageContansts.MSG_ENTITY_INSTALLATION, 
					installationRDTO.getLocationRDTO().getCodeTerritory());
		}
		//tipo via
		if (isRequestValid && installationRDTO.getLocationRDTO().getCodeTypeRoad()!=null && !installationRDTO.getLocationRDTO().getCodeTypeRoad().trim().isEmpty() &&
				!validator.validateValueListGap(installationRDTO.getLocationRDTO().getCodeTypeRoad(),
				new Entity(ValueListConstants.CODE_TYPE_ROAD))) {
			isRequestValid = false;
			isCode = ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
					MessageContansts.MSG_FIELD_CODE_TYPE_ROAD,
					MessageContansts.MSG_ENTITY_INSTALLATION, installationRDTO.getLocationRDTO().getCodeTypeRoad());
		}
		//Tipus Instal·lació
		if (isRequestValid && !validator.validateValueListGap(installationRDTO.getType(),
				new Entity(ValueListConstants.CODE_TYPE_INSTALLATION))) {
			isRequestValid = false;
			isCode = ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
										MessageContansts.MSG_FIELD_TYPE,
										MessageContansts.MSG_ENTITY_INSTALLATION,
										installationRDTO.getType());
		}
		//Tipus Titularitat
		if (isRequestValid && !validator.validateValueListGap(installationRDTO.getCodeTypeTitularity(),
				new Entity(ValueListConstants.TITULARITY_TYPE))) {
			
			isCode = ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
									MessageContansts.MSG_FIELD_CODE_TYPE_TITULARITY,
									MessageContansts.MSG_ENTITY_INSTALLATION, 
									installationRDTO.getCodeTypeTitularity());
		}
		
		
		
		
		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);

		return returnRDTO;
	}

	public ReturnRDTO validateDelete(InstallationRDTO installationRDTO) {
		
		return validateUpdate(installationRDTO);
	}

	public ReturnRDTO validateInsertExpense(InstallationRDTO installationRDTO) {
		logger.info(LogsConstants.LOG_START);
		
		boolean isRequestValid = true;
		
		returnRDTO = new ReturnRDTO();
		
		isCode = ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription();
		isMessage = ReturnEnum.CODE_SUCCESS_VALIDATE.getMessage();


		
		if (!validator.validateExistsInstallationGap(installationRDTO.getCode(),true)){
			isRequestValid = false;
			isCode 		= ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(), 
					MessageContansts.MSG_ENTITY_INSTALLATION,
					installationRDTO.getCode());
			
		}
		if (isRequestValid && !validator.validateValueList(installationRDTO.getExpenseRDTO().getCodeZone(),
				new Type(ValueListConstants.ZONE_INSTALLATION))) {
			isRequestValid = false;
			isCode = ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
					MessageContansts.MSG_FIELD_ZONE,
					MessageContansts.MSG_ENTITY_EXPENSES_INSTALLATION, installationRDTO.getExpenseRDTO().getCodeZone());
		 
		}
		
		if (isRequestValid)
		{
			returnRDTO = validator.validateTypeSubtypeExpenseGap(installationRDTO.getExpenseRDTO().getCodeType(), installationRDTO.getExpenseRDTO().getCodeSubType(),
					MessageContansts.MSG_ENTITY_EXPENSES_INSTALLATION); 
				
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

	public ReturnRDTO validateInsertApportionment(InstallationRDTO installationRDTO) {
		logger.info(LogsConstants.LOG_START);
		
		returnRDTO = new ReturnRDTO();

		boolean isRequestValid = true;
		isCode = ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription();
		isMessage = ReturnEnum.CODE_SUCCESS_VALIDATE.getMessage();

		
		if (!validator.validateExistsInstallationGap(installationRDTO.getCode(),true)){
			isRequestValid = false;
			isCode 		= ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(), 
					MessageContansts.MSG_ENTITY_INSTALLATION,
					installationRDTO.getCode());
			
		}
		if (isRequestValid)
		{
			returnRDTO = validator.validateListValueListGap(Utils.getListCodeServices(installationRDTO.getLstApportionmentRDTO()), 
					new Entity(ValueListConstants.SERVICE), 
					MessageContansts.MSG_FIELD_SERVICE, 
					MessageContansts.MSG_ENTITY_EXPENSES_INSTALLATION); 
				
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

	public ReturnRDTO validateInsertAmortizationBase(InstallationRDTO installationRDTO) {
		logger.info(LogsConstants.LOG_START);
		
		
		returnRDTO = new ReturnRDTO();

		
		isCode = ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription();
		isMessage = ReturnEnum.CODE_SUCCESS_VALIDATE.getMessage();

		if (!validator.validateExistsInstallationGap(installationRDTO.getCode(),true)){
			isCode 		= ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(), 
					MessageContansts.MSG_ENTITY_INSTALLATION,
					installationRDTO.getCode());
			
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
		if (isRequestValid && !validator.validateExistsInstallationGap(actuationRDTO.getCodeEntity(),true)){
			isRequestValid = false;
			isCode 		= ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(), 
										MessageContansts.MSG_ENTITY_INSTALLATION,
										actuationRDTO.getCodeEntity());
		}
		if (isRequestValid && ! validator.validateValueList(actuationRDTO.getTypeActuation(), new Type(ValueListConstants.TYPE_ACTUATION_INSTALLATION))) {
			
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
		if (isRequestValid && !validator.validateExistsInstallationGap(actuationRDTO.getCodeEntity(),true)){
			
			isCode 		= ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(), 
										MessageContansts.MSG_ENTITY_INSTALLATION,
										actuationRDTO.getCodeEntity());
		}
		
		
		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);
		
		logger.info(LogsConstants.LOG_END);

		return returnRDTO;

	}

	public ReturnRDTO validateInsertDocumentarySupportExpense(
			DocumentarySupportExpenseRDTO documentarySupportExpenseRDTO) {
		boolean isRequestValid = true;
		
		returnRDTO = new ReturnRDTO();

		isCode = ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription();
		isMessage = ReturnEnum.CODE_SUCCESS_VALIDATE.getMessage();

		logger.info(LogsConstants.LOG_START);

		returnRDTO = validateInsertDocumentarySupport(documentarySupportExpenseRDTO);
		if (!returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
			isRequestValid = false;
			isCode = returnRDTO.getCode();
			isMessage = returnRDTO.getMessage();
		}
		
		if (isRequestValid)
		{
			returnRDTO = validator.validateTypeSubtypeExpenseGap(documentarySupportExpenseRDTO.getCodeTypeExpense(), documentarySupportExpenseRDTO.getCodeSubTypeExpense(),
					MessageContansts.MSG_ENTITY_DOCUMENTARY_SUPPORT_EXPENSES); 
				
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

	public ReturnRDTO validateDeleteDocumentarySupportExpense(
			DocumentarySupportExpenseRDTO documentarySupportExpenseRDTO) {
		return validateDeleteDocumentarySupport(documentarySupportExpenseRDTO);
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
		if (isRequestValid && !validator.validateExistsInstallationGap(documentarySupportRDTO.getCode(),true)){
			isCode 		= ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(), 
					MessageContansts.MSG_ENTITY_INSTALLATION,
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
		if (isRequestValid && !validator.validateExistsInstallationGap(documentarySupportRDTO.getCode(),true)){
			isCode 		= ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(), 
					MessageContansts.MSG_ENTITY_INSTALLATION,
					documentarySupportRDTO.getCode());
			
		}
		
		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);
		
		logger.info(LogsConstants.LOG_END);

		return returnRDTO;

	}
}
