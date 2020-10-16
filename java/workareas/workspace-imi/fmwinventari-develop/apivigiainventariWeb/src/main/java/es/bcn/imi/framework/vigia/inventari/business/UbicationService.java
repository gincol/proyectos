package es.bcn.imi.framework.vigia.inventari.business;

import es.bcn.vigia.fmw.libcommons.business.dto.QueryParameterBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.UbicationBDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnUbicationDetailedRDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public interface UbicationService {

	public abstract ReturnRDTO insert(UbicationBDTO ubicationBDTO) throws ImiException;

	public abstract ReturnRDTO update(UbicationBDTO ubicationBDTO) throws ImiException;

	public abstract ReturnRDTO delete(UbicationBDTO ubicationBDTO) throws ImiException;

	public abstract ReturnMassiveRDTO selectMassive(QueryParameterBDTO queryParameterBDTO) throws ImiException;

	public abstract ReturnUbicationDetailedRDTO selectDetailed(QueryParameterBDTO queryParameterBDTO) throws ImiException;
}
