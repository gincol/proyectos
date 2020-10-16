package es.bcn.imi.framework.vigia.orquestrador.business.inventary;

import javax.servlet.http.HttpServletRequest;

import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CommerceRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnCommerceDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnCommerceElementsRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public interface CommerceService {

	public abstract ReturnRDTO redirect(CommerceRDTO commerceRDTO, HttpServletRequest request) throws ImiException;
	
	public abstract ReturnRDTO insert(CommerceRDTO commerceRDTO) throws ImiException;

	public abstract ReturnRDTO update(CommerceRDTO commerceRDTO) throws ImiException;

	public abstract ReturnRDTO delete(CommerceRDTO commerceRDTO) throws ImiException;
	
	public abstract ReturnRDTO insertElements(CommerceRDTO commerceRDTO) throws ImiException;

	public abstract ReturnMassiveRDTO selectMassive(QueryParameterRDTO queryParameterRDTO) throws ImiException;

	public abstract ReturnCommerceDetailedRDTO selectDetailed(QueryParameterRDTO queryParameterRDTO) throws ImiException;

	public abstract ReturnCommerceElementsRDTO selectElements(QueryParameterRDTO queryParameterRDTO) throws ImiException;
}
