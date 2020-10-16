package es.bcn.imi.framework.vigia.frontal.business.inventary;


import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterActuationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnActuationRDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public interface ActuationService {

	public abstract ReturnActuationRDTO selectActuations(QueryParameterActuationRDTO queryParameterActuationRDTO) throws ImiException;

}
