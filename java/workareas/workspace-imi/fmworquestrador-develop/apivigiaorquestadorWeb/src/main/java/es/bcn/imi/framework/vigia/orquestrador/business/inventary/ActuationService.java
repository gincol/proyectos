package es.bcn.imi.framework.vigia.orquestrador.business.inventary;

import javax.servlet.http.HttpServletRequest;

import es.bcn.vigia.fmw.libcommons.web.rest.rdto.ActuationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterActuationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnActuationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public interface ActuationService {

	public abstract ReturnRDTO redirect(ActuationRDTO actuationRDTO, HttpServletRequest request) throws ImiException;
	
	public abstract ReturnRDTO insert(ActuationRDTO actuationRDTO) throws ImiException;
	
	public abstract ReturnRDTO delete(ActuationRDTO actuationRDTO) throws ImiException;

	public abstract ReturnActuationRDTO selectActuation(QueryParameterActuationRDTO queryParameterActuationRDTO) throws ImiException;
	
}
