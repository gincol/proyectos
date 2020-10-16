package es.bcn.imi.framework.vigia.inventari.business;

import es.bcn.vigia.fmw.libcommons.business.dto.ActuationBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.QueryParameterActuationBDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnActuationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public interface ActuationService {

	public abstract ReturnRDTO insert(ActuationBDTO actuationBDTO) throws ImiException;

	public abstract ReturnRDTO delete(ActuationBDTO actuationBDTO) throws ImiException;

	public abstract ReturnActuationRDTO selectActuation(QueryParameterActuationBDTO queryParameterActuationBDTO) throws ImiException;

}
