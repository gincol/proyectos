package es.bcn.imi.framework.vigia.inventari.business;

import es.bcn.vigia.fmw.libcommons.business.dto.CommerceBDTO;
import es.bcn.vigia.fmw.libcommons.business.dto.QueryParameterBDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnCommerceDetailedRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnCommerceElementsRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnMassiveRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public interface CommerceService {

	public abstract ReturnRDTO insert(CommerceBDTO commerceBDTO) throws ImiException;

	public abstract ReturnRDTO update(CommerceBDTO commerceBDTO) throws ImiException;

	public abstract ReturnRDTO delete(CommerceBDTO commerceBDTO) throws ImiException;

	public abstract ReturnRDTO insertElements(CommerceBDTO commerceBDTO) throws ImiException;

	public abstract ReturnMassiveRDTO selectMassive(QueryParameterBDTO queryParameterBDTO) throws ImiException;

	public abstract ReturnCommerceDetailedRDTO selectDetailed(QueryParameterBDTO queryParameterBDTO) throws ImiException;

	public abstract ReturnCommerceElementsRDTO selectElements(QueryParameterBDTO queryParameterBDTO) throws ImiException;

}
