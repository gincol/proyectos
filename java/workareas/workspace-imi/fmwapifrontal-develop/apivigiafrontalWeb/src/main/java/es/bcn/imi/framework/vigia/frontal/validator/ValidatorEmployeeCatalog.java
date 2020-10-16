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
import es.bcn.vigia.fmw.libcommons.valueobject.Type;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.EmployeeCatalogRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.enums.ReturnEnum;

@Lazy
@Service(ServicesConstants.SRV_FRONT_VALIDATE_EMPLOYEE_CATALOG_FMW)
public class ValidatorEmployeeCatalog extends ValidatorContract {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ServicesConstants.SRV_FRONT_VALIDATE_FMW)
	private ValidatorUtils validator;

	private String isCode;

	private String isMessage;

	private ReturnRDTO returnRDTO;

	public ReturnRDTO validateInsert(EmployeeCatalogRDTO employeeCatalogRDTO) {

		logger.info(LogsConstants.LOG_START);
		
		boolean isRequestValid = true;

		returnRDTO = new ReturnRDTO();
		
		isCode = ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription();
		isMessage = ReturnEnum.CODE_SUCCESS_VALIDATE.getMessage();

		
		//Validaciones contra FMW
		//categoria professional
		if (!validator.validateValueList(employeeCatalogRDTO.getCodeProfessionalCategory(), new Type(ValueListConstants.PROFESSIONAL_CATEGORY))) {
			isRequestValid = false;
			isCode = ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
					MessageContansts.MSG_FIELD_PROFESSIONAL_CATEGORY,
					MessageContansts.MSG_ENTITY_EMPLOYEE_CATALOG, employeeCatalogRDTO.getCodeProfessionalCategory());
		}	
		//regimen treballador
		if (isRequestValid && !validator.validateValueList(employeeCatalogRDTO.getCodeWorkRegime(), new Type(ValueListConstants.WORK_REGIME))) {
			isRequestValid = false;
			isCode = ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
					MessageContansts.MSG_FIELD_WORK_REGIME,
					MessageContansts.MSG_ENTITY_EMPLOYEE_CATALOG, employeeCatalogRDTO.getCodeWorkRegime());
		}
		//relacio laboral
		if (isRequestValid && !validator.validateValueList(employeeCatalogRDTO.getCodeLaborRelationship(), new Type(ValueListConstants.LABOR_RELATION_SHIP))) {
			isRequestValid = false;
			isCode = ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
					MessageContansts.MSG_FIELD_LABOR_RELATION_SHIP,
					MessageContansts.MSG_ENTITY_EMPLOYEE_CATALOG, employeeCatalogRDTO.getCodeLaborRelationship());
		}
		//procedencia contratacio
		if (isRequestValid && !validator.validateValueList(employeeCatalogRDTO.getCodeContractingProvenance(), new Type(ValueListConstants.CONTRACTING_PROVENANCE))) {
			isRequestValid = false;
			isCode = ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
					MessageContansts.MSG_FIELD_CONTRATING_PROVENANCE,
					MessageContansts.MSG_ENTITY_EMPLOYEE_CATALOG, employeeCatalogRDTO.getCodeContractingProvenance());
		}
		//tius de personal
		if (isRequestValid && !validator.validateValueList(employeeCatalogRDTO.getTypePersonal(), new Type(ValueListConstants.TYPE_PERSONAL))) {
			isCode = ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage = String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(),
					MessageContansts.MSG_FIELD_EMPLOYEE_TYPE,
					MessageContansts.MSG_ENTITY_EMPLOYEE_CATALOG, employeeCatalogRDTO.getTypePersonal());
		}
		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);

		logger.info(LogsConstants.LOG_END);
		return returnRDTO;
	}

}
