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
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.TimeClockSummaryRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.enums.ReturnEnum;

@Lazy
@Service(ServicesConstants.SRV_FRONT_VALIDATE_TIME_CLOCK_SUMMARY_FMW)
public class ValidatorTimeClockSummary extends ValidatorContract {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ServicesConstants.SRV_FRONT_VALIDATE_FMW)
	private ValidatorUtils validator;

	private String isCode;

	private String isMessage;

	private ReturnRDTO returnRDTO;

	public ReturnRDTO validateInsert(TimeClockSummaryRDTO timeClockSummaryRDTO) {

		logger.info(LogsConstants.LOG_START);
		
		returnRDTO = new ReturnRDTO();
		
		boolean isRequestValid = true;
		
		isCode = ReturnEnum.CODE_SUCCESS_VALIDATE.getCodeDescription();
		isMessage = ReturnEnum.CODE_SUCCESS_VALIDATE.getMessage();
		
		
		
		if (!StringUtils.isEmpty(timeClockSummaryRDTO.getCodeInstallation()) && !validator.validateExistsInstallationGap(timeClockSummaryRDTO.getCodeInstallation(), true))
		{	
			isRequestValid = false;
			isCode 		= ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_ATTRIBUTE_NOT_FOUND.getMessage(), 
					MessageContansts.MSG_FIELD_CODE_INSTALLATION,
					MessageContansts.MSG_ENTITY_TIME_CLOCK_SUMMARY,
					timeClockSummaryRDTO.getCodeInstallation());
		}
		if (isRequestValid && validator.validateExistsTimeClockSummary(timeClockSummaryRDTO.getCodeContract(), timeClockSummaryRDTO.getCodeInstallation(), timeClockSummaryRDTO.getDate()) && StringUtils.isEmpty(timeClockSummaryRDTO.getObservation()))
		{	
			isCode 		= ReturnEnum.CODE_ERROR_FIELD_MANDATORY.getCodeDescription();
			isMessage	= String.format(ReturnEnum.CODE_ERROR_FIELD_MANDATORY.getMessage(), 
					MessageContansts.MSG_FIELD_OBSERVATION,
					MessageContansts.MSG_ENTITY_TIME_CLOCK_SUMMARY
					);
		}
				
		
		returnRDTO.setCode(isCode);
		returnRDTO.setMessage(isMessage);

		return returnRDTO;
	}

}
