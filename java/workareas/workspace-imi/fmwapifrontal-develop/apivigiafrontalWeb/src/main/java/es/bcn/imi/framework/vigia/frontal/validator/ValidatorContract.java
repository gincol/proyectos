package es.bcn.imi.framework.vigia.frontal.validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.commons.BaseTransactionIdRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;

@Lazy
@Service(ServicesConstants.SRV_FRONT_VALIDATE_CONTRACT_FMW)
public class ValidatorContract {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ServicesConstants.SRV_FRONT_VALIDATE_FMW)
	private ValidatorUtils validator;

	public ReturnRDTO validateContract(BaseTransactionIdRDTO rdto) {
		logger.info(LogsConstants.LOG_START);
		
		return validator.validateContract(rdto);
	}
}
