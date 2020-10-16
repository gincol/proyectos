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
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CertificationCleaningServiceRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CertificationCollectionServiceRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CertificationExpenseInstallationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CertificationExtraordinaryRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CertificationInspectionRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CertificationInvestmentFurnitureRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CertificationInvestmentRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CertificationInvoiceRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CertificationOthersRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CertificationPersonalRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CertificationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CertificationRegularizationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CertificationServiceRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.Utils;
import es.bcn.vigia.fmw.libutils.enums.ReturnEnum;

@Lazy
@Service(ServicesConstants.SRV_FRONT_VALIDATE_CERTIFICATION_FMW)
public class ValidatorCertification extends ValidatorContract {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ServicesConstants.SRV_FRONT_VALIDATE_FMW)
	private ValidatorUtils validator;

	private String isCode;

	private String isMessage;

	private ReturnRDTO returnRDTO;

	public ReturnRDTO validateSendVersionAndHeader(CertificationRDTO certificationRDTO) {

		logger.info(LogsConstants.LOG_START);

		return validateCommonCertificationServiceCode(certificationRDTO.getCodeService(),
				MessageContansts.MSG_ENTITY_VERSION_CERTIFICATION);
	}

	public ReturnRDTO validateSendOthersExpensesDetails(CertificationOthersRDTO certificationOthersRDTO) {
		
		logger.info(LogsConstants.LOG_START);
		boolean isRequestValid = true;

		returnRDTO = validateCommonCertificationServiceCode(certificationOthersRDTO.getCodeService(),
				MessageContansts.MSG_ENTITY_CERTIFICATION_OTHERS);

		if (!returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
			isRequestValid = false;
		}
		
		if (isRequestValid && !validator.validateExistsCodeSubtypeConceptInstallationGap(certificationOthersRDTO.getCodeSubtypeConceptInst())) {
			isCode = ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
					MessageContansts.MSG_FIELD_CODE_SUBTYPE_CONCEPTE_INSTALLATION,MessageContansts.MSG_ENTITY_CERTIFICATION_OTHERS,
					certificationOthersRDTO.getCodeSubtypeConceptInst());
		}
		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);

		return returnRDTO;
		
	}


	public ReturnRDTO validateSendExtraordinaryConceptsDetails(
			CertificationExtraordinaryRDTO certificationExtraordinaryRDTO) {
		

		logger.info(LogsConstants.LOG_START);
		boolean isRequestValid = true;

		returnRDTO = validateCommonsExtraordinaryConceptsRegularization(certificationExtraordinaryRDTO,MessageContansts.MSG_EXTRAORDINARY_CONCEPT);

		if (!returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
			isRequestValid = false;
		}
		
		if (isRequestValid && !validator.validateExistsCodeConceptCertificationGap(certificationExtraordinaryRDTO.getCodeCertificationConcept())) {
			isCode = ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
					MessageContansts.MSG_FIELD_CODE_CERTIFICATION_CONCEPT,MessageContansts.MSG_EXTRAORDINARY_CONCEPT,
					certificationExtraordinaryRDTO.getCodeCertificationConcept());
		}
		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);

		return returnRDTO;

	}

	public ReturnRDTO validateCommonsExtraordinaryConceptsRegularization(
			CertificationExtraordinaryRDTO certificationExtraordinaryRDTO,String entity) {

		logger.info(LogsConstants.LOG_START);
		boolean isRequestValid = true;

		returnRDTO = validateCommonCertificationServiceCode(certificationExtraordinaryRDTO.getCodeService(),
				entity);

		if (!returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
			isRequestValid = false;
		}
		
		if (isRequestValid && !validator.validateExistsCodeTypeExtraordinaryConceptGap(certificationExtraordinaryRDTO.getCodeTypeConceptExtra())) {
			isCode = ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
					MessageContansts.MSG_FIELD_TYPE_EXTRAORDINARY_CONCEPT,entity,
					certificationExtraordinaryRDTO.getCodeTypeConceptExtra());
		}
		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);

		return returnRDTO;
	}

	private ReturnRDTO validateCommonCertificationServiceCode(String codeService, String entity) {

		returnRDTO = new ReturnRDTO();

		isCode = ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription();
		isMessage = ReturnEnum.CODE_SUCCESS_VALIDATE.getMessage();

		if (!validator.validateValueListGap(codeService, new Entity(ValueListConstants.SERVICE))) {
			isCode = ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
					MessageContansts.MSG_FIELD_SERVICE_CODE, entity, codeService);
		}

		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);

		return returnRDTO;
	}

	public ReturnRDTO validateSendInvoiceDetails(CertificationInvoiceRDTO certificationInvoiceRDTO) {
		
		logger.info(LogsConstants.LOG_START);
		boolean isRequestValid = true;

		returnRDTO = validateCommonsExtraordinaryConceptsRegularization(certificationInvoiceRDTO,MessageContansts.MSG_ENTITY_CERTIFICATION_INVOICE);

		if (!returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
			isRequestValid = false;
		}
		
		if (isRequestValid && !validator.validateExistsCodeTypeIvaGap(certificationInvoiceRDTO.getCodeTypeIva())) {
			isCode = ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
					MessageContansts.MSG_FIELD_CODE_TYPE_IVA,MessageContansts.MSG_ENTITY_CERTIFICATION_INVOICE,
					certificationInvoiceRDTO.getCodeTypeIva());
		}
		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);

		return returnRDTO;

	}

	public ReturnRDTO validateSendInspectionsDetails(CertificationInspectionRDTO certificationInspectionRDTO) {
		returnRDTO = new ReturnRDTO();

		isCode = ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription();
		isMessage = ReturnEnum.CODE_SUCCESS_VALIDATE.getMessage();

		if (!validator.validateExistsItineraryGap(certificationInspectionRDTO.getCodeItinerary())) {
			isCode = ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
					MessageContansts.MSG_FIELD_ITINERARY_CODE, MessageContansts.MSG_ENTITY_CERTIFICATION_INSPECTION,
					certificationInspectionRDTO.getCodeItinerary());
		}

		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);

		return returnRDTO;
	}

	public ReturnRDTO validateSendInstallationExpensesDetails(
			CertificationExpenseInstallationRDTO certificationExpenseInstallationRDTO) {

		logger.info(LogsConstants.LOG_START);

		boolean isRequestValid = true;

		returnRDTO = new ReturnRDTO();

		isCode = ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription();
		isMessage = ReturnEnum.CODE_SUCCESS_VALIDATE.getMessage();
		returnRDTO = validateCommonCertificationServiceCode(certificationExpenseInstallationRDTO.getCodeService(),
				MessageContansts.MSG_ENTITY_CERTIFICATION_INSTALLATION_EXPENSE);

		if (!returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
			isRequestValid = false;
		}

		if (isRequestValid
				&& !validator.validateExistsInstallationGap(certificationExpenseInstallationRDTO.getCode(), true)) {
			isRequestValid = false;
			isCode = ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(),
					MessageContansts.MSG_ENTITY_INSTALLATION, certificationExpenseInstallationRDTO.getCode());

		}
		if (isRequestValid && !validator.validateExistsCodeSubtypeConceptInstallationGap(certificationExpenseInstallationRDTO.getCodeSubtypeConceptInst())) {
			isCode = ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
					MessageContansts.MSG_FIELD_CODE_SUBTYPE_CONCEPTE_INSTALLATION,MessageContansts.MSG_ENTITY_CERTIFICATION_INSTALLATION_EXPENSE,
					certificationExpenseInstallationRDTO.getCodeSubtypeConceptInst());
		}
		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);

		return returnRDTO;
	}

	public ReturnRDTO validateSendInvestmentRRMMDetails(CertificationInvestmentRDTO certificationInvestmentRRMMRDTO) {
		logger.info(LogsConstants.LOG_START);

		boolean isRequestValid = true;

		returnRDTO = new ReturnRDTO();

		isCode = ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription();
		isMessage = ReturnEnum.CODE_SUCCESS_VALIDATE.getMessage();
		returnRDTO = validateCommonCertificationServiceCode(certificationInvestmentRRMMRDTO.getCodeService(),
				MessageContansts.MSG_ENTITY_CERTIFICATION_INVESMENT_RRMM);

		if (!returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
			isRequestValid = false;
		}

		if (isRequestValid && !validator.validateExistsMaterialResourceGap(certificationInvestmentRRMMRDTO.getCode())) {
			isRequestValid = false;
			isCode = ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(),
					MessageContansts.MSG_ENTITY_MATERIAL_RESOURCE, certificationInvestmentRRMMRDTO.getCode());

		}
		if (isRequestValid && !validator.validateValueListGap(certificationInvestmentRRMMRDTO.getCodeGroup(),
				new Entity(ValueListConstants.GROUP_MATERIAL_RESOURCE))) {
			isRequestValid = false;
			isCode = ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
					MessageContansts.MSG_FIELD_GROUP_CODE, MessageContansts.MSG_ENTITY_MATERIAL_RESOURCE, certificationInvestmentRRMMRDTO.getCodeGroup());
		}
		if (isRequestValid && !validator.validateValueListGap(certificationInvestmentRRMMRDTO.getCodeType(),
				new Entity(ValueListConstants.MATERIAL_RESOURCE_TYPE))) {
			isRequestValid = false;
			isCode = ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
					MessageContansts.MSG_FIELD_TYPE, MessageContansts.MSG_ENTITY_MATERIAL_RESOURCE, certificationInvestmentRRMMRDTO.getCodeType());
		}
		//group servei
		if (isRequestValid && !validator.validateValueListGap(certificationInvestmentRRMMRDTO.getCodeGroupService(),
				new Entity(ValueListConstants.GROUP_SERVICE))) {
			isCode = ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
					MessageContansts.MSG_FIELD_SERVICE_GROUP_CODE,
					MessageContansts.MSG_ENTITY_MATERIAL_RESOURCE, certificationInvestmentRRMMRDTO.getCodeGroupService());
		}
		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);

		return returnRDTO;
	}

	public ReturnRDTO validateSendInvestmentFurnitureDetails(
			CertificationInvestmentFurnitureRDTO certificationInvestmentFurnitureRDTO) {
		logger.info(LogsConstants.LOG_START);

		boolean isRequestValid = true;

		returnRDTO = new ReturnRDTO();

		isCode = ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription();
		isMessage = ReturnEnum.CODE_SUCCESS_VALIDATE.getMessage();
		returnRDTO = validateCommonCertificationServiceCode(certificationInvestmentFurnitureRDTO.getCodeService(),
				MessageContansts.MSG_ENTITY_CERTIFICATION_INVESMENT_FURNITURE);

		if (!returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
			isRequestValid = false;
		}

		if (isRequestValid && !validator.validateExistsGroupFurniture(certificationInvestmentFurnitureRDTO.getCodeGroup())) {
			isRequestValid = false;
			isCode = ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
					MessageContansts.MSG_FIELD_GROUP_CODE, MessageContansts.MSG_ENTITY_FURNITURE, certificationInvestmentFurnitureRDTO.getCodeGroup());
		}
		if (isRequestValid && !validator.validateValueListGap(certificationInvestmentFurnitureRDTO.getCodeType(),
				new Entity(ValueListConstants.FURNITURE_TYPE))) {
			isRequestValid = false;
			isCode = ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
					MessageContansts.MSG_FIELD_TYPE, MessageContansts.MSG_ENTITY_FURNITURE, certificationInvestmentFurnitureRDTO.getCodeType());
		}
		//group servei
		if (isRequestValid && !validator.validateValueListGap(certificationInvestmentFurnitureRDTO.getCodeGroupService(),
				new Entity(ValueListConstants.GROUP_SERVICE))) {
			isCode = ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
					MessageContansts.MSG_FIELD_SERVICE_GROUP_CODE,
					MessageContansts.MSG_ENTITY_FURNITURE, certificationInvestmentFurnitureRDTO.getCodeGroupService());
		}
		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);

		return returnRDTO;
	}

	public ReturnRDTO validateSendCleaningServiceDetails(
			CertificationCleaningServiceRDTO certificationCleaningServiceRDTO) {
				
		return validateCommonSendCertificationService(certificationCleaningServiceRDTO,
				MessageContansts.MSG_ENTITY_CERTIFICATION_CLEANING_SERVICE);
	}

	public ReturnRDTO validateSendCollectionServiceDetails(
			CertificationCollectionServiceRDTO certificationCollectionServiceRDTO) {
		logger.info(LogsConstants.LOG_START);

		boolean isRequestValid = true;

		returnRDTO = new ReturnRDTO();

		isCode = ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription();
		isMessage = ReturnEnum.CODE_SUCCESS_VALIDATE.getMessage();
		returnRDTO = validateCommonSendCertificationService(certificationCollectionServiceRDTO,
				MessageContansts.MSG_ENTITY_CERTIFICATION_COLLECTION_SERVICE);

		if (!returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
			isRequestValid = false;
		}
		if (isRequestValid) {
			returnRDTO = validator.validateListValueListGap(
					Utils.getListCodesTypePayment(certificationCollectionServiceRDTO.getCertificationWeighsRDTO()),
					new Entity(ValueListConstants.TYPE_PAYMENT), MessageContansts.MSG_FIELD_CODE_TYPE_PAYMENT,
					MessageContansts.MSG_ENTITY_CERTIFICATION_COLLECTION_SERVICE);
			if (!returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
				isCode = returnRDTO.getCode();
				isMessage = returnRDTO.getMessage();
			}
		}

		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);

		return returnRDTO;
	}

	private ReturnRDTO validateCommonSendCertificationService(CertificationServiceRDTO certificationServiceRDTO,
			String entity) {

		logger.info(LogsConstants.LOG_START);

		boolean isRequestValid = true;

		returnRDTO = new ReturnRDTO();

		isCode = ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription();
		isMessage = ReturnEnum.CODE_SUCCESS_VALIDATE.getMessage();
		returnRDTO = validateCommonCertificationServiceCode(certificationServiceRDTO.getCodeService(), entity);

		if (!returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
			isRequestValid = false;
		}
		if (isRequestValid)
		{
			returnRDTO = validator.validateCodeItinerary(certificationServiceRDTO.getCodeItinerary());
			if (!returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
				isRequestValid = false;
				isCode = returnRDTO.getCode();
				isMessage = returnRDTO.getMessage();
			}	
		}
		if (isRequestValid && !validator.validateCoherenceServiceItinerary(certificationServiceRDTO.getCodeItinerary(),certificationServiceRDTO.getCodeService())) {
			isRequestValid = false;
			isCode = ReturnEnum.CODE_ERROR_COHERENCE_SERVICE_ITNERARY.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_COHERENCE_SERVICE_ITNERARY.getMessage(),
					certificationServiceRDTO.getCodeItinerary(),certificationServiceRDTO.getCodeService());
		}
		if (isRequestValid && !validator.validateValueListGap(certificationServiceRDTO.getCodeDistrict(), new Entity(ValueListConstants.CODE_DISTRICT))) {
			isRequestValid = false;
			isCode = ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
					MessageContansts.MSG_FIELD_DISTRICT_CODE, entity, certificationServiceRDTO.getCodeDistrict());
		}
		if (isRequestValid && !validator.validateExistsItineraryGap(certificationServiceRDTO.getCodeItinerary())) {
			isCode = ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
					MessageContansts.MSG_FIELD_ITINERARY_CODE, entity, certificationServiceRDTO.getCodeItinerary());
		}

		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);

		return returnRDTO;
	}

	public ReturnRDTO validateSendStaffExpensesDetails(CertificationPersonalRDTO certificationPersonalRDTO) {
		logger.info(LogsConstants.LOG_START);

		boolean isRequestValid = true;

		returnRDTO = new ReturnRDTO();

		isCode = ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription();
		isMessage = ReturnEnum.CODE_SUCCESS_VALIDATE.getMessage();
		returnRDTO = validateCommonCertificationServiceCode(certificationPersonalRDTO.getCodeService(),
				MessageContansts.MSG_ENTITY_CERTIFICATION_PERSONAL_EXPENSE);

		if (!returnRDTO.getCode().equals(ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription())) {
			isRequestValid = false;
		}

		if (isRequestValid && !validator.validateExistsInstallationGap(certificationPersonalRDTO.getCode(), true)) {
			isRequestValid = false;
			isCode = ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ENTITY_NOT_FOUND.getMessage(),
					MessageContansts.MSG_ENTITY_INSTALLATION, certificationPersonalRDTO.getCode());

		}
		if (isRequestValid && !validator.validateExistsCodeSubtypeConceptInstallationGap(certificationPersonalRDTO.getCodeSubtypeConceptInst())) {
			isCode = ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
					MessageContansts.MSG_FIELD_CODE_SUBTYPE_CONCEPTE_INSTALLATION,MessageContansts.MSG_ENTITY_CERTIFICATION_PERSONAL_EXPENSE,
					certificationPersonalRDTO.getCodeSubtypeConceptInst());
		}
		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);

		return returnRDTO;

	}

	public ReturnRDTO validateSendRegularizationsDetails(
			CertificationRegularizationRDTO certificationRegularizationRDTO) {
		return validateCommonsExtraordinaryConceptsRegularization(certificationRegularizationRDTO,MessageContansts.MSG_ENTITY_CERTIFICATION_REGULARIZATION);
	}

}
