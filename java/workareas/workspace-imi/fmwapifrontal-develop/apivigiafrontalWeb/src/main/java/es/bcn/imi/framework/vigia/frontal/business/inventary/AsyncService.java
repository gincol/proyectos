package es.bcn.imi.framework.vigia.frontal.business.inventary;


import javax.servlet.http.HttpServletRequest;

import es.bcn.vigia.fmw.libcommons.web.rest.rdto.AsyncRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterStatesRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnStatesRDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public interface AsyncService {

	public abstract ReturnStatesRDTO selectStates(QueryParameterStatesRDTO queryParameterStatesRDTO) throws ImiException;

	public abstract ReturnRDTO redirect(AsyncRDTO<?> rdto, String type, HttpServletRequest request) throws ImiException;

}
