package es.bcn.imi.framework.vigia.orquestrador.business.inventary;

import javax.servlet.http.HttpServletRequest;

import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.UbicationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnUbicationDetailedRDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public interface UbicationService {

	public abstract ReturnRDTO redirect(UbicationRDTO ubicationRDTO, HttpServletRequest request) throws ImiException;
	
	public abstract ReturnRDTO insert(UbicationRDTO ubicationBDTO) throws ImiException;

	public abstract ReturnRDTO update(UbicationRDTO ubicationBDTO) throws ImiException;

	public abstract ReturnRDTO delete(UbicationRDTO ubicationBDTO) throws ImiException;

	public abstract ReturnMassiveRDTO selectMassive(QueryParameterRDTO queryParameterRDTO) throws ImiException;

	public abstract ReturnUbicationDetailedRDTO selectDetailed(QueryParameterRDTO queryParameterRDTO) throws ImiException;


	
}
